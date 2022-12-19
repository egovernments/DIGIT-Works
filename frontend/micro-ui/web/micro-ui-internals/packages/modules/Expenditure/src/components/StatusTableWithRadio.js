import React from "react";

const StatusRowWithRadio = ({config, className=""}) => {
    const selectOption = (option) => {

    }
    return (
        <div className={`row ${className}`}>
            <h2>Label</h2>
            <div className="radio-wrap value status-row-radio">
                {
                    config?.options?.map((option)=>(
                        <div key={option?.key}>
                            <span className="radio-btn-wrap">
                                <input
                                    className="radio-btn"
                                    type="radio"
                                    value={option?.value}
                                    checked={config?.options[0]?.value}
                                    onChange={() => selectOption(option)}
                                    name={option?.name}
                                />
                                <span className="radio-btn-checkmark"></span>
                            </span>
                            <label>{(option?.name)}</label>
                      </div>
                    ))
                }
            </div>
        </div>
    )
}

const StatusTableWithRadio = ({config, customClass=""}) => {
   
    const employee = Digit.SessionStorage.get("user_type") === "employee" ? true : false;
    return (
        <div>
            <div className={employee ? "employee-data-table status-radio-table" : "data-table status-radio-table"}>
                {
                    <StatusRowWithRadio config={config} className={customClass}></StatusRowWithRadio>
                }
            </div>
        </div>
    )
}

export default StatusTableWithRadio;