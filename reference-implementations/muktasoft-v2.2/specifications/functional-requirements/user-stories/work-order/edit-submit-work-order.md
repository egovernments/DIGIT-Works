# Edit/Submit Work Order

## **Scope**

1. Edit Work Order action is to be mapped with Work Order Creator.
2. It is configurable and can to mapped with other roles too on demand.
3. The work order which is in the workflow can only be edited.
4. Rejected, Approved, and Accepted work orders can not be edited.
5. Edit work orders allows the user to edit the below-given work order detail.

<table><thead><tr><th width="96">#</th><th>Field</th><th width="145">Data Type</th><th width="109">Required</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Work order number</td><td>Display Only</td><td>NA</td><td>Work order no.</td></tr><tr><td>2</td><td>Project ID</td><td>Display Only</td><td>NA</td><td>Project ID of the project.</td></tr><tr><td>3</td><td>Date of proposal</td><td>Display Only</td><td>NA</td><td>Date of the proposal from the project.</td></tr><tr><td>4</td><td>Project name</td><td>Display Only</td><td>NA</td><td>Project name</td></tr><tr><td>5</td><td>Project description</td><td>Display Only</td><td>NA</td><td>Project description</td></tr><tr><td>6</td><td><strong>Project Details</strong></td><td><strong>Tab</strong></td><td> </td><td>Displayed as per view project details.</td></tr><tr><td>7</td><td><strong>Estimate Details</strong></td><td><strong>Tab</strong></td><td> </td><td>Displayed as per view estimate details.</td></tr><tr><td>8</td><td><strong>Work Order Details</strong></td><td><strong>Tab</strong></td><td> </td><td> </td></tr><tr><td>9</td><td>Name of CBO</td><td>Drop-down</td><td>Y</td><td>The name of the CBO from the organization master maintained at the ULB level. The name is searchable in the drop-down.</td></tr><tr><td>10</td><td>CBO ID</td><td>Display</td><td>Y</td><td>The CBO ID from the organization registry.</td></tr><tr><td>11</td><td>Role of CBO</td><td><p>Drop-down</p><p>(Auto- selected)</p></td><td>Y</td><td><p>The role of the CBO is decided based on the estimated amount. It is configurable in the system.</p><ol><li>IP (Implementation Partner) - If the estimated cost of the works is more than Rs.15 Lakhs</li><li>IA (Implementation Agency) -  If the estimated cost of the works is up to Rs.15 Lakhs</li></ol></td></tr><tr><td>12</td><td>Name of the officer in-charge</td><td>Drop-down</td><td>Y</td><td>The drop-down values are population based on the role assigned. The name is searchable in the drop-down. Name + Designation</td></tr><tr><td>13</td><td>Designation of officer in-charge</td><td>Display</td><td>Y</td><td>Displayed from the EIS/User’s record saved in the system.</td></tr><tr><td>14</td><td>Project completion period</td><td>Numeric</td><td>Y</td><td>Number of days work to be completed.</td></tr><tr><td>15</td><td>Work order amount</td><td>Read Only</td><td>Y</td><td>Total estimated cost of the selected work.</td></tr><tr><td> </td><td><strong>Relevant Documents</strong></td><td><strong>Sections</strong></td><td> </td><td> </td></tr><tr><td>16</td><td>BOQ</td><td>File Attachment</td><td>Y</td><td>Allows single file, not greater than 5 MB. Files can be of type doc, xls, pdf, jpg.</td></tr><tr><td>17</td><td>Labour Analysis</td><td>File Attachment</td><td>Y</td><td>Allows single file, not greater than 5 MB. Files can be of type doc, xls, pdf, jpg.</td></tr><tr><td>18</td><td>Material Analysis</td><td>File Attachment</td><td>Y</td><td>Allows single file, not greater than 5 MB. Files can be of type doc, xls, pdf, jpg.</td></tr><tr><td>19</td><td>Terms and conditions</td><td>File Attachment</td><td>Y</td><td>Allows single file, not greater than 5 MB. Files can be of type doc, xls, pdf, jpg.</td></tr><tr><td>20</td><td>Others</td><td>Textbox</td><td>N</td><td>To capture the file name</td></tr><tr><td>21</td><td> </td><td>File Attachment</td><td>N</td><td>To attach the file file the name entered above in the textbox.</td></tr></tbody></table>

Once the work order is edited, it is re-submitted for approval using the **Submit** action button.

## **Notification**

Not applicable.

## **Actions**

Based on the logged-in user role, a workflow pop-up window is displayed on submit.

<table><thead><tr><th width="229">Role</th><th>Workflow window</th></tr></thead><tbody><tr><td>Work Order Creator</td><td>Submit pop-up window</td></tr><tr><td>Work Order Verifier</td><td>Verify and Forward pop-up window</td></tr><tr><td>Approver</td><td>Approval pop-up window</td></tr></tbody></table>

1. On respective workflow action, changes get saved and the work order is forwarded to the next user in the workflow.
2. On Cancel, the pop-up window gets closed and the action gets cancelled.
3. Accordingly, the messages are shown.

## **User Interface**

\<To be updated>

## **Acceptance Criteria**

<table><thead><tr><th width="154">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Role-based access based on configuration.</td></tr><tr><td>2</td><td>The work order which is in the workflow can only be edited.</td></tr><tr><td>3</td><td>The work order is opened in editable mode.</td></tr><tr><td>4</td><td>The details given in the table can be edited by the user.</td></tr><tr><td>5</td><td>On Submit, the work order is again forwarded to the next user for approval.</td></tr></tbody></table>

