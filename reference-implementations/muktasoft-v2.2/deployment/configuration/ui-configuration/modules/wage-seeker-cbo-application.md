---
description: Wage seeker configuration details for CBO application
---

# Wage Seeker (CBO Application)

## Overview

This module helps create wage seekers.

ROLE: ORG\_ADMIN

This module contains 4 linked forms&#x20;

1. [Individual Details](wage-seeker-cbo-application.md#individual-details)
2. [Address Details](wage-seeker-cbo-application.md#address-details)
3. [Financial Details](wage-seeker-cbo-application.md#financial-details)
4. Summary Details

## **Individual Details**

<table><thead><tr><th width="112">S.No.</th><th>Fields</th><th>Mandatory</th><th>Validations</th></tr></thead><tbody><tr><td>1.</td><td>Aadhaar No.</td><td>Yes</td><td><p>Input: [0-9]</p><p>Max Length: 12</p><p>Min Length: 12</p></td></tr><tr><td>2.</td><td>Name</td><td>Yes</td><td><p>Input: [A-Za-z ]</p><p>Max Length: 128</p><p>Min Length: 2</p></td></tr><tr><td>3.</td><td>Guardian Name</td><td>Yes</td><td><p>Input: [A-Za-z ]</p><p>Max Length: 128</p><p>Min Length: 2</p></td></tr><tr><td>4.</td><td>Relationship</td><td>Yes</td><td>None</td></tr><tr><td>5.</td><td>Date of Birth</td><td>Yes</td><td>Age shouldn’t be less than 18 years</td></tr><tr><td>6.</td><td>Gender</td><td>Yes</td><td>None</td></tr><tr><td>7.</td><td>Social Category</td><td>No</td><td>None</td></tr><tr><td>8.</td><td>Mobile Number</td><td>Yes</td><td><p>Input: [0-9]</p><p>Max Length: 10</p><p>Min Length: 10</p></td></tr><tr><td>9.</td><td>Skills</td><td>Yes</td><td>Can not select multiple skills of same sub skill type</td></tr><tr><td>10.</td><td>Photograph</td><td>No</td><td>File size should be &#x3C; 5 MB</td></tr></tbody></table>

## Address Details

<table><thead><tr><th width="94">S.No. </th><th>Fields</th><th>Mandatory</th><th>Validations</th></tr></thead><tbody><tr><td>1.</td><td>Pincode</td><td>No</td><td><p>Input: [0-9]</p><p>Max Length: 6</p><p>Min Length: 6</p></td></tr><tr><td>2.</td><td>City</td><td>Yes</td><td>None</td></tr><tr><td>3.</td><td>Ward</td><td>Yes</td><td>None</td></tr><tr><td>4.</td><td>Locality</td><td>Yes</td><td>None</td></tr><tr><td>5.</td><td>Street Name</td><td>No</td><td><p><br></p><p>Max Length: 64</p><p>Min Length: 0</p></td></tr><tr><td>6.</td><td>Door No</td><td>No</td><td><p>Max Length: 64</p><p>Min Length: 0</p></td></tr></tbody></table>

## Financial Details

<table><thead><tr><th width="85">S.No. </th><th>Fields</th><th>Mandatory</th><th>Validations</th></tr></thead><tbody><tr><td>1.</td><td>Account Holder’s Name</td><td>Yes</td><td><p>Input: [A-Za-z ]</p><p>Max Length: 128</p><p>Min Length: 2</p></td></tr><tr><td>2.</td><td>Account Number</td><td>Yes</td><td><p>Input: [0-9]</p><p>Max Length: 18</p><p>Min Length: 9</p></td></tr><tr><td>3.</td><td>Re-enter Account Number </td><td>Yes</td><td><p>Input: [0-9]</p><p>Max Length: 18</p><p>Min Length: 9</p></td></tr><tr><td>4.</td><td>Account Type</td><td>Yes</td><td>None</td></tr><tr><td>5.</td><td>IFSC Code</td><td>Yes</td><td><p>Valid IFSC Code</p><p>Verified through</p><p>https://ifsc.razorpay.com</p><p><br></p></td></tr></tbody></table>

This form combines the details of the previous 3 forms, and users can tap on the respective edit icon to edit the particular details.

On submit, the wage seeker is created successfully.

## **API Details**

<table><thead><tr><th width="91">S. no.</th><th width="108">API</th><th width="363">Body/Query Params</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td><p>'individual/v1/_create'</p><p><br><br><br></p></td><td><p>"Individual": {</p><p> "tenantId":,</p><p> "name": {"givenName": ,</p><p> "dateOfBirth":,</p><p> "gender":,</p><p> "mobileNumber":,</p><p> "address": [</p><p>   {</p><p>     "tenantId":,</p><p>     "pincode":,</p><p>     "street":,</p><p>     "doorNo":,</p><p>     "type": "PERMANENT",</p><p>     "locality": {"code":</p><p> ],</p><p> "fatherName":,</p><p> "husbandName":,</p><p> "relationship":,</p><p> "identifiers": [</p><p>   {</p><p>     "identifierType": "AADHAAR",</p><p>     "identifierId":</p><p>   }</p><p> ],</p><p> "skills": [],</p><p> "photo":,</p><p> "additionalFields": {</p><p>   "fields": [</p><p>     {</p><p>       "key": "SOCIAL_CATEGORY",</p><p>       "value":</p><p>     }</p><p>   ]</p><p> }</p><p>}</p><p><br><br></p></td><td><p>Create Individual/Wage seekers</p><p><br></p></td></tr><tr><td>2</td><td><p>'/bankaccount-service/bankaccount/v1/_create'</p><p><br><br><br></p></td><td><p> </p><p>"bankAccounts": [</p><p> {</p><p>   "tenantId":,</p><p>   "serviceCode": "IND",</p><p>   "referenceId": individualId,</p><p>   "bankAccountDetails": [</p><p>     {</p><p>       "tenantId":,</p><p>       "accountHolderName":</p><p>"accountNumber":,</p><p>       "accountType":,</p><p>       "isPrimary",</p><p>       "bankBranchIdentifier": {</p><p>         "type": "IFSC",</p><p>         "code":,</p><p>         "additionalDetails": {"ifsccode"}</p><p>       },</p><p>       "isActive": true,</p><p>       "documents": [],</p><p>     }</p><p>   ],</p><p> }</p><p>]</p><p><br><br><br></p></td><td>To create the bank details for the individual that was created</td></tr></tbody></table>
