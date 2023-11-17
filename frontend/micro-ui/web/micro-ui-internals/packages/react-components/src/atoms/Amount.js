import React from "react";

  /* Amount component by default round offs and formats for amount   */

const Amount = ({t,roundOff=true,...props}) => {
    console.log(props?.value, "props value in amount")
    const value=roundOff?Math.round(props?.value):props?.value;
    console.log(value,"value in amount");
    return (
        <p style={props?.customStyle}>{value ? `${Digit?.Utils?.dss?.formatterWithoutRound(value, "number")}` : t("ES_COMMON_NA")}</p>
    )
}

export default Amount;