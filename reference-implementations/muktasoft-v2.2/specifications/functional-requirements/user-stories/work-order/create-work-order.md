# Create Work Order

## Context

Once an estimate is prepared and approved, the next step is to award the work to a contractor, to decide the various methods used like Tendering, Quotation and Nomination. Once a contractor is decided a work order is created in the favor of the contractor.

In MUKTA, it is a nomination method to decide a CBO (community-based organization) and then the work order is created in the name of that organization.

## Scope

Create Work Order

Search Estimate → View Estimate → Create Work Order.

## Actors

Employee

Role: Work Order Creator

## Details

1. CBO to whom the work is awarded is decided offline and then the work order is created in the name of CBO.
2. **Create Work Order** form is developed as per the UI design provided and the attributes listed below.
3. To create the work order estimate is searched and opened to view the details. From the action menu **Create Work Order** is selected.

## Attributes

<table data-header-hidden><thead><tr><th width="75"></th><th></th><th width="143"></th><th width="105"></th><th></th></tr></thead><tbody><tr><td><strong>#</strong></td><td><strong>Field</strong></td><td><strong>Data Type</strong></td><td><strong>Required</strong> </td><td><strong>Description</strong></td></tr><tr><td>1</td><td>Project ID</td><td>Display Only</td><td>NA</td><td>Project ID of the project.</td></tr><tr><td>2</td><td>Date of proposal</td><td>Display Only</td><td>NA</td><td>Date of the proposal from the project.</td></tr><tr><td>3</td><td>Project name</td><td>Display Only</td><td>NA</td><td>Project name</td></tr><tr><td>4</td><td>Project description</td><td>Display Only</td><td>NA</td><td>Project description</td></tr><tr><td>5</td><td><strong>Work Order Details</strong></td><td><strong>Tab</strong></td><td> </td><td> </td></tr><tr><td>6</td><td>Name of CBO</td><td>Drop-down</td><td>Y</td><td><ol><li>Organization type community based organization from the organization master maintained at the ULB level are only allowed.</li><li>Only <strong>Active</strong> organizations and the organization valid to date is above work order created date are listed under drop-down or allowed to search.</li><li>The name is searchable in the drop-down and search is start with min 3 characters has to be entered.</li><li>Search is performed for the CBOs registered within the ULB.</li></ol></td></tr><tr><td>7</td><td>CBO ID</td><td>Display</td><td>Y</td><td>The CBO ID from the organization registry.</td></tr><tr><td>8</td><td>Role of CBO</td><td><p>Drop-down</p><p>(Auto- selected)</p></td><td>Y</td><td><p>The role of the CBO is decided based on the estimated amount. It is configurable in the system.</p><ol><li>IP (Implementation Partner) - If the estimated cost of the works is more than Rs.15 Lakhs</li><li>IA (Implementation Agency) -  If the estimated cost of the works is up to Rs.15 Lakhs</li></ol></td></tr><tr><td>9</td><td>Name of the officer in-charge</td><td>Drop-down</td><td>Y</td><td><ol><li>The drop-down values are population based on the role assigned.</li><li>The name is searchable in the drop-down with min 3 characters entered. Name + Designation;</li><li>Search is performed within the employees having the role <strong>OFFICER_IN_CHARGE</strong>.</li></ol></td></tr><tr><td>10</td><td>Designation of officer in-charge</td><td>Display</td><td>Y</td><td>Displayed from the EIS/User’s record saved in the system.</td></tr><tr><td>11</td><td>Project completion period (in days)</td><td>Numeric</td><td>Y</td><td><p>Number of days work to be completed.</p><p>Min Value: <strong>1 day</strong>.</p></td></tr><tr><td>12</td><td>Work order amount</td><td>Read Only</td><td>Y</td><td>Total estimated Amount - Overhead Amount (Sum of all which are not a work value)</td></tr><tr><td>13</td><td><strong>Labour and Material Analysis</strong></td><td> </td><td> </td><td> </td></tr><tr><td>14</td><td>Labour Analysis</td><td>View Document</td><td>Y</td><td>The labour analysis file attached to estimate to be displayed here.</td></tr><tr><td>15</td><td>Material Analysis</td><td>View Document</td><td>Y</td><td>The material analysis file attached to estimate to be displayed here.</td></tr><tr><td>16</td><td><em><strong>Relevant Documents</strong></em></td><td><strong>Sections</strong></td><td> </td><td> </td></tr><tr><td>17</td><td>BOQs</td><td>File Attachment</td><td>Y</td><td>Allows single file, not greater than 5 MB. Files can be of type doc,docx, xls,xlsx pdf, jpg.</td></tr><tr><td>18</td><td>Terms and conditions</td><td>File Attachment</td><td>N</td><td>Allows single file, not greater than 5 MB. Files can be of type doc,docx, xls,xlsx pdf, jpg.</td></tr><tr><td>19</td><td>Others</td><td>Textbox</td><td>N</td><td>To capture the file name</td></tr><tr><td>20</td><td> </td><td>File Attachment</td><td>N</td><td>To attach the file file the name entered above in the textbox.Allows single file, not greater than 5 MB. Files can be of type doc,docx, xls,xlsx pdf, jpg.</td></tr><tr><td>21</td><td><strong>Terms and Conditions</strong></td><td><strong>Tab</strong></td><td> </td><td> </td></tr><tr><td>22</td><td>Description</td><td>Alphanumeric</td><td>N</td><td>Grid of textbox to enter the terms and conditions as bulleted list.</td></tr></tbody></table>

