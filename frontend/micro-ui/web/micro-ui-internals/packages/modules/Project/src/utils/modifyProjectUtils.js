import { ConvertEpochToDate } from "../../../../libraries/src/services/atoms/Utils/Date";

export const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, findCurrentDate, ULBOptions, project, headerLocale}) => {
    let projectDetails = project?.response?.[0]; 

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
        configs.defaultValues.basicDetails_projectID = projectDetails?.projectNumber ? projectDetails?.projectNumber  : "";
      }else{
        projectDetails = {};
      }

      configs.defaultValues.basicDetails_dateOfProposal = projectDetails?.additionalDetails?.dateOfProposal ? ConvertEpochToDate(projectDetails?.additionalDetails?.dateOfProposal, "yyyy-mm-dd")  : findCurrentDate(); //TODO:
      configs.defaultValues.noSubProject_ulb = ULBOptions[0];
      configs.defaultValues.basicDetails_projectName = projectDetails?.name ? projectDetails?.name  : "";
      configs.defaultValues.basicDetails_projectDesc = projectDetails?.description ? projectDetails?.description  : "";
      configs.defaultValues.noSubProject_letterRefNoOrReqNo = projectDetails?.referenceID ? projectDetails?.referenceID  : "";
      configs.defaultValues.noSubProject_typeOfProject = projectDetails?.projectType ? { code : projectDetails?.projectType, name : `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(projectDetails?.projectType)}`, projectSubType : []}  : "";
      configs.defaultValues.noSubProject_targetDemography = projectDetails?.additionalDetails?.targetDemography ? { code : projectDetails?.additionalDetails?.targetDemography, name : `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(projectDetails?.additionalDetails?.targetDemography)}`}  : "";
      configs.defaultValues.noSubProject_estimatedCostInRs = projectDetails?.additionalDetails?.estimatedCostInRs ? projectDetails?.additionalDetails?.estimatedCostInRs  : "";
      configs.defaultValues.noSubProject_geoLocation = projectDetails?.address?.addressLine1 ? projectDetails?.address?.addressLine1  : "";
      configs.defaultValues.noSubProject_ward = projectDetails?.additionalDetails?.ward ?  { code : projectDetails?.additionalDetails?.ward, name : projectDetails?.additionalDetails?.ward, i18nKey: `${headerLocale}_ADMIN_${projectDetails?.additionalDetails?.ward}`}  : "";
      configs.defaultValues.noSubProject_locality = projectDetails?.address?.boundary ? { code : projectDetails?.address?.boundary, name : projectDetails?.address?.boundary, i18nKey: `${headerLocale}_ADMIN_${projectDetails?.address?.boundary}`}  : "";
      configs.defaultValues.noSubProject_fund = projectDetails?.additionalDetails?.fund ? { code : projectDetails?.additionalDetails?.fund, name : `COMMON_MASTERS_FUND_${Digit.Utils.locale.getTransformedLocale(projectDetails?.additionalDetails?.fund)}`}  : "";
      configs.defaultValues.noSubProject_docs = projectDetails?.additionalDetails?.projectFiles ? projectDetails?.additionalDetails?.projectFiles : "";
    
      setSessionFormData({...configs?.defaultValues});
    }
}