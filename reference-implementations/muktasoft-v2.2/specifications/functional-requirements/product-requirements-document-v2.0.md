# Product Requirements Document v2.0

### Introduction <a href="#tb1v2iqvwlas" id="tb1v2iqvwlas"></a>

Mukhyamantri Karma Tatpara Abhiyan Yojana ( MUKTA Yojana) is a government scheme and This scheme is helpful for the poor urban people, which leads to the rising of the employment rate of the state. This document is prepared to detail out the specification MUKTASoft v2.0.

MUKTASoft aims to improve the overall scheme efficiency of MUKTA by identifying & providing equal job opportunities to the urban poor, construct environment-friendly projects, develop local communities and slums & plan better for upcoming years.

### Purpose <a href="#id-9cisne6xhkib" id="id-9cisne6xhkib"></a>

The purpose of this document is to give a detailed description of the requirements for the MUKTASoft v2.0. It will illustrate the purpose and complete declaration for the development of the system. It will also explain system constraints, interface and interactions with other external applications. This document is primarily intended to define the scope of the version v2.0 and propose to the stakeholders for its approval and as a reference for developing the next version of the system for the development team.

### Definitions, Acronyms & Abbreviations <a href="#qzq77sfsp70h" id="qzq77sfsp70h"></a>

<table data-header-hidden><thead><tr><th width="237"></th><th></th></tr></thead><tbody><tr><td>JE</td><td>Junior Engineer</td></tr><tr><td>ME</td><td>Municipal Engineer</td></tr><tr><td>EO</td><td>Executive Officer</td></tr><tr><td>MC</td><td>Municipal Corporation</td></tr><tr><td>DDO</td><td>Drawing and Disbursing Officer</td></tr><tr><td>SOR</td><td>Schedule of Rates</td></tr><tr><td>WO</td><td>Work Order</td></tr><tr><td>PO</td><td>Purchase Order</td></tr><tr><td>MB</td><td>Measurement Book</td></tr></tbody></table>

### Source of Information <a href="#jhiymemba6hg" id="jhiymemba6hg"></a>

