# Analysis Statements

## Scope

Analysis Statements

Analysis statements are generated for an estimate. These can be navigated from estimate view screen using view analysis statement.

## Actors <a href="#actors" id="actors"></a>

ULB: Estimate creator, verifier, technical sanctioner, approver and viewer.

## Details <a href="#details" id="details"></a>

1. The analysis statements are generated out of saved estimate details.
2. First, these analysis statements are generated SORs wise.
3. Second, they consolidated to have Material, Labour , and Machinery Wise.
4. The attributes and format is same for all three statements
5. Option to download into these statements into PDF is provided.
6. The attributes defining detailed estimate are given in below table.

<table data-header-hidden><thead><tr><th width="89"></th><th width="147"></th><th></th></tr></thead><tbody><tr><td><strong>Sr. No.</strong></td><td><strong>Field Name</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>SOR Description</td><td>SOR item from the estimate.</td></tr><tr><td>1.1</td><td>Code</td><td>The item code from the lead charges</td></tr><tr><td>1.2</td><td>Name</td><td>The item name from the lead charges</td></tr><tr><td>1.3</td><td>Unit</td><td>The unit of measurement</td></tr><tr><td>1.4</td><td>Rate</td><td>Rate per unit</td></tr><tr><td>1.5</td><td>Quantity</td><td>Total quantity required for the given SOR item.</td></tr><tr><td>1.6</td><td>Amount</td><td>Amount calculated rate*quantity.</td></tr><tr><td>1.7</td><td>Total</td><td>Total of all the material required for a given SOR item.</td></tr><tr><td>2</td><td>Grand Total</td><td>Total of all the material required.</td></tr><tr><td></td><td></td><td><strong>Material Wise Consolidation</strong></td></tr><tr><td>1</td><td>Code</td><td>The item code from the lead charges</td></tr><tr><td>2</td><td>Name</td><td>The item name from the lead charges</td></tr><tr><td>3</td><td>Unit</td><td>The unit of measurement</td></tr><tr><td>4</td><td>Rate</td><td>Rate per unit</td></tr><tr><td>5</td><td>Quantity</td><td>Total quantity required.</td></tr><tr><td>6</td><td>Amount</td><td>Amount calculated rate*quantity.</td></tr><tr><td>7</td><td>Total</td><td>Total of all the material required.</td></tr></tbody></table>

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

<figure><img src="../../../../../../.gitbook/assets/Analysis Statements.png" alt=""><figcaption></figcaption></figure>

PDF View:

<figure><img src="../../../../../../.gitbook/assets/Analysis Statements PDF.png" alt=""><figcaption></figcaption></figure>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Analysis is generated of Material, Labour and Machinery required.
2. The format and template is same.
3. Option to download these into PDF to be provided.
