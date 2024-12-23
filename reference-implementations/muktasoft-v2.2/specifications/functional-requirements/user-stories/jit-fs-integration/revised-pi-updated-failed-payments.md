# Revised PI: Updated Failed Payments

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Status Update for Payment Failed Status from revised PI

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Failed transaction failed payment (FTFPS) is the API to fetch the failed payment details from JIT.
2. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
3. The response data is stored and maintained at MUKTASoft for every FTFPS call.
4. The API call to be scheduled once in a day at 10 PM for Completed payment instructions.

#### API Request

<table data-header-hidden><thead><tr><th width="84.66666666666666"></th><th width="144"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>jitCorBillNo</td><td>Revised PI ID generated in MUKTASoft and pushed for failed transactions into JIT</td></tr><tr><td>2</td><td>jitCorBillDate</td><td>Revised PI date generated in MUKTASoft and pushed for failed transactions into  JIT</td></tr><tr><td>3</td><td>billRefNo</td><td>Online bill reference number for revised/current PI</td></tr><tr><td>4</td><td>tokenNumber</td><td>Token number generated in JIT.</td></tr><tr><td>5</td><td>tokenDate</td><td>Token date generated in JIT.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="73.66666666666666"></th><th width="177"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>jitCorBillNo</td><td>Revised PI ID generated in MUKTASoft and pushed for failed transactions into JIT</td></tr><tr><td>2</td><td>jitCorBillDate</td><td>Revised PI date generated in MUKTASoft and pushed for failed transactions into JIT</td></tr><tr><td>3</td><td>billRefNo</td><td>Online bill reference number for the current revised PI.</td></tr><tr><td>4</td><td>tokenNumber</td><td>Token number generated in JIT.</td></tr><tr><td>5</td><td>tokenDate</td><td>Token date generated in JIT.</td></tr><tr><td> </td><td><strong>Beneficiary Details</strong></td><td> </td></tr><tr><td>7</td><td>benfAcctNo</td><td>Beneficiary’s account number.</td></tr><tr><td>8</td><td>benfBankIfscCode</td><td>Beneficiary’s bank / branch IFSC.</td></tr><tr><td>9</td><td>utrNo</td><td>Unique transaction reference number used by banks to reconcile the transaction.</td></tr><tr><td>10</td><td>utrDate</td><td>UTR date.</td></tr><tr><td>11</td><td>endToEndId</td><td>It is generated to identify a beneficiary for a transaction while it is pushed to CPC for payment clearance.</td></tr><tr><td>12</td><td>orgBillRefNumber</td><td>Original online bill reference no.</td></tr><tr><td>13</td><td>challanNumber</td><td>Challan number</td></tr><tr><td>14</td><td>challanDate</td><td>Challan date</td></tr><tr><td>15</td><td>failedReason</td><td>Account related errors, Like account that doesn't exist.</td></tr><tr><td>16</td><td>benfId</td><td>Beneficiary unique ID, unique to transaction.</td></tr></tbody></table>

#### No Response

1. Error message displayed on View Payment Instruction Page. \[Message: On call of FTFPS API: No response is received.]
2. PI status at MUKTASoft remain unchanged to Completed.
3. Beneficiaries payment status remain unchanged to Payment Successful.
4. No action is enabled.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of FTFPS API: \<JIT error message>]
3. PI status at MUKTASoft remain unchanged to Completed.
4. Beneficiaries payment status remain unchanged to Payment Successful.
5. No action is enabled.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of FTFPS API: Response is received and updated successfully]
3. PI status at the MUKTASoft remain unchanged to Completed.
4. The beneficiaries for which details is received in response, the payment status changes to Payment Failed.
5. Option to generate revised PI is enabled. It triggered the COR API call.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="75"></th><th width="108"></th><th width="118"></th><th width="120"></th><th width="119"></th><th width="115"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td>Completed</td><td>Completed</td><td>Payment Successful</td><td>No Action</td><td> </td></tr><tr><td>2</td><td>Response With Error</td><td>Completed</td><td>Completed</td><td>Payment Successful</td><td>No Action</td><td> </td></tr><tr><td>3</td><td>Response Without Error</td><td>Completed</td><td>Completed</td><td>Payment Failed</td><td>Generate Revised PI</td><td>COR</td></tr></tbody></table>

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

To beneficiary for which failure details is received:

`Payment of Rs. {amount} for MUKTA, transaction <UTRNO> is failed. Contact MUKTA Cell at ULB for more detail. MUKTA - Govt. of Odisha.`

## User Interface <a href="#userinterface" id="userinterface"></a>

View Payment Instruction

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. API is called and payment status is fetched and updated for the beneficiaries.
2. SMS notification is sent to beneficiary for failed transaction.
3. Status is reflected in View Payment Instruction Page.
4. The response is captured in MUKTASoft for debugging and error reporting.
5. Technical glitched in the integration are defined as error and captured.
6. System keep trying until a response is received. The latest response is recorded in the log.
