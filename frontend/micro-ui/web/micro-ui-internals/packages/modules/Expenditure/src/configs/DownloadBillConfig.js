export const DownloadBillConfig = {
    "tenantId": "pg",
    "moduleName": "commonMuktaUiConfig",
    "DownloadBillConfig":[
        {
            label : "ES_COMMON_DOWNLOADS",
            type: 'download',
            apiDetails: {
                serviceName: "/wms/expense/_search", //API to be changed in download page
                requestParam: {},
                requestBody: {
                    inbox: {
                        moduleSearchCriteria: {}
                    }
                },
                minParametersForSearchForm:1,
                masterName:"commonUiConfig",
                moduleName:"DownloadBillConfig",
                tableFormJsonPath:"requestBody.inbox",
                filterFormJsonPath:"requestBody.inbox.moduleSearchCriteria",
                searchFormJsonPath:"requestBody.inbox.moduleSearchCriteria",
            },
            sections : {
                searchResult: {
                    label: "",
                    uiConfig: {
                        columns: [
                            {
                                label: "WORKS_SNO",
                                jsonPath: ""
                            },
                            {
                                label: "ES_COMMON_JOB_ID",
                                jsonPath: ""
                            },
                            {
                                label: "ES_COMMON_DATE",
                                jsonPath: ""
                            },
                            {
                                label: "ES_COMMON_NO_OF_BILLS",
                                jsonPath: ""
                            },
                            {
                                label: "ES_COMMON_NO_OF_BENEFICIARIES",
                                jsonPath: ""
                            },
                            {
                                label: "ES_COMMON_TOTAL_AMOUNT",
                                jsonPath: "businessObject.netPayableAmount",
                                additionalCustomization:true,
                                headerAlign: "right"
                            },
                            {
                                label: "CORE_COMMON_STATUS",
                                jsonPath: "ProcessInstance.state.state",
                                additionalCustomization:true
                            },
                            {
                                label: "CS_COMMON_ACTION",
                                jsonPath: "businessObject.businessservice",
                                additionalCustomization:true
                            }
                        ],
                        enableGlobalSearch: false,
                        enableColumnSort: true,
                        resultsJsonPath: "items"                      
                    },
                    children: {},
                    show: true 
                }
            },
            additionalSections : {}
        }
      ]
}