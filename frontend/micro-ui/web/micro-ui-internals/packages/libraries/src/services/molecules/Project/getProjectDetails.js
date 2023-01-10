const transformViewDataToApplicationDetails = {
  genericPropertyDetails: () => {
    const DepartmentDetails = {
      title: " ",
      asSectionHeader: false,
      values: [
        { title: "PROJECT_OWNING_DEPT", value: "Housing and Urban Development Department" },
        { title: "WORKS_EXECUTING_DEPT", value: "Housing and Urban Development Department" },
        { title: "WORKS_BENEFICIERY", value: "Local Slums" },
        { title: "WORKS_LOR", value: "201/A  - 19 December 2021" },
        { title: "PROJECT_ESTIMATED_COST", value: "5,00,000" },
      ],
    };
    const WorkTypeDetails = {
      title: "PROJECT_WORK_TYPE_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "WORKS_WORK_TYPE", value: "Rain Water Harvesting" },
        { title: "WORKS_SUB_TYPE_WORK", value: "NA" },
        { title: "WORKS_WORK_NATURE", value: "Capital Works" },
        { title: "WORKS_MODE_OF_INS", value: "Direct Assignment" },
      ],
    };
    const LocationDetails = {
      title: "PROJECT_LOCATION_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "WORKS_LOCALITY", value: "Vivekananda Nagar" },
        { title: "WORKS_WARD", value: "1" },
        { title: "PDF_STATIC_LABEL_ESTIMATE_ULB", value: "Jatni Municipality" },
        { title: "WORKS_GEO_LOCATION", value: "82.1837913, 19.138134" },
      ],
    };
    const Documents = {
      title: "WORKS_DOCUMENTS",
      asSectionHeader: true,
      additionalDetails : {
          documentsWithUrl : [
              {
                  title : "",
                  values : [
                      {
                          url : "",
                          title : "Document 1",
                          documentType : "pdf",
                      },
                      {
                          url : "",
                          title : "Document 2",
                          documentType : "pdf",
                      },
                      {
                          url : "",
                          title : "Document 3",
                          documentType : "pdf",
                      },
                      {
                        url : "",
                        title : "Document 3",
                        documentType : "pdf",
                    }
                  ]
              }
          ],
      }
    };
      const applicationDetails_ProjectDetails = { applicationDetails: [DepartmentDetails, WorkTypeDetails, LocationDetails, Documents] };
      return {
        applicationDetails_ProjectDetails,
        applicationData_ProjectDetails : { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
      };
    },
  };
  
  //Write Service to fetch records
  export const getProjectDetails = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
  };
  