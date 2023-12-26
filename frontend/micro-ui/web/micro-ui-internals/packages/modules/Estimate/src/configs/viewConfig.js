import React, { useState, useEffect } from "react";
import { transformEstimateObjects } from "../../util/estimateConversion";

export const data = (projectDetails, estimateDetails, overheadDetails, revisionNumber) => {
  const [viewData, setViewData] = useState({ SOR: [], NONSOR: [] });

  const documents = estimateDetails?.additionalDetails?.documents
    .filter((item) => item.fileStoreId) // Remove items without fileStoreId
    .map((item) => ({
      title: item.fileType || "NA",
      documentType: item.fileType || "NA",
      documentUid: item.documentUid || "NA",
      fileStoreId: item.fileStoreId || "NA",
    }));

    const Projectdocuments = projectDetails?.documents?.filter((item) => item?.fileStoreId)
    .map((item) => ({
      title: `PROJECT_${item.additionalDetails?.fileName}` || "NA",
      ...item
    }));

  const headerLocale = Digit.Utils.locale.getTransformedLocale(estimateDetails?.tenantId);
  const geoLocationValue = estimateDetails?.address?.latitude && estimateDetails?.address?.longitude ? `${estimateDetails?.address?.latitude}, ${estimateDetails?.address?.longitude}` : "NA";

  useEffect(() => {
    const processArrays = () => {
      if (estimateDetails) {
        //Transforming the estimate search response according to formdata 
        setViewData({
          SOR: transformEstimateObjects(estimateDetails, "SOR"),
          NONSOR: transformEstimateObjects(estimateDetails, "NON-SOR"),
        });
      }
    };
    processArrays();
  }, [estimateDetails]);

  const getRedirectionCallback = () => {
    if(revisionNumber)
    window.location.href = `/${window?.contextPath}/employee/estimate/update-revision-detailed-estimate?tenantId=${estimateDetails?.tenantId}&projectNumber=${estimateDetails?.additionalDetails?.projectNumber}&revisionNumber=${estimateDetails?.revisionNumber}&isEditRevisionEstimate=true`
    else
    window.location.href = `/${window?.contextPath}/employee/estimate/update-detailed-estimate?tenantId=${estimateDetails?.tenantId}&projectNumber=${estimateDetails?.additionalDetails?.projectNumber}&estimateNumber=${estimateDetails?.estimateNumber}&isEdit=true`
}

  return {
    cards: [
      {
        sections: [
          {
            type: "DATA",
            values: [
              {
                key: "WORKS_ESTIMATE_TYPE",
                value: revisionNumber ? "REVISED_ESTIMATE" : "ORIGINAL_ESTIMATE",
              },
              {...estimateDetails?.revisionNumber && {
                key: "ESTIMATE_REVISED_ESTIMATE_NO",
                value: estimateDetails?.revisionNumber,
              }},
              {
                key: "ESTIMATE_ESTIMATE_NO",
                value: estimateDetails?.estimateNumber,
              },
              {
                key: "WORKS_PROJECT_ID",
                value: projectDetails?.projectNumber,
              },
              {
                key: "ES_COMMON_PROPOSAL_DATE",
                value: Digit.DateUtils.ConvertEpochToDate(projectDetails?.additionalDetails?.dateOfProposal),
              },
              {
                key: "ES_COMMON_PROJECT_NAME",
                value: projectDetails?.name,
              },
              {
                key: "PROJECTS_DESCRIPTION",
                value: projectDetails?.description,
              },
            ],
          },
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "MB_SORS", inlineStyles: {} },
            component: "EstimateMeasureTableWrapper",
            props: {
              config: {
                key: "SOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: viewData?.SOR,
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, SOR: value })),
            },
          }
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "MB_NONSOR", inlineStyles: {} },
            component: "EstimateMeasureTableWrapper",
            props: {
              config: {
                key: "NONSOR",
                mode: "VIEWES",
              },
              arrayProps: {
                fields: viewData?.NONSOR,
              },
              register: () => {},
              setValue: (key, value) => setViewData((old) => ({ ...old, NONSOR: value })),
            },
          }
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "ES_OTHER_CHARGES", inlineStyles: {} },
            component: "OverheadDetailsTable",
            props: {data : overheadDetails}
          }
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "COMPONENT",
            cardHeader: { value: "", inlineStyles: {} },
            component: "ViewAnalysisStatement",
            props: {formData : {...estimateDetails, SORtable:  estimateDetails ? transformEstimateObjects(estimateDetails, "SOR") : []}}
          },
          {
            type: "COMPONENT",
            cardHeader: { value: "", inlineStyles: {} },
            component: "ViewTotalEstAmount",
            props: {mode: "VIEWES", detail : {...estimateDetails, value:estimateDetails?.additionalDetails?.totalEstimatedAmount} }
          }
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "DOCUMENTS",
            documents: [
              {
                title: "WORKS_RELEVANT_DOCUMENTS",
                BS: "Works",
                values: documents,
              },
            ],
            inlineStyles: {
              marginTop: "1rem",
            },
          }
        ],
      },
      {
        navigationKey: "card1",
        sections: [
          {
            type: "WFHISTORY",
            businessService: "ESTIMATE",
            applicationNo: revisionNumber ? revisionNumber : estimateDetails?.estimateNumber,
            tenantId: estimateDetails?.tenantId,
            timelineStatusPrefix: "WF_ESTIMATE_",
            breakLineRequired: false,
            config : {
              select: (data) => {
                return {...data, timeline: data?.timeline.filter((ob) => ob?.performedAction !== "DRAFT")}
              },
            }
          },
          {
            type: "WFACTIONS",
            forcedActionPrefix: "WF_ESTIMATE_ACTION",
            businessService: "ESTIMATE",
            applicationNo: revisionNumber ? revisionNumber : estimateDetails?.estimateNumber,
            tenantId: estimateDetails?.tenantId,
            applicationDetails: estimateDetails,
            url: "/estimate/v1/_update",
            moduleCode: "Estimate",
            editApplicationNumber: undefined,
            editCallback : getRedirectionCallback
          },
        ],
      },
      {
        navigationKey: "card2",
        sections: [
          {
            type: "DATA",
            sectionHeader: { value: "WORKS_PROJECT_DETAILS", inlineStyles: {marginBottom : "16px", marginTop:"32px", fontSize: "24px"} },
            values: [
              {
                key: "PROJECT_LOR",
                value: projectDetails?.referenceNumber,
              },
              {
                key: "WORKS_PROJECT_TYPE",
                value: projectDetails?.projectType,
              },
              {
                key: "PROJECT_TARGET_DEMOGRAPHY",
                value: projectDetails?.targets,
              },
              {
                key: "PROJECT_ESTIMATED_COST",
                value: projectDetails?.additionalDetails?.estimatedCostInRs,
              },
            ],
          },
          {
            type: "DATA",
            sectionHeader: { value: "WORKS_LOCATION_DETAILS", inlineStyles: {marginBottom : "16px", marginTop:"32px", fontSize: "24px"} },
            values: [
              {
                key: "WORKS_GEO_LOCATION",
                value: geoLocationValue,
              },
              {
                key: "WORKS_CITY",
                value: projectDetails?.address?.city,
              },
              {
                key: "WORKS_WARD",
                value: `${headerLocale}_ADMIN_${projectDetails?.address?.boundary}`,
              },
              {
                key: "WORKS_LOCALITY",
                value: `${headerLocale}_ADMIN_${projectDetails?.additionalDetails?.locality}`,
              },
            ],
          },
          {
            type: "DOCUMENTS",
            documents: [
              {
                title: "WORKS_RELEVANT_DOCUMENTS",
                BS: "Works",
                values: Projectdocuments,
              },
            ],
            inlineStyles: {
              marginTop: "1rem",
            },
            headerStyle: {
              marginTop: "32px",
              marginBottom: "8px"
            }
          },
        ],
      },
    ],
    apiResponse: {},
    additionalDetails: {},
    horizontalNav: {
      showNav: true,
      configNavItems: [
        {
          name: "card2",
          active: true,
          code: "Project Details",
        },
        {
          name: "card1",
          active: true,
          code: "Estimation",
        },
      ],
      activeByDefault: "card1",
    },
  };
};
