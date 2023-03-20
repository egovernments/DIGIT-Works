import React from 'react';
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import searchContractConfig from "../../configs/searchContractConfig";


const SearchContractDetails = () => {
    const { t } = useTranslation();
    const configs = searchContractConfig();
  
  return (
    <React.Fragment>
      <Header styles={{ fontSize: "32px" }}>{t("SEARCH_WORK_ORDER")}</Header>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default SearchContractDetails
