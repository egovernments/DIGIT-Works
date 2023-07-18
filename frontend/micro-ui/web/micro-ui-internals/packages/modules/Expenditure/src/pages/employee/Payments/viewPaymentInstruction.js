import React, { useState, useEffect } from 'react'
import { useTranslation } from "react-i18next";
import { Header, Toast,SubmitBar,ActionBar } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';

const ViewPaymentInstruction = () => {
  const { t } = useTranslation();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
  const { tenantId, piNumber } = Digit.Hooks.useQueryParams();
  const [showDataError, setShowDataError] = useState(null)

  const payload = {
    "searchCriteria": {
      tenantId,
      piNumber
  },
  "pagination": {
      "limit": "10",
      "offset": "0",
      "sortBy": "",
      "order": "ASC"
  } 
  }
  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.paymentInstruction.useViewPaymentInstruction({tenantId, data: payload, config: { cacheTime:0 }})

  const { mutate: updatePIMutation } = Digit.Hooks.paymentInstruction.useUpdatePI();

  const piStatus = data?.[0]?.applicationData?.piStatus
  
  const handleUpdatePI = async () => {
    const piDetails = data?.[0]?.applicationData
    // console.log("pi update");
    const payloadForUpdate = {
      tenantId,
      "referenceId": "EP/0/2023-24/07/12/000049",
    }
    //in case of retry (Failed status) send referenceId
    // in case of Partial status, send piNumber to generate revised pi
    await updatePIMutation(payloadForUpdate, {
      onError: async (error, variables) => {
          // sendDataToResponsePage(contractNumber, false, "CONTRACT_MODIFICATION_FAILURE", true);
          console.log("error",error);
        },
      onSuccess: async (responseData, variables) => {
          // if(responseData?.ResponseInfo?.Errors) {
          //         setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_CONTRACT"), error : true}));
          //     }else if(responseData?.ResponseInfo?.status){
          //         sendDataToResponsePage(contractNumber, true, "CONTRACTS_MODIFIED", true);
          //         clearSessionFormData();
          //     }else{
          //         setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_CONTRACT"), error : true}));
          //     }
          console.log("success",responseData);
      },
  });

  }

  useEffect(() => {
    if(isError) {
      setShowDataError(true)
    }
  }, [error])

  return (
    <React.Fragment>
      <Header className="works-header-view">{t("EXP_PAYMENT_INS")}</Header>
      {
        showDataError === null && (
          <ApplicationDetails
            applicationDetails={data?.[0]?.applicationDetails}
            isLoading={isLoading}
            applicationData={data?.[0]?.applicationData}
            moduleCode="AttendenceMgmt"
            showTimeLine={false}
            businessService={businessService}
            tenantId={tenantId}
          />
        )
      }
      {
        showDataError === null && (
          <ApplicationDetails
            applicationDetails={data?.[1]?.applicationDetails}
            isLoading={isLoading}
            applicationData={data?.[1]?.applicationData}
            moduleCode="AttendenceMgmt"
            showTimeLine={false}
            businessService={businessService}
            tenantId={tenantId}
          />
        )
      }
      {
        showDataError && <Toast error={true} label={t("COMMON_ERROR_FETCHING_PI_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      }
      { (piStatus==="FAILED" || piStatus==="PARTIAL") &&
        <ActionBar> 
          <SubmitBar label={piStatus==="FAILED" ?t("EXP_RETRY_PI"):t("EXP_GENERATE_REVISED_PI")} onSubmit={handleUpdatePI} />
        </ActionBar>
      }
    </React.Fragment>
  )
}

export default ViewPaymentInstruction;