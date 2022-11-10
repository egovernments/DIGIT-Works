import React from "react";
import _ from "lodash";
import CreateContractorForm from '../../../components/CreateContractor/CreateContractorForm'
const CreateContractor = () => {
  const onFormSubmit = async(_data) => {
  }
  return (
    <CreateContractorForm onFormSubmit={onFormSubmit}/>
  );
};

export default CreateContractor;
