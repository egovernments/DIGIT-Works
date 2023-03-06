const inboxConfigMukta = (t) => {
    return {
        label: "ACTION_TEST_ESTIMATE_INBOX",
        type: 'inbox',
        apiDetails: {
            serviceName: "/estimate-service/estimate/v1/_search",
            requestParam: {

            },
            requestBody: {

            },
            minParametersForSearchForm: 0,
            minParametersForFilterForm: 0,
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
                        projectType: "",
                        // fromProposalDate: "",
                        // toProposalDate: ""
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
                            text: "WORKS_CREATE_ESTIMATE",
                            url: `/employee/project/search-project`,
                            roles: [],
                        },
                        {
                            text: "ACTION_TEST_SEARCH_ESTIMATE",
                            url: `/employee/estimate/search-estimate`,
                            roles: [],
                        },
                    ],
                    label: "ACTION_TEST_ESTIMATE_INBOX",
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
                        createdBy: "",
                        fromProposalDate: "",
                        toProposalDate: "",
                        status: "",
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
                                    { code: "ASSIGNED_TO_ME", name: "ES_INBOX_ASSIGNED_TO_ME" },
                                    { code: "ASSIGNED_TO_ALL", name: "ES_INBOX_ASSIGNED_TO_ALL" },
                                ],
                                optionsKey:"name",
                                styles:{
                                    "gap":"1rem",
                                    "flexDirection":"column"
                                },
                                innerStyles:{
                                    "display":"flex"
                                }

                            },
                        },
                        // {
                        //     label: "COMMON_WARD",
                        //     type: "multiselectdropdown",
                        //     isMandatory: false,
                        //     disable: false,
                        //     populators: {
                        //         name: "ward",
                        //         optionsKey: "name",
                        //         options:[
                        //                     {
                        //                         "name":"Name one"
                        //                     },
                        //                     {
                        //                         "name":"Name two"
                        //                     }
                        //         ],
                        //         defaultText:"COMMON_SELECT_WARD",
                        //         selectedText:"COMMON_SELECTED"
                        //     }
                        // },
                        {
                            label: "COMMON_WARD",
                            type: "locationdropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "ward",
                                type:"ward",
                                optionsKey: "name",
                                defaultText: "COMMON_SELECT_WARD",
                                selectedText: "COMMON_SELECTED",
                                allowMultiSelect:true
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
                                name: "workflowState",
                                labelPrefix:"WORKFLOW_"
                            }
                        },
                        // {
                        //     label: "COMMON_WARD",
                        //     type: "multiselectdropdown",
                        //     isMandatory: false,
                        //     disable: false,
                        //     populators: {
                        //         name: "ward",
                        //         optionsKey: "name",
                        //         mdmsConfig: {
                        //             masterName: "TypeOfWork",
                        //             moduleName: "works",
                        //             localePrefix: "WORKS",
                        //         },
                        //         options: [
                        //             {
                        //                 "name": "Nipun"
                        //             },
                        //             {
                        //                 "name": "Vipul"
                        //             }
                        //         ],
                        //         defaultText: "COMMON_SELECT_WARD",
                        //         selectedText: "COMMON_SELECTED"
                        //     }
                        // },
                        // {
                        //     label: "WORKS_COMMON_FROM_DATE_LABEL",
                        //     type: "date",
                        //     isMandatory: false,
                        //     disable: false,
                        //     populators: {
                        //         name: "fromProposalDate"
                        //     },
                        // },
                        // {
                        //     label: "WORKS_COMMON_TO_DATE_LABEL",
                        //     type: "date",
                        //     isMandatory: false,
                        //     disable: false,
                        //     populators: {
                        //         name: "toProposalDate"
                        //     },
                        // },
                        // {
                        //     label: "WORKS_CREATED_BY",
                        //     type: "dropdown",
                        //     isMandatory: false,
                        //     disable: false,
                        //     populators: {
                        //         name: "createdBy",
                        //         optionsKey: "name",
                        //         mdmsConfig: {
                        //             masterName: "TypeOfWork",
                        //             moduleName: "works",
                        //             localePrefix: "WORKS",
                        //         }
                        //     }
                        // },
                        // {
                        //     label: "WORKS_STATUS",
                        //     type: "dropdown",
                        //     isMandatory: false,
                        //     disable: false,
                        //     populators: {
                        //         name: "status",
                        //         optionsKey: "name",
                        //         mdmsConfig: {
                        //             masterName: "TypeOfWork",
                        //             moduleName: "works",
                        //             localePrefix: "WORKS",
                        //         }
                        //     }
                        // }
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