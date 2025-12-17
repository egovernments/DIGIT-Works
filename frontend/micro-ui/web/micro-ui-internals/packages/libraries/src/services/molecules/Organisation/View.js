import { OrganisationService } from "../../elements/Organisation";
import { BankAccountService } from "../../elements/BankAccount";

const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
  if(data?.organisations?.length === 0) throw new Error('No data found');
    
  const organisation = data.organisations[0]

  const bankDetailPayload = { bankAccountDetails: { tenantId, serviceCode: "ORG", referenceId: [organisation?.id] } }
  const bankDetails = await BankAccountService.search(bankDetailPayload, {});
  const bankAccounts = bankDetails?.bankAccounts?.[0]?.bankAccountDetails

  const PAN = organisation?.identifiers?.find(item => item?.isActive && item?.type === 'PAN' )
  const GSTIN = organisation?.identifiers?.find(item => item?.isActive && item?.type === 'GSTIN')

  const getAddressMasked = (value) => {
    return value.replace(/(?<=.{1})./g, '*');
  }
  const orgDetails = [
    {
      title: '',
      values: [
        { title: "MASTERS_ORG_ID", value: organisation?.orgNumber || t("NA")},
        { title: "MASTERS_NAME_OF_THE_ORG", value: organisation?.name || t("NA")},
        { title: "MASTERS_REGISTERED_BY_DEPT", value: organisation?.additionalDetails?.registeredByDept || t("NA")},
        { title: "MASTERS_REGISTRATION_NUMBER", value: organisation?.additionalDetails?.deptRegistrationNum || t("NA")},
        { title: "MASTERS_DATE_OF_INCORPORATION", value: Digit.DateUtils.ConvertTimestampToDate(organisation?.dateOfIncorporation, 'dd/MM/yyyy') || t("NA")},
        { title: "CORE_COMMON_STATUS", value: t(`MASTERS_ORG_STATUS_${organisation?.applicationStatus}`) || t("NA")}
      ]
    },
    {
      title: 'MASTERS_FUNCTIONAL_DETAILS',
      values: [
        { title: "MASTERS_ORGANISATION_TYPE", value: organisation?.functions?.[0]?.type ? t(`COMMON_MASTERS_ORG_${organisation?.functions?.[0]?.type?.split('.')?.[0]}`) : t("NA")},
        { title: "MASTERS_ORGANISATION_SUB_TYPE", value: organisation?.functions?.[0]?.type ? t(`COMMON_MASTERS_SUBORG_${organisation?.functions?.[0]?.type?.split('.')?.[1]}`) : t("NA")},
        { title: "ES_COMMON_CATEGORY", value: organisation?.functions?.[0]?.category ? t(`COMMON_MASTERS_FUNCATEGORY_${organisation?.functions?.[0]?.category?.split('.')?.[1]}`): t("NA")},
        { title: "MASTERS_CLASS_RANK", value: organisation?.functions?.[0]?.class ? t(`COMMON_MASTERS_CLASS_${organisation?.functions?.[0]?.class}`) : t("NA")},
        { title: "ES_COMMON_VALID_FROM", value: Digit.DateUtils.ConvertTimestampToDate(organisation?.functions?.[0]?.validFrom, 'dd/MM/yyyy') || t("NA")},
        { title: "ES_COMMON_VALID_TO", value: Digit.DateUtils.ConvertTimestampToDate(organisation?.functions?.[0]?.validTo, 'dd/MM/yyyy') || t("NA")}
      ]
    }
  ]

  const locationDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
        { title: "CORE_COMMON_PROFILE_CITY", value: organisation?.orgAddress?.[0]?.tenantId ? Digit.Utils.locale.getCityLocale(organisation?.orgAddress?.[0]?.tenantId) : t("NA")},
        { title: "COMMON_WARD", value: organisation?.orgAddress?.[0]?.boundaryCode ? ( organisation?.orgAddress?.[0]?.geoLocation?.additionalDetails?.isWardMasked ? getAddressMasked(t(Digit.Utils.locale.getMohallaLocale(organisation?.orgAddress?.[0]?.boundaryCode, tenantId))) : Digit.Utils.locale.getMohallaLocale(organisation?.orgAddress?.[0]?.boundaryCode, tenantId)) : t("NA")},
        { title: "COMMON_LOCALITY", value: organisation?.additionalDetails?.locality ? ( organisation?.additionalDetails?.isLocalityMasked ? getAddressMasked(t(Digit.Utils.locale.getMohallaLocale(organisation?.additionalDetails?.locality, tenantId))) : Digit.Utils.locale.getMohallaLocale(organisation?.additionalDetails?.locality, tenantId)) : t("NA")},
        { title: "ES_COMMON_STREET", value: organisation?.orgAddress?.[0]?.street || t("NA")},
        { title: "ES_COMMON_DOOR_NO", value: organisation?.orgAddress?.[0]?.doorNo || t("NA")},
    ]
  }

  const contactDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
        { title: "CORE_COMMON_NAME", value: organisation?.contactDetails?.[0]?.contactName || t("NA")},
        { title: "CORE_COMMON_PROFILE_MOBILE_NUMBER", value: organisation?.contactDetails?.[0]?.contactMobileNumber || t("NA")},
        { title: "CORE_COMMON_PROFILE_EMAIL", value: organisation?.contactDetails?.[0]?.contactEmail || t("NA")}
    ]
  }

  let financialDetails = []
  bankAccounts?.forEach((item, index) => {
    let bankDetails = {}
    bankDetails.title = " "
    bankDetails.asSectionHeader = true
    bankDetails.values = [
      { title: "ES_COMMON_ACCOUNT_HOLDER_NAME", value: item?.accountHolderName || t("NA")},
      { title: "MASTERS_ACC_NO", value: item?.accountNumber || t("NA")},
      { title: "MASTERS_IFSC", value: item?.bankBranchIdentifier?.code || t("NA")},
      { title: "ES_COMMON_BRANCH", value: item?.bankBranchIdentifier?.additionalDetails?.ifsccode || t("NA")},
      { title: "MASTERS_EFFECTIVE_FROM", value: Digit.DateUtils.ConvertTimestampToDate(item?.auditDetails?.createdTime, 'dd/MM/yyyy') || t("NA")},
      { title: "MASTERS_EFFECTIVE_TO", value: item?.isActive && item?.isPrimary ? t("NA") : Digit.DateUtils.ConvertTimestampToDate(item?.auditDetails?.lastModifiedTime, 'dd/MM/yyyy')},
      { title: "COMMON_MASTERS_TAXIDENTIFIER_PAN", value: PAN?.value === "XXXXX0123X" ? t("NA") : PAN?.value },
      { title: "COMMON_MASTERS_TAXIDENTIFIER_GSTIN", value: GSTIN ? GSTIN?.value : t("NA")}
    ]
    financialDetails.push(bankDetails)
  })

  const applicationDetails = {
    orgDetails,
    locationDetails,
    contactDetails,
    financialDetails
  }

  return {
    applicationDetails,
    applicationData: organisation,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

const fetchBankDetails = async (data, tenantId) => {
  if(data?.organisations?.length === 0) throw new Error('No data found');

  const organisation = data.organisations[0]
  const bankDetailPayload = { bankAccountDetails: { tenantId, serviceCode: "ORG", referenceId: [organisation?.id] } }
  const bankDetails = await BankAccountService.search(bankDetailPayload, {});
  
  return {
    organisation,
    bankDetails: bankDetails?.bankAccounts
  }
}

export const View = {
    fetchOrganisationDetails: async (t, tenantId, data) => {
      try {
          const response = await OrganisationService.search(data);
          return transformViewDataToApplicationDetails(t, response, tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }  
    },

    fetchOrganisationWithBankDetails : async (tenantId, data) => {
      try {
        const response = await OrganisationService.search(data);
        return fetchBankDetails(response, tenantId)
      } catch (error) {
        throw new Error(error?.response?.data?.Errors?.[0]?.message)
      }
    }
}
