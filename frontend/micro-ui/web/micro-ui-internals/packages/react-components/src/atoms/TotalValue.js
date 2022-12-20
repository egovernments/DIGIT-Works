import React from "react";

const TotalValue = ({showTopHorizontalLine, showBottomHorizontalLine, label, value, customClass=""}) => {
    return (
        <div className={`total-value-wrapper ${customClass}`}>
            {showTopHorizontalLine && <hr className="horizontal-line"></hr>}
                <div className="total-wrapper">
                    <span className="label">{label}</span>
                    <span className="value">{value}</span>
                </div>
            {showBottomHorizontalLine && <hr className="horizontal-line"></hr>}
        </div>
    )
}

export default TotalValue;