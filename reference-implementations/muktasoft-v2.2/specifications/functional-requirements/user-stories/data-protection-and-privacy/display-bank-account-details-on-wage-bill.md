# Display Bank Account Details On Wage Bill

## Scope

Data Privacy: Bank account details will be removed from the wage bill.

## Details

1. The wage bill is generated automatically by the system on approval of the muster roll.
2. As of now in the view wage bill details page, bank account details are displayed.
3. Bank account details are to be removed from the View Wage Bill page.
4. Muster Roll ID should be a **hyperlink**, on clicking it, opens into the View Muster Roll page.
5. The wage seeker ID should be a **hyperlink**, clicking it, opens the view wage seeker’s details.
6. Based on the role user can view the wage seeker’s details masked/unmasked.

## User Interface

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works" %}

## Acceptance Criteria

1. Sensitive information account - IFSC to be removed from the view bill.
2. Muster roll ID and Wage seeker ID will be available as hyperlinks.
3. Wage seekers' details are displayed masked/unmasked based on the role of the logged-in user.
