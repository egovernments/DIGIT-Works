import React, { Fragment, useState,useEffect,useMemo } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon, TextInput, CardLabelError,Loader,Dropdown } from '@egovernments/digit-ui-react-components'
import { Controller } from 'react-hook-form';
import _ from "lodash"

const DeductionsTable = ({control,watch,...props}) => {
    
    const [totalAmount,setTotalAmount] = useState(0)
    const [sorTotal, setSorTotal] = useState(0)
    const formFieldName = "deductionDetails" // this will be the key under which the data for this table will be present on onFormSubmit
    
    const errorCardStyle = {width:"100%",fontSize:"12px"}

    const initialState = [
        {
            key: 1,
            isShow: true,
        },
    ];
    

    const { t, register, errors, setValue, getValues, formData } = props

   const [rows, setRows] = useState(
    formData?.[formFieldName]?.length > 1
      ? formData?.[formFieldName]
          ?.map((row, index) => {
            return row ?
                {
                  key: index,
                  isShow: row?.isActive ? row?.isActive : false,
                }
              : undefined;
          })
          ?.filter((row) => row)
      : initialState
  );

    const setTotal = (formData) => {
        const tableData = formData?.[formFieldName]


        const result = tableData?.filter((tableRow, idx) => {
        let include = false
        rows?.map((row) => {
          if (row.isShow && row.key === idx) include = true;
        });
        return include;
      })?.reduce((acc, curr) => acc + parseFloat(curr?.amount || 0), 0);

        setTotalAmount((prevState) => {
            return result
        })

    }

    useEffect(() => {
        setTotal(formData)
    }, [formData,rows]);

    useEffect(() => {
        setSorTotal(formData?.nonSORTablev1?.reduce((acc, row) => row?.estimatedAmount ? parseFloat(row?.estimatedAmount) + acc : acc, 0))
    }, [formData]);

    
    useEffect(() => {
        //we have to iterate over all the existing overheads and change them
        //get the filtered rows , match the index and change the amount if amount is there in formData.overheads.row
        
        formData?.[formFieldName]?.map((row,index)=> {
            if(row?.amount){
                //find corresponding row from rows
                
                const correspondingRow = rows?.filter(r=>r.key === index)?.[0]
                handleDropdownChange(row?.name, undefined, correspondingRow,undefined)
            }
        })
       
        
    }, [sorTotal]);

    const getStyles = (index) => {
        let obj = {}
        switch (index) {
            case 1:
                obj = { "width": "1rem" }
                break;
            case 2:
                obj = { "width": "50%" }
                break;
            case 3:
                obj = { "width": "30rem" }
                break;
            case 4:
                obj = { "width": "60rem" }
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
    const columns = [t('WORKS_SNO'), t('WORKS_DEDUCTION'), t('WORKS_PERCENTAGE'),t('WORKS_AMOUNT'), t('WORKS_COMMENTS'), t('WORKS_ACTIONS')]
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

        setValue(`${formFieldName}.${row.key}.amount`,0)

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
        const option = [
                {
                  "name": "Deduction 1",
                  "code": "DEDUCTION_1"
                },
                {
                  "name": "Deduction 2",
                  "code": "DEDUCTION_2"
                }
        ];
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
            //option={options?.mdmsConfig ? data : option}
            option={option}
            selected={props?.value}
            optionKey={optionKey}
            t={t}
            select={(e) => {
                props.onChange(e);
                handleDropdownChange(e, props, row, inputName)
            }}
            onBlur={props?.onBlur}
            optionCardStyles={{ maxHeight: '15rem' }}
            style={{ marginBottom: "0px" }}
        />
    }

    const handleDropdownChange = (e, props, row, inputName) => {
        // const sorTotal = formData?.nonSORTablev1?.reduce((acc, row) => row?.estimatedAmount ? parseFloat(row?.estimatedAmount) + acc:acc,0)

        //here there are multiple cases that we need to handle
        //1-> if autoCalculated field is true, populate the percentage/lumpsum(type field) , amount field and disable both of them
        //2-> if autocal is false,then let user enter the percentage/lumpsum(type field), amount field

        if (e?.isAutoCalculated) {
            if(e.type==="percentage"){
                //set the percentage field
                //set the amount field
                //disable both the fields
                const amount = (parseFloat(totalAmount) * (parseFloat(e.value)/100)).toFixed(1)
                setValue(`deductionDetails.${row.key}.percentage`,`${e.value} ${t("WORKS_PERCENT")}`)
                setValue(`deductionDetails.${row.key}.amount`,amount)

            }else if(e.type === "lumpsum"){
                //set both lumpsum and amount field
                setValue(`deductionDetails.${row.key}.percentage`, `${t("WORKS_LUMPSUM")}`)
                setValue(`deductionDetails.${row.key}.amount`, e.value)
            }
        }
        else if(!e){
            return 
        }
        else {
            setValue(`deductionDetails.${row.key}.percentage`, `${t("WORKS_LUMPSUM")}`)
        }
    }

    const isInputDisabled = (inputKey) => {
       const value = watch(inputKey)
       if(value?.isAutoCalculated) return true
       else if (!value) return true
       return false
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
                            rules={{
                                required: true,
                                pattern: /^[a-zA-Z0-9_ .$@#\/ ]*$/
                            }}
                            render={(props) => (
                                getDropDownDataFromMDMS(t, row, "name", props, register, "name", {
                                    mdmsConfig: {
                                        masterName: "Deductions",
                                        moduleName: "works",
                                        localePrefix: "ES_COMMON_DEDUCTIONS",
                                    }
                                })
                            )}
                        />
                        {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "pattern" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                        {errors && errors?.[formFieldName]?.[row.key]?.name?.type === "required" && (
                        <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                    </div>
                </td>
                
                <td style={getStyles(3)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.percentage`} inputRef={register({
                    required: true,
                    pattern: /^[a-zA-Z0-9_ .$%@#\/ ]*$/
                })}
                // disable={isInputDisabled(`${formFieldName}.${row.key}.name`)}
                disable={true}
                />
                </div></td>

                <td style={getStyles(4)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.amount`} inputRef={register({
                    required: isInputDisabled(`${formFieldName}.${row.key}.name`)? false : true,
                    pattern: /^\d*\.?\d*$/
                })}
                disable={isInputDisabled(`${formFieldName}.${row.key}.name`)}
                />
                {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "pattern" && (
                    <CardLabelError style={errorCardStyle}>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
                {errors && errors?.[formFieldName]?.[row.key]?.amount?.type === "required" && (
                    <CardLabelError style={errorCardStyle}>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                </div></td>

                <td style={getStyles(5)}><div ><TextInput style={{ "marginBottom": "0px" }} name={`${formFieldName}.${row.key}.comments`} inputRef={register({
                    required: false,
                    pattern: /^[a-zA-Z0-9_ .$%@#\/ ]*$/
                })}
                disable={false}
                />
                </div></td>
                
                <td style={getStyles(6)} >{showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}</td>
            </tr>
        })
    }


    return (
        <table className='table reports-table sub-work-table' style={{ marginTop: "-2rem" }}>
            <thead>
                <tr>{renderHeader()}</tr>
            </thead>
            <tbody>
                {renderBody()}
                <tr>
                    <td colSpan={3} style={{textAlign:"right",fontWeight:"600"}}>{t("RT_TOTAL")}</td>
                    <td colSpan={1}>{Digit.Utils.dss.formatterWithoutRound(totalAmount, 'number')}</td>
                    <td colSpan={1}></td>
                    <td colSpan={1}></td>
                </tr>
                <tr>
                    <td colSpan={6} style={{ "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#F47738"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px", fontWeight: "600", color:" #F47738" }}>{t("WORKS_ADD_DEDUCTION")}</label></span></td>
                </tr>
            </tbody>
        </table>
    )
}

export default DeductionsTable