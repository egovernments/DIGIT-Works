import React from "react";
import { useTranslation } from "react-i18next";
import { EmployeeModuleCard, OBPSIconSolidBg } from "@egovernments/digit-ui-react-components";

const ProjectCard = () => {
  const { t } = useTranslation();

  const propsForModuleCard = {
    Icon: <OBPSIconSolidBg />,
    moduleName: t("WORKS_CHECKLIST"),
    kpis: [],
    links: [
      {
        label: t("WORKS_KICKOFF_CHECKLIST"),
        link: `/${window?.contextPath}/employee/works/checklist`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
        count: 21,
      },
      {
        label: t("WORKS_CHECKLIST_INBOX"),
        link: `/${window?.contextPath}/employee/works/checklistinbox`,
        roles: ["WS_CEMP", "WS_APPROVER", "WS_FIELD_INSPECTOR", "WS_DOC_VERIFIER", "WS_CLERK"],
      },
      {
        label: t("WORKS_PROJECT_CLOSURE"),
        link: `/${window?.contextPath}/employee/works/projectclosure`,
        roles: [],
      },
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default ProjectCard;
