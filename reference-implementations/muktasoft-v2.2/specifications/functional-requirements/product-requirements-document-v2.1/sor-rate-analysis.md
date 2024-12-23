---
description: 'Rate Analysis: Understanding and Application'
---

# SOR Rate Analysis

## Introduction

Rate analysis in Public Works Departments (PWD) involves the examination and calculation of rates for various construction activities or works. It is a systematic process carried out to determine the cost of executing a particular work item per unit quantity. Rate analysis typically involves breaking down the cost components associated with a construction activity, including materials, labor, machinery, contractor's profit, overhead costs, and miscellaneous expenses.

The purpose of rate analysis in PWD is to establish fair and accurate rates for different items of work, which helps in estimating the overall cost of a construction project. These rates are often based on prevailing market prices, standard specifications, and historical data. Rate analysis is crucial for budgeting, tendering, and ensuring transparency and accountability in construction projects undertaken by the Public Works Departments.

## Scope <a href="#docs-internal-guid-4b568d38-7fff-f284-334d-71f552761871" id="docs-internal-guid-4b568d38-7fff-f284-334d-71f552761871"></a>

1. Schedule of rates
   1. Definition
   2. Rates
   3. Rate analysis
2. Usage of rate analysis
   1. Generation of analysis statements out of estimate
   2. Generation of utilization statements out of measurement book
   3. Revising the rates of works SORs
3. Download of documents
   1. Labour, Material, and Machinery analysis statements from estimate
   2. Labour, Material, and Machinery analysis statements from work order in CBO application
   3. Labour, Material, and Machinery utilization statements from measurement book

## Functional Details

### Actors <a href="#docs-internal-guid-0a80fc3a-7fff-0727-c8bf-3402307f95ef" id="docs-internal-guid-0a80fc3a-7fff-0727-c8bf-3402307f95ef"></a>

1. State Users
2. ULB Users

### Feature - Role Mapping 

<table><thead><tr><th width="283">Users/Features</th><th>State Users</th><th>ULB Users</th></tr></thead><tbody><tr><td>Add Rate Analysis</td><td>Yes</td><td>No</td></tr><tr><td>Edit Rate Analysis</td><td>Yes</td><td>No</td></tr><tr><td>View Rate Analysis</td><td>Yes</td><td>Yes</td></tr><tr><td>Revise Rate </td><td>No</td><td>Yes</td></tr></tbody></table>

### **Add Rate Analysis**&#x20;

It enables the user to add rate analysis for a Works SOR.&#x20;

To add the rate analysis -

1. Search for the relevant SOR and open it to view the details.&#x20;
2. Select the Add Rate Analysis option from Take Action - this will open the add rate analysis form with the following attributes:&#x20;

### **Attributes**