1\. MUKTA [FRS](https://drive.google.com/file/d/14C1KzG_WvSOOLOhjfuWOyMz3M7FLrYM9/view?usp=share_link)

2\. Field Visit to Dhenkanal and Jatni ULBs \[**21st & 22nd June 2023**]

### In Scope <a href="#fynoodnocymm" id="fynoodnocymm"></a>

1. Schedule of Rates
   1. Material
   2. Machinery
   3. Labour
   4. Works (Includes Material, Machinery, Labour)
2. Detailed Estimate
3. Detailed Measurement Book
4. Muster roll and purchase bill validations

### Scope of v2.1, v2.2, v2.3, v2.4 and v2.5 <a href="#opk9g5idu0s5" id="opk9g5idu0s5"></a>

1. Rate Analysis
2. Estimate Template
3. Analysis Statements
   1. Labour Analysis
   2. Material Analysis
   3. Machinery Analysis
4. Revise Estimate
5. Revise Work Order
6. Cancel Work Order
7. Utilization Statements
   1. Labour Utilization Statement (Quantity of Work Completed)
   2. Material Utilization Statement
   3. Machinery Utilization Statement
8. Project Closure
9. Dashboard Enhancements

### Functional Details <a href="#ijp25hw7g0em" id="ijp25hw7g0em"></a>

### Actors <a href="#ay6fk8fo01to" id="ay6fk8fo01to"></a>

<table><thead><tr><th width="241">Users/Features</th><th width="187.66666666666663">State</th><th>ULB</th></tr></thead><tbody><tr><td>Create SOR</td><td>Yes</td><td>No</td></tr><tr><td>Search SOR</td><td>Yes</td><td>Yes</td></tr><tr><td>View SOR</td><td>Yes</td><td>Yes</td></tr><tr><td>Modify SOR</td><td>Yes</td><td>No</td></tr><tr><td>Add/Modify Rate</td><td>No</td><td>Yes</td></tr></tbody></table>

### Schedule of Rates <a href="#l7vg7evde8la" id="l7vg7evde8la"></a>

labourThe basic rate of material, labour, and machinery is decided by the state public works department which would be the same for a group of ULBs and then the final cost of SORs would vary from ULB to ULB based on the Conveyance and Royalty Charges applicable for the ULB.

#### Units of Measurement <a href="#xcdxa6i7fufb" id="xcdxa6i7fufb"></a>

1. CUM
2. QNTL
3. MT
4. NOs
5. KG
6. LTR
7. SQM
8. HOUR
9. EACH
10. MTR
11. KL

#### SOR Heads <a href="#hcvpwd1dse5z" id="hcvpwd1dse5z"></a>

1. Basic Rate (Material, Labour, Machinery)
2. Conveyance
3. Royalty
4. Labour Cess

#### SOR Type <a href="#m6hlhia1n0og" id="m6hlhia1n0og"></a>

There are a total of 4 types of SOR.

<table><thead><tr><th width="117">S.No.</th><th width="206.33333333333331">Code</th><th>Type</th></tr></thead><tbody><tr><td>1</td><td>M</td><td>Material</td></tr><tr><td>2</td><td>L</td><td>Labour</td></tr><tr><td>3</td><td>E</td><td>Machinery</td></tr><tr><td>4</td><td>W</td><td>Works</td></tr></tbody></table>

#### SOR Sub Type <a href="#xrikr24n43l9" id="xrikr24n43l9"></a>

sub-typesThe SOR of type works are grouped into various sub-types as given below.

<table><thead><tr><th width="108.66666666666666">S.No.</th><th width="138">Code</th><th>Sub Type</th></tr></thead><tbody><tr><td>1</td><td>EW</td><td>EARTH WORK</td></tr><tr><td>2</td><td>CC</td><td>CEMENT CONCRETE</td></tr><tr><td>3</td><td>RC</td><td>RCC WORK</td></tr><tr><td>4</td><td>MB</td><td>MASONRY BRICK WORK</td></tr><tr><td>5</td><td>MS</td><td>MASONRY STONE WORK</td></tr><tr><td>6</td><td>PL</td><td>PLASTERING</td></tr><tr><td>7</td><td>WC</td><td>WHITE &#x26; COLOUR WASHING</td></tr><tr><td>8</td><td>FL</td><td>FLOORING</td></tr><tr><td>9</td><td>PA</td><td>PAINTING</td></tr><tr><td>10</td><td>RD</td><td>ROAD WORK</td></tr><tr><td>11</td><td>WD</td><td>WOOD WORK</td></tr><tr><td>12</td><td>RF</td><td>ROOFING</td></tr><tr><td>13</td><td>DI</td><td>DISMANTLING</td></tr><tr><td>14</td><td>PB</td><td>PAVER BLOCK</td></tr><tr><td>15</td><td>SC</td><td>SITE CLEARANCE</td></tr><tr><td>16</td><td>PF</td><td>PILE FOUNDATION</td></tr><tr><td>17</td><td>IW</td><td>IRON WORK</td></tr><tr><td>18</td><td>BI</td><td>OTHER BUILDING ITEMS</td></tr></tbody></table>

#### SOR Variants <a href="#i14ephumi4ok" id="i14ephumi4ok"></a>

<table><thead><tr><th width="111.66666666666666">S.No.</th><th width="150">Code</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>FN</td><td>Excavation in Foundation</td></tr><tr><td>2</td><td>GF</td><td>Ground Floor</td></tr><tr><td>3</td><td>SF</td><td>Second Floor</td></tr><tr><td>4</td><td>TF</td><td>Third Floor</td></tr><tr><td>5</td><td>PL</td><td>Foundation and Plinth</td></tr><tr><td>6</td><td>SG</td><td>Super Structure (GF)</td></tr><tr><td>7</td><td>SS</td><td>Super Structure (SF)</td></tr><tr><td>8</td><td>ST</td><td>Super Structure (TF)</td></tr><tr><td>9</td><td>BS</td><td>Basic</td></tr></tbody></table>

#### Schedule of Rates <a href="#elpqbkedvew3" id="elpqbkedvew3"></a>

The scheduled items for which the works department publishes the rates are known as the schedule of rates. There are mainly 4 types of items for which schedules of rates are published.

1. Material - These are the material items which are required to accomplish a work.
2. Labour - The skilled and unskilled labourers who are required to accomplish the work.
3. Machinery - These are the equipment which are required to accomplish the work.
4. Works - The composition of material, labour, and machinery together form a building block for a work.

**Create**

In MUKTA only a limited set of SORs are being used to estimate a work and initially, only the relevant SOR items are created. The option to create an SOR is provided to help the user take any missing or new SOR into the system as and when needed.

**Attributes**

<table><thead><tr><th width="91">S.No.</th><th width="184">Attribute Name</th><th width="145">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Type</td><td>Yes</td><td>Material, Labour, Machinery, Works.</td></tr><tr><td>2</td><td>SOR Sub Type</td><td>Yes</td><td>Applicable for SOR type Works only.</td></tr><tr><td>3</td><td>SOR Variant</td><td>No</td><td>Applicable for SOR type Works only.</td></tr><tr><td>4</td><td>Unit of Measurement</td><td>Yes</td><td>Unit of measurement for the item.</td></tr><tr><td>5</td><td>Rate Defined for Quantity</td><td>Yes</td><td>Quantity of items for which basic rate is defined.</td></tr><tr><td>6</td><td>Description</td><td>Yes</td><td>Name of item as per the standard definition of OPWD</td></tr><tr><td></td><td><strong>Rate Details</strong></td><td><strong>Optional</strong></td><td></td></tr><tr><td>7</td><td>Effective From</td><td>Yes</td><td>The date given rate will become effective, it can be a past and future date.</td></tr><tr><td></td><td><strong>Heads</strong></td><td><strong>Grid</strong></td><td>To select a head whichever is applicable.</td></tr><tr><td>8</td><td>Basic Rate</td><td>Yes</td><td>The basic rate of the item defined by the OPWD</td></tr><tr><td>9</td><td>Conveyance</td><td>No</td><td>The conveyance charges applicable based on the distance item is carried.</td></tr><tr><td>10</td><td>Royalty</td><td>No</td><td>The royalty amount on the items as per the state mining department.</td></tr><tr><td>11</td><td>Labour Cess</td><td>No</td><td>It is applicable for works items only and calculated on Basic + Conveyance + Royalty.</td></tr><tr><td>12</td><td>Rate</td><td>Display</td><td>A calculated value.</td></tr></tbody></table>

**Mockups**

![](<../../../../.gitbook/assets/0 (16).png>)

**Search SOR**

Search SOR provides the option to the user to search an SOR to see the details and modify it to bring the new rates into effect from a well-defined effective date.

**Search Criteria**

<table><thead><tr><th width="87">S.No.</th><th width="146">Field Name</th><th width="122">Data Type</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>Text</td><td>It is system system-generated unique code to identify the SOR uniquely</td></tr><tr><td>2</td><td>SOR Type</td><td>Drop-down</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Drop-down</td><td>SOR subtypes, the values from SOR Sub Type Master</td></tr><tr><td>4</td><td>SOR Variant</td><td>Drop-down</td><td>SOR variants, the values from Variant master</td></tr><tr><td>5</td><td>Status</td><td>Drop-down</td><td>Active/ Inactive</td></tr><tr><td>6</td><td>Effective From</td><td>Date</td><td>The rate effective from date</td></tr><tr><td>7</td><td>Effective To</td><td>Date</td><td>The rate effective from date</td></tr></tbody></table>

**Search Result**

The search result always displays the currently effective rates unless the search is for a different effective period.

<table><thead><tr><th width="103.66666666666666">S.No.</th><th width="144">Field Name</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Description</td><td>It is the description of SOR to describe the SOR.</td></tr><tr><td>3</td><td>SOR Type</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>4</td><td>SOR Sub Type</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>5</td><td>Status</td><td>The status of SOR, Active/ Inactive.</td></tr><tr><td>6</td><td>Rate</td><td>The current effective rate of the SOR.</td></tr></tbody></table>

**Mockups**

![](<../../../../.gitbook/assets/1 (14).png>)

**View SOR**

It enables users to display the details of a SOR and then take the required action from there. E.g. Create Rate Analysis, View Rate Analysis, Modify SOR.

**Attributes**

<table><thead><tr><th width="95.66666666666666">S.No.</th><th width="186">Field Name</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Type</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>4</td><td>SOR Variant</td><td>SOR variant, the values from the SOR Variant Master.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>The unit of measurement.</td></tr><tr><td>6</td><td>Rate Defined for Quantity</td><td>The quantity of SOR for which rate is provided.</td></tr><tr><td>7</td><td>SOR Description</td><td>It is the description of SOR to describe the SOR.</td></tr><tr><td>8</td><td>Status</td><td>The status of SOR Active/ Inactive. Active means active for usage.</td></tr><tr><td></td><td><strong>Rate</strong></td><td>The rate section of SOR.</td></tr><tr><td>9</td><td>Effective From</td><td>The date from which the rate is effective.</td></tr><tr><td></td><td><strong>Heads</strong></td><td></td></tr><tr><td>10</td><td>Basic Rate</td><td>Basic rate of the SOR, provided by the state PWD.</td></tr><tr><td>11</td><td>Conveyance</td><td>Conveyance cost defined for the unit of quantity given in SOR.</td></tr><tr><td>12</td><td>Royalty</td><td>Royalty defined for the unit of quantity given in SOR.</td></tr><tr><td>13</td><td>Labour Cess</td><td>The amount of labour cess</td></tr><tr><td>14</td><td>Total Rate</td><td>The final rate of SOR.</td></tr><tr><td></td><td><strong>Rate History</strong></td><td>History of rates which were effective in the past.</td></tr><tr><td>15</td><td>Serial No.</td><td>Serial number of the record.</td></tr><tr><td>16</td><td>Effective From</td><td>The rate effective from date.</td></tr><tr><td>17</td><td>Rate</td><td>The net effective rate.</td></tr><tr><td>18</td><td>View Details</td><td>Button to view the break-up of rate.</td></tr><tr><td>19</td><td><strong>Actions</strong></td><td><ol><li>Modify SOR/ Add Rate - Applicable to all types of SOR.</li><li>Create Rate Analysis - Applicable to Works type of SOR.</li><li>View Rate Analysis - Applicable to Works type of SOR.</li></ol></td></tr></tbody></table>

**Mockups**

![](<../../../../.gitbook/assets/2 (14).png>)

![](<../../../../.gitbook/assets/3 (14).png>)

**Modify SOR**

Modifying SOR enables the user to make the necessary corrections in the details and add the new rate effective from a future date.

**Attributes**

<table><thead><tr><th width="112">S.No.</th><th width="149">Field Name</th><th width="121">Data Type</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>Display</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Type</td><td>Display</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Display</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Display</td><td>SOR variant, the values from the SOR Variant Master.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>Display</td><td>The unit of measurement.</td></tr><tr><td>6</td><td>Defined for Quantity</td><td>Display</td><td>The quantity of SOR for which rate is provided.</td></tr><tr><td>7</td><td>SOR Description</td><td>Text</td><td>It is the description of SOR to describe the SOR.</td></tr><tr><td>8</td><td>Status</td><td>Drop-down</td><td>The status of SOR Active/ Inactive.</td></tr></tbody></table>

**Mockups**

The screen to modify SOR is almost the same as creating SOR with the limitation mentioned in the above table.

![](<../../../../.gitbook/assets/4 (14).png>)

**Add/ Modify Rate**

Add Rate enables the user to add the new rate effective from a future date.

**Attributes**

<table><thead><tr><th width="90">S.No.</th><th width="148">Field Name</th><th width="110">Data Type</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>SOR Code</td><td>Display</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Type</td><td>Display</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>3</td><td>SOR Sub Type</td><td>Display</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>4</td><td>SOR Variant</td><td>Display</td><td>SOR variant, the values from the SOR Variant Master.</td></tr><tr><td>5</td><td>Unit of Measurement</td><td>Display</td><td>The unit of measurement.</td></tr><tr><td>6</td><td>Defined for Quantity</td><td>Display</td><td>The quantity of SOR for which rate is provided.</td></tr><tr><td>7</td><td>SOR Description</td><td>Display</td><td>It is the description of SOR to describe the SOR.</td></tr><tr><td>8</td><td>Status</td><td>Display</td><td>The status of SOR Active/ Inactive.</td></tr><tr><td></td><td><strong>Rate Details</strong></td><td></td><td></td></tr><tr><td>9</td><td>Effective From</td><td>Date</td><td>The date from which the rate is effective. A future date.</td></tr><tr><td></td><td><strong>Heads</strong></td><td><strong>Grid</strong></td><td>It will enable the user to select and add a head applicable.</td></tr><tr><td>10</td><td>Basic Rate</td><td>Text</td><td>Basic rate of the SOR, provided by the state PWD.</td></tr><tr><td>11</td><td>Conveyance</td><td>Text</td><td>Conveyance cost defined for the unit of quantity given in SOR.</td></tr><tr><td>12</td><td>Royalty</td><td>Text</td><td>Royalty defined for the unit of quantity given in SOR.</td></tr><tr><td>13</td><td>Labour Cess</td><td>Display</td><td>The amount of labour cess, non editable.</td></tr><tr><td>14</td><td>Total Rate</td><td>Display</td><td>The final rate of SOR.</td></tr></tbody></table>

**Mockups**

The screen to modify SOR is almost the same as creating SOR with the limitation mentioned in the above table.

<figure><img src="../../../../.gitbook/assets/5 (16).png" alt=""><figcaption></figcaption></figure>

### Detailed Estimate <a href="#qa5qunvernhu" id="qa5qunvernhu"></a>

After getting administrative approval on pre-estimation, a detailed estimate is prepared. In this, the estimate is divided into SOR, Non-SOR and Other Expenses and the quantities of various items are calculated individually by recording the detailed measurement of each activity. A detailed estimate is more accurate in terms of predicting the cost, material, labour, and machinery required to complete the work. It is also used for tendering and contracting the work.

**Create Estimate**

Create estimate enables users to create a detailed estimate using the measurements taken from ground. The options of searching a template, and adding SOR using SOR search is provided on the create estimate page.

**Search SOR**

1. SOR Type - Drop-down to select a value for SOR type.
2. SOR Sub Type - Drop-down to select a value for the SOR sub type.
3. SOR Variant - Drop-down to select a value.
4. SOR - Drop-down with incremental/ fuzzy search on code or description.

**Plus Measurements**

These are the measurements which are added to the measurement to calculate the quantity of a particular SOR.

**Minus Measurements**

These are the measurements which are subtracted from the measurement to calculate the quantity of a particular SOR.

**Attributes**

<table><thead><tr><th width="89">S.No.</th><th width="156">Field Name</th><th width="113">Data Type</th><th width="151">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Estimate Type</td><td>Display</td><td>Yes</td><td>Estimate type, Original/ Revised.</td></tr><tr><td>2</td><td>Project ID</td><td>Display</td><td>Yes</td><td>Project ID</td></tr><tr><td>3</td><td>Project Sanction Date</td><td>Display</td><td>Yes</td><td>Project sanction date</td></tr><tr><td>4</td><td>Project Name</td><td>Display</td><td>Yes</td><td>Project name</td></tr><tr><td>5</td><td>Project Description</td><td>Display</td><td>Yes</td><td>Project description</td></tr><tr><td></td><td><strong>Search SOR</strong> - It provides the option to search a SOR and add to the estimate.</td><td></td><td></td><td></td></tr><tr><td></td><td><strong>SORs</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Code</td><td>Display</td><td>Yes</td><td>SOR code, unique identifier for each SOR.</td></tr><tr><td>2</td><td>SOR Description</td><td>Display</td><td>Yes</td><td>SOR description from the SOR master for the selected SOR.</td></tr><tr><td>3</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>4</td><td>Rate</td><td>Display</td><td>Yes</td><td>The rate defined and effective currently.</td></tr><tr><td>5</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Calculated value out of measurements.</td></tr><tr><td>6</td><td>Amount</td><td>Display</td><td>Yes</td><td>Calculated value and equal to Qty*Amount.</td></tr><tr><td></td><td><strong>Measurements</strong></td><td></td><td></td><td></td></tr><tr><td>1.1</td><td>Sr. No.</td><td>Display</td><td>Auto</td><td>Measurement serial number.</td></tr><tr><td>1.2</td><td>Type</td><td>Drop-down</td><td>Yes</td><td>Plus/ Minus measurements.</td></tr><tr><td>1.3</td><td>Name</td><td>Text</td><td>Yes</td><td>The name of the measurement.</td></tr><tr><td>1.4</td><td>Number (Nos)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>No. of items.</td></tr><tr><td>1.5</td><td>Length (L)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>Length measured</td></tr><tr><td>1.6</td><td>Breadth (B)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>Width measured</td></tr><tr><td>1.7</td><td>Height/ Depth</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>Depth measured</td></tr><tr><td>1.8</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Qty = N* L*B*D;</td></tr><tr><td>1.9</td><td>Total</td><td>Display</td><td>Yes</td><td>Grid total for the quantities of measurements</td></tr><tr><td></td><td><strong>Analysis</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Material Cost</td><td>Display</td><td>Yes</td><td>Cost of material out of SORs.</td></tr><tr><td>2</td><td>Labour Cost</td><td>Display</td><td>Yes</td><td>Cost of labours out of SORs.</td></tr><tr><td>3</td><td>Machinery Cost</td><td>Display</td><td>Yes</td><td>Cost of machinery out of SORs.</td></tr><tr><td></td><td><strong>Action</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Save</td><td>Button</td><td>Yes</td><td>Save the estimate as a draft.</td></tr><tr><td>2</td><td>Generate Analysis</td><td>Button</td><td>Yes</td><td>Generates the analysis and populates the figures.</td></tr><tr><td>3</td><td>Submit</td><td>Button</td><td>Yes</td><td>Submit the estimate for verification.</td></tr></tbody></table>

**Mockups**

![](<../../../../.gitbook/assets/6 (17).png>)

#### Workflow <a href="#id-407t7v8mmwfq" id="id-407t7v8mmwfq"></a>

One additional step of adding and saving the estimate as a draft with the creator of the estimate is incorporated.

<table><thead><tr><th width="66">#</th><th>Action</th><th>Role</th><th>From State</th><th>To State</th><th>Status</th></tr></thead><tbody><tr><td>1</td><td>Save as Draft</td><td>Estimate Creator</td><td></td><td>Saved as draft</td><td>Drafted</td></tr><tr><td>2</td><td>Submit</td><td>Estimate Creator</td><td>Saved as draft</td><td>Pending for verification</td><td>Submitted</td></tr><tr><td>3</td><td>Verify and Forward</td><td>Estimate Verifier</td><td>Pending for verification</td><td>Pending for technical sanction</td><td>Verified</td></tr><tr><td>4</td><td>Technical Sanction</td><td>Technical Sanctioner</td><td>Pending for technical sanction</td><td>Pending for approval</td><td>Technically Sanctioned</td></tr><tr><td>5</td><td>Send Back</td><td>Estimate Verifier</td><td>Pending for verification</td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>6</td><td>Send Back</td><td>Technical Sanctioner</td><td>Pending for technical sanction</td><td>Pending for verification</td><td>Sent Back</td></tr><tr><td>7</td><td>Send Back</td><td>Estimate Approver</td><td>Pending for approval</td><td>Pending for technical sanction</td><td>Sent Back</td></tr><tr><td>8</td><td>Send Back To Originator</td><td>&#x3C;roles having access></td><td>&#x3C;Current Status></td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>9</td><td>Edit/ Re-submit</td><td>Estimate Creator</td><td>Pending for correction</td><td>Pending for verification</td><td>Re-submitted</td></tr><tr><td>10</td><td>Approve</td><td>Estimate Approver</td><td>Pending for approval</td><td>Approved</td><td>Approved</td></tr><tr><td>11</td><td>Reject</td><td>&#x3C;any roles having access></td><td>&#x3C;Current Status></td><td>Rejected</td><td>Rejected</td></tr></tbody></table>

### Measurement Book <a href="#id-6eol12o3ofhf" id="id-6eol12o3ofhf"></a>

The measurement book is the most important record. It is the basis of all accounts of quantities of work done, and purchase made and it must contain such a complete record of facts as to be conclusive evidence in the court of law.

It is the basis of all accounts of quantities whether of works done by Contractors or by labourers employed departmentally, or materials received. It is so written the transactions are readily traceable.

#### MB Recording <a href="#qd7oamvegixn" id="qd7oamvegixn"></a>

All the measurements for the work completed are measured and recorded in the measurement book against each and every BOQ provided in the estimate. Once the complete quantity of the item mentioned in the estimate is consumed in MB the item is considered completed.

#### MB Process Flow <a href="#mx5uomtdjhyt" id="mx5uomtdjhyt"></a>

<div align="left"><img src="../../../../.gitbook/assets/7 (14).png" alt=""></div>

#### Create MB <a href="#id-6yxdxdxkm2ke" id="id-6yxdxdxkm2ke"></a>

It enables users to capture the measurement of the completed works item (SOR/ Non-SOR) and create a record which becomes the basis of payment for the wage seekers, suppliers and supervisors (CBOs).

**Attributes**

<table><thead><tr><th width="75">S.No.</th><th>Field Name</th><th width="109">Data Type</th><th width="144">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Work order number</td><td>Display</td><td>NA</td><td>Work order number</td></tr><tr><td>2</td><td>Project ID</td><td>Display</td><td>NA</td><td>Project ID</td></tr><tr><td>3</td><td>Project sanction date</td><td>Display</td><td>NA</td><td>Project sanction date</td></tr><tr><td>4</td><td>Project Location</td><td>Display</td><td>NA</td><td>Project location</td></tr><tr><td>5</td><td>Project Name</td><td>Display</td><td>NA</td><td>Project name</td></tr><tr><td>6</td><td>Project Description</td><td>Display</td><td>NA</td><td>Project description</td></tr><tr><td>7</td><td>View MB History</td><td>Link</td><td>NA</td><td>To show the measurement history in the format given below.</td></tr><tr><td></td><td><strong>Measurement History</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Sr. No</td><td>Display</td><td>NA</td><td>Serial number</td></tr><tr><td>2</td><td>MB Reference Number</td><td>Display</td><td>NA</td><td>Measurement reference number</td></tr><tr><td>3</td><td>MB Date</td><td>Display</td><td>NA</td><td>Measurement date</td></tr><tr><td>4</td><td>MB Period</td><td>Display</td><td>NA</td><td>Measurement period</td></tr><tr><td>5</td><td>MB Amount</td><td>Display</td><td>NA</td><td>Measurement amount</td></tr><tr><td>6</td><td>Status</td><td>Display</td><td>NA</td><td>Status of the measurement.</td></tr><tr><td></td><td><strong>Measurement Period - It has to be the same as muster roll period.</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>From Date</td><td>Date</td><td>Yes</td><td>Muster roll start date.</td></tr><tr><td>2</td><td>To Date</td><td>Date</td><td>Yes</td><td>Muster roll end date.</td></tr><tr><td></td><td><strong>SORs</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Category</td><td>Display</td><td>Yes</td><td>SOR Sub type</td></tr><tr><td>2</td><td>Code</td><td>Display</td><td>Yes</td><td>SOR Code</td></tr><tr><td>3</td><td>SOR Description</td><td>Display</td><td>Yes</td><td>SOR description from the SOR master for the selected SOR.</td></tr><tr><td>4</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>5</td><td>Rate</td><td>Display</td><td>Yes</td><td>Rate per unit</td></tr><tr><td>6</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Quantity calculated from measurement captured.</td></tr><tr><td>7</td><td>Amount</td><td>Display</td><td>Yes</td><td>Calculated from Rate*Quantity.</td></tr><tr><td></td><td><strong>Measurements</strong></td><td></td><td></td><td></td></tr><tr><td>1.1</td><td>Sr. No.</td><td>Display</td><td>Auto</td><td>Serial number of measurement</td></tr><tr><td>1.2</td><td>Type</td><td>Display</td><td>Auto</td><td>Plus/ Minus from estimate.</td></tr><tr><td>1.3</td><td>Name</td><td>Display</td><td>Auto</td><td>The name of the measurement from the estimate.</td></tr><tr><td>1.4</td><td>Number (Nos)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>No. of items if the unit of measurement is number.</td></tr><tr><td>1.5</td><td>Length (L)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>Length measured for completed work.</td></tr><tr><td>1.6</td><td>Breadth (B)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>Width measured for completed work.</td></tr><tr><td>1.7</td><td>Height/ Depth (D)</td><td><p>Numeric</p><p>(6,2)</p></td><td>Yes</td><td>Depth measured for completed work, allowed up-to 2 decimal places.</td></tr><tr><td>1.8</td><td>Quantity</td><td>Display</td><td>Yes</td><td>Qty = N*L*B*D; rounded up-to 2 decimal places.</td></tr><tr><td>1.9</td><td>Total</td><td>Display</td><td>Yes</td><td>Grid total for the quantities of measurements, rounded up-to 2 decimal places.</td></tr><tr><td></td><td><strong>Non SORs</strong> - The above is repeated for Non SORs also.</td><td></td><td></td><td></td></tr><tr><td></td><td><strong>View Utilization Statements</strong> - A link to view the utilization statements in HTML view.</td><td></td><td></td><td></td></tr><tr><td></td><td><strong>Worksite Photos</strong></td><td>Tab</td><td></td><td></td></tr><tr><td>7</td><td>Worksite Photo</td><td>Upload File</td><td>Yes</td><td>5 photos JPG and PNG images are supported.</td></tr><tr><td></td><td><strong>Actions</strong></td><td></td><td></td><td></td></tr><tr><td>1</td><td>Save as Draft</td><td>Button</td><td>Yes</td><td>Action to save the measurement record as draft.</td></tr><tr><td>2</td><td>Generate Utilization</td><td>Button</td><td>Yes</td><td>Action to generate measurement statements out of measurements taken.</td></tr><tr><td>3</td><td>Submit</td><td>Button</td><td>Yes</td><td>Action to submit the measurement book for verification</td></tr></tbody></table>

**Mockups**

![](<../../../../.gitbook/assets/8 (15).png>)

![](<../../../../.gitbook/assets/9 (14).png>)

#### Workflow <a href="#imb1lz1w9yyl" id="imb1lz1w9yyl"></a>

The table below illustrates the steps of workflow followed to approve the revised work order.

<table><thead><tr><th width="66">#</th><th>Action</th><th>Role</th><th>From State</th><th>To State</th><th>Status</th></tr></thead><tbody><tr><td>1</td><td>Save</td><td>MB Creator</td><td></td><td>Drafted</td><td>Drafted</td></tr><tr><td>2</td><td>Submit</td><td>MB Creator</td><td>Drafted</td><td>Pending for verification</td><td>Submitted</td></tr><tr><td>3</td><td>Verify and Forward</td><td>MB Verifier</td><td>Pending for verification</td><td>Pending for approval</td><td>Verified</td></tr><tr><td>4</td><td>Send Back</td><td>MB Verifier</td><td>Pending for verification</td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>5</td><td>Send Back</td><td>MB Approver</td><td>Pending for approval</td><td>Pending for verification</td><td>Sent Back</td></tr><tr><td>6</td><td>Send Back To Originator</td><td><strong>&#x3C;any roles having access of action></strong></td><td><strong>&#x3C;Current Status></strong></td><td>Pending for correction</td><td>Sent Back</td></tr><tr><td>7</td><td>Edit/ Re-submit</td><td>MB Creator</td><td>Pending for correction</td><td>Pending for verification</td><td>Re-submitted</td></tr><tr><td>8</td><td>Approve</td><td>MB Approver</td><td>Pending for approval</td><td>Approved</td><td>Approved</td></tr><tr><td>9</td><td>Reject</td><td><strong>&#x3C;any roles having access></strong></td><td><strong>&#x3C;Current Status></strong></td><td>Rejected</td><td>Rejected</td></tr></tbody></table>

#### Search MB <a href="#id-3fznk2bifw4o" id="id-3fznk2bifw4o"></a>

It enables users to search and MB recordings which are captured for a period and then sent for verification/ approval.

**Search Criteria**

<table><thead><tr><th width="77">S.No.</th><th>Field Name</th><th>Data Type</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Ward</td><td>Drop-down</td><td>Ward name from the configured data.</td></tr><tr><td>2</td><td>Project Name</td><td>Text</td><td>Project name</td></tr><tr><td>3</td><td>MB Number</td><td>Text</td><td>MB number</td></tr><tr><td>4</td><td>MB Reference Number</td><td>Text</td><td>MB reference number</td></tr><tr><td>5</td><td>Status</td><td>Drop-down</td><td>Active/ Inactive.</td></tr><tr><td>6</td><td>Created From</td><td>Date</td><td>MB created date</td></tr><tr><td>7</td><td>Created To</td><td>Date</td><td>MB created date</td></tr></tbody></table>

**Search Result**

<table><thead><tr><th width="89.66666666666666">S.No.</th><th width="220">Field Name</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>MB Reference Number</td><td>MB reference number.</td></tr><tr><td>2</td><td>MB Number</td><td>MB number</td></tr><tr><td>3</td><td>Project Name</td><td>Project name</td></tr><tr><td>4</td><td>CBO Name</td><td>Name of CBO to whom work order is awarded.</td></tr><tr><td>5</td><td>Status</td><td>Status of MB.</td></tr><tr><td>6</td><td>MB Amount</td><td>MB amount.</td></tr></tbody></table>

**Mockups**

![](<../../../../.gitbook/assets/10 (9).png>)

#### View MB <a href="#rengypa2hgz5" id="rengypa2hgz5"></a>

It enables the users to view the details and workflow status of the measurement book.

**Attributes**

View measurement book displays all the details related to it as given below.

1. MB Reference Number
2. MB Number
3. Work Order Number
4. Project ID
5. Project Sanction Date
6. Project Location
7. Project Name
8. Project Description
9. Measurement History
   1. Sr. No
   2. MB Reference Number
   3. MB Date
   4. MB Period
   5. MB Amount
   6. Status
10. Measurement Period
    1. From Date
    2. To Date
11. SORs/ Non SORs
    1. SOR Category
    2. SOR Code
    3. SOR Description
    4. Unit
    5. Rate
    6. Quantity
    7. Amount
    8. Measurements
       1. Sr. No.
       2. Type
       3. Name
       4. Number (No)
       5. Length (L)
       6. Breadth (B)
       7. Height/ Depth (D)
       8. Quantity
       9. Total
12. Analysis
    1. Material Consumed (₹)
    2. Labour Utilized (₹)
    3. Machinery Utilized (₹)
13. Workflow Timelines

**Mockups**

The screen is similar to creating MB in the display mode of information including timelines.
