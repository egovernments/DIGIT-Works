import { CardSectionHeader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { TextBlock } from "@egovernments/digit-ui-components";

const ViewTotalEstAmount = ({ detail, ...props }) => {
  const { t } = useTranslation();
  return (
    <div
    // style={{ display: "flex", justifyContent: "flex-end", marginTop: props?.mode === "VIEWES"? "0rem":"2rem" }}
    >
      <div
        className={"total_amount_wrapper"}
      >
        {/* <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F", fontSize:"18px" ,width:"fit-content" }}>{detail?.showTitle ? t(detail?.showTitle) : t("TOTAL_EST_AMOUNT")}</CardSectionHeader>
        <CardSectionHeader style={{ marginBottom: "0px", fontSize:"24px", fontWeight:"700",width:"fit-content"}}>{detail?.value?.toString().includes(",") ? detail?.value : `₹ ${Digit.Utils.dss.formatterWithoutRound(Math.round(parseFloat(detail?.value)).toFixed(2),"number",undefined,true,undefined,2)}`}</CardSectionHeader> */}

        <TextBlock
          subHeader={detail?.showTitle ? t(detail?.showTitle) : t("TOTAL_EST_AMOUNT")}
          subHeaderClassName={`table_total_amount ${"estimate_amount"}`}
        ></TextBlock>
        <TextBlock
          subHeader={
            detail?.value?.toString().includes(",")
              ? detail?.value
              : `₹ ${Digit.Utils.dss.formatterWithoutRound(
                  Math.round(parseFloat(detail?.value)).toFixed(2),
                  "number",
                  undefined,
                  true,
                  undefined,
                  2
                )}`
          }
          subHeaderClassName={`table_total_amount_value ${"estimate_amount"}`}
        ></TextBlock>
      </div>
    </div>
  );
};

export default ViewTotalEstAmount;
