import { ContractService } from "../../elements/Contracts";

const dummyData = {
        "ResponseInfo": {
            "apiId": "mukta-services",
            "ver": null,
            "ts": null,
            "resMsgId": "uief87324",
            "msgId": "Search Contract",
            "status": "successful"
        },
        "contracts": [
            {
                "id": "43763685-221c-40fc-b193-0ce34f4989ff",
                "contractNumber": "WO/2022-23/000329",
                "tenantId": "pg.citya",
                "wfStatus": "CREATED",
                "executingAuthority": "IA",
                "contractType": "CON-01",
                "totalContractedAmount": 0,
                "securityDeposit": 0,
                "agreementDate": 0,
                "issueDate": null,
                "defectLiabilityPeriod": 0,
                "orgId": "string",
                "startDate": 0,
                "endDate": 0,
                "completionPeriod": 5,
                "status": "ACTIVE",
                "lineItems": [
                    {
                        "id": "b59fba59-07e5-434c-b919-a945ba8395c2",
                        "estimateId": "a53bb314-6c48-485a-9772-792b95635012",
                        "estimateLineItemId": "bc4d07a2-270c-40c0-8976-0403749a72d8",
                        "tenantId": "pg.citya",
                        "unitRate": 10.31,
                        "noOfunit": 3.5,
                        "category": null,
                        "name": null,
                        "status": "ACTIVE",
                        "amountBreakups": [
                            {
                                "id": "4434335d-ff70-4188-8a74-f20397c9be75",
                                "estimateAmountBreakupId": "9be4a63e-925e-4833-bc9f-73bae5b059cc",
                                "amount": 9888.9845,
                                "status": "ACTIVE",
                                "additionalDetails": null
                            }
                        ],
                        "auditDetails": {
                            "createdBy": "cb1601ec-5921-447d-9040-9a759ddddbd9",
                            "lastModifiedBy": "cb1601ec-5921-447d-9040-9a759ddddbd9",
                            "createdTime": 1678774407253,
                            "lastModifiedTime": 1678774407253
                        },
                        "additionalDetails": null
                    }
                ],
                "documents": [
                    {
                        "contractId": "43763685-221c-40fc-b193-0ce34f4989ff",
                        "id": "bab5f095-d294-4f81-966b-293539fff282",
                        "documentType": "Terms And Conditions",
                        "fileStore": "string",
                        "documentUid": "string",
                        "status": "ACTIVE",
                        "additionalDetails": null
                    }
                ],
                "processInstance": null,
                "auditDetails": {
                    "createdBy": "cb1601ec-5921-447d-9040-9a759ddddbd9",
                    "lastModifiedBy": "cb1601ec-5921-447d-9040-9a759ddddbd9",
                    "createdTime": 1678774407253,
                    "lastModifiedTime": 1678774407253
                },
                "additionalDetails": {
                    "projectId": 123,
                    "projectType": "NewA",
                    "officerInChargeId": "officer-in-charge"
                }
            }
        ],
        "pagination": {
            "limit": 10,
            "offSet": 0,
            "totalCount": 1,
            "sortBy": "id",
            "order": "desc"
        }
}

const transformViewDataToApplicationDetails = async (t, data, workflowDetails, tenantId) => {
    if(data?.contracts?.length === 0) return;
    const contract = data.contracts?.[0]
    console.log("DATA :", contract);
    const contractDetails = {
        title: " ",
        asSectionHeader: false,
        values: [
            { title: "COMMON_NAME_OF_CBO", value: contract?.additionalDetails?.cboName || t("NA")},
            { title: "WORKS_ORGN_ID", value: contract?.orgId || t("NA")},
            { title: "COMMON_ROLE_OF_CBO", value: 'Implementation Agency' || t("NA")},
            { title: "COMMON_DESGN_OF_OFFICER_IN_CHARGE", value: 'Junior Engineer' || t("NA")},
            { title: "COMMON_NAME_OF_OFFICER_IN_CHARGE", value: 'S Srinivas' || t("NA")},
            { title: "COMMON_PROJECT_COMP_PERIOD_DAYS", value: contract?.completionPeriod || t("NA")},
            { title: "COMMON_WORK_ORDER_AMT_RS", value: contract?.totalContractedAmount || t("NA")},
        ]
    }

    const documentDetails = {
        title: "",
        asSectionHeader: true,
        additionalDetails: {
            documents: [{
                title: "WORKS_RELEVANT_DOCUMENTS",
                BS : 'Works',
                values: contract?.documents?.map((document) => {
                    return {
                        title: document?.documentType,
                        documentType: document?.documentType,
                        documentUid: document?.documentUid,
                        fileStoreId: document?.fileStore,
                    };
                }),
            },
            ]
        }
    }


    const applicationDetails = { applicationDetails: [contractDetails, documentDetails] };

  return {
    applicationDetails,
    applicationData: contract,
    processInstancesDetails: workflowDetails?.ProcessInstances,
    workflowDetails
  }
} 

// const workflowDataDetails = async (tenantId, businessIds) => {
//     const response = await Digit.WorkflowService.getByBusinessId(tenantId, businessIds);
//     return response
// }

export const View = {
    fetchContractDetails: async (t, tenantId, data, searchParams) => {

    //return transformViewDataToApplicationDetails(t, dummyData, tenantId) 
    try {
        const response = await ContractService.search(tenantId, data, searchParams);
        console.log('response', response);
        return transformViewDataToApplicationDetails(t, response)
        } catch (error) {
            console.log('error', error);
            throw new Error(error?.response?.data?.Errors[0].message);
        }  
    }
}

