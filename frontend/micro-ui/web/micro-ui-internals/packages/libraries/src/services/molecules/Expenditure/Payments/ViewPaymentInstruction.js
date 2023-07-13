import BillingService from "../../../elements/Bill";
import { ContractService } from "../../../elements/Contracts";
import AttendanceService from "../../../elements/Attendance";
import { ExpenseService } from "../../../elements/Expense";
import {WageSeekerService} from "../../../elements/WageSeeker"
import {BankAccountService} from "../../../elements/BankAccount"
import { OrganisationService } from "../../../elements/Organisation";

const transformViewDataToApplicationDetails = async (t, paymentInstruction, tenantId) => {
  
   if(!paymentInstruction) throw new Error('No data found');

  // const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())


  //get contract details
  // const contractPayload = {
  //   tenantId,
  //   contractNumber: workOrderNum
  // }
  
  let billUrl = ""
  if(paymentInstruction?.additionalDetails?.billNumber?.[0]?.includes("PB")){
    billUrl = `purchase-bill-details?tenantId=${paymentInstruction?.tenantId}&billNumber=${paymentInstruction?.additionalDetails?.billNumber}`
  }
  if(paymentInstruction?.additionalDetails?.billNumber?.[0]?.includes("SB")){
    billUrl = `supervision-bill-details?tenantId=${paymentInstruction?.tenantId}&billNumber=${paymentInstruction?.additionalDetails?.billNumber}`
  }
  if(paymentInstruction?.additionalDetails?.billNumber?.[0]?.includes("WB")){
    billUrl = `wage-bill-details?tenantId=${paymentInstruction?.tenantId}&billNumber=${paymentInstruction?.additionalDetails?.billNumber}`
  }
  const piDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: paymentInstruction?.additionalDetails?.billNumber?.[0] || t("ES_COMMON_NA"),isLink:true,to:billUrl},
      { title: "WORKS_PI_TYPE", value: t("EXP_PI_TYPE_ORIGINAL") || t("ES_COMMON_NA")},
      { title: "EXP_PARENT_PI_ID", value: paymentInstruction?.parentID || t("ES_COMMON_NA")},
      { title: "EXP_PI_ID", value: paymentInstruction?.jitBillNo || t("ES_COMMON_NA"),isLink:true,to:`view-payment-instruction?tenantId=${paymentInstruction?.tenantId}&piNumber=${paymentInstruction?.jitBillNo}`},
      { title: "EXP_PI_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.auditDetails?.createdTime, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_HEAD_OF_ACCOUNTS", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "EXP_MASTER_ALLT_ID", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "EXP_PAYMENT_GROSS_AMT", value: paymentInstruction?.grossAmount || t("ES_COMMON_NA") },
      { title: "EXP_PAYMENT_NET_AMT", value: paymentInstruction?.netAmount || t("ES_COMMON_NA") },
      { title: "CORE_COMMON_STATUS", value: t(`EXP_PI_STATUS_${paymentInstruction?.piStatus}`)  }

    ]
  }

  const bannerForStatusError = {
    isInfoLabel:true,
    infoHeader:"Error",
    infoText:t("BANNER_TEXT_STATUS_ERROR"),
    infoIconFill:"red",
    style:{
      "backgroundColor":"#EFC7C1",
      "width":"60%"
    }
  }

  const paDetails = {
    title: "EXP_PA_DETAILS",
    asSectionHeader: true,
    values: [
      { title: "EXP_PA_ID", value: paymentInstruction?.paDetails?.[0]?.paAdviceId || t("ES_COMMON_NA")},
      { title: "EXP_PA_DATE", value:Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paDetails?.[0]?.paAdviceDate, 'dd/MM/yyyy') || t("ES_COMMON_NA")},
      { title: "EXP_PA_TOKEN_NO", value: paymentInstruction?.paDetails?.[0]?.paTokenNumber || t("ES_COMMON_NA")},
      { title: "EXP_PA_TOKEN_DATE", value:Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paDetails?.[0]?.paTokenDate, 'dd/MM/yyyy') || t("ES_COMMON_NA")},
      { title: "EXP_PA_ONLINE_BILL_NO", value:paymentInstruction?.paDetails?.[0]?.paBillRefNumber || t("ES_COMMON_NA") }
    ]
  }

  const applicationDetails = { applicationDetails: [piDetails,bannerForStatusError] };


  //there can be three types of beneficiaries(DEPT,ORG,IND)
  // In case of DEPT we are just gonna do a bank acc search with beneficiaryId
  // In case of ORG we are gonna do and org search and a bank acc search
  // In case of IND we are gonna do and IND search and bank acc search

  let orgsToSearch = []
  let IndsToSearch = []
  const beneficiaryDetails = paymentInstruction?.beneficiaryDetails
  const beneficiaryIdsToSearch = beneficiaryDetails?.map(row => {
    if(row?.beneficiaryType === "ORG"){
      orgsToSearch.push(row.beneficiaryId)
    }else if(row?.beneficiaryType === "IND"){
      IndsToSearch.push(row.beneficiaryId)
    }
    return row.beneficiaryId
  })

  let orgPayload = orgsToSearch?.length!==0 ? {
   SearchCriteria:{
    tenantId,
    id:orgsToSearch
   }
  } : null

  let indPayload =IndsToSearch?.length!==0 ? {
    Individual:{
      id:IndsToSearch
    }
  } : null
  
  //bank acc search
  const bankAccPayload = { bankAccountDetails: { tenantId, referenceId: beneficiaryIdsToSearch } }
  const bankResponse = await BankAccountService.search(bankAccPayload, {});
  const bankAccDetails = {}
  bankResponse?.bankAccounts?.forEach(bankAcc => {
    bankAccDetails[bankAcc.referenceId] = bankAcc
  })

  //org search
  const orgResponse = await OrganisationService.search(orgPayload)
  const orgDetails = {}
  if(orgResponse?.organisations?.length > 0) {
    orgResponse?.organisations?.forEach(org=>{
      orgDetails[org.id] = org
    })
  }
  
  //ind search
  const indResponse = await WageSeekerService.search(tenantId, indPayload, {tenantId,offset:0,limit:100});
  const indDetails = {}
  if(indResponse?.Individual?.length > 0) {
    indResponse?.Individual?.forEach(ind => {
      indDetails[ind.id] = ind
    })
  }
  
  

  //ENRICH the beneficiaryDetailsObject using bankAccDetails,orgDetails,indDetails
 
  beneficiaryDetails?.forEach(beneficiary=> {
    beneficiary.bankDetails = bankAccDetails[beneficiary.beneficiaryId]
    beneficiary.orgDetails = orgDetails[beneficiary.beneficiaryId]
    beneficiary.indDetails = indDetails[beneficiary.beneficiaryId]
  })
  
  const returnPaymentStatusObject = (paymentStatus) => {
    return {
      value:t(Digit.Utils.locale.getTransformedLocale(`BILL_STATUS_${paymentStatus}`)),
      type:"paymentStatus",
      styles:paymentStatus==="Payment Success"?{color:"green"}:(paymentStatus==="Payment Failed"?{color:"red"}:{}),
      hoverIcon: paymentStatus==="Payment Failed"?"infoIcon":"",
      iconHoverTooltipText: paymentStatus==="Payment Failed" ? "Err Msg":"",
      toolTipStyles:{}
    }
  }
  
  const tableRows = beneficiaryDetails?.map((beneficiary,idx) => {
    
    if (beneficiary.beneficiaryType === "ORG") {
      return [
        {
          label: beneficiary?.orgDetails?.orgNumber || t("ES_COMMON_NA"),
          type: "link",
          path: `/${window?.contextPath}/employee/masters/view-organization?tenantId=${tenantId}&orgNumber=${beneficiary?.orgDetails?.orgNumber}`,
        },
        beneficiary?.muktaReferenceId || t("ES_COMMON_NA"),
        beneficiary?.orgDetails?.name,
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountNumber || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.bankBranchIdentifier?.code || t("ES_COMMON_NA"),
        returnPaymentStatusObject(beneficiary?.paymentStatus),
        beneficiary?.amount ? `₹ ${beneficiary?.amount}` : t("ES_COMMON_NA"),
      ];
    } else if (beneficiary.beneficiaryType === "IND") {
      return [
        {
          label: beneficiary?.indDetails?.individualId || t("ES_COMMON_NA"),
          type: "link",
          path: `/${window?.contextPath}/employee/masters/view-wageseeker?tenantId=${tenantId}&individualId=${beneficiary?.indDetails?.individualId}`,
        },
        beneficiary?.muktaReferenceId || t("ES_COMMON_NA"),
        beneficiary?.indDetails?.name?.givenName || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountNumber || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.bankBranchIdentifier?.code || t("ES_COMMON_NA"),
        returnPaymentStatusObject(beneficiary?.paymentStatus),
        beneficiary?.amount ? `₹ ${beneficiary?.amount}` : t("ES_COMMON_NA"),
      ];
    } else if (beneficiary.beneficiaryType === "DEPT") {
      return [
        // beneficiary?.beneficiaryId || t("ES_COMMON_NA"),
        t("ES_COMMON_NA"),
        beneficiary?.muktaReferenceId || t("ES_COMMON_NA"),
        t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountNumber,
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.bankBranchIdentifier?.code || t("ES_COMMON_NA"),
        returnPaymentStatusObject(beneficiary?.paymentStatus),
        beneficiary?.amount ? `₹ ${beneficiary?.amount}` : t("ES_COMMON_NA"),
      ];
    }
  })
  
  const tableHeaders = [t("EXP_BENE_ID"), t("EXP_PAYMENT_ID"), t("COMMON_NAME"), t("EXP_AC_NO"), t("EXP_IFSC"), t("CORE_COMMON_STATUS"), t("EXP_PAYMENT_AMT")]

  const beneficiaryTable = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    isTable: true,
    headers: tableHeaders,
    tableRows: tableRows,
    state: {},
    tableStyles:{
        rowStyle:{},
        cellStyle: [{}, {}, {}, {},{},{},{"textAlign":"right"}],
        tableStyle:{backgroundColor:"#FAFAFA"}
    },
    mainDivStyles:{ lineHeight: "19px", minWidth: "280px" }

  }

  const ifAnyFailedPayments = beneficiaryDetails?.some(bene => bene.paymentStatus === "Payment Failed")

  let bannerForFailedPayments = {}

  if(ifAnyFailedPayments){
    bannerForFailedPayments = {
      isInfoLabel:true,
      infoHeader:"Info",
      infoText:t("BANNER_TEXT_FAILED_PAYMENT"),
      // infoIconFill:"red",
      style:{
        // "backgroundColor":"#EFC7C1",
        "width":"80%"
      }
    }
  }
  return [
    {
      applicationDetails,
      applicationData: paymentInstruction,
      processInstancesDetails: {},
      workflowDetails: {}
    },
    {
      applicationDetails:{applicationDetails:[paDetails,beneficiaryTable,bannerForFailedPayments]},
      applicationData: {},
      processInstancesDetails: {},
      workflowDetails: {}
    }
  ]
}

