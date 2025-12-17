import { AddIcon, CardLabelError, DeleteIcon, TextInput, TextArea } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";

const WOTermsAndConditions = (props) => {
  const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {});
  const [sessionFormData] = ContractSession;

  const formFieldName = "WOTermsAndConditions"; // this will be the key under which the data for this table will be present on onFormSubmit
  const { t, register, unregister, errors, setValue, getValues, formData } = props;

  //update sub project table with session data
  const renderWOTermsAndConditionsFromSession = () => {
    if (!sessionFormData?.WOTermsAndConditions) {
      return [
        {
          key: 1,
          isShow: true,
        },
      ];
    }
    let tableState = [];
    for (let i = 0; i < sessionFormData?.WOTermsAndConditions?.length; i++) {
      if (sessionFormData?.WOTermsAndConditions[i]) {
        tableState.push({
          key: i,
          isShow: true,
        });
      }
    }
    return tableState;
  };
  const [rows, setRows] = useState(renderWOTermsAndConditionsFromSession());

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "30rem" };
        break;
      default:
        obj = { width: "1rem" };
        break;
    }
    return obj;
  };

  const columns = [t("WORKS_SNO"), t("COMMON_DESC"), ""];
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

  const showDelete = () => {
    let countIsShow = 0;
    rows?.map((row) => row.isShow && countIsShow++);
    // if (countIsShow === 1) {
    // return false
    // }
    return true;
  };

  const errorCardStyle = { width: "100%" };

  const removeRow = (row) => {
    //check if only one row is present
    let totalRows = 0;
    for (let keys of Object.keys(formData?.[formFieldName])) {
      totalRows += 1;
    }
    if (totalRows === 1) {
      setValue(`${formFieldName}.${row.key}.description`, "");
    } else {
      //make a new state here which doesn't have this key
      const updatedState = rows?.map((e) => {
        if (e.key === row.key) {
          return {
            key: e.key,
            isShow: false,
          };
        }
        return e;
      });

      unregister(`${formFieldName}.${row.key}.description`);
      setRows((prev) => updatedState);
    }
  };

  const addRow = () => {
    const obj = {
      key: null,
      isShow: true,
    };
    obj.key = rows[rows.length - 1].key + 1;
    setRows((prev) => [...prev, obj]);
  };

  const renderBody = () => {
    let i = 0;
    return rows?.map((row, index) => {
      if (row.isShow) i++;
      return (
        row.isShow && (
          <tr key={index} style={{ height: "50%" }}>
            <td style={getStyles(1)}>{i}</td>

            <td style={getStyles(2)}>
              <div>
                {/* <TextInput
                    style={{ marginBottom: "0px" }}
                    name={`${formFieldName}.${row.key}.description`}
                    inputRef={register({
                      required: false,
                    })}
                  /> */}
                <TextArea
                  style={{ marginBottom: "0px" }}
                  name={`${formFieldName}.${row.key}.description`}
                  inputRef={register({
                    required: false,
                  })}
                />
              </div>
            </td>
            <td style={getStyles(8)}>
              {showDelete() && (
                <span className="icon-wrapper" onClick={() => removeRow(row)}>
                  <DeleteIcon fill={"#B1B4B6"} />
                </span>
              )}
            </td>
          </tr>
        )
      );
    });
  };

  return (
    <table className="table reports-table sub-work-table" style={{ marginTop: "-2rem" }}>
      <thead>
        <tr>{renderHeader()}</tr>
      </thead>
      <tbody>
        {renderBody()}
        <tr>
          <td colSpan={7} style={{ textAlign: "center" }} onClick={addRow}>
            <span>
              <AddIcon fill={"#C84C0E"} styles={{ margin: "auto", display: "inline", marginTop: "-2px" }} />
              <label style={{ marginLeft: "10px", fontWeight: "600", color: "#C84C0E" }}>{t("WORKS_ADD_ITEM")}</label>
            </span>
          </td>
        </tr>
      </tbody>
    </table>
  );
};

export default WOTermsAndConditions;
