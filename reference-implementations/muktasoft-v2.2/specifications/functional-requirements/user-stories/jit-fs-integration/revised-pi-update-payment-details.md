# Revised PI: Update payment details

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Status Update for Successful Payment Details of Revised PIs

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Success Payment Details (FTPS) is the API to fetch the payment details of revised PI from JIT.
2. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
3. The response data is stored and maintained at MUKTASoft for every FTPS call.
4. The API call to be scheduled once in a day at 10 PM for In Process payment instruction.

#### API Request

<table data-header-hidden><thead><tr><th width="86.66666666666666"></th><th width="179"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>jitCorBillNo</td><td>PI ID of revised PI generated in MUKTASoft to re-push failed transactions into  JIT</td></tr><tr><td>2</td><td>jitCorBillDate</td><td>PI date of revised PI generated in MUKTASoft to re-push failed transactions into  JIT</td></tr><tr><td>3</td><td>jitCorBillDeptCode</td><td>Application code.</td></tr><tr><td>4</td><td>jitOrgBillRefNo</td><td>Original online bill reference no. in JIT while payment failed first time.</td></tr><tr><td>5</td><td>jitOrgBillNo</td><td>Original Payment Instruction ID in MUKTASoft while payment failed first time.</td></tr><tr><td>6</td><td>jitOrgBillDate</td><td>Original Payment Instruction Date in MUKTASoft while payment failed first time.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="79.66666666666666"></th><th width="172"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>jitCorBillNo</td><td>Revised PI ID generated in MUKTASoft and push failed transactions into  JIT</td></tr><tr><td>2</td><td>jitCorBillDate</td><td>Revised PI date generated in MUKTASoft and push failed transactions into  JIT</td></tr><tr><td>3</td><td>jitOrgBillRefNo</td><td>Original, the first online bill reference number generated in JIT.</td></tr><tr><td>4</td><td>voucherNumber</td><td>The voucher number of voucher generated in JIT to maintain the accounting.</td></tr><tr><td>5</td><td>voucherDate</td><td>The voucher creation date for the voucher created in JIT for payment.</td></tr><tr><td>6</td><td>billRefNo</td><td>Online bill reference number for revised PI/ Current PI.</td></tr><tr><td>7</td><td>paymentStatus</td><td>Payment status, message received from RBI at the time of Debit Scroll.</td></tr><tr><td>8</td><td>tokenNumber</td><td>Token number generated in JIT.</td></tr><tr><td>9</td><td>tokenDate</td><td>Token date generated in JIT.</td></tr><tr><td> </td><td><strong>Beneficiary Details</strong></td><td> </td></tr><tr><td>10</td><td>benfAcctNo</td><td>Beneficiary’s account number.</td></tr><tr><td>11</td><td>benfBankIfscCode</td><td>Beneficiary’s bank / branch IFSC.</td></tr><tr><td>12</td><td>utrNo</td><td>Unique transaction reference number used by banks to reconcile the transaction.</td></tr><tr><td>13</td><td>utrDate</td><td>UTR date.</td></tr><tr><td>14</td><td>endToEndId</td><td>It is generated to identify a beneficiary for a transaction while it is pushed to CPC for payment clearance.</td></tr><tr><td>15</td><td>benfId</td><td>Beneficiary unique ID, unique to transaction.</td></tr></tbody></table>

#### No Response

1. Error message displayed on View Payment Instruction Page. \[Message: On call of FTPS API: No response is received.]
2. PI status at MUKTASoft remain unchanged to In Process.
3. All beneficiaries payment status remain unchanged to Payment In Process.
4. Option to refresh the status is provided. It triggers call of FTPS.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of FTPS API: \<JIT error message>]
3. PI status at MUKTASoft remain unchanged to In Process
4. All beneficiaries payment status remain unchanged to Payment In Process
5. Option to refresh the status is provided. It triggers call of FTPS.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of FTPS API: Response is received and updated successfully]
3. PI status at the MUKTASoft changes to Completed.
4. The beneficiaries payment status is changed to Payment Successful.
5. No action is enabled.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="61"></th><th></th><th></th><th width="116"></th><th width="116"></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td>In Process</td><td>In Process</td><td>Payment In Process</td><td>Refresh</td><td>FTPS</td></tr><tr><td>2</td><td>Response With Error</td><td>In Process</td><td>In Process</td><td>Payment In Process</td><td>Refresh</td><td>FTPS</td></tr><tr><td>3</td><td>Response Without Error</td><td>In Process</td><td>Completed</td><td>Payment Successful</td><td>No Action</td><td> </td></tr></tbody></table>

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
