import { CardSectionHeader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useState } from "react";
import { useTranslation } from "react-i18next";

const ViewTotalAmount = (props) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();

  return (
    <div style={{ display: "flex", justifyContent: "flex-end", marginTop: props?.mode === "VIEWES" ? "-4rem" : "2rem" }}>
      <div
        style={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
          padding: "1rem",
          border: "1px solid #D6D5D4",
          borderRadius: "5px",
        }}
      >
        <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F", fontSize: "18px" }}>
          {detail?.showTitle ? t(detail?.showTitle) : t("TOTAL_EST_AMOUNT")}
        </CardSectionHeader>
        <CardSectionHeader style={{ marginBottom: "0px", fontSize: "24px", fontWeight: "700" }}>
          {detail?.value?.toString().includes(",")
            ? detail?.value
            : `₹ ${Digit.Utils.dss.formatterWithoutRound(Math.round(parseFloat(detail?.value)).toFixed(2), "number", undefined, true, undefined, 2)}`}
        </CardSectionHeader>
      </div>
    </div>
  );
};

export default ViewTotalAmount;
