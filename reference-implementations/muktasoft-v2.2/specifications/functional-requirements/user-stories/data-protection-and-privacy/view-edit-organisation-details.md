# View/Edit Organisation Details

## Scope

View/ Edit Organization’s Details - PII data to be masked.

## **Actor**

Employee

## **Details**

1. Change in View/ Edit Organization is required to implement data privacy.
2. PII and sensitive information to be masked for all other roles other than “**View\_ORG\_Unmasked**”.
3. The format of masking is as given below.
   1. Door No. - **The first letter is shown, and the rest is masked.** **e.g. 101A/2 → 1\*\*\*\*\*.**
   2. Street Name - **The first letter is shown, and the rest is masked.** **e.g.** Gandhi Marg → G\*\*\*\*\*\*\*\*g.
   3. Locality - **The first letter is shown, and the rest is masked.** **e.g.** Manohar Nagar → M\*\*\*\*\*\*\*\*\*\*\*r.
   4. Mobile Number - The first 8 digits are masked, rest are displayed. e.g. 8888888888 → \*\*\*\*\*\*\*\*\*\*88.
   5. Email ID - The first, last and domain are displayed, rest all are masked. e.g. [nschauhan@gmail.com](mailto:nschauhan@gmail.com) → n\*\*\*\*\*\*\*n@gmail.com
   6. Account number - Only last 4 digits are displayed, rest all are masked with asterisk. e.g. 321004567621 → \*\*\*\*\*\*\*\*7621.
   7. IFSC - The first 4 and last 2 digits are displayed, rest are masked. e.g. ICIC0000047 → ICIC\*\*\*\*\*47.
   8. Branch Address -
      1. Street number and specific street details are masked.
      2. The district name is partially masked.
      3. The city, state, and postal code remain visible.
   9. PAN - The first 3 and Last 2 digit are displayed. rest are masked. BNQNS7208B → BNQ\*\*\*\*\*\*8B.
   10. GSTIN - The first 3 and Last 2 digit are displayed. rest are masked.
4. The details shown masked for the role other than **View\_ORG\_Unmasked.**
5. For the user having role “**View\_ORG\_Unmasked**” information displayed is unmasked.

## User Interface

View

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=13630-29607&node-type=frame" %}

Edit

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=13630-29590&node-type=frame" %}

## Acceptance Criteria

1. Organization contact person PII and sensitive data are to be masked while displayed based on role.
2. A user having the role permission for “**View\_ORG\_Unmasked**” can see the details unmasked.
3. A user having a role permission other than “**View\_ORG\_Unmasked**” and view the details masked without an option to unmask it.
