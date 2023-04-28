import React, { useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import CreatePurchaseBillForm from "./CreatePurchaseBillForm";
import createPurchaseBillConfigMUKTA from "../../../configs/createPurchaseBillConfigMUKTA.json";
import { Loader, Header } from "@egovernments/digit-ui-react-components";
import { updateDefaultValues } from "../../../utils/index.js";

const PurchaseBill = () => {
    const {t} = useTranslation();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [contractNumber, setContractNumber] = useState(queryStrings?.workOrderNumber ? queryStrings?.workOrderNumber : "");
    const tenantId = queryStrings?.tenantId;
    const isModify = queryStrings?.workOrderNumber ? false : true;
    const [nameOfVendor, setNameOfVendor] = useState([]);
    const [isFormReady, setIsFormReady] = useState(false);
    const stateTenant = Digit.ULBService.getStateId();

    const { isLoading : isConfigLoading, data : configs} = Digit.Hooks.useCustomMDMS( 
    stateTenant,
    Digit.Utils.getConfigModuleName(),
    [
        {
            "name": "CreatePurchaseBillConfig"
        }
    ],
    {
      select: (data) => {
          return data?.[Digit.Utils.getConfigModuleName()]?.CreatePurchaseBillConfig[0];
      },
    }
    );

    //local config
    // let configs = createPurchaseBillConfigMUKTA?.CreatePurchaseBillConfig[0];

    //fetching contract data
    const { isLoading: isContractLoading,data : contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId,
        filters: { contractNumber, tenantId },
        config:{
            enabled: !!(contractNumber),
            cacheTime : 0
        }
    })

    const findCurrentDate = () => {
        var date = new Date();
        var dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
                      .toISOString()
                      .split("T")[0];
        return dateString;
    } 

    //session data
    const PurchaseBillSession = Digit.Hooks.useSessionStorage("PURCHASE_BILL_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = PurchaseBillSession;

    const searchVendorPayload = {
        "SearchCriteria": {
            "tenantId": tenantId,
            "functions" : {
                "type" : "VEN" //hardcoded
            }
        }
    }

    //vendor search
    const { isLoading : isOrgSearchLoading, data : vendorOptions } = Digit.Hooks.organisation.useSearchOrg(searchVendorPayload);

    const createNameOfVendorObject = (vendorOptions) => {
        return vendorOptions?.organisations?.map(vendorOption => ( {code : vendorOption?.id, name : vendorOption?.name, applicationNumber : vendorOption?.applicationNumber, orgNumber : vendorOption?.orgNumber } ))
    }

    useEffect(()=>{
        if((contract && configs && !isOrgSearchLoading && !isContractLoading)) {
            updateDefaultValues({t, tenantId, configs, findCurrentDate, isModify, sessionFormData, setSessionFormData, contract});
            setNameOfVendor(createNameOfVendorObject(vendorOptions));
            setIsFormReady(true);
        }
    },[isContractLoading, isOrgSearchLoading]);

    
    //if(isConfigLoading) return <Loader></Loader>
    return (
        <React.Fragment>
            <Header styles={{fontSize: "32px"}}>{isModify ? t("EXP_MODIFY_PB") : t("ACTION_TEST_CREATE_PB")}</Header>
            {
                isFormReady && <CreatePurchaseBillForm 
                createPurchaseBillConfig={configs} 
                sessionFormData={sessionFormData} 
                setSessionFormData={setSessionFormData} 
                clearSessionFormData={clearSessionFormData} 
                contract={contract} 
                preProcessData={{nameOfVendor}}
                isModify={isModify} 
                ></CreatePurchaseBillForm>
            }
        </React.Fragment>
    )
}

export default PurchaseBill;