# Rate Analysis

## Introduction

Rate analysis in Public Works Departments (PWD) involves the examination and calculation of rates for various construction activities or works. It is a systematic process carried out to determine the cost of executing a particular work item per unit quantity. Rate analysis typically involves breaking down the cost components associated with a construction activity, including materials, labor, machinery, contractor's profit, overhead costs, and miscellaneous expenses.

The purpose of rate analysis in PWD is to establish fair and accurate rates for different items of work, which helps in estimating the overall cost of a construction project. These rates are often based on prevailing market prices, standard specifications, and historical data. Rate analysis is crucial for budgeting, tendering, and ensuring transparency and accountability in construction projects undertaken by the Public Works Departments.

## Data Table

<table><thead><tr><th width="83">S.No.</th><th width="133">Works SOR Code (For which rate analysis is defined)</th><th>Quantity for which analysis is defined</th><th>SOR Type (L/M/E)</th><th width="136">SOR Code (L/M/E)</th><th width="149">SOR Description (L/M/E)</th><th width="81">Unit</th><th>Quantity</th></tr></thead><tbody><tr><td>1</td><td>SOR_000148</td><td>1</td><td>Material</td><td>SOR_000016</td><td>4 cm size H.G. metal (H.B.)</td><td>CUM</td><td>0.96</td></tr><tr><td> </td><td>SOR_000148</td><td> </td><td>Material</td><td>SOR_000002</td><td>Sand (screened &#x26; washed)</td><td>CUM</td><td>0.48</td></tr><tr><td> </td><td>SOR_000148</td><td> </td><td>Material</td><td>SOR_000021</td><td>Cement</td><td>QNTL</td><td>2.29</td></tr><tr><td> </td><td>SOR_000148</td><td> </td><td>Labour</td><td>SOR_000315</td><td>Mason 2nd class</td><td>NOs</td><td>0.18</td></tr><tr><td> </td><td>SOR_000148</td><td> </td><td>Labour</td><td>SOR_000291</td><td>Man mulia</td><td>NOs</td><td>2.5</td></tr><tr><td> </td><td>SOR_000148</td><td> </td><td>Labour</td><td>SOR_000292</td><td>Women mulia</td><td>NOs</td><td>1.4</td></tr></tbody></table>

## Data definition

<table><thead><tr><th width="71">#</th><th width="141">Column Name</th><th width="103">Data Type</th><th width="87">Data Size</th><th width="99">Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Works SOR Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the SOR.Works SOR Code<br>(For which rate analysis is defined)</td></tr><tr><td>2</td><td>Quantity for which analysis is defined</td><td>Numeric</td><td>256</td><td>Yes</td><td>Quantity for which analysis is defined </td></tr><tr><td>3</td><td>SOR Type</td><td>Text</td><td>256</td><td>Yes</td><td>SOR Type, labour, material or machinery.</td></tr><tr><td>4</td><td>SOR Code</td><td>Text</td><td>256</td><td>Yes</td><td>SOR Code</td></tr><tr><td>5</td><td>SOR Description</td><td>Text</td><td>1024</td><td>Yes</td><td>SOR Description</td></tr><tr><td>6</td><td>UOM</td><td>Text</td><td>64</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>7</td><td>Quantity</td><td>Numeric</td><td>(6,0)</td><td>Yes</td><td>Quantity which is required to define the rate analysis.</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/Rate Analysis Template.xlsx" %}

