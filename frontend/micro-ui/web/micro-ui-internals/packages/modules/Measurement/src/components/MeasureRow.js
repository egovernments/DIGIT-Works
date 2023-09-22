import { Card, TextInput ,Amount} from "@egovernments/digit-ui-react-components";
import React, { useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";

const MeasureRow = ({ value, index, state, dispatch }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();


  return (
    <tr key={index}>
      <td>{index + 1}</td>
      <td>{state.tableState[index].isDeduction? "YES": "NO"}</td>
      <td>{state.tableState[index].description}</td>
      <td>
        <TextInput
          value={state.tableState[index].currentNumber} 
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "number" },
            });
          }}
        />
      </td>
      <td>
        <TextInput
          value={state.tableState[index].currentLength} 
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "length" },
            });
          }}
        />
      </td>
      <td>
        <TextInput
          value={state.tableState[index].currentWidth} 
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "width" },
            });
          }}
        />
      </td>
      <td>
        <TextInput
          value={state.tableState[index].currentHeight} 
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "height" },
            });
          }}
        />
      </td>
      <td>{state.tableState[index].totalValue}</td>
    </tr>
  );
};

export default MeasureRow;
