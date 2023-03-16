import { AddIcon, CardLabelError, DeleteIcon, TextInput } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useState } from "react";
import { useTranslation } from "react-i18next";


const TermsAndConditions = (props) => {
  const formFieldName = "TermsAndConditions" // this will be the key under which the data for this table will be present on onFormSubmit
  const { t } = useTranslation()
  const { n, register, errors , setValue, getValues, formData} = props
  const initialState = [
      {
      key: 1,
      isShow: true,
      },
  ];
  const [rows, setRows] = useState(initialState);

  const getStyles = (index) => {
      let obj = {}
      switch (index) {
        case 1:
          obj = { "width": "1rem" }
          break;
        case 2:
          obj = { "width": "30rem" }
          break;
        default:
          obj = { "width": "1rem" }
          break;
      }
      return obj
    }

  const columns = [t('WORKS_SNO'), t('PROJECT_DESC'),''];
  const renderHeader = () => {
      return columns?.map((key, index) => {
      return <th key={index} style={getStyles(index + 1)} > {key} </th>
      })
  }

  const errorCardStyle = {width:"100%"}


  const renderBody = () => {
      let i = 0
      return rows.map((row, index) => {
      if (row.isShow) i++
      return row.isShow && <tr key={index} style={{ "height": "50%" }}>
          <td style={getStyles(1)}>{i}</td>
          <td style={getStyles(2)} ><div>

          <p>{props.data[1].description}</p>
          {errors && errors?.[formFieldName]?.[row.key]?.description?.type === "pattern" && (
              <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.description?.type === "required" && (
              <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>
      </tr>
      })
  }
    return (
          <table className='table reports-table sub-work-table' style={{marginTop:"-2rem"}}>
            <thead>
              <tr>{renderHeader()}</tr>
            </thead>
            <tbody>
              {renderBody()}
            </tbody>
          </table>
    )
  };
  
  export default TermsAndConditions;