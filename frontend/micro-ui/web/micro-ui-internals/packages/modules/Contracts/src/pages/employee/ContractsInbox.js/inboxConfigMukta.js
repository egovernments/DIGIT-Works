const inboxConfigMukta = () => {
    return {
        label: "ACTION_TEST_WO_INBOX",
        type: 'inbox',
        apiDetails: {
            serviceName: "/inbox/v2/_search",
            requestParam: {

            },
            requestBody: {
                inbox: {
                    processSearchCriteria: {
                        businessService: [
                            "contract-approval-mukta"//CHANGE
                        ],
                        moduleName: "contracts-service"//CHANGE
                    },
                    moduleSearchCriteria: {

                    }
                }

            },
            minParametersForSearchForm: 0,
            minParametersForFilterForm: 0,
            masterName: "commonUiConfig",
            moduleName: "EstimateInboxConfig",//CHANGE
            tableFormJsonPath: "requestBody.inbox",
            filterFormJsonPath: "requestBody.inbox.moduleSearchCriteria",
            searchFormJsonPath: "requestBody.inbox.moduleSearchCriteria",
        },
        sections: {
            search: {
                uiConfig: {
                    headerStyle: null,
                    primaryLabel: 'Search',
                    secondaryLabel: 'Clear Search',
                    minReqFields: 1,
                    defaultValues: {
                        estimateNumber: "",//CHANGE
                        projectId: "",
                        projectType: "",
                    },
                    fields: [
                        {
                            label: "WO_NUMBER",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            preProcess: {
                                convertStringToRegEx: ["populators.validation.pattern"]
                            },
                            populators: {
                                name: "estimateNumber",//CHANGE
                                error: `ESTIMATE_PATTERN_ERR_MSG`,
                                validation: { pattern: "^[a-zA-Z0-9\\/-]*$", minlength: 2 }
                            },
                        },
                        {
                            label: "WORKS_PROJECT_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            preProcess: {
                                convertStringToRegEx: ["populators.validation.pattern"]
                            },
                            populators: {
                                name: "projectId",
                                error: `PROJECT_PATTERN_ERR_MSG`,
                                validation: { pattern: "^[a-zA-Z0-9\\/-]*$", minlength: 2 }
                            },
                        },
                        {
                            label: "WORKS_PROJECT_TYPE",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "projectType",
                                optionsKey: "code",
                                mdmsConfig: {
                                    masterName: "ProjectType",
                                    moduleName: "works",
                                    localePrefix: "COMMON_MASTERS"
                                }
                            },
                        },

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
                            additionalCustomization: true
                        },
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "estimates",
                },
                children: {},
                show: true
            },
            links: {
                uiConfig: {
                    links: [
                        {
                            text: "ACTION_TEST_CREATE_WO",
                            url: `/employee/estimate/search-estimate`,
                            roles: [],
                        },
                        {
                            text: "ACTION_TEST_SEARCH_WO",
                            url: `/employee/contracts/search-contract`,
                            roles: [],
                        },
                    ],
                    label: "MUKTA",
                    logoIcon: { //Pass the name of the Icon Component as String here and map it in the InboxSearchLinks Component   
                        component: "PropertyHouse",
                        customClass: "inbox-search-icon--projects"
                    }
                },
                children: {},
                show: true //by default true. 
            },
            filter: {
                uiConfig: {
                    type: 'filter',
                    headerStyle: null,
                    primaryLabel: 'Filter',
                    secondaryLabel: '',
                    minReqFields: 1,
                    defaultValues: {
                        state: "",
                        ward: [],
                        locality: [],
                        assignee: ""
                    },
                    fields: [
                        {
                            label: "",
                            type: "radio",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "assignee",
                                options: [
                                    { code: "ASSIGNED_TO_ME", name: "COMMON_INBOX_ASSIGNED_TO_ME" },
                                    { code: "ASSIGNED_TO_ALL", name: "COMMON_INBOX_ASSIGNED_TO_ALL" },
                                ],
                                optionsKey: "name",
                                styles: {
                                    "gap": "1rem",
                                    "flexDirection": "column"
                                },
                                innerStyles: {
                                    "display": "flex"
                                }

                            },
                        },
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
                                allowMultiSelect: true
                            }
                        },
                        {
                            label: "COMMON_LOCALITY",
                            type: "locationdropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "locality",
                                type: "locality",
                                optionsKey: "name",
                                defaultText: "COMMON_SELECT_LOCALITY",
                                selectedText: "COMMON_SELECTED",
                                allowMultiSelect: true
                            }
                        },
                        {
                            label: "COMMON_WORKFLOW_STATES",
                            type: "workflowstatesfilter",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "state",
                                labelPrefix: "WF_WO_",
                                businessService: "contract-approval-mukta"
                            }
                        },
                    ]
                },
                label: "Filter",
                show: true
            },
        },
        additionalSections: {}
    }
}

export default inboxConfigMukta;