# Organisation Search

#### Objective

The Mukta Organisation Search service is designed to provide secure and efficient access to organisation data. It incorporates configurable masking patterns and role-based policies to safeguard sensitive information while maintaining accessibility for authorized users.

PII data - Door No, Street Name, Locality, Mobile Number, Email ID, PAN, GSTIN.

The response would be based on the config set in MDMS-V2 which masks the above fields in appropriate patterns.

***

## Features

1. **Role-Based Attribute Access:**
   1. Ensures that only authorized roles can access sensitive organisation attributes in plain text.
   2. Configurable access levels for each role.
2. **Configurable Masking Patterns:**
   1. Provides flexibility to define masking patterns for each attribute.
   2. Masks sensitive information to ensure data security.
3. **Dynamic Masking Configurations:**
   1. Uses MDMS (Master Data Management System) for dynamic retrieval of masking configurations.

***

#### Input Request Example

```
{
  "RequestInfo": {
    "userInfo": {
      "roles": [
        { "code": "USER_ROLE" }
      ]
    }
  },
  "SearchCriteria": {
    "tenantId": "tenant123",
    "organisationId": "org123"
  }
}

```

#### Output Response Example

* For a user without plain text access:

```
{
  "organisations": [
    {
      "name": "O*** L***",
      "registrationNumber": "***789",
      "address": "1*** S**** St****",
      "geoLocation": {
        "boundaryCode": "CS_COMMON_UNDISCLOSED",
        "isWardMasked": true
      }
    }
  ],
  "TotalCount": 1
}

```

* For a user with plain text access:

```
{
  "organisations": [
    {
      "name": "Org Legal",
      "registrationNumber": "123789",
      "address": "123 Sample Street",
      "geoLocation": {
        "boundaryCode": "12345",
        "isWardMasked": false
      }
    }
  ],
  "TotalCount": 1
}

```

## Key Components

1. MDMS Configuration Fetching:
   1. Retrieves masking patterns and role-based policies dynamically from MDMS.
2. Role-Based Masking Logic:
   1. Determines access levels for each role.
   2. Applies appropriate masking patterns to sensitive attributes.
3. Dynamic Masking of Attributes:
   1. Masks attributes such as name, registration number, and address based on user roles and configurations.
   2. Handles complex paths within objects (e.g., geoLocation.boundaryCode).

***

#### Example Workflow

1. User Request:
   1. The user sends a search request with RequestInfo and SearchCriteria.
2. Configuration Fetching:
   1. Service fetches masking patterns and role-based policies from MDMS.
3. Attribute Masking:
   1. Attributes are masked/unmasked based on user roles and configurations.
4. Response Generation:
   1. Returns the masked/unmasked organisation data to the user.

Curl:

