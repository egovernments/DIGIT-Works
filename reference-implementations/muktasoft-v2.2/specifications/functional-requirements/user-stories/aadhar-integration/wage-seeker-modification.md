# Wage Seeker Modification

## Context

Wage seeker’s modification to be changed to validate the individual’s AADHAAR through AADHAAR integration.

## Details <a href="#details" id="details"></a>

1. There is change in modification screens to accommodate the AADHAAR validation.
2. User is given an option to select the identity document and enter the document number.
3. In case, identity document selected is AADHAAR validate button is displayed.
4. In case of other document selected, validation button is not displayed.

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

#### Save <a href="#save" id="save"></a>

Save the record in system.

#### Validations <a href="#validations" id="validations"></a>

1. All existing validation
2. Validation button is displayed only when identity document selected as AADHAAR.

### Configurations <a href="#configurations" id="configurations"></a>

None

## User Interface <a href="#userinterface" id="userinterface"></a>

<figure><img src="../../../../../../.gitbook/assets/Desktop - 41.png" alt=""><figcaption></figcaption></figure>

<figure><img src="../../../../../../.gitbook/assets/Desktop - 43.png" alt=""><figcaption></figcaption></figure>

### Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

1. Change in Individual Details Page to accommodate AADHAAR validation.
2. AADHAAR validation is implementation only when identity document selected AADHAAR.
