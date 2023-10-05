import React, { Fragment, useState, useEffect } from "react";
import { Loader, Header, MultiLink, StatusTable, Card, Row, HorizontalNav, ViewDetailsCard, Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { ViewComposer } from "@egovernments/digit-ui-react-components";
import { data } from "../../configs/viewConfig";

const ViewDetailedEstimate = () => {
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

  const overheads = detailedEstimate?.estimates[0]?.estimateDetails?.filter((row) => row?.category?.includes("OVERHEAD") && row?.isActive);
  const tableHeaderOverheads = [t("WORKS_SNO"), t("WORKS_OVERHEAD"), t("WORKS_PERCENTAGE"), t("WORKS_AMOUNT")];
  const tableRowsOverheads = overheads?.map((row, index) => {
    return [
      index + 1,
      t(`ES_COMMON_OVERHEADS_${row?.name}`),
      row?.additionalDetails?.row?.name?.type?.includes("percent") ? `${row?.additionalDetails?.row?.name?.value}%` : t("WORKS_LUMPSUM"),
      Digit.Utils.dss.formatterWithoutRound(row?.amountDetail?.[0]?.amount?.toFixed(2), "number"),
    ];
  });
  const totalAmountOverheads = overheads?.reduce((acc, row) => row?.amountDetail?.[0]?.amount + acc, 0);
  tableRowsOverheads?.push(["", "", t("RT_TOTAL"), Digit.Utils.dss.formatterWithoutRound(totalAmountOverheads, "number")]);
  const overheadItems = {
    headers: tableHeaderOverheads,
    tableRows: tableRowsOverheads,
    tableStyles: {
      rowStyle: {},
      cellStyle: [{}, { width: "50vw", whiteSpace: "break-spaces", wordBreak: "break-all" }, { textAlign: "left" }, { textAlign: "right" }],
    },
  };

  const config = data(project, detailedEstimate?.estimates[0], overheadItems);

  if (isProjectLoading || isDetailedEstimateLoading) return <Loader />;

  return (
    <div>
      <ViewComposer data={config} isLoading={false} />
    </div>
  );
};

export default ViewDetailedEstimate;
