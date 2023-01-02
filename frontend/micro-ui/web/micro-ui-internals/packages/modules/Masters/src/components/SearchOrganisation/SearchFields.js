import React, { Fragment } from "react"
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, t, onClearSearch, options }) => {
    const tenant = Digit.ULBService.getStateId()
    return (
        <>
            <SearchField>
                <label>{t("MASTERS_MASTERS")}</label>
                <Controller
                    control={control}
                    name="nameOfTheOrg"
                    render={(props) => (
                        <Dropdown
                            option={options}
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
                }}>{t(`MASTERS_CLEAR_SEARCH_LINK`)}</p>
                <SubmitBar label={t("WORKS_COMMON_SEARCH")} submit />
                </div>
            </SearchField>
        </>
    )
}

export default SearchFields