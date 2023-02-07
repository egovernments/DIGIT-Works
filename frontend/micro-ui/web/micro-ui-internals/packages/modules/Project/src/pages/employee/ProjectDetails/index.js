import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader,Loader,SubmitBar,ActionBar, HorizontalNav } from '@egovernments/digit-ui-react-components'
import React, { Fragment,useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory } from 'react-router-dom'
import ProjectDetailsNavDetails from './ProjectDetailsNavDetails'

const configNavItems = [
    {
        "name":"Project_Details",
        "code":"WORKS_PROJECT_DETAILS",
    },
    {
        "name":"Financial_Details",
        "code":"WORKS_FINANCIAL_DETAILS"
    }
]

const ProjectDetails = () => {
    const { t } = useTranslation();
    const [activeLink, setActiveLink] = useState("Project_Details");
    const tenantId =  Digit.ULBService.getCurrentTenantId();
    const queryStrings = Digit.Hooks.useQueryParams();
    const history = useHistory();
    const searchParams = {
        Projects : [
            {
                tenantId : queryStrings?.tenantId,
                projectNumber : queryStrings?.projectNumber
            }
        ]
    } 
    const filters = {
        limit : 10,
        offset : 0
    }

    const handleParentProjectSearch = (parentProjectNumber) => {
        history.push(`/${window.contextPath}/employee/project/project-details?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${parentProjectNumber}`);
    }

    const { data, isLoading } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, tenantId, searchParams, filters);
    if(!isLoading) {
        
    }
    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_PROJECT_DETAILS")}</Header>
            </div>

            <Card className={"employeeCard-override"} >
                <CardSubHeader style={{ marginBottom: "16px", fontSize: "24px" }}>{t("WORKS_PROJECT_DETAILS")}</CardSubHeader>
                <StatusTable>
                    <Row className="border-none" label={`${t("WORKS_PROJECT_ID")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectID} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PDF_STATIC_LABEL_ESTIMATE_PROPOSAL_DATE")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectProposalDate} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PDF_STATIC_LABEL_ESTIMATE_PROJECT_NAME")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectName} textStyle={{ whiteSpace: "pre" }} isMandotary={true} />
                    <Row className="border-none" label={`${t("PROJECT_DESC")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectDesc} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_THE_PROJECT_HAS_SUB_PROJECT_LABEL")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectHasSubProject} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_PARENT_PROJECT_ID")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID} textStyle={{ whiteSpace: "pre" }} isValueLink={data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID === "NA" ? "" : data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID} navigateLinkHandler={()=>handleParentProjectSearch(data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID)}/>
                </StatusTable>
            </Card>
            <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>  
              <ProjectDetailsNavDetails 
                activeLink={activeLink}
              />
            </HorizontalNav>
            <ActionBar>
                <SubmitBar onSubmit={() => { }} label={t("WORKS_ACTIONS")} />
            </ActionBar>
        </div>
    )
}

export default ProjectDetails;