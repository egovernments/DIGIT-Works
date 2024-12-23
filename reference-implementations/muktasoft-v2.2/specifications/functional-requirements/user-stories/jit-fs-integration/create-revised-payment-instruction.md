# Create Revised Payment Instruction

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Re-push revised PI for failed transactions

### Actors <a href="#actors" id="actors"></a>

Employee

Role: Accountant

Home Page → Billing → Search Bills → Open Bill → Generate Revised PI

### Details <a href="#details" id="details"></a>

1. Failed Transaction Correction (COR) is the API to push revised PI to JIT.
2. A new PI is generated at MUKTASoft to push it as revised PI.
3. MUKTA accountant login in MUKTASoft open the bill screen and initiate revised PI.
4. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
5. The response data is stored and maintained at MUKTASoft for every COR call.
6. API is called with an action is triggered by user from View Payment Instruction Page.

#### API Request

<table data-header-hidden><thead><tr><th width="68.66666666666666"></th><th width="193"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>2</td><td>jitCorBillNo</td><td>Revised PI ID of the PI generated at MUKTASoft to re-push failed transactions into JIT</td></tr><tr><td>3</td><td>jitCorBillDate</td><td>Revised PI date of the PI generated in MUKTASoft to re-push failed transactions into  JIT</td></tr><tr><td>4</td><td>jitCorBillDeptCode</td><td>Application code, e.g. MUKTA.</td></tr><tr><td>5</td><td>jitOrgBillRefNo</td><td>Original viz. first (parent) bill reference no. of online bill which was received in the PAG response.</td></tr><tr><td>6</td><td>jitOrgBillNo</td><td>Original viz. first (parent) MUKTASoft PI ID.</td></tr><tr><td>7</td><td>jitOrgBillDate</td><td>Original viz. first (parent) MUKTASoft PI date.</td></tr><tr><td> </td><td>Beneficiaries Details</td><td> </td></tr><tr><td>8</td><td>benefId</td><td>Beneficiary ID, Beneficiary id of the failed transaction.</td></tr><tr><td>9</td><td>jitCurBillRefNo</td><td>Latest failed transaction bill reference number viz. The PI for which failure is reported (Original/ Revised)</td></tr><tr><td>10</td><td>orgAccountNo</td><td>Original Account Number, The one which was pushed in first PI.</td></tr><tr><td>11</td><td>orgIfsc</td><td>Original IFSC, , The one which was pushed in first PI.</td></tr><tr><td>12</td><td>correctedAccountNo</td><td>Corrected account number, recent corrected account number corrected for this revised PI.</td></tr><tr><td>13</td><td>correctedIfsc</td><td>Corrected IFSC, recent corrected IFSC corrected for this revised PI.</td></tr><tr><td>14</td><td>curAccountNo</td><td>Current account number which was pushed in just previous PI for which failure is reported.</td></tr><tr><td>15</td><td>curIfsc</td><td>Current IFSC which was pushed in just previous PI for which failure is reported.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="86.66666666666666"></th><th width="163"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>2</td><td>jitCorBillNo</td><td>Revised PI ID, created in MUKTASoft and then pushed to JIT</td></tr><tr><td>3</td><td>jitCorBillDate</td><td>Revised PI date, created in MUKTASoft and then pushed to JIT</td></tr><tr><td>4</td><td>successCode</td><td>0 - Success</td></tr><tr><td>5</td><td>sucessDescrp</td><td>JIT Correction Bill is received successfully ,Forwarded to Treasury officer to generate Return adjust Bill</td></tr></tbody></table>

#### No Response

1. PI is created and saved at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of COR API: No response is received.]
3. PI status at MUKTASoft updated to Pending.
4. Beneficiary payment status changes to “Payment Pending”.
5. Option to re-push the PI is provided, and the same time system will try to push all such PI once in a day at 9PM every day.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of COR API: \<JIT error message>]
3. PI status at MUKTASoft is updated to Declined.
4. Beneficiary’s payment status will change to “Payment Pending”.
5. Option to re-push the PI after clear the error is provided from View Payment Instruction Page.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of COR API: Response is received and updated successfully]
3. PI status at the MUKTASoft changes to In Process.
4. Beneficiary’s payment status will change to Payment In Process.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="75"></th><th></th><th></th><th></th><th width="113"></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td> </td><td>Pending</td><td>Payment Pending</td><td>Resubmit</td><td>COR</td></tr><tr><td>2</td><td>Response With Error</td><td>Pending</td><td>Declined</td><td>Payment Pending</td><td>Resubmit</td><td>COR</td></tr><tr><td>3</td><td>Response Without Error</td><td>Pending/ Decline</td><td>In Process</td><td>Payment In Process</td><td>No Action</td><td> </td></tr></tbody></table>

Note: PIS and PAG calls are skipped for revised PI.

### Validations <a href="#validations" id="validations"></a>

1. Make sure the payment instruction ID is unique and no PI has already been pushed with same PI ID. \[Message: Duplicate payment instruction ID.]
2. Make sure PI doesn’t have duplicate beneficiary. i.e. same a/c and ifsc cannot be repeated. \[Message: There are 2 or more than 2 beneficiaries having same account number and IFSC.]
3. Beneficiaries original account no /IFSC/Bifid is not matching with correction file – Make sure parameter values passed are correct. \[Message: The beneficiary \<paymentid> was not present in the original payment instruction.]

#### Configurations <a href="#configurations" id="configurations"></a>

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

#### ID Generation <a href="#idgeneration" id="idgeneration"></a>

The format used for Original Payment instruction is followed.

## User Interface <a href="#userinterface" id="userinterface"></a>

View Payment Instruction

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Revised PI is generated and pushed to JIT.
2. All the validations are taken care which are applicable to Original PI.
3. PI ID is generated as per the format defined or original PI.
4. If the PI is declined, the same one can be modified and re-pushed.
5. The response is captured in MUKTASoft for debugging and error reporting.
6. Technical glitched in the integration are defined as error and captured.
7. System keep trying until a response is received. The latest response is recorded in the log.
