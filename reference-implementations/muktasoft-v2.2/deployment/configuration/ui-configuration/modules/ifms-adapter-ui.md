---
description: UI Tech Documentation for JIT
---

# IFMS Adapter UI

JIT stands for Just in Time e-Payment. We are utilizing JIT in MUKTASOFT for payments.

### **We have the following screens for JIT in Web-Interface**

* Search Payment Instruction
* View Payment Instruction
* Create Payment Instruction (Temporary Screen)

### **Create Payment Instruction (Temporary Screen)**&#x20;

Payment Instructions are created at the Backend when a bill gets approved. This is handled via cron jobs. We have kept a temporary flow for a user to create Payment for testing cases, or for when cron jobs are not running at Backend properly. In production this flow will be disabled.

To create Payment Instruction from UI. Navigate to Search Payment Instruction screen from home screen card and click on Create Payment Instruction. Search bill screen is opened, the user can select for which bills payment has to be created and click on Generate payment. A relevant toast message is displayed.



### **Search Payment Instruction**&#x20;

1. gate, login and go to the Home Page. Then click on Payment Instruction on Mukta Home page card
2. This screen is utilized to search payments. It has 2 tabs.
3.
   * Pending For Action -> This tab will, by default, show all the payments that are either in FAILED or PARTIAL state
   * Open Search -> This screen can be utilized to search any payment&#x20;
4. Mdms Configs for Search Screen -> [Search Payment Instruction](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/commonMuktaUiConfig/SearchPaymentInstructionConfig.json)
5. Payment Instruction type config -> [Payment Instruction Type](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/expense/PaymentInstructionType.json)
6. Payment Instruction Status config -> [Payment Instruction Status](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/expense/PaymentInstructionStatus.json)



### View Payment Instruction

Users can navigate to the view payment instruction screen from the search payment instruction screen. It shows detailed information about the payment, Beneficiary, and list of payment instructions associated.

Sometimes payment gets failed or partially successful.  For these use cases we have two options which are as follows :&#x20;

* For FAILED payments : If a payment is in a failed state, an action button is shown on the view screen to take a retry action. Upon retrying, a new payment instruction gets generated and a toast message is displayed. Also the View screen is refreshed which now includes the newly created payment instruction in INITIATED state.
* For PARTIAL payments : If a payment is in PARTIAL state. This basically means payment for at least one beneficiary was successful. In this case we have an option to generate a revised payment instruction which creates a new child payment instruction associated with the original payment instruction. Again, a success toast is displayed and the View screen gets refreshed with newly generated payment instruction. Newly generated payment instruction is now referred to as revised payment instruction.

### &#x20;List of statuses available for payment

Mdms config -> [Payment Status](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/expense/PaymentInstructionStatus.json)

* INITIATED
* APPROVED
* FAILED
* INPROCESS
* SUCCESSFUL
* PARTIAL
* COMPLETED



### List of Payment Types&#x20;

Mdms config -> [Payment Types](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/expense/PaymentInstructionType.json)

* Original&#x20;
* Revised

### API's Used for JIT in UI

* /wms/ifms-pi/\_search -> for Searching PI's
* /ifms/pi/v1/_create_ -> For Retry and Partial action

Sample Curl's:

```
curl 'https://works-qa.digit.org/ifms/pi/v1/_create?_=1693813763900' \
  -H 'authority: works-qa.digit.org' \
  -H 'accept: application/json, text/plain, */*' \
  -H 'accept-language: en-US,en;q=0.9' \
  -H 'content-type: application/json' \
  -H 'cookie: __cuid=59fd9aac25b044f6af006bd4b159cbbf; amp_fef1e8=f4fc07f6-3fb0-4c67-8114-a1beb906e625R...1h2nk91dr.1h2nk91du.a.0.a' \
  -H 'origin: https://works-qa.digit.org' \
  -H 'referer: https://works-qa.digit.org/works-ui/employee/expenditure/view-payment?tenantId=pg.citya&paymentNumber=EP/0/2023-24/09/04/000172' \
  -H 'sec-ch-ua: "Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  -H 'sec-fetch-dest: empty' \
  -H 'sec-fetch-mode: cors' \
  -H 'sec-fetch-site: same-origin' \
  -H 'user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36' \
  --data-raw '{"tenantId":"pg.citya","referenceId":"EP/0/2023-24/09/04/000172","RequestInfo":{"apiId":"Rainmaker","authToken":"fb30f563-c053-46e2-acfd-6e1ebdf75827","msgId":"1693813763900|en_IN","plainAccessRequest":{}}}' \
  --compressed
```

\


