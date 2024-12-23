# Create/ Submit Work Order

## Scope

* Hence for **Work Order Creator**, the **On** **Submit** pop-up window is opened to capture the below-given details.
  1. **Assignee name**- Drop-down - Non Mandatory -  _The next user in the workflow i.e. work order verifier hence the employees having the role **Work\_Order\_Verifier** are displayed in drop-down with the Name and Designation. E.g. Suresh K working as Junior Assistant Executive Engineer and having the role of work order verifier will be displayed ‘**Suresh K - Assistant Executive Engineer**’._
  2. **Comments** - Text area - Non-Mandatory -  _In case any comments to be added._
  3. **Forward -** Action Button
  4. **Cancel** - Action Button
* On **Forward**,
  1. The pop-up window is closed, a toast success message is displayed and the view work order page is refreshed.
  2. The action menu is loaded according to the role-action mapping of the currently logged-in user.
  3. The work order is forwarded to the next user in the workflow and shown in its inbox.
  4. The workflow state changes accordingly and timelines show the current state of the estimate.
  5. Work order is removed from the currently logged-in user’s inbox.

<table><thead><tr><th width="154">Action</th><th width="128">Role</th><th width="118">From State</th><th width="126">To State</th><th>Status</th></tr></thead><tbody><tr><td>Submit/ Forward</td><td>Work Order Creator</td><td> </td><td>Pending for verification</td><td>Submitted</td></tr><tr><td>Re-submit/ Forward</td><td>Work Order Creator</td><td>Pending for correction</td><td>Pending for verification</td><td>Re-submitted</td></tr></tbody></table>

* On cancel, a pop-up window is closed, toast cancel message is displayed on the view work order page.

{% hint style="info" %}
**Toast Success Message:**

The work order is forwarded successfully.

**Failure Message:**

Forward of work order failed.

**Toast Cancel Message:**

Action is cancelled.
{% endhint %}

## **Validation**

Not applicable.

## **Notification**

Not applicable.

## **User Interface**

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=1835%3A32629\&t=wWexCFFY2p7fxq5l-4)

## **Acceptance Criteria**

<table><thead><tr><th width="189">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>On submission, the application is forwarded to the next user in the flow.</td></tr><tr><td>2</td><td>The pop-up window gets closed and the application page is refreshed. A toast success message is displayed.</td></tr><tr><td>3</td><td>On cancel pop-up window is closed. A toast cancel message is displayed.</td></tr><tr><td>4</td><td>Workflow states change and based on the role the existing user has view work order page refreshes.</td></tr></tbody></table>

