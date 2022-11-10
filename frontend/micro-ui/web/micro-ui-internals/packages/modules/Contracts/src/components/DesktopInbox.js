import { Card, Loader } from "@egovernments/digit-ui-react-components";
import React, { useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationTable from "./inbox/ApplicationTable";
import InboxLinks from "./inbox/InboxLink";
import SearchApplication from "./inbox/search";
import { Link } from "react-router-dom";

const DesktopInbox = ({tableConfig,resultOk, filterComponent,columns, isLoading, setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState, ...props }) => {
  const { data } = props;
  const { t } = useTranslation();
  const [FilterComponent, setComp] = useState(() => Digit.ComponentRegistryService?.getComponent(filterComponent));
  const GetCell = (value) => <span className="cell-text">{value}</span>;

  const inboxColumns = useMemo( 
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
                    {row.original?.contractId || "NA"}
                  </Link>
                </span> 
              ) : (
                <span>{t("NA")}</span>
              )}
            </div>)}
      },
      {
        Header: t("WORKS_CONTRACT_DATE"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.contractDate)),
      },
      {
        Header: t("WORKS_CONTRACT_TYPE"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row.contractType)),
      },
      {
        Header: t("WORKS_NAME_OF_WORK"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row.nameOfTheWork)),
      },
      {
        Header: t("WORKS_ABSTRACT_ESTIMATE_NO"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row.abstractEstimateNumber)),
      },        
      {
        Header: t("WORKS_IMPLEMENT_AUTH"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.implementingAuthority)),
      },
      {
        Header: t("WORKS_NAME_OF_ORGN"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.orgnName)),
      },
      {
        Header: t("WORKS_OFFICER_INCHARGE_NAME"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.officerIncharge)),
      },
      {
        Header: t("WORKS_AGREEMENT_AMT"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.agreemntAmount)),
      },
      {
        Header: t("WORKS_SLA"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.sla)),
      },
      {
        Header: t("WORKS_STATUS"),
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.status)),
      }
  ],[])
  let result;
  if (isLoading) {
    result = <Loader />;
  } else if (data?.display && !resultOk) {
    result = (
      <Card style={{ marginTop: 20 }}>
        {t(data?.display)
          .split("\\n")
          .map((text, index) => (
            <p key={index} style={{ textAlign: "center" }}>
              {text}
            </p>
          ))}
      </Card>
    );
  } else if (resultOk) {
  result= (
    <ApplicationTable
      t={t}
      data={data}
      columns={inboxColumns}
      getCellProps={(cellInfo) => {
        return {
          style: {
            minWidth: cellInfo.column.Header === t("WORKS_CONTRACT_ID") ? "120px" : "",
            padding: "20px 10px",
            fontSize: "16px",
          },
        };
      }}
      onPageSizeChange={props.onPageSizeChange}
      currentPage={props.currentPage}
      onNextPage={props.onNextPage}
      onPrevPage={props.onPrevPage}
      pageSizeLimit={props.pageSizeLimit}
      onSort={props.onSort}
      disableSort={props.disableSort}
      sortParams={props.sortParams}
      totalRecords={props.totalRecords}
    />
  )
  }else
  {
    result=null
  }

return (
    <div className="inbox-container">
      {!props.isSearch && (
        <div className="filters-container">
        <InboxLinks />
          <div>
            <FilterComponent
              defaultSearchParams={props.defaultSearchParams}
              statuses={data?.statuses}
              onFilterChange={props.onFilterChange}
              searchParams={props.searchParams}
              type="desktop"
            />
          </div>
        </div>
       )}
      <div style={{ flex: 1 }}>
      <SearchApplication
          defaultSearchParams={props.defaultSearchParams}
          onSearch={props.onSearch}
          type="desktop"
          searchFields={props.searchFields}
          isInboxPage={!props?.isSearch}
          searchParams={props.searchParams}
          {...{setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState}}
        />
        <div style={{ marginLeft: !props?.isSearch ? "24px" : ""}}>
          {result}
        </div>
      </div>
    </div>
  );
};

export default DesktopInbox;
