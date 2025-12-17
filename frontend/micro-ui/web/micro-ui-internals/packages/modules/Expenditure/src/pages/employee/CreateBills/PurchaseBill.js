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
    const billNumber = queryStrings?.billNumber
    const isModify = billNumber ? true : false;
    const [nameOfVendor, setNameOfVendor] = useState([]);
    const [nameOfCbo, setNameOfCbo] = useState([]);
    const [isFormReady, setIsFormReady] = useState(false);
    const stateTenant = Digit.ULBService.getStateId();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("works.purchase");

    const organisationTypes = [
        {
            code : "CBO",
            name : t("COMMON_MASTERS_ORG_CBO")
        },
        {
            code : "VEN",
            name : t("COMMON_MASTERS_ORG_VEN")
        }
    ]

    const searchVendorPayload = {
        "SearchCriteria": {
            "tenantId": tenantId,
            "functions" : {
                "type" : "VEN" //hardcoded
            }
        }
    }

    const searchCBOPayload = {
        "SearchCriteria": {
            "tenantId": tenantId,
            "functions" : {
                "type" : "CBO" //hardcoded
            }
        }
    }

    //vendor search
    const { isLoading : isOrgSearchLoading, data : vendorOptions } = Digit.Hooks.organisation.useSearchOrg(searchVendorPayload, {
        cacheTime: 0
    });

    const { isLoading : isCBOOrgSearchLoading, data : cboOptions } = Digit.Hooks.organisation.useSearchOrg(searchCBOPayload, {
        cacheTime: 0
    });

    const { allMeasurementsIds, totalMaterialAmount, totalPaidAmountForSuccessfulBills } = Digit.Hooks.paymentInstruction.useMBDataForPB({workOrderNumber:contractNumber,tenantId});
    let MBValidationData = {
        allMeasurementsIds,
        totalMaterialAmount,
        totalPaidAmountForSuccessfulBills
    }


    // const { isLoading : isConfigLoading, data : configs} = Digit.Hooks.useCustomMDMS( 
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

    //local config
    let configs = createPurchaseBillConfigMUKTA?.CreatePurchaseBillConfig[0];

    const tenant = Digit.ULBService.getStateId();

    const { isLoading: isDocConfigLoading, data : docConfigData } = Digit.Hooks.useCustomMDMS(
        tenant,
        "works",
        [
            {
                "name": "DocumentConfig",
                "filter": `[?(@.module=='Expenditure')]`
            }
        ]
    );

    //fetching contract data
    const { isLoading: isContractLoading,data : contract } = Digit.Hooks.contracts.useContractSearch({
        tenantId,
        filters: { contractNumber, tenantId },
        config:{
            enabled: true,
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

    
    const createNameOfVendorObject = (vendorOptions) => {
        return vendorOptions?.organisations?.map(vendorOption => ( {code : vendorOption?.id, name : vendorOption?.name, applicationNumber : vendorOption?.applicationNumber, orgNumber : vendorOption?.orgNumber } ))
    }

    //bill search
    const billPayload = {
        billCriteria: {
          tenantId,
          billNumbers: [ billNumber ],
          businessService: businessService,
        },
        pagination: { limit: 10, offSet: 0, sortBy: "ASC", order: "ASC"}
      }
    const { isLoading: isBillSearchLoading, data: billData} = Digit.Hooks.bills.useSearchBill({data: billPayload, config:{
        enabled: isModify,
        cacheTime:0
    }})

    const orgSearch = {
        "SearchCriteria": {
            "tenantId": tenantId,
            id: [billData?.bills?.[0]?.billDetails?.[0]?.payee?.identifier]
        }
    }

    //vendor search
    const { isLoading : isOrgSearchLoadingModify, data : vendorOptionsModify } = Digit.Hooks.organisation.useSearchOrg(orgSearch,{
        enabled:billData ? true : false,
        cacheTime:0
    });


    const { isLoading : isChargesLoading, data : charges} = Digit.Hooks.useCustomMDMS( 
    Digit.ULBService.getStateId(),
    "expense",
    [
        {
            "name": "ApplicableCharges"
        }
    ],
    {
      select: (data) => {
        return data?.expense?.ApplicableCharges
      },
    }
    );

    useEffect(()=>{
        if((configs && !isOrgSearchLoading && !isCBOOrgSearchLoading && !isContractLoading && !isDocConfigLoading && !isDocConfigLoading && !isBillSearchLoading && !isOrgSearchLoadingModify)) {
            updateDefaultValues({t, tenantId, configs, findCurrentDate, isModify, sessionFormData, setSessionFormData, contract, docConfigData, billData, setIsFormReady,charges,org:vendorOptionsModify?.organisations?.[0]});
            setNameOfVendor(createNameOfVendorObject(vendorOptions));
            setNameOfCbo(createNameOfVendorObject(cboOptions));
        }
    },[isContractLoading, isOrgSearchLoading, isCBOOrgSearchLoading, isDocConfigLoading, isBillSearchLoading,isChargesLoading,isOrgSearchLoadingModify]);

    
    // if(isConfigLoading) return <Loader></Loader>

    // if(isContractLoading || isOrgSearchLoading || isCBOOrgSearchLoading || isDocConfigLoading || isBillSearchLoading || isChargesLoading) return <Loader />

    return (
        <React.Fragment>
            <Header >{isModify ? t("EXP_MODIFY_PB") : t("ACTION_TEST_CREATE_PB")}</Header>
            {
                isFormReady && <CreatePurchaseBillForm 
                createPurchaseBillConfig={configs} 
                sessionFormData={sessionFormData} 
                setSessionFormData={setSessionFormData} 
                clearSessionFormData={clearSessionFormData} 
                contract={contract} 
                preProcessData={{nameOfVendor, nameOfCbo, organisationTypes}}
                isModify={isModify} 
                docConfigData={docConfigData}
                bill={isModify?billData?.bills?.[0]:null}
                MBValidationData={MBValidationData}
                ></CreatePurchaseBillForm>
            }
        </React.Fragment>
    )
}

export default PurchaseBill;