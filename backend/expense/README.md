# Expense Service

The expense module implements the functionality of bills and payments. A bill or a group of bills can be aggregated together as payments.
The expense module always works in combination with a calculator service.
The calculator calls into the expense service to create bills.

### Service Dependencies

- DIGIT backbone services
- Calculator Service
- Persister
- Indexer
- IDGen
- MDMS
- Workflow
- Notification

## Service Details

- Create/update/search functionality for bills
- Ability to create different bill types according to configuration.
- Workflow is integrated and needs to be configured for usage.
- Works with an expense calculator that contains the business logic to compute bills. 
- Payments V2 enhancements:
  - Period-aware bill search filters: `billingPeriodIds`, `billingType`, `reportStatus`, `isAggregate`.
  - V2 guard flag `expense.v2.periodic.billing.enabled` enforcing one bill per register per billing period (constraint-backed).
  - Pagination model updated for Payments V2 usage.

### API Specs
- Payments V2 contract: `expense/expense-service-contract-2.0.0.yaml`
- Legacy reference: https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/works/expense-contract.yml

### Key configuration (Payments V2)

- `expense.v2.periodic.billing.enabled`: Enforce uniqueness per register+billingPeriod and apply V2 validations.

### Postman Collection

- Payments V2: `expense/expense-service-2.0.0.postman_collection.json`
