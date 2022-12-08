import { Card, PropertyHouse } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const InboxLinks = ({ parentRoute, businessService, classNameForMobileView }) => {
  const { t } = useTranslation();
  
  const GetLogo = () => (
    <div className="header">
      <span className="logo"><PropertyHouse /></span>
      <span className="text" style={{paddingLeft: `${classNameForMobileView ? '16px' : '0px'}`, fontSize: "16px"}}>{t("ATM_ATTENDANCE_MANAGEMENT")}</span>
    </div>
  );

  return (
    <Card className="employeeCard filter inboxLinks">
      <div className={`complaint-links-container ${classNameForMobileView ? classNameForMobileView : ''}`}>
        {GetLogo()}
      </div>
    </Card>
  );
};

export default InboxLinks;
