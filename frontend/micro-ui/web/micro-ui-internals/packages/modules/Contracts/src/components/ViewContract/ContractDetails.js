import React, { Fragment } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ContractDetails = (props) => {

    const { t } = useTranslation()

    //const { tenantId, contractNumber } = Digit.Hooks.useQueryParams();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract-approval-mukta")

    // const { isLoading: isContractLoading, data } = Digit.Hooks.contracts.useViewContractDetails(
    //     props.tenantId,{tenantId: props.tenantId, contractNumber: props.contractNumber}, {})

    if (props.isLoading) return <Loader />

    return (
        <div>
            <ApplicationDetails
                applicationDetails={props.data?.applicationDetails}
                isLoading={props.isLoading}
                applicationData={props.data?.applicationData}
                moduleCode="contracts"
                showTimeLine={true}
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