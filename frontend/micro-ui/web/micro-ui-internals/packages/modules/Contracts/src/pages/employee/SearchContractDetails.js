import React, { useMemo } from 'react';
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import searchContractConfig from "../../configs/searchContractConfig";


const SearchContractDetails = () => {
    const { t } = useTranslation();
    //const configs = searchContractConfig();
    const configModuleName = Digit.Utils.getConfigModuleName()
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        configModuleName,
        [
            {
                "name": "SearchContractConfig"
            }
        ],
        {
          select: (data) => {
            
              const config = data?.[Digit.Utils.getConfigModuleName()]?.SearchContractConfig?.[0];
              
              return config
            },
        }
    );

    //const configs = data?.[configModuleName].SearchContractConfig?.[0]

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
      }
      ),[data])

  if (isLoading) return <Loader />
  
  return (
    <React.Fragment>
      <Header className="works-header-search">{t("SEARCH_WORK_ORDER")}</Header>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default SearchContractDetails
