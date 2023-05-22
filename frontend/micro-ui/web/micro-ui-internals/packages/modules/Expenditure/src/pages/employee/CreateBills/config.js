import { Dropdown, Loader } from '@egovernments/digit-ui-react-components';
import React, { useState } from 'react'

const getModalConfig = ({
    t,
    approvers,
    selectedApprover,
    setSelectedApprover,
    approverLoading = false,
}) => {

    let checkConditions = true



    return {
        label: {
            heading: `EXP_CREATE_PURCHASE_BILL`,
            submit: `CORE_COMMON_SUBMIT`,
            cancel: "WORKS_CANCEL",
        },
        form: [
            {
                body: [
                    {
                        label: !checkConditions ? null : t("WORKS_ASSIGNEE_NAME"),
                        //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                        isMandatory: false,
                        type: "goToDefaultCase",
                        populators: !checkConditions ? null : (
                            approverLoading ? <Loader /> : <Dropdown
                                name={"approver"}
                                option={approvers}
                                autoComplete="off"
                                optionKey="nameOfEmp"
                                id="fieldInspector"
                                select={setSelectedApprover}
                                selected={selectedApprover}
                            />
                        ),
                    },
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

export default getModalConfig