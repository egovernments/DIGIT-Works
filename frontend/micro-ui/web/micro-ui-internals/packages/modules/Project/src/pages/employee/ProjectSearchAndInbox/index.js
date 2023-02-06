import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../configs/InboxConfig";

const ProjectSearchAndInboxComponent = () => {
    const { t } = useTranslation();

    const configs = inboxConfig();
    
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{configs?.label}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default ProjectSearchAndInboxComponent;