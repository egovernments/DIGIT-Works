import React, { useCallback, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { Header } from "@egovernments/digit-ui-react-components";
import LOIDesktopInbox from "../../components/LOIDesktopInbox";
import LOIMobileInbox from "../../components/LOIMobileInbox";
import { useForm } from "react-hook-form";

const LOIInbox = ({
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
    return initialStates?.searchParams || {
      offset: 0,
      limit: 10,
      sortBy: "department",
      sortOrder: "DESC",
    }
  });
  const [payload, setPayload] = useState({
    offset: 0,
      limit: 10,
      sortBy: "department",
      sortOrder: "DESC",
  });

  let isMobile = window.Digit.Utils.browser.isMobile();
  
  const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
    defaultValues: {
      offset: 0, 
      limit: 10,
      sortBy: "department",
      sortOrder: "DESC",
    },
  });
  let paginationParams = isMobile
  ? { limit: 100, offset: 0, sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" }
  : { limit: 100, offset: getValues("offset"), sortBy: sortParams?.[0]?.id, sortOrder: sortParams?.[0]?.desc ? "DESC" : "ASC" };

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
    // if (keys_to_delete) keys_to_delete.forEach((key) => delete _new[key]);
    // delete filterParam.delete;
    // if (keys_to_delete) keys_to_delete.forEach((key) => delete _new[key]);
    // delete _new?.delete;
    // delete filterParam?.delete;
    
    setPayload(
      Object.keys(_new)
      .filter((k) => _new[k])
      .reduce((acc, key) => ({ ...acc, [key]: typeof _new[key] === "object" ? _new[key].code : _new[key] }), {})
      );

    setSetSearchFieldsBackToOriginalState(true);
    setSearchParams({ ..._new });
    setEnableSearch({ enabled: true });
  };

  const config = {
    enabled: !!(payload && Object.keys(payload).length > 0),
  };

  //API Call useEstimateInbox
  const result1 = Digit.Hooks.works.useSearchWORKS({ tenantId, filters: payload, config });
  const result = {
    status: "success",
    totalCount:10,
    isSuccess: true,
    isLoading: false,
    data:{
      estimates: [{ LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days" },
      { LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days", },
      { LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days", },
      { LOIId: "1136/TO/DB/FLOOD/10-11", LOIDate: "08/09/2010", EstimateNumber: "EST/KRPN/1136", NameOfWork: "Providing CC Drain in Birla Gaddah", ContractorName: "S.A.Basha", AgrementAmount: "3553600.00", SLA: "15Days", }]    
    }
  }

  const { isLoading: hookLoading, isError, error, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
    null,
    tenantId,
    paginationParams,
    null
);

  const getData = () => {
    if (result?.data?.estimates?.length == 0 ) {
      return { display: "ES_COMMON_NO_DATA" }
    } else if (result?.data?.estimates?.length > 0) {
      return result?.data?.estimates
    } else {
      return [];
    }
  }

  const isResultsOk = () => {
    return result?.data?.estimates?.length > 0 ? true : false;
  }

  if (isMobile) {
    return (
      <LOIMobileInbox
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
      linkPrefix={`${parentRoute}/application-details/`}
      // tableConfig={rest?.tableConfig?res?.tableConfig:TableConfig(t)["PT"]}
      filterComponent={filterComponent}
      />
    );
  } else {
    return (
      <div>
        {isInbox && <Header>{t("WORKS_LOI_INBOX")}{result?.totalCount ? <p className="inbox-count">{result?.totalCount}</p> : null}</Header>}

        <LOIDesktopInbox
          businessService={businessService}
          data={getData()}
          // tableConfig={tableConfig}
          resultOk={isResultsOk}
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
          totalRecords={Number(result?.data?.totalCount)}
          filterComponent={filterComponent}
        />
      </div>
    );
  }
  //   }
};

export default LOIInbox;