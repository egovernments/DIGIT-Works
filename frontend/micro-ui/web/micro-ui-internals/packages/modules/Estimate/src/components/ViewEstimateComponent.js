import React, { Fragment, useState, useEffect, useRef, } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline,ActionBar,Menu,SubmitBar } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../templates/ApplicationDetails';
import { useHistory } from 'react-router-dom';
const ViewEstimateComponent = (props) => {
    const history = useHistory();
    const [showActions, setShowActions] = useState(false);
    const menuRef = useRef();
    const [actionsMenu, setActionsMenu] = useState([
        
    ]);

    const { t } = useTranslation()

    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate")

    const closeMenu = () => {
        setShowActions(false);
    }
    Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions);

    const { isLoading, data: applicationDetails } = Digit.Hooks.estimates.useEstimateDetailsScreen(t, tenantId, estimateNumber)

    useEffect(() => {
        if (applicationDetails?.applicationData?.wfStatus === "APPROVED"){
            setActionsMenu((prevState => [...prevState,{
                name:"CREATE_CONTRACT"
            }]))
        }
      
    }, [applicationDetails])
    

    

    const handleActionBar = (option) => {
        if (option?.name === "CREATE_CONTRACT") {
            history.push(`/${window.contextPath}/employee/contract/create-contract?tenantId=${tenantId}&estimateNumber=${estimateNumber}`);
        }
        // if (option?.name === "VIEW_ESTIMATE") {
        //     history.push(`/${window.contextPath}/employee/estimate/estimate-details?tenantId=${searchParams?.Projects?.[0]?.tenantId}&estimateNumber=${estimates?.[0]?.estimateNumber}`);
        // }
        // if (option?.name === "MODIFY_PROJECT") {
        //     if ((estimates?.length !== 0 && estimates?.[0]?.wfStatus !== "") && (estimates?.length !== 0 && estimates?.[0]?.wfStatus !== "REJECTED")) {
        //         setToast({ show: true, label: t("COMMON_CANNOT_MODIFY_PROJECT_EST_CREATED"), error: true });
        //     } else {
        //         history.push(`/${window.contextPath}/employee/project/modify-project?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${searchParams?.Projects?.[0]?.projectNumber}`);
        //     }
        // }
    }

    if (isLoading) return <Loader />

    return (
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
            />

            {/* Adding another action bar to show Create Contract Option */}
            {applicationDetails?.applicationData?.wfStatus === "APPROVED" ? 
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
                : null}
        </>
    )
}

export default ViewEstimateComponent