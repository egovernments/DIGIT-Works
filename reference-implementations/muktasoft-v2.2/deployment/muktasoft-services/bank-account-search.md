# Bank Account Search

#### Objective:

To implement a secure bank account search service in Mukta that masks Personally Identifiable Information (PII) in the response based on the user’s role and predefined security policies.\
\
PII data - Branch name, Account number, IFSC

The response would be based on the config set in MDMS-V2 which masks the above fields in appropriate patterns.

***

## Key Components

**1. Masking Methodology**

* Masking is applied to sensitive attributes such as accountHolderName, accountNumber, and ifscCode.
* A regular expression-based masking technique is used, replacing parts of the sensitive values with asterisks (\*).
* Example: For a pattern - (?=.{4}), the value 12345678 would be masked as \*\*\*\*5678.

**2. Security Policy:**

* Role-Based Access Control (RBAC):
  * Defines which roles can access attributes in plain text.
  * Example roles: BILL\_ACCOUNTANT, MUKTA\_ADMIN.
* Attribute Visibility Levels:
  * PLAIN: The attribute is fully visible.
  * MASKED: Attribute is partially masked.

**Policy Configuration Example:**

```
{
  "model": "BankAccountSearch",
  "attributes": [
    {
      "name": "accountHolderName",
      "jsonPath": "accountHolderName",
      "patternId": "001",
      "defaultVisibility": "MASKED"
    },
    {
      "name": "accountNumber",
      "jsonPath": "accountNumber",
      "patternId": "001",
      "defaultVisibility": "MASKED"
    },
    {
      "name": "ifscCode",
      "jsonPath": "bankBranchIdentifier/code",
      "patternId": "009",
      "defaultVisibility": "MASKED"
    }
  ],
  "roleBasedDecryptionPolicy": [
    {
      "roles": ["BILL_ACCOUNTANT", "MUKTA_ADMIN"],
      "attributeAccessList": [
        {
          "attribute": "accountHolderName",
          "firstLevelVisibility": "PLAIN",
          "secondLevelVisibility": "PLAIN"
        },
        {
          "attribute": "accountNumber",
          "firstLevelVisibility": "PLAIN",
          "secondLevelVisibility": "PLAIN"
        },
        {
          "attribute": "ifscCode",
          "firstLevelVisibility": "PLAIN",
          "secondLevelVisibility": "PLAIN"
        }
      ]
    }
  ]
}

```

**3. Masking Pattern Configuration:**

* Patterns define how sensitive attributes are masked.

**Example configuration:**

```
[
  {
    "patternId": "001",
    "pattern": ".(?=.{4})"
  },
  {
    "patternId": "009",
    "pattern": "(?<=.{4}).(?=.{2})"
  }]

```

**Pattern Example:**

* Pattern.(?=.{4}): Masks all characters except the last 4.
* Pattern (?<=.{4}).(?=.{2}): Masks characters except the first 4 and last 2.

**4. Implementation Details:**

* The service retrieves security policies and masking patterns from MDMS.
* User roles are validated to determine access levels for each attribute.
* Masking is applied dynamically to sensitive data if the user’s role lacks plain text access.

**5. Code Highlights:**

* Role Access Check: Ensures attributes are visible in plain text only if the user’s role has access.
* Masking Application: Masks attribute values using patterns fetched from the configuration.
* Nested JSON Handling: Retrieves and updates attribute values dynamically using JSON paths

**Curl:**

