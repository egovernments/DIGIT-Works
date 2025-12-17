import { Header, ActionBar, SubmitBar, Menu, Card, Loader, ViewComposer, MultiLink } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";

import { viewRateAnalysisdataconfig } from "../../configs/ViewRateAnalysisConfig";

const ViewRateAnalysis = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  const userInfo = Digit.UserService.getUser();
  const userRoles = userInfo?.info?.roles?.map((roleData) => roleData?.code);
  const [current, setCurrent] = useState(Date.now());
  const queryStrings = Digit.Hooks.useQueryParams();

  let effectiveTime = queryStrings.fromeffective ? (queryStrings.fromeffective < current ? current : queryStrings.fromeffective) : current;
  let dataPaylod = {
    sorDetails: {
      tenantId: tenantId,
      sorCodes: [`${queryStrings?.sorId}`],
      effectiveFrom: effectiveTime,
    },
  };

  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.rateAnalysis.useViewRateAnalysisDetails(tenantId, dataPaylod);

  //

  let config = null;
  useEffect(() => {}, [tenantId, isLoading, applicationDetails, queryStrings]);

  const redirectToCreateBill = (contractType) => {
    if (userRoles.includes("MDMS_STATE_ADMIN") === true) {
      if (contractType === "CREATE_EDIT_RATE_ANALYSIS") {
        history.push(
          `/${window?.contextPath}/employee/rateanalysis/update-rate-analysis?compositionid=${applicationDetails?.rateAnalysisDetail.compositionId}&sorid=${queryStrings?.sorId}`
        );
      }
      if (contractType === "CREATE_RATE_ANALYSIS") {
        history.push(`/${window?.contextPath}/employee/rateanalysis/create-rate-analysis?sorid=${queryStrings?.sorId}`);
      }
    }

    if (userRoles.includes("MDMS_CITY_ADMIN") === true) {
      if (contractType === "REVISE_RATE_ANALYSIS") {
        history.push(`/${window?.contextPath}/employee/rateAnalysis/search-sor`);
      }
    }
  };
  const [displayMenu, setDisplayMenu] = useState(false);
  const actionULB = [];

  if (userRoles.includes("MDMS_STATE_ADMIN") === true) {
    actionULB.push({
      code: "CREATE_RATE_ANALYSIS",
      name: t("CREATE_RATE_ANALYSIS"),
    });

    actionULB.push({
      code: "CREATE_EDIT_RATE_ANALYSIS",
      name: t("CREATE_EDIT_RATE_ANALYSIS"),
    });
  }

  if (userRoles.includes("MDMS_CITY_ADMIN") === true) {
    actionULB.push({
      code: "REVISE_RATE_ANALYSIS",
      name: t("REVISE_RATE_ANALYSIS"),
    });
  }

  function onActionSelect(action) {
    if (action?.code === "CREATE_EDIT_RATE_ANALYSIS") {
      redirectToCreateBill("CREATE_EDIT_RATE_ANALYSIS");
    }
    if (action?.code === "CREATE_RATE_ANALYSIS") {
      redirectToCreateBill("CREATE_RATE_ANALYSIS");
    }
    if (action?.code === "REVISE_RATE_ANALYSIS") {
      redirectToCreateBill("REVISE_RATE_ANALYSIS");
    }
  }

  config = viewRateAnalysisdataconfig(
    applicationDetails?.groupedByHead,
    applicationDetails?.rateAnalysisDetail,
    queryStrings?.sorId,
    t,
    applicationDetails?.infoCard
  );

  if (isLoading) {
    return <Loader />;
  }

  return (
    <React.Fragment>
      {
        <div className={"employee-application-details"} style={{ marginBottom: "24px" }}>
          {
            <Header className="works-header-view" styles={{ marginLeft: "0px", paddingTop: "10px" }}>
              {t("RA_VIEW_RATE_HEADER")}
            </Header>
          }
        </div>
      }
      <ViewComposer data={config} isLoading={false} />
      <ActionBar>
        {displayMenu ? <Menu localeKeyPrefix={"WORKS"} options={actionULB} optionKey={"name"} t={t} onSelect={onActionSelect} /> : null}
        <SubmitBar label={t("ACTIONS")} onSubmit={() => setDisplayMenu(!displayMenu)} />
      </ActionBar>
    </React.Fragment>
  );
};

export default ViewRateAnalysis;
