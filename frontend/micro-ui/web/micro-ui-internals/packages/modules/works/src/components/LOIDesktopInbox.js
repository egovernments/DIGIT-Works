import { Card, Loader } from "@egovernments/digit-ui-react-components";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationTable from "./inbox/ApplicationTable";
import InboxLinks from "./LOIInbox/InboxLink"
import SearchApplication from "./LOIInbox/SearchApplication";
import { Link } from "react-router-dom";
import { convertEpochToDateDMY } from "../utils";


const LOIDesktopInbox = ({tableConfig, filterComponent,columns, isLoading, setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState, ...props }) => {
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
        Header: t("WORKS_LOI_ID"),
        Cell: ({ row }) => {
          return (
            <div>
              <span className="link">
                <Link to={`${props.parentRoute}/application-details/` + row.original?.searchData?.["propertyId"]}>
                  {row.original?.LOIId}
                </Link>
              </span>
            </div>
          );
        },
        mobileCell: (original) => GetMobCell(original?.searchData?.["propertyId"]),
      },
      {
        Header: t("WORKS_LOI_DATE"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.LOIDate}`);
        },
        mobileCell: (original) => GetMobCell(original?.searchData?.["owners"]?.[0].name),
      },
      {
        Header: t("WORKS_ABSTRACT_ESTIMATE_NO"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.EstimateNumber}`);
        },
        mobileCell: (original) => {
          const map = {
            "PT.CREATE": "ES_PT_NEW_PROPERTY",
            "PT.MUTATION": "ES_PT_TRANSFER_OWNERSHIP",
            "PT.UPDATE": "ES_PT_UPDATE_PROPERTY",
          };

          return GetCell(`${row.original?.CreatedBy}`);
        },
      },
      {
        Header: t("WORKS_NAME_OF_WORK"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.NameOfWork}`);
        },
        mobileCell: (original) => GetMobCell(`${original?.NameOfWork}`),
      },
      {
        Header: t("WORKS_CONTRACTOR_NAME"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.ContractorName}`);
        },
        mobileCell: (original) => GetMobCell(`${original?.ContractorName}`),
      },
      {
        Header: t("WORKS_AGREEMENT_AMT"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.AgrementAmount}`);
        },
        mobileCell: (original) => GetMobCell(`${original?.AgrementAmount}`),
      },
      {
        Header: t("WORKS_SLA"),
        Cell: ({ row }) => {
          return GetCell(`${row.original?.SLA}`);
        },
        mobileCell: (original) => GetMobCell(`${original?.SLA}`),
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
        <div className="result" style={{ marginLeft: !props?.isSearch ? "24px" : "",overflowX:"scroll"}}>
          {result}
        </div>
      </div>
    </div>
  );
};

export default LOIDesktopInbox;
