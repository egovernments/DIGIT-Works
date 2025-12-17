const searchOrganisationConfig = () => {
  return {
    label: "WORKS_SEARCH_ORGANISATION",
    type: "search",
    actionLabel: "MASTERS_ADD_NEW_ORGANISATION",
    actionRole: "MUKTA_ADMIN",
    actionLink: "masters/create-organization",
    apiDetails: {
      serviceName: "/org-services/organisation/v1/_search",
      requestParam: {},
      requestBody: {
        apiOperation: "SEARCH",
        SearchCriteria: {},
      },
      minParametersForSearchForm: 1,
      masterName: "commonUiConfig",
      moduleName: "SearchOrganisationConfig",
      tableFormJsonPath: "requestBody.Pagination",
      filterFormJsonPath: "requestBody.SearchCriteria",
      searchFormJsonPath: "requestBody.SearchCriteria",
    },
    sections: {
      search: {
        uiConfig: {
          headerStyle: null,
          primaryLabel: "ES_COMMON_SEARCH",
          secondaryLabel: "ES_COMMON_CLEAR_SEARCH",
          minReqFields: 1,
          defaultValues: {
            boundaryCode: "",
            orgNumber: "",
            name: "",
            type: "",
            applicationStatus: "",
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
                name: "boundaryCode",
                type: "ward",
                optionsKey: "i18nKey",
                // optionsCustomStyle: {
                //   top: "2.3rem",
                // },
                defaultText: "COMMON_SELECT_WARD",
                selectedText: "COMMON_SELECTED",
                allowMultiSelect: false,
              },
            },
            {
              label: "MASTERS_ORGANISATION_TYPE",
              type: "dropdown",
              isMandatory: false,
              disable: false,
              populators: {
                name: "type",
                optionsKey: "name",
                // optionsCustomStyle: {
                //   top: "2.3rem",
                // },
                mdmsConfig: {
                  masterName: "OrgType",
                  moduleName: "common-masters",
                  filter: "[?(@.active==true)].parent",
                  localePrefix: "COMMON_MASTERS_ORG",
                  select:
                    "(data)=>{ return Array.isArray(data['common-masters'].OrgType) && Digit.Utils.getUnique(data['common-masters'].OrgType).map(ele=>({code:ele,name:'COMMON_MASTERS_ORG_'+ele}))}",
                },
              },
            },

            {
              label: "MASTERS_NAME_OF_ORGN",
              type: "text",
              isMandatory: false,
              disable: false,
              populators: { name: "name", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, maxlength: 140 } },
            },
            {
              label: "MASTERS_ORGANISATION_ID",
              type: "text",
              isMandatory: false,
              disable: false,
              populators: {
                name: "orgNumber",
                error: `PROJECT_PATTERN_ERR_MSG`,
                validation: { minlength: 2 },
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
              label: "MASTERS_ORGANISATION_ID",
              jsonPath: "orgNumber",
              additionalCustomization: true,
            },
            {
              label: "MASTERS_NAME_OF_ORGN",
              jsonPath: "name",
            },
            {
              label: "MASTERS_ORGANISATION_TYPE",
              jsonPath: "functions[0].type",
              additionalCustomization: true,
            },
            {
              label: "MASTERS_ORGANISATION_SUB_TYPE",
              jsonPath: "functions[0].category",
              additionalCustomization: true,
            },
            {
              label: "MASTERS_ADDRESS",
              jsonPath: "orgAddress[0].boundaryCode",
              additionalCustomization: true,
            },
            {
              label: "CORE_COMMON_STATUS",
              jsonPath: "applicationStatus",
              additionalCustomization: true,
            },
          ],
          enableGlobalSearch: false,
          enableColumnSort: true,
          resultsJsonPath: "organisations",
        },
        children: {},
        show: true,
      },
    },
    additionalSections: {},
  };
};

export default searchOrganisationConfig;
