import React,{ Fragment,useState,useEffect } from 'react'
import { Loader,Header, MultiLink, StatusTable,Card,Row,HorizontalNav,ViewDetailsCard, Toast} from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ViewEstimate = (props) => {
    
    const { t } = useTranslation()
    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    const [cardState,setCardState] = useState([])
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

    const HandleDownloadPdf = () => {
        Digit.Utils.downloadEgovPDF('estimate/estimates',{estimateNumber,tenantId},`Estimate-${estimateNumber}.pdf`)
    }


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
        setCardState([
            {
                title: '',
                values: [
                  { title: "ESTIMATE_ESTIMATE_NO", value: estimate?.estimateNumber },
                  { title: "WORKS_ESTIMATE_TYPE", value: t("ORIGINAL_ESTIMATE") },
                  { title: "WORKS_PROJECT_ID", value: project?.projectNumber },
                  { title: "ES_COMMON_PROPOSAL_DATE", value: Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal)},
                  { title: "ES_COMMON_PROJECT_NAME", value: project?.name },
                  { title: "PROJECTS_DESCRIPTION", value: project?.description }
                ]
              }
        ])
    }, [project, estimate])
    
    

    if(isProjectLoading || isEstimateLoading) return <Loader />

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header className="works-header-view" styles={{ marginLeft: "0px", paddingTop: "10px" }}>{t("ESTIMATE_VIEW_ESTIMATE")}</Header>
                <MultiLink
                   onHeadClick={() => HandleDownloadPdf()}
                   downloadBtnClassName={"employee-download-btn-className"}
                   label={t("CS_COMMON_DOWNLOAD")}
                />
            </div>
            {(project || estimate) && <ViewDetailsCard cardState={cardState} t={t}/>}
            {
                estimate && <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
                    {
                        (activeLink === "Project_Details") && (
                            <ViewProject fromUrl={false} tenantId={tenantId} projectNumber={project?.projectNumber} module="estimate" />
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