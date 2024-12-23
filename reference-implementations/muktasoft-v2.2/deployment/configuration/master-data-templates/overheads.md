# Overheads

## Introduction

Overheads are the items other than SOR and Non-SOR which are included in the estimate to complete the estimation and arrive at the final value of work.

## Data Table

<table><thead><tr><th width="99">Sr. No.</th><th>Code</th><th width="199">Description</th><th width="153">Description (Odiya)</th><th>Is work order value?</th><th width="119">Calculation Method</th><th>Percentage/ Value</th><th>Effective From</th><th>Effective To</th><th>Is Active</th></tr></thead><tbody><tr><td>1</td><td>SC</td><td>Supervision Charge</td><td>ତଦାରଖ ଦେୟ</td><td>Yes</td><td>Percentage</td><td>7.5</td><td></td><td></td><td></td></tr><tr><td>2</td><td>GST</td><td>Goods and Service Tax</td><td>ଦ୍ରବ୍ୟ ଏବଂ ସେବା କର</td><td>Yes</td><td>Percentage</td><td>18</td><td></td><td></td><td></td></tr></tbody></table>

{% hint style="info" %}
The data given in the table is sample data for reference.
{% endhint %}

## Data Definition

<table><thead><tr><th width="97">Sr. No.</th><th>Column Name</th><th width="143">Data Type</th><th>Data Size</th><th width="74">Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the project type.</td></tr><tr><td>2</td><td>Description</td><td>Text</td><td>256</td><td>Yes</td><td>Provides the name of the project type </td></tr><tr><td>3</td><td>Description (Odiya)</td><td>Text</td><td>256</td><td>No</td><td>Project type name in local language</td></tr><tr><td>4</td><td>Is work order value?</td><td>Boolean</td><td></td><td></td><td></td></tr><tr><td>5</td><td>Calculation Method</td><td>Identifier</td><td></td><td></td><td></td></tr><tr><td>6</td><td>Percentage/Value</td><td>Numeric</td><td></td><td></td><td></td></tr><tr><td>4</td><td>Effective From</td><td>Date</td><td></td><td></td><td>Date from which the project is effective</td></tr><tr><td>5</td><td>Effective To</td><td>Date</td><td></td><td></td><td>Date till which the project is effective</td></tr><tr><td>6</td><td>Is Active</td><td>Boolean</td><td></td><td></td><td>Whether the project is active or not</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/MUKTA - Master Data-2.xlsx" %}