<table><thead><tr><th width="100">#</th><th width="155">Field Name</th><th width="155">Is Mandatory</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>Display</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Type</td><td>Display</td><td>SOR Type, Works only.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Display</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Display</td><td>SOR variant, the values from the SOR Variant Master.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>Display</td><td>Unit of measurement.</td></tr><tr><td>6</td><td>Quantity for which rate is defined</td><td>Display</td><td>The quantity for which SOR rate is defined.</td></tr><tr><td>7</td><td>SOR Description</td><td>Display</td><td>SOR description.</td></tr><tr><td>8</td><td>Status</td><td>Display</td><td>Active/ Inactive.</td></tr><tr><td><br></td><td>Rate Analysis</td><td><br></td><td><br></td></tr><tr><td>9</td><td>Effective From</td><td>Date</td><td>The effective date from which the added rate analysis will become effective.</td></tr><tr><td>10</td><td>Quantity for which analysis is defined</td><td>Numeric</td><td>The quantity for which rate analysis is defined.</td></tr><tr><td>11</td><td><strong>Material</strong></td><td><strong>Section</strong></td><td><br></td></tr><tr><td>11.1</td><td>Code</td><td>Display</td><td>Unique code defined for the material item.</td></tr><tr><td>11.2</td><td>Name</td><td>Search</td><td>Name of the material item.</td></tr><tr><td>11.3</td><td>Unit</td><td>Display</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>11.4</td><td>Basic Rate</td><td>Display</td><td>Rate of item defined for a unit</td></tr><tr><td>11.5</td><td>Quantity</td><td>Numeric</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>11.6</td><td>Amount</td><td>Display</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>11.7</td><td>Total</td><td>Display</td><td>Total of all the items added under material.</td></tr><tr><td>12</td><td><strong>Labour</strong></td><td><strong>Section</strong></td><td><br></td></tr><tr><td>12.1</td><td>Code</td><td>Display</td><td>Unique code defined for the labor item.</td></tr><tr><td>12.2</td><td>Name</td><td>Search</td><td>Name of the labor item.</td></tr><tr><td>12.3</td><td>Unit</td><td>Display</td><td>Unit of measurement on which item is measured. Mostly Nos.</td></tr><tr><td>12.4</td><td>Basic Rate</td><td>Display</td><td>Rate of item defined for a unit.</td></tr><tr><td>12.5</td><td>Quantity</td><td>Numeric</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>12.6</td><td>Amount</td><td>Display</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>12.7</td><td>Total</td><td>Display</td><td>Total of all the items added under labor.</td></tr><tr><td>13</td><td><strong>Machinery</strong></td><td><strong>Section</strong></td><td><br></td></tr><tr><td>13.1</td><td>Code</td><td>Display</td><td>Unique code defined for the machinery item.</td></tr><tr><td>13.2</td><td>Name</td><td>Search</td><td>Name of the machinery item.</td></tr><tr><td>13.3</td><td>Unit</td><td>Display</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>13.4</td><td>Basic Rate</td><td>Display</td><td>Rate of item defined for a unit</td></tr><tr><td>13.5</td><td>Quantity</td><td>Numeric</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>13.6</td><td>Amount</td><td>Display</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>13.7</td><td>Total</td><td>Display</td><td>Total of all the items added under machinery.</td></tr><tr><td>14</td><td><strong>Extra Charges</strong></td><td><br></td><td><br></td></tr><tr><td>14.1</td><td>Description</td><td>Text</td><td>Extra charge description</td></tr><tr><td>14.2</td><td>Applicable On</td><td>Drop down</td><td>The name of the component on which additional amount to be added.</td></tr><tr><td>14.3</td><td>Calculation Type</td><td>Drop-down</td><td>Fixed/ Percentage.</td></tr><tr><td>14.4</td><td>Figure</td><td>Numeric</td><td>The figure for fixed or percentage calculation type.</td></tr><tr><td>14.5</td><td>Amount</td><td>Display</td><td>The calculated value, Basic Rate * Figure, or user entered fixed value.</td></tr><tr><td>15</td><td>Basic Rate/ RAQ UOM</td><td>Display</td><td>This is the rate for the base variant for the quantity defined.</td></tr><tr><td>16</td><td>Basic Rate/ SORQ UOM</td><td>Display</td><td>This is the rate for base variant in case variant per unit quantity.</td></tr></tbody></table>

#### **Screen Mock-ups**

<figure><img src="https://lh7-us.googleusercontent.com/KmveO8YPnGkW-tstY6FiuI8IxjuAtCJN-loQCs_xt5E4pQjPXKcKFfKxDxJSDXigBSGE3jSzHQGj-TUNWmpWT-lD8VHVLxG54UA-l2zm7jZoQ9rtkk9L0XgW7CpwPfee_-YPAGG7PG-1UUNHMnCugw" alt=""><figcaption></figcaption></figure>

### **Search Rate Analysis**

Search SOR is used to search a SOR and view the rate analysis associated with it.

#### Attributes

A SOR is searched to view the SOR details and rate analysis details. The revision of rates can also be scheduled. The search parameters are given below.

1. SOR Type
2. SOR Sub Type
3. SOR Variant
4. SOR Code

#### Screen Mock-ups

<figure><img src="https://lh7-us.googleusercontent.com/ot4a4H5Bel2lh7QTHH4b4gah2xoPpbc3W8tBxC3CeofMI3ft9X0AGlP3kFkKpkKbwJQyhMNLDlwbiwJTlxitaWfWHBP-BbiWx3n6BP__lXgETfyeEer4AHSKHd2emrcewDHiyTwZpyptenQQPvpvoA" alt=""><figcaption></figcaption></figure>

