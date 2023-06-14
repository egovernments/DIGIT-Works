import React, { useMemo } from 'react'
import { useTranslation } from "react-i18next";
import { useHistory, useLocation } from 'react-router-dom';
import { Header, InboxSearchComposer, Loader, Button, DownloadImgIcon} from "@egovernments/digit-ui-react-components";
import { SearchPaymentInstructionConfig } from '../../../configs/SearchPaymentInstructionConfig';

const SearchPaymentInstruction = () => {
  const { t } = useTranslation();
  const { state } = useLocation()
  const history = useHistory()
  const stateTenant = Digit.ULBService.getStateId();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  
  const { isLoading, data:configs } = Digit.Hooks.useCustomMDMS(
      stateTenant,
      Digit.Utils.getConfigModuleName(),
      [
          {
              "name": "SearchBillWMSConfig"
          }
      ],
      {
        select: (data) => {
            // const result =  data?.[Digit.Utils.getConfigModuleName()]?.SearchBillWMSConfig[0];
            const result =  SearchPaymentInstructionConfig?.SearchBillWMSConfig[0];
          
              const configs =  Digit.Utils.preProcessMDMSConfigInboxSearch(t, result, "sections.search.uiConfig.fields",{
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
           })
           return configs
        },
      }
  );
  

  //For local Update data to access searchConfig or searchWMS config
  // const configs = SearchBillWMSConfig?.SearchBillWMSConfig?.[0]

  

  if(isLoading) return <Loader />

  return (
    <React.Fragment>
      <div className="jk-header-btn-wrapper">
        <Header className="works-header-search">{t(configs?.label)}</Header>
      </div>
      <div className="inbox-search-wrapper">
          <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default SearchPaymentInstruction;