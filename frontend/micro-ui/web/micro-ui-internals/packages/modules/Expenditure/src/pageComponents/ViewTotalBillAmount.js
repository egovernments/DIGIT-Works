import { CardSectionHeader } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { TextBlock } from "@egovernments/digit-ui-components";

const TotalBillAmountView = ({ detail, ...props }) => {
  const { t } = useTranslation();
  return (
    <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "2rem", ...detail?.containerStyles }}>
      <div className={"total_amount_wrapper"}>
        {/* <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F",width:"fit-content" }}>
          {detail?.key ? t(detail.key) : t("RT_TOTAL")}
        </CardSectionHeader>
        <CardSectionHeader style={{ marginBottom: "0px",width:"fit-content" }}>{`₹ ${detail?.value}`}</CardSectionHeader> */}

        <TextBlock subHeader={detail?.key ? t(detail.key) : t("RT_TOTAL")} subHeaderClassName={"table_total_amount"}></TextBlock>
        <TextBlock subHeader={`₹ ${detail?.value}`} subHeaderClassName={`table_total_amount_value`}></TextBlock>
      </div>
    </div>
  );
};

export default TotalBillAmountView;