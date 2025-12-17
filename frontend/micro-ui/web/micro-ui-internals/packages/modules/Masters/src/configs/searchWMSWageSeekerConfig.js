const searchWMSWageSeekerConfig = () => {
    return {
      "label": "WORKS_SEARCH_WAGESEEKERS",
      "type": "search",
      "actionLabel": "WORKS_ADD_WAGESEEKER",
      "actionRole": "INDIVIDUAL_CREATOR",
      "actionLink": "masters/create-wageseeker",
      "apiDetails": {
        "serviceName": "/wms/individual/_search",
        "requestParam": {},
        "requestBody": {
          "inbox": {
            "moduleSearchCriteria": {}
          }
        },
        "minParametersForSearchForm": 1,
        "masterName": "commonUiConfig",
        "moduleName": "SearchWMSWageSeekerConfig",
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
            "type":"customIndSearchButton",
            "minReqFields": 1,
            "defaultValues": {
              "ward": "",
              "individualId": "",
              "name": "",
              "mobileNumber": "",
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
                  "defaultText": "COMMON_SELECT_WARD",
                  "selectedText": "COMMON_SELECTED",
                  "allowMultiSelect": false
                }
              },
              {
                "label": "MASTERS_WAGESEEKER_NAME",
                "type": "text",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "name": "name",
                  "validation": { "pattern": {}, "maxlength": 140 }
                }
              },
              {
                "label": "MASTERS_WAGESEEKER_ID",
                "type": "text",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "name": "individualId",
                  "error": "PROJECT_PATTERN_ERR_MSG",
                  "validation": { "minlength": 2 }
                }
              },
              // {
              //   "label": "MASTERS_SOCIAL_CATEGORY",
              //   "type": "dropdown",
              //   "isMandatory": false,
              //   "disable": false,
              //   "populators": {
              //     "name": "socialCategoryValue",
              //     "optionsKey": "code",
              //     "mdmsConfig": {
              //       "masterName": "SocialCategory",
              //       "moduleName": "common-masters",
              //       "localePrefix": "MASTERS"
              //     }
              //   }
              // },
              {
                "label": "CREATED_FROM_DATE",
                "type": "date",
                "isMandatory": false,
                "disable": false,
                "key" : "createdFrom",
                "preProcess" : {
                  "updateDependent" : ["populators.max"]
                },
                "populators": { "name": "createdFrom", "max" : "currentDate"  }
              },
              {
                "label": "CREATED_TO_DATE",
                "type": "date",
                "isMandatory": false,
                "disable": false,
                "key" : "createdTo",
                "preProcess" : {
                  "updateDependent" : ["populators.max"]
                },
                "populators": {
                  "name": "createdTo",
                  "error": "DATE_VALIDATION_MSG",
                  "max" : "currentDate"
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
                "label": "MASTERS_WAGESEEKER_ID",
                "jsonPath": "businessObject.individualId",
                "additionalCustomization": true
              },
              {
                "label": "MASTERS_WAGESEEKER_NAME",
                "jsonPath": "businessObject.name.givenName"
              },
              {
                "label": "CORE_COMMON_PROFILE_CITY",
                "jsonPath": "businessObject.address[0].tenantId",
                "additionalCustomization": true
              },
              {
                "label": "MASTERS_WARD",
                "jsonPath": "businessObject.address[0].ward.code",
                "additionalCustomization": true
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
    };
  };
  
  export default searchWMSWageSeekerConfig;
  