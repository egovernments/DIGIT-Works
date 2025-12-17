import { TextInput, Dropdown, DeleteIcon, Button, AddIcon } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { Fragment } from "react";

function checkIntAndDecimalLength(number, decimalPlaces) {

  if(number == "" || isNaN(number))
    return true;
  var numStr = number.toString();
  // Regex to ensure up to 6 digits in the integer part and up to 4 decimal places
  var regex = new RegExp(`^\\d{1,6}(\\.\\d{0,${decimalPlaces}})?$`);
  return regex.test(numStr);
}

//Reverting this validation for digits and decimal in input value as state problem is happening
// function limitDecimalDigits(input) {

//   if(input == undefined || input === "")
//     return input;
//   // Remove non-digit characters and leading zeros
//   input = input.replace(/[^\d.]/g, '').replace(/^0+/, '');

//   // Split the value into integer and decimal parts
//   var parts = input.split('.');

//   // Limit the integer part to 6 digits
//   if (parts[0].length > 6) {
//       parts[0] = parts[0].slice(0, 6);
//   }

//   // Limit the decimal part to 4 digits
//   if (parts[1] && parts[1].length > 4) {
//       parts[1] = parts[1].slice(0, 4);
//   }

//   // Reconstruct the value
//   input = parts.join('.');
//   return input;
// }

const MeasureInputAtom = ({ id, row, mode, disable = false, fieldKey, value, dispatch, InputDecimalValidation, measurelineitemNo, style }) => {
  return(
  <td style={style ? style : {}}>
    <TextInput
      value={fieldKey === "description" || fieldKey === "measureSummary" ? value : (value > 0 && value)}
      //value={value}
      style={mode === "CREATE" || mode === "VIEW" ? {marginBottom:"0px"}: {}}
      type={fieldKey == "description" || fieldKey === "measureSummary" ? "text" : "number"}
      onChange={(newValue) => {
        //newValue.target.value = fieldKey == "description" ? newValue?.target?.value :  limitDecimalDigits(newValue.target.value);
        if(fieldKey === "description" || fieldKey === "measureSummary" || ((newValue?.target?.value && parseFloat(newValue?.target?.value) >= 0) || newValue?.target?.value === "" || newValue?.target?.value === null)){
        let updatedMeasureLineItems = []
        if(mode === "CREATE"){
          updatedMeasureLineItems = row?.additionalDetails?.measureLineItems?.length > 0 ? [...row?.additionalDetails?.measureLineItems] : [];
          let findMeasureIndex = updatedMeasureLineItems?.findIndex((ob) => ob?.measurelineitemNo === measurelineitemNo);
          updatedMeasureLineItems[findMeasureIndex][fieldKey] = newValue?.target?.value === "" ? (fieldKey === "measureSummary" ? "" : 0) : newValue?.target?.value;
        }
        //on addition of multimeasure updating its value inside additional details
        if(InputDecimalValidation?.active){
          //calling the input validation here to check if the input is under provided decimal places
            if(checkIntAndDecimalLength(parseFloat(newValue.target.value), InputDecimalValidation?.noOfDecimalPlaces))
            dispatch({
              type: "UPDATE_ROW",
              state: mode === "CREATE" ? { id: id, value: newValue.target.value, row: row, type: fieldKey, additionalDetails : {...row?.additionalDetails, measureLineItems : updatedMeasureLineItems }} : { id: id, value: newValue.target.value, row: row, type: fieldKey },
            });
        }
        else
        {
          dispatch({
            type: "UPDATE_ROW",
            state: mode === "CREATE" ? { id: id, value: newValue.target.value, row: row, type: fieldKey, additionalDetails : {...row?.additionalDetails, measureLineItems : updatedMeasureLineItems }} : { id: id, value: newValue.target.value, row: row, type: fieldKey },
          });
        }
      }
      }}
      disable={disable}
    />
  </td>)
};

