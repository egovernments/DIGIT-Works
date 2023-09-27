import { Amount } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";

const ViewOnlyCard = (props) => {
    const { t } = useTranslation();
    // Extract the "SOR" and "NONSOR" arrays from the props
    const [total, setTotal] = useState(0);
    const { SOR, NONSOR } = props.data;

    // Calculate the sum of "amount" values in both arrays
    const totalAmount = SOR.reduce((acc, item) => acc + parseFloat(item?.amount), 0) + NONSOR.reduce((acc, item) => acc + parseFloat(item?.amount), 0);

    useEffect(() => {
        setTotal(totalAmount)
    }, [totalAmount])
    return (
        <div className="view-only-card-container">
            <div className="view-only-card">
                <span>{t("MB_AMOUNT_TOTAL")}:</span>
                <Amount customStyle={{ textAlign: 'right' }} value={total} t={t} roundOff={false}></Amount>
            </div>
        </div>
    );
};

export default ViewOnlyCard;
