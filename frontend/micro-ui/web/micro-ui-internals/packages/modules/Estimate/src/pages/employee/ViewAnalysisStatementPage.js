import React, { Fragment, useState, useEffect, useRef } from "react";
import { Loader, Header, MultiLink, StatusTable, Card, Row, HorizontalNav, ViewDetailsCard, Menu, SubmitBar, CitizenInfoLabel } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { ViewComposer } from "@egovernments/digit-ui-react-components";
import { data } from "../../configs/viewStatementConfig";
import { InfoCard } from "@egovernments/digit-ui-components";
import { useHistory, useLocation } from 'react-router-dom';
import { Toast ,Button,ActionBar} from "@egovernments/digit-ui-components";

const ViewAnalysisStatement = () => {

    // let statement = [
    //     {
    //       "id": "b17bdb3c-f0c4-4507-abaf-285d7afea03e",
    //       "tenantId": "pg.citya",
    //       "targetId": "654cca5a-9ff7-4508-bfe5-5fc49956e96a",
    //       "statementType": "ANALYSIS",
    //       "basicSorDetails": [
    //         {
    //           "id": null,
    //           "amount": 157.98,
    //           "type": "W",
    //           "quantity": null
    //         },
    //         {
    //           "id": null,
    //           "amount": 280,
    //           "type": "L",
    //           "quantity": null
    //         }
    //       ],
    //       "sorDetails": [
    //         {
    //           "id": "b22e7f9b-817f-4515-9392-d79d093b282d",
    //           "statementId": "b17bdb3c-f0c4-4507-abaf-285d7afea03e",
    //           "sorId": "SOR_000002",
    //           "basicSorDetails": [
    //             {
    //               "id": "fae0aa76-bf46-449a-b7fe-964a7fc595c4",
    //               "amount": 157.98,
    //               "type": "W",
    //               "quantity": null
    //             },
    //             {
    //               "id": "ac6ed4b2-3f93-4210-bce5-c6140b0edf24",
    //               "amount": 280,
    //               "type": "L",
    //               "quantity": null
    //             }
    //           ],
    //           "lineItems": [
    //             {
    //               "id": "4452034c-7878-424f-8cb6-e243cad53890",
    //               "sorId": "SOR_000009",
    //               "sorType": "W",
    //               "referenceId": "b22e7f9b-817f-4515-9392-d79d093b282d",
    //               "basicSorDetails": [
    //                 {
    //                   "id": "0090b821-459f-4453-bbd3-e9ce45b67540",
    //                   "amount": 157.2,
    //                   "type": "W",
    //                   "quantity": 0.4
    //                 }
    //               ],
    //               "additionalDetails": {
    //                 "rateDetails": {
    //                   "id": null,
    //                   "tenantId": null,
    //                   "sorCode": null,
    //                   "sorId": "SOR_000009",
    //                   "sorType": null,
    //                   "sorSubType": null,
    //                   "sorVariant": null,
    //                   "isBasicVariant": null,
    //                   "uom": null,
    //                   "quantity": null,
    //                   "description": null,
    //                   "rate": 393,
    //                   "validFrom": "1702857600000",
    //                   "validTo": null,
    //                   "amountDetails": [
    //                     {
    //                       "id": null,
    //                       "type": "fixed",
    //                       "heads": "LA.2",
    //                       "amount": 200
    //                     }
    //                   ]
    //                 },
    //                 "sorDetails": {
    //                   "id": "SOR_000009",
    //                   "uom": "CUM",
    //                   "sorType": "W",
    //                   "quantity": 1,
    //                   "sorSubType": "CC",
    //                   "sorVariant": "FF",
    //                   "description": "C:C: (1:2:4) using 12 mm. size H.G. stone chips including the cost of all materials labour T&P sundries etc complete. (FF)"
    //                 }
    //               }
    //             },
    //             {
    //               "id": "fb1cb28f-7f72-4a01-9880-2e122ac9538a",
    //               "sorId": "SOR_000003",
    //               "sorType": "W",
    //               "referenceId": "b22e7f9b-817f-4515-9392-d79d093b282d",
    //               "basicSorDetails": [
    //                 {
    //                   "id": "82bdac99-3407-4ca2-913d-d4a354a7a917",
    //                   "amount": 0.78,
    //                   "type": "W",
    //                   "quantity": 0.0017
    //                 }
    //               ],
    //               "additionalDetails": {
    //                 "rateDetails": {
    //                   "id": null,
    //                   "tenantId": null,
    //                   "sorCode": null,
    //                   "sorId": "SOR_000003",
    //                   "sorType": null,
    //                   "sorSubType": null,
    //                   "sorVariant": null,
    //                   "isBasicVariant": null,
    //                   "uom": null,
    //                   "quantity": null,
    //                   "description": null,
    //                   "rate": 456,
    //                   "validFrom": "1702944000000",
    //                   "validTo": null,
    //                   "amountDetails": [
    //                     {
    //                       "id": null,
    //                       "type": "fixed",
    //                       "heads": "RA.5",
    //                       "amount": 200
    //                     }
    //                   ]
    //                 },
    //                 "sorDetails": {
    //                   "id": "SOR_000003",
    //                   "uom": "CUM",
    //                   "sorType": "W",
    //                   "quantity": 120,
    //                   "sorSubType": "CC",
    //                   "sorVariant": "GF",
    //                   "description": "P.C.C. Grade M25  Using Batching plant, Transit Mixer and concrete pump (Data for 120.00 Cum) [First Floor]"
    //                 }
    //               }
    //             },
    //             {
    //               "id": "e99f5df4-eb85-4677-ab86-98781f72e0fe",
    //               "sorId": "SOR_0000011",
    //               "sorType": "L",
    //               "referenceId": "b22e7f9b-817f-4515-9392-d79d093b282d",
    //               "basicSorDetails": [
    //                 {
    //                   "id": "71a91a81-8c70-4a57-b9e1-8474c54916d1",
    //                   "amount": 280,
    //                   "type": "L",
    //                   "quantity": 0.4
    //                 }
    //               ],
    //               "additionalDetails": {
    //                 "rateDetails": {
    //                   "id": null,
    //                   "tenantId": null,
    //                   "sorCode": null,
    //                   "sorId": "SOR_0000011",
    //                   "sorType": null,
    //                   "sorSubType": null,
    //                   "sorVariant": null,
    //                   "isBasicVariant": null,
    //                   "uom": null,
    //                   "quantity": null,
    //                   "description": null,
    //                   "rate": 700,
    //                   "validFrom": "1712580560000",
    //                   "validTo": "null",
    //                   "amountDetails": [
    //                     {
    //                       "id": "123",
    //                       "type": "fixed",
    //                       "heads": "FH.123",
    //                       "amount": 700
    //                     }
    //                   ]
    //                 },
    //                 "sorDetails": {
    //                   "id": "SOR_0000011",
    //                   "uom": "NOs",
    //                   "sorType": "L",
    //                   "quantity": 1,
    //                   "sorSubType": "S",
    //                   "sorVariant": "NA",
    //                   "description": "SKILLED FEMALE MULIA."
    //                 }
    //               }
    //             }
    //           ],
    //           "tenantId": "pg.citya",
    //           "isActive": true,
    //           "additionalDetails": {
    //             "rateDetails": {
    //               "id": "2",
    //               "tenantId": null,
    //               "sorCode": null,
    //               "sorId": "SOR_000002",
    //               "sorType": null,
    //               "sorSubType": null,
    //               "sorVariant": null,
    //               "isBasicVariant": null,
    //               "uom": null,
    //               "quantity": null,
    //               "description": "Earth Work",
    //               "rate": 439070.35,
    //               "validFrom": "1698796800000",
    //               "validTo": "1923609600000",
    //               "amountDetails": [
    //                 {
    //                   "id": null,
    //                   "type": "fixed",
    //                   "heads": "LC.6",
    //                   "amount": 4347.23
    //                 },
    //                 {
    //                   "id": null,
    //                   "type": "fixed",
    //                   "heads": "RA.5",
    //                   "amount": 15928
    //                 },
    //                 {
    //                   "id": null,
    //                   "type": "fixed",
    //                   "heads": "CA.4",
    //                   "amount": 32001.01
    //                 },
    //                 {
    //                   "id": null,
    //                   "type": "fixed",
    //                   "heads": "MHA.3",
    //                   "amount": 32001.01
    //                 },
    //                 {
    //                   "id": null,
    //                   "type": "fixed",
    //                   "heads": "LA.2",
    //                   "amount": 7095.84
    //                 },
    //                 {
    //                   "id": null,
    //                   "type": "fixed",
    //                   "heads": "MA.1",
    //                   "amount": 329936.45
    //                 }
    //               ]
    //             },
    //             "sorDetails": {
    //               "id": "SOR_000002",
    //               "uom": "CUM",
    //               "sorType": "W",
    //               "quantity": 120,
    //               "sorSubType": "CC",
    //               "sorVariant": "GF",
    //               "description": "P.C.C. Grade M25  Using Batching plant, Transit Mixer and concrete pump (Data for 120.00 Cum)"
    //             }
    //           }
    //         }
    //       ],
    //       "auditDetails": {
    //         "createdBy": "45614d29-9a50-4970-aba5-81b380745f48",
    //         "lastModifiedBy": "45614d29-9a50-4970-aba5-81b380745f48",
    //         "createdTime": 1718713193272,
    //         "lastModifiedTime": 1718713193272
    //       },
    //       "additionalDetails": {
    //         "estimateNumber": "ES/2024-25/000311"
    //       }
    //     }
    //   ]
      //look here need to uncomment once api works fine and check if the data is coming proper
     
      const location = useLocation();
  const { responseData, estimateId, oldData, number ,downloadStatus} = location.state || {};
     // const { state , refId } = useLocation()
      
      
      let statement = responseData?.statement;
  const history = useHistory();
  const [showActions, setShowActions] = useState(false);
  //const { tenantId } = Digit.Hooks.useQueryParams();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const [actionsMenu, setActionsMenu] = useState([]);
  const [isStateChanged, setStateChanged] = useState(``);
  const [toast, setToast] = useState({ show: false, label: "", type: "" });
  const menuRef = useRef();

  const loggedInUserRoles = Digit.Utils.getLoggedInUserDetails("roles");

  const closeMenu = () => {
    setShowActions(false);
}
  Digit.Hooks.useClickOutside(menuRef, closeMenu, showActions);

//   const requestCriteria = {
//     url: "/estimate/v1/_search",
//     params : revisionNumber ? {tenantId : tenantId , revisionNumber : revisionNumber} : {tenantId : tenantId , estimateNumber : estimateNumber}
//   };

  //fetching estimate data
  //const {isLoading: isDetailedEstimateLoading, data: detailedEstimate} = Digit.Hooks.useCustomAPIHook(requestCriteria);

  //fetching all the estimates for revision original values
//   const requestrevisionCriteria = {
//     url: "/estimate/v1/_search",
//     params : {tenantId : tenantId , estimateNumber : estimateNumber},
//     config : {
//       cacheTime : 0
//     },
//     changeQueryName: "allDetailedEstimate"
//   };

  //fetching estimate data
  //const {isLoading: isDetailedEstimatesLoading, data: allDetailedEstimate} = Digit.Hooks.useCustomAPIHook(requestrevisionCriteria);
  //fetching project data
//   const { isLoading: isProjectLoading, data: project } = Digit.Hooks.project.useProjectSearch({
//     tenantId,
//     searchParams: {
//       Projects: [
//         {
//           tenantId,
//           id: detailedEstimate?.estimates[0]?.projectId,
//         },
//       ],
//     },
//     config: {
//       enabled: !!detailedEstimate?.estimates[0]?.projectId,
//     },
//   });

  //here make a contract search based on the estimateNumber
//   let { isLoading: isLoadingContracts, data: contracts } = Digit.Hooks.contracts.useContractSearch({
//     tenantId,
//     filters: { tenantId, estimateIds: detailedEstimate?.estimates?.map((ob) => ob?.id) },
//     config: {
//       enabled: !isDetailedEstimateLoading && detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" ? true : false,
//       cacheTime: 0,
//     },
//   });
//   let validationData = Digit.Hooks.mukta.useEstimateSearchValidation({detailedestimates : allDetailedEstimate,tenantId, t});

  //fetching all work orders for a particular estimate
  //let allContract = contracts;
  //getting the object which will be in workflow, as 1:1:1 mapping is there, one one inworkflow workorder will be there for one estimate
  //let inWorkflowContract = allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.[0];
  //let isCreateContractallowed = !(allContract?.filter((ob) => ob?.wfStatus !== "REJECTED")?.length > 0) && !inWorkflowContract;

//   useEffect(() => {
//     let isUserContractCreator = loggedInUserRoles?.includes("WORK_ORDER_CREATOR");
//     if (
//       detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" &&
//       isUserContractCreator &&
//       !actionsMenu?.find((ob) => ob?.name === "CREATE_CONTRACT") && (isCreateContractallowed == true && (allContract !== undefined || allContract?.length == 0))
//     ) {
//       setActionsMenu((prevState) => [
//         ...prevState,
//         {
//           name: "CREATE_CONTRACT",
//         },
//       ]);
//     }
//     //checking if any work order is inworflow, if it is then view contract will be shown otherwise create contract

//     //if contract is already there just remove the prevState and push View contract state
//     if (contracts?.[0]?.contractNumber && !isCreateContractallowed) {
//       setActionsMenu((prevState) => [
//         ...prevState,
//         {
//           name: "VIEW_CONTRACT",
//         },
//       ]);
//     }

//     let isUserEstimateCreater = loggedInUserRoles?.includes("ESTIMATE_CREATOR");
//     //Checking if REVSION ESTIMATE can be created or not.
//     let isRevisionEstimateInWorkflow = detailedEstimate?.estimates.filter((ob) => ob?.status === "INWORKFLOW")?.length > 0;
//     if( detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" && !isRevisionEstimateInWorkflow && isUserEstimateCreater && !actionsMenu?.find((ob) => ob?.name === "CREATE_REVISION_ESTIMATE"))
//     {
//       setActionsMenu((prevState) => [
//         ...prevState,
//         {
//           name: "CREATE_REVISION_ESTIMATE",
//         },
//       ]);
//     }
  
//   }, [detailedEstimate, isStateChanged, contracts]);

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
      Digit.Utils.downloadEgovPDF("analysisStatement/analysis-statement", { tenantId: tenantId ,referenceId:estimateId}, `analysis_statement-${number}.pdf`);
  };

