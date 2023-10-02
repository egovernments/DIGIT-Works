import { TextInput, Dropdown, DeleteIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { Fragment } from "react";

const MeasureInputAtom = ({ id, row, isView = false, fieldKey, value, dispatch }) => (
  <td>
    <TextInput
      value={value}
      type="number"
      onChange={(newValue) => {
        dispatch({
          type: "UPDATE_ROW",
          state: { id: id, value: newValue.target.value, row: row, type: fieldKey },
        });
      }}
      disable={isView}
    />
  </td>
);

const MeasureRow = ({ value, index, rowState, dispatch, isView, isEstimate }) => {
  const { t } = useTranslation();
  return (
    <tr key={index}>
      <td>{rowState?.sNo}</td>
      {!isEstimate ? (
        <>
          <td>{rowState?.isDeduction ? t("MB_YES") : t("MB_NO")}</td>
          <td>{rowState?.description}</td>
        </>
      ) : (
        <>
          <td>
            <Dropdown
              t={t}
              select={(e) => {
                dispatch({
                  type: "UPDATE_ROW",
                  state: { id: index + 1, value: e, row: value, type: "isDeduction" },
                });
              }}
              option={[t("MB_YES"), t("MB_NO")]}
              selected={rowState?.isDeduction}
            />
          </td>
          <MeasureInputAtom
            dispatch={dispatch}
            row={value}
            fieldKey={"description"}
            id={index + 1}
            key={"description"}
            value={rowState?.["description"]}
          />
        </>
      )}

      <MeasureInputAtom dispatch={dispatch} row={value} isView={isView} fieldKey={"number"} id={index + 1} value={rowState?.["number"]} />
      <MeasureInputAtom dispatch={dispatch} row={value} isView={isView} fieldKey={"length"} id={index + 1} value={rowState?.["length"]} />
      <MeasureInputAtom dispatch={dispatch} row={value} isView={isView} fieldKey={"width"} id={index + 1} value={rowState?.["width"]} />
      <MeasureInputAtom dispatch={dispatch} row={value} isView={isView} fieldKey={"height"} id={index + 1} value={rowState?.["height"]} />
      <td>{rowState?.noOfunit}</td>
      {isEstimate && (
        <td>
          <span
            className="icon-wrapper"
            onClick={(newValue) => {
              dispatch({
                type: "REMOVE_ROW",
                id: index + 1,
              });
            }}
          >
            <DeleteIcon fill={"#B1B4B6"} />
          </span>
        </td>
      )}
    </tr>
  );
};

export default MeasureRow;
