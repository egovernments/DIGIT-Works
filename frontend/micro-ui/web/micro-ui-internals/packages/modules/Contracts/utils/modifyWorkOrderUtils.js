//This handler creates a FormData for Docs defaultValues
//This will add 'id' for all the docs, as they are coming from response
//These Ids will be used when user will modify or delete a doc of certain criteria
export const handleModifyWOFiles = (uploadedDocs) => {
  //form data inout name mapping with file category Name
  let fileKeyMappings = [
    {key : "doc_boq", value : "BOQ"},
    {key : "doc_terms_and_conditions", value : "Terms And Conditions"},
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

export const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, contract, estimate, project, handleWorkOrderAmount, overHeadMasterData, createNameOfCBOObject, organisationOptions, createOfficerInChargeObject, assigneeOptions}) => {
  if(!isModify) {
      //clear defaultValues from 'config' ( this case can come when user navigates from Create Screen to Modify Screen )
      //these are the req default Values for Create WO
      let validDefaultValues = ["basicDetails_projectID", "workOrderAmountRs", "basicDetails_dateOfProposal", "basicDetails_projectName", "basicDetails_projectDesc"];
      configs.defaultValues = Object.keys(configs?.defaultValues)
                            .filter(key=> validDefaultValues.includes(key))
                            .reduce((obj, key) => Object.assign(obj, {
                              [key] : configs.defaultValues[key]
                            }), {});
    }

    //update default Values
    if(!sessionFormData?.basicDetails_projectID || !sessionFormData.workOrderAmountRs || !sessionFormData.basicDetails_dateOfProposal || !sessionFormData.basicDetails_projectName || !sessionFormData.basicDetails_projectDesc ) {
      if(isModify) {
        //this field is only for Modify flow
        configs.defaultValues.basicDetails_workOrdernumber = contract?.contractNumber ? contract?.contractNumber  : "";
      }else{
        contract = {};
      }
      
      let organisations = createNameOfCBOObject(organisationOptions);
      let assignees = createOfficerInChargeObject(assigneeOptions);

      configs.defaultValues.basicDetails_projectID = project?.projectNumber ? project?.projectNumber  : "",
      configs.defaultValues.basicDetails_dateOfProposal = project?.additionalDetails?.dateOfProposal ? Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal) : "",
      configs.defaultValues.basicDetails_projectName = project?.name ? project?.name  : "";
      configs.defaultValues.basicDetails_projectDesc = project?.description ? project?.description  : "";
      configs.defaultValues.workOrderAmountRs = isModify ? contract?.totalContractedAmount  : handleWorkOrderAmount({estimate, overHeadMasterData});
      configs.defaultValues.nameOfCBO =  isModify ? (organisations?.filter(org=>org?.code === contract?.additionalDetails?.cboCode))?.[0] : "";
      configs.defaultValues.nameOfOfficerInCharge = isModify ? (assignees?.filter(assignee=>assignee?.code === contract?.additionalDetails?.officerInChargeId))?.[0] : "";
      configs.defaultValues.roleOfCBO = isModify ? {code : contract?.executingAuthority, name : `COMMON_MASTERS_${contract?.executingAuthority}`} : "";
      configs.defaultValues.projectCompletionPeriodInDays = isModify ? contract?.completionPeriod : "";
      configs.defaultValues.documents = isModify ? handleModifyWOFiles(contract?.documents) : "";
      configs.defaultValues.WOTermsAndConditions = isModify ? [...contract?.additionalDetails?.termsAndConditions] : "";
      setSessionFormData({...sessionFormData, ...configs?.defaultValues});
    }
}