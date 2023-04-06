import React, { useMemo,useState,useEffect } from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfigMukta from "./inboxConfigMukta";
import { useLocation } from "react-router-dom";
const Inbox = () => {
  const { t } = useTranslation()
  //fetch this config from mdms and pass it to the preProcess fn
  const location = useLocation()
  // let configs = inboxConfigMukta();
  const [pageConfig, setPageConfig] = useState(null)
  const moduleName = Digit.Utils.getConfigModuleName()
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(tenant,
    moduleName,
    [
      {
        name: "InboxConfigContracts",
      },
    ]);

  const configs = data?.[moduleName]?.InboxConfigContracts?.[0]

  const updatedConfig = useMemo(
    () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, pageConfig, "sections.search.uiConfig.fields", {}),
    [data,pageConfig]);

  useEffect(() => {
    setPageConfig(_.cloneDeep(configs))
  }, [data,location])



  if (isLoading || !pageConfig) return <Loader />

  return (
    <React.Fragment>
      <Header styles={{ fontSize: "32px" }}>{t(updatedConfig?.label)}{location?.state?.count ? <span className="inbox-count">{location?.state?.count}</span> : null}</Header>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={updatedConfig}></InboxSearchComposer>
      </div>
    </React.Fragment>
  )
}

export default Inbox