import React, { Fragment } from "react";
import ApplicationDetails from "../../../../../templates/ApplicationDetails";

const ProjectDetailsNavDetails = ({activeLink}) => {
    const ViewProjectComponent = Digit?.ComponentRegistryService?.getComponent("ViewProject"); 
    const ViewFinancialDetailsComponent = Digit?.ComponentRegistryService?.getComponent("ViewFinancialDetails"); 

    return (
        <>
        {
            (activeLink === "Project_Details") && (
                <ViewProjectComponent ></ViewProjectComponent>
            )
        }
        {
            (activeLink === "Financial_Details") && (
                <ViewFinancialDetailsComponent ></ViewFinancialDetailsComponent>
            )
        }
        </>
    )
}

export default ProjectDetailsNavDetails;