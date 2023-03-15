const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
  const BillDetails = {
    title: " ",
    asSectionHeader: true,
    values: [
      { title: "WORKS_BILL_NUMBER", value: "Bill/2021-22/09/0001" || t("NA")},
      { title: "Work order number", value: "A Manjunath" || t("NA")},
      { title: "WORKS_PROJECT_ID", value: "28-09-2022" || t("NA")},
      { title: "PROJECTS_DESCRIPTION", value: "To Approve"|| t("NA") },
      { title: "ES_COMMON_LOCATION", value: "To Approve"|| t("NA") },
      { title: "PROJECTS_DESCRIPTION", value: "To Approve"|| t("NA") },
      { title: "Bill Classification", value: "To Approve"|| t("NA") },
      { title: "Bill Date", value: "To Approve"|| t("NA") },
      { title: "ES_COMMON_STATUS", value: "To Approve"|| t("NA") }
    ]
  }

  const applicationDetails = { applicationDetails: [BillDetails] };

  return {
    applicationDetails,
    applicationData: {},
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

export const View = {
    fetchBillDetails: async (t, tenantId, data, searchParams) => {
        return transformViewDataToApplicationDetails(t, {}, tenantId)
        /*
          try {
              const response = await WageSeekerService.search(tenantId, data, searchParams);
              console.log('response', response);
              return transformViewDataToApplicationDetails(t, response)
          } catch (error) {
              console.log('error', error);
              throw new Error(error?.response?.data?.Errors[0].message);
          }
        */
    }
}