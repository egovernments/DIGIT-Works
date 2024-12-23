# Standard Deductions

## Introduction

These are deductions which are made from the purchase bills and then remitted to respective departments.

## Data Table

| Sr. No. | Code   | Description         | Description (Odiya)     | Applicable  | Calculation Method | Percentage/ Value | Effective From | Effective To | Is Active |
| ------- | ------ | ------------------- | ----------------------- | ----------- | ------------------ | ----------------- | -------------- | ------------ | --------- |
| 1       | LC     | Labour Cess         | ଶ୍ରମ ଉପକର (ସେସ୍)        | Vendor      | Percentage         | 1                 |                |              |           |
| 2       | ITTDS  | IT TDS              | ଆଇ.ଟି ଟି.ଡ଼ି.ଏସ୍         | Vendor      | Percentage         | 10                |                |              |           |
| 3       | ROM    | Royalty on minerals | ଖଣିଜ ପଦାର୍ଥ ଉପରେ ଲାଭାଂଶ | Vendor      | Not Calculated     |                   |                |              |           |
| 4       | ECB    | Empty Cement Bag    | ଖାଲି ସିମେଣ୍ଟ ବ୍ୟାଗ୍     | Vendor      | Not Calculated     |                   |                |              |           |
| 5       | GSTTDS | GST TDS             | ଜି.ଏସ୍.ଟି ଟି.ଡ଼ି.ଏସ୍     | Vendor      | Percentage         | 2                 |                |              |           |

{% hint style="info" %}
The data given in the table is sample data for reference.
{% endhint %}

## Data Definition

<table><thead><tr><th width="97">Sr. No.</th><th>Column Name</th><th>Data Type</th><th>Data Size</th><th>Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the project type.</td></tr><tr><td>2</td><td>Description</td><td>Text</td><td>256</td><td>Yes</td><td>Provides the name of the project type </td></tr><tr><td>3</td><td>Description (Odia)</td><td>Text</td><td>256</td><td>No</td><td>Project type name in local language</td></tr><tr><td>4</td><td>Applicable</td><td>Options</td><td></td><td></td><td></td></tr><tr><td>5</td><td>Calculation Method</td><td>Identifier</td><td></td><td></td><td></td></tr><tr><td>6</td><td>Percentage/Value</td><td>Numeric</td><td></td><td></td><td></td></tr><tr><td>4</td><td>Effective From</td><td>Date</td><td></td><td></td><td>Date from which the project is effective</td></tr><tr><td>5</td><td>Effective To</td><td>Date</td><td></td><td></td><td>Date till which the project is effective</td></tr><tr><td>6</td><td>Is Active</td><td>Boolean</td><td></td><td></td><td>Whether the project is active or not</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/MUKTA - Master Data-2.xlsx" %}
