import React, { Fragment, useState } from 'react'
import { AddIcon, DeleteIcon, RemoveIcon,Dropdown,TextInput, CardLabelError,DatePicker } from '@egovernments/digit-ui-react-components'

const WORKSContractorTable = ({ t, register, errors, rows, setRows,Controller,control }) => {
    const tenant = Digit.ULBService.getStateId()
    const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
      tenant,
      "works",
      [
          {
              "name":"ContractorClass"
          }
      ]
      );
      if(data?.works){
        var { ContractorClass } = data?.works
      }
      
    const { isLoading:deptLoading, data:deptData } = Digit.Hooks.useCustomMDMS(
        tenant,
        "common-masters",
        [
            {
                "name": "Department"
            }
        ]
        );

        if (deptData?.[`common-masters`]) {
            var { Department } = deptData?.[`common-masters`]
        }

      const statusULB=[
        {
          name:"Active", code:'Active', active:true
        },
        {
          name:"Inactive", code:'Inactive', active:true
        },
        {
          name:"Black listed", code:'Black listed', active:true
        },
        {
          name:"Debarred", code:'Debarred', active:true
        }
      ]
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
    const columns = [t('WORKS_SNO'), t('WORKS_DEPARTMENT'), t('WORKS_REGISTRATION_NO'),t('WORKS_CATEGORY'),t('WORKS_CONTRACTOR_CLASS'),t('WORKS_STATUS'),t('WORKS_FROM_DATE'),t('WORKS_TO_DATE'),t('WORKS_ACTION')]
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
                <td style={getStyles(2)} >
                  <div className='field'>
                  <Controller
                    name={`contractorDetails.${row.key}.Department`}
                    control={control}
                    rules={{ required: true }}
                    render={(props) => {
                        return (
                            <Dropdown
                                option={Department}
                                selected={props?.value}
                                optionKey={"name"}
                                t={t}
                                select={props?.onChange}
                                onBlur={props.onBlur}
                            />
                        );
                    }}
                      />
                    {errors && errors?.contractorDetails?.[row.key]?.Department?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
                </td>
                <td style={getStyles(3)}>
                  <div className='field'>
                  <TextInput name={`contractorDetails.${row.key}.registrationNumber`} inputRef={register({
                          pattern: /^$|^[ A-Za-z0-9/._$@#]*$/
                      })}
                      />
                      {errors && errors?.contractorDetails?.[row.key]?.registrationNumber?.type === "pattern" && (
                          <CardLabelError>{t(`ERR_DEFAULT_INPUT_FIELD_MSG`)}</CardLabelError>)}
                  </div>
                </td>
                <td style={getStyles(2)} >
                  <div className='field'>
                  <Controller
                    name={`contractorDetails.${row.key}.category`}
                    control={control}
                    render={(props) => {
                        return (
                            <Dropdown
                                option={Department}
                                selected={props?.value}
                                optionKey={"name"}
                                t={t}
                                select={props?.onChange}
                                onBlur={props.onBlur}
                            />
                        );
                    }}
                      />
                  </div>
                </td>
                <td style={getStyles(2)} >
                  <div className='field'>
                  <Controller
                    name={`contractorDetails.${row.key}.contractorClass`}
                    control={control}
                    rules={{ required: true }}
                    render={(props) => {
                        return (
                            <Dropdown
                                option={ContractorClass}
                                selected={props?.value}
                                optionKey={"grade"}
                                t={t}
                                select={props?.onChange}
                                onBlur={props.onBlur}
                            />
                        );
                    }}
                      />
                    {errors && errors?.contractorDetails?.[row.key]?.contractorClass?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
                </td>
                <td style={getStyles(2)} >
                  <div className='field'>
                  <Controller
                    name={`contractorDetails.${row.key}.status`}
                    control={control}
                    rules={{ required: true }}
                    render={(props) => {
                        return (
                            <Dropdown
                                option={statusULB}
                                selected={props?.value}
                                optionKey={"name"}
                                t={t}
                                select={props?.onChange}
                                onBlur={props.onBlur}
                            />
                        );
                    }}
                      />
                    {errors && errors?.contractorDetails?.[row.key]?.status?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
                </td>
                <td style={getStyles(2)} >
                  <div className='field'>
                  <Controller
                    name={`contractorDetails.${row.key}.fromDate`}
                    control={control}
                    rules={{ required: true }}
                    render={(props) => {
                        return (
                        <DatePicker 
                            date={props.value} 
                            onChange={props.onChange}       
                        />);
                    }}
                    />
                    {errors && errors?.contractorDetails?.[row.key]?.fromDate?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
                </td>
                <td style={getStyles(2)} >
                  <div className='field'>
                  <Controller
                    name={`contractorDetails.${row.key}.toDate`}
                    control={control}
                    rules={{ required: true }}
                    render={(props) => {
                        return (
                        <DatePicker 
                        date={props.value} 
                        onChange={props.onChange}       
                        />
                        );
                    }}
                    />
                    {errors && errors?.contractorDetails?.[row.key]?.toDate?.type === "required" && (
                        <CardLabelError>{t(`WORKS_REQUIRED_ERR`)}</CardLabelError>)}
                  </div>
                </td>
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
                    <td style={getStyles(2)}></td>
                    <td style={getStyles(3)}></td>
                    <td style={getStyles(4)}></td>
                    <td style={{ ...getStyles(2), "textAlign": "center" }} onClick={addRow}><span><AddIcon fill={"#C84C0E"} styles={{ "margin": "auto", "display": "inline", "marginTop": "-2px" }} /><label style={{ "marginLeft": "10px" }}>{t("WORKS_ADD_ITEM")}</label></span></td>
                    <td></td><td></td><td></td><td></td>
                </tr>
            </tbody>
        </table>
    )
}

export default WORKSContractorTable