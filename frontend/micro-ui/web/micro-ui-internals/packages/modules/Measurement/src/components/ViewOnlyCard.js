import { Amount, CardSectionHeader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useMemo } from "react";
import { useTranslation } from "react-i18next";

const ViewOnlyCard = (props) => {
  const { watch } = props;
  const { t } = useTranslation();
  // Extract the "SOR" and "NONSOR" arrays from the props
  const SOR = watch("SOR") || watch("SORtable");
  const NONSOR = watch("NONSOR") || watch("NONSORtable");

  // Calculate the sum of "amount" values in both arrays
  const totalAmount = SOR?.reduce((acc, item) => acc + parseFloat(item?.amount), 0) + NONSOR?.reduce((acc, item) => acc + parseFloat(item?.amount), 0) || 0;

  return (
    // <div className="view-only-card-container">
    //   <div className="view-only-card">
    //     <span>{t("MB_AMOUNT_TOTAL")}:</span>
    //     <Amount customStyle={{ textAlign: "right" }} value={totalAmount} t={t} roundOff={false}></Amount>
    //   </div>
    // </div>
    <div style={{ display: "flex", justifyContent: "flex-end", marginTop: props?.mode === "VIEW"? "-4rem":"2rem" }}>
    <div style={{ display: "flex", flexDirection: 'row', justifyContent: "space-between", padding: "1rem", border: "1px solid #D6D5D4", borderRadius: "5px" }}>
        <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F", fontSize:"18px" }}>{t("MB_AMOUNT_TOTAL")}</CardSectionHeader>
        <CardSectionHeader style={{ marginBottom: "0px", fontSize:"24px", fontWeight:"700" }}>{`₹ ${Digit.Utils.dss.formatterWithoutRound(Math.round(parseFloat(totalAmount)),"number")}`}</CardSectionHeader>
    </div>
    </div>
  );
};

export default ViewOnlyCard;