// Consolidated table will be sent overhere
//   const overheads = detailedEstimate?.estimates?.filter((ob) => revisionNumber ? (ob?.revisionNumber === revisionNumber) : (ob?.businessService === "ESTIMATE" || !(ob?.revisionNumber)))?.[0]?.estimateDetails?.filter((row) => row?.category?.includes("OVERHEAD") && row?.isActive);
//   const tableHeaderOverheads = [t("WORKS_SNO"), t("WORKS_OVERHEAD"), t("WORKS_PERCENTAGE"), t("WORKS_AMOUNT")];
//   const tableRowsOverheads = overheads?.map((row, index) => {
//     return [
//       index + 1,
//       t(`ES_COMMON_OVERHEADS_${row?.name}`),
//       row?.additionalDetails?.row?.name?.type?.includes("percent") ? `${row?.additionalDetails?.row?.name?.value}%` : t("WORKS_LUMPSUM"),
//       Digit.Utils.dss.formatterWithoutRound(row?.amountDetail?.[0]?.amount?.toFixed(2), "number"),
//     ];
//   });
//   const totalAmountOverheads = overheads?.reduce((acc, row) => row?.amountDetail?.[0]?.amount + acc, 0);
//   tableRowsOverheads?.push(["", "", t("RT_TOTAL"), Digit.Utils.dss.formatterWithoutRound(totalAmountOverheads, "number")]);
//   const overheadItems = {
//     headers: tableHeaderOverheads,
//     tableRows: tableRowsOverheads,
//     tableStyles: {
//       rowStyle: {},
//       cellStyle: [{}, { width: "50vw", whiteSpace: "break-spaces", wordBreak: "break-all" }, { textAlign: "left" }, { textAlign: "right" }],
//     },
//   };

