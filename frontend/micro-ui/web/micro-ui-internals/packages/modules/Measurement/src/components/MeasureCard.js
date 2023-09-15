import { Button, Card } from "@egovernments/digit-ui-react-components";
import React, { useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureRow from "./MeasureRow";

const MeasureCard = ({ columns, consumedQty, setConsumedQty,setShowMeasureCard, initialState={}, setInitialState }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  
  
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
        let findIndex = tableState.findIndex((row,index) => {
          return index + 1 === id;
        });
        debugger;
        if (type === "number") tableState[findIndex].numItems = value;
        if (type === "length") tableState[findIndex].length = value;
        if (type === "width") tableState[findIndex].width = value;
        if (type === "height") tableState[findIndex].height = value;
        if(type === "quantity") tableState[findIndex].totalValue = value;
        return { ...state, tableState };

      case "CLEAR_STATE":
        return { ...state, tableState: initialState.tableState };

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
    t("Is Deduction?"),
    t("Description "),
    t("Number"),
    t("Length"),
    t("Width"),
    t("Depth/Height"),
    t("Quantity"),
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
    return state?.tableState?.map((value, index) => {
      return <MeasureRow value={value} index={index} key={index} state={state} dispatch={dispatch} />;
    });
  };

  const calculate = () => {
    let total = 0;
    state.tableState.forEach((element,index) => {
      var calculatedValue = element.numItems * element.length * element.width * element.height;
      total += calculatedValue;
      dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: calculatedValue, row: calculatedValue, type: "quantity" },
      });
    });

    console.log("total", state);
    
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
          <div style={{display: "flex", flexDirection: "row"}}>
            <Button label={"Clear"} onButtonClick={() => {
                dispatch({ type: "CLEAR_STATE" });
              }}/>
            <Button label={"Done"} onButtonClick={() => {
                setInitialState(state);
                setConsumedQty(calculate());
                setShowMeasureCard(false);
              }}/>
          </div>
          </tr>
        </tbody>
      </table>
    </Card>
  );
};

export default MeasureCard;
