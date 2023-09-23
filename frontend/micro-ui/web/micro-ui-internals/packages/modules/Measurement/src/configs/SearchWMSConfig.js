const SearchMeasurementConfig = () => {
    return {
      label: "MB_SEARCH_MEASUREMENT",
      type: "search",
      apiDetails: {
        serviceName: "/wms/measurement-service/_search",
        requestParam: {},
        requestBody: {
        //   apiOperation: "SEARCH",
          inbox: {
            "tenantId": Digit.ULBService.getCurrentTenantId(),
            "limit": 10,
             "offset": 0,
            "moduleSearchCriteria":{
            "tenantId": Digit.ULBService.getCurrentTenantId()
          }},
        },
        minParametersForSearchForm: 1,
        masterName: "commonUiConfig",
        moduleName: "WMSSearchConfig",
        tableFormJsonPath: "requestParam",
        filterFormJsonPath: "requestBody.inbox.moduleSearchCriteria",
        searchFormJsonPath: "requestBody.inbox.moduleSearchCriteria",

      },
      sections: {
        search: {
          uiConfig: {
            headerStyle: null,
            formClassName:"custom-both-clear-search",
            primaryLabel: "ES_COMMON_SEARCH",
            secondaryLabel: "ES_COMMON_CLEAR_SEARCH",
            minReqFields: 1,
            defaultValues: {
              wardCode: "",
              MBNumber: "",
              Projectname: "",
              status: "",
              MBReference: "",
              createdFrom: "",
              createdTo: "",

            },
            fields: [
              {

                label: "MB_WARD",

                type: "locationdropdown",
                isMandatory: false,
                disable: false,
                populators: {
                    name: "wardCode",
                    type: "ward",
                  optionsKey: "i18nKey",
                    defaultText: "COMMON_SELECT_WARD",
                    selectedText: "COMMON_SELECTED",
                    allowMultiSelect: false
                }
            },
              {
                label: "MB_PROJECT_NAME",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: { name: "Projectname", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, maxlength: 140 } },
              },
              {
                label: "MB_NUMBER",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: {
                  name: "measurementNumber",
                  error: `PROJECT_PATTERN_ERR_MSG`,
                  validation: {  minlength: 2 },
                },
              },
              {
                label: "MB_REFERENCE_NUMBER",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: {
                  name: "referenceId",
                  error: `PROJECT_PATTERN_ERR_MSG`,
                  validation: {  minlength: 2 },
                },
              },
           
              {
                label: "MB_STATUS",
                type: "dropdown",
                isMandatory: false,
                disable: false,
                populators: {
                  name: "status",
                  optionsKey: "code",
                  optionsCustomStyle: {
                    top: "2.3rem",
                  },
                  mdmsConfig: {
                    masterName: "SocialCategory",
                    moduleName: "common-masters",
                    localePrefix: "MASTERS",
                  },
                },
              },
              {
                label: "CREATED_FROM_DATE",
                type: "date",
                isMandatory: false,
                disable: false,
                key : "createdFrom",
                preProcess : {
                  updateDependent : ["populators.max"]
                },
                populators: {
                  name: "createdFrom",
                  max : "currentDate"
                },
              },
              {
                label: "CREATED_TO_DATE",
                type: "date",
                isMandatory: false,
                disable: false,
                key : "createdTo",
                preProcess : {
                  updateDependent : ["populators.max"]
                },
                populators: {
                  name: "createdTo",
                  error: "DATE_VALIDATION_MSG",
                  max : "currentDate"
                },
                additionalValidation: {
                  type: "date",
                  keys: { start: "createdFrom", end: "createdTo" },
                },
              },
            ],
          },
          label: "",
          children: {},
          show: true,
        },
        searchResult: {
          label: "",
          uiConfig: {
            columns: [
              {
                label: "MB_REFERENCE_NUMBER-2",
                jsonPath: "businessObject.referenceId",
                additionalCustomization: true,
              },
              {
                label: "MB_NUMBER",
                jsonPath: "ProcessInstance.businessId",
              },
              {
                label: "MB_PROJECT_NAME",
                jsonPath: "",
              },
              {
                label: "MB_NAME_CBO",
                jsonPath: "",
                // additionalCustomization: true,
              },
              {
                label: "MB_STATUS",
                jsonPath: "",
                additionalCustomization: true,
              },
              {
                label: "MB_AMOUNT",
                jsonPath: "totalValue",
                additionalCustomization: true,
              },
            
            ],
            enableGlobalSearch: false,
            enableColumnSort: true,
            resultsJsonPath: "items",
          },
          children: {},
          show: true,
        },
      },
      additionalSections: {},
    };
  };
  
  export default SearchMeasurementConfig;