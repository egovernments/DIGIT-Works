import React from 'react'
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import searchConfig from '../../config/searchConfig';

const SearchAttendance = () => {
    const { t } = useTranslation();

    const configs = searchConfig();
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default SearchAttendance;