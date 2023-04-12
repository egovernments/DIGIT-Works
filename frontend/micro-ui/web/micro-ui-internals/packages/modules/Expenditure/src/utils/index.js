//This handler creates a FormData for Docs defaultValues
//This will add 'id' for all the docs, as they are coming from response
//These Ids will be used when user will modify or delete a doc of certain criteria
export const handleModifyPBFiles = (uploadedDocs) => {
  //form data inout name mapping with file category Name
  let fileKeyMappings = [
    {key : "doc_vendor_invoice", value : "Vendor Invoice"},
    {key : "doc_measurement_book", value : "Measurement Book"},
    {key : "doc_material_utilisation_log", value : "Material Utilisation Log"},
    {key : "doc_others", value : "Others"},
  ]
  let documentObject = {};

  //keep only active docs -- at a time there would be only fileKeyMappings.length no of files
  uploadedDocs = uploadedDocs?.filter(uploadedDoc=>uploadedDoc?.status !== "INACTIVE");
  fileKeyMappings?.map((fileKeyMapping)=>{
    let currentDoc = uploadedDocs?.filter((doc)=>doc?.documentType === fileKeyMapping?.value)[0];

    if(currentDoc?.fileStore) {
      if(fileKeyMapping?.value === "Others") {
        documentObject["doc_others_name"] = currentDoc?.additionalDetails?.otherCategoryName;
      }
      documentObject[fileKeyMapping?.key] = [
        [currentDoc?.additionalDetails?.fileName, {file : { name : currentDoc?.additionalDetails?.fileName, id : currentDoc?.id}, fileStoreId : { fileStoreId : currentDoc?.fileStore}}]
      ] 
    }
  });
  return documentObject;
}

export const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, contract, t}) => {
  if(!isModify) {
      //clear defaultValues from 'config' ( this case can come when user navigates from Create Screen to Modify Screen )
      //these are the req default Values for Create WO
      let validDefaultValues = ["basicDetails_workOrderNumber", "basicDetails_projectID", "basicDetails_projectDesc", "basicDetails_location"];
      configs.defaultValues = Object.keys(configs?.defaultValues)
                            .filter(key=> validDefaultValues.includes(key))
                            .reduce((obj, key) => Object.assign(obj, {
                              [key] : configs.defaultValues[key]
                            }), {});
    }

    //update default Values
      if(!sessionFormData?.basicDetails_workOrderNumber || !sessionFormData.basicDetails_projectID || !sessionFormData.basicDetails_projectDesc || !sessionFormData.basicDetails_location) {
        // if(isModify) {
        //   //this field is only for Modify flow
      //   configs.defaultValues.basicDetails_purchaseBillNumber = bill?.billNumber ? bill?.Number  : "";
      // }else{
      //   contract = {};
      // }

      configs.defaultValues.basicDetails_workOrderNumber = contract?.contractNumber ? contract?.contractNumber  : "";
      configs.defaultValues.basicDetails_projectID = contract?.additionalDetails?.projectId ? contract?.additionalDetails?.projectId : "";
      configs.defaultValues.basicDetails_projectDesc = contract?.additionalDetails?.projectDesc ? contract?.additionalDetails?.projectDesc  : "";
      configs.defaultValues.basicDetails_location = contract?.additionalDetails?.ward ? 
      String(
          `${t(Digit.Utils.locale.getCityLocale(contract?.tenantId))}, ${t(Digit.Utils.locale.getMohallaLocale(contract?.additionalDetails?.ward, contract?.tenantId))}`
      )  : ""; 

      
      setSessionFormData({...sessionFormData, ...configs?.defaultValues});
    }
}