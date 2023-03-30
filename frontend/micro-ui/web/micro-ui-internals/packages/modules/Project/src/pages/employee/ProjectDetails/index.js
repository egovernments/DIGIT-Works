import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader,Loader,SubmitBar,ActionBar, HorizontalNav, Menu, Toast } from '@egovernments/digit-ui-react-components'
import React, { Fragment,useEffect,useRef,useState } from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory, useLocation } from 'react-router-dom'
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
    const menuRef = useRef();
    const [showActions, setShowActions] = useState(false);
    const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");
    const [hideActionBar, setHideActionBar] = useState(true);
    const projectSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", {});
    const [sessionFormData, clearSessionFormData] = projectSession;
    const location = useLocation();
    const [actionsMenu, setActionsMenu] = useState([ 
        {
            name : "MODIFY_PROJECT"
        }
    ]);
    const [toast, setToast] = useState({show : false, label : "", error : false});
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
    ];
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

    const closeMenu = () => {
        setShowActions(false);
    }

    Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions );

    const handleParentProjectSearch = (parentProjectNumber) => {
        history.push(`/${window.contextPath}/employee/project/project-details?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${parentProjectNumber}`);
    }

    const handleActionBar = (option) => {
        if(option?.name === "CREATE_ESTIMATE"){
            history.push(`/${window.contextPath}/employee/estimate/create-estimate?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${searchParams?.Projects?.[0]?.projectNumber}`);
        }
        if(option?.name === "VIEW_ESTIMATE"){
            history.push(`/${window.contextPath}/employee/estimate/estimate-details?tenantId=${searchParams?.Projects?.[0]?.tenantId}&estimateNumber=${estimates?.[0]?.estimateNumber}`);
        }
        if(option?.name === "MODIFY_PROJECT"){
            if(estimates?.length !==0 && estimates?.[0]?.wfStatus !== "" &&  estimates?.[0]?.wfStatus !== "REJECTED") {
                setToast({show : true, label : t("COMMON_CANNOT_MODIFY_PROJECT_EST_CREATED"), error : true});
            }else {
                // history.push(`/${window.contextPath}/employee/project/modify-project?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${searchParams?.Projects?.[0]?.projectNumber}`);
                history.push({
                    pathname : `/${window.contextPath}/employee/project/create-project`,
                    search : `?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${searchParams?.Projects?.[0]?.projectNumber}`,
                })
            }
        }
    }

    const handleToastClose = () => {
      setToast({show : false, label : "", error : false});
    }

    const { data } = Digit.Hooks.works.useViewProjectDetails(t, tenantId, searchParams, filters, headerLocale);

    //fetch estimate details
    const { data : estimates, isError : isEstimateSearchError } = Digit.Hooks.works.useSearchEstimate( tenantId, {limit : 1, offset : 0, projectId : data?.projectDetails?.searchedProject?.basicDetails?.uuid });

    useEffect(()=>{
        let isUserEstimateCreator = loggedInUserRoles?.includes("ESTIMATE_CREATOR");
        if(isEstimateSearchError) {
            setToast({show : true, label : t("COMMON_ERROR_FETCHING_ESTIMATE_DETAILS"), error : true});
            setActionsMenu([]);
            setHideActionBar(true);
        }else {
            setHideActionBar(false);
            if((estimates?.length === 0 || estimates?.[0]?.wfStatus === "" || estimates?.[0]?.wfStatus === "REJECTED")) {
                if(isUserEstimateCreator) {
                    setActionsMenu([
                        {
                            name : "CREATE_ESTIMATE"
                        },
                        {
                            name : "MODIFY_PROJECT"
                        }
                    ])
                }else {
                    setActionsMenu([
                        {
                            name : "MODIFY_PROJECT"
                        }
                    ])
                }
            }else{
                setActionsMenu([
                    {
                        name : "VIEW_ESTIMATE"
                    },
                    {
                        name : "MODIFY_PROJECT"
                    }
                ])
            }
        }
    },[estimates, isEstimateSearchError]);

     //remove Toast after 3s
     useEffect(()=>{
        if(toast?.show) {
          setTimeout(()=>{
            handleToastClose();
          },3000);
        }
      },[toast?.show])

    //update config for Nav once we get the data
    useEffect(()=>{
        if(data?.projectDetails?.subProjects?.length > 0) {
            navConfigs[2].active = true;
            setSubProjects(data?.projectDetails?.subProjects);
        }else{
            navConfigs[2].active = false;
        }
        let filterdNavConfig = navConfigs.filter((config)=>config.active === true);
        setNavTypeConfig(filterdNavConfig);
    },[data]);

    //remove session form data if user navigates away from the project create screen
    useEffect(()=>{
        if (!window.location.href.includes("create-project") && sessionFormData && Object.keys(sessionFormData) != 0) {
            clearSessionFormData();
        }
    },[location]);

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_PROJECT_DETAILS")}</Header>
            </div>

            {/* <Card className={"employeeCard-override"} >
                <StatusTable>
                    <Row className="border-none" label={`${t("WORKS_PROJECT_ID")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectID} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("ES_COMMON_PROPOSAL_DATE")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectProposalDate} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("ES_COMMON_PROJECT_NAME")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectName} textStyle={{ whiteSpace: "pre" }} isMandotary={true} />
                    <Row className="border-none" label={`${t("PROJECT_DESC")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectDesc} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_THE_PROJECT_HAS_SUB_PROJECT_LABEL")}:`} text={t(data?.projectDetails?.searchedProject?.basicDetails?.projectHasSubProject)} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_PARENT_PROJECT_ID")}:`} text={data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID} textStyle={{ whiteSpace: "pre" }} isValueLink={data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID === "NA" ? "" : data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID} navigateLinkHandler={()=>handleParentProjectSearch(data?.projectDetails?.searchedProject?.basicDetails?.projectParentProjectID)}/>
                </StatusTable>
            </Card> */}
            <HorizontalNav showNav={false} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>  
              <ProjectDetailsNavDetails 
                activeLink={activeLink}
                subProjects={subProjects}
                searchParams={searchParams}
                filters={filters}
              />
            </HorizontalNav>
            {
                !hideActionBar &&
                <ActionBar>
                    {showActions ? 
                        <Menu
                            localeKeyPrefix={`COMMON`}
                            options={actionsMenu}
                            optionKey={"name"}
                            t={t}
                            onSelect={handleActionBar}
                        /> : null
                    }
                    <SubmitBar ref={menuRef} label={t("WORKS_ACTIONS")} onSubmit={() => setShowActions(!showActions)}/>
                </ActionBar>
            }
        {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
        </div>
    )
}

export default ProjectDetails;