import React from "react";
import { useForm } from "react-hook-form";
import SearchComponent from "../atoms/SearchComponent";

const InboxSearchComposer = (props) => {
    const  { configs } = props;
    const { register, handleSubmit, watch, formState: { errors } } = useForm();
    const onSubmit = data => console.log(data);

    return (
        <div className="inbox-search-component-wrapper">
            <div className={`sections-parent ${configs?.type}`}>
                {/* Since we need to keep the config sections order-less, avoiding for loop */}
                {/* That way the config can have sections in any order */}
                { configs?.children?.links?.visible && <div className="section links">
                    {/* Integrate the Links Component here*/}
                    <div>Show links</div>
                </div>}
                { configs?.children?.search?.visible && <div className="section search">
                    <SearchComponent/>
                </div>}
                { configs?.children?.filter?.visible && <div className="section filter">
                    {/* Integrate the Filter Component here*/}
                    <div>Show Filter</div>
                </div> }
                { configs?.children?.searchResult?.visible && <div className="section search-results">
                    {/* Integrate the Search Results Component here*/}
                    <div>Show table</div>
                </div> }
            </div>
            <div className="additional-sections-parent">
                {/* One can use this Parent to add additional sub parents to render more sections */}
            </div>
        </div>
    )
}

export default InboxSearchComposer;
