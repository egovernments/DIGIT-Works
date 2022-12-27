import { EmployeeModuleCard, SurveyIconSolid } from "@egovernments/digit-ui-react-components";
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
    Icon: <SurveyIconSolid />,
    moduleName: t("WORKS_CONTRACTS"),
    kpis: [
      {
        //Pass Count Value from Inbox API here
        count: 21,
        label: t("WORKS_INBOX"),
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
        label: t("ACTION_TEST_SEARCH_APPROVED_ESTIMATES"),
        link: `/${window?.contextPath}/employee/contracts/inbox`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("WORKS_SEARCH_CONTRACTS"),
        link: `/${window?.contextPath}/employee/contracts/search-contract`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("WORKS_CREATE_CONTRACT"),
        link: `/${window?.contextPath}/employee/contracts/create-contract`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("WORKS_VIEW_CONTRACT"),
        link: `/${window?.contextPath}/employee/contracts/view-contract`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      }
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default ContractsCard;
