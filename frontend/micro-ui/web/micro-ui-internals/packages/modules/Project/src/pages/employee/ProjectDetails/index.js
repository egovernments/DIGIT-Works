import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader,Loader,SubmitBar,ActionBar, HorizontalNav } from '@egovernments/digit-ui-react-components'
import React, { Fragment,useEffect,useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory } from 'react-router-dom'
import ProjectDetailsNavDetails from './ProjectDetailsNavDetails'

const ProjectDetails = () => {
    const { t } = useTranslation();
    const [activeLink, setActiveLink] = useState("Project_Details");
    const tenantId =  Digit.ULBService.getCurrentTenantId();
    const queryStrings = Digit.Hooks.useQueryParams();
    const history = useHistory();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    const [configNavItems, setNavTypeConfig] = useState([]);
    const [subProjects, setSubProjects] = useState([]);
    const navConfigs = [
        {
            "name":"Project_Details",
            "code":"WORKS_PROJECT_DETAILS",
            "active" : true 
        },
        {
            "name":"Financial_Details",
            "code":"WORKS_FINANCIAL_DETAILS",
            "active" : true 
        },
        {
            "name":"Sub_Projects_Details",
            "code":"PROJECTS_SUB_PROJECT_DETAILS",
            "active" : false 
        }
    ]

    const searchParams = {
        Projects : [
            {
                tenantId : queryStrings?.tenantId || tenantId,
                projectNumber : queryStrings?.projectNumber
            }
        ]
    } 
    const filters = {
        limit : 11,
        offset : 0,
        includeAncestors : true,
        includeDescendants : true
    }

    const handleParentProjectSearch = (parentProjectNumber) => {
        history.push(`/${window.contextPath}/employee/project/project-details?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${parentProjectNumber}`);
    }

    const handleNavigateToEstimatesScreen = () => {
        history.push(`/${window.contextPath}/employee/estimate/create-estimate?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${searchParams?.Projects?.[0]?.projectNumber}`);
    }

    const { data } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, tenantId, searchParams, filters, headerLocale);

    //update config for Nav once we get the data
    useEffect(()=>{
        if(data?.projectDetails?.subProjects.length > 0) {
            navConfigs[2].active = true;
            setSubProjects(data?.projectDetails?.subProjects);
        }else{
            navConfigs[2].active = false;
        }
        let filterdNavConfig = navConfigs.filter((config)=>config.active === true);
        setNavTypeConfig(filterdNavConfig);
    },[data]);
    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_PROJECT_DETAILS")}</Header>
            </div>

            <Card className={"employeeCard-override"} >
                <StatusTable>
                    <Row className="border-none" label={`${t("WORKS_PROJECT_ID")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectID} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PDF_STATIC_LABEL_ESTIMATE_PROPOSAL_DATE")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectProposalDate} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PDF_STATIC_LABEL_ESTIMATE_PROJECT_NAME")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectName} textStyle={{ whiteSpace: "pre" }} isMandotary={true} />
                    <Row className="border-none" label={`${t("PROJECT_DESC")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectDesc} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_THE_PROJECT_HAS_SUB_PROJECT_LABEL")}:`} text={t(data?.projectDetails?.searchedProject?.basicDetails?.projectHasSubProject)} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_PARENT_PROJECT_ID")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID} textStyle={{ whiteSpace: "pre" }} isValueLink={data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID === "NA" ? "" : data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID} navigateLinkHandler={()=>handleParentProjectSearch(data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID)}/>
                </StatusTable>
            </Card>
            <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>  
              <ProjectDetailsNavDetails 
                activeLink={activeLink}
                subProjects={subProjects}
                searchParams={searchParams}
                filters={filters}
              />
            </HorizontalNav>
            <ActionBar>
                <SubmitBar onSubmit={handleNavigateToEstimatesScreen} label={t("ACTION_TEST_CREATE_ESTIMATE")} />
            </ActionBar>
        </div>
    )
}

export default ProjectDetails;