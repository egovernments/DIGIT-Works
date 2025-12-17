import { getLabourMaterialAnalysisCost } from "../../../../../util/EstimateData";

const convertNumberFields=(text="")=>{
    return parseFloat(text)==0?null:parseFloat(text);

}
const transformLineItems = (SorData = [], category, isEdit = false, isCreateRevisionEstimate=false, isEditRevisionEstimate=false) => {
  const lineItems = [];
  SorData?.filter((ob) => ob?.category === "SOR" ? ob?.sorCode : true).map((row) => {
    const measures = row?.measures?.map((measure) => {
      return transformMeasure(measure, row, isEdit, category, isCreateRevisionEstimate, isEditRevisionEstimate);
    });
    lineItems.push(...measures);
  });
  return lineItems;
};

function transformMeasure(measure, parentData, isEdit, category, isCreateRevisionEstimate, isEditRevisionEstimate) {
  let measureObject = {
    sorId: parentData?.category == "SOR" ? parentData?.sorId : parentData?.sNo,
    category: parentData?.category || category,
    name: parentData?.description,
    unitRate: parentData?.unitRate,
    noOfunit: convertNumberFields(measure?.noOfunit) || 0,
    uom: parentData?.uom,
    height: convertNumberFields(measure?.height),
    isDeduction: measure?.isDeduction,
    length: convertNumberFields(measure?.length),
    quantity: convertNumberFields(measure?.number),
    uomValue: null,
    width: convertNumberFields(measure?.width),
    description: measure.description || "   ",
    additionalDetails: parentData.additionalInfo, // Include data from parent object
    amountDetail: [
      {
        type: "EstimatedAmount",
        amount: parentData?.unitRate * measure?.noOfunit,
        additionalDetails: {},
      },
    ],
  };
   if(isEdit)
   {
    measureObject = {
      ...measureObject,
      sorId: parentData?.sorType ? parentData?.sorCode : parentData?.sNo,
      id: measure?.id && measure?.id !== "" ? measure?.id : null,
      category: category,
      amountDetail: [
              {
                type: "EstimatedAmount",
                amount: parentData?.unitRate * measure?.noOfunit,
                additionalDetails: {},
                id: measure?.amountid,
              },
            ],
    }
   }  
   if(isCreateRevisionEstimate || isEditRevisionEstimate)
    {
      measureObject = {
        ...measureObject,
        sorId: parentData?.sorType ? parentData?.sorCode : parentData?.sNo,
        id: isEditRevisionEstimate ? (measure?.id && measure?.id !== "" ? measure?.id : null) : null,
        previousLineItemId: (isEditRevisionEstimate ? measure?.previousLineItemId : (measure?.id && measure?.id !== "" ? measure?.id : null)) || null,
        category: category,
        amountDetail: [
                {
                  type: "EstimatedAmount",
                  amount: parentData?.unitRate * measure?.noOfunit,
                  additionalDetails: {},
                  id: measure?.amountid,
                },
              ],
      }
    }
   
   return measureObject;
}

const fetchEstimateDetails = (data) => {
  // let sornonSORData = data?.nonSORTablev1?.filter(row=> row && row.estimatedAmount!=="0")?.map(row => {

  //     return {
  //         "sorId": 45,
  //         "category": "NON-SOR",
  //         "name": row?.description,
  //         "description": row?.description,
  //         "unitRate": row?.rate,
  //         "noOfunit": row?.estimatedQuantity,
  //         "uom": row?.uom?.code,
  //         // "uomValue": 10, //not sure what is this field//try removing this field
  //         "amountDetail": [
  //             {
  //                 "type": "EstimatedAmount",
  //                 "amount": row?.estimatedAmount,
  //                 "additionalDetails":{}
  //             }
  //         ],

  //     }
  // })
  const Sors = (data?.SORtable && transformLineItems(data?.SORtable,"SOR")) || [];
  const NonSors = (data?.NONSORtable && transformLineItems(data?.NONSORtable,"NON-SOR")) || [];
  const detailedEstimates = [...Sors?.filter((ob) => ob?.sorCode || ob?.sorId), ...NonSors];

  let overHeadsData = data?.overheadDetails
    ?.filter((row) => row && row.amount !== "0" && row?.amount !== undefined)
    ?.map((row) => {
      return {
        category: "OVERHEAD",
        name: row?.name?.code,
        description: row?.name?.description,
        amountDetail: [
          {
            type: row?.name?.code,
            amount: row?.amount,
          },
        ],
        additionalDetails: {
          row,
        },
      };
    });

  return [...detailedEstimates, ...overHeadsData];
};

