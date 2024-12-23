# MB Workflow

## Scope

Measurement Book Workflow

Home > Inbox

## Details <a href="#details" id="details"></a>

### Role (Actors) - Action Mapping <a href="#role-28actors-29actionmapping" id="role-28actors-29actionmapping"></a>

<table data-header-hidden><thead><tr><th width="77"></th><th width="165"></th><th></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Role (Actors)</td><td>Actions</td><td>User Persona</td></tr><tr><td>1</td><td>MB_CREATOR</td><td><ul><li>Create</li><li>Submit</li><li>Search</li><li>View</li><li>Edit/ Re-submit</li></ul></td><td>Junior Engineer/ Assistant Engineer/ MUKTA Implementation Expert</td></tr><tr><td>2</td><td>MB_VERIFIER</td><td><ul><li>Search</li><li>View</li><li>Verify and Forward</li><li>Send Back</li></ul></td><td>Executive Engineer</td></tr><tr><td>3</td><td>MB_ APPROVER</td><td><ul><li>Search</li><li>View</li><li>Approve</li><li>Send Back</li><li>Send Back To Originator</li><li>Reject</li></ul></td><td>Municipal Engineer</td></tr><tr><td>4</td><td>MB_VIEWER</td><td><ul><li>Search</li><li>View</li></ul></td><td>MUKTA Coordinator/ MUKTA Accountant</td></tr></tbody></table>

### Workflow States <a href="#workflowstates" id="workflowstates"></a>

<table data-header-hidden><thead><tr><th width="61"></th><th></th><th></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td>Action</td><td>Role</td><td>From State</td><td>To State</td><td>Status</td></tr><tr><td>1</td><td>Save as Draft</td><td>MB Creator</td><td> </td><td>Drafted</td><td>Drafted</td></tr><tr><td>2</td><td>Submit</td><td>MB Creator</td><td>Drafted</td><td>Pending for verification</td><td>Submitted</td></tr><tr><td>3</td><td>Verify and Forward</td><td>MB Verifier</td><td>Pending for verification</td><td>Pending for approval</td><td>Verified</td></tr><tr><td>4</td><td>Send Back</td><td>MB Verifier</td><td>Pending for verification</td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>5</td><td>Send Back</td><td>MB Approver</td><td>Pending for approval</td><td>Pending for verification</td><td>Sent Back</td></tr><tr><td>6</td><td>Send Back To Originator</td><td>&#x3C;roles having access></td><td>&#x3C;Current Status></td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>7</td><td>Edit/ Re-submit</td><td>MB Creator</td><td>Pending for correction</td><td>Pending for verification</td><td>Re-submitted</td></tr><tr><td>8</td><td>Approve</td><td>MB Approver</td><td>Pending for approval</td><td>Approved</td><td>Approved</td></tr><tr><td>9</td><td>Reject</td><td>&#x3C;any roles having access></td><td>&#x3C;Current Status></td><td>Rejected</td><td>Rejected</td></tr></tbody></table>

### SLAs <a href="#slas" id="slas"></a>

| **Workflow State/ Event** | **Current State**        | **SLA (In Days)** |
| ------------------------- | ------------------------ | ----------------- |
| Edit/ Re-submit           | Pending for correction   | 1                 |
| Verify and Forward        | Pending for verification | 2                 |
| Approve                   | Pending for approval     | 1                 |

## Notifications <a href="#notifications" id="notifications"></a>

On Approve To Creator

`MB Entry {refno} for the project {projectid} is approved. Login to MUKTASoft to view estimate details. MUKTA - Govt. of Odisha.`

On Reject To Creator

`MB Entry {refno} for the project {projectid} is rejected. Login to MUKTASoft to view estimate details. MUKTA - Govt. of Odisha.`

## UI <a href="#ui" id="ui"></a>

The same UIs which are being used for other flows.

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Roles are created as given provided.
2. Actions are configured based on role - action mapping.
3. Workflow states are defined as provided and the state transition done as provided.
4. SMS notifications are sent accordingly.
