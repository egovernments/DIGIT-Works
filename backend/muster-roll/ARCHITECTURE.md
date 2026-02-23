# Muster-Roll Service Architecture

## Overview

The Muster-Roll Service is a Spring Boot 3.2.x microservice (Java 17) that aggregates daily attendance logs into attendance summaries (muster rolls) for worker payment calculations. It supports two billing flows: **V1 (Legacy)** and **V2 (Intermediate Billing)**.

**Port:** 8051
**Context Path:** `/muster-roll`
**Version:** 2.0.0

---

## Core Business Logic

### What is a Muster Roll?

A **muster roll** is a time-and-attendance summary that:
- Aggregates daily attendance logs for workers in an attendance register
- Spans from a start date to an end date (V1) or a specific billing period (V2)
- Contains individual attendance summaries and detailed per-day attendance entries
- Drives payment calculations via the expense-calculator service

### V1 Flow (Legacy - Campaign-Based)

```
Register Created
    ↓
Muster Roll Created (full register date range)
    ↓
Muster Approved → Register.reviewStatus = "APPROVED"
    ↓
One Bill Generated (at campaign end)

Characteristics:
- One register → One muster roll → One bill
- No billingPeriodId field
- Register.reviewStatus updated synchronously on approval
```

### V2 Flow (Intermediate Billing - Period-Based)

```
Register Created
    ↓
Multiple Billing Periods Defined
    ↓
Muster Rolls Created (one per period)
    ↓
Muster Approved → Event Published to Kafka
    ↓
Multiple Bills Generated (one per period)

Characteristics:
- One register → Multiple muster rolls → Multiple bills
- billingPeriodId field REQUIRED
- Period-aware date intersection (register dates ∩ period dates)
- Status published via Kafka event to attendance service
- Period locking after billing to prevent edits
```

---

## Architecture Patterns

### 1. Service Architecture

```
┌─────────────────────────────────────────────────┐
│           API Controllers                        │
│   (MusterRollApiController)                      │
└────────────────────┬────────────────────────────┘
                     │
        ┌────────────┴────────────┐
        │                         │
┌───────▼──────────────┐  ┌──────▼──────────────┐
│  MusterRollService   │  │  Validator Service  │
│  (Business Logic)    │  │  (Input Validation) │
└───────┬──────────────┘  └─────────────────────┘
        │
        ├──→ EnrichmentService      (Add default values, IDs)
        ├──→ CalculationService     (Attendance computation)
        ├──→ WorkflowService        (Status transitions)
        ├──→ NotificationService    (SMS alerts)
        └──→ MusterRollRepository   (Database access)
              │
              ├──→ QueryBuilder       (Dynamic SQL)
              └──→ RowMapper          (Result mapping)
        │
        └──→ MusterRollProducer     (Kafka events)
        │
        └──→ MusterRollServiceUtil  (External service calls)
             ├──→ Attendance Service (Register/logs)
             ├──→ MDMS Service       (Configuration)
             ├──→ Workflow Service   (Transitions)
             ├──→ Expense Calculator (Billing periods)
             └──→ User Service       (Permissions)
```

### 2. Data Access Pattern

**JDBC with RowMapper** (not JPA):
- Custom SQL queries via QueryBuilder
- Manual result mapping via RowMapper
- Allows fine-grained control and optimization

```java
// Pattern: QueryBuilder + RowMapper
MusterRollQueryBuilder.buildQuery(criteria) → SQL
JDBC.executeQuery(sql) → ResultSet
MusterRollRowMapper.mapRow(rs) → MusterRoll Object
```

### 3. Event-Driven Architecture (V2)

```
Muster Status Change
    ↓
publishMusterRollStatusUpdateEvent()
    ↓
Kafka Topic: muster-roll-status-update
    ↓
Attendance Service Consumer
    ↓
Update period_statuses in eg_wms_attendance_register
    ↓
Next search returns status without API call
```

**Why Kafka (vs synchronous)?**
- Decoupled: Muster-roll doesn't know attendance internals
- Resilient: Events queued if attendance is down
- Scalable: New consumers don't affect producer
- Non-blocking: Updates complete immediately

---

## Key Components

### Controllers

**MusterRollApiController** (`web/controllers/`)
- `POST /v1/_create` - Create muster roll (estimate)
- `POST /v1/_search` - Search muster rolls with filtering
- `PUT /v1/_update` - Update muster roll (recompute attendance, approve)

### Services

