import { AddIcon, TextInput, Amount, Button, Dropdown, Loader, DeleteIcon, TextArea } from "@egovernments/digit-ui-react-components";

import React, { Fragment, useEffect, useCallback, useState } from "react";
import { useTranslation } from "react-i18next";


const TableWithOutHead = (props) => {
   const { t } = useTranslation();
  
  const [fields,setFields]=useState(null);
  const{arrayData,qty,uom}=props;

  useEffect(()=>{
    setFields(props.arrayData);
  },[props]);

  const renderBody = () => {
    // Update the state with the new data

    return fields?.map((row, index) => {
      return (
        <tr key={index}>
          <td className="flex" style={{ width: "60%", margin: "0px", padding: "10px", textAlign: "left", paddingRight: "30px" }}>
            { index===2?`${t(row?.name)}/ ${t(qty)} ${t(uom)}`:t(row?.name)}
          </td>

          <td className="flex" style={{ width: "40%", margin: "0px", padding: "10px", textAlign: "right" }}>
            
          {/*Digit.Utils.dss.formatterWithoutRound(Math.round(parseFloat(row.amount)).toFixed(2),"number",undefined,true,undefined,2)*/}
         {Digit.Utils.dss.formatterWithoutRound( parseFloat(row.amount===undefined?0:row.amount).toFixed(2), "number", undefined, true, undefined, 2)}
         
          </td>
        </tr>
      );
    });
  };

  return (
    <React.Fragment>
      <div
        className="flex"
        style={{
          display: "flex",
          flexDirection: "row",
          width: "100%",
          justifyContent: "flex-end",
          paddingRight: "0%",
          alignItems: "center",
        }}
      >
        <table
          className="table reports-table sub-work-table measurement-table-custom"
          style={{ width: "40%", justifyContent: "right", paddingRight: "0%", alignItems: "right" }}
        >
          <tbody>{renderBody()}</tbody>
        </table>
      </div>
    </React.Fragment>
  );
};

export default TableWithOutHead;