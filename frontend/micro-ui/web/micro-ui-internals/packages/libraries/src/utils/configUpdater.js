import useQueryParams from "../hooks/useQueryParams";

export const configUpdater = (config) => {
  
  //here get url params
  const presets = useQueryParams();
  
  config.label = config.alternateLabel || "Create Contract"
  //update the config and return
  config.sections.search.uiConfig.minReqFields = 0
  config.apiDetails.minParametersForSearchForm = 0

  const searchConfig = config.sections.search.uiConfig.fields
  const defaultValues = config.sections.search.uiConfig.defaultValues
  
  //fetching the target fields
  // const targetField = searchConfig?.filter(field => field?.populators?.name === disableField)?.[0]
  const targetFields = searchConfig?.filter(field => {
    //here check if field.populators.name is present in presets
    return Object.keys(presets).some(key=> key === field?.populators?.name)
  })
  
  //hide the target fields 
  targetFields?.forEach(field => field.hideInForm = true)
  //set default values for targetFields
  Object.keys(presets).forEach(preset => {
    defaultValues[preset] = presets[preset]
  })
  // targetField.hideInForm = true
  // defaultValues[disableField] = defaultValue

  //TODO:
  // move this logic to inbox search composer & hide all fields for which value is present(done -> calling this from inboxSearchComp)
  //change header config accordingly(done -> but issue in this because header is rendered before config updated)
  //shoud support multiple default values -> key(form)=value(api req)(done)
  //iterate over defaultValues object and set the value if present in url (create a common fn and call in all preprocess at the end)(done -> for estimates)
  return config
}