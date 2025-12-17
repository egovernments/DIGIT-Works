import React, { Fragment } from "react";
import { Link, useLocation, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PanelCard, Button,ActionBar } from "@egovernments/digit-ui-components";

const MastersResponse = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const { state } = useLocation();
  const queryParams = Digit.Hooks.useQueryParams();
  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

  const navigate = (page) => {
    switch (page) {
      case "modify-org": {
        history.push(`/${window.contextPath}/employee/masters/create-organization?tenantId=${queryParams?.tenantId}&orgId=${queryParams?.orgId}`);
        break;
      }
      case "create-org": {
        history.push(`/${window.contextPath}/employee/masters/create-organization`);
        break;
      }
      case "home-screen": {
        history.push(`/${window.contextPath}/employee`);
        break;
      }
    }
  };

  const children = [
    <div style={{ display: "flex", alignItems: "center" }}>
      <Button label={t("ES_COMMON_GOTO_HOME")} variation="link" icon={"ArrowBack"} onClick={() => navigate("home-screen")} type="button" />
      {!state?.isWageSeeker && state?.isSuccess && loggedInUserRoles?.includes("VIEW_ORG_UNMASKED") && loggedInUserRoles?.includes("VIEW_DED_UNMASKED") && (
        <Button label={t("MASTERS_ORGANISATION_MODIFY")} variation="link" icon={"EditIcon"} onClick={() => navigate("modify-org")} type="button" />
      )}
      {!state?.isWageSeeker && state?.isSuccess && (
        <Button
          label={t("MASTERS_CREATE_NEW_ORGANISATION")}
          variation="link"
          icon={"AddExpenseTwo"}
          onClick={() => navigate("create-org")}
          type="button"
        />
      )}
    </div>,
  ];

  return (
    <>
      <PanelCard
        type={state?.isSuccess ? "success" : "error"}
        message={t(state?.message)}
        description={!state?.isWageSeeker ? t(state?.otherMessage) : undefined}
        children={children}
        info={`${state?.showId ? (state?.isWageSeeker ? t("MASTERS_WAGESEEKER_ID") : t("MASTERS_ORGANISATION_ID")) : ""}`}
        response={state?.isWageSeeker ? queryParams?.individualId : queryParams?.orgId}
      />
      <ActionBar
        actionFields={[
          <Link to={`/${window.contextPath}/employee`}>
            <Button label={t("CORE_COMMON_GO_TO_HOME")} variation="primary" type="button" />
          </Link>
        ]}
        setactionFieldsToRight={true}
        className={"new-actionbar"}
      />
    </>
  );
};

export default MastersResponse;
