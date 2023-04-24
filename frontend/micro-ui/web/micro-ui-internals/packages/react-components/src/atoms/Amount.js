import React from "react";
import { useTranslation } from "react-i18next";

const Amount = (props) => {
    const { t } = useTranslation();
    return (
        <p style={props?.customStyle}>{props?.value ? `${Digit?.Utils?.dss?.formatterWithoutRound(props?.value, "number")}` : t("ES_COMMON_NA")}</p>
    )
}

export default Amount;