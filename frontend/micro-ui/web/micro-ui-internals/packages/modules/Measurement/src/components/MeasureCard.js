import { Button, Card } from "@egovernments/digit-ui-react-components";
import React, { useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureRow from "./MeasureRow";

const MeasureCard = ({ columns, values, consumedQty, setConsumedQty }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  values = [
    { id: 1, type: "text", desc: "caac", number: "", length: "", width: "", depth: "", quantity: "1" },
    { id: 2, type: "text", desc: "caac", number: "", length: "", width: "", depth: "", quantity: "1" },
    { id: 3, type: "text", desc: "caac", number: "", length: "", width: "", depth: "", quantity: "1" },
  ];
  const initialState = {
    tableState: values,
  };
  const reducer = (state, action) => {
    switch (action.type) {
      case "ADD_ROW":
        const { state: newRow } = action;
        return { ...state, tableState: [...state.tableState, newRow] };
      case "UPDATE_ROW":
        const {
          state: { id, value, row, type },
        } = action;
        const { tableState } = state;
        let findIndex = tableState.findIndex((row) => row.id === id);
        if (type === "number") tableState[findIndex].number = value;
        if (type === "length") tableState[findIndex].length = value;
        if (type === "width") tableState[findIndex].width = value;
        if (type === "depth") tableState[findIndex].depth = value;
        return { ...state, tableState };

      case "CLEAR_STATE":
        return { ...state, tableState: values };

      default:
        return state;
    }
  };

  const [state, dispatch] = useReducer(reducer, initialState);

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

  const renderBody = () => {
    return values?.map((value, index) => {
      return <MeasureRow value={value} index={index} key={index} state={state} dispatch={dispatch} />;
    });
  };

  const calculate = () => {
    let total = 0;
    state.tableState.forEach((element) => {
      total += element.number * element.length * element.width * element.depth;
    });
    return total;
  };
  return (
    <Card>
      <table className="table reports-table sub-work-table" style={{ marginTop: "-2rem" }}>
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}
          <tr>
            <button
              onClick={() => {
                setConsumedQty(calculate());
                dispatch({ type: "CLEAR_STATE" });
              }}
            >
              Done
            </button>
          </tr>
        </tbody>
      </table>
    </Card>
  );
};

export default MeasureCard;
