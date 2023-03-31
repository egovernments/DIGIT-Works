import React,{Fragment} from 'react'
import {
    Card,
    Header,
    CardSectionHeader,
    LabelFieldPair,
    CardLabel,
    CardText,
    CardSectionSubText,
    TextInput,
    Dropdown,
    UploadFile,
    MultiUploadWrapper,
    ActionBar,
    SubmitBar,
    CardLabelError,
    Loader,
} from "@egovernments/digit-ui-react-components";

const LabourAnalysis = ({watch,formState,...props}) => {
    const { t, register, errors, setValue, getValues, formData } = props
    
    const formFieldName = "analysis" // this will be the key under which the data for this table will be present on onFormSubmit
    const errorStyle = { marginBottom: "0px",width:"100%" };
    const inputStyle = { marginTop: "20px", marginBottom: "4px" };
    
  return (
      <Card style={{ backgroundColor: "#FAFAFA",marginTop:"2rem" }}>
          <CardSectionHeader style={{ marginTop: "14px",marginBottom:"1rem" }}>{t(`ESTIMATE_LABOUR_ANALYSIS`)}</CardSectionHeader>
          <LabelFieldPair>
              <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600",marginBottom:"-10px" }}>{`${t(`ESTIMATE_LABOUR_COST`)}*`}</CardLabel>
              <div className='field'>
                  <TextInput
                      name={`${formFieldName}.labour`}
                      inputRef={register({
                          required: true,
                          pattern: /^\d*\.?\d*$/
                      })}
                      style={inputStyle}
                  />
                  {errors && errors?.[formFieldName]?.labour?.type === "required" && (
                      <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  {errors && errors?.[formFieldName]?.labour?.type === "pattern" && (
                      <CardLabelError style={errorStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
              </div>
          </LabelFieldPair>
          <LabelFieldPair>
              <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600", marginBottom: "-10px" }}>{`${t(`ESTIMATE_MATERIAL_COST`)}*`}</CardLabel>
              <div className='field'>
                  <TextInput
                      name={`${formFieldName}.material`}
                      inputRef={register({
                          required: true,
                          pattern: /^\d*\.?\d*$/
                      })}
                      style={inputStyle}
                  />
                  {errors && errors?.[formFieldName]?.material?.type === "required" && (
                      <CardLabelError style={errorStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  {errors && errors?.[formFieldName]?.material?.type === "pattern" && (
                      <CardLabelError style={errorStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
              </div>
          </LabelFieldPair>
    </Card>
  )
}

export default LabourAnalysis