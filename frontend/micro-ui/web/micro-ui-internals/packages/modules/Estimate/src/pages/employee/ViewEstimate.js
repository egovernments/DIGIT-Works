import React,{ Fragment,useState,useEffect } from 'react'
import { Loader,WorkflowActions,WorkflowTimeline,Header,StatusTable,Card,Row,HorizontalNav} from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ViewEstimate = (props) => {
    
    const { t } = useTranslation()
    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate")
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
        estimateNumber:estimate?.estimateNumber,
        estimateType:"Original Estimate",
        projectId:project?.projectNumber,
        dateOfProposal: Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal),
        projectName:project?.name,
        projectDesc: project?.description
      }) 
    }, [project])
    
    

    if(isProjectLoading || isEstimateLoading) return <Loader />

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("ESTIMATE_VIEW_ESTIMATE")}</Header>
            </div>
            <Card className={"employeeCard-override"} >
                <StatusTable>
                    <Row className="border-none" label={`${t("WORKS_ESTIMATE_NUMBER")}:`} text={cardState?.estimateNumber} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_ESTIMATE_TYPE")}:`} text={cardState?.estimateType} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_PROJECT_ID")}:`} text={cardState?.projectId} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("ESTIMATE_PROPOSAL_DATE")}:`} text={cardState?.dateOfProposal} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("ESTIMATE_PROJECT_NAME")}:`} text={cardState?.projectName} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PROJECT_DESC")}:`} text={cardState?.projectDesc} textStyle={{ whiteSpace: "pre" }} />
                </StatusTable>
            </Card>

            <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
                {
                    (activeLink === "Project_Details") && (
                        <ViewProject fromUrl={false} tenantId={tenantId} projectNumber={cardState?.projectId} />
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