export const CreateConfig = ({ defaultValue, measurement, mbnumber }) => {
  
  return {
    CreateConfig: [
      {
        defaultValues: defaultValue, // Use the default values here

        form: [
          {
            head: "",
            subHead: "",
            body: [
              ...(mbnumber
                ? [
                    {
                      inline: true,
                      label: "MB_MEASUREMENT_NUMBER",
                      isMandatory: false,
                      key: "mbNumber",
                      type: "paragraph",
                      disable: true,
                      appendColon: false,
                      labelClassName:"mb-create-label",
                      populators: {
                        name: "mbNumber",
                        customStyle: { marginBottom: "-5px", marginTop: "10px" },
                        customParaStyle: { marginBottom: "revert" },
                      },
                    },
                  ]
                : []),
              {
                inline: true,
                label: "MB_WORK_ORDER_NUMBER",
                isMandatory: false,
                key: "contractNumber",
                type: "paragraph",
                disable: true,
                appendColon: false,
                labelClassName:"mb-create-label",
                populators: {
                  name: "contractNumber",
                  customStyle :{marginBottom:"-5px"},
                  customParaStyle : {marginBottom:"revert"},
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
                labelClassName:"mb-create-label",
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
                labelClassName:"mb-create-label",
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
                labelClassName:"mb-create-label",
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
                labelClassName:"mb-create-label",
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
                labelClassName:"mb-create-label",
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
                labelClassName:"mb-create-label",
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
                labelClassName:"mb-create-label",
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
            //forOnlyUpdate : "",
            sectionClassName:"table-included-section",
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
            sectionClassName:"table-included-section",
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
            sectionClassName:"table-included-section",
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
            sectionClassName:"viewstatement-viewamount-wrapper-create",
            body: [
              {
                type: "component",
                component: "ViewOnlyCard",
                withoutLabel: true,
                key: "viewAmount",
                populators:{
                  customStyle:{
                    marginBottom:"0px"
                  }
                }
              },
              {
                "type": "component",
                "component": "ViewAnalysisStatement",
                "withoutLabel": true,
                "key": "labourMaterialAnalysis",
                populators:{
                  customStyle:{
                    marginBottom:"0px"
                  }
                },
                "formData" : {
             Measurement:measurement,
             }
          
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
