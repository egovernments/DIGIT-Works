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

    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default ProjectCard;
