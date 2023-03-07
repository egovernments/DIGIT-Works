import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../configs/InboxConfig";

const ProjectSearchAndInboxComponent = () => {
    const { t } = useTranslation();

    const configs = inboxConfig();
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "InboxProjectConfig"
            }
        ]
    )
    // const configs = data?.commonUiConfig?.projectInboxConfig?.[0]

    // const projectSession = Digit.Hooks.useSessionStorage("INBOX_CREATE", 
    // {
    //   projectNumber : "123",
    //   department : {name : "Street Lights", code : "DEPT_1"},
    //   projectType : {id : 1,
    //   name : "Mini Park",
    //   code : "MP-CWS",
    //   group : "Capital Works",
    //   beneficiary : "Slum",
    //   active : true,
    //   projectSubType : ["MP001", "MP002"]
    // }
    // });

    // const [sessionFormData, setSessionFormData, clearSessionFormData] = projectSession;

    if(isLoading) return <Loader />

      return (
           <React.Fragment>
              <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
              <div className="inbox-search-wrapper">
                 <InboxSearchComposer configs={configs} isInbox={true}></InboxSearchComposer>
              </div>
          </React.Fragment>
      );
}

export default ProjectSearchAndInboxComponent;