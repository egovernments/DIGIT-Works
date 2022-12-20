import React, { useState } from "react";

const CollapseAndExpandGroups = ({children, groupElements=false, headerLabel, headerValue, customClass=""}) => {

    const [collapse, setCollapse] = useState(false);
    return (
        <div className={`expand-collapse-wrapper ${customClass}`}>
            {groupElements && <div className="expand-collapse-header">
                    <span className="label">{headerLabel}</span>
                    <span className="value">{headerValue}</span>
                    <div onClick={()=>setCollapse((prev)=>!prev)} className="icon-toggle ">
                        {!collapse && <span>
                            <svg width="13" height="8" viewBox="0 0 13 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M10.6369 7.3491L12.029 5.9214L5.9536 -0.00228767L0.0299208 6.07307L1.45763 7.46514L5.98937 2.82749L10.6369 7.3491Z" fill="#0B0C0C"/>
                            </svg>
                        </span>  }
                        {collapse && <span>
                            <svg width="12" height="8" viewBox="0 0 12 8" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M1.41 0.59L-2.62268e-07 2L6 8L12 2L10.59 0.59L6 5.17L1.41 0.59Z" fill="#0B0C0C"/>
                            </svg>
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