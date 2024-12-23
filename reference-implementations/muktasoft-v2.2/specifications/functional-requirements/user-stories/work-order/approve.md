# Approve

## Context

For approval of work order.

## Actors

Employees

## Actions

1. For the approval of the work order, action **Approve** is provided and the below given detail is captured in a pop-window on approval.
   * **Comments** - Text area - Non-mandatory
   * **Attach Supporting Document** - Document upload - Non-mandatory
   * **Approve** - Action Button
   * **Cancel** - Action Button
2. On Approve,
   * The work order is approved.
   * Approve pop-up window is closed, a toast success message is displayed and the view work order page is refreshed.
   * Workflow timelines are displayed accordingly.
   * Workflow state changes as given below.

| Role                | From State           | To State | Status   |
| ------------------- | -------------------- | -------- | -------- |
| Work Order Approver | Pending for approval | Approved | Approved |

3. On cancel, the toast cancel message is displayed.

{% hint style="info" %}
**Toast Success Message:**

Work order has been administratively approved successfully.

**Failure Message:**

Approval of work order is failed.

**Toast Cancel Message:**

Action has been cancelled.
{% endhint %}

## Notifications

**SMS to the Work Order Creator**

Work Order \<work order no.> for the project \<projectname> of the location \<location> has been approved and sent to \<CBOName> for acceptance. For more detail please login to MUKTASoft to view the estimate details.

**SMS to the CBO**

Dear \<contactpersonname>, \<organisationname> has been chosen as the \<IA/IP> for the project \<project name>. Please accept the work order \<WO\_NUMBER> before \<duedate> to avoid auto cancellation. To login please click on \<Organization Login URL>.

## **User Interface**

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=2325%3A34179\&t=ewVSJBLAMoyry76D-4)

## **Acceptance Criteria**

<table><thead><tr><th width="239">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>On approve, work order workflow state changes accordingly.</td></tr><tr><td>2</td><td>On approve, notification is sent to work order creator.</td></tr></tbody></table>

