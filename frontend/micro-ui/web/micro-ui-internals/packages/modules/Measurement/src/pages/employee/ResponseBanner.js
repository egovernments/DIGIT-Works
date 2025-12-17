import React, { Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { PanelCard, Button ,ActionBar} from "@egovernments/digit-ui-components";
import { useHistory } from "react-router-dom";
import { withRouter } from "react-router-dom";

const MeasurementServiceResponse = () => {
  const { t } = useTranslation();
  const history = useHistory();

  // Get the current URL
  const currentURL = window.location.href;

  // console.log(currentURL);

  // Create a URL object from the current URL
  const url = new URL(currentURL);
  // console.log(url);

  // Get the value of the "mbreference" query parameter
  const mbReference = url.searchParams.get("mbreference");

  // console.log(mbReference); // This will log "Mb848484"

  //     const responseObj = location?.state?.responseData;

  //    console.log(responseObj?.get?.responseInfo?.status);

  // Now you can work with the response object as needed

  // const isApplicationSubmitted = responseObj?.get?.responseInfo?.status === "successful";

  //   const goToHome = () => {
  //     history.push({
  //       pathname: `/${window?.contextPath}/employee`,
  //     });
  //   };

  // Customize the message based on success or failure
  const message = true ? t("MB_SUCCESSFUL_MESSAGE") : t("MB_ERROR_MESSAGE");

  const navigate = (page) => {
    switch (page) {
      case "measurement-inbox": {
        history.push(`/${window.contextPath}/employee/measurement/inbox`);
      }
    }
  };

  const children = [
    <Button label={t("MB_GO_INBOX")} variation="link" icon={"ArrowBack"} onClick={() => navigate("measurement-inbox")} type="button" />,
  ];

  return (
    <>
      <PanelCard
        type={"success"}
        message={message}
        info={t("MB_REFERENCE_NUMBER")}
        children={children}
        response={mbReference}
      />
      <ActionBar
        actionFields={[
          <Link to={`/${window.contextPath}/employee`}>
            <Button label={t("MB_GO_HOME")} variation="primary" type="button" />
          </Link>
        ]}
        setactionFieldsToRight={true}
        className={"new-actionbar"}
      />
    </>
  );
};
export default MeasurementServiceResponse;