import React from "react";
import { useForm } from "react-hook-form";
import SearchComponent from "../atoms/SearchComponent";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    const { register, handleSubmit, watch, formState: { errors } } = useForm();
    const onSubmit = data => console.log(data);

    return (
        <div className="inbox-search-component-wrapper">
            <div className={`sections-parent ${configs?.form?.type}`}>
                {/* Since we need to keep the config sections order-less, avoiding for loop */}
                {/* That way the config can have sections in any order */}
                <div className="section links">
                    {/* Integrate the Links Component here*/}
                </div>
                <div className="section search">
                    <SearchComponent/>
                </div>
                <div className="section filter">
                    {/* Integrate the Filter Component here*/}
                </div> 
                <div className="section search-results">
                    {/* Integrate the Search Results Component here*/}
                </div>
            </div>
            <div className="additional-sections-parent">
                {/* One can use this Parent to add additional sub parents to render more sections */}
            </div>
        </div>
    )
}

export default InboxSearchComposer;
