# Display Muster Roll Details

## Scope

Data Privacy: Changes in muster roll (AADHAAR to be removed)

## **Actor**

Employee

## **Details**

1. Change in View/ Edit Muster Roll is required to implement data privacy.
2. The following details are to be removed from muster roll View and Edit Pages and PDF.
   1. Account Number
   2. IFSC
   3. AADHAAR
3. Wage seeker ID should be a hyperlink to view the beneficiary details.
4. PII and sensitive information are displayed masked/unmasked based on the role of the logged-in user.
5. Rest remains the same as the existing muster roll.

## User Interface

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works" %}

## Acceptance Criteria

1. Sensitive account information like IFSC and AADHAAR is to be removed from the muster roll view, edit, and PDF.
2. Wage seeker ID to be available as a hyperlink.
3. Wage seekers' details are displayed as masked/unmasked based on the role of the logged-in user.
