import cloneDeep from "lodash/cloneDeep";
import _ from "lodash";
import { WorksService } from "../../elements/Works";
import HrmsService from "../../elements/HRMS";


const convertEpochToDate = (dateEpoch) => {
    if (dateEpoch == null || dateEpoch == undefined || dateEpoch == "") {
        return "NA";
    }
    const dateFromApi = new Date(dateEpoch);
    let month = dateFromApi.getMonth() + 1;
    let day = dateFromApi.getDate();
    let year = dateFromApi.getFullYear();
    month = (month > 9 ? "" : "0") + month;
    day = (day > 9 ? "" : "0") + day;
    return `${day}/${month}/${year}`;
};

const sampleEstimateSearchResponse = {
    "responseInfo": {
        "apiInfo": {
            "id": "string",
            "version": "string",
            "path": "string"
        },
        "deviceDetail": {
            "id": "string",
            "signature": "string"
        },
        "ts": 0,
        "action": "string",
        "key": "string",
        "msgId": "string",
        "requesterId": "string",
        "authToken": "string",
        "userInfo": {
            "tenantId": "string",
            "uuid": "string",
            "userName": "string",
            "password": "string",
            "idToken": "string",
            "mobile": "string",
            "email": "string",
            "primaryrole": [
                {
                    "name": "string",
                    "code": "string",
                    "tenantId": "string",
                    "description": "string"
                }
            ],
            "additionalroles": [
                {
                    "tenantId": "string",
                    "roles": [
                        {
                            "name": "string",
                            "code": "string",
                            "tenantId": "string",
                            "description": "string"
                        }
                    ]
                }
            ]
        },
        "correlationId": "string",
        "signature": "string"
    },
    "estimates": [
        {
            "id": "251c51eb-e970-4e01-a99a-70136c47a934",
            "tenantId": "pb.jalandhar OR dwss",
            "estimateNumber": "EST/2022-23/010",
            "adminSanctionNumber": "ASE/2022-23/110",
            "proposalDate": 1658222690000,
            "status": "ACTIVE",
            "estimateStatus": "CREATED",
            "subject": "Construct new schools",
            "requirementNumber": "File-18430283",
            "description": "Construct new schools",
            "department": "string",
            "location": "string",
            "workCategory": "string",
            "beneficiaryType": "string",
            "natureOfWork": "string",
            "typeOfWork": "string",
            "subTypeOfWork": "string",
            "entrustmentMode": "string",
            "fund": "string",
            "function": "string",
            "budgetHead": "string",
            "scheme": "string",
            "subScheme": "string",
            "totalAmount": 0,
            "estimateDetails": [
                {
                    "id": "251c51eb-e970-4e01-a99a-70136c47a934",
                    "estimateDetailNumber": "SUB-EST/2022-23/010",
                    "name": "string",
                    "amount": 0,
                    "additionalDetails": {}
                }
            ],
            "auditDetails": {
                "createdBy": "string",
                "lastModifiedBy": "string",
                "createdTime": 0,
                "lastModifiedTime": 0
            },
            "additionalDetails": {}
        }
    ]
}

const sampleLOISearchResponse = {
    "responseInfo": {
        "apiInfo": {
            "id": "string",
            "version": "string",
            "path": "string"
        },
        "deviceDetail": {
            "id": "string",
            "signature": "string"
        },
        "ts": 0,
        "action": "string",
        "key": "string",
        "msgId": "string",
        "requesterId": "string",
        "authToken": "string",
        "userInfo": {
            "tenantId": "string",
            "uuid": "string",
            "userName": "string",
            "password": "string",
            "idToken": "string",
            "mobile": "string",
            "email": "string",
            "primaryrole": [
                {
                    "name": "string",
                    "code": "string",
                    "tenantId": "string",
                    "description": "string"
                }
            ],
            "additionalroles": [
                {
                    "tenantId": "string",
                    "roles": [
                        {
                            "name": "string",
                            "code": "string",
                            "tenantId": "string",
                            "description": "string"
                        }
                    ]
                }
            ]
        },
        "correlationId": "string",
        "signature": "string"
    },
    "letterOfIndents": [
        {
            "tenantId": "pb.jalandhar OR dwss",
            "id": "251c51eb-e970-4e01-a99a-70136c47a934",
            "letterOfIndentNumber": "LOI/2022-23/010",
            "workPackageNumber": "WP/2022-23/010",
            "workIdentificationNumber": "string",
            "fileNumber": "string",
            "negotiatedPercentage": 40,
            "contractorId": "251c51eb-e970-4e01-a99a-70136c47a934",
            "securityDeposit": 100000,
            "bankGuarantee": "string",
            "emdAmount": 1.5,
            "contractPeriod": 1.5,
            "defectLiabilityPeriod": 1.5,
            "oicId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "status": "DRAFT",
            "letterStatus": "CREATED",
            "auditDetails": {
                "createdBy": "string",
                "lastModifiedBy": "string",
                "createdTime": 1660206057000,
                "lastModifiedTime": 0
            }
        }
    ]
}


