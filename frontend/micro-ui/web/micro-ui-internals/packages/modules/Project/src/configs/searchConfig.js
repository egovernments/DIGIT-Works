const searchConfig = () => {
    return {
        label : "WORKS_SEARCH_PROJECTS",
        type: 'search',
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
            jsonPathForReqBody: `requestBody.Projects[0]`,
            jsonPathForReqParam:`requestParam`,
            preProcessResponese: (data) =>  data,
            mandatoryFieldsInParam: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            mandatoryFieldsInBody: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            //Note -> The above mandatory fields should not be dynamic
            //If they are dynamic they should be part of the reducer state
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
                        subProjectId: "",
                        name: "",
                        workType: "",
                        startDate: "",
                        endDate: ""
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
                            },
                        },
                        {
                            label: "PROJECT_PRJ_SUB_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "projectNumber",
                                error: `PROJECT_PATTERN_ERR_MSG`,
                                validation: { pattern: /^[a-z0-9\/-]*$/i, minlength : 2 }
                            },
                        },
                        {
                          label: "PROJECT_NAME",
                          type: "text",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "name",
                              error: `PROJECT_PATTERN_ERR_MSG`,
                              validation: { pattern: /^[a-z0-9\/-@#]*$/i, minlength : 2 }
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
                          },
                          preProcessfn: (project)=> project?.code
                        },
                        {
                          label: "CREATED_FROM_DATE",
                          type: "date",
                          isMandatory: false,
                          disable: false,
                          populators: { 
                              name: "startDate",
                              max: new Date().toISOString().split("T")[0]
                          },
                          preProcessfn: Digit.Utils.pt.convertDateToEpoch
                        },
                        {
                            label: "CREATED_TO_DATE",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: { 
                                name: "endDate",
                                error: 'DATE_VALIDATION_MSG',
                                max: new Date().toISOString().split("T")[0]
                            },
                            preProcessfn: Digit.Utils.pt.convertDateToEpoch
                        }
                    ]
                },
                label : "",
                children : {},
                show : true
            },
            searchResult: {
                label: "",
                uiConfig: {
                    defaultValues: {
                        offset: 0,
                        limit: 10,
                        // sortBy: "department",
                        sortOrder: "ASC",
                    },
                    columns: [
                        {
                            label: "WORKS_PRJ_SUB_ID",
                            jsonPath: "projectNumber",
                            redirectUrl: '/works-ui/employee/project/project-inbox-item'
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
                            // jsonPath: "createdBy",
                            // preProcessfn: (uuid) => Digit.UserService.userSearch
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
                show: true //by default true. 
            }
        },
        additionalSections : {}
    }
}

export default searchConfig;