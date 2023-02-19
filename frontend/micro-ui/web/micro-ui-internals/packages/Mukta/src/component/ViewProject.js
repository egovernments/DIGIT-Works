
import { ActionBar, Header, SubmitBar } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import ApplicationDetails from "../../../modules/templates/ApplicationDetails";

const ViewProject = () => {
    const { t } = useTranslation();
    const tenantId =  Digit.ULBService.getCurrentTenantId();
    const queryStrings = Digit.Hooks.useQueryParams();
    const history = useHistory();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    const searchParams = {
        Projects : [
            {
                tenantId : queryStrings?.tenantId || tenantId,
                projectNumber : queryStrings?.projectNumber || "PR/2022-23/02/000985" //will change this after integration with Search
            }
        ]
    } 
    const filters = {
        limit : 1,
        offset : 0,
        includeAncestors : true,
        includeDescendants : true
    }
    const handleNavigateToEstimatesScreen = () => {
        // history.push(`/${window.contextPath}/employee/estimate/create-estimate?tenantId=${searchParams?.Projects?.[0]?.tenantId}&projectNumber=${searchParams?.Projects?.[0]?.projectNumber}`);
    }

    const preProcessApplicationData = (data) => {
        console.log(data);
    }

    const { data, isLoading } = Digit.Hooks.project.useViewProjectDetailsInEstimate(t, tenantId, searchParams, filters, headerLocale);

    if(!isLoading) {
        //process data 
        preProcessApplicationData(data);
    }

    return  (
        <div className={"employee-main-application-details"}>
            <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
                <Header styles={{ marginLeft: "0px", paddingTop: "10px", fontSize: "32px" }}>{t("WORKS_PROJECT_VIEW_PROJECT")}</Header>
            </div>
            <ApplicationDetails
                applicationDetails={data?.projectDetails?.searchedProject?.details?.projectDetails}
                isLoading={isLoading} 
                applicationData={{}}
                moduleCode="Mukta"
                isDataLoading={isLoading}
                workflowDetails={{}}
                showTimeLine={false}
                timelineStatusPrefix={""}
                businessService={""}
                forcedActionPrefix={"WORKS"}
                noBoxShadow={true}
            />
            <ActionBar>
                <SubmitBar onSubmit={handleNavigateToEstimatesScreen} label={t("ACTION_TEST_CREATE_ESTIMATE")} />
            </ActionBar>
        </div>
    )
}

export default ViewProject;