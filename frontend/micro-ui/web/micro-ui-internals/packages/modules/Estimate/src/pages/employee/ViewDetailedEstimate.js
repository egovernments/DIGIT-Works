import React, { Fragment, useState, useEffect, useRef } from "react";
import { Loader, Header, MultiLink, StatusTable, Card, Row, HorizontalNav, ViewDetailsCard, Toast, ActionBar, Menu, SubmitBar } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { ViewComposer } from "@egovernments/digit-ui-react-components";
import { data } from "../../configs/viewConfig";
import { useHistory } from 'react-router-dom';

const ViewDetailedEstimate = () => {
  const history = useHistory();
  const [showActions, setShowActions] = useState(false);
  const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
  const { t } = useTranslation();
  const [actionsMenu, setActionsMenu] = useState([]);
  const [isStateChanged, setStateChanged] = useState(``);
  const menuRef = useRef();

  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

  const closeMenu = () => {
    setShowActions(false);
}
  Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions);

  const requestCriteria = {
    url: "/estimate/v1/_search",
    params : {tenantId : tenantId , estimateNumber : estimateNumber}
  };

  //fetching estimate data
  const {isLoading: isDetailedEstimateLoading, data: detailedEstimate} = Digit.Hooks.useCustomAPIHook(requestCriteria);


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
  let { isLoading: isLoadingContracts, data: contract } = Digit.Hooks.contracts.useContractSearch({
    tenantId,
    filters: { tenantId, estimateIds: [detailedEstimate?.estimates[0]?.id] },
    config: {
      enabled: !isDetailedEstimateLoading && detailedEstimate?.estimates[0]?.wfStatus === "APPROVED" ? true : false,
      cacheTime: 0,
    },
  });

  //fetching all work orders for a particular estimate
  let allContract = contract;
  contract = contract?.[0];
  //getting the object which will be in workflow, as 1:1:1 mapping is there, one one inworkflow workorder will be there for one estimate
  let inWorkflowContract = allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.[0];

  useEffect(() => {
    let isUserContractCreator = loggedInUserRoles?.includes("WORK_ORDER_CREATOR");
    if (
      detailedEstimate?.estimates[0]?.wfStatus === "APPROVED" &&
      isUserContractCreator &&
      !actionsMenu?.find((ob) => ob?.name === "CREATE_CONTRACT")
    ) {
      setActionsMenu((prevState) => [
        ...prevState,
        {
          name: "CREATE_CONTRACT",
        },
      ]);
    }
    //checking if any work order is inworflow, if it is then view contract will be shown otherwise create contract
    let isCreateContractallowed = allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.length > 0;

    //if contract is already there just remove the prevState and push View contract state
    if (contract?.contractNumber && isCreateContractallowed) {
      setActionsMenu((prevState) => [
        {
          name: "VIEW_CONTRACT",
        },
      ]);
    }
  }, [detailedEstimate, isStateChanged, contract]);

  const handleActionBar = (option) => {
    if (option?.name === "CREATE_CONTRACT") {
      history.push(`/${window.contextPath}/employee/contracts/create-contract?tenantId=${tenantId}&estimateNumber=${estimateNumber}`);
    }
    if (option?.name === "VIEW_CONTRACT") {
      history.push(
        `/${window.contextPath}/employee/contracts/contract-details?tenantId=${tenantId}&workOrderNumber=${inWorkflowContract?.contractNumber}`
      );
    }
  };

  const HandleDownloadPdf = () => {
    Digit.Utils.downloadEgovPDF("estimate/estimates", { estimateNumber, tenantId }, `Estimate-${estimateNumber}.pdf`);
  };

  const overheads = detailedEstimate?.estimates[0]?.estimateDetails?.filter((row) => row?.category?.includes("OVERHEAD") && row?.isActive);
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

  const config = data(project, detailedEstimate?.estimates[0], overheadItems);

  if (isProjectLoading || isDetailedEstimateLoading) return <Loader />;

  return (
    <div className={"employee-main-application-details"}>
      <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
        <Header className="works-header-view" styles={{ marginLeft: "0px", paddingTop: "10px" }}>
          {t("ESTIMATE_VIEW_ESTIMATE")}
        </Header>
        <MultiLink onHeadClick={() => HandleDownloadPdf()} downloadBtnClassName={"employee-download-btn-className"} label={t("CS_COMMON_DOWNLOAD")} />
      </div>
      <ViewComposer data={config} isLoading={false} />
      <>
        {detailedEstimate?.estimates[0]?.wfStatus === "APPROVED" && !isLoadingContracts && actionsMenu?.length > 0 ? (
          <ActionBar>
          {showActions ? <Menu
              localeKeyPrefix={`EST_VIEW_ACTIONS`}
              options={actionsMenu}
              optionKey={"name"}
              t={t}
              onSelect={handleActionBar}
          />:null} 
          <SubmitBar ref={menuRef} label={t("WORKS_ACTIONS")} onSubmit={() => setShowActions(!showActions)} />
      </ActionBar>
        ) : null}
      </>
    </div>
  );
};

export default ViewDetailedEstimate;
