import React, { Fragment, useEffect, useCallback, useMemo, useState } from "react";
import { SearchForm, Table, Card, Loader, Header } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import MobileSearchApplication from "./MobileSearchEstimates";

const SearchContractApplication = ({tenantId, onSubmit, data, resultOk, isLoading, onClearSearch,showTable}) => {

    const { t } = useTranslation(); 
    const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
      defaultValues: {
        offset: 0,
        limit: 10,
        sortOrder: "DESC",
      },
    });
    let isMobile = window.Digit.Utils.browser.isMobile();
  let paginationParams = isMobile
    ? { limit: 100, offset: 0, sortOrder: "DESC"}
    : { limit: 100, offset: getValues("offset"), sortOrder: "DESC"};

    const { isLoading: hookLoading, isError, error, data:employeeData } = Digit.Hooks.hrms.useHRMSSearch(
        null,tenantId,paginationParams
      );
  
    useEffect(() => {
      register("offset", 0);
      register("limit", 10);
      register("sortOrder", "DESC");
    }, [register]);
  
    const onSort = useCallback((args) => {
      if (args.length === 0) return;
      setValue("sortOrder", args.desc ? "DESC" : "ASC");
    }, []);

    function onPageSizeChange(e) {
      setValue("limit", Number(e.target.value));
      handleSubmit(onSubmit)();
    }

    function nextPage() {
      setValue("offset", getValues("offset") + getValues("limit"));
      handleSubmit(onSubmit)();
    }
    function previousPage() {
      setValue("offset", getValues("offset") - getValues("limit"));
      handleSubmit(onSubmit)();
    }

    const GetCell = (value) => <span className="cell-text">{value}</span>;
    const columns = useMemo( 
      () => [
        {
          Header: t("WORKS_CONTRACT_ID"),
          disableSortBy: true,
          accessor: "connectionNo",
          Cell: ({ row }) =>{
            return( 
              <div>
                {row.original?.contractId ? (
                  <span className={"link"}>
                    <Link
                      to={`view-contract?tenantId=${row.original?.tenantId}&contractId=${row.original?.contractId}`}>
                      {row.original?.contractId || t("ES_COMMON_NA")}
                    </Link>
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_CONTRACT_DATE"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.contractDate ? (
                  <span >
                      {row.original?.contractDate || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_CONTRACT_TYPE"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.contractType ? (
                  <span >
                      {row.original?.contractType || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_NAME_OF_WORK"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"382px"}}>
                {row.original?.nameOfTheWork ? (
                  <span >
                      {row.original?.nameOfTheWork || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_ABSTRACT_ESTIMATE_NO"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"160px"}}>
                {row.original?.abstractEstimateNumber ? (
                  <span >
                      {row.original?.abstractEstimateNumber || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },        
        {
          Header: t("WORKS_IMPLEMENT_AUTH"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"130px"}}>
                {row.original?.implementingAuthority ? (
                  <span >
                      {row.original?.implementingAuthority || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_ORG_NAME"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"150px"}}>
                {row.original?.orgnName ? (
                  <span >
                      {row.original?.orgnName || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_OFF_IN_CHARGE"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"150px"}}>
                {row.original?.officerIncharge ? (
                  <span >
                      {row.original?.officerIncharge || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_AGREEMENT_AMT"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"220px"}}>
                {row.original?.agreemntAmount ? (
                  <span >
                      {row.original?.agreemntAmount || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_STATUS"),
          disableSortBy: true,
          // accessor: (row) => (GetCell(row?.status || t("ES_COMMON_NA"))),
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"90px"}}>
                {row.original?.status ? (
                  <span >
                      {row.original?.status || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        }
    ],[]);


    if (isMobile) {
        return <MobileSearchApplication {...{ Controller, register, control, t, reset, previousPage, handleSubmit, tenantId, data, onSubmit }} />;
    }
   
  return (
    <>
          <Header styles={{ fontSize: "32px" }}>{t("WORKS_SEARCH_CONTRACTS")}</Header>
          <SearchForm onSubmit={onSubmit} handleSubmit={handleSubmit} >
              <SearchFields {...{ register, control, reset, t, onClearSearch }} />
          </SearchForm>
          {showTable ? isLoading ? <Loader/> : data?.display && !resultOk ? (
              <Card style={{ marginTop: 20 }}>
                {t(data?.display)
                  .split("\\n")
                  .map((text, index) => (
                    <p key={index} style={{ textAlign: "center" }}>
                      {text}
                    </p>
                  ))}
              </Card>
            ) : resultOk ? (
            <div>
              <Table
                t={t}
                data={data}
                customTableWrapperClassName="table-wrapper attendence-table"
                totalRecords={1}
                columns={columns}
                getCellProps={(cellInfo) => {
                  return {
                    style: {
                      minWidth: cellInfo.column.Header === t("WORKS_CONTRACT_ID") ? "180px" : "",
                      padding: "20px 18px",
                      fontSize: "16px"
                    },
                  };
                }}
                onPageSizeChange={onPageSizeChange}
                currentPage={getValues("offset") / getValues("limit")}
                onNextPage={nextPage}
                onPrevPage={previousPage}
                pageSizeLimit={getValues("limit")}
                onSort={onSort}
                disableSort={false}
                sortParams={[{ id: getValues("sortBy"), desc: getValues("sortOrder") === "DESC" ? true : false }]}
              />
           </div>): null : null
        }

    </>
  )
}

export default SearchContractApplication