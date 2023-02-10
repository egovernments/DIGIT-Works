const inboxConfig = () => {
    return {
        label : "ES_COMMON_INBOX",
        type : "inbox", 
        //Added search config, will be updated with inbox api config while integration
        apiDetails: {
            serviceName: "/pms/project/v1/_search",
            requestParam: {
                limit:10,
                offset:0,
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            requestBody: {
                apiOperation: "SEARCH",
                Projects: [
                    {
                        tenantId: Digit.ULBService.getCurrentTenantId()
                    }
                ]
            },
            minParametersForSearchForm:1,
            masterName:"commonUiConfig",
            moduleName:"SearchProjectConfig",
            tableFormJsonPath:"requestParam",
            filterFormJsonPath:"requestBody.Projects[0]",
            searchFormJsonPath:"requestBody.Projects[0]"
        },
        sections : {
            search : {
                uiConfig : {
                    headerStyle : null,
                    primaryLabel: 'ACTION_TEST_SEARCH',
                    secondaryLabel: 'CLEAR_SEARCH_LINk',
                    minReqFields: 1,
                    defaultValues : {
                        projectNumber: "",
                        department: "",
                        projectType: ""
                    },
                    fields : [
                        {
                            label:"WORKS_PROJECT_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber",
                                error: `PROJECT_PATTERN_ERR_MSG`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            }
                        },
                        {
                            label: "ACTION_TEST_DEPARTMENT",
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
                            }
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
                              localePrefix: "ES_COMMON",
                            }
                          }
                        }
                    ]
                },
                label : "",
                children : {},
                show : true
            },
            links : {
                uiConfig : {
                    links : [
                        {
                            text: "WORKS_CREATE_PROJECT",
                            url: `/employee/project/create-project`,
                            roles: [],
                        }
                    ],
                    label : "ACTION_TEST_PROJECT_INBOX",
                    logoIcon : { //Pass the name of the Icon Component as String here and map it in the InboxSearchLinks Component   
                        component : "PropertyHouse",
                        customClass : "inbox-search-icon--projects"         
                    }
                },
                children : {},
                show : true //by default true. 
            },
            filter : {
                uiConfig : {
                    type : 'filter',
                    headerStyle : null,
                    primaryLabel: 'ACTION_TEST_APPLY',
                    minReqFields: 0,
                    secondaryLabel: '',
                    defaultValues : {
                        projectFromDate: "",
                        projectToDate: "",
                        createdBy: "",
                        status: ""
                    },
                    fields : [
                        {
                            label:"WORKS_PROJECT_CREATED_FROM_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectFromDate"
                            },
                        },
                        {
                            label:"WORKS_PROJECT_CREATED_TO_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectToDate"
                            },
                        },
                        {
                            label: "WORKS_CREATED_BY",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "createdBy",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "TypeOfWork",
                                moduleName: "works",
                                localePrefix: "WORKS",
                              }
                            }
                        },
                        {
                            label: "WORKS_STATUS",
                            type: "dropdown",
                            isMandatory: false,
                            disable: false,
                            populators: {
                              name: "status",
                              optionsKey: "name",
                              mdmsConfig: {
                                masterName: "TypeOfWork",
                                moduleName: "works",
                                localePrefix: "WORKS",
                              }
                            }
                        }
                    ]
                },
                label : "FILTERS",
                show : true
            },
            searchResult: {
                label: "",
                uiConfig: {
                    columns: [
                        {
                            label: "WORKS_PRJ_SUB_ID",
                            jsonPath: "projectNumber",
                            additionalCustomization:true
                        },
                        {
                            label: "WORKS_PROJECT_NAME",
                            jsonPath: "name"
                        },
                        {
                            label: "PROJECT_OWNING_DEPT",
                            jsonPath: "department",
                            translate:true,
                            prefix:"COMMON_MASTERS_DEPARTMENT_",
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
        additionalSections : {
            //Open for Extensions
            //One can create a diff Parent card and add additional fields in it.
        }
    }
}

export default inboxConfig;