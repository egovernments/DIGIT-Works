import TableRows from "./TableRows"
import React,{useState,Fragment} from "react";
import { useTranslation } from 'react-i18next'
import { CardSectionHeader } from "@egovernments/digit-ui-react-components";

const SubWorkTable=({register}) =>{

    const { t } = useTranslation()
    const [rowsData, setRowsData] = useState([]);

    const addTableRows = () => {

        const rowsInput = {
            fullName: '',
            emailAddress: '',
            salary: ''
        }
        setRowsData([...rowsData, rowsInput])

    }
    const deleteTableRows = (index) => {
        const rows = [...rowsData];
        rows.splice(index, 1);
        setRowsData(rows);
    }

    const handleChange = (index, evnt) => {

        const { name, value } = evnt.target;
        const rowsInput = [...rowsData];
        rowsInput[index][name] = value;
        setRowsData(rowsInput);



    }
    return (
        <>
            <CardSectionHeader >{t(`WORKS_SUB_WORK_DETAILS`)}</CardSectionHeader>
                    <table style={{"border":"1px solid grey"}}>
                        <thead>
                            <tr>
                                <th style={{"width":"1%"}}>{t("S.NO.")}</th>
                                <th colSpan={4} style={{"textAlign":"left"}}>{t("NAME_WORK")}</th>
                                <th>{t("ESTIMATED_AMT")}</th>
                                {/* <th><button onClick={addTableRows} >+</button></th> */}
                            </tr>

                        </thead>
                        <tbody>

                    <TableRows register={register} rowsData={rowsData} deleteTableRows={deleteTableRows} handleChange={handleChange} />
                <tr>
                    <td colSpan={6} style={{ "textAlign": "center" }}><button type="button" onClick={addTableRows}>{t("ADD_LINE_ITEM")}</button></td>
                </tr>
                        </tbody>
                    </table>
        </>
              
    )

}
export default SubWorkTable