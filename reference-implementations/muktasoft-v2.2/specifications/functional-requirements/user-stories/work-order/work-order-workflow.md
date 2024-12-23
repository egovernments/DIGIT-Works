# Work Order Workflow

## **Context**

Work order is created for an approved estimate in order to award the work to CBO and then send it for the approval process. The approval process contains various workflow levels and states associated with those levels.

## **Scope**

Work order preparation for a work by the Work Order Creator and then its verification and approval by other users (actors) in the workflow.

## **Details**

### Role (Actors) - Action Mapping

<table><thead><tr><th width="76">#</th><th width="165">Role</th><th width="231">Action</th><th>User Persona</th></tr></thead><tbody><tr><td>1</td><td>WORK ORDER CREATOR</td><td><ul><li>Create </li><li>Search </li><li>View </li><li>Edit/ Re-submit</li><li>Submit </li><li>Reject</li></ul></td><td>Junior Engineer/ Assistant Engineer</td></tr><tr><td>2</td><td>WORK ORDER VERIFIER</td><td><ul><li>Search</li><li>View </li><li>Verify and Forward</li><li>Send Back </li></ul></td><td>Executive Officer</td></tr><tr><td>3</td><td>WORK ORDER APPROVER</td><td><ul><li>Search</li><li>View </li><li>Approve</li><li>Send Back </li><li>Send Back To Originator</li><li>Reject</li></ul></td><td>Municipal Engineer</td></tr><tr><td>4</td><td>CBO ADMIN</td><td><ul><li>Accept</li><li>Decline</li></ul></td><td>Community based organization contact person (President/ Secretary)</td></tr></tbody></table>

### Workflow States

<table><thead><tr><th width="75">#</th><th>Action</th><th>Role</th><th>From State</th><th>To State</th><th>Status</th></tr></thead><tbody><tr><td>1</td><td>Submit</td><td>Work Order Creator</td><td> </td><td>Pending for verification</td><td>Submitted</td></tr><tr><td>2</td><td>Verify and Forward</td><td>Work Order Verifier</td><td>Pending for verification</td><td>Pending for approval</td><td>Verified</td></tr><tr><td>3</td><td>Send Back</td><td>Work Order Verifier</td><td>Pending for verification</td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>4</td><td>Send Back</td><td>Work Order Approver</td><td>Pending for approval</td><td>Pending for verification</td><td>Sent Back</td></tr><tr><td>5</td><td>Send Back To Originator</td><td><strong>&#x3C;any roles having access></strong></td><td><strong>&#x3C;Current Status></strong></td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>6</td><td>Edit/ Re-submit</td><td>Work Order Creator</td><td>Pending for correction</td><td>Pending for verification</td><td>Re-submitted</td></tr><tr><td>7</td><td>Approve</td><td>Work Order Approver</td><td>Pending for approval</td><td>Approved</td><td>Approved</td></tr><tr><td>8</td><td>Reject</td><td><strong>&#x3C;any roles having access></strong></td><td><strong>&#x3C;Current Status></strong></td><td>Rejected</td><td>Rejected</td></tr><tr><td>9</td><td>Accept</td><td>CBO Admin</td><td>Approved</td><td>Accepted</td><td>Accepted</td></tr><tr><td>10</td><td>Decline</td><td>CBO Admin</td><td>Approved</td><td>Pending for re-assignment</td><td>Declined</td></tr><tr><td>11</td><td>Edit/ Re-submit</td><td>Work Order Creator</td><td>Pending for re-assignment</td><td>Pending for verification</td><td>Re-submitted</td></tr></tbody></table>

### SLAs

<table><thead><tr><th width="161">Service Name</th><th width="178">Action</th><th width="264">Current State</th><th>SLAs (In Days)</th></tr></thead><tbody><tr><td>Work Order</td><td>Edit/ Re-submit</td><td>Pending for correction</td><td>1</td></tr><tr><td> </td><td>Edit/ Re-submit</td><td>Pending for re-assignment</td><td>1</td></tr><tr><td> </td><td>Verify and Forward</td><td>Pending for verification</td><td>2</td></tr><tr><td> </td><td>Approve</td><td>Pending for approval</td><td>1</td></tr><tr><td> </td><td>Accept</td><td>Approved</td><td>7</td></tr></tbody></table>

## **User Interface (UI)**

UI design is going to be the same as the estimate workflow. Only the workflow states will be displayed as per the table given above.

## **Acceptance Criteria**

<table><thead><tr><th width="135">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Actions are configured based on role-action mapping.</td></tr><tr><td>2</td><td>Workflow states are defined as provided and the state transition is done accordingly.</td></tr></tbody></table>
