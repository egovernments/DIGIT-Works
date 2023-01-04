import { RadioButtons } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";

const createProjectConfig =  (hasSubProjectOptions, handleHasSubProjectOptions) => {
  const { t } = useTranslation();

  return {
    defaultValues : {
        dateOfProposal : "",
        projectName : "",
        projectDesc : "",
        hasSubProjects : ""
    },
    form: [
      { 
        head: "",
        subHead: "",
        body: [
          {
            inline: true,
            label: "PROJECT_DATE_OF_PROPOSAL",
            isMandatory: false,
            key: "dateOfProposal",
            type: "date",
            disable: true,
            populators: { name: "dateOfProposal" },
          },
          {
            inline: true,
            label: "PROJECT_NAME",
            isMandatory: true,
            key: "projectName",
            type: "text",
            disable: false,
            populators: { name: "projectName" }
          },
          {
            inline: true,
            label: "PROJECT_DESC",
            isMandatory: false,
            key: "projectDesc",
            type: "text",
            disable: false,
            populators: { name: "projectDesc" }
          },
          {
            isMandatory: false,
            key: "hasSubProjects",
            type: "goToDefaultCase",
            label: "PROJECT_SUB_PROJECT",
            disable: false,
            populators: <div className="radio-wrap flex-row">
                        {
                            hasSubProjectOptions?.options?.map((option)=>(
                                <div key={option?.key} className="mg-sm">
                                    <span className="radio-btn-wrap">
                                        <input
                                            className="radio-btn"
                                            type="radio"
                                            value={option?.value}
                                            checked={hasSubProjectOptions?.options[0]?.value}
                                            onChange={() => handleHasSubProjectOptions(option)}
                                            name="hasSubProjects"   
                                        />
                                        <span className="radio-btn-checkmark"></span>
                                    </span>
                                    <label>{t(option?.code)}</label>
                            </div>
                            ))
                        }
                    </div>
        }
    ]
    }
    ]
  };
};

export default createProjectConfig;