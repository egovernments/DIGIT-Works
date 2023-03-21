import React, {useEffect, useMemo, useState} from 'react';
import { useTranslation } from "react-i18next";
import { FormComposer } from '@egovernments/digit-ui-react-components';

const navConfig =  [
    {
        name:"location_details",
        code:"ES_COMMON_LOCATION_DETAILS",
    },
    {
        name:"contact_Details",
        code:"ES_COMMON_CONTACT_DETAILS",
    },
    {
        name:"financial_Details",
        code:"MASTERS_FINANCIAL_DETAILS",
    }
];

const CreateOrganizationForm = ({ createOrganizationConfig, sessionFormData, setSessionFormData, clearSessionFormData }) => {
    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const [selectedWard, setSelectedWard] = useState('')
    const [selectedOrg, setSelectedOrg] = useState('')
    
    //location data
    const ULB = Digit.Utils.locale.getCityLocale(tenantId);
    let ULBOptions = []
    ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });

    const { isLoading, data : wardsAndLocalities } = Digit.Hooks.useLocation(
      tenantId, 'Ward',
      {
          select: (data) => {
              const wards = []
              const localities = {}
              data?.TenantBoundary[0]?.boundary.forEach((item) => {
                  localities[item?.code] = item?.children.map(item => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}`, label : item?.label }))
                  wards.push({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
              });
             return {
                  wards, localities
             }
          }
      });
    const filteredLocalities = wardsAndLocalities?.localities[selectedWard];

    //org data 
    const {isLoading: orgDataFetching, data: orgData } = Digit.Hooks.useCustomMDMS(
        stateTenant,
        "common-masters",
        [ { "name": "OrgType" }, { name: "OrgFunctionCategory" }],
        {
          select: (data) => {
            let orgTypes = []
            let orgSubTypes = {}
            let orgFunCategories = {}
            data?.["common-masters"]?.OrgType?.forEach(item => {
              if(!item?.active) return
              orgSubTypes[item?.code] = item?.subType.map(item => ({ ...item, code: item?.code, name: `COMMON_MASTERS_SUBORG_${item?.code}` }))
              orgTypes.push({...item, code: item?.code, name: `COMMON_MASTERS_ORG_${item?.code}`})
            })
            data?.["common-masters"]?.OrgFunctionCategory?.forEach(item => {
                if(!item?.active) return
                orgFunCategories[item?.code] = item?.subType.map(item => ({ ...item, code: item?.code, name: `COMMON_MASTERS_FUNCATEGORY_${item?.code}` }))
              })
            return {
                orgTypes,
                orgSubTypes,
                orgFunCategories
            }
          },
        }
      );
    const filteredOrgSubTypes = orgData?.orgSubTypes[selectedOrg]
    const filteredOrgFunCategories = orgData?.orgFunCategories[selectedOrg]

    const config = useMemo(
        () => {
            const defaultValues = {
                'locDetails_city': ULBOptions[0],
            }
            const conf = createOrganizationConfig({defaultValues, orgType:orgData, orgSubType:filteredOrgSubTypes, ULBOptions, wards:wardsAndLocalities, localities: filteredLocalities, funCategories: filteredOrgFunCategories})
            return conf?.CreateOrganisationConfig?.[0]
        },
        [wardsAndLocalities, filteredLocalities, ULBOptions, orgData, filteredOrgSubTypes ]);

   
    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));
            if(formData.locDetails_ward) {
                setSelectedWard(formData?.locDetails_ward?.code)
            }
            if (difference?.locDetails_ward) {
                setValue("locDetails_locality", '');
            }
            if(formData.funDetails_orgType) {
                setSelectedOrg(formData?.funDetails_orgType?.code)
            }
            if (difference?.funDetails_orgType) {
                setValue("funDetails_orgSubType", '');
                setValue("funDetails_category", '');
            }
            setSessionFormData({ ...sessionFormData, ...formData });
          }
    }

    const onSubmit = (data) => {
        console.log('FORM Data', data);
    }   

    return (
        <React.Fragment>
            <FormComposer
                label={t("MASTERS_CREATE_ORGANISATION")}
                config={config?.form}
                onSubmit={onSubmit}
                submitInForm={false}
                fieldStyle={{ marginRight: 0 }}
                inline={false}
                className="form-no-margin"
                defaultValues={config?.defaultValues}
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
        </React.Fragment>
    )
}

export default CreateOrganizationForm;