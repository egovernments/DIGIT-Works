import React, { Fragment, useState } from 'react'
import { Controller, useForm} from 'react-hook-form'
import { Card, Header, CardSectionHeader, LabelFieldPair, CardLabel, TextInput, Dropdown, ActionBar, SubmitBar, CardLabelError, Loader } from '@egovernments/digit-ui-react-components';
import { useTranslation } from 'react-i18next';
import WORKSContractorTable from './ContractorDetailTable';

const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;

const CreateContractorForm = ({onFormSubmit}) => {
    
    const { t } = useTranslation()
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
        defaultValues: {},
        mode: "onSubmit"
    });

    const initialState = [
        {
            key: 1,
            isShow: true
        }
    ]
    const [rows, setRows] = useState(initialState)
    const tenant = Digit.ULBService.getStateId()
    const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
        "finance",
        [
            {
                "name": "Bank"
            }
        ]
    );
    if(data?.finance){
        var { Bank } = data?.finance
    }
    const exemptedFromULB=[
        {
        name:"Income tax", code:'Income tax', active:true
        },
        {
        name:"Earnest money deposit (EMD)", code:'Earnest money deposit (EMD)', active:true
        },
        {
        name:"VAT", code:'VAT', active:true
        },
    ]
    
    const handleCreateClick = async () => {
        const subWorkFieldsToValidate = []
        rows.map(row => row.isShow && subWorkFieldsToValidate.push(...[`contractorDetails.${row.key}.Department`, `contractorDetails.${row.key}.registrationNumber`,`contractorDetails.${row.key}.category`,`contractorDetails.${row.key}.contractorClass`,`contractorDetails.${row.key}.status`,`contractorDetails.${row.key}.fromDate`,`contractorDetails.${row.key}.toDate`]))
        const fieldsToValidate = ['Name','CorrespondanceAddress','permenantAddress','contactPerson','email','narration','mobileNumber','panNo','tinNo','gstNo','Bank','IFSCCode','bankAccountNumber','PWDApprovalCode','exemptedFrom',...subWorkFieldsToValidate]
        
        const result = await trigger(fieldsToValidate)
        if (result) {
            let check=getValues();
            onFormSubmit(check);
        }
    }

    if(isLoading) {
        return <Loader />
    }

    const checkKeyDown = (e) => {
        if (e.code === 'Enter') e.preventDefault();
    };

    return (
      isFetched && <form onSubmit={handleSubmit(onFormSubmit)} onKeyDown={(e) => checkKeyDown(e)}>
          <Header styles={{ "marginLeft": "14px" }}>{t("WORKS_CREATE_CONTRACTOR")}</Header>
          <Card >
              <CardSectionHeader style={{ "marginTop": "14px" }} >{t(`WORKS_CONTRACTOR_DETAILS`)}</CardSectionHeader>
              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }} >{`${t(`ContractorCode`)}:*`}</CardLabel>
                  <TextInput className={"field"} name="proposalDate" inputRef={register()} value="CNT/2022-23/0001" disabled />
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_NAME`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="Name" inputRef={register({
                          required:true
                      })}
                    />

                    {errors && errors?.Name?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CORRESPONDANCE_ADDRESS`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="CorrespondanceAddress" inputRef={register({
                          pattern: /^$|^[ A-Za-z0-9/._$@#]+$/
                      })}
                    />

                    {errors && errors?.CorrespondanceAddress?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_PERMENANT_ADDRESS`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="permenantAddress" inputRef={register({
                          pattern: /^$|^[A-Za-z0-9/._$@#]+$/
                      })}
                    />

                    {errors && errors?.permenantAddress?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_CONTACT_PERSON`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="contactPerson" inputRef={register({
                          pattern: /^$|^[a-zA-Z\s]+$/
                      })}
                    />

                    {errors && errors?.contactPerson?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EMAIL`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="email" inputRef={register({
                          pattern: /^$|^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
                      })}
                    />

                    {errors && errors?.email?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_NARRATION`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="narration" inputRef={register({
                          pattern: /^$|^[a-zA-Z0-9\s]+$/
                      })}
                    />

                    {errors && errors?.narration?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`CORE_COMMON_MOBILE_NUMBER`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="mobileNumber" inputRef={register({
                          pattern: /^$|^[0-9]{10}/
                      })}
                    />

                    {errors && errors?.mobileNumber?.type === "pattern" && (
                        <CardLabelError>{t(`ERR_DEFAULT_INPUT_FIELD_MSG`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_PAN_NUMBER`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="panNo" inputRef={register({
                          pattern: /^$|^[a-zA-Z0-9\s]+$/
                      })}
                    />

                    {errors && errors?.panNo?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_TIN_NUMBER`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="tinNo" inputRef={register({
                          pattern: /^$|^[a-zA-Z0-9\s]+$/
                      })}
                    />

                    {errors && errors?.tinNo?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_GSTIN_NUMBER`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="gstNo" inputRef={register({
                          pattern: /^$|^[a-zA-Z0-9\s]+$/
                      })}
                    />

                    {errors && errors?.gstNo?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_GSTIN_NUMBER`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="gstNo" inputRef={register({
                          pattern: /^$|^[a-zA-Z0-9\s]+$/
                      })}
                    />

                    {errors && errors?.gstNo?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>                
              
              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BANK`)}:`}</CardLabel>
                  <div className='field'>
                      <Controller
                        name="Bank"
                        control={control}
                        rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    option={Bank}
                                    selected={props?.value}
                                    optionKey={"name"}
                                    t={t}
                                    select={props?.onChange}
                                    onBlur={props.onBlur}
                                />
                            );
                        }}
                      />
                      {errors && errors?.Bank?.type === "required" && (
                          <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_IFSC_CODE`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="IFSCCode" inputRef={register({
                          pattern: /^$|^[a-zA-Z0-9\s]+$/
                      })}
                    />

                    {errors && errors?.IFSCCode?.type === "pattern" && (
                        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>                

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_BANK_ACCOUNT_NUMBER`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="bankAccountNumber" inputRef={register({
                          pattern: /^$|^[0-9]{9,18}/
                      })}
                    />
                    {errors && errors?.bankAccountNumber?.type === "pattern" && (
                        <CardLabelError>{t(`ERR_DEFAULT_INPUT_FIELD_MSG`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>                

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_PWD_APPROVAL_CODE`)}:`}</CardLabel>
                  <div className='field'>
                  <TextInput name="PWDApprovalCode" inputRef={register({
                          pattern: /^$|^[ A-Za-z0-9/._$@#]*$/
                      })}
                    />
                    {errors && errors?.PWDApprovalCode?.type === "pattern" && (
                        <CardLabelError>{t(`ERR_DEFAULT_INPUT_FIELD_MSG`)}</CardLabelError>)}
                  </div>
              </LabelFieldPair>   

              <LabelFieldPair>
                  <CardLabel style={{ "fontSize": "16px", "fontStyle": "bold", "fontWeight": "600" }}>{`${t(`WORKS_EXEMPTED_FROM`)}:`}</CardLabel>
                  <div className='field'>
                      <Controller
                        name="exemptedFrom"
                        control={control}
                        rules={{ required: true }}
                        render={(props) => {
                            return (
                                <Dropdown
                                    option={exemptedFromULB}
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
              
              <CardSectionHeader >{t(`WORKS_CONTRACTOR_DETAILS`)}</CardSectionHeader>
              <WORKSContractorTable register={register} t={t} errors={errors} rows={rows} setRows={setRows} control={control}Controller={Controller}/>

              <ActionBar>
                  <SubmitBar onSubmit={handleCreateClick} label={t("WORKS_CREATE_CONTRACTOR")} />
              </ActionBar>
          </Card>
      </form>
  )
}

export default CreateContractorForm