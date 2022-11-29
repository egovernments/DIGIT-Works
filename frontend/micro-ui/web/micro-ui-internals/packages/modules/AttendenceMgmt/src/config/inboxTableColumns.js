import React from "react";
import { Link } from "react-router-dom";
const GetCell = (value) => <span className="cell-text">{value}</span>;

export const inboxTableColumns = [
    {
      Header: "Muster Roll ID",
      disableSortBy: true,
      accessor: "mustorRollId",
      Cell: ({ row }) => {
        return (
          <div> {
            row.original.mustorRollId ? (
                <span className="link">
                <Link to={`viewAttendance?mustorRollId=${row.original.mustorRollId}`}>{row.original.mustorRollId || "NA"}</Link>
                </span>
            ) : (<span>{t("NA")}</span>) }
          </div>
        );
      }
    },
    {
        Header: "Name of the Work",
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.work)),
    },
    {
        Header: "Attendance Week",
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.week)),
    },
    {
        Header: "IA/IP",
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.iaip)),
    },
    {
        Header: "Number of Individuals",
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.individualCount)),
    },
    {
        Header: "SLA (days)",
        disableSortBy: true,
        accessor: (row) => (GetCell(row?.slaDays)),
    }   
]