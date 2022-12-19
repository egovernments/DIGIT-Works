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
      const ViewVendorBill = {
        title: "EXP_VIEW_VENDOR_BILLS",
        asSectionHeader: true,
        values: [
          { title: "EXP_VENDOR", value: "Sri Ganesha Enterprises" },
          { title: "EXP_VENDOR_ID", value: "VDR/2021-22/09/0001" },
          { title: "EXP_CREATED_DATE", value: "28-09-2022" },
          { title: "EXP_BILL_AMOUNT", value: "5500" },
        ],
        additionalDetails : {
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
            ]
        }
      };
      const applicationDetails = { applicationDetails: [BillDetails, ViewVendorBill] };
      return {
        applicationDetails,
        applicationData: { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
      };
    },
  };

export const fetchWOBillRecords = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
};