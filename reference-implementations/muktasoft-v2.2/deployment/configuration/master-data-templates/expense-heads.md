# Expense Heads

## Introduction

These are the heads under which MUKTA expenditure is booked.&#x20;

## Data Table

| Sr. No. | Code | Description           | Description (Odiya) | Effective From | Effective To | Is Active |
| ------- | ---- | --------------------- | ------------------- | -------------- | ------------ | --------- |
| 1       | WEG  | Wages                 | ମଜୁରୀ               |                |              |           |
| 2       | SC   | Supervision Charge    | ତଦାରଖ ଦେୟ           |                |              |           |
| 3       | GST  | Goods and Service Tax | ଦ୍ରବ୍ୟ ଏବଂ ସେବା କର  |                |              |           |
| 4       | MC   | Material Cost         | ସାମଗ୍ରୀ ମୂଲ୍ୟ       |                |              |           |
| 5       | PA   | Purchase              | କ୍ରୟ ପରିମାଣ         |                |              |           |

{% hint style="info" %}
The data given in the table is sample data for reference.
{% endhint %}

## Data Definition

<table><thead><tr><th width="97">Sr. No.</th><th>Column Name</th><th>Data Type</th><th>Data Size</th><th>Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the project type.</td></tr><tr><td>2</td><td>Description</td><td>Text</td><td>256</td><td>Yes</td><td>Provides the name of the project type </td></tr><tr><td>3</td><td>Description (Odiya)</td><td>Text</td><td>256</td><td>No</td><td>Project type name in local language</td></tr><tr><td>4</td><td>Effective From</td><td></td><td></td><td></td><td>Date from which the project is effective</td></tr><tr><td>5</td><td>Effective To</td><td></td><td></td><td></td><td>Date till which the project is effective</td></tr><tr><td>6</td><td>Is Active</td><td></td><td></td><td></td><td>Whether the project is active or not</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/MUKTA - Master Data-2.xlsx" %}
