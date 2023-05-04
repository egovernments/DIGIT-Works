import React, { Fragment } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ContractDetails = (props) => {
    const { t } = useTranslation()
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract")

    if (props.isLoading) return <Loader />
    return (
        <div>
            <ApplicationDetails
                applicationDetails={props.data?.applicationDetails}
                isLoading={props.isLoading}
                applicationData={""}
                moduleCode="Contract"
                showTimeLine={true}
                timelineStatusPrefix={`WF_${businessService}_`}
                businessService={businessService}
                // forcedActionPrefix={"ACTION_"}
                tenantId={props.tenantId}
                applicationNo={props.contractNumber}
                statusAttribute={"state"}
            />
        </div>
    )
}

export default ContractDetails