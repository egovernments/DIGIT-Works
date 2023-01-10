const transformViewDataToApplicationDetails = {
    genericPropertyDetails: () => {
      const FinancialDetails = {
        title: " ",
        asSectionHeader: false,
        values: [
          { title: "WORKS_FUND", value: "Housing and Urban Development Department" },
          { title: "WORKS_FUNCTION", value: "Housing and Urban Development Department" },
          { title: "WORKS_BUDGET_HEAD", value: "Local Slums" },
          { title: "WORKS_SCHEME", value: "201/A  - 19 December 2021" },
          { title: "WORKS_SUB_SCHEME", value: "5,00,000" },
        ],
      };
      const applicationDetails_FinancialDetails = { applicationDetails: [FinancialDetails] };
      return {
        applicationDetails_FinancialDetails,
        applicationData_FinancialDetails : { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
      };
    },
  };
  
  //Write Service to fetch records
  export const getFinancialDetails = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
  };
  