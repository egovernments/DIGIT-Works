import { Header, Toast, WorkflowActions } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const ViewPurchaseBill = ({props}) => {
    const [showActions, setShowActions] = useState(false);
    const menuRef = useRef();
    const [actionsMenu, setActionsMenu] = useState([]);
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const {t} = useTranslation();
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const { data, isLoading : isViewPurchaseBillDataLoading, isError : isViewPurchaseBillDataError } = Digit.Hooks.bills.useViewPurchaseBillDetails(t, {});
    
    const handleActionBar = (option) => {

    }

    return (
        <>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("COMMON_PURCHASE_BILL")}</Header>
            {
              !isViewPurchaseBillDataLoading && !data?.isNoDataFound && 
                <ApplicationDetails
                  applicationDetails={data}
                  isLoading={isViewPurchaseBillDataLoading} 
                  applicationData={data?.applicationDetails}
                  moduleCode="works"
                  isDataLoading={isViewPurchaseBillDataLoading}
                  workflowDetails={{}}
                  showTimeLine={false}
                  timelineStatusPrefix={""}
                  businessService={""}
                  forcedActionPrefix={"WORKS"}
                  noBoxShadow={true}
                  customClass="status-table-custom-class"
                />
            }                
              <WorkflowActions
                forcedActionPrefix={"WF_ESTIMATE_ACTION"}
                businessService={""}
                applicationNo={""}
                tenantId={tenantId}
                applicationDetails={data?.applicationData}
                url={""}
                setStateChanged={""}
                moduleCode="Estimate"
                editApplicationNumber={""}
              />
              {data?.applicationData?.wfStatus === "APPROVED" ?
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
            {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
        </>
      )
}

export default ViewPurchaseBill;