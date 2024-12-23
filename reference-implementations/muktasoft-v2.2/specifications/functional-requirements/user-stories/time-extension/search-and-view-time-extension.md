# Search and View Time Extension

## Scope

Search project by ULB/ employee users.

## Actors

Employee Role: Project Admin, Project\_Viewer

## Details

1. **Search Project** action has to be configurable and allow mapping with a role on demand.
2. **Search Project** is provided to allow the users to search Work and view its details/ create estimates.

#### Search Criteria

<table data-header-hidden><thead><tr><th width="57"></th><th width="136"></th><th width="129"></th><th></th></tr></thead><tbody><tr><td><strong>#</strong></td><td><strong>Field Name</strong></td><td><strong>Data Type</strong></td><td><strong>Description</strong></td></tr><tr><td>1</td><td>Ward</td><td>Drop-down</td><td>List of ward boundaries for logged-in user ULB with search by entering name.</td></tr><tr><td>2</td><td>Project Type</td><td>Drop-down</td><td>Values of work type from MDMS configuration.</td></tr><tr><td>3</td><td>Project Name</td><td>Textbox</td><td>Project Name</td></tr><tr><td>4</td><td>Project ID</td><td>Textbox</td><td>Work identification no. generated for a work in works proposal</td></tr><tr><td>6</td><td>From Date</td><td>Date Picker</td><td>Proposal creation date, entered by user while creating works proposal.</td></tr><tr><td>7</td><td>To Date</td><td>Date Picker</td><td>Proposal creation date, entered by user while creating works proposal.</td></tr></tbody></table>

{% hint style="info" %}
* At least one parameter is required to perform the search.
* Consider From Date and To Date as a Date Range single parameter.
* An exact search is performed for the values entered/selected other than **Project Name**.
* For Project Name, fuzzy search is applicable.
* In case multiple parameter values are supplied AND are applied for searching record.
{% endhint %}

### Search Result

1. The search result is shown as given below.
2. Pagination is displayed to handle the big result set. 10 records are displayed per page.
3. The option to download the result set in Excel/ PDF is provided.

<table data-header-hidden><thead><tr><th width="84"></th><th width="156"></th><th width="132"></th><th></th></tr></thead><tbody><tr><td><strong>#</strong></td><td><strong>Field</strong></td><td><strong>Data Type</strong></td><td><strong>Comments</strong></td></tr><tr><td>1</td><td>Project ID</td><td>Display Only</td><td>A hyperlink to open the project details in view mode.</td></tr><tr><td>2</td><td>Project Name</td><td>Display Only</td><td>Name of project having <strong>project description</strong> displayed as tool-tip on mouseover.</td></tr><tr><td>4</td><td>Location</td><td>Display Only</td><td>Locality name along with ward name.</td></tr><tr><td>5</td><td>Estimated Cost</td><td>Display Only</td><td>The project cost from the project details</td></tr></tbody></table>

## Validations

All the actions are displayed based on role action mapping and the user role assignment.

## Actions

1. **Search** - It will perform the search based on the values supplied for search parameters and the logic defined.
2. **Clear Search** - It will clear the values filled for searched parameters.
3. **Project ID** - It will take the user to the **View Project Details Page**.

## Notifications

Not applicable.

## User Interface

{% embed url="https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=1756%3A31164&t=lliv14frYnlv4Nww-4" %}

## Acceptance Criteria

<table><thead><tr><th width="206">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Search Parameters/ Search Logic should be as stated in the story above.</td></tr><tr><td>2</td><td>Search result is shown as described in the story.</td></tr><tr><td>3</td><td>Pagination is provided to handle more results and 10 records per page is displayed.</td></tr></tbody></table>
