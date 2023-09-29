
export const createEstimateConfig = () => {
    return {
        "form": [
            {
                "head": "",
                "subHead": "",
                "navLink": "Project Details",
                "body": [
                    {
                        "type": "component",
                        "component": "ViewProject",
                        "withoutLabel": true,
                        "key": "projectDetails",
                        "customProps": {
                            "module": "estimate"
                        }
                    }
                ]
            },
            {
                "head": "SOR",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
               
                    
                    {
                        type: "component",
                        component: "searchSor",
                        withoutLabel: true,
                        key: "searchSor",
                        
                    },
                ]
            },
            {
                "head": "NON SOR",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    {
                        type: "component",
                        component: "MeasureTable",
                        withoutLabel: true,
                        customProps: {
                            isEstimate: true
                        },
                        key: "SOR",
                    },
                    
                    // {
                    //     "type": "component",
                    //     "component": "NonSORTable",
                    //     "withoutLabel": true,
                    //     "key": "nonSORDetails",
                    //     "populators": {
                    //         "rate": {
                    //             "max": 5000000,
                    //             "error": "ESTIMATE_LINE_ITEM_RATE_LIMIT",
                    //         },

                    //         "quantity": {
                    //             "max": 999999,
                    //             "error": "ESTIMATE_LINE_ITEM_QTY_LIMIT"
                    //         }
                    //     }
                    // }

                ]
            },
            {
                "head": "Other Charges",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    {
                        "type": "component",
                        "component": "OverheadsTable",
                        "withoutLabel": true,
                        "key": "overheadsDetails"
                    }
                ]
            },
            {
                "head": "",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    {
                        "type": "component",
                        "component": "TotalEstAmount",
                        "withoutLabel": true,
                        "key": "totalEstimatedAmount"
                    }
                ]
            },
            {
                "head": "",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    {
                        "type": "component",
                        "component": "LabourAnalysis",
                        "withoutLabel": true,
                        "key": "labourMaterialAnalysis"
                    }
                ]
            },
            {
                "navLink": "Work Details",
                "head": "",
                "body": [
                    {
                        "type": "documentUpload",
                        "withoutLabel": true,
                        "module": "Estimate",
                        "error": "WORKS_REQUIRED_ERR",
                        "name": "uploadedDocs",
                        "customClass": "",
                        "localePrefix": "ESTIMATE_DOC"
                    }
                ]
            }
        ]
    }
};
