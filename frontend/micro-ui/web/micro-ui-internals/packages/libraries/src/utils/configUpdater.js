import useQueryParams from "../hooks/useQueryParams";

export const configUpdater = (config) => {
  
  //here get url params
  const {disableField,...preset} = useQueryParams();
  debugger
  //update the config and return
  const searchConfig = config.sections.search.uiConfig.fields
  const apiDetails = config.apiDetails
  const targetField = ""
  return config
}