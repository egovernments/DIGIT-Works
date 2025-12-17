import React, { useState, useEffect } from 'react'
import { useTranslation } from "react-i18next";
import { Header } from '@egovernments/digit-ui-react-components';
import ApplicationDetails from '../../../../../templates/ApplicationDetails';
import {Toast,ActionBar} from '@egovernments/digit-ui-components';

const ViewPaymentInstruction = () => {
  const { t } = useTranslation();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
  const { tenantId, piNumber } = Digit.Hooks.useQueryParams();
  const [showDataError, setShowDataError] = useState(null)
  const [toast,setShowToast] = useState(null)
  const [showActionBar,setShowActionBar] = useState(true)
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
  const {isLoading, data, isError, isSuccess, error,refetch} = Digit.Hooks.paymentInstruction.useViewPaymentInstruction({tenantId, data: payload, config: { cacheTime:0 }})

  const { mutate: updatePIMutation } = Digit.Hooks.paymentInstruction.useUpdatePI();

  const piStatus = data?.[0]?.applicationData?.piStatus
  
  const closeToast = () => {
    setTimeout(() => {
      setShowToast(null)
    }, 6000);
  }

  const handleUpdatePI = async () => {
    const piDetails = data?.[0]?.applicationData
    const payloadForUpdate = {
      tenantId,
    }
    
    //retry
    if(piDetails?.piStatus==="FAILED") {
      payloadForUpdate.referenceId = piDetails?.muktaReferenceId
    }
    //revised
    else{
      payloadForUpdate.referenceId = piDetails?.muktaReferenceId
    }
    //in case of retry (Failed status) send referenceId
    // in case of Partial status, send piNumber to generate revised pi
    await updatePIMutation(payloadForUpdate, {
      onError: async (error, variables) => {
          setShowToast({
            type:"error",
            label:`${t("EXP_RETRY_PI_ERR_MESSAGE")} : ${error?.response.data.Errors[0].description}`
          })
          closeToast()
          refetch()
        },
      onSuccess: async (responseData, variables) => {
          setShowToast({
            type:"",
            label:`${t("EXP_RETRY_PI_MESSAGE")}`
          })
          closeToast()
          refetch()
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
      )}
      {showDataError === null && (
        <ApplicationDetails
          applicationDetails={data?.[1]?.applicationDetails}
          isLoading={isLoading}
          applicationData={data?.[1]?.applicationData}
          moduleCode="AttendenceMgmt"
          showTimeLine={false}
          businessService={businessService}
          tenantId={tenantId}
        />
      )}
      {showDataError && (
        <Toast type={"error"} label={t("COMMON_ERROR_FETCHING_PI_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
      )}
      {(piStatus === "FAILED" || piStatus === "PARTIAL") && showActionBar && (
        <ActionBar
          actionFields={[
            <Button
              type={"submit"}
              label={piStatus === "FAILED" ? t("EXP_RETRY_PI") : t("EXP_GENERATE_REVISED_PI")}
              variation={"primary"}
              onClick={handleUpdatePI}
            ></Button>,
          ]}
          setactionFieldsToRight={true}
          className={"new-actionbar"}
        />
      )}
      {toast && <Toast type={toast?.type} label={toast?.label} isDleteBtn={true} onClose={() => setShowToast(null)} />}
    </React.Fragment>
  );
}

export default ViewPaymentInstruction;