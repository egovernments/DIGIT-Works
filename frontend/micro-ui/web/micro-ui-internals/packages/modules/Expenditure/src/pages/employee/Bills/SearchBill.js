import React, { useMemo } from 'react'
import { useTranslation } from "react-i18next";
import { useLocation } from 'react-router-dom';
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import { SearchBillConfig } from '../../../configs/SearchBillConfig';
import { SearchBillWMSConfig } from '../../../configs/SearchBillWMSConfig';

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

  //For local Update data to access searchConfig or searchWMS config
  const data = SearchBillWMSConfig?.SearchBillWMSConfig?.[0]

  let configs = useMemo(
    () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, data, "sections.search.uiConfig.fields",{
       updateDependent : [
        {
          key : "createdFrom",
          value : [new Date().toISOString().split("T")[0]]
        },
        {
          key : "createdTo",
          value : [new Date().toISOString().split("T")[0]]
        }
      ]
    }));

  //if(isLoading) return <Loader />
  return (
    <React.Fragment>
      <Header className="works-header-search">{t(configs?.label)}</Header>
      <div className="inbox-search-wrapper">
          <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default SearchBill;