import React, { useEffect, useState } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, CardText, LinkLabel, EditIcon, ArrowLeftWhite} from "@egovernments/digit-ui-react-components";

const CreateProjectResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [ projectIDsList, setProjectIDsList ] = useState(queryStrings?.projectIDs.split(','));
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(Boolean(queryStrings?.isSuccess));

    const navigate = (page) =>{
        switch(page){
            case "search-project" : {
                history.push(`/${window.contextPath}/employee/project/search-project`);
                break;
            }
            case "create-estimate" : {
                history.push(`/${window.contextPath}/employee/estimate/create-estimate`);
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
                    <ArrowLeftWhite style={{marginRight: "8px"}}/>{t("PROJECT_GO_TO_SEARCH_PROJECT")}
                </LinkLabel>
                <LinkLabel style={{ display: "flex" }} onClick={()=>navigate('create-estimate')}>
                    <EditIcon style={{marginRight: "8px"}}/>{t("COMMON_CREATE_ESTIMATE")}
                </LinkLabel>      
            </div>
        </Card>
    )
}

export default CreateProjectResponse;
