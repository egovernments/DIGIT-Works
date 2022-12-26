import { useTranslation } from "react-i18next";

const Vendors = [
    {
        name: 'Vendor1',
        code: 'vendor1',
        i18nKey: 'vendor1'
    },
    {
        name: 'Vendor2',
        code: 'vendor2',
        i18nKey: 'vendor2'
    }
]
export const addVendorBillConfig = () => {
    const { t } = useTranslation()

    return {
        label: {
            submit: t("CS_COMMON_PROCEED")
        }, 
        form: [
            {
                head: t("EXP_ADD_VENDOR_BILL"),
                body: [
                {
                    isMandatory: true,
                    key: "vendor",
                    type: "dropdown",
                    label: t("VENDOR"),
                    disable: false,
                    populators: {
                        name: "vendor",
                        optionsKey: "i18nKey",
                        error: t("WORKS_REQUIRED_ERR"),
                        required: true,
                        options: Vendors
                    },
                },,
                {
                    inline: true,
                    label:t("VENDOR_ID"),
                    isMandatory: false,
                    key: "vendor_id",
                    type: "text",
                    disable: true,
                    populators: { 
                        name: "vendor_id"
                    }
                },
                {
                    inline: true,
                    label: t("EXP_BILL_AMOUNT"),
                    isMandatory: true,
                    key: "bill_amount",
                    type: "number",
                    disable: false,
                    populators: { name: "bill_amount", error: t("WORKS_REQUIRED_ERR")},
                },
                {
                    isMandatory: true,
                    key: "amount_paid_to",
                    type: "radio",
                    label: t("EXP_BILL_AMOUNT_PAID_TO"),
                    disable: false,
                    populators: {
                        name: "amount_paid_to",
                        optionsKey: "name",
                        error: t("WORKS_REQUIRED_ERR"),
                        mdmsConfig: {
                            masterName: "OrganisationType",
                            moduleName: "works",
                            localePrefix: "EXP",
                        }
                    },
                },
                {
                    type:"multiupload",
                    label: t("WORKS_UPLOAD_FILES"),
                    populators:{
                        name: "upload_files",
                        allowedMaxSizeInMB:2,
                        maxFilesAllowed:3,
                        allowedFileTypes : /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i,
                    }
                }
                ],
            }
        ]
    }

}