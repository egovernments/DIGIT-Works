import { Dropdown, Loader } from '@egovernments/digit-ui-react-components';
import React from 'react'

const getWOModalConfig = ({
    t,
    approvers,
    selectedApprover,
    setSelectedApprover,
    approverLoading = false,
    isModify
}) => {
    let checkConditions = true
    return {
        label: {
            heading: isModify ? 'CONTRACT_UPDATE_FORWARD':'COMMON_CHECK_FORWARD',
            submit: `WORKS_FORWARD`,
            cancel: "WORKS_CANCEL",
        },
        form: [
            {
                body: [
                    {
                        label: !checkConditions ? null : t("WORKS_ASSIGNEE_NAME"),
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
                                optionCardStyles={{"top":"2.3rem"}}
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

export default getWOModalConfig