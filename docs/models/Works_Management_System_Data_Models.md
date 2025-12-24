# Works Management System - Data Models Documentation

**Version:** 1.2  
**Date:** December 2025  
**Project:** JAGAN NEW Project (Sujog)

---

## 1. Overview

The Works Management System consists of ten core data models that form the backbone of public works project management. These models track projects from initial proposal through contract execution, workforce management, work measurement, and payment processing.

### 1.1 Model Relationships

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                         WORKS MANAGEMENT SYSTEM                             │
│                            (10 Core Models)                                 │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│   PROJECT   │──────│   ESTIMATE  │──────│   CONTRACT  │
│ PJ/2025-26/ │      │ ES/2025-26/ │      │ WO/2025-26/ │
│ 006635      │      │ 004597      │      │ 002981      │
└──────┬──────┘      └──────┬──────┘      └──────┬──────┘
       │                    │                    │
       │              projectId                  │
       │                    │                    │
       │                    ▼                    │
       │         ┌─────────────────┐             │
       │         │  ORGANISATION   │◄────────────┤ orgId
       │         │  ORG-002128     │             │
       │         │  (JAG Org)      │             │
       │         └────────┬────────┘             │
       │                  │                      │
       │                  │ referenceId          │
       │                  ▼                      │
       │         ┌─────────────────┐             │
       │         │  BANK ACCOUNT   │             │
       │         │  (Savings)      │             │
       │         └─────────────────┘             │
       │                                         │
       │    ┌────────────────────────────────────┘
       │    │
       │    │  referenceId (contractNumber)
       │    ▼
       │  ┌───────────────────┐
       │  │ ATTENDANCE        │
       │  │ REGISTER          │
       │  │ WR/2025-26/...    │
       │  └─────────┬─────────┘
       │            │
       │            │ registerId
       │            ▼
       │  ┌───────────────────┐      ┌─────────────┐
       │  │   MUSTER ROLL     │◄─────│  INDIVIDUAL │
       │  │   MR/2025-26/...  │      │  IND-2024.. │
       │  └─────────┬─────────┘      └─────────────┘
       │            │                individualId
       │            │
       ├────────────┼────────────────────────────────┐
       │            │                                │
       ▼            ▼                                ▼
