import React, { useEffect, useMemo, useState } from "react";
import CreatePurchaseBillForm from "./CreatePurchaseBillForm";
import createPurchaseBillConfigMUKTA from "../../../configs/createPurchaseBillConfigMUKTA.json";
import { useTranslation } from "react-i18next";
import { Loader } from "@egovernments/digit-ui-react-components";
import { updateDefaultValues } from "../../../utils/index.js";

const PurchaseBill = () => {
    const {t} = useTranslation();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [contractNumber, setContractNumber] = useState(queryStrings?.workOrderNumber ? queryStrings?.workOrderNumber : "");
    const tenantId = queryStrings?.tenantId;
    const [documents, setDocuments] = useState([]);

    const isModify = queryStrings?.workOrderNumber ? false : true;
    const [nameOfVendor, setNameOfVendor] = useState([]);
    const [isFormReady, setIsFormReady] = useState(false);

    // const { isLoading : isConfigLoading, data : configs} = Digit.Hooks.useCustomMDMS( 
    // Digit.ULBService.getCurrentTenantId(),
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

    // useEffect(()=>{
    //     //if session PB# is diff from queryString PB#, reset sessionFormData
    //     if(sessionFormData?.basicDetails_purchaseBillNumber !== queryStrings?.purchaseBillNumber) {
    //         clearSessionFormData();
    //     }
    // },[])

    // useEffect(()=>{
    //     if(!isBillLoading && isModify) {
    //         setContractNumber(bill?.additionalDetails?.contractNumber)
    //     }
    // },[bill])

    //fetching contract data
    const { isLoading: isContractLoading,data:contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId,
        filters: { contractNumber, tenantId },
        config:{
            enabled: !!(contractNumber),
            cacheTime : 0
        }
    })
    console.log("CONTRACT :", contract);

    //session data
    const PurchaseBillSession = Digit.Hooks.useSessionStorage("PURCHASE_BILL_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = PurchaseBillSession;



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
    const { isLoading : isDeductionsMasterDataLoading, data : deductionMasterData } = Digit.Hooks.useCustomMDMS(
        Digit.ULBService.getStateId(),
        "works",
        [{ "name": "Deductions" }]
    );

    const createNameOfVendorObject = (vendorOptions) => {
        return vendorOptions?.organisations?.map(vendorOption => ( {code : vendorOption?.id, name : vendorOption?.name, applicationNumber : vendorOption?.applicationNumber, orgNumber : vendorOption?.orgNumber } ))
    }

    // const handleWorkOrderAmount = ({contract, deductionMasterData}) => {
    //     deductionMasterData = deductionMasterData?.works?.Deductions;
    //     let totalAmount = contract?.totalContractedAmount;

    //     //loop through the contract Details and filter with DEDUCTION
    //     bill?.contractDetails?.forEach((contractDetail)=>{
    //         if(contractDetail?.category !== "DEDUCTION") return;
    //         let amountDetails = contractDetail?.amountDetail?.[0];

    //         let deductionCode = amountDetails?.type;
    //         let shouldSubtract = !((deductionMasterData?.filter(deduction=>deduction?.code === deductionCode)?.[0])?.isWorkOrderValue);  //change accordingly

    //         if(shouldSubtract) {
    //             totalAmount -= amountDetails?.amount;
    //         }
    //     })
    //     return totalAmount;
    // }

    useEffect(()=>{
        if((contract && configs && !isOrgSearchLoading && !isDeductionsMasterDataLoading && !isContractLoading)) {
            updateDefaultValues({ configs, isModify, sessionFormData, setSessionFormData, contract, t});

            setNameOfVendor(createNameOfVendorObject(vendorOptions));

            setIsFormReady(true);
        }
    },[isContractLoading, isOrgSearchLoading, isDeductionsMasterDataLoading, contract]);

    
    // if(isConfigLoading) return <Loader></Loader>
    return (
        <React.Fragment>
            {
                isFormReady && <CreatePurchaseBillForm 
                createPurchaseBillConfig={configs} 
                sessionFormData={sessionFormData} 
                setSessionFormData={setSessionFormData} 
                clearSessionFormData={clearSessionFormData} 
                tenantId={tenantId} 
                contract={contract} 
                preProcessData={{nameOfVendor : nameOfVendor}}
                isModify={isModify} 
                ></CreatePurchaseBillForm>
            }
        </React.Fragment>
    )
}

export default PurchaseBill;