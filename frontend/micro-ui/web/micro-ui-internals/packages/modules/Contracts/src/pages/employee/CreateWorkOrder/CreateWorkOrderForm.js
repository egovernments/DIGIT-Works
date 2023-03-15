import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import _ from "lodash";
import { createWorkOrderUtils } from "../../../../utils/createWorkOrderUtils";

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

const CreateWorkOrderForm = ({createWorkOrderConfig, sessionFormData, setSessionFormData, clearSessionFormData, tenantId, estimate, project}) => {
    const {t} = useTranslation();
    const [selectedOfficerInCharge, setSelectedOfficerInCharge] = useState([]);

    const fetchOfficerInChargeDesignation = (data) => {
        return data?.assignments?.filter(assignment=>assignment?.isCurrentAssignment)?.[0]?.designation;
    }

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

            if(formData.nameOfOfficerInCharge) {
                setValue("designationOfOfficerInCharge", fetchOfficerInChargeDesignation(formData.nameOfOfficerInCharge?.data));
            }

            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const { mutate: CreateWOMutation } = Digit.Hooks.contracts.useCreateWO();


    const onSubmit = async (data) => {
        const payload = createWorkOrderUtils({tenantId, estimate, project, data});
        
        await CreateWOMutation(payload, {
            onError: async (error, variables) => {
                console.log("RESPONSE-->",error?.response?.data?.Errors);
            },
            onSuccess: async (responseData, variables) => {
                console.log(responseData);
            },
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
        </React.Fragment>
    )
}

export default CreateWorkOrderForm;