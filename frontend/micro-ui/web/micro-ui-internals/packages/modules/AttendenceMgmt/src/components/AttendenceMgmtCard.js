import { EmployeeModuleCard, ArrowRightInbox, WSICon, ComplaintIcon } from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";

const AttendenceMgmtCard = () => {
  // if (!Digit.Utils.wsAccess()) {
  //   return null;
  // }
  const { t } = useTranslation();

  // let links = [
  //   {
  //     label: t("WS_APPLY_NEW_CONNECTION_HOME_CARD_LABEL"),
  //     link: `/${window?.contextPath}/employee/ws/create-application`,
  //     roles: ["WS_CEMP", "SW_CEMP"],
  //   },
  // ];

  // links = links.filter((link) => (link.roles ? checkForEmployee(link.roles) : true));

    const propsForModuleCard = {
        Icon: <ComplaintIcon fill="white"/>,
        moduleName: t("ATM_ATTENDANCE_MANAGEMENT"),
        kpis: [
            {
                //Pass Count Value from Inbox API here
                count: 21,
                label: t("INBOX"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
            }
        ],
        links: [
            {
                label: t("ORG_MGMT"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
            },
            {
                label: t("IND_MGMT"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
            },
            {
                label: t("ATTENDENCE_APPROVAL"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
            },
            {
                label: t("ATMGMT_DASHBOADR"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
            },
            {
                label: t("MARK_ATTENDENCE"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
            },
            {
                label: t("ATM_VIEW_ATTENDENCE"),
                link: `/${window?.contextPath}/employee/attendencemgmt/view-attendance`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
            },
        ],
    };
    return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default AttendenceMgmtCard;