| Service | Purpose |
|---------|---------|
| **MusterRollService** | Core CRUD operations, V1/V2 flow handling, period-aware dates |
| **EnrichmentService** | Add IDs, audit details, tenant info, dates |
| **CalculationService** | Compute per-day and aggregate attendance from logs |
| **WorkflowService** | Transition muster status via workflow service |
| **NotificationService** | Send SMS notifications to CBOs |
| **MusterRollProducer** | Publish Kafka events (create, update, calculate) |

### Repository Layer

**MusterRollRepository** + **MusterRollQueryBuilder** + **MusterRollRowMapper**
- Query building with dynamic filters (tenant, register, dates, period, status)
- JDBC result mapping to domain objects
- Pagination support (limit/offset)

### Utilities

| Util | Purpose |
|------|---------|
| **MusterRollServiceUtil** | External service calls (attendance, MDMS, billing periods) |
| **MdmsUtil** | Master Data Management System data fetching |
| **IdgenUtil** | Generate unique muster roll numbers |
| **NotificationUtil** | Format notification messages |
| **LocalizationUtil** | Multi-language message support |
| **UserUtil** | User permission validation |

---

## Data Model

### Core Entities

#### MusterRoll (eg_wms_muster_roll table)

```
id                      → UUID
tenant_id              → Multi-tenant identifier
attendance_register_id → Reference to attendance register
billing_period_id      → [V2] Reference to billing period (NULL for V1)
start_date             → Timestamp (milliseconds)
end_date               → Timestamp (milliseconds)
musterroll_number      → Generated unique number
musterroll_status      → CREATED, APPROVAL_PENDING, APPROVED, REJECTED
status                 → Generic status field
reference_id           → Campaign/Project reference
service_code           → Service classification code
additional_details     → JSONB extensible field
audit_details          → Created/Modified by, timestamps
```

#### IndividualEntry (Nested in MusterRoll)

```
id                     → UUID
individual_id          → Worker identifier
actual_total_attendance → Computed attendance days
modified_total_attendance → Manually adjusted attendance
additional_details     → JSONB
attendanceEntries[]    → Daily attendance records
```

#### AttendanceEntry (Nested per individual)

```
id                     → UUID
time                   → Date (timestamp in milliseconds)
attendance             → Value (1.0, 0.5, 0, etc.)
```

### Database Schema

**Tables:**
- `eg_wms_muster_roll` - Muster roll header
- `eg_wms_attendance_summary` - Individual summaries (denormalized from muster)
- `eg_wms_attendance_entries` - Daily attendance records (denormalized)

**Indexes:**
- `(tenant_id, attendance_register_id)` - Register lookup
- `(tenant_id, billing_period_id)` - V2 period lookup
- `(tenant_id, musterroll_number)` - Number search
- `(tenant_id, musterroll_status)` - Status filtering

**Constraints:**
- Unique: `(tenant_id, attendance_register_id, start_date, end_date, billing_period_id IS NULL)` - V1 dedup
- Unique: `(tenant_id, attendance_register_id, billing_period_id)` - V2 dedup

---

## Communication Patterns

### 1. Kafka Topics

| Topic | Direction | Consumer | Purpose |
|-------|-----------|----------|---------|
| `save-musterroll` | Out | Persister | Create muster roll in database |
| `update-musterroll` | Out | Persister | Update existing muster roll |
| `calculate-musterroll` | Out | Expense Calculator | Trigger payment calculation |
| `muster-roll-status-update` | Out | Attendance Service | [V2] Publish status changes |

### 2. Synchronous API Calls

**Inbound Dependencies:**

| Service | Endpoint | Usage | Sync/Async |
|---------|----------|-------|-----------|
| **Attendance** | `/attendance/v1/_search` | Fetch registers & logs | Sync |
| **MDMS** | `/egov-mdms-service/v1/_search` | Skill levels, config | Sync |
| **MDMS V2** | `/mdms-v2/v1/_search` | Configuration data | Sync |
| **Workflow** | `/egov-workflow-v2/...` | Status transitions | Sync |
| **Expense Calculator** | `/billing-config/v1/periods/_search` | Fetch billing periods | Sync |
| **Expense Calculator** | `/v1/_checkBillStatus` | Check if period locked | Sync |
| **IDGen** | `/egov-idgen/id/_generate` | Generate muster numbers | Sync |
| **Localization** | `/localization/messages/v1/_search` | Translate messages | Sync |
| **User** | `/user/users/_search` | Fetch user details | Sync |

