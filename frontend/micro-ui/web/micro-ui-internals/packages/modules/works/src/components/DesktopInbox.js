import { Card, Loader } from "@egovernments/digit-ui-react-components";
import React, { useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationTable from "./inbox/ApplicationTable";
import InboxLinks from "./inbox/InboxLink";
import SearchApplication from "./inbox/search";
import { Link } from "react-router-dom";
import { convertEpochToDateDMY } from "../utils";

const DesktopInbox = ({tableConfig,resultOk, filterComponent,columns, isLoading, setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState, ...props }) => {
  const { data } = props;
  const { t } = useTranslation();
  const [FilterComponent, setComp] = useState(() => Digit.ComponentRegistryService?.getComponent(filterComponent));
  const GetCell = (value) => <span className="cell-text">{value}</span>;
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const GetMobCell = (value) => <span className="sla-cell">{value}</span>;
    const inboxColumns = useMemo(
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
                      to={`view-estimate?tenantId=${tenantId}&estimateNumber=${row.original?.estimateNumber}`}>
                      {row.original?.estimateNumber || "NA"}
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
          accessor: (row) => (GetCell(t(`ES_COMMON_${row?.department}`)) || t("NA")),
        },
        // {
        //   Header: t("WORKS_ADMIN_SANCTION_NUMBER"),
        //   disableSortBy: true,
        //   accessor: (row) => (GetCell(row?.adminSanctionNumber)),
        // },
        {
          Header: t("WORKS_FUND"),
          disableSortBy: true,
          accessor: (row) => (GetCell(t(`ES_COMMON_FUND_${row?.fund}`)) || t("NA")),
        },
        {
          Header: t("WORKS_FUNCTION"),
          disableSortBy: true,
          accessor: (row) => (GetCell(t(`ES_COMMON_${row?.function}`)) || t("NA")),
        },
        {
          Header: t("WORKS_BUDGET_HEAD"),
          disableSortBy: true,
          accessor: (row) => (GetCell(t(`ES_COMMON_${row?.budgetHead}`)) || t("NA")),
        },        
        {
          Header: t("WORKS_CREATED_BY"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.createdBy || t("NA")) ),
        },
        {
          Header: t("WORKS_OWNER"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.owner || t("NA") ) ),
        },
        {
          Header: t("WORKS_STATUS"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.status || t("NA"))),
        },
        {
          Header: t("WORKS_TOTAL_AMOUNT"),
          disableSortBy: true,
          accessor: (row) => (GetCell(row?.totalAmount || t("NA")) ),
        },
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
      <div>
    <ApplicationTable
      t={t}
      data={data.table}
      columns={inboxColumns}
      getCellProps={(cellInfo) => {
        return {
          style: {
            minWidth: cellInfo.column.Header === t("WORKS_INBOX_APPLICATION_NO") ? "240px" : "",
            padding: "20px 18px",
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
      </div>
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
      <div style={{ flex: 1,overflow:"hidden" }}>
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
