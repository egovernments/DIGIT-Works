import { CardSectionHeader } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState, useMemo } from "react";
import { TextBlock } from "@egovernments/digit-ui-components";

const TotalEstAmount = ({ formData, setValue, t, ...props }) => {
  const formFieldNameNonSor = "NONSORtable";
  const formFieldNameSor = "SORtable";
  const formFieldNameOverheads = "overheadDetails";

  let getTotalAmount = useMemo(() => {
    let totalSor = formData?.[formFieldNameSor]?.reduce((acc, row) => {
      let amountNonSor = parseFloat(row?.amount);
      amountNonSor = amountNonSor ? amountNonSor : 0;
      return amountNonSor + parseFloat(acc);
    }, 0);
    totalSor = totalSor ? totalSor : 0;
    let totalNonSor = formData?.[formFieldNameNonSor]?.reduce((acc, row) => {
      let amountNonSor = parseFloat(row?.amount);
      amountNonSor = amountNonSor ? amountNonSor : 0;
      return amountNonSor + parseFloat(acc);
    }, 0);
    totalNonSor = totalNonSor ? totalNonSor : 0;
    let totalOverHeads = formData?.[formFieldNameOverheads]?.reduce((acc, row) => {
      let amountOverheads = parseFloat(row?.amount);
      amountOverheads = amountOverheads ? amountOverheads : 0;
      return amountOverheads + parseFloat(acc);
    }, 0);
    totalOverHeads = !Number.isNaN(totalOverHeads) ? totalOverHeads : 100;

    return totalSor + totalNonSor + totalOverHeads;
  }, [formData]);

  useEffect(() => {
    if (formData?.totalEstimatedAmount !== getTotalAmount) setValue("totalEstimatedAmount", getTotalAmount);
  }, [getTotalAmount, formData]);

  return (
    <div style={{ display: "flex", justifyContent: "flex-end"}}>
      <div className={"total_amount_wrapper"}>
        {/* <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color:"#505A5F"}}>{window.location.href.includes("/estimate/") ? t("TOTAL_CREATE_EST_AMOUNT") : t("TOTAL_EST_AMOUNT")}</CardSectionHeader>
              <CardSectionHeader style={{ marginBottom: "0px" }}>{`₹ ${Digit.Utils.dss.formatterWithoutRound(Math.round(getTotalAmount), 'number')}.00`}</CardSectionHeader> */}

        <TextBlock
          subHeader={window.location.href.includes("/estimate/") ? t("TOTAL_CREATE_EST_AMOUNT") : t("TOTAL_EST_AMOUNT")}
          subHeaderClassName={`table_total_amount ${"estimate_amount"}`}
        ></TextBlock>
        <TextBlock
          subHeader={`₹ ${Digit.Utils.dss.formatterWithoutRound(Math.round(getTotalAmount), "number")}.00`}
          subHeaderClassName={`table_total_amount_value ${"estimate_amount"}`}
        ></TextBlock>
      </div>
    </div>
  );
};

export default TotalEstAmount;
