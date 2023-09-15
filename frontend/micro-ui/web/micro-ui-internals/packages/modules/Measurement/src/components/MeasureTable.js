import { AddIcon, Card, TextInput } from "@egovernments/digit-ui-react-components";
import React, { useState, Fragment } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureCard from "./MeasureCard";

const MeasureTable = ({ columns }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const [consumedQty, setConsumedQty] = useState(0);

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "30%" };
        break;
      case 3:
        obj = { width: "27rem" };
        break;
      case 4:
        obj = { width: "27rem" };
        break;
      case 5:
        obj = { width: "3%" };
        break;
      default:
        obj = { width: "92rem" };
        break;
    }
    return obj;
  };

  columns = [
    t("WORKS_SNO"),
    t("Description"),
    t("Unit"),
    t("Rate"),
    t("Approved Quantity"),
    t("Consumed Quantity"),
    t("Current MB Entry"),
    t("Amount for current entry"),
  ];
  const renderHeader = () => {
    return columns?.map((key, index) => {
      return (
        <th key={index} style={getStyles(index + 1)}>
          {" "}
          {key}{" "}
        </th>
      );
    });
  };

  const data = [
    {
      no: 1,
      desc: "Construction of CC Road",
      unit: "Sq. Mtr",
      rate: "1000",
      approvedQty: "100",
      consumedQty: "50",
      currentMBEntry: "10",
      amount: "10000",
    },
    {
      no: 1,
      desc: "Construction of CC Road",
      unit: "Sq. Mtr",
      rate: "1000",
      approvedQty: "100",
      consumedQty: "50",
      currentMBEntry: "10",
      amount: "10000",
    },
    {
      no: 1,
      desc: "Construction of CC Road",
      unit: "Sq. Mtr",
      rate: "1000",
      approvedQty: "100",
      consumedQty: "50",
      currentMBEntry: "10",
      amount: "10000",
    },
  ];

  const renderBody = () => {
    return data?.map((row, index) => {
      const [showMeasureCard, setShowMeasureCard] = useState(false);

      return (
        <>
          <tr key={index}>
            <td>{row.no}</td>
            <td>{row.desc}</td>
            <td>{row.unit}</td>
            <td>{row.rate}</td>
            <td>{row.approvedQty}</td>
            <td>
              <div className="measurement-table-input">
                <TextInput style={{ width: "80%" }} value={consumedQty} onChange={() => { }} />
                <button
                  onClick={() => {
                    setShowMeasureCard(!showMeasureCard);
                  }}
                >
                  <AddIcon fill={"#F47738"} styles={{ margin: "auto", display: "inline", marginTop: "-2px", width: "20px", height: "20px" }} />
                </button>
              </div>
            </td>
            <td>{row.currentMBEntry}</td>
            <td>{row.amount}</td>
          </tr>
          {showMeasureCard && (
            <tr>
              <MeasureCard columns={[]} values={[]} consumedQty={consumedQty} setConsumedQty={setConsumedQty} />
            </tr>
          )}
        </>
      );
    });
  };

  return (
    <Card>
      <table className="table reports-table sub-work-table" style={{ marginTop: "-2rem" }}>
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>{renderBody()}</tbody>
      </table>
    </Card>
  );
};

export default MeasureTable;
