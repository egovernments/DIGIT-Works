# Create/ Edit MB

## Scope

Create Measurement Book

Employee Mobile Application

## Actors <a href="#actors" id="actors"></a>

ULB Employees

## Details <a href="#details" id="details"></a>

1. This page is to create a new MB for a project or edit an existing MB in workflow.
2. A MB would be either first MB, intermediate MB or a last MB for a project.
3. First MB will not have any used quantity and MB history associated with it while the subsequent MBs will have used quantity as well as a associated MB history. Complete MB details of previous MB can be seen using view MB details.
4. The details is displayed as below while creating a MB.
   1. Measurement Book Primary Details Details
      1. MB Number
      2. Work Order Number
      3. Muster Roll ID
      4. Project Description
      5. Measurement Period
5. MB History (Cards) - In case of first MB, this information is not displayed.
   1. MB Number
   2. Period
   3. Date
   4. MB Amount
   5. Status

### Actions <a href="#actions" id="actions"></a>

Actions - It will open up with the workflow actions according to role user is having.

1. Edit
2. Submit
3. Verify and Forward
4. Send Back
5. Send Back To Originator
6. Approve
7. Reject

### Validations <a href="#validations" id="validations"></a>

1. All the validations applied while creating/ editing a MB in Web application.
2. All the validation applied during workflow available in web.

### Configuration <a href="#configuration" id="configuration"></a>

None

### Notifications <a href="#notifications" id="notifications"></a>

All the notification during workflow as per web application.

## User Interface <a href="#userinterface" id="userinterface"></a>

<div><figure><img src="../../../../../../.gitbook/assets/Android - 511.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 512.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 513.png" alt=""><figcaption></figcaption></figure></div>

<div><figure><img src="../../../../../../.gitbook/assets/Android - 504.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 505.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 506.png" alt=""><figcaption></figcaption></figure></div>

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Create MB Page is developed as per figma.
2. All the validations existing to web application are applicable here.
3. All web workflow actions are applicable and displayed as per the role user has.
