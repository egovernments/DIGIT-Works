import React, { useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, LinkLabel, AddFileFilled, ArrowLeftWhite, ActionBar, SubmitBar} from "@egovernments/digit-ui-react-components";

const CreateProjectResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [ projectIDsList, setProjectIDsList ] = useState(queryStrings?.projectIDs.split(','));
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true);

    const navigate = (page) =>{
        switch(page){
            case "search-project" : {
                history.push(`/${window.contextPath}/employee/project/search-project`);
                break;
            }
            case "create-estimate" : {
                history.push(`/${window.contextPath}/employee/estimate/create-estimate?tenantId=${queryStrings?.tenantId}&projectNumber=${projectIDsList?.[0]}`);
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
                successful={isResponseSuccess}
                message={`${isResponseSuccess ? t("WORKS_PROJECT_CREATED") : t("WORKS_PROJECT_CREATE_FAILURE")}`}
                info={`${isResponseSuccess ? t("WORKS_PROJECT_ID") : ""}`}
                multipleResponseIDs={projectIDsList}
                whichSvg={`${isResponseSuccess ? "tick" : null}`}
            />
            <div style={{display: "flex"}}>
                <LinkLabel style={{ display: "flex", marginRight : "3rem" }} onClick={()=>navigate('search-project')}>
                    <ArrowLeftWhite  fill="#F47738" style={{marginRight: "8px", marginTop : "3px"}}/>{t("PROJECT_GO_TO_SEARCH_PROJECT")}
                </LinkLabel>
                {isResponseSuccess && <LinkLabel style={{ display: "flex" }} onClick={()=>navigate('create-estimate')}>
                    <AddFileFilled style={{marginRight: "8px", marginTop : "3px"}}/>{t("COMMON_CREATE_ESTIMATE")}
                </LinkLabel>  }    
            </div>
            <ActionBar>
                <Link to={`/${window.contextPath}/employee`}>
                    <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
                </Link>
            </ActionBar>
        </Card>
    )
}

export default CreateProjectResponse;
