import { Amount } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";


const ViewOnlyCard = (props) => {

    const { t } = useTranslation();
    // Extract the "SOR" and "NONSOR" arrays from the props
    const { SOR, NONSOR } = props.data;

    // Calculate the sum of "amount" values in both arrays
    const totalAmount = SOR.reduce((acc, item) => acc + item?.measures?.[0]?.rowAmount, 0) + NONSOR.reduce((acc, item) => acc + item?.measures?.[0]?.rowAmount, 0);
    return (
        <div className="view-only-card-container">
            <div className="view-only-card">
                <span>Total Amount:</span>
                <Amount customStyle={{ textAlign: 'right' }} value={totalAmount} t={t}></Amount>
            </div>
        </div>
    );
};

export default ViewOnlyCard;
