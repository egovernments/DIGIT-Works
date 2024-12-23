# Expense Calculator

## Overview

The expense calculator is an implementation-specific service that works in tandem with the expense service. This service holds all the specific business logic in computing expenses and calls the billing service with the correct payload to create a bill.

There are three types of bills in Mukta:

* Wage bill - auto-generated from an approved muster roll and to be paid to wage seekers on completion of work
* Purchase bill - submitted by a JE or ME on behalf of the vendor
* Supervision bill - computed on top of wage and purchase bills to be paid to the CBO (community-based organisation).&#x20;

The calculator performs the calculation for all three types of bills and creates a bill.&#x20;

### Dependency

* DIGIT backbone services
* Persister
* MDMS
* IDgen
* Expense

## Key Functionalities

* Listens for muster roll approval on a Kafka topic and creates a wage bill based on the muster roll.
* Calculates supervision bill (if required) on any bills that have not been included so far and submits it to expense service
* Allows update of purchase bill.
* Search for meta information related to bills

## Code

[Expense calculator](https://github.com/egovernments/DIGIT-Works/tree/master/backend/expense-calculator)

## Deployment

[Helm charts](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/expense-calculator)

## Master Data&#x20;

* HeadCodes
* ApplicableCharges
* LabourCharges
* BusinessService
* PayerList

## Integration

* API spec
* Postman collection
