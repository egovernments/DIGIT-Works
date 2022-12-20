import React, { useState } from "react";
import { ArrowCollapseDown, ArrowCollapseUp } from "./svgindex";

const CollapseAndExpandGroups = ({children, groupElements=false, headerLabel, headerValue, customClass=""}) => {

    const [collapse, setCollapse] = useState(false);
    return (
        <div className={`expand-collapse-wrapper ${customClass}`}>
            {groupElements && <div className="expand-collapse-header">
                    <span className="label">{headerLabel}</span>
                    <span className="value">{headerValue}</span>
                    <div onClick={()=>setCollapse((prev)=>!prev)} className="icon-toggle ">
                        {!collapse && <span>
                            <ArrowCollapseUp/>
                        </span>  }
                        {collapse && <span>
                            <ArrowCollapseDown/>
                        </span>}
                    </div>
            </div>
            }
            <div className={`toggling-wrapper ${collapse ? "collapse" : ""}`}>
                {children}
            </div>
        </div>
    )
}

export default CollapseAndExpandGroups;