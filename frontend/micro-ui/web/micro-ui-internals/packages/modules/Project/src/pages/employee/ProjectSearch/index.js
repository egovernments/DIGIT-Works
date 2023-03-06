import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader, Button, AddFilled } from "@egovernments/digit-ui-react-components";
import { useHistory } from "react-router-dom";

import searchConfig from "../../../configs/searchConfig";

const ProjectSearch = () => {
  const { t } = useTranslation();
  const history = useHistory();

  // const configs = searchConfig();
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(tenant, 
    Digit.Utils.getConfigModuleName(),
    [
    {
      name: "SearchProjectConfig",
    },
  ]);

  const configs = data?.[Digit.Utils.getConfigModuleName()]?.SearchProjectConfig?.[0];

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
