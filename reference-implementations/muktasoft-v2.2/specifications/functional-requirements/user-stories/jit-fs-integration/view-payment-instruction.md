# View Payment Instruction

## Context

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Payment Instruction

View Payment Instruction

### Actors <a href="#actors" id="actors"></a>

Employee

Role: Accountant

### Details <a href="#details" id="details"></a>

1. View Payment Instruction to be provided to view the detail and track the status of Original/ Revised Payment Instructions.
2. Below is the details which is displayed for a payment instruction.

#### Attributes

<table data-header-hidden><thead><tr><th width="71.66666666666666"></th><th width="179"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Parameter</td><td>Description</td></tr><tr><td>1</td><td>Bill Number</td><td>Hyperlink to view the bill details.</td></tr><tr><td>2</td><td>Payment Instruction Type</td><td>Payment instruction type, Original or Revised.</td></tr><tr><td>3</td><td>Parent Payment Instruction ID</td><td>Parent payment instruction id, i.e. original PI ID. It is a hyperlink to view the Original Payment Instruction Details.</td></tr><tr><td>4</td><td>Payment Instruction ID</td><td>Payment Instruction ID.</td></tr><tr><td>5</td><td>Payment Instruction Date</td><td>Payment Instruction Date.</td></tr><tr><td>6</td><td>Head of Account</td><td>Head of account from which amount to be paid</td></tr><tr><td>7</td><td>Master Allotment ID</td><td>Master allotment ID from which amount to be paid</td></tr><tr><td>8</td><td>Status</td><td>Status of payment instruction. A tool-tip is displayed to display the error message for declined and rejected PIs.</td></tr><tr><td>9</td><td>Payment Advice Details</td><td> </td></tr><tr><td>10</td><td>Payment Advice ID</td><td>Payment advice ID generated for the original/ revised PI.</td></tr><tr><td>11</td><td>Payment Advice Date</td><td>Payment advice generation date.</td></tr><tr><td>12</td><td>Token Number</td><td>Token no. generated for the payment instruction</td></tr><tr><td>13</td><td>Token Date</td><td>Token no. generated for the payment instruction</td></tr><tr><td>14</td><td>Online Bill Number</td><td>Online bill number for the online bill generated for the payment advice.</td></tr><tr><td> </td><td><em>Error/ Info</em> Section</td><td>A information display band to display the error message/ info messages</td></tr><tr><td> </td><td>Beneficiary Details</td><td>Tabular form</td></tr><tr><td>15</td><td>Beneficiary ID</td><td>It is individual ID of wage seeker/ organization ID for CBOs and vendors, and displayed as hyperlink to view details.</td></tr><tr><td>16</td><td>Payment ID</td><td>Unique beneficiary ID for the payment through JIT. It remain same though the process until the payment becomes successful.</td></tr><tr><td>17</td><td>Beneficiary Name</td><td>Name of the beneficiary.</td></tr><tr><td>18</td><td>Account Number</td><td>Account number of beneficiary, only last 4 digits are displayed. Remaining initial digits of A/C are masked.</td></tr><tr><td>19</td><td>IFSC</td><td>IFSC code of the bank/ branch of beneficiary accounts.</td></tr><tr><td>20</td><td>Payment Status</td><td>The payment status, as per the definition. A tooltip is shown to display the error message for failed payments.</td></tr><tr><td>21</td><td>Payment Amount</td><td>The payment amount of beneficiary.</td></tr><tr><td> </td><td>Info</td><td>In case of there are failed payments, an information is displayed. “Please make sure all the necessary corrections are made before generating the revised payment instruction for JIT” .</td></tr></tbody></table>

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

1. For Pending and Declined Payment Instruction.

Re-submit - Payment instruction is re-push again.

1. A success toast message is displayed on successful submission and the status of PI changes to Initiated. \[\*The payment instruction is submitted successfully.\*]
2. In case, the PI is decline again, the toast message is displayed with the error message and the status of PI remain Declined. \[\*Submission failed, \<error message>\*]

2\. For Completed Payment Instruction which has at least one failed payment.

Generate Revised PI - A revised PI is generated and push to JIT after clearing all the errors.

1. A success toast message is displayed on successful submission with the PI ID displayed in message. \[\*Revised payment instruction \<PIID> generated and submitted successfully.\*]
2. In case revised PI is declined, a failure toast message is displayed with the PI ID generated for revised PI. \[\*Revised payment instruction \<PIID> generated successfully, but failed to submit.\*]

#### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

## User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=7881-44243\&t=owlQidqRm0RNgQkr-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=7881-44243\&t=owlQidqRm0RNgQkr-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Payment instruction details is displayed as described in the story.
2. Actions are enabled based on the status of current payment instruction.
3. Payment instruction id is generated according to format defined in case revise PI is generated.
4. Appropriate messages are displayed with each action.