const fetchEstimateDetailsEdit = (isEdit, data, estimate, isCreateRevisionEstimate, isEditRevisionEstimate) => {

  const Sors = (data?.SORtable && transformLineItems(data?.SORtable,"SOR", isEdit, isCreateRevisionEstimate, isEditRevisionEstimate)) || [];
  const NonSors = (data?.NONSORtable && transformLineItems(data?.NONSORtable,"NON-SOR", isEdit, isCreateRevisionEstimate, isEditRevisionEstimate)) || [];
  const detailedEstimates = [...Sors?.filter((ob) => ob?.sorCode || ob?.sorId.toString()?.includes("SOR")), ...NonSors];

  let overHeadsData = data?.overheadDetails
    ?.filter((row) => row && row.amount !== "0" && row.amount !== undefined)
    ?.map((row) => {
      let overheadObject = {
            id: estimate?.estimateDetails?.filter((ob) => ob?.category === "OVERHEAD" && ob?.name === row?.name?.code)?.[0]?.id || null,
            category: "OVERHEAD",
            name: row?.name?.code,
            description: row?.name?.description,
            amountDetail: [
              {
                type: row?.name?.code,
                amount: row?.amount,
                id: estimate?.estimateDetails?.filter((ob) => ob?.category === "OVERHEAD" && ob?.name === row?.name?.code)?.[0]?.amountDetail?.[0]?.id || null
              },
            ],
            additionalDetails: {
              row,
            },
          };
        if(isCreateRevisionEstimate || isEditRevisionEstimate)
        overheadObject = {
          ...overheadObject,
          id: isEditRevisionEstimate ? estimate?.estimateDetails?.filter((ob) => ob?.category === "OVERHEAD" && ob?.name === row?.name?.code)?.[0]?.id : null,
          previousLineItemId : isEditRevisionEstimate? estimate?.estimateDetails?.filter((ob) => ob?.category === "OVERHEAD" && ob?.name === row?.name?.code)?.[0]?.previousLineItemId : estimate?.estimateDetails?.filter((ob) => ob?.category === "OVERHEAD" && ob?.name === row?.name?.code)?.[0]?.id,
        }
        return overheadObject;
    });

    //idetified and lineitems which has been deleted and then marked it as inactive
    
    let deletedSorNonSor =  estimate?.estimateDetails?.filter(item => !detailedEstimates.find(x => x.id === item.id) && item.category !== "OVERHEAD")
                .map(item => ({ ...item, isActive: false }));
    let deletedOverheads = estimate?.estimateDetails?.filter(item => !overHeadsData.find(x => x.id === item.id) && item.category === "OVERHEAD")
                .map(item => ({ ...item, isActive: false }));
    if(window.location.href.includes("estimate/create-revision-detailed-estimate" || "estimate/update-revision-detailed-estimate")){
       deletedSorNonSor = [];
       deletedOverheads = [];
    }
    return [...detailedEstimates, ...overHeadsData, ...deletedSorNonSor, ...deletedOverheads];
};

const fetchDocuments = (docs) => {
  const obj = Object.keys(docs).map((key) => {
    return {
      fileName: key?.includes("OTHERS") ? docs?.["ESTIMATE_DOC_OTHERS_name"] : docs?.[key]?.[0]?.[0],
      fileStoreId: docs?.[key]?.[0]?.[1]?.fileStoreId?.fileStoreId,
      documentUid: docs?.[key]?.[0]?.[1]?.fileStoreId?.fileStoreId,
      tenantId: docs?.[key]?.[0]?.[1]?.fileStoreId?.tenantId,
      fileType: key?.includes("OTHERS") ? "Others" : `${key}`,
    };
  });

  return obj;
};

//Method is used to create labourAnalysisPayload in additional details
const getLabourMaterialAnalysis = (data) => {
  return {
    labour : parseFloat(getLabourMaterialAnalysisCost(data,["LA"])),
    material : parseFloat(getLabourMaterialAnalysisCost(data,["MA","RA","CA","EMF","DMF","ADC","LC"])),
    machinery : parseFloat(getLabourMaterialAnalysisCost(data,["MHA"]))
  }
}

