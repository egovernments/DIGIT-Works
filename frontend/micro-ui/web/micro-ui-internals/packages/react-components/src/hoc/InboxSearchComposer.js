import InboxSearchLinks from "../atoms/InboxSearchLinks";
import React, { useReducer } from "react";
import ResultsTable from "./ResultsTable"
import reducer, { initialTableState } from "./InboxSearchComposerReducer";
import { InboxContext } from "./InboxSearchComposerContext";
import SearchComponent from "../atoms/SearchComponent";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    
    //whenever this state is updated we'll make a call to the search/inbox api
    const [state, dispatch] = useReducer(reducer, initialTableState)

    return (
        <InboxContext.Provider value={{state,dispatch}} >
            <div className="inbox-search-component-wrapper ">
            <div className={`sections-parent ${configs?.type}`}>
                {/* Since we need to keep the config sections order-less, avoiding for loop */}
                {/* That way the config can have sections in any order */}
                {
                    configs?.sections?.links?.show &&  
                        <div className="section links">
                            <InboxSearchLinks headerText={configs?.sections?.links?.uiConfig?.label} links={configs?.sections?.links?.uiConfig?.links} businessService="WORKS" logoIcon={configs?.sections?.links?.uiConfig?.logoIcon}></InboxSearchLinks>
                        </div>
                }
                {
                    configs?.sections?.search?.show &&  
                        <div className="section search">
                            <SearchComponent 
                                uiConfig={ configs?.sections?.search?.uiConfig} 
                                header={configs?.sections?.search?.label} 
                                screenType={configs?.type}/>
                        </div>
                }
                {
                configs?.sections?.filter?.show &&  
                    <div className="section filter">
                        {/* Integrate the Filter Component here*/}
                        <SearchComponent 
                                uiConfig={ configs?.sections?.filter?.uiConfig} 
                                header={configs?.sections?.filter?.label} 
                                screenType={configs?.type}/>
                    </div> 
                }
                {
                configs?.sections?.searchResult?.show &&  
                    <div className="" style={{ overflowX: "scroll" }}>
                        {/* Integrate the Search Results Component here*/}
                        <ResultsTable config={configs?.sections?.searchResult?.uiConfig}/>
                    </div>
                }
            </div>
            <div className="additional-sections-parent">
                {/* One can use this Parent to add additional sub parents to render more sections */}
            </div>
            </div>   
        </InboxContext.Provider>
    )
}

export default InboxSearchComposer;
