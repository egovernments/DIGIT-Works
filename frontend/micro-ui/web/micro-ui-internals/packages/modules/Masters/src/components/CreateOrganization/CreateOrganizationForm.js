import React, { useEffect, useState } from 'react'
import _ from "lodash";
import { createOrganizationConfig } from '../../../../Masters/src/configs/createOrganizationConfig'
import { FormComposer } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'

const CreateOrganizationForm = ({setCreateOrgStatus}) => {
    const { t } = useTranslation();

    const [selectedWard, setSelectedWard] = useState('')
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

    // useEffect(() => {
    //     console.log('$$$')
    // }, [selectedWard])

    const onFormValueChange = (setValue, formData, formState, reset) => {
        if(formData.ward) {
            setSelectedWard(formData?.ward?.code)
        }
    }

    const config = createOrganizationConfig(selectedWard);

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
            onFormValueChange={onFormValueChange}
        /> 
        </React.Fragment>
    )
}

export default CreateOrganizationForm;