```
curl 'https://mukta-uat.digit.org/mukta-services/bankaccount/v1/_search?_=1734091757871' \
  -H 'authority: mukta-uat.digit.org' \
  -H 'accept: application/json, text/plain, */*' \
  -H 'accept-language: en-GB,en-US;q=0.9,en;q=0.8' \
  -H 'content-type: application/json' \
  -H 'cookie: _gid=GA1.2.1202491491.1734070501; _ga_V2CPZCVTXQ=GS1.1.1734070500.1.0.1734070502.0.0.0; _ga=GA1.1.829748430.1733136966; _ga_H9YC8FEN6F=GS1.1.1734069891.2.1.1734072381.60.0.0; _ga_3LN58ZJ5KN=GS1.1.1734072575.3.1.1734072581.54.0.0; _ga_4FTREDCS0G=GS1.1.1734072575.3.1.1734072581.0.0.0; _ga_FS7DJ7SGKL=GS1.1.1734072575.3.1.1734072581.0.0.0; _ga_EHCDQF6VQ5=GS1.1.1734072575.3.1.1734072581.0.0.0' \
  -H 'origin: https://mukta-uat.digit.org' \
  -H 'referer: https://mukta-uat.digit.org/works-ui/employee/masters/view-wageseeker?tenantId=od.testing&individualId=IND-2024-12-06-004156' \
  -H 'sec-ch-ua: "Chromium";v="118", "Google Chrome";v="118", "Not=A?Brand";v="99"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Linux"' \
  -H 'sec-fetch-dest: empty' \
  -H 'sec-fetch-mode: cors' \
  -H 'sec-fetch-site: same-origin' \
  -H 'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36' \
  --data-raw '{"bankAccountDetails":{"tenantId":"od.testing","serviceCode":"IND","referenceId":["7e302cf3-6eb4-4392-89e7-42ab75b13672"]},"RequestInfo":{"apiId":"Rainmaker","authToken":"fdaaf53d-b05d-4659-aa86-45f0ae305f40","userInfo":{"id":271,"uuid":"81b1ce2d-262d-4632-b2a3-3e8227769a11","userName":"MUKTAUAT","name":"MUKTAUAT","mobileNumber":"9036146615","emailId":"a@gmail.com","locale":null,"type":"EMPLOYEE","roles":[{"name":"Organization viewer","code":"ORG_VIEWER","tenantId":"od.testing"},{"name":"MB_VERIFIER","code":"MB_VERIFIER","tenantId":"od.testing"},{"name":"MB_VIEWER","code":"MB_VIEWER","tenantId":"od.testing"},{"name":"MDMS Admin","code":"MDMS_ADMIN","tenantId":"od.testing"},{"name":"MUKTA Admin","code":"MUKTA_ADMIN","tenantId":"od.testing"},{"name":"BILL_ACCOUNTANT","code":"BILL_ACCOUNTANT","tenantId":"od.testing"},{"name":"WORK_ORDER_VIEWER","code":"WORK_ORDER_VIEWER","tenantId":"od.testing"},{"name":"ESTIMATE VERIFIER","code":"ESTIMATE_VERIFIER","tenantId":"od.testing"},{"name":"ESTIMATE VIEWER","code":"ESTIMATE_VIEWER","tenantId":"od.testing"},{"name":"WORK ORDER APPROVER","code":"WORK_ORDER_APPROVER","tenantId":"od.testing"},{"name":"OFFICER IN CHARGE","code":"OFFICER_IN_CHARGE","tenantId":"od.testing"},{"name":"BILL_VIEWER","code":"BILL_VIEWER","tenantId":"od.testing"},{"name":"PROJECT VIEWER","code":"PROJECT_VIEWER","tenantId":"od.testing"},{"name":"BILL_APPROVER","code":"BILL_APPROVER","tenantId":"od.testing"},{"name":"MB_CREATOR","code":"MB_CREATOR","tenantId":"od.testing"},{"name":"MUSTER ROLL VERIFIER","code":"MUSTER_ROLL_VERIFIER","tenantId":"od.testing"},{"name":"ESTIMATE APPROVER","code":"ESTIMATE_APPROVER","tenantId":"od.testing"},{"name":"WORK ORDER CREATOR","code":"WORK_ORDER_CREATOR","tenantId":"od.testing"},{"name":"ESTIMATE CREATOR","code":"ESTIMATE_CREATOR","tenantId":"od.testing"},{"name":"Employee Common","code":"EMPLOYEE_COMMON","tenantId":"od.testing"},{"name":"MDMS STATE VIEW ADMIN","code":"MDMS_STATE_VIEW_ADMIN","tenantId":"od.testing"},{"name":"Localisation admin","code":"LOC_ADMIN","tenantId":"od.testing"},{"name":"TECHNICAL SANCTIONER","code":"TECHNICAL_SANCTIONER","tenantId":"od.testing"},{"name":"Dashboard Viewer","code":"DASHBOARD_VIEWER","tenantId":"od.testing"},{"name":"BILL_CREATOR","code":"BILL_CREATOR","tenantId":"od.testing"},{"name":"Revision of rates","code":"REVISION_OF_RATES","tenantId":"od.testing"},{"name":"BILL_VERIFIER","code":"BILL_VERIFIER","tenantId":"od.testing"},{"name":"MUSTER ROLL APPROVER","code":"MUSTER_ROLL_APPROVER","tenantId":"od.testing"},{"name":"MB_APPROVER","code":"MB_APPROVER","tenantId":"od.testing"},{"name":"MDMS CITY ADMIN","code":"MDMS_CITY_ADMIN","tenantId":"od.testing"},{"name":"PROJECT CREATOR","code":"PROJECT_CREATOR","tenantId":"od.testing"},{"name":"Employee Organization Admin","code":"EMP_ORG_ADMIN","tenantId":"od.testing"},{"name":"WORK ORDER VERIFIER","code":"WORK_ORDER_VERIFIER","tenantId":"od.testing"},{"name":"HRMS Admin","code":"HRMS_ADMIN","tenantId":"od.testing"}],"active":true,"tenantId":"od.testing","permanentCity":"Testing"},"msgId":"1734091757871|en_IN","plainAccessRequest":{}}}' \
 --compressed
```

