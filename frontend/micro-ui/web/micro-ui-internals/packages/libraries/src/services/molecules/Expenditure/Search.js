import _ from "lodash";
import { ContractService } from "../../elements/Contracts";
import { OrganisationService } from "../../elements/Organisation";
import { WorksService } from "../../elements/Works";

export const BillsSearch = {
  viewSupervisionBill: async ({ t }) => {
    const billDetails = {
      title: " ",
      asSectionHeader: false,
      values: [
        { title: "WORKS_BILL_NUMBER", value: "BILL-0000001002" || t("NA") },
        { title: "WORKS_BILL_DATE", value: "12/03/2023" || t("NA") },
        { title: "WORKS_ORDER_NO", value: "WO/2022-23/000052" || t("NA") },
        { title: "WORKS_PROJECT_ID", value: "PJ/2022-23/000051" },
        {
          title: "PROJECTS_DESCRIPTION",
          value: "RWHS Scheme at Ward 2" || t("NA"),
        },
        { title: "ES_COMMON_LOCATION", value: "MG Road, Ward 1" || t("NA") },
      ],
    };

    const supervisionDetails = {
      title: "BILLS_SUPERVISION_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "COMMON_CBO_ID", value: "ORG-0000520021" || t("NA") },
        { title: "ES_COMMON_CBO_NAME", value: "BRICS" || t("NA") },
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

    const tableRows = dummyTableData.map((row, idx) => {
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
        cellStyle: [{}, {}, {}, {}, {}, {}],
      },
    };

    const totalBillAmt = {
      title: " ",
      asSectionHeader: true,
      Component: Digit.ComponentRegistryService.getComponent("TotalBillAmount"),
      value: Digit.Utils.dss.formatterWithoutRound("57000", "number") || t("NA"),
    };

    const billDetailsBelow = {
      title: "EXP_BILL_DETAILS",
      asSectionHeader: true,
      values: [{ title: "EXP_TOTAL_BILL_AMOUNT", value: "4,275.00" || t("NA") }],
    };

    const totalBillAmtBelow = {
      title: " ",
      asSectionHeader: true,
      Component: Digit.ComponentRegistryService.getComponent("TotalBillAmount"),
      value: Digit.Utils.dss.formatterWithoutRound("100000", "number") || t("NA"),
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
    console.log(metaData);
    //Bill search
    const billResponse = await WorksService?.searchBill({billCriteria, pagination});
    const billData = billResponse?.bills?.[1]; //TODO: Index with update once API is done.
    const WONumber = billData?.referenceId.split("_")[0];

    const WOSearchPayload = {
      tenantId : tenantId,
      contractNumber : WONumber
    }

    //Work order search
    const WOResponse = await ContractService?.search(tenantId, WOSearchPayload, {});
    const WOData = WOResponse?.contracts?.[0];

    const orgPayload = {
      SearchCriteria: {
        id: [billData?.billDetails?.[0]?.payee?.identifier], //b9838d9c-b079-4cdb-b061-3d9addac9d40
        tenantId
      }
    }

    //Org Search
    const orgResponse = await OrganisationService?.search(orgPayload);
    const orgData = orgResponse?.organisations?.[0];

    //PayableLineItems
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

    let billAmount = mcDetails.amount + gstDetails.amount;
    const billDetails = {
        title: "EXP_INVOICE_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "EXP_BILL_AMOUNT", value: (billAmount) || "NA" },
        ]
    };

    //headCode filteration 
    //EXP_DEDUCTION_NAME
    //EXP_PERCENTAGE_OR_FIXED
    //ES_COMMON_AMOUNT - payableLineItems.amount / payableLineItems.paidAmount
    //WF_COMMON_COMMENTS - WF_COMMON_COMMENTS

    let totalDeductions = 0;
    const deductionsTableRows = [t("WORKS_SNO"), t("EXP_DEDUCTION_NAME"), t("EXP_PERCENTAGE_OR_FIXED"), t("ES_COMMON_AMOUNT"), t("WF_COMMON_COMMENTS")];
    const deductionsTableData = lineItems?.map((lineItem, index)=>{
      if(lineItem?.type === "DEDUCTION") {
        totalDeductions += lineItem?.amount;
        return [
          index + 1,
          t(`EXP_${lineItem}`),
          "CALLMDMSFORTHIS",
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
        applicationData: [], //TODO:
        isNoDataFound : false //TODO:
    }
  }
};
