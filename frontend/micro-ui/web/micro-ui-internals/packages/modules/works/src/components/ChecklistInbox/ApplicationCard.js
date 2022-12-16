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
      [t("WORKS_ORDER_ID")]: (
          <div>
            {row?.estimateNumber ? (
              <span className={"link"}>
                <Link
                  to={`view-estimate?tenantId=${row?.tenantId}&estimateNumber=${row?.workOrderId}`}>
                  {row?.workOrderId || "NA"}
                </Link>
              </span>
            ) : (
              <span>{t("NA")}</span>
            )}
          </div>
        ),
        [t("WORKS_WO_ISSUED_DATE")]: row.woIssuedDate,
        [t("WORKS_WO_ACCEPTED_DATE")]: row.woAcceptedDate,
        [t("WORKS_NAME_OF_WORK")]: row.nameOfWork,
        [t("WORKS_AGENCY_NAME")]: row.agencyName,
        [t("WORKS_STATUS")]: row.status,
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