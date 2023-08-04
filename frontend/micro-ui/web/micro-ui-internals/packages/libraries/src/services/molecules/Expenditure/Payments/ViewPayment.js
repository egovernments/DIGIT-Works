import BillingService from "../../../elements/Bill";
import { ExpenseService } from "../../../elements/Expense";
import { WageSeekerService } from "../../../elements/WageSeeker";
import { BankAccountService } from "../../../elements/BankAccount";
import { OrganisationService } from "../../../elements/Organisation";
import { PaymentService } from "../../../elements/Payment";

const transformViewDataToApplicationDetails = async (t, payment, tenantId) => {
  if (!payment) throw new Error("No data found");

  const {
    paymentNumber,
    auditDetails: { createdTime },
    netPayableAmount,
    netPaidAmount,
    status,
  } = payment;
  //make bill seaarch using billId field
  const billSearchPayload = {
    billCriteria: {
      tenantId,
      ids: [payment?.bills?.[0]?.billId],
    },
  };
  const billResponse = await BillingService.search(billSearchPayload);
  const billObj = billResponse?.bills?.[0];
  const { billNumber } = billObj;

  let billUrl = "";
  if (billNumber.includes("PB")) {
    billUrl = `purchase-bill-details?tenantId=${tenantId}&billNumber=${billNumber}`;
  }
  if (billNumber.includes("SB")) {
    billUrl = `supervision-bill-details?tenantId=${tenantId}&billNumber=${billNumber}`;
  }
  if (billNumber.includes("WB")) {
    billUrl = `wage-bill-details?tenantId=${tenantId}&billNumber=${billNumber}`;
  }

  //make payment details card

  const paymentDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: billNumber || t("ES_COMMON_NA"), isLink: true, to: billUrl },
      { title: "EXP_PAYMENT_ID", value: paymentNumber || t("ES_COMMON_NA") },
      { title: "EXP_PAYMENT_DATE", value: Digit.DateUtils.ConvertTimestampToDate(createdTime) || t("ES_COMMON_NA") },
      { title: "EXP_PAYMENT_GROSS_AMT", value:netPayableAmount ? `₹ ${Digit.Utils.dss.formatterWithoutRound(netPayableAmount,"number")}` : t("ES_COMMON_NA") },
      { title: "EXP_PAYMENT_NET_AMT", value:netPaidAmount? `₹ ${Digit.Utils.dss.formatterWithoutRound(netPaidAmount,"number")}` : t("ES_COMMON_NA") },
      {
        title: "CORE_COMMON_STATUS",
        value: t(`EXP_PI_STATUS_${status}`),
        tab: { type: "statusColor", style: status === "SUCCESSFUL" ? { color: "green" } : status === "FAILED" ? { color: "red" } : {} },
      },
    ],
  };

  //fetch all pis
  const piSearchPayload = {
    "searchCriteria": {
      tenantId,
      muktaReferenceId:paymentNumber
  }
  }
  const piResponse = await ExpenseService.searchPayment(piSearchPayload)
  const paymentInstructions = piResponse?.paymentInstructions
  

  const reverseDateFormat = (date) => {
    if (!date) {
      return t("ES_COMMON_NA");
    }
    let splitDate = date?.split("-");
    splitDate = splitDate?.reverse();
    let dateString = "";
    splitDate?.forEach((str, idx) => {
      if (idx === splitDate?.length - 1) {
        dateString = dateString + str;
      } else {
        dateString = dateString + str + "-";
      }
    });

    return dateString;
  };

  const returnPaymentStatusObjectForPI = (paymentStatus,pi) => {
    
    return {
      value:t(Digit.Utils.locale.getTransformedLocale(`EXP_PI_STATUS_${paymentStatus}`)),
      type:"paymentStatus",
      styles:paymentStatus==="SUCCESSFUL"?{color:"green"}:(paymentStatus==="FAILED"?{color:"red"}:{}),
      hoverIcon: paymentStatus==="FAILED"?"infoIcon":"",
      iconHoverTooltipText: paymentStatus==="FAILED" ? (pi?.piStatusLogs?.[0] ? t(pi?.piStatusLogs?.[0]?.status) :  t("ES_COMMON_NA")):"",
      toolTipStyles:{}
    }
  }

  //make pi table

  const piTableHeaders = [t("EXP_PI_ID"), t("EXP_COMMON_TYPE"), t("ES_COMMON_DATE"), t("WORKS_HEAD_OF_ACCOUNTS"), t("EXP_MASTER_ALLT_ID"), t("CORE_COMMON_STATUS"), t("EXP_PAYMENT_NET_AMT")]
  let sanctionIds = []

  const transactionDetails = paymentInstructions?.forEach((pIObj) => {
    pIObj?.transactionDetails?.forEach((txn) => {
      sanctionIds.push(txn?.sanctionId);
    })
  })


  let fundsSearchPayload = {
    searchCriteria:{
     tenantId,
     ids: sanctionIds?.length !== 0 ? sanctionIds : []
    }
   } 

   const imfsFundsResponse = await PaymentService.ifms_funds_search(fundsSearchPayload);
  const piTableRows = paymentInstructions?.map((pi,idx)=>{
    const { piStatus,auditDetails:{createdTime},netAmount,jitBillNo } = pi
    return [
      {
        type:"modal",
        infoCardDetails:{
          values:[
            { title: t("EXP_PA_ID"), value: pi?.paDetails?.[0]?.paAdviceId || t("ES_COMMON_NA")},
            { title: t("EXP_PA_DATE"), value:reverseDateFormat(pi?.paDetails?.[0]?.paAdviceDate) || t("ES_COMMON_NA")},
            { title: t("EXP_PA_TOKEN_NO"), value: pi?.paDetails?.[0]?.paTokenNumber || t("ES_COMMON_NA")},
            { title: t("EXP_PA_TOKEN_DATE"), value:reverseDateFormat(pi?.paDetails?.[0]?.paTokenDate) || t("ES_COMMON_NA")},
            { title: t("EXP_PA_ONLINE_BILL_NO"), value:pi?.paDetails?.[0]?.paBillRefNumber || t("ES_COMMON_NA") }
          ],
          header:t("EXP_PA_DETAILS")
        },
        label:jitBillNo
      },
      pi?.parentPiNumber ? t("EXP_PI_TYPE_REVISED") : t("EXP_PI_TYPE_ORIGINAL"),
      Digit.DateUtils.ConvertTimestampToDate(createdTime),
      imfsFundsResponse?.funds?.find((obj) => obj?.id == pi?.transactionDetails?.[0]?.sanctionId)?.hoaCode || t("ES_COMMON_NA"),
      imfsFundsResponse?.funds?.find((obj) => obj?.id == pi?.transactionDetails?.[0]?.sanctionId)?.masterAllotmentId || t("ES_COMMON_NA"),
      returnPaymentStatusObjectForPI(piStatus,pi),
      netAmount ? `₹ ${Digit.Utils.dss.formatterWithoutRound(netAmount,"number")}` : t("ES_COMMON_NA")
    ]
  })  

  const piTable = {
    title: "EXP_PIS",
    asSectionHeader: true,
    isTable: true,
    headers: piTableHeaders,
    tableRows: piTableRows,
    state: {},
    tableStyles:{
        rowStyle:{},
        cellStyle: [{}, {}, {}, {},{},{},{"textAlign":"right"}],
        tableStyle:{backgroundColor:"#FAFAFA"}
    },
    mainDivStyles:{ lineHeight: "19px", minWidth: "280px" }
  }

  //make beneficiary table
  let orgsToSearch = []
  let IndsToSearch = []
  const latestPaymentInstruction = paymentInstructions?.[0]

  // Step 1: Get all beneficiaryDetails objects from all paymentInstructions object
  
  //Get Latest original PI
  const filteredPaymentInsOriginal = paymentInstructions?.filter((pi,idx)=>{
    if(pi.parentPiNumber) return false
    else return true
  })?.sort((a,b)=>{
    return b.auditDetails.createdTime - a.auditDetails.createdTime 
  })?.[0]

  const allBeneficiaryDetails = [...paymentInstructions?.filter((pi,idx)=> {
    if(pi.parentPiNumber) return true 
    return false
  } ),filteredPaymentInsOriginal].flatMap(payment => payment.beneficiaryDetails);

  // Step 2: Create a Map to store unique beneficiaryDetails based on beneficiaryNumber
  const uniqueBeneficiaryMap = new Map();
  allBeneficiaryDetails.forEach(beneficiary => {
    const { beneficiaryNumber, auditDetails } = beneficiary;
    if (!uniqueBeneficiaryMap.has(beneficiaryNumber) || auditDetails.lastModifiedTime > uniqueBeneficiaryMap.get(beneficiaryNumber).auditDetails.lastModifiedTime) {
      uniqueBeneficiaryMap.set(beneficiaryNumber, beneficiary);
    }
  });

  // Step 3: Convert the Map values (unique beneficiaryDetails) to an array
  const uniqueBeneficiaryDetails = Array.from(uniqueBeneficiaryMap.values());
    const beneficiaryIdsToSearch = uniqueBeneficiaryDetails?.map(row => {
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
  uniqueBeneficiaryDetails?.forEach(beneficiary=> {
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
      iconHoverTooltipText: paymentStatus==="Payment Failed" ? t(bene?.paymentStatusMessage):"",
      toolTipStyles:{}
    }
  }
  
  const beneficiaryTableRows = uniqueBeneficiaryDetails?.map((beneficiary,idx) => {
    
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
       `₹ ${beneficiary?.amount}`,
      ];
    } else if (beneficiary.beneficiaryType === "DEPT") {
      return [
        // beneficiary?.beneficiaryId || t("ES_COMMON_NA"),
        t(beneficiary?.bankDetails?.referenceId) || t("ES_COMMON_NA"),
        beneficiary?.muktaReferenceId || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountHolderName || t("ES_COMMON_NA"),
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.accountNumber,
        beneficiary?.bankDetails?.bankAccountDetails?.[0]?.bankBranchIdentifier?.code || t("ES_COMMON_NA"),
        returnPaymentStatusObject(beneficiary?.paymentStatus,beneficiary),
        `₹ ${beneficiary?.amount}` ,
      ];
    }
  })
  
  const beneficiaryTableHeaders = [t("EXP_BENE_ID"), t("EXP_PAYMENT_ID"), t("COMMON_NAME"), t("EXP_AC_NO"), t("EXP_IFSC"), t("CORE_COMMON_STATUS"), t("EXP_PAYMENT_AMT")]

  const beneficiaryTable = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    isTable: true,
    headers: beneficiaryTableHeaders,
    tableRows: beneficiaryTableRows,
    state: {},
    tableStyles:{
        rowStyle:{},
        cellStyle: [{}, {}, {}, {},{},{},{"textAlign":"right"}],
        tableStyle:{backgroundColor:"#FAFAFA"}
    },
    mainDivStyles:{ lineHeight: "19px", minWidth: "280px" }

  }
  
 
  return [
    {
      applicationDetails: { applicationDetails: [paymentDetails] },
      applicationData: payment,
      processInstancesDetails: {},
      workflowDetails: {},
      latestPaymentInstruction
    },
    {
      applicationDetails: { applicationDetails: [piTable,beneficiaryTable] },
      applicationData: {},
      processInstancesDetails: {},
      workflowDetails: {},
    },
  ];
};

export const ViewPayment = {
  fetchPayment: async (t, tenantId, data) => {
    try {
      const response = await ExpenseService.searchPA(data);
      return transformViewDataToApplicationDetails(t, response?.payments?.[0], tenantId);
    } catch (error) {
      throw new Error(error?.response?.data?.Errors[0].message);
    }
  },
};
