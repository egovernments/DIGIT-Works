import { Button, Card, Toast } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureRow from "./MeasureRow";

const MeasureCard = ({ columns, consumedQty, setConsumedQty,setShowMeasureCard, initialState={}, setInitialState }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const [total,setTotal] = useState(consumedQty);

  const validate = (value) => {
    if(value === null || value === undefined || value === "" || value === "0"){
      return 1;
    }else{
      return value;
    }
  }
  
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
        if (type === "number") tableState[findIndex].currentNumber = value;
        if (type === "length") tableState[findIndex].currentLength = value;
        if (type === "width") tableState[findIndex].currentWidth = value;
        if (type === "height") tableState[findIndex].currentHeight = value;
        const element = tableState[findIndex];
        const calculatedValue =
        (validate(element.currentNumber)) *
        (validate(element.currentLength)) *
        (validate(element.currentWidth)) *
        (validate(element.currentHeight));

        tableState[findIndex].totalValue = calculatedValue;
        setTotal(tableState.reduce((acc, curr) => acc+validate(curr.totalValue),0));

        
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
  
  return (
    <Card>
      <table className="table reports-table sub-work-table" >
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}
          <tr>
          <td colSpan={"4"}>
          <div style={{display: "flex", flexDirection: "row"}}>
            <Button label={"Clear"} onButtonClick={() => {
                dispatch({ type: "CLEAR_STATE" });
              }}/>
            <Button label={"Done"} onButtonClick={() => {
              console.log("state",state);
                setInitialState(state);
                setConsumedQty(total);
                setShowMeasureCard(false);
              }}/>
          </div>
           </td> 
          <td colSpan={"4"}>
          SubTotal: {total}
          </td> 
          </tr>
        </tbody>
      </table>
    </Card>
  );
};

export default MeasureCard;
