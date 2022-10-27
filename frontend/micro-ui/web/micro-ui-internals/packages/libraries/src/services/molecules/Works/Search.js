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

        const workflowDetails = await WorksSearch.workflowDataDetails(tenantId, estimateNumber);

        const estimateArr = await WorksSearch?.searchEstimate(tenantId, { estimateNumber })
        const estimate = estimateArr?.[0]
        let wardLocation =estimate?.location.replace(/(^:)|(:$)/g, '').split(":")
        const additionalDetails = estimate?.additionalDetails
        //const estimate = sampleEstimateSearchResponse?.estimates?.[0] 
        let details = []
        const estimateValues={
            title: "WORKS_ESTIMATE_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_ESTIMATE_ID", value: estimate?.estimateNumber},
                { title: "WORKS_STATUS", value: estimate?.estimateStatus}
            ]
        }

        const estimateDetails = {
            title: "WORKS_ESTIMATE_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_DATE_PROPOSAL", value: Digit.DateUtils.ConvertEpochToDate(estimate?.proposalDate) || t("NA") },
                { title: "WORKS_DEPARTMENT", value: t(`ES_COMMON_${estimate?.department}`) || t("NA") },
                { title: "WORKS_LOR", value: estimate?.requirementNumber || t("NA") },
                { title: "WORKS_ELECTION_WARD", value: t(`ES_COMMON_${wardLocation[4]}`) || t("NA") },
                { title: "WORKS_LOCATION", value: (`ES_COMMON_${wardLocation[5]}`) || t("NA") },
                { title: "WORKS_WORK_CATEGORY", value: estimate?.workCategory || t("NA") },
                { title: "WORKS_BENEFICIERY", value: estimate?.beneficiaryType || t("NA") },
                { title: "WORKS_WORK_NATURE", value: estimate?.natureOfWork || t("NA") },
                { title: "WORKS_WORK_TYPE", value: estimate?.typeOfWork || t("NA") },
                { title: "WORKS_SUB_TYPE_WORK", value: t(`ES_COMMON_${estimate?.subTypeOfWork}`) || t("NA") },
                { title: "WORKS_MODE_OF_INS", value: estimate?.entrustmentMode || t("NA") },
            ]
        };

        const financialDetails = {
            title: "WORKS_FINANCIAL_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_FUND", value: t(`ES_COMMON_FUND_${estimate?.fund}`) || t("NA") },
                { title: "WORKS_FUNCTION", value: t(`ES_COMMON_${estimate?.function}`) || t("NA") },
                { title: "WORKS_BUDGET_HEAD", value: t(`ES_COMMON_${estimate?.budgetHead}`) || t("NA") },
                { title: "WORKS_SCHEME", value: t(`ES_COMMON_${estimate?.scheme}`) || t("NA") },
                { title: "WORKS_SUB_SCHEME", value: t(`ES_COMMON_${estimate?.subScheme}`) || t("NA") },
            ]
        };

        const tableHeader = [t("WORKS_SNO"), t("WORKS_NAME_OF_WORK"), t("WORKS_ESTIMATED_AMT")]
        const tableRows = [["1", "Construction of CC drain from D No 45-142-A-58-A to 45-142-472-A at Venkateramana Colony in Ward No 43", "640000"], ["", "Total Amount", "640000"]]

        const workDetails = {
            title: "WORKS_WORK_DETAILS",
            asSectionHeader: true,
            isTable: true,
            headers: tableHeader,
            tableRows:estimate?.estimateDetails.map((item,index)=>
                    [index+1,
                    item?.name,
                    item?.amount]
            )
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


        details = [...details, estimateValues, estimateDetails, financialDetails, workDetails, documentDetails]
        return {
            applicationDetails: details,
            processInstancesDetails: workflowDetails?.ProcessInstances,
            applicationData:estimate,
        }
    },
    workflowDataDetails: async (tenantId, businessIds) => {
        const response = await Digit.WorkflowService.getByBusinessId(tenantId, businessIds);
        return response;
    },
    viewLOIScreen: async (t, tenantId, loiNumber,subEstimateNumber) => {
        
        
        const workflowDetails = await WorksSearch.workflowDataDetails(tenantId, loiNumber);

        const loiArr = await WorksSearch.searchLOI(tenantId, {letterOfIndentNumber:loiNumber})
         const loi = loiArr?.[0]
        //const loi = sampleLOISearchResponse?.letterOfIndents?.[0]
        //const estimate = sampleEstimateSearchResponse?.estimates?.[0]
        const estimateArr = await WorksSearch?.searchEstimate(tenantId, { estimateDetailNumber:subEstimateNumber })
        const estimate = estimateArr?.[0]   
       
        const additionalDetails = loi?.additionalDetails
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
                { title: "WORKS_SUB_ESTIMATE_NO", value: subEstimateNumber },
                { title: "WORKS_NAME_OF_WORK", value: estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber === subEstimateNumber)?.[0]?.name || t("NA") },
                { title: "WORKS_DEPARTMENT", value: t(`ES_COMMON_${estimate?.department}`) || t("NA") },
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
                { title: "WORKS_AGENCY_NAME", value: t("NA") },
                { title: "WORKS_CONT_ID", value: loi?.contractorId || t("NA") },
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
            processInstancesDetails: workflowDetails?.ProcessInstances,
            applicationData:loi,
        }
    }
}