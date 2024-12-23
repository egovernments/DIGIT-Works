# Search Payment Instruction

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Search Payment Instruction to Generate Revised PI

### Actors <a href="#actors" id="actors"></a>

Employee

Role: Accountant

Home Page > Payment Instructions > Search Payment Instruction

### Details <a href="#details" id="details"></a>

1. Search Payment Instruction to be provided to list all the PIs which have the failed transaction and the revised PI to be generated for them.
2. Two types of searched to provided with 2 different tabs.
   1. Pending for Action
   2. Open Search
3. Below are the search parameters to search such payment instructions.

<table data-header-hidden><thead><tr><th width="73.66666666666666"></th><th width="166"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameters</td><td>Description</td></tr><tr><td>1</td><td>Ward</td><td>Drop-down, with the ward name as values.</td></tr><tr><td>2</td><td>Project Type</td><td>Project Types</td></tr><tr><td>3</td><td>Project Name</td><td>Name of project</td></tr><tr><td>4</td><td>Bill Number</td><td>Bill number</td></tr><tr><td>5</td><td>Status</td><td>Status of payment instructions. This parameter is not applicable for “Payment Instruction Pending for Correction”</td></tr><tr><td>6</td><td>Created from date</td><td>Payment instruction created date. This parameter is not applicable for “Payment Instruction Pending for Correction”</td></tr><tr><td>7</td><td>Created to date</td><td>Payment instruction created date. This parameter is not applicable for “Payment Instruction Pending for Correction”</td></tr></tbody></table>

#### Search Logic

1. “Pending for Action” tab is displayed by default with the search result of PIs which are pending for action. It means.
   1. The payment instructions which have the status Completed, Declined, and Pending.
   2. Additional condition for Completed PI, It should have at least one beneficiary payment status as Payment Failed .
2. Open Search, it will allow users to search any payment instruction and view the details.
   1. In this case, at least one parameter is must to search.
   2. For name, fuzzy search is enabled.
   3. Created from and To are considered as one parameters as date range.

#### Search Result

<table data-header-hidden><thead><tr><th width="79.66666666666666"></th><th width="247"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>Payment Instruction ID</td><td>Original/ Revised Payment Instruction ID. It is a hyperlink which opens the payment instruction to view the complete details.</td></tr><tr><td>2</td><td>Payment Instruction Date</td><td>Original/ Revised Payment Instruction Date.</td></tr><tr><td>3</td><td>No. of beneficiaries</td><td>Total number of beneficiaries for which payment is getting processed.</td></tr><tr><td>4</td><td>No. of successful Payments</td><td>Total number of successful payments.</td></tr><tr><td>5</td><td>No. of failed Payments</td><td>Total number of failed payments</td></tr><tr><td>6</td><td>Total Amount</td><td>Total amount of payment instruction which is to be paid.</td></tr><tr><td>7</td><td>Status</td><td>Status of payment instruction</td></tr></tbody></table>

### Validations <a href="#validations" id="validations"></a>

Not applicable

### Configurations <a href="#configurations" id="configurations"></a>

#### Master Data <a href="#masterdata" id="masterdata"></a>

Not applicable.

#### Attachments <a href="#attachments" id="attachments"></a>

Not applicable.

#### Workflow <a href="#workflow" id="workflow"></a>

Not applicable.

#### Actions <a href="#actions" id="actions"></a>

Not applicable.

#### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

## User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=7881-44052\&t=owlQidqRm0RNgQkr-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=7881-44052\&t=owlQidqRm0RNgQkr-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Search enables users to see the pending correction PI by default.
2. Search for any PI is also provided to search and view the details.
