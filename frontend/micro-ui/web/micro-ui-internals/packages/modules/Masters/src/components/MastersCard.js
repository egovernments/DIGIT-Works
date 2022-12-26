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
      },
      {
        label: t("COMMON_REGISTER_WAGESEEKER"),
        link: `/${window.contextPath}/employee/masters/wage-seeker-registration`,
        roles: [],
      },
      {
        label: t("COMMON_VIEW_COMMUNITY_ORG"),
        link: `/${window?.contextPath}/employee/masters/view-organization`,
        roles: [],
      },
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default MastersCard;
