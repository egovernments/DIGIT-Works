import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useMemo } from "react";
import { useTranslation } from "react-i18next";

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

const CreateWorkOrderForm = ({createWorkOrderConfig, sessionFormData, setSessionFormData, clearSessionFormData}) => {
    const {t} = useTranslation();

    //Call Pre-Process here
    const config = useMemo(
        () => Digit.Utils.preProcessMDMSConfig(t, createWorkOrderConfig, {
          updateDependent : []
        }),
        []);

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        //handle formValue change here
    }

    const onSubmit = (_data) => {
        //handle submit here
        console.log(_data);
    }

    return (
        <React.Fragment>
            <Header styles={{fontSize: "32px"}}>{t("ACTION_TEST_CREATE_WO")}</Header>
                {
                    createWorkOrderConfig && (
                    <FormComposer
                        label={"ACTION_TEST_CREATE_WO"}
                        config={config?.form?.map((config) => {
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
                        showNavs={config?.metaData?.showNavs}
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