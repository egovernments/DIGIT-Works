
export const CreateConfig = ({ defaultValue }) => {

  return {
    CreateConfig: [
      {
        defaultValues: defaultValue, // Use the default values here

        form: [
          // {
          //   head: "",
          //   subHead: "",
          //   body: [
          //     {
          //       inline: true,
          //       label: "WORKS Order Number",
          //       isMandatory: false,
          //       key: "contractNumber",
          //       type: "text",
          //       disable: true,
          //       appendColon: true,
          //       populators: {
          //         name: "contractNumber",
          //         customClass: "fc-header-texts"
          //       }
          //     },
          //     {
          //       inline: true,
          //       label: "Project Id",
          //       isMandatory: false,
          //       key: "basicDetails_projectID",
          //       type: "text",
          //       disable: true,
          //       appendColon: true,
          //       populators: {
          //         name: "basicDetails_projectID",
          //         customClass: "fc-header-texts"
          //       }
          //     },
          //     {
          //       inline: true,
          //       label: "Project Senction Date",
          //       isMandatory: false,
          //       key: "basicDetails_projectSencDate",
          //       type: "text",
          //       disable: true,
          //       appendColon: true,
          //       populators: {
          //         name: "basicDetails_projectSencDate",
          //         customClass: "fc-header-texts"
          //       }
          //     },
          //     {
          //       inline: true,
          //       label: "Project Name",
          //       isMandatory: false,
          //       key: "basicDetails_projectName",
          //       type: "text",
          //       disable: true,
          //       appendColon: true,
          //       populators: {
          //         name: "basicDetails_projectName",
          //         customClass: "fc-header-texts"
          //       }
          //     },
          //     {
          //       inline: true,
          //       label: "Project Description",
          //       isMandatory: false,
          //       key: "basicDetails_projectDesc",
          //       type: "text",
          //       disable: true,
          //       appendColon: true,
          //       populators: {
          //         name: "basicDetails_projectDesc",
          //         customClass: "fc-header-texts"
          //       }
          //     },
          //     {
          //       inline: true,
          //       label: "Project Location",
          //       isMandatory: false,
          //       key: "basicDetails_projectLoc",
          //       type: "text",
          //       disable: true,
          //       appendColon: true,
          //       populators: {
          //         name: "basicDetails_projectLoc",
          //         customClass: "fc-header-texts"
          //       }
          //     }
          //   ]
          // },
          // {
          //   head: "Measurement Period",
          //   subHead: "",
          //   body: [
          //     {
          //       inline: true,
          //       label: "From Date",
          //       isMandatory: false,
          //       type: "date",
          //       disable: false,
          //       populators: { name: "fDate", error: "Required", validation: { required: true, } },
          //     },
          //     {
          //       inline: true,
          //       label: "To Date",
          //       isMandatory: false,
          //       type: "date",
          //       disable: false,
          //       populators: { name: "tDate", error: "Required", validation: { required: true, } },
          //     },
          //   ],
          // },
          
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
            "head": "",
            "body": [
              {
                "type": "documentUpload",
                "withoutLabel": true,
                "module": "Measurement",
                "error": "WORKS_REQUIRED_ERR",
                "name": "uploadedDocs",
                "key": "documentDetails",
                "customClass": "",
                "localePrefix": "ESTIMATE_DOC"
              }
            ]
          }
        ]
      }
    ]
  };

};

