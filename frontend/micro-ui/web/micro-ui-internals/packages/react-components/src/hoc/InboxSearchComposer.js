import React, { Fragment,useReducer,useContext } from "react";
import { useForm } from "react-hook-form";
import ResultsTable from "./ResultsTable"
import SubmitBar from "../atoms/SubmitBar";
import reducer, { initialTableState } from "./InboxSearchComposerReducer";
import { InboxContext } from "./InboxSearchComposerContext";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    const { register, handleSubmit, watch, formState: { errors } } = useForm();
    const onSubmit = data => console.log(data);
    
    //whenever this state is updated we'll make a call to the search/inbox api
    const [state, dispatch] = useReducer(reducer, initialTableState)
    // debugger

    const onFormSubmit = (_data) => {
        console.log(_data);
    }

    return (
        <>
            <InboxContext.Provider value={{state,dispatch}} >
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
                                    {/* Integrate the Search Component here*/}
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
                            <div className="" style={{overflowX:"scroll"}}>
                                    {/* Integrate the Search Results Component here*/}
                                    <ResultsTable config={configs?.sections?.searchResult?.uiConfig}/>
                            </div>
                            }
                    </div>
                    <div className="additional-sections-parent">
                        {/* One can use this Parent to add additional sub parents to render more sections */}
                    </div>
                </div>
                {/* In progress, Park it for now */}
                {/* <SubmitBar submit="submit" /> */}
            </InboxContext.Provider>
        </>
    )
}

export default InboxSearchComposer;
