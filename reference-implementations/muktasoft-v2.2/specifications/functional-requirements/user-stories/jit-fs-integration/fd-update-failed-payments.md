# FD: Update Failed Payments

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Status Update for Payment Failed Status

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Failed Details (FD) is the API to fetch the failed payment details from JIT.
2. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
3. The response data is stored and maintained at MUKTASoft for every FD call.
4. The API call to be scheduled once in a day at 10 PM for Completed payment instructions.

#### API Request

<table data-header-hidden><thead><tr><th width="68.66666666666666"></th><th width="166"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>2</td><td>billno</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>3</td><td>billDate</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>4</td><td>adviceId</td><td>Payment advice ID, generated at JIT-FS</td></tr><tr><td>5</td><td>onlineBillRefNo</td><td>Online bill reference no. generated at JIT and received with PAG response.</td></tr><tr><td>6</td><td>tokenNo</td><td>Token number, generated at JIT-FS</td></tr><tr><td>7</td><td>tokenDate</td><td>Token number, generated at JIT-FS</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="85.66666666666666"></th><th width="177"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>2</td><td>billNumber</td><td>Bill number of the bill created in MUKTASoft and then pushed to JIT-FS as payment instruction.</td></tr><tr><td>3</td><td>billDate</td><td>Bill date of the bill created in MUKTASoft and then pushed to JIT-FS as payment instruction.</td></tr><tr><td>4</td><td>voucherNo</td><td>Voucher generated at JIT-FS for maintaining the accounting.</td></tr><tr><td>5</td><td>voucherDate</td><td>Voucher date.</td></tr><tr><td>6</td><td>billRefNo</td><td>Online bill reference number generated at JIT and received with PAG response.</td></tr><tr><td>7</td><td>tokenNumber</td><td>Token number generated at JIT-FS</td></tr><tr><td>8</td><td>tokenDate</td><td>Token generation date</td></tr><tr><td> </td><td>benfDtls</td><td> </td></tr><tr><td>10</td><td>benfAcctNo</td><td>Beneficiary’s account number.</td></tr><tr><td>11</td><td>benfBankIfscCode</td><td>Beneficiary’s bank / branch IFSC.</td></tr><tr><td>12</td><td>utrNo</td><td>Unique transaction number used by banks to reconcile the transaction.</td></tr><tr><td>13</td><td>utrDate</td><td>UTR date.</td></tr><tr><td>14</td><td>endToEndId</td><td>End to end id generated to identify a particular beneficiary for a transaction while it is pushed to CPC for payment clearance.</td></tr><tr><td>15</td><td>orgBillRefNumber</td><td>Original online bill reference number, In first failure case billRefNo and orgBillRefNumber are same.</td></tr><tr><td>16</td><td>challanNumber</td><td>Challan number of the challan created to put the amount into suspense account</td></tr><tr><td>17</td><td>challanDate</td><td>Challan generation date</td></tr><tr><td>18</td><td>failedReason</td><td>Reason for failure</td></tr><tr><td>19</td><td>benfId</td><td>Beneficiary unique ID, unique to transaction.</td></tr></tbody></table>

#### No Response

1. Error message displayed on View Payment Instruction Page. \[Message: On call of FD API: No response is received.]
2. PI status at MUKTASoft remain unchanged to Completed.
3. Beneficiaries payment status remain unchanged to Payment Successful.
4. No action is enabled.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of FD API: \<JIT error message>]
3. PI status at MUKTASoft remain unchanged to Completed.
4. Beneficiaries payment status remain unchanged to Payment Successful.
5. No action is enabled.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of FD API: Response is received and updated successfully]
3. PI status at the MUKTASoft remain unchanged to Completed.
4. The beneficiaries for which details is received in response, the payment status changes to Payment Failed.
5. SMS notification is sent to all the beneficiaries.
6. Option to generate revised PI is enabled. It triggered the COR API call.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="62"></th><th></th><th width="116"></th><th width="120"></th><th width="117"></th><th width="112"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td>Completed</td><td>Completed</td><td>Payment Successful</td><td>No Action</td><td> </td></tr><tr><td>2</td><td>Response With Error</td><td>Completed</td><td>Completed</td><td>Payment Successful</td><td>No Action</td><td> </td></tr><tr><td>3</td><td>Response Without Error</td><td>Completed</td><td>Completed</td><td>Payment Failed</td><td>Generate Revised PI</td><td>COR</td></tr></tbody></table>

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

1. API is called and status is fetched and updated for beneficiaries.
2. SMS notification is sent to beneficiary for failed transaction.
3. Status is reflected in View Payment Instruction Page.
4. The response is captured in MUKTASoft for debugging and error reporting.
5. Technical glitched in the integration are defined as error and captured.
6. System keep trying until a response is received. The latest response is recorded in the log.
