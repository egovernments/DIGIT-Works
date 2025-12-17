import React,{Fragment} from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { EditIcon, AddNewIcon, InboxIcon , ArrowRightInbox , CreateLoiIcon, CardText, ArrowLeftWhite } from "@egovernments/digit-ui-react-components";
import { PanelCard, Button,ActionBar } from "@egovernments/digit-ui-components";
import { useHistory } from "react-router-dom";
// import { Banner, Card, ActionBar, SubmitBar } from "@egovernments/digit-ui-react-components"; // Import the Banner component you provided

const RAResponseBanner = () => {
  const { t } = useTranslation();
  const history = useHistory();

  // Get the current URL
  const currentURL = window.location.href;

  // console.log(currentURL);

  // Create a URL object from the current URL
  const url = new URL(currentURL);

  // Get the value of the "compositionId" query parameter
  const compositionId = url.searchParams.get("compositionId");
  const sorId = url.searchParams.get("sorId");
  const effectiveFrom = url.searchParams.get("fromeffective");
  const isUpdate = url.searchParams.get("isUpdate");

  // Now you can work with the response object as needed

  const navigate = (page) => {
    switch (page) {
      case "search-sor": {
        history.push(`/${window.contextPath}/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=SOR`);
        break;
      }
      case "view-rateAnalysis": {
        history.push(`/${window.contextPath}/employee/rateAnalysis/view-rate-analysis?sorId=${sorId}&fromeffective=${effectiveFrom}`);
        break;
      }
    }
  };

  // Customize the message based on success or failure
  const message = true
    ? isUpdate === true || isUpdate === "true"
      ? t("RA_SUCCESSFUL_UPDATE_MESSAGE")
      : t("RA_SUCCESSFUL_CREATE_MESSAGE")
    : t("RA_ERROR_MESSAGE");

  const children = [
    <div style={{ display: "flex", alignItems: "center" }}>
      <Button
        label={t("RA_GO_TO_SEARCH_SOR")}
        variation="link"
        icon={"ArrowBack"}
        onClick={() => navigate("search-sor")}
        type="button"
        style={{ display: "flex", marginRight: "3rem" }}
      />
        <Button
          label={t("RA_VIEW_RATE_ANALYSIS")}
          variation="link"
          icon={"AddExpenseTwo"}
          onClick={() => navigate("view-rateAnalysis")}
          type="button"
          style={{ marginRight: "8px" }}
        />
    </div>,
  ];

  return (
    <>
      <PanelCard type={"success"} message={message} response={compositionId} info={t("RA_REFERENCE_NUMBER")} children={children} />
      <ActionBar
        actionFields={[
          <Link to={`/${window.contextPath}/employee`}>
            <Button label={t("RA_GO_HOME")} variation="primary" type="button" />
          </Link>,
        ]}
        setactionFieldsToRight={true}
        className={"new-actionbar"}
      />
    </>
  );
};
export default RAResponseBanner;