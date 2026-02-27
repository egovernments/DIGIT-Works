# Health Expense Calculator Service - Architecture

## Overview

The **Health Expense Calculator** is a Spring Boot 3.2.x microservice (Java 17) that computes bills and payments for health work programs. It processes muster rolls and attendance data to generate bills for wage labor, supervision, and purchase expenses. The service supports both V1 (immediate billing) and V2 (intermediate billing with periods) billing models.

**Service Name:** health-expense-calculator
**Port:** 8087
**Context Path:** /health-expense-calculator
**Version:** 2.0.0
**Build Tool:** Maven 3.x
**Database:** PostgreSQL 12+

---

## Service Responsibilities

1. **Bill Generation**
   - Wage bills (labor costs)
   - Supervision bills (oversight costs)
   - Purchase bills (material costs)

2. **Expense Calculation**
   - Compute labor charges based on muster rolls and worker rates
   - Calculate supervision charges from supervision registers
   - Process purchase expenses with bill details

3. **Billing Configuration Management (V2)**
   - Define billing frequency (weekly, bi-weekly, monthly, custom, end-of-campaign)
   - Manage billing periods for intermediate billing
   - Track billing period status (PENDING, PROCESSING, COMPLETED, BILLED)

4. **Report Generation**
   - Create health bill payment reports
   - Export bill data to Excel format
   - Generate PDF bills

5. **Integration**
   - Communicate with Muster Roll service for attendance data
   - Query Contract service for work contract details
   - Fetch Organization data for vendor/contractor information
   - Integrate with Workflow service for bill status transitions
   - Publish bills to Expense service via Kafka

---

## Core Components

### Controllers

**1. WorksCalculatorApiController** (`web/controllers/`)
- **Endpoint:** `POST /v1/_calculate`
- **Purpose:** Main calculation endpoint for bill generation
- **Request:** `CalculationRequest` containing project, muster rolls, and configuration
- **Response:** `BillResponse` with generated bills
- **Business Logic:** Routes requests to appropriate bill generator services

**2. BillingConfigApiController** (`web/controllers/`)
- **Endpoints:**
  - `POST /billing-config/v1/_create` - Create billing configuration
  - `POST /billing-config/v1/_search` - Search configurations
  - `POST /billing-config/v1/_update` - Update configuration
  - `POST /billing-period/v1/_search` - Search billing periods
- **Purpose:** Manages V2 intermediate billing configurations and periods
- **Key Operations:** Create/update configs, generate periods, track period status

**3. PurchaseBillApiController** (`web/controllers/`)
- **Purpose:** Handles purchase bill generation
- **Integration:** Routes to PurchaseBillGeneratorService

**4. HealthReportApiController** (`web/controllers/`)
- **Purpose:** Generates and serves health bill payment reports
- **Integration:** Works with HealthBillReportGenerator

### Services

**Core Services:**

1. **ExpenseCalculatorService** (`service/`)
   - Main orchestration service
   - Delegates to specialized bill generators
   - Manages validation and error handling
   - Integrates V1 and V2 billing logic via `BillingVersionHelper`
   - Handles workflow transitions

2. **WageSeekerBillGeneratorService** (`service/`)
   - Generates wage bills for labor
   - Calculates labor charges based on:
     - Muster roll attendance data
     - Worker rates (from MDMS)
     - Billing period (for V2 intermediate billing)
   - Creates bill with details for each beneficiary

3. **SupervisionBillGeneratorService** (`service/`)
   - Generates supervision expense bills
   - Calculates supervision charges
   - Tracks supervision registers

4. **PurchaseBillGeneratorService** (`service/`)
   - Generates purchase/material cost bills
   - Processes bill details
   - Applies purchase-specific business logic

5. **BillingConfigurationService** (`service/`)
   - Manages billing configurations (V2)
   - CRUD operations for billing configs
   - Handles configuration validation

6. **IntermediateBillingService** (`service/`)
   - V2 billing feature implementation
   - Handles period-aware bill generation
   - Manages billing period status transitions
   - Aggregates bills across multiple periods

