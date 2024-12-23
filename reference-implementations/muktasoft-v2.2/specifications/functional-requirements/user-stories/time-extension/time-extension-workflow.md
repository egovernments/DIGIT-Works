---
description: The workflow to process the time extension request
---

# Time Extension Workflow

## Context

A work order is created for an approved estimate to award the work to CBO. CBO starts the work to complete it within a given period.

In case the organization come to know that they are not in a position to complete the work within the given time frame due to various reasons, they need to inform the same to officer-in-charge of the project and apply for a time extension which is then subject to approval/ cancelling of work order based on the analysis done by the ULB.

## Scope <a href="#scope" id="scope"></a>

Request for time extension can be directly raised by CBO using the mobile application and by the officer-in-charge of the project on behalf of CBO using a web application. Once a request is raised it goes for verification and approval.

1. The work order inbox is used to complete the revised work order workflow.
2. Workflow is the same as the workflow of the original work order with the same workflow levels (except/ decline are excluded) and actions.

## Functional Details <a href="#details" id="details"></a>

#### Role (Actors) - Action Mapping <a href="#role-28actors-29actionmapping" id="role-28actors-29actionmapping"></a>

<table><thead><tr><th width="83">#</th><th width="209">Role</th><th width="226">Action</th><th>User Persona</th></tr></thead><tbody><tr><td>1</td><td>WORK ORDER CREATOR/ CBO Admin</td><td><ul><li>Create </li><li>Search </li><li>View </li><li>Edit/ Re-submit</li><li>Submit</li></ul></td><td>Junior Engineer/ Assistant Engineer/ CBO User</td></tr><tr><td>2</td><td>WORK ORDER VERIFIER</td><td><ul><li>Search</li><li>View </li><li>Verify and Forward </li><li>Send Back</li></ul></td><td>Municipal Engineer</td></tr><tr><td>3</td><td>WORK ORDER APPROVER</td><td><ul><li>Search</li><li>View </li><li>Approve</li><li>Send Back </li><li>Send Back To CBO</li><li>Reject</li></ul></td><td>Executive Officer</td></tr></tbody></table>

#### Workflow States <a href="#workflowstates" id="workflowstates"></a>

<table><thead><tr><th width="96">#</th><th>Action</th><th>Role</th><th>From State</th><th>To State</th><th>Status</th></tr></thead><tbody><tr><td>1</td><td>Submit</td><td>Work Order Creator/ CBO Admin</td><td> </td><td>Pending for verification</td><td>Submitted</td></tr><tr><td>2</td><td>Verify and Forward</td><td>Work Order Verifier</td><td>Pending for verification</td><td>Pending for approval</td><td>Verified</td></tr><tr><td>3</td><td>Send Back</td><td>Work Order Verifier</td><td>Pending for verification</td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>4</td><td>Send Back</td><td>Work Order Approver</td><td>Pending for approval</td><td>Pending for verification</td><td>Sent Back</td></tr><tr><td>5</td><td>Send Back To CBO</td><td>&#x3C;any roles having access of action></td><td>&#x3C;Current Status></td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>6</td><td>Edit/ Re-submit</td><td>Work Order Creator</td><td>Pending for correction</td><td>Pending for verification</td><td>Re-submitted</td></tr><tr><td>7</td><td>Approve</td><td>Work Order Approver</td><td>Pending for approval</td><td>Approved</td><td>Approved</td></tr><tr><td>8</td><td>Reject</td><td>&#x3C;any roles having access></td><td>&#x3C;Current Status></td><td>Rejected</td><td>Rejected</td></tr></tbody></table>

#### SLAs <a href="#slas" id="slas"></a>

<table><thead><tr><th width="143">Service Name</th><th width="177">Action</th><th width="298">Current State</th><th>SLAs (in days)</th></tr></thead><tbody><tr><td>Work Order</td><td>Edit/Re-submit</td><td>Pending for correction</td><td>1</td></tr><tr><td> </td><td>Edit/ Re-submit</td><td>Pending for re-assignment</td><td>1</td></tr><tr><td> </td><td>Verify and Forward</td><td>Pending for verification</td><td>2</td></tr><tr><td> </td><td>Approve</td><td>Pending for approval</td><td>1</td></tr></tbody></table>

## Actions <a href="#actions" id="actions"></a>

#### Verify and Forward <a href="#verifyandforward" id="verifyandforward"></a>

The revised work order is forwarded to the approver.

#### Send Back <a href="#sendback" id="sendback"></a>

A revised work order is sent back to the previous user in the workflow.

#### Send Back To CBO <a href="#sendbacktocbo" id="sendbacktocbo"></a>

The revised work order is sent back to CBO for correction.

#### Edit/ Re-submit <a href="#edit-2fresubmit" id="edit-2fresubmit"></a>

The revised work order is edited and re-submitted. It goes to the verifier for verification.

#### Approve <a href="#approve" id="approve"></a>

1. The revised work order is approved.
2. The time extension comes into effect and the CBO user is allowed to track the attendance of wage seekers for an extended period.

#### Reject <a href="#reject" id="reject"></a>

The revised work order is rejected.

## Notifications <a href="#notifications" id="notifications"></a>

<table><thead><tr><th width="145">Event</th><th width="137">To Whom</th><th>SMS</th></tr></thead><tbody><tr><td>Send Back To CBO</td><td>CBO</td><td>Time extension request {timeextensionrequestid} is sent back to you for correction. Login to MUKTASoft for details. MUKTA - Govt. of Odisha.</td></tr><tr><td>Reject</td><td>CBO</td><td><p>Time extension request {timeextensionrequestid} for the project {projectid}</p><p>is rejected. Login to MUKTASoft for detail. MUKTA - Govt. of Odisha.</p></td></tr><tr><td>Approve</td><td>CBO</td><td><p>Time extension request {timeextensionrequestid}</p><p>for project</p><p>{projectid}</p><p>is approved. Login to MUKTASoft for detail. MUKTA - Govt. of Odisha.</p></td></tr><tr><td>Reject</td><td>Officer In-charge</td><td><p>Time extension request {timeextensionrequestid}</p><p>for the project</p><p>{projectid} is rejected. Login to MUKTASoft for detail. MUKTA - Govt. of Odisha.<br></p></td></tr><tr><td>Approve</td><td>Officer In-charge</td><td>Time extension request {timeextensionrequestid} for project {projectid} has been approved. Login to MUKTASoft for detail. MUKTA - Govt. of Odisha.</td></tr></tbody></table>

## User Interface <a href="#ui" id="ui"></a>

1. UI design is going to be the same as the work order workflow. Only the workflow states will be displayed as per the table given above.
2. The workflow pop-up windows for every action are going to be the same as for the work order workflow.

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. The same work order inbox is used.
2. The workflow pop-up windows are used as per the standard.
3. Actions are configured based on role-action mapping.
4. Workflow states are defined as provided and the state transition is done accordingly.
5. SMS notifications are sent.

