# Add/ Edit Rate Analysis

## Scope

Add/ Edit Rate Analysis

1. Home > Search SOR > View SOR > Add Rate Analysis (action)
2. Home > Search SOR > View SOR > Edit Rate Analysis (action)

## Actors <a href="#actors" id="actors"></a>

State: MUKTA\_STATE\_ADMIN

## Details <a href="#details" id="details"></a>

1. Add/Edit rate analysis feature is provided to add or modify the Rate Analysis.
2. To add the rate analysis, “Add Analysis” action is provided from SOR view page for those SOR which doesn’t have a rate analysis linked.
3. To edit the rate analysis, “Edit Analysis” action is provided from SOR view page for those SOR which has a rate analysis linked.
4. To add a SOR item into rate analysis, it should be active.
5. For rate analysis, only Item Code, Description, UOM, and Quantity is stored. Rates then calculated on fly.
6. The attributes defining rate analysis are given in below table.

<table data-header-hidden><thead><tr><th width="90"></th><th width="138"></th><th width="124"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Field Name</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>1</td><td>SOR Code</td><td>Display</td><td>System generated unique code to identify a SOR.</td></tr><tr><td>2</td><td>SOR Type</td><td>Display</td><td>SOR Type as defined and updated.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Display</td><td>SOR sub types as defined and updated.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Display</td><td>SOR variant as defined and updated.</td></tr><tr><td>6</td><td>SOR Unit of Measurement</td><td>Display</td><td>Unit of measurement of SOR.</td></tr><tr><td>7</td><td>Rate Defined for Quantity</td><td>Display</td><td>The quantity for which SOR rate is defined.</td></tr><tr><td>8</td><td>SOR Description</td><td>Display</td><td>SOR description.</td></tr><tr><td>9</td><td>Status</td><td>Display</td><td>Active/ Inactive.</td></tr><tr><td> </td><td>Constituents</td><td><strong>Section</strong></td><td> </td></tr><tr><td>10</td><td>Effective From</td><td>Date</td><td>A effective from date, any date while adding rate analysis first time. Second time onward new analysis can be added from a future date only.</td></tr><tr><td>11</td><td>Analysis Defined for Quantity</td><td>Numeric</td><td>The quantity for which rate analysis is defined.</td></tr><tr><td>12</td><td>Material</td><td>Grid</td><td>SOR of type Material.</td></tr><tr><td>12.1</td><td>Code</td><td>Display</td><td>Unique code defined for the material item.</td></tr><tr><td>12.2</td><td>Name</td><td>Search</td><td>Name of the material item.</td></tr><tr><td>12.3</td><td>Unit</td><td>Display</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>12.4</td><td>Quantity</td><td>Numeric</td><td>Quantity of the item defined for the given SOR.</td></tr><tr><td>13</td><td>Labour</td><td>Grid</td><td>SOR of type labour</td></tr><tr><td>13.1</td><td>Code</td><td>Display</td><td>Unique code defined for the labour item.</td></tr><tr><td>13.2</td><td>Name</td><td>Search</td><td>Name of the labour item.</td></tr><tr><td>13.3</td><td>Unit</td><td>Display</td><td>Unit of measurement on which item is measured. Mostly Nos.</td></tr><tr><td>13.4</td><td>Quantity</td><td>Numeric</td><td>Quantity of the item defined for the given SOR.</td></tr><tr><td>14</td><td><strong>Machinery</strong></td><td>Grid</td><td>SOR of type machinery</td></tr><tr><td>14.1</td><td>Code</td><td>Display</td><td>Unique code defined for the machinery item.</td></tr><tr><td>14.2</td><td>Name</td><td>Search</td><td>Name of the machinery item.</td></tr><tr><td>14.3</td><td>Unit</td><td>Display</td><td>Unit of measurement on which item is measured.</td></tr><tr><td>14.4</td><td>Quantity</td><td>Numeric</td><td>Quantity of the item defined for the given SOR.</td></tr><tr><td>16</td><td><strong>Extra Charges</strong></td><td>Grid</td><td> </td></tr><tr><td>16.1</td><td>Description</td><td>Text</td><td>Extra charge description. E.g. Scaffolding Charges.</td></tr><tr><td>16.2</td><td>Applicable On</td><td>Drop-down</td><td>SOR type values, Material, Labour, Machinery.</td></tr><tr><td>16.3</td><td>Calculation Type</td><td>Drop-down</td><td>Fixed/ Percentage.</td></tr><tr><td>16.4</td><td>Figure</td><td>Numeric</td><td>The figure calculated for fixed or percentage calculation type.</td></tr><tr><td>16.5</td><td>Amount</td><td>Display</td><td>The calculated value, Basic Rate * Figure, or user entered fixed value.</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Submit - On Submit

1. In case a new rate analysis is added.
   1. A new rate analysis record is created and linked with SOR with the given effective date.
   2. Existing rate analysis is closed with the previous day of new effective date.
   3. View Rate Analysis Page is displayed with newly created rate analysis.
   4. A success toast message is displayed.
2. In case existing rate analysis is edited.
   1. Alert message is displayed to confirm if user wants to really modify existing rate analysis.
   2. In case no changes made in the existing rate analysis, system displays an info message without saving it.
   3. Upon confirmation, a new rate analysis record is created and made effective from same effective date.
   4. Existing rate analysis record is made inactive.
   5. View Rate Analysis Page is displayed with newly updated rate analysis.
   6. A success toast message is displayed.
3. In case action is failed due to any reason, failure message is displayed.

Success Message - New Rate Analysis Added.

`Rate analysis has been created for <SORCode> and made effective from <effective date>.`

Success Message - Existing Rate Analysis Edited.

`The rate analysis effective from <effective date> for <SORCode> has been modified successfully.`

Alert! Existing rate analysis is edited.

`Do you want to update existing rate analysis for <SORCode> effective from <effective date>? Please confirm to complete the action.`

The rate analysis has not been modified as there were no changes done.

`Adding new rate analysis is failed.`

`Modification to existing rate analysis is failed.`

### Validations <a href="#validations" id="validations"></a>

* Out of 3 types of SOR items, adding at least one is mandatory.
* All the quantities can be entered up to 4 decimal places.
* All the amount calculated is rounded up to 2 decimal places.
* Effective date should not be a date before current rate analysis effective date.
* Effective from time is always start of the day i.e. 00:00. The time in between in the day is not allowed.
* Effective to date, the time is always 11:59:59.

### Configuration <a href="#configuration" id="configuration"></a>

None.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

<figure><img src="../../../../../../.gitbook/assets/Add Rate Analysis.png" alt=""><figcaption></figcaption></figure>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Rate analysis is allowed to be added to an active SOR only.
2. All the validations are taken care.
3. On Add/ Edit Rate Analysis, rate for respective SOR is created and made effective.
