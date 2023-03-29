import { FormComposer, Header, Toast } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { createWorkOrderUtils } from "../../../../utils/createWorkOrderUtils";
import { useHistory } from "react-router-dom";

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

const CreateWorkOrderForm = ({createWorkOrderConfig, sessionFormData, setSessionFormData, clearSessionFormData, tenantId, estimate, project, preProcessData}) => {
    const {t} = useTranslation();
    const [selectedOfficerInCharge, setSelectedOfficerInCharge] = useState([]);
    const [toast, setToast] = useState({show : false, label : "", error : false});
    const history = useHistory();

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
          ]
        }),
    [preProcessData?.documents, preProcessData?.officerInCharge, preProcessData?.nameOfCBO]);

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
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

    //remove Toast after 3s
    useEffect(()=>{
        if(toast?.show) {
        setTimeout(()=>{
            handleToastClose();
        },3000);
        }
    },[toast?.show]);

    const onSubmit = async (data) => {
        const payload = createWorkOrderUtils({tenantId, estimate, project, data});
        
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
            <Header styles={{fontSize: "32px"}}>{t("ACTION_TEST_CREATE_WO")}</Header>
                {
                    createWorkOrderConfig && (
                    <FormComposer
                        label={"ACTION_TEST_CREATE_WO"}
                        config={createWorkOrderConfig?.form?.map((config) => {
                        return {
                            ...config,
                            body: config?.body.filter((a) => !a.hideInEmployee),
                        };
                        })}
                        onSubmit={onSubmit}
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