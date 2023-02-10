import React, { useEffect, useReducer, useState } from "react";
import ResultsTable from "./ResultsTable"
import reducer, { initialInboxState } from "./InboxSearchComposerReducer";
import InboxSearchLinks from "../atoms/InboxSearchLinks";
import { InboxContext } from "./InboxSearchComposerContext";
import SearchComponent from "../atoms/SearchComponent";
import _ from "lodash";

const InboxSearchComposer = (props) => {
    const { configs } = props;
    const [enable, setEnable] = useState(false);
    const [state, dispatch] = useReducer(reducer, initialInboxState)
   
    const apiDetails = configs?.apiDetails
    
    useEffect(() => {
        //here if jsonpaths for search & table are same then searchform gets overridden
        
        if (Object.keys(state.searchForm)?.length >= 0) {
            const result = { ..._.get(apiDetails, apiDetails.searchFormJsonPath, {}), ...state.searchForm }
            Object.keys(result).forEach(key => {
                if (!result[key]) delete result[key]
            });
            _.set(apiDetails, apiDetails.searchFormJsonPath, result)
        }
        if (Object.keys(state.filterForm)?.length >= 0) {
            const result = { ..._.get(apiDetails, apiDetails.filterFormJsonPath, {}), ...state.filterForm }
            Object.keys(result).forEach(key => {
                if (!result[key]) delete result[key]
            });
            _.set(apiDetails, apiDetails.filterFormJsonPath, result)
        }
        if(Object.keys(state.tableForm)?.length >= 0) {
            _.set(apiDetails, apiDetails.tableFormJsonPath, { ..._.get(apiDetails, apiDetails.tableFormJsonPath, {}),...state.tableForm })  
        }

        const searchFormParamCount = Object.keys(state.searchForm).reduce((count,key)=>state.searchForm[key]===""?count:count+1,0)
        const filterFormParamCount = Object.keys(state.filterForm).reduce((count, key) => state.filterForm[key] === "" ? count : count + 1, 0)
        
        if (Object.keys(state.tableForm)?.length > 0 && (searchFormParamCount >= apiDetails.minParametersForSearchForm || filterFormParamCount >= apiDetails.minParametersForFilterForm)){
            setEnable(true)
        }
    }, [state])
    

    let requestCriteria = {
        url:configs?.apiDetails?.serviceName,
        params:configs?.apiDetails?.requestParam,
        body:configs?.apiDetails?.requestBody,
        config: {
            enabled: enable,
        },
    };
    
    const updatedReqCriteria = Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.preProcess ? Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.preProcess(requestCriteria) : requestCriteria 

    
    const { isLoading, data, revalidate,isFetching } = Digit.Hooks.useCustomAPIHook(updatedReqCriteria);
    
    
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
                                screenType={configs.type}
                                fullConfig={configs}/>
                        </div>
                }
                {
                configs?.sections?.filter?.show &&  
                    <div className="section filter">
                        <SearchComponent 
                                uiConfig={ configs?.sections?.filter?.uiConfig} 
                                header={configs?.sections?.filter?.label} 
                                screenType={configs.type}
                                fullConfig={configs}/>
                    </div> 
                }
                {   
                configs?.sections?.searchResult?.show &&  
                        <div className="" style={data?.[configs?.sections?.searchResult?.uiConfig?.resultsJsonPath]?.length > 0 ? (!(isLoading || isFetching) ?{ overflowX: "scroll" }: {}) : {  }} >
                            <ResultsTable config={configs?.sections?.searchResult?.uiConfig} data={data} isLoading={isLoading} isFetching={isFetching} fullConfig={configs}/>
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
