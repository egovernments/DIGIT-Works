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
    // const [payload, setPayload] = useState(
    //     [   
    //         configs?.apiDetails?.serviceName,
    //         configs?.apiDetails?.requestParam, 
    //         configs?.apiDetails?.requestBody,
    //         {enable:false}
    //     ]
    //     )
    useEffect(() => {
        // const requestBody = configs?.apiDetails?.requestBody
        const apiDetails = configs?.apiDetails
        if (Object.keys(state.searchForm)?.length > 0) {
            //here we can't directly put Projects[0] -> need to generalise this
            apiDetails.requestBody.Projects[0] = { tenantId: Digit.ULBService.getCurrentTenantId(),...state.searchForm }
            // requestBody.Projects[0]={...state?.searchForm}
            setEnable(true)
        }
        if(Object.keys(state.tableForm)?.length > 0) {
            apiDetails.requestParam = { tenantId: Digit.ULBService.getCurrentTenantId(), ...state.tableForm }
            setEnable(true)
        }
    }, [state])
    
   
    // useEffect(() => {
    //     //whenever state is update we'll update the payload

    // }, [state])

    const requestCriteria = [
        configs?.apiDetails?.serviceName,
        configs?.apiDetails?.requestParam,
        configs?.apiDetails?.requestBody,
        {
            enabled: enable,
            // select: config?.apiDetails?.preProcessResponese ? config?.apiDetails?.preProcessResponese : null
        }
    ];

    const { isLoading, data, revalidate } = Digit.Hooks.useCustomAPIHook(...requestCriteria);
    
    
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
                        <div className="" style={data ? { overflowX: "scroll" }:{}} >
                        <ResultsTable config={configs?.sections?.searchResult?.uiConfig} data={data} isLoading={isLoading}/>
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
