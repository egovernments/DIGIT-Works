import React, { useEffect, useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { Card, DetailsCard, Loader, PopUp, SearchAction } from "@egovernments/digit-ui-react-components";
import { FilterAction } from "@egovernments/digit-ui-react-components";
import SearchApplication from "./search";
import SortBy from "./SortBy";

export const ApplicationCard = ({
  t,
  data,
  onFilterChange,
  onSearch,
  onSort,
  isFstpOperator,
  isLoading,
  isSearch,
  searchParams,
  searchFields,
  sortParams,
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
        [t("WORKS_ESTIMATE_NO")]: (
            <div>
                <span className="link">
                    <Link to={`view-estimate?tenantId=${row?.tenantId}&estimateNumber=${row?.estimateNumber}`}>
                        {row?.estimateNumber || t("ES_COMMON_NA")}
                    </Link>
                </span>
            </div> 
        ),
        [t("WORKS_DEPARTMENT")]:t(`ES_COMMON_${row?.department}`) || t("ES_COMMON_NA"),
        [t("WORKS_ADMIN_SANCTION_NUMBER")]: row.adminSanctionNumber || t("ES_COMMON_NA"),
        [t("WORKS_FUND")]: t(`ES_COMMON_FUND_${row?.fund}`) || t("ES_COMMON_NA"),
        [t("WORKS_FUNCTION")]: t(`ES_COMMON_${row?.function}`) || t("ES_COMMON_NA"),
        [t("WORKS_BUDGET_HEAD")]: t(`ES_COMMON_${row?.budgetHead}`) || t("ES_COMMON_NA"),
        [t("WORKS_CREATED_BY")]: row?.createdBy || t("ES_COMMON_NA"),
        [t("WORKS_OWNER")]: row?.owner || t("ES_COMMON_NA"),
        [t("WORKS_STATUS")]: row?.status || t("ES_COMMON_NA"),
        [t("WORKS_TOTAL_AMOUNT")]: row?.totalAmount || t("ES_COMMON_NA"),
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
          {type === "SORT" && (
            <div className="popup-module">
              {<SortBy type="mobile" sortParams={sortParams} onClose={handlePopupClose} onSort={onSort} />}
            </div>
          )}
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