import React from "react";


const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

export const createEstimateConfig = () => {

    return {
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
                head: "",
                subHead: "",
                navLink: "Work Details",
                body: [
                    {
                        type: "component",
                        component: "TotalEstAmount",
                        withoutLabel: true,
                        key: "totalEstimatedAmount",
                    }
                ]
            },
            {
                head: "",
                subHead: "",
                navLink: "Work Details",
                body: [
                    {
                        type: "component",
                        component: "LabourAnalysis",
                        withoutLabel: true,
                        key: "labourMaterialAnalysis",
                    }
                ]
            },
            // {
            //     navLink:"Work Details",
            //     head: "WORKS_RELEVANT_DOCS",
            //     body: [
            //         {
            //             label: "WORKS_UPLOAD_FILES",
            //             isMandatory: true,
            //             key: "document",
            //             type: "multiupload",
            //             disable: false,
            //             populators: { name: "uploads", maxFilesAllowed: 5, hintText: "WORKS_DOC_UPLOAD_HINT", allowedMaxSizeInMB: 5, allowedFileTypesRegex: /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i, showHintBelow:true },
            //         },
            //     ],
            // },
            {
                "navLink": "Work Details",
                "head": "",
                "body": [
                    {
                        "type": "documentUpload",
                        "withoutLabel": true,
                        "module": "Estimate",
                        "error": "WORKS_REQUIRED_ERR",
                        "name": "uploadedDocs",
                        "customClass": "",
                        "localePrefix": "ESTIMATE_DOC"
                    }
                ]
            }
        ]
    }
}