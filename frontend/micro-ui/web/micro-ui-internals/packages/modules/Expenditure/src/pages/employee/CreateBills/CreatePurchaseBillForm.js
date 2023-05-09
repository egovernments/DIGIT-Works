import { FormComposer, Header, Toast, WorkflowModal } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { useHistory } from "react-router-dom";
import { createBillPayload } from "../../../utils/createBillUtils";
import { updateBillPayload } from "../../../utils/updateBillPayload";

const navConfig =  [
    {
        name:"PB_Details",
        code:"COMMON_PB_DETAILS",
    }
];

const CreatePurchaseBillForm = ({
    createPurchaseBillConfig, 
    sessionFormData, 
    setSessionFormData, 
    clearSessionFormData, 
    contract,  
    preProcessData,
    isModify,
    docConfigData,
    bill
}) => {
    const {t} = useTranslation();
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const history = useHistory();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const rolesForThisAction = "BILL_CREATOR" //hardcoded for now

    createPurchaseBillConfig = useMemo(
        () => Digit.Utils.preProcessMDMSConfig(t, createPurchaseBillConfig, {
            updateDependent : [
              {
                  key : 'invoiceDetails_vendor',
                  value : [preProcessData?.nameOfVendor]
              },
              {
                key : 'basicDetails_purchaseBillNumber',
                value : [!isModify ? "none" : "flex"]
              },
              {
                key : 'billDetails_billDate',
                value : [new Date().toISOString().split("T")[0]]
              },
              {
                key : 'invoiceDetails_invoiceDate',
                value : [new Date().toISOString().split("T")[0]]
              },
            ]
          }),
      [preProcessData?.nameOfVendor]);

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

            if(formData.invoiceDetails_vendor) {
                setValue("invoiceDetails_vendorId", formData.invoiceDetails_vendor?.orgNumber);
            }

            if(formData.invoiceDetails_materialCost) {
                let gstAmount = formData.invoiceDetails_gst ? formData.invoiceDetails_gst : 0;
                setValue("billDetails_billAmt", parseInt(formData.invoiceDetails_materialCost)+parseInt(gstAmount));
            }

            if(formData.billDetails_billAmt) {
                let gstAmount = formData.invoiceDetails_gst ? formData.invoiceDetails_gst : 0;
                let value = parseFloat(formData.invoiceDetails_materialCost)+ parseFloat(gstAmount);
                if(value > contract?.totalContractedAmount){
                    setValue("billDetails_billAmt", Digit.Utils.dss.formatterWithoutRound(value, "number"));
                    setError("billDetails_billAmt",{ type: "custom" }, { shouldFocus: true })
                }
                else{
                    setValue("billDetails_billAmt", Digit.Utils.dss.formatterWithoutRound(value, "number"));
                    clearErrors("billDetails_billAmt")
                }
            }

            if(difference?.billDetails_billAmt){
                let billAmount = parseFloat(Digit.Utils.dss.convertFormatterToNumber(formData?.billDetails_billAmt));
                formData?.deductionDetails?.forEach((data, index)=>{
                  if(data?.name?.calculationType === "percentage") {
                    const amount = billAmount ? (billAmount * (parseFloat(data?.name?.value)/100)).toFixed(1) : 0
                    setValue(`deductionDetails.${index}.amount`, amount);
                  }
                })
            }

            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const handleToastClose = () => {
        setToast({show : false, label : "", error : false});
    }

    //remove Toast after 3s
    useEffect(()=>{
        if(toast?.show) {
        setTimeout(()=>{
            handleToastClose();
        },3000);
        }
    },[toast?.show]);

    const { mutate: CreatePurchaseBillMutation } = Digit.Hooks.bills.useCreatePurchaseBill();
    const { mutate: UpdatePurchaseBillMutation } = Digit.Hooks.bills.useUpdatePurchaseBill();
    const onFormSubmit = async(data) => {
        //transform formdata to Payload
        const payload = createBillPayload(data, contract, docConfigData);
        
        if(isModify){
            
            const updatedBillObject = updateBillPayload(bill,payload)
            const updatedPayload = {bill:updatedBillObject,workflow:{
                "action": "RE-SUBMIT",
                "assignees": []
              }}
            await UpdatePurchaseBillMutation(updatedPayload, {
                onError: async (error, variables) => {
                    
                    sendDataToResponsePage("billNumber", tenantId, false, "EXPENDITURE_PB_CREATED_FORWARDED", false);
                },
                onSuccess: async (responseData, variables) => {
                    
                    sendDataToResponsePage(responseData?.bills?.[0]?.billNumber, tenantId, true, "EXPENDITURE_PB_CREATED_FORWARDED", true);
                },
            });
        }else{

            await CreatePurchaseBillMutation(payload, {
                onError: async (error, variables) => {
                    sendDataToResponsePage("billNumber", tenantId, false, "EXPENDITURE_PB_CREATED_FORWARDED", false);
                },
                onSuccess: async (responseData, variables) => {
                sendDataToResponsePage(responseData?.bills?.[0]?.billNumber, tenantId, true, "EXPENDITURE_PB_CREATED_FORWARDED", true);
                },
            });
        }
    }

    const sendDataToResponsePage = (billNumber, tenantId, isSuccess, message, showID) => {
        history.push({
          pathname: `/${window?.contextPath}/employee/expenditure/create-purchase-bill-response`,
          search: `?billNumber=${billNumber}&tenantId=${tenantId}&isSuccess=${isSuccess}`,
          state : {
            message : message,
            showID : showID
          }
        }); 
      }
    
    useEffect(() => {
        return () => {
            if (!window.location.href.includes("create-purchase-bill") && Object.keys(sessionFormData) != 0) {
                clearSessionFormData();
            }
        };
    });

    return (
        <React.Fragment>

                {
                    createPurchaseBillConfig && 
                    (<FormComposer
                        label={isModify ? "COMMON_MODIFY_PB" : "ACTION_TEST_CREATE_PB"}
                        config={createPurchaseBillConfig?.form?.map((config) => {
                        return {
                            ...config,
                            body: config?.body.filter((a) => !a.hideInEmployee),
                        };
                        })}
                        onSubmit={onFormSubmit}
                        submitInForm={false}
                        fieldStyle={{ marginRight: 0 }}
                        inline={false}
                        className="form-no-margin"
                        defaultValues={sessionFormData}
                        showWrapperContainers={false}
                        isDescriptionBold={false}
                        noBreakLine={true}
                        showNavs={createPurchaseBillConfig?.metaData?.showNavs}
                        showFormInNav={true}
                        showMultipleCardsWithoutNavs={false}
                        showMultipleCardsInNavs={false}
                        horizontalNavConfig={navConfig}
                        onFormValueChange={onFormValueChange}
                        cardClassName = "mukta-header-card"
                    />)
                }
               {toast?.show && <Toast error={toast?.error} label={toast?.label} isDleteBtn={true} onClose={handleToastClose} />}
        </React.Fragment>
    )
}

export default CreatePurchaseBillForm;
