import React, { useState } from 'react'
import _ from "lodash";
import { createOrganizationConfig } from '../../../../Masters/src/configs/createOrganizationConfig'
import { FormComposer } from '@egovernments/digit-ui-react-components'
import { useTranslation } from 'react-i18next'

const CreateOrganizationForm = ({setCreateOrgStatus, sessionFormData, setSessionFormData}) => {
    const { t } = useTranslation();

    const [selectedWard, setSelectedWard] = useState('')
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const ULB = Digit.Utils.pt.getCityLocale(tenantId);
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const { isLoading, data : wardsAndLocalities } = Digit.Hooks.useLocation(
        tenantId, 'Ward', 
        {
            select: (data) => {
                const wards = []
                const localities = {}
                data?.TenantBoundary[0]?.boundary.sort((a, b) => a.code.localeCompare(b.code)).forEach((item) => {
                    localities[item?.code] = item?.children.map(item => ({ code: item.code, name: t(`${headerLocale}_ADMIN_${item?.code}`), i18nKey: `${headerLocale}_ADMIN_${item?.code}` }))
                    wards.push({ code: item.code, name: t(`${headerLocale}_ADMIN_${item?.code}`), i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
                });
               return {
                    wards, localities
               }
            },
        },true)
    
    const filteredLocalities = isLoading ? [] : wardsAndLocalities?.localities[selectedWard]
    
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    let districtOptions = []
    districtOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB })

    const defaultValues = {
      'ulb': ULBOptions[0],
      'district': ULBOptions[0]
    }

    const onFormValueChange = (setValue, formData, formState) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));
            if(formData.ward) {
                setSelectedWard(formData?.ward?.code)
            }
            if (difference?.ward) {
                setValue("locality", '');
            }
            setSessionFormData({ ...sessionFormData, ...formData });
          }
    }

    const config = createOrganizationConfig(wardsAndLocalities, filteredLocalities);

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
            labelBold={true}
        /> 
        </React.Fragment>
    )
}

export default CreateOrganizationForm;