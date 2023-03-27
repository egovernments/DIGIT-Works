import React, { useMemo, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Header, Loader } from '@egovernments/digit-ui-react-components';
import { CreateWageSeekerConfig } from '../../../../configs/CreateWageSeekerConfig';
import ModifyWageSeekerForm from './ModifyWageSeekerForm';

const ModifyWageSeeker = () => {
    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        stateTenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "CreateWageSeekerConfig"
            }
        ],
        {
            select: (data) => {
                return data?.[Digit.Utils.getConfigModuleName()]?.CreateWageSeekerConfig[0];
            },
        }
    );
    console.log('MDMS Config', data, Digit.Utils.getConfigModuleName());
    //const createConfig = CreateWageSeekerConfig?.CreateWageSeekerConfig?.[0]
    
    const wageSeekerSession = Digit.Hooks.useSessionStorage("WAGE_SEEKER_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = wageSeekerSession;

    // if(isLoading) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t("MASTERS_MODIFY_WAGESEEKER")}</Header>
            <ModifyWageSeekerForm createWageSeekerConfig={data} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></ModifyWageSeekerForm>
        </React.Fragment>
    )
}

export default ModifyWageSeeker;