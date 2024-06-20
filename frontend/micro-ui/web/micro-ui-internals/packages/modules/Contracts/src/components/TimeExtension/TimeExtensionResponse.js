import React, { useState } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, LinkLabel, AddFileFilled, ArrowLeftWhite, ActionBar, SubmitBar} from "@egovernments/digit-ui-react-components";

const TimeExtensionResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [revisedWONumber,setRevisedWONumber] = useState(queryStrings?.revisedWONumber)
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true);
    
    const {state} = useLocation();
    
    const navigate = (page) =>{
        switch(page){
            case "contracts-inbox" : {
                history.push(`/${window.contextPath}/employee/contracts/inbox`)
            }
        }
    }

    return (
        <Card>
            <Banner 
                successful={isResponseSuccess}
                message={t(state?.message)}
                info={state?.label ? t(state.label) :(`${state?.showID ? t("CONTRACTS_WO_ID") : ""}`)}
                applicationNumber={revisedWONumber}
                whichSvg={`${isResponseSuccess ? "tick" : null}`}
            />
            <div style={{display: "flex"}}>
                <LinkLabel style={{ display: "flex", marginRight : "3rem" }} onClick={()=>navigate('contracts-inbox')}>
                    <ArrowLeftWhite  fill="#F47738" style={{marginRight: "8px", marginTop : "3px"}}/>{t("COMMON_GO_TO_INBOX")}
                </LinkLabel>
            </div>
            <ActionBar>
                <Link to={`/${window.contextPath}/employee`}>
                    <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
                </Link>
            </ActionBar>
        </Card>
    )
}

export default TimeExtensionResponse;
