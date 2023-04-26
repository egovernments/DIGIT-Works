import _ from "lodash";
import { ContractService } from "../../elements/Contracts";
import { OrganisationService } from "../../elements/Organisation";
import { WorksService } from "../../elements/Works";

export const BillsSearch = {
  viewSupervisionBill: async ({ t,tenantId,billNumber }) => {
    
    const supervisionBillSearch = await WorksService.searchBill({
      "billCriteria": {
        "tenantId": "pg.citya",
        "ids": ["dc3b3bcd-d31a-4fd7-87bb-47484596050c"],
        "businessService": "works.supervision",
        "referenceIds": [],
        "billNumber":"",
        "status": ""
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
    const referenceIdsToSearch = supervisionBill?.billDetails?.map(row => row?.referenceId)
    const tableBillSearch = await WorksService.searchBill({
      "billCriteria": {
        "tenantId": "pg.citya",
        "ids": referenceIdsToSearch,
    },
       "pagination": {
        "limit": 10,
        "offSet": 0,
        "sortBy": "ASC",
        "order": "ASC"
      }
    })
    const tableBills = tableBillSearch?.bills
    const billDetails = {
      title: " ",
      asSectionHeader: false,
      values: [
        { title: "WORKS_BILL_NUMBER", value: supervisionBill.billNumber || t("NA") },
        { title: "WORKS_BILL_DATE", value: Digit.DateUtils.ConvertEpochToDate(supervisionBill.fromPeriod) || t("NA") },
        { title: "WORKS_ORDER_NO", value: contractNumber || t("NA") },
        { title: "WORKS_PROJECT_ID", value: contract?.additionalDetails?.projectId || t("NA") },
        {
          title: "PROJECTS_DESCRIPTION",
          value: contract?.additionalDetails?.projectDesc || t("NA"),
        },
        { title: "ES_COMMON_LOCATION", value:t(Digit.Utils.locale.getTransformedLocale(`${tenantId}_ADMIN_${contract?.additionalDetails?.locality}`)) + `, Ward ${contract?.additionalDetails?.ward}` || t("NA") },
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
        billNo: row?.billNumber,
        billType: t(Digit.Utils.locale.getTransformedLocale(`BILL_TYPE_${row?.businessService}`)),
        billDate: Digit.DateUtils.ConvertEpochToDate(row?.fromPeriod),
        status: t(`WF_STATUS_BILL_${row?.wfStatus}`),
        amount:  Digit.Utils.dss.formatterWithoutRound(row?.netPayableAmount,'number')|| t('NA'),
        paymentStatus: t(`PAYMENT_STATUS_${row?.paymentStatus}`),
      }
    })
    const dummyTableData = [
      {
        billNo: "LE/ENG/00002/10/2017-18",
        billType: "Wage Bill",
        billDate: "21-09-2021",
        status: "Approve",
        amount: "15500.00",
        paymentStatus: "Payment In Process",
      },
      {
        billNo: "LE/ENG/00002/10/2017-10",
        billType: "Supervision Bill",
        billDate: "21-09-2021",
        status: "Approve",
        amount: "14444.00",
        paymentStatus: "Payment Completed",
      },
      {
        billNo: "LE/ENG/00002/10/2017-20",
        billType: "Wage Bill",
        billDate: "21-09-2021",
        status: "Approve",
        amount: "123131.00",
        paymentStatus: "Payment Failed",
      },
    ];

    const tableRows = tableData.map((row, idx) => {
      return [
        {
          type: "link",
          label: row?.billNo,
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

    const totalAmount = Digit.Utils.dss.formatterWithoutRound(tableBills?.reduce((acc,row)=> {
      return acc + (row?.netPayableAmount || 0)
    },0),"number")
    const totalBillAmt = {
      title: " ",
      asSectionHeader: true,
      Component: Digit.ComponentRegistryService.getComponent("TotalBillAmount"),
      value: totalAmount,
    };

    const billDetailsBelow = {
      title: "EXP_BILL_DETAILS",
      asSectionHeader: true,
      values: [{ title: "EXP_TOTAL_BILL_AMOUNT", value: Digit.Utils.dss.formatterWithoutRound(supervisionBill?.netPayableAmount, "number") || t("NA") }],
    };

    const totalBillAmtBelow = {
      title: " ",
      asSectionHeader: true,
      Component: Digit.ComponentRegistryService.getComponent("TotalBillAmount"),
      value: Digit.Utils.dss.formatterWithoutRound(supervisionBill?.netPayableAmount, "number") || t("NA"),
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
    const billData = billResponse?.bills?.[1]; //TODO: Index with update once API is done.
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
    const headerDetails = {
        title: " ",
        asSectionHeader: true,
        values: [
            { title: "WORKS_BILL_NUMBER", value: billData?.billNumber || "NA"},
            { title: "WORKS_BILL_DATE", value: Digit.Utils.pt.convertEpochToDate(billData?.billDate) || "NA"},
            { title: "WORKS_ORDER_NO", value: WOData?.contractNumber || "NA"},
            { title: "WORKS_PROJECT_ID", value: WOData?.additionalDetails?.projectId || "NA"},
            { title: "PROJECTS_DESCRIPTION", value: WOData?.additionalDetails?.projectDesc || "NA"}, 
            { title: "ES_COMMON_LOCATION", value:  WOData?.additionalDetails?.locality ? t(`${headerLocale}_ADMIN_${WOData?.additionalDetails?.locality}`) : "NA" },
        ]
    };

    const invoiceDetails = {
        title: "EXP_INVOICE_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "EXP_VENDOR_NAME", value: orgData?.name || "NA" },
            { title: "EXP_VENDOR_ID", value: orgData?.orgNumber || "NA" },
            { title: "EXP_INVOICE_NUMBER", value: billData?.additionalDetails?.invoiceNumber || "NA" },
            { title: "EXP_INVOICE_DATE", value: Digit.Utils.pt.convertEpochToDate(billData?.additionalDetails?.invoiceDate ) || "NA"}, 
            { title: "EXP_MATERIALCOST_RS", value: mcDetails.amount || "NA" }, 
            { title: "EXP_GST_RS", value: gstDetails.amount || "NA" },
        ]
    };

    //total bill amount
    let billAmount = mcDetails.amount + gstDetails.amount;
    const billDetails = {
        title: "EXP_INVOICE_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "EXP_BILL_AMOUNT", value: (billAmount) || "NA" },
        ]
    };
    console.log(lineItems, metaData);
    //totalDeductions = sum of amount in the table
    let totalDeductions = 0;
    const deductionsTableRows = [t("WORKS_SNO"), t("EXP_DEDUCTION_NAME"), t("EXP_PERCENTAGE_OR_FIXED"), t("ES_COMMON_AMOUNT"), t("WF_COMMON_COMMENTS")];
    const deductionsTableData = lineItems?.map((lineItem, index)=>{
      if(lineItem?.type === "DEDUCTION") {

        let masterDeduction = metaData?.filter(data=>data?.code === lineItem?.headCode)[0];
        let masterDeductionType = masterDeduction?.calculationType;
        let percentageOrFixed = masterDeductionType === "percentage" ? `${masterDeduction?.value}%` : t("EXP_FIXED");
        totalDeductions += lineItem?.amount;

        return [
          index + 1,
          t(`EXP_${lineItem?.headCode}`),
          percentageOrFixed,
          lineItem?.amount,
          lineItem?.additionalDetails?.comments
        ]
      }
     })

    deductionsTableData?.push(["","","","" ,t("RT_TOTAL"), Digit.Utils.dss.formatterWithoutRound(totalDeductions, 'number')]);

    const deductionsTable = {
        title: "EXP_DEDUCTIONS",
        asSectionHeader: true,
        isTable: true,
        headers: deductionsTableRows,
        tableRows: deductionsTableData,
        state: {},
        tableStyles:{
            rowStyle:{},
            cellStyle: [{}, { "width": "40vw" }, {}, {}, {  },{"textAlign":"right"}]
        }
    }

    let netPayableAmtCalc = billAmount - totalDeductions;
    const netPayableAmt = {
        "title": " ",
        "asSectionHeader": true,
        "Component": Digit.ComponentRegistryService.getComponent("ViewTotalEstAmount"),
        "value": Digit.Utils.dss.formatterWithoutRound(t(netPayableAmtCalc))
    }

    const documentDetails = {
        title: "",
        asSectionHeader: true,
        additionalDetails: {
            documents: [{
                title: "WORKS_RELEVANT_DOCS",
                BS: 'Works',
                values: currentProject?.documents?.map((document) => {
                  if(document?.status !== "INACTIVE") {
                      return {
                          title: document?.documentType === "Others" ? document?.additionalDetails?.otherCategoryName : document?.documentType,
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
        basic_details :  {applicationDetails : [headerDetails, invoiceDetails]},
        bill_details :  {applicationDetails : [billDetails, deductionsTable, netPayableAmt, documentDetails]}
    }

    return {
        applicationDetails: details,
        applicationData: [], //TODO: @hariom send the search response object here(required by WorkflowActions)
        isNoDataFound : false //TODO:
    }
  }
};
