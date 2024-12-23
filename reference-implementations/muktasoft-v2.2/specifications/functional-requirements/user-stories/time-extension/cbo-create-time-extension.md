# CBO: Create Time Extension

## Context

Request for time extension on projects.

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

CBO: My Works → Request Time Extension

### Actors <a href="#actors" id="actors"></a>

CBO

Role: CBO ADMIN

### Details <a href="#details" id="details"></a>

1. My Works lists all the work orders which are assigned to logged-in CBO and are segregated by In Progress and Completed works.
2. Work orders for which at least one muster roll is created and approved are allowed to create the time extension request.
3. Also for that project closure request should not be created.
4. In the action menu, the Request Time Extension option is displayed only if the above 2 conditions are met.

#### My Works <a href="#myworks" id="myworks"></a>

Each work order card will have the below attributes displayed:

1. Work order number
2. Project description
3. Role of CBO
4. Officer-in-charge
5. Issue Date
6. Due Date
7. Work order amount
8. Status
9. View Details - Link to view the complete details of the work order.
10. Request Time Extension - Action button. \[This action is shown only when at least one muster roll is submitted and approved]

On creating a time extension request,

1. A window to capture the requested extension for the completion period in days is displayed.
2. CBO enters the below details and submits the request.
   1. Extension Period (in days) - Mandatory
   2. Reason for Extension - Mandatory

### Validations <a href="#validations" id="validations"></a>

On Request Time Extension - In case the first muster roll is pending to submit for approval.

Not even a single muster roll has been approved for the project. Please ensure that the first muster roll is submitted for approval.

### Configurations <a href="#configurations" id="configurations"></a>

Not applicable.

### Actions <a href="#actions" id="actions"></a>

On submit,

1. A time extension request is created and sent for verification/ approval.
2. The request ID is generated as per the specified format. ID: TE/2022-23/000021.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

## User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=6324-46962\&t=8SVsHQhTGuDo3QW4-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?type=design\&node-id=6324-46962\&t=8SVsHQhTGuDo3QW4-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. On successful submission, the success page is displayed.
2. On failure, a common failure page is displayed.
3. The request ID is generated as per the specified format.
