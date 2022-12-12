import React from "react";
import { useTranslation } from "react-i18next";
import { EmployeeModuleCard, ArrowRightInbox, WSICon } from "@egovernments/digit-ui-react-components";

const MastersCard = () => {
  const { t } = useTranslation();

  const propsForModuleCard = {
    Icon: <WSICon />,
    moduleName: t("MASTERS"),
    kpis: [],
    links: [
      {
        label: t("MASTERS_MASTERS"),
        link: `/${window?.contextPath}/employee/masters/search-organization`,
        roles: [],
      }
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default MastersCard;