export const WorksSearch = {
    searchEstimate: async (tenantId="pb.jalandhar", filters = {} ) => {
        
        //dymmy response
        //const response = sampleEstimateSearchResponse
        //actual response
        const response = await WorksService?.estimateSearch({tenantId,filters})
        return response?.estimates
    },
    searchLOI: async (tenantId,filters={}) => {
        //dymmy response
        
        //const response = sampleLOISearchResponse
        //actual response
        const response = await WorksService?.loiSearch({tenantId,filters})
        return response?.letterOfIndents
    },
    viewEstimateScreen: async (t, tenantId, estimateNumber) => {
        const estimateArr = await WorksSearch?.searchEstimate(tenantId, { estimateNumber })
        const estimate = estimateArr?.[0]
        
        //const estimate = sampleEstimateSearchResponse?.estimates?.[0] 
        let details = []
        const estimateDetails = {
            title: "WORKS_ESTIMATE_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_DATE_PROPOSAL", value: estimate?.proposalDate || t("NA") },
                { title: "WORKS_DEPARTMENT", value: estimate?.department || t("NA") },
                { title: "WORKS_LOR", value: estimate?.requirementNumber || t("NA") },
                { title: "WORKS_ELECTION_WARD", value: t("NA") },
                { title: "WORKS_LOCATION", value: estimate?.location || t("NA") },
                { title: "WORKS_WORK_CATEGORY", value: estimate?.workCategory || t("NA") },
                { title: "WORKS_BENEFICIERY", value: estimate?.beneficiaryType || t("NA") },
                { title: "WORKS_WORK_NATURE", value: estimate?.natureOfWork || t("NA") },
                { title: "WORKS_WORK_TYPE", value: estimate?.typeOfWork || t("NA") },
                { title: "WORKS_SUB_TYPE_WORK", value: estimate?.subTypeOfWork || t("NA") },
                { title: "WORKS_MODE_OF_INS", value: estimate?.entrustmentMode || t("NA") },
            ]
        };

        const financialDetails = {
            title: "WORKS_FINANCIAL_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_FUND", value: estimate?.fund || t("NA") },
                { title: "WORKS_FUNCTION", value: estimate?.function || t("NA") },
                { title: "WORKS_BUDGET_HEAD", value: estimate?.budgetHead || t("NA") },
                { title: "WORKS_SCHEME", value: estimate?.scheme || t("NA") },
                { title: "WORKS_SUB_SCHEME", value: estimate?.subScheme || t("NA") },
            ]
        };

        const tableHeader = [t("WORKS_SNO"), t("WORKS_NAME_OF_WORK"), t("WORKS_ESTIMATED_AMT")]
        const tableRows = [["1", "Construction of CC drain from D No 45-142-A-58-A to 45-142-472-A at Venkateramana Colony in Ward No 43", "640000"], ["", "Total Amount", "640000"]]

        const workDetails = {
            title: "WORKS_WORK_DETAILS",
            asSectionHeader: true,
            isTable: true,
            headers: tableHeader,
            tableRows
        }

        const files = [
            {
                "fileStoreId": "81d1bce2-7513-4231-b1ab-8f7c3df37c9b",
                "tenantId": "pb"
            },
            {
                "fileStoreId": "954952fe-6d3e-484c-a773-77f20da639dc",
                "tenantId": "pb"
            },
            {
                "fileStoreId": "eb68a8ca-59bf-4e7f-b604-1c1197bb91c3",
                "tenantId": "pb"
            }
        ]
        const documentDetails = {
            title: "",
            asSectionHeader: true,
            additionalDetails: {
                documents: [{
                    title: "WORKS_RELEVANT_DOCS",
                    BS: 'Works',
                    values: files?.map((document) => {
                        return {
                            title: `Same`,
                            documentType: "type",
                            documentUid: "id",
                            fileStoreId: document?.fileStoreId,
                        };
                    }),
                },
                ]
            }
        }


        details = [...details, estimateDetails, financialDetails, workDetails, documentDetails]
        return {
            applicationDetails: details,
        }
    },
    viewLOIScreen: async (t, tenantId, loiNumber,subEstimateNumber) => {
        //debugger
        const loiArr = await WorksSearch.searchLOI(tenantId, {letterOfIndentNumber:loiNumber})
         const loi = loiArr?.[0]
        //const loi = sampleLOISearchResponse?.letterOfIndents?.[0]
        //const estimate = sampleEstimateSearchResponse?.estimates?.[0]
        const estimateArr = await WorksSearch?.searchEstimate(tenantId, { estimateDetailNumber:subEstimateNumber })
        const estimate = estimateArr?.[0]
         
        const additionalDetails = {
            "filesAttached": [
                {
                    "fileName": "consumer-WS_107_2021-22_226507.pdf",
                    "fileStoreId": "5e062edb-33fc-4505-b3a5-21c5ff7cadac",
                    "documentType": "application/pdf"
                },
                {
                    "fileName": "consumer-PB-CH-2022-07-27-001010.pdf",
                    "fileStoreId": "4cda8865-b903-40f6-ba05-e5a70194f152",
                    "documentType": "application/pdf"
                }
            ],
            "oic": {
                "id": 111,
                "uuid": "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
                "code": "EMP-107-000011",
                "employeeStatus": "EMPLOYED",
                "employeeType": "PERMANENT",
                "dateOfAppointment": 1663200000000,
                "jurisdictions": [
                    {
                        "id": "e4486069-8275-4191-bb26-33dc7d177e6d",
                        "hierarchy": "ADMIN",
                        "boundary": "pb.amritsar",
                        "boundaryType": "City",
                        "tenantId": "pb.amritsar",
                        "auditDetails": {
                            "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                            "createdDate": 1663826773203,
                            "lastModifiedBy": null,
                            "lastModifiedDate": 0
                        },
                        "isActive": true
                    }
                ],
                "assignments": [
                    {
                        "id": "740b9561-0a7b-4bdf-af3a-7e4138aeb2a8",
                        "position": 14,
                        "designation": "DESIG_01",
                        "department": "DEPT_1",
                        "fromDate": 1663804800000,
                        "toDate": null,
                        "govtOrderNumber": null,
                        "tenantid": "pb.amritsar",
                        "reportingTo": null,
                        "auditDetails": {
                            "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                            "createdDate": 1663826773203,
                            "lastModifiedBy": null,
                            "lastModifiedDate": 0
                        },
                        "isHOD": false,
                        "isCurrentAssignment": true
                    }
                ],
                "serviceHistory": [],
                "education": [],
                "tests": [],
                "tenantId": "pb.amritsar",
                "documents": [],
                "deactivationDetails": [],
                "reactivationDetails": [],
                "auditDetails": {
                    "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                    "createdDate": 1663826773203,
                    "lastModifiedBy": null,
                    "lastModifiedDate": 0
                },
                "reActivateEmployee": false,
                "user": {
                    "id": 111,
                    "uuid": "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
                    "userName": "EMP-107-000011",
                    "password": null,
                    "salutation": null,
                    "name": "Estimate Checker",
                    "gender": "MALE",
                    "mobileNumber": "8877665544",
                    "emailId": null,
                    "altContactNumber": null,
                    "pan": null,
                    "aadhaarNumber": null,
                    "permanentAddress": null,
                    "permanentCity": null,
                    "permanentPinCode": null,
                    "correspondenceCity": null,
                    "correspondencePinCode": null,
                    "correspondenceAddress": "None",
                    "active": true,
                    "dob": 778444200000,
                    "pwdExpiryDate": 1671602773000,
                    "locale": null,
                    "type": "EMPLOYEE",
                    "signature": null,
                    "accountLocked": false,
                    "roles": [
                        {
                            "name": "EST_CHECKER",
                            "code": "EST_CHECKER",
                            "description": null,
                            "tenantId": "pb.amritsar"
                        }
                    ],
                    "fatherOrHusbandName": null,
                    "relationship": null,
                    "bloodGroup": null,
                    "identificationMark": null,
                    "photo": null,
                    "createdBy": "93",
                    "createdDate": 1663826773000,
                    "lastModifiedBy": "93",
                    "lastModifiedDate": 1663826773000,
                    "otpReference": null,
                    "tenantId": "pb.amritsar"
                },
                "isActive": true,
                "nameOfEmp": "Estimate Checker"
            },
            "formData": {
                "workIdentificationNumber": "12312321",
                "fileNumber": "2131",
                "negotiatedPercentage": "90",
                "contractorId": "12321",
                "securityDeposit": "123",
                "bankGuarantee": "123",
                "emdAmount": "123",
                "contractPeriod": "12",
                "defectLiabilityPeriod": "12",
                "fileDate": "2022-12-31",
                "agreementDate": "2022-09-29",
                "agencyName": {
                    "name": "agency1"
                },
                "officerIncharge": {
                    "id": 111,
                    "uuid": "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
                    "code": "EMP-107-000011",
                    "employeeStatus": "EMPLOYED",
                    "employeeType": "PERMANENT",
                    "dateOfAppointment": 1663200000000,
                    "jurisdictions": [
                        {
                            "id": "e4486069-8275-4191-bb26-33dc7d177e6d",
                            "hierarchy": "ADMIN",
                            "boundary": "pb.amritsar",
                            "boundaryType": "City",
                            "tenantId": "pb.amritsar",
                            "auditDetails": {
                                "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                                "createdDate": 1663826773203,
                                "lastModifiedBy": null,
                                "lastModifiedDate": 0
                            },
                            "isActive": true
                        }
                    ],
                    "assignments": [
                        {
                            "id": "740b9561-0a7b-4bdf-af3a-7e4138aeb2a8",
                            "position": 14,
                            "designation": "DESIG_01",
                            "department": "DEPT_1",
                            "fromDate": 1663804800000,
                            "toDate": null,
                            "govtOrderNumber": null,
                            "tenantid": "pb.amritsar",
                            "reportingTo": null,
                            "auditDetails": {
                                "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                                "createdDate": 1663826773203,
                                "lastModifiedBy": null,
                                "lastModifiedDate": 0
                            },
                            "isHOD": false,
                            "isCurrentAssignment": true
                        }
                    ],
                    "serviceHistory": [],
                    "education": [],
                    "tests": [],
                    "tenantId": "pb.amritsar",
                    "documents": [],
                    "deactivationDetails": [],
                    "reactivationDetails": [],
                    "auditDetails": {
                        "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                        "createdDate": 1663826773203,
                        "lastModifiedBy": null,
                        "lastModifiedDate": 0
                    },
                    "reActivateEmployee": false,
                    "user": {
                        "id": 111,
                        "uuid": "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
                        "userName": "EMP-107-000011",
                        "password": null,
                        "salutation": null,
                        "name": "Estimate Checker",
                        "gender": "MALE",
                        "mobileNumber": "8877665544",
                        "emailId": null,
                        "altContactNumber": null,
                        "pan": null,
                        "aadhaarNumber": null,
                        "permanentAddress": null,
                        "permanentCity": null,
                        "permanentPinCode": null,
                        "correspondenceCity": null,
                        "correspondencePinCode": null,
                        "correspondenceAddress": "None",
                        "active": true,
                        "dob": 778444200000,
                        "pwdExpiryDate": 1671602773000,
                        "locale": null,
                        "type": "EMPLOYEE",
                        "signature": null,
                        "accountLocked": false,
                        "roles": [
                            {
                                "name": "EST_CHECKER",
                                "code": "EST_CHECKER",
                                "description": null,
                                "tenantId": "pb.amritsar"
                            }
                        ],
                        "fatherOrHusbandName": null,
                        "relationship": null,
                        "bloodGroup": null,
                        "identificationMark": null,
                        "photo": null,
                        "createdBy": "93",
                        "createdDate": 1663826773000,
                        "lastModifiedBy": "93",
                        "lastModifiedDate": 1663826773000,
                        "otpReference": null,
                        "tenantId": "pb.amritsar"
                    },
                    "isActive": true,
                    "nameOfEmp": "Estimate Checker"
                },
                "uploads": [
                    {
                        "fileName": "consumer-WS_107_2021-22_226507.pdf",
                        "fileStoreId": "5e062edb-33fc-4505-b3a5-21c5ff7cadac",
                        "documentType": "application/pdf"
                    },
                    {
                        "fileName": "consumer-PB-CH-2022-07-27-001010.pdf",
                        "fileStoreId": "4cda8865-b903-40f6-ba05-e5a70194f152",
                        "documentType": "application/pdf"
                    }
                ],
                "officerInChargedesig": {
                    "code": "DESIG_01",
                    "name": "Superintending Engineer ( B&R)",
                    "description": "Superintending Engineer ( B&R)",
                    "active": true
                },
                "comments": "sdljkf",
                "appDept": {
                    "name": "Street Lights",
                    "code": "DEPT_1",
                    "active": true
                },
                "appDesig": {
                    "code": "DESIG_02",
                    "name": "Corporation Engineer (B&R)",
                    "description": "Corporation Engineer (B&R)",
                    "active": true
                },
                "app": {
                    "id": 111,
                    "uuid": "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
                    "code": "EMP-107-000011",
                    "employeeStatus": "EMPLOYED",
                    "employeeType": "PERMANENT",
                    "dateOfAppointment": 1663200000000,
                    "jurisdictions": [
                        {
                            "id": "e4486069-8275-4191-bb26-33dc7d177e6d",
                            "hierarchy": "ADMIN",
                            "boundary": "pb.amritsar",
                            "boundaryType": "City",
                            "tenantId": "pb.amritsar",
                            "auditDetails": {
                                "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                                "createdDate": 1663826773203,
                                "lastModifiedBy": null,
                                "lastModifiedDate": 0
                            },
                            "isActive": true
                        }
                    ],
                    "assignments": [
                        {
                            "id": "740b9561-0a7b-4bdf-af3a-7e4138aeb2a8",
                            "position": 14,
                            "designation": "DESIG_01",
                            "department": "DEPT_1",
                            "fromDate": 1663804800000,
                            "toDate": null,
                            "govtOrderNumber": null,
                            "tenantid": "pb.amritsar",
                            "reportingTo": null,
                            "auditDetails": {
                                "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                                "createdDate": 1663826773203,
                                "lastModifiedBy": null,
                                "lastModifiedDate": 0
                            },
                            "isHOD": false,
                            "isCurrentAssignment": true
                        }
                    ],
                    "serviceHistory": [],
                    "education": [],
                    "tests": [],
                    "tenantId": "pb.amritsar",
                    "documents": [],
                    "deactivationDetails": [],
                    "reactivationDetails": [],
                    "auditDetails": {
                        "createdBy": "d0f71367-f81c-46a3-be73-258f8ff68b23",
                        "createdDate": 1663826773203,
                        "lastModifiedBy": null,
                        "lastModifiedDate": 0
                    },
                    "reActivateEmployee": false,
                    "user": {
                        "id": 111,
                        "uuid": "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
                        "userName": "EMP-107-000011",
                        "password": null,
                        "salutation": null,
                        "name": "Estimate Checker",
                        "gender": "MALE",
                        "mobileNumber": "8877665544",
                        "emailId": null,
                        "altContactNumber": null,
                        "pan": null,
                        "aadhaarNumber": null,
                        "permanentAddress": null,
                        "permanentCity": null,
                        "permanentPinCode": null,
                        "correspondenceCity": null,
                        "correspondencePinCode": null,
                        "correspondenceAddress": "None",
                        "active": true,
                        "dob": 778444200000,
                        "pwdExpiryDate": 1671602773000,
                        "locale": null,
                        "type": "EMPLOYEE",
                        "signature": null,
                        "accountLocked": false,
                        "roles": [
                            {
                                "name": "EST_CHECKER",
                                "code": "EST_CHECKER",
                                "description": null,
                                "tenantId": "pb.amritsar"
                            }
                        ],
                        "fatherOrHusbandName": null,
                        "relationship": null,
                        "bloodGroup": null,
                        "identificationMark": null,
                        "photo": null,
                        "createdBy": "93",
                        "createdDate": 1663826773000,
                        "lastModifiedBy": "93",
                        "lastModifiedDate": 1663826773000,
                        "otpReference": null,
                        "tenantId": "pb.amritsar"
                    },
                    "isActive": true,
                    "nameOfEmp": "Estimate Checker"
                }
            }
        }
        // const userInfo = Digit.UserService.getUser()?.info || {};
        // const uuidUser = userInfo?.uuid;
        // const {user:users} = await Digit.UserService.userSearch(tenantId, { uuid: [loi?.oicId] }, {});
        // const usersResponse = await HrmsService.search(tenantId,{codes: loi?.oicId }, {});
        // const user = users?.[0]
        // console.log(usersResponse);
        
        const loiDetails = {
            title: "WORKS_LOI_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_LOI_NUMBER", value: loi?.letterOfIndentNumber || t("NA") },
                { title: "WORKS_DATE_CREATED", value: convertEpochToDate(loi?.auditDetails?.createdTime) || t("NA") },
                { title: "WORKS_ESTIMATE_NO", value: estimate?.estimateNumber || t("NA") },
                { title: "WORKS_SUB_ESTIMATE_NO", value: estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber===subEstimateNumber)?.estimateDetailNumber || t("NA") },
                { title: "WORKS_NAME_OF_WORK", value: estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber === subEstimateNumber)?.name || t("NA") },
                { title: "WORKS_DEPARTMENT", value: estimate?.department || t("NA") },
                { title: "WORKS_FILE_NO", value: loi?.fileNumber || t("NA") },
                { title: "WORKS_FILE_DATE", value: convertEpochToDate(loi?.fileDate) || t("NA") },
            ]
        };

        const subEs = estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber === subEstimateNumber)?.[0]

        const agreementAmount = subEs?.amount + ((parseInt(loi?.negotiatedPercentage) * subEs?.amount) / 100)


        const financialDetails = {
            title: "WORKS_FINANCIAL_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_ESTIMATED_AMT", value: estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber === subEstimateNumber)?.[0]?.amount || t("NA") },
                { title: "WORKS_FINALIZED_PER", value: loi?.negotiatedPercentage || t("NA") },
                { title: "WORKS_AGREEMENT_AMT", value: agreementAmount || t("NA") },
            ]
        }
        const agreementDetails = {
            title: "WORKS_AGGREEMENT_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_AGENCY_NAME", value:  t("NA") },
                { title: "WORKS_CONT_ID", value:  t("NA") },
                { title: "WORKS_PREPARED_BY", value:  t("NA") },
                { title: "WORKS_ADD_SECURITY_DP", value:loi?.securityDeposit || t("NA") },
                { title: "WORKS_BANK_G", value: loi?.bankGuarantee || t("NA") },
                { title: "WORKS_EMD", value: loi?.emdAmount || t("NA") },
                { title: "WORKS_INCHARGE_ENGG", value: additionalDetails?.oic?.nameOfEmp || t("NA") },
            ]
        }
        const files = additionalDetails?.filesAttached
        

        const documentDetails = {
            title: "",
            asSectionHeader: true,
            additionalDetails: {
                documents: [{
                    title: "WORKS_RELEVANT_DOCS",
                    BS: 'Works',
                    values: files?.map((document) => {
                        return {
                            title: document?.fileName,
                            documentType: document?.documentType,
                            documentUid: document?.fileStoreId,
                            fileStoreId: document?.fileStoreId,
                        };
                    }),
                },
                ]
            }
        }

        let details = [loiDetails,financialDetails,agreementDetails,documentDetails]
        return {
            applicationDetails: details,
        }
    }
}