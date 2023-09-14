const SearchMeasurementConfig = () => {
    return {
      label: "Search Measurement Book",
      type: "search",
      actionLabel: "WORKS_ADD_WAGESEEKER",
      actionRole: "INDIVIDUAL_CREATOR",
      actionLink: "masters/create-wageseeker",
      apiDetails: {
        serviceName: "/individual/v1/_search",
        requestParam: {},
        requestBody: {
          apiOperation: "SEARCH",
          Individual: {},
        },
        minParametersForSearchForm: 1,
        masterName: "commonUiConfig",
        moduleName: "SearchMeasurementConfig",
        tableFormJsonPath: "requestParam",
        filterFormJsonPath: "requestBody.Individual",
        searchFormJsonPath: "requestParam",

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
                "label": "WARD",
                "type": "locationdropdown",
                "isMandatory": false,
                "disable": false,
                "populators": {
                    "name": "wardCode",
                    "type": "ward",
                  "optionsKey": "i18nKey",
                    "defaultText": "COMMON_SELECT_WARD",
                    "selectedText": "COMMON_SELECTED",
                    "allowMultiSelect": false
                }
            },
              {
                label: "Project Name",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: { name: "Projectname", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, maxlength: 140 } },
              },
              {
                label: "MB Number",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: {
                  name: "MBNumber",
                  error: `PROJECT_PATTERN_ERR_MSG`,
                  validation: {  minlength: 2 },
                },
              },
              {
                label: "MB Refernce Number",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: {
                  name: "MBReference",
                  error: `PROJECT_PATTERN_ERR_MSG`,
                  validation: {  minlength: 2 },
                },
              },
           
              {
                label: "Status",
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
                label: "MB Reference Number",
                jsonPath: "individualId",
                additionalCustomization: true,
              },
              {
                label: "MB Number",
                jsonPath: "allof.measurementNumber",
              },
              {
                label: "Project Name",
                jsonPath: "fatherName",
              },
              {
                label: "Name of CBO",
                jsonPath: "additionalFields.fields[0].value",
                // additionalCustomization: true,
              },
              {
                label: "Status",
                jsonPath: "address[0].tenantId",
                additionalCustomization: true,
              },
              {
                label: "MB Amount",
                jsonPath: "address[0].ward.code",
                additionalCustomization: true,
              },
            
            ],
            enableGlobalSearch: false,
            enableColumnSort: true,
            resultsJsonPath: "Individual",
          },
          children: {},
          show: true,
        },
      },
      additionalSections: {},
    };
  };
  
  export default SearchMeasurementConfig;