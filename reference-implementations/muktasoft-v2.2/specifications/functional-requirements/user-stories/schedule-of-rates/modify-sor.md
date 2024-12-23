# Modify SOR

## Scope

Modify Schedule of Rate

Home > Search SOR > SOR Code (Hyperlink) > View SOR > Modify SOR (action)

## Actors <a href="#actors" id="actors"></a>

State: STATE\_MUKTA\_ADMIN

## Details <a href="#details" id="details"></a>

1. Modify SOR is provided to enable the users to make the correction in the existing SOR.
2. The attributes which are allowed to modify are as given below table.

<table data-header-hidden><thead><tr><th width="80"></th><th width="137"></th><th width="113"></th><th width="125"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Field Name</td><td>Data Type</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>1</td><td>SOR Code</td><td>Display</td><td>Yes</td><td>The SOR code.</td></tr><tr><td>2</td><td>SOR Type</td><td>Display</td><td>Yes</td><td>Material, Labour, Machinery, Works.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Display</td><td>Yes</td><td>Applicable for SOR type Works only.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Display</td><td>Yes</td><td>Applicable for SOR type Works only.</td></tr><tr><td>5</td><td>Is it a basic variant?</td><td>Display</td><td>Yes</td><td>Applicable for SOR type Works only.</td></tr><tr><td>6</td><td>Unit of Measurement</td><td>Display</td><td>Yes</td><td>Unit of measurement for the item.</td></tr><tr><td>7</td><td>Rate Defined for Quantity</td><td>Display</td><td>Yes</td><td>Quantity of items for which basic rate is defined.</td></tr><tr><td>8</td><td>SOR Description</td><td>Alphanumeric</td><td>Yes</td><td>Name of item as per the standard definition of OPWD</td></tr><tr><td>9</td><td>Status</td><td>Drop-down</td><td>Yes</td><td>The status of SOR, Active/ Inactive.</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Submit - On Submit.

1. SOR detailed gets updated successfully.
2. View SOR Page is displayed with updated details.
3. Success toast message is displayed.
4. In case SOR is getting inactivated.
   1. The status of SOR is get updated inactive in case no other active SOR is referencing it.
   2. Otherwise a validation message is displayed and system doesn’t update the status to inactive.

#### Success Message

SOR details has been updated successfully.

### Validations <a href="#validations" id="validations"></a>

1. Validation message a reference SOR is getting inactivated.

`This SOR can not be inactivated as there are active SORs exists referencing it.`

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9349-23606\&mode=design\&t=k7ONf9XhnYdXofyL-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9349-23606\&mode=design\&t=k7ONf9XhnYdXofyL-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. On successful update, View SOR Page is displayed.
2. Toast success message is displayed.
3. System doesn’t allow to change the status to inactive in reference SORs exists.
