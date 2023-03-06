import React, {useEffect, useRef} from 'react'
import { useTranslation } from 'react-i18next'
import CardSectionHeader from '../atoms/CardSectionHeader'
import LabelFieldPair from '../atoms/LabelFieldPair'
import CardLabel from '../atoms/CardLabel'
import CardLabelError from '../atoms/CardLabelError'
import CitizenInfoLabel from '../atoms/CitizenInfoLabel'
import Header from '../atoms/Header'
import { Loader } from '../atoms/Loader'
import MultiUploadWrapper from '../molecules/MultiUploadWrapper'
import TextInput from '../atoms/TextInput'

const UploadFileComposer = ({module, customClass, config, register, setuploadedstate, onChange, formData, errors, localePrefix}) => {
  const { t } = useTranslation()
  let finalDocumentData = []
  
  //fetch mdms config based on module name
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      tenant,
      "works",
      [
          {
              "name": "DocumentConfig",
              "filter": `[?(@.module=='${module}')]`
          }
      ]
  );

  const docConfig = data?.works?.DocumentConfig?.[0]
  
  useEffect(() => {
    finalDocumentData = [];
    docConfig?.documents?.filter(item => item.active)?.forEach((item) => {
      finalDocumentData.push({
        code: item?.code,
        isMandatory: item?.isMandatory,
        module,
        uploadedFiles: []
      })
    })
  }, [docConfig])

  const getRegex = (allowedFormats) => {
    if(allowedFormats?.length) {
      const obj = { "expression" : `/(.*?)(${allowedFormats?.join('|')})$/`}
      const stringified = JSON.stringify(obj);
      return new RegExp(JSON.parse(stringified).expression.slice(1, -1));
    } else if(docConfig?.allowedFileTypes?.length) {
      const obj = { "expression" : `/(.*?)(${docConfig?.allowedFileTypes?.join('|')})$/`}
      const stringified = JSON.stringify(obj);
      return new RegExp(JSON.parse(stringified).expression.slice(1, -1));
    } 
    return /(.*?)(pdf|docx|jpeg|png|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/
  }

  function getFileStoreData(filesData, item, formData) {
    const numberOfFiles = filesData.length;
    
    //get existing data from form
    if(formData[config?.populators?.name]?.length) {
      finalDocumentData = formData[config?.populators?.name]
    } 

    finalDocumentData.map(doc => {
      if(doc?.code === item?.code) {
        doc.uploadedFiles = []
      } 
      return doc
    })

    if (numberOfFiles > 0) {
      const files = finalDocumentData?.filter(doc => doc?.code === item?.code)?.[0]?.uploadedFiles
      filesData.forEach((value) => {
        files?.push({
          fileName: value?.[0],
          fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
          documentType: value?.[1]?.file?.type,
          otherFileName: formData[`${item?.code}_name`] || ""
        })
      });
    }
    onChange(finalDocumentData);
  }

  if(isLoading) return <Loader />
  return (
    <React.Fragment>
      <Header styles={{fontSize: "24px"}}>{t('WORKS_RELEVANT_DOCUMENTS')}</Header>
      <CitizenInfoLabel info={t("ES_COMMON_INFO")} text={t(docConfig?.bannerLabel)} className="doc-banner"></CitizenInfoLabel>
      {
        docConfig?.documents?.map((item, index) => {
          if(!item?.active) return
          return ( 
            <LabelFieldPair key={index}>
              { item.code && (
                <CardLabel>
                  { t(`${localePrefix}_${item?.code}`)} { item?.isMandatory ? " * " : null }
                </CardLabel>) 
              }
            
              <div className="field">
                {
                  item?.showTextInput ? 
                    <TextInput 
                      style={{ "marginBottom": "16px" }} 
                      name={`${item?.code}_name`} 
                      placeholder={t('ES_COMMON_ENTER_NAME')}
                      inputRef={register({minLength: 2})}/> : 
                    null  
                }
                <div  style={{marginBottom: '24px'}}>
                  <MultiUploadWrapper
                    t={t}
                    module="works"
                    getFormState={(filesData) => getFileStoreData(filesData, item, formData, docConfig)}
                    setuploadedstate={setuploadedstate}
                    showHintBelow={item?.hintText ? true : false}
                    hintText={item?.hintText}
                    allowedFileTypesRegex={getRegex(item?.allowedFileTypes)}
                    allowedMaxSizeInMB={item?.maxSizeInMB || docConfig?.maxSizeInMB || 5}
                    maxFilesAllowed={item?.maxFilesAllowed || 1}
                    customClass={customClass}
                    customErrorMsg={item?.customErrorMsg}
                  />   
                </div>
              </div>    
              { item?.populators?.name && errors && errors[item?.populators?.name] && Object.keys(errors[item?.populators?.name]).length ? (
                <CardLabelError style={{ fontSize: "12px", marginTop: "-20px" }}>
                  {t(item?.populators?.error)}
                </CardLabelError> ) : null
              }
            </LabelFieldPair>
          )
        })
      }   
    </React.Fragment>
  )
}

export default UploadFileComposer