### **View Rate Analysis**

View rate analysis enables the user to view the details of rate analysis for a SOR.

**Attributes**

<table><thead><tr><th width="97">#</th><th width="202">Field Name</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>Unique code generated for a SOR.</td></tr><tr><td>2</td><td>SOR Type</td><td>SOR type name.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>SOR sub type name as defined in the SOR master.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Variant name as defined in variant master.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>Unit of measurement.</td></tr><tr><td>6</td><td>Rate Defined for Quantity</td><td>The quantity for which rate analysis is defined.</td></tr><tr><td>7</td><td>SOR Description</td><td>SOR description.</td></tr><tr><td>8</td><td>Status</td><td>Active/ Inactive</td></tr><tr><td><br></td><td>Analysis</td><td><br></td></tr><tr><td>9</td><td>Effective From</td><td>Display the date when it was last modified.</td></tr><tr><td>10</td><td>Analysis Defined for Quantity</td><td>The quantity for which rate analysis is defined.</td></tr><tr><td>11</td><td><strong>Labour</strong></td><td><br></td></tr><tr><td>11.1</td><td>Code</td><td>Unique code defined for the labor item.</td></tr><tr><td>11.2</td><td>Name</td><td>Name of the labor item.</td></tr><tr><td>11.3</td><td>unit</td><td>Unit of measurement on which item is measured. Mostly Nos.</td></tr><tr><td>11.4</td><td>Basic Rate</td><td>Rate of item defined for a unit.</td></tr><tr><td>11.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>11.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>11.7</td><td>Total</td><td>Total of all the items added under labor.</td></tr><tr><td>12</td><td><strong>Material</strong></td><td><br></td></tr><tr><td>12.1</td><td>Code</td><td>Unique code defined for the material item.</td></tr><tr><td>12.2</td><td>Name</td><td>Name of the material item.</td></tr><tr><td>12.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>12.4</td><td>Basic Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>12.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>12.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>12.7</td><td>Total</td><td>Total of all the items added under material.</td></tr><tr><td>13</td><td><strong>Machinery</strong></td><td><br></td></tr><tr><td>13.1</td><td>Code</td><td>Unique code defined for the machinery item.</td></tr><tr><td>13.2</td><td>Name</td><td>Name of the machinery item.</td></tr><tr><td>13.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>13.4</td><td>Basic Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>13.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>13.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>13.7</td><td>Total</td><td>Total of all the items added under machinery.</td></tr><tr><td>14</td><td><strong>Royalty</strong></td><td><br></td></tr><tr><td>14.1</td><td>Code</td><td>Unique code defined for the material item.</td></tr><tr><td>14.2</td><td>Name</td><td>Name of the material item.</td></tr><tr><td>14.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>14.4</td><td>Royalty Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>14.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>14.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>14.7</td><td>Total</td><td>Total of all the items added under material.</td></tr><tr><td>15</td><td><strong>Conveyance</strong></td><td><br></td></tr><tr><td>15.1</td><td>Code</td><td>Unique code defined for the material item.</td></tr><tr><td>15.2</td><td>Name</td><td>Name of the material item.</td></tr><tr><td>15.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>15.4</td><td>Conveyance Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>15.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>15.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>15.7</td><td>Total</td><td>Total of all the items added under material.</td></tr><tr><td>16</td><td><strong>DMF</strong></td><td>District Mineral Fund</td></tr><tr><td>16.1</td><td>Code</td><td>Unique code defined for the material item.</td></tr><tr><td>16.2</td><td>Name</td><td>Name of the material item.</td></tr><tr><td>16.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>16.4</td><td>DMF Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>16.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>16.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>16.7</td><td>Total</td><td>Total of all the items added under material.</td></tr><tr><td>17</td><td>EMF</td><td><strong>Environment Management Fund</strong></td></tr><tr><td>17.1</td><td>Code</td><td>Unique code defined for the material item.</td></tr><tr><td>17.2</td><td>Name</td><td>Name of the material item.</td></tr><tr><td>17.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>17.4</td><td>EMF Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>17.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>17.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>17.7</td><td>Total</td><td>Total of all the items added under material.</td></tr><tr><td>18</td><td><strong>Additional Charges</strong></td><td><br></td></tr><tr><td>18.1</td><td>Code</td><td>Unique code defined for the material item.</td></tr><tr><td>18.2</td><td>Name</td><td>Name of the material item.</td></tr><tr><td>18.3</td><td>Unit</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>18.4</td><td>EMF Rate</td><td>Rate of item defined for a unit</td></tr><tr><td>18.5</td><td>Quantity</td><td>Quantity of the item defined for the given SOR. </td></tr><tr><td>18.6</td><td>Amount</td><td>The total amount for item arrive from Quantity * Rate.</td></tr><tr><td>18.7</td><td>Total</td><td>Total of all the items added under material.</td></tr><tr><td>19</td><td><strong>Extra Charges</strong></td><td>It is applicable only for variants other than Basic Variant.</td></tr><tr><td>13.1</td><td>Description</td><td>Variant description</td></tr><tr><td>13.2</td><td>Applicable On</td><td>Heads/ Components, like Labour Basic Rate, Material Basic Rate etc.</td></tr><tr><td>13.3</td><td>Calculation Type</td><td>Fixed/ Percentage.</td></tr><tr><td>13.4</td><td>Figure</td><td>The figure for fixed or percentage calculation type.</td></tr><tr><td>13.5</td><td>Amount</td><td>Calculated extra amount on top of base variant.</td></tr><tr><td>13.6</td><td>Total</td><td>Total of all the items added under variants.</td></tr><tr><td>20</td><td>Labour Cess</td><td>It is calculated on the sum of all above components.</td></tr><tr><td>21</td><td>Rate/ Qty UOM</td><td>This is the rate calculated for the quantity rate analysis defined.</td></tr><tr><td>22</td><td>Rate/ UOM</td><td>This is the rate calculated as per latest rates per unit quantity.</td></tr><tr><td>23</td><td>Existing Rate/ UOM</td><td>Thus is SORs existing rate.</td></tr><tr><td>24</td><td>Take Actions</td><td><ol><li>Edit Rate Analysis</li><li>Add Rate Analysis</li><li>Revise Rate</li></ol></td></tr></tbody></table>

