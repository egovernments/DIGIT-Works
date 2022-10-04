import React, { Fragment, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError } from '@egovernments/digit-ui-react-components'

const SubWorkTable = ({ t, register, errors, rows, setRows, estimateDetails, setValue }) => {
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
        rows.map(row => countIsShow++)
        if (countIsShow === 1) {
            return false
        }
        return true
    }
    const removeRow = (row) => {
        const updatedState= rows.filter(( i) => i !== row)

        setRows(prev => updatedState)
    }
    const addRow = () => {
        const obj = {
            key: null,
        }
        obj.key = rows.length + 1
        setRows(prev => [...prev, obj])
    }
    const renderBody = () => {
        let i = 0
        return rows.map((row, index) => {
            setValue(`estimateDetails.${row.key}.name`,row.name)
            setValue(`estimateDetails.${row.key}.amount`,row.amount)
            return <tr key={index} style={{ "height": "50%" }}>
                <td style={getStyles(1)}>{++i}</td>
                <td style={getStyles(2)} ><div className='field'><TextInput style={{ "marginBottom": "0px" }} name={`estimateDetails.${row.key}.name`} inputRef={register({
                    required: true,
                    pattern: /^[a-zA-Z0-9_.$@#\/]*$/
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
                    <td style={{ ...getStyles(2), "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px" }}>{t("WORKS_ADD_ITEM")}</label></span></td>
                    <td style={getStyles(3)}></td>
                    <td style={getStyles(4)}></td>
                </tr>
            </tbody>
        </table>
    )
}

export default SubWorkTable