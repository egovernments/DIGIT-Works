import React from "react";

const ContractDetailsCard = ({ contract }) => {

    console.log(contract, "contractData")

    // Define the card style
    const cardStyle = {
        backgroundColor: "#ffffff",
        margin: "15px",
        padding: "20px",
        border: "1px solid #ccc",
        display: "flex",
        flexDirection: "column",
    };

    // Define the style for the container of key-value pairs
    const keyValueContainerStyle = {
        display: "grid",
        gridTemplateColumns: "15% 85%",
        marginBottom: "10px"
    };

    // Define the style for labels
    const labelStyle = {
        fontWeight: "bold",
        marginRight: "10px",
    };

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
        "Project ID:": projectID,
        "Project Sanction Date:": "", // did not find the value yet
        "Project Name:": projectName,
        "Project Description:": projectDesc,
        "Project Location:": projectLoc,
    };

    return (
        <div className="contract-details-card" style={cardStyle}>
            {Object.entries(keyValuePairs).map(([key, value]) => (
                <div key={key} style={keyValueContainerStyle}>
                    <span style={labelStyle}>{key}</span>
                    <span>{value}</span>
                </div>
            ))}
        </div>
    );
};

export default ContractDetailsCard;
