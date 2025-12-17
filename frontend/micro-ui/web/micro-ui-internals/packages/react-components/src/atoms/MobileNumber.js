import React from "react";
import PropTypes from "prop-types";
import {TextInput} from "@egovernments/digit-ui-components";

const MobileNumber = (props) => {
  const user_type = Digit.SessionStorage.get("userType");

  const onChange = (e) => {
    let val = e.target.value;
    if (isNaN(val) || [" ", "e", "E"].some((e) => val.includes(e)) || val.length > (props.maxLength || 10)) {
      val = val.slice(0, -1);
    }
    props?.onChange?.(val);
  };

  return (
    <React.Fragment>
      <div className={`digit-mobile-number-container ${props?.className ? props?.className : ""}`} style={props?.style}>
        <div
          className={`digit-text-input-field ${user_type === "employee" ? "" : "digit-text-mobile-input-width"} ${props.className ? props.className : ""}`}
        >
          <TextInput
            type={"text"}
            name={props.name}
            id={props.id}
            className={props?.className}
            placeholder={props.placeholder}
            onChange={onChange}
            inputRef={props.inputRef}
            value={props.value}
            style={{ ...props.style }}
            minlength={props.minlength}
            maxLength={props.maxlength}
            max={props.max}
            pattern={props.pattern}
            min={props.min}
            nonEditable={props?.nonEditable}
            title={props.title}
            step={props.step}
            autoFocus={props.autoFocus}
            onBlur={props.onBlur}
            isMandatory={props.isMandatory}
            disabled={props.disable}
            hideSpan={props.hideSpan}
            variant={props?.variant}
            populators={
              !props.hideSpan ? {prefix:"+91"} :{}
            }
            userType={user_type}
          />
        </div>
      </div>
    </React.Fragment>
  );
};

MobileNumber.propTypes = {
  userType: PropTypes.string,
  isMandatory: PropTypes.bool,
  name: PropTypes.string,
  placeholder: PropTypes.string,
  onChange: PropTypes.func,
  inputRef: PropTypes.oneOfType([PropTypes.func, PropTypes.shape({ current: PropTypes.instanceOf(Element) })]),
  value: PropTypes.any,
  className: PropTypes.string,
  style: PropTypes.object,
  maxLength: PropTypes.number,
  minlength: PropTypes.number,
  max: PropTypes.number,
  pattern: PropTypes.string,
  min: PropTypes.number,
  disable: PropTypes.bool,
  errorStyle: PropTypes.bool,
  hideSpan: PropTypes.bool,
  title: PropTypes.string,
  step: PropTypes.string,
  autoFocus: PropTypes.bool,
  onBlur: PropTypes.func,
};

MobileNumber.defaultProps = {
  isMandatory: false,
};

export default MobileNumber;
