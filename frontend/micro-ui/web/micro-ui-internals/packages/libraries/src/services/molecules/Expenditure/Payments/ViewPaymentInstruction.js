import BillingService from "../../../elements/Bill";
import { ContractService } from "../../../elements/Contracts";
import AttendanceService from "../../../elements/Attendance";
import { ExpenseService } from "../../../elements/Expense";

const getBeneficiaryData = async (beneficiaries, tenantId, t) => {
  let tableData = {}
  // const individuals = musterRoll?.individualEntries
  if(beneficiaries?.length > 0) {
    beneficiaries?.forEach((item, index) => {
      let tableRow = {}
      const individual = beneficiaries?.find(ind => ind?.individualId === item?.payee?.identifier)
      tableRow.id = item?.id
      tableRow.sno = index + 1
      tableRow.beneficiaryId = individual?.additionalDetails?.userId || t("NA")
      tableRow.nameOfIndividual = individual?.additionalDetails?.userName || t("NA")
      tableRow.amount = item?.payableLineItems?.reduce((acc,item)=>{
        if(item?.type==="PAYABLE") return acc + item.amount
        return acc 
      },0) || 0 //check if correct(add all payable here)
      tableRow.accountNo = individual?.additionalDetails?.bankDetails?.accountNo || t("NA"), 
      tableRow.ifscCode = individual?.additionalDetails?.bankDetails?.ifscCode || t("NA"), 
      tableRow.status = individual?.paymentStatus,
      tableData[item.id] = tableRow
    });

    //Add row to show Total data
    let totalRow = {}
    totalRow.sno = "RT_TOTAL"
    totalRow.beneficiaryId = "DNR"
    totalRow.nameOfIndividual = "DNR"
    tableRow.accountNo = "DNR"
    tableRow.ifscCode = "DNR"
    tableRow.status = "DNR"
    totalRow.amount = 0
  }
  return tableData
}

