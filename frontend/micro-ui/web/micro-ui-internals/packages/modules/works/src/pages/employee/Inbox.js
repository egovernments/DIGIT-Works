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
  const [payload, setPayload] = useState({offset: 0,
    limit: 10,
    sortBy: "department",
    sortOrder: "DESC",});
  const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
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

  // const { isFetching, isLoading: hookLoading, searchResponseKey, searchFields, ...rest } = Digit.Hooks.works.useInbox({
  //   tenantId,
  //   filters: { ...searchParams, ...paginationParams, sortParams },
  //   config: {},
  // });

  useEffect(() => {
    register("offset", 0);
    register("limit", 10);
    register("sortBy", "department");
    register("sortOrder", "DESC");
  }, [register]);


  const data = [{
    EstimateNumber: "LE/ENG/00002/10/2017-18",
    Department: "ENGINEERING",
    Fund: "Municipal Fund",
    Function: "Water Supply",
    BudgetHead: "Water Purification",
    CreatedBy: "A.P.Sreenivasulu",
    Owner: "A.P.Sreenivasulu",
    Status: "Craeted",
    TotalAmount: "Rs,10000",
    totalCount: 10
  }]

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
    isSuccess: true,
    isLoading: false,
    data:{
      estimates: [{
        tenantId:"pb.amritsar",
        estimateNumber: "EP/2022-23/10/000102",
        department: "DEPT_1",
        fund: "01",
        function: "0001",
        budgetHead: "01",
        createdBy: "A.P.Sreenivasulu",
        owner: "A.P.Sreenivasulu",
        estimateStatus: "APPROVED",
        totalAmount: "Rs,10000",
        auditDetails:{
          createdBy:"0f603b14-10dc-450f-976a-64aae4160907",
          lastModifiedBy:"0f603b14-10dc-450f-976a-64aae4160907"
        }
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
  // const handlePageSizeChange = (e) => {
  //   setPageSize(Number(e.target.value));
  // };
  const getData = () => {
    if (result?.data?.estimates?.length == 0 ) {
      return { display: "ES_COMMON_NO_DATA" }
    } else if (result?.data?.estimates?.length > 0) {
      let newResult = [];
        result?.data?.estimates?.map((val)=>{
          let totalAmount = 0
              val?.estimateDetails?.map((amt)=>{
              totalAmount = totalAmount + amt?.amount
            })
            employeeData?.Employees?.map((item)=>{
              if(val?.auditDetails?.lastModifiedBy === item?.uuid){
                Object.assign(val,{"owner":item?.user?.name})
              }
              if(val?.auditDetails?.createdBy === item?.uuid){
                newResult.push(Object.assign(val,{"createdBy":item?.user?.name,"totalAmount":totalAmount}))
              }
          })
          // newResult = newResult.filter((val)=>val?.fund === payload?.fund)
        })
      return newResult
    } else {
      return [];
    }
  }

  const isResultsOk = () => {
    return result?.data?.estimates?.length > 0 ? true : false;
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
        linkPrefix={`${parentRoute}/view-estimate/`}
        // tableConfig={rest?.tableConfig?res?.tableConfig:TableConfig(t)["PT"]}
        filterComponent={filterComponent}
      />
    );
  } else {
    return (
      <div>
        {isInbox && <Header>{t("WORKS_ESTIMATE_INBOX")}{data?.totalCount ? <p className="inbox-count">{data?.totalCount}</p> : null}</Header>}

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
          totalRecords={Number(data?.totalCount)}
          filterComponent={filterComponent}
        />
      </div>
    );
  }
};

export default Inbox;