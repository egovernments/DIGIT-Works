import React from "react";
import CreateWorkOrderForm from "./CreateWorkOrderForm";
import createWorkOrderConfigMUKTA from "../../../configs/createWorkOrderConfigMUKTA.json";

const CreateWorkOrder = () => {

    const configs = createWorkOrderConfigMUKTA?.CreateWorkOrderConfig?.[0];
    const ContractSession = Digit.Hooks.useSessionStorage("CONTRACT_CREATE", {
        basicDetails_projectID : "PROJECTNUMBER",
        basicDetails_dateOfProposal : "DATEPFPROPOSAL",
        basicDetails_projectName : "PROJECTNAME",
        basicDetails_projectDesc : "PROJECTDESC",
        cboID : "CBOID",
        designationOfOfficerInCharge : "DESNOFOFFICERINCHARGE",
    });
    const [sessionFormData, setSessionFormData, clearSessionFormData] = ContractSession;

    return (
        <CreateWorkOrderForm createWorkOrderConfig={configs} sessionFormData={sessionFormData} setSessionFormData={setSessionFormData} clearSessionFormData={clearSessionFormData}></CreateWorkOrderForm>
    )
}

export default CreateWorkOrder;