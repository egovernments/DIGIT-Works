import React from 'react'
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { ActionBar, Banner, Card, CardText, Loader, SubmitBar } from "@egovernments/digit-ui-react-components";


const CreateBillSuccess = ({isSuccess}) => {
    const {t} = useTranslation()

    return (
        <Card>
            <Banner 
                successful={isSuccess}
                message={`${isSuccess ? "Bill Forwarded Sucessfully" : "Failed to forward the Bill"}`}
                info={`${isSuccess ? "Bill ID" : ""}`}
                applicationNumber={`${isSuccess ? "Bill/2021-22/09/0001" : ""}`}
                whichSvg={`${isSuccess ? "tick" : null}`}
            />

            {isSuccess && <CardText>{"Bill has been successfully created and forwarded for approval"}</CardText>}

            <ActionBar>
                <Link to={`/${window.contextPath}/employee`}>
                    <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
                </Link>
            </ActionBar>
        </Card>
    )
}

export default CreateBillSuccess