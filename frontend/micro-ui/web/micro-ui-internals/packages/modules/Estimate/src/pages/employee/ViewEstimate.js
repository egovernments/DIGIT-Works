import React,{ Fragment,useState,useEffect } from 'react'
import { Loader,Header,StatusTable,Card,Row,HorizontalNav,ViewDetailsCard} from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ViewEstimate = (props) => {
    
    const { t } = useTranslation()
    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    
    const [cardState,setCardState] = useState({})
    const [activeLink, setActiveLink] = useState("Estimate_Details");
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
    const { isLoading: isEstimateLoading,data:estimate } = Digit.Hooks.estimates.useEstimateSearch({
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


    
    useEffect(() => {
      //here set cardstate when estimate and project is available
        setCardState({
            "WORKS_ESTIMATE_NUMBER": estimate?.estimateNumber,
            "WORKS_ESTIMATE_TYPE": "Original Estimate",
            "WORKS_PROJECT_ID": project?.projectNumber,
            "ESTIMATE_PROPOSAL_DATE": Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal),
            "ESTIMATE_PROJECT_NAME": project?.name,
            "PROJECT_DESC": project?.description
        }) 
    }, [project])
    
    

    if(isProjectLoading || isEstimateLoading) return <Loader />

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("ESTIMATE_VIEW_ESTIMATE")}</Header>
            </div>
            <ViewDetailsCard cardState={cardState} t={t}/>
            <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
                {
                    (activeLink === "Project_Details") && (
                        <ViewProject fromUrl={false} tenantId={tenantId} projectNumber={project?.projectNumber} />
                    )
                }
                {
                    (activeLink === "Estimate_Details") && (
                        <ViewEstimate />
                    )
                }
            </HorizontalNav>
        </div>
    )
}

export default ViewEstimate