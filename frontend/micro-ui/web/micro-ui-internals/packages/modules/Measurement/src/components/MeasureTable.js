import { AddIcon, TextInput, Amount, Button, Dropdown, Loader, DeleteIcon, TextArea,CardSectionHeader } from "@egovernments/digit-ui-react-components";
import { TextBlock } from "@egovernments/digit-ui-components";
import React, { Fragment, useEffect, useCallback} from "react";
import { useTranslation } from "react-i18next";
import MeasureCard from "./MeasureCard";

/*
4 different modes this component been used 
1. CREATE -> measurement create and update
2. VIEW -> view of measurement
3. CREATEALL -> Detailed Estimate create
4. VIEWES -> view of detailed estimate
5. CREATERE -> revised estimate create and update
6. VIEWRE -> view of revised estimate

*/

let defaultSOR = {
      "amount": 0,
      "consumedQ": 0,
      "sNo": 1,
      "uom": null,
      "description": "",
      "unitRate": 0,
      "category": "NON-SOR",
      "targetId": null,
      "approvedQuantity": 0,
      "currentMBEntry" : 0,
      "measures": [
          {
              "sNo": 1,
              "targetId": null,
              "isDeduction": false,
              "description": null || "   ",
              "id": 0,
              "height": 0,
              "width": 0,
              "length": 0,
              "number": 0,
              "noOfunit": 0,
              "rowAmount": 0,
              "consumedRowQuantity": 0
          }
      ]
  };

  function checkIntAndDecimalLength(number, decimalPlaces) {
    if(number == "")
    {  
        return true;
    }
    var numStr = number.toString();
    // Regex to ensure up to 6 digits in the integer part and up to 4 decimal places
    var regex = new RegExp(`^\\d{1,6}(\\.\\d{0,${decimalPlaces}})?$`);
    return regex.test(numStr);
  }

