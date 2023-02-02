import React from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, CardText, LinkLabel, EditIcon} from "@egovernments/digit-ui-react-components";

const CreateProjectResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const location = useLocation();
    const { isSuccess, projectID } = location?.state;

    const navigateToCreateProject = () =>{
        history.push(`/${window.contextPath}/employee/project/create-project`);
    }
    return (
        <Card>
            <Banner 
                successful={isSuccess}
                message={`${isSuccess ? t("WORKS_PROJECT_CREATED") : t("WORKS_PROJECT_CREATE_FAILURE")}`}
                info={`${isSuccess ? t("WORKS_PROJECT_ID") : ""}`}
                applicationNumber={`${isSuccess ? projectID : ""}`}
                whichSvg={`${isSuccess ? "tick" : null}`}
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