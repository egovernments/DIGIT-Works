import { Header, Card, Loader, ViewComposer, MultiLink } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { data } from "../../configs/ViewMeasurementConfig";
import { Button } from "@egovernments/digit-ui-components";

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
      key: "View"
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

  const HandleDownloadPdf = () => {
    Digit.Utils.downloadEgovPDF("measurementBook/measurement-book", { contractNumber : workOrderNumber, measurementNumber : mbNumber, tenantId }, `measurement-${mbNumber}.pdf`);
  };

  config = data(allData?.contract, allData?.estimate, allData?.measurement, allData?.allMeasurements, thumbnails, projectLocation , allData?.period, allData?.musterRollNumber, allData?.musterRolls);

  if (isMeasurementLoading && config != null) {
    return <Loader />;
  }
  return (
    <React.Fragment>
      <div className={"employee-application-details"} style={{ marginBottom: "24px",alignItems:"center" }}>
        <Header className="works-header-view" styles={{ marginLeft: "0px", marginBottom:"0px" }}>
          {t("MB_VIEW_MEASUREMENT_BOOK")}
        </Header>
        {/* <MultiLink onHeadClick={() => HandleDownloadPdf()} downloadBtnClassName={"employee-download-btn-className"} label={t("CS_COMMON_DOWNLOAD")} /> */}
        {
                      <Button
                      label={t("CS_COMMON_DOWNLOAD")}
                      onClick={() => HandleDownloadPdf()}
                      className={"employee-download-btn-className"}
                      variation={"teritiary"}
                      type="button"
                      icon={"FileDownload"}
                    />
        }
      </div>
      <ViewComposer data={config} isLoading={false} />
    </React.Fragment>
  );
};

export default ViewMeasurement;
