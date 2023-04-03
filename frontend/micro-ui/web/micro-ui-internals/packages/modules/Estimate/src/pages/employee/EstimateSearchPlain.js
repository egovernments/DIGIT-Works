import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer,Loader } from "@egovernments/digit-ui-react-components";
import searchConfig from "../../configs/searchConfig";
import searchConfigMukta from "../../configs/searchConfigMukta";
import searchConfigMuktaFuzzy from "../../configs/SearchConficMuktaFuzzy";

const EstimateSearchPlain = () => {
    const { t } = useTranslation();

    // const configs = searchConfigMuktaFuzzy();
    const configModuleName = Digit.Utils.getConfigModuleName()
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        configModuleName,
        [
            {
                "name": "SearchEstimateConfig"
            }
        ]
    );

    const configs = data?.[configModuleName].SearchEstimateConfig?.[0]

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

export default EstimateSearchPlain;