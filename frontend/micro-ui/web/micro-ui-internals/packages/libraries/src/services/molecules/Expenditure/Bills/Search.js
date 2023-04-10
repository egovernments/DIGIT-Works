export const Search = {
    viewPurchaseBillDetails: async (t, tenantId, estimateNumber) => {
        let currentProject = {};
        const headerDetails = {
            title: " ",
            asSectionHeader: true,
            values: [
                { title: "WORKS_BILL_NUMBER", value: currentProject?.projectNumber || "NA"},
                { title: "WORKS_BILL_DATE", value: Digit.Utils.pt.convertEpochToDate(currentProject?.additionalDetails?.dateOfProposal) || "NA"},
                { title: "WORKS_ORDER_NO", value: currentProject?.name || "NA"},
                { title: "WORKS_PROJECT_ID", value: currentProject?.description || "NA"},
                { title: "PROJECTS_DESCRIPTION", value: currentProject?.description || "NA"},
                { title: "ES_COMMON_LOCATION", value: currentProject?.description || "NA"}
            ]
        };

        const invoiceDetails = {
            title: "EXP_INVOICE_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "EXP_VENDOR_NAME", value: currentProject?.referenceID || "NA" },
                { title: "EXP_VENDOR_ID", value: currentProject?.referenceID || "NA" },
                { title: "EXP_INVOICE_NUMBER", value: currentProject?.referenceID || "NA" },
                { title: "EXP_INVOICE_DATE", value: Digit.Utils.pt.convertEpochToDate(currentProject?.additionalDetails?.dateOfProposal) || "NA"},
                { title: "EXP_MATERIALCOST_RS", value: currentProject?.referenceID || "NA" },
                { title: "EXP_GST_RS", value: currentProject?.referenceID || "NA" },
            ]
        };
        // const details = {applicationDetails : [headerDetails, invoiceDetails]}
        const details = [headerDetails, invoiceDetails]
    
        return {
            applicationDetails: details,
            applicationData: [], //TODO:
            isNoDataFound : false //TODO:
        }
    }
}