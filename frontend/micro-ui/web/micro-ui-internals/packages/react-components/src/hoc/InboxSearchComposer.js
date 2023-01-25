import React from "react";
import SearchComponent from "../atoms/SearchComponent";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    return (
        <div className="inbox-search-component-wrapper ">
            <div className={`sections-parent ${configs?.type}`}>
                {/* Since we need to keep the config sections order-less, avoiding for loop */}
                {/* That way the config can have sections in any order */}
                {
                    configs?.sections?.links?.show &&  
                        <div className="section links">
                            {/* Integrate the Search Component here*/}
                        </div>
                }
                {
                    configs?.sections?.search?.show &&  
                        <div className="section search">
                            <SearchComponent 
                                uiConfig={ configs?.sections?.search?.uiConfig} 
                                header={configs?.sections?.search?.label} 
                                children={configs?.sections?.search?.children} 
                                screenType={configs?.type}/>
                        </div>
                }
                {
                configs?.sections?.filter?.show &&  
                    <div className="section filter">
                        {/* Integrate the Filter Component here*/}
                    </div> 
                }
                {
                configs?.sections?.searchResult?.show &&  
                    <div className="section search-results">
                        {/* Integrate the Search Results Component here*/}
                    </div>
                }
            </div>
            <div className="additional-sections-parent">
                {/* One can use this Parent to add additional sub parents to render more sections */}
            </div>
        </div>   
    )
}

export default InboxSearchComposer;
