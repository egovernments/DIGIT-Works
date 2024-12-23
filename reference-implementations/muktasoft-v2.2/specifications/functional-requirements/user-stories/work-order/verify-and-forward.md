# Verify & Forward

## Context

Verify and forward the work order to the next workflow user.&#x20;

## Actors

Employees

## Actions

The Verify and Forward action is provided with a pop-up window to capture the below-given details.

1. **Assignee name**- Drop-down - Non Mandatory -  _The next user in the workflow i.e. **Approver**, hence the employees having the role **Work\_Order\_Approver** are displayed in drop-down with the name and the designation. E.g. Mahesh K working as EO and having the role of Work\_Order\_Approver will be displayed as ‘Mahesh K - Executive Officer’._
2. **Comments** - Text area - Non-Mandatory -  _In case any comments to be added._
3. **Attach Supporting Document** - Non-Mandatory - _Any document to be uploaded as a supporting document._
4. **Verify and Forward** - _Action Button_
5. **Cancel** - _Action Button_
   * The pop-up window is closed, toast cancel message is displayed on the view work order page
6. On **Verify and Forward**,
   * A pop-up window is closed, the toast success message is displayed and the view work order page is refreshed.
   * The action menu is loaded according to the role-action mapping of the currently logged-in user.
   * The work order is forwarded to the next user in the workflow and shown in its inbox.
   * The workflow state changes accordingly and timelines show the current state of the work order.
   * Work order is removed from the currently logged-in user’s inbox.

<table><thead><tr><th width="187">Role</th><th>From State</th><th width="220">To State</th><th>Status</th></tr></thead><tbody><tr><td>Work Order Verifier</td><td>Pending for verification</td><td>Pending for approval</td><td>Verified</td></tr></tbody></table>

{% hint style="info" %}
**Toast Success Message:**

The work order has been forwarded successfully.

**Failure Message:**

Verification of work order failed.

**Toast Cancel Message:**

Action has been cancelled.
{% endhint %}

## **Notifications**

Not applicable.

## **User Interface**

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=2325%3A33181\&t=ewVSJBLAMoyry76D-4)

## **Acceptance Criteria**

<table><thead><tr><th width="269">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Verify and forward pushes the work order to the next user in the flow.</td></tr><tr><td>2</td><td>The pop-up window is closed and the view work order page is refreshed. A toast success message is displayed.</td></tr><tr><td>3</td><td>Workflow states change, and based on the existing role the user can view the work order page on refresh.</td></tr><tr><td>4</td><td>On cancel pop-up window is closed. A toast cancel message is displayed.</td></tr></tbody></table>