const MeasureTable = (props) => {
  const { register, setValue, arrayProps = {}, config = {},watch } = props;
  let { key: tableKey, mode } = config;
  mode = props?.props?.mode || props?.config?.mode ? props?.props?.mode || props?.config?.mode : mode;
  let { fields, append, remove } = arrayProps || {};
  const options = {
    masterName: "UOM",
    moduleName: "common-masters",
    localePrefix: "ES_COMMON_UOM",
  };
  if(mode?.includes("CREATE")){
    fields = watch?.(`${tableKey}table`);
    append = (val)=>{
      setFormValue([...fields,val]);
    }
    remove = (index)=>{
      if(fields?.length == 1)
      {
        fields = JSON.parse(JSON.stringify([defaultSOR]));
        setFormValue([...fields]);
      }
      else
      setFormValue([...fields.filter((e,ind)=>ind!=index)]);
    }  
  }
  const setFormValue = useCallback(
    (value) => {
      setValue(tableKey, value);
      setValue(`${tableKey}table`, value);
    },
    [setValue, tableKey]
  );

  const requestCriteria = {
    url: "/mdms-v2/v1/_search",
    body: {
      MdmsCriteria: {
        moduleDetails : [

          {
            "moduleName": options?.moduleName,
            "masterDetails": [
                {
                    "name": options?.masterName
                    
                }  
            ]
        },
        ],
        "tenantId": Digit.ULBService.getCurrentTenantId(),
      },
    },
    config: {
            select: (data) => {
              const optionsData = _.get(data?.MdmsRes, `${options?.moduleName}.${options?.masterName}`, []);
              return optionsData?.filter((opt) => opt?.active === undefined || opt?.active === true).map((opt) => ({ ...opt, name: `${options?.localePrefix}_${opt.code}` }));
            },
            enabled: mode == "CREATEALL" || mode === "CREATERE",
          },
  };
  const { isLoading, data : UOMData} = Digit.Hooks.useCustomAPIHook(requestCriteria);

  const { t } = useTranslation();
  const sum = parseFloat(fields?.reduce((acc, row) => acc + parseFloat(row?.amount), 0)?.toFixed?.(2)) || 0;
  let formattedSum =  isNaN(sum) ? 0 : parseFloat(sum)?.toFixed(2)

  function checkIfDeletionisAllowed(row){
    if(window.location.href.includes("create"))
      return (mode === "CREATERE" && ((row?.category === "SOR" && !(row?.amountDetails))|| (row?.category === "NON-SOR" && (row?.hasOwnProperty('originalQty'))) ) )
    else if(window.location.href.includes("update"))
      return (mode === "CREATERE" && row.originalQty);

    return false;
  }

  // register(tableKey)

  useEffect(() => {
    // console.log("tableKey, fields",tableKey, fields)
    register(tableKey, fields);
    register(`${tableKey}table`, fields);
  }, []);
  const getStyles = (index) => {
    let obj = {};
    switch (index) {
      case 1:
        obj = { width: "1rem" };
        break;
      case 2:
        if (((mode === "CREATEALL" || mode === "VIEWES" || mode === "CREATERE" || mode === "VIEWRE") && tableKey === "NONSOR") || (mode !== "CREATEALL" && mode !== "VIEWES" && mode !== "CREATERE" && mode !== "VIEWRE") ) obj = { width: "52%"}
        if(mode === "CREATERE" && tableKey === "NONSOR") obj = { width: "45%"}
        if((mode === "CREATEALL" || mode === "VIEWES" || mode === "VIEWRE") && tableKey === "NONSOR") obj = { width : "49%"}
        break;
      case 4:
        (((mode === "CREATEALL" || mode === "VIEWES" || mode === "CREATERE" || mode === "VIEWRE") && tableKey === "NONSOR") || (mode !== "CREATEALL" && mode !== "VIEWES" && mode !== "CREATERE" && mode !== "VIEWRE"))? obj = {width : "27rem"}  : obj = { width: "30%" };
        if(mode === "CREATERE" && tableKey === "NONSOR") obj = { width : "73rem"}
        if(mode === "CREATERE" && tableKey === "SOR") obj = {width : "26%"}
        break;
      case 5:
        if(mode === "VIEWRE" && tableKey === "NONSOR") obj = { width: "0rem" };
        obj = { width : "27rem"};
        break;
      case 3:
        if(mode === "CREATERE" && tableKey === "NONSOR") obj = {width : "70rem"}
        if(mode === "CREATEALL" && tableKey === "NONSOR") obj = {width : "16rem"}
        if(mode === "VIEWES" && tableKey === "NONSOR") obj = { width : "27rem"}
        if(mode === "VIEWRE" && tableKey === "NONSOR") obj = { width : "27rem"}
        break;
      case 7:
        if(((mode === "VIEWRE" || mode === "CREATERE") && tableKey === "NONSOR") || (mode === "VIEW" || mode === "CREATE")) obj = { width: "130rem" };
        if(mode === "CREATEALL" && tableKey === "SOR") obj = { width : "27rem"}
        if(mode === "VIEWES" && tableKey === "SOR") obj = { width : "24rem" }
        break;
      case 9:
        if (mode === "VIEWRE" || mode === "CREATERE" && tableKey === "SOR")  obj = { width: "130rem" }
        if (tableKey === "SOR" && mode === "CREATERE") obj = {width : "84rem"}
        break;
      case 11:
        if (mode === "CREATERE" && mode === "SOR") obj = {width : "0rem"}
        break;
      default:
        obj = { width: "27rem" };
        break;
    }
    return obj;
  };

  const getColumns = (mode, t) => {
    if(mode === "CREATERE" && tableKey === "SOR")
      return [t("WORKS_SNO"), t("SOR_TYPE"), t("WORKS_CODE"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ORIGINAL_QTY"), t("WORKS_ORIGINAL_AMT"), t("WORKS_REVISED_QTY"), t("WORKS_REVISED_AMT"), t("")];
    if (mode === "CREATEALL" && tableKey === "SOR") {
      return [t("WORKS_SNO"), t("SOR_TYPE"), t("WORKS_CODE"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ESTIMATED_QUANTITY"), t("WORKS_ESTIMATED_AMOUNT"), t("")];
    } else if (mode === "VIEWES" && tableKey === "SOR") {
      return [t("WORKS_SNO"), t("SOR_TYPE"), t("WORKS_CODE"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ESTIMATED_QUANTITY"), t("WORKS_ESTIMATED_AMOUNT")];
    } else if(mode === "VIEWRE" & tableKey === "SOR")
      return [t("WORKS_SNO"), t("SOR_TYPE"), t("WORKS_CODE"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"),t("WORKS_ORIGINAL_QTY"), t("WORKS_ORIGINAL_AMT"), t("WORKS_REVISIED_ESTIMATED_QUANTITY"), t("WORKS_REVISED_ESTIMATED_AMOUNT")];
    else if (mode === "CREATEALL") {
      return [t("WORKS_SNO"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ESTIMATED_QUANTITY"), t("WORKS_ESTIMATED_AMOUNT"), t("")];
    } else if(mode === "CREATERE"){
      return [t("WORKS_SNO"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ORIGINAL_QTY"), t("WORKS_ORIGINAL_AMT"), t("WORKS_REVISED_QTY"), t("WORKS_REVISED_AMT"), t("")];
    }
    else if(mode === "VIEWRE")
      return [t("WORKS_SNO"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ORIGINAL_QTY"), t("WORKS_ORIGINAL_AMT"), t("WORKS_ESTIMATED_REVISED_QUANTITY"), t("WORKS_ESTIMATED_REVISED_AMOUNT")];
    else if (mode === "VIEWES") {
      return [t("WORKS_SNO"), t("PROJECT_DESC"), t("PROJECT_UOM"), t("CS_COMMON_RATE"), t("WORKS_ESTIMATED_QUANTITY"), t("WORKS_ESTIMATED_AMOUNT")];
    } 
    else {
      return [
        t("WORKS_SNO"),
        t("MB_DESCRIPTION"),
        t("MB_UNIT"),
        t("MB_RATE"),
        t("MB_APPROVER_QUANT"),
        t("MB_CONSUMED_QUANT"),
        t("MB_CURRENT_MB_ENTRY"),
        t("MB_AMOUNT_CURRENT_ENTRY"),
      ];
    }
  };
  

  let columns = getColumns(mode, t);

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

  const getMeasureCardColumns = () => {
    if(mode === "CREATEALL" || mode === "VIEWES" || mode === "CREATERE" || mode === "VIEWRE")
      return ([
        t("WORKS_SNO"),
        t("WORKS_ESTIMATE_IS_DEDUCTION"),
        t("WORKS_ESTIMATE_DESCRIPTION"),
        t("WORKS_ESTIMATE_ONLY_NUMBER"),
        t("WORKS_ESTIMATE_LENGTH"),
        t("WORKS_ESTIMATE_WIDTH"),
        t("WORKS_ESTIMATE_HEIGHT"),
        t("WORKS_ESTIMATE_QUANTITY"),
      ]);
    else 
      return ([
        t("WORKS_SNO"),
        t("MB_IS_DEDUCTION"),
        t("MB_DESCRIPTION"),
        t("MB_MEASURE_SUMMARY"),
        t("MB_ONLY_NUMBER"),
        t("MB_LENGTH"),
        t("MB_WIDTH"),
        t("MB_HEIGHT"),
        t("MB_QUANTITY"),
      ]);
  }

  const renderBody = () => {
    // Update the state with the new data
    const handleInputChange = useCallback(
      (key, value, index) => {
        const field = fields[index] || {};
        field[key] = value;
        if(tableKey === "NONSOR" && key === "unitRate")
        {  
          field["amount"] = (parseFloat(field[key]) * field["currentMBEntry"]) || 0
        }

        fields[index] = { ...field };
        if(tableKey === "NONSOR" && key === "unitRate" && !(checkIntAndDecimalLength(field[key],2)))
        {
          return;
        }
        setFormValue(fields);
      }
    );
    return fields?.map((row, index) => {
      const consumedQty = row.currentMBEntry || 0;
      const initialState = { tableState: row?.measures };
      const optionsData = UOMData?.map((obj) => ({ code: obj?.code, name: obj?.name }));
      if (isLoading) {
        return <Loader />;
      }
      return (
        <>
          <tr key={index}>
            <td>{index + 1}</td>
            {(mode == "CREATEALL" || mode == "CREATERE") && tableKey!="SOR" ? (
              <>
                <td style={{margin:"0px",padding:"8px"}}>
                  { tableKey === "NONSOR"?
                    <TextArea
                    style={{ marginBottom: "0px", wordWrap: "break-word" }}
                    onChange={(e) => handleInputChange("description", e.target.value, index)}
                    value={row.description}
                  />
                    :<TextInput
                    style={{ width: "100%", marginTop: "20px"  }}
                    //  {...register(`SOR.${index}.description`)}

                    onChange={(e) => handleInputChange("description", e.target.value, index)}
                    value={row.description}
                  />}
                </td>
                <td>
                  <Dropdown
                    // inputRef={register()}
                    option={optionsData}
                    selected={optionsData?.filter(e=>e.code==row.uom)?.[0]||{code:null}}
                    optionKey="name"
                    t={t}
                    select={(selectedOption) => handleInputChange("uom", selectedOption?.code, index)}
                    optionCardStyles={{ maxHeight: "15rem" }}
                    style={{ marginBottom: "4px" }}
                  />
                </td>
                <td>
                  <TextInput
                    style={{ width: "80%", marginTop: "20px", marginLeft: (mode === "CREATERE" && tableKey === "NONSOR") ? "5px" : "20px" }}
                    onChange={(e) => handleInputChange("unitRate", e.target.value, index)}
                    value={row.unitRate}
                  />
                </td>
              </>
            ) : (
              <>
                {/*added this dummy line because project creation and search is failing will check this once it works */}
                {/*((mode === "VIEWES") && tableKey === "SOR") &&<td>{`${t(`${"Works_D"}`)}/ ${t(`${"SC_D"}`)}`}</td>*/}
                {tableKey === "SOR" && (mode === "CREATEALL" || mode === "VIEWES" || mode === "CREATERE" || mode === "VIEWRE") && (((row?.sorType || row?.sorSubType)) ?<td>{`${t(`WORKS_SOR_TYPE_${row?.sorType}`)}/ ${t(`WORKS_SOR_SUBTYPE_${row?.sorSubType}`)}`}</td> : <td>{t(" ")}</td>)}
                {((mode === "CREATEALL" || mode === "VIEWES" || mode === "VIEWES" || mode === "CREATERE" || mode === "VIEWRE") && tableKey === "SOR") && <td>{row?.sorCode}</td>}
                <td>
                <div className="tooltip" >
                {row.description?.length > 128 ? `${row.description.substring(0,128)}...` : row?.description}
                                {row.description?.length > 128 &&<span className="tooltiptext" style={{
                                    whiteSpace: "nowrap",
                                    fontSize: "medium",
                                    bottom : "100%",
                                    overflow : "auto",
                                    left:"100%",
                                    marginLeft:"0px"
                                }}>
                                    {row.description}
                                </span>}
                            </div>
                </td>
                <td>{row.uom}</td>
                <td>
                  <Amount customStyle={{ textAlign: "right" }} value={Digit.Utils.dss.formatterWithoutRound(parseFloat(row?.unitRate).toFixed(2), "number", undefined, true, undefined, 2) || 0} t={t} roundOff={false} sameDisplay={true}></Amount>
                </td>
              </>
            )}
            {(mode === "VIEWRE" || mode === "CREATERE") && (
              <>
                <td>
                  <Amount customStyle={{ textAlign: "right" }} value={row?.originalQty?.toFixed?.(2) || 0} t={t} roundOff={false}></Amount>
                </td>
                <td>
                  <Amount customStyle={{ textAlign: "right" }} value={Digit.Utils.dss.formatterWithoutRound(parseFloat(isNaN(row?.originalAmount) ? 0 : row?.originalAmount).toFixed(2), "number", undefined, true, undefined, 2) || 0} t={t} roundOff={false} sameDisplay={true}></Amount>
                </td>
              </>
            )}
            {(mode != "CREATEALL"  && mode != "VIEWES" && mode != "VIEWRE" && mode != "CREATERE")  && (
              <>
                <td>
                  <Amount customStyle={{ textAlign: "right" }} value={row?.approvedQuantity?.toFixed?.(2) || 0} t={t} roundOff={false}></Amount>
                </td>
                <td>
                  <Amount customStyle={{ textAlign: "right" }} value={row?.consumedQ?.toFixed?.(2) || 0} t={t} roundOff={false}></Amount>
                </td>
              </>
            )}
            <td>
              <span className="measurement-table-input">
                <TextInput
                  style={{ width: "90%", marginTop: "12px" }}
                  textInputStyle={{marginTop:"7px"}}
                  key={row?.id} // important to include key with field's id
                  // {...register(`${tableKey}.${index}.currentMBEntry`)}
                  value={consumedQty?.toFixed(4)}
                  onChange={() => {}}
                  disable={initialState.length > 0 ? "true" : "false"}
                />
                <Button
                  className={"plus-button"}
                  onButtonClick={() => {
                    if(mode === "CREATEALL" && (row?.category === "SOR" && row?.description || row?.category === "NON-SOR") || true){
                    const measure = {
                      sNo: 0,
                      targetId: 0,
                      isDeduction: false,
                      description: "",
                      id: "",
                      height: 0,
                      width: 0,
                      length: 0,
                      number: 0,
                      noOfunit: 0,
                      rowAmount: 0,
                      consumedRowQuantity: 0,
                    };
                    const measures = fields?.[index]?.measures?.length > 0 ? fields?.[index]?.measures : [measure];
                    fields[index] = { ...fields[index], showMeasure: true, measures: measures };
                    setFormValue(fields);
                  }
                  }}
                  label={"+"}
                >
                  <AddIcon className="addIcon" />
                </Button>
              </span>
            </td>

            <td>
              <Amount customStyle={{ textAlign: "right" }} value={Digit.Utils.dss.formatterWithoutRound(parseFloat(row?.amount).toFixed(2), "number", undefined, true, undefined, 2)  || 0} t={t} roundOff={false} sameDisplay={true}></Amount>
            </td>
            {(mode == "CREATEALL" || mode == "CREATERE") && (
              <td>
                <span className="icon-wrapper" onClick={() => checkIfDeletionisAllowed(row) ? "" : remove(index)}>
                  <DeleteIcon fill={checkIfDeletionisAllowed(row) ? "lightgrey" : "#C84C0E"} />
                </span>
              </td>
            )}
          </tr>
          {row?.showMeasure && !initialState.length > 0 && (
            <tr>
              <td colSpan={"1"}></td>
              <td colSpan={(mode == "CREATEALL"  || mode == "CREATERE") && tableKey !== "SOR" ? ( mode === "CREATERE" || mode === "VIEWRE" ? 7 : 5) : (mode === "CREATERE" || mode === "VIEWRE" ? 9 : 7)}>
                <MeasureCard
                  columns={getMeasureCardColumns()}
                  unitRate={row.unitRate}
                  fields={row?.measures || []}
                  register={register}
                  setValue={setFormValue}
                  tableKey={tableKey}
                  tableData={fields}
                  tableIndex={index}
                  mode={mode}
                />
              </td>
            </tr>
          )}
        </>
      );
    });
  };

  return (
    <React.Fragment>
      <table className="table reports-table sub-work-table measurement-table-custom">
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>
          {renderBody()}
          <tr>
            {(mode == "CREATEALL" || mode == "CREATERE") && tableKey == "NONSOR" && (
              <td
                colSpan={mode === "CREATERE" ? 8 : 6}
                style={{ textAlign: "center" }}
                onClick={() => {
                  append({
                    amount: 0,
                    consumedQ: 0,
                    category: "NON-SOR",
                    sNo: fields?.length + 1,
                    currentMBEntry: 0,
                    uom: null,
                    description: "",
                    unitRate: "",
                    contractNumber: "",
                    targetId: "",
                    approvedQuantity: "",
                    measures: [],
                  });
                }}
              >
                <span>
                  <AddIcon fill={"#C84C0E"} styles={{ margin: "auto", display: "inline", marginTop: "-2px" }} />
                  <label style={{ marginLeft: "10px", fontWeight: "600", color: " #C84C0E" }}>{t("WORKS_ADD_SOR")}</label>
                </span>
              </td>
            )}
          </tr>
        </tbody>
      </table>
      {/* <div style={{ display: "flex", flexDirection: "row", justifyContent: "flex-end", margin: "20px" }}>
        <div style={{ display: "flex", flexDirection: "row", fontSize: "16px" }}>
          <span style={{ fontWeight: "bold", marginTop:"6px" }}>
            {t("WORKS_TABLE_TOTAL_AMOUNT")} :
          </span>
          <span style={{ marginLeft: "8px" }}>
            <Amount customStyle={{ textAlign: "right", fontSize:"24px", fontWeight:"700" }} value={Digit.Utils.dss.formatterWithoutRound(formattedSum, "number", undefined, true, undefined, 2) || 0} t={t} roundOff={false} rupeeSymbol={true} sameDisplay={true}></Amount>
          </span>
        </div>
      </div> */}

      <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "1.5rem" }}>
        <div className={"total_amount_wrapper"}>
          {/* <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F",fontSize:"18px",width:"fit-content"}}>{`${t(
            "WORKS_TABLE_TOTAL_AMOUNT"
          )} :`}</CardSectionHeader>
          <CardSectionHeader style={{ width:"fit-content",marginBottom: "0px" }}>
            {
              <Amount
                customStyle={{ textAlign: "right", fontSize: "24px", fontWeight: "700" }}
                value={Digit.Utils.dss.formatterWithoutRound(formattedSum, "number", undefined, true, undefined, 2) || 0}
                t={t}
                roundOff={false}
                rupeeSymbol={true}
                sameDisplay={true}
              ></Amount>
            }
          </CardSectionHeader> */}

          <TextBlock subHeader={`${t("WORKS_TABLE_TOTAL_AMOUNT")} :`} subHeaderClassName={"table_total_amount"}></TextBlock>
          <TextBlock
            subHeader={
              <Amount
                customStyle={{ textAlign: "right", fontSize: "24px", fontWeight: "700" }}
                value={Digit.Utils.dss.formatterWithoutRound(formattedSum, "number", undefined, true, undefined, 2) || 0}
                t={t}
                roundOff={false}
                rupeeSymbol={true}
                sameDisplay={true}
              ></Amount>
            }
            subHeaderClassName={`table_total_amount_value`}
          ></TextBlock>
        </div>
      </div>
    </React.Fragment>
  );
};

export default MeasureTable;
