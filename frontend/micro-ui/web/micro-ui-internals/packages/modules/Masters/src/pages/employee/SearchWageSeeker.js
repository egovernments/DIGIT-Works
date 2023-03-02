import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader, Button, AddFilled } from "@egovernments/digit-ui-react-components";
import searchWageSeekerConfig from "../../configs/searchWageSeekerConfig";

const SearchWageSeeker = () => {
  const { t } = useTranslation();

  const configs = searchWageSeekerConfig();
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(tenant, "commonUiConfig", [
    {
      name: "SearchProjectConfig",
    },
  ]);

  // const configs = data?.commonUiConfig?.SearchProjectConfig?.[0]

  if (isLoading) return <Loader />;
  return (
    <React.Fragment>
      <div className="jk-header-btn-wrapper">
        <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
        {Digit.Utils.didEmployeeHasRole(configs?.actionRole) && (
          <Button
            label={t(configs?.actionLabel)}
            variation="secondary"
            icon={<AddFilled />}
            onButtonClick={() => {
              console.log("clicked");
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

export default SearchWageSeeker;