const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
  debugger
  // if(data.payments.length === 0) throw new Error('No data found');
  
  const paymentInstruction = data


  // const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())


  //get contract details
  // const contractPayload = {
  //   tenantId,
  //   contractNumber: workOrderNum
  // }
  
  // const beneficiaryData = await getBeneficiaryData(paymentInstruction?.billDetails, tenantId, t)

  const piDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: paymentInstruction?.paymentNumber || t("ES_COMMON_NA")},
      { title: "WORKS_PI_TYPE", value: paymentInstruction?.type || t("ES_COMMON_NA")},
      { title: "EXP_PARENT_PI_ID", value: paymentInstruction?.parentID || t("ES_COMMON_NA")},
      { title: "EXP_PI_ID", value: paymentInstruction?.parentID || t("ES_COMMON_NA")},
      { title: "EXP_PI_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
      { title: "WORKS_HEAD_OF_ACCOUNTS", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "EXP_MASTER_ALLT_ID", value: paymentInstruction?.headOfAccount || t("ES_COMMON_NA")},
      { title: "EXP_PAYMENT_GROSS_AMT", value: paymentInstruction?.grossAmount || t("ES_COMMON_NA") },
      { title: "EXP_PAYMENT_NET_AMT", value: paymentInstruction?.netAmount || t("ES_COMMON_NA") },
      { title: "CORE_COMMON_STATUS", value: paymentInstruction?.netAmount || t("ES_COMMON_NA") }

    ]
  }

  const banner = {
    isInfoLabel:true,
    infoHeader:"Error",
    infoText:"Error on <ServiceID> call: <Error Received from JIT on response>",
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
      { title: "EXP_PA_ID", value: paymentInstruction?.paymentNumber || t("ES_COMMON_NA")},
      { title: "EXP_PA_DATE", value: paymentInstruction?.type || t("ES_COMMON_NA")},
      { title: "EXP_PA_TOKEN_NO", value: paymentInstruction?.parentID || t("ES_COMMON_NA")},
      { title: "EXP_PA_TOKEN_DATE", value: paymentInstruction?.parentID || t("ES_COMMON_NA")},
      { title: "EXP_PA_ONLINE_BILL_NO", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") }
    ]
  }

  // const beneficiaryDetails = {
  //   title: "EXP_BENEFICIARY_DETAILS",
  //   asSectionHeader: true,
  //   values: [],
  //   additionalDetails : {
  //     table : {
  //       mustorRollTable : true,
  //       tableData: beneficiaryData
  //     }
  //   }
  // }


  // const billAmount = {
  //   title: "EXP_PAYMENT_ADVISE_DETAILS",
  //   asSectionHeader: true,
  //   values: [
  //       { title: "EXP_PAYMENT_ADV_ID", value: paymentInstruction?.paymentAdvise?.id || t("ES_COMMON_NA")},
  //       { title: "EXP_PAYMENT_ADV_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentAdvise?.date, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
  //       { title: "EXP_PAYMENT_ADV_TOKEN_NO", value: paymentInstruction?.paymentAdvise?.tokenNo || t("ES_COMMON_NA")},
  //       { title: "EXP_PAYMENT_ADV_TOKEN_DATE", value: Digit.DateUtils.ConvertTimestampToDate(paymentInstruction?.paymentAdvise?.tokenDate, 'dd/MM/yyyy') || t("ES_COMMON_NA") },
  //       { title: "EXP_PAYMENT_ADV_ONLINE_BILL_NO", value: paymentInstruction?.paymentAdvise?.billNo || t("ES_COMMON_NA")},
  //   ],
  //   amountStyle: {
  //     width:"8rem",
  //     textAlign:"right"
  //   }
  // }


  const applicationDetails = { applicationDetails: [piDetails,banner] };


  const dummyRows = [
    ["string1", "string2", "string3", "string4", "string5", "string6", "string7"],
    ["string8", "string9", "string10", "string11", "string12", "string13", "string14"],
    ["string15", "string16", "string17", "string18", "string19", "string20", "string21"],
    ["string22", "string23", "string24", "string25", "string26", "string27", "string28"],
    ["string29", "string30", "string31", "string32", "string33", "string34", "string35"],
    ["string36", "string37", "string38", "string39", "string40", "string41", "string42"],
    ["string43", "string44", "string45", "string46", "string47", "string48", "string49"]
  ];
  

  // const piTable = {
  //   title: "EXP_PIS",
  //   asSectionHeader: true,
  //   isTable: true,
  //   headers: ["Apple", "Banana", "Cherry", "Durian", "Elderberry", "Fig", "Grape"],
  //   tableRows: dummyRows,
  //   state: {},
  //   tableStyles:{
  //       rowStyle:{},
  //       cellStyle: [{}, { "width": "40vw" }, {}, {},{"textAlign":"right"}]
  //   },
  //   mainDivStyles:{ lineHeight: "19px", minWidth: "280px" }
  // }
  const dummyHeaders = [t("EXP_BENE_ID"), t("EXP_PAYMENT_ID"), t("COMMON_NAME"), t("EXP_AC_NO"), t("EXP_IFSC"), t("CORE_COMMON_STATUS"), t("EXP_PAYMENT_AMT")]

  const beneficiaryTable = {
    title: "EXP_BENEFICIARY_DETAILS",
    asSectionHeader: true,
    isTable: true,
    headers: dummyHeaders,
    tableRows: dummyRows,
    state: {},
    tableStyles:{
        rowStyle:{},
        cellStyle: [{}, { "width": "40vw" }, {}, {},{"textAlign":"right"}]
    },
    mainDivStyles:{ lineHeight: "19px", minWidth: "280px" }

  }

  return [
    {
      applicationDetails,
      applicationData: paymentInstruction,
      processInstancesDetails: {},
      workflowDetails: {}
    },
    {
      applicationDetails:{applicationDetails:[paDetails,beneficiaryTable]},
      applicationData: {},
      processInstancesDetails: {},
      workflowDetails: {}
    }
  ]
}

