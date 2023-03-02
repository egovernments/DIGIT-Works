import Dropdown from '../../Dropdown';
import { Loader } from '../../Loader';
import React, { useState } from 'react'

const configEstimateModal = (
    t,
    action,
    approvers
) => {
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
                        isMandatory: false,
                        disable: false,
                        key:"assignees",
                        populators: {
                            name: "assignee",
                            optionsKey: "nameOfEmp",
                            options: approvers,
                            hideInForm:approvers ? false : true
                        },
                    },
                    {
                        label: t("WF_MODAL_COMMENTS"),
                        type: "textarea",
                        populators: {
                            name: "comments",
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
                            hintText: "WORKS_DOC_UPLOAD_HINT",
                            showHintBelow: true
                        }
                    }
                ]
            }
        ]
    }
}

export default configEstimateModal