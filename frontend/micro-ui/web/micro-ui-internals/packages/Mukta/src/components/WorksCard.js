import { EmployeeModuleCard, ArrowRightInbox, WorksMgmtIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const ROLES = {
  PROJECT: ["PROJECT_CREATOR", "PROJECT_VIEWER"],
  ESTIMATE: ["ESTIMATE_CREATOR", "ESTIMATE_VERIFIER", "TECHNICAL_SANCTIONER", "ESTIMATE_APPROVER"],
  CONTRACT: ["WORK_ORDER_CREATOR", "WORK_ORDER_VIEWER", "WORK_ORDER_APPROVER"],
  MASTERS: ["MUKTA_ADMIN"],
  BILLS: ["BILL_CREATOR", "BILL_VIEWER"],
  MUSTERROLLS: ["MUSTER_ROLL_VERIFIER", "MUSTER_ROLL_APPROVER"],
  DSS: ["STADMIN"],
};

// Mukta Overrriding the Works Home screen card
const WorksCard = () => {
  if (!Digit.Utils.didEmployeeHasAtleastOneRole(Object.values(ROLES).flatMap((e) => e))) {
    return null;
  }

  //getting the businessServiceMap
  const businessServiceMap = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService();

  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const requestCriteria = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [businessServiceMap?.attendencemgmt],
          moduleName: "muster-roll-service",
        },
        moduleSearchCriteria: {
          tenantId,
        },
        limit: 10,
        offset: 0,
      },
    },
    config: {
      enabled: Digit.Utils.didEmployeeHasAtleastOneRole(ROLES.MUSTERROLLS) || Digit.Utils.didEmployeeHasAtleastOneRole(ROLES.BILLS),
    },
  };

  const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);

  const requestCriteriaEstimate = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [businessServiceMap?.estimate],
          moduleName: "estimate-service",
        },
        moduleSearchCriteria: {
          tenantId,
        },
        limit: 10,
        offset: 0,
      },
    },
    config: {
      enabled: Digit.Utils.didEmployeeHasAtleastOneRole(ROLES.ESTIMATE),
    },
    changeQueryName: "EstimateInbox",
  };

  const { isLoading: isLoadingEstimate, data: dataEstimate } = Digit.Hooks.useCustomAPIHook(requestCriteriaEstimate);

  const requestCriteriaContract = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [businessServiceMap?.contracts],
          moduleName: "contract-service",
        },
        moduleSearchCriteria: {
          tenantId,
        },
        limit: 10,
        offset: 0,
      },
    },
    config: {
      enabled: Digit.Utils.didEmployeeHasAtleastOneRole(ROLES.CONTRACT),
    },
    changeQueryName: "ContractInbox",
  };

  const { isLoading: isLoadingContract, data: dataContract } = Digit.Hooks.useCustomAPIHook(requestCriteriaContract);

  let links = [
    {
      label: t("ACTION_TEST_PROJECT"),
      link: `/${window?.contextPath}/employee/project/search-project`,
      roles: ROLES.PROJECT,
    },
    {
      label: t("WORKS_ESTIMATES"),
      link: `/${window?.contextPath}/employee/estimate/inbox`,
      roles: ROLES.ESTIMATE,
      count: isLoadingEstimate ? "-" : dataEstimate?.totalCount,
    },
    {
      label: t("WORKS_CONTRACTS"),
      link: `/${window?.contextPath}/employee/contracts/inbox`,
      roles: ROLES.CONTRACT,
      count: isLoadingContract ? "-" : dataContract?.totalCount,
    },
    {
      label: t("WORKS_MUSTERROLLS"),
      link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
      roles: ROLES.MUSTERROLLS,
      count: isLoading ? "-" : data?.totalCount,
    },
    {
      label: t("WORKS_WAGESEEKERS"),
      link: `/${window?.contextPath}/employee/masters/search-wageseeker`,
      roles: ROLES.MASTERS,
    },
    {
      label: t("WORKS_MASTERS"),
      link: `/${window?.contextPath}/employee/masters/search-organization`,
      roles: ROLES.MASTERS,
    },
    {
      label: t("ACTION_TEST_BILLS"),
      link: `/${window?.contextPath}/employee/expenditure/inbox`,
      roles: ROLES.BILLS,
      count: isLoading ? "-" : data?.totalCount,
    },
    {
      label: t("WORKS_DASHBOARD"),
      link: `/${window?.contextPath}/employee/dss/dashboard/works`,
      roles: ROLES.DSS,
    },
  ];

  links = links.filter((link) => (link?.roles && link?.roles?.length > 0 ? Digit.Utils.didEmployeeHasAtleastOneRole(link?.roles) : true));

  const propsForModuleCard = {
    Icon: <WorksMgmtIcon />,
    moduleName: t("WORKS"),
    kpis: [
      // {
      //   //Pass Count Value from Inbox API here
      //   count: 33,
      //   label: t("INBOX"),
      //   link: `/${window?.contextPath}/employee/estimate/inbox`,
      // },
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
