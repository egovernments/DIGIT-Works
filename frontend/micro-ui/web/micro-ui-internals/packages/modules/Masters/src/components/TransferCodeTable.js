import React, { Fragment, useState,useEffect } from 'react'
import { Controller } from 'react-hook-form';
import _ from "lodash"
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError,Loader,Dropdown, Header, CustomDropdown } from '@egovernments/digit-ui-react-components'

const TransferCodeTable = (props) => {
    const { t, register, errors , setValue, getValues, formData, control} = props
    const columns = [t('WORKS_SNO'), t("MASTERS_IDENTIFIER_TYPE"), t("MASTERS_IDENTIFIER_VALUE"), '']

    const formFieldName = props?.config?.key //can be transferCodes or taxIdentifier

    const isTranferCodeTable = formFieldName === "transferCodes"
        
    const initialState = [
        {
            key: 1,
            isShow: true,
        }
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

    const showDelete = () => {
        let countIsShow = 0
        rows.map(row => row.isShow && countIsShow++)
        if (countIsShow === 1) {
        return false
        }
        return true
    }

    const errorCardStyle = {width:"100%"}

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

    const renderHeader = () => {
        return columns?.map((key, index) => {
            return <th key={index} style={getStyles(index+1)} > {key} </th>
        })
    }

    const getPopulators = (row) => {
        return {
            name: `${formFieldName}.${row.key}.name`,
            optionsKey: "name", 
            mdmsConfig: {
                masterName: isTranferCodeTable ? "OrgTransferCode" : "OrgTaxIdentifier",
                moduleName: "common-masters",
                localePrefix: `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(formFieldName)}`,
            },
            optionsCustomStyle : {
                top : "2.3rem"
            },
            styles: {marginBottom: "0px"},
        }
    }

    const renderBody = () => {
        let i = 0
        return rows.map((row, index) => {
        if (row.isShow) i++
        return row.isShow && <tr key={index} style={{ "height": "50%" }}>
            <td style={getStyles(1)}>{i}</td>
            <td style={getStyles(2)} >
                <div>
                    <Controller
                        render={(props) => (
                        <CustomDropdown
                            t={t}
                            label={''}
                            type={'dropdown'}
                            onBlur={props.onBlur}
                            value={props.value}
                            inputRef={props.ref}
                            onChange={props.onChange}
                            config={getPopulators(row)}
                            disable={false}
                        />
                        )}
                        rules={{ required: false }}
                        defaultValue={formData?.[`${formFieldName}.${row.key}.name`]}
                        name={`${formFieldName}.${row.key}.name`}
                        control={control}
                    />
                </div>
            </td>
            <td style={getStyles(3)}>
                <div >
                    <TextInput 
                        style={{ "marginBottom": "0px" }} 
                        name={`${formFieldName}.${row.key}.percentage`} 
                        inputRef={register({ required: false, pattern: /^[a-zA-Z0-9_ .$%@#\/ ]*$/})}
                    />
                     {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "pattern" && (
                    <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                </div>
            </td>
            <td style={getStyles(8)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
        </tr>
        })
    }

    return (
        <React.Fragment>
            <Header styles={{fontSize: "24px", marginTop: "16px", marginBottom: "16px"}}>{isTranferCodeTable ? t("MASTERS_TRANSFER_CODE") : t("MASTERS_TAX_INDENTIFIERS")}</Header>
            <table className='table reports-table sub-work-table' style={{ marginTop: "-10px", marginBottom: "2rem" }}>
                <thead>
                    <tr>{renderHeader()}</tr>
                </thead>
                <tbody>
                    {renderBody()}
                    {
                        formFieldName === "taxIdentifier" ? (
                            <tr>
                                <td colSpan={7} style={{ "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px", fontWeight: "600", color: "#F47738" }}>{t("WORKS_ADD_ITEM")}</label></span></td>
                            </tr> ) : null
                    }
                </tbody>
            </table>
        </React.Fragment>
    )
}

export default TransferCodeTable;