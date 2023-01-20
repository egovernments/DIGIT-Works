import React from "react";


const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

export const createEstimateConfig = (t) => {

    return {
        // defaultValues:{},
        defaultValues: {
            "estimateTemplateDetailsv1": {
                "templateCode": "template code 1"
            },
            "sorDetailsv1": {
                "scheduleCategory": "Schedule Category 1",
                "sor": "SOR 1"
            },
            "nonSORTablev1": [
                null,
                {
                    "description": "work 1",
                    "uom": "3",
                    "rate": "21",
                    "estimatedQuantity": "211",
                    "estimatedAmount": "1211",
                    "total": "211221"
                },
                {
                    "description": "work 2 ",
                    "uom": "21",
                    "rate": "2121",
                    "estimatedQuantity": "21121",
                    "estimatedAmount": "21121",
                    "total": "21121"
                }
            ],
            "overheadDetails": [
                null,
                {
                    "name": "overhead 1 ",
                    "percentage": "32",
                    "amount": "122"
                },
                {
                    "name": "overhead 2 ",
                    "percentage": "32",
                    "amount": "321"
                }
            ],
            "uploads": [
                [
                    "consumerCode-WS_107_2020-21_218051.pdf",
                    {
                        "file": {},
                        "fileStoreId": {
                            "fileStoreId": "caa3a801-5735-4a7e-a77e-92ca59df0bcf",
                            "tenantId": "pb.amritsar"
                        }
                    }
                ]
            ]
        },
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
            // {
            //     head: "WORKS_ESTIMATE_TEMPLATE",
            //     subHead: "",
            //     navLink: "Work Details",
            //     body: [
            //         {
            //             type: "component",
            //             component: "EstimateTemplate",
            //             withoutLabel: true,
            //             key: "estimateTemplateDetails",
            //         }
            //     ]
            // },
            // {
            //     head: "WORKS_SOR",
            //     subHead: "",
            //     navLink: "Work Details",
            //     body: [
            //         {
            //             type: "component",
            //             component: "SOR",
            //             withoutLabel: true,
            //             key: "sorDetails",
            //         }
            //     ]
            // },
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
                        populators: { name: "uploads", maxFilesAllowed: 5, hintText: t("WORKS_DOC_UPLOAD_HINT"), allowedMaxSizeInMB: 5, allowedFileTypesRegex: allowedFileTypes, showHintBelow:true },
                    },
                ],
            },
            
        ]
    }
}