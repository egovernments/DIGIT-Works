import { Header, InboxSearchComposer, PropertyHouse } from "@egovernments/digit-ui-react-components";
import React, { Fragment } from "react";
import { useTranslation } from "react-i18next";
import InboxConfig from "../../../configs/InboxConfig";

const ProjectSearchAndInbox = () => {
    const {t} = useTranslation();

    //Import Config to Bootstrap the Layout -- this is a test config
    const configs = InboxConfig(t);

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t(`${configs?.pageHeader}`)}</Header>
            </div>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </div>
    )
}

export default ProjectSearchAndInbox;