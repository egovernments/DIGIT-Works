# Expense-Calculator

The expense calculator is an implementation specific services that works in tandem with the expense service. This service holds all the specific business logic in computing expenses and calls the billing service with the correct payload to create a bill.


### Service Dependencies

- DIGIT backbone services
- Persister
- MDMS
- IDgen
- Expense

### Service Details

- Calculate the bill amount according to business logic.
- Post the bill to expense service to create a bill.




