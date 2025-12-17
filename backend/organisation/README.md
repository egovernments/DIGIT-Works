# Organisation-Service

The organisation service is a generic registry to store all types of organisations. 
This includes vendors, contractors, community based organisations in the Works domain. 
This registry stores information about an organisation including their contact details, tax identifiers, areas of work and other relevant information.

### Service Dependencies

- DIGIT backbone services
- Persister
- Indexer
- IDGen
- Individual

## Service Details

- Provides create/update/search operations for organisation entities.
- Captures organisation details, contact details and classifications.
- Links organisations to functional areas they are operational in.

### API Specs
https://raw.githubusercontent.com/egovernments/DIGIT-Specs/master/Domain%20Services/Works/Organisation-V1.0.0.yaml

### Postman Collection
https://raw.githubusercontent.com/egovernments/DIGIT-Works/master/backend/organisation/docs/Organisation%20Registry%20-%20Test%20Scripts.postman_collection.json

