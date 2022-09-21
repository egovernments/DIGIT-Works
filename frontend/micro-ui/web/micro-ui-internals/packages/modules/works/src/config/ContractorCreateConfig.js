export const newConfig = [
    {
      head: "WORKS_CREATE_CONTRACTOR",
      key: "ContractorsDetail",
      isCreate: true,
      hideInCitizen: true,
      body: [
      {
        head: "WORKS_CONTRACTOR_DETAILS",
        // isEditConnection: true,
        isCreateContracte: true,
        // isModifyConnection: true,
        // isEditByConfigConnection: true,
        body: [{
          type: "component",
          key: "ContractorDetails",
          component: "WORKSContractorDetails",
          withoutLabel: true
        }]
      },
      {
        head: "WORKS_CONTRACTOR_DETAILS",
        // isEditConnection: true,
        isCreateContracte: true,
        // isModifyConnection: true,
        // isEditByConfigConnection: true,
        body: [{
          type: "component",
          key: "ContractorTable",
          component: "WORKSContractorTable",
          withoutLabel: true
        }]
      }
      ]
    }
  ]