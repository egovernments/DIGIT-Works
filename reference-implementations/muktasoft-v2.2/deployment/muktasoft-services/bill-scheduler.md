# Bill Scheduler

## Overview

This bill scheduler is a cronjob scheduler for calculating the supervision bill. It runs based on environment configuration. It triggers multiple APIs to generate the supervision bill.

## **Dependency**

* MDMS
* User
* Contract
* Expense Calculator

## Key Functionalities

* It creates supervision bills based on ACTIVE contracts using expense-calculate service /v1/\_calculate API.

### Code

[Bill Scheduler ](https://github.com/egovernments/DIGIT-Works/tree/master/utilities/expense-cronjob)

## Configuration

* Create a role in `ACCESSCONTROL-ROLES/roles.json` MDMS like [this](https://github.com/egovernments/works-mdms-data/pull/548/files).
* Create a `SYSTEM` user with BILL\_CREATOR and `SYSTEM` roles. Find the curl below.
* The same username will be used to generate bills BILL\_GEN\_CRONJOB, it’s defined in the environment config.
* Cron job duration will be configured using environment variables from [here](https://github.com/egovernments/DIGIT-DevOps/blob/digit-works/deploy-as-code/helm/charts/utilities/expense-cronjob/values.yaml#L7).

```
curl --location 'http://localhost:8082/user/users/_createnovalidate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "RequestInfo": {
        "api_id": "1",
        "ver": "1",
        "ts": null,
        "action": "create",
        "did": "",
        "key": "",
        "msg_id": "",
        "requester_id": "",
        "userInfo": {
            "userName": "BillCreator",
            "name": "BillCreator",
            "gender": "male",
            "mobileNumber": "9999999999",
            "active": true,
            "type": "EMPLOYEE",
            "tenantId": "{STATE_TANENT_ID}",
            "password": "eGov@123",
            "roles": [
                {
                    "code": "SUPERUSER",
                    "tenantId": "{STATE_TANENT_ID}"
                }
            ]
        }
    },
    "User": {
        "userName": "BILL_GEN_CRONJOB",
        "name": "Bill Generator",
        "gender": "male",
        "mobileNumber": "0000000000",
        "active": true,
        "type": "SYSTEM",
        "tenantId": "{STATE_TANENT_ID}",
        "password": "eGov@123",
        "roles": [
            {
                "code": "SYSTEM",
                "tenantId": "{STATE_TANENT_ID}"
            },
            {
                "code": "BILL_CREATOR",
                "name": "BILL_CREATOR",
                "tenantId": "{STATE_TANENT_ID}"
            }
        ]
    }
}'

```

## Deployment

[Helm Charts](https://github.com/egovernments/DIGIT-DevOps/tree/digit-works/deploy-as-code/helm/charts/utilities/expense-cronjob)

## Update Scheduler&#x20;

There are two ways to update the configuration of the scheduler:&#x20;

1. Add the config in the DevOps environment file, and restart the service. This will trigger the scheduler based on the updated environment configuration and restart the `expense-cronjob` service.

```
expense-cronjob:
  cron:
    schedule: "*/1 * * * *"
    suspend: "false"
```

2.  Use the commands given below:\
    \
    **Change schedule** - kubectl patch cronjobs expense-cronjob -p '{"spec" : {"schedule": "\*/10 \* \* \* \*" \}}'

    \
    **Pause cron job** - kubectl patch cronjobs expense-cronjob -p '{"spec" : {"suspend" : true \}}'\
    \
    **Resume cron job** - kubectl patch cronjobs expense-cronjob -p '{"spec" : {"suspend" : false\}}'\
    \
    **Create a new cronjob scheduler** - kubectl create job --from=cronjob/expense-cronjob expense-cronjob



