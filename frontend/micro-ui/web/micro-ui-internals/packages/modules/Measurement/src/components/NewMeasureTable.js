
import { AddIcon, Card, TextInput, Amount, Button, Dropdown, Loader, DeleteIcon } from "@egovernments/digit-ui-react-components";

import React, { useState, Fragment, useEffect } from "react";
import {useFieldArray} from "react-hook-form";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureCard from "./NewMeasureCard";

const MeasureTable = (props) => {

  const tableKey=props?.config?.key|| "SOR";
  const {watch}=props;
  const stateData= watch&&watch?.( tableKey);
  const data = stateData ||( props?.data?.[tableKey]?.length > 0 ? props?.data?.[tableKey] : null);
  const [showMeasureCard, setShowMeasureCard] = useState(false);

  const { fields, append, remove } =props?.arrayProps ||{};
  console.log(stateData,data,'stateData',fields, append, remove );
  const options = {
    masterName: "uom",
    moduleName: "common-masters",
    localePrefix: "ES_COMMON_UOM",
  }

  const { isLoading, data:UOMData } = Digit.Hooks.useCustomMDMS(
    Digit.ULBService.getStateId(),
    options?.moduleName,
    [{ name: options?.masterName }],
    {
      select: (data) => {
        const optionsData = _.get(data, `${options?.moduleName}.${options?.masterName}`, []);
        return optionsData.filter((opt) => opt?.active).map((opt) => ({ ...opt, name: `${options?.localePrefix}_${opt.code}` }));
      },
      enabled: props?.props?.isEstimate,
    }
  );

  const { t } = useTranslation();
  const history = useHistory();
  const [totalMBAmount, setTotalMBAmount] = useState(0);
  const { register, setValue } = props;
  const sum = parseFloat(fields.reduce((acc, row) => acc + parseFloat(row?.amount), 0)?.toFixed?.(2))||0;


  if (!props?.isView) {
    register("sumSor", 0);
    register("sumNonSor", 0);
  }

  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        obj = { width: "30%" };
        break;
      case 3:
        obj = { width: "27rem" };
        break;
      case 4:
        obj = { width: "27rem" };
        break;
      case 5:
        obj = { width: "3%" };
        break;
      default:
        obj = { width: "92rem" };
        break;
    }
    return obj;
  };
  

  let columns = props?.props?.isEstimate ?
  [
    t("WORKS_SNO"),
    t("MB_DESCRIPTION"),
    t("MB_UNIT"),
    t("MB_RATE"),
    t("MB_CURRENT_MB_ENTRY"),
    t("MB_AMOUNT_CURRENT_ENTRY"),
    t(""),
  ]
  : [
    t("WORKS_SNO"),
    t("MB_DESCRIPTION"),
    t("MB_UNIT"),
    t("MB_RATE"),
    t("MB_APPROVER_QUANT"),
    t("MB_CONSUMED_QUANT"),
    t("MB_CURRENT_MB_ENTRY"),
    t("MB_AMOUNT_CURRENT_ENTRY"),
  ];
  const renderHeader = () => {
    return columns?.map((key, index) => {
      return (
        <th key={index} style={getStyles(index + 1)}>
          {" "}
          {key}{" "}
        </th>
      );
    });
  };


  const renderBody = () => {
    // Update the state with the new data
    return fields?.map((row, index) => {
      console.log(row,'row');
      // const [consumedQty, setConsumedQty] = useState(row.currentMBEntry);
      // const [initialState, setInitialState] = useState({ tableState: row?.measures });

      const consumedQty =row.currentMBEntry;

const initialState = { tableState: row?.measures };
const setConsumedQty =(val)=>consumedQty=val;

const setInitialState =(val)=>initialState=val;
      
      const optionsData = UOMData?.map(obj => ({ code: obj?.code, name: obj?.name }));
      if (isLoading) {
        return <Loader />
      }
      return (
        <>
          <tr key={index}>
            <td>{index + 1}</td>
            {props?.props?.isEstimate ? <TextInput style={{ width: "80%", marginTop: "27px", marginLeft: "35px" }} 
                      //  {...register(`SOR.${index}.description`)} 

            // onChange={(e) => handleInputChange("description", e.target.value, index)}
              value={row.description} /> : <td>{row.description}</td>}
            {props?.props?.isEstimate ?
              <td><Dropdown
                inputRef={register()}
                option={optionsData}
                selected={row.uom}
                optionKey="name"
                t={t}
                // select={(selectedOption) => handleInputChange("uom", selectedOption, index)}
                onBlur={props?.onBlur}
                optionCardStyles={{ maxHeight: "15rem" }}
                style={{ marginBottom: "0px" }}
              /></td>
              :
              <td>{row.uom}</td>}
            {props?.props?.isEstimate ? <TextInput style={{ width: "80%", marginTop: "20px", marginLeft: "20px" }} 
            // onChange={(e) => handleInputChange("unitRate", e.target.value, index)}
              value={row.unitRate}
            /> : <td><Amount customStyle={{ textAlign: 'right' }} value={row?.unitRate?.toFixed?.(2)} t={t} roundOff={false}></Amount></td>}
            {!props?.props?.isEstimate && (
              <>
                <td><Amount customStyle={{ textAlign: 'right' }} value={row?.approvedQuantity?.toFixed?.(2)} t={t} roundOff={false}></Amount></td>
                <td><Amount customStyle={{ textAlign: 'right' }} value={row?.consumedQ?.toFixed?.(2)} t={t} roundOff={false}></Amount></td>
              </>
            )}
            <td>
              <span className="measurement-table-input">
                <TextInput style={{ width: "80%", marginTop: "12px" }} value={consumedQty} 
                // onChange={() => { }} 
                disable={initialState.length > 0 ? "true" : "false"} />
                <Button
                  className={"plus-button"}
                  onButtonClick={() => {
                    const measure={sNo: 0,
                      targetId: 0,
                      isDeduction: false,
                      description: "",
                      id: "",
                      height: 0,
                      width: 0,
                      length:0,
                      number: 0,
                      noOfunit: 0,
                      rowAmount: 0,
                      consumedRowQuantity:0 };
                      const measures=fields?.[index]?.measures?.length>0?fields?.[index]?.measures:[measure];
                    fields[index]={...fields[index], showMeasure:true,measures:measures
}
console.log(fields,"fieldsfields")
setValue(tableKey,fields)
                    // setValue(tableKey,data)
                    // setShowMeasureCard(!showMeasureCard);
                  }}
                  label={"+"}
                >
                  <AddIcon className="addIcon" />
                </Button>
              </span>
            </td>

            <td><Amount customStyle={{ textAlign: 'right' }} value={row.amount} t={t} roundOff={false}></Amount></td>
            {props?.props?.isEstimate && <td>
              <span className="icon-wrapper" onClick={() => removeRow(row)}>
                <DeleteIcon fill={"#B1B4B6"} />
              </span>
            </td>}

          </tr>
          {row?.showMeasure && !initialState.length > 0 && (
            <tr>
              <td colSpan={"1"}></td>
              <td colSpan={props?.props?.isEstimate ? 5 : 7}>
                <MeasureCard columns={[
                  t("WORKS_SNO"),
                  t("MB_IS_DEDUCTION"),
                  t("MB_DESCRIPTION"),
                  t("MB_ONLY_NUMBER"),
                  t("MB_LENGTH"),
                  t("MB_WIDTH"),
                  t("MB_HEIGHT"),
                  t("MB_QUANTITY"),

                ]}
                 consumedQty={consumedQty}
                  setConsumedQty={setConsumedQty}
                  setInitialState={setInitialState}
                  initialState={initialState}
                  unitRate={row.unitRate}
                  register={props.isView ? () => { } : register}
                  setValue={props.isView ? () => { } : setValue}
                  tableKey={tableKey}
                  tableData={fields}
                  tableIndex={index}
                  isEstimates={props?.props?.isEstimate}
                  isView={props?.isView} />

              </td>
            </tr>
          )}
        </>
      );
    });
  };

  return (
    // <Card className="override-card">
    <React.Fragment>
      <table className="table reports-table sub-work-table measurement-table-custom">
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}

        </tbody>

      </table>
      <button onClick={()=>{
append({amount:0,
  consumedQ: 0,
  sNo: 2,
  currentMBEntry:0,
  uom: null,
  description: "test",
  unitRate: "",
  contractNumber: "",
  targetId: "id",
  approvedQuantity:"",
  measures:[],})
      }}>
