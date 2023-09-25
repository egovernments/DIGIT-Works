import { Amount } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const styles = {
    container: {
        textAlign: "right",
        margin: "20px",
        display: "flex",
        justifyContent: "flex-end",
    },
    card: {
        border: "1px solid black",
        padding: "20px",
        backgroundColor: "white",
        width: "fit-content",
        fontWeight: "bold",
        display: "flex",
    },
};

const ViewOnlyCard = (props) => {

    const { t } = useTranslation();
    // Extract the "SOR" and "NONSOR" arrays from the props
    const { SOR, NONSOR } = props.data;

    // Calculate the sum of "amount" values in both arrays
    const totalAmount = SOR.reduce((acc, item) => acc + item?.measures?.[0]?.rowAmount, 0) + NONSOR.reduce((acc, item) => acc + item?.measures?.[0]?.rowAmount, 0);
    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <span>Total Amount:</span>
                <Amount customStyle={{ textAlign: 'right' }} value={totalAmount} t={t}></Amount>
            </div>
        </div>
    );
};

export default ViewOnlyCard;
