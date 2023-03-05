import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer,Loader } from "@egovernments/digit-ui-react-components";
import searchConfig from "../../configs/searchConfig";

const EstimateSearch = () => {
    const { t } = useTranslation();

    // const configs = searchConfig();

    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "SearchEstimateConfig"
            }
        ]
    );

    const configs = data?.commonUiConfig?.SearchEstimateConfig?.[0]

    if (isLoading) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
            <div className="inbox-search-wrapper">
                <InboxSearchComposer configs={configs}></InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default EstimateSearch;