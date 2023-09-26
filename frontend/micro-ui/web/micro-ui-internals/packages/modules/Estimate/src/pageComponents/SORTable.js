import { AddIcon } from '@egovernments/digit-ui-react-components';
import React, { useReducer } from 'react'
import { useTranslation } from 'react-i18next';

const SORTable = ({props}) => {
  const {t} = useTranslation();

  const initialState = {
    tableState: [
    {
      "sno": "1",
      "overhead": "Overhead",
      "percentage": "10",
      "amount": "1000"
    }
  ]};

  const reducer = (state, action) => {
    switch (action.type) {
      case "ADD_ROW":
        const newRow = {
        sno: state.tableState.length + 1, // Generate a new serial number
        overhead: "", // Add initial values for other columns
        percentage: "",
        amount: ""
      };
        return { ...state, tableState: [...state.tableState, newRow] };
      case "UPDATE_ROW":
        const {
          state: { id, value, row, type },
        } = action;
        const { tableState } = state;
        let findIndex = tableState.findIndex((row, index) => {
          return index + 1 === id;
        });
        
        return { ...state, tableState };

      case "CLEAR_STATE":
        return { ...state, tableState: initialState.tableState };

      default:
        return state;
    }
  };
  const [state, dispatch] = useReducer(reducer, initialState);

  const columns = [t("WORKS_SNO"), t("WORKS_OVERHEAD"), t("WORKS_PERCENTAGE"), t("WORKS_AMOUNT"), t("CS_COMMON_ACTION")];
  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "45%" };
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
    return state?.tableState?.map((item, index) => {
      return (
        <tr key={index}>
          <td style={getStyles(1)}>{item.sno}</td>
          <td style={getStyles(2)}>{item.overhead}</td>
          <td style={getStyles(3)}>{item.percentage}</td>
          <td style={getStyles(4)}>{item.amount}</td>
          <td style={getStyles(5)}>
            <span className="icon-container">
              <i className="material-icons">delete</i>
            </span>
          </td>
        </tr>
      );
    });
  };
  return (
    <table className="table reports-table sub-work-table">
      <thead>
        <tr>{renderHeader()}</tr>
      </thead>
      <tbody>
        {renderBody()}
        <tr>
          <td colSpan={5} style={{ textAlign: "center" }} >
            <span onClick={() => {
            console.log(state);
            dispatch({ type: "ADD_ROW" });
          }}>
              <AddIcon fill={"#F47738"} styles={{ margin: "auto", display: "inline", marginTop: "-2px" }} />
              <label style={{ marginLeft: "10px", fontWeight: "600", color: " #F47738" }}>{t("WORKS_ADD_OVERHEAD")}</label>
            </span>
          </td>
        </tr>
      </tbody>
    </table>
  )
}

export default SORTable