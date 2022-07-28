import React, { useReducer } from 'react'
import { Controller, useForm } from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, CardText, CardSectionSubText, TextInput, Dropdown, UploadFile, MultiUploadWrapper,ActionBar,SubmitBar } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import SubWorkTable from '../../components/CreateEstimate/SubWorkTable/SubWorkTable';
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(jpg|jpeg|png|image|pdf|msword|openxmlformats-officedocument)$/i;


const CreateEstimate = (props) => {
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
        ...methods
    } = useForm();

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


    return (
        <form onSubmit={handleSubmit(onFormSubmit)}>
            <Header>{t("WORKS_CREATE_ESTIMATE")}</Header>
            <Card >
                <CardSectionHeader >{t(`WORKS_ESTIMATE_DETAILS`)}</CardSectionHeader>
                {/* TEXT INPUT ROW */}
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_DATE_PROPOSAL`)}</CardLabel>
                    <TextInput className={"field"} name="reasonDocumentNumber" inputRef={register()} />
                </LabelFieldPair>
                {/* DROPDOWN ROW */}
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_EXECUTING_DEPT`)}</CardLabel>
                    <Controller
                        name="amendmentReason"
                        control={control}
                        //rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_LOR`)}</CardLabel>
                    <TextInput className={"field"} name="lor" inputRef={register()} />
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_LOCATION_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_WARD`)}</CardLabel>
                    <Controller
                        name="ward"
                        control={control}
                        //rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_LOCATION`)}</CardLabel>
                    <Controller
                        name="location"
                        control={control}
                        //rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_WORK_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_BENEFICIERY`)}</CardLabel>
                    <Controller
                        name="beneficiery"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_WORK_NATURE`)}</CardLabel>
                    <Controller
                        name="natureofwork"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_WORK_TYPE`)}</CardLabel>
                    <Controller
                        name="workType"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_SUB_TYPE_WORK`)}</CardLabel>
                    <Controller
                        name="subtypework"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_MODE_OF_INS`)}</CardLabel>
                    <Controller
                        name="modeofens"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>

                <CardSectionHeader >{t(`WORKS_FINANCIAL_DETAILS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_FUND`)}</CardLabel>
                    <Controller
                        name="fund"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_FUNCTION`)}</CardLabel>
                    <Controller
                        name="function"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_BUDGET_HEAD`)}</CardLabel>
                    <Controller
                        name="budgetHead"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_SCHEME`)}</CardLabel>
                    <Controller
                        name="scheme"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_SUB_SCHEME`)}</CardLabel>
                    <Controller
                        name="subScheme"
                        control={control}
                        render={(props) => {
                            return (
                                <Dropdown
                                    className={`field`}
                                    option={dummyData}
                                    //selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                />
                            );
                        }}
                    />
                </LabelFieldPair>

                {/* Render the sub work table here */}
                <SubWorkTable register={register} />

                <CardSectionHeader >{t(`WORKS_RELEVANT_DOCS`)}</CardSectionHeader>
                <LabelFieldPair>
                    <CardLabel >{t(`WORKS_UPLOAD_FILES`)}</CardLabel>
                    {/* <Controller
                      name={`uploads`}
                      control={control}
                      //rules={e?.required ? { required: true } : {}}
                      render={(props) => (
                        <div className='field'>
                          <UploadFile
                              multiple={true}
                              id={`doc`}
                              onUpload={(d) => functionToHandleFileUpload(d, 'documentType', props)}
                              onDelete={() => dispatch({ type: "remove", payload: { id: 'documentType' } })}
                              message={functionToDisplayTheMessage}
                              accept="image/*, .pdf, .png, .jpeg, .doc"
                          />
                          </div>
                      )}
                  /> */}
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
                                    hintText={t("DOCUMENTS_ATTACH_RESTRICTIONS_SIZE")}
                                />
                            }
                            }
                        />
                    </div>
                </LabelFieldPair>

                <ActionBar>
                    <SubmitBar submit={true} label={t("WORKS_CREATE_ESTIMATE")} />
                </ActionBar>
            </Card>
        </form>

    )
}

export default CreateEstimate