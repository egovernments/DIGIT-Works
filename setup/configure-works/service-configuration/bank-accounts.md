# Bank accounts

## Overview

The bank accounts registry houses the financial account details of individual and organisational entities. The registry stores the account name, type, bank branch identifier (IFSC code) and other optional information. The bank branch identifier can be configured as master data. This makes it easy to extend this registry for use in countries outside India. The registry encrypts all PII and stores it securely.

## Pre-requisites

* DIGIT backbone services
* Persister
* Encryption

## Key Functionalities

* Stores bank account details of entities in a secure fashion.&#x20;
* All PII data is encrypted securely.&#x20;

**Code**

[Module code](https://github.com/egovernments/DIGIT-Works/tree/master/backend/bankaccounts)

## Deployment Details

[Helm charts](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/digit-works/backend/bankaccounts) for deployment

### Integration Details

**Base path**: `/bankaccount-service/bankaccount`

[Postman collection](https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/bankaccounts/docs/BankAccount.postman_collection.json)