export const createEstimatePayload = (data, projectData, isEdit, estimate, isCreateRevisionEstimate, isEditRevisionEstimate) => {
  if (isEdit || isCreateRevisionEstimate || isEditRevisionEstimate) {
    //here make the payload of edit estimate rather than create estimate

    let filteredFormData = Object.fromEntries(Object.entries(data).filter(([_, v]) => v != null));
    const tenantId = Digit.ULBService.getCurrentTenantId();
    let payload = {
      estimate: {
        id: estimate.id,
        estimateNumber: estimate.estimateNumber,
        revisionNumber : estimate.revisionNumber || null,
        tenantId: tenantId,
        projectId: projectData?.projectDetails?.searchedProject?.basicDetails?.uuid,
        status: "ACTIVE",
        wfStatus: estimate?.wfStatus,
        name: projectData?.projectDetails?.searchedProject?.basicDetails?.projectName,
        businessService : isCreateRevisionEstimate || isEditRevisionEstimate ? "REVISION-ESTIMATE" : null,
        description: projectData?.projectDetails?.searchedProject?.basicDetails?.projectDesc,
        oldUuid : estimate?.oldUuid,
        executingDepartment: "WRK", //hardcoded since we are not capturing it anymore and it is required at BE side
        // "executingDepartment": filteredFormData?.selectedDept?.code,
        // "projectId":"7c941228-6149-4adc-bdb9-8b77f6c3757d",//static for now
        address: {
          ...projectData?.projectDetails?.searchedProject?.basicDetails?.address,
          tenantId, //here added because in address tenantId is mandatory from BE side
        }, //get from project search
        estimateDetails: fetchEstimateDetailsEdit(isEdit, filteredFormData, estimate, isCreateRevisionEstimate, isEditRevisionEstimate),
        additionalDetails: {
          documents: fetchDocuments(data?.uploadedDocs),
          labourMaterialAnalysis: { ...filteredFormData?.labourMaterialAnalysis },
          creator: Digit.UserService.getUser()?.info?.name,
          location: {
            locality: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.boundary,
            ward: projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
            city: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.city,
          },
          projectNumber: projectData?.projectDetails?.searchedProject?.basicDetails?.projectID,
          totalEstimatedAmount: data?.totalEstimatedAmount,
          tenantId: tenantId,
          ward: projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
          locality: projectData?.projectDetails?.searchedProject?.basicDetails?.locality,
          sorSkillData : filteredFormData?.SORtable?.map((ob) => {
            return {
              sorId : ob?.sorId || ob?.sorCode,
              sorType : ob?.sorType,
              sorSubType : ob?.sorSubType
            }
        })
        },
      },
      workflow: {
        action:  (estimate?.wfStatus === "PENDINGFORCORRECTION" && (data?.wfAction === "WF_SUBMIT" || data?.workflowAction === "WF_SUBMIT")) ? "RE-SUBMIT" : data?.workflowAction?.split("_")?.[1],
        comments: filteredFormData?.comments,
        assignees: [data?.workflowAction === "WF_DRAFT" ? Digit.UserService.getUser()?.info?.uuid : (filteredFormData?.selectedApprover?.uuid ? filteredFormData?.selectedApprover?.uuid : undefined)],
      },
    };

    if (!payload.workflow.assignees?.[0]) delete payload.workflow.assignees;
    return payload;
  } else {
    let filteredFormData = Object.fromEntries(Object.entries(data).filter(([_, v]) => v != null));
    const tenantId = Digit.ULBService.getCurrentTenantId();
    let payload = {
      estimate: {
        tenantId: tenantId,
        projectId: projectData?.projectDetails?.searchedProject?.basicDetails?.uuid,
        // "projectId": "4bf36cd5-f10a-4a46-bdfc-aa364e67546f",
        status: "ACTIVE",
        wfStatus: "CREATED",
        name: "Testing",
        businessService : isCreateRevisionEstimate || isEditRevisionEstimate ? "REVISION-ESTIMATE" : "ESTIMATE",
        // "name": projectData?.projectDetails?.searchedProject?.basicDetails?.projectName,
        description: projectData?.projectDetails?.searchedProject?.basicDetails?.projectDesc,
        executingDepartment: "WRK", //hardcoded since we are not capturing it anymore and it is required at BE side
        // "executingDepartment": filteredFormData?.selectedDept?.code,
        // "projectId":"7c941228-6149-4adc-bdb9-8b77f6c3757d",//static for now
        address: {
          ...projectData?.projectDetails?.searchedProject?.basicDetails?.address,
          tenantId, //here added because in address tenantId is mandatory from BE side
        }, //get from project search
        estimateDetails: fetchEstimateDetails(filteredFormData),
        additionalDetails: {
          documents: fetchDocuments(data?.uploadedDocs),
          labourMaterialAnalysis: getLabourMaterialAnalysis(data),
          creator: Digit.UserService.getUser()?.info?.name,
          location: {
            locality: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.boundary,
            ward: projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
            city: projectData?.projectDetails?.searchedProject?.basicDetails?.address?.city,
          },
          ward: projectData?.projectDetails?.searchedProject?.basicDetails?.ward,
          locality: projectData?.projectDetails?.searchedProject?.basicDetails?.locality,
          projectNumber: projectData?.projectDetails?.searchedProject?.basicDetails?.projectID,
          totalEstimatedAmount: data?.totalEstimatedAmount,
          tenantId: tenantId,
          projectName: projectData?.projectDetails?.searchedProject?.basicDetails?.projectName,
          sorSkillData : filteredFormData?.SORtable?.map((ob) => {
            return {
              sorId : ob?.sorId,
              sorType : ob?.sorType,
              sorSubType : ob?.sorSubType
            }
        })
        },
      },
      workflow: {
        action: data?.workflowAction?.includes("WF")? data?.workflowAction?.split("_")?.[1] : data?.workflowAction,
        comments: filteredFormData?.comments,
        assignees: [data?.workflowAction === "WF_DRAFT" ? Digit.UserService.getUser()?.info?.uuid : (filteredFormData?.selectedApprover?.uuid ? filteredFormData?.selectedApprover?.uuid : undefined)],
      },
    };

    if (!payload.workflow.assignees?.[0]) delete payload.workflow.assignees;
    return payload;
  }
};
