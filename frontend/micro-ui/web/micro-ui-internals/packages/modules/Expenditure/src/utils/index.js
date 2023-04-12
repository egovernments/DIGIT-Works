import { bind } from "lodash";

//export const updateDefaultValues = ({createPurchaseBillConfigMUKTA, isModify, sessionFormData, setSessionFormData, contract, estimate, project, handleWorkOrderAmount, overHeadMasterData, createNameOfVendorObject, vendorOptions, createOfficerInChargeObject, assigneeOptions, roleOfCBO}) => {
export const updateDefaultValues = ({createPurchaseBillConfig, isModify, sessionFormData, setSessionFormData, bill, contract, createNameOfVendorObject, vendorOptions, t}) => {
    // if(!isModify) {
    //     //clear defaultValues from 'config' ( this case can come when user navigates from Create Screen to Modify Screen )
    //     //these are the req default Values for Create WO
    //     let validDefaultValues = ["basicDetails_workOrderNumber", "basicDetails_projectID", "basicDetails_projectDesc", "basicDetails_location"];
    //     createPurchaseBillConfig.defaultValues = Object.keys(createPurchaseBillConfig?.defaultValues)
    //                           .filter(key=> validDefaultValues.includes(key))
    //                           .reduce((obj, key) => Object.assign(obj, {
    //                             [key] : createPurchaseBillConfig.defaultValues[key]
    //                           }), {});
    //   }
  
      //update default Values
      if(!sessionFormData?.basicDetails_workOrderNumber || !sessionFormData.basicDetails_projectID || !sessionFormData.basicDetails_projectDesc || !sessionFormData.basicDetails_location) {
        // if(isModify) {
        //   //this field is only for Modify flow
        //   createPurchaseBillConfig.defaultValues.basicDetails_billNumber = bill?.billNumber ? bill?.billNumber  : "";
        // }else{
        //   bill = {};
        // }

        createPurchaseBillConfig?.defaultValues.basicDetails_workOrderNumber = contract?.contractNumber ? contract?.contractNumber  : "";
        createPurchaseBillConfig?.defaultValues.basicDetails_projectID = contract?.additionalDetails?.projectId ? contract?.additionalDetails?.projectId : "";
        createPurchaseBillConfig?.defaultValues.basicDetails_projectDesc = contract?.additionalDetails?.projectDesc ? contract?.additionalDetails?.projectDesc  : "";
        createPurchaseBillConfig?.defaultValues.basicDetails_location = contract?.additionalDetails?.ward ? 
        String(
            `${t(Digit.Utils.locale.getCityLocale(contract?.tenantId))}, ${t(Digit.Utils.locale.getMohallaLocale(contract?.additionalDetails?.ward, contract?.tenantId))}`
        )  : ""; 
        
        console.log("valuess :", createPurchaseBillConfig?.defaultValues);
        setSessionFormData({...sessionFormData, ...createPurchaseBillConfigMUKTA?.defaultValues});
      }
  }