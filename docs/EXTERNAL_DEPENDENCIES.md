# External Dependencies - Technical Documentation

## Table of Contents
1. [Overview](#overview)
2. [Infrastructure Dependencies](#infrastructure-dependencies)
3. [External Service Dependencies](#external-service-dependencies)
4. [Third-Party APIs](#third-party-apis)
5. [Development & Deployment Dependencies](#development--deployment-dependencies)
6. [Security & Compliance Dependencies](#security--compliance-dependencies)
7. [Monitoring & Observability](#monitoring--observability)
8. [Configuration Requirements](#configuration-requirements)

---

## Overview

This document provides a comprehensive overview of all external dependencies required by the DIGIT-Works services. These dependencies span across infrastructure components, third-party services, APIs, and development tools necessary for the complete operation of the platform.

### Dependency Categories
- **Infrastructure**: Core infrastructure components (databases, message queues, etc.)
- **External Services**: Third-party services for specific functionality
- **APIs**: External APIs for validation and integration
- **Development Tools**: Build, testing, and deployment tools
- **Security**: Security and compliance services
- **Monitoring**: Observability and monitoring tools

---

## Infrastructure Dependencies

### 1. Database Systems

#### PostgreSQL Database
- **Version Required**: 12.x or higher (Recommended: 14.x)
- **Purpose**: Primary data storage for all services
- **Configuration Requirements**:
  ```properties
  # Minimum Configuration
  max_connections = 200
  shared_buffers = 256MB
  effective_cache_size = 1GB
  work_mem = 4MB
  
  # For Production
  max_connections = 500
  shared_buffers = 1GB
  effective_cache_size = 4GB
  work_mem = 16MB
  ```
- **Extensions Required**:
  - `uuid-ossp` for UUID generation
  - `pg_crypto` for encryption functions
  - `btree_gin` for indexing
- **Services Using**: All DIGIT-Works services
- **Critical**: Yes

#### Redis Cache
- **Version Required**: 6.0+ (Recommended: 7.x)
- **Purpose**: Caching, session storage, performance optimization
- **Configuration Requirements**:
  ```properties
  maxmemory 2gb
  maxmemory-policy allkeys-lru
  save 900 1
  save 300 10
  save 60 10000
  ```
- **Services Using**: Attendance, Muster Roll, Expense, Individual, Organization
- **Critical**: Medium (Performance impact)

### 2. Message Queue Systems

#### Apache Kafka
- **Version Required**: 2.8+ (Recommended: 3.x)
- **Purpose**: Event streaming, asynchronous processing
- **Configuration Requirements**:
  ```properties
  # Broker Configuration
  num.network.threads=8
  num.io.threads=8
  socket.send.buffer.bytes=102400
  socket.receive.buffer.bytes=102400
  socket.request.max.bytes=104857600
  log.retention.hours=168
  log.segment.bytes=1073741824
  log.retention.check.interval.ms=300000
  zookeeper.connection.timeout.ms=18000
  ```
- **Topics Required**:
  - `save-attendance`
  - `update-attendance`
  - `save-attendance-log`
  - `update-attendance-log`
  - `save-musterroll`
  - `update-musterroll`
  - `save-individual-topic`
  - `update-individual-topic`
  - `save-organisation`
  - `update-organisation`
  - `expense-bill-create`
  - `expense-payment-create`
  - `save-bank-account`
  - `egov.core.notification.sms`
- **Services Using**: All DIGIT-Works services
- **Critical**: Yes

#### Apache Zookeeper
- **Version Required**: 3.6+ (Required by Kafka)
- **Purpose**: Kafka cluster coordination
- **Configuration Requirements**:
  ```properties
  tickTime=2000
  dataDir=/var/lib/zookeeper
  clientPort=2181
  initLimit=5
  syncLimit=2
  ```
- **Services Using**: Indirect (Kafka dependency)
- **Critical**: Yes

### 3. Search & Analytics

#### Elasticsearch (Optional)
- **Version Required**: 7.x
- **Purpose**: Advanced search capabilities, analytics
- **Configuration Requirements**:
  ```properties
  cluster.name: digit-works
  node.name: node-1
  path.data: /var/lib/elasticsearch
  path.logs: /var/log/elasticsearch
  network.host: 0.0.0.0
  discovery.type: single-node
  ```
- **Indexes Required**:
  - `individuals`
  - `organisations`
  - `attendance-registers`
  - `muster-rolls`
  - `bills`
- **Services Using**: Individual, Organization, Attendance, Muster Roll, Expense
- **Critical**: No (Performance enhancement)

---

## External Service Dependencies

### 1. Core DIGIT Platform Services

#### MDMS Service (Master Data Management)
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: Master data validation and management
- **APIs Used**:
  - `/egov-mdms-service/v1/_search`
- **Configuration**:
  ```properties
  egov.mdms.host=https://digit-platform.example.com
  egov.mdms.search.endpoint=/egov-mdms-service/v1/_search
  ```
- **Services Using**: All DIGIT-Works services
- **Critical**: Yes

#### ID Generation Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: Generate unique identifiers
- **APIs Used**:
  - `/egov-idgen/id/_generate`
- **Configuration**:
  ```properties
  egov.idgen.host=https://digit-platform.example.com
  egov.idgen.path=/egov-idgen/id/_generate
  ```
- **Services Using**: All DIGIT-Works services
- **Critical**: Yes

#### User Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: User management and authentication
- **APIs Used**:
  - `/user/_create`
  - `/user/_search`
  - `/user/_update`
- **Services Using**: Individual, Organization
- **Critical**: Yes

#### Workflow Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: Workflow management and approvals
- **APIs Used**:
  - `/egov-workflow-v2/egov-wf/process/_transition`
  - `/egov-workflow-v2/egov-wf/process/_search`
- **Services Using**: Muster Roll, Expense, Bank Account, Organization
- **Critical**: Yes

#### Location Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: Geographic boundary management
- **APIs Used**:
  - `/egov-location/location/v11/boundarys/_search`
- **Services Using**: Individual, Organization
- **Critical**: Medium

#### File Store Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: File upload and storage
- **APIs Used**:
  - `/filestore/v1/files/upload`
  - `/filestore/v1/files/url`
- **Services Using**: Individual, Organization, Attendance, Bank Account
- **Critical**: Medium

#### Notification Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: SMS and email notifications
- **APIs Used**:
  - Via Kafka topics: `egov.core.notification.sms`
- **Services Using**: All DIGIT-Works services
- **Critical**: Medium

#### Encryption Service
- **Version Required**: Compatible with DIGIT 2.8+
- **Purpose**: Data encryption and decryption
- **APIs Used**:
  - `/crypto-service/encrypt`
  - `/crypto-service/decrypt`
- **Services Using**: Bank Account, Individual
- **Critical**: Yes (for PII protection)

### 2. Communication Services

#### SMS Gateway
- **Purpose**: Send SMS notifications
- **Supported Providers**:
  - Textlocal
  - MSG91
  - AWS SNS
  - Twilio
  - Custom SMS providers
- **Configuration**:
  ```properties
  sms.provider.url=https://api.textlocal.in/send/
  sms.provider.username=username
  sms.provider.password=password
  sms.sender.name=DIGIT
  ```
- **Services Using**: All services (via Notification Service)
- **Critical**: Medium

#### Email Service
- **Purpose**: Send email notifications
- **Supported Providers**:
  - SMTP
  - SendGrid
  - AWS SES
  - Mailgun
- **Configuration**:
  ```properties
  spring.mail.host=smtp.gmail.com
  spring.mail.port=587
  spring.mail.username=noreply@example.com
  spring.mail.password=password
  ```
- **Services Using**: Organization, Individual
- **Critical**: Low

---

## Third-Party APIs

### 1. Financial Services APIs

#### Bank Validation APIs
- **Purpose**: Validate bank account details
- **Providers**:
  - RazorpayX
  - Cashfree
  - PayU
  - Direct bank APIs
- **APIs Used**:
  - Account validation
  - IFSC code validation
  - Bank branch details
- **Configuration**:
  ```properties
  bank.validation.api.url=https://api.razorpayx.com
  bank.validation.api.key=your-api-key
  bank.validation.api.secret=your-api-secret
  ```
- **Services Using**: Bank Account Service
- **Critical**: Medium

#### Payment Gateway APIs
- **Purpose**: Process payments
- **Providers**:
  - Razorpay
  - PayU
  - CCAvenue
  - Cashfree
  - UPI Payment Gateway
- **APIs Used**:
  - Payment processing
  - Transaction status
  - Refund processing
- **Services Using**: Expense Service
- **Critical**: High (for payments)

#### Penny Drop Services
- **Purpose**: Verify bank accounts via micro-transactions
- **Providers**:
  - RazorpayX
  - Cashfree
  - NPCI APIs
- **Configuration**:
  ```properties
  penny.drop.api.url=https://api.razorpayx.com/v1/accounts/penny_drop
  penny.drop.verification.amount=1.00
  ```
- **Services Using**: Bank Account Service
- **Critical**: Medium

### 2. Identity Verification APIs

#### Aadhaar Verification
- **Purpose**: Verify Aadhaar numbers
- **Provider**: UIDAI (Unique Identification Authority of India)
- **APIs Used**:
  - Aadhaar authentication
  - OTP verification
  - Demographic verification
- **Configuration**:
  ```properties
  aadhaar.api.url=https://auth.uidai.gov.in
  aadhaar.api.license.key=your-license-key
  ```
- **Services Using**: Individual Service
- **Critical**: High (for Indian deployments)

#### PAN Verification
- **Purpose**: Verify PAN numbers
- **Provider**: Income Tax Department, Government of India
- **APIs Used**:
  - PAN validation
  - Name matching
- **Services Using**: Individual Service, Organization Service
- **Critical**: High (for Indian deployments)

#### GST Verification
- **Purpose**: Verify GST numbers
- **Provider**: GST Network (GSTN)
- **APIs Used**:
  - GSTIN validation
  - Business details verification
- **Services Using**: Organization Service
- **Critical**: High (for Indian deployments)

### 3. Geographic Services

#### Geolocation APIs
- **Purpose**: GPS coordinate validation, address geocoding
- **Providers**:
  - Google Maps API
  - OpenStreetMap
  - MapBox
- **APIs Used**:
  - Geocoding
  - Reverse geocoding
  - Distance calculation
- **Configuration**:
  ```properties
  google.maps.api.key=your-google-maps-api-key
  google.maps.api.url=https://maps.googleapis.com/maps/api
  ```
- **Services Using**: Attendance Service (GPS validation), Individual Service (address)
- **Critical**: Medium

---

## Development & Deployment Dependencies

### 1. Build & Packaging Tools

#### Apache Maven
- **Version Required**: 3.6+
- **Purpose**: Build management and dependency resolution
- **Configuration**: `pom.xml` files in each service
- **Critical**: Yes (development)

#### Docker
- **Version Required**: 20.x+
- **Purpose**: Containerization
- **Configuration**:
  ```dockerfile
  FROM openjdk:8-jre-alpine
  ARG JAR_FILE=target/*.jar
  COPY ${JAR_FILE} app.jar
  ENTRYPOINT ["java","-jar","/app.jar"]
  ```
- **Critical**: Yes (deployment)

#### Docker Compose
- **Version Required**: 2.x
- **Purpose**: Local development environment
- **Configuration**: `docker-compose.yml`
- **Critical**: Yes (local development)

### 2. Container Orchestration

#### Kubernetes
- **Version Required**: 1.20+
- **Purpose**: Container orchestration and management
- **Components Required**:
  - kubectl CLI
  - Helm (3.x) for package management
  - Ingress controller (NGINX/Traefik)
- **Critical**: Yes (production deployment)

### 3. CI/CD Tools

#### Jenkins/GitLab CI/GitHub Actions
- **Purpose**: Continuous integration and deployment
- **Pipeline Requirements**:
  - Code compilation
  - Unit testing
  - Security scanning
  - Docker image building
  - Deployment automation
- **Critical**: Yes (automated deployment)

---

## Security & Compliance Dependencies

### 1. Security Scanning Tools

#### SonarQube
- **Version Required**: 8.x+
- **Purpose**: Code quality and security analysis
- **Configuration**:
  ```properties
  sonar.projectKey=digit-works
  sonar.sources=src/main/java
  sonar.tests=src/test/java
  sonar.java.coveragePlugin=jacoco
  ```
- **Critical**: Medium

#### OWASP ZAP
- **Purpose**: Security vulnerability scanning
- **Configuration**: Automated security testing in CI/CD pipeline
- **Critical**: Medium

#### Checkmarx/Veracode
- **Purpose**: Static application security testing (SAST)
- **Critical**: Medium

### 2. Compliance Tools

#### Data Loss Prevention (DLP)
- **Purpose**: Prevent sensitive data exposure
- **Tools**: 
  - Symantec DLP
  - Microsoft Information Protection
- **Critical**: High (for PII protection)

---

## Monitoring & Observability

### 1. Application Monitoring

#### Prometheus
- **Version Required**: 2.x
- **Purpose**: Metrics collection and monitoring
- **Configuration**:
  ```yaml
  global:
    scrape_interval: 15s
  scrape_configs:
    - job_name: 'digit-works'
      static_configs:
        - targets: ['localhost:8080']
  ```
- **Critical**: Medium

#### Grafana
- **Version Required**: 8.x+
- **Purpose**: Metrics visualization and dashboards
- **Critical**: Medium

#### ELK Stack (Elasticsearch, Logstash, Kibana)
- **Purpose**: Log management and analysis
- **Configuration**:
  ```yaml
  # Logstash configuration
  input {
    beats {
      port => 5044
    }
  }
  filter {
    # Log processing filters
  }
  output {
    elasticsearch {
      hosts => ["localhost:9200"]
    }
  }
  ```
- **Critical**: Medium

### 2. APM Tools

#### Jaeger/Zipkin
- **Purpose**: Distributed tracing
- **Configuration**:
  ```properties
  opentracing.jaeger.udp-sender.host=localhost
  opentracing.jaeger.udp-sender.port=6831
  opentracing.jaeger.sampler.type=const
  opentracing.jaeger.sampler.param=1
  ```
- **Critical**: Low

### 3. Health Check Tools

#### Spring Boot Actuator
- **Purpose**: Application health monitoring
- **Endpoints**:
  - `/actuator/health`
  - `/actuator/metrics`
  - `/actuator/info`
- **Critical**: Yes

---

## Configuration Requirements

### 1. Environment Variables

#### Common Environment Variables
```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=digit-works
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# Service URLs
MDMS_SERVICE_URL=https://digit-platform.example.com
IDGEN_SERVICE_URL=https://digit-platform.example.com
USER_SERVICE_URL=https://digit-platform.example.com

# External API Keys
SMS_API_KEY=your-sms-api-key
MAPS_API_KEY=your-maps-api-key
BANK_API_KEY=your-bank-api-key
```

### 2. SSL/TLS Certificates

#### Certificate Requirements
- **Domain certificates**: For HTTPS endpoints
- **Service certificates**: For inter-service communication
- **Database certificates**: For encrypted database connections

### 3. Network Configuration

#### Port Requirements
```bash
# Service Ports
8008  # Individual Service
8023  # Attendance Service
8038  # Bank Account Service
8051  # Muster Roll Service
8094  # Organization Service
8099  # Expense Service

# Infrastructure Ports
5432  # PostgreSQL
9092  # Kafka
6379  # Redis
9200  # Elasticsearch
3000  # Grafana
9090  # Prometheus
```

#### Firewall Rules
```bash
# Allow HTTP/HTTPS traffic
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -A INPUT -p tcp --dport 443 -j ACCEPT

# Allow database connections (internal only)
iptables -A INPUT -s 10.0.0.0/8 -p tcp --dport 5432 -j ACCEPT

# Allow Kafka connections (internal only)
iptables -A INPUT -s 10.0.0.0/8 -p tcp --dport 9092 -j ACCEPT
```

### 4. Resource Requirements

#### Minimum Hardware Requirements
```yaml
# Development Environment
CPU: 4 cores
Memory: 8 GB RAM
Storage: 50 GB SSD
Network: 100 Mbps

# Production Environment
CPU: 16+ cores
Memory: 32+ GB RAM
Storage: 500+ GB SSD
Network: 1 Gbps
```

#### Recommended Scaling Configuration
```yaml
# Kubernetes Resource Limits
resources:
  requests:
    memory: "512Mi"
    cpu: "250m"
  limits:
    memory: "1Gi"
    cpu: "500m"

# Auto-scaling configuration
hpa:
  minReplicas: 2
  maxReplicas: 10
  targetCPUUtilizationPercentage: 70
```

---

## Dependency Matrix

| Service | PostgreSQL | Kafka | Redis | MDMS | ID Gen | User | Workflow | SMS | Bank APIs |
|---------|------------|-------|-------|------|--------|------|----------|-----|-----------|
| Individual | ✓ | ✓ | ○ | ✓ | ✓ | ✓ | ○ | ○ | ○ |
| Organization | ✓ | ✓ | ○ | ✓ | ✓ | ○ | ✓ | ○ | ○ |
| Attendance | ✓ | ✓ | ✓ | ✓ | ✓ | ○ | ○ | ○ | ○ |
| Muster Roll | ✓ | ✓ | ○ | ✓ | ✓ | ○ | ✓ | ✓ | ○ |
| Expense | ✓ | ✓ | ○ | ○ | ✓ | ○ | ✓ | ✓ | ○ |
| Bank Account | ✓ | ✓ | ○ | ✓ | ✓ | ○ | ✓ | ○ | ✓ |

**Legend:**
- ✓ Required
- ○ Optional/Recommended

This comprehensive documentation serves as a complete reference for all external dependencies required to deploy and operate the DIGIT-Works platform successfully.