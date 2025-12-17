import React from "react";

  /* Amount component by default round offs and formats for amount   */

const Amount = ({t,roundOff=true,...props}) => {
    const value=roundOff?Math.round(props?.value):props?.value;
    return (
        <p style={props?.customStyle}>{props?.rupeeSymbol ? "₹" : ""}{value !== undefined && value !== null ? (props?.sameDisplay ? value : `${Digit?.Utils?.dss?.formatterWithoutRound(value, "number", undefined, true, undefined, props?.decimalPlaces)}`) : t("ES_COMMON_NA")}</p>
    )
}

export default Amount;