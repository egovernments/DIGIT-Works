# User OTP Service Documentation

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

The User OTP Service is a Spring Boot microservice that manages One-Time Password (OTP) generation, delivery, and verification for user authentication in the DIGIT Works platform. It supports multiple delivery channels (SMS, Email) and handles different OTP types for registration, login, and password reset scenarios.

```mermaid
graph TD
    A[Frontend Applications] -->|REST API| B[OTP Controller]
    B --> C[OTP Service]
    C --> D{OTP Request Type}
    
    D -->|Registration| E[User Registration Flow]
    D -->|Login| F[User Login Flow]
    D -->|Password Reset| G[Password Reset Flow]
    
    E --> H[Validate New User]
    F --> I[Validate Existing User]
    G --> J[Fetch User Details]
    
    H --> K[Generate OTP]
    I --> K
    J --> K
    
    K --> L[OTP Repository]
    L --> M[External OTP Service]
    
    K --> N[SMS Repository]
    K --> O[Email Repository]
    
    N --> P[SMS Notification Service]
    O --> Q[Email Notification Service]
    
    R[User Service] --> S[User Validation]
    T[Localization Service] --> U[Message Templates]
```

### Core Components

- **OTP Controller**: REST endpoints for OTP operations
- **OTP Service**: Business logic for different OTP flows
- **OTP Repository**: OTP generation and external service integration
- **SMS/Email Repositories**: Multi-channel notification delivery
- **User Integration**: User validation and lookup
- **Localization Support**: Multi-language OTP messages

## API Documentation

### Base URL: `/user-otp`

#### 1. Send OTP
- **Endpoint**: `POST /v1/_send`
- **Description**: Generates and sends OTP for authentication scenarios
- **Authentication**: Public endpoint for registration, validated for other operations

**Request Body**:
```json
{
  "RequestInfo": {
    "apiId": "user-otp",
    "ver": "1.0",
    "ts": 1234567890,
    "action": "send",
    "did": "1",
    "key": "abcd-efgh",
    "msgId": "send otp request",
    "authToken": "{{token}}"
  },
  "otp": {
    "mobileNumber": "9876543210",
    "tenantId": "od.testing",
    "type": "login",
    "userType": "CITIZEN"
  }
}
```

**Response**:
```json
{
  "ResponseInfo": {
    "apiId": "user-otp",
    "ver": "1.0",
    "ts": 1234567890,
    "resMsgId": "uief87324",
    "msgId": "send otp request",
    "status": "successful"
  },
  "successful": true
}
```

### OTP Types Supported

#### 1. Registration OTP
- **Type**: `register`
- **Purpose**: New user account creation
- **Validation**: Ensures user doesn't already exist
- **Delivery**: SMS only (no email for new users)

#### 2. Login OTP
- **Type**: `login`
- **Purpose**: Existing user authentication
- **Validation**: Ensures user exists in system
- **Delivery**: SMS + Email (if configured)

#### 3. Password Reset OTP
- **Type**: `passwordreset`
- **Purpose**: Password recovery for existing users
- **Validation**: Validates user existence and mobile number
- **Delivery**: SMS + Email

### Error Handling

All APIs follow standard error response format:

```json
{
  "ResponseInfo": {
    "apiId": "user-otp",
    "ver": "1.0",
    "ts": 1234567890,
    "resMsgId": "uief87324",
    "msgId": "send otp request",
    "status": "failed"
  },
  "Errors": [
    {
      "code": "USER_ALREADY_EXISTS",
      "message": "User already exists in system",
      "description": "Cannot register existing user"
    }
  ]
}
```

## Domain Models & Data Structures

### Core Entities

#### OtpRequest (Web Contract)
```java
public class OtpRequest {
    private RequestInfo requestInfo;
    private Otp otp;
    
    public org.egov.domain.model.OtpRequest toDomain() {
        return org.egov.domain.model.OtpRequest.builder()
            .mobileNumber(getMobileNumber())
            .tenantId(getTenantId())
            .type(getType())
            .userType(getUserType())
            .requestInfo(getRequestInfo())
            .build();
    }
}
```

#### Otp (Web Model)
```java
public class Otp {
    private String mobileNumber;
    private String tenantId;
    private String type;
    private String userType;
    
    public OtpRequestType getTypeOrDefault() {
        return type != null ? 
            OtpRequestType.fromValue(type) : 
            OtpRequestType.REGISTER;
    }
}
```

