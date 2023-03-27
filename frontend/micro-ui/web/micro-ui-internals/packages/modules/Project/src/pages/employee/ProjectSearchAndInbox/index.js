import React from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../configs/InboxConfig";

const ProjectSearchAndInboxComponent = () => {
   const { t } = useTranslation();

    //const configs = inboxConfig();
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "InboxProjectConfig"
            }
        ]
    );
   
    const configs = data?.[Digit.Utils.getConfigModuleName()]?.InboxProjectConfig?.[0]

    const projectSearchSession = Digit.Hooks.useSessionStorage("MOBILE_SEARCH_PROJECT", configs?.sections.search.uiConfig.defaultValues);

    const [searchSessionFormData, setSearchSessionFormData, clearSearchSessionFormData] = projectSearchSession;

    //for Filter Pop-up in Mobile View
    const projectFilterSession = Digit.Hooks.useSessionStorage("MOBILE_FILTER_PROJECT", configs?.sections.filter.uiConfig.defaultValues);

    const [filterSessionFormData, setFilterSessionFormData, clearFilterSessionFormData] = projectFilterSession;

    if(isLoading) return <Loader />

      return (
           <React.Fragment>
              <Header styles={{ fontSize: "32px" }}>{t(configs?.label)}</Header>
              <div className="inbox-search-wrapper">
              <InboxSearchComposer 
                 searchSessionStorageProps={{searchSessionFormData, setSearchSessionFormData, clearSearchSessionFormData}} 
                 filterSessionStorageProps={{filterSessionFormData, setFilterSessionFormData, clearFilterSessionFormData}}
                 configs={configs}>
             </InboxSearchComposer>
              </div>
          </React.Fragment>
      );
}

export default ProjectSearchAndInboxComponent;