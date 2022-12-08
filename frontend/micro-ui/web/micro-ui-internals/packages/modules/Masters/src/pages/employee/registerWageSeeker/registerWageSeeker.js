import { Header } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const RegisterWageSeeker = () => {
    const { t } = useTranslation();

    return (
        <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("ATM_VIEW_ATTENDENCE")}</Header>
          </div>
        </div>
      </React.Fragment>
    )
}

export default RegisterWageSeeker;