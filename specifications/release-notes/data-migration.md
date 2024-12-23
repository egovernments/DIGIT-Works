# Data Migration

## Skills Migration to SOR

Skills data is stored as SOR in MDMSv2 and needs mapping. After mapping, update the skills info in individuals' details and muster rolls with SOR. Use the provided migration script to help with this data update.

{% hint style="info" %}
This migration is Mukta-specific, it will not be part of our master code.
{% endhint %}

## &#x20;Migration Steps

To migrate data follow the steps given below:

1. **Create Labour-Sor Mapping:**&#x20;
   * Add the following mapping to the `expense.sor.migration.mapping` field in the application properties: \`{"UNSKILLED.CARPENTER":"SOR\_000016", "SKILLED.CARPENTER":"SOR
   * You can also override it in the Helm ([click here](https://github.com/egovernments/DIGIT-DevOps/blob/ad68dbd9aad3fbfb532e5cb2617e3b07d4770036/deploy-as-code/helm/environments/unified-works-dev.yaml#L236)).
2. **Use the Specific Build**:
   * egovio/expense-calculator-db:sor-migration-8007f0108-142&#x20;
   * [Click here](https://github.com/egovernments/DIGIT-Works/blob/sor-migration/backend/expense-calculator/src/main/java/org/egov/digit/expense/calculator/util/SorMigrationUtil.java) to access the branch and change details.
3. **Build and Deploy**:
   * Deploy the build provided above or build and deploy from the code linked above if custom changes are required.
   * Use the curl below to migrate the data.
4. #### Add the below environment variables to the expense-calculator helm:
   * state.level.tenant.id
   * muster.roll.update.topic
   * individual.host
   * individual.context.path
   * individual.update.topic
5. Create table eg\_sor\_migration( id character varying(128), is\_migration\_successful boolean) ;
6. ```json
   curl --location 'http://localhost:8090/expense-calculator/_migrate/musterRoll' \
   --header 'Content-Type: application/json' \
   --data '{
       "RequestInfo": {
           "apiId": "Rainmaker",
           "authToken": "e1da5b9d-e9d5-4798-bd25-f5d8fbbd7cc8",
           "userInfo": {
               "id": 176,
               "uuid": "488c2a00-e33f-49f5-932d-239b1ae33e88",
               "userName": "Product UAT",
               "name": "Product UAT",
               "mobileNumber": "7200990110",
               "type": "EMPLOYEE",
               "roles": [
                   {
                       "name": "System",
                       "code": "SYSTEM",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "MUKTA Admin",
                       "code": "MUKTA_ADMIN",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "BILL_VIEWER",
                       "code": "BILL_VIEWER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "MUSTER ROLL APPROVER",
                       "code": "MUSTER_ROLL_APPROVER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "MUSTER ROLL VERIFIER",
                       "code": "MUSTER_ROLL_VERIFIER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "Organization viewer",
                       "code": "ORG_VIEWER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "WORK ORDER APPROVER",
                       "code": "WORK_ORDER_APPROVER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "ESTIMATE VERIFIER",
                       "code": "ESTIMATE_VERIFIER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "OFFICER IN CHARGE",
                       "code": "OFFICER_IN_CHARGE",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "WORK ORDER CREATOR",
                       "code": "WORK_ORDER_CREATOR",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "Employee Common",
                       "code": "EMPLOYEE_COMMON",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "TECHNICAL SANCTIONER",
                       "code": "TECHNICAL_SANCTIONER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "PROJECT VIEWER",
                       "code": "PROJECT_VIEWER",
                       "tenantId": "statea.cityone"
                   }
               ],
               "tenantId": "statea.cityone"
           },
           "msgId": "1684664105678|en_IN",
           "plainAccessRequest": {}
       }
   }'

   ```
7. ```json
   curl --location 'http://localhost:8090/expense-calculator/_migrate/individual' \
   --header 'Content-Type: application/json' \
   --data '{
       "RequestInfo": {
           "apiId": "Rainmaker",
           "authToken": "e1da5b9d-e9d5-4798-bd25-f5d8fbbd7cc8",
           "userInfo": {
               "id": 176,
               "uuid": "488c2a00-e33f-49f5-932d-239b1ae33e88",
               "userName": "Product UAT",
               "name": "Product UAT",
               "mobileNumber": "7200990110",
               "type": "EMPLOYEE",
               "roles": [
                   {
                       "name": "System",
                       "code": "SYSTEM",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "MUKTA Admin",
                       "code": "MUKTA_ADMIN",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "BILL_VIEWER",
                       "code": "BILL_VIEWER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "MUSTER ROLL APPROVER",
                       "code": "MUSTER_ROLL_APPROVER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "MUSTER ROLL VERIFIER",
                       "code": "MUSTER_ROLL_VERIFIER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "Organization viewer",
                       "code": "ORG_VIEWER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "WORK ORDER APPROVER",
                       "code": "WORK_ORDER_APPROVER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "ESTIMATE VERIFIER",
                       "code": "ESTIMATE_VERIFIER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "OFFICER IN CHARGE",
                       "code": "OFFICER_IN_CHARGE",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "WORK ORDER CREATOR",
                       "code": "WORK_ORDER_CREATOR",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "Employee Common",
                       "code": "EMPLOYEE_COMMON",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "TECHNICAL SANCTIONER",
                       "code": "TECHNICAL_SANCTIONER",
                       "tenantId": "statea.cityone"
                   },
                   {
                       "name": "PROJECT VIEWER",
                       "code": "PROJECT_VIEWER",
                       "tenantId": "statea.cityone"
                   }
               ],
               "tenantId": "statea.cityone"
           },
           "msgId": "1684664105678|en_IN",
           "plainAccessRequest": {}
       }
   }'
   ```
8.  #### Verification in DB for muster-roll:

    Query the eg\_wms\_attendance\_summary table and check if “skillCode” in additionalDetails has been migrated from UNSKILLED.CARPENTER ->  SOR\_000016

    Example:

    Old data: additionalDetails : { …… skillCode : UNSKILLED.CARPENTER  …… }

    New data: additionalDetails : { …… skillCode : SOR\_000016  …… }
9.  **Verification in DB for an individual:** Query the individual\_skill table and check if “type” and “level” are migrated to “SOR” and “SOR\_code” respectively.

    Example.:

    Old data:

    Type: CARPENTER

    Level: UNSKILLED

    New data:

    Type: SOR

    Level: SOR\_000016
10. Once the migration is complete remove the environment variables added for migration.
