import React, { Fragment, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError } from '@egovernments/digit-ui-react-components'
import { toNumber } from 'lodash';

const OverheadsTable = (props) => {
    const [totalAmount,setTotalAmount] = useState(100)
    const formFieldName = "overheadDetails" // this will be the key under which the data for this table will be present on onFormSubmit
    
    const errorCardStyle = {width:"100%"}

    const initialState = [
        {
            key: 1,
            isShow: true,
        },
    ];
    const [rows, setRows] = useState(initialState);

    const { t, register, errors } = props

    const getStyles = (index) => {
        let obj = {}
        switch (index) {
            case 1:
                obj = { "width": "1rem" }
                break;
            case 2:
                obj = { "width": "60%" }
                break;
            case 3:
                obj = { "width": "30rem" }
                break;
            case 4:
                obj = { "width": "30rem" }
                break;
            case 5:
                obj = { "width": "3%" }
                break;
            default:
                obj = { "width": "92rem" }
                break;
        }
        return obj
    }
    const columns = [t('WORKS_SNO'), t('PROJECT_DESC'), t('WORKS_PERCENTAGE'),t('WORKS_AMOUNT'), '']
    const renderHeader = () => {
        return columns?.map((key, index) => {
            return <th key={index} style={getStyles(index+1)} > {key} </th>
        })
    }

    const showDelete = () => {
        let countIsShow = 0
        rows.map(row => row.isShow && countIsShow++)
        if (countIsShow === 1) {
            return false
        }
        return true
    }
    const removeRow = (row) => {
        //make a new state here which doesn't have this key
        const updatedState = rows.map(e => {
            if (e.key === row.key) {
                return {
                    key: e.key,
                    isShow: false
                }
            }
            return e
        })

        setRows(prev => updatedState)
    }
    const addRow = () => {
        const obj = {
            key: null,
            isShow: true
        }
        obj.key = rows[rows.length - 1].key + 1
        setRows(prev => [...prev, obj])
    }
    const renderBody = () => {
        let i = 0
        return rows.map((row, index) => {
            if (row.isShow) i++
            return row.isShow && <tr key={index} style={{ "height": "50%" }}>
                <td style={getStyles(1)}>{i}</td>
                
                <td style={getStyles(2)} ><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.name`} inputRef={register({
                    required: false,
                    //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
                    pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
                })
                }
                />{errors && errors?.[formFieldName]?.[row.key]?.name?.type === "pattern" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "required" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
                
                <td style={getStyles(3)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.percentage`} inputRef={register({
                    required: false,
                    pattern: /^[0-9]*$/
                })}
                />{errors && errors?.[formFieldName]?.[row.key]?.percentage?.type === "pattern" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.[formFieldName]?.[row.key]?.percentage?.type === "required" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

                <td style={getStyles(4)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.amount`} inputRef={register({
                    required: false,
                    pattern: /^[0-9]*$/
                })}
                />{errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
                
                <td style={getStyles(5)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
            </tr>
        })
    }


    return (
        <table className='table reports-table sub-work-table'>
            <thead>
                <tr>{renderHeader()}</tr>
            </thead>
            <tbody>
                {renderBody()}
                <tr>
                    <td colSpan={3} style={{textAlign:"right",fontWeight:"600"}}>{t("RT_TOTAL")}</td>
                    <td colSpan={1}>{totalAmount}</td>
                    <td colSpan={1}></td>
                </tr>
                <tr>
                    {/* <td style={getStyles(1)}></td> */}
                    <td colSpan={5} style={{ "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px", fontWeight: "600", color:" #F47738" }}>{t("WORKS_ADD_OVERHEAD")}</label></span></td>
                    {/* <td style={getStyles(3)}></td>
                    <td style={getStyles(6)}></td> */}
                </tr>
            </tbody>
        </table>
    )
}

export default OverheadsTable