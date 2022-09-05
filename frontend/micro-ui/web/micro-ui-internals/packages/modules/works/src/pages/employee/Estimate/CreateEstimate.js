import React, { Fragment, useState } from 'react'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper, ActionBar, SubmitBar, CardLabelError } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import CreateEstimateForm from '../../../components/CreateEstimate/CreateEstimateForm';

//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateEstimate = (props) => {
    

    const onFormSubmit = (_data) => {
        //debugger
        //console.log(errors)
        //console.log(_data);
    }
    
    
    return (

        // <form onSubmit={handleSubmit(onFormSubmit)}>
        //     <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_ESTIMATE")}</Header>
        //     <Card >
        //         <CardSectionHeader style={{ "marginTop": "14px" }} >{t(`WORKS_ESTIMATE_DETAILS`)}</CardSectionHeader>
        //         {/* TEXT INPUT ROW */}
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{`${t(`WORKS_DATE_PROPOSAL`)}:*`}</CardLabel>
        //             <TextInput className={"field"} name="reasonDocumentNumber" inputRef={register()} value={getDate()} disabled />
        //         </LabelFieldPair>


        //         {/* Modal */}
        //         {showModal && <ProcessingModal
        //             t={t}
        //             heading={"WORKS_PROCESSINGMODAL_HEADER"}
        //             closeModal={() => setShowModal(false)}
        //             actionCancelLabel={"WORKS_CANCEL"}
        //             actionCancelOnSubmit={() => setShowModal(false)}
        //             actionSaveLabel={"WORKS_FORWARD"}
        //             actionSaveOnSubmit={onFormSubmit}
        //             onSubmit={onFormSubmit}
        //             control={control}
        //             register={register}
        //             handleSubmit={handleSubmit}
        //             errors={errors}

        //         />}

        //         {/* DROPDOWN ROW */}
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EXECUTING_DEPT`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="edept"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown
        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.edept?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
        //             </div>
        //         </LabelFieldPair>

        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_LOR`)}:`}</CardLabel>
        //             <div className='field'>
        //                 <TextInput name="lor" inputRef={register({
        //                     pattern: /^[a-zA-Z0-9_.$@#\/]*$/
        //                 })}
        //                 />


        //                 {errors && errors?.lor?.type === "pattern" && (
        //                     <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
        //             </div>
        //         </LabelFieldPair>

        //         <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_LOCATION_DETAILS`)}</CardSectionHeader>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_WARD`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="ward"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown
        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.ward?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
        //             </div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_LOCATION`)}:`}</CardLabel>
        //             <Controller
        //                 name="location"
        //                 control={control}
        //                 //rules={{ required: true }}
        //                 render={(props) => {
        //                     return (
        //                         <Dropdown
        //                             className={`field`}
        //                             option={dummyData}
        //                             selected={props?.value}
        //                             optionKey={"name"}
        //                             t={t}
        //                             select={props?.onChange}
        //                             onBlur={props.onBlur}
        //                         />
        //                     );
        //                 }}
        //             />
        //         </LabelFieldPair>

        //         <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_WORK_DETAILS`)}</CardSectionHeader>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BENEFICIERY`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="beneficiery"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.beneficiery?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
        //             </div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_WORK_NATURE`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="natureofwork"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.natureofwork?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_WORK_TYPE`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="workType"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown
        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.workType?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_SUB_TYPE_WORK`)}:`}</CardLabel>
        //             <Controller
        //                 name="subtypework"
        //                 control={control}
        //                 render={(props) => {
        //                     return (
        //                         <Dropdown
        //                             className={`field`}
        //                             option={dummyData}
        //                             selected={props?.value}
        //                             optionKey={"name"}
        //                             t={t}
        //                             select={props?.onChange}
        //                             onBlur={props.onBlur}
        //                         />
        //                     );
        //                 }}
        //             />
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_MODE_OF_INS`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="modeofens"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown
        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.modeofens?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
        //             </div>
        //         </LabelFieldPair>

        //         <CardSectionHeader style={{ "marginTop": "14px" }}>{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FUND`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="fund"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.fund?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_FUNCTION`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="function"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.function?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BUDGET_HEAD`)}:*`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="budgetHead"
        //                     control={control}
        //                     rules={{ required: true }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.budgetHead?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_SCHEME`)}:`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="scheme"
        //                     control={control}
        //                     rules={{ required: false }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.scheme?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>

        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{`${t(`WORKS_SUB_SCHEME`)}:`}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="subScheme"
        //                     control={control}
        //                     rules={{ required: false }}
        //                     render={(props) => {
        //                         return (
        //                             <Dropdown

        //                                 option={dummyData}
        //                                 selected={props?.value}
        //                                 optionKey={"name"}
        //                                 t={t}
        //                                 select={props?.onChange}
        //                                 onBlur={props.onBlur}
        //                             />
        //                         );
        //                     }}
        //                 />
        //                 {errors && errors?.subScheme?.type === "required" && (
        //                     <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div>
        //         </LabelFieldPair>

        //         {/* Render the sub work table here */}
        //         <CardSectionHeader >{`${t(`WORKS_SUB_WORK_DETAILS`)}*`}</CardSectionHeader>
        //         <SubWorkTable register={register} t={t} errors={errors} />

        //         <CardSectionHeader style={{ "marginTop": "20px" }} >{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
        //         <LabelFieldPair>
        //             <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600 " }}>{t(`WORKS_UPLOAD_FILES`)}</CardLabel>
        //             <div className='field'>
        //                 <Controller
        //                     name="uploads"
        //                     control={control}
        //                     rules={{ required: false }}
        //                     render={({ onChange, ref, value = [] }) => {
        //                         function getFileStoreData(filesData) {
        //                             const numberOfFiles = filesData.length
        //                             let finalDocumentData = []
        //                             if (numberOfFiles > 0) {
        //                                 filesData.forEach(value => {
        //                                     finalDocumentData.push({
        //                                         fileName: value?.[0],
        //                                         fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
        //                                         documentType: value?.[1]?.file?.type
        //                                     })
        //                                 })
        //                             }
        //                             onChange(finalDocumentData)
        //                         }
        //                         return <MultiUploadWrapper
        //                             t={t}
        //                             module="works"
        //                             tenantId={"pb"}
        //                             getFormState={getFileStoreData}
        //                             showHintBelow={true}
        //                             setuploadedstate={value}
        //                             allowedFileTypesRegex={allowedFileTypes}
        //                             allowedMaxSizeInMB={5}
        //                             hintText={t("WORKS_DOC_UPLOAD_HINT")}
        //                             maxFilesAllowed={5}
        //                         />
        //                     }
        //                     }
        //                 />
        //             </div>
        //         </LabelFieldPair>

        //         <ActionBar>
        //             <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_CREATE_ESTIMATE")} />
        //         </ActionBar>
        //     </Card>
        // </form>
        <CreateEstimateForm onFormSubmit={onFormSubmit}/>
    )
}

export default CreateEstimate