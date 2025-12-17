import React, { Fragment, useState, useEffect, useRef } from "react";
import { Loader, Header, MultiLink, StatusTable, Card, Row, HorizontalNav, ViewDetailsCard, Menu, SubmitBar } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { ViewComposer } from "@egovernments/digit-ui-react-components";
import { data } from "../../configs/viewConfig";
import { useHistory } from 'react-router-dom';
import { Toast ,Button,ActionBar} from "@egovernments/digit-ui-components";

const ViewDetailedEstimate = () => {
  const history = useHistory();
  const [showActions, setShowActions] = useState(false);
  const { tenantId, estimateNumber, revisionNumber } = Digit.Hooks.useQueryParams();
  const { t } = useTranslation();
  const [actionsMenu, setActionsMenu] = useState([]);
  const [isStateChanged, setStateChanged] = useState(``);
  const [toast, setToast] = useState({ show: false, label: "", type: "" });
  const menuRef = useRef();
  sessionStorage.getItem("Digit.NEW_ESTIMATE_CREATE") ? sessionStorage.removeItem("Digit.NEW_ESTIMATE_CREATE") : "";

  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

  const closeMenu = () => {
    setShowActions(false);
}
  Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions);

  const requestCriteria = {
    url: "/estimate/v1/_search",
    params : revisionNumber ? {tenantId : tenantId , revisionNumber : revisionNumber} : {tenantId : tenantId , estimateNumber : estimateNumber}
  };

  //fetching estimate data
  const {isLoading: isDetailedEstimateLoading, data: detailedEstimate} = Digit.Hooks.useCustomAPIHook(requestCriteria);

  //fetching all the estimates for revision original values
  const requestrevisionCriteria = {
    url: "/estimate/v1/_search",
    params : {tenantId : tenantId , estimateNumber : estimateNumber},
    config : {
      cacheTime : 0
    },
    changeQueryName: "allDetailedEstimate"
  };

  //fetching estimate data
  const {isLoading: isDetailedEstimatesLoading, data: allDetailedEstimate} = Digit.Hooks.useCustomAPIHook(requestrevisionCriteria);
  //fetching project data
  const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
    tenantId,
    searchParams: {
      Projects: [
        {
          tenantId,
          id: detailedEstimate?.estimates[0]?.projectId,
        },
      ],
    },
    config: {
      enabled: !!detailedEstimate?.estimates[0]?.projectId,
    },
  });

  //here make a contract search based on the estimateNumber
  let { isLoading: isLoadingContracts, data: contracts } = Digit.Hooks.contracts.useContractSearch({
    tenantId,
    filters: { tenantId, estimateIds: detailedEstimate?.estimates?.map((ob) => ob?.id) },
    config: {
      enabled: !isDetailedEstimateLoading && detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" ? true : false,
      cacheTime: 0,
    },
  });
  let validationData = Digit.Hooks.mukta.useEstimateSearchValidation({detailedestimates : allDetailedEstimate,tenantId, t});

  //fetching all work orders for a particular estimate
  let allContract = contracts;
  //getting the object which will be in workflow, as 1:1:1 mapping is there, one one inworkflow workorder will be there for one estimate
  let inWorkflowContract = allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.[0];
  let isCreateContractallowed = !(allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.length > 0) && !inWorkflowContract;

  useEffect(() => {
    let isUserContractCreator = loggedInUserRoles?.includes("WORK_ORDER_CREATOR");
    if (
      detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" &&
      isUserContractCreator &&
      !actionsMenu?.find((ob) => ob?.name === "CREATE_CONTRACT") && (isCreateContractallowed == true && (allContract !== undefined || allContract?.length == 0))
    ) {
      setActionsMenu((prevState) => [
        ...prevState,
        {
          name:"CREATE_CONTRACT",
          displayName:"EST_VIEW_ACTIONS_CREATE_CONTRACT",
        },
      ]);
    }
    //checking if any work order is inworflow, if it is then view contract will be shown otherwise create contract

    //if contract is already there just remove the prevState and push View contract state
    if (contracts?.[0]?.contractNumber && !isCreateContractallowed) {
      setActionsMenu((prevState) => [
        ...prevState,
        {
          name:"VIEW_CONTRACT",
          displayName: "EST_VIEW_ACTIONS_VIEW_CONTRACT",
        },
      ]);
    }

    let isUserEstimateCreater = loggedInUserRoles?.includes("ESTIMATE_CREATOR");
    //Checking if REVSION ESTIMATE can be created or not.
    let isRevisionEstimateInWorkflow = detailedEstimate?.estimates.filter((ob) => ob?.status === "INWORKFLOW")?.length > 0;
    if( detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" && !isRevisionEstimateInWorkflow && isUserEstimateCreater && !actionsMenu?.find((ob) => ob?.name === "CREATE_REVISION_ESTIMATE"))
    {
      setActionsMenu((prevState) => [
        ...prevState,
        {
          name: "CREATE_REVISION_ESTIMATE",
          displayName: "EST_VIEW_ACTIONS_CREATE_REVISION_ESTIMATE",
        },
      ]);
    }
  
  }, [detailedEstimate, isStateChanged, contracts]);

  const handleToastClose = () => {
    setToast({ show: false, label: "", type: "" });
}

  const handleActionBar = (option) => {
    if(validationData && Object.keys(validationData)?.length > 0 && validationData?.type?.includes(option?.name))
    {
      setToast({type: validationData?.error ? "error" : "", label: validationData?.label, show:true})
      return;
    }
    if (option?.name === "CREATE_CONTRACT") {
      history.push(`/${window.contextPath}/employee/contracts/create-contract?tenantId=${tenantId}&estimateNumber=${estimateNumber}`);
    }
    if (option?.name === "VIEW_CONTRACT") {
      history.push(
        `/${window.contextPath}/employee/contracts/contract-details?tenantId=${tenantId}&workOrderNumber=${inWorkflowContract?.contractNumber}`
      );
    }
    if (option?.name === "CREATE_REVISION_ESTIMATE") {
      history.push(
        `/${window.contextPath}/employee/estimate/create-revision-detailed-estimate?tenantId=${tenantId}&projectNumber=${project?.projectNumber}&estimateNumber=${estimateNumber}&isCreateRevisionEstimate=true`
      );
    }
  };

  const HandleDownloadPdf = () => {
    if(revisionNumber)
      Digit.Utils.downloadEgovPDF("deviationStatement/deviation-statement", { revisionNumber, tenantId }, `DeviationStatement-${revisionNumber}.pdf`);
    else
    Digit.Utils.downloadEgovPDF("detailedEstimate/detailed-estimate", { estimateNumber, tenantId }, `Estimate-${estimateNumber}.pdf`);
  };

  const overheads = detailedEstimate?.estimates?.filter((ob) => revisionNumber ? (ob?.revisionNumber === revisionNumber) : (ob?.businessService === "ESTIMATE" || !(ob?.revisionNumber)))?.[0]?.estimateDetails?.filter((row) => row?.category?.includes("OVERHEAD") && row?.isActive);
  const tableHeaderOverheads = [t("WORKS_SNO"), t("WORKS_OVERHEAD"), t("WORKS_PERCENTAGE"), t("WORKS_AMOUNT")];
  const tableRowsOverheads = overheads?.map((row, index) => {
    return [
      index + 1,
      t(`ES_COMMON_OVERHEADS_${row?.name}`),
      row?.additionalDetails?.row?.name?.type?.includes("percent") ? `${row?.additionalDetails?.row?.name?.value}%` : t("WORKS_LUMPSUM"),
      Digit.Utils.dss.formatterWithoutRound(row?.amountDetail?.[0]?.amount?.toFixed(2), "number"),
    ];
  });
  const totalAmountOverheads = overheads?.reduce((acc, row) => row?.amountDetail?.[0]?.amount + acc, 0);
  tableRowsOverheads?.push(["", "", t("RT_TOTAL"), Digit.Utils.dss.formatterWithoutRound(totalAmountOverheads, "number")]);
  const overheadItems = {
    headers: tableHeaderOverheads,
    tableRows: tableRowsOverheads,
    tableStyles: {
      rowStyle: {},
      cellStyle: [{}, { width: "50vw", whiteSpace: "break-spaces", wordBreak: "break-all" }, { textAlign: "left" }, { textAlign: "right" }],
    },
  };

  const config = data(project, detailedEstimate?.estimates?.filter((ob) => revisionNumber ? (ob?.revisionNumber === revisionNumber) : !(ob?.revisionNumber))?.[0], overheadItems, revisionNumber, allDetailedEstimate);

  if (isProjectLoading || isDetailedEstimateLoading | isDetailedEstimatesLoading) return <Loader />;

  return (
    <div className={`employee-main-application-details ${"estimate-details"}`}>
      <div className={"employee-application-details"} style={{ marginBottom: "24px" ,alignItems:"center"}}>
        <Header className="works-header-view" styles={{ margin: "0px" }}>
          {revisionNumber ? t("ESTIMATE_VIEW_REVISED_ESTIMATE") : t("ESTIMATE_VIEW_ESTIMATE")}
        </Header>
        {/* <MultiLink onHeadClick={() => HandleDownloadPdf()} downloadBtnClassName={"employee-download-btn-className"} label={t("CS_COMMON_DOWNLOAD")} /> */}
        {
          <Button
            label={t("CS_COMMON_DOWNLOAD")}
            onClick={() => HandleDownloadPdf()}
            className={"employee-download-btn-className"}
            variation={"teritiary"}
            type="button"
            icon={"FileDownload"}
          />
        }
      </div>
      <ViewComposer data={config} isLoading={false} />
      {toast?.show && <Toast label={toast?.label} type={toast?.type} isDleteBtn={true} onClose={handleToastClose}></Toast>}
      <>
        {detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" &&
        !isLoadingContracts &&
        actionsMenu?.length > 0 ? (
        <ActionBar
        actionFields={[
          <Button
            t={t}
            type={"actionButton"}
            options={actionsMenu}
            label={t("WORKS_ACTIONS")}
            variation={"primary"}
            optionsKey={"displayName"}
            isSearchable={false}
            onOptionSelect={(option) => {
              handleActionBar(option);
            }}
          ></Button>
        ]}
        setactionFieldsToRight={true}
        className={"new-actionbar"}
      />

        ) : null}
      </>
    </div>
  );
};

export default ViewDetailedEstimate;
