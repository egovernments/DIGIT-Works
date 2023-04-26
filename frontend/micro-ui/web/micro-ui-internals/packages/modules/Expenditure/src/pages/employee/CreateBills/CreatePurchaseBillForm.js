import { FormComposer, Header, Toast, WorkflowModal } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { useHistory } from "react-router-dom";
import { createBillPayload } from "../../../utils/createBillUtils";

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
    isModify
}) => {
    const {t} = useTranslation();
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const history = useHistory();

    const rolesForThisAction = "BILL_CREATOR" //hardcoded for now
    const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ roles: rolesForThisAction, isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled:true });
    employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")

    // useEffect(() => {
    //     setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA") : [])
    //     //TODO: if name-designation is req
    //     // let refactoredAppoversNames = [];
    //     // if(employeeDatav1?.Employees?.length > 0) {
    //     //     refactoredAppoversNames = employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA").map((emp=>{
    //     //         let designation = t(`COMMON_MASTERS_DESIGNATION_${emp?.assignments?.[0]?.designation}`);
    //     //         return {...emp, name_designation : `${emp?.nameOfEmp} - ${designation}`}
    //     //     }))
    //     // }else {
    //     //     refactoredAppoversNames = [];
    //     // }

    //     // setApprovers(refactoredAppoversNames);
    // }, [employeeDatav1])

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
                key : 'basicDetails_purchaseBillDate',
                value : [!isModify ? "none" : "flex"]
              },
              {
                key : 'billDetails_billDate',
                value : [new Date().toISOString().split("T")[0]]
              },
            ]
          }),
      [preProcessData?.nameOfVendor]);

    //session storage rendering infinitely
    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

            if(formData.invoiceDetails_vendor) {
                setValue("invoiceDetails_vendorId", formData.invoiceDetails_vendor?.orgNumber);
            }

            if(formData.invoiceDetails_materialCost && formData.invoiceDetails_gst) {
                setValue("billDetails_billAmt", parseInt(formData.invoiceDetails_materialCost)+parseInt(formData.invoiceDetails_gst));
            }

            if(formData.billDetails_billAmt) {
                let value = parseFloat(formData.invoiceDetails_materialCost)+ parseFloat(formData.invoiceDetails_gst);
                if(value > contract?.totalContractedAmount){
                    setValue("billDetails_billAmt", Digit.Utils.dss.formatterWithoutRound(value, "number"));
                    setError("billDetails_billAmt",{ type: "custom" }, { shouldFocus: true })
                }
                else{
                    setValue("billDetails_billAmt", Digit.Utils.dss.formatterWithoutRound(value, "number"));
                    clearErrors("billDetails_billAmt")
                }

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

    const onFormSubmit = (data) => {
        console.log("Form data :", data);
        const payload = createBillPayload(data, contract);
        console.log("Payload :", payload);
        
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
