import { Table } from '@egovernments/digit-ui-react-components'
import React,{useState,useMemo,useEffect,Fragment} from 'react'
import { useTranslation } from 'react-i18next'
import SkillSelector from './SkillSelector'

const TrackAttendenceTable = ({state,dispatch}) => {
  const [showSkillSelector, setShowSkillSelector] = useState(false)
  
  const { t } = useTranslation()
  const tableRow = Object.values(state.rows)
  
  const handleCheckboxClick = (row,index) => {
    dispatch({
      type:"attendence",
      state:{
        row,
        index
      }
    })
  }

  const AttendenceSelector = (state,row,index) => {
    
    const classSelector = (state) => {
      switch (state) {
        case "half":
          return ["radio-outer-circle selected", "radio-half-inner-circle"]
        case "full":
          return ["radio-outer-circle selected", "radio-full-inner-circle"]

        default:
          return ["radio-outer-circle unselected", ""]
      }
    }

    return (
      <div className="modern-radio-container" onClick={() => handleCheckboxClick(row, index)}>
        <div className={`${classSelector(state)?.[0]}`}>
          <div className={`${classSelector(state)?.[1]}`} >
          </div>
        </div>
      </div>
    )
  }

  const tableColumns = useMemo(() => {
    return [
      {
        Header: t("ATM_WAGE_SEEKER_NAME"),
        accessor: "name",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        }
      },
      {
        Header: t("ATM_GUARDIAN_NAME"),
        accessor: "guardian",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        }
      },
      {
        Header: t("ATM_WAGE_SEEKER_AADHAR"),
        accessor: "aadhar",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        }
      },
      {
        Header: t("ATM_SKILL_LEVEL"),
        accessor: "skill",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        }
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
          return AttendenceSelector(row.original.attendence?.[0],row.original,0)
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
          return AttendenceSelector(row.original.attendence?.[1], row.original, 1)
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
          return AttendenceSelector(row.original.attendence?.[2], row.original, 2)
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
          return AttendenceSelector(row.original.attendence?.[3], row.original, 3)
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
          return AttendenceSelector(row.original.attendence?.[4], row.original, 4)
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
          return AttendenceSelector(row.original.attendence?.[5], row.original, 5)
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
          return AttendenceSelector(row.original.attendence?.[6], row.original, 6)
        },
      },
    ]
  }, [state])
  return (
    <React.Fragment>
      <div style={{ "padding": "0px", "overflowX": "scroll" }} className='card'>
        <Table
          className="table-fixed-first-column-wage-seekers wage-seekers-table"
          t={t}
          // customTableWrapperClassName={"dss-table-wrapper"}
          disableSort={false}
          autoSort={true}
          manualPagination={false}
          // globalSearch={filterValue}
          initSortId="S N "
          // onSearch={onSearch}
          data={tableRow}
          totalRecords={tableRow.length}
          columns={tableColumns}
          isPaginationRequired={false}
          getCellProps={(cellInfo) => {
            return {
              style: {},
            };
          }}
        />
        {/* {showSkillSelector && <SkillSelector t={t} closeModal={setShowSkillSelector} userState={state} setUserState={dispatch} />} */}
      </div>
    </React.Fragment>
  )
}

export default TrackAttendenceTable