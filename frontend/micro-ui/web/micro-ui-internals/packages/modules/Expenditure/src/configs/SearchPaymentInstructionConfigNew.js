export const SearchPIConfigNew = {
  "tenantId": "pg",
  "moduleName": "commonMuktaUiConfig",
  "SearchPaymentInstructionConfig": [
    {
      "label": "EXP_SEARCH_PAYMENT_INS",
      "type": "search",
      "apiDetails": {
        "serviceName": "/expense/payment/v1/_search",
        "requestParam": {},
        "requestBody": {
          "paymentCriteria": {
            
          }
        },
        "minParametersForSearchForm": 1,
        "masterName": "commonUiConfig",
        "moduleName": "SearchPaymentInstruction",
        "tableFormJsonPath": "requestBody",
        "filterFormJsonPath": "requestBody.paymentCriteria",
        "searchFormJsonPath": "requestBody.paymentCriteria"
      },
      "sections": {
        "search": {
          "uiConfig": {
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
                "label": "WORKS_PI_TYPE",
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
                "label": "EXP_PI_ID",
                "jsonPath": "businessObject.billNumber",
                // "additionalCustomization": true
              },
              {
                "label": "EXP_PI_DATE",
                "jsonPath": "businessObject.additionalDetails.projectName",
                // "additionalCustomization": true
              },
              {
                "label": "EXP_NO_BENE",
                "jsonPath": "businessObject.additionalDetails",
                // "additionalCustomization": true
              },
              {
                "label": "EXP_NO_SUCC_PAYMENTS",
                "jsonPath": "businessObject.additionalDetails.orgName"
              },
              {
                "label": "EXP_NO_FAIL_PAYMENTS",
                "jsonPath": "ProcessInstance.businessService",
                // "additionalCustomization": true
              },
              {
                "label": "CORE_COMMON_STATUS",
                "jsonPath": "businessObject.paymentStatus",
                // "additionalCustomization": true
              },
              {
                "label": "ES_COMMON_TOTAL_AMOUNT",
                "jsonPath": "businessObject.totalAmount",
                // "additionalCustomization": true,
                "headerAlign": "right"
              }
            ],
            "enableGlobalSearch": false,
            "enableColumnSort": true,
            "resultsJsonPath": "payments",
          },
          "children": {},
          "show": true
        }
      },
      "additionalSections": {}
    }
  ]
}

