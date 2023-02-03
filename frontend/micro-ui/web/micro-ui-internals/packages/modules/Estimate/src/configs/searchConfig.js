const searchConfig = () => {
    return {
        label: "Search Projects",
        type: 'search',
        apiDetails: {
            serviceName: "/estimate-service/estimate/v1/_search",
            requestParam: {
                limit: 10,
                offset: 0,
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            requestBody: {
                // apiOperation: "SEARCH",
                // Projects: [
                //     {
                //         tenantId: Digit.ULBService.getCurrentTenantId()
                //     }
                // ]
            },
            jsonPathForReqBody: `requestBody.Projects[0]`,
            jsonPathForReqParam: `requestParam`,
            preProcessResponese: (data) => data,
            mandatoryFieldsInParam: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            mandatoryFieldsInBody: {
                tenantId: Digit.ULBService.getCurrentTenantId(),
            },
            //Note -> The above mandatory fields should not be dynamic(like limit,offset)
            //If they are dynamic they should be part of the reducer state
        },
        //estimateNumber,projectId,department,estimateStatus,fromProposalDate,toProposalDate
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
                                // validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }
                            },
                        },
                        {
                            label: "WORKS_PROJECT_ID",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "projectId",
                                // validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }
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
                            preProcessfn: (estimate) => estimate?.code
                        },
                        {
                            label: "WORKS_STATUS",
                            type: "text",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "estimateStatus",
                                //   validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }
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
                            preProcessfn: Digit.Utils.pt.convertDateToEpoch
                        },
                        {
                            label: "WORKS_COMMON_TO_DATE_LABEL",
                            type: "date",
                            isMandatory: false,
                            disable: false,
                            populators: {
                                name: "toProposalDate"
                            },
                            preProcessfn: Digit.Utils.pt.convertDateToEpoch
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
        additionalSections: {}
    }
}

export default searchConfig;