import { Dropdown } from '@egovernments/digit-ui-react-components';
import React, { useState } from 'react'

const configApproveModal = ({
    t,
    action
}) => {
    if(action?.action === 'ADMINSANCTION'){
    return {
        label: {
            heading: `WORKS_APPROVE_ESTIMATE`,
            submit: `WORKS_APPROVE_ESTIMATE`,
            cancel: "CS_COMMON_CANCEL",
        },
        form: [
            {
                body: [
                    {
                        label: t("WF_COMMON_COMMENTS"),
                        type: "textarea",
                        populators: {
                            name: "comments",
                        },
                    },
                ]
            }
        ]
    }

    }else if(action?.applicationStatus === 'ATTENDANCE_APPROVE') {
        return {
            label: {
                heading: t("ATM_PROCESSINGMODAL_HEADER"),
                submit: t("ATM_FORWARD_FOR_APPROVAL"),
                cancel: t("CS_COMMON_CANCEL"),
            },
            form: [
                {
                    body: [
                        {
                            label: t("WF_COMMON_COMMENTS"),
                            type: "textarea",
                            populators: {
                                name: "comments",
                            },
                        },
                    ]
                }
            ],
            defaultValues : {
                comments : "",
            }
        }
    }else
    return {
        label: {
            heading: `WORKS_APPROVE_LOI`,
            submit: `WORKS_APPROVE_LOI`,
            cancel: "CS_COMMON_CANCEL",
        },
        form: [
            {
                body: [
                    {
                        label: t("WF_COMMON_COMMENTS"),
                        type: "textarea",
                        populators: {
                            name: "comments",
                        },
                    },
                ]
            }
        ]
    }
}

export default configApproveModal