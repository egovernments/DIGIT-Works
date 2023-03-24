import React, { useMemo, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Header, FormComposer } from '@egovernments/digit-ui-react-components';

const navConfig =  [{
    name:"Wage_Seeker_Details",
    code:"WAGE_SEEKER_DETAILS"
}]

/*
search Wageseeker, Inidvidual API to get existing data
populate existing data dynamically store it in session storage and clear it on update success/failure/home page
Enable Save button if anything is updated
On click of save call Update WageSeeker and Update Bank account details API, if both success then redirect to Success Page else redorect to Error page
*/

const ModifyWageSeekerForm = ({createWageSeekerConfig, sessionFormData, setSessionFormData, clearSessionFormData}) => {
    const {t} = useTranslation();
    const stateTenant = Digit.ULBService.getStateId();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)

    const [selectedCategory, setSelectedCategory] = useState('');
    const [selectedWard, setSelectedWard] = useState('')
    
    //Skill data
    const {isLoading: skillDataFetching, data: skillData } = Digit.Hooks.useCustomMDMS(
        stateTenant,
        "common-masters",
        [ { "name": "WageSeekerSkills" }],
        {
            select: (data) => {
                let skillCategories = []
                let skills = {}
                data?.["common-masters"]?.WageSeekerSkills?.forEach(item => {
                if(!item?.active) return
                const categoryCode = item?.code?.split('.')?.[0]
                const skillCode = item?.code?.split('.')?.[1]
                if(!skillCategories.includes(categoryCode)) skillCategories.push(categoryCode)
                if(skills[categoryCode]) {
                    skills[categoryCode].push({code: skillCode, name: `COMMON_MASTERS_SKILL_TYPE_${skillCode}`})
                } else {
                    skills[categoryCode] = [{code: skillCode, name: `COMMON_MASTERS_SKILL_TYPE_${skillCode}`}]
                }
                })
                skillCategories = skillCategories.map(item => ({code: item, name: `COMMON_MASTERS_SKILL_LEVEL_${item}`}))
                return {
                    skillCategories,
                    skills
                }
            }
        }
    );
    const filteredSkills = skillData?.skills[selectedCategory]

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
    console.log('wardsAndLocalities', wardsAndLocalities);
    console.log('selectedWard', selectedWard);

    const config = useMemo(
    () => Digit.Utils.preProcessMDMSConfig(t, createWageSeekerConfig, {
      updateDependent : [
        {
            key : "basicDetails_dateOfBirth",
            value : [new Date().toISOString().split("T")[0]]
        },
        {
            key : "skillDetails_skillCategory",
            value : [skillData?.skillCategories]
        },
        {
            key : "skillDetails_skill",
            value : [filteredSkills]
        },
        {
            key : 'locDetails_city',
            value : [ULBOptions]
        },
        {
            key : 'locDetails_ward',
            value : [wardsAndLocalities?.wards]
        },
        {
            key : 'locDetails_locality',
            value : [filteredLocalities]
        }
      ]
    }),
    [skillData, filteredSkills, wardsAndLocalities, filteredLocalities, ULBOptions]);
  
    console.log('Converted config', config);

    const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
        if (!_.isEqual(sessionFormData, formData)) {
            const difference = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));
            if(formData.skillDetails_skillCategory) {
                setSelectedCategory(formData?.skillDetails_skillCategory?.code)
            }
            if (difference?.skillDetails_skillCategory) {
                setValue("skillDetails_skill", '');
            }
            if(formData.locDetails_ward) {
                setSelectedWard(formData?.locDetails_ward?.code)
            }
            if (difference?.locDetails_ward) {
                setValue("locDetails_locality", '');
            }
            setSessionFormData({ ...sessionFormData, ...formData });
        }
    }

    const onSubmit = (data) => {
        console.log('DATA', data);
    }

    return (
        <React.Fragment>
            <FormComposer
                label={"Save"}
                config={config?.form}
                onSubmit={onSubmit}
                submitInForm={false}
                fieldStyle={{ marginRight: 0 }}
                inline={false}
                className="form-no-margin"
                defaultValues={config?.defaultValues}
                showWrapperContainers={false}
                isDescriptionBold={false}
                showNavs={config?.metaData?.showNavs}
                horizontalNavConfig={navConfig}
                noBreakLine={true}
                showFormInNav={true}
                showMultipleCardsWithoutNavs={false}
                showMultipleCardsInNavs={false}
                onFormValueChange={onFormValueChange}
                cardClassName = "mukta-header-card"
            />
        </React.Fragment>
    )
}

export default ModifyWageSeekerForm;