#### OtpRequest (Domain Model)
```java
public class OtpRequest {
    private String mobileNumber;
    private String tenantId;
    private OtpRequestType type;
    private String userType;
    private RequestInfo requestInfo;
    
    public void validate() {
        // Validation logic for required fields
    }
    
    public boolean isRegistrationRequestType() {
        return type == OtpRequestType.REGISTER;
    }
    
    public boolean isLoginRequestType() {
        return type == OtpRequestType.LOGIN;
    }
}
```

#### User (Domain Model)
```java
public class User {
    private String uuid;
    private String userName;
    private String name;
    private String mobileNumber;
    private String email;
    private String type;
    private String tenantId;
}
```

### OTP Types and Flows

#### OtpRequestType Enum
```java
public enum OtpRequestType {
    REGISTER("register"),
    LOGIN("login"),
    PASSWORDRESET("passwordreset");
    
    private final String value;
    
    public static OtpRequestType fromValue(String value) {
        // Implementation for string to enum conversion
    }
}
```

### Validation Rules

- **Mobile Number**: Must be 10 digits, can start with 6-9
- **Tenant ID**: Must be valid as per system configuration
- **User Type**: Must be valid user type (CITIZEN, EMPLOYEE, etc.)
- **Registration**: User must not exist for registration OTP
- **Login/Reset**: User must exist for login/password reset OTP

## Database Design

### External Service Integration

The User OTP service is stateless and integrates with external services for OTP generation and storage.

#### Service Integration Architecture
```mermaid
erDiagram
    OTP_REQUEST ||--|| OTP_GENERATION : triggers
    OTP_GENERATION ||--|| EXTERNAL_OTP_SERVICE : calls
    OTP_REQUEST ||--|| USER_VALIDATION : requires
    USER_VALIDATION ||--|| USER_SERVICE : calls
    OTP_GENERATION ||--o{ NOTIFICATION_CHANNELS : sends
    
    OTP_REQUEST {
        string mobileNumber
        string tenantId
        string type
        string userType
        requestInfo requestInfo
    }
    
    EXTERNAL_OTP_SERVICE {
        string otpNumber
        long expiryTime
        string requestId
    }
    
    USER_SERVICE {
        string uuid
        string mobileNumber
        string email
        string userType
        string tenantId
    }
    
    NOTIFICATION_CHANNELS {
        string channel
        string content
        string recipient
        string status
    }
```

### Data Flow Architecture

```mermaid
flowchart TD
    A[OTP Request] --> B[User Service Lookup]
    B --> C[External OTP Service]
    C --> D[OTP Generation]
    D --> E[SMS Repository]
    D --> F[Email Repository]
    E --> G[SMS Kafka Topic]
    F --> H[Email Kafka Topic]
    G --> I[SMS Notification Service]
    H --> J[Email Notification Service]
```

## Configuration & Application Properties

### Server Configuration
```properties
server.port=8080
server.contextPath=/user-otp
server.servlet.context-path=/user-otp
```

### External Service URLs
```properties
# OTP Generation Service
otp.host=http://localhost:8089/otp
otp.create.url=/v1/_create

# User Service
user.host=http://localhost:8081/
search.user.url=/user/_search

# Localization Service
egov.localisation.host=https://dev.digit.org
egov.localisation.search.endpoint=/localization/messages/v1/_search
egov.localisation.tenantid.strip.suffix.count=1
```

### Kafka Configuration
```properties
spring.kafka.bootstrap.servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

# Notification Topics
sms.topic=egov.core.notification.sms.otp
email.topic=egov.core.notification.email
works.notification.sms.topic=works.notification.sms
```

### Business Configuration
```properties
# OTP Configuration
expiry.time.for.otp=3000

# Notification Configuration
sms.isAdditonalFieldRequired=true
```

## Service Dependencies

### Internal DIGIT Services

1. **External OTP Service** (`otp.host`)
   - **Purpose**: Generate and validate OTP numbers
   - **APIs Used**: `/v1/_create`
   - **Usage**: OTP generation with expiry management

2. **User Service** (`user.host`)
   - **Purpose**: User validation and information lookup
   - **APIs Used**: `/user/_search`
   - **Usage**: Validate user existence for different OTP flows

3. **Localization Service** (`egov.localisation.host`)
   - **Purpose**: Multi-language OTP message templates
   - **APIs Used**: `/localization/messages/v1/_search`
   - **Usage**: Localized OTP SMS and email content

4. **Notification Services**
   - **SMS Notification**: Through Kafka topics
   - **Email Notification**: Through Kafka topics
   - **Usage**: Multi-channel OTP delivery

### External Dependencies

