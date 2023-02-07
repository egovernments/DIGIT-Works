import React, { useEffect, useState } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, CardText, LinkLabel, EditIcon} from "@egovernments/digit-ui-react-components";

const CreateProjectResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [ projectIDsList, setProjectIDsList ] = useState(queryStrings?.projectIDs.split(','));
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(Boolean(queryStrings?.isSuccess));

    const navigateToCreateProject = () =>{
        history.push(`/${window.contextPath}/employee/project/create-project`);
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
            <div style={{display: "flex", justifyContent:"space-between"}}>
                <LinkLabel style={{ display: "flex" }} onClick={navigateToCreateProject}>
                    <EditIcon style={{marginRight: "8px"}}/>{t("WORKS_CREATE_PROJECT")}
                </LinkLabel>   
            </div>
        </Card>
    )
}

export default CreateProjectResponse;