### Validations

1. Field-level validations as mentioned in the attribute tables.
2. Organization-type community-based organizations from the organization master maintained at the ULB level are only allowed.
3. Only **Active** and **Valid To** >= **Work Order Created Date,** organization are listed under drop-down or allowed to search. The organization with the status “**Inactive**” and “**Debarred**” are not listed irrespective of **valid to** date.
4. The minimum value for the work completion period should not be less than **1 day**.
5. The Role of CBO drop-down is selected automatically by the system based on the configuration provided.
   * IF the _total estimated amount <=15 lakhs_ THEN the _Role of CBO = IA_ AND _the_ role _can be changed by the user_.
   * IF the _total estimated amount is >15 lakhs_ THEN the _Role of CBO = IP_ AND _the_ role _can not be changed by the user_.

## Configurations

The amount limit deciding the role of CBO should be configurable. At present it is 15 lakh.

## Workflow

The stories for configuring the workflow are given separately.

## Actions

**On Submit**

* Submit workflow opens a pop-up window with the **Forward** option.
  1. The work order record is saved into the system and the workflow state changes to **Pending for verification**.
  2. The Work Order No. is generated in a **specified format**, if it is a direct submission.
  3. Format for Work Order No. is **WO/FY/<6digitrunningno.>**. Example: **WO/2022-23/000051**
  4. 6 DIGIT running sequence number is reset to 1 with the start of the new FY.
  5. The work order is available to download in **PDF** as per the given format. There will be a separate ticket for PDF download.
* On cancel, the action is cancelled.
* On successful forward the **Success Page** is displayed else the **Failure Page** is displayed.

## Notifications

Not applicable.

## User Interface

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=1831%3A30735\&t=ezrYIQWHGlDd2IDy-4)

## Acceptance Criteria

<table><thead><tr><th width="179">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>The role of CBO is decided based on the logic provided.</td></tr><tr><td>2</td><td>On Forward, the work order is forwarded to the next user.</td></tr><tr><td>3</td><td>The work order number is generated as per the specified format.</td></tr><tr><td>4</td><td>On successful forward <strong>Success Page</strong> is displayed else <strong>Failure Page</strong> is displayed.</td></tr></tbody></table>

