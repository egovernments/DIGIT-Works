const InboxMeasurementConfig = () => {
  return {
    label: "MB_INBOX",
    type: "inbox",
    // actionLabel: "WORKS_ADD_WAGESEEKER",
    // actionRole: "INDIVIDUAL_CREATOR",
    // actionLink: "masters/create-wageseeker",
    apiDetails: {
      serviceName: "/measurementservice/v1/_search",
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
      searchFormJsonPath: "requestBody.Individual",
      searchFormJsonPath: "requestParam",

    },
    sections: {
      search: {
        uiConfig: {
          headerStyle: null,
          formClassName: "custom-both-clear-search",
          primaryLabel: "ES_COMMON_SEARCH",
          secondaryLabel: "ES_COMMON_CLEAR_SEARCH",
          minReqFields: 1,
          defaultValues: {
            ProjectId: "",
            MBReference: "",
            ProjectType: "",
          },
          fields: [
            {
              label: "MB_REFERENCE_NUMBER",
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
              label: "MB_PROJECT_ID",
              type: "text",
              isMandatory: false,
              disable: false,
              populators: { name: "ProjectId", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;""'']{1,50}$/i, maxlength: 140 } },
            },
            {
              label: "MB_PROJECT_TYPE",
              type: "dropdown",
              isMandatory: false,
              disable: false,
              populators: {
                name: "ProjectType",
                optionsKey: "name",
                optionsCustomStyle: {
                  top: "2.3rem",
                },
                mdmsConfig: {
                  masterName: "ProjectType",
                  moduleName: "works",
                  localePrefix: "MASTERS",
                },
              },
            },
          ],
        },
        label: "",
        children: {},
        show: true,
      
      },
      links: {
        uiConfig: {
          links: [
            {
              text: "MB_SEARCH_MB",
              url: "/employee/measurement/search",
              roles: ["MB_CREATOR", "MB_VERIFIER", "MB_APPROVER", "MB_VIEWER"],
            },
            {
              text: "MB_CREATE_MB",
              url: "/employee/measurement/create",
              roles: ["MB_CREATOR"],
            },
          ],
          label: "ES_COMMON_ATTENDENCEMGMT",
          logoIcon: {
            component: "MuktaIcon",
            customClass: "search-icon--projects",
          },
        },
        children: {},
        show: true,
      },
      filter: {
        uiConfig: {
          type: "filter",
          headerStyle: null,
          primaryLabel: "MB_APPLY",
          secondaryLabel: "",
          minReqFields: 1,
          defaultValues: {
            state: "",
            ward: [],
            locality: [],
            assignee: {
              code: "ASSIGNED_TO_ALL",
              name: "ASSIGNED_TO_ALL",
            },
          },
          fields: [
            {
              label: "",
              type: "radio",
              isMandatory: false,
              disable: false,
              populators: {
                name: "assignee",
                options: [
                  {
                    code: "ASSIGNED TO ME",
                    name: "MB_ASSIGNED_TO_ME",
                  },
                  {
                    code: "ASSIGNED_TO_ALL",
                    name: "MB_ASSIGNED_TO_ALL",
                  },
                ],
                optionsKey: "name",
                styles: {
                  gap: "1rem",
                  flexDirection: "column",
                },
                innerStyles: {
                  display: "flex",
                },
              },
            },
            {
              label: "MB_WARD",
              type: "locationdropdown",
              isMandatory: false,
              disable: false,
              populators: {
                  name: "ward",
                  type: "ward",
                  optionsKey: "i18nKey",
                  defaultText: "COMMON_SELECT_WARD",
                  selectedText: "COMMON_SELECTED",
                  allowMultiSelect: true
              }
            },
            {
              label: "MB_WORKFLOW_STATE",
              type: "text",
              isMandatory: false,
              disable: false,
              populators: {
                name: "state",
                labelPrefix: "WF_MUSTOR_",
                businessService: "muster-roll-approval",
              },
            },
          ],
        },
        label: "Filter",
        show: true,
      },
      searchResult: {
        label: "",
        uiConfig: {
          columns: [
            {
              label: "MB_REFERENCE_NUMBER",
              jsonPath: "allOf?.measures[0].referenceId",
              additionalCustomization: true,
            },
            {
              label: "MB_PROJECT_NAME",
              jsonPath: "fatherName",
            },
            {
              label: "MB_ASSIGNEE",
              jsonPath: "additionalFields.fields[0].value",
              // additionalCustomization: true,
            },
            {
              label: "MB_WORKFLOW_STATE",
              jsonPath: "address[0].tenantId",
              additionalCustomization: true,
            },
            {
              label: "MB_AMOUNT",
              jsonPath: "address[0].ward.code",
              additionalCustomization: true,
            },
            {
              label: "MB_SLA_DAYS_REMAINING",
              jsonPath: "address[0].ward.code",
              additionalCustomization: true,
            },
          
          ],
          enableGlobalSearch: false,
          enableColumnSort: true,
          resultsJsonPath: "measurements",
        },
        children: {},
        show: true,
      },
    },
    additionalSections: {},
  };
};
export default InboxMeasurementConfig;
