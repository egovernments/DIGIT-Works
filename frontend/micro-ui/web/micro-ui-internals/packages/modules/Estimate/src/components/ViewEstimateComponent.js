import React, { Fragment, useState, useEffect, useRef, } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline,Menu,SubmitBar } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../templates/ApplicationDetails';
import { useHistory } from 'react-router-dom';
import { Toast ,ActionBar,Button} from '@egovernments/digit-ui-components';

const ViewEstimateComponent = ({editApplicationNumber,...props}) => {
    const history = useHistory();
    const [showActions, setShowActions] = useState(false);
    const [toast, setToast] = useState({show : false, label : "", type : ""});
    const menuRef = useRef();
    const [actionsMenu, setActionsMenu] = useState([
        
    ]);
    const [isStateChanged, setStateChanged] = useState(``)
    
    const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

    const { t } = useTranslation()

    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate");
    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    const closeMenu = () => {
        setShowActions(false);
    }
    Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions);

    const { isLoading, data: applicationDetails, isError } = Digit.Hooks.estimates.useEstimateDetailsScreen(t, tenantId, estimateNumber,{}, isStateChanged)
    

    //here make a contract search based on the estimateNumber
    let { isLoading: isLoadingContracts, data: contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId, filters: { tenantId, estimateIds: [applicationDetails?.applicationData?.id] },config:{
        enabled: (!isLoading &&  applicationDetails?.applicationData?.wfStatus === "APPROVED") ? true : false, cacheTime:0
    }})

    //fetching all work orders for a particular estimate
    let allContract = contract;
    contract = contract?.[0];
    //getting the object which will be in workflow, as 1:1:1 mapping is there, one one inworkflow workorder will be there for one estimate
    let inWorkflowContract = allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.[0]
    

    useEffect(() => {
        let isUserContractCreator = loggedInUserRoles?.includes("WORK_ORDER_CREATOR");
        if (applicationDetails?.applicationData?.wfStatus === "APPROVED" && isUserContractCreator && !(actionsMenu?.find((ob) => ob?.name === "CREATE_CONTRACT"))){
            setActionsMenu((prevState => [...prevState,{
                name:"CREATE_CONTRACT",
                displayNamw:"EST_VIEW_ACTIONS_CREATE_CONTRACT"
            }]))
        }
        //checking if any work order is inworflow, if it is then view contract will be shown otherwise create contract
        let isCreateContractallowed = allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.length > 0

        //if contract is already there just remove the prevState and push View contract state
        if(contract?.contractNumber && isCreateContractallowed) {
            setActionsMenu((prevState => [{
                name: "VIEW_CONTRACT",
                displayNamw:"EST_VIEW_ACTIONS_VIEW_CONTRACT"
            }]))
        }
    }, [applicationDetails, isStateChanged,contract])

    useEffect(()=>{
        if(isError || (!isLoading && applicationDetails?.isNoDataFound)) {
            setToast({show : true, label : t("COMMON_ESTIMATE_NOT_FOUND"), type : "error"});
        }
    },[isLoading, isError, applicationDetails]);

    //clear all contract session before Create Contract
    useEffect(() => {
        if (!window.location.href.includes("create-contract") && sessionFormData && Object.keys(sessionFormData) != 0) {
          clearSessionFormData();
        }
    }, [location]);
    
    const handleActionBar = (option) => {
        if (option?.name === "CREATE_CONTRACT") {
            history.push(`/${window.contextPath}/employee/contracts/create-contract?tenantId=${tenantId}&estimateNumber=${estimateNumber}`);
        }
        if (option?.name === "VIEW_CONTRACT") {
            history.push(`/${window.contextPath}/employee/contracts/contract-details?tenantId=${tenantId}&workOrderNumber=${inWorkflowContract?.contractNumber}`);
        }
    }

    const handleToastClose = () => {
        setToast({show : false, label : "", type : ""});
    }
    if (isLoading) return <Loader />
    return (
      <>
        {!applicationDetails?.isNoDataFound && !isError && (
          <>
            <ApplicationDetails
              applicationDetails={applicationDetails}
              isLoading={isLoading}
              applicationData={applicationDetails?.applicationData}
              moduleCode="Estimate"
              showTimeLine={true}
              timelineStatusPrefix={`WF_${businessService}_`}
              businessService={businessService}
              // forcedActionPrefix={"ACTION_"}
              tenantId={tenantId}
              applicationNo={estimateNumber}
              statusAttribute={"state"}
            />
            <WorkflowActions
              forcedActionPrefix={`WF_${businessService}_ACTION`}
              businessService={businessService}
              applicationNo={estimateNumber}
              tenantId={tenantId}
              applicationDetails={applicationDetails?.applicationData}
              url={Digit.Utils.Urls.works.updateEstimate}
              setStateChanged={setStateChanged}
              moduleCode="Estimate"
              editApplicationNumber={editApplicationNumber}
            />
            {/* Adding another action bar to show Create Contract Option */}
            {applicationDetails?.applicationData?.wfStatus === "APPROVED" && !isLoadingContracts && actionsMenu?.length > 0 ? (

              <ActionBar
                actionFields={[
                  <Button
                    type={"actionButton"}
                    options={actionsMenu}
                    label={t("WORKS_ACTIONS")}
                    variation={"primary"}
                    optionsKey={"displayName"}
                    isSearchable={false}
                    onOptionSelect={(option) => {
                      handleActionBar(option);
                    }}
                  ></Button>,
                ]}
                setactionFieldsToRight={true}
                className={"new-actionbar"}
              />
            ) : null}
          </>
        )}
        {toast?.show && <Toast label={toast?.label} type={toast?.type} isDleteBtn={true} onClose={handleToastClose}></Toast>}
      </>
    );
}

export default ViewEstimateComponent