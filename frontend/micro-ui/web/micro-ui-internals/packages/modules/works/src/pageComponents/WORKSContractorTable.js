import React, { useState,useEffect } from 'react';
import { useTranslation } from "react-i18next";
import { Dropdown, TextInput, LinkButton, DatePicker, Loader,CardLabelError ,DeleteIcon,AddIcon} from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";

const WORKSContractorTable = () => {
  const {t}=useTranslation();
  const [contractorDetails,setContractorDetails]=useState({key: Date.now()});
  const [rows,setRows]=useState([{key:1,isShow:true}])
  const { control, formState: localFormState, watch, setError: setLocalError, clearErrors: clearLocalErrors, setValue, trigger, getValues,register } = useForm();
  const formValue = watch();
  const { errors } = localFormState;
  const [isErrors, setIsErrors] = useState(false);
  const [focusIndex, setFocusIndex] = useState({ index: -1, type: "" });
  const dummyData=[
      {
        name: 'Burhan', code: 'Burhan', active: true
      },
      {
        name: 'Jagan', code: 'Jagan', active: true
      },
      {
        name: 'Nipun', code: 'Nipun', active: true
      }
    ]
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
  const tenant = Digit.ULBService.getStateId();
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
          return <th key={index} style={getStyles(index)} > {key} </th>
      })
    }
    const renderBody=()=>{
      let i=0;
      return rows.map((row,index)=>{
        if(row.isShow===true) i++;
        return  row.isShow && <tr key={index} style={{height:"50%"}}>
          <td style={getStyles(1)}>{i}</td>
          <td style={getStyles(2)}>
              <Controller
                control={control}
                name={`contractorDetails.${row.key}.Department`}
                // defaultValue={contractorDetails.bankName}
                rules={{ required: t("WORKS_REQUIRED_ERR") }}
                isMandatory={true}
                render={(props) => {return(
                  <Dropdown
                    option={Department}
                    selected={props?.value}
                    optionKey={"name"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                  />
                )}}
              />
              <CardLabelError>{localFormState.touched?.contractorDetails?.[row.key]?.Department ? errors?.contractorDetails?.[row.key]?.Department?.message : ""}</CardLabelError>
          </td>
          <td style={getStyles(3)}>
            <Controller
                control={control}
                name={`contractorDetails.${row.key}.registrationNumber`}
                defaultValue={""}
                rules={{ validate: {
                  pattern: (v) => (/^$|^[ A-Za-z0-9]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
                } }}
                render={(props) => (
                  <TextInput
                    value={props.value}
                    autoFocus={focusIndex.index === 1 && focusIndex.type === `contractorDetails.${row.key}.registrationNumber`}
                    errorStyle={(localFormState.touched?.contractorDetails?.[row.key]?.registrationNumber && errors?.contractorDetails?.[row.key]?.registrationNumber?.message) ? true : false}
                    onChange={(e) => {
                      props.onChange(e.target.value);
                      setFocusIndex({ index: 1, type: `contractorDetails.${row.key}.registrationNumber`});
                    }}
                    onBlur={(e) => {
                      setFocusIndex({ index: -1 });
                      props.onBlur(e);
                    }}
                  />
                )}
              />
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.registrationNumber ? errors?.contractorDetails?.[row.key]?.registrationNumber?.message : ""}</CardLabelError>
          </td>
          <td style={getStyles(4)}>
            <Controller
              control={control}
              name={`contractorDetails.${row.key}.category`}
              // defaultValue={contractorDetails.bankName}
              rules={{ required: t("WORKS_REQUIRED_ERR") }}
              isMandatory={true}
              render={(props) => (
                <Dropdown
                  option={dummyData}
                  selected={props?.value}
                  optionKey={"name"}
                  t={t}
                  select={props?.onChange}
                  onBlur={props.onBlur}
                />
            )}
          />
          <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.category ? errors?.contractorDetails?.[row.key]?.category?.message : ""}</CardLabelError>
          </td>
          <td style={getStyles(2)}>
              <Controller
                  control={control}
                  name={`contractorDetails.${row.key}.contractorClass`}
                  // defaultValue={contractorDetails.bankName}
                  rules={{ required: t("WORKS_REQUIRED_ERR") }}
                  isMandatory={true}
                  render={(props) => (
                  <Dropdown
                    option={ContractorClass}
                    selected={props?.value}
                    optionKey={"grade"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                  />
                  )}
              />
            <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.contractorClass ? errors?.contractorDetails?.[row.key]?.contractorClass?.message : ""}</CardLabelError>
          </td>
          <td style={getStyles(2)}>
              <Controller
                  control={control}
                  name={`contractorDetails.${row.key}.status`}
                  // defaultValue={contractorDetails.bankName}
                  rules={{ required: t("WORKS_REQUIRED_ERR") }}
                  isMandatory={true}
                  render={(props) => (
                    <Dropdown
                    option={statusULB}
                    selected={props?.value}
                    optionKey={"name"}
                    t={t}
                    select={props?.onChange}
                    onBlur={props.onBlur}
                  />
                  )}
                />
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.status ? errors?.contractorDetails?.[row.key]?.status?.message : ""}</CardLabelError>
          </td>
          <td>
              <Controller
                name={`contractorDetails.${row.key}.fromDate`}
                control={control}
                defaultValue={""}
                rules={{ required: t("WORKS_REQUIRED_ERR") }}
                isMandatory={true}
                render={(props) =>
                   <DatePicker 
                   style={{margin:"5px",width:"90%"}}
                    date={props.value} 
                    onChange={props.onChange} 
                    errorStyle={(localFormState.touched.contractorDetails?.[row.key]?.fromDate && errors?.contractorDetails?.[row.key]?.fromDate?.message) ? true : false}
                    />}
              />
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.fromDate ? errors?.contractorDetails?.[row.key]?.fromDate?.message : ""}</CardLabelError>
          </td>
          <td>
            <Controller
                name={`contractorDetails.${row.key}.toDate`}
                rules={{ required: t("WORKS_REQUIRED_ERR") }}
                isMandatory={true}
                defaultValue={""}
                control={control}
                render={(props) => (
                  <DatePicker
                    style={{margin:"5px",width:"90%"}}
                    date={props.value}
                    // date={CommencementDate} 
                    onChange={props.onChange}
                    //disabled={isRenewal}
                  />
                )}
              />
              <CardLabelError>{localFormState.touched.toDate ? errors?.toDate?.message : ""}</CardLabelError>
          </td>
          <td>
          {showDelete() && <span onClick={() => removeRow(row)}><DeleteIcon fill={"#B1B4B6"} style={{ "margin": "auto" }} /></span>}
          </td>
        </tr>
      })
    }
    const addRow = () => {
      const obj = {
          key: null,
          isShow: true
      }
      obj.key = rows[rows.length - 1].key + 1
      setRows(prev => [...prev, obj])
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
    // useEffect(() => {
    //   if (Object.keys(errors).length && !_.isEqual(formState.errors[config.key]?.type || {}, errors)) {
    //     setError(config.key, { type: errors });
    //   }
    //   else if (!Object.keys(errors).length && formState.errors[config.key] && isErrors) {
    //     clearErrors(config.key);
    //   }
    // }, [errors]);
  
    useEffect(() => {
      trigger();
    }, []);
  
    useEffect(() => {
      const keys = Object.keys(formValue);
      const part = {};
      keys.forEach((key) => (part[key] = contractorDetails[key]));
      let _ownerType = {};
      if (!_.isEqual(formValue, part)) {
        Object.keys(formValue).map(data => {
          if (data != "key" && formValue[data] != undefined && formValue[data] != "" && formValue[data] != null && !isErrors) {
            setIsErrors(true);
          }
        });
        setContractorDetails((prev) =>
           (prev.key && prev.key === contractorDetails.key ? { ...prev, ...formValue, ..._ownerType } : { ...prev })
        );
        // setContractorDetails((prev) => prev.map((o) => {
        //   return (o.key && o.key === contractorDetails.key ? { ...o, ...formValue, ..._ownerType } : { ...o })
        // }));
        trigger();
        }
      }, [formValue]);
  return (
    <div>
      <table className='table reports-table sub-work-table'>
        <thead style={{height:"40px"}}>
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
    </div>
  )
}

export default WORKSContractorTable