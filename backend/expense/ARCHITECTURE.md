# Expense Service Architecture

## Overview

The Expense Service is a Spring Boot 3.2.2 microservice written in Java 17 that manages bills and payments for the DIGIT-Works platform. It handles the creation, search, and management of various types of bills (wage bills, purchase bills, supervision bills) and their associated payments.

**Port:** 8099
**Module:** `expense`
**Version:** 2.0.0

## Service Purpose

The Expense Service is responsible for:
- Creating and managing bills for various business services (wage, purchase, supervision)
- Managing payment records linked to bills
- Workflow status transitions for bills and payments
- Integration with external services (MDMS, Workflow, HRMS, etc.)
- Publishing events to Kafka for asynchronous processing

## Technology Stack

- **Framework:** Spring Boot 3.2.2
- **Java Version:** Java 17
- **Database:** PostgreSQL 12
- **Migration Tool:** Flyway 9.22.3
- **Message Broker:** Kafka
- **Web Framework:** Spring Web MVC
- **Data Access:** Spring JDBC with custom QueryBuilder and RowMapper patterns
- **Build Tool:** Maven
- **Logging:** SLF4J with Lombok

## Core Dependencies

- **spring-boot-starter-web:** REST endpoint support
- **spring-boot-starter-jdbc:** Database connectivity
- **spring-boot-starter-validation:** Request validation
- **postgresql:** PostgreSQL JDBC driver
- **flyway-core:** Database schema versioning
- **mdms-client:** Master Data Management Service client
- **health-services-models:** Common data models
- **tracer:** Distributed tracing
- **services-common:** Shared utilities and configurations
- **jackson-datatype-jsr310:** Java Time API JSON serialization

## Architecture Components

### 1. Controllers

**Package:** `org.egov.digit.expense.web.controller`

#### BillController (`BillController.java`)
- **Endpoint Base:** `/bill/v1/`
- **Operations:**
  - `POST /_create` - Create a new bill
  - `POST /_search` - Search bills with filtering
  - `POST /_update` - Update existing bill

#### PaymentController (`PaymentController.java`)
- **Endpoint Base:** `/payment/v1/`
- **Operations:**
  - Payment creation and search endpoints
  - Payment status management

### 2. Services

**Package:** `org.egov.digit.expense.service`

#### BillService
Orchestrates bill operations:
- Validates bill requests using `BillValidator`
- Enriches bill data with IDs, timestamps, and audit details
- Manages workflow transitions via `WorkflowUtil`
- Persists bills to database via `BillRepository`
- Publishes bill events to Kafka topics
- Triggers notifications via `NotificationService`
- Applies business logic for different bill types

#### PaymentService
Manages payment operations:
- Validates payment requests
- Searches and retrieves payment records
- Updates payment status
- Handles payment notifications

#### WorkflowService
Integrates with egov-workflow-v2:
- Manages bill and payment workflow status transitions
- Validates state transitions
- Coordinates with external Workflow service

#### NotificationService
Sends notifications for bill and payment events:
- SMS notifications
- Push notifications
- Integration with notification service

### 3. Data Access Layer

**Package:** `org.egov.digit.expense.repository`

#### BillRepository
- Handles bill persistence and retrieval
- Uses `BillQueryBuilder` for dynamic SQL construction
- Uses `BillRowMapper` for result mapping

#### PaymentRepository
- Handles payment persistence and retrieval
- Uses `PaymentQueryBuilder` for dynamic SQL construction
- Uses `PaymentRowMapper` for result mapping

#### Query Builders
- `BillQueryBuilder` - Constructs dynamic SQL for bill queries with filters
- `PaymentQueryBuilder` - Constructs dynamic SQL for payment queries

#### Row Mappers
- `BillRowMapper` - Maps database result sets to Bill objects
- `PaymentRowMapper` - Maps database result sets to Payment objects

### 4. Kafka Integration

**Package:** `org.egov.digit.expense.kafka`

#### ExpenseProducer
Publishes events to Kafka topics:
- `expense-bill-create` - Bill creation events
- `expense-bill-update` - Bill update events
- `expense-payment-create` - Payment creation events
- `expense-payment-update` - Payment update events
- Handles tenant-specific topic naming

