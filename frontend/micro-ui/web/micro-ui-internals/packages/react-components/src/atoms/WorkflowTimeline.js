import React, { Fragment } from 'react'
import { Loader } from "./Loader"
import CardSectionHeader from './CardSectionHeader';
import { CheckPoint, ConnectingCheckPoints } from './ConnectingCheckPoints';
import BreakLine from './BreakLine';
import { useTranslation } from "react-i18next";
import TLCaption from './TLCaption';

function OpenImage(imageSource, index, thumbnailsToShow) {
    window.open(thumbnailsToShow?.fullImage?.[0], "_blank");
}

const WorkflowTimeline = ({ businessService, tenantId,applicationNo, timelineStatusPrefix="ESTIMATE_" ,statusAttribute="status", ...props}) => {
    
    //for testing from url these 2 lines of code are kept here
    // const { estimateNumber } = Digit.Hooks.useQueryParams();
    // applicationNo = applicationNo? applicationNo : estimateNumber 
    const { t } = useTranslation();
    const getTimelineCaptions = (checkpoint) => {
        
        const caption = {
            date: `${Digit.DateUtils?.ConvertTimestampToDate(checkpoint.auditDetails.lastModifiedEpoch)} ${Digit.DateUtils?.ConvertEpochToTimeInHours(
                checkpoint.auditDetails.lastModifiedEpoch
            )} ${Digit.DateUtils?.getDayfromTimeStamp(checkpoint.auditDetails.lastModifiedEpoch)}`,
            name: checkpoint?.assignes?.[0]?.name,
            mobileNumber: checkpoint?.assignes?.[0]?.mobileNumber,
            wfComment: checkpoint?.comment ? [checkpoint?.comment] :[],
            thumbnailsToShow: checkpoint?.thumbnailsToShow,
        };

        return <TLCaption data={caption} OpenImage={OpenImage} />;
        
    };

    let workflowDetails = Digit.Hooks.useWorkflowDetailsWorks(
        {
            tenantId: tenantId,
            id: applicationNo,
            moduleCode: businessService,
            config: {
                enabled: true,
                cacheTime: 0
            }
        }
    );
    
    return (
        <Fragment>
            {workflowDetails?.isLoading && <Loader />}
            { workflowDetails?.data?.timeline?.length > 0 && (
                <React.Fragment>
                    {workflowDetails?.breakLineRequired === undefined ? <BreakLine /> : workflowDetails?.breakLineRequired ? <BreakLine /> : null}
                    {!workflowDetails?.isLoading && (
                        <Fragment>
                            <CardSectionHeader style={{ marginBottom: "16px", marginTop: "32px" }}>
                                {t("WORKS_WORKFLOW_HISTORY")}
                            </CardSectionHeader>
                            {workflowDetails?.data?.timeline && workflowDetails?.data?.timeline?.length === 1 ? (
                                <CheckPoint
                                    isCompleted={true}
                                    label={t(`${timelineStatusPrefix}${workflowDetails?.data?.timeline[0]?.state}`)}
                                    customChild={getTimelineCaptions(workflowDetails?.data?.timeline[0])}
                                />
                            ) : (
                                <ConnectingCheckPoints>
                                    {workflowDetails?.data?.timeline &&
                                        workflowDetails?.data?.timeline.map((checkpoint, index, arr) => {
                                            return (
                                                <React.Fragment key={index}>
                                                    <CheckPoint
                                                        keyValue={index}
                                                        isCompleted={index === 0}
                                                        //info={checkpoint.comment}
                                                        label={t(
                                                            `${timelineStatusPrefix}${checkpoint?.performedAction === "EDIT" ? `${checkpoint?.performedAction}_ACTION` : checkpoint?.[statusAttribute]
                                                            }`
                                                        )}
                                                        customChild={getTimelineCaptions(checkpoint)}
                                                    />
                                                    
                                                </React.Fragment>
                                            );
                                        })}
                                </ConnectingCheckPoints>
                            )}
                        </Fragment>
                    )}
                </React.Fragment>
            )}
        </Fragment>
    )
}

export default WorkflowTimeline