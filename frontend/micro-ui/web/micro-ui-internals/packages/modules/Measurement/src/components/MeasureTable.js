
import { AddIcon, Card, TextInput, Amount, Button, Dropdown, Loader, DeleteIcon } from "@egovernments/digit-ui-react-components";

import React, { useState, Fragment, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureCard from "./MeasureCard";

const MeasureTable = (props) => {
  const sorData = props?.data?.SOR?.length > 0 ? props?.data?.SOR : null;
  const nonsorData = props?.data?.NONSOR?.length > 0 ? props.data.NONSOR : null;
  const data = props?.config?.key === "SOR" ? sorData : nonsorData;
  const [table, setTable] = useState(props.data);

  const [tablesState, setTablesState] = useState(data);
  const tableKey = props.config.key;
  const { t } = useTranslation();
  const history = useHistory();
  const [totalMBAmount, setTotalMBAmount] = useState(0);
  const { register, setValue } = props;


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
    return tablesState?.map((row, index) => {
      const [consumedQty, setConsumedQty] = useState(row.currentMBEntry);
      const [showMeasureCard, setShowMeasureCard] = useState(false);
      const [initialState, setInitialState] = useState({ tableState: row?.measures });

      useEffect(() => {
        tablesState[index].measures = initialState?.tableState;
        let updatedData = tablesState?.map(tableRow => {

          const currentMBEntry = tableRow?.measures.reduce((total, item) => total + item.noOfunit, 0);

          const amount = (currentMBEntry * tableRow?.unitRate).toFixed(2);

          return {
            ...tableRow,
            currentMBEntry,
            amount,
          };
        });
        let sum = updatedData.reduce((acc, row) => acc + parseFloat(row.amount), 0);
        sum = parseFloat(sum.toFixed(2));
        // Update the state with the new data
        setTablesState(updatedData);
        setTotalMBAmount(sum);
      }, [initialState]);


      const handleInputChange = (field, value, rowIndex) => {
        // Create a copy of tablesState to avoid mutation
        const newTablesState = [...tablesState];

        // Find the index of the row you want to update (you can pass the rowIndex as an argument)
        const updatedRow = newTablesState[rowIndex];

        // Update the specific field in the row
        switch (field) {
          case "description":
            updatedRow.description = value;
            break;
          case "uom":
            updatedRow.uom = value;
            break;
          case "unitRate":
            updatedRow.unitRate = value;
            break;
          default:
            break;
        }

        // Update the state with the modified tablesState
        setTablesState(newTablesState);

        // Update the table state with the new data
        const newTable = { ...table };
        newTable[tableKey] = newTablesState;
        setTable(newTable);
      };


      const options = {
        masterName: "uom",
        moduleName: "common-masters",
        localePrefix: "ES_COMMON_UOM",
      }

      const { isLoading, data } = Digit.Hooks.useCustomMDMS(
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
      const optionsData = data?.map(obj => ({ code: obj?.code, name: obj?.name }));
      if (isLoading) {
        return <Loader />
      }
      return (
        <>
          <tr key={index}>
            <td>{index + 1}</td>
            {props?.props?.isEstimate ? <TextInput style={{ width: "80%", marginTop: "27px", marginLeft: "35px" }} onChange={(e) => handleInputChange("description", e.target.value, index)}
              value={row.description} /> : <td>{row.description}</td>}
            {props?.props?.isEstimate ?
              <td><Dropdown
                inputRef={register()}
                option={optionsData}
                selected={row.uom}
                optionKey="name"
                t={t}
                select={(selectedOption) => handleInputChange("uom", selectedOption, index)}
                onBlur={props?.onBlur}
                optionCardStyles={{ maxHeight: "15rem" }}
                style={{ marginBottom: "0px" }}
              /></td>
              :
              <td>{row.uom}</td>}
            {props?.props?.isEstimate ? <TextInput style={{ width: "80%", marginTop: "20px", marginLeft: "20px" }} onChange={(e) => handleInputChange("unitRate", e.target.value, index)}
              value={row.unitRate}
            /> : <td><Amount customStyle={{ textAlign: 'right' }} value={row.unitRate.toFixed(2)} t={t} roundOff={false}></Amount></td>}
            {!props?.props?.isEstimate && (
              <>
                <td><Amount customStyle={{ textAlign: 'right' }} value={row.approvedQuantity.toFixed(2)} t={t} roundOff={false}></Amount></td>
                <td><Amount customStyle={{ textAlign: 'right' }} value={row.consumedQ.toFixed(2)} t={t} roundOff={false}></Amount></td>
              </>
            )}
            <td>
              <div className="measurement-table-input">
                <TextInput style={{ width: "80%", marginTop: "12px" }} value={consumedQty} onChange={() => { }} disable={initialState.length > 0 ? "true" : "false"} />
                <Button
                  className={"plus-button"}
                  onButtonClick={() => {
                    setShowMeasureCard(!showMeasureCard);
                  }}
                  label={"+"}
                >
                  <AddIcon className="addIcon" />
                </Button>
              </div>
            </td>

            <td><Amount customStyle={{ textAlign: 'right' }} value={row.amount} t={t} roundOff={false}></Amount></td>
            {props?.props?.isEstimate && <td>
              <span className="icon-wrapper" onClick={() => removeRow(row)}>
                <DeleteIcon fill={"#B1B4B6"} />
              </span>
            </td>}

          </tr>
          {showMeasureCard && !initialState.length > 0 && (
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

                ]} consumedQty={consumedQty}
                  setConsumedQty={setConsumedQty}
                  setInitialState={setInitialState}
                  setShowMeasureCard={setShowMeasureCard}
                  initialState={initialState}
                  unitRate={row.unitRate}
                  register={props.isView ? () => { } : register}
                  setValue={props.isView ? () => { } : setValue}
                  tableData={table}
                  tableKey={tableKey}
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
      <div style={{ display: "flex", flexDirection: "row", justifyContent: "flex-end", margin: "20px" }}>
        <div style={{ display: "flex", flexDirection: "row", fontSize: "1.2rem" }}>

          {/* <span style={{ fontWeight: "bold" }}>`{t("MB_TOTAL")} ${props.config.key} {t("MB_TOTAL_AMOUNT")}`</span> */}
          <span style={{ fontWeight: "bold" }}>{t("MB_TOTAL")}  {props.config.key} {t("MB_TOTAL_AMOUNT")} :</span>

          <span style={{ marginLeft: "3px" }}><Amount customStyle={{ textAlign: 'right' }} value={totalMBAmount.toFixed(2)} t={t} roundOff={false}></Amount></span>

        </div>
      </div>
    </React.Fragment>
    // </Card>
  );
};

export default MeasureTable;