┌─────────────┐  ┌─────────────┐              ┌─────────────┐
│ MEASUREMENT │  │   EXPENSE   │──────────────│    BILL     │
│ MB/2025-26/ │  │   (Wage)    │              │ PB/2025-26/ │
│ 001597      │  │             │              │ 000567      │
└─────────────┘  └─────────────┘              └─────────────┘
```

### 1.2 Summary Table

| Model | Identifier | Status | Amount/Value |
|-------|------------|--------|--------------|
| Project | `PJ/2025-26/006635` | Active | ₹10,00,000 (Estimated) |
| Estimate | `ES/2025-26/004597` | Approved | ₹50,366.91 |
| Contract | `WO/2025-26/002981` | Accepted | ₹50,367 |
| Organisation | `ORG-002128` | Active | JAG Org |
| Bank Account | `a487b407-...` | Active | ****0111 (Savings) |
| Attendance Register | `WR/2025-26/12/23/002411` | Active | 2 Attendees |
| Individual | `IND-2024-08-29-003481` | Active | jay |
| Muster Roll | `MR/2025-26/12/23/001329` | Approved | ₹14,500 |
| Measurement | `MB/2025-26/001597` | Approved | ₹561 |
| Bill (Purchase) | `PB/2025-26/000567` | Approved | ₹1,222 |

---

## 2. Project Model

The Project model captures the initial proposal for a public works project, including location, documentation, and preliminary cost estimates.

### 2.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `3db48223-4506-4ba2-a8e6-f56599f2a38c` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| projectNumber | String | `PJ/2025-26/006635` | Human-readable project number |
| name | String | `JAGAN NEW Project` | Project name |
| projectType | String | `CC` | Project type code |
| description | String | `JAGAN NEW Project for Sujog` | Detailed description |
| referenceID | String | `REF-111` | External reference |
| isTaskEnabled | Boolean | `false` | Task tracking flag |
| isDeleted | Boolean | `false` | Soft delete flag |
| rowVersion | Integer | `0` | Optimistic locking |

### 2.2 Address Object

| Field | Type | Sample Value |
|-------|------|--------------|
| id | UUID | `82dcb1f0-0333-4f3b-9370-7313ed0a7027` |
| tenantId | String | `od.testing` |
| latitude | Number | `11` |
| longitude | Number | `22` |
| city | String | `od.testing` |
| boundaryType | String | `Ward` |
| boundary | String | `W001` |

### 2.3 Documents Array

| Document Type | File Name | Status |
|---------------|-----------|--------|
| PROJECT_PROPOSAL | `project-payments-PJ_2024-25_003342.pdf` | ACTIVE |
| FINALIZED_WORKLIST | `Estimate-ES_2025-26_004596.pdf` | ACTIVE |
| FEASIBILITY_ANALYSIS | `Project-PJ_2025-26_006634.pdf` | ACTIVE |

### 2.4 Additional Details

| Field | Value |
|-------|-------|
| creator | `SUJOG-WORKS-UAT` |
| locality | `L002` |
| dateOfProposal | `1766514599000` (epoch) |
| targetDemography | `NSM` |
| estimatedCostInRs | `1000000` |

### 2.5 Audit Details

| Field | Value |
|-------|-------|
| createdBy | `81b1ce2d-262d-4632-b2a3-3e8227769a11` |
| lastModifiedBy | `81b1ce2d-262d-4632-b2a3-3e8227769a11` |
| createdTime | `1766486395896` |
| lastModifiedTime | `1766486395896` |

---

## 3. Estimate Model

The Estimate model contains the detailed cost breakdown for a project, including SOR items, NON-SOR items, and overhead charges.

### 3.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `15e24785-536d-4ab8-8b54-e370cc67e9f6` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| estimateNumber | String | `ES/2025-26/004597` | Human-readable estimate number |
| businessService | String | `ESTIMATE` | Service type |
| versionNumber | Integer | `1` | Version tracking |
| projectId | UUID | `3db48223-4506-4ba2-a8e6-f56599f2a38c` | **Link to Project** |
| proposalDate | Long | `1766486576712` | Proposal timestamp |
| status | String | `ACTIVE` | Record status |
| wfStatus | String | `APPROVED` | Workflow status |
| name | String | `Testing` | Estimate name |
| description | String | `JAGAN NEW Project for Sujog` | Description |
| executingDepartment | String | `WRK` | Department code |

### 3.2 Estimate Details (Line Items)

#### 3.2.1 SOR Items (Schedule of Rates)

| SOR ID | Name | Unit Rate | Qty | UOM | Amount |
|--------|------|-----------|-----|-----|--------|
| SOR_000407 | Jr. progress recorder | ₹550 | 72 | NOs | ₹39,600 |
| SOR_000188 | Batching and Mixing Plant 15-20 cum | ₹500 | 1 | HOUR | ₹500 |

**SOR Item Details:**
- Category: `SOR`
- Dimensions (SOR_000407): Length=3, Width=2, Height=12
- isDeduction: `false`

#### 3.2.2 NON-SOR Items

| Name | Description | Unit Rate | Qty | UOM | Amount |
|------|-------------|-----------|-----|-----|--------|
| temp | sas | ₹11 | 3 | MT | ₹33 |

#### 3.2.3 Overhead Charges

| Code | Name | Percentage | Amount |
|------|------|------------|--------|
| SC | Supervision Charge | 7.5% | ₹3,009.97 |
| GST | Goods and Service Tax | 18% | ₹7,223.94 |

### 3.3 Cost Summary

| Category | Amount |
|----------|--------|
| Labour (SOR) | ₹39,600 |
| Material | ₹500 |
| Machinery | ₹0 |
| NON-SOR | ₹33 |
| Supervision Charge (7.5%) | ₹3,009.97 |
| GST (18%) | ₹7,223.94 |
| **Total Estimated Amount** | **₹50,366.91** |

### 3.4 SOR Skill Data

| SOR ID | SOR Type | SOR Sub Type |
|--------|----------|--------------|
| SOR_000407 | L (Labour) | SD |
| SOR_000188 | E (Equipment) | NA1 |

### 3.5 Additional Details

| Field | Value |
|-------|-------|
| ward | `W001` |
| locality | `L002` |
| projectName | `JAGAN NEW Project` |
| projectNumber | `PJ/2025-26/006635` |

---

## 4. Contract Model

The Contract model represents the work order issued to a contractor, linking estimate line items to contracted amounts.

### 4.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `433a211f-c45f-4e1c-89b6-28685e214c0c` | Unique identifier |
| contractNumber | String | `WO/2025-26/002981` | Work order number |
| versionNumber | Integer | `1` | Version tracking |
| businessService | String | `CONTRACT` | Service type |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| wfStatus | String | `ACCEPTED` | Workflow status |
| executingAuthority | String | `IA` | Implementing Agency |
| contractType | String | `CON-01` | Contract type code |
| totalContractedAmount | Number | `50367` | Total contracted value |
| securityDeposit | Number | `0` | Security deposit |
| orgId | UUID | `c3a3d64b-621c-4609-b2ad-a99f3d361577` | Contractor organization |
| completionPeriod | Integer | `111` | Days to complete |
| status | String | `ACTIVE` | Record status |

### 4.2 Timeline

| Field | Value |
|-------|-------|
| issueDate | `1766486687411` (epoch) |
| startDate | `1766428200000` (epoch) |
| endDate | `1776104999999` (epoch) |
| agreementDate | `0` |
| defectLiabilityPeriod | `0` |

### 4.3 Line Items

The contract line items link directly to estimate line items:

| Estimate Line Item | Unit Rate | No. of Units | Amount | Status |
|--------------------|-----------|--------------|--------|--------|
| Jr. progress recorder | ₹550 | 72 | ₹39,600 | ACTIVE |
| Batching and Mixing Plant | ₹500 | 1 | ₹500 | ACTIVE |
| temp (NON-SOR) | ₹11 | 3 | ₹33 | ACTIVE |
| Supervision Charge | - | - | ₹3,009.97 | ACTIVE |
| GST | - | - | ₹7,223.94 | ACTIVE |

### 4.4 Contract Line Item Structure

```
Contract Line Item
├── id (UUID)
├── estimateId (UUID) ──────► Links to Estimate
├── estimateLineItemId (UUID) ──────► Links to Estimate Detail
├── contractLineItemRef (UUID)
├── tenantId
├── unitRate
├── noOfunit
├── status
└── amountBreakups[]
    ├── id
    ├── estimateAmountBreakupId ──────► Links to Estimate Amount
    ├── amount
    └── status
