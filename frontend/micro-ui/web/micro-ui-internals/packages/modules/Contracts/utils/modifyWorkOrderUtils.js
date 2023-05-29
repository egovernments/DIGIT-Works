//This handler creates a FormData for Docs defaultValues
//This will add 'id' for all the docs, as they are coming from response
//These Ids will be used when user will modify or delete a doc of certain criteria
export const handleModifyWOFiles = (uploadedDocs, docConfigData) => {
  //form data inout name mapping with file category Name

  let fileKeyMappings = [];
  let docMDMS = docConfigData?.works?.DocumentConfig?.[0]?.documents;
  docMDMS?.forEach((doc)=>{
    fileKeyMappings?.push({
      key : doc?.name,
      value : doc?.code
    })
  });

  let documentObject = {};

  //keep only active docs -- at a time there would be only fileKeyMappings.length no of files
  uploadedDocs = uploadedDocs?.filter(uploadedDoc=>uploadedDoc?.status !== "INACTIVE");
  fileKeyMappings?.map((fileKeyMapping)=>{
    let currentDoc = uploadedDocs?.filter((doc)=>doc?.documentType === fileKeyMapping?.value)[0];

    if(currentDoc?.fileStore) {
      if(fileKeyMapping?.value === "OTHERS") {
        documentObject["doc_others_name"] = currentDoc?.additionalDetails?.otherCategoryName;
      }
      documentObject[fileKeyMapping?.key] = [
        [currentDoc?.additionalDetails?.fileName, {file : { name : currentDoc?.additionalDetails?.fileName, id : currentDoc?.id}, fileStoreId : { fileStoreId : currentDoc?.fileStore}}]
      ] 
    }
  });
  return documentObject;
}

const handleRoleOfCBO = ({calculatedWOAmount, roleOfCBO}) => {
  let roles = roleOfCBO?.works?.CBORoles;
  let limitAmount = roles?.[0]?.amount;

  if(calculatedWOAmount < limitAmount) {
    //IA
    return roles?.filter(role=>role?.code === "IA")[0];
  }else {
    //IP
    return roles?.filter(role=>role?.code === "IP")[0];
  }
}

export const updateDefaultValues = ({createWorkOrderConfigMUKTA, isModify, sessionFormData, setSessionFormData, contract, estimate, project, handleWorkOrderAmount, overHeadMasterData, createNameOfCBOObject, organisationOptions, createOfficerInChargeObject, assigneeOptions, roleOfCBO, docConfigData}) => {
  if(!isModify) {
      //clear defaultValues from 'config' ( this case can come when user navigates from Create Screen to Modify Screen )
      //these are the req default Values for Create WO
      let validDefaultValues = ["basicDetails_projectID", "workOrderAmountRs", "basicDetails_dateOfProposal", "basicDetails_projectName", "basicDetails_projectDesc"];
      createWorkOrderConfigMUKTA.defaultValues = Object.keys(createWorkOrderConfigMUKTA?.defaultValues)
                            .filter(key=> validDefaultValues.includes(key))
                            .reduce((obj, key) => Object.assign(obj, {
                              [key] : createWorkOrderConfigMUKTA.defaultValues[key]
                            }), {});
    }

    //update default Values
    if(!sessionFormData?.basicDetails_projectID || !sessionFormData.workOrderAmountRs || !sessionFormData.basicDetails_dateOfProposal || !sessionFormData.basicDetails_projectName || !sessionFormData.basicDetails_projectDesc ) {
      if(isModify) {
        //this field is only for Modify flow
        createWorkOrderConfigMUKTA.defaultValues.basicDetails_workOrdernumber = contract?.contractNumber ? contract?.contractNumber  : "";
      }else{
        contract = {};
      }
      
      let organisations = createNameOfCBOObject(organisationOptions);
      let assignees = createOfficerInChargeObject(assigneeOptions);

      createWorkOrderConfigMUKTA.defaultValues.basicDetails_projectID = project?.projectNumber ? project?.projectNumber  : "",
      createWorkOrderConfigMUKTA.defaultValues.basicDetails_dateOfProposal = project?.additionalDetails?.dateOfProposal ? Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal) : "",
      createWorkOrderConfigMUKTA.defaultValues.basicDetails_projectName = project?.name ? project?.name  : "";
      createWorkOrderConfigMUKTA.defaultValues.basicDetails_projectDesc = project?.description ? project?.description  : "";    
      
      let calculatedWOAmount = handleWorkOrderAmount({estimate, overHeadMasterData})

      createWorkOrderConfigMUKTA.defaultValues.workOrderAmountRs = isModify ? contract?.totalContractedAmount : calculatedWOAmount;
      createWorkOrderConfigMUKTA.defaultValues.nameOfCBO =  isModify ? (organisations?.filter(org=>org?.code === contract?.additionalDetails?.cboCode))?.[0] : "";
      createWorkOrderConfigMUKTA.defaultValues.nameOfOfficerInCharge = isModify ? (assignees?.filter(assignee=>assignee?.code === contract?.additionalDetails?.officerInChargeId))?.[0] : "";

      let roleOfCBO_basedOnWOAmount = handleRoleOfCBO({calculatedWOAmount, roleOfCBO});

      createWorkOrderConfigMUKTA.defaultValues.roleOfCBO = isModify ? {code : contract?.executingAuthority, name : `COMMON_MASTERS_${contract?.executingAuthority}`} : roleOfCBO_basedOnWOAmount;
      createWorkOrderConfigMUKTA.defaultValues.projectCompletionPeriodInDays = isModify ? contract?.completionPeriod : "";
      createWorkOrderConfigMUKTA.defaultValues.documents = isModify ? handleModifyWOFiles(contract?.documents, docConfigData) : "";
      createWorkOrderConfigMUKTA.defaultValues.WOTermsAndConditions = isModify ? [...contract?.additionalDetails?.termsAndConditions] : "";
      setSessionFormData({...sessionFormData, ...createWorkOrderConfigMUKTA?.defaultValues});
    }
}

