export const data = (projectDetails, estimateDetails) => {
  const documents = estimateDetails?.additionalDetails?.documents
    .filter((item) => item.fileStoreId) // Remove items without fileStoreId
    .map((item) => ({
      title: item.fileType || "NA",
      documentType: item.fileType || "NA",
      documentUid: item.documentUid || "NA",
      fileStoreId: item.fileStoreId || "NA",
    }));
    const headerLocale = Digit.Utils.locale.getTransformedLocale("pg.citya");
    const geoLocationValue = (estimateDetails?.address?.latitude && estimateDetails?.address?.longitude) ? `${latitude}, ${longitude}` : 'NA';

  return {
    cards: [
      {
        sections: [
          {
            type: "DATA",
            values: [
              {
                key: "WORKS_ESTIMATE_TYPE",
                value: "Original Estimate",
              },
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
          // {
          //     type : "COMPONENT",
          //     component : "MeasureTable"
          // },
          {
            type: "DATA",
            sectionHeader: { value: "Section 1", inlineStyles: {} },
            cardHeader: { value: "SOR", inlineStyles: {} },
            values: [
              {
                key: "key 1",
                value: "value 1",
              },
              {
                key: "key 2",
                value: "value 2",
              },
              {
                key: "key 3",
                value: "value 3",
              },
            ],
          },
          {
            type: "DATA",
            sectionHeader: { value: "Section 2", inlineStyles: { marginTop: "2rem" } },
            values: [
              {
                key: "key 1",
                value: "value 1",
              },
              {
                key: "key 2",
                value: "value 2",
              },
              {
                key: "key 3",
                value: "value 3",
              },
            ],
          },
          {
            type: "DOCUMENTS",
            documents: [
              {
                title: "WORKS_RELEVANT_DOCUMENTS",
                BS: "Works",
                values: documents
              },
            ],
            inlineStyles: {
              marginTop: "1rem",
            },
          },
          {
            type: "WFHISTORY",
            businessService: "ESTIMATE",
            applicationNo: estimateDetails?.estimateNumber,
            tenantId: estimateDetails?.tenantId,
            timelineStatusPrefix: "TEST",
          },
          {
            type: "WFACTIONS",
            forcedActionPrefix: "TEST",
            businessService: "ESTIMATE",
            applicationNo: estimateDetails?.estimateNumber,
            tenantId: estimateDetails?.tenantId,
            applicationDetails: estimateDetails,
            url: "/estimate/v1/_update",
            moduleCode: "Estimate",
            editApplicationNumber: undefined,
          },
        ],
      },
      {
        navigationKey: "card2",
        sections: [
          {
            type: "DATA",
            sectionHeader: { value: "WORKS_PROJECT_DETAILS", inlineStyles: {} },
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
            sectionHeader: { value: "WORKS_LOCATION_DETAILS", inlineStyles: {} },
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
                value: `${headerLocale}_ADMIN_${projectDetails?.address?.boundary}`
              },
              {
                key: "WORKS_LOCALITY",
                value: `${headerLocale}_ADMIN_${projectDetails?.additionalDetails?.locality}`,
              },
            ],
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
