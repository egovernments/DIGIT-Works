import React, { Fragment, useEffect, useCallback, useMemo } from "react";
import { SearchForm, Table, Card, Loader, Header,EditIcon } from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";
import SearchFields from "./SearchFieldsApprovedEstimate";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import MobileSearchApplication from "./MobileSearchApplication";
const SearchApplication = ({ tenantId, onSubmit, count, resultOk, businessService }) => {

  const { t } = useTranslation();
  const { register, control, handleSubmit, setValue, getValues, reset } = useForm({
    defaultValues: {
      offset: 0,
      limit: 10,
      sortBy: "department",
      sortOrder: "DESC",
      // isConnectionSearch: true,
    },
  });
  const tenant = Digit.ULBService.getStateId();
  const { isLoading, data, isFetched } = Digit.Hooks.useCustomMDMS(
        tenant,
        "works",
        [
            {
                "name": "Department"
            }
        ]
        );
        if(data?.works){
          var { Department } = data?.works
        }

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

  const isMobile = window.Digit.Utils.browser.isMobile();

  if (isMobile) {
    return <MobileSearchApplication {...{ Controller, register, control, t, reset, previousPage, handleSubmit, tenantId, data, onSubmit }} />;
  }

  //need to get from workflow
  const GetCell = (value) => <span className="cell-text">{value}</span>;
  const columns = useMemo(
    () => [
      {
        Header: t("WORKS_SUB_ESTIMATE_NO"),
        disableSortBy: true,
        accessor: "connectionNo",
        Cell: ({ row }) =>{
          let service = "WORKS";
          return(
            <div>
              {row.original?.subEstimateNumber ? (
                <span className={"link"}>
                  <Link
                    to={`/digit-ui/employee/works/estimate-details?applicationNumber=${
                      row.original["connectionNo"]
                      }&tenantId=${tenantId}&service=${service}&due=${row.original?.due || 0}&from=WS_SEWERAGE_APPLICATION_SEARCH`}
                  >
                    {row.original?.subEstimateNumber || "NA"}
                  </Link>
                </span>
              ) : (
                <span>{t("NA")}</span>
              )}
            </div>)}
      },
      {
        Header: t("WORKS_NAME_OF_WORK"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.nameOfWork)),
      },
      {
        Header: t("WORKS_DEPARTMENT"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.department)),
      },
      {
        Header: t("WORKS_ADMINISTRATIVE_SANCTION_NO"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.administrativeSanctionNo)),
      },
      {
        Header: t("WORKS_ADMIN_APPROVED_DATE"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.adminApprovedDate)),
      },
      {
        Header: t("WORKS_FUND"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.fund)),
      },
      {
        Header: t("WORKS_FUNCTION"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.function)),
      },
      {
        Header: t("WORKS_BUDGET_HEAD"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.budgetHead)),
      },
      {
        Header: t("WORKS_CREATED_BY"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.createdBy)),
      },
      {
        Header: t("WORKS_OWNER"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.owner)),
      },
      {
        Header: t("WORKS_STATUS"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.status)),
      },
      {
        Header: t("WORKS_TOTAL_AMOUNT"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.totalAmount)),
      },
      {
        Header: t("WORKS_ACTION"),
        disableSortBy: true,
        Cell: ({ row }) =>{
          let service = "WORKS";
          return(
            <div>
              {row.original?.actions ? (
                <span className={"link"}>
                  <Link
                    to={`/digit-ui/employee/works/connection-details?applicationNumber=${
                      row.original["connectionNo"]
                      }&tenantId=${tenantId}&service=${service}&due=${row.original?.due || 0}&from=WS_SEWERAGE_APPLICATION_SEARCH`}
                  >
                   <div style={{display:"flex",flexDirection:"row"}}><EditIcon style={{ cursor: "pointer"}} /> {row.original?.actions || "NA"}</div>
                  </Link>
                </span>
              ) : (
                <span>{t("NA")}</span>
              )}
            </div>)}
      },
    ],
    []
  );

  return (
    <>
      <Header styles={{ fontSize: "32px" }}>{businessService === "WORKS" ? t("WORKS_SEARCH_APPROVED_ESTIMATE") : t("WORKS_SEARCH_APPROVED_ESTIMATE")}</Header>
      <SearchForm onSubmit={onSubmit} handleSubmit={handleSubmit} >
        <SearchFields {...{ register, control, reset, tenantId, t,businessService ,Department}} />
      </SearchForm>
      {data?.display && resultOk ? (
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
        </div>
      ) : (
        <Loader />
      )}
    </>
  );
};

export default SearchApplication;