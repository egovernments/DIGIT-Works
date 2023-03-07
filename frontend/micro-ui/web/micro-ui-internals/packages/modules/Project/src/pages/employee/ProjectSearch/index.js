import React, { useMemo } from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader, Button, AddFilled } from "@egovernments/digit-ui-react-components";
import { useHistory } from "react-router-dom";
import searchConfigMUKTA from "../../../configs/searchConfigMUKTA";

const ProjectSearch = () => {
  let renderType = "local";  //mdms, local
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
  const searchMDMS = searchConfigMUKTA?.SearchProjectConfig?.[0];
  const { isLoading : isWardLoading, data : wardsAndLocalities } = Digit.Hooks.useLocation(
    tenantId, 'Ward',
    {
        select: (data) => {
            const wards = []
            const localities = {}
            data?.TenantBoundary[0]?.boundary.forEach((item) => {
                localities[item?.code] = item?.children.map(item => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}`, label : item?.label }))
                wards.push({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
            });
           return {
                wards, localities
           }
        }
    });

  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(tenant, 
    Digit.Utils.getConfigModuleName(),
    [
    {
      name: "SearchProjectConfig",
    },
  ]);

  let configs = {};
  if(renderType === "mdms") {
    configs = data?.[Digit.Utils.getConfigModuleName()]?.SearchProjectConfig?.[0];
  }else{
    configs = useMemo(
      () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, searchMDMS, "sections.search.uiConfig.fields",{
        updateDependent : [
          {
            key : 'ward',
            value : wardsAndLocalities?.wards
          }
        ]
      }),[wardsAndLocalities]);
  }

  if (isLoading) return <Loader />;
  return (
    <React.Fragment>
      <div className="jk-header-btn-wrapper">
        <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
        {Digit.Utils.didEmployeeHasRole(configs?.actionRole) && (
          <Button
            label={t(configs?.actionLabel)}
            variation="secondary"
            icon={<AddFilled style={{height : "20px", width : "20px"}}/>}
            onButtonClick={() => {
              history.push(`/${window?.contextPath}/employee/${configs?.actionLink}`);
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

export default ProjectSearch;
