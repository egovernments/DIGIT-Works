import { Amount, CardSectionHeader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useMemo } from "react";
import { useTranslation } from "react-i18next";
import { TextBlock } from "@egovernments/digit-ui-components";

const ViewOnlyCard = (props) => {
  const { watch } = props;
  const { t } = useTranslation();
  // Extract the "SOR" and "NONSOR" arrays from the props
  const SOR = watch("SOR") || watch("SORtable");
  const NONSOR = watch("NONSOR") || watch("NONSORtable");

  // Calculate the sum of "amount" values in both arrays
  const totalAmount =
    SOR?.reduce((acc, item) => acc + parseFloat(item?.amount), 0) + NONSOR?.reduce((acc, item) => acc + parseFloat(item?.amount), 0) || 0;

  return (
    <div style={{ display: "flex", justifyContent: "flex-end", marginTop: props?.mode === "VIEW" ? "-4rem" : "0rem" ,...props?.style}}>
      <div className="total_amount_wrapper">
        {/* <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F", fontSize: "18px",width:"fit-content" }}>
          {t("MB_AMOUNT_TOTAL")}
        </CardSectionHeader>
        <CardSectionHeader style={{ marginBottom: "0px", fontSize: "24px", fontWeight: "700" ,width:"fit-content"}}>{`₹ ${Digit.Utils.dss.formatterWithoutRound(
          Math.round(parseFloat(totalAmount)).toFixed(2),
          "number",
          undefined,
          true,
          undefined,
          2
        )}`}</CardSectionHeader> */}

        <TextBlock subHeader={t("MB_AMOUNT_TOTAL")} subHeaderClassName={"table_total_amount"}></TextBlock>
        <TextBlock
          subHeader={`₹ ${Digit.Utils.dss.formatterWithoutRound(
            Math.round(parseFloat(totalAmount)).toFixed(2),
            "number",
            undefined,
            true,
            undefined,
            2
          )}`}
          subHeaderClassName={`table_total_amount_value`}
        ></TextBlock>
      </div>
    </div>
  );
};

export default ViewOnlyCard;