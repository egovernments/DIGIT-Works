import { Toast } from "@egovernments/digit-ui-react-components";
import React from "react";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const ViewPurchaseBill = ({props}) => {

    const [toast, setToast] = useState({show : false, label : "", error : false});
    const { data, isViewPurchaseBillDataLoading, isViewPurchaseBillDataError } = Digit.Hooks.bills.useViewPurchaseBillDetails();

    return (
        <>
            {
              !data?.isNoDataFound && 
                <ApplicationDetails
                  applicationDetails={data?.applicationDetails}
                  isLoading={isViewPurchaseBillDataLoading} 
                  applicationData={{}}
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