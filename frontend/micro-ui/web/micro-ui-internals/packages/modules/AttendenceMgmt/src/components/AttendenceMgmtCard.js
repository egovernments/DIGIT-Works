import { EmployeeModuleCard, BioMetricIcon} from "@egovernments/digit-ui-react-components";
import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";

const AttendenceMgmtCard = () => {
    const { t } = useTranslation();
    const tenantId = Digit.ULBService.getCurrentTenantId()

    const requestCriteria = {
        url:'/inbox/v2/_search',
        body: {
            inbox: {
                tenantId,
                processSearchCriteria: {
                    businessService: [
                        "muster-roll-approval"
                    ],
                    moduleName: "muster-roll-service"
                },
                moduleSearchCriteria: {
                    tenantId
                },
                limit: 10,
                offset: 0
            }
        }
    };
    const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);

    const propsForModuleCard = {
        Icon: <BioMetricIcon fill="white"/>,
        moduleName: t("ACTION_TEST_ATTENDENCEMGMT"),
        kpis: [
            {
                count: isLoading ? "-" : data?.totalCount,
                label: t("INBOX"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
            }
        ],
        links: [
            {
                label: t("INBOX"),
                link: `/${window?.contextPath}/employee/attendencemgmt/inbox`,
                roles: ["EST_CREATOR", "EST_CHECKER", "EST_TECH_SANC", "EST_FIN_SANC", "EMPLOYEE"],
                count: isLoading ? "-" : data?.totalCount,
            },
            {
                label: t("CS_INBOX_SEARCH"),
                link: `/${window?.contextPath}/employee/attendencemgmt/search-attendance`,
                roles: [],
                count: 0,
            }
        ],
    };
    return <EmployeeModuleCard {...propsForModuleCard} />;
};

export default AttendenceMgmtCard;
