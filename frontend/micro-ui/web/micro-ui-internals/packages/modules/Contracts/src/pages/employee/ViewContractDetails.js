import React, { useState, useEffect, Fragment }from 'react';
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';
import { Header, ActionBar, SubmitBar,ViewDetailsCard , HorizontalNav, Loader, WorkflowActions, Toast, MultiLink } from '@egovernments/digit-ui-react-components';


const ViewContractDetails = () => {
    const { t } = useTranslation();
    const [showToast, setShowToast] = useState(null);
    const queryStrings = Digit.Hooks.useQueryParams();
    const contractId = queryStrings?.workOrderNumber;
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contracts")
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    const payload = {
        tenantId : queryStrings?.tenantId || tenantId,
        contractNumber : queryStrings?.workOrderNumber
    }
    const [cardState,setCardState] = useState([])
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
    const {isLoading : isContractLoading, data, isError : isContractError, isSuccess, error} = Digit.Hooks.contracts.useViewContractDetails(payload?.tenantId, payload, {}, {cacheTime : 0})
    //const {isLoading : isContractLoading, data } = Digit.Hooks.contracts.useViewContractDetails(payload?.tenantId, payload, {})

    //fetching project data
    const { isLoading: isProjectLoading, data: project, isError : isProjectError } = Digit.Hooks.project.useProjectSearch({
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
        if (!window.location.href.includes("create-contract") && sessionFormData && Object.keys(sessionFormData) != 0) {
          clearSessionFormData();
        }
    }, [location]);

    useEffect(()=>{
        if(isContractError || (!isContractLoading && data?.isNoDataFound)) {
            setToast({show : true, label : t("COMMON_WO_NOT_FOUND"), error : true});
        }
    },[isContractError, data, isContractLoading]);

    useEffect(()=>{
        if(isProjectError) {
            setToast({show : true, label : t("COMMON_PROJECT_NOT_FOUND"), error : true});
        }
    },[isProjectError]);

    const HandleDownloadPdf = () => {
        Digit.Utils.downloadEgovPDF('workOrder/work-order',{contractId,tenantId},`workOrder-${contractId}.pdf`)
    }

    const handleToastClose = () => {
        setToast({show : false, label : "", error : false});
    }

    useEffect(() => {
        //here set cardstate when contract and project is available
        setCardState([
            {
                title: '',
                values: [
                  { title: "WORKS_ORDER_ID", value: payload?.contractNumber },
                  { title: "WORKS_PROJECT_ID", value: project?.projectNumber, },
                  { title: "ES_COMMON_PROPOSAL_DATE", value: Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal) },
                  { title: "ES_COMMON_PROJECT_NAME", value: project?.name },
                  { title: "PROJECTS_DESCRIPTION", value: project?.description }
                ]
              }
        ]) 
      }, [project])


    if(isProjectLoading || isContractLoading) 
         return <Loader/>;
    return (
      <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header className="works-header-view" styles={{ marginLeft: "0px", paddingTop: "10px"}}>{t("WORKS_VIEW_WORK_ORDER")}</Header>
            {data?.applicationData?.wfStatus === "APPROVED" && 
               <MultiLink
                 onHeadClick={() => HandleDownloadPdf()}
                 downloadBtnClassName={"employee-download-btn-className"}
                 label={t("CS_COMMON_DOWNLOAD")}
               />
            }
          </div>
          {project && <ViewDetailsCard cardState={cardState} t={t} />}
          {
            !data?.isNoDataFound && 
                <>
                    <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>
                        {activeLink === "Work_Order" && <ContractDetails fromUrl={false} tenantId={tenantId} contractNumber={payload?.contractNumber} data={data} isLoading={isContractLoading}/>}
                        {activeLink === "Terms_and_Conditions" && <TermsAndConditions data={data?.applicationData?.additionalDetails?.termsAndConditions}/>}
                    </HorizontalNav>
                    <WorkflowActions
                        forcedActionPrefix={"WF_CONTRACT_ACTION"}
                        businessService={businessService}
                        applicationNo={queryStrings?.workOrderNumber}
                        tenantId={tenantId}
                        applicationDetails={data?.applicationData}
                        url={Digit.Utils.Urls.contracts.update}
                        moduleCode="Contract"
                    />
                </>
          }
        </div>
        {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
      </React.Fragment>
    );
}

export default ViewContractDetails



