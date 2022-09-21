import React, { useState, Fragment, useEffect, useRef } from "react";
import {
    Header,
} from "@egovernments/digit-ui-react-components";
import { useParams, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { ApplicationDetailsTemplate } from "../../../../../templates/ApplicationDetails"
import ApplicationDetailsContent from "../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent";

const ViewEstimate = (props) => {
    const { t } = useTranslation()

    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewEstimateDetails(t);

    return (
        <Fragment>
            <div className={"employee-main-application-details"}>
                <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                    <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_VIEW_ESTIMATE")}</Header>
                </div>

                <ApplicationDetailsContent
                    applicationDetails={applicationDetails}
                    //workflowDetails={workflowDetails}
                    //isDataLoading={isDataLoading}
                    //applicationData={applicationData}
                    //businessService={businessService}
                    //timelineStatusPrefix={timelineStatusPrefix}
                    //statusAttribute={statusAttribute}
                    //paymentsList={paymentsList}
                    showTimeLine={false}
                //oldValue={oldValue}
                //isInfoLabel={isInfoLabel}
                />
            </div>
        </Fragment>

    )
}

export default ViewEstimate