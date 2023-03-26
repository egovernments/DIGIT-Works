import { ConvertEpochToDate } from "../../../../libraries/src/services/atoms/Utils/Date";

export const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, findCurrentDate, ULBOptions, project, headerLocale}) => {
  console.log(project);
  if(!isModify) {
      //clear defaultValues from 'config' ( this case can come when user navigates from Create Screen to Modify Screen )
      let validDefaultValues = ["basicDetails_dateOfProposal", "noSubProject_ulb"];
      configs.defaultValues = Object.keys(configs?.defaultValues)
                            .filter(key=> validDefaultValues.includes(key))
                            .reduce((obj, key) => Object.assign(obj, {
                              [key] : configs.defaultValues[key]
                            }), {});
    }

    //update default Values
    //Only update the default values when date of proposal or ULB is not present in Session form Data.
    //If we update evertytime, we would overwrite the Session data, on every refresh
    if(!sessionFormData?.basicDetails_dateOfProposal || !sessionFormData.noSubProject_ulb) {
      if(isModify) {
        //this field is only for Modify flow
        configs.defaultValues.basicDetails_projectID = project?.projectNumber ? project?.projectNumber  : "";
      }else{
        project = {};
      }

      configs.defaultValues.basicDetails_dateOfProposal = project?.additionalDetails?.dateOfProposal ? ConvertEpochToDate(project?.additionalDetails?.dateOfProposal, "yyyy-mm-dd")  : findCurrentDate(); //TODO:
      configs.defaultValues.noSubProject_ulb = ULBOptions[0];
      configs.defaultValues.basicDetails_projectName = project?.name ? project?.name  : "";
      configs.defaultValues.basicDetails_projectDesc = project?.description ? project?.description  : "";
      configs.defaultValues.noSubProject_letterRefNoOrReqNo = project?.referenceID ? project?.referenceID  : "";
      configs.defaultValues.noSubProject_typeOfProject = project?.projectType ? { code : project?.projectType, name : `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(project?.projectType)}`, projectSubType : []}  : "";
      configs.defaultValues.noSubProject_targetDemography = project?.additionalDetails?.targetDemography ? { code : project?.additionalDetails?.targetDemography, name : `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(project?.additionalDetails?.targetDemography)}`}  : "";
      configs.defaultValues.noSubProject_estimatedCostInRs = project?.additionalDetails?.estimatedCostInRs ? project?.additionalDetails?.estimatedCostInRs  : "";
      configs.defaultValues.noSubProject_geoLocation = project?.address?.addressLine1 ? project?.address?.addressLine1  : ""; 
      configs.defaultValues.noSubProject_ward = project?.address?.boundary ?  { code : project?.address?.boundary, name : project?.address?.boundary, i18nKey: `${headerLocale}_ADMIN_${project?.address?.boundary}`}  : "";
      configs.defaultValues.noSubProject_locality = project?.additionalDetails?.locality ? { code : project?.additionalDetails?.locality?.code , name : project?.additionalDetails?.locality?.code, i18nKey: `${headerLocale}_ADMIN_${project?.additionalDetails?.locality?.code}`, label : project?.additionalDetails?.locality?.label}  : "";
      configs.defaultValues.noSubProject_fund = project?.additionalDetails?.fund ? { code : project?.additionalDetails?.fund, name : `COMMON_MASTERS_FUND_${Digit.Utils.locale.getTransformedLocale(project?.additionalDetails?.fund)}`}  : "";
      // configs.defaultValues.noSubProject_docs = project?.additionalDetails?.projectFiles ? project?.additionalDetails?.projectFiles : "";
    
      setSessionFormData({...configs?.defaultValues});
    }
}