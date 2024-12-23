# Revising estimate calculation logic

## Context

An estimate can be revised due to following reasons.

1. Due to revision in rates.
2. Due to deviations (less/excess) in SOR/ Non-SOR quantities.

And for both the revision, logic to calculate estimated to be implemented as given below.

## Details <a href="#details" id="details"></a>

1. For an estimate system should know the used and unused quantities of all the SORs/Non-SORs.
2. Used quantity to be fetched from approved MBs. In workflow MBs will not be considered to determine the used quantity.
3. Current effective rates to be applied to calculate the revised estimate amount and it will be calculated SOR/ Non-SOR wise as given below.
4. Amount = (Unused Quantity + Additional Quantity (if added any))\* current effective rate.
5. For newly added SOR/ Non-SOR, current effective rates are applied.

## Acceptance Criteria <a href="#acceptancecriteria" id="acceptancecriteria"></a>

The revised estimate amount is calculated using current effective rates on unused quantities of SORs.
