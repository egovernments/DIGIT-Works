---
description: Creating a time extension request by employee
---

# EMP: Create Time Extension

## Context

A work order is created for an approved estimate in order to award the work to CBO. CBO starts the work to complete it within a given time period.

In case the organization comes to know that they are not in a position to complete the work within the given time frame due to various reasons, they need to inform the same to officer-in-charge of the project and apply for a time extension which is then subject to approval/ cancelling of work order based on the analysis done by the ULB.

## Scope <a href="#scope" id="scope"></a>

Request for time extension can be directly raised by CBO using the mobile application and by the officer-in-charge of the project on behalf of CBO using a web application. Once a request is raised it goes for verification and approval.

Home > Work Orders > Inbox > Search Work Order > View Work Order > Request Time Extension (From Action Menu)

## Functional Details <a href="#details" id="details"></a>

1. Work order to be revised to extend the completion period.
2. The request to revise the work order for the extension of time can be created by the CBO/officer-in-charge.

<table><thead><tr><th width="70">#</th><th width="167">Field</th><th width="143">Data Type</th><th width="148">Required</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Work order number</td><td>Display Only</td><td>NA</td><td>Work order no.</td></tr><tr><td>2</td><td>Project ID</td><td>Display Only</td><td>NA</td><td>Project ID of the project.</td></tr><tr><td>3</td><td>Project sanction date</td><td>Display Only</td><td>NA</td><td>Date of the proposal from the project.</td></tr><tr><td>4</td><td>Project name</td><td>Display Only</td><td>NA</td><td>Project name</td></tr><tr><td>5</td><td>Project description</td><td>Display Only</td><td>NA</td><td>Project description</td></tr><tr><td>8</td><td><strong>Work Order Details</strong></td><td><strong>Tab</strong></td><td> </td><td> </td></tr><tr><td>9</td><td>Name of CBO</td><td>Display Only</td><td>NA</td><td>The name of the CBO as per original WO.</td></tr><tr><td>10</td><td>CBO ID</td><td>Display Only</td><td>NA</td><td>The CBO ID as per original WO.</td></tr><tr><td>11</td><td>Role of CBO</td><td>Display Only</td><td>NA</td><td>The role of the CBO IA/IP as per original WO.</td></tr><tr><td>12</td><td>Name of the officer in-charge</td><td>Display Only</td><td>NA</td><td>Name of officer in-charge as per original WO.</td></tr><tr><td>13</td><td>Designation of officer in-charge</td><td>Display Only</td><td>NA</td><td>Designation of officer in-charge as per original WO.</td></tr><tr><td>14</td><td>Project completion period</td><td>Display Only</td><td>NA</td><td>Number of days work to be completed as per original WO.</td></tr><tr><td>15</td><td>Work Start Date</td><td>Display Only</td><td>NA</td><td>Work start date as per original WO.</td></tr><tr><td>16</td><td>Work End Date</td><td>Display Only</td><td>NA</td><td>Work end date as per original WO.</td></tr><tr><td>17</td><td>Work order amount</td><td>Display Only</td><td>NA</td><td>Total estimated cost as per original WO.</td></tr><tr><td>18</td><td>Extension requested</td><td>Numeric</td><td>Y</td><td>Time requested to extend in days.</td></tr><tr><td>19</td><td>Reason for Extension</td><td>Text-area</td><td>Y</td><td>The reason of time extension to be captured here, it should not be more than 250 characters.</td></tr><tr><td>20</td><td>Terms and Conditions</td><td>Display Only/ Tab</td><td>NA</td><td>Terms and conditions as per original WO.</td></tr></tbody></table>

## Validations

On Request Time Extension

1. In case the first muster roll is pending to submit for approval.

`Time extension request can not be created. Please ensure that no muster roll is pending for submission.`

2\. In case the first muster roll itself is not created.

`Time extension request can not be created. Please ensure that the first muster roll is submitted for approval.`

3\. In case a project closure request is already created.

`Time extension request can not be created. A closure request has already been created for the selected project.`

## Notification

Not applicable.

## Action

On submit, create and forward workflow pop-up window is displayed.

On Create and Forward,

1. The success page is displayed.
2. The time Extension request number is generated as per the specified format. TE/2022-23/000021.
3. The time extension request is forwarded to the verifier in the workflow.

## User Interface

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=6705-47564\&t=8SVsHQhTGuDo3QW4-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=6705-47564\&t=8SVsHQhTGuDo3QW4-4)

## Acceptance Criteria

1. Modification of work order is allowed to extend the time.
2. A time extension request number is generated to identify the request.
3. The link between original and revised work orders is maintained.



&#x20;
