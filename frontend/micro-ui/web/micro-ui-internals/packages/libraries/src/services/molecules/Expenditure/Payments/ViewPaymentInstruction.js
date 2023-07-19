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
      { title: "EXP_PARENT_PI_ID", value: paymentInstruction?.parentID || t("ES_COMMON_NA"),isLink:paymentInstruction?.parentID ? true : false,to:`view-payment-instruction?tenantId=${paymentInstruction?.tenantId}&piNumber=${paymentInstruction?.parentID}`},
      // { title: "EXP_PI_ID", value: paymentInstruction?.jitBillNo || t("ES_COMMON_NA"),isLink:true,to:`view-payment-instruction?tenantId=${paymentInstruction?.tenantId}&piNumber=${paymentInstruction?.jitBillNo}`},
      { title: "EXP_PI_ID", value: paymentInstruction?.jitBillNo || t("ES_COMMON_NA")},
      { title: "EXP_PI_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.auditDetails?.createdTime, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_HEAD_OF_ACCOUNTS", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "EXP_MASTER_ALLT_ID", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "EXP_PAYMENT_GROSS_AMT", value: paymentInstruction?.grossAmount || t("ES_COMMON_NA") },
      { title: "EXP_PAYMENT_NET_AMT", value: paymentInstruction?.netAmount || t("ES_COMMON_NA") },
      { title: "CORE_COMMON_STATUS", value: t(Digit.Utils.locale.getTransformedLocale(`EXP_PI_STATUS_${paymentInstruction?.piStatus}`)),tab:{type:"statusColor",style:paymentInstruction?.piStatus ==="SUCCESSFUL" ? {color:"green"}: (paymentInstruction?.piStatus ==="FAILED" ?{color:"red"}:{}) }  }

    ],
  }

  const bannerForStatusError = {
    isInfoLabel:true,
    infoHeader:t("COMMON_ERR"),
    infoText:`${t("BANNER_TEXT_STATUS_ERROR")} : ${paymentInstruction?.piErrorResp}`,
    infoIconFill:"red",
    style:{
      "backgroundColor":"#EFC7C1",
      "width":"80%"
    }
  }

  const reverseDateFormat = (date) => {
    if(!date){
      return t("ES_COMMON_NA")
    }
    let splitDate = date?.split("-")
    splitDate = splitDate?.reverse()
    let dateString = ""
    splitDate?.forEach((str,idx)=> {
      if(idx===splitDate?.length - 1){
        dateString= dateString+str
      }
      else{
      dateString = dateString+str+"-"
      }
    }) 

    return dateString
  }
 

  const paDetails = {
    title: "EXP_PA_DETAILS",
    asSectionHeader: true,
    values: [
      { title: "EXP_PA_ID", value: paymentInstruction?.paDetails?.[0]?.paAdviceId || t("ES_COMMON_NA")},
      { title: "EXP_PA_DATE", value:reverseDateFormat(paymentInstruction?.paDetails?.[0]?.paAdviceDate) || t("ES_COMMON_NA")},
      { title: "EXP_PA_TOKEN_NO", value: paymentInstruction?.paDetails?.[0]?.paTokenNumber || t("ES_COMMON_NA")},
      { title: "EXP_PA_TOKEN_DATE", value:reverseDateFormat(paymentInstruction?.paDetails?.[0]?.paTokenDate) || t("ES_COMMON_NA")},
      { title: "EXP_PA_ONLINE_BILL_NO", value:paymentInstruction?.paDetails?.[0]?.paBillRefNumber || t("ES_COMMON_NA") }
    ]
  }

  const applicationDetails = { applicationDetails: [piDetails] };
  if(paymentInstruction?.piStatus === "FAILED"){
    applicationDetails.applicationDetails.push(bannerForStatusError)
  }

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
  
  const returnPaymentStatusObject = (paymentStatus,bene) => {
    
    return {
      value:t(Digit.Utils.locale.getTransformedLocale(`BILL_STATUS_${paymentStatus}`)),
      type:"paymentStatus",
      styles:paymentStatus==="Payment Successful"?{color:"green"}:(paymentStatus==="Payment Failed"?{color:"red"}:{}),
      hoverIcon: paymentStatus==="Payment Failed"?"infoIcon":"",
      iconHoverTooltipText: paymentStatus==="Payment Failed" ? bene?.paymentStatusMessage:"",
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
        returnPaymentStatusObject(beneficiary?.paymentStatus,beneficiary),
        `₹ ${beneficiary?.amount}` ,
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
        returnPaymentStatusObject(beneficiary?.paymentStatus,beneficiary),
       `₹ ${beneficiary?.amount}` ,
      ];
    } else if (beneficiary.beneficiaryType === "DEPT") {
      return [
        // beneficiary?.beneficiaryId || t("ES_COMMON_NA"),
        t("ES_COMMON_NA"),
        beneficiary?.muktaReferenceId || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountHolderName || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountNumber,
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.bankBranchIdentifier?.code || t("ES_COMMON_NA"),
        returnPaymentStatusObject(beneficiary?.paymentStatus,beneficiary),
         `₹ ${beneficiary?.amount}` ,
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
        
        return transformViewDataToApplicationDetails(t, response?.paymentInstructions?.[0], tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}