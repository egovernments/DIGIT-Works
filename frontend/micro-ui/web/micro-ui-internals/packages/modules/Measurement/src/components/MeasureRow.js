import { TextInput, Dropdown, DeleteIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { Fragment } from "react";

const MeasureInputAtom = ({ id, row, mode, disable = false, fieldKey, value, dispatch }) => (
  <td>
    <TextInput
      value={value}
      type={fieldKey == "description" ? "text" : "number"}
      onChange={(newValue) => {
        dispatch({
          type: "UPDATE_ROW",
          state: { id: id, value: newValue.target.value, row: row, type: fieldKey },
        });
      }}
      disable={disable}
    />
  </td>
);

const MeasureRow = ({ value, index, rowState, dispatch, mode }) => {
  const { t } = useTranslation();
  const options = [
    { label: t("MB_YES"), code: true },
    { label: t("MB_NO"), code: false },
  ];
  return (
    <tr key={index}>
      <td>{rowState?.sNo}</td>
      {mode != "CREATEALL" ? (
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
                  state: { id: index + 1, value: e?.code, row: value, type: "isDeduction" },
                });
              }}
              optionKey={"label"}
              option={options}
              selected={options?.filter((row) => row?.code == rowState?.isDeduction)?.[0]}
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

      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"number"} id={index + 1} value={rowState?.["number"]} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"length"} id={index + 1} value={rowState?.["length"]} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"width"} id={index + 1} value={rowState?.["width"]} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"height"} id={index + 1} value={rowState?.["height"]} />
      <td>{rowState?.noOfunit}</td>
      {mode == "CREATEALL" && (
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
