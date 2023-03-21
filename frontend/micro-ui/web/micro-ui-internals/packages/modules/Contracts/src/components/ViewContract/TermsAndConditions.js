import { AddIcon, CardLabelError, DeleteIcon, TextInput } from "@egovernments/digit-ui-react-components";
import React, { Fragment, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";


const TermsAndConditions = (props) => {
  let termsAndConditions = props?.data;
  const { t } = useTranslation()
  const [rows, setRows] = useState(termsAndConditions);

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
      return obj;
    }

  useEffect(()=>{
    termsAndConditions = termsAndConditions?.filter(e => e);
    setRows(termsAndConditions);
  },[]);

  const columns = [t('WORKS_SNO'), t('COMMON_DESC')];
  const renderHeader = () => {
      return columns?.map((key, index) => {
      return <th key={index} style={getStyles(index + 1)} > {key} </th>
      })
  }

  const renderBody = () => {
      let i = 0
      return rows?.map((row, index) => {
        i++;
        return <tr key={index} style={{ "height": "50%" }}>
              <td style={getStyles(1)}>{i}</td>
              <td style={getStyles(2)}><p>{row?.description ? row?.description : "NA"}</p></td>
          </tr>
        })
  }
    return (
          <table className='table reports-table sub-work-table' style={{marginTop:"0rem"}}>
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