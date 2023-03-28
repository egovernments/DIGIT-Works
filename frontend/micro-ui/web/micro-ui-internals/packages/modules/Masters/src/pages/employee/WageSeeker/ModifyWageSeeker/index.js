import React, { useMemo, useState, useEffect } from 'react'
import { useTranslation } from "react-i18next";
import { Header, Loader, Toast } from '@egovernments/digit-ui-react-components';
import { CreateWageSeekerConfig } from '../../../../configs/CreateWageSeekerConfig';
import ModifyWageSeekerForm from './ModifyWageSeekerForm';
import { updateWageSeekerFormDefaultValues } from '../../../../utils';

const ModifyWageSeeker = () => {
    const {t} = useTranslation();
    const [showDataError, setShowDataError] = useState(null)
    const [isFormReady, setIsFormReady] = useState(false);

    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)
    /*
    const { isLoading, data: configs } = Digit.Hooks.useCustomMDMS(
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
    */
    const configs = CreateWageSeekerConfig?.CreateWageSeekerConfig?.[0]

    const ULB = Digit.Utils.locale.getCityLocale(tenantId)
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    //const { individualId } = Digit.Hooks.useQueryParams()
    const individualId = 'IND-2023-03-27-001462'//'IND-2023-03-27-001461'
    const isModify = false //individualId ? true : false;

    //Call Search Wage Seeker
    const payload = {
        Individual: { individualId }
    }
    const searchParams = { offset: 0, limit: 100 }
      
    const {isLoading: wageSeekerDataFetching, data: wageSeekerData, isError, isSuccess, error} = Digit.Hooks.wageSeeker.useWageSeekerDetails({tenantId, data: payload, searchParams, config:{
        enabled: isModify,
        cacheTime:0
    }})

    useEffect(() => {
        if(isError) {
            setShowDataError(true)
        }
    }, [error])
    
    const wageSeekerSession = Digit.Hooks.useSessionStorage("WAGE_SEEKER_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = wageSeekerSession;

    useEffect(() => {
        if(configs && !wageSeekerDataFetching) {
            updateWageSeekerFormDefaultValues({ configs, isModify, sessionFormData, setSessionFormData, wageSeekerData, tenantId, headerLocale, ULBOptions})
            setIsFormReady(true)
        }
      },[configs, wageSeekerDataFetching]);

    //if(isLoading) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t("MASTERS_MODIFY_WAGESEEKER")}</Header>
            {
                showDataError === null && isFormReady && (
                    <ModifyWageSeekerForm 
                        createWageSeekerConfig={configs} 
                        sessionFormData={sessionFormData} 
                        setSessionFormData={setSessionFormData} 
                        clearSessionFormData={clearSessionFormData}
                        isModify={isModify}
                        wageSeekerDataFromAPI={wageSeekerData}
                        >  
                    </ModifyWageSeekerForm>
                )
            }
            {
                showDataError && <Toast error={true} label={t("COMMON_ERROR_FETCHING_WAGE_SEEKER_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
            }
        </React.Fragment>
    )
}

export default ModifyWageSeeker;