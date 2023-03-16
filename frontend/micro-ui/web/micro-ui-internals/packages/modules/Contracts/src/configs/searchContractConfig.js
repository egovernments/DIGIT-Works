const contractStatus = [
  {
      name: 'ACTIVE',
      code: 'ACTIVE',
      i18nKey: 'ACTIVE'
  },
  {
      name: 'INACTIVE',
      code: 'INACTIVE',
      i18nKey: 'INACTIVE'
  }
]


const searchContractConfig = () => {
return {
  label: "WORKS_SEARCH_CONTRACT",
  type: "search",
  apiDetails: {
    serviceName: "/wms/contract/_search",
    requestParam: {},
    requestBody: {
    },
    minParametersForSearchForm: 1,
    masterName: "commonUiConfig",
    moduleName: "SearchContractConfig",
    tableFormJsonPath: "requestParam",
    // filterFormJsonPath: "requestBody.contracts",
    // searchFormJsonPath: "requestBody.contracts",
    filterFormJsonPath: "requestParam",
    searchFormJsonPath: "requestParam",
  },
  sections: {
    search: {
      uiConfig: {
        headerStyle: null,
        primaryLabel: "ES_COMMON_SEARCH",
        secondaryLabel: "ES_COMMON_CLEAR_SEARCH",
        minReqFields: 1,
        showFormInstruction: "CONTRACT_SEARCH_HINT",
        defaultValues: {
          ward: "",
          projectType: "",
          projectName: "",
          contractNumber: "",
          contractStatus:"",
          createdFrom: "",
          createdTo: "",
        },
        fields: [
          {
            label: "COMMON_WARD",
            type: "locationdropdown",
            isMandatory: false,
            disable: false,
            populators: {
                name: "ward",
                type: "ward",
                optionsKey: "name",
                defaultText: "COMMON_SELECT_WARD",
                selectedText: "COMMON_SELECTED",
                allowMultiSelect: false
            }
        },
          {
            label: "WORKS_PROJECT_TYPE",
            type: "dropdown",
            isMandatory: false,
            disable: false,
            populators: { 
              name: "projectType", 
              optionsKey: "name",
              optionsCustomStyle: {
                  top: "2.3rem"
                },
                mdmsConfig: {
                  masterName: "ProjectType",
                  moduleName: "works",
                  localePrefix: "COMMON_MASTERS"
                }
            },
          },
          {
              label: "WORKS_PROJECT_NAME",
              type: "text",
              isMandatory: false,
              disable: false,
              preProcess: {
                convertStringToRegEx: [
                  "populators.validation.pattern"
                ]
              },
              populators: {
                name: "projectName",
                error: "PROJECT_PATTERN_ERR_MSG",
                validation: {
                    "pattern": "^[^\\$\"<>?\\\\~`!@$%^()+={}\\[\\]*:;“”‘’]{1,50}$",
                    "minlength": 2
                }
              }
          },
          {
              label: "WORKS_ORDER_ID",
              type: "text",
              isMandatory: false,
              disable: false,
              preProcess: {
                convertStringToRegEx: [
                  "populators.validation.pattern"
                ]
              },
              populators: {
                name: "contractNumber",
                error: "CONTRACT_PATTERN_ERR_MSG",
                validation: {
                    pattern: "WO\\/[0-9]+-[0-9]+\\/[0-9]+\\/[0-9]+",
                    minlength: 2
                }
              }
            },
          {
              label: "ES_COMMON_STATUS",
              type: "dropdown",
              isMandatory: false,
              disable: false,
              populators: {
                  name: "contractStatus",
                  optionsKey: "i18nKey",
                  options: contractStatus,
                  optionsCustomStyle : {
                      top : "2.3rem"
                  }
              }
          },
          {
            label: "CREATED_FROM_DATE",
            type: "date",
            isMandatory: false,
            disable: false,
            populators: {
              name: "createdFrom",
            },
          },
          {
            label: "CREATED_TO_DATE",
            type: "date",
            isMandatory: false,
            disable: false,
            populators: {
              name: "createdTo",
              error: "DATE_VALIDATION_MSG",
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
            label: "WORKS_ORDER_ID",
            jsonPath: "contractNumber",
            additionalCustomization: true,
          },
          {
            label: "WORKS_PROJECT_NAME",
            jsonPath: "additionalDetails.projectName",
          },
          {
            label: "NAME_OF_CBO",
            jsonPath: "additionalDetails?.orgName",
          },
          {
            label: "ROLE_OF_CBO",
            jsonPath: "executingAuthority",
            //additionalCustomization: true,
          },
          {
            label: "WORKS_LOCATION",
            jsonPath: "",
          },
          {
            label: "ES_COMMON_STATUS",
            jsonPath: "status",
          },
          {
            label: "WORKS_ORDER_AMOUNT",
            jsonPath: "totalContractedAmount",
            additionalCustomization: true,
          },
        ],
        enableGlobalSearch: false,
        enableColumnSort: true,
        resultsJsonPath: "contracts",
      },
      children: {},
      show: true,
    },
  },
  additionalSections: {},
};
};

export default searchContractConfig;

