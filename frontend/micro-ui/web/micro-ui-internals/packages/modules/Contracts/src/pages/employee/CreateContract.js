import React, { Fragment, useState } from "react";
import { Toast } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import CreateContractForm from "../../components/CreateContract/CreateContractForm";

const CreateContract = (props) => {
  const [showToast, setShowToast] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const { t } = useTranslation();
  //To Call create contract API by using requestInfo,contract(payload,workflow)
  // const { mutate: contractMutation } = Digit.Hooks.works.useCreateContract("WORKS");
  const { estimateNumber, task, subEstimate } = Digit.Hooks.useQueryParams();
  const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {
    estimateNumber: estimateNumber,
    task: task,
    subEstimateNo: subEstimate,
    executingAuthority: { code: "WORKS_COMMUNITY_ORGN", name: "WORKS_COMMUNITY_ORGN" },
    projectEstimateAmount: "500000",
    contractedAmount: 0,
    balanceAmount: 0,
  });

  const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

  const onFormSubmit = async (_data) => {
    setShowModal(true);
    //      use below code for create contract API CALL

    //     await contractMutation(payload, {
    //         onError: (error, variables) => {
    //             setShowToast({ warning: true, label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
    //             setTimeout(() => {
    //             setShowToast(false);
    //             }, 5000);
    //         },
    //         onSuccess: async (responseData, variables) => {
    //             history.push("/works-ui/employee/works/response",{
    //                 header:"Work Order Created Successfully and sent for Approval",
    //                 id:"WO/ENG/0001/07/2021-22",
    //                 info:t("WORKS_ORDER_ID"),
    //                 message:`Work order with Work Order ID {workID} created successfully.`,
    //                 links:[
    //                     {
    //                         name:t("WORKS_CREATE_CONTRACT"),
    //                         redirectUrl:"/works-ui/employee/works/create-contract",
    //                         code:"",
    //                         svg:"CreateEstimateIcon",
    //                         isVisible:true,
    //                         type:"add"
    //                     },
    //                     {
    //                         name:t("WORKS_GOTO_CONTRACT_INBOX"),
    //                         redirectUrl:"/works-ui/employee/works/create-contract",
    //                         code:"",
    //                         svg:"RefreshIcon",
    //                         isVisible:true,
    //                         type:"add"
    //                     }
    //                 ]
    //             })
    //         }
    //     })
  };

  return (
    <Fragment>
      {showModal && <div>Popup</div>}
      <CreateContractForm onFormSubmit={onFormSubmit} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} />
      {showToast && (
        <Toast
          style={{ zIndex: "9999999" }}
          error={showToast.error}
          warning={showToast.warning}
          label={t(showToast.label)}
          onClose={() => {
            setShowToast(null);
          }}
        />
      )}
    </Fragment>
  );
};

export default CreateContract;
