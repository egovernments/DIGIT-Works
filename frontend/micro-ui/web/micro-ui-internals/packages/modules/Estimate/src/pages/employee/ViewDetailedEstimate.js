import React, { Fragment, useState, useEffect } from "react";
import { Loader, Header, MultiLink, StatusTable, Card, Row, HorizontalNav, ViewDetailsCard, Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { ViewComposer } from "@egovernments/digit-ui-react-components";
import { data } from "../../configs/viewConfig";

const NewView = () => {
  const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
  const { t } = useTranslation();

  const requestCriteria = {
    url: "/estimate/v1/_search",
    params : {tenantId : tenantId , estimateNumber : estimateNumber}
  };

  //fetching estimate data
  const {isLoading: isDetailedEstimateLoading, data: detailedEstimate} = Digit.Hooks.useCustomAPIHook(requestCriteria);


  //fetching project data
  const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
    tenantId,
    searchParams: {
      Projects: [
        {
          tenantId,
          id: detailedEstimate?.estimates[0]?.projectId,
        },
      ],
    },
    config: {
      enabled: !!detailedEstimate?.estimates[0]?.projectId,
    },
  });


  const config = data(project, detailedEstimate?.estimates[0]);

  if (isProjectLoading || isDetailedEstimateLoading) return <Loader />;

  return <ViewComposer data={config} isLoading={false} />;
};

export default NewView;