7. **PeriodGenerationService** (`service/`)
   - Generates billing periods from configuration
   - Creates periods based on frequency rules
   - Validates period dates and constraints

8. **HealthBillReportGenerator** (`service/`)
   - Generates payment reports
   - Creates Excel/PDF exports
   - Formats report data with localization

9. **RedisService** (`service/`)
   - Caches frequently accessed data
   - Improves performance for repeated lookups
   - Stores temporary calculation state

### Repositories

**Database Access Layer:**

1. **ExpenseCalculatorRepository** (`repository/`)
   - Searches and retrieves bills
   - Queries muster rolls, projects, contracts
   - Uses custom row mappers for complex object mapping

2. **BillingConfigRepository** (`repository/`)
   - CRUD operations for billing configurations
   - Searches billing periods
   - Manages period status updates

3. **ServiceRequestRepository** (`repository/`)
   - Generic service request handling
   - Query building and execution

**Query Builders:**
- `ExpenseCalculatorQueryBuilder` - Dynamic SQL for bills/muster rolls
- `BillingConfigQueryBuilder` - Queries for configurations and periods

**Row Mappers:**
- `ExpenseCalculatorBillRowMapper`
- `ExpenseCalculatorMusterRowMapper`
- `BillingConfigRowMapper`
- `BillingPeriodRowMapper`
- `BillRowMapper`

### Utilities

**External Service Integration:**
- `MdmsUtils` - Master Data Management Service queries
- `ContractUtils` - Contract service integration
- `EstimateServiceUtil` - Estimate service queries
- `HRMSUtils` - Human Resource Management integration
- `AttendanceUtil` - Attendance service integration
- `BoundaryUtil` - Administrative boundary data

**Business Logic:**
- `ExpenseCalculatorServiceConstants` - Constants and enums
- `ExpenseCalculatorUtil` - Calculation helpers
- `BillUtils` - Bill creation and manipulation
- `BillingVersionHelper` - V1/V2 routing logic
- `BillingPeriodUtil` - Period calculation utilities
- `ProjectUtil` - Project data handling

**Cross-Cutting:**
- `ResponseInfoFactory` - Response formatting
- `IdgenUtil` - ID generation service integration
- `LocalizationUtil` - Multi-language support
- `PDFServiceUtil` - PDF generation
- `FileStoreUtil` - File storage integration
- `NotificationUtil` - Notification delivery
- `UserUtil` - User data handling
- `RegisterPermissionValidator` - Permission checks

### Validators

- `ExpenseCalculatorServiceValidator` - Main input validation
- `BillingConfigValidator` - Configuration validation
- `IntermediateBillingValidator` - V2 billing validation

### Kafka Integration

**Producer:** `ExpenseCalculatorProducer`
- Publishes generated bills to Expense service
- Topics:
  - `expense-bill-create-health` - New bill creation
  - `expense-bill-update-health` - Bill updates
  - `expense-bill-index-topic` - Indexing for search

**Consumer:** `ExpenseCalculatorConsumer`
- Consumes muster roll data
- Topic: `calculate-musterroll`
- Triggers automatic calculation when rolls are available

---

## Database Schema

### Primary Tables

#### eg_expense_billing_config
Stores billing configuration for intermediate billing (V2):
- **id** (VARCHAR 64, PK): Configuration identifier
- **tenant_id** (VARCHAR 64): Multi-tenant support
- **project_id** (VARCHAR 256): Associated project/campaign
- **billing_frequency** (VARCHAR 32): WEEKLY, BI_WEEKLY, MONTHLY, CUSTOM, END_OF_CAMPAIGN
- **custom_frequency_days** (INTEGER): Days for custom frequency (min 3)
- **project_start_date** (BIGINT): Campaign start (epoch ms)
- **project_end_date** (BIGINT): Campaign end (epoch ms)
- **status** (VARCHAR 32): ACTIVE, INACTIVE, COMPLETED
- **created_by/time**, **last_modified_by/time**: Audit fields
- **additional_details** (JSONB): Extra configuration data

**Indices:**
- Unique on (project_id, tenant_id)
- On (tenant_id), (status), (created_time)

