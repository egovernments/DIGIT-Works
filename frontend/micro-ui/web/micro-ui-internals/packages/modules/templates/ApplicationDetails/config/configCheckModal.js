import { Dropdown } from '@egovernments/digit-ui-react-components';
import React,{useState} from 'react'

const configCheckModal = ({
    t,
    action,
    businessService,
    approvers,
    selectedApprover,
    setSelectedApprover,
    designation,
    selectedDesignation,
    setSelectedDesignation,
    department,
    selectedDept,
    setSelectedDept
}) => {
    debugger
    let checkConditions = true
    if (action.isTerminateState) checkConditions = false;

    if(designation?.length===0 || approvers?.length===0 || department?.length===0) return {}

    return {
        label: {
            heading: `WORKS_PROCESSINGMODAL_HEADER`,
            submit: `WORKS_FORWARD`,
            cancel: "CS_COMMON_CANCEL",
        },
        form: [
            {
                body:[
                    {
                        label: !checkConditions ? null : t("WORKS_APPROVER_DEPT"),
                        //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                        // isMandatory: false,
                        type: "dropdown",
                        populators: !checkConditions ? null : (
                            <Dropdown
                                option={department}
                                autoComplete="off"
                                optionKey="name"
                                //id="fieldInspector"
                                select={setSelectedDept}
                                selected={selectedDept}
                            />
                        ),
                    },
                    {
                        label: !checkConditions ? null : t("WORKS_APPROVER_DESIGNATION"),
                        //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                        // isMandatory: false,
                        type: "dropdown",
                        populators: !checkConditions ? null : (
                            <Dropdown
                                option={designation}
                                autoComplete="off"
                                optionKey="name"
                                //id="fieldInspector"
                                select={setSelectedDesignation}
                                selected={selectedDesignation}
                            />
                        ),
                    },
                    {
                        label: !checkConditions ? null : t("WORKS_APPROVER"),
                        //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                        // isMandatory: false,
                        type: "dropdown",
                        populators: !checkConditions ? null : (
                            <Dropdown
                                option={approvers}
                                autoComplete="off"
                                optionKey="name"
                                //id="fieldInspector"
                                select={setSelectedApprover}
                                selected={selectedApprover}
                            />
                        ),
                    },
                ]
            }
        ]
    }
}

export default configCheckModal