```
curl 'https://mukta-uat.digit.org/mukta-services/org-services/organisation/v1/_search' \
  -H 'authority: mukta-uat.digit.org' \
  -H 'accept: application/json, text/plain, */*' \
  -H 'accept-language: en-GB,en-US;q=0.9,en;q=0.8' \
  -H 'content-type: application/json' \
  -H 'cookie: _gid=GA1.2.1202491491.1734070501; _ga_V2CPZCVTXQ=GS1.1.1734070500.1.0.1734070502.0.0.0; _ga=GA1.1.829748430.1733136966; _ga_H9YC8FEN6F=GS1.1.1734069891.2.1.1734072381.60.0.0; _ga_3LN58ZJ5KN=GS1.1.1734072575.3.1.1734072581.54.0.0; _ga_4FTREDCS0G=GS1.1.1734072575.3.1.1734072581.0.0.0; _ga_FS7DJ7SGKL=GS1.1.1734072575.3.1.1734072581.0.0.0; _ga_EHCDQF6VQ5=GS1.1.1734072575.3.1.1734072581.0.0.0' \
  -H 'origin: https://mukta-uat.digit.org' \
  -H 'referer: https://mukta-uat.digit.org/works-ui/employee/masters/view-organization?tenantId=od.testing&orgId=ORG-001629' \
  -H 'sec-ch-ua: "Chromium";v="118", "Google Chrome";v="118", "Not=A?Brand";v="99"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Linux"' \
  -H 'sec-fetch-dest: empty' \
  -H 'sec-fetch-mode: cors' \
  -H 'sec-fetch-site: same-origin' \
  -H 'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36' \
  --data-raw '{"SearchCriteria":{"orgNumber":"ORG-001629","tenantId":"od.testing"},"RequestInfo":{"apiId":"Rainmaker","authToken":"fdaaf53d-b05d-4659-aa86-45f0ae305f40","userInfo":{"id":271,"uuid":"81b1ce2d-262d-4632-b2a3-3e8227769a11","userName":"MUKTAUAT","name":"MUKTAUAT","mobileNumber":"9036146615","emailId":"a@gmail.com","locale":null,"type":"EMPLOYEE","roles":[{"name":"Organization viewer","code":"ORG_VIEWER","tenantId":"od.testing"},{"name":"MB_VERIFIER","code":"MB_VERIFIER","tenantId":"od.testing"},{"name":"MB_VIEWER","code":"MB_VIEWER","tenantId":"od.testing"},{"name":"MDMS Admin","code":"MDMS_ADMIN","tenantId":"od.testing"},{"name":"MUKTA Admin","code":"MUKTA_ADMIN","tenantId":"od.testing"},{"name":"BILL_ACCOUNTANT","code":"BILL_ACCOUNTANT","tenantId":"od.testing"},{"name":"WORK_ORDER_VIEWER","code":"WORK_ORDER_VIEWER","tenantId":"od.testing"},{"name":"ESTIMATE VERIFIER","code":"ESTIMATE_VERIFIER","tenantId":"od.testing"},{"name":"ESTIMATE VIEWER","code":"ESTIMATE_VIEWER","tenantId":"od.testing"},{"name":"WORK ORDER APPROVER","code":"WORK_ORDER_APPROVER","tenantId":"od.testing"},{"name":"OFFICER IN CHARGE","code":"OFFICER_IN_CHARGE","tenantId":"od.testing"},{"name":"BILL_VIEWER","code":"BILL_VIEWER","tenantId":"od.testing"},{"name":"PROJECT VIEWER","code":"PROJECT_VIEWER","tenantId":"od.testing"},{"name":"BILL_APPROVER","code":"BILL_APPROVER","tenantId":"od.testing"},{"name":"MB_CREATOR","code":"MB_CREATOR","tenantId":"od.testing"},{"name":"MUSTER ROLL VERIFIER","code":"MUSTER_ROLL_VERIFIER","tenantId":"od.testing"},{"name":"ESTIMATE APPROVER","code":"ESTIMATE_APPROVER","tenantId":"od.testing"},{"name":"WORK ORDER CREATOR","code":"WORK_ORDER_CREATOR","tenantId":"od.testing"},{"name":"ESTIMATE CREATOR","code":"ESTIMATE_CREATOR","tenantId":"od.testing"},{"name":"Employee Common","code":"EMPLOYEE_COMMON","tenantId":"od.testing"},{"name":"MDMS STATE VIEW ADMIN","code":"MDMS_STATE_VIEW_ADMIN","tenantId":"od.testing"},{"name":"Localisation admin","code":"LOC_ADMIN","tenantId":"od.testing"},{"name":"TECHNICAL SANCTIONER","code":"TECHNICAL_SANCTIONER","tenantId":"od.testing"},{"name":"Dashboard Viewer","code":"DASHBOARD_VIEWER","tenantId":"od.testing"},{"name":"BILL_CREATOR","code":"BILL_CREATOR","tenantId":"od.testing"},{"name":"Revision of rates","code":"REVISION_OF_RATES","tenantId":"od.testing"},{"name":"BILL_VERIFIER","code":"BILL_VERIFIER","tenantId":"od.testing"},{"name":"MUSTER ROLL APPROVER","code":"MUSTER_ROLL_APPROVER","tenantId":"od.testing"},{"name":"MB_APPROVER","code":"MB_APPROVER","tenantId":"od.testing"},{"name":"MDMS CITY ADMIN","code":"MDMS_CITY_ADMIN","tenantId":"od.testing"},{"name":"PROJECT CREATOR","code":"PROJECT_CREATOR","tenantId":"od.testing"},{"name":"Employee Organization Admin","code":"EMP_ORG_ADMIN","tenantId":"od.testing"},{"name":"WORK ORDER VERIFIER","code":"WORK_ORDER_VERIFIER","tenantId":"od.testing"},{"name":"HRMS Admin","code":"HRMS_ADMIN","tenantId":"od.testing"}],"active":true,"tenantId":"od.testing","permanentCity":"Testing"},"msgId":"1734091873033|en_IN","plainAccessRequest":{}}}' \
  --compressed

```

