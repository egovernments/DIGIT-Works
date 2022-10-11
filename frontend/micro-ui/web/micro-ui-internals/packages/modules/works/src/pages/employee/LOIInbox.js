import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";

import LOIDesktopInbox from "../../components/LOIDesktopInbox";
import LOIMobileInbox from "../../components/LOIMobileInbox";

const LOIInbox = ({
  parentRoute,
  businessService = "LOI",
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

  const { isFetching, isLoading: hookLoading, searchResponseKey, searchFields, ...rest } = Digit.Hooks.tl.useInbox({
    tenantId,
    filters: { ...searchParams, ...paginationParams, sortParams },
    config: {},
  });
  const data = [{ LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days" },
  { LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days", },
  { LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days", },
  { LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days", }]

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
    let keys_to_delete = filterParam?.delete;
    let _new = {};
    if (isMobile) {
      _new = { ...filterParam };
    } else {
      _new = { ...searchParams, ...filterParam };
    }
    // let _new = { ...searchParams, ...filterParam };
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
  const getSearchFields = () => {
    return [
      {
        label: t("TL_HOME_SEARCH_RESULTS_APP_NO_LABEL"),
        name: "applicationNumber",
      },
      {
        label: t("CORE_COMMON_MOBILE_NUMBER"),
        name: "mobileNumber",
        maxlength: 10,

        pattern: Digit.Utils.getPattern("MobileNo"),

        type: "mobileNumber",

        title: t("ES_SEARCH_APPLICATION_MOBILE_INVALID"),
        componentInFront: "+91",
      },
    ];
  };

  if (isMobile) {
    return (
      <LOIMobileInbox
        data={data}
        isLoading={hookLoading}
        searchFields={getSearchFields()}
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
        {isInbox && <Header>{t("WORKS_LOI_INBOX")}{data?.totalCount ? <p className="inbox-count">{data?.totalCount}</p> : null}</Header>}

        <LOIDesktopInbox
          businessService={businessService}
          data={data}
          tableConfig={rest?.tableConfig}
          isLoading={hookLoading}
          defaultSearchParams={initialStates.searchParams}
          isSearch={!isInbox}
          onFilterChange={handleFilterChange}
          searchFields={getSearchFields()}
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
  //   }
};

export default LOIInbox;