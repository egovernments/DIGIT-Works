import { FormComposer, Header, Loader, Toast } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useLocation, useHistory } from "react-router-dom";
import _ from "lodash";
import { newConfig as newConfigLocal } from "../../../config/ContractorCreateConfig";
import CreateContractorForm from '../../../components/CreateContractor/CreateContractorForm'
const NewApplication = () => {
  const onFormSubmit = async(_data) => {
  }
//   const { t } = useTranslation();
//   const { state } = useLocation();
//   const history = useHistory();
// //   let filters = func.getQueryStringParams(location.search);
//   const [canSubmit, setSubmitValve] = useState(false);
//   const [isEnableLoader, setIsEnableLoader] = useState(false);
//   const [showToast, setShowToast] = useState(null);
//   const [config, setConfig] = useState({head: "WORKS_CREATE_CONTRACTOR",
//   key: "ContractorDetails",
//   isCreate: true,
//   hideInCitizen: true,
//   body: [
//     {
//       head: "WORKS_CREATE_CONTRACTOR",
//       key: "CreateContractor",
//       isCreate: true,
//       hideInCitizen: true,
//       body: [
//       {
//         head: "WORKS_CONTRACTOR_DETAILS",
//         // isEditConnection: true,
//         isCreateContracte: true,
//         // isModifyConnection: true,
//         // isEditByConfigConnection: true,
//         body: [{
//           type: "component",
//           key: "ContractorDetails",
//           component: "WORKSContractorDetails",
//           withoutLabel: true
//         }]
//       },
//       {
//         head: "WORKS_CONTRACTOR_TABLE",
//         // isEditConnection: true,
//         isCreateContracte: true,
//         // isModifyConnection: true,
//         // isEditByConfigConnection: true,
//         body: [{
//           type: "component",
//           key: "ContractorTable",
//           component: "WORKSContractorTable",
//           withoutLabel: true
//         }]
//       }
//       ]
//     }
//   ]
//  });

  // const sessionFormData = [123,123,123]
  // const [sessionFormData, setSessionFormData, clearSessionFormData] = Digit.Hooks.useSessionStorage("PT_CREATE_EMP_TRADE_NEW_FORM", {});


  // useEffect(() => {
  //   const config = newConfigLocal.find((conf) => conf.hideInCitizen && conf.isCreate);
  //   config.head = "WORKS_CREATE_CONTRACTOR";
  //   let bodyDetails = [];
  //   config?.body?.forEach(data => { if(data?.isCreateContracte) bodyDetails.push(data); })
  //   config.body = bodyDetails;
  //   setConfig(config);
  // }, []);

  // const onFormValueChange = (setValue, formData, formState) => {
  //   if (!_.isEqual(sessionFormData, formData)) {
  //     setSessionFormData({ ...sessionFormData, ...formData });
  //   }

  //   if (Object.keys(formState.errors).length > 0 && Object.keys(formState.errors).length == 1 && formState.errors["owners"] && Object.values(formState.errors["owners"].type).filter((ob) => ob.type === "required").length == 0 && !formData?.cpt?.details?.propertyId) setSubmitValve(true);
  //   else setSubmitValve(!(Object.keys(formState.errors).length));
  //   // if(!formData?.cpt?.details?.propertyId) setSubmitValve(false);
  // };
  // const onFormValueChange = (setValue, formData, formState) => {

  //   if (!_.isEqual(sessionFormData, formData)) {
  //     setSessionFormData({ ...sessionFormData, ...formData });
  //   }

    // if (
    //   Object.keys(formState.errors).length > 0 &&
    //   Object.keys(formState.errors).length == 1 &&
    //   formState.errors["owners"] &&
    //   Object.entries(formState.errors["owners"].type).filter((ob) => ob?.[1].type === "required").length == 0
    // ) {
    //   setSubmitValve(true);
    // } else {
    //   setSubmitValve(!Object.keys(formState.errors).length);
    // }
  // };
  // const closeToastOfError = () => { setShowToast(null); };


  // const onSubmit = (data) => {
 
  // };

  // const closeToast = () => {
  //   setShowToast(null);
  // };

  // if (isEnableLoader) {
  //   return <Loader />;
  // }

  return (
    // <React.Fragment>
    //   <div style={{ marginLeft: "15px" }}>
    //     <Header>{t(config.head)}</Header>
    //   </div>
    //   <FormComposer
    //     config={config.body}
    //     userType={"employee"}
    //     onFormValueChange={onFormValueChange}
    //     // isDisabled={!canSubmit}
    //     label={t("WORKS_CREATE_CONTRACTOR")}
    //     onSubmit={onSubmit}
    //     defaultValues={sessionFormData}
    //   ></FormComposer>
    //   {showToast && <Toast isDleteBtn={true} error={showToast?.key === "error" ? true : false} label={t(showToast?.message)} onClose={closeToast} />}
    //   {/* {showToast && <Toast error={showToast.key} label={t(showToast?.message)} onClose={closeToast} />} */}
    // </React.Fragment>
    <CreateContractorForm onFormSubmit={onFormSubmit}/>
  );
};

export default NewApplication;
