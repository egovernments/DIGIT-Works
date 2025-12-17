const searchConfigMuktaFuzzy = () => {
  return {
    "label": "WORKS_SEARCH_ESTIMATES",
    "type": "search",
    "apiDetails": {
      "serviceName": "/wms/estimate/_search",
      "requestParam": {},
      "requestBody": {
        "inbox": {
          "moduleSearchCriteria": {}
        }
      },
      "minParametersForSearchForm": 1,
      "masterName": "commonUiConfig",
      "moduleName": "SearchEstimateConfig",
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
          "showFormInstruction": "ESTIMATE_SEARCH_HINT",
          "defaultValues": {
            "ward": "",
            "typeOfWork": "",
            "projectName": "",
            "estimateId": "",
            "status": "",
            "fromProposalDate": "",
            "toProposalDate": ""
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
                "name": "typeOfWork",
                "optionsKey": "name",
                // "optionsCustomStyle": {
                //   "top": "2.3rem"
                // },
                "mdmsConfig": {
                  "masterName": "ProjectType",
                  "moduleName": "works",
                  "localePrefix": "COMMON_MASTERS"
                }
              }
            },
            {
              "label": "ES_COMMON_PROJECT_NAME",
              "type": "text",
              "isMandatory": false,
              "disable": false,
              "preProcess": {
                "convertStringToRegEx": ["populators.validation.pattern"]
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
              "label": "ESTIMATE_ESTIMATE_NO",
              "type": "text",
              "isMandatory": false,
              "disable": false,
              "populators": {
                "name": "estimateId",
                "error": "ESTIMATE_PATTERN_ERR_MSG",
                "validation": {
                  "pattern": "ES\/[0-9]+-[0-9]+\/[0-9]+",
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
                // "optionsCustomStyle": {
                //   "top": "2.3rem"
                // },
                "name": "status",
                "optionsKey": "i18nKey",
                "allowMultiSelect": false,
                "masterName": "commonUiConfig",
                "moduleName": "SearchEstimateConfig",
                "customfn": "populateReqCriteria"
              }
            },
            {
              "label": "WORKS_COMMON_FROM_DATE_LABEL",
              "type": "date",
              "isMandatory": false,
              "disable": false,
              "key" : "fromProposalDate",
              "preProcess" : {
                "updateDependent" : ["populators.max"]
               },
               "populators": {
                  "name": "fromProposalDate",
                  "max" : "currentDate"
              }
            },
            {
              "label": "WORKS_COMMON_TO_DATE_LABEL",
              "type": "date",
              "isMandatory": false,
              "disable": false,
              "key" : "toProposalDate",
              "preProcess" : {
                "updateDependent" : ["populators.max"]
               },
              "populators": {
                "name": "toProposalDate",
                "error": "DATE_VALIDATION_MSG",
                "max" : "currentDate"
                },
                "additionalValidation": {
                  "type": "date",
                  "keys": {
                    "start": "fromProposalDate",
                    "end": "toProposalDate"
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
        "estimateNumber": "",
        "projectId": "",
        "department": "",
        "estimateStatus": "",
        "fromProposalDate": "",
        "toProposalDate": "",
        "uiConfig": {
          "columns": [
            {
              "label": "ESTIMATE_ESTIMATE_NO",
              "jsonPath": "businessObject.estimateNumber",
              "additionalCustomization": true
            },
            {
              "label": "ES_COMMON_PROJECT_NAME",
              "jsonPath": "businessObject.additionalDetails.projectName",
              "additionalCustomization":true
            },
            {
              "label": "ES_COMMON_LOCATION",
              "jsonPath": "businessObject.additionalDetails.location",
              "additionalCustomization": true
            },
            {
              "label": "ESTIMATE_PREPARED_BY",
              "jsonPath": "businessObject.additionalDetails.creator"
            },
            {
              "label": "CORE_COMMON_STATUS",
              "jsonPath": "ProcessInstance.state.state",
              "additionalCustomization": true
            },
            {
              "label": "WORKS_ESTIMATED_AMOUNT",
              "jsonPath": "businessObject.additionalDetails.totalEstimatedAmount",
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

export default searchConfigMuktaFuzzy;
