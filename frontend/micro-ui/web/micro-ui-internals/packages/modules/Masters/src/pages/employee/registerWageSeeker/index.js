import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import ConfigWageSeekerRegistrationForm from "../../../configs/configWageSeekerRegistrationForm";

const RegisterWageSeeker = () => {
  const { t } = useTranslation();
  const [configs, setConfigs] = useState({});

  useEffect(() => {
    setConfigs(ConfigWageSeekerRegistrationForm());
  }, []);

  const onSubmit = () => {};

  return (
    <React.Fragment>
      <div className={"employee-main-application-details"}>
        <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
          <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("MASTERS_CREATE_NEW_WAGE_SEEKER")}</Header>
        </div>
        {configs.form && (
          <FormComposer
            label={"MASTERS_CREATE_WAGE_SEEKER_RECORD"}
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
          />
        )}
      </div>
    </React.Fragment>
  );
};

export default RegisterWageSeeker;
