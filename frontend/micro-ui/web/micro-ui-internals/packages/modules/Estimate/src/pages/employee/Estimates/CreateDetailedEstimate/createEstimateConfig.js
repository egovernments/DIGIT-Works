
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
                "head": "",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    
                    // {
                    //     type: "component",
                    //     component: "searchSor",
                    //     withoutLabel: true,
                    //     key: "searchSor",
                        
                    // },
                ]
            },
            {
                "head": " SOR",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    {
                        type: "component",
                        component: "MeasureTable",
                        withoutLabel: true,
                        key: "SOR",
                        customProps: {
                           mode:"createall"
                        },
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
                            mode:"createall"
                         },
                        key: "NONSOR",
                    },
                    
                

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
