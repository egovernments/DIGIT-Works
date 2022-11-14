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
          accessor: (row) => (GetCell(row?.contractDate || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_CONTRACT_TYPE"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row.contractType || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_NAME_OF_WORK"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row.nameOfTheWork || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_ABSTRACT_ESTIMATE_NO"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row.abstractEstimateNumber || t("ES_COMMON_NA"))),
        },        
        {
          Header: t("WORKS_IMPLEMENT_AUTH"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.implementingAuthority || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_NAME_OF_ORGN"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.orgnName || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_OFFICER_INCHARGE_NAME"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.officerIncharge || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_AGREEMENT_AMT"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.agreemntAmount || t("ES_COMMON_NA"))),
        },
        {
          Header: t("WORKS_STATUS"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.status || t("ES_COMMON_NA"))),
        }
    ],[]);

    const isMobile = window.Digit.Utils.browser.isMobile();
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
            <div style={{overflowX:"scroll"}}>
              <Table
                t={t}
                data={data}
                // totalRecords={count}
                columns={columns}
                getCellProps={(cellInfo) => {
                  return {
                    style: {
                      minWidth: cellInfo.column.Header === t("WORKS_INBOX_APPLICATION_NO") ? "240px" : "",
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