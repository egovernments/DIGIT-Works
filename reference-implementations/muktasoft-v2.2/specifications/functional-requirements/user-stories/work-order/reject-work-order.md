# Reject Work Order

## Context

Reject the work order.

## Actors

Employees

## Actions

1. To reject the work order, action is provided to capture the below-given detail and reject the work order.
   * **Comments** - Text area - Mandatory
   * **Attach Supporting Document** - Document upload
   * **Reject** - Action Button
   * **Cancel** - Action Button
2. On Reject,
   * The pop-up window is closed, toast reject message is displayed.
   * The work order page is refreshed. No actions are enabled for the rejected work order.
   * The work order creator is informed about the rejection of the work order through SMS notification.
   * Workflow state changes as given below.

| Role                                       | From State       | To State | Status   |
| ------------------------------------------ | ---------------- | -------- | -------- |
| \<the role having access of reject action> | \<Current State> | Rejected | Rejected |

3\. On cancel, a toast cancel message is displayed on the view work order page.

{% hint style="info" %}
**Toast Success Message:**

Work order has been rejected successfully.

**Failure Message:**

Rejection of work order is failed.

**Toast Cancel Message:**

Action has been cancelled.
{% endhint %}

## Notifications

_SMS to the creator’s mobile_

Work order \<work order no.> for the project \<project name> of the location \<location> has been rejected by \<username+designation>. For more detail please login to MUKTASoft to view the work order details.

## **User Interface**

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=2325%3A33715\&t=ewVSJBLAMoyry76D-4)

## **Acceptance Criteria**

<table><thead><tr><th width="212">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>On reject, the work order is rejected and the workflow state/status changes accordingly.</td></tr><tr><td>2</td><td>No further actions can be performed on a rejected work order.</td></tr><tr><td>3</td><td>Notification is sent to the work order creator.</td></tr></tbody></table>