1. **Kafka Message Broker**
   - **Purpose**: Asynchronous notification delivery
   - **Topics**: `egov.core.notification.sms.otp`, `egov.core.notification.email`
   - **Usage**: Decoupled notification processing

## Events & Messaging

### Notification Events

#### SMS Notification Event
```json
{
  "message": "Your OTP for login is: 123456. Valid for 5 minutes.",
  "mobileNumber": "9876543210",
  "category": "OTP",
  "expiryTime": 1234567890,
  "additionalFields": {
    "templateCode": "OTP_LOGIN",
    "requestInfo": {...},
    "tenantId": "od.testing"
  }
}
```

#### Email Notification Event
```json
{
  "email": {
    "emailTo": ["user@example.com"],
    "subject": "OTP for Account Verification",
    "body": "Your OTP is: 123456. Please use this to complete verification.",
    "isHTML": false
  },
  "requestInfo": {...}
}
```

### Event Processing Patterns

#### OTP Send Flow
```mermaid
sequenceDiagram
    participant Client
    participant OTPService
    participant UserService
    participant OTPGenerator
    participant SMSKafka
    participant EmailKafka
    participant NotificationService

    Client->>OTPService: POST /v1/_send
    OTPService->>UserService: Validate User
    UserService-->>OTPService: User Details
    OTPService->>OTPGenerator: Generate OTP
    OTPGenerator-->>OTPService: OTP Number
    OTPService->>SMSKafka: Publish SMS Event
    OTPService->>EmailKafka: Publish Email Event
    OTPService-->>Client: Success Response
    
    SMSKafka->>NotificationService: SMS Delivery
    EmailKafka->>NotificationService: Email Delivery
```

## Execution & Business Flows

### 1. User Registration OTP Flow

```mermaid
flowchart TD
    A[Registration OTP Request] --> B{Validate Request}
    B -->|Invalid| C[Return Validation Error]
    B -->|Valid| D[Check User Existence]
    D --> E{User Exists?}
    E -->|Yes| F[Return User Already Exists Error]
    E -->|No| G[Generate OTP]
    G --> H[Store OTP in External Service]
    H --> I[Send SMS Notification]
    I --> J[Log Success]
    J --> K[Return Success Response]
```

### 2. User Login OTP Flow

```mermaid
flowchart TD
    A[Login OTP Request] --> B{Validate Request}
    B -->|Invalid| C[Return Validation Error]
    B -->|Valid| D[Fetch User Details]
    D --> E{User Found?}
    E -->|No| F[Return User Not Found Error]
    E -->|Yes| G[Generate OTP]
    G --> H[Store OTP in External Service]
    H --> I[Send SMS Notification]
    I --> J{User Has Email?}
    J -->|Yes| K[Send Email Notification]
    J -->|No| L[Skip Email]
    K --> M[Return Success Response]
    L --> M
```

### 3. Password Reset OTP Flow

```mermaid
flowchart TD
    A[Password Reset Request] --> B{Validate Request}
    B -->|Invalid| C[Return Validation Error]
    B -->|Valid| D[Fetch User by Mobile]
    D --> E{User Found?}
    E -->|No| F[Return User Not Found Error]
    E -->|Yes| G{Mobile Number Valid?}
    G -->|No| H[Return Mobile Not Found Error]
    G -->|Yes| I[Generate OTP]
    I --> J[Store OTP in External Service]
    J --> K[Send SMS to User Mobile]
    K --> L{User Has Email?}
    L -->|Yes| M[Send Email Notification]
    L -->|No| N[Skip Email]
    M --> O[Return Success Response]
    N --> O
```

### 4. Multi-Channel Notification Flow

```mermaid
flowchart TD
    A[OTP Generated] --> B[SMS Repository]
    A --> C[Email Repository]
    
    B --> D[Build SMS Request]
    C --> E[Build Email Request]
    
    D --> F[Get Localized SMS Template]
    E --> G[Get Localized Email Template]
    
    F --> H[Replace OTP Placeholder]
    G --> I[Replace OTP Placeholder]
    
    H --> J[Publish SMS Kafka Event]
    I --> K[Publish Email Kafka Event]
    
    J --> L[SMS Notification Service]
    K --> M[Email Notification Service]
    
    L --> N[SMS Gateway]
    M --> O[Email Server]
```

## Security Considerations

### Authentication & Authorization

1. **Public Registration Endpoint**
   - Registration OTP endpoint is public to allow new user registration
   - Rate limiting to prevent abuse
   - Input validation for security

2. **Authenticated Operations**
   - Login and password reset require basic request validation
   - Tenant-based access control
   - User type validation

