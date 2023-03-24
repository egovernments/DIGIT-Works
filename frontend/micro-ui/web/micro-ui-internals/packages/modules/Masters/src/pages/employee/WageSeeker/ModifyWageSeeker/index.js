import React, { useMemo, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Header, FormComposer } from '@egovernments/digit-ui-react-components';
import { CreateWageSeekerConfig } from '../../../../configs/CreateWageSeekerConfig';
import ModifyWageSeekerForm from './ModifyWageSeekerForm';

const ModifyWageSeeker = () => {
    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();

    /*
    const { isLoading, data : configs} = Digit.Hooks.useCustomMDMS(
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
    */
    const createConfig = CreateWageSeekerConfig?.CreateWageSeekerConfig?.[0]
    console.log('MDMS Config', createConfig);

    const wageSeekerSession = Digit.Hooks.useSessionStorage("WAGE_SEEKER_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = wageSeekerSession;

    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{'Modify Wage Seeker'}</Header>
            <ModifyWageSeekerForm createWageSeekerConfig={createConfig} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></ModifyWageSeekerForm>
        </React.Fragment>
    )
}

export default ModifyWageSeeker;