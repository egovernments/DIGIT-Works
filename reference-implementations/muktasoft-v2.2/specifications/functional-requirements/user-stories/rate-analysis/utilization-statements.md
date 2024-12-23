# Utilization Statements

## Scope

Utilization Statements

Utilization statements are generated for measurement of work completed and captured in a MB. Utilization statements can be viewed and download from MB view screen.

## Actors <a href="#actors" id="actors"></a>

ULB: MB creator, verifier, approver and viewer.

## Details <a href="#details" id="details"></a>

1. The utilization statements are generated out of saved MB details.
2. First, these are generated SORs wise.
3. Second,  get consolidated to have Material Wise, Labour Wise, and Machinery Wise View.
4. The attributes and format is same for all three.
5. The option to download into PDF is provided.
6. The attributes defining detailed estimate are given in below table.

<table data-header-hidden><thead><tr><th width="98"></th><th width="151"></th><th></th></tr></thead><tbody><tr><td><strong>Sr. No.</strong></td><td><strong>Field Name</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>Code</td><td>SOR code of type material, labour, or machinery.</td></tr><tr><td>1.2</td><td>Description</td><td>SOR description of type material, labour, or machinery.</td></tr><tr><td>1.3</td><td>Unit</td><td>The unit of measurement</td></tr><tr><td>1.4</td><td>Rate</td><td>Rate per unit</td></tr><tr><td>1.5</td><td>Quantity</td><td>Total quantity required for the given SOR item.</td></tr><tr><td>1.6</td><td>Amount</td><td>Amount calculated rate*quantity.</td></tr><tr><td>1.7</td><td>Total</td><td>Total of all the material required for a given SOR item.</td></tr><tr><td>2</td><td>Grand Total</td><td>Total of all the material required.</td></tr><tr><td></td><td></td><td><strong>Material Wise Consolidation</strong></td></tr><tr><td>1</td><td>Code</td><td>SOR code of type material, labour, or machinery.</td></tr><tr><td>2</td><td>Description</td><td>OR description of type material, labour, or machinery.</td></tr><tr><td>3</td><td>Unit</td><td>The unit of measurement</td></tr><tr><td>4</td><td>Rate</td><td>Rate per unit</td></tr><tr><td>5</td><td>Quantity</td><td>Total quantity required.</td></tr><tr><td>6</td><td>Amount</td><td>Amount calculated rate*quantity.</td></tr><tr><td>7</td><td>Total</td><td>Total of all the material required.</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Not applicable.

### Validations <a href="#validations" id="validations"></a>

Not applicable.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

HTML View:

<figure><img src="../../../../../../.gitbook/assets/Utilization Statements.png" alt=""><figcaption></figcaption></figure>

PDF View:

<figure><img src="../../../../../../.gitbook/assets/Utilization Statement PDF.png" alt=""><figcaption></figcaption></figure>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Utilization is generated of material, labour and machinery required.
2. The format and template is same for all three.
3. Option to download these into PDF to be provided.
