import React from "react";
import { useFieldArray } from "react-hook-form";

const WrapperComponent = ({ component: Component, ...allProps }) => {
  if (!Component) {
    return <span>Component Not Found</span>;
  }

  /* added field array, it is available in when type is component can be accessed through prop called arrayProps */
  const { ...arrayProps } = allProps?.config?.useFieldArray
    ? useFieldArray({
        control: allProps?.control,
        name: allProps?.config?.key,
      })
    : {};
  if (allProps?.config?.useFieldArray) {
    allProps["arrayProps"] = arrayProps;
  }
  return <Component {...allProps}></Component>;
};

export default WrapperComponent;
