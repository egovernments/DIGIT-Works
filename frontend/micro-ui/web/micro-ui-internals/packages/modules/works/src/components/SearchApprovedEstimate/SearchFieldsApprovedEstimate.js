import  React ,{Fragment}from 'react'
import { Controller, useWatch } from "react-hook-form";
import { TextInput, SubmitBar, DatePicker, SearchField, Dropdown, Loader } from "@egovernments/digit-ui-react-components";

const SearchFields = ({ register, control, reset, tenantId, t,businessService }) => {
    const { isLoading: applicationTypesLoading, data: applicationTypes } = Digit.Hooks.ws.useWSMDMSWS.applicationTypes(Digit.ULBService.getStateId());
    const filterString = businessService==="WORKS"
    const filteredApplicationTypes = applicationTypes?.filter(e => e?.code?.includes(filterString))
    const applicationType = useWatch({ control, name: "applicationType" });
    let businessServices = ["WORKS"];
    let selectedService = "WORKS"
    const { data: statusData } = Digit.Hooks.useApplicationStatusGeneral({ businessServices, tenantId }, {});
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
        "works",
        [
            {
                "name": "Department"
            }
        ]
        );
        if(data?.works){
          var { Department } = data?.works
        }
    let applicationStatuses = []

    statusData && statusData?.otherRoleStates?.map((status) => {

        if(selectedService!==""){
             status?.stateBusinessService===selectedService && applicationStatuses.push({
                code: status?.applicationStatus, i18nKey: `WF_NEWWS1_${(status?.applicationStatus)}`
            })
        return
        }
        let found = applicationStatuses.length > 0 ? applicationStatuses?.some(el => el?.code === status.applicationStatus) : false;
        if (!found) applicationStatuses.push({ code: status?.applicationStatus, i18nKey: `WF_NEWWS1_${(status?.applicationStatus)}` })
    })

    statusData && statusData?.userRoleStates?.map((status) => {

        if (selectedService !== "") {
            status?.stateBusinessService === selectedService && applicationStatuses.push({
                code: status?.applicationStatus, i18nKey: `WF_NEWWS1_${(status?.applicationStatus)}`
            })
            return
        }
        let found = applicationStatuses.length > 0 ? applicationStatuses?.some(el => el?.code === status.applicationStatus) : false;
        if (!found) applicationStatuses.push({ code: status?.applicationStatus, i18nKey: `WF_NEWWS1_${(status?.applicationStatus)}` })
    })

    //Sorting the statuses alphabetically
    applicationStatuses?.sort((a, b) => (t(a.i18nKey) > t(b.i18nKey))? 1 :((t(b.i18nKey)>t(a.i18nKey))? -1 :0))
    
    let validation = {}
    return <>
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
        <SearchField>
            <label>{t("WORKS_SUB_ESTIMATE_NO")}</label>
            <TextInput 
                name="subEstimateNumber" 
                inputRef={register()} 
                {...(validation = {
                    isRequired: false,
                    pattern:"^[a-zA-Z0-9\/-]*$",
                    type: "text",
                    title: t("ERR_INVALID_SUB_ESTIMATE_NO"),
                })}
            />
        </SearchField>
        <SearchField>
            <label>{t("WORKS_ADMIN_SANCTION_NUMBER")}</label>
            <TextInput 
                name="adminSanctionNumber" 
                inputRef={register({})} 
                {...(validation = {
                    isRequired: false,
                    pattern:"^[a-zA-Z0-9\/-]*$",
                    type: "text",
                    title: t("ERR_INVALID_ADMIN_SANCTION_NUMBER"),
                })}/>
        </SearchField>
        {/* {applicationTypesLoading ? <Loader /> :  */}
        <SearchField>
            <label>{t("WORKS_DEPARTMENT")}</label>
            <Controller
                control={control}
                name="department"
                render={(props) => (
                    <Dropdown
                        option={Department}
                        selected={props?.value}
                        optionKey={"name"}
                        t={t}
                        select={props?.onChange}
                        onBlur={props.onBlur}
                    />
                )}
            />
        </SearchField>
        {/* } */}
        <SearchField>
            <label>{t("WORKS_ADMIN_SANCTION_FROM_DATE")}</label>
            <Controller
                render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                name="fromProposalDate"
                control={control}
            />
        </SearchField>
        <SearchField>
            <label>{t("WORKS_ADMIN_SANCTION_TO_DATE")}</label>
            <Controller
                render={(props) => <DatePicker date={props.value} onChange={props.onChange} />}
                name="toProposalDate"
                control={control}
            />
        </SearchField>
        <SearchField/>
        <SearchField className="submit">
            <SubmitBar label={t("ES_COMMON_SEARCH")} submit />
            <p onClick={() => {
                reset({
                    applicationType: "",
                    fromDate: "",
                    toDate: "",
                    licenseNumbers: "",
                    status: "",
                    tradeName: "",
                    offset: 0,
                    limit: 10,
                    sortBy: "department",
                    sortOrder: "DESC"
                });
            }}>{t(`CS_COMMON_CLEAR_SEARCH`)}</p>
        </SearchField>
    </>
}
export default SearchFields;