import React, { Fragment } from 'react'
import { Loader, WorkflowActions, WorkflowTimeline } from '@egovernments/digit-ui-react-components';
import { useTranslation } from "react-i18next";
import ApplicationDetails from '../../../../templates/ApplicationDetails';

const ContractDetails = (props) => {

    const { t } = useTranslation()

    const { tenantId, contractNumber } = Digit.Hooks.useQueryParams();
    const businessService = Digit?.Customizations?.["commonUiConfig"]?.getBusinessService("contract-approval-mukta")

    const { isLoading: isContractLoading,data } = Digit.Hooks.contracts.useViewContractDetails(
        props.tenantId,{tenantId: props.tenantId, contractNumber: props.contractNumber}, {})
    console.log("DATAAA :", data);

    if (isContractLoading) return <Loader />

    return (
        <div>
            <ApplicationDetails
                applicationDetails={props.applicationDetails}
                isLoading={isContractLoading}
                applicationData={props.applicationDetails?.applicationData}
                moduleCode="contracts"
                showTimeLine={true}
                timelineStatusPrefix={"WF_CONTRACT_STATUS_"}
                businessService={businessService}
                // forcedActionPrefix={"ACTION_"}
                tenantId={tenantId}
                applicationNo={contractNumber}
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