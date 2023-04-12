import { FormComposer, Header, Toast, WorkflowModal } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { useHistory } from "react-router-dom";
import getBillModalConfig from "../../../configs/getBillModalConfig";

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
    tenantId, 
    contract,  
    preProcessData,
    isModify
}) => {
    const {t} = useTranslation();
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const history = useHistory();
    const [showModal, setShowModal] = useState(false);
    const [createPBModalConfig, setCreatePBModalConfig] = useState({});
    const rolesForThisAction = "BILL_CREATOR" //hardcoded for now
    const [inputFormdata, setInputFormData] = useState([]);
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
                  key : 'nameOfVendor',
                  value : [preProcessData?.nameOfVendor]
              },
              {
                key : 'basicDetails_purchaseBillNumber',
                value : [!isModify ? "none" : "flex"]
              },
            ]
          }),
      [preProcessData?.nameOfVendor]);

    //session storage rendering infinitely
    // const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    //     if (!_.isEqual(sessionFormData, formData)) {
    //         const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

    //         if(formData.nameOfVender) {
    //             setValue("vendorID", formData.nameOfVendor?.orgNumber);
    //         }

    //         setSessionFormData({ ...sessionFormData, ...formData });
    //     }
    // }

    const handleToastClose = () => {
        setToast({show : false, label : "", error : false});
    }

    //const { mutate: CreatePBMutation } = Digit.Hooks.bills.useCreatePB();
    //const { mutate: UpdatePBMutation } = Digit.Hooks.bills.useUpdatePB();

    //remove Toast after 3s
    useEffect(()=>{
        if(toast?.show) {
        setTimeout(()=>{
            handleToastClose();
        },3000);
        }
    },[toast?.show]);

    // useEffect(() => {
    //     setCreatePBModalConfig(
    //         getBillModalConfig({
    //             t,
    //             approvers,
    //             selectedApprover,
    //             setSelectedApprover,
    //             approverLoading
    //         })
    //     )
    // }, [approvers]);

    const onFormSubmit = (_data) => {
        console.log("Form data :", _data);
        setInputFormData(_data);
        setShowModal(true);
    }

    // const modifyParams = {
    //         contractID,
    //         contractNumber,
    //         lineItems,
    //         contractAuditDetails,
    //         updateAction : isModify ? "EDIT" : "",
    // }
    

    // const sendDataToResponsePage = (billNumber, isSuccess, message, showID) => {
    //     history.push({
    //       pathname: `/${window?.contextPath}/employee/expenditure/create-purchase-bill-response`,
    //       search: `?billNumber=${billNumber}&tenantId=${tenantId}&isSuccess=${isSuccess}`,
    //       state : {
    //         message : message,
    //         showID : showID
    //       }
    //     }); 
    // }

    // const handleResponseForUpdatePB = async(payload) => {
    //     await UpdatePBMutation(payload, {
    //         onError: async (error, variables) => {
    //             sendDataToResponsePage(billNumber, false, "PURCHASE_BILL_MODIFICATION_FAILURE", true); //change here based on response data
    //         },
    //         onSuccess: async (responseData, variables) => {
    //             if(responseData?.ResponseInfo?.Errors) {
    //                     setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PURCHASE_BILL"), error : true}));
    //                 }else if(responseData?.ResponseInfo?.status){
    //                     sendDataToResponsePage(billNumber, true, "PURCHASE_BILL_MODIFIED", true); //change here based on response data
    //                     clearSessionFormData();
    //                 }else{
    //                     setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PURCHASE_BILL"), error : true}));
    //                 }
    //         },
    //     });
    // }

    // const handleResponseForCreateWO = async(payload) => {
    //     await CreatePBMutation(payload, {
    //         onError: async (error, variables) => {
    //             sendDataToResponsePage("", false, "PURCHASE_BILL_FAILED", false);
    //         },
    //         onSuccess: async (responseData, variables) => {
    //             if(responseData?.ResponseInfo?.Errors) {
    //                     setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PURCHASE_BILL"), error : true}));
    //                 }else if(responseData?.ResponseInfo?.status){
    //                     sendDataToResponsePage(responseData?.bills?.[0]?.billNumber, true, "PURCHASE_BILL_CREATED_FORWARDED", true); //change here based on response data
    //                     clearSessionFormData();
    //                 }else{
    //                     setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_PURCHASE_BILL"), error : true}));
    //                 }
    //         },
    //     });
    // }

    // const onModalSubmit = async (modalData) => {
    //     const payload = createPurchaseBillUtils({tenantId, contract, inputFormdata, selectedApprover, modalData, createPurchaseBillConfig, modifyParams});
    //     if(isModify) {
    //         handleResponseForUpdatePB(payload);
    //     }else {
    //         handleResponseForCreateWO(payload);
    //     }
    // }

    return (
        <React.Fragment>
            {
                showModal && 
                <WorkflowModal
                    closeModal={() => setShowModal(false)}
                    //onSubmit={onModalSubmit}
                    onSubmit={[]}
                    config={createPBModalConfig}
                />
            }
            <Header styles={{fontSize: "32px"}}>{isModify ? t("COMMON_MODIFY_PB") : t("ACTION_TEST_CREATE_PB")}</Header>
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
                        //onFormValueChange={onFormValueChange}
                        cardClassName = "mukta-header-card"
                    />)
                }
               {toast?.show && <Toast error={toast?.error} label={toast?.label} isDleteBtn={true} onClose={handleToastClose} />}
        </React.Fragment>
    )
}

export default CreatePurchaseBillForm;
