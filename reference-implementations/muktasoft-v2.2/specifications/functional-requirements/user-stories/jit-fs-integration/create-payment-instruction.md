# Create Payment Instruction

## Context

The integration with JIT start with the payment instruction. Hence the entity we create at MUKTASoft to push into JIT as Payment Instruction will also be called Payment Instruction.

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Auto Generation

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Payment instruction (PI) is the API to push the PI details into JIT.
2. For failed transactions, revised PI is generated and then pushed into JIT.
3. For each bill one PI is prepared and pushed into JIT.
4. PI is prepared and pushed with approval of Bill (Wage Bill, Purchase Bill, Supervision Bill).
5. To generate a PI, selection of HOA logic is given under configuration section.
6. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
7. The response data is stored and maintained at MUKTASoft for each PI and revised PI.
8. Once a PI is generated can be searched and viewed using search and view PI feature.

#### Selection of HOA

1. System performs a check to decide on the HOA, It picks a HOA first out of three HOAs and check for the fund available for all the sanction orders one by one and when found sufficient fund is available, create PI.
2. HOAs are scanned in the sequence given below. Sequence of HOA to be selected should be configurable.
   1. SC Head
   2. ST Head
   3. General Head

#### API Request

<table data-header-hidden><thead><tr><th width="68"></th><th width="178"></th><th width="123"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>2</td><td>jitBillNo</td><td>Yes</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>3</td><td>jitBillDate</td><td>Yes</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>4</td><td>jitBillDdoCode</td><td>Yes</td><td>The code of DDO from the configuration.</td></tr><tr><td>5</td><td>granteeAgCode</td><td>Yes</td><td>Grantee code from the configuration.</td></tr><tr><td>6</td><td>schemeCode</td><td>Yes</td><td>MUKTA scheme code</td></tr><tr><td>7</td><td>hoa</td><td>Yes</td><td>Head of account from which payment to be made.</td></tr><tr><td>8</td><td>ssuIaId</td><td>Yes</td><td>Special spending unit id from the configuration.</td></tr><tr><td>9</td><td>mstAllotmentDistId</td><td>Yes</td><td>Virtual allotment parent ID/sanction ID from which payment to be made.</td></tr><tr><td>10</td><td>billNetAmount</td><td>Yes</td><td>PI net amount of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>11</td><td>billGrossAmount</td><td>Yes</td><td>PI gross amount of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>12</td><td>billNumberOfBenf</td><td>Yes</td><td>The count of beneficiaries in the payment instruction.</td></tr><tr><td>13</td><td>purpose</td><td>Yes</td><td>Purpose is the reference text. E.g. Muster Roll ID etc. for which the payment instruction is created.</td></tr><tr><td> </td><td>Beneficiary Details</td><td>Array</td><td>In a single request multiple beneficiaries can be added.</td></tr><tr><td>14</td><td>benefId</td><td>Yes</td><td>The beneficiary's Payment ID, unique for each beneficiary for its payment. Payment of the beneficiary is tracked by this throughout the payment processing. It is generated with the PI generation.</td></tr><tr><td>15</td><td>benefName</td><td>Yes</td><td>Beneficiary name maintained in MUKTASoft.</td></tr><tr><td>16</td><td>benfAcctNo</td><td>Yes</td><td>Beneficiary’s bank  account number maintained in MUKTASoft.</td></tr><tr><td>17</td><td>ifscCode</td><td>Yes</td><td>IFSC of bank branch from beneficiary’s accounts details.</td></tr><tr><td>18</td><td>benfMobileNo</td><td>Yes</td><td>Beneficiary's mobile number maintained in MUKTASoft.</td></tr><tr><td>19</td><td>benfAddress</td><td>Yes</td><td>Beneficiary’s address maintained in MUKTASoft.</td></tr><tr><td>20</td><td>accountType</td><td>Yes</td><td>Account type of beneficiary’s account maintained in MUKTASoft</td></tr><tr><td>21</td><td>paymentAmount</td><td>Yes</td><td>Amount payable to beneficiary.</td></tr><tr><td>22</td><td>panNo</td><td>No</td><td>PAN of beneficiary</td></tr><tr><td>23</td><td>adhaarNumber</td><td>No</td><td>Aadhaar of beneficiary</td></tr><tr><td>24</td><td>purpose</td><td>Yes</td><td>Purpose is the reference text. E.g. Muster Roll ID etc. for which the bill is created.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="69.66666666666666"></th><th width="155"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>jitBillNo</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>2</td><td>jitBillDate</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>3</td><td>ssuIaId</td><td>Special spending unit ID. A master value maintained in JIT-FS.</td></tr><tr><td>4</td><td>successCode</td><td>0 - for successfully accepting the PI.</td></tr><tr><td>7</td><td>sucessDescrp</td><td>Jit Bill is received successfully ,Payment Instruction will be generated after Bill is submitted by SSU in JIT-FS</td></tr></tbody></table>

