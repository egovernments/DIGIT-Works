import React from "react";

  /* Amount component by default round offs and formats for amount   */

const Amount = ({t,roundOff=true,...props}) => {
    const value=roundOff?Math.round(props?.value):props?.value;
    return (
        <p style={props?.customStyle}>{props?.rupeeSymbol ? "₹" : ""}{value !== undefined && value !== null ? `${Digit?.Utils?.dss?.formatterWithoutRound(value, "number")}` : t("ES_COMMON_NA")}</p>
    )
}

export default Amount;