import React, { useMemo } from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfigMukta from "./inboxConfigMukta";

const ContractsInbox = () => {
  const { t } = useTranslation()
  //fetch this config from mdms and pass it to the preProcess fn
  let configs = inboxConfigMukta(t);
  const moduleName = Digit.Utils.getConfigModuleName()
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(tenant,
    moduleName,
    [
      {
        name: "ContractsInboxConfig",
      },
    ]);

  //const configs = data?.[moduleName]?.EstimateInboxConfig?.[0]

  const updatedConfig = useMemo(
    () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, configs, "sections.search.uiConfig.fields", {}),
    [data]);

  if (isLoading) return <Loader />
  return (
    <React.Fragment>
      <Header styles={{ fontSize: "32px" }}>{t(updatedConfig?.label)}</Header>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={updatedConfig}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default ContractsInbox