// let InfoCardData = {
//   "Info": "STATEMENT_ANALYSIS_INFO_LABEL",
//   "reasons": [
//     "STATEMENT_ANALYSIS_INFO_1",
//     "STATEMENT_ANALYSIS_INFO_2",
//     "STATEMENT_ANALYSIS_INFO_3",
//     "STATEMENT_ANALYSIS_INFO_4"
//   ]
// }

  const config = data(statement?.[0],statement,oldData);

  //if (isProjectLoading || isDetailedEstimateLoading | isDetailedEstimatesLoading) return <Loader />;

  return (
    <div className={`employee-main-application-details ${"analysis-details"}`}>
      <div className={"employee-application-details"} style={{ marginBottom: "24px", alignItems: "center" }}>
        <Header className="works-header-view" styles={{ margin: "0px" }}>
          {t("ESTIMATE_ANALYSIS_STATEMENT")}
        </Header>
        {downloadStatus && (
          // <MultiLink onHeadClick={() => HandleDownloadPdf()} downloadBtnClassName={"employee-download-btn-className"} label={t("CS_COMMON_DOWNLOAD")} />
          <Button
            label={t("CS_COMMON_DOWNLOAD")}
            onClick={() => HandleDownloadPdf()}
            className={"employee-download-btn-className"}
            variation={"teritiary"}
            type="button"
            icon={"FileDownload"}
          />
        )}
      </div>
      <div>
        <InfoCard
          populators={{
            name: "doc-banner-infoCard",
          }}
          variant="default"
          text={t("STATEMENT_ANALYSIS_INFO_RATE")}
          label={t("CS_INFO")}
          style={{ margin: "0px", maxWidth: "100%", marginBottom: "1.5rem" }}
        />
        {/* <CitizenInfoLabel className="doc-banner" textType={"Componenet"} style={{margin:"0px", maxWidth:"100%", marginBottom:"1.5rem"}} info={t("CS_INFO")} text={t("STATEMENT_ANALYSIS_INFO_RATE")}  /> */}
      </div>
      <ViewComposer data={config} isLoading={false} />
      {toast?.show && <Toast label={toast?.label} type={toast?.type} isDleteBtn={true} onClose={handleToastClose}></Toast>}
      <>
        <ActionBar
          actionFields={[<Button type={"button"} label={t("STATEMENT_GO_BACK")} variation={"primary"} onClick={() => history.goBack()}></Button>]}
          setactionFieldsToRight={true}
          className={"new-actionbar"}
        />
        {/* {detailedEstimate?.estimates?.filter((ob) => ob?.businessService !== "REVISION-ESTIMATE")?.[0]?.wfStatus === "APPROVED" && !isLoadingContracts && actionsMenu?.length > 0 ? (
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
        ) : null} */}
      </>
    </div>
  );
};

export default ViewAnalysisStatement;
