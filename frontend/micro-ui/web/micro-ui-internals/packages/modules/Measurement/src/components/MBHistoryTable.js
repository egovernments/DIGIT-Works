import { Card, Header, Loader } from "@egovernments/digit-ui-react-components";
import React,{Fragment} from "react";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import { Link } from "react-router-dom";
import { findMusterRollNumber } from "../utils/transformEstimateData";
import {  Button,TextBlock } from "@egovernments/digit-ui-components";


const CustomCollapsibleTable = ({ children, isTableCollapsed }) => {
  return <div className={`custom-collapsible-table ${isTableCollapsed ? "collapsed" : ""}`}>{children}</div>;
};

const MeasurementHistory = ({ contractNumber, measurementNumber }) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const history = useHistory();
  const [isTableCollapsed, setIsTableCollapsed] = useState(true);
  const searchparams = new URLSearchParams(location.search);
  contractNumber = contractNumber ? contractNumber : searchparams.get("workOrderNumber");

  const toggleTableCollapse = () => {
    setIsTableCollapsed((prevState) => !prevState);
  };

  const requestCriteria = {
    url : "/mukta-services/measurement/_search",

    body: {
      "contractNumber" : contractNumber,
      "tenantId" : tenantId,
      "key" : "View",
    }

  }


  const {isLoading, data} = Digit.Hooks.useCustomAPIHook(requestCriteria);


  //extracting the numeric part from the measurement number
  const extractNumericPart = (mbNumber) => {
    const parts = mbNumber.split("/");
    if (parts.length === 3 && parts[2]) {
      const numericPart = parts[2].split("-");
      if (numericPart.length === 2) {
        return parseInt(numericPart[1]);
      }
    }
    return 0; // Return 0 if the format is not as expected
  };

  function formatStatus(status) {
    if (!status) return '';
  
    // Convert to lowercase and capitalize the first letter
    const formattedStatus = status.charAt(0).toUpperCase() + status.slice(1).toLowerCase();
  
    // Replace underscores with spaces
    return formattedStatus.replace(/_/g, ' ');
  }

  const columns = [
    { label: t("MB_SNO"), key: "sno" },
    { label: t("MB_REFERENCE_NUMBER"), key: "mbref" },
    { label: t("MB_MUSTER_ROLL_ID"), key: "musterid" },
    { label: t("MB_DATE"), key: "mbDate" },
    { label: t("MB_PERIOD"), key: "period" },
    { label: t("MB_STATUS"), key: "status" },
    { label: t("MB_ONLY_AMOUNT"), key: "amount" },
  ];
  let relevantMbs = window?.location.href.includes("/measurement/view") ? data?.allMeasurements?.filter(obj => obj.auditDetails.lastModifiedTime < data?.allMeasurements.find(o => o.measurementNumber === measurementNumber).auditDetails.lastModifiedTime) : data?.allMeasurements;
  const filteredArray =  relevantMbs && relevantMbs?.length > 0 && relevantMbs?.code !== "NO_MEASUREMENT_ROLL_FOUND"? relevantMbs?.filter((item) => item.measurementNumber !== measurementNumber && item?.wfStatus === "APPROVED") : [];

  const sortedRows = (filteredArray || [])
    .sort((a, b) => {
      const numericA = extractNumericPart(a.measurementNumber);
      const numericB = extractNumericPart(b.measurementNumber);
      return numericA - numericB; // Sort in ascending order
    })
    .reverse()
    .map((item, index) => ({
      sno: index + 1,
      mbref: {link:true, value:item?.measurementNumber, search:`?tenantId=${tenantId}&workOrderNumber=${contractNumber}&mbNumber=${item?.measurementNumber}`, pathname:`/${window?.contextPath}/employee/measurement/view`},
      musterid: {link:true, value:findMusterRollNumber(data?.musterRolls, item?.measurementNumber, item?.additionalDetails?.startDate, item?.additionalDetails?.endDate), search:`?tenantId=${tenantId}&musterRollNumber=${findMusterRollNumber(data?.musterRolls, item?.measurementNumber, item?.additionalDetails?.startDate, item?.additionalDetails?.endDate)}`, pathname:`/${window.contextPath}/employee/attendencemgmt/view-attendance`},
      mbDate: Digit.Utils.pt.convertEpochToDate(item?.entryDate),
      period:  `${Digit.DateUtils.ConvertEpochToDate(item?.additionalDetails?.startDate)} - ${Digit.DateUtils.ConvertEpochToDate(item?.additionalDetails?.endDate)}` || t("NA"),
      status: formatStatus(item?.wfStatus),
      amount: Digit.Utils.dss.formatterWithoutRound(Math.round(parseFloat(item?.additionalDetails?.totalAmount)).toFixed(2), "number",undefined,true,undefined,2),
    }));

  if (isLoading) {
    return <Loader></Loader>;
  }

  return (
    // <Card className="override-card">
    <>
      {/* <Header className="works-header-view">{t("MB_HISTORY")}</Header> */}
      <TextBlock subHeader={t("MB_HISTORY")} subHeaderClassName={"mb-history-header"}></TextBlock>
      {!isTableCollapsed && (
        <CustomCollapsibleTable isTableCollapsed={isTableCollapsed}>
          <table className="table reports-table sub-work-table mb-history-table">
            <thead>
              <tr>
                {columns.map((column, index) => (
                  <th key={index}>{column.label}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {sortedRows?.length > 0 ? (
                sortedRows.map((row, rowIndex) => (
                  <tr key={rowIndex}>
                    {columns.map((column, columnIndex) => (
                      <td key={columnIndex}>
                        {row?.[column.key]?.link == true ? (
                          <Link
                            to={{
                              pathname: row?.[column.key]?.pathname,
                              search: row?.[column.key]?.search,
                            }}
                            style={{ color: "#C84C0E" }}
                          >
                            <Button
                              size={"medium"}
                              style={{ padding: "0px" }}
                              variation={"link"}
                              onClick={toggleTableCollapse}
                              label={row?.[column.key]?.value}
                            ></Button>
                          </Link>
                        ) : (
                          row[column.key]
                        )}
                      </td>
                    ))}
                  </tr>
                ))
              ) : (
                <td colSpan={7} style={{ marginLeft: "10px", color: "#9E9E9E", textAlign: "center" }}>
                  {t("WORKS_NO_DATA_PRESENT_IN_MB")}
                </td>
              )}
            </tbody>
          </table>
        </CustomCollapsibleTable>
      )}
      <Button
        style={{ padding: "0px" }}
        variation={"teritiary"}
        onClick={toggleTableCollapse}
        icon={isTableCollapsed ? "ExpandMore" : "ExpandLess"}
        isSuffix={true}
        label={isTableCollapsed ? t("MB_SHOW_HISTORY") : t("MB_HIDE_HISTORY")}
      ></Button>
    </>

    // </Card>
  );
};

export default MeasurementHistory;
