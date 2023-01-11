import { EmployeeModuleCard, CaseIcon } from "@egovernments/digit-ui-react-components";
import React,{useState} from "react";
import { useTranslation } from "react-i18next";

const EstimateCard = () => {
  // if (!Digit.Utils.wsAccess()) {
  //   return null;
  // }
  const { t } = useTranslation();

  // let links = [
  //   {
  //     label: t("WS_APPLY_NEW_CONNECTION_HOME_CARD_LABEL"),
  //     link: `/${window?.contextPath}/employee/ws/create-application`,
  //     roles: ["WS_CEMP", "SW_CEMP"],
  //   },
  // ];

  const tenantId = Digit.ULBService.getCurrentTenantId();

  const [payload, setPayload] = useState({
    offset: 0,
    limit: 10,
    sortBy: "department",
    sortOrder: "DESC",
  });

  const { isFetching, isLoading, data, ...rest } = Digit.Hooks.works.useInbox({
    tenantId,
    _filters: payload,
    config: { cacheTime: 0 },
  });

  
  // links = links.filter((link) => (link.roles ? checkForEmployee(link.roles) : true));

  const propsForModuleCard = {
    Icon: <CaseIcon />,
    moduleName: t("WORKS_ESTIMATES"),
    kpis: [
      {
        //Pass Count Value from Inbox API here
        count: isLoading ? "-" : data?.totalCount,
        label: t("WORKS_INBOX"),
        link: `/${window?.contextPath}/employee/works/inbox`,
      }
    ],
    links: [
      {
        label: t("ACTION_TEST_ESTIMATE_INBOX"),
        link: `/${window?.contextPath}/employee/works/inbox`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("ACTION_TEST_SEARCH_ESTIMATE"),
        link: `/${window?.contextPath}/employee/works/search-Estimate`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("ACTION_TEST_CREATE_ESTIMATE"),
        link: `/${window?.contextPath}/employee/works/create-estimate`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
      {
        label: t("ACTION_TEST_CREATE_ESTIMATE"),
        link: `/${window?.contextPath}/employee/estimate/create-estimate`,
        roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
      },
    ],
  };
  return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default EstimateCard;
