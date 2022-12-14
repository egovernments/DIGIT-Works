import React from "react";
import { useTranslation } from "react-i18next";
import { EmployeeModuleCard, PTIcon } from "@egovernments/digit-ui-react-components";

const MastersCard = () => {
  const { t } = useTranslation();

  const propsForModuleCard = {
    Icon: <PTIcon />,
    moduleName: t("ACTION_TEST_MASTERS"),
    kpis: [],
    links: [
      {
        label: t("ACTION_TEST_MASTERS"),
        link: `/${window?.contextPath}/employee/masters/search-organization`,
        roles: [],
      }
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default MastersCard;
