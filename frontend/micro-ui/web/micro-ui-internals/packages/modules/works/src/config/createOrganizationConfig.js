import React from "react";
import { Dropdown, DatePicker, UploadFile, RadioButtons } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";

const org_type_options = [
    {
        code: "community_organisation",
        name: "Community Organisation",
    },
    {
        code: "vendor",
        name: "Vendor",
    }
]

const bank_acc_type_options = [
    {
      code: "savings",
      name: "Savings",
    },
    {
      code: "current",
      name: "Current",
    }
]

const org_classification = [
    {
      code: "org1",
      name: "org1",
    },
    {
      code: "org2",
      name: "org2",
    }
]

const gender_options = [
    {
      code: "male",
      name: "Male",
    },
    {
      code: "female",
      name: "Female",
    },
    {
      code: "transgender",
      name: "Transgender",
    }
]

export const createOrganizationConfig = ({selectFile, uploadedFile, setUploadedFile, error}) => {
    const { t } = useTranslation()

    return {
        label: {
            submit: "Create Organisation Record"
        }, 
        form: [
            {
                head: "Organisation Details",
                body: [
                {
                    isMandatory: true,
                    key: "org_type",
                    type: "radio",
                    label: "Organisation Type",
                    disable: false,
                    populators: {
                        name: "org_type",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: false,
                        options: org_type_options
                    },
                },
                {
                    inline: true,
                    label:"Name of the Organisation",
                    isMandatory: true,
                    key: "org_name",
                    type: "text",
                    disable: false,
                    populators: { name: "org_name", error: "Enter required details", validation: { pattern: /^[A-Za-z]+$/i } },
                },
                {
                    //TODO: max date is not working
                    inline: true,
                    label: "Formation Date",
                    isMandatory: false,
                    type: "date",
                    disable: false,
                    populators: { 
                        name: "formation_date", 
                        max: Digit.Utils.date.getDate()
                    },
                },
                {
                    isMandatory: true,
                    key: "org_classification",
                    type: "dropdown",
                    label: "Organisation Classification",
                    disable: false,
                    populators: {
                      name: "org_classification",
                      optionsKey: "name",
                      error: "Enter required details",
                      required: true,
                      options: org_classification
                    },
                },
                {
                    label: "Total Members",
                    isMandatory: false,
                    key: "total",
                    type: "number",
                    disable: false,
                    populators: { name: "total", error: "Total should be greated that 0", validation: { min: 1 } },
                }
                ],
            },
            {
                head: "Location Details",
                body: [
                {
                    isMandatory: false,
                    key: "locality",
                    type: "dropdown",
                    label: "Locality",
                    disable: false,
                    populators: {
                        name: "locality",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: false,
                        options: org_classification
                    },
                },
                {
                    isMandatory: true,
                    key: "ward",
                    type: "dropdown",
                    label: "Ward",
                    disable: false,
                    populators: {
                        name: "ward",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: true,
                        options: org_classification
                    },
                },
                {
                    isMandatory: true,
                    key: "ulb",
                    type: "dropdown",
                    label: "ULB",
                    disable: false,
                    populators: {
                        name: "ulb",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: true,
                        options: org_classification
                    },
                },
                {
                    isMandatory: true,
                    key: "district",
                    type: "dropdown",
                    label: "District",
                    disable: false,
                    populators: {
                        name: "district",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: true,
                        options: org_classification
                    },
                }
                ],
            },
            {
                head: "Financial Details",
                body: [
                {
                    inline: true,
                    label:"Bank Account Holder Name",
                    isMandatory: true,
                    key: "bank_acc_holder_name",
                    type: "text",
                    disable: false,
                    populators: { name: "acc_holder_name", error: "Enter required details", validation: { pattern: /^[A-Za-z]+$/i } },
                },
                {
                    isMandatory: true,
                    key: "bank_acc_type",
                    type: "radio",
                    label: "Bank Account Type",
                    disable: false,
                    populators: {
                        name: "bank_acc_type",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: false,
                        options: bank_acc_type_options
                    },
                },
                {
                    inline: true,
                    label: "Account Number",
                    isMandatory: true,
                    key: "bank_acc_no",
                    type: "number",
                    disable: false,
                    populators: { name: "bank_acc_no", error: "Enter required details" },
                },
                {
                    inline: true,
                    label:"IFSC",
                    isMandatory: true,
                    key: "ifsc",
                    type: "text",
                    disable: false,
                    populators: { name: "ifsc", error: "Enter required details", validation: { pattern: /^[A-Za-z0-9]+$/i } },
                },
                {
                    inline: true,
                    label:"PAN",
                    isMandatory: false,
                    key: "pan",
                    type: "text",
                    disable: false,
                    populators: { name: "pan", error: "Enter valid PAN", validation: { pattern: Digit.Utils.getPattern('PAN') } },
                },
                {
                    inline: true,
                    label:"GSTIN",
                    isMandatory: false,
                    key: "gstin",
                    type: "text",
                    disable: false,
                    populators: { name: "gstin", error: "Enter valid GSTIN", validation: { pattern: Digit.Utils.getPattern('GSTNo') } },
                }
                ],
            },
            {
                head: "Primary Contact Details",
                body: [ 
                {
                    inline: true,
                    label:"Name of Contract Person",
                    isMandatory: true,
                    key: "contract_person_name",
                    type: "text",
                    disable: false,
                    populators: { name: "contract_person_name", error: "Enter required details", validation: { pattern: /^[A-Za-z]+$/i } },
                },
                {
                    inline: true,
                    label:"Father’s/Guardians Name",
                    isMandatory: false,
                    key: "father_name",
                    type: "text",
                    disable: false,
                    populators: { name: "father_name", error: "Enter required details", validation: { pattern: /^[A-Za-z]+$/i } },
                },
                {
                    isMandatory: true,
                    key: "gender",
                    type: "dropdown",
                    label: "Gender",
                    disable: false,
                    populators: {
                        name: "gender",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: true,
                        options: gender_options
                    },
                },
                {
                    inline: true,
                    label: "Phone Number",
                    isMandatory: true,
                    key: "phone",
                    type: "number",
                    disable: false,
                    populators: { name: "phone", error: "Enter valid phone", validation: Digit.Utils.getPattern('MobileNo')},
                },
                {
                    isMandatory: true,
                    key: "designatiom",
                    type: "dropdown",
                    label: "Designation",
                    disable: false,
                    populators: {
                        name: "designatiom",
                        optionsKey: "name",
                        error: "Enter required details",
                        required: true,
                        options: gender_options
                    },
                },
                {   
                    label: "Photograph",
                    populators: (
                      <UploadFile
                        id={"upload_photo"}
                        onUpload={selectFile}
                        onDelete={() => {
                            setUploadedFile(null);
                        }}
                        showHint={true}
                        message={uploadedFile ? `1 ${t(`CS_ACTION_FILEUPLOADED`)}` : t(`CS_ACTION_NO_FILEUPLOADED`)}
                        accept= "image/*, .pdf, .png, .jpeg, .jpg"
                        iserror={error}
                      />
                    ),
                  },
                ],
            }
        ]
    }
}