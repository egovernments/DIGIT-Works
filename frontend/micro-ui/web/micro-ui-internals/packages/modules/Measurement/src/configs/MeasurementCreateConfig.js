export const CreateConfig = ({ defaultValues }) => {

  return {
    CreateConfig: [
      {
        defaultValues: {
          basicDetails_OrderNumber: defaultValues?.contractNumber,
          basicDetails_projectID: defaultValues?.contractNumber,
          basicDetails_projectSencDate: defaultValues?.contractNumber,
          basicDetails_projectName: defaultValues?.contractNumber,
          basicDetails_projectDesc: defaultValues?.contractNumber,
          basicDetails_projectLoc: defaultValues?.contractNumber,


          // basicDetails_dateOfProposal: defaultValues?.basicDetails_dateOfProposal,
          // basicDetails_projectName: defaultValues?.basicDetails_projectName,
          // workOrderAmountRs: defaultValues?.workOrderAmountRs
        },
        form: [
          {
            head: "",
            subHead: "",
            body: [
              {
                inline: true,
                label: "WORKS Order Number",
                isMandatory: false,
                key: "basicDetails_OrderNumber",
                type: "text",
                disable: true,
                appendColon: true,
                populators: {
                  name: "basicDetails_OrderNumber",
                  customClass: "fc-header-texts"
                }
              },
              {
                inline: true,
                label: "Project Id",
                isMandatory: false,
                key: "basicDetails_projectID",
                type: "text",
                disable: true,
                appendColon: true,
                populators: {
                  name: "basicDetails_projectID",
                  customClass: "fc-header-texts"
                }
              },
              {
                inline: true,
                label: "Project Senction Date",
                isMandatory: false,
                key: "basicDetails_projectSencDate",
                type: "text",
                disable: true,
                appendColon: true,
                populators: {
                  name: "basicDetails_projectSencDate",
                  customClass: "fc-header-texts"
                }
              },
              {
                inline: true,
                label: "Project Name",
                isMandatory: false,
                key: "basicDetails_projectName",
                type: "text",
                disable: true,
                appendColon: true,
                populators: {
                  name: "basicDetails_projectName",
                  customClass: "fc-header-texts"
                }
              },
              {
                inline: true,
                label: "Project Description",
                isMandatory: false,
                key: "basicDetails_projectDesc",
                type: "text",
                disable: true,
                appendColon: true,
                populators: {
                  name: "basicDetails_projectDesc",
                  customClass: "fc-header-texts"
                }
              },
              {
                inline: true,
                label: "Project Location",
                isMandatory: false,
                key: "basicDetails_projectLoc",
                type: "text",
                disable: true,
                appendColon: true,
                populators: {
                  name: "basicDetails_projectLoc",
                  customClass: "fc-header-texts"
                }
              }
            ]
          },
          {
            head: "Measurement Period",
            subHead: "",
            body: [
              {
                inline: true,
                label: "From Date",
                isMandatory: false,
                type: "date",
                disable: false,
                populators: { name: "fDate", error: "Required", validation: { required: true, } },
              },
              {
                inline: true,
                label: "To Date",
                isMandatory: false,
                type: "date",
                disable: false,
                populators: { name: "tDate", error: "Required", validation: { required: true, } },
              },
            ],
          },
          {
            head: "",
            subHead: "",
            body: [

              {
                type: "component",
                component: "MeasureTable",
                withoutLabel: true,
                key: "SOR",
              },
              {
                type: "component",
                component: "MeasureTable",
                withoutLabel: true,
                key: "NONSOR",
              },
            ],
          },
        ]
      }
    ]
  };
};


