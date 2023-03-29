export const updateWageSeekerFormDefaultValues = async ({configs, isModify, sessionFormData, setSessionFormData, wageSeekerData, tenantId, headerLocale, ULBOptions, setIsFormReady }) => {

    const individual = wageSeekerData?.individual
    const bankAccountDetails = wageSeekerData?.bankDetails?.[0]?.bankAccountDetails?.[0]

    const adhaar = individual?.identifiers?.find(item => item?.identifierType === 'AADHAAR')
    const socialCategory = individual?.additionalFields?.fields?.find(item => item?.key === "SOCIAL_CATEGORY")
    const skills = individual?.skills?.length > 0 ? individual?.skills?.map(skill => ({code: `${skill?.level}.${skill?.type}`, name: `COMMON_MASTERS_SKILLS_${skill?.level}.${skill?.type}`})) : ""
    
    const photo = individual?.photo && await Digit.UploadServices.Filefetch([individual?.photo], tenantId);
    const imageLink = photo?.data?.fileStoreIds?.[0]?.url
    const imageName = Digit.Utils.getDocumentName(imageLink)

    if(isModify) {
        configs.defaultValues.basicDetails_wageSeekerId = individual?.individualId ? individual?.individualId : ""
    }
    configs.defaultValues.basicDetails_aadhar = adhaar ? adhaar?.identifierId : ""
    configs.defaultValues.basicDetails_wageSeekerName = individual?.name?.givenName ? individual?.name?.givenName : ""
    configs.defaultValues.basicDetails_fatherHusbandName = individual?.fatherName ? individual?.fatherName : ""
    configs.defaultValues.basicDetails_relationShip = individual?.relationship ? { code: individual?.relationship, name: `COMMON_MASTERS_RELATIONSHIP_${individual?.relationship}`, active: true } : ""
    configs.defaultValues.basicDetails_dateOfBirth = individual?.dateOfBirth ? Digit.DateUtils.ConvertTimestampToDate(new Date(individual?.dateOfBirth), 'yyyy-MM-dd') : ""
    configs.defaultValues.basicDetails_gender = individual?.gender ? { code: individual?.gender, name: `COMMON_MASTERS_GENDER_${individual?.gender}`, active: true } : ""
    configs.defaultValues.basicDetails_mobileNumber = individual?.mobileNumber ? individual?.mobileNumber : ""
    configs.defaultValues.basicDetails_socialCategory = socialCategory ? { code:socialCategory?.value, name: `COMMON_MASTERS_SOCIAL_${socialCategory?.value}`, active: true} : ""
    configs.defaultValues.basicDetails_photograph = individual?.photo ? [[ 
        imageName , {  file: {}, fileStoreId: { fileStoreId: individual?.photo, tenantId }
    }]] : ""

    configs.defaultValues.skillDetails_skill = skills

    configs.defaultValues.locDetails_city = ULBOptions[0]
    configs.defaultValues.locDetails_ward = individual?.address?.[0]?.ward ? { code: individual?.address?.[0]?.ward?.code, name: individual?.address?.[0]?.ward?.code, i18nKey: `${headerLocale}_ADMIN_${individual?.address?.[0]?.ward?.code}`} : ""
    configs.defaultValues.locDetails_locality = individual?.address?.[0]?.locality ? { code: individual?.address?.[0]?.locality?.code, name: individual?.address?.[0]?.locality?.code, i18nKey: `${headerLocale}_ADMIN_${individual?.address?.[0]?.locality?.code}`} : ""
    configs.defaultValues.locDetails_streetName = individual?.address?.[0]?.street ? individual?.address?.[0]?.street : ""
    configs.defaultValues.locDetails_houseName = individual?.address?.[0]?.doorNo ? individual?.address?.[0]?.doorNo : ""

    configs.defaultValues.financeDetails_accountHolderName = bankAccountDetails?.accountHolderName ? bankAccountDetails?.accountHolderName : ""
    configs.defaultValues.financeDetails_accountNumber = bankAccountDetails?.accountNumber ? bankAccountDetails?.accountNumber : ""
    configs.defaultValues.financeDetails_accountType = bankAccountDetails?.accountType ? { code: bankAccountDetails?.accountType , name: `MASTERS_${bankAccountDetails?.accountType}`, active: true } : ""
    configs.defaultValues.financeDetails_ifsc = bankAccountDetails?.bankBranchIdentifier?.code ? bankAccountDetails?.bankBranchIdentifier?.code : ""
    configs.defaultValues.financeDetails_branchName = bankAccountDetails?.bankBranchIdentifier?.additionalDetails?.ifsccode ? bankAccountDetails?.bankBranchIdentifier?.additionalDetails?.ifsccode : ""

    setSessionFormData({...configs?.defaultValues})
    setIsFormReady(true)
}

