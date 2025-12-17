import React, { Fragment, useState, useEffect } from "react";
import { WorkflowModal } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import CreateContractForm from "../../components/CreateContract/CreateContractForm";
import getModalConfig from "../../../utils/getModalConfig";
import {Toast} from '@egovernments/digit-ui-components'


const CreateContract = (props) => {
  const [showToast, setShowToast] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [createFormData,setCreateFormData] = useState(null)

  const [config, setConfig] = useState({});
  const [approvers, setApprovers] = useState([]);
  const [selectedApprover, setSelectedApprover] = useState({});

  const [department, setDepartment] = useState([]);
  const [selectedDept, setSelectedDept] = useState({})

  const [designation, setDesignation] = useState([]);
  const [selectedDesignation, setSelectedDesignation] = useState({})

  const rolesForThisAction = "EST_CHECKER" //hardcoded for now

  const { isLoading: mdmsLoading, data: mdmsData, isSuccess: mdmsSuccess } = Digit.Hooks.useCustomMDMS(
    Digit.ULBService.getCurrentTenantId(),
    "common-masters",
    [
      {
        "name": "Designation"
      },
      {
        "name": "Department"
      }
    ]
  );

  mdmsData?.["common-masters"]?.Designation?.map(designation => {
    designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
  })

  mdmsData?.["common-masters"]?.Department?.map(department => {
    department.i18nKey = `ES_COMMON_${department?.code}`
  })

  useEffect(() => {

    //setApprovers(approverData?.Employees?.map((employee) => ({ uuid: employee?.uuid, name: employee?.user?.name })));
    //setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees : [])
    setDepartment(mdmsData?.["common-masters"]?.Department)
    setDesignation(mdmsData?.["common-masters"]?.Designation)
  }, [mdmsData]);


  const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code, departments: selectedDept?.code, roles: rolesForThisAction, isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: !!(selectedDept || selectedDesignation) });


  employeeDatav1?.Employees.map(emp => emp.nameOfEmp = emp?.user?.name || "NA")

  useEffect(() => {
    setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter(emp => emp?.nameOfEmp !== "NA") : [])
  }, [employeeDatav1])

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
  
  useEffect(() => {
   
    setConfig(
      getModalConfig({
        t,
        approvers,
        selectedApprover,
        setSelectedApprover,
        designation,
        selectedDesignation,
        setSelectedDesignation,
        department,
        selectedDept,
        setSelectedDept,
        approverLoading
      })
    )

  }, [approvers, designation, department])
  

  const onFormSubmit = async (_data) => {
    //first do whatever processing you want on form data then pass it over to modal's onSubmit function
    setCreateFormData((prevState) => _data)
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
    //             history.push(`/${window?.contextPath}/employee/works/response",{
    //                 header:"Work Order Created Successfully and sent for Approval",
    //                 id:"WO/ENG/0001/07/2021-22",
    //                 info:t("WORKS_ORDER_ID"),
    //                 message:`Work order with Work Order ID {workID} created successfully.`,
    //                 links:[
    //                     {
    //                         name:t("WORKS_CREATE_CONTRACT"),
    //                         redirectUrl:`/${window?.contextPath}/employee/works/create-contract",
    //                         code:"",
    //                         svg:"CreateEstimateIcon",
    //                         isVisible:true,
    //                         type:"add"
    //                     },
    //                     {
    //                         name:t("WORKS_GOTO_CONTRACT_INBOX"),
    //                         redirectUrl:`/${window?.contextPath}/employee/works/create-contract",
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

  const onModalSubmit = (data) => {
    //here you can handle the data submitted in the modal and call the api
    //access comments from data and details such as dept,desig,approver are stored locally in this comp
  }

  return (
    <Fragment>
      {/* here render the newly created modal component */}
      {showModal && <WorkflowModal 
                      closeModal={()=>setShowModal(false)}
                      onSubmit={onModalSubmit}
                      config={config}
                      sessionFormData={sessionFormData}
                      setSessionFormData={setSessionFormData}
                    />
      }
      <CreateContractForm onFormSubmit={onFormSubmit} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} />
      {showToast && (
        <Toast
          style={{ zIndex: "9999999" }}
          type={showToast?.type}
          label={t(showToast?.label)}
          onClose={() => {
            setShowToast(null);
          }}
        />
      )}
    </Fragment>
  );
};

export default CreateContract;
