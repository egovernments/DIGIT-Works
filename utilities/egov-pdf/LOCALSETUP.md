# Local Setup

To setup the egov-pdf service in your local system, clone the [works repository](https://github.com/egovernments/DIGIT-Works).

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

- Update below listed properties in `config.js` before running the project:

```ini
mdms: process.env.EGOV_MDMS_HOST || HOST || "http://localhost:8085/",
pdf: process.env.EGOV_PDF_HOST || HOST || "http://localhost:8087/",
user: process.env.EGOV_USER_HOST || HOST || "http://localhost:8089/",
workflow: process.env.EGOV_WORKFLOW_HOST || HOST || "http://localhost:8091/"
```
- Open the terminal and run the following command
    - `cd [filepath to egov-pdf service]`
    - `npm install`
    - `npm start`
