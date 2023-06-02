export const loginConfig = [
  {
    texts: {
      header: "CORE_COMMON_FORGOT_PASSWORD_LABEL",
      description: "ES_FORGOT_PASSWORD_DESC",
      submitButtonLabel: "CORE_COMMON_CONTINUE",
    },
    inputs: [
      {
        label: "CORE_COMMON_USERNAME",
        type: "text",
        name: "userName",
        error: "ERR_HRMS_INVALID_USERNAME",
      },
      {
        label: "CORE_COMMON_CITY",
        type: "custom",
        name: "city",
        error: "ERR_HRMS_INVALID_CITY",
      },
    ],
  },
];
