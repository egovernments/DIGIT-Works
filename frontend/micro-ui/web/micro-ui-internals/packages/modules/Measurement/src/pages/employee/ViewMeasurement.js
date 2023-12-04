import { Header, Card, Loader, ViewComposer } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { data } from "../../configs/ViewMeasurementConfig";

const ViewMeasurement = () => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { workOrderNumber, mbNumber } = Digit.Hooks.useQueryParams();

  const requestCriteria = {
    url: "/mukta-services/measurement/_search",

    body: {
      contractNumber: workOrderNumber,
      tenantId: tenantId,
      measurementNumber: mbNumber,
    },
  };



  let { isLoading: isMeasurementLoading, data: allData } = Digit.Hooks.useCustomAPIHook(requestCriteria);


  let thumbnails = "";
  useEffect(() => {
    if (!isMeasurementLoading) {
      allData = null;
      thumbnails = "";
    }
    const fetchData = () => {
      if (allData?.measurement != undefined || allData?.measurement != null) {
        const fileStoreIds = allData?.measurement?.documents.map((item) => item.fileStore);

        try {
          thumbnails = Digit.Utils.getThumbnails(fileStoreIds, tenantId);
        } catch (error) {
          console.log(error);
        }
      }
    };

    fetchData();
  }, [allData, tenantId, isMeasurementLoading]);
  let config = null;

  config = data(allData?.contract, allData?.estimate, allData?.measurement, allData?.allMeasurements, thumbnails);

  if (isMeasurementLoading && config != null) {
    return <Loader />;
  }
  return (
    <React.Fragment>
      <Header className="works-header-view">{t("MB_VIEW_MEASUREMENT_BOOK")}</Header>
      <ViewComposer data={config} isLoading={false} />
    </React.Fragment>
  );
};

export default ViewMeasurement;
