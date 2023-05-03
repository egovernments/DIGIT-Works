import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader,Loader,SubmitBar,ActionBar, HorizontalNav,ViewImages } from '@egovernments/digit-ui-react-components'
import React, { Fragment,useState } from 'react'
import { useTranslation } from 'react-i18next'
import ProjectClosureDetails from './ProjectClosureDetails';


const configNavItems = [
    {
        "name":"Estimation",
        "code":"WORKS_ESTIMATION"
    },
    {
        "name":"Contracts",
        "code":"WORKS_CONTRACTS"
    },
    {
        "name": "FieldSurvey",
        "code":"WORKS_FIELD_SURVEY"
    },
    {
        "name": "Billing",
        "code": "WORKS_BILLING"
    },
    {
        "name": "Closure Checklist",
        "code": "WORKS_CLOSURE_CHECKLIST"
    },

]

const ProjectClosure = () => {
    const { t } = useTranslation()
    const [activeLink, setActiveLink] = useState("Estimation")
    //based on the active link we'll render applicationdetailstemplate accordingly

    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewProjectClosureDetails("pb.amritsar");
    
    let workflowDetails = Digit.Hooks.useWorkflowDetails(
        {
            tenantId: "pb.amritsar",
            id: applicationDetails?.applicationData?.estimateNumber,
            moduleCode: applicationDetails?.processInstancesDetails?.[0]?.businessService,
            config: {
                enabled: applicationDetails?.processInstancesDetails?.[0]?.businessService ? true : false,
                cacheTime: 0
            }
        },
    );

    const tenant = Digit.ULBService.getStateId();
    const { isLoading:isMdmsLoading, data: checklistData } = Digit.Hooks.useCustomMDMS(
        tenant,
        "works",
        [
            {
                "name": "ClosureChecklist"
            },
            {
                "name": "KickoffChecklist"
            }
        ]
    );


  return (
    <Fragment>
          <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_PROJECT_CLOSURE")}</Header>
                <MultiLink
                    className="multilinkWrapper employee-mulitlink-main-div"
                    onHeadClick={{}}
                    downloadBtnClassName={"employee-download-btn-className"}
                />
            </div>

            <Card className={"employeeCard-override"} >
                <CardSubHeader style={{ marginBottom: "16px", fontSize: "24px" }}>{t("WORKS_PROJECT_DETAILS")}</CardSubHeader>
                <StatusTable>
                <Row className="border-none" label={t("WORKS_NAME_OF_WORK")} text={"Construction of Public Toilet in Khanderi"} textStyle={{ whiteSpace: "pre" }} />
                <Row className="border-none" label={t("WORKS_DATE_CREATED")} text={"23/01/2015"} textStyle={{ whiteSpace: "pre" }} />
                </StatusTable>
            </Card>

              
            {/* <div className="horizontal-nav">
                {configNavItems?.map((item, index) => (
                    <div className={`sidebar-list ${activeLink===item.name?"active":""}`} key={index} onClick={()=>setActive(item)}>
                        <MenuItem item={item} />
                    </div>
                ))}
            </div> */}

            {/* Here render the applicationDetails based on activeLink */}
            <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>  
              < ProjectClosureDetails 
                activeLink={activeLink} isMdmsLoading={isMdmsLoading} checklistData={checklistData}
              />
            </HorizontalNav>

            
              <ActionBar>
                  <SubmitBar onSubmit={() => { }} label={t("WORKS_CLOSE_PROJECT")} />
              </ActionBar>
          </div>


    </Fragment>
  )
}

export default ProjectClosure