import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import createProjectConfig from "../../../configs/createProjectConfig";

const CreateProject = () => {
    const {t} = useTranslation();
    const [hasSubProjects, setHasSubProjects] = useState("");

    const hasSubProjectOptions = {
        options : [
            {
                code : "COMMON_YES",
                name : "COMMON_YES"
            },
            {
                code : "COMMON_NO",
                name : "COMMON_NO"
            },
        ]
    }

    const handleHasSubProjectOptions = (option) => {
        setHasSubProjects(option);
    }
    const configs = createProjectConfig(hasSubProjectOptions, handleHasSubProjectOptions);
    useEffect(()=>{
        console.log(hasSubProjects);
    },[hasSubProjects]);

    const onSubmit = () => {}

    return (
        <React.Fragment>
        <div className={"employee-main-application-details"}>
          <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
            <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("MASTERS_CREATE_PROJECT")}</Header>
          </div>
          {configs.form && (
            <FormComposer
              label={"MASTERS_CREATE_PROJECT"}
              config={configs?.form.map((config) => {
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
              defaultValues={configs.defaultValues}
            />
          )}
        </div>
      </React.Fragment>
    )
}

export default CreateProject;