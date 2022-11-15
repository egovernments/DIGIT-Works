# Estimate-Service

Estimate proposal is the simplest form of estimate that is created to start detailing out the scope and financial aspects of a project.

### DB UML Diagram

NA

### Service Dependencies

- user
- ID-GEN
- MDMS
- Location

### Swagger API Contract

https://raw.githubusercontent.com/egovernments/DIGIT-Works/estimate-service/backend/estimate-service/Estimate-Service-1.0.0.yaml

## Service Details

An estimate proposal contains
- Administrative details (Department, Work type, Work Category etc)
- Financial Details (Fund, Function, Budget heads COA etc)
- Work Details (Name of the work, estimated amount)
- Processing details (Approving department, Approver designation)


### API Details

- estimate - The estimate proposal API's can be used to create, update, and search .

### Kafka Producers

estimate.kafka.create.topic=save-estimate
estimate.kafka.update.topic=update-estimate

