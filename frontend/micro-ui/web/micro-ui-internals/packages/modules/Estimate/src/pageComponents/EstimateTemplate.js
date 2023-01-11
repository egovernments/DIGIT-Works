import { Card,LabelFieldPair,CardLabel,TextInput,SubmitBar } from '@egovernments/digit-ui-react-components'
import React,{Fragment,useState,useEffect} from 'react'

const EstimateTemplate = (props) => {
    const { t, register, errors } = props
    const formFieldName = "estimateTemplateDetailsv1"
  return (
      <>
          <LabelFieldPair>
              <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_WORK_TYPE`)}:`}</CardLabel>
              <CardLabel>Buildings</CardLabel>
          </LabelFieldPair>
          <LabelFieldPair>
              <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_SUB_TYPE_WORK`)}:`}</CardLabel>
              <CardLabel>School Building</CardLabel>
          </LabelFieldPair>
          <LabelFieldPair>
              <CardLabel style={{ fontSize: "16px", fontStyle: "bold", fontWeight: "600" }}>{`${t(`WORKS_TEMPLATE_CODE`)}:`}</CardLabel>
              <TextInput
                  className={"field"}
                  textInputStyle={{width:"31%"}}
                  inputRef={register({
                      required: false,
                      //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
                      pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
                  })}
                  name={`${formFieldName}.templateCode`}
              />
              <div style={{ alignSelf:'flex-start',marginLeft:"1rem" }} >
                  <SubmitBar
                      label={t("WORKS_SELECT")}
                      onSubmit={()=>console.log("Selected Template")}
                  />
              </div>
              {/* <CardLabel>School Building</CardLabel> */}
          </LabelFieldPair>

      </>
  )
}

export default EstimateTemplate