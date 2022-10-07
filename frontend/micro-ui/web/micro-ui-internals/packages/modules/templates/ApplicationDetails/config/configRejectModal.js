import { Dropdown,LabelFieldPair,CardLabel} from '@egovernments/digit-ui-react-components';
import React, { useState } from 'react'

const configRejectModal = ({
    t,
    action,
    rejectReasons,
    selectedReason,
    setSelectedReason,
    loiNumber,
    department
}) => {

    let checkConditions = true
    if (action.isTerminateState) checkConditions = false;

    if(rejectReasons?.length === 0) return {}

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
}

export default configRejectModal