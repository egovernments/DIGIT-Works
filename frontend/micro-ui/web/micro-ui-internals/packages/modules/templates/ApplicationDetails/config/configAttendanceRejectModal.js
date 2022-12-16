import { LabelFieldPair,CardLabel} from '@egovernments/digit-ui-react-components';
import React from 'react'

const configAttendanceRejectModal = ({
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

}

export default configAttendanceRejectModal