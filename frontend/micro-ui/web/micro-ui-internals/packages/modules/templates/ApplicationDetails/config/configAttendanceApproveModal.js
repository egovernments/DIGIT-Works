
const configAttendanceApproveModal = ({ t, action }) => {
  if (action?.applicationStatus === "ATTENDANCE_APPROVE") {
    return {
      label: {
        heading: t("ATM_PROCESSINGMODAL_HEADER"),
        submit: t("ATM_FORWARD_FOR_APPROVAL"),
        cancel: t("CS_COMMON_CANCEL"),
      },
      form: [
        {
          body: [
            {
              label: t("WF_COMMON_COMMENTS"),
              type: "textarea",
              populators: {
                name: "comments",
              },
            },
          ],
        },
      ],
      defaultValues: {
        comments: "",
      },
    };
  }
};

export default configAttendanceApproveModal;
