import { TextInput, Dropdown, DeleteIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { Fragment } from "react";

function has4DecimalPlaces(number, decimalPlaces) {

  if(number == "" || isNaN(number))
    return true;
  var numStr = number.toString();
  // Using regex to check if its accepting upto 4 decimal places
  var regex = new RegExp(`^\\d+(\\.\\d{0,${decimalPlaces}})?$`);
  return regex.test(numStr);
}

const MeasureInputAtom = ({ id, row, mode, disable = false, fieldKey, value, dispatch, InputDecimalValidation }) => (
  <td>
    <TextInput
      value={fieldKey === "description" ? value : (value > 0 && value)}
      //value={value}
      type={fieldKey == "description" ? "text" : "number"}
      onChange={(newValue) => {
        if(InputDecimalValidation?.active){
          //calling the input validation here to check if the input is under provided decimal places
            if(has4DecimalPlaces(parseFloat(newValue.target.value), InputDecimalValidation?.noOfDecimalPlaces))
            dispatch({
              type: "UPDATE_ROW",
              state: { id: id, value: newValue.target.value, row: row, type: fieldKey },
            });
        }
        else
        {
          dispatch({
            type: "UPDATE_ROW",
            state: { id: id, value: newValue.target.value, row: row, type: fieldKey },
          });
        }
      }}
      disable={disable}
    />
  </td>
);

const MeasureRow = ({ value, index, rowState, dispatch, mode, fields }) => {
  const { t } = useTranslation();
  const options = [
    { label: t("MB_YES"), code: true },
    { label: t("MB_NO"), code: false },
  ];
  const InputDecimalValidation = {
    active : true,
    noOfDecimalPlaces : 4
  }
  return (
    <tr key={index}>
      <td><div style={{marginBottom:"21px"}}>{index + 1}</div></td>
      {mode != "CREATEALL" && mode != "CREATERE" ? (
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
            InputDecimalValidation={InputDecimalValidation}
          />
        </>
      )}

      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"number"} id={index + 1} value={rowState?.["number"]} InputDecimalValidation={InputDecimalValidation} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"length"} id={index + 1} value={rowState?.["length"]} InputDecimalValidation={InputDecimalValidation} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"width"} id={index + 1} value={rowState?.["width"]} InputDecimalValidation={InputDecimalValidation} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"height"} id={index + 1} value={rowState?.["height"]} InputDecimalValidation={InputDecimalValidation} />
      <td><div style={{marginBottom:"21px"}}>{rowState?.noOfunit}</div></td>
      {(mode == "CREATEALL" || mode == "CREATERE") && fields?.length > 1 && (
        <td>
          <span
            className="icon-wrapper"
            onClick={(newValue) => {
              //added this condition as user should not able to delete row if only one is present
              if(fields?.length > 1)
              dispatch({
                type: "REMOVE_ROW",
                id: index + 1,
              });
            }}
          >
            <DeleteIcon fill={"#FF9100"} />
          </span>
        </td>
      )}
    </tr>
  );
};

export default MeasureRow;
