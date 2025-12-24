# Works Management System - Data Models Documentation

**Version:** 1.0  
**Date:** December 2025  
**Project:** JAGAN NEW Project (Sujog)

---

## 1. Overview

The Works Management System consists of three core data models that form the backbone of public works project management. These models track projects from initial proposal through contract execution and payment processing.

### 1.1 Model Relationships

```
┌─────────────┐      ┌─────────────┐      ┌─────────────┐
│   PROJECT   │──────│   ESTIMATE  │──────│   CONTRACT  │
│             │      │             │      │             │
│ PJ/2025-26/ │      │ ES/2025-26/ │      │ WO/2025-26/ │
│ 006635      │      │ 004597      │      │ 002981      │
└─────────────┘      └─────────────┘      └─────────────┘
      │                    │                    │
      │                    │                    │
   projectId ◄─────────────┘                    │
                     estimateId ◄───────────────┘
```

### 1.2 Summary Table

| Model | Identifier | Status | Amount |
|-------|------------|--------|--------|
| Project | `PJ/2025-26/006635` | Active | ₹10,00,000 (Estimated) |
| Estimate | `ES/2025-26/004597` | Approved | ₹50,366.91 |
| Contract | `WO/2025-26/002981` | Accepted | ₹50,367 |

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

## 5. Data Model Relationships

### 5.1 Entity Relationship Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            WORKS MANAGEMENT SYSTEM                          │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────┐
│      PROJECT        │
├─────────────────────┤
│ PK: id              │
│ projectNumber       │
│ name                │
│ projectType         │
│ description         │
│ address             │
│ documents[]         │
│ additionalDetails   │
│ auditDetails        │
└─────────┬───────────┘
          │
          │ 1:N
          ▼
┌─────────────────────┐
│      ESTIMATE       │
├─────────────────────┤
│ PK: id              │
│ FK: projectId ──────┼──────► PROJECT.id
│ estimateNumber      │
│ wfStatus            │
│ estimateDetails[]   │
│ additionalDetails   │
│ auditDetails        │
└─────────┬───────────┘
          │
          │ 1:N
          ▼
┌─────────────────────┐
│      CONTRACT       │
├─────────────────────┤
│ PK: id              │
│ contractNumber      │
│ orgId ──────────────┼──────► ORGANIZATION.id
│ wfStatus            │
│ lineItems[]         │
│   └─ estimateId ────┼──────► ESTIMATE.id
│   └─ estimateLineId ┼──────► ESTIMATE_DETAIL.id
│ documents[]         │
│ additionalDetails   │
│ auditDetails        │
└─────────────────────┘
```

### 5.2 Key Linkages

| Source | Field | Target | Field |
|--------|-------|--------|-------|
| Estimate | projectId | Project | id |
| Contract.lineItems | estimateId | Estimate | id |
| Contract.lineItems | estimateLineItemId | Estimate.estimateDetails | id |
| Contract.lineItems.amountBreakups | estimateAmountBreakupId | Estimate.estimateDetails.amountDetail | id |

---

## 6. JSON Samples

### 6.1 Project Model (Complete)

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

### 6.2 Estimate Detail Item (SOR)

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

### 6.3 Contract Line Item

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

---

## 7. Appendix

### 7.1 Status Codes

| Status | Description |
|--------|-------------|
| ACTIVE | Record is active and valid |
| APPROVED | Workflow approved |
| ACCEPTED | Contract accepted by contractor |

### 7.2 Document Types

| Code | Description |
|------|-------------|
| PROJECT_PROPOSAL | Initial project proposal document |
| FINALIZED_WORKLIST | Finalized work list |
| FEASIBILITY_ANALYSIS | Feasibility study document |
| ESTIMATE_DOC_DESIGN_DOCUMENT | Design document for estimate |
| WORKORDER_WORK_AGREEMENT | Work order agreement |

### 7.3 Category Types

| Code | Description |
|------|-------------|
| SOR | Schedule of Rates item |
| NON-SOR | Non-scheduled item |
| OVERHEAD | Overhead charges (SC, GST) |

---

*Document generated for Works Management System reference architecture.*
