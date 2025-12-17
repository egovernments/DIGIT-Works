import {
  Card,
  StatusTable,
  Row,
  Header,
  HorizontalNav,
  SubmitBar,
  WorkflowModal,
  FormComposer,
  Loader,
  ViewDetailsCard,
  Menu,
  FormComposerV2,
} from "@egovernments/digit-ui-react-components";
import { Toast ,ActionBar,Button} from "@egovernments/digit-ui-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import getModalConfig from "./config";
import { createEstimateConfig } from "./createEstimateConfig";
import { createEstimatePayload } from "./createEstimatePayload";
import { useHistory, useLocation } from "react-router-dom";
import { editEstimateUtil } from "./editEstimateUtil";
import { transformEstimateData, getLabourMaterialAnalysisCost } from "../../../../../util/EstimateData";

const configNavItems = [
  {
    name: "Project Details",
    code: "WORKS_PROJECT_DETAILS",
  },
  {
    name: "Work Details",
    code: "WORKS_WORK_DETAILS",
    activeByDefault: true,
  },
];
const CreateEstimate = ({ props }) => {
  const tenant = Digit.ULBService.getStateId();
  const { t } = useTranslation();
  const [showToast, setShowToast] = useState(null);
  const [displayMenu, setDisplayMenu] = useState(false);
  const [actionSelected, setActionSelected] = useState(false);
  const [isButtonDisabled, setIsButtonDisabled] = useState(false);
  let {
    tenantId,
    projectNumber,
    isEdit,
    isCreateRevisionEstimate,
    isEditRevisionEstimate,
    estimateNumber,
    revisionNumber,
  } = Digit.Hooks.useQueryParams();
  // const [ isFormReady,setIsFormReady ] = useState(isEdit ? false : true)
  const [isFormReady, setIsFormReady] = useState(true);

  const history = useHistory();

  let actionMB = [
    {
      name: "WF_SUBMIT",
    },
    {
      name: "WF_DRAFT",
    },
  ];

  function onActionSelect(action) {
    if (sessionFormData?.period?.type == "error") {
      setShowToast({ type: "error", label: sessionFormData?.period?.message });
      return null;
    }
    setActionSelected(action?.name);
    onFormSubmit(sessionFormData, action?.name);
  }

  // const {state} = useLocation()

  //if estimateNumber is there and isEdit is true then search estimate
  //fetching estimate data
  const { isLoading: isEstimateLoading, data: estimate } = Digit.Hooks.estimates.useEstimateSearch({
    tenantId,
    filters: isEditRevisionEstimate ? { revisionNumber } : { estimateNumber },
    config: {
      enabled: (isEdit || isCreateRevisionEstimate || isEditRevisionEstimate) && (estimateNumber || revisionNumber) ? true : false,
    },
  });

  //fetching all the estimates for revision original values
  const requestrevisionCriteria = {
    url: "/estimate/v1/_search",
    params: { tenantId: tenantId, estimateNumber: estimateNumber },
    config: {
      cacheTime: 0,
    },
    changeQueryName: "allDetailedEstimate",
  };

  //fetching estimate data
  const { isLoading: isAllEstimateLoading, data: allEstimates } = Digit.Hooks.useCustomAPIHook(requestrevisionCriteria);

  actionMB =
    actionMB && (isEdit || isEditRevisionEstimate) && estimate && estimate?.wfStatus === "PENDINGFORCORRECTION"
      ? actionMB?.filter((ob) => ob?.name !== "WF_DRAFT")
      : actionMB;

  const searchParams = {
    Projects: [
      {
        tenantId,
        projectNumber: projectNumber,
      },
    ],
  };
  const filters = {
    limit: 11,
    offset: 0,
    includeAncestors: true,
    includeDescendants: true,
  };

  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
  const { data: projectData, isLoading } = Digit.Hooks.works.useViewProjectDetails(t, tenantId, searchParams, filters, headerLocale);

  const cardState = [
    {
      title: " ",
      asSectionHeader: true,
      values: [
        {
          title: "WORKS_ESTIMATE_TYPE",
          value: revisionNumber ? t("REVISION_ESTIMATE") : t("ORIGINAL_ESTIMATE"),
        },
        {
          title: "WORKS_PROJECT_ID",
          value: projectData?.projectDetails?.searchedProject?.basicDetails?.projectID,
        },
        {
          title: "WORKS_DATE_PROPOSAL",
          value: projectData?.projectDetails?.searchedProject?.basicDetails?.projectProposalDate,
        },
        {
          title: "WORKS_PROJECT_NAME",
          value: projectData?.projectDetails?.searchedProject?.basicDetails?.projectName,
        },
        {
          title: "PROJECTS_DESCRIPTION",
          value: projectData?.projectDetails?.searchedProject?.basicDetails?.projectDesc,
        },
      ],
    },
  ];

  if (isEdit || isCreateRevisionEstimate || isEditRevisionEstimate) {
    cardState[0].values = [
      {
        title: "WORKS_ESTIMATE_ID",
        value: estimateNumber || estimate?.estimateNumber,
      },
      ...cardState?.[0]?.values,
    ];
  }

  if (isEditRevisionEstimate) {
    cardState[0].values = [
      {
        title: "WORKS_REVISED_NO",
        value: revisionNumber,
      },
      ...cardState?.[0]?.values,
    ];
  }

  //for creating estimates
  const { mutate: EstimateMutation } = Digit.Hooks.works.useCreateEstimateNew("WORKS");

  //for updating estimate
  const { mutate: EstimateUpdateMutation } = Digit.Hooks.works.useApplicationActionsEstimate();

  const [showModal, setShowModal] = useState(false);

  const rolesForThisAction = "ESTIMATE_VERIFIER"; //hardcoded for now
  const [config, setConfig] = useState({});
  const [approvers, setApprovers] = useState([]);
  const [selectedApprover, setSelectedApprover] = useState({});

  // const [department, setDepartment] = useState([]);
  // const [selectedDept, setSelectedDept] = useState({})

  // const [designation, setDesignation] = useState([]);
  // const [selectedDesignation, setSelectedDesignation] = useState({})

  const [inputFormData, setInputFormData] = useState({});

  //getting uom and overheads masters from mdms
  let { isLoading: isUomLoading, data: uom } = Digit.Hooks.useCustomMDMS(
    tenant,
    "common-masters",
    [
      {
        name: "UOM",
      },
    ],
    {
      select: (data) => {
        return data?.["common-masters"]?.UOM;
      },
    }
  );

  const { isLoading: isDocLoading, data: docData } = Digit.Hooks.useCustomMDMS(tenant, "works", [
    {
      name: "DocumentConfig",
      filter: `[?(@.module=='Estimate')]`,
    },
  ]);

  let { isLoading: isOverheadsLoading, data: overheads } = Digit.Hooks.useCustomMDMS(
    tenant,
    "works",
    [
      {
        name: "Overheads",
      },
    ],
    {
      select: (data) => {
        return data?.["works"]?.Overheads;
      },
    }
  );

  const requestCriteria = {
    url: "/mdms-v2/v1/_search",
    body: {
      MdmsCriteria: {
        tenantId: tenantId,
        moduleDetails: [
          {
            moduleName: "WORKS-SOR",
            masterDetails: [
              {
                name: "Rates",
                //filter: `[?(@.sorId=='${sorid}')]`,
              },
            ],
          },
        ],
      },
    },
    changeQueryName: "ratesQuery",
  };

  const { isRatesLoading, data: RatesData } = Digit.Hooks.useCustomAPIHook(requestCriteria);

  const moduleName = Digit.Utils.getConfigModuleName();
  let { isLoading: isConfigLoading, data: estimateFormConfig } = Digit.Hooks.useCustomMDMS(
    tenant,
    moduleName,
    [
      {
        name: "CreateEstimateConfig",
      },
    ],
    {
      select: (data) => {
        return data?.[moduleName]?.CreateEstimateConfig?.[0];
      },
    }
  );

  let currentEstimate =
    window.location.href.includes("/update-revision-detailed-estimate") || window.location.href.includes("/update-detailed-estimate")
      ? estimate
      : allEstimates?.estimates?.filter((ob) => ob?.wfStatus === "APPROVED")?.[0];

  const closeToast = () => {
    setTimeout(() => {
      setShowToast(null);
    }, 7000);
  };
  //to use local config
  estimateFormConfig = createEstimateConfig();
  const sorCategoryArray = [];
  const nonSorCategoryArray = [];
  sorCategoryArray.push(transformEstimateData("SOR"));
  nonSorCategoryArray.push(transformEstimateData());

  let sorAndNonSorData = {
    SOR: sorCategoryArray,
    NONSOR: nonSorCategoryArray,
    // SORtable: sorCategoryArray,
    // NONSORtable: nonSorCategoryArray,
    ...(JSON.parse(sessionStorage.getItem("Digit.NEW_ESTIMATE_CREATE"))?.value) || {},
    projectType: {},
  };
  if(window.location.href.includes("create-detailed-estimate")) sorAndNonSorData = { ...sorAndNonSorData, SORtable: sorCategoryArray, NONSORtable: nonSorCategoryArray }
  const EstimateSession = Digit.Hooks.useSessionStorage("NEW_ESTIMATE_CREATE", sorAndNonSorData);
  const [sessionFormData, setSessionFormData, clearSessionFormData] = EstimateSession;

  useEffect(() => {
    const savedFormData = sessionStorage.getItem("Digit.NEW_ESTIMATE_CREATE");
    if (savedFormData) {
      setSessionFormData(JSON.parse(savedFormData));
    }
  }, []);
  
  useEffect(() => {
    sessionStorage.setItem("Digit.NEW_ESTIMATE_CREATE", JSON.stringify(sessionFormData));
  }, [sessionFormData]);

  const initialDefaultValues = RatesData ? editEstimateUtil(currentEstimate, uom, overheads, RatesData, allEstimates, sessionFormData) : {};

  // useEffect(() => {

  // }, [])

  useEffect(() => {
    if (uom && estimate && currentEstimate && overheads && (isEdit || isCreateRevisionEstimate || isEditRevisionEstimate)) {
      setSessionFormData(initialDefaultValues);
    }
  }, [currentEstimate, uom, overheads, RatesData]);

  const onFormValueChange = (setValue, formData, formState, reset, setError, clearErrors, trigger, getValues) => {
    if (!_.isEqual(formData, sessionFormData)) {
      // if(isEdit) {
      //     setSessionFormData({...initialDefaultValues,...formData,...sessionFormData})
      // }
      // else{
      setSessionFormData({ ...formData });
      // }
      // setSessionFormData({ ...sessionFormData, ...formData });
    }
  };

  function validateNonSor(items) {
    //this is to check is any one param is present for non other param should also be present or removed alltogether
    for (const item of items) {
      if (
        !item.description ||
        !item.uom ||
        item.unitRate === undefined ||
        item.unitRate <= 0 ||
        item.currentMBEntry === undefined ||
        (isCreateRevisionEstimate || isEditRevisionEstimate ? !(item?.currentMBEntry >= 0) : !item.currentMBEntry)
      ) {
        setShowToast({ type: "error", label: `${t("ERR_NONSOR_ITEM_IS_MISSING")} ${item?.sNo}` });
        setIsButtonDisabled(false);
        setShowModal(false);
        return false;
      }

      //this is to check if measures are there in NON sor it should have description
      if (item.measures && item.measures.length > 0) {
        for (const measure of item.measures) {
          if (!measure.description) {
            setShowToast({ type: "error", label: `${t("ERR_ENTER_DESCRIPTION_IN_NONSOR")} ${item?.sNo}` });
            setIsButtonDisabled(false);
            setShowModal(false);
            return false;
          }
        }
      }
    }
    return true;
  }

  function validateData(data) {
    // To validate either SOR or NON SOR must be present
    if (
      (!data?.SORtable && !data?.NONSORtable) ||
      (data?.SORtable?.length <= 0 && data?.NONSORtable?.length <= 0) ||
      (data?.SORtable?.length === 1 && data?.SORtable?.[0]?.category === "NON-SOR")
    ) {
      setShowToast({ type: "error", label: "ERR_ATLEAST_SOR_OR_NON_SOR_PRESENT" });
      setIsButtonDisabled(false);
      setShowModal(false);
      return false;
    }
    //To validate that if SOR is present it should have measures
    if (
      data?.SORtable?.filter(
        (ob) => ob?.sorCode && (isCreateRevisionEstimate || isEditRevisionEstimate ? ob?.currentMBEntry < 0 : !ob?.currentMBEntry)
      )?.length > 0
    ) {
      setShowToast({ type: "error", label: "ERR_MB_AMOUNT_IS_NOT_RIGHT_FOR_SOR" });
      setIsButtonDisabled(false);
      setShowModal(false);
      return false;
    }
    //To validate if the measures are present in SOR table it should have description param
    let descriptionpresent = data?.SORtable?.find(
      (item) =>
        item?.sorCode &&
        item.measures.some((measure) => measure?.description === "" || measure?.description === undefined || measure?.description === null)
    );
    if (descriptionpresent) {
      setShowToast({ type: "error", label: `${t("ERR_ENTER_DESCRIPTION_IN_SOR")} ${descriptionpresent?.sorId || descriptionpresent?.sorCode}` });
      setIsButtonDisabled(false);
      setShowModal(false);
      return false;
    }
    //To validate the data of NON Sor
    if (data?.NONSORtable && !validateNonSor(data?.NONSORtable)) return false;

    //To validate SOR and NON SOR will not have negative values
    let negativeValuedObject =
      data?.SORtable?.find((item) => parseFloat(item.amount) < 0) || data?.NONSORtable?.find((item) => parseFloat(item.amount) < 0);
    if (negativeValuedObject) {
      setShowToast({ type: "error", label: `${t("ERR_NEGATIVE_VALUE_IS_NOT_ALLOWED")} ${negativeValuedObject?.category}` });
      setIsButtonDisabled(false);
      setShowModal(false);
      return false;
    }
    let documentData = data?.uploadedDocs;
    let documentValidated = true;
    //To Validate the documents in estimate
    docData?.works?.DocumentConfig?.[0]?.documents?.forEach((doc) => {
      const documentName = doc?.name;
      const isMandatory = doc?.isMandatory;
      // Check if the document is mandatory
      if (isMandatory) {
        // Check if the corresponding data array is empty
        if (documentData[documentName].length === 0) {
          documentValidated = false;
        }
      }
    });
    if (!documentValidated) {
      setShowToast({ type: "error", label: `${t("ERR_DOCUMENT_IS_MANDATORY")}` });
      setIsButtonDisabled(false);
      setShowModal(false);
      return false;
    }

    return true;
  }

  const onFormSubmit = async (_data, action) => {
    _data = Digit.Utils.trimStringsInObject(_data);
    //added this totalEst amount logic here because setValues in pageComponents don't work
    //after setting the value, in consequent renders value changes to undefined
    //check TotalEstAmount.js
    let totalLabourAndMaterial =
      parseInt(getLabourMaterialAnalysisCost(_data, ["LA"])) +
        parseInt(getLabourMaterialAnalysisCost(_data, ["MA", "RA", "CA", "EMF", "DMF", "ADC", "LC"])) +
        parseInt(getLabourMaterialAnalysisCost(_data, ["MHA"])) ||
      _data?.labourMaterialAnalysis?.labour + _data?.labourMaterialAnalysis?.material + _data?.labourMaterialAnalysis?.machinery;
    //here check totalEst amount should be less than material+labour
    if (_data.totalEstimateAmount < totalLabourAndMaterial && action !== "WF_DRAFT") {
      setShowToast({ type: "warning", label: "ERR_ESTIMATE_AMOUNT_MISMATCH" });
      setIsButtonDisabled(false);
      closeToast();
      return;
    }
    // else if (totalLabourAndMaterial === 0  && action !== "DRAFT") {
    //   debugger;
    //   setShowToast({ type:"warning", label: "ERR_ESTIMATE_AMOUNT_IMPROPER" });
    //   closeToast();
    //   return;
    // }

    setInputFormData((prevState) => _data);
    //first do whatever processing you want on form data then pass it over to modal's onSubmit function

    if (action === "WF_DRAFT") onModalSubmit(_data, action);
    else setShowModal(true);
  };

  function removeNonsortableObjectWithoutRequiredParams(data) {
    const nonsorTable = data.NONSORtable;

    if (nonsorTable && nonsorTable.length > 0) {
      const hasAtLeastOneItemWithRequiredParams = nonsorTable.some(
        (item) =>
          (item.unitRate !== undefined && item.unitRate > 0) ||
          item.description ||
          item.uom ||
          (item.currentMBEntry !== undefined && item.currentMBEntry) ||
          (item.measures && item.measures.length > 0 && item.measures.some((measure) => measure.description))
      );

      if (!hasAtLeastOneItemWithRequiredParams) {
        // If none of the items in the nonsorTable have at least one required parameter, remove the entire nonsorTable
        data.NONSORtable = [];
      }
    }
  }

  const onModalSubmit = async (_data, action) => {
    setIsButtonDisabled(true);
    _data = Digit.Utils.trimStringsInObject(_data);
    const completeFormData = {
      ...inputFormData,
      ..._data,
      selectedApprover,
      workflowAction: actionSelected || action,
      // selectedDept,
      // selectedDesignation
    };

    removeNonsortableObjectWithoutRequiredParams(completeFormData);
    let validated = action !== "WF_DRAFT" ? validateData(completeFormData) : true;
    if(validated){
      const payload = createEstimatePayload(completeFormData, projectData, isEdit,  currentEstimate, isCreateRevisionEstimate, isEditRevisionEstimate);
      setShowModal(false);

      //make a util for updateEstimatePayload since there are some deviations

      if ((isEdit || isEditRevisionEstimate) && (estimateNumber  || revisionNumber)) {
        await EstimateUpdateMutation(payload, {
          onError: async (error, variables) => {
            sessionStorage.removeItem("Digit.NEW_ESTIMATE_CREATE");
            setIsButtonDisabled(false);
            setShowToast({ type: "warning", label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
            setTimeout(() => {
              setShowToast(false);
            }, 3000);
            if(error?.toString().includes("not found in config for the businessId"))
            {
              if(isCreateRevisionEstimate || isEditRevisionEstimate)
                setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&revisionNumber=${revisionNumber}&estimateNumber=${estimateNumber}&projectNumber=${projectNumber}`)}, 3500);
              else 
                setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/estimate-details?tenantId=${tenantId}&estimateNumber=${estimateNumber}&projectNumber=${projectNumber}`)}, 3500);
            }
          },
          onSuccess: async (responseData, variables) => {
            sessionStorage.removeItem("Digit.NEW_ESTIMATE_CREATE");
            clearSessionFormData();
            const state = {
              header: isCreateRevisionEstimate || isEditRevisionEstimate ? t("WORKS_REVISION_ESTIMATE_RESPONSE_UPDATED_HEADER") : t("WORKS_ESTIMATE_RESPONSE_UPDATED_HEADER"),
              id: isCreateRevisionEstimate || isEditRevisionEstimate ? responseData?.estimates[0]?.revisionNumber : responseData?.estimates[0]?.estimateNumber,
              info: isCreateRevisionEstimate || isEditRevisionEstimate ?  t("ESTIMATE_REVISION_ESTIMATE_NO") : t("ESTIMATE_ESTIMATE_NO"),
              // message: t("WORKS_ESTIMATE_RESPONSE_MESSAGE_CREATE", { department: t(`ES_COMMON_${responseData?.estimates[0]?.executingDepartment}`) }),
              links: [
                {
                  name: t("WORKS_GOTO_ESTIMATE_INBOX"),
                  redirectUrl: `/${window.contextPath}/employee/estimate/inbox`,
                  code: "",
                  svg: "GotoInboxIcon",
                  isVisible: true,
                  type: "inbox",
                },
              ],
            };
            if(action === "WF_DRAFT")
            {
              setShowToast({ label: t("WORKS_ESTIMATE_APPLICATION_DRAFTED") });
              if(isCreateRevisionEstimate || isEditRevisionEstimate)
                setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/update-revision-detailed-estimate?tenantId=${responseData?.estimates[0]?.tenantId}&revisionNumber=${responseData?.estimates[0]?.revisionNumber}&estimateNumber=${responseData?.estimates[0]?.estimateNumber}&projectNumber=${projectNumber}&isEditRevisionEstimate=true`, state)}, 3000);
              else
              setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/update-detailed-estimate?tenantId=${responseData?.estimates[0]?.tenantId}&estimateNumber=${responseData?.estimates[0]?.estimateNumber}&projectNumber=${projectNumber}&isEdit=true`, state)}, 3000);
            }
            else
            history.push(`/${window?.contextPath}/employee/estimate/response`, state);
          },
        });
      } else {
        await EstimateMutation(payload, {
          onError: async (error, variables) => {
            sessionStorage.removeItem("Digit.NEW_ESTIMATE_CREATE");
            setIsButtonDisabled(false);
            setShowToast({ type:"warning", label: error?.response?.data?.Errors?.[0].message ? error?.response?.data?.Errors?.[0].message : error });
            setTimeout(() => {
              setShowToast(false);
            }, 5000);
          },
          onSuccess: async (responseData, variables) => {
            sessionStorage.removeItem("Digit.NEW_ESTIMATE_CREATE");
            clearSessionFormData();
            const state = {
              header: isCreateRevisionEstimate || isEditRevisionEstimate ? t("WORKS_REVISION_ESTIMATE_RESPONSE_CREATED_HEADER") :t("WORKS_ESTIMATE_RESPONSE_CREATED_HEADER"),
              id: isCreateRevisionEstimate || isEditRevisionEstimate ? responseData?.estimates[0]?.revisionNumber : responseData?.estimates[0]?.estimateNumber,
              info:isCreateRevisionEstimate || isEditRevisionEstimate ?  t("ESTIMATE_REVISION_ESTIMATE_NO") : t("ESTIMATE_ESTIMATE_NO"),
              // message: t("WORKS_ESTIMATE_RESPONSE_MESSAGE_CREATE", { department: t(`ES_COMMON_${responseData?.estimates[0]?.executingDepartment}`) }),
              links: [
                {
                  name: t("WORKS_GOTO_ESTIMATE_INBOX"),
                  redirectUrl: `/${window.contextPath}/employee/estimate/inbox`,
                  code: "",
                  svg: "GotoInboxIcon",
                  isVisible: true,
                  type: "inbox",
                },
              ],
            };
            if(action === "WF_DRAFT")
            {
              setShowToast({ label: t("WORKS_ESTIMATE_APPLICATION_DRAFTED") });
              if(isCreateRevisionEstimate || isEditRevisionEstimate)
                setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/update-revision-detailed-estimate?tenantId=${responseData?.estimates[0]?.tenantId}&revisionNumber=${responseData?.estimates[0]?.revisionNumber}&estimateNumber=${responseData?.estimates[0]?.estimateNumber}&projectNumber=${projectNumber}&isEditRevisionEstimate=true`, state)}, 3000);
              else
              setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/update-detailed-estimate?tenantId=${responseData?.estimates[0]?.tenantId}&estimateNumber=${responseData?.estimates[0]?.estimateNumber}&projectNumber=${projectNumber}&isEdit=true`, state)}, 3000);
            }
            else
            setTimeout(() => {history.push(`/${window?.contextPath}/employee/estimate/response`, state)}, 5000);
          },
        });
      }
    };
  };

  // const { isLoading: mdmsLoading, data: mdmsData, isSuccess: mdmsSuccess } = Digit.Hooks.useCustomMDMS(
  //     Digit.ULBService.getCurrentTenantId(),
  //     "common-masters",
  //     [
  //         {
  //             "name": "Designation"
  //         },
  //         {
  //             "name": "Department"
  //         }
  //     ]
  // );

  // mdmsData?.["common-masters"]?.Designation?.map(designation => {
  //     designation.i18nKey = `ES_COMMON_DESIGNATION_${designation?.name}`
  // })

  // mdmsData?.["common-masters"]?.Department?.map(department => {
  //     department.i18nKey = `ES_COMMON_${department?.code}`
  // })
  // useEffect(() => {
  //     setDepartment(mdmsData?.["common-masters"]?.Department)
  //     setDesignation(mdmsData?.["common-masters"]?.Designation)
  // }, [mdmsData]);

  // const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch({ designations: selectedDesignation?.code, departments: selectedDept?.code, roles: rolesForThisAction, isActive: true }, Digit.ULBService.getCurrentTenantId(), null, null, { enabled: !!(selectedDept || selectedDesignation) });
  const { isLoading: approverLoading, isError, error, data: employeeDatav1 } = Digit.Hooks.hrms.useHRMSSearch(
    { roles: rolesForThisAction, isActive: true },
    Digit.ULBService.getCurrentTenantId(),
    null,
    null,
    { enabled: true }
  );

  employeeDatav1?.Employees.map((emp) => (emp.nameOfEmp = emp?.user?.name || "NA"));

  useEffect(() => {
    setApprovers(employeeDatav1?.Employees?.length > 0 ? employeeDatav1?.Employees.filter((emp) => emp?.nameOfEmp !== "NA") : []);
  }, [employeeDatav1]);
  useEffect(() => {
    setConfig(
      getModalConfig({
        t,
        approvers,
        selectedApprover,
        setSelectedApprover,
        approverLoading,
        isEdit: isEdit || isEditRevisionEstimate,
        // designation,
        // selectedDesignation,
        // setSelectedDesignation,
        // department,
        // selectedDept,
        // setSelectedDept,
      })
    );
  }, [approvers]);

  if (isConfigLoading || isEstimateLoading || isUomLoading || isOverheadsLoading || isDocLoading || isAllEstimateLoading || isRatesLoading) {
    return <Loader />;
  }
  if ((isEdit || isCreateRevisionEstimate || isEditRevisionEstimate) && Object.keys(sessionFormData).length === 0) {
    return <Loader />;
  }
  return (
    <Fragment>
      {showModal && <WorkflowModal closeModal={() => setShowModal(false)} onSubmit={onModalSubmit} config={config} isDisabled={isButtonDisabled} />}
      <Header className="works-header-create">
        {isEdit
          ? isCreateRevisionEstimate || isEditRevisionEstimate
            ? t("ACTION_TEST_EDIT_REVISION_ESTIMATE")
            : t("ACTION_TEST_EDIT_ESTIMATE")
          : isCreateRevisionEstimate || isEditRevisionEstimate
          ? t("ACTION_TEST_CREATE_REVISION_ESTIMATE")
          : t("ACTION_TEST_CREATE_ESTIMATE")}
      </Header>
      {/* Will fetch projectId from url params and do a search for project to show the below data in card while integrating with the API  */}
      {isLoading ? <Loader /> : <ViewDetailsCard cardState={cardState} t={t} createScreen={true} />}
      {/* {isLoading? <Loader/>: <ViewDetailsCard cardState={cardState} t={t} />} */}
      {isFormReady ? (
        <FormComposerV2
          label={isEdit ? "CORE_COMMON_SUBMIT" : "ACTION_TEST_CREATE_ESTIMATE"}
          config={estimateFormConfig?.form.map((config) => {
            return {
              ...config,
              body: config?.body.filter((a) => !a.hideInEmployee),
            };
          })}
          onSubmit={onFormSubmit}
          submitInForm={false}
          fieldStyle={{ marginRight: 0 }}
          inline={false}
          // className="card-no-margin"
          defaultValues={
            (isEdit === "true" || isCreateRevisionEstimate === "true" || isEditRevisionEstimate === "true") && (estimateNumber || revisionNumber)
              ? initialDefaultValues
              : sessionFormData
          }
          //defaultValues={{...sessionFormData}}
          showWrapperContainers={false}
          isDescriptionBold={false}
          noBreakLine={true}
          //showMultipleCardsWithoutNavs={true}
          showMultipleCardsInNavs={true}
          horizontalNavConfig={configNavItems}
          showFormInNav={true}
          showNavs={true}
          // sectionHeadStyle={{ marginTop: "2rem" }}
          labelBold={true}
          onFormValueChange={onFormValueChange}
          fieldPairNoMargin={true}
        />
      ) : null}
      {showToast && (
        <Toast
          type={showToast?.type}
          label={t(showToast.label)}
          onClose={() => {
            setShowToast(null);
          }}
          isDleteBtn={true}
        />
      )}

      {
        <ActionBar
          actionFields={[
            <Button
              isDisabled={isButtonDisabled}
              t={t}
              type={"actionButton"}
              options={actionMB}
              label={t("ACTIONS")}
              variation={"primary"}
              optionsKey={"name"}
              isSearchable={false}
              onOptionSelect={(option) => {
                onActionSelect(option);
              }}
            ></Button>
          ]}
          setactionFieldsToRight={true}
          className={"new-actionbar"}
        />
      }
    </Fragment>
  );
};

export default CreateEstimate;
