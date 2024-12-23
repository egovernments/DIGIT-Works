# Search SOR

## Scope

Search Schedule of Rates

Home > Schedule of Rates > Search SOR

## Actors <a href="#actors" id="actors"></a>

State: STATE\_MUKTA\_ADMIN

ULBs: MUKTA\_ENG\_ADMIN

## Details <a href="#details" id="details"></a>

1. Search SOR to be provided to list all the SORs.
2. There are various search parameters to search such a SOR.

<table data-header-hidden><thead><tr><th width="86"></th><th width="150"></th><th width="127"></th><th></th></tr></thead><tbody><tr><td><strong>#</strong></td><td><strong>Field Name</strong></td><td><strong>Data Type</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>SOR Type</td><td>Drop-down</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>2</td><td>SOR Sub Type</td><td>Drop-down</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>3</td><td>SOR Variant</td><td>Drop-down</td><td>SOR variant, the values from the SOR Variant Master.</td></tr><tr><td>4</td><td>SOR Code</td><td>Text</td><td>The system generated unique code of the SOR.</td></tr><tr><td>5</td><td>Status</td><td>Drop-down</td><td>Active/ Inactive.</td></tr><tr><td>6</td><td>Effective From</td><td>Date</td><td>The rate effective from date, can be a future date too.</td></tr><tr><td>7</td><td>Effective To</td><td>Date</td><td>The rate effective from date, can be a future date too.</td></tr></tbody></table>

### Search Logic

1. At least one parameter is required to perform the search.
2. Consider From Date and To Date as a Date Range single parameter.
3. Exact search is performed for the values entered/selected.
4. In case multiple parameters values are supplied AND is applied for searching record.
5. By default active SORs are searched and currently effective rates are displayed.

### Search Result

<table data-header-hidden><thead><tr><th width="71.66666666666666"></th><th width="165"></th><th></th></tr></thead><tbody><tr><td>#</td><td><strong>Field Name</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>SOR Code</td><td>It is system generated unique code to identify the SOR uniquely.</td></tr><tr><td>2</td><td>SOR Description</td><td>It is the description of SOR upto 64 characters only and end with a (…) with an option to display the complete text in tool-tip on mouse-click.</td></tr><tr><td>3</td><td>SOR Type</td><td>SOR types, the values from SOR Type Master.</td></tr><tr><td>4</td><td>SOR Sub Type</td><td>SOR sub types, the values from SOR Sub Type Master.</td></tr><tr><td>5</td><td>Status</td><td>The status of SOR, Active/ Inactive.</td></tr><tr><td>6</td><td>Rate</td><td>The current effective rate of the SOR.</td></tr></tbody></table>

### Actions <a href="#actions" id="actions"></a>

1. Search - To perform the search based on the parameters entered.
2. Clear - To clear the values entered for search.
3. SOR Code - Hyperlink to view the SOR detail on mouse click. View SOR display the searched rate with SOR details on View Page.

#### On Search

1. Search result is displayed.
2. Option to download search result in excel is provided.
3. Pagination is provided to displayed 10 record at a time.

### Validations <a href="#validations" id="validations"></a>

Not applicable.

### Configuration <a href="#configuration" id="configuration"></a>

Not applicable.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

### User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-24833\&mode=design\&t=k7ONf9XhnYdXofyL-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-24833\&mode=design\&t=k7ONf9XhnYdXofyL-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Search is provided with the search parameters mentioned and the result is displayed as mentioned.
2. On click of SOR code, searched rate is displayed with SOR details on View SOR Page.
3. Pagination and option to download the searched result into Excel is provided.
