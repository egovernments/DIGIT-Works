import React, { Fragment } from "react"
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, t }) => {
    const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
        "pb",
        "works",
        [
            {
                "name": "TypeOfWork"
            },
            {
                "name": "Department"
            }
        ] 
        );
        if(data?.works){
            var {TypeOfWork,Department } = data?.works
        }

    let validation = {} 
    return (
        <>
            <SearchField>
                <label>{t("WORKS_ESTIMATE_ID")}</label>
                <TextInput
                    name="estimateNumber"
                    inputRef={register()}
                    // {...(validation = {
                    //     isRequired: false,
                    //     pattern: "^[a-zA-Z0-9-_\/]*$",
                    //     type: "text",
                    //     title: t("ERR_INVALID_APPLICATION_NO"),
                    // })}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_ADMIN_SANCTION_NUMBER")}</label>
                <TextInput
                    name="adminSanctionNumber"
                    inputRef={register()}
                    // {...(validation = {
                    //     isRequired: false,
                    //     pattern: "^[a-zA-Z0-9-_\/]*$",
                    //     type: "text",
                    //     title: t("ERR_INVALID_APPLICATION_NO"),
                    // })}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_DEPARTMENT")}</label>
                <Controller
                    control={control}
                    name="department"
                    render={(props) => (
                        <Dropdown
                            selected={props.value}
                            select={props.onChange}
                            onBlur={props.onBlur}
                            option={Department}
                            optionKey="name"
                            t={t}
                        />
                    )}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_WORK_TYPE")}</label>
                <Controller
                    control={control}
                    name="typeofwork"
                    render={(props) => (
                        <Dropdown
                            selected={props.value}
                            select={props.onChange}
                            onBlur={props.onBlur}
                            option={TypeOfWork}
                            optionKey="name"
                            t={t}
                        />
                    )}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_COMMON_FROM_DATE_LABEL")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="fromProposalDate"
                    control={control}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_COMMON_TO_DATE_LABEL")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="toProposalDate"
                    control={control}
                />
            </SearchField>
            <SearchField/>
            <SearchField className="submit">
                <SubmitBar label={t("WORKS_COMMON_SEARCH")} submit />
                <p onClick={() => {
                    reset({
                        estimateNumber: "",
                        fromProposalDate: "",
                        toProposalDate: "",
                        typeofwork: "",
                        department: "",
                        adminSanctionNumber: "",
                        offset: 0,
                        limit: 10,
                        sortBy: "department",
                        sortOrder: "DESC"
                    });
                }}>{t(`CLEAR_SEARCH_LINk`)}</p>
            </SearchField>
        </>
    )
}

export default SearchFields