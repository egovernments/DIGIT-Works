import { getThumbnails } from "../../../utils/thumbnail";
import { WageSeekerService } from "../../elements/WageSeeker";

const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
    if(data?.Individual?.length === 0) throw new Error('No data found');
    
    const individual = data.Individual[0]
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const thumbnails = await getThumbnails([individual?.photo], tenantId)
    const socialCategory = individual?.additionalFields?.fields?.find(item => item?.key === "SOCIAL_CATEGORY")
    const adhaar = individual?.identifiers?.find(item => item?.identifierType === 'AADHAAR')

    const headerDetails = {
        title: " ",
        asSectionHeader: true,
        values: [
            { title: "MASTERS_WAGE_SEEKER_ID", value: individual?.individualId || t("NA")},
            { title: "ES_COMMON_AADHAR", value: adhaar ? adhaar?.identifierId : t("NA")},
            { title: "MASTERS_NAME_OF_WAGE_SEEKER", value: individual?.name?.givenName || t("NA")},
            { title: "MASTERS_FATHER_HUSBAND_NAME", value: individual?.fatherName || t("NA")},
            { title: "ES_COMMON_RELATIONSHIP", value: individual?.relationship ? `COMMON_MASTERS_RELATIONSHIP_${individual?.relationship}` : t("NA")},
            { title: "ES_COMMON_BIRTHDATE", value: individual?.dateOfBirth || t("NA")},
            { title: "CORE_COMMON_PROFILE_GENDER", value: individual?.gender ? `COMMON_MASTERS_GENDER_${individual?.gender}` : t("NA")},
            { title: "CORE_COMMON_PROFILE_MOBILE_NUMBER", value: individual?.mobileNumber || t("NA")},
            { title: "MASTERS_SOCIAL_CATEGORY", value: socialCategory ? `COMMON_MASTERS_SOCIAL_${socialCategory?.value}` : t("NA")}
        ],
        additionalDetails: {
          skills: {
            title: "MASTERS_SKILLS",
            skillData: individual?.skills || []
          },
          photo : {
            title: "ES_COMMON_PHOTOGRAPH",
            thumbnailsToShow: thumbnails          
          }
        }
    }
    const locationDetails = {
        title: "ES_COMMON_LOCATION_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "CORE_COMMON_PROFILE_CITY", value: individual?.address?.[0]?.city || t("NA")},
            { title: "COMMON_WARD", value: individual?.address?.[0]?.ward?.code ? `${headerLocale}_ADMIN_${individual?.address?.[0]?.ward?.code }` : t("NA")},
            { title: "COMMON_LOCALITY", value: individual?.address?.[0]?.locality?.code ? `${headerLocale}_ADMIN_${individual?.address?.[0]?.locality?.code}` : t("NA")},
            { title: "ES_COMMON_STREET", value: individual?.address?.[0]?.street || t("NA")},
            { title: "ES_COMMON_DOOR_NO", value: individual?.address?.[0]?.doorNo || t("NA")},
        ]
    }
    const financialDetails = {
        title: "WORKS_FINANCIAL_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "ES_COMMON_ACCOUNT_HOLDER_NAME", value: 'Asha Devi' || t("NA")},
            { title: "MASTERS_ACC_NO", value: '1000023401231' || t("NA")},
            { title: "MASTERS_IFSC", value: 'SBIN0000123' || t("NA")},
            { title: "ES_COMMON_BRANCH", value: 'Block 1, Kormangala, Bangalore' || t("NA")},
            { title: "MASTERS_EFFECTIVE_FROM", value: '01/04/2022' || t("NA")},
            { title: "MASTERS_EFFECTIVE_TO", value: 'NA' || t("NA")},
        ]
    }
    const applicationDetails = { applicationDetails: [headerDetails, locationDetails, financialDetails] };

  return {
    applicationDetails,
    applicationData: individual,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

export const View = {
    fetchWageSeekerDetails: async (t, tenantId, data, searchParams) => {
      try {
          const response = await WageSeekerService.search(tenantId, data, searchParams);
          return transformViewDataToApplicationDetails(t, response, tenantId)
      } catch (error) {
          console.log('error', error);
          throw new Error(error?.response?.data?.Errors[0].message);
      }  
    }
}