add
      </button>
      <button onClick={()=>{
remove(1)
}}>
remove
</button>
<button onClick={()=>{
  fields[2]={...fields[2],  approvedQuantity:"4",  description: "testupdate", sNo: 2,
  currentMBEntry:3,measures:[  {sNo: 1,
    targetId: 0,
    isDeduction: false,
    description: "test sub",
    id: "s",
    height: 0,
    width: 2,
    length:23,
    number: 4,
    noOfunit: 5,
    rowAmount: 6,
    consumedRowQuantity:0 }]

}
console.log(fields,"fieldsfields")
setValue(tableKey,fields)}}>
update
      </button>
      <div style={{ display: "flex", flexDirection: "row", justifyContent: "flex-end", margin: "20px" }}>
        <div style={{ display: "flex", flexDirection: "row", fontSize: "1.2rem" }}>

          {/* <span style={{ fontWeight: "bold" }}>`{t("MB_TOTAL")} ${props.config.key} {t("MB_TOTAL_AMOUNT")}`</span> */}
          <span style={{ fontWeight: "bold" }}>{t("MB_TOTAL")}  {props.config.key} {t("MB_TOTAL_AMOUNT")} :</span>

          <span style={{ marginLeft: "3px" }}><Amount customStyle={{ textAlign: 'right' }} value={sum?.toFixed?.(2)} t={t} roundOff={false}></Amount></span>

        </div>
      </div>
    </React.Fragment>
    // </Card>
  );
};
//think more update table should have option to update whole table state similarly card should update back the parent state

export default MeasureTable;
