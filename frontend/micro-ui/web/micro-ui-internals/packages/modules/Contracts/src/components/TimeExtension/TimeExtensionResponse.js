import React, { useState,Fragment } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, LinkLabel, AddFileFilled, ArrowLeftWhite, SubmitBar} from "@egovernments/digit-ui-react-components";
import { PanelCard, Button,ActionBar } from "@egovernments/digit-ui-components";

const TimeExtensionResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [revisedWONumber,setRevisedWONumber] = useState(queryStrings?.revisedWONumber)
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true);
    
    const {state} = useLocation();
    
    const navigate = (page) =>{
        switch(page){
            case "contracts-inbox" : {
                history.push(`/${window.contextPath}/employee/contracts/inbox`)
            }
        }
    }

    const children = [
      <Button label={t("COMMON_GO_TO_INBOX")} variation="link" icon={"ArrowBack"} onClick={() => navigate("contracts-inbox")} type="button" />,
    ];

    return (
      <>
        <PanelCard
          type={isResponseSuccess ? "success" : "error"}
          message={t(state?.message)}
          children={children}
          info={state?.label ? t(state.label) : `${state?.showID ? t("CONTRACTS_WO_ID") : ""}`}
          response={revisedWONumber}
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
}

export default TimeExtensionResponse;
