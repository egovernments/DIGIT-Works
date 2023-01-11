import React, { Fragment, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError } from '@egovernments/digit-ui-react-components'
import { toNumber } from 'lodash';

const NonSORTable = (props) => {
  const [totalAmount, setTotalAmount] = useState(100)
  const formFieldName = "nonSORTablev1" // this will be the key under which the data for this table will be present on onFormSubmit

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
        obj = { "width": "30rem" }
        break;
      case 3:
        obj = { "width": "10rem" }
        break;
      case 4:
        obj = { "width": "10rem" }
        break;
      case 5:
        obj = { "width": "15rem" }
        break;
      case 6:
        obj = { "width": "18rem" }
        break;
      case 7:
        obj = { "width": "10rem" }
        break;
      case 8:
        obj = { "width": "1rem" }
        break;
      default:
        obj = { "width": "1rem" }
        break;
    }
    return obj
  }
  const columns = [t('WORKS_SNO'), t('PROJECT_DESC'), t('PROJECT_UOM'), t('CS_COMMON_RATE'), t('WORKS_ESTIMATED_QUANTITY'), t('WORKS_ESTIMATED_AMOUNT'),t('RT_TOTAL'), '']
  const renderHeader = () => {
    return columns?.map((key, index) => {
      return <th key={index} style={getStyles(index + 1)} > {key} </th>
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

        <td style={getStyles(2)} ><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.description`} inputRef={register({
          required: false,
          //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
          pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
        })
        }
        />{errors && errors?.[formFieldName]?.[row.key]?.name?.type === "pattern" && (
          <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "required" && (
            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(3)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.uom`} inputRef={register({
          required: false,
          pattern: /^[0-9]*$/
        })}
        />{errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
          <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(4)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.rate`} inputRef={register({
          required: false,
          pattern: /^[0-9]*$/
        })}
        />{errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
          <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(5)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.estimatedQuantity`} inputRef={register({
          required: false,
          pattern: /^[0-9]*$/
        })}
        />{errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
          <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(6)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.estimatedAmount`} inputRef={register({
          required: false,
          pattern: /^[0-9]*$/
        })}
        />{errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
          <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(7)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.total`} inputRef={register({
          required: false,
          pattern: /^[0-9]*$/
        })}
        />{errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
          <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
            <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(8)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
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
        {/* <tr>
          <td colSpan={3} style={{ textAlign: "right", fontWeight: "600" }}>{t("RT_TOTAL")}</td>
          <td colSpan={1}>{totalAmount}</td>
          <td colSpan={1}></td>
        </tr> */}
        
        <tr>
          <td style={getStyles(1)}></td>
          <td colSpan={6} style={{ "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px", fontWeight: "600" }}>{t("WORKS_ADD_NON_SOR")}</label></span></td>
          <td style={getStyles(1)}></td>
        </tr>
        
      </tbody>
    </table>
  )
}

export default NonSORTable