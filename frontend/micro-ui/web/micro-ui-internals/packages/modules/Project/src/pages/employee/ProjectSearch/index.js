import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer } from "@egovernments/digit-ui-react-components";
import searchConfig from "../../../configs/searchConfig";

const ProjectSearch = () => {
    const { t } = useTranslation();

    const configs = searchConfig();
   
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{configs?.label}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default ProjectSearch;