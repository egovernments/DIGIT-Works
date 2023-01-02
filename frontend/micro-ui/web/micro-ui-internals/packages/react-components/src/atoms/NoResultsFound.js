import React from "react";
import { NoResultsFoundIcon } from "./svgindex";

const NoResultsFound = () => {
    return (
        <div className="no-data-found">
              <NoResultsFoundIcon />
              <span className="error-msg">No Result Found</span>
        </div>
    )
}

export default NoResultsFound;