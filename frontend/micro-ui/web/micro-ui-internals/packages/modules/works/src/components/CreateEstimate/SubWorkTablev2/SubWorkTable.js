import React, { Fragment, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError } from '@egovernments/digit-ui-react-components'

const SubWorkTable = ({ t, register, errors }) => {


    const [rows, setRows] = useState([1])

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

    const removeRow = (key) => {
        //make a new state here which doesn't have this key
        setRows(prev => rows.filter(e => e !== key))
    }
    const addRow = () => {
        setRows(prev => [...prev, prev[prev.length - 1] + 1])
    }
    const renderBody = () => {
        return rows.map(key => {
            return <tr style={{ "height": "50%" }}>
                <td style={getStyles(1)}>{key}</td>
                <td style={getStyles(2)} ><div className='field'><TextInput style={{ "marginBottom": "0px" }} name={`work.${key}.name`} inputRef={register({
                    required: true,
                    pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                })
                }

                />{errors && errors?.work?.[key]?.name?.type === "pattern" && (
                    <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.work?.[key]?.name?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
                <td style={getStyles(3)}><div className='field'><TextInput style={{ "marginBottom": "0px" }} name={`work.${key}.amount`} inputRef={register({
                    required: true,
                    pattern: /^[0-9]*$/
                })}
                />{errors && errors?.work?.[key]?.amount?.type === "pattern" && (
                    <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                    {errors && errors?.work?.[key]?.amount?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
                <td style={getStyles(4)} >{key !== 1 && <span onClick={() => removeRow(key)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
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