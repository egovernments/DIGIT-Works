const getValidDateObject = (dateString) => {
    let [day, month, year] = dateString.split('/')
    return new Date(+year, +month - 1, +day)
}

const ORG_VALIDTO_DATE = '2099-03-31'

export const getTomorrowsDate = () => {
    let today = new Date()
    let tomorrow = new Date()
    tomorrow.setDate(today.getDate() + 1)
    return tomorrow.toISOString().split("T")[0]
}

export const updateWageSeekerFormDefaultValues = async ({configs, isModify, sessionFormData, setSessionFormData, wageSeekerData, tenantId, headerLocale, ULBOptions, setIsFormReady, t }) => {

    const individual = wageSeekerData?.individual
    const bankAccountDetails = wageSeekerData?.bankDetails?.[0]?.bankAccountDetails?.[0]

    const adhaar = individual?.identifiers?.find(item => item?.identifierType === 'AADHAAR')
    const socialCategory = individual?.additionalFields?.fields?.find(item => item?.key === "SOCIAL_CATEGORY")
    const skills = individual?.skills?.length > 0 ? individual?.skills?.map(skill => ({code: `${skill?.level}`, name: t(`COMMON_MASTERS_SKILLS_${skill?.level}`)})) : ""
    
    let photo = ''
    try {
        photo = individual?.photo && await Digit.UploadServices.Filefetch([individual?.photo], tenantId);
    } catch (error) {}
    const imageLink = photo?.data?.fileStoreIds?.[0]?.url
    const imageName = Digit.Utils.getDocumentName(imageLink) || 'profile'

    if(!sessionFormData?.locDetails_city) {
        if(isModify) {
            configs.defaultValues.basicDetails_wageSeekerId = individual?.individualId ? individual?.individualId : ""
        }
        configs.defaultValues.basicDetails_aadhar = adhaar ? adhaar?.identifierId : ""
        configs.defaultValues.basicDetails_wageSeekerName = individual?.name?.givenName ? individual?.name?.givenName : ""
        configs.defaultValues.basicDetails_fatherHusbandName = individual?.fatherName ? individual?.fatherName : ""
        configs.defaultValues.basicDetails_relationShip = individual?.relationship ? { code: individual?.relationship, name: `COMMON_MASTERS_RELATIONSHIP_${individual?.relationship}`, active: true } : ""
        configs.defaultValues.basicDetails_dateOfBirth = individual?.dateOfBirth ? Digit.DateUtils.ConvertTimestampToDate(getValidDateObject(individual?.dateOfBirth), 'yyyy-MM-dd') : ""
        configs.defaultValues.basicDetails_gender = individual?.gender ? { code: individual?.gender, name: `COMMON_MASTERS_GENDER_${individual?.gender}`, active: true } : ""
        configs.defaultValues.basicDetails_mobileNumber = individual?.mobileNumber ? individual?.mobileNumber : ""
        configs.defaultValues.basicDetails_socialCategory = socialCategory ? { code:socialCategory?.value, name: `COMMON_MASTERS_SOCIAL_${socialCategory?.value}`, active: true} : ""
        configs.defaultValues.basicDetails_photograph = individual?.photo ? [[ 
            imageName , {  file: {}, fileStoreId: { fileStoreId: individual?.photo, tenantId }
        }]] : ""
    
        configs.defaultValues.skillDetails_skill = skills
    
        configs.defaultValues.locDetails_city = ULBOptions[0]
        configs.defaultValues.locDetails_ward = individual?.address?.[0]?.ward ? { code: individual?.address?.[0]?.ward?.code, name: individual?.address?.[0]?.ward?.code, i18nKey: Digit.Utils.locale.getMohallaLocale(individual?.address?.[0]?.ward?.code, tenantId)} : ""
        configs.defaultValues.locDetails_locality = individual?.address?.[0]?.locality ? { code: individual?.address?.[0]?.locality?.code, name: individual?.address?.[0]?.locality?.code, i18nKey: Digit.Utils.locale.getMohallaLocale(individual?.address?.[0]?.locality?.code, tenantId)} : ""
        configs.defaultValues.locDetails_streetName = individual?.address?.[0]?.street ? individual?.address?.[0]?.street : ""
        configs.defaultValues.locDetails_houseName = individual?.address?.[0]?.doorNo ? individual?.address?.[0]?.doorNo : ""
    
        configs.defaultValues.financeDetails_accountHolderName = bankAccountDetails?.accountHolderName ? bankAccountDetails?.accountHolderName : ""
        configs.defaultValues.financeDetails_accountNumber = bankAccountDetails?.accountNumber ? bankAccountDetails?.accountNumber : ""
        configs.defaultValues.financeDetails_accountType = bankAccountDetails?.accountType ? { code: bankAccountDetails?.accountType , name: `MASTERS_${bankAccountDetails?.accountType}`, active: true } : ""
        configs.defaultValues.financeDetails_ifsc = bankAccountDetails?.bankBranchIdentifier?.code ? bankAccountDetails?.bankBranchIdentifier?.code : ""
        configs.defaultValues.financeDetails_branchName = bankAccountDetails?.bankBranchIdentifier?.additionalDetails?.ifsccode ? bankAccountDetails?.bankBranchIdentifier?.additionalDetails?.ifsccode : ""
    
        setSessionFormData({...configs?.defaultValues})
    }
    setIsFormReady(true)
}

