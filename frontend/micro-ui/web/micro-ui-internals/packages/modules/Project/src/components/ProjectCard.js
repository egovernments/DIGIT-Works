import React from "react";
import { useTranslation } from "react-i18next";
import { EmployeeModuleCard, PTIcon } from "@egovernments/digit-ui-react-components";

const ProjectCard = () => {
  const { t } = useTranslation();

  const propsForModuleCard = {
    Icon: <PTIcon />,
    moduleName: t("WORKS_PROJECT"),
    kpis: [],
    links: [
      {
        label: t("WORKS_CREATE_PROJECT"),
        link: `/${window?.contextPath}/employee/project/create-project`,
        roles: [],
      },
      {
        label: t("WORKS_PROJECT_DETAILS"),
        link: `/${window?.contextPath}/employee/project/project-details`,
        roles: [],
      },
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default ProjectCard;
