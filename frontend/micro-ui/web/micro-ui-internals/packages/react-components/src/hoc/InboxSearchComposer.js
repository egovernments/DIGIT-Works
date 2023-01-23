import React, { Fragment } from "react";
import { useForm } from "react-hook-form";
import InboxLinks from "../atoms/InboxLinks";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    const { register, handleSubmit, watch, formState: { errors } } = useForm();
    const onSubmit = data => console.log(data);
    
    const onFormSubmit = (_data) => {
        console.log(_data);
    }

    return (
        <>
            <form onSubmit={handleSubmit(onFormSubmit)}>
                <div className="inbox-search-component-wrapper ">
                    <div className={`sections-parent ${configs?.form?.type}`}>
                            {/* Since we need to keep the config sections order-less, avoiding for loop */}
                            {/* That way the config can have sections in any order */}
                        {
                            configs?.sections?.links?.show &&  
                                <div className="section links">
                                    <InboxLinks customClass="inbox-search-links-component" links={configs?.sections?.links?.uiConfig?.links} headerText={configs?.sections?.links?.uiConfig?.headerText} logoIcon={configs?.sections?.links?.uiConfig?.logoIcon}></InboxLinks>
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
                                <div className="section search-results">
                                    {/* Integrate the Search Results Component here*/}
                                </div>
                            }
                    </div>
                    <div className="additional-sections-parent">
                        {/* One can use this Parent to add additional sub parents to render more sections */}
                    </div>
                </div>
                <SubmitBar submit="submit" />
            </form>
        </>
    )
}

export default InboxSearchComposer;
