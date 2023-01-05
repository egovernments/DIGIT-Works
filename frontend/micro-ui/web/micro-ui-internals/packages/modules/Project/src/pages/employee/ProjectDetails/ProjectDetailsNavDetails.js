import React, { Fragment } from "react";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ProjectDetailsNavDetails = ({activeLink}) => {

    // integrate once api is ready
    const workflowDetails = {}; 
    const { applicationDetails_ProjectDetails, applicationData_ProjectDetails } = Digit.Hooks.project.useViewProjectDetails({}); 
    const { applicationDetails_FinancialDetails, applicationData_FinancialDetails } = Digit.Hooks.project.useViewFinancialDetails({}); 

    return (
        <>
        {
            (activeLink === "Project_Details") && (
                <ApplicationDetails
                    applicationDetails={applicationDetails_ProjectDetails}
                    isLoading={false} //will come from backend
                    applicationData={applicationData_ProjectDetails}
                    moduleCode="Project"
                    isDataLoading={false}
                    workflowDetails={workflowDetails}
                    showTimeLine={true}
                    timelineStatusPrefix={""}
                    businessService={""}
                    forcedActionPrefix={"PROJECT"}
              />
            )
        }
        {
            (activeLink === "Financial_Details") && (
                <ApplicationDetails
                    applicationDetails={applicationDetails_FinancialDetails}
                    isLoading={false} //will come from backend
                    applicationData={applicationData_FinancialDetails}
                    moduleCode="Project"
                    isDataLoading={false}
                    workflowDetails={workflowDetails}
                    showTimeLine={true}
                    timelineStatusPrefix={""}
                    businessService={""}
                    forcedActionPrefix={"PROJECT"}
          />
            )
        }
        </>
    )
}

export default ProjectDetailsNavDetails;