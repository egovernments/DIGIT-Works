import React,{Fragment,useState} from "react";
import { Modal, Card, CardText, TextInput, CardLabelError,Dropdown,TextArea,Loader } from "@egovernments/digit-ui-react-components";
import { Controller, useForm } from 'react-hook-form'


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
    errors:formErrors
}) => {
    

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
    const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
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
        ]
    );

    if (data?.works) {
        var { EntrustmentMode, BeneficiaryType, NatureOfWork, TypeOfWork, Department } = data?.works
    }
    if(isLoading) return <Loader/>
    
    return (
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
                                    option={Department}
                                    selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
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
                                    option={dummyData}
                                    selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
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
                                <Dropdown
                                    onBlur={props.onBlur}
                                    option={dummyData}
                                    selected={props?.value}
                                    optionKey={"name"}
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
