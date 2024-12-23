# Payment Advice Format

## Introduction

This the template to generate the payment advice from the system. After downloading the data accountant will be able to proceed with payments in IFMS system.

## Data Table

| Sr. No. | Project ID | Work Order ID | Bill Number       | Bill Type   | Gross Amount | Beneficiary Type | Beneficiary Name | Beneficiary ID | Account Type | Bank Account Number | IFSC | Payable Amount |
| ------- | ---------- | ------------- | ----------------- | ----------- | ------------ | ---------------- | ---------------- | -------------- | ------------ | ------------------- | ---- | -------------- |
| 1       |            |               | PB/2023-24/000001 | Purchase    | 1010         | ORG              |                  |                |              |                     |      | 1000           |
| 2       |            |               | PB/2023-24/000001 | Purchase    | 1010         | ULB              |                  |                |              |                     |      | 10             |
| 3       |            |               | SB/2023-24/000002 | Supervision | 900          | ORG              |                  |                |              |                     |      | 900            |
|         |            |               | WB/2023-24/00001  | WAGE        | 100          | IND              | A                |                |              |                     |      |                |
|         |            |               | WB/2023-24/00001  | WAGE        | 200          | IND              | B                |                |              |                     |      |                |
|         |            |               | WB/2023-24/00001  | WAGE        | 300          | IND              | C                |                |              |                     |      |                |
|         |            |               |                   |             |              |                  |                  |                |              |                     |      |                |
|         |            |               |                   |             |              |                  |                  |                |              |                     |      |                |
|         |            |               |                   |             |              |                  |                  |                |              |                     |      |                |

{% hint style="info" %}
The data given in the table is sample data for reference.
{% endhint %}

## Data Definition

<table><thead><tr><th width="97">Sr. No.</th><th>Column Name</th><th>Data Type</th><th>Data Size</th><th>Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the project type.</td></tr><tr><td>2</td><td>Project Type</td><td>Text</td><td>256</td><td>Yes</td><td>Provides the name of the project type </td></tr><tr><td>3</td><td>Project Type (Odiya)</td><td>Text</td><td>256</td><td>No</td><td>Project type name in local language</td></tr><tr><td>4</td><td>Effective From</td><td></td><td></td><td></td><td>Date from which the project is effective</td></tr><tr><td>5</td><td>Effective To</td><td></td><td></td><td></td><td>Date till which the project is effective</td></tr><tr><td>6</td><td>Is Active</td><td></td><td></td><td></td><td>Whether the project is active or not</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/MUKTA - Master Data-2.xlsx" %}
