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

    

    const { isLoading, data:departmentOptions, isFetched } = Digit.Hooks.useCustomMDMS(
        "pb",
        "works",
        [
            {
                "name": "BeneficiaryType"
            },
            {
                "name": "EntrustmentMode"
            },
            {
                "name": "NatureOfWork"
            },
            {
                "name": "TypeOfWork"
            },
            {
                "name": "Department"
            }
        ],
        {
            select:(data) => {
                
                return data?.works?.Department
            }
        }
    );

    const deptOptions = [
        {
            "name":"Engg"
        },
        {
            "name": "R&D"
        }, {
            "name": "Civil"
        },
    ]
    
    let validation = {}

    if (isLoading) {
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
                    name="estiamteDetailNumber"
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
                            option={deptOptions}
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