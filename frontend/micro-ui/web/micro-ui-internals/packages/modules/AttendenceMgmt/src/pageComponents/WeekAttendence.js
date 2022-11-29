import { Table, ArrowDown } from "@egovernments/digit-ui-react-components";
import React, { useState, useMemo, useEffect, Fragment, useCallback } from "react";
import { useTranslation } from "react-i18next";

const WeekAttendence = ({ state, dispatch, searchQuery }) => {
  console.log(state);
  const { t } = useTranslation();
  const tableRow = Object.values(state.rows);

  const handleCheckboxClick = (row, index) => {
    dispatch({
      type: "attendence",
      state: {
        row,
        index,
      },
    });
  };

  const AttendenceSelector = (state, row, index) => {
    const classSelector = (state) => {
      switch (state) {
        case "half":
          return ["radio-outer-circle selected", "radio-half-inner-circle"];
        case "full":
          return ["radio-outer-circle selected", "radio-full-inner-circle"];

        default:
          return ["radio-outer-circle unselected", ""];
      }
    };

    return (
      <div className="modern-radio-container week-table" onClick={() => handleCheckboxClick(row, index)}>
        <div className={`${classSelector(state)?.[0]}`}>
          <div className={`${classSelector(state)?.[1]}`}></div>
        </div>
      </div>
    );
  };

  const renderTotal = (value) => {
    return (
      <div className="total-attendence">
        <span>{value}</span>
      </div>
    );
  };

  const tableColumns = useMemo(() => {
    return [
      {
        // Header: t("ATM_WAGE_SEEKER_NAME"),
        Header: "S. No",
        accessor: "sno",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: "Registration ID",
        accessor: "reg_id",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        // Header: t("ATM_WAGE_SEEKER_AADHAR"),
        Header: "Name of the Individual",
        accessor: "name_of_individual",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        // Header: t("ATM_WAGE_SEEKER_AADHAR"),
        Header: "Father / Guardian' Name",
        accessor: "guardian_name",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Mon</p>
            <p className="date-attendence">21 Oct</p>
          </div>
        ),
        accessor: "mon",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[0]));
          }
          return AttendenceSelector(row.original.attendence?.[0], row.original, 0);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Tue</p>
            <p className="date-attendence">22 Oct</p>
          </div>
        ),
        accessor: "tue",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[1]));
          }
          return AttendenceSelector(row.original.attendence?.[1], row.original, 1);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Wed</p>
            <p className="date-attendence">23 Oct</p>
          </div>
        ),
        accessor: "wed",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[2]));
          }
          return AttendenceSelector(row.original.attendence?.[2], row.original, 2);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Thu</p>
            <p className="date-attendence">24 Oct</p>
          </div>
        ),
        accessor: "thu",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[3]));
          }
          return AttendenceSelector(row.original.attendence?.[3], row.original, 3);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Fri</p>
            <p className="date-attendence">25 Oct</p>
          </div>
        ),
        accessor: "fri",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[4]));
          }
          return AttendenceSelector(row.original.attendence?.[4], row.original, 4);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Sat</p>
            <p className="date-attendence">26 Oct</p>
          </div>
        ),
        accessor: "sat",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[5]));
          }
          return AttendenceSelector(row.original.attendence?.[5], row.original, 5);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">Sun</p>
            <p className="date-attendence">27 Oct</p>
          </div>
        ),
        accessor: "sun",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[6]));
          }
          return AttendenceSelector(row.original.attendence?.[6], row.original, 6);
        },
      },
    ];
  }, [state]);
  return (
    <React.Fragment>
      <div style={{ padding: "0px", overflowX: "scroll" }} className="card week-table-card-wrapper">
        <Table
          className="wage-seekers-table week-table"
          t={t}
          // customTableWrapperClassName={"dss-table-wrapper"}
          disableSort={false}
          autoSort={false}
          manualPagination={false}
          initSortId="S N "
          data={tableRow}
          totalRecords={tableRow.length}
          columns={tableColumns}
          isPaginationRequired={true}
          getCellProps={(cellInfo) => {
            return {
              style: {},
            };
          }}
        />
      </div>
    </React.Fragment>
  );
};

export default WeekAttendence;
