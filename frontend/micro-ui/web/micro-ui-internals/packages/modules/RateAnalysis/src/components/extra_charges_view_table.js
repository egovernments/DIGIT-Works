import { Card, Header, Button, Loader, TextInput, DeleteIcon } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";
import SearchBar from "../../../Estimate/src/pageComponents/SearchBar";
import { has4DecimalPlaces } from "../utils/transformData";

import { calculateTotalAmount } from "../utils/transformData";

const ExtraChargesViewTable = (props) => {
  //new component only
  const { t } = useTranslation();

  const { pageType, arrayData, emptyTableMsg } = props;

  const [SORDetails, setSORDetails] = useState([]);

  useEffect(() => {
    setSORDetails(arrayData ? arrayData : []);
  }, [arrayData]);

  const columns = [
    { label: t("RA_SNO"), key: "sno" },

    { label: t("RA_NAME"), key: "description" },
    { label: t("RA_APPLIED"), key: "appliedOn" },
    { label: t("RA_CAL_TYPE"), key: "calculationType" },
    { label: t("RA_FIGURE"), key: "figure" },
    { label: t("RA_AMT"), key: "amount" },
  ];

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem",textAlign: "left" };
        break;
      case 2:
        obj = { width: "70rem",textAlign: "left" };
        break;
      case 3:
        obj = { width: "10rem" ,textAlign: "left"};
        break;
      case 4:
        obj = { width: "10rem" ,textAlign: "left"};
        break;
      case 5:
        obj = pageType === "VIEW" ? { width: "15rem", textAlign: "right" } : { width: "15rem" ,textAlign: "right"};
        break;
      case 6:
        obj = pageType === "VIEW" ? { width: "16rem", textAlign: "right" } : { width: "15rem" ,textAlign: "right"};
        break;
      case 7:
        obj = pageType === "VIEW" ? { width: "14rem", textAlign: "right" } : { width: "10rem" ,textAlign: "right"};
        break;
      case 8:
        obj = { width: "3%" };
        break;
      default:
        obj = { width: "1rem" };
        break;
    }
    return obj;
  };

  const sortedRows = SORDetails.filter((ob) => ob?.sorType === props?.config?.sorType).map((row, index) => ({
    sno: index + 1,

    description: row?.description,
    appliedOn: row?.appliedOn ? t(row?.appliedOn) : row?.appliedOn,
    calculationType: row?.calculationType,
    figure: Digit.Utils.dss.formatterWithoutRound(parseFloat(row?.figure).toFixed(2), "number", undefined, true, undefined, 2),
    amount: Digit.Utils.dss.formatterWithoutRound(parseFloat(row?.amount).toFixed(2), "number", undefined, true, undefined, 2),
  }));

  return (
    <div
      style={{
        paddingRight: "0%",
      }}
    >
      <div className="search-sor-container">
        <span className={pageType !== "VIEW"?"search-sor-label":"card-section-header"} style={pageType !== "VIEW"?{}:{marginBottom:"-20px"}}>{t(`RA_${props?.config?.sorType}_HEADER`)}</span>
      </div>
      <table className="table reports-table sub-work-table">
        <thead>
          <tr>
            {columns.map((column, index) => (
              <th key={index} style={getStyles(index + 1)}>{column.label}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {/*renderBody*/}
          {sortedRows.length > 0 ? (
            sortedRows.map((row, rowIndex) => (
              <tr key={rowIndex}>
                {columns.map((column, columnIndex) => (
                  <td key={columnIndex} style={getStyles(columnIndex + 1)}>
                    {row[column.key]}
                  </td>
                ))}
              </tr>
            ))
          ) : (
            <td colSpan={8} style={{ textAlign: "center" }}>
              {t(emptyTableMsg)}
            </td>
          )}

          {sortedRows.length > 0 && pageType === "VIEW" && (
            <tr>
              <td colSpan={5} style={{ textAlign: "right" }}>
                {t("RA_TOTAL")}
              </td>
              <td style={{ textAlign: "right" }}>{calculateTotalAmount(arrayData)}</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ExtraChargesViewTable;