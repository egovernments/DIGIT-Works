import { EmployeeModuleCard, ArrowRightInbox, WorksMgmtIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const ROLES = {
  PROJECT: ["PROJECT_CREATOR", "PROJECT_VIEWER"],
  ESTIMATE: ["ESTIMATE_CREATOR", "ESTIMATE_VERIFIER", "TECHNICAL_SANCTIONER", "ESTIMATE_APPROVER"],
  CONTRACT: ["WORK_ORDER_CREATOR", "WORK_ORDER_VERIFIER", "WORK_ORDER_APPROVER"],
  MASTERS: ["MUKTA_ADMIN"],
  BILLS: ["BILL_CREATOR", "BILL_VERIFIER","BILL_APPROVER"],
  PAYMENT: ["BILL_ACCOUNTANT"],
  MUSTERROLLS: ["MUSTER_ROLL_VERIFIER", "MUSTER_ROLL_APPROVER"],
  MEASUREMENT: ["MB_CREATOR", "MB_VERIFIER", "MB_APPROVER", "MB_VIEWER"],
  WORKBENCH : ["MDMS_ADMIN", "MDMS_STATE_ADMIN", "MDMS_CITY_ADMIN", "MDMS_STATE_VIEW_ADMIN", "MDMS_CITY_VIEW_ADMIN"],
  REVISIONOFRATES : ["MDMS_ADMIN", "MDMS_CITY_ADMIN", "MDMS_STATE_VIEW_ADMIN", "MDMS_CITY_VIEW_ADMIN"],
  DSS: ["STADMIN"],
  REVISIONOFRATES : ["REVISION_OF_RATES"],
  ESTIMATETEMPLATE : ["MDMS_ADMIN", "MDMS_STATE_ADMIN"],
};

// Mukta Overrriding the Works Home screen card
const WorksCard = () => {
  if (!Digit.Utils.didEmployeeHasAtleastOneRole(Object.values(ROLES).flatMap((e) => e))) {
    return null;
  }

  const bsEstimate = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate");
  const bsContract = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract");
  const bsRevisedWO = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("revisedWO");
  const bsMuster = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("muster roll");
  const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
  const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
  const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
  const bsMeasurement = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("measurement");
  

  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const requestCriteria = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [bsMuster],
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
          businessService: [bsEstimate],
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

  const requestCriteriaMeasurement = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [bsMeasurement],
          moduleName: "measurement-service",
        },
        moduleSearchCriteria: {
          tenantId,
        },
        limit: 10,
        offset: 0,
      },
    },
    config: {
      enabled: Digit.Utils.didEmployeeHasAtleastOneRole(ROLES.MEASUREMENT),
    },
    changeQueryName: "MeasurementInbox",
  };

  const { isLoading: isLoadingMeasurement, data: dataMeasurement } = Digit.Hooks.useCustomAPIHook(requestCriteriaMeasurement);
  

  const requestCriteriaContract = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [bsContract,bsRevisedWO],
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

  const requestCriteriaBilling = {
    url: "/inbox/v2/_search",
    body: {
      inbox: {
        tenantId,
        processSearchCriteria: {
          businessService: [bsPurchaseBill,bsWageBill,bsSupervisionBill],
          moduleName: "expense",
        },
        moduleSearchCriteria: {
          tenantId,
        },
        limit: 10,
        offset: 0,
      },
    },
    config: {
      enabled: Digit.Utils.didEmployeeHasAtleastOneRole(ROLES.BILLS),
    },
    changeQueryName: "BillInbox",
  };

  const { isLoading: isLoadingBilling, data: dataBilling } = Digit.Hooks.useCustomAPIHook(requestCriteriaBilling);

  let links = [
    {
      label: t("ACTION_TEST_1PROJECT"),
      link: `/${window?.contextPath}/employee/project/search-project`,
      roles: ROLES.PROJECT,
    },
    {
      label: t("ACTION_TEST_2ESTIMATE"),
      link: `/${window?.contextPath}/employee/estimate/inbox`,
      roles: ROLES.ESTIMATE,
      count: isLoadingEstimate ? "-" : dataEstimate?.totalCount,
    },
    {
      label: t("ACTION_TEST_3CONTRACTS"),
      link: `/${window?.contextPath}/employee/contracts/inbox`,
      roles: ROLES.CONTRACT,
      count: isLoadingContract ? "-" : dataContract?.totalCount,
    },
    {
      label: t("ACTION_TEST_5MEASUREMENT"),
      link: `/${window?.contextPath}/employee/measurement/inbox`,
      roles: ROLES.MEASUREMENT,
      count: isLoadingMeasurement? "-" : dataMeasurement?.totalCount,
    },
    {
      label: t("ACTION_TEST_4ATTENDENCEMGMT"),
      link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
      roles: ROLES.MUSTERROLLS,
      count: isLoading ? "-" : data?.totalCount,
    },
    {
      label: t("ACTION_TEST_5BILLS"),
      link: `/${window?.contextPath}/employee/expenditure/billinbox`,
      roles: ROLES.BILLS,
      count: isLoadingBilling ? "-" : dataBilling?.totalCount,
    },
    {
      label: t("EXP_PAYMENT_INS"),
      link: `/${window?.contextPath}/employee/expenditure/search-payment-instruction`,
      roles: ROLES.PAYMENT,
    },
    // We are hiding this button beacuse of latest requirement i.e PFM-4316
    // {
    //   label: t("ACTION_TEST_5PAYMENT"),
    //   link: `/${window?.contextPath}/employee/expenditure/search-bill?status=APPROVED`,
    //   roles: ROLES.PAYMENT,
    // },
    // {
    //   label: t("ACTION_TEST_6DASHBOARD"),
    //   link: `/${window?.contextPath}/employee/dss/dashboard/mukta`,
    //   roles: ROLES.DSS,
    // },
    {
      label: t("ACTION_TEST_7MASTERS"),
      link: `/${window?.contextPath}/employee/masters/search-organization`,
      roles: ROLES.MASTERS,
    },
    {
      label: t("ACTION_TEST_8WAGESEEKER"),
      link: `/${window?.contextPath}/employee/masters/search-wageseeker`,
      roles: ROLES.MASTERS,
    },
    {
      label: t("ACTION_TEST_9PROJECTTYPEMDMS"),
      link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=works&masterName=ProjectType`,
      roles: ROLES.WORKBENCH,
    },
    {
      label: t("ACTION_TEST_10REVISIONOFRATES"),
      link: `/${window?.contextPath}/employee/rateAnalysis/search-sor`,
      roles: ROLES.REVISIONOFRATES,
    },
    {
      label: t("ACTION_TEST_11ESTIMATETEMPLATE"),
      link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS&masterName=EstimateTemplate`,
      roles: ROLES.ESTIMATETEMPLATE,
    }
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
