import { Button, CardLabelError, CardSectionHeader, CloseSvg } from "@egovernments/digit-ui-react-components";
import React, { useReducer, Fragment, useState } from "react";
import { useTranslation } from "react-i18next";
import MeasureRow from "./MeasureRow";

const getStyles = (index) => {
  let obj = {};
  switch (index) {
    case 1:
      obj = { width: "0.5rem" };
      break;
    case 2:
      obj = { width: "0.5rem" };
      break;
    case 3:
      obj = { width: "45rem" };
      break;
    case 4:
      obj = { width: "3rem" };
      break;
    case 5:
      obj = { width: "3rem" };
      break;
    default:
      obj = { width: "3rem" };
      break;
  }
  return obj;
};
const validate = (value) => {
  if (value === null || value === undefined || value === "" || value === 0 || value === "0") {
    return 1;
  } else {
    return value;
  }
};
const initialValue = (element) => {
  if (element.number !== "" && element.number !== "0" && element.number !== 0) return false;
  if (element.width !== "" && element.width !== "0" && element.width !== 0) return false;
  if (element.length !== "" && element.length !== "0" && element.length !== 0) return false;
  if (element.height !== "" && element.height !== "0" && element.height !== 0) return false;
  return true;
};

{
  /* <Amount customStyle={{ textAlign: 'right'}} value={Math.round(value)} t={t}></Amount> */
}
const MeasureCard = React.memo(({ columns, fields = [], register, setValue, tableData, tableKey, tableIndex, unitRate, mode }) => {
  const { t } = useTranslation();
  const [error, setError] = useState(false)

  const reducer = (state, action) => {
    // console.log(state, action, "reducer");
    switch (action.type) {
      case "ADD_ROW":
        const { state: newRow } = action;
        return [...state, newRow];
      case "UPDATE_ROW":
        const {
          state: { id, value, row, type },
        } = action;
        let findIndex = state.findIndex((row, index) => {
          return index + 1 === id;
        });
        state[findIndex][type] = value;

        const element = state[findIndex];
        let calculatedValue = validate(element.number) * validate(element.length) * validate(element.width) * validate(element.height);
        if (initialValue(element)) {
          calculatedValue = 0;
        }
        state[findIndex].noOfunit = calculatedValue ? calculatedValue?.toFixed(4) : 0;
        state[findIndex].rowAmount = unitRate * calculatedValue || 0;
        return [...state];
      case "REMOVE_ROW":
        const { id: rowIdToRemove } = action;
        const updatedTableState = state.filter((row, index) => index + 1 !== rowIdToRemove);
        return [...updatedTableState];
      case "CLEAR_STATE":
        setError(false);
        const clearedTableState = state.map((item) => ({
          ...item,
          height: 0,
          description : mode === "CREATE" ? item?.description : "",
          width: 0,
          length: 0,
          number: 0,
          noOfunit: 0,
          rowAmount: 0,
        }));
        return [...clearedTableState];

      default:
        return state;
    }
  };

  const [state, dispatch] = useReducer(reducer, fields);

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
    return state?.map((value, index) => {
      return <MeasureRow value={value} index={index} key={index} rowState={state?.[index]} dispatch={dispatch} mode={mode} fields={state}/>;
    });
  };

  const total = state?.reduce?.((acc, curr) => curr.isDeduction == true ? acc - parseFloat(curr?.noOfunit) :  acc + parseFloat(curr?.noOfunit), 0) || 0;
  return (
    <Fragment>
      <div>
      <CardSectionHeader style={{fontSize:"18px"}}>{t("WORKS_MEASUREMENT_TABLE_HEADER")}</CardSectionHeader>
      <span className="measure-table-header" onClick={() => {
        tableData[tableIndex].showMeasure = false;
        setValue(tableData);
      }}>
      <CloseSvg />
      </span>
      </div>
      <table className="table reports-table sub-work-table">
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}
          <tr>
            <td colSpan={"4"}>
            {error && <CardLabelError style={{width:"100%"}}>{t("MB_APPROVED_QTY_VALIDATION")}</CardLabelError>}
              <div style={{ display: "flex", flexDirection: "row" }}>
                { (
                  <>
                    {(mode == "CREATEALL" || mode == "CREATERE") && (
                      <Button
                        className={"outline-btn"}
                        label={t("MB_ADD_ROW")}
                        onButtonClick={() => {
                          dispatch({
                            type: "ADD_ROW",
                            state: {
                              sNo: state.length + 1,
                              targetId: "",
                              isDeduction: false,
                              description: "",
                              id: null,
                              height: 0,
                              width: 0,
                              length: 0,
                              number: 0,
                              noOfunit: 0,
                              rowAmount: 0,
                              consumedRowQuantity: 0,
                            },
                          });
                        }}
                      />
                    )}
                    {!(mode.includes("VIEW")) && <Button
                      className={"outline-btn"}
                      label={t("MB_CLEAR")}
                      onButtonClick={() => {
                        dispatch({ type: "CLEAR_STATE" });
                      }}
                    />}
                    {!(mode.includes("VIEW")) && <Button
                      className={"outline-btn"}
                      label={t("MB_DONE")}
                      onButtonClick={() => {
                        // check for deduction and set accordingly
                        const totalQuantity = state?.reduce((total, item) => item?.isDeduction == true ? total - parseFloat(item.noOfunit) :  total + parseFloat(item.noOfunit), 0);
                        if(mode === "CREATE" && (totalQuantity < 0 || totalQuantity > tableData[tableIndex]?.approvedQuantity - tableData[tableIndex]?.consumedQ))
                        setError(true);
                        else
                        {
                        tableData[tableIndex].measures = state;
                        tableData[tableIndex].amount = parseFloat(totalQuantity * unitRate).toFixed(2);
                        tableData[tableIndex].showMeasure = false;
                        tableData[tableIndex].currentMBEntry = totalQuantity;
                        setValue(tableData);
                        }                  
                        // setConsumedQty(total);
                        // setShowMeasureCard(false);
                      }}
                    />}
                  </>
                )}
              </div>
            </td>
            <td colSpan={"4"} style={{textAlign:"end"}}>
              {t("MB_SUBTOTAL")}: {parseFloat(total)?.toFixed(4)}
            </td>
          </tr>
        </tbody>
      </table>
    </Fragment>
  );
});

export default MeasureCard;
