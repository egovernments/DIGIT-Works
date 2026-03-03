
All notable changes to this module will be documented in this file.

## 2.0.1 - 2026-03-03

### Changes

- Upgraded tracer library version; new tracer handles `DataAccessException` errors via `@ControllerAdvice`.

## 2.0.0 - 2025-11-24

- Added Payments V2 period-aware bill search fields (`billingPeriodIds`, `billingType`, `reportStatus`, `isAggregate`) with backward compatibility.
- Introduced flag `expense.v2.periodic.billing.enabled` to enforce one bill per register per billing period.
- Updated pagination model to align with Payments V2 usage.
- Added constraints/migrations for register+period uniqueness (`V20250213120000__expense_bill_update_unique_constraint_for_v2_ddl.sql`, `V20250213150000__fix_billing_period_constraints.sql`).

## 1.2.0 - 2025-06-20

- Added tenant-based schema resolution using MultiStateInstanceUtil and schema placeholders in queries.
- Updated repository methods to require tenant ID and handle InvalidTenantIdException.
- Modified migration scripts for central instance compatibility.

## 1.1.0 - 2025-01-27

- Added localityCode to bill
- Added like query in bill referenceId search for health context
- Added search from and to date in bill search criteria
- Changed search query from INNER JOIN to LEFT join to handle empty lineItems in bill 

## 1.0.0 - 2023-04-17

- Base version
