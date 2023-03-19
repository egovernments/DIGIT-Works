# Local Setup

To setup the egov-bff-service service in your local system, clone the [utilities Service repository](https://github.com/egovernments/utilities).

## Dependencies

### Infra Dependency

- [ ] Postgres DB
- [ ] Redis
- [ ] Elasticsearch
- [ ] Kafka
  - [ ] Consumer
  - [ ] Producer

## Running Locally

- To run the ws-services in local system, you need to port forward below services.

```bash
 function kgpt(){kubectl get pods -n egov --selector=app=$1 --no-headers=true | head -n1 | awk '{print $1}'}
 kubectl port-forward -n egov $(kgpt egov-mdms-service) 8085:8080 &
 kubectl port-forward -n egov $(kgpt pdf-service) 8087:8080 &
 kubectl port-forward -n egov $(kgpt egov-user) 8089:8080 &
 kubectl port-forward -n egov $(kgpt egov-workflow-v2) 8091:8080
 
``` 

```bash
 function kgpt(){kubectl get pods -n works --selector=app=$1 --no-headers=true | head -n1 | awk '{print $1}'}
 kubectl port-forward -n works $(kgpt muster-roll-service) 8070:8080 &
 kubectl port-forward -n works $(kgpt individual) 8071:8080 


 function kgpt(){kubectl get pods -n egov --selector=app=$1 --no-headers=true | head -n1 | awk '{print $1}'}
 kubectl port-forward -n egov $(kgpt  egov-user) 8080:8080  
``` 

- Update below listed properties in `config.js` before running the project:

```ini
mdms: process.env.EGOV_MDMS_HOST || HOST || "http://localhost:8085/",
pdf: process.env.EGOV_PDF_HOST || HOST || "http://localhost:8087/",
user: process.env.EGOV_USER_HOST || HOST || "http://localhost:8089/",
workflow: process.env.EGOV_WORKFLOW_HOST || HOST || "http://localhost:8091/"
muster: process.env.EGOV_MUSTER_HOST || HOST || "http://localhost:8070/",
individual: process.env.EGOV_INDIVIDUAL_HOST || HOST || "http://localhost:8071/"
```



- Open the terminal and run the following command
    - `cd [filepath to egov-bff-service service]`
    - `npm install`
    - `npm start`
