# Expense

## Overview

The Expense service allows users to capture the details for expense bills and payments.

## API Specifications

**Base path**: `/expense/bill/`

### API Contract Link

The API specification is available [here](https://github.com/egovernments/DIGIT-Specs/blob/master/works/expense-contract.yml). To view it in the Swagger editor, click below.

{% embed url="https://editor.swagger.io/?url=https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/works/expense-contract.yml" %}

## Data Model&#x20;

### DB Schema Diagram

<figure><img src="https://github.com/egovernments/DIGIT-Works/blob/master/backend/expense/src/main/resources/db-diagram-expense.png?raw=true" alt=""><figcaption></figcaption></figure>

### Web Sequence Diagrams

TBD

### Persister

Persister configuration: [Expense persister](https://github.com/egovernments/works-configs/blob/UAT/egov-persister/expense-bill-payment-persister.yaml)

### Indexer

Indexer configuration: [Expense indexer](https://github.com/egovernments/works-configs/blob/UAT/egov-indexer/expensebill-indexer.yml)

Index Name: expense-bill-index

## Related Topics

* [Functional specifications - Expense](../../../../specifications/functional-specifications/expenditure-billing.md)
* [Expense service configuration](../../../../setup/configure-works/service-configuration/expense.md)
* Expense UI configuration - for MuktaSoft
* Expense user manual
