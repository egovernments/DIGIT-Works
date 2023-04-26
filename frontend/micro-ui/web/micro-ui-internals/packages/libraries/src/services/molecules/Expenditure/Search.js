import _ from "lodash";
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
};