const getSkillsToUpdate = (formData, wageSeekerDataFromAPI) => {
    let updatedSkills = formData?.skillDetails_skill
    //added code field in existing skills to find difference
    let existingSkills = wageSeekerDataFromAPI?.individual?.skills?.map(item => ({ ...item, code : `${item?.level}`}))

    let set1 = new Set(updatedSkills.map(({ code }) => code))
    let set2 = new Set(existingSkills.map(({ code }) => code))
    let extraSkillsTobeAdded = updatedSkills.filter(({ code }) => !set2.has(code))
    let extraSkillsTobeRemoved = existingSkills.filter(({ code }) => !set1.has(code))

    let filterExistingSkills = existingSkills?.filter(item => {
        //remove skillsToBeRemoved from existingSkills if present
        let takeIt = true
        extraSkillsTobeRemoved?.forEach(row => {
            if(row?.code === item?.code){
                takeIt = false
            }
        })
        return takeIt
    })

    let skillsTobeAdded = extraSkillsTobeAdded?.map(item => {
        //const separator = item?.code.includes('.') ? '.' : '_';
        //const [level, type] = item?.code.split(separator);
        const level = item?.code;
        const type = item?.code;
        return { level, type };
    });
    let skillsTobeRemoved = extraSkillsTobeRemoved?.map(item => ({ ...item, isDeleted: true }))
    return {
        skillsTobeAdded: [...filterExistingSkills, ...skillsTobeAdded],
        skillsTobeRemoved
    }
}

