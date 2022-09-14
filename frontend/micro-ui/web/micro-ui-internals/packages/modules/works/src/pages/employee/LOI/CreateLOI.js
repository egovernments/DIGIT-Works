import React, { useReducer,useState } from 'react'
import { Controller, useForm } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, DatePicker, Row, StatusTable, CardLabelError, AddIcon, SubtractIcon, InfoBannerIcon } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import CreateLoiForm from '../../../components/CreateLOI/CreateLoiForm';
import { useHistory } from 'react-router-dom';


const CreateLOI = () => {
    const { mutate: LOIMutation } = Digit.Hooks.works.useCreateLOI();
    const history = useHistory();
    const {t} = useTranslation()
    const onFormSubmit = async (_data) => {
        //debugger
        const data = Object.keys(_data)
            .filter((k) => _data[k])
            .reduce((acc, key) => ({ ...acc, [key]: typeof _data[key] === "object" && key!=="uploads" ? _data[key].name : _data[key] }), {})
        
        data.fileDate = Digit.Utils.pt.convertDateToEpoch(data.fileDate)
        data.agreementDate = Digit.Utils.pt.convertDateToEpoch(data.agreementDate)
        const letterOfIndent = {letterOfIndent:data}
        //console.log(_data);

        await LOIMutation(letterOfIndent,{
            onError:(error,variables)=>{
                //debugger
            },
            onSuccess:async (responseData,requestData)=>{
                //debugger
                //console.log(window.contextPath);
                //take us to the response page on succesfull create
                // const state = {
                //     header: "Estimate Created and Forwarded Successfully",
                //     id: "EP/ENG/00001/07/2021-22",
                //     info: "Estimate ID",
                //     message: "A new Estimate has been created successfully and forwarded to Designation or the <Department>  Department for processing.",
                //     links: [
                //         {
                //             name: "Create new Estimate",
                //             redirectUrl: "/digit-ui/employee/works/create-estimate",
                //             code: "",
                //             svg: "CreateEstimateIcon"
                //         }
                //     ]
                // }
                //create a state obj in this format and push
                const state = {
                    header: t("WORKS_LOI_RESPONSE_HEADER"),
                    id: "LI/ENG/0001/07/2021-22",
                    info: t("WORKS_LOI_ID"),
                    message: t("WORKS_LOI_RESPONSE_MESSAGE"),
                    links: [
                        {
                            name: t("WORKS_CREATE_NEW_LOI"),
                            redirectUrl: `/${window.contextPath}/employee/works/create-loi`,
                            code: "",
                            svg: "CreateEstimateIcon"
                        },
                        {
                            name: t("WORKS_GOTO_LOI_INBOX"),
                            redirectUrl: `/${window.contextPath}/employee/works/LOIInbox`,
                            code: "",
                            svg: "CreateEstimateIcon"
                        },
                    ]
                }
                history.push(`/${window.contextPath}/employee/works/response`,state)
            }
        })
        

    }


    

    return (
//         <form onSubmit={handleSubmit(onFormSubmit)}>
//             <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_LOI")}</Header>
//             <Card >
                
//                 <CardSectionHeader >{t(`WORKS_LOI_DETAILS`)}</CardSectionHeader>
//                 <StatusTable>
//                     <Row label={`${t("WORKS_ESTIMATE_NO")}:`} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
//                     <Row
//                         label={`${t("WORKS_NAME_OF_WORK")}:`}
//                         text={"NA"}
//                         textStyle={{ whiteSpace: "pre" }}
//                     />
//                     <Row label={`${t("WORKS_SUB_ESTIMATE_NO")}:`} text={"NA"} />
//                 </StatusTable>
//                 {showModal && <ProcessingModal
//                     t={t}
//                     heading={"WORKS_PROCESSINGMODAL_HEADER"}
//                     closeModal={() => setShowModal(false)}
//                     actionCancelLabel={"WORKS_CANCEL"}
//                     actionCancelOnSubmit={() => setShowModal(false)}
//                     actionSaveLabel={"WORKS_FORWARD"}
//                     actionSaveOnSubmit={onFormSubmit}
//                     onSubmit={onFormSubmit}
//                     control={control}
//                     register={register}
//                     handleSubmit={handleSubmit}
//                     errors={errors}

//                 />}

                
//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ABSTRACT_ESTIMATE_NO`)}:`}</CardLabel>
//                     <div className='field'>
//                     <TextInput name="absEstimateNo" inputRef={register({
//                         required:false,
//                         pattern: /^[a-zA-Z0-9_.$@#\/]*$/
//                     })} />
//                         {errors && errors?.absEstimateNo?.type === "pattern" && (
//                             <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_NO`)}:*`}</CardLabel>
//                     <div className='field'>
//                         <TextInput name="fileno" inputRef={register({ required: true, pattern: /^[a-zA-Z0-9_.$@#\/]*$/ })}  />
//                         {errors && errors?.fileno?.type === "required" && (
//                             <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                         {errors && errors?.fileno?.type === "pattern" && (
//                             <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FILE_DATE`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <Controller
//                             name="fromDate"
//                             control={control}
//                             rules={{ required: true }}
//                         render={(props) => <DatePicker 
//                                 style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
//                     />
//                     {errors && errors?.fromDate?.type === "required" && (
//                         <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                 </div>
//                 </LabelFieldPair>

//                 <CardSectionHeader >{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
//                 <StatusTable>
//                     <Row label={`${t("WORKS_ESTIMATED_AMT")}:`} text={"NA"} />
//                 </StatusTable>
//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FINALIZED_PER`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <div className='percent-input'>
//                         <button style={{"height":"40px","width":"40px"}}><AddIcon fill={"#F47738"} styles={{"display":"revert"}}/></button>
//                             <button style={{ "height": "40px", "width": "40px" }}><SubtractIcon fill={"#AFA8A4"} styles={{ "display": "revert","marginTop":"7px" }} /></button>
//                             <TextInput name="percent" type="number" inputRef={register({validate:value=>parseInt(value)>=-100 && parseInt(value)<=100,required:true})} />
//                             <div className="tooltip" style={{ "margin": "8px -30px 10px 10px" }}>
//                                 <InfoBannerIcon fill="#0b0c0c"  />
//                                 <span className="tooltiptext" style={{
//                                     whiteSpace: "nowrap",
//                                     fontSize: "medium"
//                                 }}>
//                                     {`${t(`WORKS_PERCENT_TOOLTIP`)}`}
//                                 </span>
//                             </div>        
//                     </div>
//                         {errors && errors?.percent?.type === "required" && (
//                             <CardLabelError >{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                         {errors && errors?.percent?.type === "validate" && (
//                             <CardLabelError>{t(`WORKS_LIMIT_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>
//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGREEMENT_AMT`)}:`}</CardLabel>
//                     <TextInput className={"field"} name="aggrementAmount" disabled={true} inputRef={register()} />
//                 </LabelFieldPair>

//                 <CardSectionHeader >{t(`WORKS_AGGREEMENT_DETAILS`)}</CardSectionHeader>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DATE_OF_AGG`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <Controller
//                         render={(props) => <DatePicker style={{ "width": "100%" }} date={props.value} onChange={props.onChange} onBlur={props.onBlur} />}
//                         name="aggDate"
//                         control={control}
//                         rules={{required:true}}
//                     />
//                         {errors && errors?.aggDate?.type === "required" && (
//                             <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_AGENCY_NAME`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <Controller
//                         name="agencyname"
//                         control={control}
//                         rules={{ required: true }}
//                         render={(props) => {
//                             return (
//                                 <Dropdown
//                                     option={dummyData}
//                                     selected={props?.value}
//                                     optionKey={"name"}
//                                     t={t}
//                                     select={props?.onChange}
//                                     onBlur={props.onBlur}
//                                 />
//                             );
//                         }}
//                     />
//                         {errors && errors?.agencyname?.type === "required" && (
//                         <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                         </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_ID`)}:`}</CardLabel>
//                     <TextInput className={"field"} name="contId" inputRef={register()} />
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_ADD_SECURITY_DP`)}:`}</CardLabel>
//                     <div className='field'>
//                     <TextInput name="securitydeposit" inputRef={register({
//                         pattern: /^[0-9]*$/
//                     })} 
//                        />
//                         {errors && errors?.securitydeposit?.type === "pattern" && (
//                             <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BANK_G`)}:`}</CardLabel>
//                     <div className='field'>
//                     <TextInput name="bankG" inputRef={register({
//                         pattern: /^[a-zA-Z0-9_.$@#\/]*$/
//                     })} />
//                     {errors && errors?.bankG?.type === "pattern" && (
//                         <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EMD`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <TextInput  name="emd" inputRef={register({required:true,
//                         pattern: /^[0-9]*$/
// })}   />
//                     {errors && errors?.emd?.type === "required" && (
//                         <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                         {errors && errors?.emd?.type === "pattern" && (
//                             <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONT_PERIOD`)}:`}</CardLabel>
//                     <div className='field'>
//                     <TextInput name="contperiod" inputRef={register({
//                         pattern: /^[0-9]*$/
//                     })}/>
//                         {errors && errors?.contperiod?.type === "pattern" && (
//                             <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_DEFECT_LIA`)}:*`}</CardLabel>
//                     <div className='field'>
//                         <TextInput name="dlperiod" inputRef={register({ required: true, pattern: /^[0-9]*$/ })} />
//                         {errors && errors?.dlperiod?.type === "required" && (
//                             <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                         {errors && errors?.dlperiod?.type === "pattern" && (
//                             <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_OFFICER_INCHARGE_DES`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <Controller
//                         name="officerInChargedesig"
//                         control={control}
//                         rules={{ required: true }}
//                         render={(props) => {
//                             return (
//                                 <Dropdown
//                                     option={dummyData}
//                                     selected={props?.value}
//                                     optionKey={"name"}
//                                     t={t}
//                                     select={props?.onChange}
//                                     onBlur={props.onBlur}
//                                 />
//                             );
//                         }}
//                     />
//                         {errors && errors?.officerInChargedesig?.type === "required" && (
//                             <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>

//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_OFFICER_INCHARGE_NAME`)}:*`}</CardLabel>
//                     <div className='field'>
//                     <Controller
//                         name="officerIncharge"
//                         control={control}
//                         rules={{ required: true }}
//                         render={(props) => {
//                             return (
//                                 <Dropdown
//                                    onBlur={props.onBlur}
//                                     option={dummyData}
//                                     selected={props?.value}
//                                     optionKey={"name"}
//                                     t={t}
//                                     select={props?.onChange}
//                                 />
//                             );
//                         }}
//                     />
//                         {errors && errors?.officerIncharge?.type === "required" && (
//                             <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
//                     </div>
//                 </LabelFieldPair>
//                 {/* --------------------------------------- */}

//                 <CardSectionHeader >{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
//                 <LabelFieldPair>
//                     <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600 " }}>{t(`WORKS_UPLOAD_FILES`)}</CardLabel>
//                     <div className='field'>
//                         <Controller
//                             name="uploads"
//                             control={control}
//                             rules={{ required: false }}
//                             render={({ onChange, ref, value = [] }) => {
//                                 function getFileStoreData(filesData) {
//                                     const numberOfFiles = filesData.length
//                                     let finalDocumentData = []
//                                     if (numberOfFiles > 0) {
//                                         filesData.forEach(value => {
//                                             finalDocumentData.push({
//                                                 fileName: value?.[0],
//                                                 fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
//                                                 documentType: value?.[1]?.file?.type
//                                             })
//                                         })
//                                     }
//                                     onChange(finalDocumentData)
//                                 }
//                                 return <MultiUploadWrapper
//                                     t={t}
//                                     module="works"
//                                     tenantId={"pb"}
//                                     getFormState={getFileStoreData}
//                                     showHintBelow={true}
//                                     setuploadedstate={value}
//                                     allowedFileTypesRegex={allowedFileTypes}
//                                     allowedMaxSizeInMB={5}
//                                     hintText={t("WORKS_DOC_UPLOAD_HINT")}
//                                     maxFilesAllowed={5}
//                                 />
//                             }
//                             }
//                         />
//                     </div>
//                 </LabelFieldPair>

//                 <ActionBar>
//                     <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_CREATE_ESTIMATE")} />
//                 </ActionBar>
//             </Card>
//         </form>
        <CreateLoiForm onFormSubmit={onFormSubmit} />

    )
}

export default CreateLOI