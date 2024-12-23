# Estimate-Service

Estimate proposal is the simplest form of estimate that is created to start detailing out the scope and financial aspects of a project.

### DB UML Diagram

NA

### Service Dependencies

- ID-GEN
- MDMS
- Project Management service
- workflow service
- Notification Service
- Localization Service
- Access control
- MDMS V2
- User Service
- Contract Service
- Measurement Service

### Swagger API Contract

https://raw.githubusercontent.com/egovernments/DIGIT-Works/develop/backend/estimate-service/Estimate-service-1.0.0.yaml

## Service Details

An estimate proposal contains
- details (Department,projectId,name,status,wfStatus,description,referenceNumber,proposalDate)
- Estimate Details
- Amount details
- Address details


### API Details

- estimate - The estimate proposal API's can be used to create, update, and search .

### Kafka Producers

estimate.kafka.create.topic=save-estimate

estimate.kafka.update.topic=update-estimate

estimate.kafka.enrich.topic=enrich-estimate

### Postman collection

https://elements.getpostman.com/redirect?entityId=5823395-ff2189ac-05b3-4edd-a3c3-b75199319800&entityType=collection

https://elements.getpostman.com/redirect?entityId=5823395-14d1ca52-b655-4009-92be-475c7172974c&entityType=collection 

