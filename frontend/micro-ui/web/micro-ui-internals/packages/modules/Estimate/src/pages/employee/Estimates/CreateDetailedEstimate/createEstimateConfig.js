
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
                "head": "ESTIMATE_TEMPLATE_HEADER",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    {
                        type: "component",
                        component: "searchTemplate",
                        withoutLabel: true,
                        key: "searchTemplate",
                        
                    },
                ]
            },
            {
                "head": "",
                "subHead": "",
                "navLink": "Work Details",
                "body": [
                    // {
                    //     label: "WORKS_PROJECT_TYPE",
                    //     type: "dropdown",
                    //     isMandatory: false,
                    //     disable: false,
                    //     key: "projectType",

                    //     populators: {
                    //       name: "projectType",
                    //       optionsKey: "name",
                    //       optionsCustomStyle: {
                    //         top: "2.3rem",
                    //       },
                    //       mdmsConfig: {
                    //         masterName: "Type",
                    //         moduleName: "WORKS-SOR",
                    //         localePrefix: "COMMON_MASTERS",
                    //         v2:true
                    //       },
                    //     },
                    //   },
                    {
                        type: "component",
                        component: "searchSor",
                        withoutLabel: true,
                        key: "searchSor",
                        
                    },
                ]
            },
            {
                "head": " SOR",
                "subHead": "",
                "navLink": "Work Details",
                "sectionClassName":"table-included-section",
                "body": [
                    {
                        type: "component",
                        component: "EstimateMeasureTableWrapper",
                        withoutLabel: true,
                        key: "SOR",
                        customProps: {
                           mode:"CREATEALL"
                        },
                        useFieldArray:true,
                        mode:"CREATEALL"
                    },
                    
                

                ]
            },
            {
                "head": "NON_SOR",
                "subHead": "",
                "navLink": "Work Details",
                "sectionClassName":"table-included-section",
                "body": [
                    {
                        type: "component",
                        component: "EstimateMeasureTableWrapper",
                        withoutLabel: true,
                        customProps: {
                            mode:"CREATEALL"
                         },
                        useFieldArray:true,
                        key: "NONSOR",
                        mode:"CREATEALL"
                    },     
                
                ]
            },
            {
                "head": "Other Charges",
                "subHead": "",
                "navLink": "Work Details",
                "sectionClassName":"table-included-section",
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
                "sectionClassName":"viewstatement-viewamount-wrapper-create",
                "navLink": "Work Details",
                "body": [
                    {
                        "type": "component",
                        "component": "TotalEstAmount",
                        "withoutLabel": true,
                        "key": "totalEstimatedAmount",
                        "props":{
                            style:{
                                marginTop:"0px"
                            }
                        }
                    },
                    {
                        "type": "component",
                        "component": "ViewAnalysisStatement",
                        "withoutLabel": true,
                        "key": "labourMaterialAnalysis"
                    }
                ]
            },
            // {
            //     "head": "",
            //     "subHead": "",
            //     "navLink": "Work Details",
            //     "body": [
            //         {
            //             "type": "component",
            //             "component": "ViewAnalysisStatement",
            //             "withoutLabel": true,
            //             "key": "labourMaterialAnalysis"
            //         }
            //     ]
            // },
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
