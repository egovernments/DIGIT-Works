import React, { useEffect, useState, Fragment } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PanelCard, Button,ActionBar } from "@egovernments/digit-ui-components";

const CreatePurchaseBillResponse = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const queryStrings = Digit.Hooks.useQueryParams();
  const [billNumberList, setBillNumberList] = useState(queryStrings?.billNumber?.split(","));
  const [isResponseSuccess, setIsResponseSuccess] = useState(
    queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true
  );
  const { state } = useLocation();
  //session data
  const PurchaseBillSession = Digit.Hooks.useSessionStorage("PURCHASE_BILL_CREATE", {});
  const [sessionFormData, setSessionFormData, clearSessionFormData] = PurchaseBillSession;

  useEffect(() => {
    if (Object.keys(sessionFormData).length != 0) {
      clearSessionFormData();
    }
  });

  const navigate = (page) => {
    switch (page) {
      case "billing-inbox": {
        history.push(`/${window.contextPath}/employee/expenditure/inbox`);
      }
    }
  };

  const children = [
    <Button label={t("COMMON_GO_TO_INBOX")} variation="link" icon={"ArrowBack"} onClick={() => navigate("billing-inbox")} type="button" />,
  ];

  return (
    <>
      <PanelCard
        type={isResponseSuccess ? "success" : "error"}
        message={t(state?.message)}
        children={children}
        info={`${state?.showID ? t("EXP_PB_ID") : ""}`}
        multipleResponses={billNumberList}
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

export default CreatePurchaseBillResponse;
