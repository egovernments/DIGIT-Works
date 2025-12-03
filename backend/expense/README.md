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
    - `billingType`: Type of billing - `PERIODIC` (individual period bills) or `AGGREGATE` (consolidated bills across periods).
    - `reportStatus`: Reporting status filter - `APPROVED`, `PENDING`, `REJECTED` (matches bill workflow state for reporting).
    - `isAggregate`: Boolean filter - `true` for aggregate bills, `false` for period-specific bills.
    - `billingPeriodIds`: List of billing period IDs to filter bills.
  - V2 guard flag `expense.v2.periodic.billing.enabled` enforcing one bill per register per billing period (constraint-backed).
  - Pagination model updated for Payments V2 usage.
  - See `expense-service-contract-2.0.0.yaml` for complete API specifications.

### API Specs
- Payments V2 contract: `expense/expense-service-contract-2.0.0.yaml`
- Legacy reference: https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/works/expense-contract.yml

### Key configuration (Payments V2)

- `expense.v2.periodic.billing.enabled`: Enforce uniqueness per register+billingPeriod and apply V2 validations.

### Bill Status Lifecycle

Bills follow a workflow-driven status lifecycle:

- **ACTIVE States**: Bills in active workflow states (e.g., PENDING, APPROVED, PAID)
  - Subject to uniqueness constraints enforced by database index
  - Included in all business logic queries and reporting
  - Can transition through workflow actions

- **INACTIVE Status**: Soft-deleted, cancelled, or superseded bills
  - Multiple INACTIVE bills are allowed for the same key combination (not enforced by unique index)
  - Excluded from uniqueness validation (see `BillValidator.getBillsForValidation`)
  - Excluded from business logic queries via `WHERE status != 'INACTIVE'`
  - Bills transition to INACTIVE when:
    - Cancelled through workflow action
    - Superseded by a new bill
    - Marked as duplicate or invalid
  - INACTIVE bills are retained for audit trail but not processed

### Database Constraints (Payments V2)

The unique constraint on `eg_expense_bill` enforces one bill per register per billing period:
```sql
CREATE UNIQUE INDEX index_unique_eg_expense_bill ON eg_expense_bill
(referenceId, businessservice, tenantid, fromperiod, toperiod)
WHERE status != 'INACTIVE';
```

See migration file `V20251114150000__eg_expense_bill_update_unique_constraint_for_v2_ddl.sql` for details.

### Postman Collection

- Payments V2: `expense/expense-service-2.0.0.postman_collection.json`
