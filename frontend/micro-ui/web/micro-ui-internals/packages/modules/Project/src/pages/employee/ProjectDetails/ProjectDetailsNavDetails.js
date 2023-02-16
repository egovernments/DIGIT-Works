import React, { Fragment } from "react";

const ProjectDetailsNavDetails = ({activeLink, subProjects, searchParams, filters}) => {
    const ViewProjectComponent = Digit?.ComponentRegistryService?.getComponent("ViewProject"); 
    const ViewFinancialDetailsComponent = Digit?.ComponentRegistryService?.getComponent("ViewFinancialDetails"); 
    const ViewSubProjectsDetailsComponent = Digit?.ComponentRegistryService?.getComponent("ViewSubProjectsDetails");

    return (
        <>
        {
            (activeLink === "Project_Details") && (
                <ViewProjectComponent searchParams={searchParams} filters={filters}></ViewProjectComponent>
            )
        }
        {
            (activeLink === "Financial_Details") && (
                <ViewFinancialDetailsComponent searchParams={searchParams} filters={filters} ></ViewFinancialDetailsComponent>
            )
        }
        {
            (activeLink === "Sub_Projects_Details") && (
                <ViewSubProjectsDetailsComponent subProjects={subProjects}></ViewSubProjectsDetailsComponent>
            )
        }
        </>
    )
}

export default ProjectDetailsNavDetails;