import { FormComposer, Header, Toast, WorkflowModal } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { createWorkOrderUtils } from "../../../../utils/createWorkOrderUtils";
import { useHistory } from "react-router-dom";
import getWOModalConfig from "../../../configs/getWOModalConfig";

const navConfig =  [
    {
        name:"WO_Details",
        code:"COMMON_WO_DETAILS",
    },
    {
        name:"Terms_And_Conditions",
        code:"COMMON_TERMS_&_CONDITIONS",
    }
];

const CreateWorkOrderForm = ({createWorkOrderConfig, sessionFormData, setSessionFormData, clearSessionFormData, tenantId, estimate, project, preProcessData, isModify, contractID, lineItemID}) => {
    const {t} = useTranslation();
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const history = useHistory();
    const [showModal, setShowModal] = useState(false);
    const [createWOModalConfig, setCreateWOModalConfig] = useState({});
    const rolesForThisAction = "WORK_ORDER_VERIFIER" //hardcoded for now
    const [approvers, setApprovers] = useState([]);
    const [selectedApprover, setSelectedApprover] = useState({});
    const [inputFormdata, setInputFormData] = useState([]);
    const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ roles: rolesForThisAction, isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled:true });
    employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")

    useEffect(() => {
        setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA") : [])
        //TODO: if name-designation is req
        // let refactoredAppoversNames = [];
        // if(employeeDatav1?.Employees?.length > 0) {
        //     refactoredAppoversNames = employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA").map((emp=>{
        //         let designation = t(`COMMON_MASTERS_DESIGNATION_${emp?.assignments?.[0]?.designation}`);
        //         return {...emp, name_designation : `${emp?.nameOfEmp} - ${designation}`}
        //     }))
        // }else {
        //     refactoredAppoversNames = [];
        // }

        // setApprovers(refactoredAppoversNames);
    }, [employeeDatav1])

    const fetchOfficerInChargeDesignation = (data) => {
        return data?.assignments?.filter(assignment=>assignment?.isCurrentAssignment)?.[0]?.designation;
    }

    createWorkOrderConfig = useMemo(
        () => Digit.Utils.preProcessMDMSConfig(t, createWorkOrderConfig, {
          updateDependent : [
            {
                key : 'labourAndMaterialAnalysis',
                value : [preProcessData?.documents]
            },
            {
                key : 'nameOfOfficerInCharge',
                value : [preProcessData?.officerInCharge]
            },
            {
                key : 'nameOfCBO',
                value : [preProcessData?.nameOfCBO]
            },
            {
                key : 'basicDetails_workOrdernumber',
                value : [!isModify ? "none" : "flex"]
            },
          ]
        }),
    [preProcessData?.documents, preProcessData?.officerInCharge, preProcessData?.nameOfCBO]);

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        console.log("formData", formData);
        console.log("session", sessionFormData);
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

            if(formData.nameOfOfficerInCharge) {
                setValue("designationOfOfficerInCharge", t(`COMMON_MASTERS_DESIGNATION_${fetchOfficerInChargeDesignation(formData.nameOfOfficerInCharge?.data)}`));
            }

            if(formData.nameOfCBO) {
                setValue("cboID", formData.nameOfCBO?.applicationNumber);
            }

            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const handleToastClose = () => {
        setToast({show : false, label : "", error : false});
    }

    const { mutate: CreateWOMutation } = Digit.Hooks.contracts.useCreateWO();
    const { mutate: UpdateWOMutation } = Digit.Hooks.contracts.useUpdateWO();

    //remove Toast after 3s
    useEffect(()=>{
        if(toast?.show) {
        setTimeout(()=>{
            handleToastClose();
        },3000);
        }
    },[toast?.show]);

    useEffect(() => {
        setCreateWOModalConfig(
            getWOModalConfig({
                t,
                approvers,
                selectedApprover,
                setSelectedApprover,
                approverLoading
            })
        )
    }, [approvers]);

    const onFormSubmit = (_data) => {
        setInputFormData(_data);
        setShowModal(true);
    }

    const handleResponseForUpdate = async(payload) => {
        await UpdateWOMutation(payload, {
            onError: async (error, variables) => {
                if(error?.response?.data?.Errors?.[0]?.code === "INVALID_ESTIMATELINEITEMID") {
                    setToast(()=>({show : true, label : t("ESTIMATE_ALREADY_ASSOCIATED_TO_OTHER_CONTRACT"), error : true}));
                }else {
                    setToast(()=>({show : true, label : t(error?.response?.data?.Errors?.[0]?.code), error : true}));
                }
            },
            onSuccess: async (responseData, variables) => {
                if(responseData?.ResponseInfo?.Errors) {
                        setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_CONTRACT"), error : true}));
                    }else if(responseData?.ResponseInfo?.status){
                        sendDataToResponsePage(responseData?.contracts?.[0]?.contractNumber, responseData, true);
                        clearSessionFormData();
                    }else{
                        setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_CONTRACT"), error : true}));
                    }
            },
        });
    }

    const handleResponseForCreateWO = async(payload) => {
        await CreateWOMutation(payload, {
            onError: async (error, variables) => {
                if(error?.response?.data?.Errors?.[0]?.code === "INVALID_ESTIMATELINEITEMID") {
                    setToast(()=>({show : true, label : t("ESTIMATE_ALREADY_ASSOCIATED_TO_OTHER_CONTRACT"), error : true}));
                }else {
                    setToast(()=>({show : true, label : t(error?.response?.data?.Errors?.[0]?.code), error : true}));
                }
            },
            onSuccess: async (responseData, variables) => {
                if(responseData?.ResponseInfo?.Errors) {
                        setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_CONTRACT"), error : true}));
                    }else if(responseData?.ResponseInfo?.status){
                        sendDataToResponsePage(responseData?.contracts?.[0]?.contractNumber, responseData, true);
                        clearSessionFormData();
                    }else{
                        setToast(()=>({show : true, label : t("WORKS_ERROR_CREATING_CONTRACT"), error : true}));
                    }
            },
        });
    }

    const modifyParams = {
        contractID,
        lineItemID
    }

    const onModalSubmit = async (modalData) => {
        const payload = createWorkOrderUtils({tenantId, estimate, project, inputFormdata, selectedApprover, modalData, createWorkOrderConfig, modifyParams});
        if(isModify) {
            handleResponseForUpdate(payload);
        }else {
            handleResponseForCreateWO(payload);
        }
    }

    const sendDataToResponsePage = (contractNumber, responseData, isSuccess) => {
        let queryString = "";
        if(responseData) {
          queryString = contractNumber;
        }
        history.push({
          pathname: `/${window?.contextPath}/employee/contracts/create-contract-response`,
          search: `?contractNumber=${queryString}&tenantId=${tenantId}&isSuccess=${isSuccess}`,
        }); 
    }

    return (
        <React.Fragment>
            {
                showModal && 
                <WorkflowModal
                    closeModal={() => setShowModal(false)}
                    onSubmit={onModalSubmit}
                    config={createWOModalConfig}
                />
            }
            <Header styles={{fontSize: "32px"}}>{isModify ? t("COMMON_MODIFY_WO") : t("ACTION_TEST_CREATE_WO")}</Header>
                {
                    createWorkOrderConfig && (
                    <FormComposer
                        label={isModify ? "COMMON_MODIFY_WO" : "ACTION_TEST_CREATE_WO"}
                        config={createWorkOrderConfig?.form?.map((config) => {
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
                        showNavs={createWorkOrderConfig?.metaData?.showNavs}
                        showFormInNav={true}
                        showMultipleCardsWithoutNavs={false}
                        showMultipleCardsInNavs={false}
                        horizontalNavConfig={navConfig}
                        onFormValueChange={onFormValueChange}
                        cardClassName = "mukta-header-card"
                    />
                    )
                }
               {toast?.show && <Toast error={toast?.error} label={toast?.label} isDleteBtn={true} onClose={handleToastClose} />}
        </React.Fragment>
    )
}

export default CreateWorkOrderForm;