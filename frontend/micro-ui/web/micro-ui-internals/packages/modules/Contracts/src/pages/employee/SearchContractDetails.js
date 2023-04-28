import React from 'react';
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import searchContractConfig from "../../configs/searchContractConfig";


const SearchContractDetails = () => {
    const { t } = useTranslation();
    const configs = searchContractConfig();
    const configModuleName = Digit.Utils.getConfigModuleName()
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        configModuleName,
        [
            {
                "name": "SearchContractConfig"
            }
        ]
    );

    // const configs = data?.[configModuleName].SearchContractConfig?.[0]

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
