# Works PDF

## Overview

Works-PDF is an intermediary service that bridges the gap between the pdf-service and client requests. Within works-pdf, there are several APIs available for producing various PDFs. This service gathers data from multiple sources, amalgamates it, and then forwards the request to the PDF service for PDF generation.

Further, Works PDF can produce an Excel file when a payment is initiated. It actively listens to the "expense-payment-create" topic, capturing relevant payment data to trigger this process.

### Dependency&#x20;

* MDMS
* PDF
* Project
* Estimates
* Muster Roll&#x20;
* Contract
* Organization
* Localization
* Expense
* Expense calculator
* Bank account
* Filestore

### Key Functionalities

* The system retrieves data from various sources to create multiple PDFs such as estimates, muster reports, and project documents.
* When a payment is generated, it produces Excel files corresponding to the payment bills. This is triggered by a payment creation event communicated through a topic.
* Users also have the option to recreate the Excel files for a specific payment using the same payment ID. This functionality allows for the regeneration of payment-related Excel documents.

### Code

[Works-PDF](https://github.com/egovernments/DIGIT-Works/tree/master/utilities/works-pdf)

## Deployment

* [Helm Chart](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/utilities/works-pdf)
* [Environment](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/environments/works-dev.yaml)&#x20;
  * Add all pdf configurations under pdf-service data config and format config
  * Add file type 'application/zip' under `xlsx` format in `egov-filestore` `allowed-file-formats-map`.

## PDF Configuration

* Estimates PDF - [Data Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/data-config/estimate.json), [Format Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/format-config/estimate.json)
* Muster Roll PDF - [Data Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/data-config/nominal-muster-roll.json), [Format Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/format-config/nominal-muster-roll.json)
* Project PDF - [Data Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/data-config/project-detail.json), [Format Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/format-config/project-detail.json)
* Work order PDF - [Data Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/data-config/work-order.json), [Format Config](https://github.com/egovernments/works-configs/blob/UAT/pdf-service/format-config/work-order.json)

## Role Action&#x20;

<table><thead><tr><th width="222.33333333333331">API EndPoints</th><th>Roles</th></tr></thead><tbody><tr><td>/egov-pdf/download/estimate/estimates</td><td><p>ESTIMATE_CREATOR</p><p>ESTIMATE_VERIFIER</p><p>TECHNICAL_SANCTIONER</p><p>ESTIMATE_APPROVER</p><p>ESTIMATE_VIEWER</p></td></tr><tr><td>/egov-pdf/download/musterRoll/muster-roll</td><td><p>MUSTER_ROLL_APPROVER</p><p>ORG_ADMIN</p><p>MUSTER_ROLL_VERIFIER</p></td></tr><tr><td>/egov-pdf/download/project/project-details</td><td>PROJECT_VIEWER</td></tr><tr><td>/egov-pdf/download/workOrder/work-order</td><td><p>ORG_ADMIN</p><p>WORK_ORDER_VIEWER</p><p>WORK_ORDER_APPROVER</p></td></tr><tr><td>/egov-pdf/bill/_generate</td><td>BILL_ACCOUNTANT</td></tr><tr><td>/egov-pdf/bill/_search</td><td>BILL_ACCOUNTANT</td></tr></tbody></table>

## Create Account For Deductions

Create accounts for purchase and wage bills, for head codes where the category is deduction.&#x20;

Example: Below is the head code object:

```
{
      "id": "5",
      "code": "LC",
      "category": "deduction",
      "service": "works.purchase",
      "description": "Labour Cess",
      "active": true,
      "effectiveFrom": 1682164954037,
      "effectiveTo": null
}
```

Where tenantId is pg.citya

The format of referenceId is `Deduction_{tanentId}_{headcode}`

Then create a bank account with the field `referenceId` value `Deduction_pg.citya_LC`