```

### 4.5 Contractor Details

| Field | Value |
|-------|-------|
| orgId | `c3a3d64b-621c-4609-b2ad-a99f3d361577` |
| cboCode | `c3a3d64b-621c-4609-b2ad-a99f3d361577` |
| cboName | `JAG Org` |
| orgName | `JAG Org` |
| cboOrgNumber | `ORG-002128` |

### 4.6 Officer In Charge

| Field | Value |
|-------|-------|
| officerInChargeId | `MBJECREATOR` |
| officerInChargeName | `MB JE CREATOR` |
| officerInChargeDesgn | `Junior Engineer` |

### 4.7 Documents

| Document Type | Status |
|---------------|--------|
| WORKORDER_WORK_AGREEMENT | ACTIVE |

### 4.8 Terms and Conditions

```json
[
  {
    "description": "Hi contract"
  }
]
```

### 4.9 Related References

| Field | Value |
|-------|-------|
| projectId | `PJ/2025-26/006635` |
| projectName | `JAGAN NEW Project` |
| projectType | `CC` |
| estimateNumber | `ES/2025-26/004597` |
| totalEstimatedAmount | `50366.91` |
| attendanceRegisterNumber | `WR/2025-26/12/23/002411` |

---

## 5. Organisation Model

The Organisation model represents contractor entities (CBOs - Community Based Organizations) that execute works contracts.

### 5.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `c3a3d64b-621c-4609-b2ad-a99f3d361577` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| name | String | `JAG Org` | Organization name |
| applicationNumber | String | `SR/ORG/22-12-2025/002128` | Application reference |
| orgNumber | String | `ORG-002128` | Human-readable org number |
| applicationStatus | String | `ACTIVE` | Application status |
| dateOfIncorporation | Long | `1764613799000` | Incorporation date (epoch) |
| isActive | Boolean | `true` | Active flag |

### 5.2 Address Object

| Field | Type | Sample Value |
|-------|------|--------------|
| id | UUID | `3d33e33a-8944-46cf-8633-91662eed5eee` |
| doorNo | String | `1` |
| street | String | `New cross street` |
| city | String | `od.testing` |
| boundaryType | String | `Ward` |
| boundaryCode | String | `W001` |

### 5.3 Contact Details

| Field | Value |
|-------|-------|
| contactName | `Jagankumar` |
| contactMobileNumber | `9996566433` |
| contactEmail | `jagan.kumar@egov.org.in` |
| individualId | `7ce51353-54ba-4282-badd-30ab3393e19c` |

### 5.4 Identifiers

| Type | Value | Status |
|------|-------|--------|
| PAN | `AAAAA1111D` | Active |

### 5.5 Functions

| Field | Value |
|-------|-------|
| applicationNumber | `SR/FUNC/22-12-2025/002128` |
| type | `CBO.MSG` |
| category | `CBO.NA` |
| class | `A` |
| validFrom | `1764613799000` |
| validTo | `4078664999000` |
| isActive | `true` |

### 5.6 Additional Details

| Field | Value |
|-------|-------|
| locality | `L002` |
| registeredByDept | `SUJOG` |
| deptRegistrationNum | `SUJOG-10101` |

---

## 6. Bank Account Model

The Bank Account model stores banking details for organisations, enabling payment processing through government treasury systems.

### 6.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `a487b407-c1cb-469c-be9a-0562cf4aeacf` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| serviceCode | String | `ORG` | Service type |
| referenceId | UUID | `c3a3d64b-621c-4609-b2ad-a99f3d361577` | **Link to Organisation** |

### 6.2 Bank Account Details

| Field | Type | Sample Value |
|-------|------|--------------|
| id | UUID | `ee6d1241-5d4d-444b-ae02-5eb2cca42173` |
| tenantId | String | `od.testing` |
| accountHolderName | String | `Jagan` |
| accountNumber | String | `********0111` (masked) |
| accountType | String | `SAVINGS` |
| isPrimary | Boolean | `true` |
| isActive | Boolean | `true` |

### 6.3 Bank Branch Identifier

| Field | Value |
|-------|-------|
| id | `43ec1c02-fd9d-43cb-9131-d68a532536cb` |
| type | `IFSC` |
| code | `ICIC*****36` (masked) |
| additionalDetails.ifsccode | `P******` (masked) |

### 6.4 Bank Account Structure

```
Bank Account
├── id (UUID)
├── tenantId
├── serviceCode (ORG)
├── referenceId ──────────────► Organisation.id
├── bankAccountDetails[]
│   ├── id
│   ├── accountHolderName
│   ├── accountNumber (masked)
│   ├── accountType (SAVINGS/CURRENT)
│   ├── isPrimary
│   ├── isActive
│   └── bankBranchIdentifier
│       ├── id
│       ├── type (IFSC)
│       ├── code (masked)
│       └── additionalDetails
└── auditDetails
```

---

## 7. Attendance Register Model

The Attendance Register model manages the workforce roster for a contract, tracking staff (supervisors) and attendees (workers) enrolled for the project.

### 7.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `d71d6d8e-26f6-483f-b65b-390cedefc0bb` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| registerNumber | String | `WR/2025-26/12/23/002411` | Register reference |
| name | String | `JAGAN NEW Project` | Register name |
| referenceId | String | `WO/2025-26/002981` | **Link to Contract** |
| serviceCode | String | `WORKS-CONTRACT` | Service identifier |
| status | String | `ACTIVE` | Record status |

### 7.2 Timeline

| Field | Value |
|-------|-------|
| startDate | `1766428200000` (epoch) |
| endDate | `1776104999999` (epoch) |

### 7.3 Staff (Supervisors)

| Field | Value |
|-------|-------|
| id | `cf0eedff-4240-4dbd-a4d4-3cf4e2db3383` |
| registerId | `d71d6d8e-26f6-483f-b65b-390cedefc0bb` |
| userId | `7ce51353-54ba-4282-badd-30ab3393e19c` |
| enrollmentDate | `1766486746844` |
| denrollmentDate | `null` |

### 7.4 Attendees (Workers)

| Individual ID | Individual Name | Guardian | Gender | Enrollment Date |
|---------------|-----------------|----------|--------|-----------------|
| `517d11b4-...` | jay | pathak | FEMALE | `1766428200000` |
| `ebb60896-...` | jagan | Ee | MALE | `1766428200000` |

### 7.5 Attendee Details

| Field | Jay | Jagan |
|-------|-----|-------|
| id | `419d607f-f06f-4e4b-88de-b2c0546d5910` | `4351d106-4437-4388-a3f5-e69bb9caf363` |
| individualId | `517d11b4-210d-43bd-bc42-1806452ebb5d` | `ebb60896-0b43-4d93-8701-ebbbf5114d74` |
| individualID | `IND-2024-08-29-003481` | `IND-2025-12-22-005545` |

### 7.6 Attendance Register Structure

```
Attendance Register
├── id (UUID)
├── registerNumber
├── name
├── referenceId ──────────────► Contract.contractNumber
├── serviceCode
├── startDate, endDate
├── status
├── staff[]
│   ├── id
│   ├── registerId ───────────► AttendanceRegister.id
│   ├── userId ───────────────► User/Contact.id
│   ├── enrollmentDate
│   └── denrollmentDate
├── attendees[]
│   ├── id
│   ├── registerId ───────────► AttendanceRegister.id
│   ├── individualId ─────────► Individual.id
│   ├── enrollmentDate
│   ├── denrollmentDate
│   └── additionalDetails
│       ├── individualID
│       ├── individualName
│       ├── individualGaurdianName
│       └── gender
└── additionalDetails
    ├── projectId, projectName
    ├── contractId
    ├── orgName
    └── officerInCharge
