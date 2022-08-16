import React,{useEffect, useState} from 'react'
import { Table } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { Dropdown, TextInput, LinkButton, DatePicker, Loader ,DeleteIcon,AddIcon} from "@egovernments/digit-ui-react-components";

const WORKSContractorTable = () => {
  const { t } = useTranslation();
  const GetCell = (value) => <span className="cell-text">{value}</span>;
  const [createdFromDate, setCreatedFromDate] = useState("");
  const [createdToDate, setCreatedToDate] = useState("");
  const [status,setStatus]=useState("");
  const [department,setDepartment]=useState("")
  const [contractorClass,setContractorClass]=useState("")
  const [registrationNumber,setRegistrationNumber]= useState("");

  const data=[{SerialNumber:1,
  Department:"ENGINEERING",
  Fund:"Municipal Fund",
  Function:"Water Supply",
  BudgetHead:"Water Purification",
  CreatedBy:"A.P.Sreenivasulu",
  Owner:"A.P.Sreenivasulu",
  Status:"Craeted",
  TotalAmount:"Rs,10000" }]
  const inboxColumns = () => [
    {
      Header: t("WORKS_S.NO"),
      Cell: ({ row }) => (
              <Link>{row.original?.SerialNumber}</Link>
        ),
      mobileCell: (original) => GetMobCell(original?.SerialNumber),
    },
    {
      Header: t("DEPARTMENT"),
      Cell: () => <Dropdown 
      // option={userUlbs} 
      optionKey={"department"} 
      value={department} 
      selected={department} 
      select={setDepartment} 
      t={t} 
      // disable={userUlbs} 
    />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("REGISTRATION_NO"),
      Cell: () => (
         <TextInput
        value={registrationNumber}
        onChange={(e) =>setRegistrationNumber(e.target.value)}
        // disable={isRenewal}
      />
      ),
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("CATEGORY"),
      Cell: () => <Dropdown 
      // option={userUlbs} 
      optionKey={"status"} 
      value={status} 
      selected={status} 
      select={setStatus} 
      t={t} 
      // disable={userUlbs} 
    />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("CONTRACTOR_CLASS"),
      Cell: () => <Dropdown 
      // option={userUlbs} 
      optionKey={"contractorClass"} 
      value={contractorClass} 
      selected={contractorClass} 
      select={setContractorClass} 
      t={t} 
      // disable={userUlbs} 
    />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("STATUS"),
      Cell: () => <Dropdown 
      // option={userUlbs} 
      optionKey={"name"} 
      value={status} 
      selected={status} 
      select={setStatus} 
      t={t} 
      // disable={userUlbs} 
    />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("FROM_DATE"),
      Cell: () =><DatePicker
                date={createdFromDate}
                onChange={(d) => {
                setCreatedFromDate(d);
                }}
              />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("TO_DATE"),
      Cell: () => <DatePicker
                date={createdToDate}
                onChange={(d) => {
                  setCreatedToDate(d);
                }}
              />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("ACTION"),
      Cell: () => <LinkButton
      label={<DeleteIcon fill={"#494848"} />}
      style={{ margin: "10px" }}
      onClick={(e) =>console.log(e) } 
    />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
  ];
  useEffect(()=>{
    
  },[data])
  const addNewRow=()=>{
    data.push({SerialNumber:2})
    console.log("data",data)
  }
  return (
    <div>
      <Table
      className="contractor-table"
      t={t}
      data={data}
      getCellProps={(cellInfo) => {
        return {
          style: {
            // minWidth: cellInfo.column.Header === t("ES_INBOX_APPLICATION_NO") ? "240px" : "",
            padding: "20px 18px",
            fontSize: "16px",
          },
        }; 
      }}
      // currentPage={currentPage}
      columns={inboxColumns(data)}
      // onPageSizeChange={props.onPageSizeChange}
      // currentPage={props.currentPage}
      // onNextPage={props.onNextPage}
      // onPrevPage={props.onPrevPage}
      // pageSizeLimit={props.pageSizeLimit}
      // onSort={props.onSort}
      // disableSort={props.disableSort}
      // sortParams={props.sortParams}
      // totalRecords={props.totalRecords}
      manualPagination={false}
    />
    <div style={{display:"flex",justifyContent:"center",alignItems:"center"}}>
    <span style={{display:"flex",cursor: "pointer"}}onClick={addNewRow}>
      <AddIcon fill="#fff" styles={{ width: "24px", height: "24px", background:"black",borderRadius:"50%" }} /> Add Line Item
    </span>
    </div>
    </div>
  )
}

export default WORKSContractorTable