#### eg_wms_billing_period
Stores individual billing periods generated from configs:
- **id** (VARCHAR 64, PK): Period identifier
- **tenant_id** (VARCHAR 64): Multi-tenant support
- **project_id** (VARCHAR 256): Associated project
- **billing_config_id** (VARCHAR 64, FK): Parent configuration
- **period_number** (INTEGER): Sequential (1, 2, 3...)
- **period_start_date** (BIGINT): Period start (epoch ms)
- **period_end_date** (BIGINT): Period end (epoch ms)
- **period_type** (VARCHAR 32): INTERMEDIATE or FINAL_AGGREGATE
- **status** (VARCHAR 32): PENDING, PROCESSING, COMPLETED, BILLED
- **bill_id** (VARCHAR 64): Links to generated bill
- **total_amount** (DECIMAL 12,2): Aggregated amount
- **beneficiary_count** (INTEGER): Count of beneficiaries
- **register_count** (INTEGER): Attendance registers processed
- **muster_roll_count** (INTEGER): Muster rolls generated
- **created_by/time**, **last_modified_by/time**: Audit fields

**Indices:**
- Unique on (project_id, period_number, tenant_id)
- On (billing_config_id), (status, project_id), (period_start_date, period_end_date), (bill_id)

#### eg_expense_calc_status
Tracks calculation status (from earlier migration):
- Status tracking for bill generation processes
- Used to handle calculation completion and errors

---

## API Endpoints

### Calculation Endpoints

**POST /v1/_calculate**
```
Request: CalculationRequest {
  requestInfo: RequestInfo,
  bills: [BillMetaRecords],
  calculationCriteria: [CalculatorSearchRequest]
}

Response: BillResponse {
  responseInfo: ResponseInfo,
  bills: [Bill],
  errors: [String]
}
```
Calculates bills based on muster rolls and configuration.

### Billing Configuration (V2)

**POST /billing-config/v1/_create**
- Creates a new billing configuration
- Returns: Configuration with generated ID

**POST /billing-config/v1/_search**
- Searches existing configurations
- Filters: project_id, tenant_id, status, date range
- Pagination support

**POST /billing-config/v1/_update**
- Updates configuration details
- Validates frequency and date constraints

### Billing Periods (V2)

**POST /billing-period/v1/_search**
- Searches billing periods
- Filters: config_id, status, period_type
- Returns periods with status and aggregated metrics

### Report Endpoints

**POST /v1/_reports**
- Generates health bill payment reports
- Returns: Report data with localization
- Supports Excel/PDF export

### Purchase Bill Endpoints

**POST /purchase/v1/_create**
- Creates purchase bills
- Processes material/procurement expenses

---

## Service Dependencies

### External Services (HTTP/REST)

| Service | Endpoint | Purpose |
|---------|----------|---------|
| **Muster Roll** | `/health-muster-roll/v1/_search` | Fetch attendance muster rolls |
| **Contract** | `/contract/v1/_search` | Validate work contracts |
| **Organization** | `/org-services/organisation/v1/_search` | Fetch vendor/contractor data |
| **Estimate** | `/estimate/v1/_search` | Validate estimates |
| **MDMS** | `/mdms-v2/v1/_search` | Master data (worker rates, config) |
| **HRMS** | `/egov-hrms/employees/_search` | Employee information |
| **Workflow** | `/egov-workflow-v2/egov-wf/process/_transition` | Bill status transitions |
| **Expense** | `/health-expense/bill/v1/_create/_update/_search` | Bill persistence |
| **Individual** | `/health-individual/v1/_search` | Individual (beneficiary) data |
| **Attendance** | `/health-attendance/v1/_search/_update` | Attendance registers |
| **IDGen** | `/egov-idgen/id/_generate` | Generate unique IDs |
| **Boundary** | `/boundary-service/boundary-relationships/_search` | Geographic boundaries |
| **Localization** | `/localization/messages/v1/_search` | Multi-language labels |
| **URL Shortener** | `/egov-url-shortening/shortener` | Shorten notification URLs |
| **PDF Service** | `/pdf-service/v1/_create` | Generate PDF bills |
| **FileStore** | `/filestore/v1/files` | Store generated files |

