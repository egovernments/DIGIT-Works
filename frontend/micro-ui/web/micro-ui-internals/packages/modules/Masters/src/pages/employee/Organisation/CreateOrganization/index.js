import React, { useEffect, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Header } from '@egovernments/digit-ui-react-components';
import { createOrganizationConfigMUKTA } from '../../../../configs/createOrganizationConfigMUKTA';
import CreateOrganizationForm from './CreateOrganizationForm';

const CreateOrganisation = () => {
    const {t} = useTranslation();

    const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;

    return (
        <React.Fragment>
            <Header styles={{fontSize: "32px"}}>{t("ACTION_TEST_MASTERS_CREATE_ORGANISATION")}</Header>
            <CreateOrganizationForm createOrganizationConfig={createOrganizationConfigMUKTA} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></CreateOrganizationForm>
        </React.Fragment>
    )
}

export default CreateOrganisation;