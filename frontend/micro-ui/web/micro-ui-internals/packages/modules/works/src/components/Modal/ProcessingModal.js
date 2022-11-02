import React,{Fragment,useState} from "react";
import { Modal, Card, CardText, TextInput, CardLabelError,Dropdown,TextArea,Loader } from "@egovernments/digit-ui-react-components";
import { Controller, useForm,useWatch } from 'react-hook-form'


const Heading = (props) => {
    return <h1 className="heading-m">{props.t(props.heading)}</h1>;
};

const Close = () => (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="#FFFFFF">
        <path d="M0 0h24v24H0V0z" fill="none" />
        <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z" />
    </svg>
);

const CloseBtn = (props) => {
    return (
        <div className="icon-bg-secondary" onClick={props.onClick}>
            <Close />
        </div>
    );
};


const ProcessingModal = ({
    t,
    heading,
    closeModal,
    actionCancelLabel,
    actionCancelOnSubmit,
    actionSaveLabel,
    actionSaveOnSubmit,
    handleSubmit,
    control,
    register,
    errors:formErrors,
    employeeData,
    Department,
    Designation,
    action="estimate",
    setValue,
}) => {

    const allowedRoles = {
        estimate: "EST_CHECKER",
        loi: "LOI_CHECKER"
    } 
    
    const rolesForThisAction = allowedRoles?.[action];
    const { isLoading: desLoading, data: designationData } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getCurrentTenantId(),
        "common-masters",
        [
            {
                "name": "Designation"
            },
            {
                "name": "Department"
            }
        ]
    );

    designationData?.["common-masters"]?.Designation?.map(designation => {
        designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
    })
    designationData?.["common-masters"]?.Department?.map(department => {
        department.i18nKey = `ES_COMMON_${department?.code}`
    })

    const isChanged = () => {
        setValue("app", "")
    }



    const selectedDepartment = useWatch({ control: control, name: "appDept", defaultValue: "" });
    const selectedDesignation = useWatch({ control: control, name: "appDesig", defaultValue: "" });
    
    
    //based on these two make an hrms search for approver dropdown
    let Approvers = []

    // const { isLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code, departments: selectedDepartment?.code,isActive:true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: !!(selectedDepartment || selectedDesignation) });

    // employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")
     //filter based on roles, use rolesForThisAction
    // const subResult = employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees: []
    
    // Approvers = subResult?.filter(app=>app?.user?.roles?.some(role=> role?.code===rolesForThisAction))
    

    const { isLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code, departments: selectedDepartment?.code, roles: rolesForThisAction, isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: !!(selectedDepartment || selectedDesignation) });
    employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")
    Approvers = employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter(emp=>emp?.nameOfEmp!=="NA") : []

    
    return (
        desLoading?<Loader/> :
        <Modal
            headerBarMain={<Heading t={t} heading={heading} />}
            headerBarEnd={<CloseBtn onClick={closeModal} />}
            actionCancelLabel={t(actionCancelLabel)}
            actionCancelOnSubmit={actionCancelOnSubmit}
            actionSaveLabel={t(actionSaveLabel)}
            actionSaveOnSubmit={handleSubmit(actionSaveOnSubmit)}
            formId="modal-action"
            headerBarMainStyle={{ marginLeft: "20px" }}
        >

            <Card style={{ boxShadow: "none" }}>
                    <span className="surveyformfield">
                        <label>{`${t("WORKS_APPROVER_DEPT")}*`}</label>
                    <Controller
                        name="appDept"
                        control={control}
                        rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    onBlur={props.onBlur}
                                    option={designationData?.["common-masters"]?.Department}
                                    selected={props?.value}
                                    optionKey={"i18nKey"}
                                    t={t}
                                    select={(val)=>{
                                        props.onChange(val)
                                        isChanged()    
                                        //resetting approver dropdown when dept/designation changes
                                    }}
                                    
                                />
                            );
                        }}
                    />
                        {formErrors && formErrors?.appDept && formErrors?.appDept?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                        )}
                        
                    </span>
                <span className="surveyformfield">
                    <label>{`${t("WORKS_APPROVER_DESIGNATION")}*`}</label>
                    <Controller
                        name="appDesig"
                        control={control}
                        rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    onBlur={props.onBlur}
                                    option={designationData?.["common-masters"]?.Designation}
                                    selected={props?.value}
                                    optionKey={"i18nKey"}
                                    t={t}
                                    select={(val) => {
                                        props.onChange(val)
                                        isChanged()
                                    }}
                                />
                            );
                        }}
                    />
                    {formErrors && formErrors?.appDesig && formErrors?.appDesig?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                        )}

                </span>
                <span className="surveyformfield">
                    <label>{`${t("WORKS_APPROVER")}*`}</label>
                    <Controller
                        name="app"
                        control={control}
                        rules={{ required: true }}
                        render={(props) => {
                            return (
                                isLoading?<Loader/>:
                                <Dropdown
                                    onBlur={props.onBlur}
                                    option={Approvers}
                                    selected={props?.value}
                                    optionKey={"nameOfEmp"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                    {formErrors && formErrors?.app && formErrors?.app?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>
                        )}

                </span>
                <span className="surveyformfield">
                    <label>{t("WORKS_COMMENTS")}</label>
                        <TextArea
                            style={{"marginTop":"3px"}}
                            name={"comments"}
                            inputRef={register({
                                maxLength:140
                            })}
                        />
                    {formErrors?.comments?.type && <CardLabelError>{t(`ERROR_${formErrors?.comments?.type?.toUpperCase()}_140`)}</CardLabelError>}
                    
                </span>

                    {/* <span className="surveyformfield">
                        <label>{t("LABEL_SURVEY_START_TIME")}</label>
                        <Controller
                            control={controlSurveyForm}
                            name="fromTime"
                            defaultValue={surveyFormState?.fromTime}
                            rules={{ required: true, validate: { isValidFromTime } }}
                            render={({ onChange, value }) => <TextInput type="time" isRequired={true} onChange={onChange} defaultValue={value} />}
                        />
                        {formErrors && formErrors?.fromTime && formErrors?.fromTime?.type === "required" && (
                            <CardLabelError>{t(`EVENTS_TO_DATE_ERROR_REQUIRED`)}</CardLabelError>
                        )}
                        {formErrors && formErrors?.fromTime && formErrors?.fromTime?.type === "isValidToDate" && (
                            <CardLabelError>{t(`EVENTS_TO_DATE_ERROR_INVALID`)}</CardLabelError>
                        )}
                    </span>

                    <span className="surveyformfield">
                        <label>{t("LABEL_SURVEY_END_DATE")}</label>
                        <Controller
                            control={controlSurveyForm}
                            name="toDate"
                            defaultValue={surveyFormState?.toDate}
                            rules={{ required: true, validate: { isValidToDate } }}
                            render={({ onChange, value }) => <TextInput type="date" isRequired={true} onChange={onChange} defaultValue={value} />}
                        />
                        {formErrors && formErrors?.toDate && formErrors?.toDate?.type === "required" && (
                            <CardLabelError>{t(`EVENTS_TO_DATE_ERROR_REQUIRED`)}</CardLabelError>
                        )}
                        {formErrors && formErrors?.toDate && formErrors?.toDate?.type === "isValidToDate" && (
                            <CardLabelError>{t(`EVENTS_TO_DATE_ERROR_INVALID`)}</CardLabelError>
                        )}{" "}
                    </span>

                    <span className="surveyformfield">
                        <label>{t("LABEL_SURVEY_END_TIME")}</label>

                        <Controller
                            control={controlSurveyForm}
                            name="toTime"
                            defaultValue={surveyFormState?.toTime}
                            rules={{ required: true, validate: { isValidToTime } }}
                            render={({ onChange, value }) => <TextInput type="time" isRequired={true} onChange={onChange} defaultValue={value} />}
                        />
                        {formErrors && formErrors?.toTime && formErrors?.toTime?.type === "required" && (
                            <CardLabelError>{t(`EVENTS_TO_DATE_ERROR_REQUIRED`)}</CardLabelError>
                        )}
                        {formErrors && formErrors?.toTime && formErrors?.toTime?.type === "isValidToDate" && (
                            <CardLabelError>{t(`EVENTS_TO_DATE_ERROR_INVALID`)}</CardLabelError>
                        )}
                    </span> */}
                
            </Card>
        </Modal>
    );
};

export default ProcessingModal;
