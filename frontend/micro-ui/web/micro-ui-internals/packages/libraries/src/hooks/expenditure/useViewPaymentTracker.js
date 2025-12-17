import React from 'react'

const useViewPaymentTracker = ({projectId, tenantId}) => {

  const projectSearchCriteria = {
    url: `/project/v1/_search?limit=10&offset=0&tenantId=${tenantId}&includeAncestors=true&includeDescendants=true`,

    body: {
      Projects: [
        {
          tenantId: tenantId,
          projectNumber: projectId
        }
      ],
      apiOperation: "SEARCH"
    }
  }

  let { isLoading: isProjectLoading, data : projectData } = Digit.Hooks.useCustomAPIHook(projectSearchCriteria);

  const billPaidSearchCriteria = {
    url: "/wms/report/payment_tracker",

    body: {
      "searchCriteria": {
        "tenantId": tenantId,
        "moduleSearchCriteria": {
          "projectId": projectId
        },
      }
    }
  };

  let { isLoading: isBillPaidLoading, data : billPaidData } = Digit.Hooks.useCustomAPIHook(billPaidSearchCriteria);
  
  const billSearchCriteria = {
    url: "/wms/mukta-pi/_search",

    body: {
      inbox: {
        "tenantId": tenantId,
        "moduleSearchCriteria": {
          "tenantId": tenantId,
          "projectId": projectId
        },
        "limit": 100,
        "offset": 0,
      }
    }
  };

  let { isLoading: isBillLoading, data : billData } = Digit.Hooks.useCustomAPIHook(billSearchCriteria);

  return {projectData, billPaidData, billData, isProjectLoading, isBillPaidLoading, isBillLoading}
}

export default useViewPaymentTracker