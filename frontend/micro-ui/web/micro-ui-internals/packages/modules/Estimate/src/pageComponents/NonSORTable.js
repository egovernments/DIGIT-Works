import { AddIcon, DeleteIcon } from '@egovernments/digit-ui-react-components';
import React, { useReducer } from 'react'
import { useTranslation } from 'react-i18next';

const NonSORTable = ({props}) => {
  const {t} = useTranslation();

  const initialState = {
    tableState: [
    {
      "sno": "1",
      "Description": "Description",
      "unit ": "10",
      "rate" :"",
      "ApprovedQuantity": "",
      "ConsumedQuantity": "",
      "CurrentMBEntry": "",
      "AmountforcurrentEntry": ""
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
        switch (type) {
          case "number":
            tableState[findIndex].number = value;
            break;
          case "length":
            tableState[findIndex].length = value;
            break;
          case "width":
            tableState[findIndex].width = value;
            break;
          case "height":
            tableState[findIndex].height = value;
            break;
          default:

        }
        const element = tableState[findIndex];
        const calculatedValue =
          (validate(element.number)) *
          (validate(element.length)) *
          (validate(element.width)) *
          (validate(element.height));

        tableState[findIndex].noOfunit = calculatedValue;
        tableState[findIndex].rowAmount = unitRate * calculatedValue;
        setTotal(tableState.reduce((acc, curr) => acc + validate(curr.noOfunit), 0));


        return { ...state, tableState };

      case "CLEAR_STATE":
        return { ...state, tableState: initialState.tableState };

      default:
        return state;
    }
  };
  const [state, dispatch] = useReducer(reducer, initialState);

  const columns = [t("WORKS_SNO"), t("Description"), t("Unit"), t("Rate"), t("Approved Quantity"),  t("Consumed Quantity"), t("Current MB Entry"), t("Amount for current Entry"), t("Action")];
  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "40%" };
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
        case 6:
        obj = { width: "3%" };
        break;
        case 7:
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
          <td style={getStyles(2)}>{item.Description}</td>
          <td style={getStyles(3)}>{item.unit}</td>
          <td style={getStyles(4)}>{item.rate}</td>
          <td style={getStyles(5)}>{item.ApprovedQuantity}</td>
          <td style={getStyles(6)}>{item.ConsumedQuantity}</td>
          <td style={getStyles(7)}>{item.CurrentMBEntry}</td>
          <td style={getStyles(4)}>{item.AmountforcurrentEntry}</td>
          <td style={getStyles(5)}>
            {/* <span className="icon-container">
              <i className="material-icons">delete</i>
            </span> */}
            <span  className="icon-wrapper">
                    <DeleteIcon fill={"#B1B4B6"} />
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
          <td colSpan={9} style={{ textAlign: "center" }} >
            <span onClick={() => {
            console.log(state);
            dispatch({ type: "ADD_ROW" });
          }}>
              <AddIcon fill={"#F47738"} styles={{ margin: "auto", display: "inline", marginTop: "-2px" }} />
              <label style={{ marginLeft: "10px", fontWeight: "600", color: " #F47738" }}>{t("Add New NonSOR")}</label>
            </span>
          </td>
        
        </tr>
      </tbody>
    </table>
  )
}

export default NonSORTable









