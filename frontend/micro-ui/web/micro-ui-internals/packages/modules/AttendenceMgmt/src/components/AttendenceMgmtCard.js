import { EmployeeModuleCard, BioMetricIcon} from "@egovernments/digit-ui-react-components";
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
        Icon: <BioMetricIcon fill="white"/>,
        moduleName: t("ACTION_TEST_ATTENDENCEMGMT"),
        kpis: [
            {
                //Pass Count Value from Inbox API here
                count: 15,
                label: t("INBOX"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
            }
        ],
        links: [
            {
                label: t("INBOX"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
                count: 15,
            },
            {
                label: t("CS_INBOX_SEARCH"),
                link: `/${window?.contextPath}/employee/attendencemgmt/search-attendance`,
                roles: [],
                count: 0,
            }
        ],
    };
    return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default AttendenceMgmtCard;
