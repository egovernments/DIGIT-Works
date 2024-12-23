# Work Order PDF

## Context

Generate a pdf copy of the work order.

## Actors

Employees

## Actions

The Work Order PDF has 6 main sections.

1. Header - Municipality Info and Work Order No. and Amount.
2. Work order is addressed to either JE/AE or CBO.
3. The subject section
4. The content of the work order body
5. The work order issue detail
6. Footer - Terms and Conditions

**Conditions**

1. In case the CBO role is defined as the Implementation Agency
   * The work order is addressed to CBO only.
   * \<Officer Incharge/ CBO> ---> \<CBO Name>
   * \<Implementation Agency/ Implementation Partner> ---> \<Implementation Agency>
2. In case the CBO role is defined as Implementation Partner
   * The work order is addressed to JE and CBO both, JE’s name comes first.
   * \<Officer Incharge/ CBO> ---> \<Officer In-charge Name>
   * \<Implementation Agency/ Implementation Partner> ---> \<Implementation Partner>

Other variables -

* SLA Days - maximum days are given to CBO to accept the work order.
* Due Date - Work order approval date + SLA Days

## **User Interface**

[<img src="https://static.figma.com/uploads/b6df2735e4cb368306acf5480b50f96e69f96099" alt="" data-size="line">DIGIT-Works](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=3007-34151\&t=fsj3x2YAlWlUqPRx-4)

## **Acceptance Criteria**

<table><thead><tr><th width="244">Acceptance Criteria</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Design should be as per Figma.</td></tr><tr><td>2</td><td>Conditions are fulfilled.</td></tr></tbody></table>

\
