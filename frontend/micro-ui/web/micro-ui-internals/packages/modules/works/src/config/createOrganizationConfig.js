import React from "react";
import { UploadFile } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";

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

export const createOrganizationConfig = ({selectFile, uploadedFile, setUploadedFile, error}) => {
    const { t } = useTranslation()

    const userInfo = Digit.UserService.getUser();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.pt.getCityLocale(tenantId);
    const city = userInfo && userInfo?.info?.permanentCity;
   
    const {data: Localities } = Digit.Hooks.useLocation(tenantId, {}, 'Locality')
    const {data: Wards } = Digit.Hooks.useLocation(tenantId, {}, 'Ward')
    
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)
    console.log('headerLocale', headerLocale)
    let LocalityOptions = []
    Localities &&
        Localities?.TenantBoundary[0]?.boundary.map(item => {
            LocalityOptions.push({code: item.code, name: item.name,  i18nKey: t(`TENANT_TENANTS_${item?.code}`) })
        })
       
    let WardOptions = []
    Wards &&
        Wards?.TenantBoundary[0]?.boundary.map(item => {
            WardOptions.push({code: item.code, name: item.name,  i18nKey: t(`TENANT_TENANTS_${item?.code}`) })
        })

    return {
        label: {
            submit: t("MASTERS_CREATE_ORG_RECORD")
        }, 
        form: [
            {
                head: t("MASTERS_ORGANISATION_DETAILS"),
                body: [
                {
                    isMandatory: true,
                    key: "org_type",
                    type: "radio",
                    label: t("MASTERS_ORGANISATION_TYPE"),
                    disable: false,
                    populators: {
                        name: "org_type",
                        optionsKey: "name",
                        error: t("ENTER_REQ_DETAILS"),
                        required: false,
                        mdmsConfig: {
                            masterName: "OrganisationType",
                            moduleName: "works",
                            localePrefix: "MASTERS",
                        }
                    },
                },
                {
                    inline: true,
                    label:t("MASTERS_NAME_OF_ORGN"),
                    isMandatory: true,
                    key: "org_name",
                    type: "text",
                    disable: false,
                    populators: { 
                        name: "org_name", 
                        error: t("ENTER_REQ_DETAILS"), 
                        validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,120}$/i },
                    },
                },
                {
                    inline: true,
                    label: t("MASTERS_FORMATION_DATE"),
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
                    label: t("MASTERS_ORGANISATION_CLASSIFICATION"),
                    disable: false,
                    populators: {
                      name: "org_classification",
                      optionsKey: "name",
                      error: t("ENTER_REQ_DETAILS"),
                      required: true,
                      mdmsConfig: {
                        masterName: "OrganisationClassification",
                        moduleName: "works",
                        localePrefix: "MASTERS",
                    }
                    },
                },
                {
                    label: t("MASTERS_TOTAL_MEMBERS"),
                    isMandatory: false,
                    key: "total",
                    type: "number",
                    disable: false,
                    populators: { name: "total", error: t("MASTERS_GREATER_THAN_ZERO_VALIDATION"), validation: { min: 1 } },
                }
                ],
            },
            {
                head: t("ES_NEW_APPLICATION_LOCATION_DETAILS"),
                body: [
                {
                    isMandatory: false,
                    key: "locality",
                    type: "dropdown",
                    label: t("ES_INBOX_LOCALITY"),
                    disable: false,
                    populators: {
                        name: "locality",
                        optionsKey: "i18nKey",
                        error: t("ENTER_REQ_DETAILS"),
                        required: false,
                        options: LocalityOptions
                    },
                },
                {
                    isMandatory: true,
                    key: "ward",
                    type: "dropdown",
                    label: t("PDF_STATIC_LABEL_ESTIMATE_WARD"),
                    disable: false,
                    populators: {
                        name: "ward",
                        optionsKey: "i18nKey",
                        error: t("ENTER_REQ_DETAILS"),
                        required: true,
                        options: WardOptions
                    },
                },
                {
                    inline: true,
                    label: t("ULB"),
                    isMandatory: true,
                    key: "ulb",
                    type: "text",
                    disable: false,
                    value: t(ULB),
                    populators: { name: "ulb", validation: {
                        required: true,
                      }},
                },
                // {
                //     inline: true,
                //     label: t("PDF_STATIC_LABEL_ESTIMATE_DISTRICT"),
                //     isMandatory: true,
                //     key: "district",
                //     type: "text",
                //     disable: true,
                //     value: city,
                //     populators: { name: "district" },
                // },
                // {
                //     isMandatory: true,
                //     key: "ulb",
                //     type: "dropdown",
                //     label: t("ULB"),
                //     disable: false,
                //     populators: {
                //         name: "ulb",
                //         optionsKey: "name",
                //         error: t("ENTER_REQ_DETAILS"),
                //         required: true,
                //         value: "org1",
                //         options: org_classification
                //     },
                // },
                {
                    isMandatory: true,
                    key: "district",
                    type: "dropdown",
                    label: t("PDF_STATIC_LABEL_ESTIMATE_DISTRICT"),
                    disable: false,
                    populators: {
                        name: "district",
                        optionsKey: "name",
                        error: t("ENTER_REQ_DETAILS"),
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
                    label:t("BANK_ACC_HOLDER_NAME"),
                    isMandatory: true,
                    key: "bank_acc_holder_name",
                    type: "text",
                    disable: false,
                    populators: { name: "acc_holder_name", error: t("ENTER_REQ_DETAILS"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,120}$/i } },
                },
                {
                    isMandatory: true,
                    key: "bank_acc_type",
                    type: "radio",
                    label: t("BANK_ACCOUNT_TYPE"),
                    disable: false,
                    populators: {
                        name: "bank_acc_type",
                        optionsKey: "name",
                        error: t("ENTER_REQ_DETAILS"),
                        required: false,
                        mdmsConfig: {
                            masterName: "BankAccType",
                            moduleName: "works",
                            localePrefix: "MASTERS",
                        }
                    },
                },
                {
                    inline: true,
                    label: t("BANK_ACC_NUMBER"),
                    isMandatory: true,
                    key: "bank_acc_no",
                    type: "number",
                    disable: false,
                    populators: { name: "bank_acc_no", error: t("ENTER_REQ_DETAILS"), validation: {pattern: Digit.Utils.getPattern('bankAccountNo')} },
                },
                {
                    inline: true,
                    label:t("IFSC"),
                    isMandatory: true,
                    key: "ifsc",
                    type: "text",
                    disable: false,
                    populators: { name: "ifsc", error: t("ENTER_REQ_DETAILS"), validation: {pattern: Digit.Utils.getPattern('IFSC')} },
                },
                {
                    inline: true,
                    label:t("PAN"),
                    isMandatory: false,
                    key: "pan",
                    type: "text",
                    disable: false,
                    populators: { name: "pan", error: t("PAN_VALIDATION"), validation: { pattern: Digit.Utils.getPattern('PAN') } },
                },
                {
                    inline: true,
                    label:t("GSTIN"),
                    isMandatory: false,
                    key: "gstin",
                    type: "text",
                    disable: false,
                    populators: { name: "gstin", error: t("GSTIN_VALIDATION"), validation: { pattern: Digit.Utils.getPattern('GSTNo') } },
                }
                ],
            },
            {
                head: t("PRIMARY_CONTACT_DETAILS"),
                body: [ 
                {
                    inline: true,
                    label:t("CONTRACT_PERSON_NAME"),
                    isMandatory: true,
                    key: "contract_person_name",
                    type: "text",
                    disable: false,
                    populators: { name: "contract_person_name", error: t("ENTER_REQ_DETAILS"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,120}$/i } },
                },
                {
                    inline: true,
                    label:t("FATHER_NAME"),
                    isMandatory: false,
                    key: "father_name",
                    type: "text",
                    disable: false,
                    populators: { name: "father_name", error: t("ENTER_REQ_DETAILS"), validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,120}$/i } },
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
                        error: t("ENTER_REQ_DETAILS"),
                        required: true,
                        mdmsConfig: {
                            masterName: "GenderType",
                            moduleName: "common-masters",
                            localePrefix: "COMMON_GENDER",
                        }
                    },
                },
                {
                    inline: true,
                    label: t("CORE_COMMON_PHONE_NUMBER"),
                    isMandatory: true,
                    key: "phone",
                    type: "number",
                    disable: false,
                    populators: { name: "phone", error: t("PHONE_VALIDATION"), validation: { min: 5999999999, max: 9999999999 }},
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
                        error: t("ENTER_REQ_DETAILS"),
                        required: true,
                        mdmsConfig: {
                            masterName: "Designation",
                            moduleName: "works",
                            localePrefix: "MASTERS",
                        }
                    },
                },
                {   
                    label: t("PHOTOGRAPH"),
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