# Schedule of rates

## Introduction

Schedule of rates are the items used to estimate civil works. The schedule is common across all the ULBs in a state while the rates specific to a ULB.

## Data Table

<table><thead><tr><th width="122">Code</th><th>Type</th><th width="114">Sub Type</th><th>Variant</th><th width="413">Description</th><th>UOM</th><th>Quantity</th><th width="125">Material Rate</th><th width="100">Labour Rate</th><th width="108">Machinery Rate</th><th width="110">Conveyance</th><th>Royalty</th><th width="129">Labour Cess</th><th>Total</th></tr></thead><tbody><tr><td>FL00001</td><td>Works</td><td>Flooring</td><td>Ground Floor</td><td>Fixing tiles in floors, treads or steps and landing over 25mm bed of C:M (1:1) jointed with neat cement slurry mixed with pigment to match the shade of the tiles including rubbing and polishing complete excluding the cost of pre-cast tiles</td><td>SQM</td><td>1</td><td>1307.80</td><td>3598.02</td><td></td><td>66.01</td><td>4.55</td><td>49.76</td><td>5026.14</td></tr><tr><td>CC00001</td><td>Works</td><td>Cement Concrete</td><td>Ground Floor</td><td>P.C.C. Grade M25 Using Batching plant, Transit Mixer and concrete pump (Data for 120.00 Cum)</td><td>CUM</td><td>120</td><td>329935.45</td><td>7095.84</td><td>49760.82</td><td>32001.01</td><td>15930</td><td>4347.23</td><td>439070.35</td></tr><tr><td>CC00002</td><td>Works</td><td>Cement Concrete</td><td>First Floor</td><td>P.C.C. Grade M25 Using Batching plant, Transit Mixer and concrete pump (Data for 120.00 Cum) [First Floor]</td><td>CUM</td><td>120</td><td>329935.45</td><td>7095.84</td><td>49760.82</td><td>32001.01</td><td>15930</td><td>4347.23</td><td>439070.35</td></tr><tr><td>LD00002</td><td>Labour</td><td>Un Skilled</td><td>Not Applicable</td><td>Survey Khalasi/ Chain Man</td><td>Nos</td><td>1</td><td></td><td>345.00</td><td></td><td></td><td></td><td></td><td>345.00</td></tr></tbody></table>

{% hint style="info" %}
The data given in the table is sample data for reference.
{% endhint %}

## Data Definition

<table><thead><tr><th width="71">#</th><th width="141">Column Name</th><th width="103">Data Type</th><th width="117">Data Size</th><th width="139">Is Mandatory?</th><th>Definition/ Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Alphanumeric</td><td>64</td><td>Yes</td><td>A unique code that identifies the SOR.</td></tr><tr><td>2</td><td>Type</td><td>Text</td><td>256</td><td>Yes</td><td>SOR Type </td></tr><tr><td>3</td><td>Sub Type</td><td>Text</td><td>256</td><td>No</td><td>SOR Sub Type</td></tr><tr><td>4</td><td>Variant</td><td>Text</td><td>256</td><td>No</td><td>SOR Variant</td></tr><tr><td>5</td><td>Description</td><td>Text</td><td>1024</td><td>Yes</td><td>SOR Description</td></tr><tr><td>6</td><td>UOM</td><td>Text</td><td>64</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>7</td><td>Quantity</td><td>Numeric</td><td>(6,0)</td><td>Yes</td><td>Quantity for which rate is defined</td></tr><tr><td>8</td><td>Material Rate</td><td>Numeric</td><td>(6,2)</td><td>No</td><td>Rate</td></tr><tr><td>9</td><td>Labour Rate</td><td>Numeric</td><td>(6,2)</td><td>No</td><td>Rate</td></tr><tr><td>10</td><td>Machinery Rate</td><td>Numeric</td><td>(6,2)</td><td>No</td><td>Rate</td></tr><tr><td>11</td><td>Conveyance</td><td>Numeric</td><td>(6,2)</td><td>No</td><td>Rate</td></tr><tr><td>12</td><td>Royalty</td><td>Numeric</td><td>(6,2)</td><td>No</td><td>Rate</td></tr><tr><td>13</td><td>Labour Cess</td><td>Numeric</td><td>(6,2)</td><td>No</td><td>Rate</td></tr></tbody></table>

## Attachments

{% file src="../../../../../.gitbook/assets/SOR MDT.xlsx" %}
