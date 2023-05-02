import { Header, Toast, WorkflowActions } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const ViewPurchaseBill = ({props}) => {
    const [showActions, setShowActions] = useState(false);
    const menuRef = useRef();
    const [actionsMenu, setActionsMenu] = useState([]);
    const {billNumber,tenantId } = Digit.Hooks.useQueryParams()
    const [isStateChanged, setStateChanged] = useState(``)
    const {t} = useTranslation();
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);    
    const billCriteria = { //update this
        "tenantId": tenantId,
        "ids": [],
        "businessService": "works.purchase",
        "referenceIds": [],
        "status": ""
    }

    const { isLoading : isApplicableChargesLoading, data : metaData } = Digit.Hooks.useCustomMDMS(
      Digit.ULBService.getStateId(),
      "expense",
      [{ name: "ApplicableCharges" }],
      {
          select: (data) => {
              const optionsData = _.get(data, `expense.ApplicableCharges`, []);
              return optionsData.filter((opt) => opt?.active && opt?.service === "works.purchase").map((opt) => ({ ...opt, name: `COMMON_MASTERS_DEDUCTIONS_${opt.code}` }));
          },
          enabled : true
      }
    );

    const { data, isLoading : isViewPurchaseBillDataLoading, isError : isViewPurchaseBillDataError } = Digit.Hooks.bills.useViewPurchaseBillDetails(tenantId, t, billCriteria, headerLocale, {enabled : !isApplicableChargesLoading}, metaData);
    
    const handleActionBar = (option) => {

    }

    return (
        <>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("COMMON_PURCHASE_BILL")}</Header>
            {
              !isViewPurchaseBillDataLoading && !data?.isNoDataFound && 
                <>
                    <ApplicationDetails
                      applicationDetails={data?.applicationDetails?.basic_details}
                      isLoading={isViewPurchaseBillDataLoading} 
                      applicationData={data?.basic_details}
                      moduleCode="works"
                      isDataLoading={isViewPurchaseBillDataLoading}
                      workflowDetails={{}}
                      showTimeLine={false}
                      timelineStatusPrefix={""}
                      businessService={"works.purchase"}
                      forcedActionPrefix={"WORKS"}
                      noBoxShadow={true}
                    />
                    <ApplicationDetails
                      applicationDetails={data?.applicationDetails?.bill_details}
                      isLoading={isViewPurchaseBillDataLoading} 
                      applicationData={data?.bill_details}
                      moduleCode="works"
                      isDataLoading={isViewPurchaseBillDataLoading}
                      workflowDetails={{}}
                      showTimeLine={true}
                      timelineStatusPrefix={"WF_PBILL_STATUS_"}
                      businessService={"works.purchase"}
                      forcedActionPrefix={"WORKS"}
                      noBoxShadow={true}
                      applicationNo={billNumber}
                      tenantId={tenantId}
                      statusAttribute={"state"}
                    />
                </>
            }                
                <WorkflowActions
                  forcedActionPrefix={"WF_PBILL_ACTION"}
                  businessService={"works.purchase"}
                  applicationNo={billNumber}
                  tenantId={tenantId}
                  applicationDetails={data?.applicationData}
                  url={""} //TODO: @hariom Add the update api of bills here
                  setStateChanged={setStateChanged}
                  moduleCode="Expenditure"
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
