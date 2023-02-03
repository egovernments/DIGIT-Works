import { Header, MultiLink, Card, StatusTable, Row, CardSubHeader,Loader,SubmitBar,ActionBar, HorizontalNav } from '@egovernments/digit-ui-react-components'
import React, { Fragment,useState } from 'react'
import { useTranslation } from 'react-i18next'
import ProjectDetailsNavDetails from './ProjectDetailsNavDetails'

const configNavItems = [
    {
        "name":"Project_Details",
        "code":"WORKS_PROJECT_DETAILS",
    },
    {
        "name":"Financial_Details",
        "code":"WORKS_FINANCIAL_DETAILS"
    }
]

const ProjectDetails = () => {
    const { t } = useTranslation();
    const [activeLink, setActiveLink] = useState("Project_Details");

    return (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_PROJECT_DETAILS")}</Header>
            </div>

            <Card className={"employeeCard-override"} >
                <CardSubHeader style={{ marginBottom: "16px", fontSize: "24px" }}>{t("WORKS_PROJECT_DETAILS")}</CardSubHeader>
                <StatusTable>
                    <Row className="border-none" label={`${t("WORKS_PROJECT_ID")}:`} text={"PR-2102-13130"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PDF_STATIC_LABEL_ESTIMATE_PROPOSAL_DATE")}:`} text={"06/05/2006"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("PDF_STATIC_LABEL_ESTIMATE_PROJECT_NAME")}:`} text={"RWHS Ward 1"} textStyle={{ whiteSpace: "pre" }} isMandotary={true} />
                    <Row className="border-none" label={`${t("PROJECT_DESC")}:`} text={"Rainwater Harvesting Scheme in Ward 1"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_THE_PROJECT_HAS_SUB_PROJECT_LABEL")}:`} text={"No"} textStyle={{ whiteSpace: "pre" }} />
                    <Row className="border-none" label={`${t("WORKS_PARENT_PROJECT_ID")}:`} text={"PR-2102-13130"} textStyle={{ whiteSpace: "pre" }} isValueLink={{href : ""}} />
                </StatusTable>
            </Card>
            <HorizontalNav showNav={true} configNavItems={configNavItems} activeLink={activeLink} setActiveLink={setActiveLink} inFormComposer={false}>  
              <ProjectDetailsNavDetails 
                activeLink={activeLink}
              />
            </HorizontalNav>
            <ActionBar>
                <SubmitBar onSubmit={() => { }} label={t("WORKS_ACTIONS")} />
            </ActionBar>
        </div>
    )
}

export default ProjectDetails;