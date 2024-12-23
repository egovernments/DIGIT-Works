# Fund Allocation Register

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Fund Allocation Register

Reports → Fund Allocation Register

### Actors <a href="#actors" id="actors"></a>

Employee

Role: Fund Allocation View

### Details <a href="#details" id="details"></a>

1. Virtual Allotment Details (VA) is the API to fetch the fund allocation details.
2. MUKTA as a scheme has multiple HOAs for which fund is sanctioned and allocated.
3. Fund Allocation Register has to be developed to see and download the details.
4. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
5. This data is stored and maintained at MUKTASoft for every financial year and used for reporting and reference purposes.
6. APIs to be triggered daily at 10 PM.

Search Parameters

<table data-header-hidden><thead><tr><th width="60.66666666666666"></th><th width="172"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Field</td><td>Description</td></tr><tr><td>1</td><td>Financial Year</td><td>Financial year, by default current financial year is selected.</td></tr><tr><td>2</td><td>Head of Account</td><td>HOAs from the configuration.</td></tr><tr><td>3</td><td>Transaction Type</td><td>Allotment types are, 1) Initial Allotment 2) Additional Allotment 3) Withdrawal 4) Expense 5) Expense Reversed</td></tr></tbody></table>

**Note:** Financial Year is mandatory to select, by default current financial year is selected and records are searched.

Search Result

<table data-header-hidden><thead><tr><th width="74.66666666666666"></th><th width="192"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Field</td><td>Description</td></tr><tr><td>1</td><td>HOA</td><td>Head of accounts of MUKTA</td></tr><tr><td>2</td><td>Transaction Number</td><td>Transaction number of the transactions fetched from JIT or created in MUKTASoft.</td></tr><tr><td>3</td><td>Transaction Date</td><td>Date of transaction received from JIT-FS in a response of API call or the MUKTASoft PI creation date. Date to be taken care when calling the API again.</td></tr><tr><td>4</td><td>Transaction Type</td><td><p>A transaction type would be anything from the options given below.</p><ol start="1"><li>Initial Allotment</li><li>Additional Allotment</li><li>Withdrawal</li><li>Expense</li><li>Expense (Reversed)<br>First 3 are received from JIT-FS system through API call while the last one is from MUKTASoft when a PI is pushed. A reverse entry of the Expense is made in the case PI is canceled or failed to create.</li></ol></td></tr><tr><td>5</td><td>Transaction Amount</td><td>It is transaction amount.</td></tr><tr><td>6</td><td>Sanctioned Balance</td><td>It is the balance remaining from the sanctioned amount and calculated as given below.<br>Sanctioned Balance = Total Sanctioned Amount - Sum of all the allotments.</td></tr><tr><td>7</td><td>Fund Available</td><td>It the fund available for the expenditure and calculated as given below.<br>Fund Available = Sum of all the allotments - (Sum of all the expenditure + Sum of all the withdrawal)</td></tr></tbody></table>

### Validations <a href="#validations" id="validations"></a>

1. While fetching the details, from date should be taken care properly.
2. The records/transactions are sorted in chronological order.

### Configurations <a href="#configurations" id="configurations"></a>

#### Master Data <a href="#masterdata" id="masterdata"></a>

The master data which needs to be configured.

1. Special Spending Unit
   1. SSU ID
   2. SSU Name
   3. Grantee Code
   4. DDO Code
   5. Tenant ID
2. Head of Accounts
   1. Code
   2. Name

* 221705789358641045908 (MUKTA -  SC Component)&#x20;
* 221705800358641045908 (MUKTA -  General Component)
* 221705796358641045908 (MUKTA -  ST Component)

#### Attachments <a href="#attachments" id="attachments"></a>

Not applicable.

#### Workflow <a href="#workflow" id="workflow"></a>

Not applicable.

#### Actions <a href="#actions" id="actions"></a>

Search - Fetch and display the records based on the search parameters.

Clear - Clear the search parameters.

Download - Option to download the report into PDF format is provided as per the attached format.

#### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

## User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=7881-44052\&t=owlQidqRm0RNgQkr-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=7881-44052\&t=owlQidqRm0RNgQkr-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Data fetched is stored for reporting and reference purpose.
2. Report is developed with the option to download the it into PDF.
