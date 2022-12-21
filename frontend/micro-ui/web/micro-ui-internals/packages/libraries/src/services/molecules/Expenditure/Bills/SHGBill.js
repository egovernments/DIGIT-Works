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
      const ViewVendorBill = {
        title: "EXP_VIEW_VENDOR_BILLS",
        asSectionHeader: true,
        values: [
          { title: "EXP_VENDOR", value: "Sri Ganesha Enterprises" },
          { title: "EXP_VENDOR_ID", value: "VDR/2021-22/09/0001" },
          { title: "EXP_BILL_AMOUNT", value: "5500" },
        ],
        additionalDetails : {
            statusWithRadio : {
              customClass : "border-none",
              radioConfig : {
                options : [
                  {
                    name : "BillAmount",
                    value : "SHG",
                    key : "SHG"
                  },
                  {
                    name : "BillAmount",
                    value : "Vendor",
                    key : "Vendor"
                  }
                ]
              },
            },
            documentsWithUrl : [
                {
                    title : "Upload Files",
                    values : [
                        {
                            url : "",
                            title : "Document 1",
                            documentType : "pdf",
                        },
                        {
                            url : "",
                            title : "Document 2",
                            documentType : "pdf",
                        },
                        {
                            url : "",
                            title : "Document 3",
                            documentType : "pdf",
                        }
                    ]
                }
            ],
        }
      };
      const applicationDetails = { applicationDetails: [BillDetails,MusterRollDetails_1, MusterRollDetails_2, MusterRollDetails_3, MusterRollDetails_4, ViewVendorBill] };
      return {
        applicationDetails,
        applicationData: { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
      };
    },
  };

export const fetchSHGBillRecords = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
};