#### No response

1. PI is created and saved at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of PI API: No response is received.]
3. PI status at MUKTASoft changes to Pending.
4. Beneficiary payment status update to “Payment Pending”.
5. Option to re-push the PI is provided, and the same time system will try to push all such PI once in a day at 9PM every day.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of PI API: \<JIT error message>]
3. PI status at MUKTASoft updated/changes to Declined.
4. Beneficiary payment status updated to “Payment Pending”.
5. Option to re-push the PI is provided, necessary correction is made to encounter the error and PI is re-pushed.

#### Response Without Error

1. Success response is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of PI API: Response is received and updated successfully]
3. PI status at the MUKTASoft changes to Initiated.
4. Beneficiary payment status changes to “Payment Initiated”.
5. An expense transaction is recorded under Fund Allocation Register.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="76"></th><th width="116"></th><th></th><th></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td> </td><td>Pending</td><td>Payment Pending</td><td>Resubmit</td><td>PI</td></tr><tr><td>2</td><td>Response with Error</td><td>Pending</td><td>Declined</td><td>Payment Pending</td><td>Resubmit</td><td>PI</td></tr><tr><td>3</td><td>Response Without Error</td><td>Pending/ Decline</td><td>Initiated</td><td>Payment Initiated</td><td>No Action</td><td> </td></tr></tbody></table>

### Validations <a href="#validations" id="validations"></a>

1. Make sure DDO Code and SSUID are passed into requests as per the configuration. In case configuration is missing. \[Message: DDO and SSUID configuration is missing.]
2. Make sure Net or Gross amount of Payment Instruction is not more than the total allotted amount for SSU a HOA and Sanctioned ID. \[Message: Insufficient fund.]
3. Make sure the payment instruction ID is unique and no PI has already been pushed with same PI ID. \[Message: Duplicate payment instruction ID.]
4. Make sure number of beneficiaries mentioned in the header should not mismatch with the actual details. \[Message: Number of beneficiaries provided in header doesn’t match with the details.]
5. Make sure amount mentioned in the net amount should not mismatch with the total of all the beneficiaries amount. \[Message: The total net amount provided in hear doesn’t match with total of all the beneficiaries.]
6. Make sure Gorss amount is either equal to or more than Net Amount and none of them can be zero. \[Message: Gross amount can not be less than the net amount.]
7. Make sure at least one beneficiary is included in PI. \[Message: Beneficiary detail is missing.]
8. Make sure total net amount is equal to sum of all the beneficiaries’ payment amount. \[Message: The total net amount provided in header doesn’t match with total of all the beneficiaries.]
9. Make sure PI doesn’t have duplicate beneficiary. i.e. same a/c and ifsc cannot be repeated. \[Message: There are 2 or more than 2 beneficiaries account number and IFSC are same.]
10. Beneficiaries original account no /IFSC/Bifid is not matching with correction file – Make sure parameter values passed are correct. \[Message: The beneficiary \<paymentid> was not present in the original payment instruction.]

### Configurations <a href="#configurations" id="configurations"></a>

#### Master Data <a href="#masterdata" id="masterdata"></a>

Status are configured as master data.

PI Status

1. Pending
2. Declined
3. Initiated
4. Rejected
5. Approved
6. In Process
7. Completed

Payment Status - Beneficiary’s payment status.

1. Payment Pending
2. Payment Initiated
3. Payment In Process
4. Payment Successful
5. Payment Failed

### Attachments <a href="#attachments" id="attachments"></a>

Not applicable.

### Workflow <a href="#workflow" id="workflow"></a>

Not applicable.

### Actions <a href="#actions" id="actions"></a>

Resubmit (\*On View Payment Instruction)\*

PI is re-constructed, availability of fund is checked and push and the response is updated back.

Scheduler: Same time a scheduler running every day at 10PM will try to push such PIs which are created with status Pending.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### ID Generation <a href="#idgeneration" id="idgeneration"></a>

PI ID is generated following the format given below.

PI-\<ULBCODE>/FY/<6 digit running sequence number>. E.g. PI-DK/2022-23/000023.

* The Six digit running sequence no. should be running for ULB wise.
* It has to be reset to 1 with start of every financial year. i.e. on 01/04 00:00AM

Payment transaction ID is generated for each beneficiary, which is unique for the every transaction. There is not specific format.

## User Interface <a href="#userinterface" id="userinterface"></a>

View Payment Instruction.

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Make sure the the availability of fund is checked before pushing the payment instruction into JIT.
2. PI is generated for each and every bill and pushed to JIT with the approval of bill.
3. All the validations are taken care.
4. PI ID is generated as per the format defined.
5. If the PI is declined, the same PI can be modified and re-pushed.
6. The response is captured in MUKTASoft for debugging and error reporting.
7. Technical glitched in the integration are defined as error and captured.
8. System keep trying until a response is received. The latest response is recorded in the log.
