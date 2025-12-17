const inboxConfigMukta = () => {
  return {
    label: "ACTION_TEST_ESTIMATE_INBOX",
    type: "inbox",
    apiDetails: {
      serviceName: "/inbox/v2/_search",
      requestParam: {},
      requestBody: {
        inbox: {
          processSearchCriteria: {
            businessService: ["estimate"],
            moduleName: "estimate-service",
          },
          moduleSearchCriteria: {},
        },
      },
      minParametersForSearchForm: 0,
      minParametersForFilterForm: 0,
      masterName: "commonUiConfig",
      moduleName: "EstimateInboxConfig",
      tableFormJsonPath: "requestBody.inbox",
      filterFormJsonPath: "requestBody.inbox.moduleSearchCriteria",
      searchFormJsonPath: "requestBody.inbox.moduleSearchCriteria",
    },
    sections: {
      search: {
        uiConfig: {
          headerStyle: null,
          primaryLabel: "Search",
          secondaryLabel: "Clear Search",
          minReqFields: 1,
          defaultValues: {
            estimateNumber: "",
            projectId: "",
            projectType: "",
          },
          fields: [
            {
              label: "ESTIMATE_INBOX_ESTIMATE_NO",
              type: "text",
              isMandatory: false,
              disable: false,
              preProcess: {
                convertStringToRegEx: ["populators.validation.pattern"],
              },
              populators: {
                name: "estimateNumber",
                error: "ESTIMATE_PATTERN_ERR_MSG",
                validation: {
                  pattern: "ES\/[0-9]+-[0-9]+\/[0-9]+",
                  minlength: 2,
                },
              },
            },
            {
              label: "WORKS_PROJECT_ID",
              type: "text",
              isMandatory: false,
              disable: false,
              preProcess: {
                convertStringToRegEx: ["populators.validation.pattern"],
              },
              populators: {
                name: "projectId",
                error: "PROJECT_PATTERN_ERR_MSG",
                validation: {
                  pattern: "PR\\/[0-9]+-[0-9]+\\/[0-9]+\\/[0-9]+",
                  minlength: 2,
                },
              },
            },
            {
              label: "WORKS_PROJECT_TYPE",
              type: "dropdown",
              isMandatory: false,
              disable: false,
              populators: {
                name: "projectType",
                optionsKey: "name",
                // optionsCustomStyle: {
                //   top: "2.3rem",
                // },
                mdmsConfig: {
                  masterName: "ProjectType",
                  moduleName: "works",
                  localePrefix: "COMMON_MASTERS",
                },
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
        estimateNumber: "",
        projectId: "",
        department: "",
        estimateStatus: "",
        fromProposalDate: "",
        toProposalDate: "",
        uiConfig: {
          columns: [
            {
              label: "ESTIMATE_INBOX_ESTIMATE_NO",
              jsonPath: "ProcessInstance.businessId",
              key: "estimateNumber",
              additionalCustomization: true,
            },
            {
              label: "ES_COMMON_PROJECT_NAME",
              jsonPath: "businessObject.project.name",
            },
            {
              label: "ESTIMATE_PREPARED_BY",
              jsonPath: "businessObject.additionalDetails.creator",
            },
            {
              label: "COMMON_ASSIGNEE",
              jsonPath: "ProcessInstance.assignes",
              additionalCustomization: true,
              key: "assignee",
            },
            {
              label: "COMMON_WORKFLOW_STATES",
              jsonPath: "ProcessInstance.state.state",
              additionalCustomization: true,
              key: "state",
            },
            {
              label: "WORKS_ESTIMATED_AMOUNT",
              jsonPath: "businessObject.additionalDetails.totalEstimatedAmount",
              additionalCustomization: true,
              key: "estimatedAmount",
              headerAlign: "right"
            },
            {
              label: "COMMON_SLA_DAYS",
              jsonPath: "businessObject.serviceSla",
              additionalCustomization: true,
              key: "sla",
            },
          ],
          enableGlobalSearch: false,
          enableColumnSort: true,
          resultsJsonPath: "items",
        },
        children: {},
        show: true,
      },
      links: {
        uiConfig: {
          links: [
            {
              text: "ACTION_TEST_CREATE_ESTIMATE",
              url: "/employee/project/search-project",
              roles: ["PROJECT_VIEWER", "ESTIMATE_CREATOR"],
            },
            {
              text: "ACTION_TEST_SEARCH_ESTIMATE",
              url: "/employee/estimate/search-estimate",
              roles: ["ESTIMATE_VIEWER", "ESTIMATE_CREATOR", "ESTIMATE_VERIFIER", "TECHNICAL_SANCTIONER", "ESTIMATE_APPROVER"],
            },
          ],
          label: "MUKTA",
          logoIcon: {
            component: "PropertyHouse",
            customClass: "inbox-search-icon--projects",
          },
        },
        children: {},
        show: true,
      },
      filter: {
        uiConfig: {
          type: "filter",
          headerStyle: null,
          primaryLabel: "Filter",
          secondaryLabel: "",
          minReqFields: 0,
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
              label: "COMMON_WARD",
              type: "locationdropdown",
              isMandatory: false,
              disable: false,
              populators: {
                // optionsCustomStyle: {
                //   top: "2.3rem",
                // },
                name: "ward",
                type: "ward",
                optionsKey: "i18nKey",
                defaultText: "COMMON_SELECT_WARD",
                selectedText: "COMMON_SELECTED",
                allowMultiSelect: true,
                isDropdownWithChip:true,
              },
            },
            {
              label: "COMMON_LOCALITY",
              type: "locationdropdown",
              isMandatory: false,
              disable: false,
              populators: {
                name: "locality",
                type: "locality",
                optionsKey: "i18nKey",
                defaultText: "COMMON_SELECT_LOCALITY",
                selectedText: "COMMON_SELECTED",
                allowMultiSelect: true,
                isDropdownWithChip:true,
              },
            },
            {
              label: "COMMON_WORKFLOW_STATES",
              type: "workflowstatesfilter",
              labelClassName:"checkbox-status-filter-label" ,
              isMandatory: false,
              disable: false,
              populators: {
                name: "state",
                labelPrefix: "WF_EST_",
                businessService: "estimate",
              },
            },
          ],
        },
        label: "Filter",
        show: true,
      },
    },
    additionalSections: {},
  };
};

export default inboxConfigMukta;
