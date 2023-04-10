import React, { useState } from 'react'
import { Controller } from 'react-hook-form';
import _ from "lodash"
import { AddIcon, DeleteIcon, TextInput, CardLabelError, Loader, Dropdown, Header } from '@egovernments/digit-ui-react-components'

const TransferCodeTable = (props) => {
    const { t, register, errors , setValue, getValues, onSelect, formData, control, formState, onChange, unregister } = props

    const orgSession = Digit.Hooks.useSessionStorage("ORG_CREATE", {});
    const [sessionFormData] = orgSession;

    const columns = [t('WORKS_SNO'), t("MASTERS_IDENTIFIER_TYPE"), t("MASTERS_IDENTIFIER_VALUE"), '']
    const formFieldName = props?.config?.key === "transferCodes" ? 'transferCodesData' : 'taxIdentifierData'

    const module = props?.config?.key
    const isTranferCodeTable = formFieldName === "transferCodesData"
       
    const renderTableFromSession = () => {
        if(!sessionFormData?.taxIdentifierData) {
            return [{
                key: 0,
                isShow: true,
            }];
        }
        let tableState = [];
        for(let i = 0; i<sessionFormData?.taxIdentifierData?.length; i++) {
          if(sessionFormData?.taxIdentifierData[i]) {
            tableState.push({
              key: i,
              isShow: true,
            })
          }
        }
        return tableState;
    }

    const initialState = [
        {
            key: 0,
            isShow: true
        }
    ];
    const [rows, setRows] = useState(isTranferCodeTable ? initialState : renderTableFromSession());

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
    const errorCardStyle = {width:"100%"}

    const getPatterns = (rowKey) => {
        if(isTranferCodeTable) return Digit.Utils.getPattern('IFSC')
        if(formData?.taxIdentifierData?.[rowKey]?.name?.code === 'PAN') return Digit.Utils.getPattern('PAN')
        if(formData?.taxIdentifierData?.[rowKey]?.name?.code === 'GSTIN') return Digit.Utils.getPattern('GSTNo')
        return ""
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
        unregister(`${formFieldName}.${row.key}.name`)
        unregister(`${formFieldName}.${row.key}.value`)
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
        )
        
        if (isLoading) {
            return <Loader />
        } else return (
            <Dropdown
                inputRef={register()}
                option={options?.mdmsConfig ? data : options}
                selected={props?.value}
                optionKey={optionKey}
                t={t}
                select={(e) => {
                    props.onChange(e)
                }}
                onBlur={props.onBlur}
                optionCardStyles={{ maxHeight: '15rem' }}
                style={{ marginBottom: "0px" }}
            /> 
        )
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
                            control={control}
                            name={`${formFieldName}.${row.key}.name`}
                            defaultValue={formData?.[`${formFieldName}.${row.key}.name`]}
                            rules={{ required: isTranferCodeTable }}
                            render={(props) => (
                                getDropDownDataFromMDMS(t, row, "name", props, register, "name", {
                                    mdmsConfig: {
                                        masterName: isTranferCodeTable ? "OrgTransferCode" : "OrgTaxIdentifier",
                                        moduleName: "common-masters",
                                        localePrefix: `COMMON_MASTERS_${Digit.Utils.locale.getTransformedLocale(module)}`
                                    }
                                })
                            )}
                        />
                        {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "required" && (
                            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </td>
                <td style={getStyles(3)}>
                    <div >
                        <TextInput 
                            style={{ "marginBottom": "0px" }} 
                            name={`${formFieldName}.${row.key}.value`} 
                            selected={formData && formData[formFieldName] ? formData[formFieldName][`${formFieldName}.${row.key}.value`] : undefined}
                            inputRef={register({ required: isTranferCodeTable, pattern: getPatterns(row.key)})}
                            onChange={onChange}
                        />
                        {errors && errors?.[formFieldName]?.[row.key]?.value?.type === "pattern" && (
                            <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                        {errors && errors?.[formFieldName]?.[row.key]?.value?.type === "required" && (
                            <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
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
                        formFieldName === "taxIdentifierData" ? (
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