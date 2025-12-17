import { getThumbnails } from "../../../utils/thumbnail";
import { BankAccountService } from "../../elements/BankAccount";
import { WageSeekerService } from "../../elements/WageSeeker";

const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
    if(data?.Individual?.length === 0) throw new Error('No data found');
    
    const individual = data?.Individual?.[0]
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const bankDetailPayload = { bankAccountDetails: { tenantId, serviceCode: "IND", referenceId: [individual?.id] } }
    const bankDetails = await BankAccountService.search(bankDetailPayload, {});
    const bankAccounts = bankDetails?.bankAccounts?.[0]?.bankAccountDetails

    let thumbnails = ''
    try {
      thumbnails = individual?.photo && await getThumbnails([individual?.photo], tenantId)
    } catch (error) {}
    
    const socialCategory = (individual?.additionalFields?.fields && Array.isArray(individual?.additionalFields?.fields)) ? individual?.additionalFields?.fields?.find(item => item?.key === "SOCIAL_CATEGORY") : undefined;
    const adhaar = (individual?.identifiers && Array.isArray(individual.identifiers)) ? individual?.identifiers?.find(item => item?.identifierType === 'AADHAAR') : undefined;

    const headerDetails = {
        title: " ",
        asSectionHeader: true,
        values: [
            { title: "MASTERS_WAGE_SEEKER_ID", value: individual?.individualId || t("NA")},
            { title: "ES_COMMON_AADHAR", value: adhaar ? t(adhaar?.identifierId) : t("NA")},
            { title: "MASTERS_NAME_OF_WAGE_SEEKER", value: individual?.name?.givenName || t("NA")},
            { title: "MASTERS_FATHER_HUSBAND_NAME", value: individual?.fatherName || t("NA")},
            { title: "ES_COMMON_RELATIONSHIP", value: individual?.relationship ? (individual?.relationship?.includes("UNDISCLOSED")? t(individual?.relationship): `COMMON_MASTERS_RELATIONSHIP_${individual?.relationship}`) : t("NA")},
            { title: "ES_COMMON_BIRTHDATE", value: individual?.dateOfBirth || t("NA")},
            { title: "CORE_COMMON_PROFILE_GENDER", value: individual?.gender ?(individual?.gender?.includes("UNDISCLOSED") ? t(individual?.gender) : `COMMON_MASTERS_GENDER_${individual?.gender}`) : t("NA")},
            { title: "CORE_COMMON_PROFILE_MOBILE_NUMBER", value: individual?.mobileNumber || t("NA")},
            { title: "MASTERS_SOCIAL_CATEGORY", value: socialCategory ? (socialCategory?.value?.includes("UNDISCLOSED")? t(socialCategory?.value): `COMMON_MASTERS_SOCIAL_${socialCategory?.value}`) : t("CS_COMMON_UNDISCLOSED")}
        ],
        additionalDetails: {
          skills: {
            title: "MASTERS_SKILLS",
            skillData: individual?.skills || []
          },
          photo : {
            title: "ES_COMMON_PHOTOGRAPH",
            thumbnailsToShow: thumbnails && individual?.additionalFields?.isPhotoMasked == true ? '' : thumbnails,
            isMasked : thumbnails && individual?.additionalFields?.isPhotoMasked == true ? "CS_COMMON_UNDISCLOSED" : false         
          }
        }
    }
    const locationDetails = {
        title: "ES_COMMON_LOCATION_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "CORE_COMMON_PROFILE_CITY", value: individual?.address?.[0]?.tenantId ? (individual?.address?.[0]?.city?.includes("*") ? getAddressMasked(t(Digit.Utils.locale.getCityLocale(individual?.address?.[0]?.tenantId))) : Digit.Utils.locale.getCityLocale(individual?.address?.[0]?.tenantId)) : t("NA")},
            { title: "COMMON_WARD", value: individual?.address?.[0]?.ward?.code ? (individual?.address?.[0]?.ward?.additionalDetails?.isMasked ? getAddressMasked(t(Digit.Utils.locale.getMohallaLocale(individual?.address?.[0]?.ward?.code, tenantId))) : Digit.Utils.locale.getMohallaLocale(individual?.address?.[0]?.ward?.code, tenantId)) : t("NA")},
            { title: "COMMON_LOCALITY", value: individual?.address?.[0]?.locality?.code ? (individual?.address?.[0]?.locality?.additionalDetails?.isMasked ? getAddressMasked(t(Digit.Utils.locale.getMohallaLocale(individual?.address?.[0]?.locality?.code, tenantId))) : Digit.Utils.locale.getMohallaLocale(individual?.address?.[0]?.locality?.code, tenantId)) : t("NA")},
            { title: "ES_COMMON_STREET", value: individual?.address?.[0]?.street || t("NA")},
            { title: "ES_COMMON_DOOR_NO", value: individual?.address?.[0]?.doorNo || t("NA")},
        ]
    }

    let financialDetails = []
    bankAccounts?.forEach((item, index) => {
      let bankDetails = {}
      bankDetails.title = index === 0 ?  "WORKS_FINANCIAL_DETAILS" : " "
      bankDetails.asSectionHeader = true
      bankDetails.values = [
        { title: "ES_COMMON_ACCOUNT_HOLDER_NAME", value: item?.accountHolderName || t("NA")},
        { title: "MASTERS_ACC_NO", value: item?.accountNumber || t("NA")},
        { title: "MASTERS_IFSC", value: item?.bankBranchIdentifier?.code || t("NA")},
        { title: "ES_COMMON_BRANCH", value: item?.bankBranchIdentifier?.additionalDetails?.ifsccode || t("NA")},
        { title: "MASTERS_EFFECTIVE_FROM", value: Digit.DateUtils.ConvertTimestampToDate(item?.auditDetails?.createdTime, 'dd/MM/yyyy') || t("NA")},
        { title: "MASTERS_EFFECTIVE_TO", value: item?.isActive && item?.isPrimary ? t("NA") : Digit.DateUtils.ConvertTimestampToDate(item?.auditDetails?.lastModifiedTime, 'dd/MM/yyyy')}
      ]
      financialDetails.push(bankDetails)
    })

    const applicationDetails = { applicationDetails: [headerDetails, locationDetails, ...financialDetails] };

  return {
    applicationDetails,
    applicationData: individual,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

const getAddressMasked = (value) => {
  return value.replace(/.(?=.{1,}$)/g, '*');

}

const fetchBankDetails = async (data, tenantId) => {
  if(data?.Individual?.length === 0) throw new Error('No data found');

  const individual = data.Individual[0]
  const bankDetailPayload = { bankAccountDetails: { tenantId, serviceCode: "IND", referenceId: [individual?.id] } }
  const bankDetails = await BankAccountService.search(bankDetailPayload, {});
  
  return {
    individual,
    bankDetails: bankDetails?.bankAccounts
  }
}

export const View = {
    fetchWageSeekerDetails: async (t, tenantId, data, searchParams) => {
      try {
          const response = await WageSeekerService.search(tenantId, data, searchParams);
          return transformViewDataToApplicationDetails(t, response, tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }  
    },

    fetchWageSeekerWithBankDetails : async (tenantId, data, searchParams) => {
      try {
        const response = await WageSeekerService.search(tenantId, data, searchParams);
        return fetchBankDetails(response, tenantId)
      } catch (error) {
        throw new Error(error?.response?.data?.Errors?.[0]?.message)
      }
    }
}
