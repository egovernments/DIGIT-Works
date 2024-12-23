# View SOR

## Scope

View Schedule of Rates

Home > Search SOR > SOR Code (Hyperlink) > View SOR

## Actors <a href="#actors" id="actors"></a>

State: STATE\_MUKTA\_ADMIN

ULBs: MUKTA\_ENG\_ADMIN

## Details <a href="#details" id="details"></a>

1. View SOR is provided to view the details of created active/inactive SOR.
2. Search a SOR  using the search SOR and then open to view the details.
3. The details displayed as given below.

### Attributes

<table data-header-hidden><thead><tr><th width="83.66666666666666"></th><th width="187"></th><th></th></tr></thead><tbody><tr><td>#</td><td>Field Name</td><td>Description</td></tr><tr><td>1</td><td>SOR Code</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Type</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>4</td><td>SOR Variant</td><td>SOR variant, the values from the SOR Variant Master.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>The unit of measurement.</td></tr><tr><td>6</td><td>Rate Defined for Quantity</td><td>The quantity of SOR for which rate is provided.</td></tr><tr><td>7</td><td>SOR Description</td><td>It is the description of SOR to describe the SOR.</td></tr><tr><td>8</td><td>Status</td><td>The status of SOR Active/ Inactive. Active means active for usage.</td></tr><tr><td> </td><td><strong>Rate Details</strong></td><td>SOR rate, based on the search performed.</td></tr><tr><td>9</td><td>Effective From</td><td>The date from which the rate is effective.</td></tr><tr><td> </td><td><em>Heads</em></td><td>A grid to select the head which ever applicable is provided.</td></tr><tr><td>10</td><td>Basic Rate</td><td>Basic rate of the SOR, provided by the state PWD.</td></tr><tr><td>11</td><td>Conveyance</td><td>Conveyance cost defined for the unit of quantity given in SOR.</td></tr><tr><td>12</td><td>Royalty</td><td>Royalty defined for the unit of quantity given in SOR.</td></tr><tr><td>13</td><td>Labour Cess</td><td>The amount of labour cess, it is applicable to SOR of type Works only.</td></tr><tr><td>14</td><td>Rate</td><td>The final rate of SOR.</td></tr><tr><td> </td><td><strong>Rate History</strong></td><td>History of rates which were effective in the past.</td></tr><tr><td>1</td><td>Serial No.</td><td>Serial number of the record.</td></tr><tr><td>2</td><td>Effective From</td><td>The rate effective from date.</td></tr><tr><td>3</td><td>Rate/ Unit</td><td>The net effective rate.</td></tr><tr><td>4</td><td>View Details</td><td>Button to view the break-up of rate.</td></tr><tr><td>5</td><td>Actions</td><td><ol start="1"><li>Modify SOR - Applicable to all type of SOR. (State Users Only)</li><li>Add/ Modify Rate - Applicable to other than Works (State/ ULB Users)</li><li>Add/ Modify Rate Analysis - Applicable to Works type of SOR. (State Users Only)</li><li>View Rate Analysis - Applicable to Works type of SOR. (State/ ULB Users)</li></ol></td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

Modify SOR - It is applicable to all type of SOR and enable the user to change of SOR description and status.

Add/ Modify Rate - It is applicable to all type of SOR other than Works and enable the user to add new rate or modify existing rate.

Add/ Modify Analysis - It is applicable to Works type of SOR only and enable the user to add rate analysis or modify existing rate analysis.

View Rate Analysis - It is applicable to Works type of SOR only and enable the user to view the currently linked rate analysis.

### Validations <a href="#validations" id="validations"></a>

Not applicable.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-24969\&mode=design\&t=k7ONf9XhnYdXofyL-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-24969\&mode=design\&t=k7ONf9XhnYdXofyL-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. SOR details is displayed as per the attribute provided in the story.
2. Rate is displayed as per the search result.
3. View history displayed the history of rates.
