const transformViewDataToApplicationDetails = {
  genericPropertyDetails: () => {
    const DepartmentDetails = {
      title: " ",
      asSectionHeader: false,
      values: [
        { title: "PROJECT_OWNING_DEPT", value: "Housing and Urban Development Department" },
        { title: "PROJECT_EXECUTING_DEPT", value: "Housing and Urban Development Department" },
        { title: "PROJECT_BENEFICIARY", value: "Local Slums" },
        { title: "PROJECT_LETTER_REF_REQ_NO", value: "201/A  - 19 December 2021" },
        { title: "PROJECT_ESTIMATED_COST", value: "5,00,000" },
      ],
    };
    const WorkTypeDetails = {
      title: "PROJECT_WORK_TYPE_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "PROJECT_TYPE_OF_WORK", value: "Rain Water Harvesting" },
        { title: "PROJECT_SUB_TYPE_OF_WORK", value: "NA" },
        { title: "PROJECT_NATURE_OF_WORK", value: "Capital Works" },
        { title: "PROJECT_LETTER_REF_REQ_NO", value: "201/A  - 19 December 2021" },
        { title: "PROJECT_RECOMMENDED_MODE_OF_ENTRUSTMENT", value: "Direct Assignment" },
      ],
    };
    const LocationDetails = {
      title: "PROJECT_LOCATION_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "PROJECT_LOCALITY", value: "Vivekananda Nagar" },
        { title: "PROJECT_WARD", value: "1" },
        { title: "PROJECT_ULB", value: "Jatni Municipality" },
        { title: "PROJECT_GEO_LOCATION", value: "82.1837913, 19.138134" },
      ],
    };
    const Documents = {
      title: "PROJECT_DOCUMENTS",
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
  