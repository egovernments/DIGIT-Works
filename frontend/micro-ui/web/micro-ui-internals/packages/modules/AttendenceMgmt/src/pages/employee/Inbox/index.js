import React,{useState,useEffect,useMemo} from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer, Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../../config/inboxConfig";
import { useLocation } from 'react-router-dom';

const Inbox = () => {
    const { t } = useTranslation();
    const location = useLocation()
    
    //const configs = inboxConfig();
    const [pageConfig, setPageConfig] = useState(null)
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(
        tenant,
        Digit.Utils.getConfigModuleName(),
        [
            {
                "name": "InboxMusterConfig"
            }
        ]
    );
   
    const configs = data?.[Digit.Utils.getConfigModuleName()]?.InboxMusterConfig?.[0]


   
    useEffect(() => {
        setPageConfig(_.cloneDeep(data?.[Digit.Utils.getConfigModuleName()]?.InboxMusterConfig?.[0]))

    }, [data, location])
    //for Search Pop-up in Mobile View
    const musterSearchSession = Digit.Hooks.useSessionStorage("MOBILE_SEARCH_MUSTER_ROLL", pageConfig?.sections.search.uiConfig.defaultValues);

    const [searchSessionFormData, setSearchSessionFormData, clearSearchSessionFormData] = musterSearchSession;

    //for Filter Pop-up in Mobile View
    const musterFilterSession = Digit.Hooks.useSessionStorage("MOBILE_FILTER_MUSTER_ROLL", pageConfig?.sections.filter.uiConfig.defaultValues);

    const [filterSessionFormData, setFilterSessionFormData, clearFilterSessionFormData] = musterFilterSession;

    if(isLoading || !pageConfig)  return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t(pageConfig?.label)}{location?.state?.count ? <span className="inbox-count">{location?.state?.count}</span> : null}</Header>
            <div className="inbox-search-wrapper">
            <InboxSearchComposer
               searchSessionStorageProps={{ searchSessionFormData, setSearchSessionFormData, clearSearchSessionFormData }}
               filterSessionStorageProps={{ filterSessionFormData, setFilterSessionFormData, clearFilterSessionFormData }}
               configs={pageConfig}
             ></InboxSearchComposer>;
                {/* <InboxSearchComposer configs={pageConfig}></InboxSearchComposer> */}
            </div>
        </React.Fragment>
    )
}

export default Inbox;