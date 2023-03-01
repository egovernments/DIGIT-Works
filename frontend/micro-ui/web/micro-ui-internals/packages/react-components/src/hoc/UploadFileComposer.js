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


const test = {
  "tenantId": "pg",
  "moduleName": "works",
  "DocumentConfig": [ 
    {
      "module": "Project",
      "allowedFileTypes": ["pdf","docx"],
      "maxSizeInMB": 5,
      "documents": [
        {
          "code": "PROJECT_PROPOSAL",
          "active": true,
          "isMandatory": true,
          "isMultiple" : true,
          "allowedFileTypes": ["png","jpeg","pdf"],
          "maxSizeInMB": 2,
          "maxFilesAllowed": 2
        },
        {
          "code": "FINALIZED_WORKLIST",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "FEASIBILITY_ANALYSIS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        }
      ]
    },
    {
      "module": "Estimate",
      "allowedFileTypes": ["pdf","docx"],
      "maxSizeInMB": 3,
      "documents": [
        {
          "code": "DETAILED_ESTIMATE",
          "active": true,
          "isMandatory": true,
          "isMultiple" : true,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 2
        },
        {
          "code": "LABOUR_ANALYSIS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "MATERIAL_ANALYSIS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "DESIGN_DOCUMENT",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": ["pdf","docx"],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "OTHERS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : true,
          "allowedFileTypes": ["pdf","docx"],
          "maxSizeInMB": 2,
          "maxFilesAllowed": 3
        }
      ]
    }, 
    {
      "module": "Work Order",
      "allowedFileTypes": ["pdf","docx"],
      "maxSizeInMB": 5,
      "documents": [
        {
          "code": "BOQ",
          "active": true,
          "isMandatory": true,
          "isMultiple" : true,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 3
        },
        {
          "code": "LABOUR_ANALYSIS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null
        },
        {
          "code": "MATERIAL_ANALYSIS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "TERMS_AND_CONDITIONS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "OTHERS",
          "active": true,
          "isMandatory": false,
          "isMultiple" : true,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        }
      ]
    },
    {
      "module": "Bill",
      "allowedFileTypes": ["pdf","docx"],
      "maxSizeInMB": 2,
      "documents": [
        {
          "code": "MEASUREMENT_BOOK",
          "active": true,
          "isMandatory": true,
          "isMultiple" : true,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 2
        },
        {
          "code": "MATERIAL_UTILIZATION_LOG",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        },
        {
          "code": "LABOUR_UTILIZATION_LOG",
          "active": true,
          "isMandatory": false,
          "isMultiple" : false,
          "allowedFileTypes": [],
          "maxSizeInMB": null,
          "maxFilesAllowed": 0
        }
      ]
    }
  ]
}

  /*

  get config as per module
  in UI show below things:
  - blue info box with message - pass as object with message to show or not?
  - one obj - one upload component
  - create/edit mode
  - upload/delete
  - validations on number of files and size
  - UI
  */
const UploadFileComposer = ({module, customClass, config, register, setuploadedstate, onChange, formData}) => {
  const { t } = useTranslation()
  //fetch mdms config based on module name
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      tenant,
      "works",
      [
          {
              "name": "DocumentConfig"
          }
      ]
  );
  const docConfig = test?.DocumentConfig[0] //data?.works?.DocumentConfig?.filter(item => item.module === module)?.[0]
  
  //check if this banner text is dynamic or can be fixed
  const getBannerText = () => {
    return `Only ${docConfig?.allowedFileTypes?.join(', ')} files can be uploaded. File size should not exceed ${docConfig?.maxSizeInMB} MB.`
  }

  //Check by sending whole item obj and do stringify
  const getRegex = (allowedFormats) => {
    if(allowedFormats?.length) {
      const exp = `/(.*?)(${allowedFormats?.join('|')})$/`
      const obj = { "expression" : exp}
      const stringified = JSON.stringify(obj);
      return new RegExp(JSON.parse(stringified).expression.slice(1, -1));
    } else if(docConfig?.allowedFileTypes?.length) {
      const obj = { "expression" : `/(.*?)(${docConfig?.allowedFormats?.join('|')})$/`}
      const stringified = JSON.stringify(obj);
      return new RegExp(JSON.parse(stringified).expression.slice(1, -1));
    } 
    return /(.*?)(pdf|docx|jpeg|png|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/
  }

  function getFileStoreData(filesData, item, formData) {
    console.log('&&&', formData);
    const numberOfFiles = filesData.length;
    let finalDocumentData = []
    if(formData[config?.populators?.name]?.length) {
      finalDocumentData = formData[config?.populators?.name]
    } 
    if (numberOfFiles > 0) {
      filesData.forEach((value) => {
        const index = finalDocumentData.findIndex(item => item?.fileStoreId === value?.[1]?.fileStoreId?.fileStoreId)
        if(index !== -1) finalDocumentData.splice(index, 1)
        finalDocumentData.push({
          fileName: value?.[0],
          fileStoreId: value?.[1]?.fileStoreId?.fileStoreId,
          documentType: value?.[1]?.file?.type,
          otherFileName: formData['other_name'],
          code: item?.code,
          module
        });
      });
    }
    onChange(numberOfFiles>0?finalDocumentData:[]);
  }

  if(isLoading) return <Loader />
  return (
    <React.Fragment>
      <Header styles={{fontSize: "24px"}}>{"Create Project"}</Header>
      <CitizenInfoLabel info={t("ES_COMMON_INFO")} text={getBannerText()} className="doc-banner"></CitizenInfoLabel>
      {
        docConfig?.documents?.map((item, index) => {
          if(!item?.active) return
          return ( 
            <LabelFieldPair key={index}>
              { item.code && (
                <CardLabel>
                  {t(item.code)}{ item?.isMandatory ? " * " : null }
                </CardLabel>) 
              }
            
              <div className="field">
                {
                  item.code === 'OTHERS' ? 
                    <TextInput 
                      style={{ "marginBottom": "16px" }} 
                      name={'other_name'} 
                      placeholder={'Enter the file name'}
                      inputRef={register({required : true, minLength: 2})}/> : 
                    null  
                }
                <div  style={{marginBottom: '24px'}}>
                  <MultiUploadWrapper
                    t={t}
                    module="works"
                    getFormState={(filesData) => getFileStoreData(filesData, item, formData)}
                    setuploadedstate={setuploadedstate}
                    showHintBelow={item?.hintText ? true : false}
                    hintText={item?.hintText}
                    allowedFileTypesRegex={getRegex(item?.allowedFileTypes)}
                    allowedMaxSizeInMB={item?.maxSizeInMB || docConfig?.maxSizeInMB || 5}
                    maxFilesAllowed={item?.isMultiple ? (item?.maxFilesAllowed || 2) : 1}
                    customClass={item?.customClass}
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


//update allowedFormats to allowedFileTypes