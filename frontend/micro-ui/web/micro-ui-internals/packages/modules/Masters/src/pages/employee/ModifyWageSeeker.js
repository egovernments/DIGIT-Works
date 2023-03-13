import React from 'react'
import { useTranslation } from "react-i18next";
import { Header, FormComposer } from '@egovernments/digit-ui-react-components';
import { CreateWageSeekerConfig } from '../../configs/CreateWageSeekerConfig'

const ModifyWageSeeker = () => {
  const {t} = useTranslation();
  const stateTenant = Digit.ULBService.getStateId();
  const tenantId = Digit.ULBService.getCurrentTenantId();

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
  const configs = CreateWageSeekerConfig?.CreateWageSeekerConfig?.[0]

  console.log('@@@', configs);

  const onSubmit = (data) => {
    console.log('DATA', data);
  }

  return (
    <React.Fragment>
      <Header styles={{ fontSize: "32px" }}>{'Modify Wage Seeker'}</Header>
      <FormComposer
        label={"Save"}
        config={configs?.form}
        onSubmit={onSubmit}
        submitInForm={false}
        fieldStyle={{ marginRight: 0 }}
        inline={false}
        className="form-no-margin"
        defaultValues={configs?.defaultValues}
        showWrapperContainers={false}
        isDescriptionBold={false}
        showNavs={configs?.metaData?.showNavs}
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