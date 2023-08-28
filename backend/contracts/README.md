# Contract Service

The contract service captures work orders or purchase orders. It validates the work order against the estimate(s).
Line items from one estimate can be put in a contract. Line items from multiple estimates can be aggregated into one work order as well. 
The contract service validates the line items from each estimate as part of create and update.

### Service Dependencies

- DIGIT backbone services
- Estimate service
- IDGen
- MDMS
- Workflow
- User
- HRMS
- Organisation
- Persister
- Indexer

## Service Details

- Models a real world work order/contract
- All line items of a single estimate can be put in a contract.
- Line items from multiple estimates can also be grouped into a contract.
- The service validates the estimate line items and ensures no duplication happens in including estimate line items in a contract.
- Terms and conditions, milestones and payment calendar are WIP

### API Specs
https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/Domain%20Services/Works/Contract-Service-v1.0.0.yaml

### Postman Collection

https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/contracts/Contract_Service_Postman_Scripts.postman_collection.json

