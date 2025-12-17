import { HRIcon, EmployeeModuleCard, WorksMgmtIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const ROLES = {
  TYPE: ["MDMS_STATE_ADMIN","MDMS_STATE_VIEW_ADMIN"],
  SUBTYPE: ["MDMS_STATE_ADMIN","MDMS_STATE_VIEW_ADMIN"],
  VARIENT: ["MDMS_STATE_ADMIN","MDMS_STATE_VIEW_ADMIN"],
  SOR: ["MDMS_STATE_ADMIN","MDMS_STATE_VIEW_ADMIN"],
  OVERHEAD: ["MDMS_STATE_ADMIN","MDMS_STATE_VIEW_ADMIN"],
  RATE: ["MDMS_CITY_ADMIN","MDMS_STATE_VIEW_ADMIN","MDMS_CITY_VIEW_ADMIN"],
  RATEANALYSIS: ["MDMS_STATE_ADMIN","MDMS_CITY_VIEW_ADMIN"],
};

const WorkbenchCard = () => {
  if (!Digit.Utils.didEmployeeHasAtleastOneRole(Object.values(ROLES).flatMap((e) => e))) {
    return null;
  }

  const { t } = useTranslation();

  const propsForModuleCard = {
    Icon: <WorksMgmtIcon />,
    moduleName: t("ACTION_TEST_WORKBENCH_CARD"),
    kpis: [
      // {
      //     count:  isLoading ? "-" : data?.EmployeCount?.totalEmployee,
      //     label: t("TOTAL_EMPLOYEES"),
      //     link: `/${window?.contextPath}/employee/hrms/inbox`
      // },
      // {
      //   count:  isLoading ? "-" : data?.EmployeCount?.activeEmployee,
      //     label: t("ACTIVE_EMPLOYEES"),
      //     link: `/${window?.contextPath}/employee/hrms/inbox`
      // }
    ],
    links: [
      {
        label: t("MDMS_SOR_TYPE"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=Type`,
        roles: ROLES.TYPE
      },
      {
        label: t("MDMS_SOR_SUBTYPE"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=SubType`,
        roles: ROLES.SUBTYPE
      },
      {
        label: t("MDMS_SOR_VARIENT"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=Variant`,
        roles: ROLES.VARIENT
      },
      {
        label: t("MDMS_SOR_SOR"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=SOR`,
        roles: ROLES.SOR
      },
      {
        label: t("MDMS_SOR_OVERHEAD"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=Overhead`,
        roles: ROLES.OVERHEAD
      },
      {
        label: t("MDMS_SOR_RATES"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=Rates`,
        roles: ROLES.RATE
      },
      {
        label: t("MDMS_SOR_RATES_AANALYSIS"),
        link: `/workbench-ui/employee/workbench/mdms-search-v2?moduleName=WORKS-SOR&masterName=Composition`,
        roles: ROLES.RATEANALYSIS
      }
    ],
  };

  propsForModuleCard.links = propsForModuleCard?.links.filter((link) => (link?.roles && link?.roles?.length > 0 ? Digit.Utils.didEmployeeHasAtleastOneRole(link?.roles) : true));


  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default WorkbenchCard;