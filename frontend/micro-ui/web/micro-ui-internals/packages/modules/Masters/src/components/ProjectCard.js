import React from "react";
import { useTranslation } from "react-i18next";
import { EmployeeModuleCard, ArrowRightInbox, WSICon } from "@egovernments/digit-ui-react-components";

const ProjectCard = () => {
  const { t } = useTranslation();

  const propsForModuleCard = {
    Icon: <WSICon />,
    moduleName: t("ACTION_TEST_PROJECT"),
    kpis: [],
    links: [
      {
        label: t("WORKS_KICKOFF_CHECKLIST"),
        link: `/${window?.contextPath}/employee/works/checklist`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
        count: 21,
      }
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default ProjectCard;
