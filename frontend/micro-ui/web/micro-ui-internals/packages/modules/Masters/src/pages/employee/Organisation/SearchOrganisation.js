import React, { useEffect, useMemo } from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import searchOrganisationConfig from "../../../configs/searchOrganisationConfig";
import { useHistory, useLocation } from "react-router-dom";
import { Button } from "@egovernments/digit-ui-components";

const SearchOrganisation = () => {
  const { t } = useTranslation();
  const history = useHistory()
  const location = useLocation()

  const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
  const [sessionFormData, clearSessionFormData] = orgSession;

  //const orgConfigs = searchOrganisationConfig();
  const configModuleName = Digit.Utils.getConfigModuleName()
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      tenant,
      configModuleName,
   [
    {
      name: "SearchOrganisationConfig",
    },
  ]);

  const orgConfigs = data?.[configModuleName]?.SearchOrganisationConfig?.[0]

  let configs = useMemo(
    () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, orgConfigs, "sections.search.uiConfig.fields",{
      updateDependent : [
        {
          key : "createdFrom",
          value : [new Date().toISOString().split("T")[0]]
        },
        {
          key : "createdTo",
          value : [new Date().toISOString().split("T")[0]]
        }
      ]
    }
    ),[orgConfigs]);

  useEffect(()=>{
      if (!window.location.href.includes("create-organization") && sessionFormData && Object.keys(sessionFormData) != 0) {
        clearSessionFormData();
      }
  },[location]);

  if (isLoading) return <Loader />;
  return (
    <React.Fragment>
      <div className="jk-header-btn-wrapper">
        <Header className="works-header-search">{t(configs?.label)}</Header>
        {Digit.Utils.didEmployeeHasRole(configs?.actionRole) && (
          <Button
          label={t(configs?.actionLabel)}
          variation="secondary"
          icon={"Add"}
          onClick={() => {
            history.push(`/${window?.contextPath}/employee/${configs?.actionLink}`)
          }}
          type="button"
        />
        )}
      </div>
      <div className="inbox-search-wrapper">
        <InboxSearchComposer configs={configs}></InboxSearchComposer>
      </div>
    </React.Fragment>
  );
};

export default SearchOrganisation;
