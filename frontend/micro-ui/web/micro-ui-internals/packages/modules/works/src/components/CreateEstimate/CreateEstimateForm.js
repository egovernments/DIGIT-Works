import React, { Fragment, useState } from 'react'
import { Controller, useForm, useWatch } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, CardLabelError, Loader } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import SubWorkTable from './SubWorkTable';
import ProcessingModal from '../Modal/ProcessingModal';

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateEstimateForm = ({ onFormSubmit }) => {

    const { t } = useTranslation()
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
        defaultValues: {},
        mode: "onSubmit"
    });

    const DummyData = [
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


    const getDate = () => {
        const today = new Date();

        const date = today.getDate() + '/' + (today.getMonth() + 1) + '/' + today.getFullYear();
        return date
    }

    const initialState = [
        {
            key: 1,
            isShow: true
        }
    ]
    const [rows, setRows] = useState(initialState)

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

    const { isLoading: isFinanceDataLoading, data: financeData, isFetched: isFinanceDataFetched } = Digit.Hooks.useCustomMDMS(
        "pb",
        "finance",
        [
            {
                "name": "Scheme"
            },
            {
                "name": "BudgetHead"
            },
            {
                "name": "Functions"
            },
            {
                "name": "Fund"
            }
        ]
    );


    const { subTypes: SubTypeOfWork } = useWatch({ control: control, name: "typeOfWork", defaultValue: [] });

    if (data?.works) {
        var { EntrustmentMode, BeneficiaryType, NatureOfWork, TypeOfWork, Department } = data?.works
    }

    const { subScheme: subScheme } = useWatch({ control: control, name: "scheme", defaultValue: [] });
    if (financeData?.finance) {
        var { Scheme, BudgetHead, Functions, Fund } = financeData?.finance
    }



    const handleCreateClick = async () => {

        const obj = {
            "requirementNumber": "123123",
            "estimateDetails": [
                null,
                {
                    "name": "work",
                    "amount": "12312"
                }
            ],
            "department": {
                "name": "Nipun"
            },
            "ward": {
                "name": "Nipun"
            },
            "location": {
                "name": "Vipul"
            },
            "beneficiaryType": {
                "name": "Vipul"
            },
            "natureOfWork": {
                "name": "Shaifali"
            },
            "typeOfWork": {
                "name": "Nipun"
            },
            "subTypeOfWork": {
                "name": "Vipul"
            },
            "entrustmentMode": {
                "name": "Shaifali"
            },
            "fund": {
                "name": "Vipul"
            },
            "function": {
                "name": "Shaifali"
            },
            "budgetHead": {
                "name": "Vipul"
            },
            "scheme": {
                "name": "Shaifali"
            },
            "subScheme": {
                "name": "Vipul"
            },
            "uploads": [
                {
                    "fileName": "consumer-WS_107_2021-22_226507.pdf",
                    "fileStoreId": "4a28b85e-63af-402e-bc8f-9bb0c148e47b",
                    "documentType": "application/pdf"
                },
                {
                    "fileName": "consumer-PB-CH-2022-07-27-001010.pdf",
                    "fileStoreId": "8cf5da0b-42a0-41ff-b98a-22cf84619d28",
                    "documentType": "application/pdf"
                },
                {
                    "fileName": "consumerCode-WS_107_2020-21_218051.pdf",
                    "fileStoreId": "6d8962ec-9d34-4dfc-b7c2-90fe9cf0b0e2",
                    "documentType": "application/pdf"
                }
            ],
        }
        const subWorkFieldsToValidate = []
        rows.map(row => row.isShow && subWorkFieldsToValidate.push(...[`estimateDetails.${row.key}.name`, `estimateDetails.${row.key}.amount`]))
        const fieldsToValidate = ['requirementNumber', 'department', 'ward', 'location', 'beneficiaryType', 'natureOfWork', 'typeOfWork', 'subTypeOfWork', 'entrustmentMode', 'fund', 'function', 'budgetHead', 'scheme', 'subScheme', ...subWorkFieldsToValidate]


        const result = await trigger(fieldsToValidate)
        if (result) {
            setShowModal(true)
        }
    }

    const [showModal, setShowModal] = useState(false)

    if (isLoading) {
        return <Loader />
    }

    const checkKeyDown = (e) => {
        if (e.code === 'Enter') e.preventDefault();
    };

    return (
        isFetched && <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
            <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_ESTIMATE")}</Header>
            <Card >
                <CardSectionHeader style={{ "marginTop": "14px" }} >{t(`WORKS_ESTIMATE_DETAILS`)}</CardSectionHeader>
                {/* TEXT INPUT ROW */}
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{`${t(`WORKS_DATE_PROPOSAL`)}:*`}</CardLabel>
                    <TextInput className={"field"} name="proposalDate" inputRef={register()} value={getDate()} disabled style={{ backgroundColor: "#E5E5E5" }} />
                </LabelFieldPair>


                {/* Modal */}
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

                {/* DROPDOWN ROW */}
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EXECUTING_DEPT`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="department"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={Department}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.department?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_LOR`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="requirementNumber" inputRef={register({
                            pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                        })}
                        />


                        {errors && errors?.requirementNumber?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_LOCATION_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_WARD`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="ward"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={BeneficiaryType}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.ward?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_LOCATION`)}:`}</CardLabel>
                    <Controller
                        name="location"
                        control={control}
                        //rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={BeneficiaryType}
                                    selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                    onBlur={props.onBlur}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>

                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_WORK_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BENEFICIERY`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="beneficiaryType"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={BeneficiaryType}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.beneficiaryType?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_WORK_NATURE`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="natureOfWork"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={NatureOfWork}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.natureOfWork?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_WORK_TYPE`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="typeOfWork"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={TypeOfWork}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.typeOfWork?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_SUB_TYPE_WORK`)}:`}</CardLabel>
                    <Controller
                        name="subTypeOfWork"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={SubTypeOfWork}
                                    selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                    onBlur={props.onBlur}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_MODE_OF_INS`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="entrustmentMode"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={EntrustmentMode}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.entrustmentMode?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FUND`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="fund"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={Fund}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.fund?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FUNCTION`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="function"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={Functions}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.function?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BUDGET_HEAD`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="budgetHead"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={BudgetHead}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.budgetHead?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_SCHEME`)}:`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="scheme"
                            control={control}
                            rules={{ required: false }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={Scheme}
                                        selected={props?.value}
                                        optionKey={"schemeName"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.scheme?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{`${t(`WORKS_SUB_SCHEME`)}:`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="subScheme"
                            control={control}
                            rules={{ required: false }}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={subScheme}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.subScheme?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>

                {/* Render the sub work table here */}
                <CardSectionHeader >{`${t(`WORKS_SUB_WORK_DETAILS`)}*`}</CardSectionHeader>
                <SubWorkTable register={register} t={t} errors={errors} rows={rows} setRows={setRows} />

                <CardSectionHeader style={{ "marginTop": "20px" }} >{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
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
                                    extraStyleName={{ padding: "0.5rem" }}
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

export default CreateEstimateForm