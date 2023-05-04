import { Card, LabelFieldPair, CardLabel, TextInput, SubmitBar } from '@egovernments/digit-ui-react-components'
import React, { Fragment, useState, useEffect } from 'react'
import SORTable from './SORTable'
const EstimateTemplate = (props) => {
    const { t, register, errors } = props
    const formFieldName = "sorDetailsv1"
    return (
        <>
            <LabelFieldPair>
                <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_SCHEDULE_CATEGORY`)}:`}</CardLabel>
                <TextInput
                    className={"field"}
                    //textInputStyle={{ width: "30%" }}
                    inputRef={register({
                        required: false,
                        //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
                        pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
                    })}
                    name={`${formFieldName}.scheduleCategory`}
                />
            </LabelFieldPair>
            <LabelFieldPair>
                <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_SEARCH_SOR`)}:`}</CardLabel>
                <TextInput
                    className={"field"}
                    placeholder={t("WORKS_SOR_INPUT_PLACEHOLDER")}
                    textInputStyle={{ width: "31%" }}
                    inputRef={register({
                        required: false,
                        //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
                        pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
                    })}
                    name={`${formFieldName}.sor`}
                />
                <div style={{ alignSelf: 'flex-start', marginLeft: "1rem" }} >
                    <SubmitBar
                        label={t("WORKS_ADD")}
                        onSubmit={() => {}}
                    />
                </div>
            </LabelFieldPair>

            <SORTable />
        </>
    )
}

export default EstimateTemplate