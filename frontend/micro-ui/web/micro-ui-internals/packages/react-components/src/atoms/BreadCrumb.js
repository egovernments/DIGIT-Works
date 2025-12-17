import React from "react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

const Breadcrumb = (props) => {
  function isLast(index) {
    //to check if the crumb is the last object in the valid crumb array so it will be non clickable
    let validcrumb = props.crumbs?.filter((ob) => ob?.show == true)
    return validcrumb?.findIndex((ob) => ob?.path === props?.crumbs?.[index]?.path) === validcrumb?.length - 1;
  }
  return (
    <ol className="bread-crumb">
      {props?.crumbs?.map((crumb, ci) => {
        if (!crumb?.show) return;
        if (crumb?.isBack)
          return (
            <li key={ci} style={ci == 0 ? {...props?.style, ...props?.zerothStyle} : { ...props.style }} className="bread-crumb--item">
              <span style={{ cursor: "pointer" }} onClick={() => window.history.back()}>
                {crumb.content}
              </span>
            </li>
          );
        return (
          <li key={ci} style={ci == 0 ? {...props?.style, ...props?.zerothStyle} : { ...props.style }} className="bread-crumb--item">
            {isLast(ci) || !crumb?.path ? (
              <span style={props?.spanStyle ? { ...props?.spanStyle, color: "#0B0C0C" } : { color: "#0B0C0C" }}>{crumb.content}</span>
            ) : (
              <Link to={{ pathname:crumb.path, state: {count : crumb?.count} }}>{crumb.content}</Link>
            )}
          </li>
        );
      })}
    </ol>
  );
};

Breadcrumb.propTypes = {
  crumbs: PropTypes.array,
};

Breadcrumb.defaultProps = {
  successful: true,
};

export default Breadcrumb;
