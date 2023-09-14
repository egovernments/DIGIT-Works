export const CreateConfig = [
  {
    head: "Sample Object Creation",
    subHead: "Supporting Details",
    body: [
      {
        inline: true,
        label: "Salutation",
        isMandatory: false,
        type: "text",
        disable: false,
        populators: { name: "salutation", error: "Required", validation: { pattern: /^[A-Za-z]+$/i, maxlength: 5 } },
      },
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
        key: "SOR",
      },
    ],
  },
];

