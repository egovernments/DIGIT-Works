# Create MB

## Scope

Create Detailed Measurement Book

Home > Measurement Book> MB Inbox > Create MB

## Actors <a href="#actors" id="actors"></a>

ULB: MB Creator

## Details <a href="#details" id="details"></a>

1. A detailed measurement book creation screen to be provided.
2. From view work order page, action Create MB is provided.
3. The attributes defining detailed measurement are given in below table.

<table data-header-hidden><thead><tr><th width="63"></th><th></th><th></th><th></th><th></th></tr></thead><tbody><tr><td>#</td><td><strong>Field Name</strong></td><td><strong>Data Type</strong></td><td><strong>Is Mandatory?</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>Work order number</td><td>Display</td><td>NA</td><td>Work order number</td></tr><tr><td>2</td><td>Project ID</td><td>Display</td><td>NA</td><td>Project ID</td></tr><tr><td>3</td><td>Project sanction date</td><td>Display</td><td>NA</td><td>Project sanction date</td></tr><tr><td>4</td><td>Project Location</td><td>Display</td><td>NA</td><td>Project location</td></tr><tr><td>5</td><td>Project Name</td><td>Display</td><td>NA</td><td>Project name</td></tr><tr><td>6</td><td>Project Description</td><td>Display</td><td>NA</td><td>Project description</td></tr><tr><td>7</td><td>View MB History</td><td>Link</td><td>NA</td><td>In case second onward MB entries, to show the measurement history in the format given below.</td></tr><tr><td></td><td><strong>MB History</strong> - It is displayed for second onward MB entries.</td><td></td><td></td><td></td></tr><tr><td>1</td><td>Sr. No</td><td>Display</td><td>NA</td><td>Serial number</td></tr><tr><td>2</td><td>MB Reference Number</td><td>Display</td><td>NA</td><td>Measurement reference number</td></tr><tr><td>3</td><td>MB Date</td><td>Display</td><td>NA</td><td>Measurement date</td></tr><tr><td>4</td><td>MB Period</td><td>Display</td><td>NA</td><td>Measurement period</td></tr><tr><td>5</td><td>MB Amount</td><td>Display</td><td>NA</td><td>Measurement amount</td></tr><tr><td>6</td><td>Status</td><td>Display</td><td>NA</td><td>Status of the measurement.</td></tr><tr><td></td><td><strong>MB Period</strong> - It has to follow muster roll muster roll period.</td><td></td><td></td><td></td></tr><tr><td>1</td><td>From Date</td><td>Date</td><td>Yes</td><td>The date from which measurement is taken. In case not the first MB, auto filled with previous MB’s To Date +1.</td></tr><tr><td>2</td><td>To Date</td><td>Date</td><td>Yes</td><td>The date till which measurement is recorded.</td></tr><tr><td></td><td><strong>SORs</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Type</td><td>Display</td><td>Yes</td><td>SOR Sub type as provided in estimate</td></tr><tr><td>2</td><td>Code</td><td>Display</td><td>Yes</td><td>SOR Code as provided in estimate</td></tr><tr><td>3</td><td>SOR Description</td><td>Display</td><td>Yes</td><td>SOR description as provided in estimate</td></tr><tr><td>4</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement as provided in estimate</td></tr><tr><td>5</td><td>Rate</td><td>Display</td><td>Yes</td><td>Rate per unit as provided in estimate.</td></tr><tr><td>6</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Quantity calculated from measurement captured.</td></tr><tr><td>7</td><td>Amount</td><td>Display</td><td>Yes</td><td>Calculated from Rate*Quantity. Rounded up to 2 decimal places.</td></tr><tr><td></td><td><strong>Measurements</strong></td><td></td><td></td><td></td></tr><tr><td>1.1</td><td>Sr. No.</td><td>Display</td><td>Auto</td><td>Serial number of measurement</td></tr><tr><td>1.2</td><td>Type</td><td>Display</td><td>Auto</td><td>Plus/ Minus from estimate.</td></tr><tr><td>1.3</td><td>Name</td><td>Display</td><td>Auto</td><td>The name of the measurement from the estimate.</td></tr><tr><td>1.4</td><td>Number (Nos)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>No. of items if the unit of measurement is number.</td></tr><tr><td>1.5</td><td>Length (L)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>Length measured for completed work.</td></tr><tr><td>1.6</td><td>Breadth (B)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>Width measured for completed work.</td></tr><tr><td>1.7</td><td>Height/ Depth (D)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>Depth measured for completed work.</td></tr><tr><td>1.8</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Qty = N*L*B*D; rounded up-to 4 decimal places.</td></tr><tr><td>1.9</td><td>Total</td><td>Display</td><td>Yes</td><td>Grid total for the quantities of measurements, rounded up-to 4 decimal places.</td></tr><tr><td></td><td><strong>Non SORs</strong> - The above is repeated for Non SORs also except Type and Code.</td><td></td><td></td><td></td></tr><tr><td></td><td><strong>View Utilization Statements</strong> - A link to view the utilization statements in HTML view.</td><td></td><td></td><td></td></tr><tr><td></td><td><strong>Worksite Photos</strong></td><td>Tab</td><td></td><td></td></tr><tr><td>7</td><td>Worksite Photo</td><td>Upload File</td><td>Yes</td><td>5 photos JPG and PNG images are supported.</td></tr><tr><td> </td><td><strong>Actions</strong></td><td> </td><td> </td><td> </td></tr><tr><td>1</td><td>Save as Draft</td><td>Menu</td><td>Yes</td><td>Action to save the measurement record as draft.</td></tr><tr><td>2</td><td>Generate Utilization</td><td>Menu</td><td>Yes</td><td>Action to generate measurement statements out of measurements recorded.</td></tr><tr><td>3</td><td>Submit</td><td>Menu</td><td>Yes</td><td>Action to submit the measurement book for  verification</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

