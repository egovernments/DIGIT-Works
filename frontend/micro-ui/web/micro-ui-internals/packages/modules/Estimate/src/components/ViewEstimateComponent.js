import React, { Fragment, useState, useEffect, useRef, } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline,ActionBar,Menu,SubmitBar, Toast } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../templates/ApplicationDetails';
import { useHistory } from 'react-router-dom';
const ViewEstimateComponent = ({editApplicationNumber,...props}) => {
    const history = useHistory();
    const [showActions, setShowActions] = useState(false);
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const menuRef = useRef();
    const [actionsMenu, setActionsMenu] = useState([
        
    ]);
    const [isStateChanged, setStateChanged] = useState(``)
    
    const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

    const { t } = useTranslation()

    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessServiceV1("estimate");
    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    const closeMenu = () => {
        setShowActions(false);
    }
    Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions);

    const { isLoading, data: applicationDetails, isError } = Digit.Hooks.estimates.useEstimateDetailsScreen(t, tenantId, estimateNumber,{}, isStateChanged)
    

    //here make a contract search based on the estimateNumber
    const { isLoading: isLoadingContracts, data: contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId, filters: { tenantId, estimateIds: [applicationDetails?.applicationData?.id] },config:{
        enabled: (!isLoading &&  applicationDetails?.applicationData?.wfStatus === "APPROVED") ? true : false
    }})
    

    useEffect(() => {
        let isUserContractCreator = loggedInUserRoles?.includes("WORK_ORDER_CREATOR");
        if (applicationDetails?.applicationData?.wfStatus === "APPROVED" && isUserContractCreator){
            setActionsMenu((prevState => [...prevState,{
                name:"CREATE_CONTRACT"
            }]))
        }
        
        //if contract is already there just remove the prevState and push View contract state
        if(contract?.contractNumber) {
            setActionsMenu((prevState => [, {
                name: "VIEW_CONTRACT"
            }]))
        }
    }, [applicationDetails, isStateChanged,contract])

    useEffect(()=>{
        if(isError || (!isLoading && applicationDetails?.isNoDataFound)) {
            setToast({show : true, label : t("COMMON_ESTIMATE_NOT_FOUND"), error : true});
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
            history.push(`/${window.contextPath}/employee/contracts/contract-details?tenantId=${tenantId}&workOrderNumber=${contract?.contractNumber}`);
        }
    }

    const handleToastClose = () => {
        setToast({show : false, label : "", error : false});
    }

    if (isLoading) return <Loader />
    return (
        <>
            {
                (!applicationDetails?.isNoDataFound) && !isError && 
                <>
                    <ApplicationDetails
                        applicationDetails={applicationDetails}
                        isLoading={isLoading}
                        applicationData={applicationDetails?.applicationData}
                        moduleCode="Estimate"
                        showTimeLine={true}
                        timelineStatusPrefix={"WF_ESTIMATE_STATUS_"}
                        businessService={businessService}
                        // forcedActionPrefix={"ACTION_"}
                        tenantId={tenantId}
                        applicationNo={estimateNumber}
                        statusAttribute={"state"}
                    />
                    <WorkflowActions
                        forcedActionPrefix={"WF_ESTIMATE_ACTION"}
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
                    {applicationDetails?.applicationData?.wfStatus === "APPROVED" && !isLoadingContracts ?
                        <ActionBar>

                            {showActions ? <Menu
                                localeKeyPrefix={`EST_VIEW_ACTIONS`}
                                options={actionsMenu}
                                optionKey={"name"}
                                t={t}
                                onSelect={handleActionBar}
                            />:null} 
                            <SubmitBar ref={menuRef} label={t("WORKS_ACTIONS")} onSubmit={() => setShowActions(!showActions)} />
                        </ActionBar>
                        : null
                    }
                </>
                
            }
            {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
        </>
    )
}

export default ViewEstimateComponent