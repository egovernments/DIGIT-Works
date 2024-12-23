# Individual Search

#### Objective

The Mukta Individual Search service is designed to retrieve and handle individual-related data securely. The service masks personally identifiable information (PII) based on configurable patterns and enforces role-based access control to ensure sensitive data is visible only to authorized users.

PII data - Name, Identity number, Father/ husband name, Relationship, Address, Date of birth, Gender, Mobile Number, Social category, Email ID, Photo.

The response would be based on the config set in MDMS-V2 which masks the above fields in appropriate patterns.

***

## Masking Methodology

1. **Masking Patterns:**
   1. Configured in the DataSecurity module.
   2. Patterns define the masking logic for attributes, e.g.,
      1. 001: .\\\*(?=.{4}) masks all but the last 4 characters.
      2. 009: (?<=.{4}).(?=.{2}) masks characters except the first 4 and last 2.
2. **Role-Based Security Policies:**
   1. Defined in the MaskingUIConfig module.
   2. Specifies the visibility of attributes based on user roles.
   3. Attributes can have PLAIN or MASKED visibility.
3. **Implementation:**
   1. Attributes are extracted from the response using JSON paths.
   2. If a user's role does not permit plain text access, the attribute is masked based on the configured pattern.

***

#### Security Policy Example

```
{
  "model": "IndividualSearch",
  "attributes": [
    { "name": "fullName", "jsonPath": "fullName", "patternId": "001", "defaultVisibility": "MASKED" },
    { "name": "phoneNumber", "jsonPath": "contact/phone", "patternId": "001", "defaultVisibility": "MASKED" },
    { "name": "address", "jsonPath": "address", "patternId": "009", "defaultVisibility": "MASKED" }
  ],
  "roleBasedDecryptionPolicy": [
    {
      "roles": ["ADMIN", "SUPERVISOR"],
      "attributeAccessList": [
        { "attribute": "fullName", "firstLevelVisibility": "PLAIN", "secondLevelVisibility": "PLAIN" },
        { "attribute": "phoneNumber", "firstLevelVisibility": "PLAIN", "secondLevelVisibility": "PLAIN" },
        { "attribute": "address", "firstLevelVisibility": "PLAIN", "secondLevelVisibility": "PLAIN" }
      ]
    }
  ]
}

```

Curl:

```
curl 'https://mukta-uat.digit.org/mukta-services/individual/v1/_search?tenantId=od.testing&offset=0&limit=100&_=1734091757683' \
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
  --data-raw '{"Individual":{"individualId":["IND-2024-12-06-004156"]},"RequestInfo":{"apiId":"Rainmaker","authToken":"fdaaf53d-b05d-4659-aa86-45f0ae305f40","userInfo":{"id":271,"uuid":"81b1ce2d-262d-4632-b2a3-3e8227769a11","userName":"MUKTAUAT","name":"MUKTAUAT","mobileNumber":"9036146615","emailId":"a@gmail.com","locale":null,"type":"EMPLOYEE","roles":[{"name":"Organization viewer","code":"ORG_VIEWER","tenantId":"od.testing"},{"name":"MB_VERIFIER","code":"MB_VERIFIER","tenantId":"od.testing"},{"name":"MB_VIEWER","code":"MB_VIEWER","tenantId":"od.testing"},{"name":"MDMS Admin","code":"MDMS_ADMIN","tenantId":"od.testing"},{"name":"MUKTA Admin","code":"MUKTA_ADMIN","tenantId":"od.testing"},{"name":"BILL_ACCOUNTANT","code":"BILL_ACCOUNTANT","tenantId":"od.testing"},{"name":"WORK_ORDER_VIEWER","code":"WORK_ORDER_VIEWER","tenantId":"od.testing"},{"name":"ESTIMATE VERIFIER","code":"ESTIMATE_VERIFIER","tenantId":"od.testing"},{"name":"ESTIMATE VIEWER","code":"ESTIMATE_VIEWER","tenantId":"od.testing"},{"name":"WORK ORDER APPROVER","code":"WORK_ORDER_APPROVER","tenantId":"od.testing"},{"name":"OFFICER IN CHARGE","code":"OFFICER_IN_CHARGE","tenantId":"od.testing"},{"name":"BILL_VIEWER","code":"BILL_VIEWER","tenantId":"od.testing"},{"name":"PROJECT VIEWER","code":"PROJECT_VIEWER","tenantId":"od.testing"},{"name":"BILL_APPROVER","code":"BILL_APPROVER","tenantId":"od.testing"},{"name":"MB_CREATOR","code":"MB_CREATOR","tenantId":"od.testing"},{"name":"MUSTER ROLL VERIFIER","code":"MUSTER_ROLL_VERIFIER","tenantId":"od.testing"},{"name":"ESTIMATE APPROVER","code":"ESTIMATE_APPROVER","tenantId":"od.testing"},{"name":"WORK ORDER CREATOR","code":"WORK_ORDER_CREATOR","tenantId":"od.testing"},{"name":"ESTIMATE CREATOR","code":"ESTIMATE_CREATOR","tenantId":"od.testing"},{"name":"Employee Common","code":"EMPLOYEE_COMMON","tenantId":"od.testing"},{"name":"MDMS STATE VIEW ADMIN","code":"MDMS_STATE_VIEW_ADMIN","tenantId":"od.testing"},{"name":"Localisation admin","code":"LOC_ADMIN","tenantId":"od.testing"},{"name":"TECHNICAL SANCTIONER","code":"TECHNICAL_SANCTIONER","tenantId":"od.testing"},{"name":"Dashboard Viewer","code":"DASHBOARD_VIEWER","tenantId":"od.testing"},{"name":"BILL_CREATOR","code":"BILL_CREATOR","tenantId":"od.testing"},{"name":"Revision of rates","code":"REVISION_OF_RATES","tenantId":"od.testing"},{"name":"BILL_VERIFIER","code":"BILL_VERIFIER","tenantId":"od.testing"},{"name":"MUSTER ROLL APPROVER","code":"MUSTER_ROLL_APPROVER","tenantId":"od.testing"},{"name":"MB_APPROVER","code":"MB_APPROVER","tenantId":"od.testing"},{"name":"MDMS CITY ADMIN","code":"MDMS_CITY_ADMIN","tenantId":"od.testing"},{"name":"PROJECT CREATOR","code":"PROJECT_CREATOR","tenantId":"od.testing"},{"name":"Employee Organization Admin","code":"EMP_ORG_ADMIN","tenantId":"od.testing"},{"name":"WORK ORDER VERIFIER","code":"WORK_ORDER_VERIFIER","tenantId":"od.testing"},{"name":"HRMS Admin","code":"HRMS_ADMIN","tenantId":"od.testing"}],"active":true,"tenantId":"od.testing","permanentCity":"Testing"},"msgId":"1734091757683|en_IN","plainAccessRequest":{}}}' \
  --compressed
```
