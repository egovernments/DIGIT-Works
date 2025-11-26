# Attendance Service

The attendance service provides generic attendance register management and logging of "in" and "out" timestamps per individual. Aggregation (muster-roll) is handled by the muster-roll service.

## Service Dependencies

- DIGIT backbone services
- Individual
- MDMS
- ID-GEN
- Persister
- Indexer

## Service Details

- Create/update/search attendance registers; map staff/attendees with permission enforcement.
- Record attendance events (IN/OUT timestamps) against registers.
- Payments V2 (period-aware) enhancements:
  - V2 search parameters: `billingPeriodId`, `registerPeriodStatus` (APPROVED/PENDING) to filter registers overlapping a billing period.
  - Register responses include `periodStatuses` (per-period muster status) and derived `registerPeriodStatus`.
  - Kafka-driven sync of muster-roll statuses (`muster-roll-status-update` topic) to update `periodStatuses` JSONB; search falls back to muster-roll API only when status is missing.
  - Backward compatibility: `reviewStatus` still works and overrides V2 filters when provided.

## Key configuration (Payments V2)

- `attendance.register.kafka.muster.status.update.topic`: Topic consumed to sync period statuses from muster-roll.
- `egov.expense.calculator.host` + `egov.expense.calculator.billing.period.search.endpoint`: Billing period metadata lookup.
- `egov.muster.roll.host` + `egov.muster.roll.search.endpoint`: Fallback muster-roll search when a period status is absent.

## API Specs

- Payments V2 contract: `attendance/Attendance-Service-2.0.0.yaml`
- Legacy reference: https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/Domain%20Services/Works/Attendance-Service-v1.0.0.yaml

## Postman Collection

- Payments V2: `attendance/Attendance-Service-2.0.0.postman_collection.json`
- Legacy: https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/attendance/Attendace%20Service%20Postman%20Scripts.postman_collection.json