export const ViewPaymentInstruction = {
    fetchPaymentInstruction: async (t, tenantId, data) => {
      try {
        // const response = await ExpenseService.searchPayment(data);
        //dummy response 
        const response =  {
          "id": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
          "tenantId": "pg.citya",
          "muktaReferenceId": "EP/0/2023-24/07/06/000046",
          "numBeneficiaries": 3,
          "grossAmount": 300.00,
          "netAmount": 300.00,
          "piStatus": "FAILED",
          "piErrorResp": "Error!!!",
          "additionalDetails": {
              "billNumber": [
                  "PB/2023-24/000458"
              ],
              "referenceId": [
                  "WO/2023-24/000775"
              ]
          },
          "auditDetails": {
              "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
              "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
              "createdTime": 1688654871867,
              "lastModifiedTime": 1688654871867
          },
          "transactionDetails": [],
          "paDetails": [
              {
                  "id": "6c3b8016-e18a-46f4-9f2f-b9d41aa12b42",
                  "tenantId": "pg.citya",
                  "muktaReferenceId": "EP/0/2023-24/07/06/000046",
                  "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
                  "paBillRefNumber": null,
                  "paFinYear": null,
                  "paAdviceId": null,
                  "paAdviceDate": null,
                  "paTokenNumber": null,
                  "paTokenDate": null,
                  "paErrorMsg": null,
                  "additionalDetails": null,
                  "auditDetails": {
                      "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "createdTime": 1688654871867,
                      "lastModifiedTime": 1688654871867
                  }
              }
          ],
          "jitBillNo": "PI-1013/2023-24/000035",
          "beneficiaryDetails": [
              {
                  "id": "1310a40c-aee5-4f2d-ae24-e680f1756f03",
                  "tenantId": "pg.citya",
                  "muktaReferenceId": "EP/0/2023-24/07/06/000046",
                  "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
                  "beneficiaryId": "Deduction_pg.citya_GSTTDS",
                  "beneficiaryType": "DEPT",
                  "bankAccountId": "4a3e05fa-3919-45b1-b52e-0b9c5377b2a3",
                  "amount": 36.00,
                  "voucherDate": 0,
                  "paymentStatus": "Payment Initiated",
                  "benfLineItems": [
                      {
                          "id": "94538804-1269-4a7e-95b2-dcbd5e069c7b",
                          "beneficiaryId": "1310a40c-aee5-4f2d-ae24-e680f1756f03",
                          "lineItemId": "e2930afa-b947-4164-9651-f7f68a0de61f",
                          "auditDetails": {
                              "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                              "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                              "createdTime": 1688654871867,
                              "lastModifiedTime": 1688654871867
                          }
                      }
                  ],
                  "auditDetails": {
                      "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "createdTime": 1688654871867,
                      "lastModifiedTime": 1688654871867
                  }
              },
              {
                  "id": "550b8088-5bf6-4467-9913-27a30f8b7cf5",
                  "tenantId": "pg.citya",
                  "muktaReferenceId": "EP/0/2023-24/07/06/000046",
                  "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
                  "beneficiaryId": "Deduction_pg.citya_ECB",
                  "beneficiaryType": "DEPT",
                  "amount": 12.00,
                  "voucherDate": 0,
                  "paymentStatus": "Payment Initiated",
                  "benfLineItems": [
                      {
                          "id": "c500e9a6-9b39-4474-89f0-7ec5d846b5aa",
                          "beneficiaryId": "550b8088-5bf6-4467-9913-27a30f8b7cf5",
                          "lineItemId": "2926aca4-3e99-488f-8885-04961721cd35",
                          "auditDetails": {
                              "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                              "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                              "createdTime": 1688654871867,
                              "lastModifiedTime": 1688654871867
                          }
                      }
                  ],
                  "auditDetails": {
                      "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "createdTime": 1688654871867,
                      "lastModifiedTime": 1688654871867
                  }
              },
              {
                  "id": "872f1f25-88be-4bd1-9d66-b2957ea726e2",
                  "tenantId": "pg.citya",
                  "muktaReferenceId": "EP/0/2023-24/07/06/000046",
                  "piId": "2538edc2-8abd-4ca1-adcc-3a38367c30ff",
                  "beneficiaryId": "d17ba578-aa56-4834-b415-6eab7f3b9ebd",
                  "beneficiaryType": "ORG",
                  "bankAccountId": "1e52c7af-9835-4a62-afd6-6e2e59c15cfa",
                  "amount": 252.00,
                  "voucherDate": 0,
                  "paymentStatus": "Payment Initiated",
                  "benfLineItems": [
                      {
                          "id": "4195bcc6-f404-439a-93a6-88ccde6674c7",
                          "beneficiaryId": "872f1f25-88be-4bd1-9d66-b2957ea726e2",
                          "lineItemId": "7afc26d0-00c8-45cf-ad1f-2bf7e1c4b154",
                          "auditDetails": {
                              "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                              "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                              "createdTime": 1688654871867,
                              "lastModifiedTime": 1688654871867
                          }
                      }
                  ],
                  "auditDetails": {
                      "createdBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "lastModifiedBy": "ba722111-0fbd-4a39-a4f0-0039f96eb69c",
                      "createdTime": 1688654871867,
                      "lastModifiedTime": 1688654871867
                  }
              }
          ]
        }
        return transformViewDataToApplicationDetails(t, response, tenantId)
      } catch (error) {
          throw new Error(error?.response?.data?.Errors[0].message);
      }
    }
}