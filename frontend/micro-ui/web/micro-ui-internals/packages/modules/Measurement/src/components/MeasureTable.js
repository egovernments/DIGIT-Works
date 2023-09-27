
import { AddIcon, Card, TextInput, Amount, Button } from "@egovernments/digit-ui-react-components";

import React, { useState, Fragment, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureCard from "./MeasureCard";

const MeasureTable = (props) => {

  const sorData = props.data.SOR?.length > 0 ? props.data.SOR : null;
  const nonsorData = props.data.NONSOR?.length > 0 ? props.data.NONSOR : null;
  const data = props.config.key === "SOR" ? sorData : nonsorData;
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

  columns = [
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


      return (
        <>
          <tr key={index}>
            <td>{index + 1}</td>
            <td>{row.description}</td>
            <td>{row.uom}</td>
            <td><Amount customStyle={{ textAlign: 'right' }} value={row.unitRate.toFixed(2)} t={t} roundOff={false}></Amount></td>
            <td><Amount customStyle={{ textAlign: 'right' }} value={row.approvedQuantity.toFixed(2)} t={t} roundOff={false}></Amount></td>
            <td><Amount customStyle={{ textAlign: 'right' }} value={row.consumedQ.toFixed(2)} t={t} roundOff={false}></Amount></td>
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


          </tr>
          {showMeasureCard && !initialState.length > 0 && (
            <tr>
              <td colSpan={"1"}></td>
              <td colSpan={"7"}>
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
                  tableData={props.data}
                  tableKey={tableKey}
                  tableIndex={index}
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
      <table className="table reports-table sub-work-table">
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>{renderBody()}

        </tbody>

      </table>
      <div style={{ display: "flex", flexDirection: "row", justifyContent: "flex-end", margin: "20px" }}>
        <div style={{ display: "flex", flexDirection: "row", fontSize: "1.2rem" }}>

          <span style={{ fontWeight: "bold" }}>Total {props.config.key} MB Amount(For Current Entry): </span>
          <span style={{ marginLeft: "3px" }}><Amount customStyle={{ textAlign: 'right' }} value={totalMBAmount.toFixed(2)} t={t} roundOff={false}></Amount></span>

        </div>
      </div>
    </React.Fragment>
    // </Card>
  );
};

export default MeasureTable;
