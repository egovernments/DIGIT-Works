import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../configs/InboxConfig";
import MobileView from "../../../components/MobileView";

const ProjectSearchAndInboxComponent = () => {
    const { t } = useTranslation();
    const configs = inboxConfig();

    let isMobile = window.Digit.Utils.browser.isMobile();
    console.log("Mobile : ", isMobile);
    
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