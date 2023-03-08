import React from 'react'
import { useTranslation } from "react-i18next";
import { Header } from '@egovernments/digit-ui-react-components';


const ViewWageSeeker = () => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getStateId()
  const {isLoading, data, isError, isSuccess, error} = Digit.Hooks.wageSeeker.useViewWageSeeker(tenantId, {}, {})
  console.log('Got Data', data);
 
  return (
    <React.Fragment>
      <Header>{'View Wage Seeker'}</Header>
    </React.Fragment>
  )
}

export default ViewWageSeeker