import React, { useState, useEffect } from 'react'
import { useTranslation } from "react-i18next";
import { Header, Toast } from '@egovernments/digit-ui-react-components';
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
    </React.Fragment>
  )
}

export default ViewPaymentInstruction;