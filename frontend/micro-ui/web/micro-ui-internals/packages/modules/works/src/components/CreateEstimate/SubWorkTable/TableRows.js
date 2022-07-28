import React, { useState } from "react";
import { TextInput,TextArea } from "@egovernments/digit-ui-react-components";
const TableRows=({ rowsData, deleteTableRows, handleChange,register }) =>{


    return (

        rowsData.map((data, index) => {
            const { fullName, emailAddress, salary } = data;
            return (

                <tr key={index}>
                    <td style={{"width":"10px"}}>
                        {/* <TextInput type="text" value={index} disabled={"true"} onChange={(evnt) => (handleChange(index, evnt))} name="fullName" className="form-control" /> */}
                        {index+1}
                    </td>
                    <td colSpan={4}><TextInput inputRef={register()} type="text" onChange={(evnt) => (handleChange(index, evnt))} name={`inputsName`} className="form-control" /> </td>
                    <td><TextInput style={{"width":"20%"}} type="text" value={salary} onChange={(evnt) => (handleChange(index, evnt))} name="salary" className="form-control" /> </td>
                    <td><button type="button" className="btn btn-outline-danger" onClick={() => (deleteTableRows(index))}>x</button></td>
                </tr>

            )
        })

    )

}

export default TableRows;