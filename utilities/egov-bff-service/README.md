# Backend For Frontend service

# egov-bff-service

egov-bff-service service work in between multiple microservices. Earlier client used to directly call multiple microservice, but with introduction of this new service one can provide just few parameters ex:- applicationnumber, tenantId to this new service to get a complete required details. 
### DB UML Diagram

- NA

### Service Dependencies

- egov-mdms-service
- individual-service
- muster-roll-service
- User-service
- Workflow-service


### Swagger API Contract

http://editor.swagger.io/?url=https://raw.githubusercontent.com/egovernments/utilities/jagankumar-egov/docs/egov-bff-service_contract.yml#!/

## Service Details

egov-bff-service service is new service being added which can work in between existing microservices.  With this service the existing API endpoints need not be exposed to frontend.

For any new requirement one new endpoint with validations and logic for getting data from multiple microservice has to be added in the code. With separate endpoint for each pdf we can define access rules per pdf basis. Currently egov-bff-service service has endpoint for following pdfs used in our system:-

- Muster roll


#### Configurations

**Steps/guidelines for adding support for new API's:**


- Follow code of [existing supported PDFs](https://github.com/egovernments/utilities/tree/master/egov-pdf/src/routes) and create new endpoint with suitable search parameters for each api

- Put parameters validations, module level validations ex:- application status,applicationtype and api error responses with proper error messages and error codes

- Add access to endpoint in MDMS for suitable roles

### API Details
Currently below endpoints are in use for 'EMPLOYEE’ roles

| Endpoint | module | query parameter | Restrict Citizen to own records |
| -------- | ------ | --------------- | ------------------------------- |
|`/egov-bff-service/download/PT/ptreceipt` | property-tax | `uuid, tenantId` | no |


### Kafka Consumers
NA

### Kafka Producers
NA

## License

MIT © [jagankumar-egov](https://github.com/jagankumar-egov)
