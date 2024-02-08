import _ from "lodash";
import { ContractService } from "../../elements/Contracts";
import { OrganisationService } from "../../elements/Organisation";
import { WorksService } from "../../elements/Works";

export const BillsSearch = {
  viewSupervisionBill: async ({ t,tenantId,billNumber }) => {
    
    const supervisionBillSearch = await WorksService.searchBill({
      "billCriteria": {
        "tenantId": tenantId,
        // "ids": ["dc3b3bcd-d31a-4fd7-87bb-47484596050c"],
        "businessService": Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision"),
        // "referenceIds": [],
        "billNumbers":[billNumber],
        // "status": ""
    },
       "pagination": {
        "limit": 10,
        "offSet": 0,
        "sortBy": "ASC",
        "order": "ASC"
      }
    })
    
    
    const supervisionBill = supervisionBillSearch?.bills?.[0]
    
    const contractNumber = supervisionBill?.referenceId.split('_')?.[0]

    //make contracts search to fetch other details

    const contractSearch = await WorksService.contractSearch({tenantId,filters:{
      tenantId,
      contractNumber
    }})

    const contract = contractSearch?.contracts?.[0]

    //fetch the list of ids from supervisionBill.billDetails then make a call to fetch those bills as well
    const bsPurchaseBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");
    const bsWageBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.wages");
    const bsSupervisionBill = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.supervision");
    const referenceIdsToSearch = supervisionBill?.billDetails?.map(row => row?.referenceId)
    const tableBillSearch = await WorksService.searchBillCalculator({
      "searchCriteria": {
        "tenantId": tenantId,
        "billNumbers": referenceIdsToSearch,
        // "businessService":[bsPurchaseBill,bsWageBill,bsSupervisionBill]
        // "businessService":bsPurchaseBill
      },
       "pagination": {
        "limit": 10,
        "offSet": 0,
        "sortBy": "ASC",
        "order": "ASC"
      }
    })
    const tableBills = tableBillSearch?.bills
    const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
    
    const location = {
      "ward":contract?.additionalDetails?.ward?t(`${headerLocale}_ADMIN_${contract?.additionalDetails?.ward}`):null,
      "locality":contract?.additionalDetails?.locality?t(`${headerLocale}_ADMIN_${contract?.additionalDetails?.locality}`):null,
      "city":contract?.tenantId ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(contract?.tenantId)}`) :null
    };
    const locationString = `${location.locality ? location.locality + ", " : ""}${location.ward ? location.ward + ", " : ""}${location.city ? location.city : ""}`
    

    const billDetails = {
      title: " ",
      asSectionHeader: false,
      values: [
        { title: "WORKS_BILL_NUMBER", value: supervisionBill.billNumber || t("NA") },
        { title: "WORKS_BILL_DATE", value: Digit.DateUtils.ConvertEpochToDate(supervisionBill.billDate) || t("NA") },
        { title: "WORKS_ORDER_NO", value: contractNumber || t("NA") },
        { title: "WORKS_PROJECT_ID", value: contract?.additionalDetails?.projectId || t("NA") },
        {
          title: "PROJECTS_DESCRIPTION",
          value: contract?.additionalDetails?.projectDesc || t("NA"),
        },
        // { title: "ES_COMMON_LOCATION", value:t(Digit.Utils.locale.getTransformedLocale(`${tenantId}_ADMIN_${contract?.additionalDetails?.locality}`)) + `, Ward ${contract?.additionalDetails?.ward}` || t("NA") },
        { title: "ES_COMMON_LOCATION", value:locationString}
      ],
    };

    
    const supervisionDetails = {
      title: "BILLS_SUPERVISION_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "COMMON_CBO_ID", value: contract?.additionalDetails?.cboOrgNumber || t("NA") },
        { title: "ES_COMMON_CBO_NAME", value: contract?.additionalDetails?.cboName || t("NA") },
      ],
    };

    const tableHeaders = [
      t("WORKS_BILL_NUMBER"),
      t("WORKS_BILL_TYPE"),
      t("WORKS_BILL_DATE"),
      t("ES_COMMON_STATUS"),
      t("EXP_BILL_AMOUNT"),
      t("ES_COMMON_PAYMENT_STATUS"),
    ];

    const tableData = tableBills?.map(row => {
      return {
        billNo: row?.bill?.billNumber,
        billType: t(Digit.Utils.locale.getTransformedLocale(`COMMON_MASTERS_BILL_TYPE_${row?.bill?.businessService}`)),
        billDate: Digit.DateUtils.ConvertEpochToDate(row?.bill?.billDate),
        status: t(`WF_BILL_${row?.bill?.status}`),
        amount:  Digit.Utils.dss.formatterWithoutRound(row?.bill?.totalAmount,'number')|| t('NA'),
        paymentStatus: row?.bill.paymentStatus ? t(`PAYMENT_STATUS_${row?.bill?.paymentStatus}`): t("NA"),
      }
    })
    
    const tableRows = tableData.map((row, idx) => {
      
      let billType = ""
        if(row.billNo.includes("PB")){
          billType = "purchase"
        }
        else if(row.billNo.includes("SB") ){
          billType = "supervision"
        }
        else if(row.billNo.includes("WB")){
          billType = "wage"
        }

      return [
        {
          type: "link",
          label: row?.billNo,
          path:`/${window.contextPath}/employee/expenditure/${billType}-bill-details?tenantId=${tenantId}&billNumber=${row.billNo}`
        },
        row.billType,
        row?.billDate,
        row?.status,
        row?.amount,
        row?.paymentStatus,
      ];
    });

    const billTable = {
      title: " ",
      asSectionHeader: true,
      isTable: true,
      headers: tableHeaders,
      tableRows: tableRows,
      state: {},
      tableStyles: {
        rowStyle: {},
        cellStyle: [{}, {}, {}, {}, {"textAlign":"right"}, {}],
      },
    };

    const totalAmount = Digit.Utils.dss.formatterWithoutRound(Math.round(tableBills?.reduce((acc,row)=> {
      return acc + (row?.bill?.totalAmount || 0)
    },0)),"number")
    const totalBillAmt = {
      title: " ",
      asSectionHeader: true,
      Component: Digit.ComponentRegistryService.getComponent("TotalBillAmountView"),
      value: totalAmount,
    };

    const billDetailsBelow = {
      title: "EXP_BILL_DETAILS",
      asSectionHeader: true,
      values: [{ title: "EXP_TOTAL_BILL_AMOUNT", value: Digit.Utils.dss.formatterWithoutRound(Math.round(supervisionBill?.totalAmount), "number") || t("NA") }],
    };

    const totalBillAmtBelow = {
      title: " ",
      asSectionHeader: true,
      Component: Digit.ComponentRegistryService.getComponent("TotalBillAmountView"),
      value: Digit.Utils.dss.formatterWithoutRound(Math.round(supervisionBill?.totalAmount), "number") || t("NA"),
      containerStyles: { justifyContent: "flex-start" },
      key: "BILLS_NET_PAYABLE",
    };

    return {
      applicationDetailsCardOne: {
        applicationDetails: [billDetails, supervisionDetails, billTable, totalBillAmt],
      },
      applicationDetailsCardTwo: {
        applicationDetails: [billDetailsBelow, totalBillAmtBelow],
      },
      applicationData: {},
      isNoDataFound: false,
    };
  },
  viewPurchaseBillDetails: async ({tenantId, t, billCriteria, pagination, headerLocale, metaData= {}}) => {

    //Bill search
    
    const billResponse = await WorksService?.searchBill({billCriteria, pagination});
    const billData = billResponse?.bills?.[0]; //TODO: Index with update once API is done.
    const WONumber = billData?.referenceId.split("_")[0];

    //Work order search
    const WOSearchPayload = {
      tenantId : tenantId,
      contractNumber : WONumber
    }
    const WOResponse = await ContractService?.search(tenantId, WOSearchPayload, {});
    const WOData = WOResponse?.contracts?.[0];

    //Org Search
    const orgPayload = {
      SearchCriteria: {
        id: [billData?.billDetails?.[0]?.payee?.identifier], //b9838d9c-b079-4cdb-b061-3d9addac9d40
        tenantId
      }
    }
    const orgResponse = await OrganisationService?.search(orgPayload);
    const orgData = orgResponse?.organisations?.[0];

    //lineItems
    const lineItems = billData?.billDetails?.[0]?.lineItems;

    let mcDetails = {};
    let gstDetails = {};
    mcDetails.amount = lineItems?.filter(lineItem=>lineItem?.headCode === "MC")?.[0]?.amount;
    gstDetails.amount = lineItems?.filter(lineItem=>lineItem?.headCode === "GST")?.[0]?.amount;

    let currentProject = {};
    // const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
    const location = {
      "ward":billData?.additionalDetails?.ward?t(`${headerLocale}_ADMIN_${billData?.additionalDetails?.ward}`):null,
      "locality":billData?.additionalDetails?.locality?t(`${headerLocale}_ADMIN_${billData?.additionalDetails?.locality}`):null,
      "city":billData?.tenantId ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(billData?.tenantId)}`) :null
    };
    const locationString = `${location.locality ? location.locality + ", " : ""}${location.ward ? location.ward + ", " : ""}${location.city ? location.city : ""}`
    

    const headerDetails = {
        title: " ",
        asSectionHeader: true,
        values: [
            { title: "WORKS_BILL_NUMBER", value: billData?.billNumber || "NA"},
            { title: "WORKS_BILL_DATE", value: Digit.Utils.pt.convertEpochToDate(billData?.billDate) || "NA"},
            { title: "WORKS_ORDER_NO", value: WOData?.contractNumber || "NA"},
            { title: "WORKS_PROJECT_ID", value: WOData?.additionalDetails?.projectId || "NA"},
            { title: "PROJECTS_DESCRIPTION", value: WOData?.additionalDetails?.projectDesc || "NA"}, 
            // { title: "ES_COMMON_LOCATION", value:  WOData?.additionalDetails?.locality ? t(`${headerLocale}_ADMIN_${WOData?.additionalDetails?.locality}`) : "NA" },
            { title: "ES_COMMON_LOCATION", value:locationString },
        ]
    };

    const mbdetails = {
      title: "EXP_MB_DETAILES",
      isMbDetails : true,
      mbValidationData : billData?.additionalDetails?.mbValidationData
  };

    const invoiceDetails = {
        title: "EXP_INVOICE_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "EXP_VENDOR_NAME", value: orgData?.name || "NA" },
            { title: "EXP_VENDOR_ID", value: orgData?.orgNumber || "NA" },
            { title: "EXP_INVOICE_NUMBER", value: billData?.additionalDetails?.invoiceNumber || "NA" },
            { title: "EXP_INVOICE_DATE", value: Digit.Utils.pt.convertEpochToDate(billData?.additionalDetails?.invoiceDate ) || "NA"}, 
            { title: "EXP_MATERIALCOST_RS", value: Digit.Utils.dss.formatterWithoutRound(mcDetails.amount, "number") || "NA" }, 
            { title: "EXP_GST_RS", value: Digit.Utils.dss.formatterWithoutRound(gstDetails.amount,  "number") || "NA" },
        ]
    };

    //total bill amount
    let billAmount = mcDetails.amount + (gstDetails.amount ? gstDetails.amount : 0);
    const billDetails = {
        title: "EXP_BILL_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "EXP_BILL_AMOUNT", value: (Digit.Utils.dss.formatterWithoutRound(billAmount, "number")) || "NA" },
        ]
    };
    //totalDeductions = sum of amount in the table
    let totalDeductions = 0;
    const deductionsTableRows = [t("WORKS_SNO"), t("EXP_DEDUCTION_NAME"), t("EXP_PERCENTAGE_OR_FIXED"), t("WF_COMMON_COMMENTS"),t("ES_COMMON_AMOUNT")];
    let index = 0;
    const deductionsTableData = lineItems?.map((lineItem)=>{
      if(lineItem?.type === "DEDUCTION") {

        let masterDeduction = metaData?.filter(data=>data?.code === lineItem?.headCode)[0];
        let masterDeductionType = masterDeduction?.calculationType;
        let percentageOrFixed = masterDeductionType === "percentage" ? `${masterDeduction?.value}%` : t("EXP_FIXED");
        totalDeductions += lineItem?.amount;

        return [
          index + 1,
          t(`EXP_${lineItem?.headCode}`),
          percentageOrFixed,
          lineItem?.additionalDetails?.comments || "NA",
          lineItem?.amount,
        ]
      }
     })

     deductionsTableData?.push(["",totalDeductions <= 0? t("EXPENDITURE_NO_DEDUCTION"):"","",t("RT_TOTAL"), Digit.Utils.dss.formatterWithoutRound(totalDeductions, 'number')]);


    const deductionsTable = {
        title: "EXP_DEDUCTIONS",
        asSectionHeader: true,
        isTable: true,
        headers: deductionsTableRows,
        tableRows: deductionsTableData,
        state: {},
        tableStyles:{
            rowStyle:{},
            cellStyle: [{}, { "width": "40vw" }, {}, {},{"textAlign":"right"}]
        }
    }

    let netPayableAmtCalc = billAmount - totalDeductions;
    
    const netPayableAmt = {
        "title": " ",
        "asSectionHeader": true,
        "Component": Digit.ComponentRegistryService.getComponent("ViewTotalEstAmount"),
        "value": Digit.Utils.dss.formatterWithoutRound(Math.round(netPayableAmtCalc), "number"),
        "showTitle":"BILLS_NET_PAYABLE"
    }

    const documentDetails = {
        title: "",
        asSectionHeader: true,
        additionalDetails: {
            documents: [{
                title: "WORKS_RELEVANT_DOCS",
                BS: 'Works',
                values: billData?.additionalDetails?.documents?.map((document) => {
                  if(document?.status !== "INACTIVE") {
                      return {
                          title: document?.documentType === "OTHERS" ? document?.additionalDetails?.otherCategoryName : t(`EXP_${document?.documentType}`),
                          documentType: document?.documentType,
                          documentUid: document?.fileStore,
                          fileStoreId: document?.fileStore,
                      };
                  }
                  return {};
              })
            }
            ]
        }
    }
    const details = {
        basic_details :  {applicationDetails : [headerDetails,mbdetails, invoiceDetails]},
        bill_details :  {applicationDetails : [billDetails, deductionsTable, netPayableAmt, documentDetails]}
    }

    return {
        applicationDetails: details,
        applicationData: billData, //TODO: @hariom send the search response object here(required by WorkflowActions)
        isNoDataFound : false, //TODO:
        contract:WOData  
    }
  }
};
