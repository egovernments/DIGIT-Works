
const searchContractConfig = () => {
return  {
  "label": "WORKS_SEARCH_CONTRACT",
  "type": "search",
  "apiDetails": {
    "serviceName": "/wms/contract/_search",
    "requestParam": {},
    "requestBody": {
      "inbox": {
         "moduleSearchCriteria": {}
      }
    },
    "minParametersForSearchForm": 1,
    "masterName": "commonUiConfig",
    "moduleName": "SearchContractConfig",
    "tableFormJsonPath": "requestBody.inbox",
    "filterFormJsonPath": "requestBody.inbox.moduleSearchCriteria",
    "searchFormJsonPath": "requestBody.inbox.moduleSearchCriteria"
  },
  "sections": {
    "search": {
      "uiConfig": {
        "headerStyle": null,
        "formClassName": "custom-both-clear-search",
        "primaryLabel": "ES_COMMON_SEARCH",
        "secondaryLabel": "ES_COMMON_CLEAR_SEARCH",
        "minReqFields": 1,
        "showFormInstruction": "CONTRACT_SEARCH_HINT",
        "defaultValues": {
          "ward": "",
          "projectType": "",
          "projectName": "",
          "workOrderNumber": "",
          "revisedWorkOrderNumber": "",
          "status":"",
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
                "optionsKey": "name",
                "defaultText": "COMMON_SELECT_WARD",
                "selectedText": "COMMON_SELECTED",
                "allowMultiSelect": false
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
              "label": "WORKS_SEARCH_ORDER_ID",
              "type": "text",
              "isMandatory": false,
              "disable": false,
              "preProcess": {
                "convertStringToRegEx": [
                  "populators.validation.pattern"
                ]
              },
              "populators": {
                "name": "workOrderNumber",
                "error": "CONTRACT_PATTERN_ERR_MSG",
                "validation": {
                    "pattern": "WO|TE\/[0-9]+-[0-9]+\/[0-9]+",
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
                "moduleName": "SearchContractConfig",
                "customfn": "populateReqCriteria"
              }
            },
          {
            "label": "CREATED_FROM_DATE",
            "type": "date",
            "isMandatory": false,
            "disable": false,
            "populators": {
              "name": "createdFrom"
            }
          },
          {
            "label": "CREATED_TO_DATE",
            "type": "date",
            "isMandatory": false,
            "disable": false,
            "populators": {
              "name": "createdTo",
              "error": "DATE_VALIDATION_MSG"
            },
            "additionalValidation": {
              "type": "date",
              "keys": { "start": "createdFrom", "end": "createdTo" }
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
            "label": "WORKS_SEARCH_ORDER_ID",
            "jsonPath": "businessObject.contractNumber",
            "additionalCustomization": true
          },
          {
            "label": "WORKS_PROJECT_NAME",
            "jsonPath": "businessObject.additionalDetails.projectName"
          },
          {
            "label": "COMMON_NAME_OF_CBO",
            "jsonPath": "businessObject.additionalDetails.orgName"
          },
          {
            "label": "COMMON_ROLE_OF_CBO",
            "jsonPath": "businessObject.executingAuthority",
            "additionalCustomization": true
          },
          {
            "label": "ES_COMMON_LOCATION",
            "jsonPath": "businessObject.additionalDetails.ward",
            "additionalCustomization": true
          },
          {
            "label": "ES_COMMON_STATUS",
            "jsonPath": "businessObject.contractStatus"
          },
          {
            "label": "ES_COMMON_AMOUNT",
            "jsonPath": "businessObject.totalContractedAmount",
            "additionalCustomization": true,
            "headerAlign": "right"
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
  "additionalSections": {}
}
};

export default searchContractConfig;

