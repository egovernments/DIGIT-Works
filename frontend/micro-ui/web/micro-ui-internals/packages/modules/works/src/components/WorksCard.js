import { EmployeeModuleCard, ArrowRightInbox, WSICon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const WorksCard = () => {
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
    moduleName: t("WORKS"),
    kpis: [
      {
        //Pass Count Value from Inbox API here
        count: 21,
        label: t("INBOX"),
        link: `/${window?.contextPath}/employee/works/inbox`,
      },
    ],
    links: [
      {
        label: t("WORKS_ESTIMATES"),
        link: `/${window?.contextPath}/employee/works/inbox`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("WORKS_LOI"),
        link: `/${window?.contextPath}/employee/works/loiinbox`,
        roles: ["LOI CHECKER", "LOI APPROVER", "LOI CREATOR", "EMPLOYEE"],
      },
      {
        label: t("WORKS_MILESSTONES"),
        link: `/${window?.contextPath}/employee`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
      {
        label: t("WORKS_PAY_CALENDAR"),
        link: `/${window?.contextPath}/employee`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
      {
        label: t("WORKS_KICKOFF_CHECKLIST"),
        link: `/${window?.contextPath}/employee/works/checklist`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
        count: 21,
      },
      {
        label: t("WORKS_CONTRACTOR_BILL"),
        link: `/${window?.contextPath}/employee/works/create-contractor`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
      {
        label: t("WORKS_REPORTS"),
        link: `/${window?.contextPath}/employee/`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
      {
        label: t("WORKS_MASTERS"),
        link: `/${window?.contextPath}/employee/works/search-organization`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
      {
        label: t("WORKS_DASHBOARD"),
        link: `/${window?.contextPath}/employee/`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default WorksCard;
