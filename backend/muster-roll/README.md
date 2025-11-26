# Muster Roll Service

This service depends on the Attendance service. Attendance logs basic timestamps; muster-roll aggregates, validates, and sequences those logs to produce period-specific muster rolls and wage computation inputs.

### Service Dependencies

- DIGIT backbone services
- Idgen
- Persister
- Indexer
- Workflow
- User
- Attendance
- Expense calculator (for billing period lookups and bill-status lock checks)

## Service Details
- Estimate wages of a wage seeker based on attendance logs
- Create/update/search muster rolls
- Payments V2 enhancements:
  - `billingPeriodId` on create/update/search; period-aware date intersection with the register’s billing period.
  - Register+billingPeriod uniqueness to prevent duplicate musters per period.
  - Billing lock check via calculator `/v1/_checkBillStatus` to stop edits once billed.
  - Kafka status publisher (`musterroll.kafka.status.update.topic`) sends status updates to attendance for periodStatuses sync.
  - V1 reviewStatus updates skipped for V2 musters (multi-period registers).

### Key configuration (Payments V2)

- `musterroll.kafka.status.update.topic`: Publish muster status changes.
- `works.expense.calculator.billing.period.search.endpoint`, `works.expense.calculator.billing.config.search.endpoint`: Period/config lookups.
- `musterroll.period.locking.enabled`: Toggle write protection after billing.

### API Specs
- Payments V2 contract: `muster-roll/muster-roll-v2-api-swagger-2.0.0.yaml`
- Legacy reference: https://github.com/egovernments/DIGIT-Specs/blob/master/Domain%20Services/Works/Muster-Roll-Service-v1.0.0.yaml

### Postman Collection
- Payments V2: `muster-roll/muster-roll-2.0.0.postman_collection.json`
- Legacy: https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/muster-roll/src/main/resources/Muster%20Roll%20Service.postman_collection.json
