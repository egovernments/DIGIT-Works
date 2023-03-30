import React, { useMemo } from 'react';
import { useTranslation } from "react-i18next";
import { useLocation } from 'react-router-dom';
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import { InboxBillConfig } from '../../../configs/InboxBillConfig';

const BillInbox = () => {
  const { t } = useTranslation();
  const { state } = useLocation()
  const stateTenant = Digit.ULBService.getStateId();

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

  let configs = useMemo(
    () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, data, "sections.search.uiConfig.fields",{}));
  */

  //For local
  let configs = useMemo( () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, InboxBillConfig?.InboxBillConfig?.[0], "sections.search.uiConfig.fields",{}));


  //if(isLoading) return <Loader />
  return (
      <React.Fragment>
          <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}{state?.count ? <span className="inbox-count">{state?.count}</span> : null}</Header>
          <div className="inbox-search-wrapper">
            <InboxSearchComposer configs={configs}></InboxSearchComposer>
          </div>
      </React.Fragment>
  )
}

export default BillInbox;