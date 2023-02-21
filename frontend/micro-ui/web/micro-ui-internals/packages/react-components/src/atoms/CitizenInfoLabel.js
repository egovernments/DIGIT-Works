import React from "react";
import { InfoBannerIcon } from "./svgindex";

const CitizenInfoLabel = ({ props }) => {
  return (
    <div className={`info-banner-wrap ${props?.className ? props?.className : ""}`} style={props?.style}>
      {(props?.showInfo && true ) && <div>
        <InfoBannerIcon fill={props?.fill} />
        <h2 style={props?.textStyle}>{props?.info}</h2>
      </div>
      }
      <p style={props?.textStyle}>{props?.text}</p>
    </div>
  );
};

export default CitizenInfoLabel;
