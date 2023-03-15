import React from 'react'
import { useTranslation } from "react-i18next";
import { useLocation } from 'react-router-dom';
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import { SearchBillConfig } from '../../../configs/SearchBillConfig';

const SearchBill = () => {
  const { t } = useTranslation();
  const { state } = useLocation()
  const stateTenant = Digit.ULBService.getStateId();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  /*
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      stateTenant,
      Digit.Utils.getConfigModuleName(),
      [
          {
              "name": "SearchBillConfig"
          }
      ],
      {
        select: (data) => {
            return data?.[Digit.Utils.getConfigModuleName()]?.SearchBillConfig[0];
        },
      }
  );
  */

  const configs = SearchBillConfig?.SearchBillConfig?.[0]
  console.log('@@', configs);
  //if(isLoading) return <Loader />
  return (
    <React.Fragment>
      <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
      <div className="inbox-search-wrapper">
          <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default SearchBill;