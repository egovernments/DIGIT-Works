import { Card, StatusTable, Row, DetailsCard, SearchIcon, Dropdown, SearchableDropdown,Table } from '@egovernments/digit-ui-react-components'
import React,{useMemo,useState} from 'react'
import { useTranslation } from 'react-i18next'
import { Link, useLocation } from "react-router-dom"

const sampleTableData = {
  rows:[
    {
      "name":"Name A",
      "aadhar":"1223-1223-1221",
      "acno":"1213-1241-2121",
      "ifsc":"3242-3423-4322"
    },
    {
      "name": "Name B",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    },
    {
      "name": "Name C",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    },
    {
      "name": "Name D",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    },
    {
      "name": "Name E",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    },
    {
      "name": "Name F",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    },
    {
      "name": "Name G",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    },
    {
      "name": "Name H",
      "aadhar": "1223-1223-1221",
      "acno": "1223-1223-1221",
      "ifsc": "1223-1223-1221"
    }
  ]
}

const ViewProject = ({isLoading=false,resultOk=true,data={}}) => {
  const state  = useLocation()
  const { t } = useTranslation()
  const [service, setService] = useState([]);
  const [showExpander,setShowExpander] = useState(false)
  const [isExpanded, setIsExpanded] = useState(true)
  const [wageSeekers,setWageSeekers] = useState([])
  data = [[]]

  const tableColumns = useMemo(()=>{
    return [
      {
        Header: t("ATM_WAGE_SEEKER_NAME"),
        accessor: "name",
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
        Header: t("ATM_WAGE_SEEKER_ACNO"),
        accessor: "acno",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        }
      },
      {
        Header: t("ATM_WAGE_SEEKER_IFSC"),
        accessor: "ifsc",
        Cell: ({ value, column, row }) => {
          return String(t(value));
        }
      },
    ]
  },[])

  const sampleOptions = [
    {
      "name":"Nipun"
    },
    {
      "name": "Vipul"
    },
    {
      "name": "Shaifali"
    }
  ]

  const propsMobileInboxCards = useMemo(() => {
    if (isLoading) {
      return [];
    }
    
    return data?.map((row) => ({
      [t("ATM_PROJECT_NAME")]: "NA",
      [t("ATM_WIN_CODE")]: "NA",
      [t("ATM_ENGG_INCHARGE")]: "NA",
      [t("ATM_START_DATE")]: "NA",
      [t("REGISTER_STATUS")]: "NA",
    }));
  }, [data]);

  return (
    <React.Fragment>
      <Card className={"card"} style={{ "marginBottom": "1rem" }}>
        <StatusTable>
          <Row className="border-none" label={t("ATM_PROJECT_NAME")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
          {isExpanded ? 
          <div>
            <Row className="border-none" label={t("ATM_WIN_CODE")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
            <Row className="border-none" label={t("ATM_ENGG_INCHARGE")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
            <Row className="border-none" label={t("ATM_START_DATE")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
            {wageSeekers.length > 0 ? <Row className="border-none" label={t("ATM_SEEKERS_COUNT")} text={"NA"} textStyle={{ whiteSpace: "pre" }} /> : null}
            {wageSeekers.length > 0 ? <Row className="border-none" label={t("ATM_LAST_ATTENDENCE_DATE")} text={"NA"} textStyle={{ whiteSpace: "pre" }} /> : null}
            <Row className="border-none" label={t("REGISTER_STATUS")} text={"NA"} textStyle={{ whiteSpace: "pre" }} />
          </div>: null}

          <p>
            <span className='link' style={{ color: "#f47738" }} onClick={() => setIsExpanded(!isExpanded)}>
              {isExpanded ? t("HIDE_REGISTER_DETAILS") : t("VIEW_REGISTER_DETAILS")}
            </span>
          </p>
        </StatusTable>
      </Card>

      <div className='card' style={{"padding":"0px"}}>
        <SearchableDropdown showSearchIcon={true} noBorder={true} placeholder={t("ATM_SEARCH_WAGE_SEEKERS_PLACEHOLDER")} option={sampleOptions} optionKey={"name"} selected={service} select={setService}  />
      </div>

      {/* Render the table here */}
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
          data={sampleTableData.rows}
          totalRecords={sampleTableData.rows.length}
          columns={tableColumns}
          isPaginationRequired={false}
          getCellProps={(cellInfo) => {
            return {
              style: {},
            };
          }}
        />
      </div>
    </React.Fragment>
  )
}

export default ViewProject