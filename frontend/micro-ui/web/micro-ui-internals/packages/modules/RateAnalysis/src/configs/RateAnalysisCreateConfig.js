export const CreateConfig = ({t, defaultValue, isUpdate, measurement }) => {
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
                  label: "RA_SOR_CODE",
                  isMandatory: false,
                  key: "SORCode",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "SORCode",
                    customStyle :{marginBottom:"-5px", marginTop:"10px"},
                    customParaStyle : {marginBottom:"revert"}
                  },
                },
                {
                  inline: true,
                  label: "RA_SOR_TYPE",
                  isMandatory: false,
                  key: "SORType",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert"},
                    name: "SORType",
                    // "customClass": "fc-header-texts"
                  },
                },
                {
                  inline: true,
                  label: "RA_SOR_SUBTYPE",
                  isMandatory: false,
                  key: "SORSubType",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "SORSubType",
                    //customClass: "",
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert"}
                  },
                },
                {
                  inline: true,
                  label: "RA_SOR_VARIENT",
                  isMandatory: false,
                  key: "SORVarient",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "SORVarient",
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert"}
                    // "customClass": "fc-header-texts"
                  },
                },
                {
                  inline: true,
                  label: "RA_UOM",
                  isMandatory: false,
                  key: "uom",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "uom",
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert"},
                    //customClass: "",
                  },
                },
                {
                  inline: true,
                  label: "RA_RATE_DEFINED",
                  isMandatory: false,
                  key: "rateDefinedForQty",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "rateDefinedForQty",
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert"},
                    //customClass: "",
                  },
                },
                {
                  inline: true,
                  label: "RA_DESCRIPTION",
                  isMandatory: false,
                  key: "description",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "description",
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert"},
                    // customClass: "",
                  },
                },
                {
                  inline: true,
                  label: "RA_STATUS",
                  isMandatory: false,
                  key: "status",
                  type: "paragraph",
                  disable: true,
                  appendColon: false,
                  populators: {
                    name: "status",
                    customStyle :{marginBottom:"-5px"},
                    customParaStyle : {marginBottom:"revert", color:defaultValue?.status?.includes("INACTIVE") ? "red": "green"},
                    // customClass: "",
                  },
                },
              ],
            },
            {
              head : "RA_ANALYSIS_DETAILS",
              subHead: "",
              //forOnlyUpdate : "",
              body: [
                {
                  "inline": true,
                  "label": "RA_DATE",
                  "isMandatory": true,
                  "key": "effective_from_date",
                  "type": "date",
                  "disable":false,
                  // "preProcess" : {
                  //   "updateDependent" : ["populators.validation.max"]
                  // },
                  "populators": {
                    "name": "effective_from_date",
                    "nonEditable": isUpdate ? true : false,
                    "validation":{
                      "min":isUpdate ? null : defaultValue?.currentDate
                    }
                  }
                },
                {
                  "inline": true,
                  "label": "RA_ANALYSIS_QUANTITY_DEFINED",
                  "isMandatory": true,
                  "key": "analysis_qty_defined",
                  "type": "text",
                  "disable": false,
                  "populators": {
                    "name": "analysis_qty_defined",
                    "error": t("ERR_QUANTITY_MANDATORY_FORMAT"),
                    "validation": {
                      "pattern": /^$|^\d{1,4}(\.\d{1,2})?$/i,
                      //"ValidationRequired" : true,
                      //"title" : "please correct the input",
                    }
                  }
                },
              ],
            },
            {
              head: "RA_LABOUR",
              subHead: "",
              body: [
                {
                  type: "component",
                  component: "SORDetailsTemplate",
                  withoutLabel: true,
                  key: "SORDetails_Labour",
                  mode: "CREATE",
                  sorType : "LABOUR",
                  useFieldArray: true,
                  customProps:{
                    SORDetails:defaultValue?.SORDetails,
                  }
                },
              ],
            },
            {
              head: "RA_MATERIAL",
              subHead: "",
              body: [
                {
                  type: "component",
                  component: "SORDetailsTemplate",
                  withoutLabel: true,
                  key: "SORDetails_Material",
                  mode: "CREATE",
                  sorType : "MATERIAL",
                  useFieldArray: true,
                  customProps:{
                    SORDetails:defaultValue?.SORDetails,
                  }
                },
              ],
            },
            {
              head: "RA_MACHINERY",
              subHead: "",
              body: [
                {
                  type: "component",
                  component: "SORDetailsTemplate",
                  withoutLabel: true,
                  key: "SORDetails_Machinery",
                  mode: "CREATE",
                  sorType : "MACHINERY",
                  useFieldArray: true,
                  customProps:{
                    SORDetails:defaultValue?.SORDetails,
                  }
                },
              ],
            },
            {
              head: "RA_EXTRACHARGES",
              subHead: "",
              body: [
                {
                  type: "component",
                  component: "ExtraCharges",
                  withoutLabel: true,
                  key: "extraCharges",
                  mode: "CREATE",
                  sorType : "EXTRACHARGES",
                  useFieldArray: true,
                  // customProps:{
                  //   extraCharges:defaultValue?.extraCharges,
                  // }
                },
              ],
            },
          ],
        },
      ],
    };
  };