import React, { useEffect, useMemo, useState } from "react";
import CreatePurchaseBillForm from "./CreatePurchaseBillForm";
import createPurchaseBillConfigMUKTA from "./createPurchaseBillConfigMUKTA.json";
import { useTranslation } from "react-i18next";
import { Loader } from "@egovernments/digit-ui-react-components";

const PurchaseBill = () => {
    const {t} = useTranslation();
    const queryStrings = Digit.Hooks.useQueryParams();
    const contractNumber = queryStrings?.workOrderNumber;
    const tenantId = queryStrings?.tenantId;
    const [documents, setDocuments] = useState([]);

    // const { isLoading : isConfigLoading, data : createPurchaseBillConfigMUKTA} = Digit.Hooks.useCustomMDMS( //change to data
    // stateTenant,
    // Digit.Utils.getConfigModuleName(),
    // [
    //     {
    //         "name": "CreatePurchaseBillConfig"
    //     }
    // ],
    // {
    //   select: (data) => {
    //       return data?.[Digit.Utils.getConfigModuleName()]?.CreatePurchaseBillConfig[0];
    //   },
    // }
    // );

    let configs = createPurchaseBillConfigMUKTA?.CreatePurchaseBillConfig[0];
    //fetching contract data
    // const { isLoading: isContractLoading, data:contract } = Digit.Hooks.contracts.useContractSearch({
    //     tenantId,
    //     filters: { contractNumber }
    // })

    //fetching project data
    // const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
    //     tenantId,
    //     searchParams: {
    //         Projects: [
    //             {
    //                 tenantId,
    //                 id:contract?.projectId
    //             }
    //         ]
    //     },
    //     config:{
    //         enabled: !!(contract?.projectId) 
    //     }
    // })

    //session data
    // const PurchaseBillSession = Digit.Hooks.useSessionStorage("PURCHASE_BILL_CREATE", {
    //     basicDetails_workOrderNumber : "",
    //     basicDetails_projectID : "",
    //     basicDetails_projectDesc : "",
    //     basicDetails_location : ""
    // });
    // const [sessionFormData, setSessionFormData, clearSessionFormData] = PurchaseBillSession;

    const createDocumentObject = (documents) => {
        let docs =  documents?.filter(document=>document?.fileStoreId)?.map((document) => {
            return {
                title: document?.fileType,
                documentType: document?.fileType,
                documentUid: document?.documentUid,
                fileStoreId: document?.fileStoreId,
            };
        })

        return [
            {
                title: "",
                BS : 'Works',
                values: docs,
            }
        ];
    }
    
    //Deductions Search
    // const { isLoading : isDeductionsMasterDataLoading, data : deductionsMasterData } = Digit.Hooks.useCustomMDMS(
    //     Digit.ULBService.getStateId(),
    //     "works",
    //     [{ "name": "Deductions" }]
    // );



    // if(isConfigLoading) return <Loader></Loader>
    return (
        <React.Fragment>
            {
                 <CreatePurchaseBillForm 
                createPurchaseBillConfig={configs} 
                //sessionFormData={sessionFormData} 
                //setSessionFormData={setSessionFormData} 
                //clearSessionFormData={clearSessionFormData} 
                tenantId={tenantId} 
                //contract={contract} 
                //project={project} 
                preProcessData={{documents : documents}}
                ></CreatePurchaseBillForm>
            }
        </React.Fragment>
    )
}

export default PurchaseBill;