#### ExpenseConsumer
Listens to Kafka messages (currently disabled):
- Can be enabled for consuming messages from `kafka.topics.consumer`
- Placeholder for handling inbound events

### 5. Configuration & Utilities

**Package:** `org.egov.digit.expense.config` and `org.egov.digit.expense.util`

#### Configuration
- `Configuration.java` - Application configuration bean
- `MainConfiguration.java` - Main Spring configuration
- `Constants.java` - Application constants

#### Utilities
- **EnrichmentUtil** - Enriches domain objects (adds IDs, timestamps, audit details)
- **WorkflowUtil** - Handles workflow transitions and status management
- **IdgenUtil** - Generates unique IDs using egov-idgen service
- **MdmsUtil** - Fetches master data from MDMS service
- **NotificationUtil** - Prepares notification payloads
- **LocalizationUtil** - Manages localized messages
- **UserUtil** - User data retrieval and management
- **HRMSUtils** - HRMS integration for employee data
- **QueryBuilderUtils** - SQL query construction helpers
- **GenderUtil** - Gender code normalization
- **UrlShortenerUtil** - URL shortening integration
- **ResponseInfoFactory** - Creates standardized response envelopes

### 6. Validators

**Package:** `org.egov.digit.expense.web.validators`

- **BillValidator** - Validates bill requests (amount, dates, references)
- **PaymentValidator** - Validates payment requests and state transitions

### 7. Data Models

**Package:** `org.egov.digit.expense.web.models`

#### Domain Models
- **Bill** - Main bill entity with metadata and line items
- **BillDetail** - Detailed breakdown of a bill
- **LineItem** - Individual line items within bill details
- **Party** - Payer/payee information (could be individual or organization)
- **Payment** - Payment record linked to bills
- **PaymentBill** - Bill reference in payment
- **PaymentLineItem** - Line item in payment

#### Request/Response Models
- **BillRequest** - API request to create/update bills
- **BillResponse** - API response containing bills
- **BillSearchRequest** - Query parameters for bill search
- **BillCriteria** - Filter criteria for bills
- **PaymentRequest** - API request for payments
- **PaymentResponse** - API response containing payments
- **PaymentSearchRequest** - Query parameters for payment search
- **PaymentCriteria** - Filter criteria for payments

#### Support Models
- **Pagination** - Pagination info (limit, offset, sorting)
- **User** - User information
- **Role** - User role information
- **ErrorRes** - Error response wrapper
- **Error** - Error details
- **SMSRequest** - SMS notification request

#### Enums
- **Status** - Bill status (ACTIVE, INACTIVE)
- **PaymentStatus** - Payment status (INITIATED, PENDING, COMPLETED, REJECTED)
- **ReferenceStatus** - Reference document status
- **LineItemType** - Type of line item (DEDUCTION, EARNING)
- **ResponseStatus** - Response status codes
- **Order** - Sort order (ASC, DESC)

## Database Schema

### Tables

#### eg_expense_bill
Main bill records:
- **Primary Key:** (id, tenantid)
- **Key Fields:**
  - `id` - Unique bill identifier
  - `tenantId` - Multi-tenant identifier
  - `businessService` - Type: WAGES, PURCHASE, SUPERVISION
  - `referenceId` - ID of entity bill is for (e.g., register ID)
  - `billDate`, `dueDate` - Timestamps
  - `totalAmount`, `totalPaidAmount` - Financial amounts
  - `status` - ACTIVE, INACTIVE
  - `paymentStatus` - INITIATED, PENDING, COMPLETED, etc.
  - `billNumber` - Human-readable bill number (generated)
  - `fromPeriod`, `toPeriod` - Billing period timestamps
  - `additionalDetails` - JSONB for extensible data

#### eg_expense_billdetail
Breakdowns within bills:
- **Primary Key:** (id, tenantid)
- **Foreign Key:** References eg_expense_bill
- **Key Fields:**
  - `billId` - Parent bill reference
  - `referenceId` - External entity reference
  - `status`, `paymentStatus` - Status tracking
  - `fromPeriod`, `toPeriod` - Period details
  - `additionalDetails` - JSONB

