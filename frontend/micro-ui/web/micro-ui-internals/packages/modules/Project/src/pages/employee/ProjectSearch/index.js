import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../configs/inboxConfig";

const ProjectSearch = () => {
    const { t } = useTranslation();

    //Import Config to Bootstrap the Layout -- this is a test config
    const configs = inboxConfig();
   
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{configs?.pageHeader}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default ProjectSearch;