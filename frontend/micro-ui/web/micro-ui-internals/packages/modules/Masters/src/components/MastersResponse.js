import React from "react";
import { Link, useLocation, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { ActionBar, Banner, LinkLabel, Card, SubmitBar, ArrowLeftWhite } from "@egovernments/digit-ui-react-components";

const MastersResponse = () => {
    const { t } = useTranslation()
    const history = useHistory();
    const { state }  = useLocation()
    const { individualId } = Digit.Hooks.useQueryParams();

    const navigateToHome = () =>{
        history.push(`/${window.contextPath}/employee`);
    }
    
    return (
        <Card>
            <Banner 
                successful={state?.isSuccess}
                message={state?.message}
                info={`${state?.showWageSeekerID ? t("MASTERS_WAGESEEKER_ID") : ""}`}
                applicationNumber={individualId}
                whichSvg={`${state?.isSuccess ? "tick" : null}`}
            />

            <div style={{display: "flex", justifyContent:"space-between"}}>
                <LinkLabel style={{ display: "flex" }} onClick={navigateToHome}>
                    <ArrowLeftWhite  fill="#F47738" style={{marginRight: "8px", marginTop : "3px"}}/>{t("ES_COMMON_GOTO_HOME")}
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

export default MastersResponse;