**Screen Mock-ups**

<figure><img src="https://lh7-us.googleusercontent.com/LHzwIcJTp805Lw2a3VK4IbWyaYZw16Rh64QxdUpgbnwRqhixKFAnFkvNqc6cbmg22WzOkSJBywz0SwH4OCZv33g8OX-aeWG_7U-DBouFb8Wk2LHglK-W24J4uisQ4eKX4CJt-hOXrSNkMTQtvGm9Tw" alt=""><figcaption></figcaption></figure>

### **Edit Rate Analysis**

Edit rate analysis allows the user to modify the existing rate analysis, on modification the existing rate analysis is marked inactive while the new rate analysis is added and made effective from the same date and time for which the existing rate analysis was effective.

### **Revision of Rates**

The rates of works SORs are revised when the rates of SORs of type Material, Labour, and Machinery are revised. The rates for works SORs are to be revised by scheduling a job in the system. The user at the ULB takes a call to revise the rate and accordingly schedule it in the system.

#### **Scheduling a JOB**

<figure><img src="https://lh7-us.googleusercontent.com/mT4D5_89mUBPE5Y7zxQcqiesl4jkNgkCtieKhd-xbKnvOyKvsdNhLPIEt7hG8gGApfTemmqwwc-nD64guT29IIrm9oIZ_ZjmYQzBNGKMand87TcKJ0zST1HlgoERv9TnLQ8uIDnZbUlX5Ft9l9Bz7Q" alt=""><figcaption></figcaption></figure>

#### View Scheduled Jobs

It allows the users to search, view and track the status of the JOBs scheduled for revising rate.&#x20;

<figure><img src="https://lh7-us.googleusercontent.com/ZSRtd2b-e8jYVLXGznp6yz7JO6TtoW_AxY1zLfaorLWNW9YvEa3nbfPLZ9LeHK6OhB9sDOCpXZCEIMZjHcspwWpFUSHy8blP_6zwVn87Edziv59d5Brxii4umogk03NaelY8wWUtiQvVB1O9aajFQg" alt=""><figcaption></figcaption></figure>

