    export const createProjectConfigMUKTA = {
        "tenantId" : "pg",
        "moduleName" : "commonUiConfig",
        "CreateProjectConfig" : {
            "form" : 
                [
                {
                head: "",
                subHead: "",
                body: [
                    {
                        inline: true,
                        label: "PDF_STATIC_LABEL_ESTIMATE_PROPOSAL_DATE",
                        isMandatory: false,
                        key: "basicDetails_dateOfProposal",
                        type: "date",
                        disable: false,
                        populators: { name: "basicDetails_dateOfProposal" },
                    },
                    {
                        inline: true,
                        label: "PDF_STATIC_LABEL_ESTIMATE_PROJECT_NAME",
                        isMandatory: true,
                        key: "basicDetails_projectName",
                        type: "text",
                        disable: false,
                        preProcess : {
                            translate : "populators.error"
                        },
                        populators: { name: "basicDetails_projectName", error: "PROJECT_PATTERN_ERR_MSG_PROJECT_NAME", validation: { pattern: /^[^\$\"<>?\\\\~`!@$%^()+={}\[\]*:;“”‘’]{1,50}$/i, minlength : 2 }}
                    },
                    {
                        inline: true,
                        label: "PROJECT_DESC",
                        isMandatory: false,
                        key: "basicDetails_projectDesc",
                        type: "text",
                        disable: false,
                        preProcess : {
                            translate : "populators.error"
                        },
                        populators: { name: "basicDetails_projectDesc", error: "PROJECT_PATTERN_ERR_MSG_PROJECT_DESC", validation: { pattern: /^[^{0-9}^\$\"<>?\\\\~!@#$%^()+={}\[\]*,/_:;“”‘’]{1,50}$/i, minlength : 2 }}
                    },
                    {
                        isMandatory: false,
                        key: "basicDetails_hasSubProjects",
                        type: "radio",
                        label: "WORKS_HAS_SUB_PROJECT_LABEL",
                        disable: false,
                        populators: {
                            name: "basicDetails_hasSubProjects",
                            optionsKey: "name",
                            error: "Required",
                            required: false,
                            options: [
                            {
                                code: "COMMON_YES",
                                name: "COMMON_YES",
                            },
                            {
                                code: "COMMON_NO",
                                name: "COMMON_NO",
                            }
                            ],
                        },
                    },
                    {
                        key: "noSubProject_estimatedCostInRs",
                        type: "component",
                        component: "SubProjectDetailsTable",
                        withoutLabel: true,
                      },
                ]
                },
            ]
        }
    }