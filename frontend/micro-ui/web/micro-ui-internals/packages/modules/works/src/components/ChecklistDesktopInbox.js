import { Card, Loader } from "@egovernments/digit-ui-react-components";
import React, { useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import ApplicationTable from "./ChecklistInbox/ApplicationTable";
import InboxLinks from "./ChecklistInbox/InboxLink";
import SearchApplication from "./ChecklistInbox/search";
import { Link } from "react-router-dom";
import { convertEpochToDateDMY } from "../utils";

const DesktopInbox = ({ tableConfig, resultOk, filterComponent, columns, isLoading, setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState, ...props }) => {
    const dummyDataForTable = [
        {
            workOrderId: "1136/TO/DB/FLOOD/10-11",
            woIssuedDate: "08/09/2010",
            woAcceptedDate: "08/09/2010",
            nameOfWork: "Providing CC Drain in Birla Gaddah(akshaynagar colony) in 27th ward",
            agencyName: "S.K. Om Birla",
            status: "complete"
            //sending dummy data(here for now I'm calling estimates inbox just to test the inbox table, for every item in estimate inbox respnse I'm showing the same dummy data)
        },
        {
            workOrderId: "1136/TO/DB/FLOOD/10-11",
            woIssuedDate: "08/09/2010",
            woAcceptedDate: "08/09/2010",
            nameOfWork: "Providing CC Drain in Birla Gaddah(akshaynagar colony) in 27th ward",
            agencyName: "S.K. Om Birla",
            status: "complete"
            //sending dummy data(here for now I'm calling estimates inbox just to test the inbox table, for every item in estimate inbox respnse I'm showing the same dummy data)
        },
        {
            workOrderId: "1136/TO/DB/FLOOD/10-11",
            woIssuedDate: "08/09/2010",
            woAcceptedDate: "08/09/2010",
            nameOfWork: "Providing CC Drain in Birla Gaddah(akshaynagar colony) in 27th ward",
            agencyName: "S.K. Om Birla",
            status: "complete"
            //sending dummy data(here for now I'm calling estimates inbox just to test the inbox table, for every item in estimate inbox respnse I'm showing the same dummy data)
        },
        {
            workOrderId: "1136/TO/DB/FLOOD/10-11",
            woIssuedDate: "08/09/2010",
            woAcceptedDate: "08/09/2010",
            nameOfWork: "Providing CC Drain in Birla Gaddah(akshaynagar colony) in 27th ward",
            agencyName: "S.K. Om Birla",
            status: "complete"
            //sending dummy data(here for now I'm calling estimates inbox just to test the inbox table, for every item in estimate inbox respnse I'm showing the same dummy data)
        },
        {
            workOrderId: "1136/TO/DB/FLOOD/10-11",
            woIssuedDate: "08/09/2010",
            woAcceptedDate: "08/09/2010",
            nameOfWork: "Providing CC Drain in Birla Gaddah(akshaynagar colony) in 27th ward",
            agencyName: "S.K. Om Birla",
            status: "complete"
            //sending dummy data(here for now I'm calling estimates inbox just to test the inbox table, for every item in estimate inbox respnse I'm showing the same dummy data)
        }
    ]
    const { data } = props;
    
    const { t } = useTranslation();
    const [FilterComponent, setComp] = useState(() => Digit.ComponentRegistryService?.getComponent(filterComponent));
    const GetCell = (value) => <span className="cell-text">{value}</span>;

    const GetMobCell = (value) => <span className="sla-cell">{value}</span>;
    const inboxColumns = useMemo(
        () => [
            {
                Header: t("WORKS_ORDER_ID"),
                disableSortBy: true,
                accessor: "workOrderId",
                Cell: ({ row }) => {
                    let service = "WORKS";
                    
                    return (
                        <div>
                            {row.original?.workOrderId ? (
                                <span className={"link"}>
                                    <Link
                                        to={`view-estimate?tenantId=${row.original?.tenantId}&estimateNumber=${row.original?.workOrderId}`}>
                                        {row.original?.workOrderId || "NA"}
                                    </Link>
                                </span>
                            ) : (
                                <span>{t("NA")}</span>
                            )}
                        </div>)
                }
            },
            {
                Header: t("WORKS_WO_ISSUED_DATE"),
                disableSortBy: true,
                accessor: (row) => (GetCell(t(`${row.woIssuedDate}`))),
            },
            {
                Header: t("WORKS_WO_ACCEPTED_DATE"),
                disableSortBy: true,
                accessor: (row) => (GetCell(t(`${row.woAcceptedDate}`))),
            },
            {
                Header: t("WORKS_NAME_OF_WORK"),
                disableSortBy: true,
                accessor: (row) => (GetCell(t(`${row.nameOfWork}`))),
            },
            {
                Header: t("WORKS_AGENCY_NAME"),
                disableSortBy: true,
                accessor: (row) => (GetCell(t(`${row.agencyName}`))),
            },
            {
                Header: t("WORKS_STATUS"),
                disableSortBy: true,
                accessor: (row) => (GetCell(t(`${row.status}`))),
            },
        ], [])
    let result;
    if (isLoading) {
        result = <Loader />;
    } 
    else if (data?.display && !resultOk) {
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
    } 
    // else if (resultOk) {
       else if(true){ // for static screen purposes
        result = (
            <ApplicationTable
                t={t}
                //data={data.table}
                data={dummyDataForTable} //for static screen
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
        )
    } else {
        result = null
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
            <div style={{ flex: 1,overflowX:"hidden" }}>
                <SearchApplication
                    defaultSearchParams={props.defaultSearchParams}
                    onSearch={props.onSearch}
                    type="desktop"
                    searchFields={props.searchFields}
                    isInboxPage={!props?.isSearch}
                    searchParams={props.searchParams}
                    {...{ setSearchFieldsBackToOriginalState, setSetSearchFieldsBackToOriginalState }}
                />
                <div style={{ marginLeft: !props?.isSearch ? "24px" : "" }}>
                    {result}
                </div>
            </div>
        </div>
    );
};

export default DesktopInbox;
