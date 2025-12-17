const ViewScheduledJobsExcelConfig = {
    tenantId: "pg",
    moduleName: "ViewScheduledJobsExcelConfig",
    ViewScheduledJobsExcelConfig: [
      {
        label: "EXP_GENERATE_EXCEL_JOBS",
        type: "search",
        actionLabel: "",
        actionRole: "",
        actionLink: "",
        apiDetails: {
          serviceName: "/wms/report/payment-tracker/_search",
          requestParam: {},
          requestBody: {
            reportSearchCriteria: {},
          },
          minParametersForSearchForm: 1,
          masterName: "commonUiConfig",
          moduleName: "ViewScheduledJobsExcelConfig",
          tableFormJsonPath: "requestBody.pagination",
          filterFormJsonPath: "requestBody.reportSearchCriteria",
          searchFormJsonPath: "requestBody.reportSearchCriteria",
        },
        sections: {
          search: {
            uiConfig: {
              headerStyle: null,
              primaryLabel: "ES_COMMON_SEARCH",
              secondaryLabel: "ES_COMMON_CLEAR_SEARCH",
              searchWrapperClassName:"view-scheduled-jobs-serach-wrapper",
              minReqFields: 1,
              showFormInstruction: "",
              defaultValues: {
                status: "",
                scheduledFrom: "",
                scheduledTo: "",
              },
              fields: [
                {
                  label: "EXP_STATUS",
                  type: "dropdown",
                  isMandatory: false,
                  disable: false,
                  populators: {
                    name: "status",
                    optionsKey: "name",
                    optionsCustomStyle: {
                      top: "2.3rem",
                    },
                    options:[
                      {code:"INITIATED",name:"Initiated"},
                      {code:"INPROGRESS",name:"In progress"},
                      {code:"COMPLETED",name:"Completed"},
                      {code:"FAILED",name:"Failed"}
                    ]
                  },
                },
                {
                  label: "EXP_SCHEDULED_FROM",
                  type: "date",
                  isMandatory: false,
                  disable: false,
                  key: "scheduledFrom",
                  preProcess: {
                    updateDependent: ["populators.max"],
                  },
                  populators: {
                    name: "scheduledFrom",
                    max: "currentDate",
                  },
                },
                {
                  label: "EXP_SCHEDULED_TO",
                  type: "date",
                  isMandatory: false,
                  disable: false,
                  key: "scheduledTo",
                  preProcess: {
                    updateDependent: ["populators.max"],
                  },
                  populators: {
                    name: "scheduledTo",
                    error: "DATE_VALIDATION_MSG",
                    max: "currentDate",
                  },
                  additionalValidation: {
                    type: "date",
                    keys: {
                      start: "scheduledFrom",
                      end: "scheduledTo",
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
            uiConfig: {
              columns: [
                {
                  label: "EXP_JOB_ID",
                  jsonPath: "reportNumber",
                  additionalCustomization: true,
                },
                {
                  label: "EXP_SCHEDULED_ON",
                  jsonPath: "auditDetails.createdTime",
                  additionalCustomization: true,
                },
                {
                  label: "EXP_NO_OF_PROJECTS",
                  jsonPath: "noOfProjects",
                  // additionalCustomization: true,
                },
                {
                  label: "EXP_STATUS_ACTION",
                  jsonPath: "status",
                  additionalCustomization: true,
                  headerAlign: "right",
                },
              ],
              enableGlobalSearch: false,
              enableColumnSort: true,
              resultsJsonPath: "reportJobs",
            },
            children: {},
            show: true,
          },
        },
        additionalSections: {},
      },
    ],
  };
  
  export default ViewScheduledJobsExcelConfig;
  