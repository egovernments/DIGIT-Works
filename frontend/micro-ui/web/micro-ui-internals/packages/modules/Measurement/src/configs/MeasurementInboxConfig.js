const InboxMeasurementConfig = () => {
    return {
      label: "Inbox",
      type: "inbox",
      actionLabel: "WORKS_ADD_WAGESEEKER",
      actionRole: "INDIVIDUAL_CREATOR",
      actionLink: "masters/create-wageseeker",
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
              ProjectId: "",
              MBReference: "",
              ProjectType: "",
            },
            fields: [
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
                label: "Project ID",
                type: "text",
                isMandatory: false,
                disable: false,
                populators: { name: "ProjectId", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, maxlength: 140 } },
              },
              {
                label: "Project Type",
                type: "dropdown",
                isMandatory: false,
                disable: false,
                populators: {
                  name: "ProjectType",
                  optionsKey: "code",
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
                text: "Search MB",
                url: "/employee/measurement/search",
                roles: ["MUSTER_ROLL_VERIFIER", "MUSTER_ROLL_APPROVER"],
              },
              {
                text: "Create MB",
                url: "/employee/measurement/craete",
                roles: ["MUSTER_ROLL_VERIFIER", "MUSTER_ROLL_APPROVER"],
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
            primaryLabel: "Apply",
            secondaryLabel: "",
            minReqFields: 1,
            defaultValues: {
              state: "",
              ward: [],
              locality: [],
              assignee: {
                code: "ASSIGNED_TO_ALL",
                name: "EST_INBOX_ASSIGNED_TO_ALL",
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
                      code: "ASSIGNED_TO_ME",
                      name: "EST_INBOX_ASSIGNED_TO_ME",
                    },
                    {
                      code: "ASSIGNED_TO_ALL",
                      name: "EST_INBOX_ASSIGNED_TO_ALL",
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
                "label": "Ward",
                "type": "locationdropdown",
                "isMandatory": false,
                "disable": false,
                "populators": {
                    "name": "ward",
                    "type": "ward",
                    "optionsKey": "i18nKey",
                    "defaultText": "COMMON_SELECT_WARD",
                    "selectedText": "COMMON_SELECTED",
                    "allowMultiSelect": true
                }
              },
              {
                label: "Workflow State",
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
                label: "MB Reference Number",
                jsonPath: "allOf?.measures[0].referenceId",
                additionalCustomization: true,
              },
              {
                label: "Project Name",
                jsonPath: "fatherName",
              },
              {
                label: "Assignee",
                jsonPath: "additionalFields.fields[0].value",
                // additionalCustomization: true,
              },
              {
                label: "Workflow state",
                jsonPath: "address[0].tenantId",
                additionalCustomization: true,
              },
              {
                label: "MB Amount",
                jsonPath: "address[0].ward.code",
                additionalCustomization: true,
              },
              {
                label: "SLA days remaining",
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
        children: {},
        show: true,
      },
    },
    additionalSections: {},
  };
};

export default InboxMeasurementConfig;