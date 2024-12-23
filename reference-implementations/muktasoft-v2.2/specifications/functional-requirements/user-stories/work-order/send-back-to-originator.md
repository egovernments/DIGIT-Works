# Send Back To Originator

## Context

Send the work order back to the originator’s inbox for any correction required.

## Actors

Employees

## Actions

1. It is provided to send the work order back to the originator’s inbox for any correction required. Below given detail is captured.
   * **Comments** - Text area - Non-mandatory - _It is provided to add any remarks/ instructions to be passed to the originator of the work order._
   * **Attach Supporting Document** - Document upload - Non-mandatory - _In case any documents are to be attached while sending the work order back to the originator._
   * **Send Back** - Action Button
   * **Cancel** - Action Button
2. On **Send Back -**
   * The pop-up window is closed and a toast success message is displayed.
   * The view work order page is refreshed and the actions menu is loaded according to the role the logged-in user has.
   * The work order is placed into the work order creator’s inbox.
   * The ‘Edit Work Order’ option is provided to **Work Order Creator** to edit the work order and attached the new documents files and Re-submit it.
   * Workflow state changes as given below.

| Role                                              | From State        | To State               | Status    |
| ------------------------------------------------- | ----------------- | ---------------------- | --------- |
| \<roles having access to send back to originator> | \<Current Status> | Pending for correction | Sent Back |

3. On cancel, the pop-up window is closed, toast cancel message is displayed on the view work order page.

{% hint style="info" %}
**Toast Success Message:**

Work order has been sent back to the creator successfully.

**Failure Message:**

Sending back of work order is failed.

**Toast Cancel Message:**

Action has been cancelled.
{% endhint %}

## Notifications

Not applicable.

## User Interface

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=2325%3A33537\&t=ewVSJBLAMoyry76D-4)

## **Acceptance Criteria**

<table><thead><tr><th width="217">Acceptance Criteria</th><th></th></tr></thead><tbody><tr><td>1</td><td>The work order is moved to Work Order Creator’s inbox.</td></tr><tr><td>2</td><td>Work Order Creator- Edit Work Order action is enabled to edit the work order.</td></tr><tr><td>3</td><td>Workflow state changes as mentioned in the ticket.</td></tr></tbody></table>

