import { Card, PropertyHouse } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

const InboxLinks = ({ parentRoute, businessService, classNameForMobileView }) => {
  const { t } = useTranslation();
  
  const GetLogo = () => (
    <div className="header">
      <span className="logo"><PropertyHouse /></span>
      <span className="text" style={{paddingLeft: `${classNameForMobileView ? '16px' : '0px'}`}}>{"Attendance Mgmt"}</span>
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
