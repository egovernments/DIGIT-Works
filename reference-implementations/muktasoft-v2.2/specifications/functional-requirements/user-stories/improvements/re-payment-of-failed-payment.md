# Re-payment of failed payment

## Context

They payment to the beneficiaries are done through JIT system. In a case when a payment is failed due to wrong bank account number or IFSC, or due to any other reasons the same is submitted again to JIT as a revised PI.

As per the recent conversations, it has been communicated to us that the revised PI works in tried within 90 days or before 30th April of next financial year from the date transaction got failed. Hence change in the system to made to address this use case.

## Details <a href="#details" id="details"></a>

1. This is for the failed transactions which could not be cleared before 90 days from the failure date or 30th April of next FY, whichever is earlier.
2. From the view PI page option to create a new PI to be enabled for such transactions.
3. A new original PI is created with request JSON PI is required and submit to JIT.

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

New PI is created and send for payment for those failed transactions which could not be cleared before 90 days from the failure date or 30th April of next FY, whichever is earlier.
