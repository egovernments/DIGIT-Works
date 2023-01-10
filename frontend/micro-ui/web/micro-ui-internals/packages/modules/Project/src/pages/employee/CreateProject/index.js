import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { createProjectSectionConfig } from "../../../configs/createProjectConfig";

const whenHasProjectsHorizontalNavConfig =  [
  {
      name:"Project_Details",
      code:"WORKS_PROJECT_DETAILS",
  },
  {
      name:"Financial_Details",
      code:"WORKS_FINANCIAL_DETAILS",
  }
];

const whenHasSubProjectsHorizontalNavConfig =  [
  {
    name:"Project_Details",
    code:"WORKS_PROJECT_DETAILS",
  },
  {
      name:"Financial_Details",
      code:"WORKS_FINANCIAL_DETAILS",
  },
  {
      name: "Sub_Project_Details",
      code:"WORKS_SUB_PROJECT_DETAILS",
  }
];

const CreateProject = () => {
    const {t} = useTranslation();
    const [selectedProjectType, setSelectedProjectType] = useState("");
    const [navTypeConfig, setNavTypeConfig] = useState(whenHasProjectsHorizontalNavConfig);
    const [showNavs, setShowNavs] = useState(false);
    const [subTypeOfWorkOptions, setSubTypeOfWorkOptions] = useState([]);
    const [subSchemaOptions, setSubSchemaOptions] = useState([]);
    const [selectedWard, setSelectedWard] = useState('');
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const orgSession = Digit.Hooks.useSessionStorage("NEW_PROJECT_CREATE", {});
    const [sessionFormData, setSessionFormData, clearSessionFormData] = orgSession;
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);

    //clear session data on first init
    useEffect(()=>{
      clearSessionFormData();
    },[]);

    const { isLoading, data : wardsAndLocalities } = Digit.Hooks.useLocation(
      tenantId, 'Ward',
      {
          select: (data) => {
              const wards = []
              const localities = {}
              data?.TenantBoundary[0]?.boundary.forEach((item) => {
                  localities[item?.code] = item?.children.map(item => ({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` }))
                  wards.push({ code: item.code, name: item.name, i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
              });
             return {
                  wards, localities
             }
          }
      })
  
    const filteredLocalities = wardsAndLocalities?.localities[selectedWard];

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
        if(formData?.hasSubProjects) {
          setSelectedProjectType(formData?.hasSubProjects);
        } 
        if(formData?.typeOfWork) {
          setSubTypeOfWorkOptions(formData?.typeOfWork?.subTypes);
        } 
        if(formData?.scheme) {
          setSubSchemaOptions(formData?.scheme?.subSchemes);
        } 
    }
    const createProjectSectionFormConfig = createProjectSectionConfig(subTypeOfWorkOptions, subSchemaOptions, wardsAndLocalities, filteredLocalities);

    useEffect(()=>{
        if(selectedProjectType?.code === "COMMON_YES") {
          setNavTypeConfig(whenHasProjectsHorizontalNavConfig);
          setShowNavs(true);
        }else if(selectedProjectType?.code === "COMMON_NO") {
          setNavTypeConfig(whenHasSubProjectsHorizontalNavConfig);
          setShowNavs(true);
        }
    },[selectedProjectType]);

    const onSubmit = () => {}

    return (
        <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_CREATE_PROJECT")}</Header>
          </div>
          {
            createProjectSectionFormConfig?.form && (
              <FormComposer
                label={"WORKS_CREATE_PROJECT"}
                config={createProjectSectionFormConfig?.form.map((config) => {
                  return {
                    ...config,
                    body: config?.body.filter((a) => !a.hideInEmployee),
                  };
                })}
                onSubmit={onSubmit}
                submitInForm={false}
                fieldStyle={{ marginRight: 0 }}
                inline={false}
                className="card-no-margin"
                defaultValues={createProjectSectionFormConfig?.defaultValues}
                showWrapperContainers={false}
                isDescriptionBold={false}
                noBreakLine={true}
                showNavs={showNavs}
                showMultipleCardsWithoutNavs={false}
                showMultipleCardsInNavs={false}
                horizontalNavConfig={navTypeConfig}
                onFormValueChange={onFormValueChange}
            />
           )}
        </div>
      </React.Fragment>
    )
}

export default CreateProject;