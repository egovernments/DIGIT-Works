import { Dropdown,Loader } from '@egovernments/digit-ui-react-components';
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
    setSelectedDept,
    approverLoading=false,
}) => {

    let checkConditions = true
    if (action.isTerminateState) checkConditions = false;

    if(designation?.length===0 || department?.length===0) return {}

    if(action?.applicationStatus === "ATTENDANCE_CHECKED") {
        return {
            label: {
                heading: t("ATM_PROCESSINGMODAL_HEADER"),
                submit: t("ATM_FORWARD_FOR_CHECK"),
                cancel: t("WORKS_CANCEL"),
            },
            form: [
                {
                    body:[
                        {
                            isMandatory: true,
                            key : "department",
                            type: "radioordropdown",
                            label: !checkConditions ? null : t("ATM_APPROVER_DEPT"),
                            disable: false,
                            populators: {
                              name: "department",
                              optionsKey: "i18nKey",
                              error: "Department is required",
                              required: true,
                              options: department,
                            },
                        },
                        {
                            isMandatory: true,
                            key : "designation",
                            type: "radioordropdown",
                            label: !checkConditions ? null : t("ATM_APPROVER_DESIGNATION"),
                            disable: false,
                            populators: {
                              name: "designation",
                              optionsKey: "i18nKey",
                              error: "Designation is required",
                              required: true,
                              options: designation
                            },
                        },
                        {
                            isMandatory: true,
                            key : "approvers",
                            type: "radioordropdown",
                            label: !checkConditions ? null : t("WORKS_APPROVER"),
                            disable: false,
                            populators: {
                              name: "approvers",
                              optionsKey: "nameOfEmp",
                              error: "Designation is required",
                              required: true,
                              options: approvers
                            },
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
            ],
            defaultValues : {
                department : "",
                designation : "",
                approvers : "",
                comments : "",
            }
        }
    }else {
        return {
            label: {
                heading: `WORKS_CHECK_FORWARD`,
                submit: `WORKS_FORWARD_FOR_APPROVAL`,
                cancel: "WORKS_CANCEL",
            },
            form: [
                {
                    body:[
                        {
                            label: !checkConditions ? null : t("WORKS_APPROVER_DEPT"),
                            placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                            isMandatory: true,
                            type: "dropdown",
                            populators: !checkConditions ? null : (
                                <Dropdown
                                    option={department}
                                    autoComplete="off"
                                    optionKey="i18nKey"
                                    id="sdf"
                                    select={(val) => { 
                                        setSelectedDept(val)
                                        setSelectedApprover("")
                                        //setValue()
                                    }}
                                    selected={selectedDept}
                                    t={t}
                                />
                            ),
                        },
                        {
                            label: !checkConditions ? null : t("WORKS_APPROVER_DESIGNATION"),
                            //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                            isMandatory: true,
                            type: "dropdown",
                            populators: !checkConditions ? null : (
                                <Dropdown
                                    option={designation}
                                    autoComplete="off"
                                    optionKey="i18nKey"
                                    id="name2"
                                    select={(val)=>{
                                        setSelectedDesignation(val)
                                        setSelectedApprover("")
                                        //resetting approver dropdown when dept/designation changes
                                    }}
                                    selected={selectedDesignation}
                                    t={t}
                                />
                            ),
                        },
                        {
                            label: !checkConditions ? null : t("WORKS_APPROVER"),
                            //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                            isMandatory: true,
                            type: "dropdown",
                            populators: !checkConditions ? null : (
                                approverLoading ? <Loader/> : <Dropdown
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

}

export default configCheckModal