3. **OTP Security**
   - Time-bound OTP validity (configurable expiry)
   - External OTP service for secure generation
   - No local OTP storage

### Input Validation

1. **Mobile Number Validation**
   - Format validation for Indian mobile numbers
   - Length and pattern checking
   - Sanitization of input

2. **Request Validation**
   - Required field validation
   - Type validation for enum fields
   - Tenant ID validation

3. **User Validation**
   - User existence checks based on OTP type
   - Mobile number ownership validation
   - User type consistency checking

### Data Protection

1. **Privacy Protection**
   - No storage of OTP numbers in service
   - Secure communication with external OTP service
   - Minimal logging of sensitive data

2. **Communication Security**
   - HTTPS for external service calls
   - Secure Kafka message transmission
   - Encrypted OTP delivery through SMS/Email

## API Flow Diagrams

### 1. Send OTP API Flow

```mermaid
flowchart TD
    A[POST /v1/_send] --> B[Request Validation]
    B --> C{OTP Type Check}
    C -->|Registration| D[Registration Flow]
    C -->|Login| E[Login Flow]
    C -->|Password Reset| F[Password Reset Flow]
    
    D --> G[Check User Not Exists]
    E --> H[Check User Exists]
    F --> I[Fetch User Details]
    
    G --> J{User Validation}
    H --> J
    I --> J
    
    J -->|Failed| K[Return Error Response]
    J -->|Passed| L[Generate OTP]
    
    L --> M[Send Notifications]
    M --> N[Return Success Response]
    
    O[Error Handler] --> K
    B -->|Invalid Request| O
    G -->|User Exists| O
    H -->|User Not Found| O
    I -->|Invalid Mobile| O
```

### 2. Multi-Channel Notification Flow

```mermaid
sequenceDiagram
    participant OTPService
    participant SMSRepo
    participant EmailRepo
    participant LocalizationService
    participant KafkaProducer
    participant NotificationServices

    OTPService->>SMSRepo: Send SMS Notification
    OTPService->>EmailRepo: Send Email Notification
    
    SMSRepo->>LocalizationService: Get SMS Template
    EmailRepo->>LocalizationService: Get Email Template
    
    LocalizationService-->>SMSRepo: Localized SMS Template
    LocalizationService-->>EmailRepo: Localized Email Template
    
    SMSRepo->>SMSRepo: Build SMS Content with OTP
    EmailRepo->>EmailRepo: Build Email Content with OTP
    
    SMSRepo->>KafkaProducer: Publish SMS Event
    EmailRepo->>KafkaProducer: Publish Email Event
    
    KafkaProducer->>NotificationServices: SMS Notification
    KafkaProducer->>NotificationServices: Email Notification
```

### 3. User Validation Flow

```mermaid
flowchart TD
    A[OTP Request with Mobile] --> B[User Service Call]
    B --> C[Search User by Mobile]
    C --> D{User Found?}
    D -->|Yes| E[Extract User Details]
    D -->|No| F[No User Object]
    E --> G[Validate Mobile Number]
    G --> H{Mobile Valid?}
    H -->|Yes| I[Return User Object]
    H -->|No| J[Return Mobile Error]
    F --> K[Return Null User]
    
    L[OTP Type Check] --> M{Registration?}
    M -->|Yes| N[User Should Not Exist]
    M -->|No| O[User Should Exist]
    
    N --> P{User is Null?}
    O --> Q{User Found?}
    P -->|Yes| R[Proceed with Registration]
    P -->|No| S[User Already Exists Error]
    Q -->|Yes| T[Proceed with Login/Reset]
    Q -->|No| U[User Not Found Error]
```

### 4. Error Handling Flow

```mermaid
flowchart TD
    A[OTP Request Processing] --> B{Try-Catch Block}
    B --> C{Exception Type}
    C -->|UserAlreadyExistInSystemException| D[Registration Error Response]
    C -->|UserNotExistingInSystemException| E[Login Error Response]
    C -->|UserNotFoundException| F[User Not Found Response]
    C -->|UserMobileNumberNotFoundException| G[Mobile Not Found Response]
    C -->|ValidationException| H[Validation Error Response]
    C -->|SystemException| I[Generic Error Response]
    
    D --> J[HTTP 400 Bad Request]
    E --> J
    F --> J
    G --> J
    H --> J
    I --> K[HTTP 500 Internal Error]
    
    L[Exception Logger] --> M[Audit Trail]
    C --> L
```

This comprehensive documentation provides detailed insights into the User OTP Service's authentication flow, multi-channel notification delivery, external service integration, and secure OTP management for DIGIT Works platform.