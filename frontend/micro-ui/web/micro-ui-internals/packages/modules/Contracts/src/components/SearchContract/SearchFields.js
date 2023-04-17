import React, { Fragment } from "react"
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, t, onClearSearch }) => {
    const tenant = Digit.ULBService.getStateId()
    let validation = {} 
    return (
        <>
            <SearchField>
                <label>{t("WORKS_PROJECT_NAME")}</label>
                <TextInput
                    name="nameOfTheProject"
                    inputRef={register()}
                    {...(validation = {
                        isRequired: false,
                        pattern: "^[a-zA-Z0-9-_\/]*$",
                        type: "text",
                        title: t("ERR_INVALID_PROJECT_NAME"),
                    })}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_CONTRACT_ID")}</label>
                <TextInput
                    name="contractId"
                    inputRef={register()}
                    {...(validation = {
                        isRequired: false,
                        pattern: "^[a-zA-Z0-9-_\/]*$",
                        type: "text",
                        title: t("ERR_INVALID_CONTRACT_ID"),
                    })}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_ESTIMATE_NO")}</label>
                <TextInput
                    name="estimateNumber"
                    inputRef={register()}
                    {...(validation = {
                        isRequired: false,
                        pattern: "^[a-zA-Z0-9-_\/]*$",
                        type: "text",
                        title: t("ERR_INVALID_ESTIMATE_NO"),
                    })}
                />
            </SearchField>
            <SearchField/>
            <SearchField>
                <label>{t("WORKS_CONTRACT_FROM_DATE")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="fromProposalDate"
                    control={control}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_CONTRACT_TO_DATE")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="toProposalDate"
                    control={control}
                />
            </SearchField>
            <SearchField/>
            <SearchField className="submit">
                <div style={{"display":"flex","flexDirection":"row"}}>
                <p style={{"marginTop":"8px"}} onClick={() => {
                    reset({
                        nameOfTheProject: "",
                        fromProposalDate: "",
                        toProposalDate: "",
                        contractId: "",
                        estimateNumber: "",
                        offset: 0,
                        limit: 10,
                        sortOrder: "DESC"
                    });
                    onClearSearch(false)
                }}>{t(`CLEAR_SEARCH_LINk`)}</p>
                <SubmitBar label={t("WORKS_COMMON_SEARCH")} submit />
                </div>
            </SearchField>
        </>
    )
}

export default SearchFields