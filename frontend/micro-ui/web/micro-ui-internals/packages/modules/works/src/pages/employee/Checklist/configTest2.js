import { RadioButtons, MultiUploadWrapper, CheckBox } from "@egovernments/digit-ui-react-components";
import { Controller, useForm } from "react-hook-form";
import React from "react";

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

const surveyConfig = {
    "uuid": "SY-2022-12-05-000583",
    "tenantIds": null,
    "title": "Kickoff Checklist",
    "status": "ACTIVE",
    "description": "",
    "questions": [
        {
            "uuid": "d1283bf8-f8ca-4a50-bfea-c9f97b811c7a",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "Is the site handed over?",
            "options": [
                "Yes",
                "No"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "MULTIPLE_ANSWER_TYPE",
            "required": false,
            "qorder": 1
        },
        {
            "uuid": "5599e0b1-585b-444d-9a6e-499d2c97e635",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "When is the project Start Date?",
            "options": [
                "NA"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "DATE_ANSWER_TYPE",
            "required": false,
            "qorder": 2
        },
        {
            "uuid": "b9ef3681-4e9f-4ce1-be46-66795e0b8bb1",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "When is the Labour Start Date?",
            "options": [
                "NA"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "DATE_ANSWER_TYPE",
            "required": false,
            "qorder": 3
        },
        {
            "uuid": "0785c388-bcd8-4faf-b7f6-1f2c7f2205c2",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": " Is the first aid available?",
            "options": [
                "Yes",
                "No"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "MULTIPLE_ANSWER_TYPE",
            "required": false,
            "qorder": 4
        },
        {
            "uuid": "f2647b72-cea1-4732-a51f-fca190b0f327",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "Is the rest shade available?",
            "options": [
                "Yes",
                "No"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "MULTIPLE_ANSWER_TYPE",
            "required": false,
            "qorder": 5
        },
        {
            "uuid": "87cb542e-417a-4367-bd93-fe89a2fc1ac1",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "Are the Water Facilities available?",
            "options": [
                "Yes",
                "No"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "MULTIPLE_ANSWER_TYPE",
            "required": false,
            "qorder": 6
        },
        {
            "uuid": "7104ddb9-c272-4190-b42b-aa1d40393941",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "Upload Site Photos",
            "options": [
                "NA"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "upload",
            "required": false,
            "qorder": 7
        }
    ],
    "startDate": 1670226180000,
    "endDate": 1670658180000,
    "postedBy": "Jagan",
    "auditDetails": {
        "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
        "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
        "createdTime": 1670226215380,
        "lastModifiedTime": 1670226215380
    },
    "tenantId": "pb.amritsar",
    "active": true,
    "answersCount": 0,
    "hasResponded": null
}
const closureChecklistConfig = {
    "uuid": "SY-2022-12-08-000584",
    "tenantIds": null,
    "title": "Closure Checklist",
    "status": "ACTIVE",
    "description": "",
    "questions": [
        {
            "uuid": "c44c558b-9d84-4b1b-badd-7d74a8615d86",
            "surveyId": "SY-2022-12-08-000584",
            "questionStatement": "What is the labour end date?",
            "options": [
                "NA"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670483418270,
                "lastModifiedTime": 1670483418270
            },
            "status": null,
            "type": "DATE_ANSWER_TYPE",
            "required": false,
            "qorder": 1
        },
        {
            "uuid": "d2bf6fbf-a517-4fe9-97e2-025698d3ddf4",
            "surveyId": "SY-2022-12-08-000584",
            "questionStatement": "Are All Muster rolls submitted?",
            "options": [
                "Yes",
                "No"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670483418270,
                "lastModifiedTime": 1670483418270
            },
            "status": null,
            "type": "MULTIPLE_ANSWER_TYPE",
            "required": false,
            "qorder": 2
        },
        {
            "uuid": "ce692883-3d08-45ae-b4c3-e9e474a4b62a",
            "surveyId": "SY-2022-12-08-000584",
            "questionStatement": "Is the final Bill submitted?",
            "options": [
                "Yes",
                "No"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670483418270,
                "lastModifiedTime": 1670483418270
            },
            "status": null,
            "type": "MULTIPLE_ANSWER_TYPE",
            "required": false,
            "qorder": 3
        },
        {
            "uuid": "7104ddb9-c272-4190-b42b-aa1d40393942",
            "surveyId": "SY-2022-12-05-000583",
            "questionStatement": "Upload Site Photos",
            "options": [
                "NA"
            ],
            "auditDetails": {
                "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
                "createdTime": 1670226215380,
                "lastModifiedTime": 1670226215380
            },
            "status": null,
            "type": "upload",
            "required": false,
            "qorder": 4
        }
    ],
    "startDate": 1670483400000,
    "endDate": 1670656200000,
    "postedBy": "Jagan",
    "auditDetails": {
        "createdBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
        "lastModifiedBy": "90fed312-d88a-4a8a-b411-fe682e5b28cd",
        "createdTime": 1670483418270,
        "lastModifiedTime": 1670483418270
    },
    "tenantId": "pb.amritsar",
    "active": true,
    "answersCount": 0,
    "hasResponded": null
}

// const [file, setFile] = useState(null);

// const getData = (state) => {
//     let data = Object.fromEntries(state);
//     let newArr = Object.values(data);
//     selectfile(newArr[newArr.length - 1]);
// }


// function selectfile(e) {
//     e && setFile(e.file);
// }

const getConfig = (question, t, index) => {

    switch (question.type) {
        //Yes / No
        case "MULTIPLE_ANSWER_TYPE":
            return {
                isMandatory: true,
                key: question.uuid,
                type: "radio",
                label: `${index + 1}. ${question?.questionStatement}`,
                disable: false,
                populators: {
                    name: question.uuid,
                    optionsKey: "name",
                    error: "Required",
                    required: true,
                    options: [
                        {
                            code: "YES",
                            name: "Yes",
                        },
                        {
                            code: "NO",
                            name: "No",
                        },
                    ],
                },
            }
        case "DATE_ANSWER_TYPE":
            return {
                inline: true,
                label: `${index + 1}. ${question?.questionStatement}`,
                isMandatory: true,
                //description: "Field supporting description",
                type: "date",
                disable: false,
                populators: { name: question.uuid, error: "Required", validation: { required: true } },
            }
        case "upload":
            return {
                type: "multiupload",
                label: `${index + 1}. ${question?.questionStatement}`,
                populators: {
                    name: question.uuid,
                    allowedMaxSizeInMB: 2,
                    maxFilesAllowed: 4,
                    hintText: t("WORKS_DOC_UPLOAD_HINT_2MB"),
                    allowedFileTypes: /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i,

                }
            }

        default:
            return {
                isMandatory: true,
                key: "opt",
                type: "radio",
                label: question?.questionStatement,
                disable: false,
                populators: {
                    name: question.uuid,
                    optionsKey: "name",
                    error: "sample required message",
                    required: true,
                    options: [
                        {
                            code: "YES",
                            name: "Yes",
                        },
                        {
                            code: "NO",
                            name: "No",
                        },
                    ],
                },
            }
    }
}

export const configChecklistTest2 = (t, setShowForm, showForm) => {
    const handleChange = (props) => {
        setShowForm((prevState) => !prevState)
    }
    return {
        form: [
            //every object in this array is a section and incase of multiple cards every section is rendered in a separate card
            //now we want to render a horizontal navigation above the Card to render different sections based on the active link in the horizontal nav
            //so we have to add multiple sub sections somehow inside the body property, the way it is done is by adding the navLink property 
            // finally -> so every object in this form array will always be a separate card when showMultiple cards prop is true the only difference will be if navLink is set to be some applicable link it'll be part of horizontal nav flow otherwise it'll just be a separate card 
            // Note -> you will have to send the list of all the links as a direct prop to formComposer
            {
                head: t("WORKS_KICKOFF_CHECKLIST"),
                body: surveyConfig.questions.map((question, index) => getConfig(question, t, index)),
                //navLink:"Estimation"
            },
            {
                //navLink:"Closure",
                head: t("WORKS_CLOSURE_CHECKLIST"),
                body: showForm ? [
                    {
                        populators: (
                            <CheckBox
                                onChange={handleChange}
                                checked={showForm}
                                label={t("WORKS_PROJECT_COMPLETED")}
                            />
                        ),
                        hideContainer: true
                    },
                    ...closureChecklistConfig.questions.map((question, index) => getConfig(question, t, index))
                ] : [
                    {
                        populators: (
                            <CheckBox
                                onChange={handleChange}
                                checked={showForm}
                                label={t("WORKS_PROJECT_COMPLETED")}
                                name={"Checkbox"}
                            />
                        ),
                        hideContainer: true
                    },
                ],
            },
            // {
            //     //navLink: "Closure",
            //     head: t("WORKS_CLOSURE_CHECKLIST"),
            //     body: showForm ? [
            //         {
            //             populators: (
            //                 <CheckBox
            //                     onChange={handleChange}
            //                     checked={showForm}
            //                     label={t("WORKS_PROJECT_COMPLETED")}
            //                 />
            //             ),
            //             hideContainer: true
            //         },
            //         ...closureChecklistConfig.questions.map((question, index) => getConfig(question, t, index))
            //     ] : [
            //         {
            //             populators: (
            //                 <CheckBox
            //                     onChange={handleChange}
            //                     checked={showForm}
            //                     label={t("WORKS_PROJECT_COMPLETED")}
            //                     name={"Checkbox"}
            //                 />
            //             ),
            //             hideContainer: true
            //         },
            //     ],
            // }
        ]
    }
}

