import React,{ Fragment,useState,useEffect } from 'react'
import { Loader,Header,StatusTable,Card,Row,HorizontalNav,ViewDetailsCard, Toast} from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ViewEstimate = (props) => {
    
    const { t } = useTranslation()
    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    const [cardState,setCardState] = useState({})
    const [activeLink, setActiveLink] = useState("Estimate_Details");
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const configNavItems = [
        {
            "name": "Project_Details",
            "code": "WORKS_PROJECT_DETAILS",
            "active": true
        },
        {
            "name": "Estimate_Details",
            "code": "WORKS_ESTIMATE_DETAILS",
            "active": true
        }
    ]

    const ViewEstimate = Digit.ComponentRegistryService.getComponent("ViewEstimatePage");
    const ViewProject = Digit.ComponentRegistryService.getComponent("ViewProject");

    //fetching estimate data
    const { isLoading: isEstimateLoading,data:estimate, isError : isEstimateError } = Digit.Hooks.estimates.useEstimateSearch({
        tenantId,
        filters: { estimateNumber }
    })
    //fetching project data
    const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
        tenantId,
        searchParams: {
            Projects: [
                {
                    tenantId,
                    id:estimate?.projectId
                }
            ]
        },
        config:{
            enabled: !!(estimate?.projectId) 
        }
    })

    useEffect(()=>{
        if(isEstimateError || (!isEstimateLoading && !estimate)) {
            setToast({show : true, label : t("COMMON_ESTIMATE_NOT_FOUND"), error : true});
        }
    },[isEstimateLoading, isEstimateError, estimate])

    const handleToastClose = () => {
        setToast({show : false, label : "", error : false});
    }
    
    useEffect(() => {
      //here set cardstate when estimate and project is available
        setCardState({
            "ESTIMATE_ESTIMATE_NO": estimate?.estimateNumber,
            "WORKS_ESTIMATE_TYPE": "Original Estimate",
            "WORKS_PROJECT_ID": project?.projectNumber,
            "ES_COMMON_PROPOSAL_DATE": Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal),
            "ES_COMMON_PROJECT_NAME": project?.name,
            "PROJECTS_DESCRIPTION": project?.description
        }) 
    }, [project])
    
    

    if(isProjectLoading || isEstimateLoading) return <Loader />

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("ESTIMATE_VIEW_ESTIMATE")}</Header>
            </div>
            {(project || estimate) && <ViewDetailsCard cardState={cardState} t={t}/>}
            {
                estimate && <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
                    {
                        (activeLink === "Project_Details") && (
                            <ViewProject fromUrl={false} tenantId={tenantId} projectNumber={project?.projectNumber} />
                        )
                    }
                    {
                        (activeLink === "Estimate_Details") && (
                            <ViewEstimate editApplicationNumber={project?.projectNumber}/>
                        )
                    }
                </HorizontalNav>
            }
            {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
        </div>
    )
}

export default ViewEstimate