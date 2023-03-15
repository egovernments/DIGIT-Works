import Dropdown from '../../Dropdown';
import { Loader } from '../../Loader';
import React, { useState } from 'react'

const configEstimateModal = (
    t,
    action,
    approvers,
    businessService
) => {
    const {action:actionString} = action

    const configMap = {
        "estimate-approval-5": {
            "default":{
                comments:{
                    isMandatory:false,
                    show:true,
                },
                assignee:{
                    isMandatory:false,
                    show:true
                },
                upload:{
                    isMandatory:false,
                    show:true
                }
            },
            "REJECT": {
                comments: {
                    isMandatory: true,
                    show: true,
                },
                assignee: {
                    isMandatory: false,
                    show: false
                },
                upload: {
                    isMandatory: false,
                    show: true
                }
            },
            "SENDBACK": {
                comments: {
                    isMandatory: false,
                    show: true,
                },
                assignee: {
                    isMandatory: false,
                    show: false
                },
                upload: {
                    isMandatory: false,
                    show: true
                }
            },
            "SENDBACKTOORIGINATOR": {
                comments: {
                    isMandatory: false,
                    show: true,
                },
                assignee: {
                    isMandatory: false,
                    show: false
                },
                upload: {
                    isMandatory: false,
                    show: true
                }
            },
            "APPROVE": {
                comments: {
                    isMandatory: false,
                    show: true,
                },
                assignee: {
                    isMandatory: false,
                    show: false
                },
                upload: {
                    isMandatory: false,
                    show: true
                }
            },


        }
    }
//field can have (comments,assignee,upload)
    const fetchIsMandatory = (field) => {
        
        if(configMap?.[businessService]?.[actionString]){
            console.log(configMap?.[businessService]?.[actionString]?.[field]?.isMandatory);
            return configMap?.[businessService]?.[actionString]?.[field]?.isMandatory ? configMap?.[businessService]?.[actionString]?.[field]?.isMandatory : false
        }else{
            console.log(configMap?.[businessService]?.default?.[field]?.isMandatory);
            return configMap?.[businessService]?.default?.[field]?.isMandatory ? configMap?.[businessService]?.default?.[field]?.isMandatory: false
        }
    }
    const fetchIsShow = (field) => {
        
        if (configMap?.[businessService]?.[actionString]) {
            console.log(configMap?.[businessService]?.[actionString]?.[field]?.show);
           return configMap?.[businessService]?.[actionString]?.[field]?.show ? configMap?.[businessService]?.[actionString]?.[field]?.show : false
        } else {
            console.log(configMap?.[businessService]?.default?.[field]?.show);
            return configMap?.[businessService]?.default?.[field]?.show ? configMap?.[businessService]?.default?.[field]?.show:false
        }
        
    }

    return {
        label: {
            heading: `WF_MODAL_HEADER_ESTIMATE_${action.action}`,
            submit: `WF_MODAL_SUBMIT_ESTIMATE_${action.action}`,
            cancel: "WF_MODAL_CANCEL",
        },
        form: [
            {
                body: [
                    {
                        label: t("WF_MODAL_APPROVER"),
                        type: "dropdown",
                        // isMandatory: true,
                        isMandatory: fetchIsMandatory("assignee"),
                        disable: false,
                        key:"assignees",
                        populators: {
                            name: "assignee",
                            optionsKey: "nameOfEmp",
                            options: approvers,
                            // hideInForm:approvers ? false : true
                            hideInForm: !fetchIsShow("assignee")
                        },
                    },
                    {
                        label: t("WF_MODAL_COMMENTS"),
                        type: "textarea",
                        isMandatory: fetchIsMandatory("comments"),
                        populators: {
                            name: "comments",
                            hideInForm:!fetchIsShow("comments")
                        },
                    },
                    {
                        type: "multiupload",
                        label: t("WORKFLOW_MODAL_UPLOAD_FILES"),
                        populators: {
                            name: "documents",
                            allowedMaxSizeInMB: 5,
                            maxFilesAllowed: 2,
                            allowedFileTypes: /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i,
                            customClass: "upload-margin-bottom",
                            errorMessage: t("WORKS_FILE_UPLOAD_CUSTOM_ERROR_MSG"),
                            hintText: "WORKFLOW_MODAL_UPLOAD_HINT_TEXT",
                            showHintBelow: true,
                            hideInForm:!fetchIsShow("upload")
                        }
                    }
                ]
            }
        ]
    }
}

export default configEstimateModal