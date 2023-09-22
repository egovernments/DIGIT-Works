
export const CreateConfig = ({ defaultValue }) => {

  return {
    CreateConfig: [
      {
        defaultValues: defaultValue, // Use the default values here

        form: [
          {
            head: "SORs",
            subHead: "",
            body: [
              {
                type: "component",
                component: "MeasureTable",
                withoutLabel: true,
                key: "SOR",
              },
            ],
          },
          {
            head: "Non SORs",
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
            // "navLink": "Work Details",
            "head": "Worksite Photos",
            "body": [
              {
                "type": "documentUpload",
                "withoutLabel": true,
                "module": "Measurement",
                "error": "WORKS_REQUIRED_ERR",
                "name": "uploadedDocs",
                "key": "documentDetails",
                "customClass": "my doc",
                "localePrefix": "MEASUREMENT_DOC"
              }
            ]
          }
        ]
      }
    ]
  };

};