```
curl 'https://works-qa.digit.org/wms/ifms-pi/_search' \
  -H 'authority: works-qa.digit.org' \
  -H 'accept: application/json, text/plain, */*' \
  -H 'accept-language: en-US,en;q=0.9' \
  -H 'content-type: application/json' \
  -H 'cookie: __cuid=59fd9aac25b044f6af006bd4b159cbbf; amp_fef1e8=f4fc07f6-3fb0-4c67-8114-a1beb906e625R...1h2nk91dr.1h2nk91du.a.0.a' \
  -H 'origin: https://works-qa.digit.org' \
  -H 'referer: https://works-qa.digit.org/works-ui/employee/expenditure/search-payment-instruction' \
  -H 'sec-ch-ua: "Chromium";v="116", "Not)A;Brand";v="24", "Google Chrome";v="116"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  -H 'sec-fetch-dest: empty' \
  -H 'sec-fetch-mode: cors' \
  -H 'sec-fetch-site: same-origin' \
  -H 'user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36' \
  --data-raw '{"inbox":{"moduleSearchCriteria":{"tenantId":"pg.citya","status":["PARTIAL","FAILED"]},"tenantId":"pg.citya","limit":10,"offset":0},"RequestInfo":{"apiId":"Rainmaker","authToken":"fb30f563-c053-46e2-acfd-6e1ebdf75827","userInfo":{"id":444,"uuid":"bff9c3f6-6a25-45c4-b7ae-dddc016598fd","userName":"SMS QA testing","name":"SMS QA testing","mobileNumber":"9043685314","emailId":"","locale":null,"type":"EMPLOYEE","roles":[{"name":"HRMS Admin","code":"HRMS_ADMIN","tenantId":"pg.citya"},{"name":"WORK_ORDER_VIEWER","code":"WORK_ORDER_VIEWER","tenantId":"pg.citya"},{"name":"BILL_ACCOUNTANT","code":"BILL_ACCOUNTANT","tenantId":"pg.citya"},{"name":"WORK ORDER CREATOR","code":"WORK_ORDER_CREATOR","tenantId":"pg.citya"},{"name":"ESTIMATE VERIFIER","code":"ESTIMATE_VERIFIER","tenantId":"pg.citya"},{"name":"BILL_VERIFIER","code":"BILL_VERIFIER","tenantId":"pg.citya"},{"name":"ESTIMATE APPROVER","code":"ESTIMATE_APPROVER","tenantId":"pg.citya"},{"name":"Organization viewer","code":"ORG_VIEWER","tenantId":"pg.citya"},{"name":"WORK ORDER VERIFIER","code":"WORK_ORDER_VERIFIER","tenantId":"pg.citya"},{"name":"PROJECT VIEWER","code":"PROJECT_VIEWER","tenantId":"pg.citya"},{"name":"BILL_APPROVER","code":"BILL_APPROVER","tenantId":"pg.citya"},{"name":"MUSTER ROLL VERIFIER","code":"MUSTER_ROLL_VERIFIER","tenantId":"pg.citya"},{"name":"OFFICER IN CHARGE","code":"OFFICER_IN_CHARGE","tenantId":"pg.citya"},{"name":"PROJECT CREATOR","code":"PROJECT_CREATOR","tenantId":"pg.citya"},{"name":"Employee Common","code":"EMPLOYEE_COMMON","tenantId":"pg.citya"},{"name":"BILL_VIEWER","code":"BILL_VIEWER","tenantId":"pg.citya"},{"name":"TECHNICAL SANCTIONER","code":"TECHNICAL_SANCTIONER","tenantId":"pg.citya"},{"name":"BILL_CREATOR","code":"BILL_CREATOR","tenantId":"pg.citya"},{"name":"MUSTER ROLL APPROVER","code":"MUSTER_ROLL_APPROVER","tenantId":"pg.citya"},{"name":"ESTIMATE VIEWER","code":"ESTIMATE_VIEWER","tenantId":"pg.citya"},{"name":"WORK ORDER APPROVER","code":"WORK_ORDER_APPROVER","tenantId":"pg.citya"},{"name":"ESTIMATE CREATOR","code":"ESTIMATE_CREATOR","tenantId":"pg.citya"},{"name":"State Dashboard Admin","code":"STADMIN","tenantId":"pg.citya"},{"name":"MUKTA Admin","code":"MUKTA_ADMIN","tenantId":"pg.citya"}],"active":true,"tenantId":"pg.citya","permanentCity":null},"msgId":"1693813698339|en_IN","plainAccessRequest":{}}}' \
  --compressed
```



### User Roles Required for JIT

Roles related to Bill accountant are used here:

| Role            | Role Code        | Access                                                                 |
| --------------- | ---------------- | ---------------------------------------------------------------------- |
| Bill Accountant | BILL\_ACCOUNTANT | Search PI Screen, View PI Screen, Retry and Generate Revised PI Access |
| Bill Creator    | BILL\_CREATOR    | Search PI Screen, View PI Screen, Retry and Generate Revised PI Access |
