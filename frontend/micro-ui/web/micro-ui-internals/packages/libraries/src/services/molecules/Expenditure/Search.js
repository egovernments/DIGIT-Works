import _ from "lodash";
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
};