### 3. Request/Response Pattern

```json
// Request
{
  "requestInfo": { userId, token, action, version... },
  "musterRoll": { id, registerId, billingPeriodId, startDate, endDate... },
  "individualEntries": [
    {
      "individualId": "...",
      "attendanceEntries": [
        { "time": 1234567890000, "attendance": 1.0 }
      ]
    }
  ]
}

// Response
{
  "responseInfo": { status, timestamp... },
  "musterRolls": [...],
  "count": 10
}
```

---

## Integration Points

### External Service Dependencies

#### 1. Attendance Service
- **Source of Truth:** Attendance logs, attendance registers
- **Interaction:** Fetch logs for computation, update register status (V1 only)
- **Event Consumption:** Receives muster status updates (V2) via Kafka

#### 2. Expense Calculator Service
- **Trigger:** Approved muster rolls published to calculate topic
- **Billing Periods:** Fetches period definitions for V2 period-aware dates
- **Bill Status:** Checks if period locked to prevent edits

#### 3. Workflow Service
- **Status Transitions:** CREATED → APPROVAL_PENDING → APPROVED/REJECTED
- **Module:** `muster-roll-services`, Business Service: `MR`

#### 4. MDMS (Master Data Management)
- **Configuration:** Skill levels, designation mappings, business rules
- **V2 API:** Newer format for complex queries

#### 5. IDGen Service
- **Muster Number Generation:** Unique identifiers per muster roll
- **Format:** Configurable via MDMS

#### 6. Persister Service
- **Async Database Persistence:** Muster rolls persisted to DB via Kafka
- **Transaction Support:** Ensures ACID compliance across tables
- **Audit Trail:** Automatic audit logging

---

## Configuration Management

### Application Properties

**Key Configurations:**

```properties
# Server
server.port=8051
server.contextPath=/muster-roll

# Database (Flyway migrations)
spring.datasource.url=jdbc:postgresql://host:5432/digit-works
spring.flyway.enabled=true
spring.flyway.table=musterroll_service_schema

# Kafka Topics
musterroll.kafka.create.topic=save-musterroll
musterroll.kafka.update.topic=update-musterroll
musterroll.kafka.calculate.topic=calculate-musterroll
musterroll.kafka.status.update.topic=muster-roll-status-update

# Pagination
musterroll.default.limit=100
musterroll.search.max.limit=200

# Workflow
musterroll.workflow.enabled=true
musterroll.workflow.business.service=MR
musterroll.noworkflow.create.status=APPROVAL_PENDING

# V2 Intermediate Billing Features
musterroll.period.locking.enabled=true          # Prevent edits after billing
musterroll.update.recompute.attendance.enabled=true

# Feature Flags
musterroll.validate.start.date.monday.enabled=true
musterroll.set.default.duration.enabled=true
musterroll.add.bank.account.details.enabled=true
musterroll.update.attendance.register.review.status.enabled=false
```

### Environment Overrides

Create `env.development.local` for local development:
```bash
# Override only what you need
egov.mdms.host=http://localhost:8080
egov.workflow.host=http://localhost:8080
kafka.config.bootstrap_server_config=localhost:9092
```

---

## Request Flow Examples

### Flow 1: Create Muster Roll (V2 with Billing Period)

```
1. POST /v1/_create
   └─ MusterRollApiController.createMusterRoll()
      ├─ MusterRollValidator.validateCreateMusterRoll()
      ├─ MusterRollService.createMusterRoll()
      │  ├─ applyPeriodAwareDates()
      │  │  ├─ fetchBillingPeriod() → Expense Calculator
      │  │  ├─ fetchAttendanceRegister() → Attendance Service
      │  │  └─ Calculate intersection (register ∩ period)
      │  ├─ EnrichmentService.enrichMusterRollOnCreate()
      │  │  ├─ Set auditDetails
      │  │  ├─ Set musterRollNumber (IDGen)
      │  │  ├─ Set timestamps
      │  │  └─ Check for duplicates
      │  ├─ CalculationService.createAttendance()
      │  │  └─ Fetch logs, compute per-day & aggregate
      │  ├─ WorkflowService.updateWorkflowStatus()
      │  │  └─ Transition to APPROVAL_PENDING
      │  ├─ publishMusterRollStatusUpdateEvent() → Kafka
      │  │  └─ MusterRollStatusUpdateEvent published
      │  └─ MusterRollProducer.push() → save-musterroll topic
      │     └─ Persister saves to DB (async)
      └─ Return MusterRollResponse
```

