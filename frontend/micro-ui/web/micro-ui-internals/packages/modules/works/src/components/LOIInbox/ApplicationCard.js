import React, { useEffect, useState, useMemo } from "react";
import { Card, DetailsCard, Loader, PopUp, SearchAction } from "@egovernments/digit-ui-react-components";
import { FilterAction } from "@egovernments/digit-ui-react-components";
import SearchApplication from "./SearchApplication";
import { Link } from "react-router-dom";
export const ApplicationCard = ({
  t,
  data,
  onFilterChange,
  onSearch,
  onSort,
  serviceRequestIdKey,
  isFstpOperator,
  isLoading,
  isSearch,
  searchParams,
  searchFields,
  sortParams,
  linkPrefix,
  removeParam,
  filterComponent,
}) => {
  const [type, setType] = useState(isSearch ? "SEARCH" : "");
  const [popup, setPopup] = useState(isSearch ? true : false);
  const [_sortparams, setSortParams] = useState(sortParams);
  const [FilterComp] = useState(() => Digit.ComponentRegistryService?.getComponent(filterComponent));

  const onSearchFilter = (params) => {
    onFilterChange(params);
    setPopup(false);
  };

  useEffect(() => {
    if (type) setPopup(true);
  }, [type]);

  const propsMobileInboxCards = useMemo(() => {
    if (data?.display) {
        return [];
    }

    return data?.map((row) => ({
        [t("WORKS_LOI_ID")]: (
            <div>
                <span className="link">
                    <Link to={`view-loi/?loiNumber=LI/2022-23/10/000083&subEstimateNumber=EP/2022-23/09/000092/000068`}>
                        {row?.LOIId || "NA"}
                    </Link>
                </span>
            </div> 
        ),
        [t("WORKS_LOI_DATE")]: row.LOIDate || t("ES_COMMON_NA"),
        [t("WORKS_ABSTRACT_ESTIMATE_NO")]: row.EstimateNumber || t("ES_COMMON_NA"),
        [t("WORKS_NAME_OF_WORK")]: row.NameOfWork || t("ES_COMMON_NA"),
        [t("WORKS_CONTRACTOR_NAME")]: row.ContractorName || t("ES_COMMON_NA"),
        [t("WORKS_AGREEMENT_AMT")]: row?.AgrementAmount || t("ES_COMMON_NA"),
        [t("WORKS_SLA")]: row?.SLA || t("ES_COMMON_NA"),
    }));
}, [data]);
  let result;
  if (!data || data?.length === 0) {
    result = (
      <Card style={{ marginTop: 20 }}>
        {t("CS_MYAPPLICATIONS_NO_APPLICATION")
          .split("\\n")
          .map((text, index) => (
            <p key={index} style={{ textAlign: "center" }}>
              {text}
            </p>
          ))
        }
      </Card>
    );
  } 
  else if (data && data?.length > 0) {
    result =<DetailsCard
    {...{
      data: propsMobileInboxCards,
      linkPrefix: `/${window.contextPath}/employee/works/view-estimate`,
          serviceRequestIdKey: t("WORKS_ESTIMATE_NO"),
  }}
  />
  }
  
  const handlePopupClose = () => {
    setPopup(false);
    setType("");
    setSortParams(sortParams);
  };

  if (isLoading) {
    return <Loader />;
  }

  return (
    <React.Fragment>
      <div className="searchBox">
        {onSearch && (
          <SearchAction
            text="SEARCH"
            handleActionClick={() => {
              setType("SEARCH");
              setPopup(true);
            }}
          />
        )}
        {!isSearch && onFilterChange && (
          <FilterAction
            text="FILTER"
            handleActionClick={() => {
              setType("FILTER");
              setPopup(true);
            }}
          />
        )}
        {/* <FilterAction
          text="SORT"
          handleActionClick={() => {
            setType("SORT");
            setPopup(true);
          }}
        /> */}
      </div>
      {result}
      {popup && (
        <PopUp>
          {type === "FILTER" && (
            <div className="popup-module">
              {<FilterComp onFilterChange={onSearchFilter} Close={handlePopupClose} type="mobile" searchParams={searchParams} />}
            </div>
          )}
          {/* {type === "SORT" && (
            <div className="popup-module">
              {<SortBy type="mobile" sortParams={sortParams} onClose={handlePopupClose} onSort={onSort} />}
            </div>
          )} */}
          {type === "SEARCH" && (
            <div className="popup-module">
              <SearchApplication
                type="mobile"
                onClose={handlePopupClose}
                onSearch={onSearch}
                isFstpOperator={isFstpOperator}
                searchParams={searchParams}
                searchFields={searchFields}
              />
            </div>
          )}
        </PopUp>
      )}
    </React.Fragment>
  );
};