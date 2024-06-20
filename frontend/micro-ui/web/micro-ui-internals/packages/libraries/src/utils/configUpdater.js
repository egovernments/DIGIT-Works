import useQueryParams from "../hooks/useQueryParams";

export const configUpdater = (config) => {
  
  //here get url params
  const presets = useQueryParams();
  
  // config.label = config.alternateLabel || "Create Contract"
  
  //update the config and return
  config.sections.search.uiConfig.minReqFields = 0
  config.apiDetails.minParametersForSearchForm = 0

  const searchConfig = config.sections.search.uiConfig.fields
  const defaultValues = config.sections.search.uiConfig.defaultValues
  
  const targetFields = searchConfig?.filter(field => {
    //here check if field.populators.name is present in presets
    return Object.keys(presets).some(key=> key === field?.populators?.name)
  })

  //set default values for targetFields
  Object.keys(presets).forEach(preset => {
    if(preset !=="showFields" && preset!=="showFieldsDisabled"){
      defaultValues[preset] = presets[preset]
    }
  })

  

  //hide the target fields 
  targetFields?.forEach(field => field.hideInForm = true)
  
  //assuming there won't be any duplicates between showFields & showFieldsDisabled
  const showFields = presets?.showFields ? presets.showFields?.split(',') : []
  const showFieldsDisabled = presets.showFieldsDisabled ? presets.showFieldsDisabled?.split(',') : []
  //Now we need to decide for each field whether to follow default, show preset & allow to change, show prest & diabled
  //fieldsToHide will be subset of targetFields
  //this logic is required later::
  // const fieldsToHide = targetFields.filter(field => {
  //   const fieldName = field.populators.name
  //   let hideIt = true
  //   showFields?.forEach(field =>{
  //     if(field === fieldName) hideIt = false
  //   })
  //   showFieldsDisabled?.forEach(field =>{
  //     if(field === fieldName) hideIt = false
  //   })
  //   return hideIt
  // })
  // fieldsToHide?.forEach(field => {
  //   //if field is not present in showFields & showFieldsDisabled then follow default behaviour and hide it
  //   field.hideInForm = true
  // })


  const targetFieldsDisabled = searchConfig?.filter(field => {
    //here check if field.populators.name is present in presets
    return showFieldsDisabled.some(key=> key === field?.populators?.name)
  })

  targetFieldsDisabled?.forEach(field => {
    field.disable = true
  })

  //TODO:
  // move this logic to inbox search composer & hide all fields for which value is present(done -> calling this from inboxSearchComp)
  //change header config accordingly(done -> but issue in this because header is rendered before config updated)
  //shoud support multiple default values -> key(form)=value(api req)(done)
  //iterate over defaultValues object and set the value if present in url (create a common fn and call in all preprocess at the end)(done -> for estimates)

  //keep config for disable and allow to change values(2 more use cases)(default behavious is to keep em hidden)(two more use cases -> showFields(show and allow to change), showFieldsDisabled(prepopulate and show as disabled))
  //also if key is not there in default values don't set in api payload

  //ISSUES:
  //issue in default value set -> need to set object but setting just a string(for dropdowns)
  //issue in setting default value and allowing to change -> we are setting default value in preprocess at the end but if we allow to change that value we will not be able to set it (the changed value is overidden in preprocess at the end)
  return config
}