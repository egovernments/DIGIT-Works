import React, { Fragment, useState } from "react";
import { Toast } from "@egovernments/digit-ui-components";
import { useTranslation } from "react-i18next";
import CreateEstimateForm from "../../../components/CreateEstimate/CreateEstimateForm";
import { createEstimatePayload } from "../../../utils/createEstimatePayload";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
//import SubWork from '../../components/CreateEstimate/SubWork';
const allowedFileTypes = /(.*?)(pdf|docx|msword|openxmlformats-officedocument|wordprocessingml|document|spreadsheetml|sheet)$/i;


const CreateEstimate = (props) => {
  // Call create estimate API by using requestInfo,estimate(payload,workflow)
  const { mutate: EstimateMutation } = Digit.Hooks.works.useCreateEstimate("WORKS");
  const [showToast, setShowToast] = useState(null);
  const { t } = useTranslation();
  const history = useHistory();
  const [sessionFormData, setSessionFormData, clearSessionFormData] = props.EstimateSession;

  const onFormSubmit = async (_data) => {
    const payload = await createEstimatePayload(_data);
    const workflow = {
      action: "CREATE",
      comment: _data?.comments,
      assignees: [_data?.app?.uuid],
    };
    Object.keys(workflow).forEach((key) => {
      if (workflow[key] === undefined) {
        delete workflow[key];
      }
    });
    const estimate = {
      estimate: payload,
      workflow,
    };

    await EstimateMutation(estimate, {
      onError: (error, variables) => {
        setShowToast({ type:"warning", label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
        setTimeout(() => {
          setShowToast(false);
        }, 5000);
      },
      onSuccess: async (responseData, variables) => {
        clearSessionFormData();
        history.push(`/${window?.contextPath}/employee/works/response`, {
          header: t("WORKS_ESTIMATE_RESPONSE_CREATED_HEADER"),
          id: responseData?.estimates[0]?.estimateNumber,
          info: t("WORKS_ESTIMATE_ID"),
          message: t("WORKS_ESTIMATE_RESPONSE_MESSAGE_CREATE", { department: t(`ES_COMMON_${responseData?.estimates[0]?.department}`) }),
          links: [
            {
              name: t("WORKS_CREATE_ESTIMATE"),
              redirectUrl: `/${window?.contextPath}/employee/works/create-estimate`,
              code: "",
              svg: "CreateEstimateIcon",
              isVisible: true,
              type: "add",
            },
            {
              name: t("WORKS_GOTO_ESTIMATE_INBOX"),
              redirectUrl: `/${window?.contextPath}/employee/works/inbox`,
              code: "",
              svg: "GotoInboxIcon",
              isVisible: true,
              type: "inbox",
            },
            {
              name: t("WORKS_DOWNLOAD_PDF"),
              redirectUrl:`/${window?.contextPath}/employee/works/inbox`,
              code:"",
              svg:"DownloadPrefixIcon",
              isVisible:true,
              type:"download",
            },
          ],
        });
      },
    });
  };

  return (
    <Fragment>
      <CreateEstimateForm onFormSubmit={onFormSubmit} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} />
      {showToast && (
        <Toast
          style={{ zIndex: "9999999" }}
          type={showToast?.type}
          label={t(showToast.label)}
          onClose={() => {
            setShowToast(null);
          }}
        />
      )}
    </Fragment>
  );
};

export default CreateEstimate;
