import React, { Fragment } from "react"
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, t, formState, onClearSearch }) => {
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

    
    const tenant = Digit.ULBService.getStateId();
    const { isLoading: desgLoading, data: departmentData } = Digit.Hooks.useCustomMDMS(
        tenant,
        "common-masters",
        [
            {
                "name": "Department"
            }
        ]
    );

    if (departmentData?.[`common-masters`]) {
        var { Department } = departmentData?.[`common-masters`]
    }
    
    

    Department?.map(dept=>{
        dept.i18nKey = `ES_COMMON_${dept.code}`
    })
    
    
    let validation = {}

    if (desgLoading) {
        return <Loader />
    }

    return (
        <>
            <SearchField>
                <label>{t("WORKS_ESTIMATE_NO")}</label>
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
                <label>{t("WORKS_SUB_ESTIMATE_NO")}</label>
                <TextInput
                    name="estimateDetailNumber"
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
                            //onBlur={props.onBlur}
                            option={Department}
                            optionKey="i18nKey"
                            t={t}
                        />
                    )}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_ADMIN_FROM_DATE_LABEL")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="fromProposalDate"
                    control={control}
                />
            </SearchField>
            <SearchField>
                <label>{t("WORKS_ADMIN_TO_DATE_LABEL")}</label>
                <Controller
                    render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                    name="toProposalDate"
                    control={control}
                />
            </SearchField>
            <SearchField />
            <SearchField className="submit">
                <SubmitBar label={t("ACTION_TEST_SEARCH")} submit />
                <p onClick={() => {
                    reset({
                        "offset": 0,
                        "limit": 10,
                        "sortBy": "department",
                        "sortOrder": "DESC",
                        "toProposalDate":"",
                        "fromProposalDate":"",
                        "department":"",
                        "adminSanctionNumber":"",
                        "estimateDetailNumber":"",
                        "estimateNumber":"",
                    });
                    onClearSearch(false)
                }}>{t(`ES_COMMON_CLEAR_SEARCH`)}</p>
            </SearchField>
        </>
    )
}

export default SearchFields