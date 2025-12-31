# SUJOG Works Platform v1 - Comprehensive Service Documentation

## Table of Contents
1. [Platform Overview](#platform-overview)
2. [Service Architecture](#service-architecture)
3. [Actual Workflow](#actual-workflow)
4. [Service Dependencies & Master Data](#service-dependencies--master-data)
5. [Complete Entity Models & Schemas](#complete-entity-models--schemas)
6. [API Specifications](#api-specifications)
7. [Advanced Features](#advanced-features)

---

## Platform Overview

The SUJOG Works Platform v1 is a comprehensive e-governance solution for managing public works projects with focus on strategic planning, contract management, and infrastructure execution. This platform emphasizes annual action planning and work plan execution while maintaining robust project lifecycle management.

### Core Services (Available in SUJOG Works v1)
1. **Annual Action Plan Service** - Strategic planning and budget allocation
2. **Project Service** - Project management and hierarchy
3. **Organisation Service** - Contractor and vendor management  
4. **Estimate Service** - Cost estimation and BOQ management
5. **Contract Service** - Contract lifecycle management
6. **Work Plan Execution Service** - Work plan and milestone execution
7. **Measurement Service** - Work measurement and verification
8. **Expense/Bill Service** - Bill generation (Material and Supervision bills only)
9. **Bank Account Service** - Bank account management
10. **SOR Service** - Schedule of Rates management
11. **Rate Analysis Service** - Analysis for non-SOR items
12. **Statement Service** - Financial statements and reports

### Integration Services
13. **IFMS Adapter Service** - Integration with state financial systems
14. **SMS/Notification Service** - Communication and notifications
15. **Human Resource Management Service** - Employee and staff management

---

## Service Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                   SUJOG Works Platform v1                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │Annual Action │  │   Project    │  │  Estimate    │        │
│  │Plan Service  │→ │   Service    │→ │   Service    │        │
│  └──────────────┘  └──────────────┘  └──────────────┘        │
│         ↓                  ↓                 ↓                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │Organisation  │  │   Contract   │  │Work Plan     │        │
│  │   Service    │  │   Service    │  │Execution     │        │
│  └──────────────┘  └──────────────┘  └──────────────┘        │
│                            ↓                 ↓                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │
│  │ Measurement  │  │Expense/Bill  │  │    SOR       │        │
│  │   Service    │  │   Service    │  │   Service    │        │
│  └──────────────┘  └──────────────┘  └──────────────┘        │
│                            ↓                                   │
│                    ┌──────────────┐                           │
│                    │  Statement   │                           │
│                    │   Service    │                           │
│                    └──────────────┘                           │
└─────────────────────────────────────────────────────────────────┘
```

---

## Actual Workflow

### Complete SUJOG Works Management Flow

```
1. Annual Action Plan Creation
   └─→ 2. Strategic Project Planning
       └─→ 3. Fund Source & Scheme Allocation
           └─→ 4. Project Creation & Organization Setup
               └─→ 5. Estimate Preparation
                   └─→ 6. Contract Creation
                       └─→ 7. Work Plan Execution
                           ├─→ 7a. Milestone Definition
                           ├─→ 7b. Site Handover
                           ├─→ 7c. Activity Completion
                           └─→ 7d. Measurement Recording
                               └─→ 8. Bill Generation (Material/Supervision)
                                   └─→ 9. Payment Processing (JIT Integration)
```

### Key Differences from MUKTA Works v1.1
- **No Attendance/Muster Roll Management** - Focus on material and supervision billing
- **Enhanced Planning** - Annual Action Plan with strategic alignment
- **Work Plan Execution** - Milestone-based execution tracking
- **Fund Source Integration** - Government scheme and funding source tracking
- **Simplified Billing** - Material bills and supervision bills only

### Detailed Flow Description

#### Phase 1: Strategic Planning
**Service**: Annual Action Plan Service  
**Dependencies**: 
- Fund Source/Scheme masters
- Department and organizational hierarchy

**Flow**:
1. Create annual action plan with strategic objectives
2. Define budget allocation by fund sources
3. Link to government schemes
4. Set KPIs and deliverables
5. Approval workflow

#### Phase 2: Project Setup
**Services**: Project Service, Organisation Service  
**Dependencies**:
- Annual Action Plan approval
- Organisation registration

**Flow**:
1. Create projects linked to approved action plans
2. Register organizations (contractors/vendors)
3. Define project hierarchy and beneficiaries
4. Set execution methodology

#### Phase 3: Estimation & Contracting
**Services**: Estimate Service, Contract Service  
**Dependencies**:
- Project creation
- SOR and Rate Analysis

**Flow**:
1. Prepare detailed estimates with BOQ
2. Define execution method for each line item
3. Process contract awards
4. Set milestone-based payment terms

#### Phase 4: Work Plan Execution
**Service**: Work Plan Execution Service  
**Dependencies**:
- Approved contracts
- Site handover completion

**Flow**:
1. Create work plan with milestone breakup
2. Conduct site handover with geo-tagging
3. Track milestone-wise activity completion
4. Upload geo-tagged photographs
5. Record quality checks and verification

#### Phase 5: Measurement & Billing
**Services**: Measurement Service, Expense/Bill Service  
**Dependencies**:
- Completed milestones
- Work measurements

**Flow**:
1. Record measurements against contract items
2. Generate material bills based on measurements
3. Generate supervision bills based on milestones
4. Process payments through IFMS integration

---

## Service Dependencies & Master Data

### 1. Annual Action Plan Service

**API Endpoints**:
```
POST /annual-action-plan/v1/_create
POST /annual-action-plan/v1/_update
POST /annual-action-plan/v1/_search
POST /annual-action-plan/v1/_workflow
POST /annual-action-plan/project/v1/_create
POST /annual-action-plan/project/v1/_search
POST /annual-action-plan/milestone/v1/_create
POST /annual-action-plan/dashboard/v1/_get
```

**Dependencies**:
- Project Service

**MDMS Masters Used**:
- `works.FundSource` - Fund source types
- `works.Scheme` - Government schemes
- `common-masters.Department` - Department list
- `works.ProjectType` - Project types

**Key Fields**:
```json
{
  "id": "UUID",
  "planNumber": "AAP/2024-25/001",
  "name": "Infrastructure Development Plan 2024-25",
  "financialYear": "2024-25",
  "department": "PWD",
  "status": "APPROVED",
  "fundSource": "STATE_FUND",
  "scheme": "PMGSY",
  "budget": {
    "totalBudget": 50000000,
    "allocatedBudget": 45000000,
    "utilizedBudget": 12000000
  },
  "objectives": [],
  "kpis": [],
  "projects": []
}
```

### 2. Work Plan Execution Service

**API Endpoints**:
```
POST /work-plan/v1/_create
POST /work-plan/v1/_update
POST /work-plan/v1/_search
POST /work-plan/milestone/v1/_create
POST /work-plan/milestone/v1/_update
POST /site-handover/v1/_create
POST /activity-completion/v1/_create
POST /activity-completion/photo/v1/_upload
POST /execution/dashboard/v1/_get
```

**Dependencies**:
- Contract Service
- Project Service

**MDMS Masters Used**:
- `works.ExecutionMethod` - Execution methodology
- `works.Milestone` - Milestone types
- `works.Category` - Work categories
- `common-masters.UOM` - Units of measurement

**Key Fields**:
```json
{
  "id": "UUID",
  "workPlanNumber": "WP/2024-25/001",
  "contractId": "contract-uuid",
  "name": "Q1 Infrastructure Work Plan",
  "executionMethod": "MANUAL",
  "milestones": [
    {
      "id": "milestone-uuid",
      "name": "Foundation Work",
      "targetDate": "epoch",
      "percentageCompletion": 75,
      "deliverables": []
    }
  ],
  "siteHandover": {
    "handoverDate": "epoch",
    "geoLocation": {
      "latitude": 28.6139,
      "longitude": 77.2090
    },
    "photographs": []
  }
}
```

### 3. Enhanced Project Service

**Additional Dependencies for SUJOG**:
- Annual Action Plan Service (project linkage)
- Fund Source configuration

**Additional MDMS Masters**:
- `works.FundSource` - Project funding sources
- `works.Scheme` - Associated government schemes
- `works.ExecutionMethod` - Project execution methodology

### 4. Enhanced Expense/Bill Service (Material & Supervision Only)

**Bill Types Supported**:
- **Material Bills**: Based on measurement quantities
- **Supervision Bills**: Based on milestone completion

**Dependencies**:
- Contract Service
- Measurement Service
- Work Plan Execution Service

**Key Features**:
- No wage bill processing
- Milestone-based supervision billing
- Enhanced IFMS integration for government funds

---

## Complete Entity Models & Schemas

### Entity Classification for SUJOG Works v1

| Entity Name | Type | UUID Field | Schema Code (if Master) | Primary References | Service Owner |
|-------------|------|------------|------------------------|-------------------|---------------|
| **AnnualActionPlan** | Core Entity | id | - | department, fundSource | AAP Service |
| **Project** | Core Entity | id | - | parent, annualActionPlanId | Project Service |
| **Estimate** | Core Entity | id | - | projectId | Estimate Service |
| **Contract** | Core Entity | id | - | estimateId, orgId | Contract Service |
| **WorkPlan** | Core Entity | id | - | contractId | Work Execution Service |
| **SiteHandover** | Core Entity | id | - | contractId, workPlanId | Work Execution Service |
| **ActivityCompletion** | Core Entity | id | - | workPlanId, milestoneId | Work Execution Service |
| **Measurement** | Core Entity | id | - | contractId | Measurement Service |
| **Bill** | Core Entity | id | - | contractId | Expense Service |
| **Payment** | Core Entity | id | - | billId | Expense Service |
| **Organisation** | Core Entity | id | - | - | Organisation Service |
| **BankAccount** | Core Entity | id | - | orgId | Bank Account Service |
| **SOR** | Core Entity | id | - | projectId | SOR Service |
| **RateAnalysis** | Core Entity | id | - | projectId | Rate Analysis Service |
| **Statement** | Core Entity | id | - | billId, paymentId | Statement Service |
| **AAPProject** | Link Entity | id | - | annualActionPlanId, projectId | AAP Service |
| **AAPMilestone** | Link Entity | id | - | annualActionPlanId | AAP Service |
| **WorkPlanMilestone** | Link Entity | id | - | workPlanId | Work Execution Service |
| **ProjectBeneficiary** | Link Entity | id | - | projectId, beneficiaryId | Project Service |
| **ProjectStaff** | Link Entity | id | - | projectId, userId | Project Service |
| **ProjectTask** | Link Entity | id | - | projectId | Project Service |
| **Photograph** | Support Entity | id | - | referenceId (any entity) | Work Execution Service |
| **Document** | Support Entity | id | - | referenceId (any entity) | Multiple Services |
| **AuditDetails** | Support Entity | - | - | - | All Services |
| **Address** | Support Entity | - | - | - | Multiple Services |
| **GeoLocation** | Support Entity | - | - | - | Work Execution Service |
| **FundSource** | Master Data | - | works.FundSource | - | MDMS |
| **Scheme** | Master Data | - | works.Scheme | - | MDMS |
| **ExecutionMethod** | Master Data | - | works.ExecutionMethod | - | MDMS |
| **MilestoneType** | Master Data | - | works.Milestone | - | MDMS |
| **ProjectType** | Master Data | - | works.ProjectType | - | MDMS |
| **ContractType** | Master Data | - | works.ContractType | - | MDMS |
| **Department** | Master Data | - | common-masters.Department | - | MDMS |
| **UOM** | Master Data | - | common-masters.UOM | - | MDMS |
| **HeadCode** | Master Data | - | expense.HeadCodes | - | MDMS |
| **ApplicableCharge** | Master Data | - | expense.ApplicableCharges | - | MDMS |

### Detailed Entity Models for SUJOG Works v1

#### 1. Annual Action Plan Entity

**Purpose**: Strategic planning and budget allocation for annual infrastructure development

```json
{
  "id": "UUID - System generated unique identifier",
  "tenantId": "STRING - Tenant identifier",
  "planNumber": "STRING - Auto-generated plan number (AAP/2024-25/001)",
  "name": "STRING - Plan name/title",
  "description": "STRING - Detailed plan description",
  "financialYear": "STRING - Financial year (2024-25)",
  "department": "STRING - Reference to common-masters.Department",
  "fundSource": "STRING - Reference to works.FundSource master",
  "scheme": "STRING - Reference to works.Scheme master",
  "status": "STRING - Plan status (DRAFT, APPROVED, IN_PROGRESS, COMPLETED)",
  "workflowState": "STRING - Current workflow state",
  "startDate": "LONG - Plan start date (epoch)",
  "endDate": "LONG - Plan end date (epoch)",
  "budget": {
    "totalBudget": "DOUBLE - Total allocated budget",
    "allocatedBudget": "DOUBLE - Budget allocated to projects",
    "utilizedBudget": "DOUBLE - Budget utilized so far",
    "availableBudget": "DOUBLE - Remaining budget",
    "currency": "STRING - Currency code (INR)",
    "budgetBreakdown": [
      {
        "category": "STRING - Budget category",
        "allocatedAmount": "DOUBLE - Allocated amount",
        "utilizedAmount": "DOUBLE - Utilized amount"
      }
    ]
  },
  "objectives": [
    {
      "id": "UUID",
      "title": "STRING - Objective title",
      "description": "STRING - Objective description",
      "priority": "STRING - Priority (HIGH, MEDIUM, LOW)",
      "targetMetrics": "STRING - Target metrics",
      "achievedMetrics": "STRING - Achieved metrics"
    }
  ],
  "kpis": [
    {
      "id": "UUID",
      "name": "STRING - KPI name",
      "targetValue": "DOUBLE - Target value",
      "actualValue": "DOUBLE - Actual achieved value",
      "unit": "STRING - Measurement unit",
      "frequency": "STRING - Measurement frequency"
    }
  ],
  "approvalDetails": {
    "approvedBy": "STRING - Approver user ID",
    "approvalDate": "LONG - Approval date (epoch)",
    "approvalComments": "STRING - Approval comments"
  },
  "documents": ["Array of Document objects"],
  "additionalDetails": "JSON - Additional plan details",
  "isDeleted": "BOOLEAN - Soft delete flag",
  "rowVersion": "LONG - Optimistic locking",
  "auditDetails": "AuditDetails object"
}
```

#### 2. Work Plan Entity

**Purpose**: Detailed execution plan with milestone-wise breakdown

```json
{
  "id": "UUID - System generated unique identifier",
  "tenantId": "STRING - Tenant identifier",
  "workPlanNumber": "STRING - Auto-generated work plan number",
  "contractId": "UUID - Reference to Contract.id",
  "projectId": "STRING - Associated project ID",
  "estimateId": "STRING - Associated estimate ID",
  "name": "STRING - Work plan name",
  "description": "STRING - Detailed description",
  "executionMethod": "STRING - Reference to works.ExecutionMethod",
  "startDate": "LONG - Plan start date (epoch)",
  "endDate": "LONG - Plan end date (epoch)",
  "status": "STRING - Plan status",
  "workType": "STRING - Type of work",
  "totalEstimatedCost": "DOUBLE - Total estimated cost",
  "totalActualCost": "DOUBLE - Total actual cost",
  "overallProgress": "DOUBLE - Overall progress percentage",
  "milestones": [
    {
      "id": "UUID",
      "sequenceNumber": "INTEGER - Milestone sequence",
      "name": "STRING - Milestone name",
      "type": "STRING - Reference to works.Milestone",
      "description": "STRING - Milestone description",
      "targetDate": "LONG - Target completion date",
      "actualDate": "LONG - Actual completion date",
      "status": "STRING - Milestone status",
      "percentageCompletion": "DOUBLE - Completion percentage",
      "estimatedCost": "DOUBLE - Estimated cost",
      "actualCost": "DOUBLE - Actual cost",
      "deliverables": [
        {
          "id": "UUID",
          "name": "STRING - Deliverable name",
          "description": "STRING - Deliverable description",
          "status": "STRING - Deliverable status",
          "dueDate": "LONG - Due date",
          "completionDate": "LONG - Completion date"
        }
      ],
      "dependencies": ["Array of dependent milestone IDs"]
    }
  ],
  "documents": ["Array of Document objects"],
  "additionalDetails": "JSON - Additional details",
  "isDeleted": "BOOLEAN - Soft delete flag",
  "rowVersion": "LONG - Optimistic locking",
  "auditDetails": "AuditDetails object"
}
```

#### 3. Site Handover Entity

**Purpose**: Capture site handover details with geo-location

```json
{
  "id": "UUID - System generated unique identifier",
  "tenantId": "STRING - Tenant identifier",
  "handoverNumber": "STRING - Auto-generated handover number",
  "contractId": "UUID - Reference to Contract.id",
  "workPlanId": "UUID - Reference to WorkPlan.id",
  "projectId": "STRING - Associated project",
  "handoverDate": "LONG - Site handover date (epoch)",
  "handoverBy": "STRING - Handover authority",
  "handoverTo": "STRING - Receiving contractor",
  "siteDetails": {
    "siteName": "STRING - Site name",
    "siteAddress": "Address object",
    "siteArea": "DOUBLE - Site area",
    "siteAreaUnit": "STRING - Area unit",
    "boundaryDetails": "STRING - Site boundary details",
    "accessDetails": "STRING - Site access details",
    "utilities": [
      {
        "type": "STRING - Utility type (WATER, ELECTRICITY, etc)",
        "available": "BOOLEAN - Availability status",
        "details": "STRING - Utility details"
      }
    ]
  },
  "geoLocation": {
    "latitude": "DOUBLE - Site latitude",
    "longitude": "DOUBLE - Site longitude",
    "accuracy": "DOUBLE - GPS accuracy",
    "capturedAt": "LONG - Location capture timestamp"
  },
  "status": "STRING - Handover status",
  "conditions": [
    {
      "type": "STRING - Condition type",
      "status": "STRING - Compliance status",
      "remarks": "STRING - Condition remarks"
    }
  ],
  "photographs": [
    {
      "id": "UUID",
      "fileStoreId": "STRING - Photo file reference",
      "caption": "STRING - Photo caption",
      "category": "STRING - Photo category",
      "geoLocation": "GeoLocation object",
      "timestamp": "LONG - Photo timestamp"
    }
  ],
  "documents": ["Array of Document objects"],
  "remarks": "STRING - Handover remarks",
  "additionalDetails": "JSON - Additional details",
  "isDeleted": "BOOLEAN - Soft delete flag",
  "rowVersion": "LONG - Optimistic locking",
  "auditDetails": "AuditDetails object"
}
```

#### 4. Activity Completion Entity

**Purpose**: Track milestone-wise activity completion with evidence

```json
{
  "id": "UUID - System generated unique identifier",
  "tenantId": "STRING - Tenant identifier",
  "completionNumber": "STRING - Auto-generated completion number",
  "workPlanId": "UUID - Reference to WorkPlan.id",
  "milestoneId": "UUID - Reference to milestone",
  "activityId": "STRING - Specific activity reference",
  "contractId": "UUID - Reference to contract",
  "completionDate": "LONG - Activity completion date",
  "completionType": "STRING - Completion type (FULL, PARTIAL, MILESTONE)",
  "percentageCompleted": "DOUBLE - Completion percentage",
  "quantityCompleted": "DOUBLE - Quantity completed",
  "unit": "STRING - Unit of measurement",
  "executionMethod": "STRING - Method used for execution",
  "geoLocation": {
    "latitude": "DOUBLE - Activity location latitude",
    "longitude": "DOUBLE - Activity location longitude",
    "accuracy": "DOUBLE - GPS accuracy",
    "capturedAt": "LONG - Location capture time"
  },
  "photographs": [
    {
      "id": "UUID",
      "fileStoreId": "STRING - Photo file reference",
      "fileName": "STRING - Original file name",
      "fileType": "STRING - File type",
      "fileSize": "INTEGER - File size in bytes",
      "caption": "STRING - Photo caption",
      "category": "STRING - Photo category (BEFORE, DURING, AFTER, MILESTONE)",
      "geoLocation": "GeoLocation object - Photo location",
      "timestamp": "LONG - Photo timestamp",
      "uploadedBy": "STRING - User who uploaded",
      "tags": ["Array of photo tags"]
    }
  ],
  "qualityChecks": [
    {
      "parameter": "STRING - Quality parameter",
      "specification": "STRING - Required specification",
      "actualValue": "STRING - Measured value",
      "status": "STRING - Check status (PASS, FAIL, CONDITIONAL)",
      "remarks": "STRING - Quality remarks"
    }
  ],
  "verificationDetails": {
    "verifiedBy": "STRING - Verifier user ID",
    "verificationDate": "LONG - Verification date",
    "verificationStatus": "STRING - Verification status",
    "verificationRemarks": "STRING - Verification comments",
    "signature": "STRING - Digital signature reference"
  },
  "remarks": "STRING - Activity remarks",
  "weather": "STRING - Weather conditions",
  "impediments": "STRING - Encountered impediments",
  "additionalDetails": "JSON - Additional details",
  "isDeleted": "BOOLEAN - Soft delete flag",
  "rowVersion": "LONG - Optimistic locking",
  "auditDetails": "AuditDetails object"
}
```

### New Master Data Entities for SUJOG Works v1

#### Fund Source Master
```json
{
  "code": "STRING - Fund source code",
  "name": "STRING - Fund source name",
  "description": "STRING - Fund source description",
  "type": "STRING - Fund type (CENTRAL, STATE, LOCAL, PRIVATE)",
  "category": "STRING - Fund category",
  "active": "BOOLEAN - Active status"
}
```

**Example Values**:
- `CENTRAL_FUND` - Central Government Fund
- `STATE_FUND` - State Government Fund  
- `MGNREGA` - MGNREGA Fund
- `PMGSY` - Pradhan Mantri Gram Sadak Yojana
- `PRIVATE_CSR` - Corporate Social Responsibility Fund

#### Scheme Master
```json
{
  "code": "STRING - Scheme code",
  "name": "STRING - Scheme name", 
  "description": "STRING - Scheme description",
  "fundSource": "STRING - Associated fund source",
  "ministry": "STRING - Responsible ministry",
  "launchDate": "LONG - Scheme launch date",
  "endDate": "LONG - Scheme end date",
  "budgetAllocation": "DOUBLE - Total scheme budget",
  "objectives": ["Array of scheme objectives"],
  "beneficiaries": ["Array of target beneficiaries"],
  "active": "BOOLEAN - Active status"
}
```

**Example Values**:
- `PMGSY` - Pradhan Mantri Gram Sadak Yojana
- `NHAI` - National Highway Authority of India
- `MGNREGA` - Mahatma Gandhi National Rural Employment Guarantee Act
- `SBM` - Swachh Bharat Mission
- `AMRUT` - Atal Mission for Rejuvenation and Urban Transformation

#### Execution Method Master
```json
{
  "code": "STRING - Execution method code",
  "name": "STRING - Method name",
  "description": "STRING - Method description",
  "category": "STRING - Method category",
  "applicableWorkTypes": ["Array of applicable work types"],
  "requiredSkills": ["Array of required skills"],
  "equipmentNeeded": ["Array of equipment"],
  "active": "BOOLEAN - Active status"
}
```

**Example Values**:
- `MANUAL` - Manual execution method
- `MECHANICAL` - Machine-based execution
- `HYBRID` - Combined manual and mechanical
- `SPECIALIZED` - Specialized equipment required
- `TRADITIONAL` - Traditional construction methods

#### Milestone Master
```json
{
  "code": "STRING - Milestone type code",
  "name": "STRING - Milestone name",
  "description": "STRING - Milestone description",
  "category": "STRING - Milestone category",
  "applicableWorkTypes": ["Array of work types"],
  "standardDuration": "INTEGER - Standard duration in days",
  "deliverables": ["Array of standard deliverables"],
  "qualityParameters": ["Array of quality check parameters"],
  "active": "BOOLEAN - Active status"
}
```

**Example Values**:
- `FOUNDATION` - Foundation work completion
- `STRUCTURE` - Main structure completion  
- `FINISHING` - Finishing work completion
- `HANDOVER` - Project handover milestone
- `COMMISSIONING` - Equipment commissioning

---

## API Specifications

### Enhanced Annual Action Plan APIs

```yaml
/annual-action-plan/v1/_create:
  - Creates new annual action plan
  - Links to fund sources and schemes
  - Initializes budget allocation
  - Sets strategic objectives

/annual-action-plan/project/v1/_create:
  - Links projects to action plan
  - Allocates project-wise budget
  - Sets priority and timeline

/annual-action-plan/dashboard/v1/_get:
  - Annual plan dashboard
  - Fund utilization metrics
  - Project progress summary
  - KPI achievement status
```

### Work Plan Execution APIs

```yaml
/work-plan/v1/_create:
  - Creates execution work plan
  - Defines milestone breakdown
  - Links to approved contracts

/work-plan/milestone/v1/_create:
  - Creates milestone definitions
  - Sets deliverable requirements
  - Defines quality parameters

/site-handover/v1/_create:
  - Records site handover
  - Captures geo-location
  - Documents site conditions

/activity-completion/v1/_create:
  - Records activity completion
  - Updates milestone progress
  - Links quality evidence

/activity-completion/photo/v1/_upload:
  - Uploads geo-tagged photos
  - Associates with milestones
  - Maintains metadata
```

---

## Advanced Features

### Strategic Planning Integration

**Fund Source Tracking**:
- Real-time fund availability monitoring
- Multi-source fund allocation
- Scheme-wise budget tracking
- Cross-scheme fund transfer

**Scheme Alignment**:
- Government scheme compliance
- Beneficiary targeting
- Objective achievement tracking
- Impact measurement

### Work Plan Execution Monitoring

**Milestone Management**:
- Dependency tracking
- Critical path analysis
- Progress visualization
- Delay impact assessment

**Quality Assurance**:
- Photo-based evidence collection
- Geo-tagged verification
- Quality parameter checking
- Digital signature integration

### Enhanced Billing (Material & Supervision Only)

**Material Bills**:
- Measurement-based billing
- BOQ quantity verification
- Rate analysis integration
- Multi-currency support

**Supervision Bills**:
- Milestone-based billing
- Percentage completion tracking
- Professional service rates
- Performance-based payments

---

## Key Differences from MUKTA Works v1.1

| Feature | MUKTA Works v1.1 | SUJOG Works v1 |
|---------|------------------|------------------|
| **Planning** | Project-based | Strategic AAP-based |
| **Attendance** | Full attendance tracking | Not applicable |
| **Muster Rolls** | Wage calculation | Not applicable |
| **Individual Management** | Wage seeker registration | Not applicable |
| **Billing Types** | Wage, Material, Supervision | Material, Supervision only |
| **Execution Focus** | Labor management | Infrastructure execution |
| **Fund Management** | Basic budget tracking | Fund source & scheme integration |
| **Milestone Management** | Basic milestones | Enhanced milestone execution |
| **Quality Assurance** | Basic documentation | Geo-tagged photo evidence |
| **Strategic Alignment** | Project-level | Government scheme alignment |

---

## Conclusion

SUJOG Works v1 is designed for infrastructure-focused public works with emphasis on strategic planning, milestone-based execution, and material/supervision billing. It removes the complexity of wage management while enhancing planning capabilities and execution monitoring through geo-tagged evidence collection and government scheme integration.

---

**Document Version**: 1.0  
**Last Updated**: December 31, 2024  
**Platform**: SUJOG Works v1  
**Contact**: sujog-support@egov.org.in