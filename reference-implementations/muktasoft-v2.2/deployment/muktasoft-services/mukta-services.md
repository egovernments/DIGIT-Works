# MUKTA Services

## Overview

MUKTA Services is an intermediary service that bridges the gap between the API and client requests. It helps Clients/UI gather all the required information from different services.

## Dependency&#x20;

* MDMS
* MDMS V2
* Estimates
* Muster Roll&#x20;
* Contract
* Measurement
* Bank Accounts
* Individual
* Organisation

## Code

[Mukta-services](https://github.com/egovernments/DIGIT-Works/tree/measurement-ui/utilities/mukta-services)

## Deployment

* [Helm Chart](https://github.com/egovernments/DIGIT-DevOps/tree/unified-env/deploy-as-code/helm/charts/digit-works/utilities/mukta-services)

## Role Action&#x20;

| API EndPoints                                              | Roles               |
| ---------------------------------------------------------- | ------------------- |
| mukta-services/measurement/\_search                        | MB\_CREATOR         |
| mukta-services/bankaccount-service/bankaccount/v1/\_search | VIEW\_DED\_UNMASKED |
| mukta-services/individual/v1/\_search                      | VIEW\_WS\_UNMASKED  |
| mukta-services/org-services/organisation/v1/\_search       | VIEW\_ORG\_UNMASKED |

## Measurement Create

### Flow Diagram

<figure><img src="https://github.com/egovernments/DIGIT-Works/blob/04689228d238592a34e832be2997a0f05ac956f8/utilities/mukta-services/docs/flowdiagram.png?raw=true" alt=""><figcaption><p>mukta service flow diagram</p></figcaption></figure>

### Measurement Create Criteria

This service handles all the [measurement creation criteria](https://github.com/egovernments/egov-mdms-data/blob/UNIFIED-DEV/data/pg/works/MeasurementCriteria.json) and helps the UI consolidate all information. The measurement period is calculated in the mukta-services based on the same criteria.

```
 {
        "reference": "CONTRACT", //this criteria is for the measurement created using contract
        "period": 7, // default measurement period 
        "contractStatus":"ACTIVE", // measurement can be created if the referenced contract is in the following status
        "measurementWorkflowStatus": "APPROVED", // measurement can be created if previously created measurement in this status
        "enableMeasurementAfterContractEndDate":false, // measurement can be created even if the contract has expired
        "active": true
      }
```

### Measurement Response&#x20;

The measurement response would be based on the config \
[https://github.com/egovernments/egov-mdms-data/blob/UNIFIED-DEV/data/pg/works/MeasurementBFFConfig.json](https://github.com/egovernments/egov-mdms-data/blob/UNIFIED-DEV/data/pg/works/MeasurementBFFConfig.json)\
which transforms the final consolidated object of Estimate, Contract, measurement, and Muster roll into the user-requested object.

```
{
            "path": "contract",   // reponse objects path
            "jsonPath": "$.contract"  // path from the consolidated object
        },
```

## Data Masking Controller

This service masks the search response before displaying it in the UI. This middleware eliminates the need to make major changes in the backend. The masking is done based on the user roles for sensitive PII data.

### Flow Diagram

<figure><img src="https://lh7-rt.googleusercontent.com/docsz/AD_4nXf58pHabfkf3XoXSveAya24j-h7BCnzKur1HV7WH3FBK0_JZ_8wwbpFul5NLXe-ODbKBLQuLVvESYxAWN_7lKhejPJmPmZ5gWiJ9Hat_XE4gBeW0-U6BGV8bk5u6-gmNdx7Zad0Uw?key=Ygm9bXC8tcP0lXIGA6_Ws618" alt=""><figcaption></figcaption></figure>

Three APIs have been defined under the data privacy controller as follows:

* BankAccountSearch: Unmasking Role - VIEW\_DED\_UNMASKED
* IndividualSearch: Unmasking Role - VIEW\_WS\_UNMASKED
* OrganisatonSearch: Unmasking Role - VIEW\_ORG\_UNMAKSED
