const transformViewDataToApplicationDetails = {
    genericPropertyDetails: () => {
      const CommunityOrganisationDetails = {
        title: "MASTERS_COMMUNITY_ORG_DETAILS",
        asSectionHeader: true,
        values: [
          { title: "MASTERS_ORG_ID", value: "DH21M210129" },
          { title: "MASTERS_NAME_OF_ORGN", value: "Maa Bhagwati Organisation" },
          { title: "MASTERS_FORMATION_DATE", value: "28-09-2021" },
          { title: "MASTERS_ORGANISATION_CLASSIFICATION", value: "MSG" },
          { title: "MASTERS_TOTAL_MEMBERS", value: "15" },
          { title: "MASTERS_ORGANISATION_TYPE", value: "Organisation Type" },
        ],
      };
      const LocationDetails = {
        title: "MASTERS_COMMUNITY_ORG_DETAILS",
        asSectionHeader: true,
        values: [
          { title: "MASTERS_LOCALITY", value: "23 College Road" },
          { title: "MASTERS_WARD", value: "Ward 01" },
          { title: "MASTERS_ULB", value: "DHDHC001-Dhenkanal" },
          { title: "MASTERS_DISTRICT", value: "Dhenkanal" },
        ],
      };
      const FinancialDetails = {
        title: "MASTERS_FINANCIAL_DETAILS",
        asSectionHeader: true,
        values: [
          { title: "MASTERS_BANK_ACC_HOLDER_NAME", value: "23 College Road" },
          { title: "MASTERS_BANK_ACCOUNT_TYPE", value: "Current" },
          { title: "MASTERS_ACC_NO", value: "918756789876" },
          { title: "MASTERS_BANK_NAME", value: "State Bank of India" },
          { title: "MASTERS_BANK_BRANCH", value: "22 College Road, Ward 11, Dhenkanal" },
          { title: "MASTERS_IFSC", value: "HJUI009898" },
        ],
      };
      const MemberDetails = {
        title: "MASTERS_MEMBER_DETAILS",
        asSectionHeader: true,
        values: [
          { title: "MASTERS_NAME_OF_CONTACT_PERSON", value: "Asha Devi" },
          { title: "MASTERS_FATHER_GUARDIANS_NAME", value: "Sr. Asha Devi" },
          { title: "MASTERS_GENDER", value: "Female" },
          { title: "MASTERS_PHONE_NUMBER", value: "8978678799" },
          { title: "MASTERS_DESIGNATION", value: "President" },
          { title: "MASTERS_PHOTOGRAPH", value: "URL--" },
        ],
      };
      const applicationDetails = { applicationDetails: [CommunityOrganisationDetails, LocationDetails, FinancialDetails, MemberDetails] };
      return {
        applicationDetails,
        applicationData: { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
      };
    },
  };
  
  //Write Service to fetch records
  export const fetchOrganisationDetails = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
  };
  