```

### 7.7 Additional Details

| Field | Value |
|-------|-------|
| ward | `W001` |
| locality | `L002` |
| orgName | `JAG Org` |
| projectId | `PJ/2025-26/006635` |
| projectName | `JAGAN NEW Project` |
| projectType | `CC` |
| contractId | `WO/2025-26/002981` |
| officerInCharge | `MBJECREATOR` |
| executingAuthority | `IA` |

---

## 8. Individual Model

The Individual model represents workers/laborers who can be enrolled in attendance registers and assigned to work on contracts.

### 8.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `517d11b4-210d-43bd-bc42-1806452ebb5d` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| individualId | String | `IND-2024-08-29-003481` | Human-readable ID |
| rowVersion | Integer | `1` | Optimistic locking |
| hasErrors | Boolean | `false` | Validation flag |
| isDeleted | Boolean | `false` | Soft delete flag |
| isSystemUser | Boolean | `false` | System user flag |
| isSystemUserActive | Boolean | `true` | System user active |

### 8.2 Personal Information

| Field | Value |
|-------|-------|
| givenName | `jay` |
| familyName | `null` |
| dateOfBirth | `29/08/2003` |
| gender | `CS_COMMON_UNDISCLOSED` |
| mobileNumber | `9876543234` |
| fatherName | `pathak` |
| relationship | `CS_COMMON_UNDISCLOSED` |

### 8.3 Address

| Field | Value |
|-------|-------|
| id | `ff7fa4d8-9447-4265-af70-e50938fa4aae` |
| type | `PERMANENT` |
| doorNo | `B block` |
| street | `#100` |
| city | `od.testing` |
| pincode | `875649` |
| locality.code | `L002` |
| ward.code | `W002` |

### 8.4 Identifiers

| Type | Value | Status |
|------|-------|--------|
| AADHAAR | `********2372` (masked) | Active |

### 8.5 Skills

| Skill Type | Skill Level | Status |
|------------|-------------|--------|
| SOR_000369 | SOR_000369 | Active |
| SOR_000371 | SOR_000371 | Active |

### 8.6 Additional Fields

| Key | Value |
|-----|-------|
| SOCIAL_CATEGORY | `CS_COMMON_UNDISCLOSED` |
| is_aadhaar_verified | `false` |
| verification_time | `1724925704163` |

### 8.7 Individual Structure

```
Individual
├── id (UUID)
├── individualId (IND-YYYY-MM-DD-NNNNNN)
├── tenantId
├── name
│   ├── givenName
│   ├── familyName
│   └── otherNames
├── dateOfBirth
├── gender
├── mobileNumber
├── fatherName / husbandName
├── relationship
├── address[]
│   ├── id
│   ├── type (PERMANENT/CORRESPONDENCE)
│   ├── doorNo, street, city, pincode
│   ├── locality (code)
│   └── ward (code)
├── identifiers[]
│   ├── identifierType (AADHAAR)
│   └── identifierId (masked)
├── skills[]
│   ├── type (SOR code)
│   ├── level
│   └── experience
├── additionalFields
│   └── fields[] (SOCIAL_CATEGORY, aadhaar_verified, etc.)
├── userDetails
│   ├── username, password
│   ├── tenantId, roles, type
└── auditDetails
```

### 8.8 User Details

| Field | Value |
|-------|-------|
| tenantId | `od.testing` |
| username | `null` |
| roles | `null` |
| type | `null` |

---

## 9. Measurement Model

The Measurement model (MB - Measurement Book) records actual work progress against contracted items, tracking quantities completed for each line item.

### 9.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `b1eebb30-d6ab-437a-9967-fb5ed1875164` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| measurementNumber | String | `MB/2025-26/001597` | MB reference number |
| referenceId | String | `WO/2025-26/002981` | **Link to Contract** |
| entryDate | Long | `1766486931027` | Entry timestamp |
| isActive | Boolean | `true` | Active flag |
| wfStatus | String | `APPROVED` | Workflow status |

### 9.2 Measures Array

Each measure links to a contract line item via `targetId`:

| Target ID | Type | L x B x H | Qty | Current Value | Cumulative | MB Amount |
|-----------|------|-----------|-----|---------------|------------|-----------|
| `5e1651de-...` | SOR | 1 x 1 x 1 | 1 | 1 | 1 | ₹550 |
| `93e72408-...` | SOR | 0 x 0 x 0 | 0 | 0 | 0 | ₹0 |
| `564f8da5-...` | NONSOR | 1 x 1 x 1 | 1 | 1 | 1 | ₹11 |

### 9.3 Measure Structure

```
Measure
├── id (UUID)
├── referenceId ──────────────► Measurement.id
├── targetId ─────────────────► Contract.lineItems.contractLineItemRef
├── length, breadth, height
├── numItems
├── currentValue
├── cumulativeValue
├── isActive
├── comments
├── documents
└── additionalDetails
    ├── type (SOR/NONSOR)
    ├── mbAmount
    └── measureLineItems[]
        ├── measureSummary
        ├── length, width, height
        ├── number, quantity
        └── measurelineitemNo
```

### 9.4 Measure Line Items (SOR Example)

