# Create Detailed Estimate

## Scope

Create Detailed Estimate

Home > Estimates > Estimate Inbox > Create Estimate

## Actors <a href="#actors" id="actors"></a>

ULB: Estimate Creator (Same as existing)

## Details <a href="#details" id="details"></a>

1. A detailed estimate creation screen to be provided.
2. The attributes defining detailed estimate are given in below table.

<table data-header-hidden><thead><tr><th width="73"></th><th width="153"></th><th width="113"></th><th width="135"></th><th></th></tr></thead><tbody><tr><td>#</td><td><strong>Field Name</strong></td><td><strong>Data Type</strong></td><td><strong>Is Mandatory?</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>Estimate Type</td><td>Display</td><td>Yes</td><td>Estimate type, Original.</td></tr><tr><td>2</td><td>Project ID</td><td>Display</td><td>Yes</td><td>Project ID</td></tr><tr><td>3</td><td>Project Sanction Date</td><td>Display</td><td>Yes</td><td>Project sanction date</td></tr><tr><td>4</td><td>Project Name</td><td>Display</td><td>Yes</td><td>Project name</td></tr><tr><td>5</td><td>Project Description</td><td>Display</td><td>Yes</td><td>Project description</td></tr><tr><td></td><td><strong>Search SOR</strong> </td><td></td><td></td><td>It provides the option to search a SOR and add to estimate.</td></tr><tr><td></td><td><strong>SORs</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Code</td><td>Display</td><td>Yes</td><td>SOR code, unique identifier for each SOR.</td></tr><tr><td>2</td><td>SOR Description</td><td>Display</td><td>Yes</td><td>SOR description from the SOR master for the selected SOR.</td></tr><tr><td>3</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>4</td><td>Rate</td><td>Display</td><td>Yes</td><td>The rate defined and effective currently.</td></tr><tr><td>5</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Calculated value out of measurements.</td></tr><tr><td>6</td><td>Amount</td><td>Display</td><td>Yes</td><td>Calculated value and equal to Qty*Amount.</td></tr><tr><td></td><td><strong>Measurements</strong> </td><td></td><td></td><td>A table to capture the measurement details and calculate the quantity using it.</td></tr><tr><td>1.1</td><td>Sr. No.</td><td>Display</td><td>Auto</td><td>Measurement serial number.</td></tr><tr><td>1.2</td><td>Type</td><td>Drop-down</td><td>Yes</td><td>Plus/ Minus measurements.</td></tr><tr><td>1.3</td><td>Name</td><td><p>Alphanumeric</p><p>(32 Chars)</p></td><td>Yes</td><td>The name of the measurement.</td></tr><tr><td>1.4</td><td>Number (Nos)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>No. of items.</td></tr><tr><td>1.5</td><td>Length (L)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>Length measured. Allowed up-to 4 places of decimal.</td></tr><tr><td>1.6</td><td>Breadth (B)</td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>Width measured. Allowed up-to 4 places of decimal.</td></tr><tr><td>1.7</td><td>Height/ Depth </td><td><p>Numeric</p><p>(6,4)</p></td><td>Yes</td><td>Depth measured. Allowed up-to 4 places of decimal.</td></tr><tr><td>1.8</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Qty = N* L*B*D; rounded up-to 4 places of decimal.</td></tr><tr><td>1.9</td><td>Total</td><td>Display</td><td>Yes</td><td>Grid total for the quantities of measurements. rounded up-to 4 places of decimal.</td></tr><tr><td></td><td><strong>Non SORs</strong></td><td></td><td></td><td></td></tr><tr><td>2</td><td>SOR Description</td><td><p>Alphanumeric</p><p>(2048 Chars)</p></td><td>Yes</td><td>SOR description from the SOR master for the selected SOR.</td></tr><tr><td>3</td><td>Unit</td><td>Drop-down</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>4</td><td>Rate</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>The rate defined and effective currently.</td></tr><tr><td>5</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Calculated value out of measurements.</td></tr><tr><td>6</td><td>Amount</td><td>Display</td><td>Yes</td><td>Calculated value and equal to Qty*Amount.</td></tr><tr><td></td><td><strong>Measurements</strong></td><td></td><td></td><td>The table is same as described under SOR.</td></tr><tr><td></td><td><strong>Other Charges</strong></td><td></td><td></td><td>It is going to be same as provided in the existing estimate screen as overhead.</td></tr><tr><td>7</td><td>View Analysis Statements</td><td>Link</td><td>Yes</td><td>It will take the user to analysis page. It gets enabled once the analysis is generated.</td></tr><tr><td></td><td><strong>Actions</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Save as Draft</td><td>Button</td><td>Yes</td><td>Save the estimate as a draft.</td></tr><tr><td>2</td><td>Generate Analysis</td><td>Button</td><td>Yes</td><td>Generates the analysis and populates the figures.</td></tr><tr><td>3</td><td>Submit</td><td>Button</td><td>Yes</td><td>Submit the estimate for verification.</td></tr></tbody></table>

Note: Attachment section to be changed to remove detailed estimate as attachment.

### Actions <a href="#actions" id="actions"></a>

1. Save as Draft - It is to save the details captured for detailed estimate and keeping it with creator.
2. Generate Analysis - Generate the analysis statement out of saved detailed estimate details.
3. Submit - It is the same as per existing estimate, allow the user to forward the estimate for verification.
4. View Analysis Statements - It will take the user to view analysis statement HTML page.

#### On Save as Draft

1. Estimate is created with the details provided.
2. If it is first time, estimate no. is generated as per the format provided.
3. Success toast message is displayed and page is loaded again in editable mode with the details saved.
4. Estimate gets assigned to the creator and made available in his/her inbox with the WF state Drafted.
5. On open from inbox, its get opened in editable mode same as edit estimate.

#### On Submit

1. This action is enabled for saved estimate only.
2. The estimate details are saved.
3. Analysis statements are revised.
4. Estimate is forwarded to verifier.
5. Success page is displayed with success message.

#### On Generate Analysis

1. First time, this action is enabled for saved estimate only.
2. The analysis is generated and Success toast message is displayed.
3. View Analysis Statements link is enabled.

Analysis statements are generated successfully.

Analysis statements generation failed.

### Validations <a href="#validations" id="validations"></a>

1. Only active SORs and active and effective rates are displayed on search SOR.
2. All the intermediate figures are rounded up-to 2 decimal places.

### Configuration <a href="#configuration" id="configuration"></a>

Prerequisite

1. SOR Rate Master

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9447-27072\&mode=design\&t=27hyRLLzWSSph6Bk-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9447-27072\&mode=design\&t=27hyRLLzWSSph6Bk-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. The given additional features are incorporated.
2. The workflow is changed to have the option to save the details as draft.
3. The drafted estimate is assigned to the creator of estimate and can be searched using search estimate.
4. Estimate is made available to the inbox of estimate creator as well.
