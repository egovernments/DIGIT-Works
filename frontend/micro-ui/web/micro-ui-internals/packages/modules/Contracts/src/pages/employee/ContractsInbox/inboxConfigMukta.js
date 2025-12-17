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
                            "contract-approval-mukta"
                        ],
                        moduleName: "contract-service"
                    },
                    moduleSearchCriteria: {

                    }
                }

            },
            minParametersForSearchForm: 0,
            minParametersForFilterForm: 0,
            masterName: "commonUiConfig",
            moduleName: "ContractsInboxConfig",
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
                        workOrderNumber: "",
                        projectId: "",
                        projectType: "",
                    },
                    fields: [
                        {
                            label: "WO_INBOX_NUMBER",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            preProcess: {
                                convertStringToRegEx: ["populators.validation.pattern"]
                            },
                            populators: {
                                name: "workOrderNumber",
                                error: `CONTRACT_PATTERN_ERR_MSG`,
                                validation: { pattern: "WO\\/[0-9]+-[0-9]+\\/[0-9]+\\/[0-9]+", minlength: 2 }
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
                                validation: { pattern: "PJ\\/[0-9]+-[0-9]+\\/[0-9]+\\/[0-9]+", minlength: 2 }
                            },
                        },
                        {
                            label: "WORKS_PROJECT_TYPE",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "projectType",
                                optionsKey: "name",
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
                            label: "WORKS_INBOX_ORDER_NO",
                            jsonPath: "ProcessInstance.businessId",
                            additionalCustomization: true,
                            key:"workOrderNumber"
                        },
                        {
                            label: "WORKS_PROJECT_NAME",
                            jsonPath: "businessObject.additionalDetails.projectName"
                        },
                        {
                            label: "ES_COMMON_CBO_NAME",
                            jsonPath: "businessObject.additionalDetails.orgName",
                        },
                        {
                            label: "COMMON_ASSIGNEE",
                            jsonPath: "ProcessInstance.assignes",
                            "additionalCustomization": true,
                            "key": "assignee"
                        },
                        {
                            label: "COMMON_WORKFLOW_STATES",
                            jsonPath: "ProcessInstance.state.state",
                            "additionalCustomization": true,
                            "key": "state"
                        },
                        {
                            label: "ES_COMMON_AMOUNT",
                            jsonPath: "businessObject.totalContractedAmount",
                            additionalCustomization: true,
                            "key": "estimatedAmount",
                            headerAlign: "right"
                        },
                        {
                            "label": "COMMON_SLA_DAYS",
                            "jsonPath": "businessObject.serviceSla",
                            "additionalCustomization": true,
                            "key": "sla"
                        }
                    ],
                    enableGlobalSearch: false,
                    enableColumnSort: true,
                    resultsJsonPath: "items",
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
                            roles: ["WORK_ORDER_CREATOR"],
                        },
                        {
                            text: "ACTION_TEST_SEARCH_WO",
                            url: `/employee/contracts/search-contract`,
                            roles: ["WORK_ORDER_CREATOR", "WORK_ORDER_VERIFIER","WORK_ORDER_APPROVER"],
                        },
                    ],
                    label: "MUKTA",
                    logoIcon: {   
                        component: "PropertyHouse",
                        customClass: "inbox-search-icon--projects"
                    }
                },
                children: {},
                show: true  
            },
            filter: {
                uiConfig: {
                    type: 'filter',
                    headerStyle: null,
                    primaryLabel: 'Filter',
                    secondaryLabel: '',
                    minReqFields: 0,
                    defaultValues: {
                        state: "",
                        ward: [],
                        locality: [],
                        assignee: { code: "ASSIGNED_TO_ALL", name: "COMMON_INBOX_ASSIGNED_TO_ALL" }
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
                                optionsKey: "i18nKey",
                                defaultText: "COMMON_SELECT_WARD",
                                selectedText: "COMMON_SELECTED",
                                allowMultiSelect: true,
                                isDropdownWithChip:true,
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
                                optionsKey: "i18nKey",
                                defaultText: "COMMON_SELECT_LOCALITY",
                                selectedText: "COMMON_SELECTED",
                                allowMultiSelect: true,
                                isDropdownWithChip:true,
                            }
                        },
                        {
                            label: "COMMON_WORKFLOW_STATES",
                            type: "workflowstatesfilter",
                            labelClassName:"checkbox-status-filter-label" ,
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