| Field | Value |
|-------|-------|
| measureSummary | `one week` |
| length | `1` |
| width | `1` |
| height | `1` |
| number | `1` |
| quantity | `1.0000` |
| measurelineitemNo | `0` |

### 9.5 Documents

| Document Type | File Name | Status |
|---------------|-----------|--------|
| image/png | `db-diagram.png` | Active |

### 9.6 Amount Summary

| Category | Amount |
|----------|--------|
| SOR Amount | ₹550 |
| Non-SOR Amount | ₹11 |
| **Total Amount** | **₹561** |

### 9.7 Additional Details

| Field | Value |
|-------|-------|
| source | `Web` |
| startDate | `1766341800000` |
| endDate | `1766946599999` |
| musterRollNumber | `MR/2025-26/12/23/001329` |

---

## 10. Muster Roll Model

The Muster Roll model tracks daily attendance of workers assigned to a contract, enabling wage calculation and payment processing.

### 10.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `50481744-c8c9-47f9-96a2-7581b8a4cae4` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| musterRollNumber | String | `MR/2025-26/12/23/001329` | MR reference |
| registerId | UUID | `d71d6d8e-26f6-483f-b65b-390cedefc0bb` | **Link to Attendance Register** |
| status | String | `ACTIVE` | Record status |
| musterRollStatus | String | `APPROVED` | Workflow status |
| referenceId | String | `WO/2025-26/002981` | **Link to Contract** |
| serviceCode | String | `WORKS-CONTRACT` | Service identifier |

### 10.2 Timeline

| Field | Value |
|-------|-------|
| startDate | `1766341800000` (epoch) |
| endDate | `1766860200000` (epoch) |

### 10.3 Individual Entries

| Individual ID | User ID | User Name | Skill Code | Total Attendance |
|---------------|---------|-----------|------------|------------------|
| `517d11b4-...` | `IND-2024-08-29-003481` | jay | `SOR_000371` | 1 |
| `ebb60896-...` | `IND-2025-12-22-005545` | jagan | `SOR_000367` | 1 |

### 10.4 Attendance Entry Structure

```
Individual Entry
├── id (UUID)
├── individualId ──────────────► Individual.id
├── actualTotalAttendance
├── modifiedTotalAttendance
├── attendanceEntries[]
│   ├── id
│   ├── time (epoch)
│   ├── attendance (0 or 1)
│   └── auditDetails
└── additionalDetails
    ├── userId
    ├── userName
    └── skillCode
```

### 10.5 Attendance Entries (Sample - Jay)

| Date (Epoch) | Attendance |
|--------------|------------|
| 1766860200000 | 0 (Absent) |
| 1766773800000 | 0 (Absent) |
| 1766687400000 | 0 (Absent) |
| 1766601000000 | 0 (Absent) |
| 1766514600000 | 0 (Absent) |
| 1766428200000 | **1 (Present)** |
| 1766341800000 | 0 (Absent) |

### 10.6 Additional Details

| Field | Value |
|-------|-------|
| ward | `W001` |
| locality | `L002` |
| orgId | `c3a3d64b-621c-4609-b2ad-a99f3d361577` |
| orgName | `JAG Org` |
| projectId | `PJ/2025-26/006635` |
| projectName | `JAGAN NEW Project` |
| projectType | `CC` |
| contractId | `WO/2025-26/002981` |
| executingAuthority | `IA` |
| attendanceRegisterNo | `WR/2025-26/12/23/002411` |
| attendanceRegisterName | `JAGAN NEW Project` |
| amount | `14500` |

---

## 11. Bill Model

The Bill model represents purchase bills or payment requests generated against contracts, supporting both payable amounts and deductions.

### 11.1 Core Fields

| Field | Type | Sample Value | Description |
|-------|------|--------------|-------------|
| id | UUID | `1084cd70-74d8-4875-949a-02c2e5d64649` | Unique identifier |
| tenantId | String | `od.testing` | Multi-tenant identifier |
| billNumber | String | `PB/2025-26/000567` | Bill reference |
| billDate | Long | `1766514599000` | Bill date (epoch) |
| businessService | String | `EXPENSE.PURCHASE` | Service type |
| referenceId | String | `WO/2025-26/002981_PR_000588` | Contract + PR reference |
| totalAmount | Number | `1222` | Total bill amount |
| totalPaidAmount | Number | `0` | Amount paid |
| status | String | `INWORKFLOW` | Record status |
| wfStatus | String | `APPROVED` | Workflow status |
| paymentStatus | String | `null` | Payment status |

### 11.2 Timeline

| Field | Value |
|-------|-------|
| fromPeriod | `1766428200000` |
| toPeriod | `1776104999999` |
| dueDate | `0` |

### 11.3 Payer Details

| Field | Value |
|-------|-------|
| id | `83280dc5-6582-4b05-9d92-3ae30e0e47ed` |
| tenantId | `od.testing` |
| type | `od.testing` |
| identifier | `003` |
| status | `ACTIVE` |

### 11.4 Payee Details (Bill Detail)

| Field | Value |
|-------|-------|
| id | `9ab35999-25c8-4464-a3b1-2ca5a2f9a78b` |
| type | `ORG` |
| identifier | `c3a3d64b-621c-4609-b2ad-a99f3d361577` |
| status | `ACTIVE` |

### 11.5 Line Items

| Head Code | Type | Amount | Status |
|-----------|------|--------|--------|
| MC | PAYABLE | ₹1,111 | ACTIVE |
| GST | PAYABLE | ₹111 | ACTIVE |
| ITTDSOTI | DEDUCTION | ₹22 | ACTIVE |

### 11.6 Payable Line Items

| Head Code | Type | Amount | Status |
|-----------|------|--------|--------|
| ITTDSOTI | PAYABLE | ₹22 | ACTIVE |
| PURCHASE | PAYABLE | ₹1,200 | ACTIVE |

### 11.7 Bill Structure

