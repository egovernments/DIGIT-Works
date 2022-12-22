import React, { Fragment } from "react"
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, t, onClearSearch }) => {
    const tenant = Digit.ULBService.getStateId()
    let validation = {} 
    return (
        <>
            <SearchField>
                <label>{t("MASTERS_ORGANISATION")}</label>
                <Controller
                    control={control}
                    name="organisation"
                    render={(props) => (
                        <Dropdown
                            option={[]}
                            selected={props?.value}
                            optionKey={"name"}
                            t={t}
                            select={props?.onChange}
                            onBlur={props.onBlur}
                        />
                    )}
                />
            </SearchField>
            <SearchField className="submit">
                <div style={{"display":"flex","flexDirection":"row"}}>
                <p style={{"marginTop":"8px"}} onClick={() => {
                    reset({
                        nameOfTheOrg: "",
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