const MeasureRow = ({ value, index, rowState, dispatch, mode, fields }) => {
  const { t } = useTranslation();
  const options = [
    { label: t("MB_YES"), code: true },
    { label: t("MB_NO"), code: false },
  ];
  const InputDecimalValidation = {
    active : true,
    noOfDecimalPlaces : 4
  }
  let firstMeasurelineitem = value?.additionalDetails?.measureLineItems?.[0];
  return (
    <>
    <tr key={index}>
      <td style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}}><div style={{marginBottom:"21px"}}>{index + 1}</div></td>
      {mode != "CREATEALL" && mode != "CREATERE" ? (
        <>
          <td style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}} >{rowState?.isDeduction ? t("MB_YES") : t("MB_NO")}</td>
          <td style={mode === "VIEW" ? {verticalAlign:"top"}:{}}>
            {rowState?.description}
            {rowState?.additionalDetails?.measureLineItems?.length == 1 && mode === "CREATE" && (
              <Button
                className={"outline-btn"}
                label={<div><AddIcon className="addIcon" fill={`#C84C0E`} styles={{margin:"revert"}}/> {t("MB_ADD_MORE_MBS")}</div>}
                variation="secondary"
                style={{width:"100%",borderRadius:"revert", marginLeft:"0px", marginRight:"0px"}}
                onButtonClick={() => {
                  dispatch({
                    type: "ADD_MEASURE",
                    state: {
                      //sNo: 1,
                      //targetId: "",
                      //isDeduction: false,
                      //description: "",
                      //id: index+1,
                      // measurelineitemNo: index+1,
                      // height: 0,
                      // width: 0,
                      // length: 0,
                      // number: 0,
                      // noOfunit: 0,
                      // rowAmount: 0,
                      // consumedRowQuantity: 0,
                      ...value,
                      id: index+1,
                      additionalDetails : {
                        ...value?.additionalDetails,
                        measureLineItems: [
                          ...value?.additionalDetails?.measureLineItems,
                          {
                          measureSummary: "",
                          number : 0,
                          length: 0,
                          width: 0,
                          height: 0,
                          quantity : 0,
                          measurelineitemNo: value?.additionalDetails?.measureLineItems[value?.additionalDetails?.measureLineItems.length-1]?.measurelineitemNo+1,
                        }]
                      }
                    },
                  });
                }}
              />
            )}
          </td>
        </>
      ) : (
        <>
          <td>
            <Dropdown
              t={t}
              select={(e) => {
                dispatch({
                  type: "UPDATE_ROW",
                  state: { id: index + 1, value: e?.code, row: value, type: "isDeduction" },
                });
              }}
              optionKey={"label"}
              option={options}
              selected={options?.filter((row) => row?.code == rowState?.isDeduction)?.[0]}
            />
          </td>
          <MeasureInputAtom
            dispatch={dispatch}
            row={value}
            fieldKey={"description"}
            id={index + 1}
            key={"description"}
            value={rowState?.["description"]}
            InputDecimalValidation={InputDecimalValidation}
          />
        </>
      )}
      {(mode === "CREATE" || mode === "VIEW") && <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"measureSummary"} id={index + 1} value={value?.additionalDetails?.measureLineItems?.length > 0 && firstMeasurelineitem?.["measureSummary"] ? firstMeasurelineitem?.["measureSummary"] : rowState?.["measureSummary"]} mode={mode} measurelineitemNo={firstMeasurelineitem?.measurelineitemNo} style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}} />}
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"number"} id={index + 1} value={value?.additionalDetails?.measureLineItems?.length > 0 ? firstMeasurelineitem?.["number"] : rowState?.["number"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={firstMeasurelineitem?.measurelineitemNo} style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"length"} id={index + 1} value={value?.additionalDetails?.measureLineItems?.length > 0 ? firstMeasurelineitem?.["length"] : rowState?.["length"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={firstMeasurelineitem?.measurelineitemNo} style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}}/>
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"width"} id={index + 1} value={value?.additionalDetails?.measureLineItems?.length > 0 ? firstMeasurelineitem?.["width"] : rowState?.["width"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={firstMeasurelineitem?.measurelineitemNo} style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}} />
      <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"height"} id={index + 1} value={value?.additionalDetails?.measureLineItems?.length > 0 ? firstMeasurelineitem?.["height"] : rowState?.["height"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={firstMeasurelineitem?.measurelineitemNo} style={mode === "CREATE" || mode === "VIEW" ? {verticalAlign:"top"}:{}} />
      <td style={value?.additionalDetails?.measureLineItems?.length == 1 ? {verticalAlign:"top"} : {}}><div style={mode === "CREATE" || mode === "VIEW"? {} : {marginBottom:"21px"}}>{value?.additionalDetails?.measureLineItems?.length > 0 ? firstMeasurelineitem?.["quantity"] : rowState?.noOfunit}</div></td>
      {(mode == "CREATEALL" || mode == "CREATERE") && fields?.length > 1 && (
        <td>
          <span
            className="icon-wrapper"
            onClick={(newValue) => {
              //added this condition as user should not able to delete row if only one is present
              if(fields?.length > 1 && (mode === "CREATERE" && (window?.location.href.includes("create") ? !(value?.id) : !(value?.previousLineItemId)) || mode !== "CREATERE"))
              dispatch({
                type: "REMOVE_ROW",
                id: index + 1,
              });
            }}
          >
            <DeleteIcon fill={mode === "CREATERE" && (window.location.href.includes("create")? value?.id : value?.previousLineItemId)? "lightgrey" : "#FF9100"} />
          </span>
        </td>
      )}
    </tr>
     {value && value?.additionalDetails?.measureLineItems?.length > 0 && value?.additionalDetails?.measureLineItems?.map((ob, idx) => {
      if (idx > 0) {
        return (
          <tr key={`additional_${idx}`}>
              {/* <td style={{borderTop:"hidden"}} colSpan={idx ===  (value?.additionalDetails?.measureLineItems?.length - 1) ? 2 : 3} /> */}
              {idx ===  (value?.additionalDetails?.measureLineItems?.length - 1) ?
               <><td style={{borderTop:"hidden"}}/><td style={{borderTop:"hidden"}}/></>
              :<><td style={{borderTop:"hidden"}}/><td style={{borderTop:"hidden"}}/><td style={{borderTop:"hidden"}}/></>
              }    
          {idx ===  (value?.additionalDetails?.measureLineItems?.length - 1) && mode === "CREATE" && <td style={{borderTop:"hidden"}}>
            {(
              <Button
                className={"outline-btn"}
                label={<><AddIcon className="addIcon" fill={`#C84C0E`} styles={{margin:"revert"}}/> {t("MB_ADD_MORE_MBS")}</>}
                style={{width:"100%",borderRadius:"revert", marginLeft:"0px", marginRight:"0px"}}
                onButtonClick={() => {
                  dispatch({
                    type: "ADD_MEASURE",
                    state: {
                      ...value,
                      id: index+1,
                      additionalDetails : {
                        ...value?.additionalDetails,
                        measureLineItems: [
                          ...value?.additionalDetails?.measureLineItems,
                          {
                          measureSummary:"",
                          number : 0,
                          length: 0,
                          width: 0,
                          height: 0,
                          quantity : 0,
                          measurelineitemNo: value?.additionalDetails?.measureLineItems[value?.additionalDetails?.measureLineItems.length-1]?.measurelineitemNo+1,
                        }]
                      }
                    },
                  });
                }}
              />
            )}
          </td>}
          {idx ===  (value?.additionalDetails?.measureLineItems?.length - 1) && mode === "VIEW" && <td style={{borderTop:"hidden"}}></td>}
          {(mode === "CREATE" || mode === "VIEW") && <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"measureSummary"} id={index + 1} value={ob?.["measureSummary"]} mode={mode} measurelineitemNo={ob?.measurelineitemNo} />}
              <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"number"} id={index + 1} value={ob?.["number"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={ob?.measurelineitemNo} />
              <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"length"} id={index + 1} value={ob?.["length"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={ob?.measurelineitemNo}/>
              <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"width"} id={index + 1} value={ob?.["width"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={ob?.measurelineitemNo} />
              <MeasureInputAtom dispatch={dispatch} row={value} disable={mode.includes("VIEW")} fieldKey={"height"} id={index + 1} value={ob?.["height"]} mode={mode} InputDecimalValidation={InputDecimalValidation} measurelineitemNo={ob?.measurelineitemNo} />
              <td><div style={mode === "CREATE" || mode === "VIEW" ? {}:{marginBottom:"21px"}}>{ob?.["quantity"]}</div></td>
              {(mode == "CREATE") && value?.additionalDetails?.measureLineItems?.length > 1 && (
                <td>
                  <span
                    className="icon-wrapper"
                    onClick={(newValue) => {
                      dispatch({
                        type: "REMOVE_MEASURE_ROW",
                        id: index + 1,
                        measurelineitemNo: ob?.measurelineitemNo,
                      });
                    }}
                  >
                    <DeleteIcon fill={"#FF9100"} />
                  </span>
                </td>
              )}
          </tr>
        );
      }
      return null;
    })}
    </>
  );
};

export default MeasureRow;
