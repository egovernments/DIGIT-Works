# DIGIT Works Platform v1.1 - Complete Service Documentation

## Table of Contents
1. [Platform Overview](#platform-overview)
2. [Service Architecture](#service-architecture)
3. [Actual Workflow](#actual-workflow)
4. [Service Dependencies & Master Data](#service-dependencies--master-data)
5. [API Specifications](#api-specifications)
6. [Data Models & Relationships](#data-models--relationships)

---

## Platform Overview

The DIGIT Works Platform v1.1 is a comprehensive system for managing public works projects. The platform consists of the following core services based on actual specifications:

### Core Services (Available in v1.1)
1. **Project Service** - Project management and hierarchy
2. **Organisation Service** - Contractor and vendor management  
3. **Estimate Service** - Cost estimation and BOQ management
4. **Contract Service** - Contract lifecycle management
5. **Measurement Service** - Work measurement and verification
6. **Expense/Bill Service** - Bill generation and payment processing
7. **Bank Account Service** - Bank account management
8. **SOR Service** - Schedule of Rates management
9. **Rate Analysis Service** - Analysis for non-SOR items
10. **Statement Service** - Financial statements and reports

---

## Service Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    DIGIT Works Platform v1.1                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │   Project    │  │  Estimate    │  │   Contract   │        │
│  │   Service    │→ │   Service    │→ │   Service    │        │
│  └──────────────┘  └──────────────┘  └──────────────┘        │
│         ↓                  ↓                 ↓                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │Organisation  │  │     SOR      │  │ Measurement  │        │
│  │   Service    │  │   Service    │  │   Service    │        │
│  └──────────────┘  └──────────────┘  └──────────────┘        │
│                            ↓                 ↓                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │ Bank Account │  │Rate Analysis │  │Expense/Bill  │        │
│  │   Service    │  │   Service    │  │   Service    │        │
│  └──────────────┘  └──────────────┘  └──────────────┘        │
│                                              ↓                 │
│                                     ┌──────────────┐          │
│                                     │  Statement   │          │
│                                     │   Service    │          │
│                                     └──────────────┘          │
└─────────────────────────────────────────────────────────────────┘
```

---

## Actual Workflow

### Complete Works Management Flow (Based on Available Services)

```
1. Project Creation
   └─→ 2. Organisation Registration (Contractor/Vendor)
       └─→ 3. Estimate Preparation
           └─→ 4. Contract Creation
               └─→ 5. Measurement Recording
                   └─→ 6. Bill Generation
                       └─→ 7. Payment Processing
```

### Detailed Flow Description

#### Phase 1: Project Initiation
**Service**: Project Service  
**Dependencies**: 
- Organisation Service (for project owner details)
- MDMS Masters: ProjectType, Department, hierarchyType

**Flow**:
1. Create project with basic details
2. Link project beneficiaries
3. Assign project staff
4. Create project tasks (if enabled)
5. Link facilities and resources

#### Phase 2: Organisation Setup
**Service**: Organisation Service  
**Dependencies**:
- Bank Account Service
- MDMS Masters: OrgType, OrgFunctionClass, OrgTaxIdentifier

**Flow**:
1. Register organisation (contractor/vendor)
2. Add contact details
3. Link bank accounts
4. Define functional areas

#### Phase 3: Estimation
**Service**: Estimate Service  
**Dependencies**:
- Project Service (projectId required)
- SOR Service (for SOR items)
- Rate Analysis Service (for non-SOR items)
- MDMS Masters: EstimateTemplate, UOM, Overheads

**Flow**:
1. Create estimate linked to project
2. Add line items (SOR and non-SOR)
3. Apply overhead charges
4. Calculate total estimate value
5. Submit for approval

#### Phase 4: Contract Management
**Service**: Contract Service  
**Dependencies**:
- Estimate Service (estimateId required)
- Organisation Service (orgId for contractor)
- MDMS Masters: ContractType, DocumentConfig

**Flow**:
1. Create contract from approved estimate
2. Link contractor organisation
3. Define contract terms
4. Set security deposit
5. Process contract approval

#### Phase 5: Measurement
**Service**: Measurement Service  
**Dependencies**:
- Contract Service (contractId)
- MDMS Masters: MeasurementCriteria, UOM

**Flow**:
1. Create measurement book
2. Record measurements against contract items
3. Verify measurements
4. Approve measurement book

#### Phase 6: Billing & Payment
**Service**: Expense/Bill Service  
**Dependencies**:
- Contract Service
- Measurement Service
- Bank Account Service
- MDMS Masters: HeadCodes, ApplicableCharges, PaymentInstructionType

**Flow**:
1. Generate bill from approved measurements
2. Apply deductions and charges
3. Calculate net payable amount
4. Process payment instruction
5. Update payment status

---

## Service Dependencies & Master Data

### 1. Project Service

**API Endpoints**:
```
POST /project/v1/_create
POST /project/v1/_update
POST /project/v1/_search
POST /project/beneficiary/v1/_create
POST /project/beneficiary/v1/_search
POST /project/task/v1/_create
POST /project/task/v1/_search
POST /project/staff/v1/_create
POST /project/staff/v1/_search
POST /project/facility/v1/_create
POST /project/resource/v1/_create
```

**Dependencies**:
- Organisation Service (for project owner)

**MDMS Masters Used**:
- `works.ProjectType` - Project types and subtypes
- `common-masters.Department` - Department list
- `common-masters.hierarchyType` - Project hierarchy types
- `works.TargetDemography` - Target beneficiary types
- `common-masters.Designation` - Staff designations

**Key Fields**:
```json
{
  "id": "UUID",
  "projectNumber": "PR/2024-25/001",
  "name": "Road Construction Project",
  "projectType": "from MDMS",
  "department": "from MDMS",
  "startDate": "epoch",
  "endDate": "epoch",
  "address": {},
  "targets": [],
  "parent": "parent-project-id"
}
```

### 2. Organisation Service

**API Endpoints**:
```
POST /org-services/organisation/v1/_create
POST /org-services/organisation/v1/_update
POST /org-services/organisation/v1/_search
```

**Dependencies**:
- Bank Account Service

**MDMS Masters Used**:
- `common-masters.OrgType` - Organisation types
- `common-masters.OrgFunctionClass` - Functional classification
- `common-masters.OrgFunctionCategory` - Functional categories
- `common-masters.OrgTaxIdentifier` - Tax identifiers (PAN, GST)

**Key Fields**:
```json
{
  "id": "UUID",
  "name": "ABC Contractors",
  "applicationNumber": "ORG/2024-25/001",
  "orgNumber": "ORG001",
  "registrationStatus": "ACTIVE",
  "orgType": "from MDMS",
  "taxIdentifiers": [],
  "orgFunctions": [],
  "bankAccounts": []
}
```

### 3. Estimate Service

**API Endpoints**:
```
POST /estimate/v1/_create
POST /estimate/v1/_update
POST /estimate/v1/_search
```

**Dependencies**:
- Project Service (projectId required)
- SOR Service
- Rate Analysis Service

**MDMS Masters Used**:
- `WORKS.EstimateTemplate` - Estimate templates
- `common-masters.UOM` - Units of measurement
- `works.Overheads` - Overhead charges
- `works.Category` - Work categories

**Key Fields**:
```json
{
  "id": "UUID",
  "estimateNumber": "EST/2024-25/001",
  "projectId": "project-uuid",
  "estimateType": "ESTIMATE",
  "status": "ACTIVE",
  "estimateDetails": [
    {
      "lineItems": [],
      "category": "from MDMS",
      "uom": "from MDMS",
      "rate": 1000,
      "quantity": 100,
      "amount": 100000
    }
  ]
}
```

### 4. Contract Service

**API Endpoints**:
```
POST /contract/v1/_create
POST /contract/v1/_update
POST /contract/v1/_search
```

**Dependencies**:
- Estimate Service (estimateId required)
- Organisation Service (orgId for contractor)

**MDMS Masters Used**:
- `works.ContractType` - Contract types
- `works.DocumentConfig` - Required documents
- `expense.BusinessService` - Workflow configuration

**Key Fields**:
```json
{
  "id": "UUID",
  "contractNumber": "CON/2024-25/001",
  "estimateId": "estimate-uuid",
  "orgId": "contractor-org-uuid",
  "agreementDate": "epoch",
  "defectLiabilityPeriod": 365,
  "contractType": "from MDMS",
  "status": "ACTIVE",
  "securityDeposit": 50000,
  "agreementAmount": 1000000,
  "lineItems": []
}
```

### 5. Measurement Service

**API Endpoints**:
```
POST /measurement/v1/_create
POST /measurement/v1/_update  
POST /measurement/v1/_search
```

**Dependencies**:
- Contract Service (contractId required)

**MDMS Masters Used**:
- `works.MeasurementCriteria` - Measurement rules
- `common-masters.UOM` - Units of measurement
- `works.MeasurementBFFConfig` - UI configuration

**Key Fields**:
```json
{
  "id": "UUID",
  "measurementNumber": "MB/2024-25/001",
  "contractId": "contract-uuid",
  "physicalRefNumber": "MB-001",
  "isActive": true,
  "measurements": [
    {
      "targetId": "contract-line-item-id",
      "cumulativeValue": 50,
      "currentValue": 10
    }
  ]
}
```

### 6. Expense/Bill Service

**API Endpoints**:
```
POST /expense/bill/v1/_create
POST /expense/bill/v1/_update
POST /expense/bill/v1/_search
POST /expense/payment/v1/_create
POST /expense/payment/v1/_search
```

**Dependencies**:
- Contract Service
- Measurement Service
- Bank Account Service

**MDMS Masters Used**:
- `expense.HeadCodes` - Budget head codes
- `expense.ApplicableCharges` - Deduction types
- `expense.PayerList` - Payer configuration
- `expense.PaymentInstructionType` - Payment types
- `expense.PaymentInstructionStatus` - Payment statuses

**Key Fields**:
```json
{
  "id": "UUID",
  "billNumber": "BILL/2024-25/001",
  "contractId": "contract-uuid",
  "fromPeriod": "epoch",
  "toPeriod": "epoch",
  "billAmount": 100000,
  "paidAmount": 0,
  "status": "APPROVED",
  "billDetails": [
    {
      "lineItems": [],
      "payableAmount": 100000,
      "billDeductions": []
    }
  ]
}
```

### 7. Bank Account Service

**API Endpoints**:
```
POST /bankaccount/v1/_create
POST /bankaccount/v1/_update
POST /bankaccount/v1/_search
```

**MDMS Masters Used**:
- `works.BankAccType` - Account types

**Key Fields**:
```json
{
  "id": "UUID",
  "accountNumber": "1234567890",
  "accountType": "from MDMS",
  "bankName": "State Bank",
  "bankBranch": "Main Branch",
  "ifscCode": "SBIN0001234"
}
```

### 8. SOR Service

**API Endpoints**:
```
POST /sor/v1/_create
POST /sor/v1/_update
POST /sor/v1/_search
```

**MDMS Masters Used**:
- `WORKS-SOR.SOR` - Schedule of rates
- `WORKS-SOR.Type` - SOR types
- `WORKS-SOR.SubType` - SOR subtypes
- `WORKS-SOR.Variant` - SOR variants
- `WORKS-SOR.Composition` - Material composition
- `WORKS-SOR.Overhead` - Overhead percentages
- `WORKS-SOR.Rates` - Location-specific rates

**Key Fields**:
```json
{
  "id": "UUID",
  "sorNumber": "SOR001",
  "sorType": "from MDMS",
  "description": "Earthwork excavation",
  "uom": "CUM",
  "rate": 500,
  "validFrom": "epoch",
  "validTo": "epoch"
}
```

---

## Data Models & Relationships

### Entity Relationship Diagram

```mermaid
erDiagram
    Project ||--o{ Estimate : has
    Project ||--o{ ProjectBeneficiary : serves
    Project ||--o{ ProjectStaff : assigns
    Project ||--o{ Task : contains
    
    Organisation ||--o{ BankAccount : owns
    Organisation ||--o{ Contract : executes
    
    Estimate ||--|| Contract : generates
    
    Contract ||--o{ Measurement : records
    Contract ||--o{ Bill : generates
    
    Measurement ||--|| Bill : creates
    
    Bill ||--o{ Payment : processes
    
    SOR ||--o{ EstimateLineItem : uses
    RateAnalysis ||--o{ EstimateLineItem : defines
```

### Service Flow Dependencies

```
Project Service
    ↓ (projectId)
Estimate Service → SOR Service
    ↓ (estimateId)    ↓
Contract Service ← Rate Analysis Service
    ↓ (contractId)
Measurement Service
    ↓ (measurements)
Expense/Bill Service → Bank Account Service
    ↓ (billId)
Statement Service
```

---

## Common MDMS Masters

### Access Control
- `ACCESSCONTROL-ROLES` - System roles
- `ACCESSCONTROL-ROLEACTIONS` - Role-action mapping
- `ACCESSCONTROL-ACTIONS-TEST` - Available actions

### Common Masters
- `common-masters.Department` - Department list
- `common-masters.Designation` - Designations
- `common-masters.UOM` - Units of measurement
- `common-masters.StateInfo` - State information
- `common-masters.IdFormat` - ID generation formats

### Works Specific
- `works.Category` - Work categories
- `works.ContractType` - Contract types
- `works.ProjectType` - Project types
- `works.CBORoles` - CBO roles
- `works.OICRoles` - OIC roles

### Financial
- `expense.HeadCodes` - Budget heads
- `expense.ApplicableCharges` - Deductions
- `segment-codes.*` - Financial segment codes
- `ifms.SchemeDetails` - Scheme information

### UI Configuration
- `commonMuktaUiConfig.*` - UI field configurations
- `commonUiConfig.*` - UI display configurations
- `INBOX.*` - Inbox configurations
- `SEARCH.*` - Search configurations

---

## API Response Structure

### Standard Request Format
```json
{
  "RequestInfo": {
    "apiId": "org.egov.works",
    "ver": "1.0",
    "ts": 1704067200000,
    "msgId": "unique-id",
    "authToken": "token"
  },
  "Entity": {
    // Entity specific data
  }
}
```

### Standard Response Format
```json
{
  "ResponseInfo": {
    "apiId": "org.egov.works",
    "ver": "1.0",
    "ts": 1704067200000,
    "msgId": "unique-id",
    "status": "successful"
  },
  "Entity": {
    // Response data
  }
}
```

---

## Workflow Integration

Each major entity follows workflow states:

### Estimate Workflow
```
DRAFT → SUBMITTED → CHECKED → APPROVED → REJECTED
```

### Contract Workflow
```
DRAFT → PENDING_APPROVAL → APPROVED → ACTIVE → COMPLETED
```

### Bill Workflow
```
DRAFT → SUBMITTED → VERIFIED → APPROVED → PAID
```

---

## Key Integration Points

1. **Project → Estimate**: projectId linkage
2. **Estimate → Contract**: estimateId linkage
3. **Contract → Measurement**: contractId linkage
4. **Measurement → Bill**: measurement-based billing
5. **Organisation → Contract**: contractor linkage
6. **All Services → MDMS**: Master data dependency
7. **All Services → Workflow**: State management

---

## Version Information

**Platform Version**: 1.1  
**API Version**: v1  
**OpenAPI Specification**: 3.0.0  
**Last Updated**: December 2024

---

*This document represents the actual state of DIGIT Works v1.1 based on available service specifications and MDMS configurations.*