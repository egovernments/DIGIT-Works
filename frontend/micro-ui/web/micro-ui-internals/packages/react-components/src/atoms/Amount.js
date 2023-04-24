import React from "react";

const Amount = (props) => {
    return (
        <p style={props?.customStyle}>{props?.value ? `${Digit?.Utils?.dss?.formatterWithoutRound(props?.value, "number")}` : t("ES_COMMON_NA")}</p>
    )
}

export default Amount;