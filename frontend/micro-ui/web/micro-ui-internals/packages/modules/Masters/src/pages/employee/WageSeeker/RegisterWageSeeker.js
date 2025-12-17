import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import ConfigWageSeekerRegistrationForm from "../../../configs/configWageSeekerRegistrationForm";

const RegisterWageSeeker = () => {
  const { t } = useTranslation();
  const [file, setFile] = useState(null);
  const [uploadedFile, setUploadedFile] = useState(null);
  const [error, setError] = useState(null);

  const selectFile = (e) => {
      setFile(e.target.files[0]);
  }

  useEffect(() => {
      (async () => {
        setError(null);
        if (file) {
          const allowedFileTypesRegex = /(.*?)(jpg|jpeg|png|image|pdf)$/i
          if (file.size >= 5242880) {
            setError(t("CS_MAXIMUM_UPLOAD_SIZE_EXCEEDED"));
          } else if (file?.type && !allowedFileTypesRegex.test(file?.type)) {
            setError(t(`NOT_SUPPORTED_FILE_TYPE`))
          } else {
            try {
              const response = await Digit.UploadServices.Filestorage("NOC", file, Digit.ULBService.getStateId() || tenantId?.split(".")[0]);
              if (response?.data?.files?.length > 0) {
                setUploadedFile(response?.data?.files[0]?.fileStoreId);
              } else {
                setError(t("CS_FILE_UPLOAD_ERROR"));
              }
            } catch (err) {
              setError(t("CS_FILE_UPLOAD_ERROR"));
            }
          }
        }
    })();
  }, [file]);

  const configs = ConfigWageSeekerRegistrationForm({selectFile, uploadedFile, setUploadedFile, error});

  const onSubmit = (data) => {
  };

  return (
    <React.Fragment>
      <div className={"employee-main-application-details"}>
        <div className={"employee-application-details"} style={{ marginBottom: "24px" }}>
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
            defaultValues={{}}
            labelBold={true}
          />
        )}
      </div>
    </React.Fragment>
  );
};

export default RegisterWageSeeker;