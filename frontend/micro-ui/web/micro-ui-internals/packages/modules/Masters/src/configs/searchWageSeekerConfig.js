const searchWageSeekerConfig = () => {
  return {
    label: "WORKS_SEARCH_WAGESEEKERS",
    type: "search",
    actionLabel: "WORKS_ADD_WAGESEEKER",
    actionRole: "INDIVIDUAL_CREATOR",
    apiDetails: {
      serviceName: "/individual/v1/_search",
      requestParam: {},
      requestBody: {
        apiOperation: "SEARCH",
        Individual: {},
      },
      minParametersForSearchForm: 1,
      masterName: "commonUiConfig",
      moduleName: "SearchWageSeekerConfig",
      tableFormJsonPath: "requestParam",
      filterFormJsonPath: "requestBody.Individual",
      searchFormJsonPath: "requestBody.Individual",
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
            id: "",
            name: "",
            category: "",
            mobileNumber: "",
            startDate: "",
            endDate: "",
          },
          fields: [
            {
              label: "MASTERS_WARD",
              type: "dropdown",
              isMandatory: false,
              disable: false,
              populators: {
                name: "boundaryCode",
                optionsCustomStyle: {
                  top: "2.3rem",
                },
                optionsKey: "name",
                options: [
                  {
                    code: "WARD1",
                    name: "WARD1",
                  },
                ],
              },
            },
            {
              label: "MASTERS_WAGESEEKER_NAME",
              type: "text",
              isMandatory: false,
              disable: false,
              populators: { name: "name", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, maxlength: 140 } },
            },
            {
              label: "MASTERS_WAGESEEKER_ID",
              type: "text",
              isMandatory: false,
              disable: false,
              populators: {
                name: "id",
                error: `PROJECT_PATTERN_ERR_MSG`,
                validation: { pattern: /^[a-z0-9\/-@# ]*$/i, minlength: 2 },
              },
            },
            {
              label: "MASTERS_PHONE_NUMBER",
              type: "mobileNumber",
              isMandatory: false,
              disable: false,
              populators: {
                name: "mobileNumber",
                error: `PROJECT_PATTERN_ERR_MSG`,
                validation: { pattern: /^[a-z0-9\/-@# ]*$/i, minlength: 2 },
              },
            },
            {
              label: "MASTERS_SOCIAL_CATEGORY",
              type: "dropdown",
              isMandatory: false,
              disable: false,
              populators: {
                name: "category",
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
              label: "MASTERS_WAGESEEKER_ID",
              jsonPath: "id",
              additionalCustomization: true,
            },
            {
              label: "WORKS_PROJECT_NAME",
              jsonPath: "name.givenName",
            },
            {
              label: "MASTERS_FATHER_NAME",
              jsonPath: "fatherName",
            },
            {
              label: "MASTERS_SOCIAL_CATEGORY",
              jsonPath: "projectType",
              additionalCustomization: true,

            },
            {
              label: "MASTERS_ULB",
              jsonPath: "address[0].tenantId",
              additionalCustomization: true,

            },
            {
              label: "MASTERS_WARD",
              jsonPath: "address[0].ward",
              additionalCustomization: true,

            },
            {
              label: "MASTERS_LOCALITY",
              jsonPath: "address[0].locality.code",
              additionalCustomization: true,
            }
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

export default searchWageSeekerConfig;
