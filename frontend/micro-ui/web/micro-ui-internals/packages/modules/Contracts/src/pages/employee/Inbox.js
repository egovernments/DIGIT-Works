import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";
import DesktopInbox from "../../components/DesktopInbox";
import MobileInbox from "../../components/MobileInbox";
import { useForm, Controller } from "react-hook-form";

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
  const [sortParams, setSortParams] = useState(initialStates?.sortParams || [{ id: "applicationDate", desc: false }]);
  const [setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState] = useState(false);
  const [searchParams, setSearchParams] = useState(() => {
    return initialStates?.searchParams || {
      offset: 0,
      limit: 10,
      sortBy: "department",
      sortOrder: "DESC",
    }
  });
  const [payload, setPayload] = useState({offset: 0,
    limit: 10,
    sortBy: "department",
    sortOrder: "DESC",});
  const { register, control, handleSubmit, setValue, getValues } = useForm({
    defaultValues: {
      offset: 0, 
      limit: 10,
      sortBy: "department",
      sortOrder: "DESC",
    },
  });

  let isMobile = window.Digit.Utils.browser.isMobile();
  let paginationParams = isMobile
    ? { limit: 100, offset: 0, sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" }
    : { limit: 100, offset: getValues("offset"), sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" };

  const config = {
    enabled: !!(payload && Object.keys(payload).length > 0),
  };
  
  //API Call useEstimateInbox
 const { isFetching, isLoading, data, ...rest }=Digit.Hooks.works.useInbox({
    tenantId,
    _filters: payload,
    config: config,
  });

  useEffect(() => {
    register("offset", 0);
    register("limit", 10);
    register("sortBy", "department");
    register("sortOrder", "DESC");
  }, [register]);

  const onSort = useCallback((args) => {
    if (args.length === 0) return;
    setValue("sortBy", args.id);
    setValue("sortOrder", args.desc ? "DESC" : "ASC");
  }, []);

  const onPageSizeChange=(e)=> {
    setValue("limit", Number(e.target.value));
    handleSubmit(handleFilterChange)();
  }

  const nextPage=()=> {
    setValue("offset", getValues("offset") + getValues("limit"));
    handleSubmit(handleFilterChange)();
  }
  const previousPage=()=> {
    setValue("offset", getValues("offset") - getValues("limit"));
    handleSubmit(handleFilterChange)();
  }

  const handleFilterChange = (filterParam) => {
    let _new = { ...searchParams, ...filterParam };

    setPayload(
      Object.keys(_new)
      .filter((k) => _new[k])
      .reduce((acc, key) => ({ ...acc, [key]: typeof _new[key] === "object" ? _new[key].code : _new[key] }), {})
      );

    setSetSearchFieldsBackToOriginalState(true);
    setSearchParams({ ..._new });
    setEnableSearch({ enabled: true });
  };
  const result1 = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });
  const result = {
    status: "success",
    isSuccess: true,
    totalCount: 10,
    isLoading: false,
    data:{
      contracts: [{
        tenantId:"pb.amritsar",
        contractId: "1136/TO/DB/FLOOD/10-11",
        contractDate: "08/09/2010",
        contractType: "Work Order",
        nameOfTheWork: "Providing CC Drain in Birla Gaddah (Tungabhaqdra workers colony) in 27th ward",
        abstractEstimateNumber: "EST/KRPN/1136",
        implementingAuthority: "Organisation",
        orgnName: "Maa Bhagavati SHG",
        officerIncharge: "S.A Bhasha",
        agreemntAmount: "35,53,600.00",
        sla:"15 days",
        status:"Approved"
      }]
    }
  }
  const handleSort = useCallback((args) => {
    if (args.length === 0) return;
    setSortParams(args);
  }, []);
  const { isLoading: hookLoading, isError, error, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
    null,
    tenantId,
    paginationParams,
    null
);
  
  const getData = () => {
    if (result.data.contracts?.length == 0 ) {
      return { display: "ES_COMMON_NO_DATA" }
    } else if (result?.data.contracts?.length > 0) {
      return result?.data.contracts
    } else {
      return [];
    }
  }

  const isResultsOk = () => {
    return result?.data.contracts?.length > 0 ? true : false;
  }
  

  if (isMobile) {
    return (
      <MobileInbox
        data={getData()}
        isLoading={result1?.isLoading}
        isSearch={!isInbox}
        // searchFields={searchFields}
        onFilterChange={handleFilterChange}
        onSearch={handleFilterChange}
        onSort={onSort}
        parentRoute={parentRoute}
        searchParams={searchParams}
        sortParams={sortParams}
        linkPrefix={`${parentRoute}/view-contract/`}
        // tableConfig={rest?.tableConfig?res?.tableConfig:TableConfig(t)["PT"]}
        filterComponent={filterComponent}
      />
    );
  } else {
    return (
      <div>
        {isInbox && <Header>{t("WORKS_CONTRACTS_INBOX")}{result?.totalCount ? <p className="inbox-count">{result?.totalCount}</p> : null}</Header>}

        <DesktopInbox
          businessService={businessService}
          data={getData()}
          // tableConfig={tableConfig}
          resultOk={isResultsOk()}
          isLoading={result1?.isLoading}
          defaultSearchParams={initialStates.searchParams}
          isSearch={!isInbox}
          onFilterChange={handleFilterChange}
          // searchFields={getSearchFields()}
          setSearchFieldsBackToOriginalState={setSearchFieldsBackToOriginalState}
          setSetSearchFieldsBackToOriginalState={setSetSearchFieldsBackToOriginalState}
          onSearch={handleFilterChange}
          onSort={onSort}
          onNextPage={nextPage}
          onPrevPage={previousPage}
          currentPage={getValues("offset") / getValues("limit")}
          pageSizeLimit={getValues("limit")}
          disableSort={false}
          onPageSizeChange={onPageSizeChange}
          parentRoute={parentRoute}
          searchParams={searchParams}
          sortParams={sortParams}
          // totalRecords={Number(data?.totalCount)}
          totalRecords={1}
          filterComponent={filterComponent}
        />
      </div>
    );
  }
};

export default Inbox;