#### eg_expense_lineitem
Individual line items:
- **Primary Key:** (id, tenantid)
- **Foreign Key:** References eg_expense_billdetail
- **Key Fields:**
  - `billDetailId` - Parent bill detail
  - `headCode` - Accounting/budget code
  - `amount`, `paidAmount` - Amounts
  - `type` - EARNING or DEDUCTION
  - `isLineItemPayable` - Payability flag
  - `paymentStatus` - Item-level payment status

#### eg_expense_party
Payer/payee information:
- **Primary Key:** (id, tenantid)
- **Key Fields:**
  - `type` - Party type (INDIVIDUAL, ORGANIZATION)
  - `identifier` - Party ID/reference
  - `parentId` - Parent bill or bill detail ID
  - `status` - Status tracking

#### eg_expense_payment
Payment records:
- Tracks payment against bills
- Stores payment amounts, dates, references
- Maintains payment status

## Service Dependencies

### Internal Dependencies
- **expense-calculator** - Computes bill amounts and line items
- **workflow** - Status transitions (egov-workflow-v2)
- **persister** - Kafka-to-database persistence
- **indexer** - Elasticsearch indexing (optional)
- **idgen** - ID generation service

### External Service Integrations

#### MDMS (Master Data Management)
- Endpoints:
  - `/egov-mdms-service/v1/_search` (v1)
  - `/mdms-v2/v1/_search` (v2)
- Fetches: Bill types, business service configurations, locales

#### Workflow Service (egov-workflow-v2)
- Endpoints:
  - `/egov-workflow-v2/egov-wf/process/_transition`
  - `/egov-workflow-v2/egov-wf/businessservice/_search`
  - `/egov-workflow-v2/egov-wf/process/_search`
- Manages workflow states and transitions

#### IDGen Service
- Generates: Wage bill numbers, purchase bill numbers, supervision bill numbers
- Format: WB/[fy]/[SEQ], PB/[fy]/[SEQ], SB/[fy]/[SEQ]

#### HRMS Service
- Endpoint: `/egov-hrms/employees/_search`
- Fetches employee data for wage bills

#### Organisation Service
- Endpoint: `/org-services/organisation/v1/_search`
- Fetches vendor and contractor information

#### Contract Service
- Endpoint: `/contract/v1/_search`
- Validates bill references against contracts

#### User Service
- Endpoints:
  - `/user/users/_createnovalidate`
  - `/user/users/_updatenovalidate`
  - `/user/users/_search`
- User management

#### Localization Service
- Endpoint: `/localization/messages/v1/_search`
- Provides localized messages

#### URL Shortener Service
- Endpoint: `/egov-url-shortening/shortener`
- Shortens URLs in notifications

#### Notification Service
- SMS notifications via `egov.core.notification.sms` topic
- Email and push notifications

## Configuration

### Application Properties

Key configurations in `application.properties`:

```properties
# Server
server.port=8099
server.contextPath=/expense

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/digit-works
spring.flyway.enabled=true
spring.flyway.locations=classpath:/db/migration/main

# Kafka
kafka.config.bootstrap_server_config=localhost:9092
kafka.topics.consumer=expense-billing-consumer-topic

# Service URLs
egov.mdms.host, egov.workflow.host, egov.idgen.host, etc.

# Billing Configuration
expense.v2.periodic.billing.enabled=true  # Enable v2 periodic billing
expense.billing.default.limit=100
expense.billing.search.max.limit=200

# Bill Number Generation
egov.idgen.works.wage.bill.number.format=WB/[fy:yyyy-yy]/[SEQ_WAGE_NUM]
egov.idgen.works.purchase.bill.number.format=PB/[fy:yyyy-yy]/[SEQ_PURCHASE_NUM]
egov.idgen.works.supervision.bill.number.format=SB/[fy:yyyy-yy]/[SEQ_SUPERVISION_NUM]
```

### Persister Configuration

Persister YAML files map Kafka topics to database operations:
- `expense-bill-payment-persister.yaml` - Bill and payment creation
- `expense-billarray-payment-persister.yaml` - Bulk bill operations

These define INSERT statements that persist data from Kafka messages to the database.

## Data Flow

### Bill Creation Flow
1. **API Request** → BillController._create()
2. **Validation** → BillValidator.validate()
3. **Enrichment** → EnrichmentUtil (adds ID, timestamps, audit)
4. **ID Generation** → IdgenUtil (generates bill number)
5. **Database** → BillRepository.insert()
6. **Workflow** → WorkflowUtil (creates workflow state)
7. **Kafka Event** → ExpenseProducer.push() to `expense-bill-create`
8. **Notification** → NotificationService (SMS/email)

