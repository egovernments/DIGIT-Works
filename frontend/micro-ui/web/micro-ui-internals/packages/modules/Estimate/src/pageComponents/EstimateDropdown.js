import { Dropdown, Loader } from '@egovernments/digit-ui-react-components';
import React from 'react'
import { useTranslation } from 'react-i18next';

const EstimateDropdown = (props) => {
    const { t } = useTranslation();
    const tenantId = Digit.ULBService.getCurrentTenantId()

    const requestCriteria = {
        url: '/mdms-v2/v2/_search',
        body: {
            MdmsCriteria: {
                tenantId: tenantId,
                filters: {},
                schemaCode: props?.schemaCode,
                limit: 50,
                offset: 0
            },
        },
        changeQueryName:props?.schemaCode
    };
    const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);

    if(isLoading){
        return <Loader />
    }
        const filteredCodes = data?.mdms
    .filter(item => item.data && item.data.code) // Ensure 'data' and 'code' exist
    .map(item => item.data.code);
  
    return (
        <div>
            <div>
                {props?.label}
            </div>
            <Dropdown t = {t} select={(e) => {
                console.log(e)
                props?.setStateData({
                    ...props?.stateData,
                    [props?.type]: e
                })
            }} option={
                filteredCodes
            } selected={
                props?.stateData[props?.type]
            } />
        </div>
  )
}

export default EstimateDropdown