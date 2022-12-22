import React, { Fragment, useEffect, useCallback, useMemo, useState } from "react";
import { SearchForm, Table, Card, Loader, Header } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";

const SearchOrganisationApplication = ({tenantId, onSubmit, data, resultOk, isLoading, onClearSearch,showTable}) => {

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
          Header: t("MASTERS_ORG_ID"),
          disableSortBy: true,
          accessor: "org_id",
          Cell: ({ row }) =>{
            return( 
              <div> 
                {row.original?.org_id ? (
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
          Header: t("MASTERS_NAME_OF_THE_ORG"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.nameOfTheOrg ? (
                  <span >
                      {row.original?.nameOfTheOrg || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("MASTERS_TYPE_OF_THE_ORG"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.nameOfTheOrg ? (
                  <span >
                      {row.original?.nameOfTheOrg || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("MASTERS_ORG_CATEGORY"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.nameOfTheOrg ? (
                  <span >
                      {row.original?.nameOfTheOrg || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("MASTERS_NO_OF_MEMBERS"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.nameOfTheOrg ? (
                  <span >
                      {row.original?.nameOfTheOrg || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("MASTERS_DISTRICT"),
          disableSortBy: true,
          Cell: ({ row }) =>{
            return( 
              <div style={{"minWidth":"110px"}}>
                {row.original?.nameOfTheOrg ? (
                  <span >
                      {row.original?.nameOfTheOrg || t("ES_COMMON_NA")}
                  </span> 
                ) : (
                  <span>{t("ES_COMMON_NA")}</span>
                )}
              </div>)}
        }
    ],[]);

  return (
    <>
          <Header styles={{ fontSize: "32px" }}>{t("MASTERS_SEARCH_ORG")}</Header>
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
                      minWidth: cellInfo.column.Header === t("WORKS_ORG_ID") ? "180px" : "",
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

export default SearchOrganisationApplication