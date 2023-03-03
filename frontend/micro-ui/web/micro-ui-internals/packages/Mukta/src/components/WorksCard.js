import { EmployeeModuleCard, ArrowRightInbox, WorksMgmtIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";


// Mukta Overrriding the Works Home screen card
const WorksCard = () => {
  // if (!Digit.Utils.wsAccess()) {
  //   return null;
  // }
  const { t } = useTranslation();

  let links = [
    {
      label: t("ACTION_TEST_PROJECT"),
      link: `/${window?.contextPath}/employee/project/search-project`,
      roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE","SUPERUSER"],
    },
    {
      label: t("WORKS_ESTIMATES"),  
      link: `/${window?.contextPath}/employee/estimate/inbox`,
      roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE","SUPERUSER"],
      count: 1,
    },
    {
      label: t("WORKS_CONTRACTS"),
      link: `/${window?.contextPath}/employee/contracts/inbox`,
      roles: ["LOI CHECKER", "LOI APPROVER", "LOI CREATOR", "EMPLOYEE","SUPERUSER"],
      count: 1,
    },
  
    {
      label: t("WORKS_WAGESEEKERS"),
      link: `/${window?.contextPath}/employee/masters/search-wageseeker`,
      roles: ["MUKTA_ADMIN"],
    },
    {
      label: t("WORKS_MASTERS"),
      link: `/${window?.contextPath}/employee/masters/search-organization`,
      roles: ["MUKTA_ADMIN"],
    },
    {
      label: t("WORKS_DASHBOARD"),
      link: `/${window?.contextPath}/employee/dss/dashboard/works`,
      roles: ["STADMIN"],
    },
  ]

  links = links.filter((link) => (link?.roles && link?.roles?.length>0 ? Digit.Utils.didEmployeeHasAtleastOneRole(link?.roles) : true));

  const propsForModuleCard = {
    Icon: <WorksMgmtIcon />,
    moduleName: t("WORKS"),
    kpis: [
      {
        //Pass Count Value from Inbox API here
        count: 33,
        label: t("INBOX"),
        link: `/${window?.contextPath}/employee/estimate/inbox`,
      },
    ],
    links: links,
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default WorksCard;



  // {
    //   label: t("WORKS_MILESSTONES"),
    //   link: `/${window?.contextPath}/employee`,
    //   roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
    // },
    // {
    //   label: t("WORKS_PAY_CALENDAR"),
    //   link: `/${window?.contextPath}/employee`,
    //   roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
    // },
    // {
    //   label: t("WORKS_KICKOFF_CHECKLIST"),
    //   link: `/${window?.contextPath}/employee/works/checklist`,
    //   roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
    //   count: 21,
    // },
    // {
    //   label: t("WORKS_CONTRACTOR_BILL"),
    //   link: `/${window?.contextPath}/employee/works/create-contractor`,
    //   roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
    //   count:15
    // },

    // {
    //   label: t("ACTION_TEST_BILLS"),
    //   link: `/${window?.contextPath}/employee/expenditure/billinbox`,
    //   roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
    //   count: 10,
    // },
    // {
    //   label: t("WORKS_KICKOFF_CHECKLIST"),
    //   link: `/${window?.contextPath}/employee/works/checklistinbox`,
    //   roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
    //   count: 5,
    // },
    // {
    //   label: t("WORKS_PROJECT_CLOSURE"),
    //   link: `/${window?.contextPath}/employee/works/projectclosure`,
    //   roles: [],
    // },