//This handler creates a FormData for Docs defaultValues
//This will add 'id' for all the docs, as they are coming from response
//These Ids will be used when user will modify or delete a doc of certain criteria
export const handleModifyProjectFiles = (uploadedDocs) => {
  
  //form data inout name mapping with file category Name
  let fileKeyMappings = [
    {key : "noSubProject_doc_feasibility_analysis", value : "Feasiblity Analysis"},
    {key : "noSubProject_doc_finalized_worklist", value : "Finalized Worklist"},
    {key : "noSubProject_doc_others", value : "Other"},
    {key : "noSubProject_doc_project_proposal", value : "Project Proposal"},
  ]

  let documentObject = {};
  fileKeyMappings?.map((fileKeyMapping)=>{
    let currentDoc = uploadedDocs?.filter((doc)=>doc?.documentType === fileKeyMapping?.value)[0];

    if(currentDoc?.fileStore && currentDoc?.status !== "INACTIVE") {
      if(fileKeyMapping?.value === "Other") {
        documentObject["noSubProject_doc_others_name"] = currentDoc?.additionalDetails?.otherCategoryName;
      }
      documentObject[fileKeyMapping?.key] = [
        [currentDoc?.additionalDetails?.fileName, {file : { name : currentDoc?.additionalDetails?.fileName, id : currentDoc?.id}, fileStoreId : { fileStoreId : currentDoc?.fileStore}}]
      ] 
    }
  });
  return documentObject;
}

export const updateDefaultValues = ({configs, isModify, sessionFormData, setSessionFormData, contract, estimate, project, handleWorkOrderAmount, overHeadMasterData}) => {
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

      configs.defaultValues.basicDetails_projectID = project?.projectNumber ? project?.projectNumber  : "",
      configs.defaultValues.basicDetails_dateOfProposal = project?.additionalDetails?.dateOfProposal ? Digit.DateUtils.ConvertEpochToDate(project?.additionalDetails?.dateOfProposal) : "",
      configs.defaultValues.basicDetails_projectName = project?.name ? project?.name  : "";
      configs.defaultValues.basicDetails_projectDesc = project?.description ? project?.description  : "";
      configs.defaultValues.workOrderAmountRs = isModify ? contract?.totalContractedAmount  : handleWorkOrderAmount({estimate, overHeadMasterData});

   
      setSessionFormData({...sessionFormData, ...configs?.defaultValues});
    }
}