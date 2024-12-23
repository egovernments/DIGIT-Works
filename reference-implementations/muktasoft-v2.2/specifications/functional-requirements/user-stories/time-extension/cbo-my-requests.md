# CBO: My Requests

## Context

A closure request is created and send for approval, there should be an option to view all the request which are raised so far and a option to edit if a closure request is sent to creator for the same.

## Solution <a href="#solution" id="solution"></a>

### Scope <a href="#scope" id="scope"></a>

Closure Requests/ Time Extension Request

### Actors <a href="#actors" id="actors"></a>

CBO

Role: CBO ADMIN

### Details <a href="#details" id="details"></a>

1. To be view the closure requests Closure Requests feature is provided.
2. Closure Requests lists all the closure requests which are created for the works assigned to logged-in CBO user and are segregated by In Progress and Approved closure requests.

#### Attributes <a href="#attributes" id="attributes"></a>

Each closure request card will have below attributes displayed

1. Closure Request No.
2. Work Order No.
3. Work Description
4. Location - Locality + Ward
5. Work Start Date
6. Work End Date
7. Work Amount
8. Status \[Submitted, Sent Back, Verified, Approved]
9. View Details/ Edit - Action button to see the muster roll details./ Edit action is enabled when service request is send back for correction.

On View Details, the details of closure request is displayed with the attributes as given below.

1. Project Details
2. Closure Request No.
3. Project ID
4. Project Sanction Date
5. Project Type
6. Project Name
7. Project Description
8. Location

On View Details, the details of time extension request is displayed with the attributes as given below.

1. Request ID
2. Work Order No.
3. Project ID
4. Work Description
5. Completion Days
6. Work Start Date
7. Work End Date
8. Extension required in days
9. Extended End Date
10. Extension Reason
11. Status

### Validations <a href="#validations" id="validations"></a>

Not applicable

### Configurations <a href="#configurations" id="configurations"></a>

Not applicable

### Actions <a href="#actions" id="actions"></a>

1. View Details - To view the closure request details.
2. Edit - In case closure request is sent back for correction.

### Notifications <a href="#notifications" id="notifications"></a>

Not applicable.

## User Interface <a href="#userinterface" id="userinterface"></a>

[https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=5661-45392\&t=AM5rvvYAmzOMeYfB-4](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=5661-45392\&t=AM5rvvYAmzOMeYfB-4)

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. My service request to list all the request raised for project assigned to him.
2. Details in the card view is displayed as provided.
3. Details for detailed view is displayed as provided.
4. CBO can edit the closure request when it is sent back to CBO.
