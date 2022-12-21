const transformViewDataToApplicationDetails = {
  genericPropertyDetails: () => {
    const BillDetails = {
      title: "EXP_BILL_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "EXP_BILL_ID", value: "Bill/2021-22/09/0001" },
        { title: "EXP_CREATED_BY", value: "A Manjunath" },
        { title: "EXP_CREATED_DATE", value: "28-09-2022" },
        { title: "EXP_STATUS", value: "To Approve" },
      ],
    };
    const MusterRollDetails_1 = {
      expandAndCollapse : {
        groupComponents : true,
        headerLabel : "Muster Roll 1- ID (MSR/2022-23/08/0004) - Date (20-09-2022-27/09-2022)",
        headerValue : "Rs 30000",
        groupHeader : "EXP_MUSTER_ROLL_DETAILS"
      },
      additionalDetails : {
        table : {
          mustorRollTable : true
        },
      }
    };
    const MusterRollDetails_2 = {
      expandAndCollapse : {
        groupComponents : true,
        headerLabel : "Muster Roll 2 - ID (MSR/2022-23/08/0004) - Date (20-09-2022-27/09-2022)",
        headerValue : "Rs 30000"
      },
    };
    const MusterRollDetails_3 = {
      expandAndCollapse : {
        groupComponents : true,
        headerLabel : "Muster Roll 3 - ID (MSR/2022-23/08/0004) - Date (20-09-2022-27/09-2022)",
        headerValue : "Rs 30000"
      },
    };
    const MusterRollDetails_4 = {
      expandAndCollapse : {
        groupComponents : true,
        headerLabel : "Muster Roll 4 - ID (MSR/2022-23/08/0004) - Date (20-09-2022-27/09-2022)",
        headerValue : "Rs 30000"
      },
    };
    const TotalLabourBill = {
      additionalDetails : {
        showTotal : {
          bottomBreakLine : true,
          label : "EXP_TOTAL_LABOUR_BILL",
          value : "₹ 1,20,000"
        }
      }
    };
    const CommissionDetails = {
      title: "EXP_COMMISSION_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "EXP_NAME_OF_SHG", value: "Maa Bhagwati SHG" },
        { title: "EXP_COMMISSION_PERCENTAGE", value: "7.5%" },
        { title: "EXP_COMMISSION_AMOUNT", value: "Rs. 48949" },
      ],
    };
    const TotalCommissionAmount = {
      additionalDetails : {
        showTotal : {
          bottomBreakLine : true,
          label : "EXP_TOTAL_COMMISSION_AMOUNT",
          value : "₹ 1,20,000"
        }
      }
    };
    const TotalBillAmount = {
      additionalDetails : {
        showTotal : {
          bottomBreakLine : true,
          label : "EXP_TOTAL_BILL_AMOUNT",
          value : "₹ 3,60,000"
        }
      }
    };
    const applicationDetails = { applicationDetails: [BillDetails,MusterRollDetails_1, MusterRollDetails_2, MusterRollDetails_3, MusterRollDetails_4, TotalLabourBill, CommissionDetails, TotalCommissionAmount, TotalBillAmount] };
    return {
      applicationDetails,
      applicationData: { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
    };
  },
  };

export const fetchWOBillRecords = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
};