import React, { Fragment } from "react";
import { useForm } from "react-hook-form";
import SubmitBar from "../atoms/SubmitBar";

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
                                <div className="section search-results">
                                    {/* Integrate the Search Results Component here*/}
                                </div>
                            }
                    </div>
                    <div className="additional-sections-parent">
                        {/* One can use this Parent to add additional sub parents to render more sections */}
                    </div>
                </div>
                {/* In progress, Park it for now */}
                <SubmitBar submit="submit" />
            </form>
        </>
    )
}

export default InboxSearchComposer;
