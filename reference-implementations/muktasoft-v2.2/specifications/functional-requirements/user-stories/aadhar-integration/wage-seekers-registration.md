# Wage Seeker's Registration

## Context

Wage seeker’s registration process to be changed to validate the individual’s AADHAAR through AADHAAR integration.

## Details <a href="#details" id="details"></a>

1. There is change in wage registration mobile screens to accommodate the AADHAAR validation and make it more user friendly.
2. Entire registration process is divided into 4 steppers as it was there in earlier and the screens provided as given below.
   1. Individual’s Identification Details
   2. Individual’s Personal Details
   3. Individual’s Skills Details
   4. Individual’s Photo
   5. Location Details
   6. Financial Details
   7. Summary

### Actions <a href="#actions" id="actions"></a>

#### Validate <a href="#validate" id="validate"></a>

On validate, API is called and AADHAAR is validated.

1. On valid AADHAAR, a inline success message is displayed and “Next” button is enabled to move to next page.

```
AADHAAR provide is valid!
```

2. On invalid AADHAAR, a inline invalid message is displayed and “Next” button is kept disabled.

```
 AADHAR provided is invalid, enter a valid AADHAAR or choose any other idetity document!
```

3. On failure, a inline invalid message is displayed and “Next” button is kept disabled.

```
Validation process failed, try again or choose any other idetity document!
```

4. On selection of any other identity document, other than AADHAAR, “Validate” action is not displayed and user is allowed to move to next scree if case all the required detail is entered.

#### Next <a href="#next" id="next"></a>

Takes the user to next page, when all the required details of current page entered.

#### Submit <a href="#submit" id="submit"></a>

Save the record in system.

#### Validations <a href="#validations" id="validations"></a>

1. All existing validation
2. Validation button is displayed only when identity document selected as AADHAAR.

### Configurations <a href="#configurations" id="configurations"></a>

None

## User Interface <a href="#userinterface" id="userinterface"></a>

<div><figure><img src="../../../../../../.gitbook/assets/Android - 500.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 502.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 503.png" alt=""><figcaption></figcaption></figure></div>

<div><figure><img src="../../../../../../.gitbook/assets/Android - 498 (1).png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 504 (2).png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 505 (1).png" alt=""><figcaption></figcaption></figure></div>

<div><figure><img src="../../../../../../.gitbook/assets/Android - 499.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 501.png" alt=""><figcaption></figcaption></figure> <figure><img src="../../../../../../.gitbook/assets/Android - 506 (2).png" alt=""><figcaption></figcaption></figure></div>

### Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Change in Individual Details Page to accommodate AADHAAR validation.
2. AADHAAR validation is implementation only when identity document selected AADHAAR.
