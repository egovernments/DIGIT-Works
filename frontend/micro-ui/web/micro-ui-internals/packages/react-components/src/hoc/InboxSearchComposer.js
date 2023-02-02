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
    // const [payload, setPayload] = useState(
    //     [   
    //         configs?.apiDetails?.serviceName,
    //         configs?.apiDetails?.requestParam, 
    //         configs?.apiDetails?.requestBody,
    //         {enable:false}
    //     ]
    //     )
    const apiDetails = configs?.apiDetails
    useEffect(() => {
        // const requestBody = configs?.apiDetails?.requestBody
        
        if (Object.keys(state.searchForm)?.length > 0) {
            //here we can't directly put Projects[0] -> need to generalise this
            // apiDetails.requestBody.Projects[0] = { ...apiDetails.mandatoryFieldsInBody,...state.searchForm }
            const mandatoryFields = {}
            // Object.keys(apiDetails.mandatoryFieldsInBody).forEach(key=>{
            //     // debugger
            //     mandatoryFields[key] = Digit.Utils.commonUiUtils[apiDetails.mandatoryFieldsInBody[key]]()
            // })
            
            // _.set(apiDetails, apiDetails.jsonPathForReqBody, { ...mandatoryFields, ...state.searchForm })

            _.set(apiDetails, apiDetails.jsonPathForReqBody, { ...state.searchForm })

            // setEnable(true)
        }
        if(Object.keys(state.tableForm)?.length > 0) {
            const mandatoryFields = {}
            // Object.keys(apiDetails.mandatoryFieldsInParam).forEach(key => {
            //     // debugger
            //     mandatoryFields[key] = Digit.Utils.commonUiUtils[apiDetails.mandatoryFieldsInParam[key]]()
            // })
            // _.set(apiDetails, apiDetails.jsonPathForReqParam, { ...mandatoryFields, ...state.tableForm })  
            
            _.set(apiDetails, apiDetails.jsonPathForReqParam, { ...state.tableForm })  

            
            // setEnable(true)
        }
        if (Object.keys(state.tableForm)?.length > 0 && Object.keys(state.searchForm)?.length >= apiDetails.minParametersForSearchForm){
            setEnable(true)
        }
    }, [state])
    

    let requestCriteria = {
        url:configs?.apiDetails?.serviceName,
        params:configs?.apiDetails?.requestParam,
        body:configs?.apiDetails?.requestBody,
        config: {
            enabled: enable,
            // select: config?.apiDetails?.preProcessResponese ? config?.apiDetails?.preProcessResponese : null
        },
        // jsonPath: configs?.apiDetails?.queryNameJsonPath
    };
    
    const updatedReqCriteria =  Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName] ? Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName](requestCriteria) : requestCriteria 

    
    const { isLoading, data, revalidate } = Digit.Hooks.useCustomAPIHook(updatedReqCriteria);
    
    
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
