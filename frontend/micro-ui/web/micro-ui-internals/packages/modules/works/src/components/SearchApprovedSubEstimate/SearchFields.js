import React, { Fragment } from "react"
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, t,formState }) => {
    const dummyData = [
        {
            name: "Nipun"
        },
        {
            name: "Vipul"
        },
        {
            name: "Shaifali"
        },
        {
            name: "Amit"
        },
        {
            name: "Sumit"
        },
    ]
    console.log("stat",formState?.errors)
    let validation = {}
    return (
        <>
            <SearchField>
                <label>{t("WORKS_ESTIMATE_NO")}</label>
                <TextInput
                    name="estimateId"
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
                <label>{t("WORKS_SUB_ESTIMATE_NO")}</label>
                <TextInput
                    name="subEstimateNo"
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
                <label>{t("WORKS_ADMIN_SANC_NO")}</label>
                <TextInput
                    name="adminSanc"
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
                <label>{t("WORKS_DEPT")}</label>
                <Controller
                    control={control}
                    name="deptx"
                    render={(props) => (
                        <Dropdown
                            selected={props.value}
                            select={props.onChange}
                            //onBlur={props.onBlur}
                            option={dummyData}
                            optionKey="name"
                            t={t}
                        />
                    )}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_ADMIN_FROM_DATE_LABEL")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="fromDate"
                    control={control}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_ADMIN_TO_DATE_LABEL")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="toDate"
                    control={control}
                />
            </SearchField>
            <SearchField className="submit">
                <SubmitBar label={t("ACTION_TEST_SEARCH")} submit />
                <p onClick={() => {
                    reset({
                        // applicationType: "",
                        // fromDate: "",
                        // toDate: "",
                        // connectionNumber: "",
                        // applicationStatus: "",
                        // applicationNumber: "",
                        // tradeName: "",
                        // offset: 0,
                        // limit: 10,
                        // sortBy: "commencementDate",
                        // sortOrder: "DESC"
                    });
                }}>{t(`ES_COMMON_CLEAR_SEARCH`)}</p>
            </SearchField>
        </>
    )
}

export default SearchFields