const searchConfig = () => {
    return {
        label: "WORKS_SEARCH_ESTIMATES",
        type: 'search',
        apiDetails: {
            serviceName: "/estimate/v1/_search",
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
                    defaultValues: {
                        estimateNumber: "",
                        projectId: "",
                        department: "",
                        estimateStatus: "",
                        fromProposalDate: "",
                        toProposalDate: ""
                    },
                    fields: [
                        {
                            label: "WORKS_ESTIMATE_ID",
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
                            label: "WORKS_PROJECT_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "projectId",
                                error: `PROJECT_PATTERN_ERR_MSG`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength: 2 }
                            },
                        },
                        {
                            label: "WORKS_DEPARTMENT",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "department",
                                optionsKey: "name",
                                mdmsConfig: {
                                    masterName: "Department",
                                    moduleName: "common-masters",
                                    localePrefix: "COMMON_MASTERS_DEPARTMENT",
                                }
                            },
                        },
                        {
                            label: "WORKS_STATUS",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "estimateStatus",
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
                            label: "WORKS_ESTIMATE_ID",
                            jsonPath: "estimateNumber",
                            additionalCustomization: true
                        },
                        {
                            label: "WORKS_PROJECT_ID",
                            jsonPath: "projectId"
                        },
                        {
                            label: "PROJECT_OWNING_DEPT",
                            jsonPath: "executingDepartment",
                            translate: true,
                            prefix: "COMMON_MASTERS_DEPARTMENT_",
                        },
                        {
                            label: "WORKS_STATUS",
                            jsonPath: "wfStatus",
                        },
                        {
                            label: "WORKS_ESTIMATED_AMOUNT",
                            jsonPath: "estimateDetails",
                            additionalCustomization:true
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

export default searchConfig;