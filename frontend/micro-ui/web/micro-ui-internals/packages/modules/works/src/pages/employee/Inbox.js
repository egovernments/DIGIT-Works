import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";
import DesktopInbox from "../../components/DesktopInbox";
import MobileInbox from "../../components/MobileInbox";

const Inbox = ({
  parentRoute,
  businessService = "WORKS",
  initialStates = {},
  filterComponent,
  isInbox
}) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const [enableSarch, setEnableSearch] = useState(() => (isInbox ? {} : { enabled: false }));
  const [pageOffset, setPageOffset] = useState(initialStates?.pageOffset || 0);
  const [pageSize, setPageSize] = useState(initialStates?.pageSize || 10);
  const [sortParams, setSortParams] = useState(initialStates?.sortParams || [{ id: "applicationDate", desc: false }]);
  const [setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState] = useState(false);
  const [searchParams, setSearchParams] = useState(() => {
    return initialStates?.searchParams || {};
  });
  let isMobile = window.Digit.Utils.browser.isMobile();
  let paginationParams = isMobile
    ? { limit: 100, offset: 0, sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" }
    : { limit: pageSize, offset: pageOffset, sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" };

  // const { isFetching, isLoading: hookLoading, searchResponseKey, searchFields, ...rest } = Digit.Hooks.works.useInbox({
  //   tenantId,
  //   filters: { ...searchParams, ...paginationParams, sortParams },
  //   config: {},
  // });
  const data = [{
    EstimateNumber: "LE/ENG/00002/10/2017-18",
    Department: "ENGINEERING",
    Fund: "Municipal Fund",
    Function: "Water Supply",
    BudgetHead: "Water Purification",
    CreatedBy: "A.P.Sreenivasulu",
    Owner: "A.P.Sreenivasulu",
    Status: "Craeted",
    TotalAmount: "Rs,10000"
  }]

  useEffect(() => {
    setPageOffset(0);
  }, [searchParams]);

  const fetchNextPage = () => {
    setPageOffset((prevState) => prevState + pageSize);
  };

  const fetchPrevPage = () => {
    setPageOffset((prevState) => prevState - pageSize);
  };

  const handleFilterChange = (filterParam) => {
    let _new = { ...searchParams, ...filterParam };
    // if (keys_to_delete) keys_to_delete.forEach((key) => delete _new[key]);
    // delete filterParam.delete;
    if (keys_to_delete) keys_to_delete.forEach((key) => delete _new[key]);
    delete _new?.delete;
    delete filterParam?.delete;
    setSetSearchFieldsBackToOriginalState(true);
    setSearchParams({ ..._new });
    setEnableSearch({ enabled: true });
  };
  const handleSort = useCallback((args) => {
    if (args.length === 0) return;
    setSortParams(args);
  }, []);

  const handlePageSizeChange = (e) => {
    setPageSize(Number(e.target.value));
  };

  if (isMobile) {
    return (
      <MobileInbox
        data={data}
        // isLoading={hookLoading}
        // searchFields={getSearchFields()}
        onFilterChange={handleFilterChange}
        onSearch={handleFilterChange}
        onSort={handleSort}
        parentRoute={parentRoute}
        searchParams={searchParams}
        sortParams={sortParams}
      />
    );
  } else {
    return (
      <div>
        {isInbox && <Header>{t("ES_COMMON_INBOX")}{data?.totalCount ? <p className="inbox-count">{data?.totalCount}</p> : null}</Header>}

        <DesktopInbox
          businessService={businessService}
          data={data}
          // tableConfig={tableConfig}
          // isLoading={hookLoading}
          defaultSearchParams={initialStates.searchParams}
          isSearch={!isInbox}
          onFilterChange={handleFilterChange}
          // searchFields={getSearchFields()}
          setSearchFieldsBackToOriginalState={setSearchFieldsBackToOriginalState}
          setSetSearchFieldsBackToOriginalState={setSetSearchFieldsBackToOriginalState}
          onSearch={handleFilterChange}
          onSort={handleSort}
          onNextPage={fetchNextPage}
          onPrevPage={fetchPrevPage}
          currentPage={Math.floor(pageOffset / pageSize)}
          pageSizeLimit={pageSize}
          disableSort={false}
          onPageSizeChange={handlePageSizeChange}
          parentRoute={parentRoute}
          searchParams={searchParams}
          sortParams={sortParams}
          totalRecords={Number(data?.totalCount)}
          filterComponent={filterComponent}
        />
      </div>
    );
  }
};

export default Inbox;