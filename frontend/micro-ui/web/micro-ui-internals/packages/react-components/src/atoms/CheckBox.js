import React ,{Fragment}from "react";
import { CheckSvg } from "./svgindex";
import PropTypes from "prop-types";
import BreakLine from "./BreakLine";

const CheckBox = ({ onChange, label, value, disable, ref, checked, inputRef, pageType, style, index, isLabelFirst, customLabelMarkup,  ...props }) => {
  
  const userType = pageType || Digit.SessionStorage.get("userType");
  let wrkflwStyle = props.styles;
  if (isLabelFirst) {
    return (
      <div className="checkbox-wrap" style={wrkflwStyle ? wrkflwStyle : {}}>
        <p style={style ? style : null}> {index+1}.</p>
        <p className="label" style={{ maxWidth: "80%", marginLeft: "10px" }}>
          {label}
        </p>
        <div>
          <input
            type="checkbox"
            className={userType === "employee" ? "input-emp" : ""}
            onChange={onChange}
            style={{ cursor: "pointer", left: "90%" }}
            value={value || label}
            {...props}
            ref={inputRef}
            disabled={disable}
            checked={checked}
          />
          <p className={userType === "employee" ? "custom-checkbox-emp" : "custom-checkbox"} style={disable ? { opacity: 0.5 } : {left: "90%"}}>
            <CheckSvg />
          </p>
        </div>
      </div>
    );
  } else {
    return (
      <div className="checkbox-wrap" style={wrkflwStyle ? wrkflwStyle : {}}>
        <div>
          <input
            type="checkbox"
            className={userType === "employee" ? "input-emp" : ""}
            onChange={onChange}
            style={{ cursor: "pointer" }}
            value={value || label}
            {...props}
            ref={inputRef}
            disabled={disable}
            // {(checked ? (checked = { checked }) : null)}
            checked={checked}
          />
          <p className={userType === "employee" ? "custom-checkbox-emp" : "custom-checkbox"} style={disable ? { opacity: 0.5 } : null}>
            {/* <img src={check} alt="" /> */}
            <CheckSvg />
          </p>
        </div>
        <p className="label" style={style ? style : {}}>
          
          {customLabelMarkup ? 
          <>
            <p>I certify that appropriate amount of work has been completed. Muster roll has been verified against Measurement Book.</p>
            <br />
            <p>
            <b> Note</b>: Once approved Payment Advice will be generated and send to JIT-FS for payment processing.
            </p>
            </> : label}
        </p>
      </div>
    );
  }
  
};

CheckBox.propTypes = {
  /**
   * CheckBox content
   */
  label: PropTypes.string.isRequired,
  /**
   * onChange func
   */
  onChange: PropTypes.func,
  /**
   * input ref
   */
  ref: PropTypes.func,
  userType: PropTypes.string,
};

CheckBox.defaultProps = {};

export default CheckBox;
