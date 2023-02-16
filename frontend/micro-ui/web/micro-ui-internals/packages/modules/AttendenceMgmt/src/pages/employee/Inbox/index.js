import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../config/inboxConfig";

const Inbox = () => {
    const { t } = useTranslation();
    
    //const configs = inboxConfig();
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        "commonUiConfig",
        [
            {
                "name": "musterInboxConfig"
            }
        ]
    );
   
    const configs = data?.commonUiConfig?.musterInboxConfig?.[0]
   
    if(isLoading) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default Inbox;