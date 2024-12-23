# SORs and Rates

### Overview

This module provides the ability to create SORs and Rates\


This module has 5 associated screens :&#x20;

1. Create
2. Edit
3. View
4. Search

## Workbench

The SORs and some related masters were created using the workbench and MDMS v2.

Refer to the Workbench [Release document ](https://workbench.digit.org/platform/release-notes/service-build-updates)to enable the Workbench \


## SOR Schemas

SOR

```
{
  "id": "fd565798-c000-4053-b5f0-4ef5af179934",
  "tenantId": "pg",
  "code": "WORKS-SOR.SOR",
  "description": "WORKS-SOR.SOR",
  "definition": {
    "type": "object",
    "title": "WORKS_SOR.SOR",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "id",
      "sorType",
      "uom",
      "quantity"
    ],
    "x-unique": [
      "id"
    ],
    "properties": {
      "id": {
        "type": "string"
      },
      "uom": {
        "type": "string"
      },
      "sorType": {
        "type": "string"
      },
      "quantity": {
        "type": "number"
      },
      "sorSubType": {
        "type": "string",
        "default": "NA"
      },
      "sorVariant": {
        "type": "string",
        "default": "NA"
      },
      "description": {
        "type": "string"
      }
    },
    "x-ui-schema": {
      "ui:order": [
        "sorType",
        "sorSubType",
        "sorVariant",
        "uom",
        "quantity",
        "description",
        "*"
      ],
      "description": {
        "ui:widget": "textarea"
      },
      "ui-autogenerate": {
        "id": "works.sor.number"
      }
    },
    "x-ref-schema": [
      {
        "fieldPath": "sorType",
        "schemaCode": "WORKS-SOR.Type"
      },
      {
        "fieldPath": "sorSubType",
        "schemaCode": "WORKS-SOR.SubType"
      },
      {
        "fieldPath": "sorVariant",
        "schemaCode": "WORKS-SOR.Variant"
      },
      {
        "fieldPath": "uom",
        "schemaCode": "common-masters.UOM"
      }
    ]
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1695898033182,
    "lastModifiedTime": 1695898033182
  }
}

```

SOR Type

```
{
  "id": "9da79f30-0d7f-4ec8-927a-7151f803fcd5",
  "tenantId": "pg",
  "code": "WORKS-SOR.Type",
  "description": "WORKS-SOR.Type",
  "definition": {
    "type": "object",
    "title": "WORKS-SOR.Type",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "code",
      "active"
    ],
    "x-unique": [
      "code"
    ],
    "properties": {
      "code": {
        "type": "string"
      },
      "active": {
        "type": "boolean"
      },
      "description": {
        "type": "string"
      }
    }
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1695897163197,
    "lastModifiedTime": 1695897163197
  }
}
```

SOR Subtype

```
{
  "id": "6d4b30f2-b489-4f8b-bb02-6b3014750e18",
  "tenantId": "pg",
  "code": "WORKS-SOR.SubType",
  "description": "WORKS-SOR.SubType",
  "definition": {
    "type": "object",
    "title": "WORKS-SOR.SubType",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "code",
      "active",
      "type"
    ],
    "x-unique": [
      "code"
    ],
    "properties": {
      "code": {
        "type": "string"
      },
      "type": {
        "type": "string"
      },
      "active": {
        "type": "boolean"
      },
      "description": {
        "type": "string"
      }
    },
    "x-ref-schema": [
      {
        "fieldPath": "type",
        "schemaCode": "WORKS-SOR.Type"
      }
    ]
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1695897222499,
    "lastModifiedTime": 1695897222499
  }
}
```

SOR Variant

```
{
  "id": "6ea9de1f-f924-4133-8689-1571258b57fe",
  "tenantId": "pg",
  "code": "WORKS-SOR.Variant",
  "description": "WORKS-SOR.Variant",
  "definition": {
    "type": "object",
    "title": "WORKS-SOR.Variant",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "code",
      "active"
    ],
    "x-unique": [
      "code"
    ],
    "properties": {
      "code": {
        "type": "string"
      },
      "active": {
        "type": "boolean"
      },
      "description": {
        "type": "string"
      }
    }
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1695897273559,
    "lastModifiedTime": 1695897273559
  }
}
```

UOM

```
{
  "id": "dc4eee9c-28a0-4c99-b113-d1695d1796d6",
  "tenantId": "pg",
  "code": "common-masters.UOM",
  "description": "common-masters.UOM",
  "definition": {
    "type": "object",
    "title": "common-masters.UOM",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "code"
    ],
    "x-unique": [
      "code"
    ],
    "properties": {
      "code": {
        "type": "string",
        "pattern": "^[A-Za-z]+$"
      },
      "baseUom": {
        "type": "boolean"
      },
      "description": {
        "type": "string",
        "pattern": "^[A-Za-z]+$"
      },
      "uomCategory": {
        "type": "string"
      },
      "conversionFactor": {
        "type": "number",
        "minLength": 2
      }
    },
    "x-ref-schema": []
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1692771724255,
    "lastModifiedTime": 1692771724255
  }
}

```

Rates

```
{
  "id": "21d86249-65ec-4104-9b8f-ce9deb5ad235",
  "tenantId": "pg",
  "code": "WORKS-SOR.Rates",
  "description": "WORKS-SOR.Rates",
  "definition": {
    "type": "object",
    "title": "WORKS-SOR.Rates",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "sorId",
      "rate",
      "validFrom",
      "amountDetails"
    ],
    "x-unique": [
      "sorId",
      "validFrom"
    ],
    "properties": {
      "rate": {
        "type": "number"
      },
      "sorId": {
        "type": "string"
      },
      "validTo": {
        "type": "string"
      },
      "validFrom": {
        "type": "string"
      },
      "amountDetails": {
        "type": "array",
        "items": {
          "type": "object",
          "required": [
            "type",
            "heads",
            "amount"
          ],
          "properties": {
            "type": {
              "enum": [
                "percentage",
                "fixed"
              ],
              "type": "string"
            },
            "heads": {
              "type": "string"
            },
            "amount": {
              "type": "number"
            }
          }
        }
      }
    },
    "x-ui-schema": {
      "ui:order": [
        "sorId",
        "validFrom",
        "validTo",
        "rate",
        "amountDetails",
        "*"
      ]
    },
    "x-ref-schema": [
      {
        "fieldPath": "sorId",
        "schemaCode": "WORKS-SOR.SOR"
      },
      {
        "fieldPath": "amountDetails.*.heads",
        "schemaCode": "WORKS-SOR.Overhead"
      }
    ]
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1696330283758,
    "lastModifiedTime": 1696330283758
  }
}
```

Overheads

```
{
  "id": "13f4ee64-0d83-4ff8-9cd2-c166fc410405",
  "tenantId": "pg",
  "code": "WORKS-SOR.Overhead",
  "description": "WORKS-SOR.Overhead",
  "definition": {
    "type": "object",
    "title": "WORKS-SOR.Overhead",
    "$schema": "http://json-schema.org/draft-07/schema#",
    "required": [
      "code",
      "active",
      "validFrom",
      "id",
      "description"
    ],
    "x-unique": [
      "code",
      "id"
    ],
    "properties": {
      "id": {
        "type": "string"
      },
      "code": {
        "type": "string"
      },
      "type": {
        "enum": [
          "percentage",
          "lumpsum"
        ],
        "type": "string"
      },
      "value": {
        "type": "string"
      },
      "active": {
        "type": "boolean"
      },
      "validTo": {
        "type": "string",
        "format": "date-time"
      },
      "validFrom": {
        "type": "string",
        "format": "date-time"
      },
      "description": {
        "type": "string"
      }
    }
  },
  "isActive": true,
  "auditDetails": {
    "createdBy": null,
    "lastModifiedBy": null,
    "createdTime": 1695898168948,
    "lastModifiedTime": 1695898168948
  }
}
```

MDMS Role Action Configs

{% embed url="https://github.com/egovernments/egov-mdms-data/commit/845fc1357b44eb2ba90c415c08e217a293eb1f58" %}

Sample data can be referred from the below sheet

{% embed url="https://docs.google.com/spreadsheets/d/1GN41IYq2B56VANxYqU3x_up0ejR9XxKhpw76wZs7myI/edit" %}
sor data
{% endembed %}
