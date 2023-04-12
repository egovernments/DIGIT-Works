import React, { useEffect, useMemo, useState } from "react";
import CreatePurchaseBillForm from "./CreatePurchaseBillForm";
import createPurchaseBillConfigMUKTA from "../../../configs/createPurchaseBillConfigMUKTA.json";
import { useTranslation } from "react-i18next";
import { Loader } from "@egovernments/digit-ui-react-components";
import { updateDefaultValues } from "../../../utils/index.js";

const PurchaseBill = () => {
    const {t} = useTranslation();
    const queryStrings = Digit.Hooks.useQueryParams();
    const contractNumber = queryStrings?.workOrderNumber;
    const tenantId = queryStrings?.tenantId;
    const [documents, setDocuments] = useState([]);

    const isModify = queryStrings?.workOrderNumber ? false : true;
    const [nameOfVendor, setNameOfVendor] = useState([]);
    //const [isFormReady, setIsFormReady] = useState(false);

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
    const { isLoading: isContractLoading,data:contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId,
        filters: { contractNumber, tenantId },
        config:{
            enabled: true,
            cacheTime : 0
        }
    })

    console.log("CONTRACT :", contract);


    //session data
    const PurchaseBillSession = Digit.Hooks.useSessionStorage("PURCHASE_BILL_CREATE", {
        basicDetails_workOrderNumber : "",
        basicDetails_projectID : "",
        basicDetails_projectDesc : "",
        basicDetails_location : ""
    });
    const [sessionFormData, setSessionFormData, clearSessionFormData] = PurchaseBillSession;

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

    const searchVendorPayload = {
        "SearchCriteria": {
            "tenantId": tenantId,
            "functions" : {
                "type" : "VEN"
            }
        }
    }

    //vendor search
    const { isLoading : isOrgSearchLoading, data : vendorOptions } = Digit.Hooks.organisation.useSearchOrg(searchVendorPayload);

    
    //Deductions Search
    // const { isLoading : isDeductionsMasterDataLoading, data : deductionsMasterData } = Digit.Hooks.useCustomMDMS(
    //     Digit.ULBService.getStateId(),
    //     "works",
    //     [{ "name": "Deductions" }]
    // );

    const createNameOfVendorObject = (vendorOptions) => {
        return vendorOptions?.organisations?.map(vendorOption => ( {code : vendorOption?.id, name : vendorOption?.name, applicationNumber : vendorOption?.applicationNumber, orgNumber : vendorOption?.orgNumber } ))
    }

    useEffect(()=>{
        if((contract && configs && !isOrgSearchLoading && !isContractLoading)) {
            updateDefaultValues({ configs, isModify, contract, createNameOfVendorObject, vendorOptions, t});

            //setDocuments(createDocumentObject(contract?.additionalDetails?.documents));
            setNameOfVendor(createNameOfVendorObject(vendorOptions));

            //setIsFormReady(true);
        }
    },[isContractLoading, isOrgSearchLoading, isContractLoading, contract]);
    
    // if(isConfigLoading) return <Loader></Loader>
    return (
        <React.Fragment>
            {
                 <CreatePurchaseBillForm 
                createPurchaseBillConfig={configs} 
                sessionFormData={sessionFormData} 
                setSessionFormData={setSessionFormData} 
                clearSessionFormData={clearSessionFormData} 
                tenantId={tenantId} 
                contract={contract} 
                preProcessData={{nameOfVendor : nameOfVendor}}
                ></CreatePurchaseBillForm>
            }
        </React.Fragment>
    )
}

export default PurchaseBill;