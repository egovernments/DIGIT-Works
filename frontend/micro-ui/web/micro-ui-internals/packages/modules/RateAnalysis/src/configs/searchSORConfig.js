const searchSORConfig = {
  tenantId: "od",
  moduleName: "searchSORConfig",
  SearchSORConfig: [
    {
      label: "RA_Search_SOR",
      type: "search",
      actionLabel: "RA_Show_Jobs",
      actionRole: "",
      actionLink: "rateanalysis/view-scheduled-jobs",
      apiDetails: {
        serviceName: "/mdms-v2/v2/_search",
        requestParam: {},
        requestBody: {
          MdmsCriteria: {},
        },
        minParametersForSearchForm: 1,
        masterName: "commonUiConfig",
        moduleName: "searchSORConfig",
        tableFormJsonPath: "requestBody.Pagination",
        filterFormJsonPath: "requestBody.MdmsCriteria",
        searchFormJsonPath: "requestBody.MdmsCriteria",
      },
      sections: {
        search: {
          uiConfig: {
            headerStyle: null,
            primaryLabel: "ES_COMMON_SEARCH",
            secondaryLabel: "ES_COMMON_CLEAR_SEARCH",
            minReqFields: 1,
            showFormInstruction: "",
            defaultValues: {
              sorType: { 
                active: true,
                code: "W",
                description: "Works",
                i18nKey: "WORKS_SOR_TYPE_W",
              },
              sorSubType: "",
              sorVariant: "",
              sorCode: "",
            },
            fields: [
              {
                isMandatory: true,
                key: "sorType",
                type: "dropdown",
                label: "WORKS_SOR_TYPE",
                disable: false,
                populators: {
                  name: "sorType",
                  optionsKey: "description",
                  required: true,
                  // "optionsCustomStyle": {
                  //   "top": "2.3rem"
                  // },
                  options: [
                    {
                      active: true,
                      code: "W",
                      description: "Works",
                      i18nKey: "WORKS_SOR_TYPE_W",
                    },
                  ],
                },
              },
              {
                isMandatory: false,
                key: "sorSubType",
                type: "dropdown",
                label: "WORKS_SOR_SUBTYPE",
                disable: false,
                populators: {
                  name: "sorSubType",
                  optionsKey: "description",
                  required: true,
                  // "optionsCustomStyle": {
                  //   "top": "2.3rem"
                  // },
                  mdmsConfig: {
                    masterName: "SubType",
                    moduleName: "WORKS-SOR",
                  },
                  // mdmsv2: {
                  //   schemaCode: "WORKS-SOR.SubType",
                  // },
                },
              },
              {
                isMandatory: false,
                key: "sorVariant",
                type: "dropdown",
                label: "WORKS_SOR_VARIANT",
                disable: false,
                populators: {
                  name: "sorVariant",
                  optionsKey: "description",
                  required: true,
                  // "optionsCustomStyle": {
                  //   "top": "2.3rem"
                  // },
                  mdmsConfig: {
                    masterName: "Variant",
                    moduleName: "WORKS-SOR",
                  },
                  // mdmsv2: {
                  //   schemaCode: "WORKS-SOR.Variant",
                  // },
                },
              },
              {
                label: "RA_SOR_CODE",
                isMandatory: false,
                type: "text",
                disable: false,
                populators: {
                  name: "sorCode",
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
                label: "RA_SOR_CODE",
                jsonPath: "data.id",
                additionalCustomization: true,
              },
              {
                label: "RA_SOR_SUBTYPE",
                jsonPath: "data.sorSubType",
                additionalCustomization: true,
              },
              {
                label: "RA_SOR_VARIANT",
                jsonPath: "data.sorVariant",
                additionalCustomization: true,
              },
              {
                label: "RA_SOR_DESCRIPTION",
                jsonPath: "data.description",
                additionalCustomization: true,
              },
            ],
            enableGlobalSearch: false,
            enableColumnSort: true,
            resultsJsonPath: "mdms",
          },
          children: {},
          show: true,
        },
      },
      additionalSections: {},
    },
  ],
};

export default searchSORConfig;
