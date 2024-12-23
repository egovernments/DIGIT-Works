# Add/ Modify Rate

## Scope

Add/ Modify SOR Rate

Home > Search SOR > SOR Code (Hyperlink) > View SOR > Add/Modify Rate (action)

## Actors <a href="#actors" id="actors"></a>

ULB: MUKTA\_ENG\_ADMIN

## Details <a href="#details" id="details"></a>

1. Add Rate is provided to enable the users add new rate or modify an existing rate.
2. Add/ Modify rate is allowed to only non works SORs only.
3. On add new rate, modify page is opened with the existing details and rates.
4. The attributes which are allowed to modify are as given below table.

<table data-header-hidden><thead><tr><th width="76"></th><th width="138"></th><th width="130"></th><th width="129"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Field Name</td><td>Data Type</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>1</td><td>SOR Code</td><td>Display</td><td>Yes</td><td>The SOR code.</td></tr><tr><td>2</td><td>SOR Type</td><td>Display</td><td>Yes</td><td>Material, Labour, Machinery, Works.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Display</td><td>Yes</td><td>Applicable for SOR type Works only.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Display</td><td>Yes</td><td>Applicable for SOR type Works only.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>Display</td><td>Yes</td><td>Unit of measurement for the item.</td></tr><tr><td>6</td><td>Rate Defined for Quantity</td><td>Display</td><td>Yes</td><td>Quantity of items for which basic rate is defined.</td></tr><tr><td>7</td><td>Description</td><td>Display</td><td>Yes</td><td>Name of item as per the standard definition of OPWD</td></tr><tr><td>8</td><td>Status</td><td>Display</td><td>Yes</td><td>The status of SOR, Active/ Inactive.</td></tr><tr><td> </td><td><strong>Rate Details</strong></td><td> </td><td> </td><td> </td></tr><tr><td>9</td><td>Effective From</td><td>Date</td><td>Yes</td><td>The date from which rate is become effective.</td></tr><tr><td> </td><td><strong>Heads</strong></td><td>Grid</td><td>Yes</td><td>Rate of other than works SORs can only be added.</td></tr><tr><td>10</td><td>Basic Rate</td><td>Numeric</td><td>Yes</td><td>The basic rate of the item defined by the OPWD.</td></tr><tr><td>11</td><td>Conveyance</td><td>Numeric</td><td>No</td><td>The conveyance charges applicable.</td></tr><tr><td>12</td><td>Royalty</td><td>Numeric</td><td>No</td><td>The royalty amount on the items.</td></tr><tr><td>13</td><td>Rate</td><td>Display</td><td>Yes</td><td>A calculated value.</td></tr><tr><td>14</td><td>Submit</td><td>Button</td><td>Yes</td><td>It saves the changes into system.</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

#### Submit - On Submit.

1. In case new rate is added.
   1. A new rate is created with new effective date.
   2. The previous rate is closed with the previous day date.
   3. View SOR Page is displayed with newly added rate.
   4. A success toast message is displayed for new rate creation.

Success Message - New Rate Added

`Rate has been added successfully effective from <effective date>.`

Success Message - Existing Rate Modified

`The rate effective from <effective date> has been modified successfully.`

Alert! On Modify

`Do you want to update existing rate effective from <effective date>? Please confirm to complete the action.`

On Modify (In no change in rate details)

`Modification to existing SOR rate is failed as there were no changes done.`

Adding new rate to the SOR is failed.

Modification to existing SOR rate is failed.

### Validations <a href="#validations" id="validations"></a>

1. Effective date should not be a date before current rate effective date.
2. Effective from time is always start of the day i.e. 00:00. The time in between in the day is not allowed.
3. Effective to date, the time is always 11:59:59.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9349-23239\&mode=design\&t=k7ONf9XhnYdXofyL-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9349-23239\&mode=design\&t=k7ONf9XhnYdXofyL-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Modification of existing rate is allowed.
2. Adding new rate with a new effective date is allowed.
3. On modification, alert message is displayed.
4. On successful add/modify rate, success toast message is displayed.
