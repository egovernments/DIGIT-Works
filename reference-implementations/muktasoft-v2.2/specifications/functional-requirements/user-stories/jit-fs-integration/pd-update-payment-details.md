# PD: Update payment details

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Status Update for Payment Status

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Payment Details (PD) is the API to fetch the payment details from JIT.
2. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
3. The response data is stored and maintained at MUKTASoft for every PD call.
4. The API call to be scheduled once in a day at 10 PM for In Process payment instruction.

#### API Request

<table data-header-hidden><thead><tr><th width="95.66666666666666"></th><th width="146"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>finYear</td><td>Financial year received in the APIs response of PAG.</td></tr><tr><td>2</td><td>ExtAppName</td><td>The name of the application from which the call is being made.</td></tr><tr><td>3</td><td>billRefNo</td><td>Online bill reference number received in the APIs response of PAG.</td></tr><tr><td>4</td><td>tokenNumber</td><td>Token number received in the APIs response of PAG.</td></tr><tr><td>6</td><td>tokenDate</td><td>Token date received in the APIs response of PAG.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="83.66666666666666"></th><th width="175"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>billNumber</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>2</td><td>billDate</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>3</td><td>voucherNumber</td><td>Voucher number of the voucher created in JIT to maintain the accounting.</td></tr><tr><td>4</td><td>voucherDate</td><td>The voucher creation date of the voucher created in JIT-FS for payment.</td></tr><tr><td>5</td><td>billRefNo</td><td>Online bill reference number generated in JIT and received in PAG response.</td></tr><tr><td>6</td><td>paymentStatus</td><td>Payment status Message Received from RBI at the time of Debit Scroll.</td></tr><tr><td>7</td><td>tokenNumber</td><td>Token number generated in JIT and received in PAG response.</td></tr><tr><td>8</td><td>tokenDate</td><td>Token date of the token generated in JIT and received in PAG response.</td></tr><tr><td> </td><td>Debit Scroll Details</td><td> </td></tr><tr><td>9</td><td>benfAcctNo</td><td>Beneficiary’s account number.</td></tr><tr><td>10</td><td>benfBankIfscCode</td><td>Beneficiary’s bank / branch IFSC.</td></tr><tr><td>11</td><td>utrNo</td><td>Unique transaction number used by banks to reconcile the transaction.</td></tr><tr><td>12</td><td>utrDate</td><td>The date of transaction which is used by bank.</td></tr><tr><td>13</td><td>endToEndId</td><td>End to end id generated to identify a particular beneficiary for a transaction while it is pushed to CPC for payment clearance.</td></tr><tr><td>14</td><td>benfId</td><td>Beneficiary unique ID, unique to transaction.</td></tr></tbody></table>

#### No Response

1. Error message displayed on View Payment Instruction Page. \[Message: On call of PD API: No response is received.]
2. PI status at MUKTASoft remain unchanged to In Process.
3. All beneficiaries payment status remain unchanged to Payment In Process.
4. Option to refresh the status is provided. It triggers call of PD.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of PD API: \<JIT error message>]
3. PI status at MUKTASoft remain unchanged to In Process.
4. All beneficiaries payment status remain unchanged to Payment In Process.
5. Option to refresh the status is provided. It triggers call of PD.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of PD API: Response is received and updated successfully]
3. PI status at the MUKTASoft changes to Completed.
4. The beneficiaries payment status is changed to “Payment Successful”.
5. No action is enabled.
6. SMS notification is sent to all the beneficiaries.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="72"></th><th></th><th></th><th width="119"></th><th width="115"></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td>In Process</td><td>In Process</td><td>Payment In Process</td><td>Refresh</td><td>PD</td></tr><tr><td>2</td><td>Response With Error</td><td>In Process</td><td>In Process</td><td>Payment In Process</td><td>Refresh</td><td>PD</td></tr><tr><td>3</td><td>Response Without Error</td><td>In Process</td><td>Completed</td><td>Payment Successful</td><td>No Action</td><td> </td></tr></tbody></table>

### Validations <a href="#validations" id="validations"></a>

Not applicable.

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

To all the beneficiaries:

`Payment of Rs. {amount} for MUKTA, transaction <UTRNO> is made to your registered bank account. Contact your bank if not credited within 72 hours. MUKTA - Govt. of Odisha.`

## User Interface <a href="#userinterface" id="userinterface"></a>

View Payment Instruction

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. API is called and status is fetched and updated for both PI and Beneficiary.
2. SMS notification is sent to all the beneficiaries.
3. Status is reflected in View Payment Instruction Page.
4. The response is captured in MUKTASoft for debugging and error reporting.
5. Technical glitched in the integration are defined as error and captured.
6. System keep trying until a response is received. The latest response is recorded in the log.
