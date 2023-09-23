
import { AddIcon, Card, TextInput, Amount, Button } from "@egovernments/digit-ui-react-components";

import React, { useState, Fragment, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import MeasureCard from "./MeasureCard";

const MeasureTable = (props) => {
  const sorData = props.data.SOR?.length > 0 ? props.data.SOR : null;
  const nonsorData = props.data.NONSOR?.length > 0 ? props.data.NONSOR : null;
  const data = props.config.key === "SOR" ? sorData : nonsorData;
  const tableKey = props.config.key;
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();
  const [totalMBAmount, setTotalMBAmount] = useState(0);
  const tableMBAmounts = [];
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
    return data?.map((row, index) => {
      const [consumedQty, setConsumedQty] = useState(0);
      const [showMeasureCard, setShowMeasureCard] = useState(false);
      const [initialState, setInitialState] = useState({ tableState: row?.additionalDetails?.measurement });
      const [totalAmount, setTotalAmount] = useState(0)

      useEffect(() => {
        tableMBAmounts[index] = consumedQty * row.unitRate;
        setTotalAmount(tableMBAmounts[index]);
        let sum = 0;
        for (let i = 0; i < tableMBAmounts.length; i++) {
          sum += tableMBAmounts[i];
        }
        setTotalMBAmount(sum);
        if (!props.isView) {
          if (props.config.key == "SOR") {
            if (props.formData.sumSor != sum) {
              setValue("sumSor", sum);
            }
          } else {
            if (props.formData.sumNonSor != sum) {
              setValue("sumNonSor", sum);
            }
          }
        }
        // console.log(props, "FFFFFFFFFFFFFFFFFFF")

      }, [consumedQty, tableMBAmounts]);

      return (
        <>
          <tr key={index}>
            <td>{index + 1}</td>
            <td>{row.description}</td>
            <td>{row.uom}</td>
            <td><Amount customStyle={{ textAlign: 'right'}} value={Math.round(row.unitRate)} t={t}></Amount></td>
            <td><Amount customStyle={{ textAlign: 'right'}} value={Math.round(row.noOfunit)} t={t}></Amount></td>
            <td><Amount customStyle={{ textAlign: 'right'}} value={null} t={t}></Amount></td>
            <td>
              <div className="measurement-table-input">
                <TextInput style={{ width: "80%" }} value={consumedQty} onChange={() => { }} disable={initialState.length > 0 ? "true" : "false"} />
                <Button
                  className={"plus-button"}
                  onButtonClick={() => {
                    setShowMeasureCard(!showMeasureCard);
                  }}
                  label={"+"}
                >
                  <AddIcon fill={"#F47738"} styles={{ margin: "auto", display: "inline", marginTop: "-2px", width: "20px", height: "20px" }} />
                </Button>
              </div>
            </td>

            <td><Amount customStyle={{ textAlign: 'right'}} value={totalAmount} t={t}></Amount></td>


          </tr>
          {showMeasureCard && !initialState.length > 0 && (
            <tr>
              <td colSpan={"1"}></td>
              <td colSpan={"7"}>
                <MeasureCard columns={[


                  t("WORKS_SNO"),
                  t("Is Deduction?"),
                  t("Description "),
                  t("Number"),
                  t("Length"),
                  t("Width"),
                  t("Depth/Height"),
                  t("Quantity"),
                ]} consumedQty={consumedQty}
                  setConsumedQty={setConsumedQty}
                  setInitialState={setInitialState}
                  setShowMeasureCard={setShowMeasureCard}
                  initialState={initialState}
                  register={props.isView ? () => {} : register}
                  setValue={props.isView ? () => {} : setValue}
                  tableData={props.data}
                  tableKey={tableKey}
                  tableIndex={index} />

              </td>
            </tr>
          )}
        </>
      );
    });
  };

  return (
    <Card className = "override-card">
      <table className="table reports-table sub-work-table">
        <thead>
          <tr>{renderHeader()}</tr>
        </thead>
        <tbody>{renderBody()}

        </tbody>

      </table>
      <div style={{ display: "flex", flexDirection: "row", justifyContent: "flex-end", margin: "20px" }}>
        <div style={{ display: "flex", flexDirection: "row", fontSize: "1.2rem" }}>
          <span style={{ fontWeight: "bold" }}>Total MB Amount(For Current Entry): </span>
          <span style={{ marginLeft: "3px" }}>{totalMBAmount}</span>
        </div>
      </div>
    </Card>
  );
};

export default MeasureTable;