```
Bill
├── id (UUID)
├── billNumber
├── billDate
├── businessService
├── referenceId ──────────────► Contract Reference
├── totalAmount
├── payer
│   ├── id, type, identifier
│   └── status
├── billDetails[]
│   ├── id
│   ├── billId ───────────────► Bill.id
│   ├── referenceId ──────────► Contract.contractNumber
│   ├── totalAmount
│   ├── payee
│   │   ├── type (ORG)
│   │   └── identifier ───────► Organisation.id
│   ├── lineItems[]
│   │   ├── headCode (MC, GST, ITTDSOTI)
│   │   ├── type (PAYABLE/DEDUCTION)
│   │   └── amount
│   └── payableLineItems[]
│       ├── headCode
│       ├── type (PAYABLE)
│       └── amount
└── additionalDetails
    ├── invoiceNumber
    ├── invoiceDate
    ├── documents[]
    └── mbValidationData
```

### 11.8 Amount Calculation

| Description | Amount |
|-------------|--------|
| Material Cost (MC) | ₹1,111 |
| GST | ₹111 |
| **Gross Amount** | **₹1,222** |
| Less: TDS (ITTDSOTI) | (₹22) |
| **Net Payable** | **₹1,200** |

### 11.9 Documents

| Document Type | File Name |
|---------------|-----------|
| VENDOR_INVOICE | `Project-PJ_2025-26_006635.pdf` |

### 11.10 Additional Details

| Field | Value |
|-------|-------|
| ward | `W001` |
| locality | `L002` |
| orgName | `JAG Org` |
| projectId | `PJ/2025-26/006635` |
| projectName | `JAGAN NEW Project` |
| invoiceNumber | `INV111` |
| invoiceDate | `1766514599000` |
| totalBillAmount | `1222` |
| organisationType.code | `CBO` |
| organisationType.name | `Community based organization` |

### 11.11 MB Validation Data

| Field | Value |
|-------|-------|
| allMeasurementsIds | `["MB/2025-26/001597"]` |
| totalMaterialAmount | `0` |
| totalPaidAmountForSuccessfulBills | `0` |

---

## 12. Data Model Relationships

### 12.1 Entity Relationship Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            WORKS MANAGEMENT SYSTEM                          │
│                              (10 Core Models)                               │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────┐
│      PROJECT        │
├─────────────────────┤
│ PK: id              │
│ projectNumber       │◄─────────────────────────────────────────────────────┐
│ name                │                                                      │
│ projectType         │                                                      │
│ description         │                                                      │
│ address             │                                                      │
│ documents[]         │                                                      │
└─────────┬───────────┘                                                      │
          │ 1:N                                                              │
          ▼                                                                  │
┌─────────────────────┐                                                      │
│      ESTIMATE       │                                                      │
├─────────────────────┤                                                      │
│ PK: id              │                                                      │
│ FK: projectId ──────┼──────► PROJECT.id                                    │
│ estimateNumber      │                                                      │
│ wfStatus            │                                                      │
│ estimateDetails[]   │                                                      │
└─────────┬───────────┘                                                      │
          │ 1:N                                                              │
          ▼                                                                  │
┌─────────────────────┐       ┌─────────────────────┐                        │
│      CONTRACT       │       │    ORGANISATION     │                        │
├─────────────────────┤       ├─────────────────────┤                        │
│ PK: id              │       │ PK: id              │                        │
│ contractNumber      │◄──────│ orgNumber           │                        │
│ FK: orgId ──────────┼──────►│ name                │                        │
│ wfStatus            │       │ applicationStatus   │                        │
│ lineItems[]         │       │ contactDetails[]    │                        │
│   └─ estimateId ────┼──────►│ identifiers[]       │                        │
└─────────┬───────────┘       │ functions[]         │                        │
          │                   │ orgAddress[]        │                        │
          │                   └─────────────────────┘                        │
          │                                                                  │
          │ 1:N (referenceId = contractNumber)                               │
          │                                                                  │
          ├──────────────────────┬──────────────────────┐                    │
          ▼                      ▼                      ▼                    │
┌─────────────────────┐ ┌─────────────────────┐ ┌─────────────────────┐      │
│    MUSTER ROLL      │ │    MEASUREMENT      │ │        BILL         │      │
├─────────────────────┤ ├─────────────────────┤ ├─────────────────────┤      │
│ PK: id              │ │ PK: id              │ │ PK: id              │      │
│ musterRollNumber    │ │ measurementNumber   │ │ billNumber          │      │
│ FK: referenceId ────┼─┼─► CONTRACT.number   │ │ FK: referenceId ────┼──────┘
│ registerId          │ │ measures[]          │ │ FK: payee.id ───────┼──► ORG.id
│ individualEntries[] │ │   └─ targetId ──────┼─┼─► CONTRACT.lineItem │
│   └─ attendanceEntries                      │ │ billDetails[]       │
│ musterRollStatus    │ │ wfStatus            │ │   └─ lineItems[]    │
└─────────────────────┘ │ documents[]         │ │   └─ payableItems[] │
                        └─────────────────────┘ │ wfStatus            │
                                                └─────────────────────┘