export const getWageSeekerUpdatePayload = ({formData, wageSeekerDataFromAPI, tenantId, isModify}) => {
    console.log('DATA', {formData, wageSeekerDataFromAPI});
    let Individual = {}
    Individual.id = wageSeekerDataFromAPI?.individual?.id
    Individual.individualId = formData?.basicDetails_wageSeekerId
    Individual.tenantId = tenantId
    Individual.name = {
        givenName: formData?.basicDetails_wageSeekerName
    }
    Individual.dateOfBirth = Digit.DateUtils.ConvertTimestampToDate(new Date(formData?.basicDetails_dateOfBirth), 'dd/MM/yyyy')
    Individual.gender = formData?.basicDetails_gender?.code
    Individual.mobileNumber = formData?.basicDetails_mobileNumber
    Individual.address = [{
        id: wageSeekerDataFromAPI?.individual?.address?.[0]?.id,
        individualId: wageSeekerDataFromAPI?.individual?.id,
        tenantId: wageSeekerDataFromAPI?.individual?.address?.[0]?.tenantId,
        city: formData?.locDetails_city?.code,
        doorNo: formData?.locDetails_houseName,
        street: formData?.locDetails_streetName,
        type: "PERMANENT",
        locality: {
            code: formData?.locDetails_locality?.code,
        },
        ward: {
            code: formData?.locDetails_ward?.code
        }
    }]
    Individual.fatherName = formData?.basicDetails_fatherHusbandName
    Individual.relationship = formData?.basicDetails_relationShip?.code
    Individual.skills = [] //formData?.skillDetails_skill?.map(skill => ({ level: skill?.code?.split('.')?.[0], type: skill?.code?.split('.')?.[1]})) //update
    Individual.photo = formData?.basicDetails_photograph?.[0]?.[1]?.fileStoreId?.fileStoreId
    Individual.additionalFields = {
        fields: [{
            key: "SOCIAL_CATEGORY",
            value: formData?.basicDetails_socialCategory?.code
        }]
    }
    Individual.rowVersion = wageSeekerDataFromAPI?.individual?.rowVersion

    /* For Create
    Individual.tenantId = tenantId
    Individual.name.givenName = formData?.basicDetails_wageSeekerName
    Individual.dateOfBirth = Digit.DateUtils.ConvertTimestampToDate(new Date(formData?.basicDetails_dateOfBirth), 'dd/MM/yyyy')
    Individual.gender = formData?.basicDetails_gender?.code
    Individual.mobileNumber = formData?.basicDetails_mobileNumber
    Individual.address[0].tenantId = wageSeekerDataFromAPI?.individual?.address?.[0]?.tenantId
    Individual.address[0].doorNo = formData?.locDetails_houseName
    Individual.address[0].street = formData?.locDetails_streetName
    Individual.address[0].locality.code = formData?.locDetails_locality?.code
    Individual.address[0].ward.code = formData?.locDetails_ward?.code
    Individual.address[0].city = formData?.locDetails_city?.code
    Individual.fatherName = formData?.basicDetails_fatherHusbandName
    Individual.relationship = formData?.basicDetails_relationShip?.code
    Individual.identifiers[0].identifierType = 'AADHAAR'
    Individual.identifiers[0].identifierId = formData?.basicDetails_aadhar
    Individual.skills = [] //update
    Individual.photo = formData?.basicDetails_photograph?.[0]?.[1]?.fileStoreId
    Individual.additionalFields.fields[0].key = "SOCIAL_CATEGORY"
    Individual.additionalFields.fields[0].value = formData?.basicDetails_socialCategory?.code
    */
    return {
        Individual
    }
}

export const getBankAccountUpdatePayload = ({formData, wageSeekerDataFromAPI, tenantId, isModify}) => {
    //copy existing data
    let bankAccounts = [...wageSeekerDataFromAPI?.bankDetails]
    delete bankAccounts[0]?.auditDetails
    
    let bankAccountDetails = bankAccounts?.[0]?.bankAccountDetails?.[0]
    bankAccountDetails.isActive = false
    bankAccountDetails.isPrimary = false
    delete bankAccountDetails?.auditDetails

    //create new object if anything updated
    let bankAccountsData = {}
    bankAccountsData.tenantId = tenantId
    bankAccountsData.accountHolderName = formData?.financeDetails_accountHolderName
    bankAccountsData.accountNumber = formData?.financeDetails_accountNumber
    bankAccountsData.accountType = formData?.financeDetails_accountType?.code
    bankAccountsData.bankBranchIdentifier = {
        type: "IFSC",
        code: formData?.financeDetails_ifsc,
        additionalDetails: {
            ifsccode: formData?.financeDetails_branchName
        }
    }
    bankAccountsData.isActive = true
    bankAccountsData.isPrimary = true
    bankAccounts?.[0]?.bankAccountDetails.push(bankAccountsData)
    console.log('&&&', bankAccounts);
    return {
        bankAccounts
    }
}   