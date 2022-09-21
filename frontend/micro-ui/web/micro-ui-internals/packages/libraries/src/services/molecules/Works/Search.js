import cloneDeep from "lodash/cloneDeep";
import _ from "lodash";
import { WorksService } from "../../elements/Works";


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
        const response = sampleEstimateSearchResponse
        //actual response
        const responseStatic = await WorksService?.estimateSearch({tenantId,filters})
        return response?.estimates
    },
    searchLOI: async (tenantId,filters={}) => {
        //dymmy response
        const response = sampleLOISearchResponse
        //actual response
        //const response = await WorksService?.loiSearch({tenantId,filters})
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
    viewLOIScreen: async (t, tenantId, loiNumber,estimateNumber="") => {
        
         const loiArr = await WorksSearch.searchLOI(tenantId,{loiNumber})
         const loi = loiArr?.[0]
        //const loi = sampleLOISearchResponse?.letterOfIndents?.[0]
        //const estimate = sampleEstimateSearchResponse?.estimates?.[0]
        const estimateArr = await WorksSearch?.searchEstimate(tenantId, { estimateNumber })
        const estimate = estimateArr?.[0]
        const loiDetails = {
            title: "WORKS_LOI_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_LOI_NUMBER", value: loi?.letterOfIndentNumber || t("NA") },
                { title: "WORKS_DATE_CREATED", value: convertEpochToDate(loi?.auditDetails?.createdTime) || t("NA") },
                { title: "WORKS_ESTIMATE_NO", value: estimate?.estimateNumber || t("NA") },
                { title: "WORKS_SUB_ESTIMATE_NO", value:estimate?.estimateDetails?.estimateDetailNumber || t("NA") },
                { title: "WORKS_NAME_OF_WORK", value: estimate?.natureOfWork || t("NA") },
                { title: "WORKS_DEPARTMENT", value: estimate?.department || t("NA") },
                { title: "WORKS_FILE_NO", value: loi?.fileNumber || t("NA") },
                { title: "WORKS_FILE_DATE", value: estimate?.natureOfWork || t("NA") },
            ]
        };
        const financialDetails = {
            title: "WORKS_FINANCIAL_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_ESTIMATED_AMT", value: estimate?.proposalDate || t("NA") },
                { title: "WORKS_FINALIZED_PER", value: estimate?.department || t("NA") },
                { title: "WORKS_AGREEMENT_AMT", value: estimate?.requirementNumber || t("NA") },
            ]
        }
        const agreementDetails = {
            title: "WORKS_AGGREEMENT_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_AGENCY_NAME", value: estimate?.proposalDate || t("NA") },
                { title: "WORKS_CONT_ID", value: estimate?.department || t("NA") },
                { title: "WORKS_PREPARED_BY", value: estimate?.requirementNumber || t("NA") },
                { title: "WORKS_ADD_SECURITY_DP", value: t("NA") },
                { title: "WORKS_BANK_G", value: estimate?.location || t("NA") },
                { title: "WORKS_EMD", value: estimate?.workCategory || t("NA") },
                { title: "WORKS_INCHARGE_ENGG", value: estimate?.beneficiaryType || t("NA") },
            ]
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

        let details = [loiDetails,financialDetails,agreementDetails,documentDetails]
        return {
            applicationDetails: details,
        }
    }
}