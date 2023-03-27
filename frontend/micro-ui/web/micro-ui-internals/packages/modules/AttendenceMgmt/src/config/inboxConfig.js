const inboxConfig = () => {
    return {
            "label" : "ES_COMMON_INBOX",
            "postProcessResult": true,
            "type" : "inbox", 
            "apiDetails": {
                "serviceName": "/inbox/v2/_search",
                "requestParam": {},
                "requestBody": {
                    "inbox": {
                        "processSearchCriteria": {
                            "businessService": [
                                "muster-roll-approval"
                            ],
                            "moduleName": "muster-roll-service"
                        },
                        "moduleSearchCriteria": {}
                    }
                },
                "minParametersForSearchForm":1,
                "masterName":"commonUiConfig",
                "moduleName":"AttendanceInboxConfig",
                "tableFormJsonPath":"requestBody.inbox",
                "filterFormJsonPath":"requestBody.inbox.moduleSearchCriteria",
                "searchFormJsonPath":"requestBody.inbox.moduleSearchCriteria"
            },
            "sections" : {
                "search" : {
                    "uiConfig" : {
                        "headerStyle" : null,
                        "primaryLabel": "ES_COMMON_SEARCH",
                        "secondaryLabel": "ES_COMMON_CLEAR_SEARCH",
                        "minReqFields": 1,
                        "defaultValues" : {
                            "projectId": "",
                            "projectType":"",
                            "mustorRollId":""
                        },
                        "fields" : [
                            {
                                "label": "ATM_MUSTER_ROLL_ID",
                                "type": "text",
                                "isMandatory": false,
                                "disable": false,
                                "preProcess": {
                                    "convertStringToRegEx": [
                                        "populators.validation.pattern"
                                    ]
                                },
                                "populators": {
                                    "name": "mustorRollId",
                                    "error": "PROJECT_PATTERN_ERR_MSG",
                                    "validation": {
                                        "pattern": "MR\\/[0-9]+-[0-9]+\\/[0-9]+\\/[0-9]+",
                                        "minlength": 2
                                    }
                                }
                            },
                            {
                                "label": "WORKS_PROJECT_ID",
                                "type": "text",
                                "isMandatory": false,
                                "disable": false,
                                "preProcess": {
                                    "convertStringToRegEx": [
                                        "populators.validation.pattern"
                                    ]
                                },
                                "populators": {
                                    "name": "projectId",
                                    "error": "PROJECT_PATTERN_ERR_MSG",
                                    "validation": {
                                        "pattern": "PR\\/[0-9]+-[0-9]+\\/[0-9]+\\/[0-9]+",
                                        "minlength": 2
                                    }
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
                            }
                        ]
                    },
                    "label" : "",
                    "children" : {},
                    "show" : true
                },
                "links" : {
                    "uiConfig" : {
                        "links" : [
                            {
                                "text": "ATM_SEARCH_ATTENDANCE",
                                "url": "/employee/attendencemgmt/search-attendance",
                                "roles": ["MUSTER_ROLL_VERIFIER","MUSTER_ROLL_APPROVER"]
                            }
                        ],
                        "label" : "ES_COMMON_ATTENDENCEMGMT",
                        "logoIcon" : {
                            "component" : "BioMetricIcon",
                            "customClass" : "search-icon--projects"       
                        }
                    },
                    "children" : {},
                    "show" : true 
                },
                "filter" : {
                    "uiConfig": {
                            "type": "filter",
                            "headerStyle": null,
                            "primaryLabel": "Filter",
                            "secondaryLabel": "",
                            "minReqFields": 1,
                            "defaultValues": {
                                "state": "",
                                "ward": [],
                                "locality": [],
                                "assignee": {
                                    "code": "ASSIGNED_TO_ALL",
                                    "name": "EST_INBOX_ASSIGNED_TO_ALL"
                                }
                            },
                            "fields": [
                                {
                                    "label": "",
                                    "type": "radio",
                                    "isMandatory": false,
                                    "disable": false,
                                    "populators": {
                                        "name": "assignee",
                                        "options": [
                                            {
                                                "code": "ASSIGNED_TO_ME",
                                                "name": "EST_INBOX_ASSIGNED_TO_ME"
                                            },
                                            {
                                                "code": "ASSIGNED_TO_ALL",
                                                "name": "EST_INBOX_ASSIGNED_TO_ALL"
                                            }
                                        ],
                                        "optionsKey": "name",
                                        "styles": {
                                            "gap": "1rem",
                                            "flexDirection": "column"
                                        },
                                        "innerStyles": {
                                            "display": "flex"
                                        }
                                    }
                                },
                                {
                                    "label": "COMMON_WARD",
                                    "type": "locationdropdown",
                                    "isMandatory": false,
                                    "disable": false,
                                    "populators": {
                                        "name": "ward",
                                        "type": "ward",
                                        "optionsKey": "i18nKey",
                                        "defaultText": "COMMON_SELECT_WARD",
                                        "selectedText": "COMMON_SELECTED",
                                        "allowMultiSelect": true
                                    }
                                },
                                {
                                    "label": "COMMON_LOCALITY",
                                    "type": "locationdropdown",
                                    "isMandatory": false,
                                    "disable": false,
                                    "populators": {
                                        "name": "locality",
                                        "type": "locality",
                                        "optionsKey": "i18nKey",
                                        "defaultText": "COMMON_SELECT_LOCALITY",
                                        "selectedText": "COMMON_SELECTED",
                                        "allowMultiSelect": true
                                    }
                                },
                                {
                                    "label": "COMMON_WORKFLOW_STATES",
                                    "type": "workflowstatesfilter",
                                    "isMandatory": false,
                                    "disable": false,
                                    "populators": {
                                        "name": "state",
                                        "labelPrefix": "WF_MUSTOR_",
                                        "businessService": "muster-roll-approval"
                                    }
                                }
                            ]
                        },
                    "label" : "ES_COMMON_FILTERS",
                    "show" : true
                },
                "searchResult": {
                    "label": "",
                    "uiConfig": {
                        "columns": [
                            {
                                "label": "ATM_MUSTER_ROLL_ID",
                                "jsonPath": "businessObject.musterRollNumber",
                                "additionalCustomization":true 
                            },
                            {
                                "label": "ES_COMMON_PROJECT_NAME",
                                "jsonPath": "businessObject.additionalDetails.attendanceRegisterName"
                            },
                            {
                                "label": "ES_COMMON_CBO_NAME",
                                "jsonPath": "businessObject.additionalDetails.cboName"
                            },
                            {
                                "label": "COMMON_ASSIGNEE",
                                "jsonPath": "ProcessInstance.assignes",
                                // "additionalCustomization": true,
                                "key": "assignee"
                            },
                            {
                                "label": "COMMON_WORKFLOW_STATES",
                                "jsonPath": "ProcessInstance.state.state",
                                // "additionalCustomization": true,
                                "key": "state"
                            },
                            {
                                "label": "ATM_AMOUNT_IN_RS",
                                "jsonPath": "businessObject.additionalDetails.totalEstimatedAmount",
                                // "additionalCustomization": true,
                                "key": "estimatedAmount"
                            },
                            {
                                "label": "ATM_SLA",
                                "jsonPath": "businessObject.serviceSla",
                                "additionalCustomization":true
                            }
                        ],
                        "enableGlobalSearch": false,
                        "enableColumnSort": true,
                        "resultsJsonPath": "items"
                    },
                    "children": {},
                    "show": true 
                }
            },
            "additionalSections" : {}
        }
}

export default inboxConfig;