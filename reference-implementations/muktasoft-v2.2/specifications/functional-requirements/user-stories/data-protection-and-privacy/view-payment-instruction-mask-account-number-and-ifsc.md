# View Payment Instruction - Mask Account Number & IFSC

## Scope

View Payment Instruction - Account Number and IFSC to be masked.

## **Actor**

Employee

## **Details**

1. Change in View Payment Instruction is required to implement data privacy.
2. PII and sensitive information are masked so the users do not have a role to view it.
3. The format of masking is given below.
   1. Account number - Only the last 4 digits are displayed, the rest are masked with asterisk. e.g. 321004567621 to \*\*\*\*\*\*\*\*7621.
   2. IFSC - The first 4 letters and last 2 digits are displayed. e.g. ICIC0000047 to ICIC\*\*\*\*\*47.
4. The details shown are masked for the role other than **View\_WS\_Unmasked, View\_ORG\_Unmasked, and View\_DED\_Unmasked.**
5. For the user having roles **View\_WS\_Unmasked, View\_ORG\_Unmasked, and View\_DED\_Unmasked** information displayed is unmasked.

## User Interface

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=13294-27030&node-type=frame" %}

## Acceptance Criteria

1. Beneficiaries' PII and sensitive data are to be masked while displayed based on role.
2. A user having roles **View\_WS\_Unmasked, View\_ORG\_Unmasked, and View\_DED\_Unmasked** can see the details unmasked.
3. A user having roles other than **View\_WS\_Unmasked, View\_ORG\_Unmasked, and View\_DED\_Unmasked** views the details masked.
