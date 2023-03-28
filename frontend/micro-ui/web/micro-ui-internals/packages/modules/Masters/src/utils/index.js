const dummyData = [
    {
        "id": "50e94705-8218-4b41-ab1b-0db496f231f8",
        "individualId": "IND-2023-03-27-001462",
        "tenantId": "pg.citya",
        "clientReferenceId": null,
        "userId": null,
        "name": {
            "givenName": "Test User1",
            "familyName": null,
            "otherNames": null
        },
        "dateOfBirth": "05/10/1997",
        "gender": "FEMALE",
        "bloodGroup": null,
        "mobileNumber": "9898986544",
        "altContactNumber": null,
        "email": null,
        "address": [
            {
                "id": "14f6acc2-d282-412b-b489-29bcb2c6c51d",
                "clientReferenceId": null,
                "individualId": "50e94705-8218-4b41-ab1b-0db496f231f8",
                "tenantId": "pg.citya",
                "doorNo": "507",
                "latitude": 0.0,
                "longitude": 0.0,
                "locationAccuracy": 0.0,
                "type": "PERMANENT",
                "addressLine1": null,
                "addressLine2": null,
                "landmark": null,
                "city": null,
                "pincode": null,
                "buildingName": null,
                "street": "MG Road1",
                "locality": {
                    "code": "SUN11",
                    "name": null,
                    "label": null,
                    "latitude": null,
                    "longitude": null,
                    "children": null,
                    "materializedPath": null
                },
                "ward": {
                    "code": "B2",
                    "name": null,
                    "label": null,
                    "latitude": null,
                    "longitude": null,
                    "children": null,
                    "materializedPath": null
                },
                "isDeleted": false,
                "auditDetails": {
                    "createdBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "lastModifiedBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "createdTime": 1679939870693,
                    "lastModifiedTime": 1679942656557
                }
            }
        ],
        "fatherName": "Test Father1",
        "husbandName": null,
        "relationship": "FATHER",
        "identifiers": [
            {
                "id": "1f07c7e1-b329-481e-a3fb-507da26e86f7",
                "clientReferenceId": null,
                "individualId": "50e94705-8218-4b41-ab1b-0db496f231f8",
                "identifierType": "AADHAAR",
                "identifierId": "XXXXXXXX7733",
                "isDeleted": false,
                "auditDetails": {
                    "createdBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "lastModifiedBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "createdTime": 1679939870694,
                    "lastModifiedTime": 1679939870694
                }
            }
        ],
        "skills": [
            {
                "id": "dc9e32e2-f692-4721-b2a4-7221b29f61bb",
                "clientReferenceId": null,
                "individualId": "50e94705-8218-4b41-ab1b-0db496f231f8",
                "type": "FEMALE_MULIA",
                "level": "SKILLED",
                "experience": null,
                "isDeleted": false,
                "auditDetails": {
                    "createdBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "lastModifiedBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "createdTime": 1679939870694,
                    "lastModifiedTime": 1679939870694
                }
            },
            {
                "id": "2169eafb-9a98-4c2b-989b-93b167c4787b",
                "clientReferenceId": null,
                "individualId": "50e94705-8218-4b41-ab1b-0db496f231f8",
                "type": "MALE_MULIA",
                "level": "UNSKILLED",
                "experience": null,
                "isDeleted": false,
                "auditDetails": {
                    "createdBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "lastModifiedBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
                    "createdTime": 1679939870694,
                    "lastModifiedTime": 1679942656557
                }
            }
        ],
        "photo": "42d03bb8-71a0-45c9-9709-99709b63971d",
        "additionalFields": {
            "schema": null,
            "version": null,
            "fields": [
                {
                    "key": "SOCIAL_CATEGORY",
                    "value": "SC"
                }
            ]
        },
        "isDeleted": false,
        "rowVersion": 2,
        "auditDetails": {
            "createdBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
            "lastModifiedBy": "1b348954-c257-4d18-afac-1b19fc3d86da",
            "createdTime": 1679939870693,
            "lastModifiedTime": 1679942656558
        }
    }
]

export const updateWageSeekerFormDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, wageSeekerData, tenantId, headerLocale, ULBOptions }) => {

    const individual = dummyData[0]  //wageSeekerData?.individual
    const bankAccountDetails = wageSeekerData?.bankDetails?.[0]?.bankAccountDetails?.[0]

    const adhaar = individual?.identifiers?.find(item => item?.identifierType === 'AADHAAR')
    const socialCategory = individual?.additionalFields?.fields?.find(item => item?.key === "SOCIAL_CATEGORY")
    const skills = individual?.skills?.length > 0 ? individual?.skills?.map(skill => ({code: `${skill?.level}.${skill?.type}`, name: `COMMON_MASTERS_SKILLS_${skill?.level}.${skill?.type}`})) : ""
    //const photo = await Digit.UploadServices.Filefetch([individual?.photo], tenantId);
    //console.log('photo', photo);

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
        "profile.jpeg", { 
                file: {}, 
                fileStoreId: { fileStoreId: individual?.photo, tenantId }
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
}

/*
 "Individual": {
        "id": "50e94705-8218-4b41-ab1b-0db496f231f8",
        "tenantId": "pg.citya",
        "name": {
            "givenName": "Test User2"
        },
        "dateOfBirth": "05/10/1995",
        "gender": "FEMALE",
        "mobileNumber": "9898986544",
        "address": [
            {
                "id": "14f6acc2-d282-412b-b489-29bcb2c6c51d",
                "individualId": "50e94705-8218-4b41-ab1b-0db496f231f8",
                "tenantId": "pg.citya",
                "doorNo": "507",
                "type": "PERMANENT",
                "street": "MG Road1",
                "locality": {
                    "code": "SUN11"
                },
                "ward": {
                    "code": "B2"
                }
            }
        ],
        "fatherName": "Test Father1",
        "husbandName": null,
        "relationship": "FATHER", 
        "skills": [
            {
                "id": "2169eafb-9a98-4c2b-989b-93b167c4787b",
                "individualId": "50eabc90-d808-4eb9-8c9b-1d9a274e53fc",
                "type": "MALE_MULIA",
                "level": "UNSKILLED"
            }
        ],
        "photo": "42d03bb8-71a0-45c9-9709-99709b63971d",
        "additionalFields": {
            "fields": [
                {
                    "key": "SOCIAL_CATEGORY",
                    "value": "SC"
                }
            ]
        },
        "rowVersion": 2
    }
    */
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
    Individual.skills = [] //update
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

export const getBankAccountUpdatePayload = ({}) => {
    //{formData, wageSeekerDataFromAPI, tenantId, isModify}
    let bankAccounts = []
    return bankAccounts
}   