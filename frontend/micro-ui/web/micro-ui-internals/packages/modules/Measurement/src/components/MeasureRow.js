import { Card, TextInput } from "@egovernments/digit-ui-react-components";
import React, { useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";

const MeasureRow = ({ value, index, state, dispatch }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();

  return (
    <tr key={index}>
      <td>{value.id}</td>
      <td>{value.type}</td>
      <td>{value.desc}</td>
      <td>
        <TextInput
          value={state.tableState[index].number} // Replace field1 with the appropriate field name.
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
          value={state.tableState[index].length} // Replace field1 with the appropriate field name.
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
          value={state.tableState[index].width} // Replace field1 with the appropriate field name.
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
          value={state.tableState[index].depth} // Replace field1 with the appropriate field name.
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "depth" },
            });
          }}
        />
      </td>
      <td>{state.tableState[index].quantity}</td>
    </tr>
  );
};

export default MeasureRow;
