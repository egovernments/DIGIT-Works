# Bank Account Service

The bank accounts registry houses financial account details of individual and organisational entities. 
The registry stores the account name, type, bank branch identifier (IFSC code) and other optional information. 
The bank branch identifier can be configured as master data. 
This makes it easy to extend this registry for use in countries outside India. 
The registry encrypts all PII and stores it in a secure fashion.

### Service Dependencies

- DIGIT backbone services
- Persister
- Encryption


## Service Details

- Stores bank account details of entities in a secure fashion. 
- All PII data is encrypted securely

### API Specs

https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/Domain%20Services/Works/Bank-Account-v1.0.0.yaml

### Postman Collection

https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/bankaccounts/docs/BankAccount.postman_collection.json

