import React, { useReducer, useState } from 'react'
import { Controller, useForm,useWatch } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, DatePicker, Row, StatusTable, CardLabelError, AddIcon, SubtractIcon, InfoBannerIcon,Loader } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import ProcessingModal from '../Modal/ProcessingModal';

import { useHistory, useLocation } from "react-router-dom";
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

const CreateLoiForm = ({ onFormSubmit, defaultFormValues, state, loiNumber, isEdit }) => {
    
    //state?.data?.applicationDetails?.processInstancesDetails?.[0]?.state?.state
    const handleCreateClick = async (e) => {
        
        // const result = await trigger(["lor", "fileno", "dlperiod", "fromDate", "aggDate", "agencyname", "officerInChargedesig","officerIncharge","work"])
        const obj = {
            "workIdentificationNumber": "123423",
            "fileNumber": "32423",
            "negotiatedPercentage": "2",
            "contractorId": "324",
            "securityDeposit": "324",
            "bankGuarantee": "432",
            "emdAmount": "123",
            "contractPeriod": "32",
            "defectLiabilityPeriod": "12",
            "fileDate": "2022-12-31",
            "agreementDate": "2021-12-31",
            "agencyName": {
                "name": "Vipul"
            },
            "officerInChargedesig": {
                "name": "Shaifali"
            },
            "officerIncharge": {
                "name": "Nipun"
            },
            "uploads": [],
        }
        const fieldsToValidate = Object.keys(obj)
        const result = await trigger(fieldsToValidate)
        if (result) {
            setShowModal(true)
        }
    }

    
    const { t } = useTranslation()
    const [showModal, setShowModal] = useState(false)
    const {
        register,
        control,
        watch,
        setValue,
        getValues,
        unregister,
        handleSubmit,
        formState: { errors, ...rest },
        reset, 
        trigger,
        ...methods
    } = useForm({
        defaultValues:defaultFormValues,
        mode: "onSubmit"
    });

    const dummyData = {
        agencyName: [
            { name: "agency1" }, { name: "agency2" }, { name: "agency3" }
        ],
        designation: [{ name: "d1" }, { name: "d2" }, { name: "d3" }],
        nameOfOfficer: [{ name: "officer1" }, { name: "officer2" }, { name: "officer3" }, {
            name: "officer4"
        }],

    }

    const { isLoading: desLoading, data: designationData } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getCurrentTenantId(),
        "common-masters",
        [
            {
                "name": "Designation"
            }
        ]
    );

    designationData?.["common-masters"]?.Designation?.map(designation=> {
        designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
    })

    
    

    const { estimateNumber,subEstimateNumber } = Digit.Hooks.useQueryParams();
    //search this estimate here
    // Call search approved estimate API by using params tenantInfo, filters, config
    const { isLoading: isLoadingEstimateSearch, isError: isErrorEstimateSearch, data: estimateSearchResponse, isSuccess: estimateSearchSuccess } = Digit.Hooks.works.useSearchWORKS({ tenantId: Digit.ULBService.getCurrentTenantId(), filters: { estimateDetailNumber:subEstimateNumber }, config: { enabled: subEstimateNumber ? true:false } });
        
    const estimate = estimateSearchResponse?.estimates?.[0]
    const subEstimateDetails = estimate?.estimateDetails?.filter(subEs => subEs?.estimateDetailNumber===subEstimateNumber)?.[0]
    const selectedDesignation = useWatch({ control: control, name: "officerInChargedesig", defaultValue: "" });
    //use this designation to make an hrms search and get the options for officer in charge from there

    const { isLoading, isError, error, data: employeeData } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code }, Digit.ULBService.getCurrentTenantId(), null, null,{enabled:!!selectedDesignation});

    const Employees = employeeData? employeeData.Employees : []
    Employees.map(emp => emp.nameOfEmp = emp.user.name)
    

    let validation = {}
    const checkKeyDown = (e) => {
        if (e.code === 'Enter') e.preventDefault();
    };
    const convertToNegative = (e) => {
        
        const aggrementAmount = subEstimateDetails?.amount
        let value = getValues("negotiatedPercentage")
        const setThisValue = (Math.abs(parseFloat(value)) * -1).toFixed(2)
        setValue("negotiatedPercentage", setThisValue , { shouldValidate: true });
        value= setThisValue ;
        const result = aggrementAmount - ((Math.abs(parseFloat(value)) * aggrementAmount) / 100)
        //setValue('negotiatedPercentage', `-${Math.abs(value)}`, { shouldValidate: true })
        setValue('aggrementAmount', result.toFixed(2).toString(), { shouldValidate: true })
    }
    const convertToPositive = (e) => {
        const aggrementAmount = subEstimateDetails?.amount
        let value = getValues("negotiatedPercentage")
        const setThisValue = Math.abs((parseFloat(value))).toFixed(2)
        setValue("negotiatedPercentage", setThisValue, { shouldValidate: true });
        value = setThisValue;
        const result = aggrementAmount + ((Math.abs(parseFloat(value)) * aggrementAmount) / 100)
        //setValue('negotiatedPercentage', Math.abs(value), { shouldValidate: true })
        setValue('aggrementAmount', result.toFixed(2).toString(), { shouldValidate: true })
    }
    return (
        <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
            <Header styles={{ "marginLeft": "14px" }}>{isEdit ? t("WORKS_MODIFY_LOI"): t("WORKS_CREATE_LOI")}</Header>
            <Card >
                <CardSectionHeader >{t(`WORKS_LOI_DETAILS`)}</CardSectionHeader>
                <StatusTable>
                    {isEdit && <Row label={`${t("WORKS_LOI_ID")}:`} text={loiNumber} textStyle={{ whiteSpace: "pre" }} />}
                    {isEdit && <Row label={`${t("WORKS_LOI_STATUS")}:`} text={state?.data?.applicationDetails?.processInstancesDetails?.[0]?.state?.state} textStyle={{ whiteSpace: "pre", color: "red" }} />}
                </StatusTable>
                {isLoadingEstimateSearch && <Loader />}
                {estimateSearchSuccess  && <StatusTable>
                    <Row label={`${t("WORKS_ESTIMATE_NO")}:`} text={estimate?.estimateNumber} textStyle={{ whiteSpace: "pre" }} />
                    <Row
                        label={`${t("WORKS_NAME_OF_WORK")}:`}
                        text={estimate?.estimateDetails?.[0]?.name}
                        textStyle={{ whiteSpace: "pre" }}
                    />
                    <Row label={`${t("WORKS_SUB_ESTIMATE_NO")}:`} text={estimate?.estimateDetails?.[0]?.estimateDetailNumber} />
                </StatusTable>}
                
                {showModal && <ProcessingModal
                    t={t}
                    heading={"WORKS_PROCESSINGMODAL_HEADER"}
                    closeModal={() => setShowModal(false)}
                    actionCancelLabel={"WORKS_CANCEL"}
                    actionCancelOnSubmit={() => setShowModal(false)}
                    actionSaveLabel={"WORKS_FORWARD_LOI"}
                    actionSaveOnSubmit={onFormSubmit}
                    onSubmit={onFormSubmit}
                    control={control}
                    register={register}
                    handleSubmit={handleSubmit}
                    errors={errors}
                    action={"loi"}
                    setValue={setValue}
                />}

                
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ABSTRACT_ESTIMATE_NO`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="workIdentificationNumber" inputRef={register({
                            required: false,
                            pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                        })} />
                        {errors && errors?.workIdentificationNumber?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_NO`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="fileNumber" inputRef={register({ required: true, pattern: /^[a-zA-Z0-9_.$@#\/]*$/ })} />
                        {errors && errors?.fileNumber?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.fileNumber?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_DATE`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="fileDate"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => <DatePicker
                                style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
                        />
                        {errors && errors?.fileDate?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <StatusTable>
                    <Row label={`${t("WORKS_ESTIMATED_AMT")}:`} text={subEstimateDetails?.amount} />
                </StatusTable>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FINALIZED_PER`)}:*`}</CardLabel>
                    <div className='field'>
                        <div className='percent-input'>
                            <button type="button" onClick={convertToPositive} style={{ "height": "40px", "width": "40px" }}><AddIcon fill={"#C84C0E"} styles={{ "display": "revert" }} /></button>
                            <button type="button" onClick={convertToNegative} style={{ "height": "40px", "width": "40px" }}><SubtractIcon fill={"#AFA8A4"} styles={{ "display": "revert", "marginTop": "7px" }} /></button>
                            <TextInput name="negotiatedPercentage" defaultValue={0} type="number" inputRef={register({ validate: value => parseInt(value) >= -100 && parseInt(value) <= 100, required: true })}/>
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
                        {errors && errors?.negotiatedPercentage?.type === "required" && (
                            <CardLabelError >{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.negotiatedPercentage?.type === "validate" && (
                            <CardLabelError>{t(`WORKS_LIMIT_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGREEMENT_AMT`)}:`}</CardLabel>
                    <TextInput className={"field"} name="aggrementAmount" type="number" defaultValue={parseInt(subEstimateDetails?.amount)} disabled  inputRef={register()} style={{ backgroundColor: "#E5E5E5" }} />
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_AGGREEMENT_DETAILS`)}</CardSectionHeader>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DATE_OF_AGG`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            render={(props) => <DatePicker style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
                            name="agreementDate"
                            control={control}
                            rules={{ required: true }}
                        />
                        {errors && errors?.agreementDate?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGENCY_NAME`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="agencyName"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={dummyData.agencyName}
                                        selected={props?.value}
                                        optionKey={"name"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.agencyName?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_ID`)}:`}</CardLabel>
                    <TextInput style={{ backgroundColor: "#E5E5E5" }} disabled className={"field"} name="contractorId" inputRef={register()} />
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ADD_SECURITY_DP`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="securityDeposit" type="number" inputRef={register({
                            pattern: /^[0-9]*$/
                        })}
                            
                        />
                        {errors && errors?.securityDeposit?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BANK_G`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="bankGuarantee" inputRef={register({
                            pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                        })} />
                        {errors && errors?.bankGuarantee?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EMD`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="emdAmount" type="number" inputRef={register({
                            required: true,
                            pattern: /^[0-9]*$/
                        })} />
                        {errors && errors?.emdAmount?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.emdAmount?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_PERIOD`)}:`}</CardLabel>
                    <div className='field'>
                        <TextInput name="contractPeriod" type="number" inputRef={register({
                            pattern: /^[0-9]*$/
                        })} />
                        {errors && errors?.contractPeriod?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DEFECT_LIA`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="defectLiabilityPeriod" type="number" inputRef={register({ required: true, pattern: /^[0-9]*$/ })} />
                        {errors && errors?.defectLiabilityPeriod?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                        {errors && errors?.defectLiabilityPeriod?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                {desLoading?<Loader />: <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_OFFICER_INCHARGE_DES`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="officerInChargedesig"
                            control={control}
                            rules={{ required: true }}
                            render={(props) => {
                                return (
                                    <Dropdown
                                        option={designationData?.["common-masters"]?.Designation}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
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
                </LabelFieldPair>}

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
                                        option={selectedDesignation? Employees : []}
                                        selected={props?.value}
                                        optionKey={"nameOfEmp"}
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
                    <SubmitBar onSubmit={handleCreateClick} label={isEdit ? t("WORKS_PROCEED_TO_FORWARD"):t("WORKS_CREATE_LOI")} />
                </ActionBar>
            </Card>
        </form>

    )
}

export default CreateLoiForm