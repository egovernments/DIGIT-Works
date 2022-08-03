import React, { useReducer,useState } from 'react'
import { Controller, useForm } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, DatePicker, Row, StatusTable,CardLabelError } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import ProcessingModal from '../../../components/Modal/ProcessingModal';


//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(jpg|jpeg|png|image|pdf|msword|openxmlformats-officedocument)$/i;

const CreateLOI = () => {
    const { t } = useTranslation()
    const [showModal,setShowModal] = useState(false)
    const {
        register,
        control,
        watch,
        setValue,
        unregister,
        handleSubmit,
        formState: { errors, ...rest },
        reset,
        ...methods
    } = useForm({
        defaultValues: {
            "lor": "12",
            "fileno": "3242",
            "dlperiod": "12",
            "fromDate": "2022-08-04",
            "aggDate": "2022-08-04",
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
            "comments": "sdfs",
            "appDept": {
                "name": "Nipun"
            },
            "appDesig": {
                "name": "Vipul"
            },
            "app": {
                "name": "Vipul"
            }
        }
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

    const onFormSubmit = (_data) => {
        debugger
        
        console.log(_data);
    }
    console.log(errors)
    const getDate = () => {
        const today = new Date();

        const date = today.getDate() + '/' + (today.getMonth() + 1) + '/' + today.getFullYear();
        return date
    }

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

                {/* <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_ESTIMATE_NO`)}</CardLabel>
                    <p>{"estimateIdPlaceholder"}</p>
                </LabelFieldPair>
                
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_NAME_OF_WORK`)}</CardLabel>
                    <p>{"nameofworkplaceholder"}</p>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_SUB_ESTIMATE_NO`)}</CardLabel>
                    <p>{"subestimatenoplaceholder"}</p>
                </LabelFieldPair> */}

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ABSTRACT_ESTIMATE_NO`)}:`}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()}
                        {...(validation = {
                            isRequired: false,
                            pattern: "^[a-zA-Z0-9_.$@#\/]*$",
                            type: "text",
                            title: t("WORKS_INVALID_INPUT"),
                        })} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_NO`)}:*`}</CardLabel>
                    <div className='field'>
                    <TextInput  name="fileno" inputRef={register({required:true})} {...(validation = {
                        isRequired: false,
                        pattern: "^[a-zA-Z0-9_.$@#\/]*$",
                        type: "text",
                        title: t("WORKS_INVALID_INPUT"),
                    })} />
                        {errors && errors?.fileno?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_DATE`)}:*`}</CardLabel>
                    <div className='field'>
                    <Controller
                        render={(props) => <DatePicker style={{ "width": "100%" }} date={props.value} onChange={props.onChange} />}
                        name="fromDate"
                        control={control}
                        rules={{required:true}}
                    />
                    {errors && errors?.fromDate?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                </div>
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <StatusTable>
                    <Row label={`${t("WORKS_ESTIMATED_AMT")}:`} text={"NA"} />
                </StatusTable>
                {/* <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_ESTIMATED_AMT`)}</CardLabel>
                    <p>{"ESTIMATE AMT"}</p>
                </LabelFieldPair> */}

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FINALIZED_PER`)}:`}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()} />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGREEMENT_AMT`)}:`}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()} />
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_AGGREEMENT_DETAILS`)}</CardSectionHeader>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DATE_OF_AGG`)}:*`}</CardLabel>
                    <div className='field'>
                    <Controller
                        render={(props) => <DatePicker style={{ "width": "100%" }} date={props.value} onChange={props.onChange} />}
                        name="aggDate"
                        control={control}
                        rules={{required:true}}
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
                    <TextInput className={"field"} name="lor" inputRef={register()} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ADD_SECURITY_DP`)}:`}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()} 
                        {...(validation = {
                            isRequired: false,
                            pattern: "^[0-9]*$",
                            type: "number",
                            title: t("WORKS_INVALID_INPUT"),
                        })} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BANK_G`)}:`}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()} {...(validation = {
                        isRequired: false,
                        pattern: "^[a-zA-Z0-9_.$@#\/]*$",
                        type: "text",
                        title: t("WORKS_INVALID_INPUT"),
                    })} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EMD`)}:*`}</CardLabel>
                    <div className='field'>
                    <TextInput  name="lor" inputRef={register({required:true})}  {...(validation = {
                        isRequired: false,
                        pattern: "^[0-9]*$",
                        type: "number",
                        title: t("WORKS_INVALID_INPUT"),
                    })} />
                    {errors && errors?.fileno?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_PERIOD`)}:`}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()} {...(validation = {
                        isRequired: false,
                        pattern: "^[0-9]*$",
                        type: "number",
                        title: t("WORKS_INVALID_INPUT"),
                    })} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DEFECT_LIA`)}:*`}</CardLabel>
                    <div className='field'>
                    <TextInput name="dlperiod" inputRef={register({required:true})} {...(validation = {
                        isRequired: false,
                        pattern: "^[0-9]*$",
                        type: "number",
                        title: t("WORKS_INVALID_INPUT"),
                    })} />
                        {errors && errors?.dlperiod?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
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
                            // defaultValue={formData?.category ? data?.mseva?.EventCategories.filter(category => category.code === formData?.category)?.[0] : null}
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
                                />
                            }
                            }
                        />
                    </div>
                </LabelFieldPair>

                <ActionBar>
                    <SubmitBar onSubmit={() => setShowModal(true)} label={t("WORKS_CREATE_ESTIMATE")} />
                </ActionBar>
            </Card>
        </form>

    )
}

export default CreateLOI