import React, { Fragment, useState } from 'react'
import { Controller, useForm, useWatch } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, CardLabelError, Loader } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import SubWorkTable from './SubWorkTable';
import ProcessingModal from '../Modal/ProcessingModal';

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

const ModifyEstimateForm = ({ onFormSubmit, estimate}) => {

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
        getValues,
        ...methods
    } = useForm({
        defaultValues: {},
        mode: "onSubmit"
    });

    let filesAttached=[]
    estimate?.additionalDetails?.filesAttached.map((val)=>{
        filesAttached.push([
            val?.fileName,
            {
                file:{
                    type:val?.documentType,
                    name:val?.fileName
                },
            fileStoreId:{
                fileStoreId:val?.fileStoreId
            }}
        ])
    })
    const tenantId = Digit.ULBService.getCurrentTenantId();
    let paginationParams = { limit: 10, offset:0, sortOrder:"ASC" }
    const { isLoading: hookLoading, isError, error, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
        null,
        tenantId,
        paginationParams,
        null
    );
    const tenant = Digit.ULBService.getStateId()
    const { isLoading:desgLoading, data:designationData } = Digit.Hooks.useCustomMDMS(
        tenant,
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

    if (designationData?.[`common-masters`]) {
        var { Designation, Department } = designationData?.[`common-masters`]
    }
    Department?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))

    const [rows, setRows] = useState(estimate?.estimateDetails)

    const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
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
            }
        ]
    );

    const { isLoading: isFinanceDataLoading, data: financeData, isFetched: isFinanceDataFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
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
    SubTypeOfWork?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))

    if (data?.works) {
        var { EntrustmentMode, BeneficiaryType, NatureOfWork, TypeOfWork } = data?.works
    }
    EntrustmentMode?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))
    BeneficiaryType?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))
    NatureOfWork?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))
    TypeOfWork?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))

    const { subSchemes: subScheme } = useWatch({ control: control, name: "scheme", defaultValue: [] });
    if (financeData?.finance) {
        var { Scheme, BudgetHead, Functions, Fund } = financeData?.finance
    }
    Scheme?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.schemeCode}`)}))
    BudgetHead?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_BUDGETHEAD_${item?.code}`)}))
    Functions?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))
    Fund?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_FUND_${item?.code}`)}))
    subScheme?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))

    const { isLoading: locationLoading, data: locationData, isFetched: locationDataFetched } = Digit.Hooks.useCustomMDMS(
        tenantId,
        "egov-location",
        [
            {
                "name": "TenantBoundary"
            },
        ]
    );
    if (locationData?.[`egov-location`]) {
        var { children: ward } = locationData?.[`egov-location`]?.TenantBoundary[0]?.boundary?.children[0]
    }
    ward?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))

    const { children: location } = useWatch({ control: control, name: "ward", defaultValue: [] });
    location?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))

    const handleCreateClick = async () => {
        const subWorkFieldsToValidate = []
        rows.map((row,index) => subWorkFieldsToValidate.push(...[`estimateDetails.${index}.name`, `estimateDetails.${index}.amount`]))
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
    const ulb={"name":t("WORKS_MODIFY_ESTIMATE"), "code":"health", "active":true}
    let wardLocation =estimate?.location.replace(/(^:)|(:$)/g, '').split(":")
    return (
        isFetched && <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
            <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_MODIFY_ESTIMATE")}</Header>
            <Card >
                <LabelFieldPair>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_ESTIMATE_ID`)}</CardLabel>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{estimate?.estimateNumber}</CardLabel>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_STATUS`)}</CardLabel>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{estimate?.estimateStatus}</CardLabel>
                </LabelFieldPair>
                <CardSectionHeader style={{ "marginTop": "14px" }} >{t(`WORKS_ESTIMATE_DETAILS`)}</CardSectionHeader>
                {/* TEXT INPUT ROW */}
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{`${t(`WORKS_DATE_PROPOSAL`)}:*`}</CardLabel>
                    <TextInput className={"field"} name="proposalDate" inputRef={register()} value={Digit.DateUtils.ConvertEpochToDate(estimate?.proposalDate)} disabled style={{ backgroundColor: "#E5E5E5" }} />
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
                    action={"estimate"}
                    setValue={setValue}
                />}

                {/* DROPDOWN ROW */}
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EXECUTING_DEPT`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="department"
                            control={control}
                            rules={{ required: true }}
                            defaultValue={{i18nKey:t(`ES_COMMON_${estimate?.department}`), code:estimate?.department, active:true}}
                            render={(props) => {
                                return ( 
                                    <Dropdown
                                        option={Department}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_LOR`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            control={control}
                            name="requirementNumber"
                            defaultValue={estimate?.requirementNumber}
                            rules={{pattern: /^[a-zA-Z0-9_.$@#\/]*$/, required:true}}
                            render={(props)=>(
                                <TextInput
                                    value={props.value}
                                    onChange={(e) => {
                                        props.onChange(e.target.value);
                                    }}
                                    onBlur={props.onBlur}
                                />
                            )}
                        />
                        {errors && errors?.requirementNumber?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                        {errors && errors?.requirementNumber?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
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
                            defaultValue={{i18nKey:t(`ES_COMMON_${wardLocation[4]}`), code:wardLocation[4], active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={ward}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
                                        t={t}
                                        select={(val)=>{
                                            props?.onChange(val)
                                            setValue("location","")
                                        }}
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
                        defaultValue={{i18nKey:t(`ES_COMMON_${wardLocation[5]}`), code:wardLocation[5], active:true}}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={location}
                                    selected={props?.value}
                                    optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:estimate?.beneficiaryType, code:estimate?.beneficiaryType, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={BeneficiaryType}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:estimate?.natureOfWork, code:estimate?.natureOfWork, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={NatureOfWork}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:estimate?.typeOfWork, code:estimate?.typeOfWork, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={TypeOfWork}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
                                        t={t}
                                        select={(val)=>{
                                            props?.onChange(val)
                                            setValue("subTypeOfWork","")
                                        }}
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
                        defaultValue={{i18nKey:t(`ES_COMMON_${estimate?.subTypeOfWork}`), code:estimate?.subTypeOfWork, active:true}}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={SubTypeOfWork}
                                    selected={props?.value}
                                    optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:estimate?.entrustmentMode, code:estimate?.entrustmentMode, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={EntrustmentMode}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:t(`ES_COMMON_FUND_${estimate?.fund}`), code:estimate?.fund, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={Fund}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:t(`ES_COMMON_${estimate?.function}`), code:estimate?.function, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={Functions}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:t(`ES_COMMON_${estimate?.budgetHead}`), code:estimate?.budgetHead, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={BudgetHead}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                            defaultValue={{i18nKey:t(`ES_COMMON_${estimate?.scheme}`), code:estimate?.scheme, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={Scheme}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
                                        t={t}
                                        select={(val)=>{
                                            props?.onChange(val)
                                            setValue("subScheme","")
                                        }}
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
                            defaultValue={{i18nKey:t(`ES_COMMON_${estimate?.subScheme}`), code:estimate?.subScheme, active:true}}
                            render={(props) => {
                                return (
                                    <Dropdown

                                        option={subScheme}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                <SubWorkTable register={register} t={t} errors={errors} Controller={Controller} control={control} rows={rows} setRows={setRows} setValue={setValue} estimateDetails={estimate?.estimateDetails} />

                <CardSectionHeader style={{ "marginTop": "20px" }} >{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600 " }}>{t(`WORKS_UPLOAD_FILES`)}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="uploads"
                            defaultValue={filesAttached}
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
                                    tenantId={tenant}
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
                    <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_MODIFY_ESTIMATE")} />
                </ActionBar>
            </Card>
        </form>
    )
}

export default ModifyEstimateForm