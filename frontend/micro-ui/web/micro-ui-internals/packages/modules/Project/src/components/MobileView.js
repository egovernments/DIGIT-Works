import React, { useEffect, useReducer, useState } from "react";
import { Link } from "react-router-dom"; 
//import ResultsTable from "./ResultsTable"

import { Card, DetailsCard, Loader, PopUp, SearchAction, InboxSearchLinks, ResultsTable } from "@egovernments/digit-ui-react-components";
import { FilterAction } from "@egovernments/digit-ui-react-components";
import reducer, { initialInboxState } from "./MobileInboxReducer";
import { MobileInboxContext } from "./MobileInboxContext";
import { MobileFilterComponent } from "./MobileFilterComponent";
import { MobileSearchComponent } from "./MobileSearchComponent";
//import SearchComponent from "../atoms/SearchComponent";
import _ from "lodash";

const MobileView = (props) => {
  const { configs } = props;
  const [enable, setEnable] = useState(false);
  const [state, dispatch] = useReducer(reducer, initialInboxState);
  const [type, setType] = useState("");
  const [popup, setPopup] = useState(false);

  const apiDetails = configs?.apiDetails;

  useEffect(() => {
    //here if jsonpaths for search & table are same then searchform gets overridden

    if (Object.keys(state.searchForm)?.length >= 0) {
      const result = { ..._.get(apiDetails, apiDetails.searchFormJsonPath, {}), ...state.searchForm };
      Object.keys(result).forEach((key) => {
        if (!result[key]) delete result[key];
      });
      _.set(apiDetails, apiDetails.searchFormJsonPath, result);
    }
    if (Object.keys(state.filterForm)?.length >= 0) {
      const result = { ..._.get(apiDetails, apiDetails.filterFormJsonPath, {}), ...state.filterForm };
      Object.keys(result).forEach((key) => {
        if (!result[key]) delete result[key];
      });
      _.set(apiDetails, apiDetails.filterFormJsonPath, result);
    }
    if (Object.keys(state.tableForm)?.length >= 0) {
      _.set(apiDetails, apiDetails.tableFormJsonPath, { ..._.get(apiDetails, apiDetails.tableFormJsonPath, {}), ...state.tableForm });
    }

    const searchFormParamCount = Object.keys(state.searchForm).reduce((count, key) => (state.searchForm[key] === "" ? count : count + 1), 0);
    const filterFormParamCount = Object.keys(state.filterForm).reduce((count, key) => (state.filterForm[key] === "" ? count : count + 1), 0);

    if (
      Object.keys(state.tableForm)?.length > 0 &&
      (searchFormParamCount >= apiDetails.minParametersForSearchForm || filterFormParamCount >= apiDetails.minParametersForFilterForm)
    ) {
      setEnable(true);
    }

    if (configs?.type === "inbox") setEnable(true);
  }, [state]);

  let requestCriteria = {
    url: configs?.apiDetails?.serviceName,
    params: configs?.apiDetails?.requestParam,
    body: configs?.apiDetails?.requestBody,
    config: {
      enabled: enable,
    },
  };

  const updatedReqCriteria = Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.preProcess
    ? Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.preProcess(requestCriteria)
    : requestCriteria;

  const { isLoading, data, revalidate, isFetching } = Digit.Hooks.useCustomAPIHook(updatedReqCriteria);

  useEffect(() => {
    return () => {
      revalidate();
      setEnable(false);
    };
  });

  useEffect(() => {
    if (type) setPopup(true);
  }, [type]);

  const handlePopupClose = () => {
    setPopup(false);
    setType("");
    setSortParams(sortParams);
  };

  return (
    <MobileInboxContext.Provider value={{ state, dispatch }}>
      <div className="inbox-search-component-wrapper">
        <div className={`sections-parent ${configs?.type}`}>
          {configs?.sections?.links?.show && (
            <div className="section links">
              <InboxSearchLinks
                headerText={configs?.sections?.links?.uiConfig?.label}
                links={configs?.sections?.links?.uiConfig?.links}
                businessService="WORKS"
                logoIcon={configs?.sections?.links?.uiConfig?.logoIcon}
              ></InboxSearchLinks>
            </div>
          )}
          {
            <div className="searchBox">
              {
                configs?.sections?.search?.show && (
                  <SearchAction 
                  text="SEARCH" 
                  handleActionClick={() => {
                    setType("SEARCH");
                    setPopup(true);
                  }}
                  />
              )}
              {configs?.sections?.filter?.show && (
                <FilterAction
                  text="FILTER"
                    handleActionClick={() => {
                      setType("FILTER");
                      setPopup(true);
                    }}
                />
              )}
            </div>
          }
          {configs?.sections?.searchResult?.show && (
            <div
              className=""
              style={
                data?.[configs?.sections?.searchResult?.uiConfig?.resultsJsonPath]?.length > 0
                  ? !(isLoading || isFetching)
                    ? { overflowX: "scroll" }
                    : {}
                  : {}
              }
            >
              {/* <ResultsTable 
                                config={configs?.sections?.searchResult?.uiConfig} 
                                data={data} 
                                isLoading={isLoading} 
                                isFetching={isFetching} 
                                fullConfig={configs}/> */}
            </div>
          )}
          {popup && (
            <PopUp>
              {type === "FILTER" && (
                <div className="popup-module">
                    <MobileFilterComponent
                    uiConfig={ configs?.sections?.filter?.uiConfig} 
                    header={configs?.sections?.filter?.label} 
                    screenType={configs.type}
                    fullConfig={configs}
                    data={data}
                    onClose={handlePopupClose}
                    />
                  {/* {<FilterComp onFilterChange={onSearchFilter} Close={handlePopupClose} type="mobile" searchParams={searchParams} />} */}
                </div>
              )}
              {type === "SEARCH" && (
                <div className="popup-module">
                    <MobileSearchComponent
                    uiConfig={ configs?.sections?.search?.uiConfig} 
                    header={configs?.sections?.search?.label} 
                    screenType={configs.type}
                    fullConfig={configs}
                    data={data}
                    onClose={handlePopupClose}
                    />
                </div>
              )}
            </PopUp>
          )}
        </div>
      </div>
    </MobileInboxContext.Provider>
  );
};

export default MobileView;
