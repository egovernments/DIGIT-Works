import { HRIcon, EmployeeModuleCard, WorksMgmtIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const ROLES = {
  MUKTADASHBOARD: ["DASHBOARD_VIEWER"],
  PAYMENT: ["BILL_ACCOUNTANT"],
};

const DSSCard = () => {
  if (!Digit.Utils.didEmployeeHasAtleastOneRole(Object.values(ROLES).flatMap((e) => e))) {
    return null;
  }

  const { t } = useTranslation();

  let isProduction = window.location.href.includes("odisha.gov.in") ? true : false;
  const propsForModuleCard = {
    Icon: <WorksMgmtIcon />,
    moduleName: t("ACTION_TEST_10DSS"),
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
        label: t("DSS_MUKTA_DASHBOARD"),
        link: isProduction ? `/kibana-v8/app/dashboards#/view/fcba7d24-851a-4b11-b792-e790a86bab3d?_g=[…]!t,value:60000),time:(from:now-1y%2Fd,to:now))` : `/kibana-v8/app/dashboards#/view/c8d7e730-2252-11ef-98df-3bbfa2125b2b?_g=(filters:!(),time:(from:now-1y,to:now))`,
        roles: ROLES.MUKTADASHBOARD,
        target: "_blank"
      },
      {
        label: t("DSS_ROLLOUT_DASHBOARD"),
        link: isProduction ? `/kibana-v8/app/dashboards#/view/1cb35e7c-2a53-45e2-a46b-018d17ae4c15?_g=[…]!t,value:60000),time:(from:now-1y%2Fd,to:now))`: `/kibana-v8/app/dashboards#/view/261cb0b0-fbde-11ee-b08d-8b505ceea182?_g=[…]use:!t,value:60000),time:(from:now-1y,to:now))`,
        roles: ROLES.MUKTADASHBOARD,
        target: "_blank"
      },
      {
        label: t("PAYMENT_TRACKER"),
        link: `/${window?.contextPath}/employee/expenditure/payment-tracker`,
        roles: ROLES.PAYMENT,
        target: "_blank"
      },
    ],
  };

  propsForModuleCard.links = propsForModuleCard?.links.filter((link) => (link?.roles && link?.roles?.length > 0 ? Digit.Utils.didEmployeeHasAtleastOneRole(link?.roles) : true));


  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default DSSCard;