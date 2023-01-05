import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { createProjectSectionConfig } from "../../../configs/createProjectHeaderConfig";

const whenHasProjectsHorizontalNavConfig =  [
  {
      name:"Project_Details",
      code:"Project_Details",
  },
  {
      name:"Financial_Details",
      code:"Financial_Details",
  }
];

const whenHasSubProjectsHorizontalNavConfig =  [
  {
    name:"Project_Details",
    code:"Project_Details",
  },
  {
      name:"Financial_Details",
      code:"Financial_Details",
  },
  {
      name: "Sub_Project_Details",
      code:"Sub_Project_Details",
  }
];

const CreateProject = () => {
    const {t} = useTranslation();
    const [selectedProjectType, setSelectedProjectType] = useState("");
    const [navTypeConfig, setNavTypeConfig] = useState(whenHasProjectsHorizontalNavConfig);
    const [showNavs, setShowNavs] = useState(false);
    const onFormValueChange = (setValue, formData, formState) => {
        if(formData?.hasSubProjects) {
          setSelectedProjectType(formData?.hasSubProjects);
        }
    }

    const createProjectSectionFormConfig = createProjectSectionConfig();

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
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("MASTERS_CREATE_PROJECT")}</Header>
          </div>
          {
            createProjectSectionFormConfig?.form && (
              <FormComposer
                label={"MASTERS_CREATE_PROJECT"}
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