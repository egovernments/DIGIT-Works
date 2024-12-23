# Send Back

## Context

Send the work order back to the previous user in the workflow.

## Actors

Employees

## Actions

1. **Send Back** action is provided with the below details to be captured.
   * **Comments** - Text area - Non-mandatory - _It is provided to add any remarks/instructions to be passed on to the previous user in the workflow._
   * **Attach Supporting Document** - Document upload - Non-mandatory - _In case any documents are to be attached._
   * **Send Back** - Action Button
   * **Cancel** - Action Button
2. On Send Back,
   * The pop-up window is closed, and a toast success message is displayed.
   * The view work order page is refreshed and the action menu is loaded according to the role of the logged-in user.
   * The work order is sent back to the previous user’s inbox.
   * Workflow states change as per the flow.

| Role                | From State               | To State                 | Status    |
| ------------------- | ------------------------ | ------------------------ | --------- |
| Work Order Verifier | Pending for verification | Pending for correction   | Sent Back |
| Work Order Approver | Pending for approval     | Pending for verification | Sent Back |

3. On cancel, the toast cancel message is displayed on top of the view work order page.

{% hint style="info" %}
**Toast Success Message:**

Work order has been sent back successfully.

**Failure Message:**

Sending back of work order is failed.

**Toast Cancel Message:**

Action has been cancelled.
{% endhint %}

## **Notifications**

Not applicable.

## **User Interface**

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=2325%3A33359\&t=ewVSJBLAMoyry76D-4)

## **Acceptance Criteria**

<table><thead><tr><th width="200">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>On send back, the pop-up window is closed and a toast success message is displayed. The view work order page is refreshed.</td></tr><tr><td>2</td><td>The work order is sent back to the previous user in the workflow and the workflow timeline gets updated.</td></tr><tr><td>3</td><td>Workflow state changes based on the role as mentioned in the story above.</td></tr><tr><td>4</td><td>On cancel, the pop-up window is closed and a toast cancel message is displayed.</td></tr></tbody></table>

