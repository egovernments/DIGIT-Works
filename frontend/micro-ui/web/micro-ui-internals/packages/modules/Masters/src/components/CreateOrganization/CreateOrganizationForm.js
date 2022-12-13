import React, { useEffect, useState } from 'react'
import { createOrganizationConfig } from '../../../../Masters/src/configs/createOrganizationConfig'
import { FormComposer } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'
import { useHistory } from 'react-router-dom'

const CreateOrganizationForm = ({setCreateOrgStatus}) => {
    const { t } = useTranslation();

    const userInfo = Digit.UserService.getUser();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.pt.getCityLocale(tenantId);
    const city = userInfo && userInfo?.info?.permanentCity;
    
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    let districtOptions = []
    districtOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    const defaultValues = {
      'ulb': ULBOptions[0],
      'district': ULBOptions[0]
    }
  
    const config = createOrganizationConfig();

    const onSubmit = (data) => {
        //TODO: based on API response, pass as true/false
        setCreateOrgStatus(true)
    }

    return (
        <React.Fragment>
        <FormComposer
            heading={""}
            label={config.label.submit}
            config={config.form}
            onSubmit={onSubmit}
            fieldStyle={{ fontWeight: '600' }}
            noBreakLine={true}
            sectionHeadStyle={{marginTop: '1rem', marginBottom: '2rem'}}
            defaultValues={defaultValues}
        /> 
        </React.Fragment>
    )
}

export default CreateOrganizationForm;