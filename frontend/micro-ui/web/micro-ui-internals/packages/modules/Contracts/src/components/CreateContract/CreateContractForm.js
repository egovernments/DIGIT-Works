import React, { Fragment, useState, useMemo } from 'react'
import { Controller, useForm, useWatch } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, CardLabelError, Loader, DatePicker, RadioButtons, InfoBannerIcon } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import ProcessingModal from '@egovernments/digit-ui-module-works/src/components/Modal/ProcessingModal';
import { format } from "date-fns";
import { useLocation } from 'react-router-dom';

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateContractForm = ({ onFormSubmit }) => {

    const { t } = useTranslation()
    const {state} = useLocation();
    const {index, data} = state;
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

    const currentContractType = useMemo(()=>[
        { code: "WORKS_WORK_ORDER", name: "WORKS_WORK_ORDER"},
        { code: "WORKS_PURCHASE_ORDER", name: "WORKS_PURCHASE_ORDER"}
    ],[t])
    const [balanceAmount, setBalanceAmount] = useState(0);
    const [contractedAmount, setContractedAmount] = useState(0);
    const selectedDesignation = useWatch({ control: control, name: "officerInChargedesig", defaultValue: "" });
    //use this designation to make an hrms search and get the options for officer in charge from there
    let officerIncharge = []
    const { isLoading, isError, error, data: employeeData } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code }, Digit.ULBService.getCurrentTenantId(), null, null,{enabled:!!selectedDesignation});

    employeeData?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")
    officerIncharge = employeeData?.Employees?.length > 0 ? employeeData?.Employees : []
    officerIncharge?.map(officer=> {
        officer.i18nKey = `ES_COMMON_OFFICER_${officer?.nameOfEmp}`
    })
    const tenant = Digit.ULBService.getStateId()
    
    const dummyData = [{name:"Orgn1"},{name:"Orgn2"},{name:"Orgn3"}]

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

    designationData?.["common-masters"]?.Designation?.map(designation=> {
        designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
    })
    designationData?.["common-masters"]?.Department?.map(department=> {
        department.i18nKey = `ES_COMMON_${department?.code}`
    })


    const getDate = () => {
        return format(new Date(),'yyyy-MM-dd').toString();
    }
    
    const handleCreateClick = async () => {
        const fieldsToValidate = ['fileNumber', 'fileDate', 'executingAuthority', 'contractedAmount', 'currentContractType', 'currentContractAmount', 'balanceAmount', 'agreementDate', 'organisationId', 'officerInChargedesig', 'officerIncharge', 'contractPeriod']

        const result = await trigger(fieldsToValidate)
        if (result) {
            setShowModal(true)
        }
    }

    const [showModal, setShowModal] = useState(false)

    const checkKeyDown = (e) => {
        if (e.code === 'Enter') e.preventDefault();
    };

    const errorStyle = {"marginBottom" : "0px","whiteSpace":"nowrap"}
    const contractStyle = {"marginTop":"20px","marginBottom":"4px"}

    return (
        <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
            <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_CONTRACT")}</Header>
            <Card >
                <CardSectionHeader style={{ "marginTop": "14px" }} >{t(`WORKS_PROJECT_DETAILS`)}</CardSectionHeader>
                {/* TEXT INPUT LABEL */}
                <LabelFieldPair>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_ESTIMATE_NO`)}</CardLabel>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{data?.state?.estimateNumber}</CardLabel>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_NAME_OF_WORK`)}</CardLabel>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{data?.state?.estimateDetails[index]?.name}</CardLabel>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{t(`WORKS_SUB_ESTIMATE_NO`)}</CardLabel>
                    <CardLabel style={{ "marginTop": "14px", "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{data?.state?.estimateDetails[index]?.estimateDetailNumber}</CardLabel>
                </LabelFieldPair>

                {/* Modal */}
                {showModal && <ProcessingModal
                    t={t}
                    heading={"WORKS_PROCESSINGMODAL_HEADER"}
                    closeModal={() => setShowModal(false)}
                    actionCancelLabel={"WORKS_CANCEL"}
                    actionCancelOnSubmit={() => setShowModal(false)}
                    actionSaveLabel={"WORKS_FORWARD_FOR_APPROVAL"}
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
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_FILE_NO`)}</CardLabel>
                    <div className='field'>
                        <TextInput 
                            name="fileNumber" 
                            inputRef={register({ pattern: /^[a-zA-Z0-9_.$@#\/]*$/ })} 
                            style={contractStyle}
                        />        
                        {errors && errors?.fileNumber?.type === "pattern" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_FILE_DATE`)}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="fileDate"
                            control={control}
                            render={(props) => 
                            <DatePicker
                                style={{ "width": "100%","marginTop":"20px","marginBottom":"4px" }}
                                date={props.value}
                                onChange={props.onChange}
                                onBlur={props.onBlur}
                                max={Digit.Utils.date.getDate()}
                            />}
                        />
                        {errors && errors?.fileDate?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EXECUTING_AUTH`)}:*`}</CardLabel>
                    <div className='field' style={{"margin": "0px"}}>
                        <Controller
                            name="executingAuthority"
                            control={control}
                            rules={{ required: true }}
                            defaultValue={assignedToOptions[0]}
                            render={(props) => {
                                return (
                                    <RadioButtons
                                        style={{ display: "flex",columnGap:"50px","marginTop":"20px","marginBottom":"4px"}}
                                        onSelect={props.onChange} 
                                        selectedOption={props.value} 
                                        optionsKey="name" 
                                        options={assignedToOptions}
                                     />
                                );
                            }}
                        />
                        {errors && errors?.executingAuthority?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>

                {/* FINANCIAL DETAILS */}
                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_PROJECT_ESTIMATE_AMT`)}</CardLabel>
                    <div className='field' style={{"marginBottom": "24px"}}>
                        <CardLabel style={{ "fontSize": "16px"}} >{`5,00,000`}</CardLabel>
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_CONTRACTED_AMT`)}</CardLabel>
                        <TextInput 
                            className={"field"} 
                            name="contractedAmount" 
                            inputRef={register()} 
                            value={contractedAmount} 
                            disabled 
                            style={{ backgroundColor: "#E5E5E5","marginTop":"20px","marginBottom":"4px"}} 
                        />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONTRACT_TYPE`)}:*`}</CardLabel>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONTRACT_TYPE`)}:*`}</CardLabel>
                    <div className='field'>
                        <Controller
                            name="currentContractType"
                            control={control}
                            rules= {{required : true}}
                            render={(props) => {
                                return (
                                    <RadioButtons 
                                        style={{ display: "flex", columnGap:"50px","marginTop":"20px","marginBottom":"4px" }}
                                        onSelect={props.onChange} 
                                        selectedOption={props.value} 
                                        optionsKey="name" 
                                        options={currentContractType}
                                        innerStyles={{"marginBottom":"2px"}}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.currentContractType?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CURRENT_CONTRACT_AMT`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput 
                            name="currentContractAmount" 
                            inputRef={register({ pattern: /^$|^[0-9.\s]+$/ , required: true })}
                            style={contractStyle}
                        />
                            {errors && errors?.currentContractAmount?.type === "pattern" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}                        
                            {errors && errors?.currentContractAmount?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_BALANCE_AMT`)}</CardLabel>
                        <TextInput 
                            className={"field"} 
                            name="balanceAmount" 
                            inputRef={register()} 
                            value={balanceAmount} 
                            disabled 
                            style={{ backgroundColor: "#E5E5E5","marginTop":"20px","marginBottom":"4px" }} 
                        />
                </LabelFieldPair>
                {/* AGGREEMENT DETAILS */}
                <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_AGGREEMENT_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{t(`WORKS_DATE_OF_AGG`)}</CardLabel>
                    <div className='field'>
                    <Controller
                            name="agreementDate"
                            control={control}
                            defaultValue={getDate}
                            render={(props) => 
                            <DatePicker
                                style={{ "width": "100%","marginTop":"20px","marginBottom":"4px"}} 
                                date={props.value}
                                max={Digit.Utils.date.getDate()}
                                onChange={props.onChange} 
                                onBlur={props.onBlur} 
                            />}
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
                                        option={designationData?.["common-masters"]?.Department}
                                        selected={props?.value}
                                        optionKey={"i18nKey"}
                                        t={t}
                                        select={props?.onChange}
                                        onBlur={props.onBlur}
                                        style={contractStyle}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.nameOfOrganisation?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                    <div className="tooltip" style={{ "margin": "0px -30px 10px 10px" }}>
                        <InfoBannerIcon fill="#0b0c0c" />
                        <div className="tooltiptext" style={{
                            width:"220px",
                            height:"100px",
                            marginLeft:"0px",
                            overflowWrap: "break-word",
                            fontSize: "medium"
                        }}>
                            {`${t(`WORKS_ORGN_INFO`)}`}
                        </div>
                    </div>
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ORGN_ID`)}:*`}</CardLabel>
                        <TextInput 
                            style={{ backgroundColor: "#E5E5E5","marginTop":"20px","marginBottom":"4px" }} 
                            disabled 
                            className={"field"} 
                            name="organisationId" 
                            inputRef={register()}
                        />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_PERIOD`)}:*`}</CardLabel>
                    <div className='field'>
                        <TextInput 
                            name="contractPeriod" 
                            inputRef={register({ pattern: /^[+]?([1-9][0-9]*(?:[\.][0-9]*)?|0*\.0*[1-9][0-9]*)(?:[eE][+-][0-9]+)?$/ , required: true })}
                            style={contractStyle}
                        />
                            {errors && errors?.contractPeriod?.type === "pattern" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}                        
                            {errors && errors?.contractPeriod?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
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
                                        select={(val)=>{
                                            props.onChange(val)
                                            setValue("officerIncharge","")
                                        }}
                                        onBlur={props.onBlur}
                                        style={contractStyle}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.officerInChargedesig?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
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
                                        option={officerIncharge}
                                        selected={props?.value}
                                        optionKey={"nameOfEmp"}
                                        t={t}
                                        select={props?.onChange}
                                        style={contractStyle}
                                    />
                                );
                            }}
                        />
                        {errors && errors?.officerIncharge?.type === "required" && (
                            <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
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
        </form>
    )
}

export default CreateContractForm