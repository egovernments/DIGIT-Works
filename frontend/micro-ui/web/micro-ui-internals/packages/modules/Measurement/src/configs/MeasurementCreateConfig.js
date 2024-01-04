export const CreateConfig = ({ defaultValue, measurement }) => {
  return {
    CreateConfig: [
      {
        defaultValues: defaultValue, // Use the default values here

        form: [
          {
            head: "",
            subHead: "",
            body: [
              {
                inline: true,
                label: "MB_WORK_ORDER_NUMBER",
                isMandatory: false,
                key: "contractNumber",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "contractNumber",
                  customStyle :{marginBottom:"-5px", marginTop:"10px"},
                  customParaStyle : {marginBottom:"revert"}
                },
              },
              {
                inline: true,
                label: "MB_PROJECT_ID",
                isMandatory: false,
                key: "projectID",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"},
                  name: "projectID",
                  // "customClass": "fc-header-texts"
                },
              },
              {
                inline: true,
                label: "MB_MUSTER_ROLL_NO",
                isMandatory: false,
                key: "musterRollNo",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "musterRollNo",
                  customClass: "",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"}
                },
              },
              {
                inline: true,
                label: "MB_PROJECT_DATE",
                isMandatory: false,
                key: "sanctionDate",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "sanctionDate",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"}
                  // "customClass": "fc-header-texts"
                },
              },
              {
                inline: true,
                label: "MB_PROJECT_NAME",
                isMandatory: false,
                key: "projectName",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "projectName",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"},
                  customClass: "",
                },
              },
              {
                inline: true,
                label: "MB_PROJECT_DESC",
                isMandatory: false,
                key: "projectDesc",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "projectDesc",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"},
                  customClass: "",
                },
              },
              {
                inline: true,
                label: "ES_COMMON_LOCATION",
                isMandatory: false,
                key: "projectLocation",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "projectLocation",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"},
                  customClass: "",
                },
              },
              {
                inline: true,
                label: "MB_MEASUREMENT_PERIOD",
                isMandatory: false,
                key: "measurementPeriod",
                type: "paragraph",
                disable: true,
                appendColon: false,
                populators: {
                  name: "measurementPeriod",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"},
                  customClass: "",
                },
              },
            ],
          },
          {
            subHead: "",
            forOnlyUpdate : "",
            body: [
              {
                type: "component",
                component: "MeasurementHistory",
                withoutLabel: true,
                key: "MeasurementHistory",
                useFieldArray: true,
                "customProps" : {
                  "contractNumber" : defaultValue?.contractNumber,
                  "measurementNumber" : measurement?.measurementNumber
                }
              },
            ],
          },
          {
            head: "MB_SORS",
            subHead: "",
            body: [
              {
                type: "component",
                component: "MeasureTable",
                withoutLabel: true,
                key: "SOR",
                mode: "CREATE",
                useFieldArray: true,
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
                mode: "CREATE",
                useFieldArray: true,
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
              {
                "type": "component",
                "component": "ViewAnalysisStatement",
                "withoutLabel": true,
                "key": "labourMaterialAnalysis"
              }
            ],
          },
          {
            // "navLink": "Work Details",
            // "head": "Worksite Photos",
            body: [
              {
                type: "documentUpload",
                withoutLabel: true,
                module: "Measurement",
                error: "WORKS_REQUIRED_ERR",
                name: "uploadedDocs",
                key: "documentDetails",
                customClass: "my doc",
                localePrefix: "MB_MEASUREMENT_DOC",
              },
            ],
          },
        ],
      },
    ],
  };
};
