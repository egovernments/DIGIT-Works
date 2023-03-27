import React, {useMemo,useState,useEffect} from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer,Loader } from "@egovernments/digit-ui-react-components";
import inboxConfig from "../../configs/inboxConfig";
import inboxConfigMukta from "../../configs/inboxConfigMukta";
import { useLocation } from "react-router-dom";

const EstimateInbox = () => {
    const { t } = useTranslation();
    const location = useLocation()
    
    //fetch this config from mdms and pass it to the preProcess fn
    // let configs = inboxConfigMukta(t);
    const [pageConfig,setPageConfig] = useState(null)
    const moduleName = Digit.Utils.getConfigModuleName()
    const tenant = Digit.ULBService.getStateId();
    const { isLoading, data } = Digit.Hooks.useCustomMDMS(tenant,
        moduleName,
        [
            {
                name: "EstimateInboxConfig",
            },
        ]);
        
    // let configs = data?.[moduleName]?.EstimateInboxConfig?.[0]
 

    const updatedConfig = useMemo(
        () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, pageConfig,"sections.search.uiConfig.fields",{}),
        [data,pageConfig]);

    
    useEffect(() => {
        setPageConfig(_.cloneDeep(data?.[moduleName]?.EstimateInboxConfig?.[0]))
        
    }, [data,location])

    //for Search Pop-up in Mobile View
    const estimateSearchSession = Digit.Hooks.useSessionStorage("MOBILE_SEARCH_ESTIMATE", 
        updatedConfig?.sections.search.uiConfig.defaultValues
    );

    const [searchSessionFormData, setSearchSessionFormData, clearSearchSessionFormData] = estimateSearchSession;

    //for Filter Pop-up in Mobile View
    const estimateFilterSession = Digit.Hooks.useSessionStorage("MOBILE_FILTER_ESTIMATE", 
        updatedConfig?.sections.filter.uiConfig.defaultValues
    );

    const [filterSessionFormData, setFilterSessionFormData, clearFilterSessionFormData] = estimateFilterSession;
    
    if(isLoading || !pageConfig) return <Loader />
    return (
        <React.Fragment>
            <Header styles={{ fontSize: "32px" }}>{t(updatedConfig?.label)}{location?.state?.count ? <span className="inbox-count">{location?.state?.count}</span> : null}</Header>
            <div className="inbox-search-wrapper">
            <InboxSearchComposer 
               searchSessionStorageProps={{searchSessionFormData, setSearchSessionFormData, clearSearchSessionFormData}} 
               filterSessionStorageProps={{filterSessionFormData, setFilterSessionFormData, clearFilterSessionFormData}}
               configs={updatedConfig}>
            </InboxSearchComposer>
            </div>
        </React.Fragment>
    )
}

export default EstimateInbox;