1. Save as Draft - It is to save the details captured for detailed MB and keeping it with creator.
2. Generate Utilization- Generate the utilization statement out of saved detailed MB details.
3. Submit - It is to allow the user to forward the MB for verification.
4. View Utilization Statements - It will take the user to view utilization statement HTML page.
5. View MB History - To display all the MBs created so far as per the detail provided in the table above.

#### On Save as Draft

1. MB is created with the details provided.
2. If it is first time, MB number and MB reference number are generated as per the format provided.
3. Success toast message is displayed and page is loaded again in editable mode with the details saved.
4. MB gets assigned to the creator and made available in his/her inbox with the state Drafted.
5. On open from inbox, its get opened in editable mode same as edit estimate.

#### On Submit

1. This action is enabled for saved (Drafted) MB only.
2. The MB details are saved.
3. Utilization statements are revised.
4. MB is forwarded to verifier.
5. Success page is displayed with success message.

#### On Generate Utilization

1. First time, this action is enabled for saved (Drafted) MB only.
2. The utilization is generated and success toast message is displayed.
3. View Analysis Statements link is enabled.

`Utilization statements are generated successfully.`

`Utilization statements generation failed.`

### Validations <a href="#validations" id="validations"></a>

1. MB period shall follow the muster roll period for each project.
2. Only active SORs and active and effective rates are displayed on search SOR.
3. All the intermediate calculated figures are rounded up-to 2 decimal places.
4. All the measurements can be entered into up-to 4 decimal places.

### Configuration <a href="#configuration" id="configuration"></a>

MB Number

MB/FY: yyyy-yy/SEQUENCE (6 Digits)

MB/2023-24/000091.

MB Reference Number

MB/FY: yyyy-yy/SEQUENCE (6 Digits)/ XX

Here XX 2 digit running sequence no.

MB/2023-24/000091/01

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

First MB

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9180-23221\&mode=design\&t=ymTk31lR0N9tdKmc-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9180-23221\&mode=design\&t=ymTk31lR0N9tdKmc-4)

Second Onward

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9453-23814\&mode=design\&t=ymTk31lR0N9tdKmc-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9453-23814\&mode=design\&t=ymTk31lR0N9tdKmc-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. MB form is designed as per the wire-frame provided.
2. Each MB entry goes for approval.
3. Measurements entered are allowed to be captured up to 4 decimal places.
4. The amount calculated is rounded up to 2 decimal places.
5. Attachment section to attach the site photos.
