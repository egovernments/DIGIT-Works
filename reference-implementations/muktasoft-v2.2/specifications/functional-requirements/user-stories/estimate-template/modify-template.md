# Modify Template

## Scope

Modify Estimate Template

Home > Search Template> Template Code (Hyperlink) > View Template> Modify (action)

## Actors <a href="#actors" id="actors"></a>

State: STATE\_MUKTA\_ADMIN

## Details <a href="#details" id="details"></a>

1. Modify template is provided to enable the users to make the correction in the existing template details.
2. The attributes which are allowed to modify are as given below table.

<table data-header-hidden><thead><tr><th width="73"></th><th width="128"></th><th width="119"></th><th width="127"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Field</td><td>Data Type</td><td>Is Mandatory ?</td><td>Description</td></tr><tr><td>1</td><td>Code</td><td>Display</td><td>Yes</td><td>A unique code generated for template.</td></tr><tr><td>2</td><td>Project Type</td><td>Drop-down</td><td>Yes</td><td>Project types values from master data.</td></tr><tr><td>3</td><td>Name</td><td>Text</td><td>Yes</td><td>Name given to template.</td></tr><tr><td>4</td><td>Description</td><td>Text</td><td>Yes</td><td>The description, describing the template in detail.</td></tr><tr><td>5</td><td>Status</td><td>Drop-down</td><td>Yes</td><td>Status of the template Active/ Inactive</td></tr><tr><td>5</td><td>Search SOR</td><td>Search</td><td>Yes</td><td>To search an SOR and add to the template.</td></tr><tr><td>6</td><td>SORs</td><td> </td><td> </td><td> </td></tr><tr><td>6.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>6.2</td><td>Sub Type</td><td>Display</td><td>Yes</td><td>SOR Sub Type</td></tr><tr><td>6.3</td><td>SOR Code</td><td>Display</td><td>Yes</td><td>SOR Code</td></tr><tr><td>6.4</td><td>Description</td><td>Display</td><td>Yes</td><td>SOR description</td></tr><tr><td>6.5</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>7</td><td>Non SORs</td><td> </td><td> </td><td> </td></tr><tr><td>7.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>7.2</td><td>Description</td><td>Text</td><td>Yes</td><td>Non SOR description</td></tr><tr><td>7.3</td><td>Unit</td><td>Drop-down</td><td>Yes</td><td>Unit of measurement</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Submit - On Submit,

1. Changes are saved and success toast message is displayed.
2. The existing estimates which has already used the template remains unaffected.

Changes to template are saved successfully.

### Validations <a href="#validations" id="validations"></a>

Not applicable.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

### User Interface <a href="#userinterface" id="userinterface"></a>

<figure><img src="../../../../../../.gitbook/assets/View Template.png" alt=""><figcaption></figcaption></figure>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Estimate template is modified.
2. Estimates created remains unchanged.
