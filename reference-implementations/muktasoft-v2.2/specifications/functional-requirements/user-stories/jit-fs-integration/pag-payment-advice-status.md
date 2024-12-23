# PAG: Payment Advice Status

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

Status Update for Payment Advice

### Actors <a href="#actors" id="actors"></a>

Employee

Role: System

### Details <a href="#details" id="details"></a>

1. Payment Advice Status (PAG) is the API to fetch the payment advice status from JIT.
2. Once a PI is accepted at JIT, it is then approved with a digital sign by SSU in JIT.
3. For approved PI then payment advice is generated.
4. For Request/ Response parameters, please refer the [integration approach document](https://docs.google.com/document/d/1U7yYfJ86vK71KRJ09LPtGHe64kcMaNHZi_gpwtsq3oU/edit#heading=h.ke6q7c75vkyz).
5. The response data is stored and maintained at MUKTASoft for every PAG call.
6. The API call to be scheduled once in a day at 10:15 PM every day for the Approved payment instructions.

#### API Request

<table data-header-hidden><thead><tr><th width="77.66666666666666"></th><th width="134"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>pmtInstId</td><td>Payment instruction ID of the payment instruction generated at JIT</td></tr><tr><td>2</td><td>pmtInstDate</td><td>Payment instruction date of the payment instruction generated at JIT</td></tr><tr><td>3</td><td>billNo</td><td>Payment Instruction ID of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>4</td><td>billDate</td><td>Payment Instruction date of the payment instruction created in MUKTASoft and then pushed to JIT.</td></tr><tr><td>5</td><td>ssuIaId</td><td>Special spending unit ID. A master value maintained in JIT-FS.</td></tr></tbody></table>

#### API Response

<table data-header-hidden><thead><tr><th width="76.66666666666666"></th><th width="173"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>2</td><td>billNo</td><td>Bill number of the bill created in MUKTASoft and then pushed to JIT-FS as payment instruction.</td></tr><tr><td>3</td><td>ssuIaId</td><td>Special spending unit ID. A master value maintained in JIT-FS.</td></tr><tr><td>4</td><td>finYear</td><td>The financial year for which bill was created.</td></tr><tr><td>5</td><td>adviceId</td><td>Unique Id generated after successfully submission of Advice for payment in JIT.</td></tr><tr><td>6</td><td>adviceDate</td><td>Advice date of payment advice generated in JIT.</td></tr><tr><td>7</td><td>onlineBillRefNo</td><td>Auto generated online bill Reference number in JIT.</td></tr><tr><td>8</td><td>tokenNumber</td><td>Token number generated automatically after auto submitted bill to treasury</td></tr><tr><td>9</td><td>tokenDate</td><td>Token date of the token generated automatically.</td></tr><tr><td> </td><td><strong>Beneficiary List</strong></td><td> </td></tr><tr><td>10</td><td>benfName</td><td>Beneficiary name</td></tr><tr><td>11</td><td>benfAccountNo</td><td>Beneficiary account number</td></tr><tr><td>12</td><td>benfBankIfscCode</td><td>Beneficiary’s IFSC which was pushed through PI.</td></tr><tr><td>13</td><td>amount</td><td>Amount to be paid to the beneficiary.</td></tr><tr><td>14</td><td>benfId</td><td>Beneficiary unique ID which was generated for PI.</td></tr></tbody></table>

#### No Response

1. Error message displayed on View Payment Instruction Page. \[Message: On call of PAG API: No response is received.]
2. PI status at MUKTASoft remain unchanged to Approved.
3. All beneficiaries payment status remain unchanged to Payment Initiated.
4. Option to refresh the status is provided. It triggers call of PAG.

#### Response With Error

1. Error message is stored at MUKTASoft.
2. Error message displayed on View Payment Instruction Page. \[Message: On call of PAG API: \<JIT error message>]
3. PI status at MUKTASoft remain unchanged to Approved.
4. All beneficiaries payment status remain unchanged to Payment Initiated.
5. Option to refresh the status is provided. It triggers call of PAG.

#### Response Without Error

1. Success message is received and same is stored at MUKTASoft.
2. Info message displayed on View Payment Instruction Page. \[Message: On call of PAG API: Response is received and updated successfully]
3. PI status at the MUKTASoft changes to In Process.
4. All beneficiaries payment status remain unchanged to Payment In Process.
5. Option to refresh the status is provided. It triggers call of PD.

#### API Call - Status - Payment Status - Actions Mapping

<table data-header-hidden><thead><tr><th width="85"></th><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Response</td><td>PI Status<br>(From)</td><td>PI Status<br>(To)</td><td>Payment Status</td><td>User Action</td><td>API Call</td></tr><tr><td>1</td><td>No Response</td><td>Approved</td><td>Approved</td><td>Payment Initiated</td><td>Refresh</td><td>PAG</td></tr><tr><td>2</td><td>Response With Error</td><td>Approved</td><td>Approved</td><td>Payment Initiated</td><td>Refresh</td><td>PAG</td></tr><tr><td>3</td><td>Response Without Error</td><td>Approved</td><td>In Process</td><td>Payment In Process</td><td>Refresh</td><td>PD</td></tr></tbody></table>

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

View Payment Instruction

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Status is fetched and update in MUKTASoft for both PI and Beneficiary.
2. The response is captured in MUKTASoft for debugging and error reporting.
3. Technical glitched in the integration are defined as error and captured.
4. System keep trying until a response is received. The latest response is recorded in the log.
