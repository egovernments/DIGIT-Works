import React from "react";
import { useTranslation } from "react-i18next";


const ContractDetailsCard = ({ contract }) => {


    const { t } = useTranslation();

    // extract details from contract 
    const {
        contractNumber,
        additionalDetails: {
            projectId: projectID,
            startDate: projectSencDate,
            projectName: projectName,
            projectDesc: projectDesc,
            locality: projectLoc,
        },
    } = contract;

    // Create an object for key-value pairs
    const keyValuePairs = {
        "Work Order Number:": contractNumber,
        "Project Id:": projectID,
        "Project Sanction Date:": "", // did not find the value yet
        "Project Name:": projectName,
        "Project Description:": projectDesc,
        "Project Location:": projectLoc,
    };

    return (
        <div className="contract-details-card contract-card" >
            {Object.entries(keyValuePairs).map(([key, value]) => (
                <div key={key} className="key-value-container">
                    <span className="contract-card-label">{key}</span>
                    <span>{value}</span>
                </div>
            ))}
        </div>
    );
};

export default ContractDetailsCard;
