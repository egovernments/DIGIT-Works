import React from 'react';
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer } from "@egovernments/digit-ui-react-components";
import InboxConfig from "../../../config/inboxConfig";


const TestInbox = ({isInbox, parentRoute}) => {
    const { t } = useTranslation();
    const configs = InboxConfig();
  return (
    <React.Fragment>
    <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
    <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={configs}></InboxSearchComposer>
    </div>
    </React.Fragment>
  )
}

export default TestInbox;
