All notable changes to this module will be documented in this file.

## 2.0.0 - 2025-11-24

### BREAKING CHANGES

- **New Required Configuration Properties**: The following properties must be configured for the service to function correctly with Payments V2:
  - `attendance.register.kafka.muster.status.update.topic` - Kafka topic for muster status updates
  - `egov.muster.roll.host` - Host URL for muster roll service
  - `egov.muster.roll.search.endpoint` - Endpoint for muster roll search (fallback lookup)
  - `egov.expense.calculator.host` - Host URL for expense calculator service
  - `egov.expense.calculator.billing.period.search.endpoint` - Endpoint for billing period search

- **Database Schema Change**: New `period_statuses` JSONB column added to `eg_wms_attendance_register` table. While the migration is backward compatible (additive column with default values), consumers relying on strict schema validation should update their data models.

- **API Behavior Change**: Register search API now supports period-aware filtering with new query parameters (`billingPeriodId`, `registerPeriodStatus`). Existing V1 queries remain compatible.

### Migration Guide

1. **Configuration Update**: Add the new required properties to your application configuration before deploying v2.0.0.
2. **Database Migration**: The Flyway migration `V20250320120000__add_period_statuses_to_attendance_register.sql` will run automatically. Ensure your database user has ALTER TABLE permissions.
3. **Kafka Setup**: Configure the Kafka topic `muster-roll-status-update` (or your custom topic name) with appropriate partitions and retention settings.
4. **Service Dependencies**: Ensure `muster-roll` and `expense-calculator` services are accessible at the configured hosts.

### Changes

- Added Payments V2 period-aware register search (`billingPeriodId`, `registerPeriodStatus`) with enrichment from persisted muster-roll period statuses.
- Introduced Kafka consumer (`muster-roll-status-update`) to sync periodStatuses JSONB and avoid synchronous muster lookups during search.
- Added backward-compatible V1 reviewStatus mapping for aggregate billing mode.

### Migrations / Persistence

- Added `period_statuses` JSONB column with GIN index to `eg_wms_attendance_register` (migration `V20250320120000__add_period_statuses_to_attendance_register.sql`).
- Updated persister to allow JSONB updates for periodStatuses from Kafka consumer.

### Configuration

- New properties for Payments V2:
  - `attendance.register.kafka.muster.status.update.topic`
  - `egov.muster.roll.host` / `egov.muster.roll.search.endpoint` (fallback lookup)
  - `egov.expense.calculator.host` / `egov.expense.calculator.billing.period.search.endpoint`

## 1.3.0 - 2025-06-13

### Changes

- Added new tag field in eg_wms_attendance_attendee table with supporting index
- Introduced support for attendee tagging and tag-based filtering
- Enhanced attendee retrieval logic to support:
  - Filtering attendees based on explicit tags
  - Resolving and including attendees with matching tags when includeTaggedAttendees is true
- Added new /_updateTag API to update attendee tags

### Configuration Enhancements

- Extended AttendanceRegisterSearchCriteria with:
  - tags – list of tags to filter attendees
  - includeTaggedAttendees – boolean flag that, when true, also fetches every attendee sharing those tags

## 1.2.0 - 2025-03-15

- Added tenant-based schema resolution using MultiStateInstanceUtil and schema placeholders in queries.
- Updated repository methods to require tenant ID and handle InvalidTenantIdException.
- Modified migration scripts for central instance compatibility.

## 1.1.0 - 2025-01-27

### Changes

- Added support for attendance register review status
- Introduced locality code and boundary validation for attendance registers
- Enhanced project and staff management capabilities
- Added configurable open search and registration permissions
- Added pagination and filtering capabilities
- Improved error handling and validation mechanisms

### Configuration Enhancements
- New configuration options for attendance register search and review
- Added boundary service integration
- Expanded staff type and permission management


## 1.0.2 - 2024-03-04

- Added user Id list changes in Individual search
- Upgraded PostgresSQL Driver version to 42.7.1
- Upgraded Flyway base image version to 10.7.1

## 1.0.1 - 2024-03-04

- Implemented Offline Enabled Functionality in Attendance Module

## 0.1.0 - 2023-04-17

- Base version
