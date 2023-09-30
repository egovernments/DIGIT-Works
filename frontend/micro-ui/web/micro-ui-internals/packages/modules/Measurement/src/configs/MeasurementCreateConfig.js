
export const CreateConfig = ({ defaultValue }) => {

  return {
    CreateConfig: [
      {
        defaultValues: defaultValue, // Use the default values here

        form: [

          {
            head: "MB_SORS",
            subHead: "",
            body: [
              {
                type: "component",
                component: "NewMeasureTable",
                withoutLabel: true,
                key: "SOR",
              },
            ],
          },
          {
            head: "MB_NONSOR",
            subHead: "",
            body: [

              {
                type: "component",
                component: "MeasureTable",
                withoutLabel: true,
                key: "NONSOR",
              },
            ],
          },
          {
            head: "",
            subHead: "",
            body: [

              {
                type: "component",
                component: "ViewOnlyCard",
                withoutLabel: true,
                key: "viewAmount",
              },
            ],
          },
          {

            // "navLink": "Work Details",
            // "head": "Worksite Photos",
            "body": [
              {
                
                "type": "documentUpload",
                "withoutLabel": true,
                "module": "Measurement",
                "error": "WORKS_REQUIRED_ERR",
                "name": "uploadedDocs",
                "key": "documentDetails",
                "customClass": "my doc",
                "localePrefix": "MB_MEASUREMENT_DOC"

              }

            ]
          }
        ]
      }
    ]

  };
}

