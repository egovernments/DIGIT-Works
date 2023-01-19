import React, { Fragment } from "react";

const InboxSearchComposer = ({configs}) => {

    return (
        <>
            <div className="inbox-search-component-wrapper ">
                <div className={`sections-parent ${configs?.form?.type}`}>
                        {/* Since we need to keep the config sections order-less, avoiding for loop */}
                        {/* That way the config can have sections in any order */}
                        <div className="section links">
                            {/* Integrate the Links Component here*/}
                        </div>
                        <div className="section search">
                            {/* Integrate the Search Component here*/}
                        </div>
                        <div className="section filter">
                            {/* Integrate the Filter Component here*/}
                        </div> 
                        <div className="section search-results">
                            {/* Integrate the Search Results Component here*/}
                        </div>
                </div>
                <div className="additional-sections-parent">
                    {/* One can use this Parent to add additional sub parents to render more sections */}
                </div>
            </div>
        </>
    )
}

export default InboxSearchComposer;
