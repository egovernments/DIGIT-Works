import React from "react";
import { useTranslation } from "react-i18next";

export const createEstimateConfig = (t) => {

    return {
        defaultValues:{},
        form:[
            {
                head:"",
                subHead:"",
                navLink: "Project Details",
                body:[
                    {
                        type: "component",
                        component: "ViewProject",
                        withoutLabel: true,
                        key: "projectDetails",
                    }
                ]
            },
            {
                navLink: "Work Details",
                head: t("WORK DETAILS"),
                body: [
                    {
                        isMandatory: true,
                        key: "typeOfWork",
                        type: "radioordropdown",
                        label: "PROJECT_TYPE_OF_WORK",
                        disable: false,
                        populators: {
                            name: "typeOfWork",
                            optionsKey: "name",
                            error: t("WORKS_REQUIRED_ERR"),
                            required: true,
                            optionsCustomStyle: {
                                top: "2.5rem"
                            },
                            mdmsConfig: {
                                masterName: "WageSeekerSkills",
                                moduleName: "common-masters",
                                localePrefix: "MASTERS",
                            },
                        },
                    },
                ]
            }
        ]
    }
}