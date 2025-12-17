import React from "react";
import { useTranslation } from "react-i18next";
import { CardSectionHeader } from "@egovernments/digit-ui-react-components";
import { TextBlock } from "@egovernments/digit-ui-components";

const PayableAmt = ({ detail }) => {
  const { t } = useTranslation();
  return (
    <div style={{ display: "flex", justifyContent: "flex-start" }}>
      <div className={"total_amount_wrapper"}>
        {/* <CardSectionHeader style={{ fontSize: "18px", marginRight: "1rem", marginBottom: "0px", color: "#505A5F",width:"fit-content" }}>{t("EXP_NET_PAYABLE")}</CardSectionHeader>
            <CardSectionHeader style={{ marginBottom: "0px" ,width:"fit-content"}}>{`₹ ${detail?.value}`}</CardSectionHeader> */}

        <TextBlock subHeader={t("EXP_NET_PAYABLE")} subHeaderClassName={`table_total_amount`}></TextBlock>
        <TextBlock subHeader={`₹ ${detail?.value}`} subHeaderClassName={`table_total_amount_value`}></TextBlock>
      </div>
    </div>
  );
};

export default PayableAmt;