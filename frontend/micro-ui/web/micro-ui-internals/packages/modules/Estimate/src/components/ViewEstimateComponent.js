import React, { Fragment } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../templates/ApplicationDetails';

const ViewEstimateComponent = (props) => {

    const { t } = useTranslation()

    const { tenantId, estimateNumber } = Digit.Hooks.useQueryParams();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("estimate")


    const { isLoading, data: applicationDetails } = Digit.Hooks.estimates.useEstimateDetailsScreen(t, tenantId, estimateNumber)

    if (isLoading) return <Loader />

    return (
        <>
            <ApplicationDetails
                applicationDetails={applicationDetails}
                isLoading={isLoading}
                applicationData={applicationDetails?.applicationData}
                moduleCode="Estimate"
                showTimeLine={true}
                timelineStatusPrefix={"WF_ESTIMATE_STATUS_"}
                businessService={businessService}
                // forcedActionPrefix={"ACTION_"}
                tenantId={tenantId}
                applicationNo={estimateNumber}
            />
            <WorkflowActions
                forcedActionPrefix={"WF_ESTIMATE_ACTION"}
                businessService={businessService}
                applicationNo={estimateNumber}
                tenantId={tenantId}
                applicationDetails={applicationDetails?.applicationData}
                url={Digit.Utils.Urls.works.updateEstimate}
            />
        </>
    )
}

export default ViewEstimateComponent