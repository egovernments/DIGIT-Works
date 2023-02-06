import React, { useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import { Table } from "@egovernments/digit-ui-react-components";

const WeekAttendence = ({ state, dispatch, modify, setSaveAttendanceState, weekDates}) => {
  const { t } = useTranslation();
  const [editable, setEditable] = useState(false)
  const tableRow = state ? Object.values(state) : []
  const [prevAttendanceTotal, setPrevAttendanceTotal] = useState({})
  
  useEffect(() => {
    dispatch({
      type: "initialTotal",
      state: {},
    });

    let prev = {}
    tableRow?.forEach(row => {
      prev[row.id] = row.modifiedWorkingDays
    })
    setPrevAttendanceTotal(prev)

    if(modify) setEditable(true)
  }, [modify])

  const handleCheckboxClick = (row, day) => {
    dispatch({
      type: "attendanceTotal",
      state: {
        row,
        day
      },
    });
  };

  const renderAttendenceSelector = (state, row, day) => {
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
      <div className="modern-radio-container week-table">
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

  const handleModifiedWorkingDays = (e, row) => {
    let val = parseFloat(e.target.value);
    let prevVal = parseFloat(prevAttendanceTotal[row.id])
    if (val && val !== prevVal) {
      setSaveAttendanceState(prevState => 
                    ({...prevState, 
                      displaySave: true, 
                      updatePayload: prevState.updatePayload.some(item => item.id === row.id) ? 
                          (prevState.updatePayload.map(item => {
                              if(item.id === row.id) {
                                item.totalAttendance = val
                              } 
                              return item
                          })) :
                          [...prevState.updatePayload, {id: row.id, totalAttendance: val}]
                      }))
      dispatch({
        type: "updateModifiedTotal",
        state: {
          row,
          val
        },
      });
    }
  };

  const renderBankAccountDetails = (value) => {
    return (
      <div className="column-bank-details">
        <p className="detail">{value.accountNo}</p>
        <p className="detail">{value.ifscCode}</p>
      </div>
    );
  };

  const renderInputBoxSelector = (value, row) => {
    return <input type="number" name="amount" className="modified-amount" step={0.5} defaultValue={value} onChange={(e) => handleModifiedWorkingDays(e, row)}/>
  };

  const tableColumnsReadOnly = useMemo(() => {
    return [
      {
        Header: () => <p>{t("WORKS_SNO")}</p>,
        accessor: "sno",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotalLabel(t(row.original.sno));
          }
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_REGISTRATION_ID")}</p>,
        accessor: "registerId",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_NAME_OF_THE_INDIVIDUAL")}</p>,
        accessor: "nameOfIndividual",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_FATHER/GUARDIAN_NAME")}</p>,
        accessor: "guardianName",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_MON")}</p>
            <p className="date-attendence">{weekDates.Mon}</p>
          </div>
        ),
        accessor: "mon",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Mon));
          }
          return renderAttendenceSelector(row.original.attendence?.Mon, row.original, 'Mon');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_TUE")}</p>
            <p className="date-attendence">{weekDates.Tue}</p>
          </div>
        ),
        accessor: "tue",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Tue));
          }
          return renderAttendenceSelector(row.original.attendence?.Tue, row.original, 'Tue');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_WED")}</p>
            <p className="date-attendence">{weekDates.Wed}</p>
          </div>
        ),
        accessor: "wed",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Wed));
          }
          return renderAttendenceSelector(row.original.attendence?.Wed, row.original, 'Wed');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_THR")}</p>
            <p className="date-attendence">{weekDates.Thu}</p>
          </div>
        ),
        accessor: "thu",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Thu));
          }
          return renderAttendenceSelector(row.original.attendence?.Thu, row.original, 'Thu');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_FRI")}</p>
            <p className="date-attendence">{weekDates.Fri}</p>
          </div>
        ),
        accessor: "fri",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Fri));
          }
          return renderAttendenceSelector(row.original.attendence?.Fri, row.original, 'Fri');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_SAT")}</p>
            <p className="date-attendence">{weekDates.Sat}</p>
          </div>
        ),
        accessor: "sat",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Sat));
          }
          return renderAttendenceSelector(row.original.attendence?.Sat, row.original, 'Sat');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_SUN")}</p>
            <p className="date-attendence">{weekDates.Sun}</p>
          </div>
        ),
        accessor: "sun",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Sun));
          }
          return renderAttendenceSelector(row.original.attendence?.Sun, row.original, 'Sun');
        },
      }
    ];
  }, [state]);

  const tableColumnsEditable = useMemo(() => {
    return [
      {
        Header: () => <p>{t("WORKS_SNO")}</p>,
        accessor: "sno",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotalLabel(t(row.original.sno));
          }
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_REGISTRATION_ID")}</p>,
        accessor: "registerId",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_NAME_OF_THE_INDIVIDUAL")}</p>,
        accessor: "nameOfIndividual",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_FATHER/GUARDIAN_NAME")}</p>,
        accessor: "guardianName",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_MON")}</p>
            <p className="date-attendence">{weekDates.Mon}</p>
          </div>
        ),
        accessor: "mon",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Mon));
          }
          return renderAttendenceSelector(row.original.attendence?.Mon, row.original, 'Mon');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_TUE")}</p>
            <p className="date-attendence">{weekDates.Tue}</p>
          </div>
        ),
        accessor: "tue",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Tue));
          }
          return renderAttendenceSelector(row.original.attendence?.Tue, row.original, 'Tue');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_WED")}</p>
            <p className="date-attendence">{weekDates.Wed}</p>
          </div>
        ),
        accessor: "wed",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Wed));
          }
          return renderAttendenceSelector(row.original.attendence?.Wed, row.original, 'Wed');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_THR")}</p>
            <p className="date-attendence">{weekDates.Thu}</p>
          </div>
        ),
        accessor: "thu",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Thu));
          }
          return renderAttendenceSelector(row.original.attendence?.Thu, row.original, 'Thu');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_FRI")}</p>
            <p className="date-attendence">{weekDates.Fri}</p>
          </div>
        ),
        accessor: "fri",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Fri));
          }
          return renderAttendenceSelector(row.original.attendence?.Fri, row.original, 'Fri');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_SAT")}</p>
            <p className="date-attendence">{weekDates.Sat}</p>
          </div>
        ),
        accessor: "sat",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Sat));
          }
          return renderAttendenceSelector(row.original.attendence?.Sat, row.original, 'Sat');
        },
      },
      {
        Header: () => (
          <div className="column-attendence">
            <p className="day-attendence">{t("ATM_SUN")}</p>
            <p className="date-attendence">{weekDates.Sun}</p>
          </div>
        ),
        accessor: "sun",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return renderTotal(t(row.original.attendence?.Sun));
          }
          return renderAttendenceSelector(row.original.attendence?.Sun, row.original, 'Sun');
        },
      },
      {
        Header: () => <p>{t("ATM_ACTUAL_WORKING_DAYS")}</p>,
        accessor: "actualWorkingDays",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_SKILL")}</p>,
        accessor: "skill",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_AMOUNT_IN_RS")}</p>,
        accessor: "amount",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_MODIFIED_WORKING_DAYS")}</p>,
        accessor: "modifiedWorkingDays",
        Cell: ({ value, column, row }) => {
          if (row.original.type === "total") {
            return String(t(value));
          }
          return renderInputBoxSelector(value, row.original);
        },
      },
      {
        Header: () => <p>{t("ATM_MODIFIED_AMOUNT_IN_RS")}</p>,
        accessor: "modifiedAmount",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        },
      },
      {
        Header: () => <p>{t("ATM_BANK_ACCOUNT_DETAILS")}</p>,
        accessor: "bankAccountDetails",
        Cell: ({ value, column, row }) => {
          return renderBankAccountDetails(value);
        },
      },
      {
        Header: () => <p>{t("ATM_WAGE_SEEKER_AADHAR")}</p>,
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
          columns={editable ? tableColumnsEditable : tableColumnsReadOnly}
          isPaginationRequired={false}
          getCellProps={(cellInfo) => {
            let tableProp = {};
            if(cellInfo.column.Header === "Modified Amount(Rs)") {
              tableProp["data-modified-amt"] = "modified-amt";
            }
            if(cellInfo.value === undefined) {
              tableProp["data-radio-selection"] = "last-radio";
            }
            if(cellInfo?.row?.original?.type === "total") {
              tableProp["data-last-row-cell"] = "last-row";
            }
            if(cellInfo.value === "ATM_TOTAL") {
              tableProp["colSpan"] = 4;
            }
            if(cellInfo.value === "DNR") {
              tableProp["style"] = {
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
