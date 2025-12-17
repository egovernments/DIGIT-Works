import { FormComposer, Header } from "@egovernments/digit-ui-react-components";
import React, { Fragment } from "react";
import { useTranslation } from "react-i18next";
import { format } from "date-fns";
import _ from "lodash";
var Digit=window?.Digit;
export const newConfig = [
  {
    head: "WORKS_PROJECT_DETAILS",
    body: [
      {
        inline: true,
        label: "WORKS_ESTIMATE_NO",
        isMandatory: false,
        key: "EstimateNumber",
        type: "text",
        disable: true,
        populators: { name: "estimateNumber", error: "Required", validation: { pattern: Digit?.Utils?.getPattern?.("ApplicationNo") } },
      },
      {
        inline: true,
        label: "WORKS_NAME_OF_WORK",
        isMandatory: false,
        key: "nameOfTheWork",
        type: "text",
        disable: true,
        populators: { name: "task", error: "Required", validation: { pattern: Digit?.Utils?.getPattern?.("TradeName") } },
      },
      {
        inline: true,
        label: "WORKS_SUB_ESTIMATE_NO",
        isMandatory: false,
        key: "subEstimateNo",
        type: "text",
        disable: true,
        populators: { name: "subEstimateNo", error: "Required", validation: { pattern: Digit?.Utils?.getPattern?.("ApplicationNo") } },
      },
      {
        inline: true,
        label: "WORKS_FILE_NO",
        isMandatory: false,
        type: "text",
        disable: false,
        populators: { name: "fileNumber", error: "WORKS_PATTERN_ERR", validation: { pattern: Digit?.Utils?.getPattern?.("ApplicationNo") } },
      },
      {
        label: "WORKS_FILE_DATE",
        isMandatory: false,
        key: "fileDate",
        type: "date",
        disable: false,
        populators: { name: "fileDate" },
      },
      {
        label: "WORKS_EXECUTING_AUTH",
        isMandatory: true,
        key: "executingAuthority",
        type: "radio",
        disable: false,
        populators: {
          name: "executingAuthority",
          error: `WORKS_REQUIRED_ERR`,
          validation: { required: true },
          optionsKey: "name",
          options: [
            {
              code: "WORKS_COMMUNITY_ORGN",
              name: "WORKS_COMMUNITY_ORGN",
            },
            {
              code: "WORKS_DEPARTMENT",
              name: "WORKS_DEPARTMENT",
            },
          ],
        },
      },
    ],
  },
  {
    head: "WORKS_FINANCIAL_DETAILS",
    body: [
      {
        label: "WORKS_PROJECT_ESTIMATE_AMT",
        isMandatory: false,
        key: "projectEstimateAmount",
        type: "text",
        disable: true,
        populators: { name: "projectEstimateAmount", error: "sample error message", validation: { pattern: Digit?.Utils?.getPattern?.("Amount") } },
      },
      {
        isMandatory: false,
        key: "contractedAmount",
        type: "text",
        label: "WORKS_CONTRACTED_AMT",
        disable: true,
        populators: { name: "contractedAmount" },
      },
      {
        isMandatory: true,
        key: "currentContractType",
        type: "radio",
        label: "WORKS_CONTRACT_TYPE",
        disable: false,
        populators: {
          name: "currentContractType",
          optionsKey: "name",
          error: "WORKS_REQUIRED_ERR",
          validation: { required: true },
          options: [
            {
              code: "WORKS_WORK_ORDER",
              name: "WORKS_WORK_ORDER",
            },
            {
              code: "WORKS_PURCHASE_ORDER",
              name: "WORKS_PURCHASE_ORDER",
            },
          ],
        },
      },
      {
        isMandatory: true,
        key: "currentContractAmount",
        type: "text",
        label: "WORKS_CURRENT_CONTRACT_AMT",
        disable: false,
        populators: {
          name: "currentContractAmount",
          optionsKey: "name",
          error: "WORKS_PATTERN_ERR",
          required: true,
          validation: { pattern: Digit?.Utils?.getPattern?.("Amount") },
        },
      },
      {
        key: "balanceAmount",
        type: "number",
        label: "WORKS_BALANCE_AMT",
        disable: true,
        populators: { name: "balanceAmount", error: "WORKS_PATTERN_ERR_NEG_AMT", required: false, validation: { min: 0 } },
      },
    ],
  },
  {
    head: "WORKS_AGGREEMENT_DETAILS",
    body: [
      {
        label: "WORKS_DATE_OF_AGG",
        isMandatory: false,
        key: "dateOfAgreement",
        type: "date",
        disable: false,
        populators: { name: "agreementDate" },
      },
      {
        isMandatory: true,
        description: "WORKS_ORGN_INFO",
        key: "orgnInfo",
        type: "radioordropdown",
        label: "WORKS_NAME_OF_ORGN",
        disable: false,
        populators: {
          name: "orgnInfo",
          error: "WORKS_REQUIRED_ERR",
          required: true,
          optionsKey: "name",
          mdmsConfig: {
            masterName: "Department",
            moduleName: "common-masters",
            localePrefix: "ES_COMMON",
          },
        },
      },
      {
        isMandatory: true,
        key: "orgnId",
        type: "text",
        label: "WORKS_ORGN_ID",
        disable: false,
        populators: {
          name: "organisationId",
          error: "WORKS_REQUIRED_ERR",
          validation: { required: true },
        },
      },
      {
        isMandatory: true,
        key: "contractPeriod",
        type: "text",
        label: "WORKS_CONT_PERIOD",
        disable: false,
        populators: {
          name: "contractPeriod",
          error: "WORKS_PATTERN_ERR",
          required: true,
          pattern: Digit?.Utils?.getPattern?.("UOMValue"),
        },
      },
      {
        key: "officerInChargedesig",
        type: "radioordropdown",
        label: "WORKS_OFFICER_INCHARGE_DES",
        isMandatory: true,
        disable: false,
        populators: {
          name: "officerInChargedesig",
          error: "WORKS_REQUIRED_ERR",
          required: true,
          optionsKey: "name",
          mdmsConfig: {
            masterName: "Designation",
            moduleName: "common-masters",
            localePrefix: "ES_COMMON_DESIGNATION",
          },
        },
      },
      {
        key: "officerInchargeName",
        type: "radioordropdown",
        label: "WORKS_OFFICER_INCHARGE_NAME",
        isMandatory: true,
        disable: false,
        populators: {
          name: "officerInchargeName",
          error: "WORKS_REQUIRED_ERR",
          required: true,
          optionsKey: "name",
          mdmsConfig: {
            masterName: "Designation",
            moduleName: "common-masters",
            localePrefix: "ES_COMMON_DESIGNATION",
          },
        },
      },
    ],
  },
  {
    head: "WORKS_RELEVANT_DOCS",
    body: [
      {
        label: "WORKS_UPLOAD_FILES",
        isMandatory: true,
        key: "document",
        type: "multiupload",
        disable: false,
        populators: { name: "uploads" },
      },
    ],
  },
];
const CreateContractForm = ({ onFormSubmit, estimateNumber, task, subEstimate, sessionFormData, setSessionFormData }) => {
  const { t } = useTranslation();
  const configs = newConfig ? newConfig : newConfig;
  const onFormValueChange = (setValue, formData, formState) => {
    if (!_.isEqual(sessionFormData, formData)) {
      const result = _.pickBy(sessionFormData, (v, k) => !_.isEqual(formData[k], v));

      if (result?.currentContractAmount) {
        setValue("balanceAmount", Number(formData?.projectEstimateAmount) - Number(formData?.currentContractAmount));
        // projectEstimateAmount: "500000",
        // contractedAmount: 0,
        // balanceAmount: 0,
      }
      setSessionFormData({ ...sessionFormData, ...formData });
    }
  };

  return (
    <React.Fragment>
      <Header className="works-header-create">{t("WORKS_CREATE_CONTRACT")}</Header>
      <FormComposer
        label={t("WORKS_PROCEED_FORWARD")}
        config={configs.map((config) => {
          return {
            ...config,
            body: config.body.filter((a) => !a.hideInEmployee),
          };
        })}
        defaultValues={sessionFormData}
        onFormValueChange={onFormValueChange}
        onSubmit={onFormSubmit}
        fieldStyle={{ marginRight: 0 }}
        // className="form-no-margin"
        labelBold={true}
      />
    </React.Fragment>
  );
};

export default CreateContractForm;
