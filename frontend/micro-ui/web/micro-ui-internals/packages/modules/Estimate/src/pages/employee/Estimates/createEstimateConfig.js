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
                head: "WORKS_ESTIMATE_TEMPLATE",
                subHead: "",
                navLink: "Work Details",
                body: [
                    {
                        type: "component",
                        component: "EstimateTemplate",
                        withoutLabel: true,
                        key: "estimateTemplateDetails",
                    }
                ]
            },
            {
                head: "WORKS_SOR",
                subHead: "",
                navLink: "Work Details",
                body: [
                    {
                        type: "component",
                        component: "SOR",
                        withoutLabel: true,
                        key: "sorDetails",
                    }
                ]
            },
            {
                head: "WORKS_NON_SOR",
                subHead: "",
                navLink: "Work Details",
                body: [
                    {
                        type: "component",
                        component: "NonSORTable",
                        withoutLabel: true,
                        key: "nonSORDetails",
                    }
                ]
            },
            {
                head: "WORKS_OVERHEADS",
                subHead: "",
                navLink: "Work Details",
                body: [
                    {
                        type: "component",
                        component: "OverheadsTable",
                        withoutLabel: true,
                        key: "overheadsDetails",
                    }
                ]
            },
            {
                navLink:"Work Details",
                head: "WORKS_RELEVANT_DOCS",
                body: [
                    {
                        label: "WORKS_UPLOAD_FILES",
                        isMandatory: true,
                        key: "document",
                        type: "multiupload",
                        disable: false,
                        populators: { name: "uploads" },
                    },
                ],
            },
            
        ]
    }
}