### Flow 2: Update & Approve Muster Roll

```
1. PUT /v1/_update
   └─ MusterRollApiController.updateMusterRoll()
      ├─ validatePeriodNotLocked() [V2 only]
      │  └─ Check if bill already generated (period locked)
      ├─ MusterRollValidator.validateUpdateMusterRoll()
      ├─ MusterRollService.updateMusterRoll()
      │  ├─ reapplyPeriodAwareDates() [if V2]
      │  ├─ fetchExistingMusterRoll()
      │  ├─ EnrichmentService.enrichMusterRollOnUpdate()
      │  ├─ [Optional] CalculationService.updateAttendance()
      │  ├─ WorkflowService.updateWorkflowStatus()
      │  │  └─ Transition to APPROVED
      │  ├─ publishMusterRollStatusUpdateEvent() → Kafka
      │  │  └─ Attendance service updates period_statuses
      │  ├─ [V1 Only] Update Register.reviewStatus = APPROVED
      │  ├─ MusterRollProducer.push() → update-musterroll topic
      │  │  └─ Persister updates DB (async)
      │  ├─ NotificationService.sendNotificationToCBO()
      │  │  └─ SMS notification via Kafka
      │  └─ [If Approved] MusterRollProducer.push() → calculate-musterroll
      │     └─ Expense Calculator triggers bill creation
      └─ Return MusterRollResponse
```

### Flow 3: Search Muster Rolls (with RBAC)

```
1. POST /v1/_search
   └─ MusterRollApiController.searchMusterRolls()
      ├─ MusterRollValidator.validateSearchMuster()
      ├─ Check user role (ORG_ADMIN, ORG_STAFF restricted)
      │  └─ [If Restricted] fetchAttendanceRegistersOfUser()
      │     └─ Filter to user's registers only
      ├─ MusterRollRepository.getMusterRoll(criteria)
      │  └─ MusterRollQueryBuilder.buildQuery(criteria)
      │     └─ Dynamic SQL with filters
      ├─ MusterRollRepository.getMusterRollCount(criteria)
      ├─ Apply limit/offset
      └─ Return MusterRollResponse with results & count
```

---

## Important Concepts

### V1 vs V2 Detection

The service **automatically detects flow version** based on `billingPeriodId`:

```java
if (StringUtils.isNotBlank(musterRoll.getBillingPeriodId())) {
    // V2 Flow: Period-based billing
    applyPeriodAwareDates(musterRollRequest);  // Calculate intersection
    validatePeriodNotLocked(...);               // Check if billed
    publishMusterRollStatusUpdateEvent(...);    // Send Kafka event
} else {
    // V1 Flow: Campaign-based billing (legacy)
    // Use register dates as-is
    // Update register.reviewStatus synchronously
}
```

**Key Differences:**

| Aspect | V1 | V2 |
|--------|----|----|
| **Dates** | Register dates | Intersection of register + period dates |
| **Uniqueness** | (register, startDate, endDate) | (register, billingPeriodId) |
| **Register Status Update** | Synchronous | Skip (1:many mapping) |
| **Status Communication** | Direct DB update | Kafka event |
| **Period Locking** | N/A | Enforced after billing |

### Period Locking (V2)

**Purpose:** Maintain data consistency across intermediate bills

```
Bill Generated for Period X
    ↓
Period X marked as "Billed"
    ↓
Next muster update for Period X → validatePeriodNotLocked()
    ↓
PERIOD_LOCKED exception raised
    ↓
User must void/cancel bill before editing
```

### Duplicate Muster Detection

```java
// V1: Check dates
getMusterRoll(registerId, startDate, endDate)

// V2: Check period
getMusterRoll(registerId, billingPeriodId)
```

---

## Database Migrations

Managed by **Flyway** with automatic version control.

**Migration Files** (`src/main/resources/db/migration/main/`):

| Version | Description |
|---------|-------------|
| V20221122121630 | Create muster_roll, attendance_summary, attendance_entries tables |
| V20221122133030 | Create performance indexes |
| V20230111101300 | Alter muster_roll table (add fields) |
| V20230119140800 | Alter attendance_summary table |
| V20230405154700 | Add columns to muster_roll_table |
| V20250201120000 | **[V2]** Add billing_period_id to muster_roll |
| V20250302120000 | **[V2]** Add unique constraint (register, period) |
| V20250312120000 | **[V2]** Add billing_period_id to attendance tables |

---

## Development Guidelines

### Building & Running

