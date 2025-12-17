import React, { useEffect, useState, Fragment } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PanelCard, SubmitBar, Button,ActionBar } from "@egovernments/digit-ui-components";

const CreateProjectResponse = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const queryStrings = Digit.Hooks.useQueryParams();
  const [projectIDsList, setProjectIDsList] = useState(queryStrings?.projectIDs.split(","));
  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");
  const [isEstimateCreator, setIsEstimateCreator] = useState(false);
  const [isResponseSuccess, setIsResponseSuccess] = useState(
    queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true
  );
  const { state } = useLocation();

  useEffect(() => {
    setIsEstimateCreator(loggedInUserRoles?.includes("ESTIMATE_CREATOR"));
  }, []);

  const navigate = (page) => {
    switch (page) {
      case "search-project": {
        history.push(`/${window.contextPath}/employee/project/search-project`);
        break;
      }
      case "create-estimate": {
        history.push(
          `/${window.contextPath}/employee/estimate/create-detailed-estimate?tenantId=${queryStrings?.tenantId}&projectNumber=${projectIDsList?.[0]}`
        );
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
      <Button
        label={t("PROJECT_GO_TO_SEARCH_PROJECT")}
        variation="link"
        icon={"ArrowBack"}
        onClick={() => navigate("search-project")}
        type="button"
        style={{ display: "flex", marginRight: "3rem" }}
      />

      {isResponseSuccess && isEstimateCreator && (
        <Button
          label={t("COMMON_CREATE_ESTIMATE")}
          variation="link"
          icon={"AddExpenseTwo"}
          onClick={() => navigate("create-estimate")}
          type="button"
          style={{ marginRight: "8px" }}
        />
      )}
    </div>,
  ];


  return (
    <>
      <PanelCard
        type={isResponseSuccess ? "success" : "error"}
        message={t(state?.message)}
        multipleResponses={projectIDsList}
        info={`${state?.showProjectID ? t("WORKS_PROJECT_ID") : ""}`}
        children={children}
      />
      <ActionBar
        actionFields={[
          <Link to={`/${window.contextPath}/employee`}>
            <Button label={t("CORE_COMMON_GO_TO_HOME")} variation="primary" type="button" />
          </Link>,
        ]}
        setactionFieldsToRight={true}
        className={"new-actionbar"}
      />
    </>
  );
};

export default CreateProjectResponse;