### Message Queue (Kafka)

**Consumer Topics:**
- `calculate-musterroll` - Muster roll data for calculation

**Producer Topics:**
- `expense-bill-create-health` - New bill events
- `expense-bill-update-health` - Bill update events
- `expense-bill-index-topic` - Elasticsearch indexing
- `bill-generation-async-topic` - Async bill generation
- `report-generation-trigger` - Trigger report generation
- `report-generation-retry-trigger` - Retry failed reports

### Cache (Redis)

- Caches muster rolls, worker rates, and configuration data
- Default TTL: 3600 seconds
- Improves performance for repeated lookups

---

## Configuration

### Application Properties

**Server:**
```properties
server.port=8087
server.contextPath=/health-expense-calculator
app.timezone=UTC
```

**Database (PostgreSQL):**
```properties
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/digit-works
spring.datasource.username=postgres

spring.flyway.locations=classpath:/db/migration/main
spring.flyway.enabled=true
```

**Kafka:**
```properties
kafka.config.bootstrap_server_config=localhost:9092
expense.calculator.consume.topic=calculate-musterroll
expense.calculator.create.topic=save-calculator
expense.billing.bill.create=expense-bill-create-health
expense.billing.bill.update=expense-bill-update-health
```

**Feature Flags:**
```properties
is.workflow.enabled=true
is.health.integration.enabled=true
is.attendance.approval.required=false

# V2 Intermediate Billing
billing.config.v2.enabled=true
billing.period.generation.enabled=true
billing.v2.batch.processing.enabled=false  # Keep disabled in production
```

**Business Rules:**
```properties
egov.works.expense.wage.head.code=WEG
egov.works.expense.wage.labour.charge.unit=day
egov.works.expense.wage.business.service=EXPENSE.WAGES
egov.works.expense.purchase.business.service=EXPENSE.PURCHASE
egov.works.expense.supervision.business.service=EXPENSE.SUPERVISION
```

**Search Limits:**
```properties
expense.billing.default.limit=100
expense.billing.search.max.limit=200
expense.bill.search.default.limit=100
expense.bill.search.max.limit=1000
```

### Environment Overrides

- `src/main/resources/env.development.local` - Local development overrides
- Supports host/port configuration for all dependent services

---

## Data Flow

### V1 Bill Generation Flow (Immediate)

```
CalculationRequest
    ↓
WorksCalculatorApiController.calculate()
    ↓
ExpenseCalculatorService.calculate()
    ↓
BillingVersionHelper (route to V1)
    ↓
[WageSeekerBillGeneratorService || SupervisionBillGeneratorService || PurchaseBillGeneratorService]
    ↓
Generate Bill
    ↓
ExpenseCalculatorProducer → Kafka (expense-bill-create-health)
    ↓
Workflow Transition
    ↓
BillResponse returned
```

### V2 Bill Generation Flow (Intermediate with Periods)

```
BillingConfig Request
    ↓
BillingConfigApiController._create()
    ↓
BillingConfigurationService.create()
    ↓
PeriodGenerationService.generatePeriods()
    ↓
Create billing periods (based on frequency)
    ↓
Store in eg_wms_billing_period

When billing period is selected:
CalculationRequest (with billingPeriodId)
    ↓
ExpenseCalculatorService.calculate()
    ↓
BillingVersionHelper (route to V2)
    ↓
IntermediateBillingService.generateBill()
    ↓
Filter muster rolls for period date range
    ↓
Generate bill for period
    ↓
Update period status → BILLED
    ↓
ExpenseCalculatorProducer → Kafka
    ↓
BillResponse returned
```

### Kafka Consumer Flow

```
Muster Roll Created/Updated
    ↓
Kafka Topic: calculate-musterroll
    ↓
ExpenseCalculatorConsumer
    ↓
Extract project & muster roll data
    ↓
ExpenseCalculatorService.calculate()
    ↓
Generate bills automatically (if configured)
    ↓
Publish to Kafka (expense-bill-create-health)
```

---

## Code Patterns

### JDBC with RowMapper Pattern
No JPA ORM. Uses Spring JDBC with custom `RowMapper` implementations:
```java
// Example
List<Bill> bills = jdbcTemplate.query(sql, new BillRowMapper());
```

