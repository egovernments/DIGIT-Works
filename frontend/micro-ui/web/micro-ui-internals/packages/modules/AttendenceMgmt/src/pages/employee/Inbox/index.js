import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../config/inboxConfig";
import { useLocation } from 'react-router-dom';

const Inbox = () => {
    const { t } = useTranslation();
    const { state } = useLocation()

    //const configs = inboxConfig();
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "InboxMusterConfig"
            }
        ]
    );
   
    const configs = data?.[Digit.Utils.getConfigModuleName()]?.InboxMusterConfig?.[0]

    const musterSession = Digit.Hooks.useSessionStorage("SEARCH_AND_FILTER_MUSTER", 
        configs?.defaultValues
    );

    const [sessionFormData, setSessionFormData, clearSessionFormData] = musterSession;
   
    if(isLoading) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{`${t(configs?.label)} (${state?.count})`}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}  configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default Inbox;