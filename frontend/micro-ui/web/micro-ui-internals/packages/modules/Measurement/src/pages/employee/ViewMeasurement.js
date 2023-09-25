import { Header, Card, Loader, Button, WorkflowActions } from "@egovernments/digit-ui-react-components";
import { transformEstimateData } from "../../utils/transformEstimateData";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import ApplicationDetails from "../../../../templates/ApplicationDetails";
import MeasurementHistory from "../../components/MBHistoryTable";
import MeasureTable from "../../components/MeasureTable";

const ViewMeasurement = () => {
  const { t } = useTranslation();
  const history = useHistory();
  const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("measurement");
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { workOrderNumber, mbNumber } = Digit.Hooks.useQueryParams();
  const [loading, setLoading] = useState(true); 
  const [sorCategoryArray, setSorCategoryArray] = useState([]);
  const [nonSorCategoryArray, setNonSorCategoryArray] = useState([]);

  const pagination = {
    pagination: {
      limit: 10,
      offSet: 0,
      sortBy: "createdTime",
      order: "DESC",
    },
  };

  const criteria = {
    criteria: {
      tenantId: tenantId,
      referenceId: [workOrderNumber],
      measurementNumber: mbNumber,
    },
    ...pagination,
  };

  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.measurement.useViewMeasurement(tenantId, criteria);

  const projectDetails = { applicationDetails: [applicationDetails?.applicationDetails?.applicationDetails[0]] };
  const imageDetails = { applicationDetails: [applicationDetails?.applicationDetails?.applicationDetails[2]] };


  const measures = applicationDetails?.applicationData?.measurements[0].measures;
  console.log(measures, "measure");
  const data = applicationDetails?.applicationData;

  const estimateDetails = data?.estimate[0]?.estimateDetails || [];

  useEffect(() => {
    const processArrays = () => {
      if (data) {
        const sorData = transformEstimateData(estimateDetails, data?.contracts[0], "SOR", measures);
        const nonSorData = transformEstimateData(estimateDetails, data?.contracts[0], "NON-SOR", measures);


  const measures = applicationDetails?.applicationData?.measurements[0].measures

        setSorCategoryArray(sorData);
        setNonSorCategoryArray(nonSorData);
        setLoading(false);
      }
    };

    processArrays();
  }, [data]);


  const tableData = {
    data: {
      SOR: sorCategoryArray,
      NONSOR: nonSorCategoryArray,
    },
    config: {
      key: "SOR",
    },
  };  

  if (isLoading) {
    return <Loader />;
  }
  if (loading || sorCategoryArray.length === 0 || nonSorCategoryArray.length === 0) {
    return <Loader />;
  }

  return (
    <React.Fragment>
      <Header className="works-header-view">{t("MB_VIEW_MEASUREMENT_BOOK")}</Header>
      <ApplicationDetails
        applicationDetails={projectDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="measurements"
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={false}
      />

      <MeasurementHistory contractNumber={workOrderNumber} measurementNumber={mbNumber} />
      <MeasureTable {...tableData} isView={true} measureData={measures} />
      <MeasureTable
        {...tableData}
        config={{
          key: "NONSOR",
        }}
        isView={true}
        measureData={measures}
      />

      <ApplicationDetails
        applicationDetails={imageDetails}
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData}
        moduleCode="measurements"
        workflowDetails={applicationDetails?.workflowDetails}
        mutate={() => {}}
        tenantId={tenantId}
        showTimeLine={false}
      />
      <ApplicationDetails
        isLoading={isLoading}
        applicationData={applicationDetails?.applicationData?.measurements}
        moduleCode="measurements"
        timelineStatusPrefix={`WF_${businessService}_`}
        businessService={businessService}
        tenantId={tenantId}
        showTimeLine={true}
        applicationNo={mbNumber}
      />
      <WorkflowActions
        forcedActionPrefix={`WF_${businessService}_ACTION`}
        businessService={businessService}
        applicationNo={mbNumber}
        tenantId={tenantId}
        applicationDetails={applicationDetails?.applicationData?.measurements}
        url={Digit.Utils.Urls.measurement.update}
        // setStateChanged={setStateChanged}
        moduleCode="measurements"
        // editApplicationNumber={editApplicationNumber}
      />
    </React.Fragment>
  );
};

export default ViewMeasurement;
