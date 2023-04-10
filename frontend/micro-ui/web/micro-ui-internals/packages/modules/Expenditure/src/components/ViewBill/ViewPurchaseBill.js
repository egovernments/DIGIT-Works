import { Toast } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useState } from "react";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const ViewPurchaseBill = ({props}) => {
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const { data, isLoading : isViewPurchaseBillDataLoading, isError : isViewPurchaseBillDataError } = Digit.Hooks.bills.useViewPurchaseBillDetails({});
    if(!isViewPurchaseBillDataLoading) {
      console.log("view bill details", data);
    }
    return (
        <>
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
            {toast?.show && <Toast label={toast?.label} error={toast?.error} isDleteBtn={true} onClose={handleToastClose}></Toast>}
        </>
      )
}

export default ViewPurchaseBill;