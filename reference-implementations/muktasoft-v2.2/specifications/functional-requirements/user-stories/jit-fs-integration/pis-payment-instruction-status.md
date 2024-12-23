# PIS: Payment Instruction Status

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Status Update API Call

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Payment instruction status (PIS) is the API to fetch the PI status from JIT.
2. Once a PI is accepted at JIT, it is then approved with a digital sign by SSU in JIT.
3. The approved ones only has the Payment Instruction ID and Date available in response.
4. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
5. The response data is stored and maintained at MUKTASoft for every PIS call.
6. The API call to be scheduled once in a day at 10:00PM every day for the Payment Instructions Initiated, and the Payment Instructions Declined having error “_Duplicate Payment Information Id_”.

#### API Request

<table data-header-hidden><thead><tr><th width="71"></th><th width="126"></th><th width="135"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>1</td><td>jitBillNo</td><td>Yes</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>2</td><td>jitBillDate</td><td>Yes</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>3</td><td>ssuIaId</td><td>Yes</td><td>Special spending unit ID. A master value maintained in JIT-FS.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="71.66666666666666"></th><th width="163"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>pmtInstId</td><td>The unique id of payment instruction that’s been generated at JIT.</td></tr><tr><td>2</td><td>payInstDate</td><td>The date of payment instruction created.</td></tr><tr><td>3</td><td>jitBillNumber</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>4</td><td>jitBillDate</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>5</td><td>ssuIaId</td><td>Special spending unit ID. A master value maintained in JIT-FS.</td></tr><tr><td>6</td><td>billNetAmount</td><td>Net bill amount sent in the PI</td></tr><tr><td>7</td><td>billGrossAmount</td><td>Gross bill amount sent in the PI</td></tr><tr><td>8</td><td>schemeCode</td><td>Scheme code under which PI is created</td></tr><tr><td>9</td><td>totalNumberOfBeneficiary</td><td>Total beneficiary count</td></tr></tbody></table>

#### No Response

1. Error message displayed on View Payment Instruction Page. \[Message: On call of PIS API: No response is received.]
2. PI status at MUKTASoft remain unchanged to Initiated.
3. All beneficiaries payment status remain unchanged to Payment Initiated.
4. Option is given to user to refresh the status. On refresh API call is triggered.

#### Response With Error (Others)

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of PIS API: \<JIT error message>]
3. PI status at MUKTASoft remain unchanged to Initiated.
4. All beneficiaries payment status remain unchanged to Payment Initiated.
5. Option is given to user to refresh the status. On refresh API call is triggered.

#### Response With Error (Rejected)

1. If the PI is rejected by SSU user, the same is received in error message.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of PIS API: \<JIT error message>]
3. PI status at MUKTASoft changes to Rejected.
4. All beneficiaries payment status changes to NA.
5. A reverse expense transaction is recorded under Fund Allocation Register.
6. Option to generate new PI is provided from View Payment Instruction Page.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of PIS API: Response is received and updated successfully]
3. PI status at the MUKTASoft changes to Approved.
4. All beneficiaries payment status remain unchanged to Payment Initiated.
5. Option to refresh the status is provided. It triggers call of PAG.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="70"></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td>Initiated</td><td>Initiated</td><td>Payment Initiated</td><td>Refresh</td><td>PIS</td></tr><tr><td>2</td><td>Response with Error Others</td><td>Initiated</td><td>Initiated</td><td>Payment Initiated</td><td>Refresh</td><td>PIS</td></tr><tr><td>3</td><td>Response with Error Rejected</td><td>Initiated</td><td>Rejected</td><td>Payment Initiated</td><td>Generate New PI</td><td>PI</td></tr><tr><td>4</td><td>Response Without Error</td><td>Initiated</td><td>Approved</td><td>Payment Initiated</td><td>Refresh</td><td>PAG</td></tr></tbody></table>

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

Not applicable

## User Interface <a href="#userinterface" id="userinterface"></a>

View Payment Advice

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. There is scheduler running to fetch the PI status and updated at MUKTASoft.
2. Status is updated based on response received.
3. Both the statuses are reflected in View Payment Instruction.
4. Option to Generate Revised PI is given to user through View Payment Instruction Page.
5. The response is captured in MUKTASoft for debugging and error reporting.
6. Technical glitched in the integration are defined as error and captured.
7. System keep trying based on schedule until a response is received. The latest response is recorded in the log.
