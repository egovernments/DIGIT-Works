# Create Template

## Scope

Create Estimate Template

Home > Estimate Template > Search Template > Action (Create Template)

## Actors <a href="#actors" id="actors"></a>

State: STATE\_MUKTA\_ADMIN

## Details <a href="#details" id="details"></a>

1. Create estimate template feature is provided to enable the user to create a template.
2. The attributes defining template are given in below table.

<table data-header-hidden><thead><tr><th width="92"></th><th width="139"></th><th width="110"></th><th width="129"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Field</td><td>Data Type</td><td>Is Mandatory?</td><td>Description</td></tr><tr><td>1</td><td>Project Type</td><td>Drop-down</td><td>Yes</td><td>Project type, the master data from MDMS.</td></tr><tr><td>2</td><td>Template Name</td><td>Text</td><td>Yes</td><td>Name given to template.</td></tr><tr><td>3</td><td>Template Description</td><td>Text</td><td>Yes</td><td>The description, describing the template in detail.</td></tr><tr><td>4</td><td>Search SOR</td><td>Search</td><td>Yes</td><td>To search an SOR and add to the template.</td></tr><tr><td>5</td><td>SORs</td><td>Section</td><td> </td><td> </td></tr><tr><td>5.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>5.1</td><td>Sub Type</td><td>Display</td><td>Yes</td><td>SOR sub type</td></tr><tr><td>5.2</td><td>SOR Code</td><td>Display</td><td>Yes</td><td>SOR code</td></tr><tr><td>5.3</td><td>Description</td><td>Display</td><td>Yes</td><td>SOR description</td></tr><tr><td>5.4</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>6</td><td>Non SORs</td><td>Section</td><td> </td><td> </td></tr><tr><td>6.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>6.2</td><td>Description</td><td>Text</td><td>Yes</td><td>Non SOR description</td></tr><tr><td>6.3</td><td>Unit</td><td>Drop-down</td><td>Yes</td><td>Unit of measurement</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Submit - On Submit

1. In case a template with same set of SORs are already present an alert is displayed.
2. Save the details into system with status Active.
3. Generate a unique code using the format TMP-<4 DIGIT RUNNING SEQUENCE>. e.g. TMP-0001
4. Success page is displayed with the success message and below actions.
   1. Create Template
   2. Go To Search Template
   3. Go To Home Page

It looks like a duplicate template. A template with same set of SORs already exists. Do you want to continue?

### Validations <a href="#validations" id="validations"></a>

1. Another template of same set of SORs alert is displayed.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

<figure><img src="../../../../../../.gitbook/assets/Create Template.png" alt=""><figcaption></figcaption></figure>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Template code is generated as per the format given.
2. Alert on duplicate template.
