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
          statusWithRadio : {
            customClass : "border-none",
            radioConfig : {
              label : "EXP_WHO_SHOULD_THIS_BILL_AMT_PAID_TO",
              options : [
                {
                  name : "SHG",
                  value : "SHG",
                  key : "SHG"
                },
                {
                  name : "Vendor",
                  value : "Vendor",
                  key : "Vendor"
                }
              ]
            },
          },
          documentsWithUrl : [
              {
                  title : "EXP_UPLOAD_FILES",
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
      const TotalVendorBill = {
        additionalDetails : {
          showTotal : {
            bottomBreakLine : true,
            label : "EXP_TOTAL_VENDOR_BILL",
            value : "₹ 1,20,000"
          }
        }
      };
      const applicationDetails = { applicationDetails: [BillDetails, ViewVendorBill, TotalVendorBill] };
      return {
        applicationDetails,
        applicationData: { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
      };
    },
  };

export const fetchPOBillRecords = () => {
    return transformViewDataToApplicationDetails.genericPropertyDetails();
};