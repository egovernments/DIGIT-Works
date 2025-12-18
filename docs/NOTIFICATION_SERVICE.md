# Notification Service Documentation

## Table of Contents
1. [System & Architecture Overview](#system--architecture-overview)
2. [API Documentation](#api-documentation)
3. [Domain Models & Data Structures](#domain-models--data-structures)
4. [Database Design](#database-design)
5. [Configuration & Application Properties](#configuration--application-properties)
6. [Service Dependencies](#service-dependencies)
7. [Events & Messaging](#events--messaging)
8. [Execution & Business Flows](#execution--business-flows)
9. [Security Considerations](#security-considerations)
10. [API Flow Diagrams](#api-flow-diagrams)

## System & Architecture Overview

The Notification Service is a Spring Boot microservice that handles SMS notifications for the DIGIT Works platform. It supports multiple SMS gateway providers (CDAC, MSDG, Generic) and provides comprehensive SMS delivery management with callback handling, bounce tracking, and error handling.

```mermaid
graph TD
    A[Frontend/Services] -->|Kafka Events| B[Notification Consumer]
    B --> C[SMS Service Router]
    C --> D{Provider Type}
    
    D -->|CDAC| E[CDAC SMS Service]
    D -->|MSDG| F[MSDG SMS Service] 
    D -->|Generic| G[Generic SMS Service]
    D -->|Console| H[Console SMS Service]
    
    E --> I[CDAC Gateway API]
    F --> J[MSDG Gateway API]
    G --> K[Generic Gateway API]
    H --> L[Console Output]
    
    I --> M[SMS Provider Response]
    J --> M
    K --> M
    
    N[SMS Gateway Callbacks] --> O[Callback API Controller]
    O --> P[Bounce/Status Tracker]
    P --> Q[Kafka Topics]
    
    R[MDMS Service] --> S[Template Management]
    T[Kafka Producer] --> U[Error/Bounce Topics]
```

### Core Components

- **SMS Service Router**: Provider-specific SMS routing logic
- **Multiple Provider Support**: CDAC, MSDG, Generic HTTP, Console
- **Callback API**: SMS delivery status and bounce handling
- **Template Management**: Dynamic SMS content from MDMS
- **Kafka Integration**: Event-driven SMS processing
- **Error Handling**: Dead letter queue and retry mechanisms

## API Documentation

### Base URL: `/notification-sms`

#### 1. SMS Callback API
- **Endpoint**: `GET/POST /smsbounce/callback`
- **Description**: Receives delivery status callbacks from SMS gateways
- **Authentication**: Provider-specific callback authentication

**Query Parameters**:
- `userId`: User identifier
- `jobno`: SMS job number from provider
- `mobilenumber`: Target mobile number
- `status`: Delivery status code (0-11)
- `DoneTime`: Delivery timestamp
- `messagepart`: Message content part
- `sender_name`: Sender identifier

**Response**:
```json
{
  "status": "success",
  "message": "Status successfully sent"
}
```

### SMS Processing (Kafka-driven)

The service primarily operates through Kafka event consumption rather than direct REST APIs.

#### Kafka Event Schema
```json
{
  "message": "Your project PJ/2023-24/000001 has been approved",
  "mobileNumber": "+919876543210",
  "templateId": "PROJECT_APPROVAL",
  "tenantId": "od.testing",
  "additionalFields": {
    "requestInfo": {...},
    "templateData": {
      "projectNumber": "PJ/2023-24/000001",
      "applicantName": "John Doe"
    }
  }
}
```

## Domain Models & Data Structures

### Core Entities

#### SMSRequest
```java
public class SMSRequest {
    private RequestInfo RequestInfo;
    private String message;
    private String mobileNumber;
    private String templateId;
    private String tenantId;
    private Category category;
    private Long expiryTime;
    private Map<String, Object> additionalFields;
}
```

#### Sms
```java
public class Sms {
    private String mobileNumber;
    private String message;
    private Category category;
    private Long expiryTime;
    private String templateId;
    private Map<String, String> templateData;
    
    public boolean isValid() {
        return mobileNumber != null && message != null;
    }
}
```

#### Report (Callback)
```java
public class Report {
    private String jobno;
    private Integer messagestatus;
    private String DoneTime;
    private String usernameHash;
}
```

#### SMS Provider Configuration
```java
public class SMSProperties {
    private String providerClass;
    private String requestType;
    private String url;
    private String contentType;
    private String username;
    private String password;
    private String senderId;
    private String departmentId;
    private List<String> blacklistNumbers;
    private List<String> whitelistNumbers;
    private List<Integer> successCodes;
    private List<Integer> errorCodes;
    private Map<String, String> configMap;
    private Map<String, Object> categoryMap;
}
```

### Provider-Specific Implementations

#### CDAC SMS Service
- **Provider**: Government of Odisha CDAC SMS Gateway
- **Authentication**: Username/password based
- **API**: REST POST with JSON payload
- **Features**: Delivery reports, template mapping

#### MSDG SMS Service
- **Provider**: Mobile Service Delivery Gateway
- **Authentication**: API key based
- **API**: REST POST with form-data
- **Features**: Departmental SMS, template validation

#### Generic SMS Service
- **Provider**: Configurable HTTP gateway
- **Authentication**: Configurable
- **API**: Flexible HTTP methods and content types
- **Features**: Universal provider support

### Validation Rules

- **Mobile Number**: Must match pattern `^[6-9][0-9]{9}$`
- **Message**: Cannot be null or empty
- **Status Codes**: Callback status must be 0-11
- **Blacklist/Whitelist**: Number filtering based on configuration
- **Template**: Valid template ID from MDMS (if used)

## Database Design

### Kafka-Based Architecture

The Notification Service is primarily stateless and uses Kafka for data persistence and event tracking.

#### Message Flow Architecture
```mermaid
erDiagram
    KAFKA_MESSAGE ||--o{ SMS_REQUEST : contains
    SMS_REQUEST ||--|| SMS_PROCESSING : triggers
    SMS_PROCESSING ||--o{ PROVIDER_CALL : makes
    PROVIDER_CALL ||--o{ CALLBACK_REPORT : generates
    CALLBACK_REPORT ||--|| BOUNCE_TRACKING : creates
    
    KAFKA_MESSAGE {
        string topic
        string key
        json value
        long timestamp
        int partition
    }
    
    SMS_REQUEST {
        string mobileNumber
        string message
        string templateId
        string tenantId
        json additionalFields
        long expiryTime
    }
    
    CALLBACK_REPORT {
        string jobno
        int messagestatus
        string DoneTime
        string usernameHash
    }
```

### Kafka Topics Structure

#### Input Topics
- `egov.core.notification.sms`: General DIGIT SMS notifications
- `mukta.notification.sms`: MUKTA-specific SMS notifications
- `works.notification.sms`: Works-specific SMS notifications

#### Output Topics
- `egov.core.notification.sms.bounce`: SMS bounce and delivery reports
- `egov.core.sms.expiry`: Expired SMS tracking
- `egov.core.sms.error`: SMS processing errors
- `notification-sms-deadletter`: Failed message handling

## Configuration & Application Properties

### Server Configuration
```properties
server.servlet.context-path=/notification-sms
server.context.path=/notification-sms
server.port=8095
spring.main.web-environment=false
```

### SMS Provider Configuration
```properties
# Provider Selection
sms.provider.class=CDAC
sms.provider.requestType=POST
sms.provider.url=https://govtsms.odisha.gov.in/api/api.php
sms.provider.contentType=application/json

# Provider Credentials
sms.provider.username=username
sms.provider.password=password
sms.senderid=ODIGOV
sms.departmentid=D001002

# Provider Behavior
sms.verify.response=true
sms.print.response=true
sms.verify.responseContains="success":true
sms.verify.ssl=true
sms.url.dont_encode_url=true
```

### Number Management
```properties
# Filtering
sms.blacklist.numbers=9999X,5*
sms.whitelist.numbers=
sms.mobile.prefix=

# Response Codes
sms.success.codes=200,201,202
sms.error.codes=
```

### Template and Content Mapping
```properties
# Dynamic Configuration Maps
sms.config.map={'action':'$action', 'source':'$source', 'department_id':'$department_id', 'template_id':'$template_id', 'sms_content': '$smsContent' , 'phonenumber': '$phonenumber'}
sms.category.map={'mtype': {'*': 'abc', 'OTP': 'def'}}
sms.extra.config.map={'extraParam': 'abc'}
```

### Kafka Configuration
```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=sms
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.session-timeout-ms-config=15000

# Topic Names
kafka.topics.notification.sms.name=egov.core.notification.sms
kafka.topics.mukta.notification.sms.name=mukta.notification.sms
kafka.topics.works.notification.sms.id=notification.sms
kafka.topics.sms.bounce=egov.core.notification.sms.bounce
kafka.topics.error.sms=egov.core.sms.error
```

### MDMS Integration
```properties
egov.mdms.host=http://localhost:8080
egov.mdms.search.endpoint=/egov-mdms-service/v1/_search
```

## Service Dependencies

### Internal DIGIT Services

1. **MDMS Service** (`egov.mdms.host`)
   - **Purpose**: SMS template management and configuration
   - **APIs Used**: `/egov-mdms-service/v1/_search`
   - **Usage**: Fetch SMS templates, tenant configurations

2. **All Works Services**
   - **Purpose**: SMS notification triggers
   - **Integration**: Through Kafka topics
   - **Usage**: Receive notification events from various services

### External Dependencies

1. **SMS Gateway Providers**
   - **CDAC Gateway**: Government SMS service
   - **MSDG Gateway**: Mobile service delivery
   - **Generic HTTP**: Third-party SMS providers
   - **Usage**: Actual SMS delivery

2. **Kafka Message Broker**
   - **Purpose**: Event-driven SMS processing
   - **Topics**: Multiple notification and tracking topics
   - **Usage**: Asynchronous SMS processing pipeline

3. **Hash Service**
   - **Purpose**: Mobile number hashing for privacy
   - **Usage**: Secure mobile number storage in callbacks

## Events & Messaging

### SMS Processing Flow

#### Input Events
```json
{
  "RequestInfo": {...},
  "message": "Your application {applicationNumber} has been {status}",
  "mobileNumber": "+919876543210", 
  "templateId": "APPLICATION_STATUS",
  "tenantId": "od.testing",
  "category": "NOTIFICATION",
  "additionalFields": {
    "templateData": {
      "applicationNumber": "AP/2023/000001",
      "status": "approved"
    }
  }
}
```

#### Output Events
```json
{
  "jobno": "JOB123456",
  "messagestatus": 1,
  "DoneTime": "2023-01-01 10:30:00",
  "usernameHash": "hashed-mobile-number"
}
```

### Event Processing Patterns

#### SMS Send Flow
```mermaid
sequenceDiagram
    participant SourceService
    participant Kafka
    participant SMSConsumer
    participant SMSService
    participant SMSProvider
    participant CallbackAPI

    SourceService->>Kafka: Publish SMS Request
    Kafka->>SMSConsumer: Consume SMS Event
    SMSConsumer->>SMSService: Process SMS
    SMSService->>SMSService: Validate & Route
    SMSService->>SMSProvider: Send SMS
    SMSProvider-->>SMSService: Delivery Response
    SMSProvider->>CallbackAPI: Delivery Callback
    CallbackAPI->>Kafka: Publish Bounce Report
```

## Execution & Business Flows

### 1. SMS Processing Flow

```mermaid
flowchart TD
    A[SMS Event Received] --> B{Validate SMS Request}
    B -->|Invalid| C[Send to Error Topic]
    B -->|Valid| D{Check Mobile Number}
    D -->|Blacklisted| E[Log and Ignore]
    D -->|Not Whitelisted| E
    D -->|Valid| F[Select SMS Provider]
    F --> G{Provider Type}
    G -->|CDAC| H[CDAC Implementation]
    G -->|MSDG| I[MSDG Implementation]
    G -->|Generic| J[Generic Implementation]
    G -->|Console| K[Console Output]
    
    H --> L[Build CDAC Request]
    I --> M[Build MSDG Request]
    J --> N[Build Generic Request]
    
    L --> O[Send to Provider]
    M --> O
    N --> O
    
    O --> P{Response Successful?}
    P -->|No| Q[Send to Error Topic]
    P -->|Yes| R[Log Success]
    K --> R
```

### 2. Provider Selection Flow

```mermaid
flowchart TD
    A[SMS Request] --> B[Read Provider Configuration]
    B --> C{Provider Class}
    C -->|CDAC| D[Initialize CDAC Service]
    C -->|MSDG| E[Initialize MSDG Service]
    C -->|Generic| F[Initialize Generic Service]
    C -->|Console| G[Initialize Console Service]
    
    D --> H[Apply CDAC Config]
    E --> I[Apply MSDG Config]
    F --> J[Apply Generic Config]
    G --> K[Console Configuration]
    
    H --> L[Execute SMS Send]
    I --> L
    J --> L
    K --> L
```

### 3. Callback Processing Flow

```mermaid
flowchart TD
    A[Callback Received] --> B{Validate Parameters}
    B -->|Invalid| C[Return Bad Request]
    B -->|Valid| D[Validate Mobile Number]
    D -->|Invalid| C
    D -->|Valid| E[Validate Status Code]
    E -->|Invalid| C
    E -->|Valid| F[Hash Mobile Number]
    F --> G[Create Report Object]
    G --> H[Publish to Bounce Topic]
    H --> I[Return Success]
```

### 4. Template Processing Flow

```mermaid
flowchart TD
    A[SMS with Template] --> B[Fetch from MDMS]
    B --> C{Template Found?}
    C -->|No| D[Use Raw Message]
    C -->|Yes| E[Extract Template Data]
    E --> F[Replace Placeholders]
    F --> G[Build Final Message]
    G --> H[Send SMS]
    D --> H
```

## Security Considerations

### Authentication & Authorization

1. **Provider Authentication**
   - SMS gateway credentials securely configured
   - API key management for different providers
   - SSL/TLS encryption for provider communication

2. **Callback Security**
   - Provider IP whitelisting (if supported)
   - Signature validation for callbacks
   - Rate limiting on callback endpoints

3. **Data Protection**
   - Mobile number hashing in bounce reports
   - No storage of sensitive SMS content
   - Secure credential management

### Input Validation

1. **Mobile Number Validation**
   - Regex pattern validation for Indian mobile numbers
   - Blacklist/whitelist number filtering
   - International number format support

2. **Message Content Validation**
   - Content length limits based on provider
   - Character encoding validation
   - Template injection prevention

3. **Provider Response Validation**
   - Expected response code validation
   - Response content verification
   - Error code handling

### Privacy & Compliance

1. **Data Minimization**
   - No persistent storage of SMS content
   - Mobile number hashing for tracking
   - Minimal log retention

2. **Audit Trail**
   - SMS delivery status tracking
   - Provider response logging
   - Error tracking and alerting

## API Flow Diagrams

### 1. SMS Send Process Flow

```mermaid
flowchart TD
    A[Kafka SMS Event] --> B[SMS Consumer]
    B --> C[Request Validation]
    C --> D[Provider Router]
    D --> E{Provider Selection}
    E -->|CDAC| F[CDAC Service]
    E -->|MSDG| G[MSDG Service]
    E -->|Generic| H[Generic Service]
    
    F --> I[Build CDAC Payload]
    G --> J[Build MSDG Payload]
    H --> K[Build Generic Payload]
    
    I --> L[HTTP POST to Provider]
    J --> L
    K --> L
    
    L --> M{Success Response?}
    M -->|Yes| N[Log Success]
    M -->|No| O[Error Handler]
    O --> P[Dead Letter Queue]
    N --> Q[Process Complete]
```

### 2. Callback Processing Flow

```mermaid
sequenceDiagram
    participant Provider
    participant CallbackAPI
    participant HashService
    participant KafkaProducer
    participant BounceConsumer

    Provider->>CallbackAPI: POST /smsbounce/callback
    CallbackAPI->>CallbackAPI: Validate Parameters
    CallbackAPI->>HashService: Hash Mobile Number
    HashService-->>CallbackAPI: Hashed Value
    CallbackAPI->>KafkaProducer: Publish Bounce Report
    CallbackAPI-->>Provider: Success Response
    
    KafkaProducer->>BounceConsumer: Bounce Event
    BounceConsumer->>BounceConsumer: Process Bounce Data
```

### 3. Template Resolution Flow

```mermaid
flowchart TD
    A[SMS with Template ID] --> B[Check MDMS Cache]
    B --> C{Template in Cache?}
    C -->|Yes| D[Use Cached Template]
    C -->|No| E[Fetch from MDMS]
    E --> F[Cache Template]
    F --> D
    D --> G[Extract Placeholder Variables]
    G --> H[Replace with Actual Values]
    H --> I[Build Final Message]
    I --> J[Send to Provider]
```

### 4. Error Handling Flow

```mermaid
flowchart TD
    A[SMS Processing Error] --> B{Error Type}
    B -->|Validation Error| C[Log Error]
    B -->|Provider Error| D[Retry Logic]
    B -->|System Error| E[Dead Letter Queue]
    
    C --> F[Send to Error Topic]
    D --> G{Retry Attempts < Max?}
    G -->|Yes| H[Exponential Backoff]
    G -->|No| E
    H --> I[Retry Send]
    I --> J{Success?}
    J -->|No| G
    J -->|Yes| K[Success Logging]
    E --> L[Manual Investigation]
    F --> L
```

This comprehensive documentation provides detailed insights into the Notification Service's multi-provider SMS architecture, Kafka-driven processing, callback handling, and secure SMS delivery management for DIGIT Works platform.