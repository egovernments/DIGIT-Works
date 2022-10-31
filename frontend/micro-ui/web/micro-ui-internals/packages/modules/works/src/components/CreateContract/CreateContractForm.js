import React, { Fragment, useState, useMemo } from 'react'
import { Controller, useForm, useWatch } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, CardLabelError, Loader, DatePicker, RadioButtons } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import ProcessingModal from '../Modal/ProcessingModal';

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateContractForm = ({ onFormSubmit }) => {

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
        mode: "onSubmit"
    });
    const assignedToOptions = useMemo(
        () => [
          { code: "WORKS_COMMUNITY_ORGN", name: t("WORKS_COMMUNITY_ORGN") },
          { code: "WORKS_DEPARTMENT", name: t("WORKS_DEPARTMENT") },
        ],
        [t]
      );
    const [selectAssigned, setSelectedAssigned] = useState();

    const currentContractType = useMemo(()=>[
        { code: "WORKS_WORK_ORDER", name: "WORKS_WORK_ORDER"},
        { code: "WORKS_PURCHASE_ORDER", name: "WORKS_PURCHASE_ORDER"}
    ],[t])
    const [selectContactType, setSelectContractType] = useState();
    
    const selectedDesignation = useWatch({ control: control, name: "officerInChargedesig", defaultValue: "" });

    const {isLoading, isError, error, data: employeeData } = Digit.Hooks.hrms.useHRMSSearch({ Designation: selectedDesignation?.code }, Digit.ULBService.getCurrentTenantId(), null, null,{enabled:!!selectedDesignation});

    const Employees = employeeData? employeeData.Employees : []
    Employees.map(emp => emp.nameOfEmp = emp.user.name)

    const tenantId = Digit.ULBService.getCurrentTenantId();
    
    const tenant = Digit.ULBService.getStateId()
    const { isLoading:desgLoading, data:designationData, isFetched:desgFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
        "common-masters",
        [
            {
                "name": "Department"
            },
            {
                "name": "Designation"
            }
        ]
        );

    if (designationData?.[`common-masters`]) {
        var { Department, Designation } = designationData?.[`common-masters`]
    }
    Department?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_${item?.code}`)}))
    Designation?.map((item)=> Object.assign(item, {i18nKey:t(`ES_COMMON_DESIGNATION_${item?.name}`)}))
    const getDate = () => {
        const today = new Date();

        const date = today.getDate() + '/' + (today.getMonth() + 1) + '/' + today.getFullYear();
        return date
    }
    
    const handleCreateClick = async () => {
        const fieldsToValidate = ['fileNumber', 'fileDate', 'executingAuthority', 'contractedAmount', 'currentContractType', 'currentContractAmount', 'balanceAmount', 'agreementDate', 'OrganisationId', 'officerInChargedesig', 'officerIncharge']

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

    const onRadioChange = (value) => {
    setSelectedAssigned(value);
    };

    const onContractTypeChange = (value) => {
        setSelectContractType(value)
    }
    return (
        ( desgFetched )?
        <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
            <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_CONTRACT")}</Header>
            <Card >
                <CardSectionHeader style={{ "marginTop": "14px" }} >{t(`WORKS_PROJECT_DETAILS`)}</CardSectionHeader>
                {/* TEXT INPUT LABEL */}
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_ESTIMATE_NO`)}</CardLabel>
                    <CardLabel style={{ "fontSize": "16px" }} >{`1136/TO/DB/FLOOD/10-11`}</CardLabel>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_NAME_OF_WORK`)}</CardLabel>
                    <CardLabel style={{ "fontSize": "16px" ,"width": "100%", marginLeft:"150px" }} >{`Construction of CC drain from D No 45-142-A-58-A to 45-142-472-A at Venkateramana Colony in Ward No 43`}</CardLabel>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_SUB_ESTIMATE_NO`)}</CardLabel>
                    <CardLabel style={{ "fontSize": "16px"}} >{`LE/ENG/00002/10/2017-18`}</CardLabel>
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

                {/* PROJECT DETAILS */}
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_NO`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="fileNumber" inputRef={register({ pattern: /^[a-zA-Z0-9_.$@#\/]*$/ })} />
        
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
                            // rules={{ required: true }}
                            render={(props) => <DatePicker
                                style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
                        />
                        {errors && errors?.fileDate?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EXECUTING_AUTH`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="executingAuthority"
                            control={control}
                            rules={{ required: true }}
                            render={() => {
                                return (
                                    <RadioButtons
                                        style={{ display: "flex" }}
                                        onSelect={onRadioChange} 
                                        selectedOption={selectAssigned} 
                                        optionsKey="name" 
                                        options={assignedToOptions}
                                        value={selectAssigned} />
                                );
                            }}
                        />
                        {errors && errors?.executingAuthority?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                {/* FINANCIAL DETAILS */}
                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_PROJECT_ESTIMATE_AMT`)}</CardLabel>
                    <CardLabel style={{ "fontSize": "16px"}} >{`5,00,000`}</CardLabel>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_CONTRACTED_AMT`)}</CardLabel>
                    <TextInput className={"field"} name="contractedAmount" inputRef={register()} value="0" disabled style={{ backgroundColor: "#E5E5E5" }} />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CURRENT_CONTRACT_TYPE`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="currentContractType"
                            control={control}
                            rules= {{required : true}}
                            render={() => {
                                return (
                                    <RadioButtons 
                                        style={{ display: "flex" }}
                                        onSelect={onContractTypeChange} 
                                        selectedOption={selectContactType} 
                                        optionsKey="name" 
                                        options={currentContractType}
                                        />
                                );
                            }}
                        />
                        {errors && errors?.currentContractType?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CURRENT_CONTRACT_AMT`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput name="currentContractAmount" inputRef={register({
                            pattern: /^$|^[0-9\s]+$/ , required: true
                        })}
                        />
                            {errors && errors?.fileNumber?.type === "pattern" && (
                            <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}                        
                            {errors && errors?.currentContractAmount?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_BALANCE_AMT`)}</CardLabel>
                    <div className='field'>
                        <TextInput className={"field"} name="balanceAmount" inputRef={register()} value="0" disabled style={{ backgroundColor: "#E5E5E5" }} />
                    </div>
                </LabelFieldPair>
                {/* AGGREEMENT DETAILS */}
                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_AGGREEMENT_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DATE_OF_AGG`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            defaultValue={getDate()}
                            render={(props) => <DatePicker style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
                            name="agreementDate"
                            control={control}
                        />
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_NAME_OF_ORGN`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="nameOfOrganisation"
                            control={control}
                            rules={{ required: true }}
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
                        {errors && errors?.nameOfOrganisation?.type === "required" && (
                            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ORGN_ID`)}:*`}</CardLabel>
                    <TextInput style={{ backgroundColor: "#E5E5E5" }} disabled className={"field"} name="OrganisationId" inputRef={register()} />
                </LabelFieldPair>
                {desgLoading?<Loader />: <LabelFieldPair>
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
                    <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_PROCEED_FORWARD")} />
                </ActionBar>
            </Card>
        </form> : <Loader/>
    )
}

export default CreateContractForm