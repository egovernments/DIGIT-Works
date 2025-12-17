import { getThumbnails } from "../../../utils/thumbnail";
import { CustomService } from "../../elements/CustomService";

const combine = (docs, estimateDocs) => {
  let allDocuments = [];
  for (let i = 0; i < docs?.length; i++) {
    if (docs[i]?.fileStoreId !== undefined || docs[i]?.fileStore !== undefined) {
      allDocuments.push(docs[i]);
    }
  }
  for (let i = 0; i < estimateDocs?.length; i++) {
    if (estimateDocs?.[i]?.fileStoreId !== undefined || estimateDocs?.[i]?.fileStore !== undefined) {
      allDocuments.push(estimateDocs[i]);
    }
  }
  return allDocuments;
};

const transformViewDataToApplicationDetails = async (t, data, workflowDetails, revisedWONumber) => {
  //if revisedWONumber is defined then it's a time extension screen(use TE object here)
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const isTimeExtAlreadyInWorkflow = data.contracts.some(
    (element) =>
      element.businessService === Digit?.Customizations?.["commonUiConfig"]?.businessServiceMap?.revisedWO && element.status === "INWORKFLOW"
  );

  let contract;

  // let contract = data.contracts.filter(element => element.supplementNumber=== null)?.[0]
  if (data.contracts.length === 1) contract = data.contracts[0];
  else {
    contract = data.contracts.filter((element) => element.supplementNumber && (element.status === "ACTIVE" || element.status === "INWORKFLOW"))?.[0];

    contract = { ...contract, wfStatus: "ACCEPTED" };
  }

  if (revisedWONumber) {
    contract = data?.contracts?.filter((row) => row?.supplementNumber === revisedWONumber)?.[0];
  }

  let measurements = data?.measurements;
  const fileStoreIds = measurements[0]?.documents.map((item) => item.fileStore);

  let thumbnails = "";
  try {
    thumbnails = await getThumbnails(
      // ["8f158603-26a7-4c3a-8433-64b9ed20db60", "10ca8e0f-6d2e-4918-b628-b8bda860f061", "611d55d1-9a43-4037-ab9a-fae24466a4a6"],
      fileStoreIds,
      tenantId
    );
  } catch (error) {
    console.log(error);
  }

  let estimateDetails = data?.estimate;

  const contractDetails = {
    title: " ",
    asSectionHeader: false,
    values: [
      { title: t("MB_NUMBER"), value: measurements[0]?.measurementNumber || t("NA") },
      { title: t("MB_WORK_ORDER_NUMBER"), value: contract?.contractNumber || t("NA") },
      { title: t("MB_PROJECT_ID"), value: contract?.additionalDetails?.projectId || t("NA") },
      { title: t("MB_MUSTER_ROLL_ID"), value: t("NA") },
      { title: t("MB_PROJECT_DATE"), value: t("NA") },
      { title: t("MB_PROJECT_NAME"), value: contract?.additionalDetails?.projectName || t("NA") },
      { title: t("MB_PROJECT_DESC"), value: contract?.additionalDetails?.projectDesc || t("NA") },
      { title: t("MB_LOCATION"), value: t("NA") },
      { title: t("MB_MEASUREMENT_PERIOD"), value: t("NA") },
    ],
  };
  if (contract.startDate) {
    contractDetails.values.push({ title: "WORKS_START_DATE", value: Digit.DateUtils.ConvertEpochToDate(contract.startDate) || t("NA") });
  }
  if (contract.endDate) {
    contractDetails.values.push({ title: "WORKS_END_DATE", value: Digit.DateUtils.ConvertEpochToDate(contract.endDate) || t("NA") });
  }
  if (contract.additionalDetails.timeExt && revisedWONumber) {
    contractDetails.values.push({ title: "EXTENSION_REQ", value: contract?.additionalDetails?.timeExt || t("NA") });
  }
  if (contract.additionalDetails.timeExtReason && revisedWONumber) {
    contractDetails.values.push({ title: "EXTENSION_REASON", value: contract?.additionalDetails?.timeExtReason || t("NA") });
  }

  const allDocuments = combine(contract?.documents, contract?.additionalDetails?.estimateDocs);
  let documentDetails = {
    title: "",
    asSectionHeader: true,
    additionalDetails: {
      documents: [
        {
          title: "WORKS_VIEW_RELEVANT_DOCUMENTS",
          BS: "Works",
          values: allDocuments?.map((document) => {
            if (!document.fileStoreId && !document.fileStore) return null;
            if (document?.status !== "INACTIVE") {
              if (document?.fileStoreId) {
                //ESTIMATE FILES
                return {
                  title: document?.fileType === "Others" ? document?.fileName : document?.fileType,
                  documentType: document?.fileName,
                  documentUid: document?.documentUid,
                  fileStoreId: document?.fileStoreId,
                };
              }
              //WO FILES
              return {
                title: document?.documentType === "OTHERS" ? document?.additionalDetails?.otherCategoryName : t(`CONTRACT_${document?.documentType}`),
                documentType: document?.documentType,
                documentUid: document?.fileStore,
                fileStoreId: document?.fileStore,
              };
            }
            return {};
          }),
        },
      ],
    },
  };

  const imageDetails = {
    title: "MB_WORKSITE_PHOTOS",
    asSectionHeader: true,
    additionalDetails: {
      photo: {
        thumbnailsToShow: thumbnails,
      },
    },
  };

  //filter any empty object
  documentDetails.additionalDetails.documents[0].values = documentDetails?.additionalDetails?.documents?.[0]?.values?.filter((value) => {
    if (value?.title) {
      return value;
    }
  });

  const applicationDetails = revisedWONumber
    ? { applicationDetails: [contractDetails] }
    : { applicationDetails: [contractDetails, documentDetails, imageDetails] };

  return {
    applicationDetails,

    applicationData: data ,

    processInstancesDetails: workflowDetails?.ProcessInstances,
    workflowDetails,
    isNoDataFound: data?.contracts?.length === 0 ? true : false,
    additionalDetails: {
      isTimeExtAlreadyInWorkflow,
    },
  };
};

export const View = {
  fetchMeasurementDetails: async (t, tenantId, data, searchParams, revisedWONumber) => {
    try {

      const url = "/mukta-services/measurement/_search"

      const allData = await CustomService.getResponse({
        url, 
        params : {tenantId}, 
        body : {...data} 
      });

      const response = {
        measurements: [allData.measurement],
        contracts: [allData.contract],
        estimate: [allData.estimate],
      };

      return transformViewDataToApplicationDetails(t, response, undefined, revisedWONumber);
    } catch (error) {
      throw new Error(error?.response?.data?.Errors[0].message);
    }
  },
};
