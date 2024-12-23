# Display Engaged Wage Seeker Details

## Scope

Data Privacy: Engage Wage Seeker changes

## **Actor**

CBO

## **Details**

1. Mobile number to be removed from display of engaged wage seekers.
2. The details are displayed as given below.
   1. Name
   2. Father/ husband’s name
   3. Address
3. The details given above are displayed only in cases where the role permission is “**View\_WS\_Unmasked**”.
4. For the other roles, the details given above are displayed masked.
5. The masking format is given below.
   1. Name - displayed unmasked.
   2. Father/ husband name: _**The first letter of each part of the name, followed by asterisks. (A\*\* B\*\*\*\*\*\*)**_
   3. Address
      1. Door No. - **The first letter is shown, and the rest is masked.**
      2. Street name - The first letter is shown, and the rest is masked.
      3. Locality - The first letter is shown, and the rest is masked.
      4. Ward - The first letter is shown, and the rest is masked.
      5. City - The first letter is shown, and the rest is masked.

## User Interface

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=13294-27030&node-type=frame" %}

## Acceptance Criteria

1. Mobile number is removed from engaged wage seekers display.
2. PII and sensitive information displayed masked for roles with permissions other than **View\_WS\_Unmasked**.
3. For the user(s) having role permissions for **View\_WS\_Unmasked** PII and sensitive details are displayed unmasked.