export const ViewPaymentInstruction = {
    fetchPaymentInstruction: async (t, tenantId, data) => {
      try {
        const response = await ExpenseService.searchPayment(data);
        
        //dummy response 
        // const dresponse =  {
        //   "id": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
        //   "tenantId": "pg.citya",
        //   "muktaReferenceId": "EP/0/2023-24/07/06/000046",
        //   "numBeneficiaries": 3,
        //   "grossAmount": 300.00,
        //   "netAmount": 300.00,
        //   "piStatus": "FAILED",
        //   "piErrorResp": "Error!!!",
        //   "additionalDetails": {
        //       "billNumber": [
        //           "PB/2023-24/000458"
        //       ],
        //       "referenceId": [
        //           "WO/2023-24/000775"
        //       ]
        //   },
        //   "auditDetails": {
        //       "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //       "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //       "createdTime": 1688654871867,
        //       "lastModifiedTime": 1688654871867
        //   },
        //   "transactionDetails": [],
        //   "paDetails": [
        //       {
        //           "id": "6c3b8016-e18a-46f4-9f2f-b9d41aa12b42",
        //           "tenantId": "pg.citya",
        //           "muktaReferenceId": "EP/0/2023-24/07/06/000046",
        //           "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
        //           "paBillRefNumber": null,
        //           "paFinYear": null,
        //           "paAdviceId": null,
        //           "paAdviceDate": null,
        //           "paTokenNumber": null,
        //           "paTokenDate": null,
        //           "paErrorMsg": null,
        //           "additionalDetails": null,
        //           "auditDetails": {
        //               "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "createdTime": 1688654871867,
        //               "lastModifiedTime": 1688654871867
        //           }
        //       }
        //   ],
        //   "jitBillNo": "PI-1013/2023-24/000035",
        //   "beneficiaryDetails": [
        //       {
        //           "id": "1310a40c-aee5-4f2d-ae24-e680f1756f03",
        //           "tenantId": "pg.citya",
        //           "muktaReferenceId": "EP/0/2023-24/07/06/000046",
        //           "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
        //           "beneficiaryId": "Deduction_pg.citya_GSTTDS",
        //           "beneficiaryType": "DEPT",
        //           "bankAccountId": "4a3e05fa-3919-45b1-b52e-0b9c5377b2a3",
        //           "amount": 36.00,
        //           "voucherDate": 0,
        //           "paymentStatus": "Payment Initiated",
        //           "benfLineItems": [
        //               {
        //                   "id": "94538804-1269-4a7e-95b2-dcbd5e069c7b",
        //                   "beneficiaryId": "1310a40c-aee5-4f2d-ae24-e680f1756f03",
        //                   "lineItemId": "e2930afa-b947-4164-9651-f7f68a0de61f",
        //                   "auditDetails": {
        //                       "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //                       "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //                       "createdTime": 1688654871867,
        //                       "lastModifiedTime": 1688654871867
        //                   }
        //               }
        //           ],
        //           "auditDetails": {
        //               "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "createdTime": 1688654871867,
        //               "lastModifiedTime": 1688654871867
        //           }
        //       },
        //       {
        //           "id": "550b8088-5bf6-4467-9913-27a30f8b7cf5",
        //           "tenantId": "pg.citya",
        //           "muktaReferenceId": "EP/0/2023-24/07/06/000046",
        //           "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
        //           "beneficiaryId": "Deduction_pg.citya_ECB",
        //           "beneficiaryType": "DEPT",
        //           "amount": 12.00,
        //           "voucherDate": 0,
        //           "paymentStatus": "Payment Initiated",
        //           "benfLineItems": [
        //               {
        //                   "id": "c500e9a6-9b39-4474-89f0-7ec5d846b5aa",
        //                   "beneficiaryId": "550b8088-5bf6-4467-9913-27a30f8b7cf5",
        //                   "lineItemId": "2926aca4-3e99-488f-8885-04961721cd35",
        //                   "auditDetails": {
        //                       "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //                       "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //                       "createdTime": 1688654871867,
        //                       "lastModifiedTime": 1688654871867
        //                   }
        //               }
        //           ],
        //           "auditDetails": {
        //               "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "createdTime": 1688654871867,
        //               "lastModifiedTime": 1688654871867
        //           }
        //       },
        //       {
        //           "id": "872f1f25-88be-4bd1-9d66-b2957ea726e2",
        //           "tenantId": "pg.citya",
        //           "muktaReferenceId": "EP/0/2023-24/07/06/000046",
        //           "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
        //           "beneficiaryId": "d17ba578-aa56-4834-b415-6eab7f3b9ebd",
        //           "beneficiaryType": "ORG",
        //           "bankAccountId": "1e52c7af-9835-4a62-afd6-6e2e59c15cfa",
        //           "amount": 252.00,
        //           "voucherDate": 0,
        //           "paymentStatus": "Payment Initiated",
        //           "benfLineItems": [
        //               {
        //                   "id": "4195bcc6-f404-439a-93a6-88ccde6674c7",
        //                   "beneficiaryId": "872f1f25-88be-4bd1-9d66-b2957ea726e2",
        //                   "lineItemId": "7afc26d0-00c8-45cf-ad1f-2bf7e1c4b154",
        //                   "auditDetails": {
        //                       "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //                       "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //                       "createdTime": 1688654871867,
        //                       "lastModifiedTime": 1688654871867
        //                   }
        //               }
        //           ],
        //           "auditDetails": {
        //               "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
        //               "createdTime": 1688654871867,
        //               "lastModifiedTime": 1688654871867
        //           }
        //       }
        //   ]
        // }
        return transformViewDataToApplicationDetails(t, response?.paymentInstructions?.[0], tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}