### Analysis Statements

The analysis of estimates is done to know the breakup of the labour, material and machinery at the item and cost level. Once an estimate detail is saved, analysis statements can be generated. Action to generate analysis is provided from the Edit/ View Estimate page, once generated the same can be downloaded from the edit/view screen using the download option in PDF format.

The analysis of material out of the estimate prepared is the process of identifying the quantity of material which is required to accomplish the work. It generates a statement for material to be procured and then handed over to CBO along with the work order.

#### **Attributes**

<table><thead><tr><th width="89">#</th><th width="220">Field Name</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Description</td><td>SOR item from the estimate.</td></tr><tr><td>1.1</td><td>Code</td><td>The item code from the lead charges</td></tr><tr><td>1.2</td><td>Name</td><td>The item name from the lead charges</td></tr><tr><td>1.3</td><td>Unit</td><td>The unit of measurement</td></tr><tr><td>1.4</td><td>Rate</td><td>Rate per unit</td></tr><tr><td>1.5</td><td>Quantity</td><td>Total quantity required for the given SOR item.</td></tr><tr><td>1.6</td><td>Amount</td><td>Amount calculated rate*quantity.</td></tr><tr><td>1.7</td><td>Total</td><td>Total of all the material required for a given SOR item.</td></tr><tr><td>2</td><td>Grand Total</td><td>Total of all the material required.</td></tr><tr><td></td><td></td><td><strong>Material Wise Consolidation</strong></td></tr><tr><td>1</td><td>Code</td><td>The item code from the lead charges</td></tr><tr><td>2</td><td>Name</td><td>The item name from the lead charges</td></tr><tr><td>3</td><td>Unit</td><td>The unit of measurement</td></tr><tr><td>4</td><td>Rate</td><td>Rate per unit</td></tr><tr><td>5</td><td>Quantity</td><td>Total quantity required.</td></tr><tr><td>6</td><td>Amount</td><td>Amount calculated rate*quantity.</td></tr><tr><td>7</td><td>Total</td><td>Total of all the material required.</td></tr></tbody></table>

**Screen Mock-ups**

<figure><img src="https://lh7-us.googleusercontent.com/2RSnSGA8lz7F8MI7wDU1g7lEKX2tWu7ol8w_w-QUT_faKwcvtEyTHPHtiTDKDA2VjQUeWuJr4MCshkCfy_PbcfTwb_Z34tjzSVvBCW0RWoOFgIwbauEAgkagYQ6SO7G0HHlTp_xEyn-Ilu9HtobhqQ" alt=""><figcaption></figcaption></figure>

### Utilization Statements

Utilization statements are generated out of the measurement book and are used to validate the Muster Roll and Purchase Bill.

#### **Material Utilization**

Using the rate analysis the material utilization statement is created for the consumed quantity of SOR items in the measurement book and a statement in the same format as the material analysis statement is created. The cost of material utilized here is used to validate the purchase bill created for payment now.

#### **Labour Utilization**

Using the rate analysis the labour utilization statement is created for the consumed quantity of SOR items in the measurement book and a statement in the same format as the labour analysis statement is created. The cost of labour utilized here is used to validate the muster roll created for the period.

#### **Machinery Utilization**

Using the rate analysis the machinery utilization statement is created for the consumed quantity of SOR items in the measurement book and a statement in the same format as the machinery analysis statement is created. The cost of machinery utilized here is used to validate the purchase bill created for the period.

### Analysis Statements Download

The option to download the estimate analysis statements is to be enabled in the view work order screen for both CBO and Employee.

<figure><img src="https://lh7-us.googleusercontent.com/bmIcTkJBbawShNvQJaz3WU-_WwXI3qZsBQRbl5dMVAfVGpXOY3oU1VGsFIrJGlucfgN6hrtp6pe8mC6C15eyMXE9IDxocoXK6KafMoToTq2OmTc-cT7qf2wxNYnSFesJPV1D1SQ4Rj-dHyGBLBNMHQ" alt=""><figcaption></figcaption></figure>

Same way labour and machinery analysis statement PDFs are also generated and allowed to be downloaded.
