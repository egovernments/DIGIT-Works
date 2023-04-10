import React, { useEffect, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Header, Loader, Toast } from '@egovernments/digit-ui-react-components';
import CreateOrganizationForm from './CreateOrganizationForm';
import { createOrganizationConfigMUKTA } from '../../../../configs/createOrganizationConfigMUKTA';
import { updateOrganisationFormDefaultValues } from '../../../../utils/index'

const CreateOrganisation = () => {
    const {t} = useTranslation();

    const [showDataError, setShowDataError] = useState(null)
    const [isFormReady, setIsFormReady] = useState(false);

    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    
    const { isLoading, data: configs } = Digit.Hooks.useCustomMDMS(
        stateTenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "CreateOrganizationConfig"
            }
        ],
        {
            select: (data) => {
                return data?.[Digit.Utils.getConfigModuleName()]?.CreateOrganizationConfig[0];
            },
        }
    );
    

    //For local config
    //const configs = createOrganizationConfigMUKTA?.CreateOrganizationConfig?.[0]

    const ULB = Digit.Utils.locale.getCityLocale(tenantId)
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    const { orgId } = Digit.Hooks.useQueryParams()
    const isModify = orgId ? true : false;

    //Call Search Wage Seeker
    const payload = {
        SearchCriteria: { orgNumber: orgId }
    }
    const {isLoading: orgDataFetching, data: orgData, isError, isSuccess, error} = Digit.Hooks.organisation.useOrganisationDetails({tenantId, data: payload, config:{
        enabled: isModify,
        cacheTime:0
    }})

    useEffect(() => {
        if(isError) {
            setShowDataError(true)
        }
    }, [error])

    const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;

    useEffect(() => {
        if(sessionFormData?.basicDetails_orgId !== orgId) {
          clearSessionFormData();
        }
    },[])

    useEffect(() => {
        if(configs && !orgDataFetching) {
            updateOrganisationFormDefaultValues({ configs, isModify, sessionFormData, setSessionFormData, orgData, tenantId, ULBOptions, setIsFormReady})
        }
      },[configs, orgDataFetching]);
    
    if(isLoading || orgDataFetching) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{fontSize: "32px"}}>{isModify ? t("MASTERS_MODIFY_VENDOR_ORG") : t("ACTION_TEST_MASTERS_CREATE_ORGANISATION")}</Header>
            {
                showDataError === null && isFormReady && (
                    <CreateOrganizationForm 
                        createOrganizationConfig={configs} 
                        sessionFormData={sessionFormData} 
                        setSessionFormData={setSessionFormData} 
                        clearSessionFormData={clearSessionFormData}
                        isModify={isModify}
                        orgDataFromAPI={orgData}
                        >  
                    </CreateOrganizationForm>
                )
            }
            {
                showDataError && <Toast error={true} label={t("COMMON_ERROR_FETCHING_ORG_DETAILS")} isDleteBtn={true} onClose={() => setShowDataError(false)} />
            }
        </React.Fragment>
    )
}

export default CreateOrganisation;