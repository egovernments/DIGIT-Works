# Estimate Template

## Overview

Requirement of creating an estimate template that can be used to create an estimate, This template has SOR and NON-SOR line items, which will be copied while creating an estimate.

**Why Choose MDMS V2?**

According to the requirement, the Estimate service has no dependency on other services. The only dependency is with SOR which is a part of MDMS-V2. It stores the data and for that the MDMS service is sufficient. We finalized this approach to reduce the overhead of creating and maintaining a new service.&#x20;

**Figma Design**

<figure><img src="../../../.gitbook/assets/estimate-template (2).png" alt=""><figcaption></figcaption></figure>

## MDMS Schema&#x20;

```
{
           "id": "2e42e1fc-fa18-459f-bdc4-5dfcfc1b0b10",
           "tenantId": "pg",
           "code": "WORKS.EstimateTemplateTest",
           "description": "WORKS.EstimateTemplateTest",
           "definition": {
               "type": "object",
               "title": "Generated schema for Estimate Template",
               "$schema": "http://json-schema.org/draft-07/schema#",
               "required": [
                   "templateId",
                   "templateName",
                   "projectType",
                   "projectSubType",
                   "lineItems"
               ],
               "x-unique": [
                   "templateId"
               ],
               "properties": {
                   "lineItems": {
                       "type": "array",
                       "items": {
                           "type": "object",
                           "required": [
                               "lineItemType"
                           ],
                           "properties": {
                               "uom": {
                                   "type": "string"
                               },
                               "sorCode": {
                                   "type": "string"
                               },
                               "description": {
                                   "type": "string"
                               },
                               "lineItemType": {
                                   "type": "string",
                                   "default": "LIT"
                               }
                           },
                           "additionalProperties": false
                       }
                   },
                   "templateId": {
                       "type": "string",
                       "default": "TMP"
                   },
                   "projectType": {
                       "type": "string"
                   },
                   "templateName": {
                       "type": "string"
                   },
                   "projectSubType": {
                       "type": "string"
                   },
                   "templateDescription": {
                       "type": "string"
                   }
               },
               "x-ui-schema": {
                   "ui:order": [
                       "templateId",
                       "templateName",
                       "templateDescription",
                       "projectType",
                       "projectSubType",
                       "lineItems"
                   ],
                   "templateId": {
                       "format": [
                           "preprocess"
                       ],
                       "formatType": "autogenerate",
                       "ui:readonly": true,
                       "autogenerate": "works.template.number"
                   }
               },
               "x-ref-schema": [
                   {
                       "fieldPath": "lineItems.*.sorCode",
                       "schemaCode": "WORKS-SOR.SOR"
                   },
                   {
                       "fieldPath": "lineItems.*.uom",
                       "schemaCode": "common-masters.UOM"
                   }
               ]
           },
           "isActive": true,
           "auditDetails": {
               "createdBy": null,
               "lastModifiedBy": null,
               "createdTime": 1702025336295,
               "lastModifiedTime": 1702025336295
           }
}
```

#### &#x20; 
