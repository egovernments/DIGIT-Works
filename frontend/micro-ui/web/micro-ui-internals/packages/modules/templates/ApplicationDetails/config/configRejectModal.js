import { Dropdown,LabelFieldPair,CardLabel} from '@egovernments/digit-ui-react-components';
import React, { useState } from 'react'

const configRejectModal = ({
    t,
    action,
    rejectReasons,
    selectedReason,
    setSelectedReason,
    loiNumber,
    department,
    estimateNumber
}) => {

    let checkConditions = true
    if (action.isTerminateState) checkConditions = false;
    const fieldLabelStyle = {
        "display" : "grid",
        "gridTemplateColumns" : "60% 1fr"
    };
    if(rejectReasons?.length === 0) return {}
    if(action?.applicationStatus === "ATTENDANCE_REJECTED") {
        return {
            label: {
                heading: t("ATM_PROCESSINGMODAL_HEADER"),
                submit: t("ATM_CONFIRM_REJECT"),
                cancel: t("CS_COMMON_CANCEL"),
            },
            form: [
                {
                    body: [
                        {
                            withoutLabel:true,
                            populators: <LabelFieldPair style={fieldLabelStyle}>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold", width: "100%" }}>{t("ATM_DEPARTMENT")}</CardLabel>
                                <CardLabel style={{ width: "100%" }}>{t("ATM_ENGINEERING")}</CardLabel>
                            </LabelFieldPair>,
                        },
                        {
                            withoutLabel:true,
                            populators: <LabelFieldPair  style={fieldLabelStyle}>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold", width: "100%" }}>{t("ATM_DESIGNATION")}</CardLabel>
                                <CardLabel style={{ width: "100%" }}>{t("ATM_JUNIOR_ENGINEER")}</CardLabel>
                            </LabelFieldPair>,
                        },
                        {
                            withoutLabel:true,
                            populators: <LabelFieldPair  style={fieldLabelStyle}>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold", width: "100%" }}>{t("ATM_REJECTED_BY")}</CardLabel>
                                <CardLabel style={{ width: "100%" }}>{"RASHMI"}</CardLabel>
                            </LabelFieldPair>,
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
                comments : "",
            }
        }
    }else if(loiNumber){
        return {
            label: {
                heading: `WORKS_REJECT_LOI`,
                submit: `WORKS_REJECT_LOI`,
                //cancel: "CS_COMMON_CANCEL",
            },
            form: [
                {
                    body: [
                        {
                            withoutLabel:true,
                            populators: <LabelFieldPair>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold" }}>{t("WORKS_DEPARTMENT")}</CardLabel>
                                <CardLabel >{"ENGG"}</CardLabel>
                            </LabelFieldPair>,
                        },
                        {
                            //label: t("WORKS_LOI_ID"),
                            //type: "text",
                            withoutLabel: true,
                            populators: <LabelFieldPair>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold" }}>{t("WORKS_LOI_ID")}</CardLabel>
                                <CardLabel >{loiNumber}</CardLabel>
                            </LabelFieldPair>
                        },
                        {
                            label: !checkConditions ? null : t("WORKS_REJECT_REASON"),
                            //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                            // isMandatory: false,
                            type: "dropdown",
                            populators: !checkConditions ? null : (
                                <Dropdown
                                    option={rejectReasons}
                                    autoComplete="off"
                                    optionKey="name"
                                    select={setSelectedReason}
                                    selected={selectedReason}
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
    }else{
        return {
            label: {
                heading: `WORKS_REJECT_ESTIMATE`,
                submit: `WORKS_REJECT_ESTIMATE`,
                //cancel: "CS_COMMON_CANCEL",
            },
            form: [
                {
                    body: [
                        {
                            withoutLabel:true,
                            populators: <LabelFieldPair>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold" }}>{t("WORKS_DEPARTMENT")}</CardLabel>
                                <CardLabel >{"ENGG"}</CardLabel>
                            </LabelFieldPair>,
                        },
                        {
                            //label: t("WORKS_LOI_ID"),
                            //type: "text",
                            withoutLabel: true,
                            populators: <LabelFieldPair>
                                <CardLabel style={{ "fontSize": "16px", fontWeight: "bold" }}>{t("WORKS_ESTIMATE_ID")}</CardLabel>
                                <CardLabel style={{ width:"100%", marginLeft:"30px" }}>{estimateNumber}</CardLabel>
                            </LabelFieldPair>
                        },
                        {
                            label: !checkConditions ? null : t("WORKS_REJECT_REASON"),
                            //placeholder: !checkConditions ? null : t("WF_ASSIGNEE_NAME_PLACEHOLDER"),
                            // isMandatory: false,
                            type: "dropdown",
                            populators: !checkConditions ? null : (
                                <Dropdown
                                    option={rejectReasons}
                                    autoComplete="off"
                                    optionKey="name"
                                    select={setSelectedReason}
                                    selected={selectedReason}
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

export default configRejectModal