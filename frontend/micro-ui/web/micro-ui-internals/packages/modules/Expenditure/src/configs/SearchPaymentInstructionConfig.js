export const SearchPaymentInstructionConfig = {
  "tenantId": "pg",
  "moduleName": "commonMuktaUiConfig",
  "SearchBillWMSConfig": [
    {
      "label": "EXP_SEARCH_PAYMENT_INS",
      "type": "search",
      "actionLabel": "ES_COMMON_DOWNLOAD_PAYMENT_ADVICE",
      "actionRole": "BILL_ACCOUNTANT",
      "actionLink": "expenditure/download-bill",
      "apiDetails": {
        "serviceName": "/wms/expense/_search",
        "requestParam": {},
        "requestBody": {
          "inbox": {
            "moduleSearchCriteria": {}
          }
        },
        "minParametersForSearchForm": 1,
        "masterName": "commonUiConfig",
        // "moduleName": "SearchPaymentInstruction",
        "moduleName": "SearchBillWMSConfig",
        "tableFormJsonPath": "requestBody.inbox",
        "filterFormJsonPath": "requestBody.inbox.moduleSearchCriteria",
        "searchFormJsonPath": "requestBody.inbox.moduleSearchCriteria"
      },
      "sections": {
        "search": {
          "uiConfig": {
            "additionalTabs":[
              {
                "uiConfig": {
                  "navLink":"Open Search",
                  "headerStyle": null,
                  "primaryLabel": "ES_COMMON_SEARCH",
                  "secondaryLabel": "ES_COMMON_CLEAR_SEARCH",
                  "minReqFields": 1,
                  "showFormInstruction": "PAYMENT_INS_SELECT_ONE_PARAM_TO_SEARCH",
                  "defaultValues": {
                    "ward": "",
                    "billType": "",
                    "projectName": "",
                    "billNumber": "",
                    "status": "",
                    "createdFrom": "",
                    "createdTo": ""
                  },
                  "fields": [
                    {
                      "label": "COMMON_WARD",
                      "type": "locationdropdown",
                      "isMandatory": false,
                      "disable": false,
                      "populators": {
                        "name": "ward",
                        "type": "ward",
                        "optionsKey": "i18nKey",
                        "allowMultiSelect": false,
                        "optionsCustomStyle": {
                          "top": "2.3rem"
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
                      "label": "WORKS_BILL_NUMBER",
                      "type": "text",
                      "isMandatory": false,
                      "disable": false,
                      "populators": {
                        "name": "billNumber",
                        "error": "ES_COMMON_BILL_PATTERN_ERR_MSG",
                        "validation": {
                          "pattern": "^[A-Za-z0-9\\/-]*$",
                          "minlength": 2
                        }
                      }
                    },
                    {
                      "label": "CORE_COMMON_STATUS",
                      "type": "apidropdown",
                      "isMandatory": false,
                      "disable": false,
                      "populators": {
                        "optionsCustomStyle": {
                          "top": "2.3rem"
                        },
                        "name": "status",
                        "optionsKey": "i18nKey",
                        "allowMultiSelect": false,
                        "masterName": "commonUiConfig",
                        "moduleName": "SearchBillWMSConfig",
                        "customfn": "populateReqCriteria"
                      }
                    },
                    {
                      "label": "CREATED_FROM_DATE",
                      "type": "date",
                      "isMandatory": false,
                      "disable": false,
                      "key": "createdFrom",
                      "preProcess": {
                        "updateDependent": [
                          "populators.max"
                        ]
                      },
                      "populators": {
                        "name": "createdFrom",
                        "max": "currentDate"
                      }
                    },
                    {
                      "label": "CREATED_TO_DATE",
                      "type": "date",
                      "isMandatory": false,
                      "disable": false,
                      "key": "createdTo",
                      "preProcess": {
                        "updateDependent": [
                          "populators.max"
                        ]
                      },
                      "populators": {
                        "name": "createdTo",
                        "error": "DATE_VALIDATION_MSG",
                        "max": "currentDate"
                      },
                      "additionalValidation": {
                        "type": "date",
                        "keys": {
                          "start": "createdFrom",
                          "end": "createdTo"
                        }
                      }
                    }
                  ]
                }
              }
            ],
            "configNavItems":[{
                  "name": "Pending for action",
                  "code": "PENDING_FOR_ACTION",
                  "activeByDefault": true,
              },
              {
                  "name": "Open Search",
                  "code": "OPEN_SEARCH",
              }
            ],
            "navLink":"Pending for action",
            "headerStyle": null,
            "primaryLabel": "ES_COMMON_SEARCH",
            "secondaryLabel": "ES_COMMON_CLEAR_SEARCH",
            "minReqFields": 1,
            "showFormInstruction": "PAYMENT_INS_SELECT_ONE_PARAM_TO_SEARCH",
            "defaultValues": {
              "ward": "",
              "projectType": "",
              "projectName": "",
              "billNumber": "",
            },
            "fields": [
              {
                "label": "COMMON_WARD",
                "type": "locationdropdown",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "name": "ward",
                  "type": "ward",
                  "optionsKey": "i18nKey",
                  "allowMultiSelect": false,
                  "optionsCustomStyle": {
                    "top": "2.3rem"
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
              },,
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
                "label": "WORKS_BILL_NUMBER",
                "type": "text",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "name": "billNumber",
                  "error": "ES_COMMON_BILL_PATTERN_ERR_MSG",
                  "validation": {
                    "pattern": "^[A-Za-z0-9\\/-]*$",
                    "minlength": 2
                  }
                }
              }
            ]
          },
          "label": "",
          "children": {},
          "show": true
        },
        "searchResult": {
          "label": "",
          "uiConfig": {
            "columns": [
              {
                "label": "WORKS_BILL_NUMBER",
                "jsonPath": "businessObject.billNumber",
                "additionalCustomization": true
              },
              {
                "label": "WORKS_PROJECT_NAME",
                "jsonPath": "businessObject.additionalDetails.projectName",
                "additionalCustomization":true
              },
              {
                "label": "ES_COMMON_LOCATION",
                "jsonPath": "businessObject.additionalDetails",
                "additionalCustomization": true
              },
              {
                "label": "ES_COMMON_CBO_NAME",
                "jsonPath": "businessObject.additionalDetails.orgName"
              },
              {
                "label": "WORKS_BILL_TYPE",
                "jsonPath": "ProcessInstance.businessService",
                "additionalCustomization": true
              },
              {
                "label": "CORE_COMMON_STATUS",
                "jsonPath": "businessObject.paymentStatus",
                "additionalCustomization": true
              },
              {
                "label": "EXP_BILL_AMOUNT",
                "jsonPath": "businessObject.totalAmount",
                "additionalCustomization": true,
                "headerAlign": "right"
              }
            ],
            "enableGlobalSearch": false,
            "enableColumnSort": true,
            "resultsJsonPath": "items",
            "showCheckBox": true,
            "checkBoxActionLabel": "ES_COMMON_GENERATE_PAYMENT_ADVICE",
            "showTableInstruction": "EXP_DOWNLOAD_BILL_INSTRUCTION"
          },
          "children": {},
          "show": true
        }
      },
      "additionalSections": {}
    }
  ]
}