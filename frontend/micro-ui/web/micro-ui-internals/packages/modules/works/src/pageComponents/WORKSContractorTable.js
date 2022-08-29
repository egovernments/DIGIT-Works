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
  const userUlbs=[{label:"active",value:1,code:"active"},{label:"Inactive",value:2},{label:"Black listed",value:3}]
  console.log("localFormState",localFormState,formValue)
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
            <div>
              <Controller
                control={control}
                name={`contractorDetails.${row.key}.Department`}
                // defaultValue={contractorDetails.bankName}
                rules={{ required: t("REQUIRED_FIELD") }}
                isMandatory={true}
                render={(props) => {console.log("errors",errors);return(
                  <Dropdown
                    className="form-field"
                    selected={getValues(`contractorDetails.${row.key}.Department`)}
                    disable={false}
                    // option={[{label:"active",value:1},{label:"Inactive",value:2},{label:"Black listed",value:3}]}
                    errorStyle={(localFormState.touched?.contractorDetails?.[row.key]?.Department && errors?.contractorDetails?.[row.key]?.Department?.message) ? true : false}
                    select={(e) => {
                      props.onChange(e);
                    }}
                    optionKey="i18nKey"
                    onBlur={props.onBlur}
                    t={t}
                  />
                )}}
              />
              <CardLabelError>{localFormState.touched?.contractorDetails?.[row.key]?.Department ? errors?.contractorDetails?.[row.key]?.Department?.message : ""}</CardLabelError>
            </div>
          </td>
          <td style={getStyles(3)}>
            <div className='field'>
            <Controller
                control={control}
                name={`contractorDetails.${row.key}.registrationNumber`}
                defaultValue={""}
                rules={{ validate: {
                  pattern: (v) => (/^[ A-Za-z0-9]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
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
                    // disable={isRenewal}
                  />
                )}
              />
              {/* <TextInput name="registrationNumber" inputRef={register({
                  pattern: /^[a-zA-Z0-9_.$@#\/]*$/
                  })}
                /> */}
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.registrationNumber ? errors?.contractorDetails?.[row.key]?.registrationNumber?.message : ""}</CardLabelError>

              {/* {errors && errors?.registrationNumber?.type === "pattern" && (
              <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)} */}
            </div>
          </td>
          <td style={getStyles(4)}>
            <div className="field">
            <Controller
              control={control}
              name={`contractorDetails.${row.key}.category`}
              // defaultValue={contractorDetails.bankName}
              rules={{ required: t("REQUIRED_FIELD") }}
              isMandatory={true}
              render={(props) => (
                <Dropdown
                  className="form-field"
                  selected={getValues(`contractorDetails.${row.key}.category`)}
                  disable={false}
                  // option={[{label:"active",value:1},{label:"Inactive",value:2},{label:"Black listed",value:3}]}
                  errorStyle={(localFormState.touched.contractorDetails?.[row.key]?.category && errors?.contractorDetails?.[row.key]?.category?.message) ? true : false}
                  select={(e) => {
                    props.onChange(e);
                  }}
                  optionKey="i18nKey"
                  onBlur={props.onBlur}
                  t={t}
                />
            )}
          />
          <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.category ? errors?.contractorDetails?.[row.key]?.category?.message : ""}</CardLabelError>
            </div>
          </td>
          <td style={getStyles(2)}>
            <div>
              <Controller
                  control={control}
                  name={`contractorDetails.${row.key}.contractorClass`}
                  // defaultValue={contractorDetails.bankName}
                  rules={{ required: t("REQUIRED_FIELD") }}
                  isMandatory={true}
                  render={(props) => (
                    <Dropdown
                      className="form-field"
                      selected={getValues(`contractorDetails.${row.key}.contractorClass`)}
                      disable={false}
                      // option={[{label:"active",value:1},{label:"Inactive",value:2},{label:"Black listed",value:3}]}
                      errorStyle={(localFormState.touched.contractorDetails?.[row.key]?.contractorClass && errors?.contractorDetails?.[row.key]?.contractorClass?.message) ? true : false}
                      select={(e) => {
                        props.onChange(e);
                      }}
                      optionKey="i18nKey"
                      onBlur={props.onBlur}
                      t={t}
                    />
                  )}
              />
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.contractorClass ? errors?.contractorDetails?.[row.key]?.contractorClass?.message : ""}</CardLabelError>
            </div>
          </td>
          <td style={getStyles(2)}>
            <div>
              <Controller
                  control={control}
                  name={`contractorDetails.${row.key}.status`}
                  // defaultValue={contractorDetails.bankName}
                  rules={{ required: t("REQUIRED_FIELD") }}
                  isMandatory={true}
                  render={(props) => (
                    <Dropdown
                      className="form-field"
                      selected={getValues(`contractorDetails.${row.key}.status`)}
                      disable={false}
                      // option={[{label:"active",value:1},{label:"Inactive",value:2},{label:"Black listed",value:3}]}
                      errorStyle={(localFormState.touched.contractorDetails?.[row.key]?.status && errors?.status?.contractorDetails?.[row.key]?.message) ? true : false}
                      select={(e) => {
                        props.onChange(e);
                      }}
                      optionKey="i18nKey"
                      onBlur={props.onBlur}
                      t={t}
                    />
                  )}
                />
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.status ? errors?.contractorDetails?.[row.key]?.status?.message : ""}</CardLabelError>
            </div>
          </td>
          <td>
            <div>
              <Controller
                name={`contractorDetails.${row.key}.fromDate`}
                control={control}
                defaultValue={null}
                rules={{ required: t("REQUIRED_FIELD") }}
                isMandatory={true}
                render={(props) =>
                   <DatePicker 
                    date={props.value} 
                    onChange={props.onChange} 
                    errorStyle={(localFormState.touched.contractorDetails?.[row.key]?.fromDate && errors?.contractorDetails?.[row.key]?.fromDate?.message) ? true : false}
                    />}
              />
              <CardLabelError>{localFormState.touched.contractorDetails?.[row.key]?.fromDate ? errors?.contractorDetails?.[row.key]?.fromDate?.message : ""}</CardLabelError>
            </div>
          </td>
          <td>
            <div>
            <Controller
                name={`contractorDetails.${row.key}.toDate`}
                rules={{ required: t("REQUIRED_FIELD") }}
                isMandatory={true}
                defaultValue={""}
                control={control}
                render={(props) => (
                  <DatePicker
                    date={props.value}
                    // date={CommencementDate} 
                    onChange={props.onChange}
                    //disabled={isRenewal}
                  />
                )}
              />
              <CardLabelError>{localFormState.touched.toDate ? errors?.toDate?.message : ""}</CardLabelError>
            </div>
          </td>
          <td>
          <LinkButton
              label={<DeleteIcon fill={"#494848"} />}
              style={{ margin: "10px" }}
              onClick={() => removeRow(row)} 
            />
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
    console.log(updatedState)
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
      <table className="contractor-table">
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}
        </tbody>
      </table>
      <div onClick={addRow} style={{cursor:"pointer",display:"flex",justifyContent:"center",alignItems:"center"}}><AddIcon fill={"#F47738"}/>{t("WORKS_ADD_ITEM")}</div>
    </div>
  )
}

export default WORKSContractorTable