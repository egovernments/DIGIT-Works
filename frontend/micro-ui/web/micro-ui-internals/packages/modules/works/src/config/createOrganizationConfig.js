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
            submit: t("WORKS_CREATE_ORG_RECORD")
        }, 
        form: [
            {
                head: t("WORKS_ORGANISATION_DETAILS"),
                body: [
                {
                    isMandatory: true,
                    key: "org_type",
                    type: "radio",
                    label: t("WORKS_ORGANISATION_TYPE"),
                    disable: false,
                    populators: {
                        name: "org_type",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: false,
                        options: org_type_options
                    },
                },
                {
                    inline: true,
                    label:t("WORKS_NAME_OF_ORGN"),
                    isMandatory: true,
                    key: "org_name",
                    type: "text",
                    disable: false,
                    populators: { name: "org_name", error: t("WORKS_ENTER_REQ_DETAILS"), validation: { pattern: Digit.Utils.getPattern('Name') } },
                },
                {
                    //TODO: max date is not working
                    inline: true,
                    label: t("WORKS_FORMATION_DATE"),
                    isMandatory: false,
                    type: "date",
                    disable: false,
                    populators: { 
                        name: "formation_date", 
                        max: new Date().toISOString().split("T")[0]
                    },
                },
                {
                    isMandatory: true,
                    key: "org_classification",
                    type: "dropdown",
                    label: t("WORKS_ORGANISATION_CLASSIFICATION"),
                    disable: false,
                    populators: {
                      name: "org_classification",
                      optionsKey: "name",
                      error: t("WORKS_ENTER_REQ_DETAILS"),
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
                    populators: { name: "total", error: t("WORKS_GREATER_THAN_ZERO_VALIDATION"), validation: { min: 1 } },
                }
                ],
            },
            {
                head: t("WORKS_LOCATION_DETAILS"),
                body: [
                {
                    isMandatory: false,
                    key: "locality",
                    type: "dropdown",
                    label: t("WORKS_LOCALITY"),
                    disable: false,
                    populators: {
                        name: "locality",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: false,
                        options: org_classification
                    },
                },
                {
                    isMandatory: true,
                    key: "ward",
                    type: "dropdown",
                    label: t("WORKS_WARD"),
                    disable: false,
                    populators: {
                        name: "ward",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: true,
                        options: org_classification
                    },
                },
                {
                    isMandatory: true,
                    key: "ulb",
                    type: "dropdown",
                    label: t("ULB"),
                    disable: false,
                    populators: {
                        name: "ulb",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: true,
                        options: org_classification
                    },
                },
                {
                    isMandatory: true,
                    key: "district",
                    type: "dropdown",
                    label: t("PDF_STATIC_LABEL_ESTIMATE_DISTRICT"),
                    disable: false,
                    populators: {
                        name: "district",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
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
                    label:t("WORKS_BANK_ACC_HOLDER_NAME"),
                    isMandatory: true,
                    key: "bank_acc_holder_name",
                    type: "text",
                    disable: false,
                    populators: { name: "acc_holder_name", error: t("WORKS_ENTER_REQ_DETAILS"), validation: { pattern: Digit.Utils.getPattern('Name') } },
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
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: false,
                        options: bank_acc_type_options
                    },
                },
                {
                    inline: true,
                    label: t("WORKS_BANK_ACC_NUMBER"),
                    isMandatory: true,
                    key: "bank_acc_no",
                    type: "number",
                    disable: false,
                    populators: { name: "bank_acc_no", error: t("WORKS_ENTER_REQ_DETAILS"), validation: {pattern: Digit.Utils.getPattern('bankAccountNo')} },
                },
                {
                    inline: true,
                    label:t("WORKS_IFSC"),
                    isMandatory: true,
                    key: "ifsc",
                    type: "text",
                    disable: false,
                    populators: { name: "ifsc", error: t("WORKS_ENTER_REQ_DETAILS"), validation: {pattern: Digit.Utils.getPattern('IFSC')} },
                },
                {
                    inline: true,
                    label:t("WORKS_PAN"),
                    isMandatory: false,
                    key: "pan",
                    type: "text",
                    disable: false,
                    populators: { name: "pan", error: t("WORKS_PAN_VALIDATION"), validation: { pattern: Digit.Utils.getPattern('PAN') } },
                },
                {
                    inline: true,
                    label:t("WORKS_GSTIN"),
                    isMandatory: false,
                    key: "gstin",
                    type: "text",
                    disable: false,
                    populators: { name: "gstin", error: t("WORKS_GSTIN_VALIDATION"), validation: { pattern: Digit.Utils.getPattern('GSTNo') } },
                }
                ],
            },
            {
                head: t("WORKS_PRIMARY_CONTACT_DETAILS"),
                body: [ 
                {
                    inline: true,
                    label:t("WORKS_CONTRACT_PERSON_NAME"),
                    isMandatory: true,
                    key: "contract_person_name",
                    type: "text",
                    disable: false,
                    populators: { name: "contract_person_name", error: t("WORKS_ENTER_REQ_DETAILS"), validation: { pattern: Digit.Utils.getPattern('Name') } },
                },
                {
                    inline: true,
                    label:t("WORKS_FATHER_NAME"),
                    isMandatory: false,
                    key: "father_name",
                    type: "text",
                    disable: false,
                    populators: { name: "father_name", error: t("WORKS_ENTER_REQ_DETAILS"), validation: { pattern: Digit.Utils.getPattern('Name') } },
                },
                {
                    isMandatory: true,
                    key: "gender",
                    type: "dropdown",
                    label: t("CORE_COMMON_PROFILE_GENDER"),
                    disable: false,
                    populators: {
                        name: "gender",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: true,
                        options: gender_options
                    },
                },
                {
                    inline: true,
                    label: t("CORE_COMMON_PHONE_NUMBER"),
                    isMandatory: true,
                    key: "phone",
                    type: "number",
                    disable: false,
                    populators: { name: "phone", error: t("WORKS_PHONE_VALIDATION"), validation: { min: 5999999999, max: 9999999999 }},
                },
                {
                    isMandatory: true,
                    key: "designatiom",
                    type: "dropdown",
                    label: t("ATM_DESIGNATION"),
                    disable: false,
                    populators: {
                        name: "designatiom",
                        optionsKey: "name",
                        error: t("WORKS_ENTER_REQ_DETAILS"),
                        required: true,
                        options: gender_options
                    },
                },
                {   
                    label: t("WORKS_PHOTOGRAPH"),
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