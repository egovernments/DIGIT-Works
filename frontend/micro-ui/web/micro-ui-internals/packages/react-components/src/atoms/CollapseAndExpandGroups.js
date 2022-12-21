import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import CardSubHeader from "./CardSubHeader";
import { ArrowCollapseDown, ArrowCollapseUp } from "./svgindex";

const CollapseAndExpandGroups = ({children, groupElements=false, groupHeader="", headerLabel="", headerValue="", customClass=""}) => {
    const {t} = useTranslation();
    const [collapse, setCollapse] = useState(false);
    return (
        <div className={`expand-collapse-wrapper ${customClass}`}>
             <CardSubHeader style={{ marginBottom: "16px", fontSize: "24px" }}>{t(groupHeader)}</CardSubHeader>
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
            <div className={`${groupElements ? 'toggling-wrapper' : ''} ${groupElements && collapse ? 'collapse' : ''}`}>
                {children}
            </div>
        </div>
    )
}

export default CollapseAndExpandGroups;