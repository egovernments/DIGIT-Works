import React, { Fragment } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ContractDetails = (props) => {
    const { t } = useTranslation()
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract-approval-mukta")

    if (props.isLoading) return <Loader />
    return (
        <div>
            <ApplicationDetails
                applicationDetails={props.data?.applicationDetails}
                isLoading={props.isLoading}
                applicationData={""}
                moduleCode="contracts"
                showTimeLine={false}
                timelineStatusPrefix={"WF_CONTRACT_STATUS_"}
                businessService={businessService}
                // forcedActionPrefix={"ACTION_"}
                tenantId={props.tenantId}
                applicationNo={props.contractNumber}
                statusAttribute={"state"}
            />
            {/* <WorkflowActions
                forcedActionPrefix={"WF_CONTRACT_ACTION"}
                businessService={businessService}
                applicationNo={contractNumber}
                tenantId={tenantId}
                applicationDetails={props.applicationDetails?.applicationData}
                url={Digit.Utils.Urls.works.updateEstimate}
            /> */}
        </div>
    )
}

export default ContractDetails