### Bill Search Flow
1. **API Request** → BillController._search()
2. **Query Building** → BillQueryBuilder (dynamic SQL)
3. **Database** → BillRepository.search()
4. **Row Mapping** → BillRowMapper (ResultSet → Bill)
5. **Response** → Wrap in BillResponse with pagination

### Payment Flow
Similar to bill flow but:
- References existing bills
- Supports partial payments
- Updates bill payment status
- Publishes `expense-payment-create` and `expense-payment-update` events

## Multi-Tenancy

The service supports schema-based multi-tenancy:
- Each tenant uses separate database schema
- Queries are prefixed with schema placeholder and replaced at runtime via `MultiStateInstanceUtil`
- Tenant ID is required in all requests
- Kafka topics are prefixed with tenant ID

## API Endpoints

### Bill APIs
- `POST /bill/v1/_create` - Create bill
- `POST /bill/v1/_search` - Search bills
- `POST /bill/v1/_update` - Update bill

### Payment APIs
- `POST /payment/v1/_create` - Create payment
- `POST /payment/v1/_search` - Search payments
- `POST /payment/v1/_update` - Update payment

## Key Design Patterns

### QueryBuilder Pattern
Dynamic SQL construction with parameterized queries to prevent SQL injection.

```java
// Usage
List<Object> params = new ArrayList<>();
String query = queryBuilder.getPaymentQuery(searchRequest, params, false);
```

### RowMapper Pattern
Maps database ResultSet rows to domain objects.

```java
// Usage
List<Bill> bills = jdbcTemplate.query(query, rowMapper);
```

### Repository Pattern
Encapsulates data access logic.

```java
public List<Bill> search(BillSearchRequest request) {
    // Query construction and execution
}
```

### Service Orchestration
Services handle business logic and coordinate between repositories, validators, and Kafka.

```java
public BillResponse create(BillRequest request) {
    validator.validate(request);
    enrichmentUtil.enrich(request);
    billRepository.insert(bill);
    expenseProducer.push(...);
    notificationService.notify(...);
}
```

## Error Handling

- **Validation Errors** - BillValidator and PaymentValidator
- **Business Errors** - Service-level validation
- **Technical Errors** - Exception handling with `CustomException`
- **Response Format** - Standardized ErrorRes with error codes

## Workflow Integration

Bills and payments participate in configurable workflows:
- **Wage Bills** - Workflow enabled (business.workflow.status.map)
- **Purchase Bills** - Workflow disabled
- **Supervision Bills** - Workflow enabled

Workflow states control bill and payment status transitions.

## Testing

Test files located in `src/test/java`:
- `BillApiControllerTest` - Controller unit tests

## Building and Running

```bash
# Build
mvn -f backend/expense/pom.xml clean install

# Run
mvn -f backend/expense/pom.xml spring-boot:run

# Skip tests
mvn -f backend/expense/pom.xml clean install -DskipTests

# Run specific test
mvn -f backend/expense/pom.xml test -Dtest=BillApiControllerTest
```

## Health Check

Service provides Spring Boot health endpoint (if enabled in configuration).

## Related Services

- **Health Expense Calculator** - Computes bill amounts
- **Muster Roll** - Aggregates attendance for wage bills
- **Attendance Service** - Provides attendance data
- **Contracts Service** - Manages work orders
- **Organisation Service** - Manages vendors and contractors

## Key Files Reference

- **Controllers:** `web/controller/`
- **Services:** `service/`
- **Repositories:** `repository/`
- **Models:** `web/models/`
- **Validators:** `web/validators/`
- **Utilities:** `util/`
- **Database:** `src/main/resources/db/migration/main/`
- **Persister:** `src/main/resources/*-persister.yaml`
- **Configuration:** `src/main/resources/application.properties`

## Future Enhancements

- V2 periodic billing support (currently configurable via `expense.v2.periodic.billing.enabled`)
- Payment breakdown persistence optimization (via `bill.persistence.breakdown.enabled`)
- Additional bill types and workflows
- Enhanced payment reconciliation features