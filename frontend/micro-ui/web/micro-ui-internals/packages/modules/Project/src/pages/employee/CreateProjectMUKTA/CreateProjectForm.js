import { FormComposer, Header, Toast } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { createProjectSectionConfig } from "../../../configs/createProjectConfig";
import _ from "lodash";
import CreateProjectUtils from "../../../utils/createProjectUtils";
import { useHistory } from "react-router-dom";
import { createProjectConfigMUKTA } from "../../../configs/createProjectConfigMUKTA";
import preProcessMDMSConfig from "../../../configs/refactor";


const CreateProjectForm = ({sessionFormData, setSessionFormData, clearSessionFormData}) => {

    const {t} = useTranslation();
    // let config = preProcessMDMSConfig(createProjectConfigMUKTA, t);
    console.log(createProjectConfigMUKTA);
    return (
        <React.Fragment>
            <Header styles={{fontSize: "32px"}}>{t("WORKS_CREATE_PROJECT")}</Header>
          {
            createProjectConfigMUKTA && (
              <FormComposer
                label={"WORKS_CREATE_PROJECT"}
                config={createProjectConfigMUKTA?.CreateProjectConfig?.form.map((config) => {
                  return {
                    ...config,
                    body: config?.body.filter((a) => !a.hideInEmployee),
                  };
                })}
            />
           )}
      </React.Fragment>
    )
}

export default CreateProjectForm;