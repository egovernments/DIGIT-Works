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

### API Specs
https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/works/expense-contract.yml