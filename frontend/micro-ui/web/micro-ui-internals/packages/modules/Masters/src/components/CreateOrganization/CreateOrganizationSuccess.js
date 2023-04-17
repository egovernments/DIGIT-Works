import React from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { ActionBar, Banner, Card, CardText, Loader, SubmitBar, LinkLabel, EditIcon, AddNewIcon} from "@egovernments/digit-ui-react-components";

const CreateOrganizationSuccess = ({isSuccess, setCreateOrgStatus}) => {
    const {t} = useTranslation()

    const modifyOrg = () => {
        setCreateOrgStatus(null)
    }

    const createOrg = () => {
        setCreateOrgStatus(null)
    }

    return (
        <Card>
            <Banner 
                successful={isSuccess}
                message={`${isSuccess ? t("MASTERS_ORGANISATION_CREATED") : t("MASTERS_ORGANISATION_CREATE_FAILURE")}`}
                info={`${isSuccess ? t("WORKS_ORGANISATION_CODE") : ""}`}
                applicationNumber={`${isSuccess ? "DH21M20129" : ""}`}
                whichSvg={`${isSuccess ? "tick" : null}`}
            />

            {isSuccess && <CardText>{t("MASTERS_ORGANISATION_CREATED_SUCCESS")}</CardText>}

            <div style={{display: "flex", justifyContent:"space-between"}}>
                <LinkLabel style={{ display: "flex" }} onClick={modifyOrg}>
                    <EditIcon style={{marginRight: "8px"}}/>{t("MASTERS_ORGANISATION_MODIFY")}
                </LinkLabel>
                <LinkLabel style={{ display: "flex" }} onClick={createOrg}>
                    <AddNewIcon style={{marginRight: "8px"}}/>{t("MASTERS_CREATE_NEW_ORGANISATION")}
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

export default CreateOrganizationSuccess;