import React from 'react';
import { useTranslation } from "react-i18next";
import { useLocation } from 'react-router-dom';
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import { InboxBillConfig } from '../../../configs/InboxBillConfig';

const BillInbox = () => {
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
              "name": "InboxBillConfig"
          }
      ],
      {
        select: (data) => {
            return data?.[Digit.Utils.getConfigModuleName()]?.InboxBillConfig[0];
        },
      }
  );
  */

  console.log('InboxBillConfig', InboxBillConfig);
  console.log('Digit.Utils.getConfigModuleName()', Digit.Utils.getConfigModuleName())
  const configs = InboxBillConfig?.InboxBillConfig?.[0]
  console.log('@@', configs);
  //if(isLoading) return <Loader />
  return (
      <React.Fragment>
          <Header styles={{ fontSize: "32px" }}>{`${t(configs?.label)} (${state?.count})`}</Header>
          <div className="inbox-search-wrapper">
              <InboxSearchComposer configs={configs}></InboxSearchComposer>
          </div>
      </React.Fragment>
  )
}

export default BillInbox;