export const getWageSeekerUpdatePayload = ({formData, wageSeekerDataFromAPI, tenantId, isModify}) => {
    let Individual = {}

    Individual.tenantId = tenantId
    Individual.name = {
        givenName: formData?.basicDetails_wageSeekerName
    }
    Individual.dateOfBirth = Digit.DateUtils.ConvertTimestampToDate(new Date(formData?.basicDetails_dateOfBirth), 'dd/MM/yyyy')
    if(!(formData?.basicDetails_gender?.code?.includes("UNDISCLOSED"))) Individual.gender = formData?.basicDetails_gender?.code
    Individual.mobileNumber = formData?.basicDetails_mobileNumber
    Individual.fatherName = formData?.basicDetails_fatherHusbandName
    if(!(formData?.basicDetails_relationShip?.code?.includes("UNDISCLOSED")))Individual.relationship = formData?.basicDetails_relationShip?.code
    // Individual.skills = formData?.skillDetails_skill?.map(skill => ({ level: skill?.code?.split('.')?.[0], type: skill?.code?.split('.')?.[1]}))
    Individual.photo = formData?.basicDetails_photograph?.[0]?.[1]?.fileStoreId?.fileStoreId

    //DPP update here as well for socialCategory
    if(formData?.basicDetails_socialCategory?.code) {
        Individual.additionalFields = {
            fields: [...wageSeekerDataFromAPI?.individual?.additionalFields.fields.filter((ob) => ob?.key !== "SOCIAL_CATEGORY"),{
                key: "SOCIAL_CATEGORY",
                value: formData?.basicDetails_socialCategory?.code
            }]
        }
    }
    
    Individual.address = [{
        tenantId: tenantId,
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

    if(!Individual.address[0]?.doorNo) delete Individual.address[0]?.doorNo
    if(!Individual.address[0]?.street) delete Individual.address[0]?.street

    if(!isModify) {
        Individual.identifiers = [{
            identifierType: "AADHAAR",
            identifierId: formData?.basicDetails_aadhar
        }]
    }

    if(isModify) {
        Individual.auditDetails = wageSeekerDataFromAPI?.individual?.auditDetails
        Individual.id = wageSeekerDataFromAPI?.individual?.id
        Individual.individualId = formData?.basicDetails_wageSeekerId
        Individual.address = [{
            id: wageSeekerDataFromAPI?.individual?.address?.[0]?.id,
            individualId: wageSeekerDataFromAPI?.individual?.id,
            tenantId: wageSeekerDataFromAPI?.individual?.address?.[0]?.tenantId,
            city: formData?.locDetails_city?.code,
            doorNo: formData?.locDetails_houseName || "NA", //min 2 chars mandatory from BE
            street: formData?.locDetails_streetName || "NA" ,//min 2 chars mandatory from BE
            type: "PERMANENT",
            locality: {
                code: formData?.locDetails_locality?.code,
            },
            ward: {
                code: formData?.locDetails_ward?.code
            }
        }]
       
        const { skillsTobeAdded, skillsTobeRemoved } = getSkillsToUpdate(formData, wageSeekerDataFromAPI)
        Individual.skills = skillsTobeAdded?.length > 0 ? skillsTobeAdded : []
        if(skillsTobeRemoved?.length > 0) {
            Individual.skillsTobeRemoved = skillsTobeRemoved
        }
        Individual.rowVersion = wageSeekerDataFromAPI?.individual?.rowVersion
        //here set the identifiers on Individual object
        Individual.identifiers = wageSeekerDataFromAPI?.individual?.identifiers
    }
    
    return {
        Individual
    }
}

export const getWageSeekerSkillDeletePayload = ({wageSeekerDataFromAPI, tenantId, skillsTobeRemoved}) => {
    let Individual = {...wageSeekerDataFromAPI?.Individual}
    Individual.id = wageSeekerDataFromAPI?.Individual?.id
    Individual.tenantId = tenantId
    Individual.name = wageSeekerDataFromAPI?.Individual?.name
    Individual.rowVersion = parseInt(wageSeekerDataFromAPI?.Individual?.rowVersion)
    Individual.skills = skillsTobeRemoved
    Individual.additionalFields = wageSeekerDataFromAPI?.Individual?.additionalFields
    return {
       Individual
    }
}

export const getBankAccountUpdatePayload = ({formData, apiData, tenantId, isModify, referenceId, isWageSeeker}) => {
    let bankAccounts = []

    //create new object for bank account details
    let bankAccountsData = {}
    bankAccountsData.tenantId = tenantId
    bankAccountsData.accountHolderName = formData?.financeDetails_accountHolderName
    bankAccountsData.accountNumber = formData?.financeDetails_accountNumber
    bankAccountsData.accountType = formData?.financeDetails_accountType?.code
    bankAccountsData.bankBranchIdentifier = {
        type: "IFSC",
        code: isWageSeeker? formData?.financeDetails_ifsc : formData?.transferCodesData?.[0]?.value,
        additionalDetails: {
            ifsccode: formData?.financeDetails_branchName
        }
    }
    bankAccountsData.isActive = true
    bankAccountsData.isPrimary = true

    if(isModify) {
        //copy existing data
        bankAccounts = [...apiData?.bankDetails]
        delete bankAccounts[0]?.auditDetails

        let bankAccountDetails = bankAccounts?.[0]?.bankAccountDetails?.[0]
        if(bankAccountDetails) {
            bankAccountDetails.isActive = true
            bankAccountDetails.isPrimary = true
            bankAccountDetails.accountHolderName = formData?.financeDetails_accountHolderName
            bankAccountDetails.accountNumber = formData?.financeDetails_accountNumber
            bankAccountDetails.accountType = formData?.financeDetails_accountType?.code
            bankAccountDetails.tenantId = tenantId
            bankAccountDetails.bankBranchIdentifier = {
                type: "IFSC",
                code: isWageSeeker? formData?.financeDetails_ifsc : formData?.transferCodesData?.[0]?.value,
                additionalDetails: {
                    ifsccode: formData?.financeDetails_branchName
                },
                id: isModify ? bankAccountDetails.bankBranchIdentifier?.id : null
            }
        }
        delete bankAccountDetails?.auditDetails
    } else {
        let bankObj = {}
        bankObj.tenantId = tenantId
        bankObj.serviceCode = isWageSeeker ? "IND" : "ORG"
        bankObj.referenceId = referenceId
        bankObj.bankAccountDetails = []
        bankAccounts.push(bankObj)
        bankAccounts?.[0]?.bankAccountDetails.push(bankAccountsData)
    }
    
    return {
        bankAccounts
    }
}   

export const updateOrganisationFormDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, orgData, tenantId, ULBOptions, setIsFormReady }) => {
    const organisation = orgData?.organisation
    const bankAccountDetails = orgData?.bankDetails?.[0]?.bankAccountDetails?.[0]
    
    const funDetails = organisation?.functions?.[0]
    let identifiers = organisation?.identifiers?.filter(item => item?.isActive)?.map(item => {
        return {
            name: { code: item?.type, name: `COMMON_MASTERS_TAXIDENTIFIER_${item?.type}`, active: true},
            value: item?.value ? item?.value : ""
        }
    })

    if(!sessionFormData?.locDetails_city) {
        if(isModify) {
            configs.defaultValues.basicDetails_orgId = organisation?.orgNumber ? organisation?.orgNumber : ""
        }

        configs.defaultValues.basicDetails_orgName = organisation?.name ? organisation?.name : ""
        configs.defaultValues.basicDetails_regDept = organisation?.additionalDetails?.registeredByDept ? organisation?.additionalDetails?.registeredByDept : ""
        configs.defaultValues.basicDetails_regDeptNo = organisation?.additionalDetails?.deptRegistrationNum ? organisation?.additionalDetails?.deptRegistrationNum : ""
        configs.defaultValues.basicDetails_dateOfIncorporation = organisation?.dateOfIncorporation ? Digit.DateUtils.ConvertTimestampToDate(organisation?.dateOfIncorporation, 'yyyy-MM-dd') : ""
        
        configs.defaultValues.funDetails_orgType = funDetails?.type ? { code: funDetails?.type?.split('.')?.[0] , name: `COMMON_MASTERS_ORG_${funDetails?.type?.split('.')?.[0]}`} : ""
        configs.defaultValues.funDetails_orgSubType = funDetails?.type ? { code: funDetails?.type?.split('.')?.[1] , name: `COMMON_MASTERS_SUBORG_${funDetails?.type?.split('.')?.[1]}`} : ""
        configs.defaultValues.funDetails_category = funDetails?.category ? { code: funDetails?.category?.split('.')?.[1] , name: `COMMON_MASTERS_FUNCATEGORY_${funDetails?.category?.split('.')?.[1]}`} : ""
        configs.defaultValues.funDetails_classRank = funDetails?.class ? { code:funDetails?.class , name: `COMMON_MASTERS_CLASS_${funDetails?.class}`} : ""
        configs.defaultValues.funDetails_validFrom = funDetails?.validFrom ? Digit.DateUtils.ConvertTimestampToDate(funDetails?.validFrom, 'yyyy-MM-dd') : ""
        configs.defaultValues.funDetails_validTo = funDetails?.validTo ? Digit.DateUtils.ConvertTimestampToDate(funDetails?.validTo, 'yyyy-MM-dd') : ""

        configs.defaultValues.locDetails_city = ULBOptions[0]
        configs.defaultValues.locDetails_ward = organisation?.orgAddress?.[0]?.boundaryCode ? { code: organisation?.orgAddress?.[0]?.boundaryCode, name: organisation?.orgAddress?.[0]?.boundaryCode, i18nKey: Digit.Utils.locale.getMohallaLocale(organisation?.orgAddress?.[0]?.boundaryCode, tenantId)} : ""
        configs.defaultValues.locDetails_locality = organisation?.additionalDetails?.locality ? { code: organisation?.additionalDetails?.locality, name: organisation?.additionalDetails?.locality, i18nKey: Digit.Utils.locale.getMohallaLocale(organisation?.additionalDetails?.locality, tenantId)} : ""
        configs.defaultValues.locDetails_streetName = organisation?.orgAddress?.[0]?.street ? organisation?.orgAddress?.[0]?.street : ""
        configs.defaultValues.locDetails_houseName = organisation?.orgAddress?.[0]?.doorNo ? organisation?.orgAddress?.[0]?.doorNo : ""
        
        configs.defaultValues.contactDetails_name = organisation?.contactDetails?.[0]?.contactName ? organisation?.contactDetails?.[0]?.contactName : ""
        configs.defaultValues.contactDetails_mobile = organisation?.contactDetails?.[0]?.contactMobileNumber ? organisation?.contactDetails?.[0]?.contactMobileNumber : ""
        configs.defaultValues.contactDetails_email = organisation?.contactDetails?.[0]?.contactEmail ? organisation?.contactDetails?.[0]?.contactEmail : ""
        
        configs.defaultValues.financeDetails_accountHolderName = bankAccountDetails?.accountHolderName ? bankAccountDetails?.accountHolderName : ""
        configs.defaultValues.financeDetails_accountType = bankAccountDetails?.accountType ? { code: bankAccountDetails?.accountType , name: `MASTERS_${bankAccountDetails?.accountType}`, active: true } : ""
        configs.defaultValues.financeDetails_accountNumber = bankAccountDetails?.accountNumber ? bankAccountDetails?.accountNumber : ""
        configs.defaultValues.financeDetails_bankName = ''
        configs.defaultValues.financeDetails_branchName = ''

        configs.defaultValues.transferCodesData = [{
            name: { code: 'IFSC', "name": "COMMON_MASTERS_TRANSFERCODES_IFSC", active: true },
            value: bankAccountDetails?.bankBranchIdentifier?.code ? bankAccountDetails?.bankBranchIdentifier?.code : "" 
        }]

        configs.defaultValues.taxIdentifierData = identifiers?.length > 0 ? identifiers : ""
       
        setSessionFormData({...configs?.defaultValues})
    }
    setIsFormReady(true)
}

