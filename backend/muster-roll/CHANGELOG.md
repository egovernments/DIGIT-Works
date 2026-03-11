
All notable changes to this module will be documented in this file.

## 2.0.1 - 2026-03-03

### Changes

- Upgraded tracer library version; new tracer handles `DataAccessException` errors via `ExceptionAdvise`.
- Added OpenTelemetry BOM and Instrumentation BOM dependency management.

## 2.0.0 - 2025-11-24

### Changes

- Added Payments V2 billing period support on muster rolls (create/update/search with `billingPeriodId`).
- Enforced period-aware date intersection with attendance register and duplicate prevention per register+period.
- Added Kafka status publisher for V2 musters to keep attendance register periodStatuses in sync.
- Introduced billing lock check via calculator `/v1/_checkBillStatus` to block updates once billed.
- Skipped V1 reviewStatus updates for V2 musters to preserve multi-period registers.

### Migrations / Constraints

- Added billing period columns and indexes to muster and attendance tables (`V20250201120000__add_billing_period_to_muster_roll.sql`, `V20250201123000__add_billing_period_to_attendance_tables.sql`).
- Added unique constraint for registerId + billingPeriodId (`V20250201122000__add_v2_register_period_unique_constraint.sql`).

### Configuration

- New properties:
  - `musterroll.kafka.status.update.topic`
  - `works.expense.calculator.billing.period.search.endpoint`, `works.expense.calculator.billing.config.search.endpoint`
  - `musterroll.period.locking.enabled`

## 1.2.0 - 2025-06-20

### Changes

- Added tenant-based schema resolution using MultiStateInstanceUtil and schema placeholders in queries.
- Updated repository methods to require tenant ID and handle InvalidTenantIdException.
- Modified migration scripts for central instance compatibility.

## 1.1.0 - 2025-01-27

### Changes
- Changed notification to be configurable
- Configurable start day as Monday validation
- Configurable bank account for muster roll health
- Configurable recomputing attendance for muster roll update
- Added configurable support for workflow in muster roll

### New features
- Update attendance register on approval of muster roll
- Added configurable validation for number of days for attendance for muster roll apis
- new /v2/_search API to search muster roll, to be able to use large request body instead of params. ie. bills with support of search using multiple register ids

### Backward Compatibility
- All changes are configurable and backward compatible

## 0.1.0 - 2023-04-17

- Base version
