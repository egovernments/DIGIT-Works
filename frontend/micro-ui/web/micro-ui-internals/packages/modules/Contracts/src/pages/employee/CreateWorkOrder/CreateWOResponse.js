import React,{ useState,Fragment } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PanelCard, Button,ActionBar } from "@egovernments/digit-ui-components";

const CreateWOResponse = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const queryStrings = Digit.Hooks.useQueryParams();
  const [contractNumberList, setContractNumberList] = useState(queryStrings?.contractNumber?.split(","));
  const [isResponseSuccess, setIsResponseSuccess] = useState(
    queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true
  );
  const { state } = useLocation();

  const navigate = (page) => {
    switch (page) {
      case "contracts-inbox": {
        history.push(`/${window.contextPath}/employee/contracts/inbox`);
      }
    }
  };

  const children = [
    <Button label={t("COMMON_GO_TO_INBOX")} variation="link" icon={"ArrowBack"} onClick={() => navigate("contracts-inbox")} type="button" />,
  ];
  return (
    <>
      <PanelCard
        type={isResponseSuccess ? "success" : "error"}
        message={t(state?.message)}
        children={children}
        info={`${state?.showID ? t("CONTRACTS_WO_ID") : ""}`}
        multipleResponses={contractNumberList}
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

export default CreateWOResponse;
