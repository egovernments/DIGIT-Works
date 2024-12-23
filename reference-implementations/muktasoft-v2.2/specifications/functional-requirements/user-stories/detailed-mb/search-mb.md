# Search MB

## Scope

Search Measurement Book

### Actors <a href="#actors" id="actors"></a>

Employee

Role: MB Creator, MB Verifier, MB Approver

### Details <a href="#details" id="details"></a>

1. Search Measurement Book to be provided to search a MB and view the details.
2. Search is provided based on various parameters.

#### Search Criteria <a href="#searchcriteria" id="searchcriteria"></a>

<table data-header-hidden><thead><tr><th width="99"></th><th width="152"></th><th width="133"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Field Name</td><td>Data Type</td><td>Description</td></tr><tr><td>1</td><td>Ward</td><td>Drop-down</td><td>Name of the ward from the configured boundary data.</td></tr><tr><td>2</td><td>Project Name</td><td>Text</td><td>Project name</td></tr><tr><td>3</td><td>MB Number</td><td>Text</td><td>MB number</td></tr><tr><td>4</td><td>MB Reference Number</td><td>Text</td><td>MB reference number</td></tr><tr><td>5</td><td>Status</td><td>Drop-down</td><td>Workflow statuses, Drafted, Submitted, Verified, Approved.</td></tr><tr><td>6</td><td>Created From</td><td>Date</td><td>MB created date</td></tr><tr><td>7</td><td>Created To</td><td>Date</td><td>MB created date</td></tr></tbody></table>

1. At least one parameter’s value is required to perform the search.
2. Date range From Date/ To Date is considered one parameter.
3. Exact search is performed for the values entered/selected other than Project Name.
4. For Project Name, fuzzy search to be provided.
5. In case multiple parameters values are supplied AND is applied for searching record.

#### Search Result <a href="#searchresult" id="searchresult"></a>

1. Search result is shown as given below.
2. Pagination is displayed to handle the big result set. 10 record per page are displayed.
3. Option to download the result set in Excel/ PDF is provided.

<table data-header-hidden><thead><tr><th width="101.66666666666666"></th><th width="176"></th><th></th></tr></thead><tbody><tr><td>Sr. No.</td><td>Field Name</td><td>Description</td></tr><tr><td>1</td><td>MB Reference Number</td><td>MB reference number, a hyperlink to open the MB.</td></tr><tr><td>2</td><td>MB Number</td><td>MB number</td></tr><tr><td>3</td><td>Project Name</td><td>Project name, with the option to see the project description as tool-tip.</td></tr><tr><td>4</td><td>CBO Name</td><td>Name of CBO to whom work order is awarded.</td></tr><tr><td>5</td><td>Status</td><td>Status of MB.</td></tr><tr><td>6</td><td>MB Amount</td><td>MB amount.</td></tr></tbody></table>

### Validations <a href="#validations" id="validations"></a>

1. At least one parameter’s value is required to perform the search.

### Actions <a href="#actions" id="actions"></a>

1. Search - It will perform the search and display the result. In case, no result found appropriate message is displayed.
2. Clear Search - It will clear the search parameters.
3. MB Reference Nbmer - Hyperlink will take the user to MB detail page.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

## User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-31446\&mode=design\&t=iYj7FtnLquFhW5r2-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=9137-31446\&mode=design\&t=iYj7FtnLquFhW5r2-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. At least one parameter is required to perform the search.
2. Search result displayed on matching records found else no record found message is displayed.
3. Pagination is applied if more than 10 records are found.
