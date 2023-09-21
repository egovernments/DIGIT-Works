export const CreateConfig = ({ defaultValues }) => {

  return {
    CreateConfig: [
      {
        defaultValues: defaultValue, // Use the default values here
        form: [
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
        ]
      }
    ]
  };
};