### Dynamic SQL with QueryBuilder
```java
// Example
String query = queryBuilder.buildBillSearchQuery(criteria);
List<Bill> results = repository.execute(query);
```

### Request/Response Wrapper Pattern
All API requests/responses wrap data with `RequestInfo`/`ResponseInfo`:
```java
public class CalculationRequest {
    RequestInfo requestInfo;
    List<BillMetaRecords> bills;
}
```

### Separation of Concerns
- **Controllers:** HTTP handling, request routing
- **Services:** Business logic, orchestration
- **Repositories:** Data access
- **Utilities:** Cross-cutting concerns (integration, formatting)
- **Validators:** Input validation
- **Mappers:** Object transformation

### Async Bill Generation
- Kafka-based event streaming for async processing
- Retry topics for failed operations
- Redis caching for performance

---

## Key Configuration Properties for Development

```properties
# Local dev endpoints (override with env.development.local)
egov.mdms.host=http://localhost:8083
egov.musterroll.host=http://localhost:8084
egov.contract.service.host=http://localhost:8085/
egov.bill.host=http://localhost:8086
works.estimate.host=http://localhost:8288
egov.individual.host=http://localhost:8286
works.attendance.log.host=http://localhost:8088

# Feature management
billing.config.v2.enabled=true           # Enable V2 intermediate billing
billing.v2.batch.processing.enabled=false # Disable auto batch processing
report.generation.auto.enabled=true       # Enable auto report generation
bill.generation.async.enabled=true        # Enable async bill generation
is.workflow.enabled=true                  # Enable workflow integration
```

---

## Building and Running

### Build
```bash
mvn -f backend/health-expense-calculator/pom.xml clean install
```

### Run
```bash
mvn -f backend/health-expense-calculator/pom.xml spring-boot:run
```

### Skip Tests
```bash
mvn -f backend/health-expense-calculator/pom.xml clean install -DskipTests
```

### Run Tests
```bash
# All tests
mvn -f backend/health-expense-calculator/pom.xml test

# Single test class
mvn -f backend/health-expense-calculator/pom.xml test -Dtest=ExpenseCalculatorServiceTest

# Single test method
mvn -f backend/health-expense-calculator/pom.xml test -Dtest=ExpenseCalculatorServiceTest#testCalculateWageBill
```

### API Documentation
- Swagger/OpenAPI spec: `src/main/resources/health-calculator-service-1.0.0.yaml`
- Postman collection: `src/main/resources/Expense-Calculator.postman_collection.json`

---

## Development Notes

### Multi-Tenancy
- Schema-based multi-tenancy
- `tenant_id` field in all tables
- State-level tenant ID: `mz` (configurable)

### Localization
- Supports multiple languages
- Default locale: `en_MZ`
- Module name: `hcm-payments`

### Billing Periods (V2)
- Supports: WEEKLY (7 days), BI_WEEKLY (14 days), MONTHLY (30 days), CUSTOM (min 3 days), END_OF_CAMPAIGN
- Periods are generated from config on creation
- Each period can be billed independently
- Status progression: PENDING → PROCESSING → COMPLETED → BILLED

### Important: Batch Processing Disabled
- `billing.v2.batch.processing.enabled=false` should remain disabled in production
- Bills require manually approved muster rolls
- UI selects specific billing period for generation
- Batch mode would cause failures due to unapproved rolls

### Workflow Integration
- Bills require workflow state transitions
- Supported business services:
  - `EXPENSE.WAGES` - Wage bills
  - `EXPENSE.PURCHASE` - Purchase bills
  - `EXPENSE.SUPERVISION` - Supervision bills

---

## References

- **Parent Module:** DIGIT-Works backend
- **Related Services:** Expense, Muster Roll, Attendance, Contracts, Estimates
- **Common Library:** `works-services-common` (shared models and utilities)
- **Framework:** Spring Boot 3.2.2, Java 17
- **Database:** PostgreSQL 12+, Flyway migrations

---

*Last Updated: February 2025*
*Service Version: 2.0.0*