```

### 12.2 Key Linkages

| Source | Field | Target | Field |
|--------|-------|--------|-------|
| Estimate | projectId | Project | id |
| Contract | orgId | Organisation | id |
| Contract.lineItems | estimateId | Estimate | id |
| Contract.lineItems | estimateLineItemId | Estimate.estimateDetails | id |
| Bank Account | referenceId | Organisation | id |
| Attendance Register | referenceId | Contract | contractNumber |
| Attendance Register.attendees | individualId | Individual | id |
| Muster Roll | registerId | Attendance Register | id |
| Muster Roll | referenceId | Contract | contractNumber |
| Muster Roll.individualEntries | individualId | Individual | id |
| Measurement | referenceId | Contract | contractNumber |
| Measurement.measures | targetId | Contract.lineItems | contractLineItemRef |
| Bill | referenceId | Contract | contractNumber (partial) |
| Bill.billDetails.payee | identifier | Organisation | id |
| Bill.additionalDetails | mbValidationData.allMeasurementsIds | Measurement | measurementNumber |

### 12.3 Workflow Status Flow

```
PROJECT ──► ESTIMATE ──► CONTRACT ──► ATT. REGISTER ──► MUSTER ROLL ──► MEASUREMENT ──► BILL
(Active)   (Approved)   (Accepted)     (Active)         (Approved)      (Approved)     (Approved)
```

---

## 13. JSON Samples

### 13.1 Project Model (Complete)

```json
{
  "id": "3db48223-4506-4ba2-a8e6-f56599f2a38c",
  "tenantId": "od.testing",
  "projectNumber": "PJ/2025-26/006635",
  "name": "JAGAN NEW Project",
  "projectType": "CC",
  "projectSubType": "",
  "department": "",
  "description": "JAGAN NEW Project for Sujog",
  "referenceID": "REF-111",
  "documents": [
    {
      "id": "aa12013d-dfc6-42f6-aacc-d2ffa85d966e",
      "documentType": "PROJECT_PROPOSAL",
      "fileStoreId": "63de148c-8cb3-4e96-b326-fb08f4e689fb",
      "status": "ACTIVE",
      "additionalDetails": {
        "fileName": "project-payments-PJ_2024-25_003342.pdf"
      }
    }
  ],
  "address": {
    "id": "82dcb1f0-0333-4f3b-9370-7313ed0a7027",
    "latitude": 11,
    "longitude": 22,
    "city": "od.testing",
    "boundaryType": "Ward",
    "boundary": "W001"
  },
  "additionalDetails": {
    "creator": "SUJOG-WORKS-UAT",
    "locality": "L002",
    "dateOfProposal": 1766514599000,
    "targetDemography": "NSM",
    "estimatedCostInRs": "1000000"
  },
  "isDeleted": false,
  "rowVersion": 0,
  "auditDetails": {
    "createdBy": "81b1ce2d-262d-4632-b2a3-3e8227769a11",
    "createdTime": 1766486395896
  }
}
```

### 13.2 Estimate Detail Item (SOR)

```json
{
  "id": "870e1b49-9aa4-4b1d-8c54-b0661019da82",
  "sorId": "SOR_000407",
  "category": "SOR",
  "name": "Jr. progress recorder",
  "description": "Hi",
  "unitRate": 550,
  "noOfunit": 72,
  "uom": "NOs",
  "length": 3,
  "width": 2,
  "height": 12,
  "quantity": 1,
  "isDeduction": false,
  "amountDetail": [
    {
      "id": "02729b07-af64-485d-b767-557fb98611e3",
      "type": "EstimatedAmount",
      "amount": 39600,
      "isActive": true
    }
  ],
  "isActive": true
}
```

### 13.3 Contract Line Item

```json
{
  "id": "5b14ff1a-823b-43da-ba26-2664f8f59d2f",
  "estimateId": "15e24785-536d-4ab8-8b54-e370cc67e9f6",
  "estimateLineItemId": "870e1b49-9aa4-4b1d-8c54-b0661019da82",
  "contractLineItemRef": "5e1651de-1f3c-46ba-b966-e932323b4d93",
  "tenantId": "od.testing",
  "unitRate": 550,
  "noOfunit": 72,
  "status": "ACTIVE",
  "amountBreakups": [
    {
      "id": "5e25d2d8-ab98-49ae-9cef-fb49fafe7bd3",
      "estimateAmountBreakupId": "02729b07-af64-485d-b767-557fb98611e3",
      "amount": 39600,
      "status": "ACTIVE"
    }
  ]
}
```

### 13.4 Organisation (Summary)

```json
{
  "id": "c3a3d64b-621c-4609-b2ad-a99f3d361577",
  "tenantId": "od.testing",
  "name": "JAG Org",
  "orgNumber": "ORG-002128",
  "applicationNumber": "SR/ORG/22-12-2025/002128",
  "applicationStatus": "ACTIVE",
  "contactDetails": [
    {
      "contactName": "Jagankumar",
      "contactMobileNumber": "9996566433",
      "contactEmail": "jagan.kumar@egov.org.in"
    }
  ],
  "identifiers": [
    { "type": "PAN", "value": "AAAAA1111D", "isActive": true }
  ],
  "functions": [
    { "type": "CBO.MSG", "category": "CBO.NA", "class": "A" }
  ]
}
```

### 13.5 Bank Account

```json
{
  "id": "a487b407-c1cb-469c-be9a-0562cf4aeacf",
  "tenantId": "od.testing",
  "serviceCode": "ORG",
  "referenceId": "c3a3d64b-621c-4609-b2ad-a99f3d361577",
  "bankAccountDetails": [
    {
      "id": "ee6d1241-5d4d-444b-ae02-5eb2cca42173",
      "accountHolderName": "Jagan",
      "accountNumber": "********0111",
      "accountType": "SAVINGS",
      "isPrimary": true,
      "bankBranchIdentifier": {
        "type": "IFSC",
        "code": "ICIC*****36"
      },
      "isActive": true
    }
  ]
}
```

### 13.6 Attendance Register (Summary)

```json
{
  "id": "d71d6d8e-26f6-483f-b65b-390cedefc0bb",
  "registerNumber": "WR/2025-26/12/23/002411",
  "name": "JAGAN NEW Project",
  "referenceId": "WO/2025-26/002981",
  "serviceCode": "WORKS-CONTRACT",
  "status": "ACTIVE",
  "staff": [
    { "userId": "7ce51353-54ba-4282-badd-30ab3393e19c" }
  ],
  "attendees": [
    { "individualId": "517d11b4-...", "individualName": "jay" },
    { "individualId": "ebb60896-...", "individualName": "jagan" }
  ]
}
```

### 13.7 Individual (Summary)

```json
{
  "id": "517d11b4-210d-43bd-bc42-1806452ebb5d",
  "individualId": "IND-2024-08-29-003481",
  "name": { "givenName": "jay" },
  "dateOfBirth": "29/08/2003",
  "mobileNumber": "9876543234",
  "fatherName": "pathak",
  "identifiers": [
    { "identifierType": "AADHAAR", "identifierId": "********2372" }
  ],
  "skills": [
    { "type": "SOR_000369" },
    { "type": "SOR_000371" }
  ]
}
```

### 13.8 Measurement (Measure Item)

```json
{
  "id": "287d28d4-97ae-4b48-a982-011639804866",
  "referenceId": "b1eebb30-d6ab-437a-9967-fb5ed1875164",
  "targetId": "5e1651de-1f3c-46ba-b966-e932323b4d93",
  "length": 1,
  "breadth": 1,
  "height": 1,
  "numItems": 1,
  "currentValue": 1,
  "cumulativeValue": 1,
  "isActive": true,
  "additionalDetails": {
    "type": "SOR",
    "mbAmount": 550,
    "measureLineItems": [
      {
        "width": "1",
        "height": "1",
        "length": "1",
        "number": "1",
        "quantity": "1.0000",
        "measureSummary": "one week"
      }
    ]
  }
}
```

### 13.9 Muster Roll (Individual Entry)

```json
{
  "id": "50df17eb-ad94-4a23-aed3-5d6b373b3b9b",
  "individualId": "517d11b4-210d-43bd-bc42-1806452ebb5d",
  "actualTotalAttendance": 1,
  "attendanceEntries": [
    { "time": 1766428200000, "attendance": 1 },
    { "time": 1766341800000, "attendance": 0 }
  ],
  "additionalDetails": {
    "userId": "IND-2024-08-29-003481",
    "userName": "jay",
    "skillCode": "SOR_000371"
  }
}
```

### 13.10 Bill (Line Item)

```json
{
  "id": "5a09d1de-01e1-4ebe-b959-c1d69e2cfb26",
  "tenantId": "od.testing",
  "headCode": "MC",
  "amount": 1111,
  "type": "PAYABLE",
  "paidAmount": 0,
  "status": "ACTIVE"
}
```

---

## 14. Appendix

### 14.1 Status Codes

| Status | Description |
|--------|-------------|
| ACTIVE | Record is active and valid |
| APPROVED | Workflow approved |
| ACCEPTED | Contract accepted by contractor |
| INWORKFLOW | Currently in approval workflow |

### 14.2 Workflow Status Codes

| wfStatus | Used In | Description |
|----------|---------|-------------|
| APPROVED | Estimate, Muster Roll, Measurement, Bill | Approved by authority |
| ACCEPTED | Contract | Accepted by contractor |

### 14.3 Document Types

| Code | Description | Used In |
|------|-------------|---------|
| PROJECT_PROPOSAL | Initial project proposal document | Project |
| FINALIZED_WORKLIST | Finalized work list | Project |
| FEASIBILITY_ANALYSIS | Feasibility study document | Project |
| ESTIMATE_DOC_DESIGN_DOCUMENT | Design document for estimate | Estimate |
| WORKORDER_WORK_AGREEMENT | Work order agreement | Contract |
| VENDOR_INVOICE | Vendor invoice | Bill |
| img_measurement_book | Measurement book image | Measurement |

### 14.4 Category Types

| Code | Description |
|------|-------------|
| SOR | Schedule of Rates item |
| NON-SOR / NONSOR | Non-scheduled item |
| OVERHEAD | Overhead charges (SC, GST) |

### 14.5 Head Codes (Bill)

| Code | Type | Description |
|------|------|-------------|
| MC | PAYABLE | Material Cost |
| GST | PAYABLE | Goods and Service Tax |
| ITTDSOTI | DEDUCTION | Income Tax TDS |
| ITTDSOTI | PAYABLE | TDS Payable to Government |
| PURCHASE | PAYABLE | Net Purchase Amount |

### 14.6 Organisation Types

| Code | Description |
|------|-------------|
| CBO | Community Based Organization |
| CBO.MSG | CBO - MSG Type |
| CBO.NA | CBO - NA Category |

### 14.7 Business Services

| Code | Description |
|------|-------------|
| ESTIMATE | Estimate service |
| CONTRACT | Contract/Work Order service |
| EXPENSE.PURCHASE | Purchase bill service |
| WORKS-CONTRACT | Works contract service |
| ORG | Organisation service |

### 14.8 Account Types

| Code | Description |
|------|-------------|
| SAVINGS | Savings bank account |
| CURRENT | Current/checking account |

### 14.9 Identifier Types

| Code | Used In | Description |
|------|---------|-------------|
| PAN | Organisation | Permanent Account Number |
| AADHAAR | Individual | Aadhaar unique ID |
| IFSC | Bank Account | Bank branch identifier |

### 14.10 Number Formats

| Entity | Format | Example |
|--------|--------|---------|
| Project | `PJ/YYYY-YY/NNNNNN` | `PJ/2025-26/006635` |
| Estimate | `ES/YYYY-YY/NNNNNN` | `ES/2025-26/004597` |
| Contract | `WO/YYYY-YY/NNNNNN` | `WO/2025-26/002981` |
| Measurement | `MB/YYYY-YY/NNNNNN` | `MB/2025-26/001597` |
| Muster Roll | `MR/YYYY-YY/MM/DD/NNNNNN` | `MR/2025-26/12/23/001329` |
| Bill | `PB/YYYY-YY/NNNNNN` | `PB/2025-26/000567` |
| Organisation | `ORG-NNNNNN` | `ORG-002128` |
| Attendance Register | `WR/YYYY-YY/MM/DD/NNNNNN` | `WR/2025-26/12/23/002411` |
| Individual | `IND-YYYY-MM-DD-NNNNNN` | `IND-2025-12-22-005545` |

---

*Document generated for Works Management System reference architecture.*
*Version 1.2 - Updated with Bank Account, Attendance Register, and Individual models.*
