# Document Upload Container

## Overview

This component is used to render file upload UI based on the configuration passed for each module.&#x20;

## Workflow Details

[Upload File Component](https://github.com/egovernments/DIGIT-Works/blob/develop/frontend/micro-ui/web/micro-ui-internals/packages/react-components/src/hoc/UploadFileComposer.js) - It has a title, information banner with a relevant message, document name, and corresponding file input to upload doc. All these details can be configured via MDMS.

## MDMS Configurations

This component fetches below MDMS configuration by filtering modules to render the relevant UI.&#x20;

[File Upload Config](https://github.com/egovernments/works-mdms-data/blob/DEV/data/pg/works/DocumentConfig.json)

### Using The Component

1. If a new module is being added then the corresponding config should be updated in MDMS before using this component in the below format. (refer to Project module config below)

```
{
"module": "Project",
            "allowedFileTypes": ["pdf","doc","docx","xlsx","xls","jpeg","jpg","png"],
            "maxSizeInMB": 5,
            "bannerLabel": "PROJECT_BANNER",
           "documents": [
        {
          "code": "PROJECT_PROPOSAL",
          "name" : "noSubProject_doc_project_proposal",
          "active": true,
          "isMandatory": false,
          "showTextInput" : false,
          "allowedFileTypes": ["pdf","doc","docx","xlsx","xls","jpeg","jpg","png"],
          "maxSizeInMB": 5,
          "maxFilesAllowed": 1
        }, {}, …
]
}
```

2. To show this in form, the config with type ‘documentUpload’ needs to be passed to FormComposer. (refer to below config)

```
{
              "type": "documentUpload",
              "withoutLabel": true,
              "module": "Project",
              "error": "WORKS_REQUIRED_ERR",
              "name": "noSubProject_docs",
              "customClass": "",
              "localePrefix": "PROJECT"
            }
```

3. Once these two configurations are added, the upload component will become visible within the form. Upon clicking the submit button, it will capture the data in the format specified below, which can then be modified in accordance with the API contract for each module.

```
keyName: {
    "noSubProject_doc_others_name": "Other file",
    "noSubProject_doc_project_proposal": [
        [
            "1.5mb.pdf",
            {
                "file": {},
                "fileStoreId": {
                    "fileStoreId": "891f3c71-6e31-4111-b08a-867ce7753ce0",
                    "tenantId": "pg.citya"
                }
            }
        ]
    ],
    "noSubProject_doc_finalized_worklist": [
        [
            "test.jpg",
            {
                "file": {},
                "fileStoreId": {
                    "fileStoreId": "9ea3e0f9-f5ea-4a5c-968f-ca2ec73690b0",
                    "tenantId": "pg.citya"
                }
            }
        ]
    ],
    "noSubProject_doc_feasibility_analysis": [
        [
            "test.png",
            {
                "file": {},
                "fileStoreId": {
                    "fileStoreId": "2df09fb4-8a01-466b-816c-0524393494ec",
                    "tenantId": "pg.citya"
                }
            }
        ]
    ],
    "noSubProject_doc_others": [
        [
            "1.5mb.pdf",
            {
                "file": {},
                "fileStoreId": {
                    "fileStoreId": "4ab45ddd-f1b0-486a-8a70-f20471691b5a",
                    "tenantId": "pg.citya"
                }
            }
        ]
    ]
}
```

