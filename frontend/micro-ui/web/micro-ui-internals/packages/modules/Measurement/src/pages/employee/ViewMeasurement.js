import { Header, Card, Loader, ViewComposer } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { data } from "../../configs/ViewMeasurementConfig";

const ViewMeasurement = () => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { workOrderNumber, mbNumber } = Digit.Hooks.useQueryParams();
  const [thumbnails, setThumbnails] = useState("")

  const requestCriteria = {
    url: "/mukta-services/measurement/_search",

    body: {
      contractNumber: workOrderNumber,
      tenantId: tenantId,
      measurementNumber: mbNumber,
    },
    changeQueryName:mbNumber,
  };



  let { isLoading: isMeasurementLoading, data: allData } = Digit.Hooks.useCustomAPIHook(requestCriteria);


  useEffect(() => {
    // if (!isMeasurementLoading) {
    //   allData = null;
    //   thumbnails = "";
    // }
    const fetchData = async () => {
      if (allData?.measurement != undefined || allData?.measurement != null) {
        const fileStoreIds = allData?.measurement?.documents.map((item) => item.fileStore);

        try {
          let thumbnailsData = await Digit.Utils.getThumbnails(fileStoreIds, tenantId);
          setThumbnails(thumbnailsData);
        } catch (error) {
          console.log(error);
        }
      }
    };

    fetchData();
  }, [allData?.measurement, tenantId, isMeasurementLoading]);
  let config = null;

  //Getting project location for Measurement View Page
  let projectLocation = "NA";
  if(allData?.contract){  
    const { additionalDetails: { locality: projectLoc, ward: projectWard }} = allData?.contract;
    const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId());
    const Pward = projectWard ? t(`${headerLocale}_ADMIN_${projectWard}`) : "";
    const city = projectLoc ? t(`${headerLocale}_ADMIN_${projectLoc}`) : "";
    projectLocation = `${Pward ? Pward + ", " : ""}${city}`;
  }

  config = data(allData?.contract, allData?.estimate, allData?.measurement, allData?.allMeasurements, thumbnails, projectLocation , allData?.period, allData?.musterRollNumber);

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
