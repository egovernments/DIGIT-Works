# View /Edit Wage Seeker Details

## Scope

View/ Edit Wage Seeker's Details - PII data to be masked.

## **Actor**

Employee

## **Details**

1. Change in View/ Edit Wage Seeker is required to implement data privacy.
2. PII and sensitive information are to be masked for all roles other than “**View\_WS\_Unmasked**”.
3. The format of masking is given below.
   1. Name - displayed unmasked.
   2. Identity number - Completely masked (**Undisclosed**) and for the role **View\_WS\_Unmasked** - (\*\*\*\*\*\*\*\***1234)**
   3. Father/ husband name - _**The first letter of each part of the name, followed by asterisks. (A\*\* B\*\*\*\*\*\*)**_
   4. Relationship - Completely masked (**Undisclosed**)
   5. Address
      1. Door No. - **The first letter is shown, and the rest is masked.** **e.g. 101A/2 → 1\*\*\*\*\*.**
      2. Street Name - **The first letter is shown, and the rest is masked.** **e.g. Gandhi Marg → G\*\*\*\*\*\*\*\*\*.**
      3. Locality - **The first letter is shown, and the rest is masked.** **e.g. Manohar Nagar → M\*\*\*\*\*\*\*\*\*\*\*\*.**
      4. Ward - The first letter is shown, and the rest is masked.
      5. City - The first letter is shown, and the rest is masked.
   6. Date of birth - Only the year is visible - **\*\*/\*\*/1990**
   7. Gender - Complete masked. (**Undisclosed)**
   8. Mobile Number - The first 8 digits are masked, rest are displayed. **e.g. 8888888888 → \*\*\*\*\*\*\*\*\*\*88.**
   9. Social category - Complete masked. (**Undisclosed)**
   10. Email ID - The first and last letters and domain are displayed, rest are masked. **e.g.** [**nschauhan@gmail.com**](mailto:nschauhan@gmail.com) **→ n\*\*\*\*\*\*\*n@gmail.com**
   11. Photo - Completely masked - **Undisclosed**
   12. Account number - The last 4 digits are displayed, the rest are masked with an asterisk. **e.g. 321004567621 → \*\*\*\*\*\*\*\*7621.**
   13. IFSC - The first 4 and last 2 digits are displayed, rest are masked. **e.g. ICIC0000047 → ICIC\*\*\*\*\*47.**
   14. PAN - The first 3 and Last 2 digits are displayed. rest are masked. **e.g. BNQNS7208B → BNQ\*\*\*\*\*\*8B.**
4. The details shown masked for the role other than **View\_WS\_Unmasked.**
5. For the user having role “**View\_WS\_Unmasked**” information displayed is unmasked.

## User Interface

{% embed url="https://www.figma.com/design/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=13630-30962&node-type=frame" %}

## Acceptance Criteria

1. Wage seekers' PII and sensitive data are to be masked while displayed based on role.
2. A user having the role permission for “**View\_WS\_Unmasked**” can see the details unmasked except the AADHAAR/ Identity number.
3. A user having role permission other than “**View\_WS\_Unmasked**” can view the details masked without an option to unmask it.
