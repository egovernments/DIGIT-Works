import React, { useMemo, useState } from 'react'
import { useTranslation } from "react-i18next";
import { Header, FormComposer } from '@egovernments/digit-ui-react-components';
import { CreateWageSeekerConfig } from '../../configs/CreateWageSeekerConfig'
import { set } from 'lodash';

const ModifyWageSeeker = () => {
  const {t} = useTranslation();
  const stateTenant = Digit.ULBService.getStateId();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  const [selectedCategory, setSelectedCategory] = useState('');
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

  //Get MDMS data
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
            skills[categoryCode].push({code: skillCode, name: `COMMON_MASTERS_${skillCode}`})
          } else {
            skills[categoryCode] = [{code: skillCode, name: `COMMON_MASTERS_${skillCode}`}]
          }
        })
        skillCategories = skillCategories.map(item => ({code: item, name: `COMMON_MASTERS_${item}`}))
        return {
          skillCategories,
          skills
        }
      },
    }
  );
  const filteredSkills = skillData?.skills[selectedCategory]

  const ULB = Digit.Utils.locale.getCityLocale(tenantId);
  let ULBOptions = []
  ULBOptions.push({code: tenantId, name: t(ULB),  i18nKey: ULB });
  
  const config = useMemo(
    () => Digit.Utils.preProcessMDMSConfig(t, createConfig, {
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
        }
      ]
    }),
    [skillData, filteredSkills]);
  
  console.log('Converted config', config);

  const onSubmit = (data) => {
    console.log('DATA', data);
  }

  return (
    <React.Fragment>
      <Header styles={{ fontSize: "32px" }}>{'Modify Wage Seeker'}</Header>
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
        horizontalNavConfig={[{
          name:"Project_Details",
          code:"WORKS_PROJECT_DETAILS",
        }]}
        noBreakLine={true}
        showFormInNav={true}
        showMultipleCardsWithoutNavs={false}
        showMultipleCardsInNavs={false}
        cardClassName = "mukta-header-card"
      />
    </React.Fragment>
  )
}

export default ModifyWageSeeker