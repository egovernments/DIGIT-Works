import React, { useReducer, useState } from 'react'
import { Controller, useForm } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, DatePicker, Row, StatusTable, CardLabelError, AddIcon, SubtractIcon, InfoBannerIcon } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import ProcessingModal from '../Modal/ProcessingModal';

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

const CreateLoiForm = ({onFormSubmit}) => {
    const handleCreateClick = async () => {
        debugger
        // const result = await trigger(["lor", "fileno", "dlperiod", "fromDate", "aggDate", "agencyname", "officerInChargedesig","officerIncharge","work"])
        const arr = Object.keys(dummyDefault)
        const result = await trigger(arr)
        if (result) {
            setShowModal(true)
        }
    }

    const dummyDefault = {
        "absEstimateNo": "1223",
        "fileno": "111",
        "percent": "12",
        "contId": "12",
        "securitydeposit": "21",
        "bankG": "33",
        "emd": "11",
        "contperiod": "12",
        "dlperiod": "1",
        "fromDate": "2022-12-31",
        "aggDate": "0011-02-12",
        "agencyname": {
            "name": "Nipun"
        },
        "officerInChargedesig": {
            "name": "Nipun"
        },
        "officerIncharge": {
            "name": "Vipul"
        },
        "uploads": [],
        // "comments": "sadfskldfjwel",
        // "appDept": {
        //     "name": "Vipul"
        // },
        // "appDesig": {
        //     "name": "Shaifali"
        // },
        // "app": {
        //     "name": "Nipun"
        // }
    }
    const { t } = useTranslation()
    const [showModal, setShowModal] = useState(false)
    const {
        register,
        control,
        watch,
        setValue,
        unregister,
        handleSubmit,
        formState: { errors, ...rest },
        reset,
        trigger,
        ...methods
    } = useForm({
        defaultValues: { ...dummyDefault },
        mode: "onSubmit"
    });

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


    console.log(errors)



    let validation = {}

    return (
        <form onSubmit={handleSubmit(onFormSubmit)}>
            <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_LOI")}</Header>
            <Card >

                <CardSectionHeader >{t(`WORKS_LOI_DETAILS`)}</CardSectionHeader>
                <StatusTable>
                    <Row label={`${t("WORKS_ESTIMATE_NO")}:`} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
                    <Row
                        label={`${t("WORKS_NAME_OF_WORK")}:`}
                        text={"NA"}
                        textStyle={{ whiteSpace: "pre" }}
                    />
                    <Row label={`${t("WORKS_SUB_ESTIMATE_NO")}:`} text={"NA"} />
                </StatusTable>
                {showModal && <ProcessingModal
                    t={t}
                    heading={"WORKS_PROCESSINGMODAL_HEADER"}
                    closeModal={() => setShowModal(false)}
                    actionCancelLabel={"WORKS_CANCEL"}
                    actionCancelOnSubmit={() => setShowModal(false)}
                    actionSaveLabel={"WORKS_FORWARD"}
                    actionSaveOnSubmit={onFormSubmit}
                    onSubmit={onFormSubmit}
                    control={control}
                    register={register}
                    handleSubmit={handleSubmit}
                    errors={errors}

                />}


                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ABSTRACT_ESTIMATE_NO`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="absEstimateNo" inputRef={register({
                            required: false,
                            pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                        })} />
                        {errors && errors?.absEstimateNo?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_NO`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="fileno" inputRef={register({ required: true, pattern: /^[a-zA-Z0-9_.$@#\/]*$/ })} />
                        {errors && errors?.fileno?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.fileno?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_DATE`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="fromDate"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => <DatePicker
                                style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
                        />
                        {errors && errors?.fromDate?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <StatusTable>
                    <Row label={`${t("WORKS_ESTIMATED_AMT")}:`} text={"NA"} />
                </StatusTable>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FINALIZED_PER`)}:*`}</CardLabel>
                    <div className='field'>
                        <div className='percent-input'>
                            <button style={{ "height": "40px", "width": "40px" }}><AddIcon fill={"#F47738"} styles={{ "display": "revert" }} /></button>
                            <button style={{ "height": "40px", "width": "40px" }}><SubtractIcon fill={"#AFA8A4"} styles={{ "display": "revert", "marginTop": "7px" }} /></button>
                            <TextInput name="percent" type="number" inputRef={register({ validate: value => parseInt(value) >= -100 && parseInt(value) <= 100, required: true })} />
                            <div className="tooltip" style={{ "margin": "8px -30px 10px 10px" }}>
                                <InfoBannerIcon fill="#0b0c0c" />
                                <span className="tooltiptext" style={{
                                    whiteSpace: "nowrap",
                                    fontSize: "medium"
                                }}>
                                    {`${t(`WORKS_PERCENT_TOOLTIP`)}`}
                                </span>
                            </div>
                        </div>
                        {errors && errors?.percent?.type === "required" && (
                            <CardLabelError >{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.percent?.type === "validate" && (
                            <CardLabelError>{t(`WORKS_LIMIT_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGREEMENT_AMT`)}:`}</CardLabel>
                    <TextInput className={"field"} name="aggrementAmount" disabled={true} inputRef={register()} />
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_AGGREEMENT_DETAILS`)}</CardSectionHeader>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DATE_OF_AGG`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            render={(props) => <DatePicker style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
                            name="aggDate"
                            control={control}
                            rules={{ required: true }}
                        />
                        {errors && errors?.aggDate?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGENCY_NAME`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="agencyname"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={dummyData}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.agencyname?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_ID`)}:`}</CardLabel>
                    <TextInput className={"field"} name="contId" inputRef={register()} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ADD_SECURITY_DP`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="securitydeposit" inputRef={register({
                            pattern: /^[0-9]*$/
                        })}
                        />
                        {errors && errors?.securitydeposit?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BANK_G`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="bankG" inputRef={register({
                            pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                        })} />
                        {errors && errors?.bankG?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EMD`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="emd" inputRef={register({
                            required: true,
                            pattern: /^[0-9]*$/
                        })} />
                        {errors && errors?.emd?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.emd?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_PERIOD`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="contperiod" inputRef={register({
                            pattern: /^[0-9]*$/
                        })} />
                        {errors && errors?.contperiod?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DEFECT_LIA`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="dlperiod" inputRef={register({ required: true, pattern: /^[0-9]*$/ })} />
                        {errors && errors?.dlperiod?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.dlperiod?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_OFFICER_INCHARGE_DES`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="officerInChargedesig"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={dummyData}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.officerInChargedesig?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_OFFICER_INCHARGE_NAME`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="officerIncharge"
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
                        {errors && errors?.officerIncharge?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                {/* --------------------------------------- */}

                <CardSectionHeader >{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600 " }}>{t(`WORKS_UPLOAD_FILES`)}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="uploads"
                            control={control}
                            rules={{ required: false }}
                            render={({ onChange, ref, value = [] }) => {
                                function getFileStoreData(filesData) {
                                    const numberOfFiles = filesData.length
                                    let finalDocumentData = []
                                    if (numberOfFiles > 0) {
                                        filesData.forEach(value => {
                                            finalDocumentData.push({
                                                fileName: value?.[0],
                                                fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
                                                documentType: value?.[1]?.file?.type
                                            })
                                        })
                                    }
                                    onChange(finalDocumentData)
                                }
                                return <MultiUploadWrapper
                                    t={t}
                                    module="works"
                                    tenantId={"pb"}
                                    getFormState={getFileStoreData}
                                    showHintBelow={true}
                                    setuploadedstate={value}
                                    allowedFileTypesRegex={allowedFileTypes}
                                    allowedMaxSizeInMB={5}
                                    hintText={t("WORKS_DOC_UPLOAD_HINT")}
                                    maxFilesAllowed={5}
                                />
                            }
                            }
                        />
                    </div>
                </LabelFieldPair>

                <ActionBar>
                    <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_CREATE_ESTIMATE")} />
                </ActionBar>
            </Card>
        </form>

    )
}

export default CreateLoiForm