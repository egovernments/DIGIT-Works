import React, { useState, useEffect }from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar,ViewDetailsCard , HorizontalNav, Loader, WorkflowActions } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../templates/ApplicationDetails';


const ViewContractDetails = () => {
    const { t } = useTranslation();
    const [showToast, setShowToast] = useState(null);
    const queryStrings = Digit.Hooks.useQueryParams();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contracts")

    const payload = {
        tenantId : queryStrings?.tenantId || tenantId,
        contractNumber : queryStrings?.workOrderNumber
    }
    const [cardState,setCardState] = useState({})
    const [activeLink, setActiveLink] = useState("Work_Order");
    const configNavItems = [
        {
            "name": "Work_Order",
            "code": "COMMON_WO_DETAILS",
            "active": true
        },
        {
            "name": "Terms_and_Conditions",
            "code": "COMMON_TERMS_&_CONDITIONS",
            "active": true
        }
    ]
    const ContractDetails = Digit.ComponentRegistryService.getComponent("ContractDetails");
    const TermsAndConditions = Digit.ComponentRegistryService.getComponent("TermsAndConditions");
    const {isLoading : isContractLoading, data, isError, isSuccess, error} = Digit.Hooks.contracts.useViewContractDetails(payload?.tenantId, payload, {})

    //fetching project data
    const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
        tenantId,
        searchParams: {
            Projects: [
                {
                    tenantId,
                    projectNumber : data?.applicationData?.additionalDetails?.projectId
                }
            ]
        },
        config:{
            enabled: !!(data?.applicationData?.additionalDetails?.projectId) 
        }
    })

    useEffect(() => {
        //here set cardstate when contract and project is available
          setCardState({
              "WORKS_ORDER_ID": payload?.contractNumber,
              "WORKS_PROJECT_ID": project?.projectNumber,            
              "ES_COMMON_PROPOSAL_DATE": Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal),
              "ES_COMMON_PROJECT_NAME": project?.name,
              "PROJECTS_DESCRIPTION": project?.description
          }) 
      }, [project])


    if(isProjectLoading || isContractLoading) 
         return <Loader/>;
    return (
      <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_VIEW_WORK_ORDER")}</Header>
          </div>
          <ViewDetailsCard cardState={cardState} t={t} />
          <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
            {activeLink === "Work_Order" && <ContractDetails fromUrl={false} tenantId={tenantId} contractNumber={payload?.contractNumber} data={data} isLoading={isContractLoading}/>}
            {activeLink === "Terms_and_Conditions" && <TermsAndConditions data={data?.applicationData?.additionalDetails?.termsAndConditions}/>}
          </HorizontalNav>
          <WorkflowActions
              forcedActionPrefix={"WF_CONTRACT_ACTION"}
              businessService={businessService}
              applicationNo={payload?.contractNumber}
              tenantId={tenantId}
              applicationDetails={data?.applicationData}
              url={Digit.Utils.Urls.contracts.update}
              moduleCode="Contract"
          />
        </div>
      </React.Fragment>
    );
}

export default ViewContractDetails



