import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader,Loader,SubmitBar,ActionBar, HorizontalNav } from '@egovernments/digit-ui-react-components'
import React, { Fragment,useState } from 'react'
import { useTranslation } from 'react-i18next'

const ProjectDetails = () => {
    const { t } = useTranslation()
    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("PROJECT_PROJECT_DETAILS")}</Header>
            </div>

            <Card className={"employeeCard-override"} >
                <CardSubHeader style={{ marginBottom: "16px", fontSize: "24px" }}>{t("PROJECT_PROJECT_DETAILS")}</CardSubHeader>
                <StatusTable>
                    <Row className="border-none" label={t("PROJECT_PROJECT_ID")} text={"PR-2102-13130"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("PROJECT_DATE_OF_PROPOSAL")} text={"06/05/2006"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("PROJECT_PROJECT_NAME")} text={"RWHS Ward 1"} textStyle={{ whiteSpace: "pre" }} isMandotary={true} />
                    <Row className="border-none" label={t("PROJECTS_DESC")} text={"Rainwater Harvesting Scheme in Ward 1"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("PROJECT_PROJECT_HAS_SUB_PROJECTS")} text={"No"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={t("PROJECT_PARENT_PROJECT_ID")} text={"PR-2102-13130"} textStyle={{ whiteSpace: "pre" }} isValueLink={{href : ""}} />
                </StatusTable>
            </Card>
        </div>
    )
}

export default ProjectDetails;