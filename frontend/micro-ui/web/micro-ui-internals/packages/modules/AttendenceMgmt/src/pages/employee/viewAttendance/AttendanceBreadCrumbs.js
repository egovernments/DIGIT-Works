import { BreadCrumb } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";

const AttendanceBreadCrumbs = ({ location }) => {
  const { t } = useTranslation();

  const search = useLocation().search;
  const fromScreen = new URLSearchParams(search).get("from") || null;
  const crumbs = [
    {
      path: "/works-ui/employee",
      content: t("WORKS_WMS"),
      show: true,
    },
    {
      path: `/${window.contextPath}/employee/attendencemgmt/view-attendance`,
      content: fromScreen ? `${t(fromScreen)} / VIEW ATTENDANCE` : "VIEW ATTENDANCE",
      show: location.pathname.includes("/attendencemgmt/view-attendance") ? true : false,
      isBack: fromScreen && true,
    },
  ];
  return <BreadCrumb crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

export default AttendanceBreadCrumbs;
