# Create SOR

## Scope

Create SOR

Home > Schedule of Rate > Search SOR Page > Action (Create SOR)

## Actors <a href="#actors" id="actors"></a>

State: STATE\_MUKTA\_ADMIN

## Details <a href="#details" id="details"></a>

1. Create SOR feature is provided to enable the users create a SOR with its rate.
2. There are 4 types of SOR defined. Material, Labour, Machinery, and Works.
3. The rates of first 3 type of SORs are directly added by user on creation of SOR.
4. The rate for the SOR of type Works is calculated using rate analysis and added to SOR.
5. The attributes defining SOR are given in below table.

<table data-header-hidden><thead><tr><th width="95"></th><th width="142"></th><th width="127"></th><th width="125"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Field Name</td><td>Data Type</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>1</td><td>SOR Type</td><td>Drop-down</td><td>Yes</td><td>Material, Labour, Machinery, Works.</td></tr><tr><td>2</td><td>SOR Sub Type</td><td>Drop-down</td><td>Yes*</td><td>Applicable for SOR type Works only.</td></tr><tr><td>3</td><td>SOR Variant</td><td>Drop-down</td><td>Yes*</td><td>Applicable for SOR type Works only.</td></tr><tr><td>4</td><td>Unit of Measurement</td><td>Drop-down</td><td>Yes</td><td>Unit of measurement for the item.</td></tr><tr><td>5</td><td>Rate defined for quantity</td><td>Numeric<br>(6,0)</td><td>Yes</td><td>Quantity of items for which basic rate is defined.</td></tr><tr><td>6</td><td>Description</td><td>Alphanumeric (2048 Chars)</td><td>Yes</td><td>Name of item as per the standard definition of OPWD</td></tr><tr><td> </td><td>Rate Details</td><td>Section</td><td>Optional</td><td>It is not applicable to Works SOR.</td></tr><tr><td>7</td><td>Effective From</td><td>Date</td><td>Yes</td><td>The date given rate will become effective, it can be a past and future date. Time is always 00.00</td></tr><tr><td> </td><td>Heads</td><td>Grid</td><td>Optional</td><td>A grid to select the head whichever is applicable.</td></tr><tr><td>8</td><td>Basic Rate</td><td>Numeric<br>(6,2)</td><td>Yes</td><td>The basic rate of the item defined by the OPWD</td></tr><tr><td>9</td><td>Conveyance</td><td>Numeric<br>(6,2)</td><td>No</td><td>The conveyance charges applicable based on the distance item is carried.</td></tr><tr><td>10</td><td>Royalty</td><td>Numeric<br>(6,2)</td><td>No</td><td>The royalty amount on the items as per the state mining department.</td></tr><tr><td>11</td><td>Labour Cess</td><td>Numeric<br>(6,2)</td><td>No</td><td>It is applicable for works items only and calculated on Basic + Conveyance + Royalty.</td></tr><tr><td>12</td><td>Rate/ Unit</td><td>Display</td><td>Yes</td><td>A calculated value.</td></tr><tr><td>13</td><td>Submit and Create Another SOR</td><td>Button</td><td>Yes</td><td>It will allow the user to submit the details and open a new create SOR form.</td></tr><tr><td>14</td><td>Submit</td><td>Button</td><td>Yes</td><td>It saves the SOR details into system and create SOR record.</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Submit - On Submit

1. Save the details into system and SOR is created with the status active.
2. Generate a unique code as per given format.
3. Success page is displayed with the success message and below actions.

a) Create SOR

b) Add Rate Analysis

c) Go To Search SOR

c) Go To Home Page

On Submit and Create Another SOR

1. Save the details into system and SOR is created with the status active.
2. Toast success message is displayed.
3. Create SOR Page is opened to create another SOR.

Success Message

SOR has been created successfully. SOR Code: <>

### Validations <a href="#validations" id="validations"></a>

1. Duplicate record of same type, sub type, and variant is not allowed.
2. Effective from time is always start of the day i.e. 00:00.
3. Effective from date while creating SOR can be any date.

Duplicate Message

This SOR already exists. SOR Code:\<code>.

### Configuration <a href="#configuration" id="configuration"></a>

These are the masters which are configured into MDMS to enable SOR creation.

1. SOR Type
2. SOR Sub Type
3. SOR Variant
4. Unit of Measurement
5. Heads/ Charges

(Separate tickets are created for each of them to configure into MDMS)

SOR Code format.

<table data-header-hidden><thead><tr><th width="122.66666666666666"></th><th width="139"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>SOR Type</td><td>Format</td></tr><tr><td>1</td><td>Material</td><td>LD-&#x3C;5 digit running sequence>. E.g. LD-00001</td></tr><tr><td>2</td><td>Labour</td><td>LD-&#x3C;5 digit running sequence>. E.g. LD-00001</td></tr><tr><td>3</td><td>Machinery</td><td>LD-&#x3C;5 digit running sequence>. E.g. LD-00001</td></tr><tr><td>4</td><td>Works</td><td>&#x3C;SUBTYPECODE>-&#x3C;5 digit running sequence>. E.g. EW-00001</td></tr></tbody></table>

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-24733\&mode=design\&t=k7ONf9XhnYdXofyL-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-24733\&mode=design\&t=k7ONf9XhnYdXofyL-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. SOR can be created without having rate associated with it.
2. It covers all 4 types of SORs. Works SOR are created without rate.
3. Effective from time is always 00:00:00.
4. SOR code is generated as per the format provided.
