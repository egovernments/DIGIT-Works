import React, { useEffect, useReducer, useState } from "react";
import ResultsTable from "./ResultsTable"
import reducer, { initialInboxState } from "./InboxSearchComposerReducer";
import InboxSearchLinks from "../atoms/InboxSearchLinks";
import { InboxContext } from "./InboxSearchComposerContext";
import SearchComponent from "../atoms/SearchComponent";

const InboxSearchComposer = (props) => {
    const { configs } = props;
    const [enable, setEnable] = useState(false);
    const [state, dispatch] = useReducer(reducer, initialInboxState)

    useEffect(() => {
        const requestBody = configs?.apiDetails?.requestBody
        if(state.searchForm?.data) {
            console.log('state.searchForm?.data', state.searchForm?.data);
            requestBody.Projects[0].projectNumber = "PR/2022-23/01/000284",
            requestBody.Projects[0].name = '',
            requestBody.Projects[0].startDate = '',
            requestBody.Projects[0].endDate = '',
            setEnable(true)
        }
    }, [state.searchForm])
    
   
    const requestCriteria = [
        configs?.apiDetails?.serviceName,
        configs?.apiDetails?.requestParam,
        configs?.apiDetails?.requestBody,
        {},
        {
            enabled: enable
        }
    ];

    const { isLoading, data, revalidate } = Digit.Hooks.useCustomAPIHook(...requestCriteria);
    console.log('project data', data);
    
    useEffect(() => {
        return () => {
            revalidate();
            setEnable(false);
        };
    });

    return (
        <InboxContext.Provider value={{state,dispatch}} >
            <div className="inbox-search-component-wrapper ">
            <div className={`sections-parent ${configs?.type}`}>
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
                        <SearchComponent 
                                uiConfig={ configs?.sections?.filter?.uiConfig} 
                                header={configs?.sections?.filter?.label} 
                                screenType={configs?.type}/>
                    </div> 
                }
                {
                configs?.sections?.searchResult?.show &&  
                    <div className="" style={{ overflowX: "scroll" }}>
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
