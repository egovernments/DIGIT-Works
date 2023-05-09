import React, { Fragment } from 'react'
import {
    Card,
    CardSectionHeader,
    LabelFieldPair,
    CardLabel,
} from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next'

const ViewLabourAnalysis = ({ detail,...props }) => {
    const { t } = useTranslation()

    return (
        <div style={{ marginTop: "2rem" }}>
            <LabelFieldPair>
                <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }} >{`${t(detail?.value?.[0]?.title)}`}</CardLabel>
                <CardLabel style={{ fontSize: "16px" }} >
                    {`${detail?.value?.[0]?.value}`}
                </CardLabel>
            </LabelFieldPair>
            <LabelFieldPair>
                <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(detail?.value?.[1]?.title)}`}</CardLabel>
                <CardLabel style={{ fontSize: "16px" }}>
                    {`${detail?.value?.[1]?.value}`}
                </CardLabel>
            </LabelFieldPair>
        </div>
    )
}

export default ViewLabourAnalysis