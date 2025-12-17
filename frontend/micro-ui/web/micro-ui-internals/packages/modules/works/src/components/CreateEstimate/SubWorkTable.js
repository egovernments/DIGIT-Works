import React, { Fragment, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError } from '@egovernments/digit-ui-react-components'

const SubWorkTable = ({ t, register, errors, rows, setRows }) => {

    // const initialState = [
    //     {
    //         key: 1,
    //         isShow: true
    //     }
    // ]
    // const [rows, setRows] = useState(initialState)

    const getStyles = (index) => {
        let obj = {}
        switch (index) {
            case 1:
                obj = { "width": "1vw" }
                break;
            case 2:
                obj = { "width": "60vw" }
                break;
            case 3:
                obj = { "width": "20vw" }
                break;
            case 4:
                obj = { "width": "10vw" }
                break;
            default:
                obj = { "width": "1vw" }
                break;
        }
        return obj
    }
    const columns = [t('WORKS_SNO'), t('WORKS_NAME_OF_WORK'), t('WORKS_ESTIMATED_AMT'), '']
    const renderHeader = () => {
        return columns?.map((key, index) => {
            return <th key={index} style={getStyles(key)} > {key} </th>
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
                <td style={getStyles(2)} ><div className='field'><TextInput style={{ "marginBottom": "0px" }} name={`estimateDetails.${row.key}.name`} inputRef={register({
                    required: true,
                    //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
                    pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
                })
                }

                />{errors && errors?.estimateDetails?.[row.key]?.name?.type === "pattern" && (
                    <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.estimateDetails?.[row.key]?.name?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
                <td style={getStyles(3)}><div className='field'><TextInput style={{ "marginBottom": "0px" }} name={`estimateDetails.${row.key}.amount`} inputRef={register({
                    required: true,
                    pattern: /^[0-9]*$/
                })}
                />{errors && errors?.estimateDetails?.[row.key]?.amount?.type === "pattern" && (
                    <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.estimateDetails?.[row.key]?.amount?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
                <td style={getStyles(4)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
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
                    <td style={getStyles(1)}></td>
                    <td style={{ ...getStyles(2), "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#C84C0E"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px" }}>{t("WORKS_ADD_ITEM")}</label></span></td>
                    <td style={getStyles(3)}></td>
                    <td style={getStyles(4)}></td>
                </tr>
            </tbody>
        </table>
    )
}

export default SubWorkTable