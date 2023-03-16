import React, { useState, useEffect }from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar,ViewDetailsCard , HorizontalNav } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../templates/ApplicationDetails';


const ViewContractDetails = () => {
    const { t } = useTranslation();
    //const history = useHistory();
    const [showToast, setShowToast] = useState(null);
    const queryStrings = Digit.Hooks.useQueryParams();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    // contractNumber = "WO/2022-23/000329"

    const payload = {
        tenantId : queryStrings?.tenantId || tenantId,
        contractNumber : queryStrings?.workOrderNumber
    }

    const [cardState,setCardState] = useState({})
    const [activeLink, setActiveLink] = useState("Work_Order");
    const configNavItems = [
        {
            "name": "Work_Order",
            "code": "WORK_ORDER",
            "active": true
        },
        {
            "name": "Terms_and_Conditions",
            "code": "TERMS_AND_CONDITIONS",
            "active": true
        }
    ]
    const ContractDetails = Digit.ComponentRegistryService.getComponent("ContractDetails");
    const TermsAndConditions = Digit.ComponentRegistryService.getComponent("TermsAndConditions");

    const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.contracts.useViewContractDetails(payload?.tenantId, payload, {})
    console.log('@@Data', data);

    //fetching project data
    const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
        tenantId,
        searchParams: {
            Projects: [
                {
                    tenantId,
                    id:payload?.contractNumber?.projectId
                }
            ]
        },
        config:{
            enabled: !!(payload?.contractNumber?.projectId) 
        }
    })

  
    useEffect(() => {
        //here set cardstate when contract and project is available
          setCardState({
              "WORKS_ORDER_ID": "",
              "WORKS_PROJECT_ID": "",            
              "ES_COMMON_PROPOSAL_DATE": Digit.DateUtils.ConvertEpochToDate(""),
              "ES_COMMON_PROJECT_NAME": "",
              "PROJECTS_DESCRIPTION": ""
          }) 
      }, [project])


    /*
    let workflowDetails = Digit.Hooks.useWorkflowDetails(
      {
          tenantId: searchParams?.tenantId,
          id: searchParams?.contractNumber,
          moduleCode: data?.processInstancesDetails?.[0]?.businessService,
          config: {
              enabled:data?.processInstancesDetails?.[0]?.businessService ? true : false,
              cacheTime:0
          }
      }
    );

    console.log("workflow :", workflowDetails);
    */
   
    return (
      <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_VIEW_WORK_ORDER")}</Header>
          </div>
          <ApplicationDetails
            applicationDetails={data?.applicationDetails}
            isLoading={isLoading}
            applicationData={data?.applicationData}
            moduleCode="contracts"
            isDataLoading={false}
            workflowDetails={{}}
            businessService={"contracts"}
            showTimeLine={true}
            mutate={() => {}} //mutate={mutate}
            tenantId={tenantId}
            showToast={showToast}
            setShowToast={setShowToast}
            applicationNo={payload?.contractNumber}
            //setshowEditTitle={setshowEditTitle}
            //timelineStatusPrefix={"ATM_"}
            //forcedActionPrefix={"ATM"}
          />
          <ViewDetailsCard cardState={cardState} t={t} />
          <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
            {activeLink === "Work_Order" && <ContractDetails fromUrl={false} tenantId={tenantId} contractNumber={payload?.contractNumber} />}
            {activeLink === "Terms_and_Conditions" && <TermsAndConditions />}
          </HorizontalNav>
        </div>
      </React.Fragment>
    );
}

export default ViewContractDetails



