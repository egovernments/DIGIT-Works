import React, { Fragment, useEffect, useCallback, useMemo, useState } from "react";
import { SearchForm, Table, Card, Loader, Header } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFields";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import MobileSearchApplication from "./MobileSearchEstimates";

const SearchEstimateApplication = ({tenantId, onSubmit, data, resultOk, isLoading, onClearSearch, showTable, count}) => {

    const { t } = useTranslation(); 
    const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
      defaultValues: {
        offset: 0,
        limit: 10,
        // sortBy: "department",
        sortOrder: "DESC",
      },
    });
  
    useEffect(() => {
      register("offset", 0);
      register("limit", 10);
      // register("sortBy", "department");
      register("sortOrder", "DESC");
    }, [register]);
  
    const onSort = useCallback((args) => {
      if (args.length === 0) return;
      setValue("sortBy", args.id);
      // setValue("sortOrder", args.desc ? "DESC" : "ASC");
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
          Header: t("WORKS_ESTIMATE_NO"),
          disableSortBy: true,
          accessor: "connectionNo",
          Cell: ({ row }) =>{
            let service = "WORKS";
            return( 
              <div>
                {row.original?.estimateNumber ? (
                  <span className={"link"}>
                    <Link
                      to={`view-estimate?tenantId=${row.original?.tenantId}&estimateNumber=${row.original?.estimateNumber}&department=${row.original?.department}`}>
                      {row.original?.estimateNumber || t("ES_COMMON_NA")}
                    </Link>
                  </span> 
                ) : (
                  <span>{t("NA")}</span>
                )}
              </div>)}
        },
        {
          Header: t("WORKS_DEPARTMENT"),
          disableSortBy: true,
          accessor: (row) => GetCell(t(`ES_COMMON_${row?.department}`) || t("ES_COMMON_NA")),
        },
        // {
        //   Header: t("WORKS_ADMIN_SANCTION_NUMBER"),
        //   disableSortBy: true,
        //   accessor: (row) => GetCell(row?.adminSanctionNumber || t("ES_COMMON_NA")),
        // },
        {
          Header: t("WORKS_FUND"),
          disableSortBy: true,
          accessor: (row) => GetCell(t(`ES_COMMON_FUND_${row?.fund}`) || t("ES_COMMON_NA")),
        },
        {
          Header: t("WORKS_FUNCTION"),
          disableSortBy: true,
          accessor: (row) => GetCell(t(`ES_COMMON_${row?.function}`) || t("ES_COMMON_NA")),
        },
        {
          Header: t("WORKS_BUDGET_HEAD"),
          disableSortBy: true,
          accessor: (row) => GetCell(t(`ES_COMMON_${row?.budgetHead}`) || t("ES_COMMON_NA")),
        },        
        {
          Header: t("WORKS_CREATED_BY"),
          disableSortBy: true,
          accessor: (row) => GetCell(row?.createdBy || t("ES_COMMON_NA")),
        },
        {
          Header: t("WORKS_OWNER"),
          disableSortBy: true,
          accessor: (row) => GetCell(row?.owner || t("ES_COMMON_NA")),
        },
        {
          Header: t("WORKS_STATUS"),
          disableSortBy: true,
          accessor: (row) => GetCell(t(`ES_COMMON_${row?.estimateStatus}`) || t("ES_COMMON_NA")) ,
        },
        {
          Header: t("WORKS_TOTAL_AMOUNT"),
          disableSortBy: true,
          accessor: (row) => GetCell(row?.totalAmount || t("ES_COMMON_NA")),
        },
      ],
      []
    );
    const isMobile = window.Digit.Utils.browser.isMobile();
    if (isMobile) {
        return <MobileSearchApplication {...{ Controller, register, control, t, reset, previousPage, handleSubmit, tenantId, data, onSubmit }} />;
    }
   
  return (
    <>
          <Header styles={{ fontSize: "32px" }}>{t("WORKS_SEARCH_ESTIMATES")}</Header>
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
                totalRecords={count}
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

export default SearchEstimateApplication