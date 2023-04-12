export const Search = {
    viewPurchaseBillDetails: async (t) => {
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

        const billDetails = {
            title: "EXP_INVOICE_DETAILS",
            asSectionHeader: true,
            values: [
                { title: "EXP_BILL_DETAILS", value: currentProject?.referenceID || "NA" },
            ]
        };

        const deductionsTableRows = [t("WORKS_SNO"), t("EXP_DEDUCTION_NAME"), t("EXP_PERCENTAGE_OR_FIXED"), t("ES_COMMON_AMOUNT"), t("WF_COMMON_COMMENTS")] 
        const deductionsTableData = [];
        const deductionsTableTotalAmount = "";
        deductionsTableData?.push(["","","","" ,t("RT_TOTAL"), Digit.Utils.dss.formatterWithoutRound(deductionsTableTotalAmount, 'number')])
        const deductionsTable = {
            title: "WORKS_NON_SOR",
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

        const netPayableAmt = {
            "title": " ",
            "asSectionHeader": true,
            "Component": Digit.ComponentRegistryService.getComponent("ViewTotalEstAmount"),
            "value": Digit.Utils.dss.formatterWithoutRound(t("NA"))
        }

        const documentDetails = {
            title: "",
            asSectionHeader: true,
            additionalDetails: {
                documents: [{
                    title: "WORKS_RELEVANT_DOCS",
                    BS: 'Works',
                    values: [],
                },
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
}