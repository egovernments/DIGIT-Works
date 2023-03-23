import React, { Fragment, useState ,useEffect,useMemo} from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError,Dropdown,Loader } from '@egovernments/digit-ui-react-components'
import { Controller } from 'react-hook-form';
import _ from "lodash"

const NonSORTable = ({control,watch,...props}) => {
  const [totalAmount, setTotalAmount] = useState(100)
  const formFieldName = "nonSORTablev1" // this will be the key under which the data for this table will be present on onFormSubmit
  const initialState = [
    {
      key: 1,
      isShow: true,
    },
  ];
  const [rows, setRows] = useState(initialState);

  const { t, register, errors , setValue, getValues, formData} = props
  
  const setTotal = (formData) => {
    const tableData = formData?.[formFieldName]
   
    const filteredRows = rows?.filter(row=> row?.isShow)
    setTotalAmount((prevState)=> {        
      return tableData?.filter((row, index) => row)?.filter((row, index) => filteredRows?.[index]?.isShow)?.reduce((acc, curr) => acc + parseFloat(curr?.estimatedAmount || 0) 
        ,0)
    })

  }
  
  
  useEffect(() => {
    setTotal(formData)
  }, [formData,rows]);

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
        obj = { "width": "3%" }
        break;
      default:
        obj = { "width": "1rem" }
        break;
    }
    return obj
  }
  const columns = [t('WORKS_SNO'), t('PROJECT_DESC'), t('PROJECT_UOM'), t('CS_COMMON_RATE'), t('WORKS_ESTIMATED_QUANTITY'), t('WORKS_ESTIMATED_AMOUNT'), '']
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
  const errorCardStyle = { width: "100%","fontSize": "12px" }
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

  const getDropDownDataFromMDMS = (t, row, inputName, props, register, optionKey = "name", options = []) => {
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
      Digit.ULBService.getStateId(),
      options?.mdmsConfig?.moduleName,
      [{ name: options?.mdmsConfig?.masterName }],
      {
        select: (data) => {
          const optionsData = _.get(data, `${options?.mdmsConfig?.moduleName}.${options?.mdmsConfig?.masterName}`, []);
          return optionsData.filter((opt) => opt?.active).map((opt) => ({ ...opt, name: `${options?.mdmsConfig?.localePrefix}_${opt.code}` }));
        },
        enabled: options?.mdmsConfig ? true : false,
      }
    );
   
    if (isLoading) {
      return <Loader />;
      //show MDMS data if options are not provided. Options are in use here for pre defined options from config. 
      //Usage example : dependent dropdown
    } else return <Dropdown
      inputRef={register()}
      option={options?.mdmsConfig ? data : options}
      selected={props?.value}
      optionKey={optionKey}
      t={t}
      select={(e) => {
        props.onChange(e);
      }}
      onBlur={props?.onBlur}
      optionCardStyles={{ maxHeight: '15rem' }}
      style={{marginBottom:"0px"}}
    />
  }

  const setAmountField = (e,row) => {
    const quantity = parseInt(watch(`${formFieldName}.${row.key}.estimatedQuantity`))
    const ratePerUnit = parseFloat(watch(`${formFieldName}.${row.key}.rate`))
    if(!ratePerUnit || !quantity) return 
    const amountToSet = parseFloat(quantity * ratePerUnit).toFixed(1)
    setValue(`${formFieldName}.${row.key}.estimatedAmount`,amountToSet)
  }

  const renderBody = () => {
    let i = 0
    return rows.map((row, index) => {
      if (row.isShow) i++
      return row.isShow && <tr key={index} style={{ "height": "50%" }}>
        <td style={getStyles(1)}>{i}</td>

        <td style={getStyles(2)} ><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.description`} inputRef={register({
          required: true,
          //@Burhan-j Don't remove this whitespace in pattern, it is used for validation
          pattern: /^[a-zA-Z0-9_ .$@#{}:;&()\/ ]*$/
        })
        }
        />{errors && errors?.[formFieldName]?.[row.key]?.description?.type === "pattern" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.description?.type === "required" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(3)}>
          <div >
            <Controller
              control={control}
              name={`${formFieldName}.${row.key}.uom`}
              rules={{
                required: true,
                pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
              }}
              render={(props) => (
                getDropDownDataFromMDMS(t, row, "uom", props, register, "name", {
                  mdmsConfig: {
                    masterName: "uom",
                    moduleName: "common-masters",
                    localePrefix: "ES_COMMON_UOM",
                  }
                })
              )}
            />
        {errors && errors?.[formFieldName]?.[row.key]?.uom?.type === "pattern" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.uom?.type === "required" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
          </div>
        </td>

        <td style={getStyles(4)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.rate`} inputRef={register({
          required: true,
          pattern: /^\d*\.?\d*$/
        })}
        onChange={(e) => setAmountField(e, row)}
        />{errors && errors?.[formFieldName]?.[row.key]?.rate?.type === "pattern" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.rate?.type === "required" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(5)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.estimatedQuantity`} inputRef={register({
          required: true,
          pattern: /^[0-9]*$/
        })}
        onChange={(e)=>setAmountField(e,row)}
        />{errors && errors?.[formFieldName]?.[row.key]?.estimatedQuantity?.type === "pattern" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.estimatedQuantity?.type === "required" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}</div></td>

        <td style={getStyles(6)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.estimatedAmount`} inputRef={register({
          required: true,
          pattern: /^\d*\.?\d*$/
        })}
        disable={true}
        />
        {/* {errors && errors?.[formFieldName]?.[row.key]?.estimatedAmount?.type === "pattern" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
          {errors && errors?.[formFieldName]?.[row.key]?.estimatedAmount?.type === "required" && (
            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)} */}
          </div></td>

        <td style={getStyles(8)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
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
        <tr>
          <td colSpan={1}></td>
          <td colSpan={4} style={{ textAlign: "right", fontWeight: "600" }}>{t("RT_TOTAL")}</td>
          <td colSpan={1}>{Digit.Utils.dss.formatterWithoutRound(totalAmount, 'number')}</td>
          <td colSpan={1}></td>
        </tr>
        
        <tr>
          {/* <td style={getStyles(1)}></td> */}
          <td colSpan={7} style={{ "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px", fontWeight: "600", color: "#F47738" }}>{t("WORKS_ADD_ITEM")}</label></span></td>
          {/* <td style={getStyles(1)}></td> */}
        </tr>
        
      </tbody>
    </table>
  )
}

export default NonSORTable