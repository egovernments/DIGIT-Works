import React, { Fragment } from "react";
import { useForm } from "react-hook-form";
import InboxLinks from "../atoms/InboxLinks";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    const { register, handleSubmit, watch, formState: { errors } } = useForm();
    const onSubmit = data => console.log(data);

    return (
        <>
            <div className="inbox-search-component-wrapper ">
                <div className={`sections-parent ${configs?.form?.type}`}>
                        {/* Since we need to keep the config sections order-less, avoiding for loop */}
                        {/* That way the config can have sections in any order */}
                       {
                        configs?.type === "inbox" &&  
                            <div className="section links">
                                <InboxLinks links={configs?.sections?.links?.uiConfig?.links} headerText={configs?.sections?.links?.uiConfig?.headerText} logoIcon={configs?.sections?.links?.uiConfig?.logoIcon}></InboxLinks>
                            </div>
                       }
                        <div className="section search">
                            {/* Integrate the Search Component here*/}
                        </div>
                        {
                        configs?.form?.type === "inbox" &&  
                            <div className="section filter">
                                {/* Integrate the Filter Component here*/}
                            </div> 
                        }
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
