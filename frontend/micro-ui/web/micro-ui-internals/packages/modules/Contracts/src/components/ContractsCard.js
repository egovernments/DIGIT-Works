import { EmployeeModuleCard, ArrowRightInbox, WSICon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const ContractsCard = () => {
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
    Icon: <WSICon />,
    moduleName: t("Contracts"),
    kpis: [
      {
        //Pass Count Value from Inbox API here
        count: 21,
        label: t("INBOX"),
        link: `/${window?.contextPath}/employee/contracts/inbox`,
      }
    ],
    links: [
      {
        label: t("WORKS_CONTRACTS_INBOX"),
        link: `/${window?.contextPath}/employee/contracts/inbox`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("WORKS_CONTRACTS_SEARCH"),
        link: `/${window?.contextPath}/employee/contracts/search`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("WORKS_CONTRACTS_CREATE"),
        link: `/${window?.contextPath}/employee/contracts/create`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default ContractsCard;
