# Estimate-Service

Estimate proposal is the simplest form of estimate that is created to start detailing out the scope and financial aspects of a project.

### DB UML Diagram

NA

### Service Dependencies

- ID-GEN
- MDMS
- Project Management service
- workflow service

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

