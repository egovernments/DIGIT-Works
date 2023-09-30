import { Card, TextInput, Amount, Dropdown, DeleteIcon } from "@egovernments/digit-ui-react-components";
import React, { useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import { Fragment } from "react";

const MeasureRow = ({ value, index, state, dispatch, isView, isEstimate }) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();


  return (
    <tr key={index}>
      <td>{state.tableState[index].sNo}</td>
      {
        !isEstimate ? <>
          <td>{state.tableState[index].isDeduction ? t("MB_YES") : t("MB_NO")}</td>
          <td>{state.tableState[index].description}</td>
        </> :
          <>
            <td>
              <Dropdown t={t} select={(e) => {
                dispatch({
                  type: "UPDATE_ROW",
                  state: { id: index + 1, value: e, row: value, type: "isDeduction" },
                });
              }} option={[t("MB_YES"), t("MB_NO")]} selected={state.tableState[index].isDeduction} />
            </td>
            <td>
              <TextInput
                value={state.tableState[index].description}
                onChange={(newValue) => {
                  dispatch({
                    type: "UPDATE_ROW",
                    state: { id: index + 1, value: newValue.target.value, row: value, type: "description" },
                  });
                }}
                disable={isView}
              />
            </td>

          </>
      }

      <td>
        <TextInput
          value={state.tableState[index].number}
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "number" },
            });
          }}
          disable={isView}
        />
      </td>
      <td>
        <TextInput
          value={state.tableState[index].length}
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "length" },
            });
          }}
          disable={isView}
        />
      </td>
      <td>
        <TextInput
          value={state.tableState[index].width}
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "width" },
            });
          }}
          disable={isView}
        />
      </td>
      <td>
        <TextInput
          value={state.tableState[index].height}
          onChange={(newValue) => {
            dispatch({
              type: "UPDATE_ROW",
              state: { id: index + 1, value: newValue.target.value, row: value, type: "height" },
            });
          }}
          disable={isView}
        />
      </td>
      <td>{state.tableState[index].noOfunit}</td>
      {isEstimate && <td><span className="icon-wrapper" onClick={(newValue) => {
        dispatch({
          type: "REMOVE_ROW",
          id: index + 1,
        });
      }}>
        <DeleteIcon fill={"#B1B4B6"} />
      </span></td>}

    </tr>
  );
};

export default MeasureRow;