const getOrgIdentifiersToUpdate = (formData, orgDataFromAPI) => {
    let updatedIdentifiers = formData?.taxIdentifierData
    let existingIdentifiers = orgDataFromAPI?.organisation?.identifiers

    let orgIdentifiers = []
    let types = ['PAN', 'GSTIN']
    types?.forEach(type => {
        let formIdentifier = updatedIdentifiers?.find(item => item?.name?.code === type)
        let apiIdentifier = existingIdentifiers?.find(item => item?.type === type)
        if(formIdentifier && apiIdentifier && formIdentifier?.value !== apiIdentifier?.value) {
            orgIdentifiers.push({...apiIdentifier, value: formIdentifier?.value, isActive: true})
        }
        if(formIdentifier && apiIdentifier && formIdentifier?.value === apiIdentifier?.value) {
            orgIdentifiers.push({...apiIdentifier, isActive: true})
        }
        if(formIdentifier && !apiIdentifier) {
            orgIdentifiers.push({type: formIdentifier?.name?.code,  value: formIdentifier?.value })
        }
        if(!formIdentifier && apiIdentifier) {
            orgIdentifiers.push({...apiIdentifier, isActive: false})
        }
    })
    return orgIdentifiers
}

export const getOrgPayload = ({formData, orgDataFromAPI, tenantId, isModify}) => {
    let organisation = {}
    let organisations = []
    organisation.tenantId = tenantId
    organisation.name = formData?.basicDetails_orgName
    organisation.applicationStatus = 'ACTIVE'
    organisation.dateOfIncorporation = Digit.Utils.pt.convertDateToEpoch(formData?.basicDetails_dateOfIncorporation)
    organisation.orgAddress = [{
        tenantId: tenantId,
        doorNo: formData?.locDetails_houseName,
        city: formData?.locDetails_city?.code,
        boundaryType: 'Ward',
        boundaryCode: formData?.locDetails_ward?.code,
        street: formData?.locDetails_streetName,
        geoLocation: {}
    }]
    organisation.additionalDetails = {
        locality: formData?.locDetails_locality?.code || formData?.locDetails_locality?.[0]?.code,
        registeredByDept: formData?.basicDetails_regDept,
        deptRegistrationNum: formData?.basicDetails_regDeptNo
    }
    organisation.contactDetails = [{
        contactName: formData?.contactDetails_name, 
        contactMobileNumber: formData?.contactDetails_mobile,
        contactEmail: formData?.contactDetails_email != '' ? formData?.contactDetails_email : null
    }]
    organisation.functions = [{
        type: `${formData?.funDetails_orgType?.code}.${formData?.funDetails_orgSubType?.code}`,
        category: `${formData?.funDetails_orgType?.code}.${formData?.funDetails_category?.code}`,
        class: formData?.funDetails_classRank?.code,
        validFrom: Digit.Utils.pt.convertDateToEpoch(formData?.funDetails_validFrom),
        validTo: formData?.funDetails_validTo ? Digit.Utils.pt.convertDateToEpoch(formData?.funDetails_validTo) : Digit.Utils.pt.convertDateToEpoch(ORG_VALIDTO_DATE)
    }]
    organisation.identifiers = formData?.taxIdentifierData?.map(item => {
        if(item?.name && item?.value) {
            return {
                type: item?.name?.code,
                value: item?.value,
                isActive: true
            }
        } else {
            return {
                type: "PAN",
                value: item?.value ? item?.value : "XXXXX0123X", //if nothing is entered, pass default type PAN and junk value
                isActive: true
            }  
        }
    }).filter(item=> item)

    if(isModify) {
        organisation.id = orgDataFromAPI?.organisation?.id
        organisation.applicationNumber = orgDataFromAPI?.organisation?.applicationNumber
        organisation.applicationStatus = 'ACTIVE'
        organisation.orgNumber = orgDataFromAPI?.organisation?.orgNumber
        organisation.orgAddress = [{
            id: orgDataFromAPI?.organisation?.orgAddress?.[0]?.id,
            orgId: orgDataFromAPI?.organisation?.id,
            tenantId: tenantId,
            boundaryType: 'Ward',
            boundaryCode: formData?.locDetails_ward?.code,
            city: formData?.locDetails_city?.code,
            doorNo: formData?.locDetails_houseName,
            street: formData?.locDetails_streetName,
        }]

        organisation.contactDetails = [{
            id: orgDataFromAPI?.organisation?.contactDetails?.[0]?.id,
            orgId: orgDataFromAPI?.organisation?.id,
            tenantId: tenantId,
            contactName: formData?.contactDetails_name, 
            contactMobileNumber: formData?.contactDetails_mobile,
            contactEmail: formData?.contactDetails_email !== "" ? formData?.contactDetails_email : null
        }]

        organisation.identifiers = getOrgIdentifiersToUpdate(formData, orgDataFromAPI).filter(item=> item)

        organisation.functions[0].id = orgDataFromAPI?.organisation?.functions?.[0]?.id
        organisation.functions[0].orgId = orgDataFromAPI?.organisation?.id
        organisation.functions[0].applicationNumber = orgDataFromAPI?.organisation?.functions?.[0]?.applicationNumber
        organisation.functions[0].isActive = true

        organisation.isActive = true
    }
    organisations.push(organisation)

    return {
        organisations
    }
}