const searchConfigMukta = () => {
    return {
        label: "WORKS_SEARCH_ESTIMATES",
        type: 'search',
        apiDetails: {
            serviceName: "/estimate-service/estimate/v1/_search",
            requestParam: {

            },
            requestBody: {

            },
            minParametersForSearchForm: 1,
            masterName: "commonUiConfig",
            moduleName: "SearchEstimateConfig",
            tableFormJsonPath: "requestParam",
            filterFormJsonPath: "requestParam",
            searchFormJsonPath: "requestParam",
        },
        sections: {
            search: {
                uiConfig: {
                    headerStyle: null,
                    primaryLabel: 'Search',
                    secondaryLabel: 'Clear Search',
                    minReqFields: 1,
                    showFormInstruction:"ESTIMATE_SEARCH_HINT",
                    defaultValues: {
                        ward: "",
                        projectType: "",
                        projectName: "",
                        estimateNumber: "",
                        wfStatus: "",
                        fromProposalDate: "",
                        toProposalDate: "",    
                    },
                    fields: [
                        {
                            label: "COMMON_WARD",
                            type: "locationdropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "ward",
                                type: "ward",
                                optionsKey: "name",
                                defaultText: "COMMON_SELECT_WARD",
                                selectedText: "COMMON_SELECTED",
                                allowMultiSelect: false
                            }
                        },
                        {
                            "label": "WORKS_PROJECT_TYPE",
                            "type": "dropdown",
                            "isMandatory": false,
                            "disable": false,
                            "populators": {
                                "name": "projectType",
                                "optionsKey": "name",
                                "optionsCustomStyle": {
                                    "top": "2.3rem"
                                },
                                "mdmsConfig": {
                                    "masterName": "ProjectType",
                                    "moduleName": "works",
                                    "localePrefix": "COMMON_MASTERS"
                                }
                            }
                        },
                        {
                            "label": "WORKS_PROJECT_NAME",
                            "type": "text",
                            "isMandatory": false,
                            "disable": false,
                            "preProcess": {
                                "convertStringToRegEx": [
                                    "populators.validation.pattern"
                                ]
                            },
                            "populators": {
                                "name": "projectName",
                                "error": "PROJECT_PATTERN_ERR_MSG",
                                "validation": {
                                    "pattern": "^[^\\$\"<>?\\\\~`!@$%^()+={}\\[\\]*:;“”‘’]{1,50}$",
                                    "minlength": 2
                                }
                            }
                        },
                        {
                            label: "ESTIMATE_ESTIMATE_NO",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "estimateNumber",
                                error: `ESTIMATE_PATTERN_ERR_MSG`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength: 2 }
                            },
                        },
                        {
                            label: "CORE_COMMON_STATUS",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "wfStatus",
                            }
                        },
                        {
                            label: "WORKS_COMMON_FROM_DATE_LABEL",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "fromProposalDate"
                            },
                        },
                        {
                            label: "WORKS_COMMON_TO_DATE_LABEL",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "toProposalDate"
                            },
                        }
                    ]
                },
                label: "",
                children: {},
                show: true
            },
            searchResult: {
                label: "",
                estimateNumber: "",
                projectId: "",
                department: "",
                estimateStatus: "",
                fromProposalDate: "",
                toProposalDate: "",
                uiConfig: {
                    columns: [
                        {
                            label: "ESTIMATE_ESTIMATE_NO",
                            jsonPath: "estimateNumber",
                            additionalCustomization: true
                        },
                        {
                            label: "ES_COMMON_PROJECT_NAME",
                            jsonPath: "name"
                        },
                        {
                            label: "ES_COMMON_LOCATION",
                            // jsonPath: "additionalDetails.creator",
                            // translate: true,
                            //prefix: "",
                            additionalCustomization: true
                        },
                        {
                            label: "ESTIMATE_PREPARED_BY",
                            jsonPath: "additionalDetails.creator",
                            // translate: true,
                            //prefix: "",
                        },
                        
                        {
                            label: "CORE_COMMON_STATUS",
                            jsonPath: "wfStatus",
                        },
                        {
                            label: "WORKS_ESTIMATED_AMOUNT",
                            jsonPath: "estimateDetails",
                            additionalCustomization: true
                        },
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "estimates",
                },
                children: {},
                show: true
            }
        },
        additionalSections: {}
    }
}

export default searchConfigMukta;