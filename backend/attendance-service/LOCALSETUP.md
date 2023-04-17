# Local Setup

To set up the attendance service in your local system, clone the Works Git repo(https://github.com/egovernments/DIGIT-Works). 
Also clone the DIGIT Core repository for the persister service.

## Dependencies

- MDMS
- IDGen
- Workflow
- Individual


### Infra Dependency

- [X] Postgres DB
- [ ] Redis
- [ ] Elasticsearch
- [X] Kafka

## Running Locally

To run the service locally, you need to port forward below services.

```bash
function kgpt(){kubectl get pods -n egov --selector=app=$1 --no-headers=true | head -n1 | awk '{print $1}'}

kubectl port-forward -n egov $(kgpt egov-user) 8085:8080
kubectl port-forward -n egov $(kgpt egov-idgen) 8086:8080
kubectl port-forward -n egov $(kgpt egov-mdms-service) 8087:8080
kubectl port-forward -n egov $(kgpt egov-workflow) 8088:8080
kubectl port-forward -n works $(kgpt individual) 8089:8080
``` 

Update below listed properties in `application.properties` before running the project:

```ini
egov.idgen.hostname = http://127.0.0.1:8086

# can use non port forwarded environment host as well
egov.mdms.host = http://127.0.0.1:8087
egov.workflow.host = http://127.0.0.1:8088
works.individual.host=https://works-dev.digit.org

```

Run Kafka locally, run the persister service (from DIGIT-Core) and run the project.
