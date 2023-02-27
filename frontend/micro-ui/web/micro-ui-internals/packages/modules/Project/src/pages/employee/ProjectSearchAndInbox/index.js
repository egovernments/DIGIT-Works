import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../configs/InboxConfig";
import MobileView from "../../../components/MobileView";

const ProjectSearchAndInboxComponent = () => {
    const { t } = useTranslation();
    let isMobile = window.Digit.Utils.browser.isMobile();
    console.log("Mobile : ", isMobile);

    //const configs = inboxConfig();
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        "commonUiConfig",
        [
            {
                "name": "projectInboxConfig"
            }
        ]
    );
   
    const configs = data?.commonUiConfig?.projectInboxConfig?.[0]

    if(isLoading) return <Loader />
    if (isMobile) {
      return (
        <React.Fragment>
              <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
              <div className="inbox-search-wrapper">
                  <MobileView configs={configs}></MobileView>
              </div>
       </React.Fragment>
      );
    } else {
      return (
           <React.Fragment>
              <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
              <div className="inbox-search-wrapper">
                 <InboxSearchComposer configs={configs}></InboxSearchComposer>
              </div>
          </React.Fragment>
      );
    }
}

export default ProjectSearchAndInboxComponent;