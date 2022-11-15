import _ from "lodash";
// import { WorksService } from "../../elements/Works";


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

let sampleContractSearchResponse = {
    status: "success",
    isSuccess: true,
    totalCount: 10,
    isLoading: false,
    data:{
      contracts: [{
        tenantId:"pb.amritsar",
        contractId: "1136/TO/DB/FLOOD/10-11",
        contractDate: "08/09/2010",
        contractType: "Work Order",
        nameOfTheWork: "Providing CC Drain in Birla Gaddah (Tungabhaqdra workers colony) in 27th ward",
        abstractEstimateNumber: "EST/KRPN/1136",
        estimateNumber:"est/ns/2039",
        subEstimateNumber:"wn/ns/2039",
        fileNumber:"14-9/GF/knl/EE-II/14-15",
        fileDate:"10/11/2014",
        agreementAmount:"1908500.00",
        implementingAuthority:"Organisation",
        nameOfOrgn:"Maa Bhagavati Org",
        orgnId:"KMC149",
        preparedBy:"M.Nasir",
        additionalSecurityDeposit:"",
        bankGuarantee:"",
        emdAmount:"",
        engineerIncharge: "S.A Bhasha",
        sla:"15 days",
        status:"Approved"
      }]
    }
}

export const ContractSearch = {
    searchContract: async (tenantId="pb.jalandhar", filters = {} ) => {
        
        // dymmy response
        const response = sampleContractSearchResponse
        //actual response
        // const response = await WorksService?.estimateSearch({tenantId,filters})
        // return response?.estimates
    },
    viewContractScreen: async (t, tenantId, loiNumber,subEstimateNumber) => {
        
        
        // const workflowDetails = await WorksSearch.workflowDataDetails(tenantId, loiNumber);

        // const loiArr = await WorksSearch.searchLOI(tenantId, {letterOfIndentNumber:loiNumber})
        //  const loi = loiArr?.[0]
        const contract = sampleContractSearchResponse?.data?.contracts[0]
        // const additionalDetails = loi?.additionalDetails
        // const userInfo = Digit.UserService.getUser()?.info || {};
        // const uuidUser = userInfo?.uuid;
        // const {user:users} = await Digit.UserService.userSearch(tenantId, { uuid: [loi?.oicId] }, {});
        // const usersResponse = await HrmsService.search(tenantId,{codes: loi?.oicId }, {});
        // const user = users?.[0]
        // console.log(usersResponse);
        
        const contractDetails = {
            title: "WORKS_CONTRACT_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_CONTRACT_ID", value: contract?.contractId || t("NA") },
                // { title: "WORKS_DATE_CREATED", value: convertEpochToDate(contract?.auditDetails?.createdTime) || t("NA") },
                {title: "WORKS_DATE_CREATED", value: contract?.contractDate || t("NA") }, 
                { title: "WORKS_NAME_OF_WORK", value: contract?.nameOfTheWork || t("NA") },
                { title: "WORKS_ESTIMATE_NO", value: contract?.estimateNumber },
                // { title: "WORKS_SUB_ESTIMATE_NO", value: estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber === subEstimateNumber)?.[0]?.name || t("NA") },
                {title:"WORKS_SUB_ESTIMATE_NO", value: contract?.subEstimateNumber || t("NA")},
                { title: "WORKS_FILE_NO", value: contract?.fileNumber || t("NA") },
                { title: "WORKS_FILE_DATE", value: contract?.fileDate || t("NA") },
                // { title: "WORKS_FILE_DATE", value: convertEpochToDate(contract?.fileDate) || t("NA") },
                { title: "WORKS_CONTRACT_TYPE", value: contract?.contractType || t("NA") },
                { title: "WORKS_STATUS", value: contract?.status || t("NA") },
            ]
        };

        // const agreementAmount = subEs?.amount + ((parseInt(loi?.negotiatedPercentage) * subEs?.amount) / 100)

        const financialDetails = {
            title: "WORKS_FINANCIAL_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_AGREEMENT_AMT", value: contract.agreementAmount || t("NA") },
            ]
        }
        const agreementDetails = {
            title: "WORKS_AGGREEMENT_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "WORKS_IMPLEMENT_AUTH", value: contract?.implementingAuthority|| t("NA") },
                { title: "WORKS_NAME_OF_ORGN", value: contract?.nameOfOrgn|| t("NA") },
                { title: "WORKS_ORGN_ID", value: contract?.orgnId|| t("NA") },
                { title: "WORKS_PREPARED_BY", value:contract?.preparedBy ||  t("NA") },
                { title: "WORKS_ADD_SECURITY_DP", value:contract?.additionalSecurityDeposit || t("NA") },
                { title: "WORKS_BANK_G", value: contract?.bankGuarantee || t("NA") },
                { title: "WORKS_EMD", value: contract?.emdAmount || t("NA") },
                { title: "WORKS_INCHARGE_ENGG", value: contract?.engineerIncharge || t("NA") },
                // { title: "WORKS_INCHARGE_ENGG", value: additionalDetails?.oic?.nameOfEmp || t("NA") },
            ]
        }
        // const files = additionalDetails?.filesAttached
        

        const documentDetails = {
            title: "",
            asSectionHeader: true,
            additionalDetails: {
                documents: [{
                    title: "WORKS_RELEVANT_DOCS",
                    BS: 'Works',
                    // values: files?.map((document) => {
                    //     return {
                    //         title: document?.fileName,
                    //         documentType: document?.documentType,
                    //         documentUid: document?.fileStoreId,
                    //         fileStoreId: document?.fileStoreId,
                    //     };
                    // }),
                },
                ]
            }
        }

        let details = [contractDetails,financialDetails,agreementDetails,documentDetails]
        return {
            applicationDetails: details,
            // processInstancesDetails: workflowDetails?.ProcessInstances,
            applicationData:contract,
        }
    }
}