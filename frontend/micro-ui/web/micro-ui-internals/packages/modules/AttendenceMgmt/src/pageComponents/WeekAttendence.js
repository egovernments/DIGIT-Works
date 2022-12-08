import { Table, ArrowDown, TextInput } from "@egovernments/digit-ui-react-components";
import React, { useState, useMemo, useEffect, Fragment, useCallback } from "react";
import { useTranslation } from "react-i18next";

const WeekAttendence = ({ state, dispatch, searchQuery }) => {
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

  const renderAttendenceSelector = (state, row, index) => {
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

  const renderTotalLabel = (value) => {
    return (
      <div className="total-attendence-label">
        <span>{value}</span>
      </div>
    );
  };

  const handleModifiedAmount = (event) => {
  }

  const renderBankAccountDetails = (value) => {
    return (
      <div className="column-bank-details">
        <p className="detail">{value.accountNo}</p>
        <p className="detail">{value.ifscCode}</p>
      </div>
    )
  }

  const renderInputBoxSelector = (value) => {
    return (
      <input type="number" className="modified-amount" onChange={handleModifiedAmount}></input>
    )
  }

  const tableColumns = useMemo(() => {
    return [
      {
        Header: t("WORKS_SNO"),
        accessor: "sno",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotalLabel(t(row.original.sno)); //Pass Total as Label
          }
          return String(t(value));
        },
      },
      {
        Header: t("ATM_REGISTRATION_ID"),
        accessor: "reg_id",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: t("ATM_NAME_OF_THE_INDIVIDUAL"),
        accessor: "name_of_individual",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: t("ATM_FATHER/GUARDIAN_NAME"),
        accessor: "guardian_name",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_MON")}</p>
            <p className="date-attendence">21 Oct</p>
          </div>
        ),
        accessor: "mon",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[0]));
          }
          return renderAttendenceSelector(row.original.attendence?.[0], row.original, 0);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_TUE")}</p>
            <p className="date-attendence">22 Oct</p>
          </div>
        ),
        accessor: "tue",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[1]));
          }
          return renderAttendenceSelector(row.original.attendence?.[1], row.original, 1);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_WED")}</p>
            <p className="date-attendence">23 Oct</p>
          </div>
        ),
        accessor: "wed",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[2]));
          }
          return renderAttendenceSelector(row.original.attendence?.[2], row.original, 2);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_THR")}</p>
            <p className="date-attendence">24 Oct</p>
          </div>
        ),
        accessor: "thu",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[3]));
          }
          return renderAttendenceSelector(row.original.attendence?.[3], row.original, 3);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_FRI")}</p>
            <p className="date-attendence">25 Oct</p>
          </div>
        ),
        accessor: "fri",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[4]));
          }
          return renderAttendenceSelector(row.original.attendence?.[4], row.original, 4);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_SAT")}</p>
            <p className="date-attendence">26 Oct</p>
          </div>
        ),
        accessor: "sat",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[5]));
          }
          return renderAttendenceSelector(row.original.attendence?.[5], row.original, 5);
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_SUN")}</p>
            <p className="date-attendence">27 Oct</p>
          </div>
        ),
        accessor: "sun",
        Cell: ({ value, column, row }) => {
          //row.original.attendence[0] -> state
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.[6]));
          }
          return renderAttendenceSelector(row.original.attendence?.[6], row.original, 6);
        },
      },
      {
        Header: t("ATM_ACTUAL_WORKING_DAYS"),
        accessor: "actualWorkingDays",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: t("ATM_AMOUNT_IN_RS"),
        accessor: "amountInRs",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: t("ATM_MODIFIED_WORKING_DAYS"),
        accessor: "modifiedWorkingDays",
        Cell: ({ value, column, row }) => {
          if(row.original.type === "total") {
            return String(t(value));
          }
          return renderInputBoxSelector(value);
        }
      },
      {
        Header: t("ATM_MODIFIED_AMOUNT_IN_RS"),
        accessor: "modifiedAmountInRs",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: t("ATM_BANK_ACCOUNT_DETAILS"),
        accessor: "bankAccountDetails",
        Cell: ({ value, column, row }) => {
          return renderBankAccountDetails(value);
        },
      },
      {
        Header: t("ATM_WAGE_SEEKER_AADHAR"),
        accessor: "aadharNumber",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
    ];
  }, [state]);
  return (
    <React.Fragment>
      <div style={{ padding: "0px", overflowX: "scroll" }} className="card week-table-card-wrapper">
        <Table
          className="wage-seekers-table week-table"
          customTableWrapperClassName="table-wrapper attendence-table"
          t={t}
          disableSort={false}
          autoSort={false}
          manualPagination={false}
          initSortId="S N "
          data={tableRow}
          totalRecords={tableRow.length}
          columns={tableColumns}
          isPaginationRequired={true}
          getCellProps={(cellInfo) => {
            let tableProp = {};
            if(cellInfo.column.Header === "Modified Amount(Rs)") {
              tableProp['data-modified-amt'] = "modified-amt";
            }
            if(cellInfo.value === undefined) {
              tableProp['data-radio-selection'] = "last-radio";
            }
            if(cellInfo?.row?.original?.type === "total") {
              tableProp['data-last-row-cell'] = "last-row";
            }
            if(cellInfo.value === "ATM_TOTAL") {
              tableProp['colSpan'] = 4;
            }
            if (cellInfo.value === "DNR") {
                tableProp['style'] = {
                  display: "none",
                }
            }
            return tableProp;
          }}
        />
      </div>
    </React.Fragment>
  );
};

export default WeekAttendence;