```bash
# Build
mvn -f backend/muster-roll/pom.xml clean install

# Run with tests
mvn -f backend/muster-roll/pom.xml spring-boot:run

# Skip tests
mvn -f backend/muster-roll/pom.xml clean install -DskipTests

# Run specific test
mvn -f backend/muster-roll/pom.xml test -Dtest=MusterRollServiceTest

# Run single test method
mvn -f backend/muster-roll/pom.xml test -Dtest=MusterRollServiceTest#testCreateMusterRoll
```

### Key Test Classes

- `MusterRollServiceTest` - Business logic tests
- `EnrichmentServiceTest` - Enrichment logic tests
- `MusterRollValidatorTest` - Validation tests
- `MusterRollApiControllerTest` - API tests

### Code Patterns

**Use Lombok for boilerplate:**
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusterRoll { ... }
```

**Repository Pattern:**
```java
// Query building
MusterRollQueryBuilder.buildQuery(criteria) → SQL String

// Execution
JDBC.executeQuery(sql) → ResultSet

// Mapping
MusterRollRowMapper.mapRow(rs) → Domain Object
```

**Service Injection:**
```java
@Autowired
public MusterRollService(
    MusterRollRepository repo,
    EnrichmentService enrichment,
    CalculationService calculation,
    // ... other dependencies
) { ... }
```

### Logging Best Practices

```java
@Slf4j  // Lombok annotation for SLF4J logger
public class MusterRollService {
    log.info("MusterRollService::createMusterRoll - Creating muster for register: {}", registerId);
    log.error("MusterRollService::createMusterRoll - Error: {}", e.getMessage(), e);
}
```

---

## Common Tasks

### Adding a New Field to Muster Roll

1. Update `MusterRoll` model class
2. Update `MusterRollRowMapper` to map from ResultSet
3. Update `MusterRollQueryBuilder` to include in SELECT/INSERT/UPDATE
4. Create Flyway migration to alter table
5. Update `muster-roll-persister.yml` if needed for async persistence
6. Add validation in `MusterRollValidator` if required
7. Update tests

### Adding a New Search Filter

1. Add field to `MusterRollSearchCriteria`
2. Update `MusterRollQueryBuilder.buildWhereClause()`
3. Update repository method signatures
4. Add validation in `MusterRollValidator`
5. Update API controller to accept new parameter
6. Update tests

### Adding an External Service Call

1. Create utility method in `MusterRollServiceUtil`
2. Add configuration properties in `application.properties`
3. Add config fields in `MusterRollServiceConfiguration`
4. Use `RestTemplate` to call service
5. Handle exceptions and logging
6. Add response validation and null checks
7. Add unit test with mock responses

---

## Troubleshooting

### Issue: "DUPLICATE_MUSTER_ROLL"

**Cause:** Muster with same register + dates (V1) or register + period (V2) already exists

**Solution:**
- V1: Check if muster exists for register within same date range
- V2: Check if muster exists for register in same billing period
- Delete duplicate or search existing

### Issue: "PERIOD_LOCKED"

**Cause:** Bill already generated for period/register, preventing edits (V2)

**Solution:**
- Void/cancel the bill before editing muster roll
- Or disable `musterroll.period.locking.enabled=false` if locking not needed

### Issue: "NO_DATE_INTERSECTION"

**Cause:** Register dates don't overlap with billing period dates (V2)

**Solution:**
- Create muster roll for register within a different billing period
- Verify register and period dates are correct

### Issue: "Muster roll not found" on update

**Cause:** Muster ID doesn't exist in database

**Solution:**
- Verify muster roll was created successfully
- Check database for muster record
- Verify tenant ID and muster ID are correct

---

## Performance Considerations

1. **Indexes:** Key fields (tenant, register, billing_period, status) are indexed
2. **Pagination:** Always use limit/offset for large result sets
3. **Caching:** MDMS and workflow responses should be cached by client
4. **Async Persistence:** Use Kafka topics for async DB writes (no blocking)
5. **Query Optimization:** QueryBuilder constructs efficient SQL with proper WHERE clauses

---

## References

- **DIGIT Framework:** https://github.com/egovernments/DIGIT-Works
- **Spring Boot 3.2 Docs:** https://spring.io/projects/spring-boot
- **Flyway Migrations:** https://flywaydb.org/
- **PostgreSQL JDBC:** https://jdbc.postgresql.org/

---

**Last Updated:** February 2025
**Maintained By:** DIGIT Works Team
