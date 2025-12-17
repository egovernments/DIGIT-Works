import React,{Fragment} from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PanelCard, Button } from "@egovernments/digit-ui-components";

const CreateOrganizationSuccess = ({ isSuccess, setCreateOrgStatus }) => {
  const { t } = useTranslation();

  const modifyOrg = () => {
    setCreateOrgStatus(null);
  };

  const createOrg = () => {
    setCreateOrgStatus(null);
  };

  const children = [
    <div style={{ display: "flex", justifyContent: "space-between" }}>
      <Button
        label={t("MASTERS_ORGANISATION_MODIFY")}
        variation="link"
        icon={"EditIcon"}
        onClick={modifyOrg}
        type="button"
        style={{ display: "flex" }}
      />
      <Button
        label={t("MASTERS_CREATE_NEW_ORGANISATION")}
        variation="link"
        icon={"AddNewIcon"}
        onClick={createOrg}
        type="button"
        style={{ display: "flex" }}
      />
    </div>,
  ];

  const footerChildren = [
    <Link to={`/${window.contextPath}/employee`}>
      <Button label={t("CORE_COMMON_GO_TO_HOME")} variation="primary" type="button" />
    </Link>,
  ];

  return (
    <>
      <PanelCard
        type={isSuccess ? "success" : "error"}
        message={`${isSuccess ? t("MASTERS_ORGANISATION_CREATED") : t("MASTERS_ORGANISATION_CREATE_FAILURE")}`}
        info={`${isSuccess ? t("WORKS_ORGANISATION_CODE") : ""}`}
        footerChildren={footerChildren}
        children={children}
        response={`${isSuccess ? "DH21M20129" : ""}`}
      />
    </>
  );
};

export default CreateOrganizationSuccess;
