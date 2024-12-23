# User Role

## Introduction

The roles defined in the system carry specific functionality and need to map with users when created in the system to enable the user to use the respective functionality.

## Data Table

| Sr. No. | Code                   | Role Description             | Action                                                                                                                                                        | Service         |
| ------- | ---------------------- | ---------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------- |
| 1       | PROJECT\_CREATOR       | Poject Creator               | <p>Create<br>Search<br>View<br>Modify</p>                                                                                                                     | Project         |
| 2       | PROJECT\_VIEWER        | Project Viewer               | <p>Search <br>View</p>                                                                                                                                        | Project         |
| 4       | ESTIMATE\_CREATOR      | Estimate Creator             | <p>Inbox<br>Create<br>Search<br>View<br>Edit</p>                                                                                                              | Estimate        |
| 5       | ESTIMATE\_VERIFIER     | Estimate Verifier            | <p>Inbox<br>Search<br>View<br>Verify and Forward<br>Send Back</p>                                                                                             | Estimate        |
| 6       | TECHNICAL\_SANCTIONER  | Technical Sanctioner         | <p>Inbox<br>Search<br>View<br>Technically Sanction<br>Send Back<br>Send Back To Originator<br>Reject</p>                                                      | Estimate        |
| 7       | ESTIMATE\_APPROVER     | Estimate Approver            | <p>Inbox<br>Search<br>View<br>Approve<br>Send Back<br>Send Back To Originator<br>Reject</p>                                                                   | Estimate        |
| 9       | WORK\_ORDER\_CREATOR   | Work Order Creator           | <p>Inbox<br>Create<br>Search<br>View<br>Edit</p>                                                                                                              | Contract        |
| 10      | WORK\_ORDER\_VIEWER    | Work Order Verifier          | <p>Inbox<br>Search<br>View<br>Verify and Forward<br>Send Back</p>                                                                                             | Contract        |
| 11      | WORK\_ORDER\_APPROVER  | WOrk Order Approver          | <p>Inbox<br>Search<br>View<br>Approve<br>Send Back<br>Send Back To Originator<br>Reject</p>                                                                   | Contract        |
| 13      | MUSTER\_ROLL\_VERIFIER | Muster Roll Verifier         | <p>Inbox<br>Search<br>View<br>Edit<br>Verify and Forward<br>Send Back</p>                                                                                     | Muster Roll     |
| 14      | MUSTER\_ROLL\_APPROVER | Muster Roll Approver         | <p>Inbox<br>Search<br>View<br>Approve<br>Send Back</p>                                                                                                        | Muster Roll     |
| 16      | MUKTA\_ADMIN           | MUKTA Admin                  | <p>Create Organization<br>Search Organization<br>View Organization<br>Modify Organization<br>Search Wage Seeker<br>View Wage Seeker<br>Modify Wage Seeker</p> | Masters         |
| 17      | HRMS\_ADMIN            | HRMS Admin                   | <p>Create Employee <br>Search Employee <br>View Employee <br>Modify Employee</p>                                                                              | Employee Users  |
| 18      | STADMIN                | Dashboard Viewer             | Mukta Dashboard                                                                                                                                               | Mukta Dashboard |
| 19      | ORG\_ADMIN             | CBO Admin                    | all actions of CBO portal                                                                                                                                     | CBO Portal      |
| 20      | BILL\_CREATOR          | Bill Creator                 | <p>Inbox<br>Search<br>View<br>Create</p>                                                                                                                      | Billing         |
| 21      | BILL\_VIEWER           | Bill Viewer                  | <p>Inbox<br>Search<br>View</p>                                                                                                                                | Billing         |
| 22      | EMPLOYEE\_COMMON       | Common Role For all employee | This role will have all common access that employee should have like edit profile, get Workflow status etc                                                    | Common          |
| 23      | ESTIMATE\_VIEWER       | Estimate Viewer              | <p><br>View</p>                                                                                                                                               | Estimate        |

{% hint style="info" %}
The data given in the table is sample data for reference.
{% endhint %}

## Data Definition

<table><thead><tr><th width="97">Sr. No.</th><th>Column Name</th><th>Data Type</th><th>Data Size</th><th>Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the project type.</td></tr><tr><td>2</td><td>Project Type</td><td>Text</td><td>256</td><td>Yes</td><td>Provides the name of the project type </td></tr><tr><td>3</td><td>Project Type (Odiya)</td><td>Text</td><td>256</td><td>No</td><td>Project type name in local language</td></tr><tr><td>4</td><td>Effective From</td><td></td><td></td><td></td><td>Date from which the project is effective</td></tr><tr><td>5</td><td>Effective To</td><td></td><td></td><td></td><td>Date till which the project is effective</td></tr><tr><td>6</td><td>Is Active</td><td></td><td></td><td></td><td>Whether the project is active or not</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/MUKTA - Master Data-2.xlsx" %}
