import { Loader,Card } from '@egovernments/digit-ui-react-components';
import React,{Fragment,useState,useEffect} from 'react'
import { useTranslation } from 'react-i18next';
import ApplicationDetailsContent from '../../../../../templates/ApplicationDetails/components/ApplicationDetailsContent';

//useViewProjectDetailsInEstimate
const CreateEstimateTabDetails = ({activeLink}) => {
    const { t } = useTranslation()
    let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewProjectDetailsInEstimate(t, "", "");
    
    return (
        <>
            {activeLink === "Project Details" ? isLoading ? <Loader /> : <Card className={"employeeCard-override"}>
                <ApplicationDetailsContent
                    applicationDetails={applicationDetails}
                    workflowDetails={{}}
                    isDataLoading={isLoading}
                    applicationData={applicationDetails?.applicationData}
                    //businessService={businessService}
                    timelineStatusPrefix={""}
                    statusAttribute={"status"}
                    //paymentsList={paymentsList}
                    showTimeLine={false}
                    //oldValue={oldValue}
                    isInfoLabel={false}
                    noBoxShadow={true}

                />
            </Card> : null}
        </>
    )
}

export default CreateEstimateTabDetails