import React from "react";
import { Link, useLocation, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { ActionBar, Banner, LinkLabel, Card, SubmitBar, ArrowLeftWhite, CardText, EditIcon, AddFileFilled } from "@egovernments/digit-ui-react-components";

const MastersResponse = () => {
    const { t } = useTranslation()
    const history = useHistory()
    const { state } = useLocation()
    const queryParams = Digit.Hooks.useQueryParams()

    const navigate = (page) => {
        switch(page){
            case "modify-org" : {
                history.push(`/${window.contextPath}/employee/masters/create-organization?tenantId=${queryParams?.tenantId}&orgId=${queryParams?.orgId}`);
                break;
            }
            case "create-org" : {
                history.push(`/${window.contextPath}/employee/masters/create-organization`);
                break;
            }
            case "home-screen" : {
                history.push(`/${window.contextPath}/employee`);
                break;
            }
        }
    }
    
    return (
        <Card>
            
            <Banner 
                successful={state?.isSuccess}
                message={t(state?.message)}
                info={`${state?.showId ? (state?.isWageSeeker ? t("MASTERS_WAGESEEKER_ID") : t("MASTERS_ORGANISATION_ID")) : ""}`}
                applicationNumber={state?.isWageSeeker ? queryParams?.individualId : queryParams?.orgId}
                whichSvg={`${state?.isSuccess ? "tick" : null}`}
            />
            {!state?.isWageSeeker && <CardText>{t(state?.otherMessage)}</CardText>}

            <div style={{display: "flex", justifyContent:"space-between", width: "60%"}}>
                <LinkLabel style={{ display: "flex" }} onClick={() => navigate("home-screen")}>
                    <ArrowLeftWhite  fill="#F47738" style={{marginRight: "8px", marginTop : "3px"}}/>{t("ES_COMMON_GOTO_HOME")}
                </LinkLabel> 
                {
                    !state?.isWageSeeker && state?.isSuccess &&(
                        <React.Fragment>
                             <LinkLabel style={{ display: "flex" }} onClick={() => navigate("modify-org")}>
                                <EditIcon style={{marginRight: "8px"}}/>{t("MASTERS_ORGANISATION_MODIFY")}
                            </LinkLabel>  
                            <LinkLabel style={{ display: "flex" }} onClick={() => navigate("create-org")}>
                                <AddFileFilled style={{marginRight: "8px", marginTop : "3px"}}/>{t("MASTERS_CREATE_NEW_ORGANISATION")}
                            </LinkLabel>
                        </React.Fragment>
                    )
                }
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