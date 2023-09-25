import React from "react";
import { useTranslation } from "react-i18next";
import MeasurementHistory from "./MBHistoryTable";


const ContractDetailsCard = ({ contract, isUpdate }) => {
    // isUpdate = true;
    // get MBNumber from the url
    const searchparams = new URLSearchParams(location.search);
    const mbNumber = searchparams.get("mbNumber");

    const { t } = useTranslation();
    // Define the card style
    const cardStyle = {
        backgroundColor: "#ffffff",
        margin: "15px",

        padding: "20px",
        border: "1px solid #ccc",
        display: "flex",
        flexDirection: "column",
    };

    // if (key === "ES_COMMON_LOCATION") {
    //     const location = value;
    //     const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
    //     if (location) {
    //         let locality = location?.locality ? t(`${headerLocale}_ADMIN_${location?.locality}`) : "";
    //         let ward = location?.ward ? t(`${headerLocale}_ADMIN_${location?.ward}`) : "";
    //         let city = location?.city ? t(`TENANT_TENANTS_${Digit.Utils.locale.getTransformedLocale(location?.city)}`) : "";
    //         return <p>{`${ward ? ward + ", " : ""}${city}`}</p>;
    //     }
    //     return <p>{"NA"}</p>;
    // }

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
            ward: projectWard,
        },
    } = contract;
    const headerLocale = Digit.Utils.locale.getTransformedLocale(Digit.ULBService.getCurrentTenantId())
    const Pward = projectWard ? t(`${headerLocale}_ADMIN_${projectWard}`) : "";
    // const city = projectLoc ? t(`${Digit.Utils.locale.getTransformedLocale(projectLoc)}`) : "";
    const city = projectLoc ? t(`${headerLocale}_ADMIN_${projectLoc}`) : "";

    // Create an object for key-value pairs
    const keyValuePairs = {
        "Work Order Number:": contractNumber,
        "Project Id:": projectID,
        "Project Sanction Date:": "NA", // did not find the value yet
        "Project Name:": projectName,
        "Project Description:": projectDesc,
        "Project Location:": `${Pward ? Pward + ", " : ""}${city}`,
    };

    return (
        <div>
            <div className="contract-details-card" style={cardStyle}>
                {Object.entries(keyValuePairs).map(([key, value]) => (
                    <div key={key} style={keyValueContainerStyle}>
                        <span style={labelStyle}>{key}</span>
                        <span>{value}</span>
                    </div>
                ))}
            </div>

            {isUpdate && <MeasurementHistory contractNumber={contractNumber} measurementNumber={mbNumber}></MeasurementHistory>}
        </div>
    );
};

export default ContractDetailsCard;
