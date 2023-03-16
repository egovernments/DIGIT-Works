import React, { useState } from 'react';
import { useTranslation } from "react-i18next";
//import { Toast } from "@egovernments/digit-ui-react-components";
import { Header, InboxSearchComposer, Loader, Button, AddFilled } from "@egovernments/digit-ui-react-components";
import searchContractConfig from "../../configs/searchContractConfig";


const SearchContractDetails = () => {
    const { t } = useTranslation();
    const configs = searchContractConfig();
  
    //if (isLoading) return <Loader />;
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
