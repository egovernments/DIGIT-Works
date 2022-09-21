import { Card, Loader } from "@egovernments/digit-ui-react-components";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationTable from "./inbox/ApplicationTable";
import InboxLinks from "./inbox/InboxLink";
import SearchApplication from "./inbox/search";
import { Link } from "react-router-dom";
import { convertEpochToDateDMY } from "../utils";

const DesktopInbox = ({tableConfig, filterComponent,columns, isLoading, setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState, ...props }) => {
  const { data } = props;
  const { t } = useTranslation();
  const [FilterComponent, setComp] = useState(() => Digit.ComponentRegistryService?.getComponent(filterComponent));
  const GetCell = (value) => <span className="cell-text">{value}</span>;
  const GetSlaCell = (value) => {
    if(value === "CS_NA") return t(value)
    if (isNaN(value)) return <span className="sla-cell-success">0</span>;
    return value < 0 ? <span className="sla-cell-error">{value}</span> : <span className="sla-cell-success">{value}</span>;
  };
  const stringReplaceAll = (str = "", searcher = "", replaceWith = "") => {
    if (searcher == "") return str;
    while (str.includes(searcher)) {
      str = str.replace(searcher, replaceWith);
    }
    return str;
  };

  const GetMobCell = (value) => <span className="sla-cell">{value}</span>;
    const inboxColumns = () => [
      {
        Header: t("WORKS_ESTIMATE_NO"),
        Cell: ({ row }) => {
          return (
            <div>
              <span className="link">
                <Link to={`${props.parentRoute}/application-details/` + row.original?.searchData?.["propertyId"]}>
                  {row.original?.EstimateNumber}
                </Link>
              </span>
            </div>
          );
        },
        mobileCell: (original) => GetMobCell(original?.searchData?.["propertyId"]),
      },
      {
        Header: t("WORKS_DEPARTMENT"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.Department}`);
        },
        mobileCell: (original) => GetMobCell(original?.searchData?.["owners"]?.[0].name),
      },
      {
        Header: t("WORKS_FUND"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.Fund}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
      {
        Header: t("WORKS_FUNCTION"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.Function}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
      {
        Header: t("WORKS_BUDGET_HEAD"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.BudgetHead}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
      {
        Header: t("WORKS_CREATED_BY"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.CreatedBy}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
      {
        Header: t("WORKS_OWNER"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.Owner}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
      {
        Header: t("WORKS_STATUS"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.Status}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
      {
        Header: t("WORKS_TOTAL_AMOUNT"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.TotalAmount}`);
        },
        mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
      },
    ];
    let result;
    // if (props.isLoading) {
  //   result = <Loader />;
  // } else if (data?.table?.length === 0) {
  //   result = (
  //     <Card style={{ marginTop: 20 }}>
  //       {t("CS_MYAPPLICATIONS_NO_APPLICATION")
  //         .split("\\n")
  //         .map((text, index) => (
  //           <p key={index} style={{ textAlign: "center" }}>
  //             {text}
  //           </p>
  //         ))}
  //     </Card>
  //   );
  // } else if (data?.table?.length > 0) {
    result = (
      <ApplicationTable
      t={t}
      data={data}
      columns={inboxColumns(data)}
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
                   );
  // }

return (
    <div className="inbox-container">
      {!props.isSearch && (
          <div className="filters-container">
          <InboxLinks />
          <div>
            {
            // isLoading ? <Loader /> :
              <FilterComponent
                defaultSearchParams={props.defaultSearchParams}
                statuses={data?.statuses}
                onFilterChange={props.onFilterChange}
                searchParams={props.searchParams}
                type="desktop"
              />
            }
          </div>
        </div>
       )}
      <div style={{overflowX:"hidden"}}>
      <SearchApplication
          defaultSearchParams={props.defaultSearchParams}
          onSearch={props.onSearch}
          type="desktop"
          searchFields={props.searchFields}
          isInboxPage={!props?.isSearch}
          searchParams={props.searchParams}
          {...{setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState}}
        />
        <div style={{ marginLeft: !props?.isSearch ? "24px" : "",overflowX:"scroll"}}>
          {result}
        </div>
      </div>
    </div>
  );
};

export default DesktopInbox;
