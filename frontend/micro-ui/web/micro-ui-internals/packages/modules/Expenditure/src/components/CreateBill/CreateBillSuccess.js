import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PanelCard, Button } from "@egovernments/digit-ui-components";

const CreateBillSuccess = ({ isSuccess }) => {
  const { t } = useTranslation();

  return (
    <>
      <PanelCard
        type={isSuccess ? "success" : "error"}
        message={`${isSuccess ? t("EXP_BILL_CREATION_SUCCESS") : t("EXP_BILL_CREATION_FAILURE")}`}
        footerChildren={[
          <Link to={`/${window.contextPath}/employee`}>
            <Button label={t("CORE_COMMON_GO_TO_HOME")} variation="primary" type="button" />
          </Link>
        ]}
        description={isSuccess ? t("EXP_BILL_CREATION_SUCCESS_MESSAGE") : undefined}
        info={`${isSuccess ? t("EXP_BILL_ID") : ""}`}
        response={`${isSuccess ? "Bill/2021-22/09/0001" : ""}`}
      />
    </>
  );
};

export default CreateBillSuccess;
