const searchConfig = () => {
    return {
        label: "Search Projects",
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
                uiConfig: {
                    defaultValues: {
                        offset: 0,
                        limit: 10,
                        sortOrder: "ASC",
                    },
                    columns: [
                        {
                            label: "WORKS_PRJ_SUB_ID",
                            jsonPath: "projectNumber",
                            additionalCustomization: true
                        },
                        {
                            label: "WORKS_PROJECT_NAME",
                            jsonPath: "name"
                        },
                        {
                            label: "PROJECT_OWNING_DEPT",
                            jsonPath: "department",
                            translate: true,
                            prefix: "COMMON_MASTERS_DEPARTMENT_",
                        },
                        {
                            label: "WORKS_PROJECT_TYPE",
                            jsonPath: "projectType",
                        },
                        {
                            label: "WORKS_SUB_PROJECT_TYPE",
                            jsonPath: "projectSubType",
                        },
                        {
                            label: "WORKS_WORK_NATURE",
                            jsonPath: "endDate",
                        },
                        {
                            label: "WORKS_PARENT_PROJECT_ID",
                            jsonPath: "parentId",
                        },
                        {
                            label: "WORKS_CREATED_BY",
                            jsonPath: "auditDetails.createdBy",
                        },
                        {
                            label: "WORKS_STATUS",
                            jsonPath: "status",
                        },
                        {
                            label: "WORKS_TOTAL_AMOUNT",
                            jsonPath: "totalAmount",
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "Projects",
                },
                children: {},
                show: true
            }
        },
        additionalSections: {}
    }
}

export default searchConfig;