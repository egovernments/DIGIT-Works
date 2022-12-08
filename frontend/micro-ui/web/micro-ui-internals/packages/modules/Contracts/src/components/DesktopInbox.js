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
            <div style={{"minWidth":"150px"}}>
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
            <div style={{"minWidth":"200px"}}>
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
        Header: t("WORKS_SLA"),
        disableSortBy: true,
        Cell: ({ row }) =>{
          let cellcolor;
          row.original?.sla > 30 ? cellcolor="#FF0000" : cellcolor="#4F992D"
          return( 
            <div style={{"minWidth":"80px", "color":cellcolor}}>
              {row.original?.sla ? (
                <span >
                    {row.original?.sla || t("ES_COMMON_NA")}
                </span> 
              ) : (
                <span>{t("ES_COMMON_NA")}</span>
              )}
            </div>)}      
      },
      {
        Header: t("WORKS_STATUS"),
        disableSortBy: true,
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
      <div style={{ flex: 1, overflowY:"hidden" }}>
        <div id="search-form" className="rm-mb form-field-flex-one">
      <SearchApplication
          defaultSearchParams={props.defaultSearchParams}
          onSearch={props.onSearch}
          type="desktop"
          searchFields={props.searchFields}
          isInboxPage={!props?.isSearch}
          searchParams={props.searchParams}
          {...{setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState}}
        />
        </div>
        <div className="result" style={{ marginLeft: "24px", flex: 1 }}>
        {/* <div style={{ marginLeft: !props?.isSearch ? "24px" : "",flex: 1}}> */}
          {result}
        </div>
      </div>
    </div>
  );
};

export default DesktopInbox;
