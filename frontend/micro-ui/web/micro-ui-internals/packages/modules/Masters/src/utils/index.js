export const updateWageSeekerFormDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, wageSeekerData, tenantId, headerLocale, ULBOptions }) => {

    const individual = wageSeekerData?.individual
    const bankAccountDetails = wageSeekerData?.bankDetails?.[0]?.bankAccountDetails?.[0]

    const adhaar = individual?.identifiers?.find(item => item?.identifierType === 'AADHAAR')
    const socialCategory = individual?.additionalFields?.fields?.find(item => item?.key === "SOCIAL_CATEGORY")
    //const photo = await Digit.UploadServices.Filefetch([individual?.photo], tenantId);
    //console.log('photo', photo);

    if(isModify) {
        configs.defaultValues.basicDetails_wageSeekerId = individual?.individualId ? individual?.individualId : ""
    }
    configs.defaultValues.basicDetails_aadhar = adhaar ? adhaar?.identifierId : ""
    configs.defaultValues.basicDetails_wageSeekerName = individual?.name?.givenName ? individual?.name?.givenName : ""
    configs.defaultValues.basicDetails_fatherHusbandName = individual?.fatherName ? individual?.fatherName : ""
    configs.defaultValues.basicDetails_relationShip = individual?.relationship ? { code: individual?.relationship, name: `COMMON_MASTERS_RELATIONSHIP_${individual?.relationship}` } : ""
    configs.defaultValues.basicDetails_dateOfBirth = individual?.dateOfBirth ? individual?.dateOfBirth : ""
    configs.defaultValues.basicDetails_gender = individual?.gender ? { code: individual?.gender, name: `COMMON_MASTERS_GENDER_${individual?.gender}` } : ""
    configs.defaultValues.basicDetails_mobileNumber = individual?.mobileNumber ? individual?.mobileNumber : ""
    configs.defaultValues.basicDetails_socialCategory = socialCategory ? { code:socialCategory?.value, name: `COMMON_MASTERS_SOCIAL_${socialCategory?.value}`} : ""
    configs.defaultValues.basicDetails_photograph = individual?.photo ? [[ 
        "", { 
                file: {}, 
                fileStoreId: { fileStoreId: individual?.photo, tenantId }
        }]] : ""

    configs.defaultValues.skillDetails_skillCategory = ""
    configs.defaultValues.skillDetails_skill = ""

    configs.defaultValues.locDetails_city = ULBOptions[0]
    configs.defaultValues.locDetails_ward = individual?.address?.[0]?.ward ? { code: individual?.address?.[0]?.ward?.code, name: individual?.address?.[0]?.ward?.code, i18nKey: `${headerLocale}_ADMIN_${individual?.address?.[0]?.ward?.code}`} : ""
    configs.defaultValues.locDetails_locality = individual?.address?.[0]?.locality ? { code: individual?.address?.[0]?.locality?.code, name: individual?.address?.[0]?.locality?.code, i18nKey: `${headerLocale}_ADMIN_${individual?.address?.[0]?.locality?.code}`} : ""
    configs.defaultValues.locDetails_streetName = individual?.address?.[0]?.street ? individual?.address?.[0]?.street : ""
    configs.defaultValues.locDetails_houseName = individual?.address?.[0]?.doorNo ? individual?.address?.[0]?.doorNo : ""

    configs.defaultValues.financeDetails_accountHolderName = bankAccountDetails?.accountHolderName ? bankAccountDetails?.accountHolderName : ""
    configs.defaultValues.financeDetails_accountNumber = bankAccountDetails?.accountNumber ? bankAccountDetails?.accountNumber : ""
    configs.defaultValues.financeDetails_accountType = bankAccountDetails?.accountType ? { code: bankAccountDetails?.accountType , name: `MASTERS_${bankAccountDetails?.accountType}` } : ""
    configs.defaultValues.financeDetails_ifsc = bankAccountDetails?.bankBranchIdentifier?.code ? bankAccountDetails?.bankBranchIdentifier?.code : ""
    configs.defaultValues.financeDetails_branchName = bankAccountDetails?.bankBranchIdentifier?.additionalDetails?.ifsccode ? bankAccountDetails?.bankBranchIdentifier?.additionalDetails?.ifsccode : ""

    setSessionFormData({...configs?.defaultValues})
}