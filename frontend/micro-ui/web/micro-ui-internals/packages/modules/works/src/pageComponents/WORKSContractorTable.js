import React,{useEffect, useState} from 'react'
import { Table } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { Dropdown, TextInput, LinkButton, DatePicker, Loader,CardLabelError ,DeleteIcon,AddIcon} from "@egovernments/digit-ui-react-components";
import { useForm, Controller } from "react-hook-form";

const createContractorDetails = () => ({
  contractorCode: "",
  contractorName: "",
  correspondanceAddress: "",
  permenantAddress: "",
  contactPerson: "",
  email: "",
  narration: "",
  mobileNumber: "",
  panNo:"",
  tinNo:"",
  gstNo:"",
  bankName:"",
  IFSCCode:"",
  bankAccountNumber:"",
  PWDApprovalCode:"",
  key: Date.now()
});

const WORKSContractorTable = () => {
  const { t } = useTranslation();
  const GetCell = (value) => <span className="cell-text">{value}</span>;
  const [focusIndex, setFocusIndex] = useState({ index: -1, type: "" });
  const { control, formState: localFormState, watch, setError: setLocalError, clearErrors: clearLocalErrors, setValue, trigger, getValues,register } = useForm();
  const formValue = watch();
  const { errors } = localFormState;
  const [isErrors, setIsErrors] = useState(false);
  const [contractorDetails,setContractorDetails]=useState(createContractorDetails());
  const userUlbs=[{label:"active",value:1},{label:"Inactive",value:2},{label:"Black listed",value:3}]
  const [createdFromDate, setCreatedFromDate] = useState("");
  const [createdToDate, setCreatedToDate] = useState("");
  const [status,setStatus]=useState("");
  const [department,setDepartment]=useState("")
  const [contractorClass,setContractorClass]=useState("")
  const [registrationNumber,setRegistrationNumber]= useState("");
  const [data,setData]=useState([{SerialNumber:1,
    Department:"ENGINEERING",
    RegNo:"123",
    Category:"Supply",
    contractorClass:"Purification",
    Status:"Created",
    FromDate:"",
    ToDate:""}])
  const errorStyle = { width: "70%", marginLeft: "30%", fontSize: "12px", marginTop: "-21px" };

  const inboxColumns = () => [
    {
      Header: t("WORKS_S.NO"),
      Cell: ({ row }) => (
              <Link to={"/digit-ui/employee"}>{row.original?.SerialNumber}</Link>
        ),
      mobileCell: (original) => GetMobCell(original?.SerialNumber),
    },
    {
      Header: t("DEPARTMENT"),
      Cell: () => 
      <div className='field'>
        {/* <Controller
            name="department"
            control={control}
            rules={{ required: true }}
            render={(props) => {
              return (
                  <Dropdown
                      option={userUlbs}
                      // selected={props?.value}
                      optionKey={"name"}
                      t={t}
                      select={props?.onChange}
                      onBlur={props.onBlur}
                  />
              );
            }}
        /> */}
        <Dropdown
          option={userUlbs} 
          optionKey={"department"} 
          value={department} 
          selected={department} 
          select={(e) => {
            onChange(e);
            setDepartment(e);
          }}
        {...register("Department", { required: true})}/>
        {errors && errors?.Department &&(
        <CardLabelError>{t("REQUIRED_FIELD")}</CardLabelError>)}
    </div>,

    //   <Dropdown 
    //   option={userUlbs} 
    //   optionKey={"department"} 
    //   value={department} 
    //   selected={department} 
    //   select={(e) => {
    //     // onChange(value);
    //     setDepartment(e);
    //   }}
    //   // select={setDepartment} 
    //   t={t} 
    //   // disable={userUlbs} 
    // />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("REGISTRATION_NO"),
      Cell: ({row}) => (
    // <div className="field">
    //       <Controller
    //         control={control}
    //         name={"RegNo"}
              //  defaultValue={""}
    //         rules={{ validate: {
    //           pattern: (v) => (/^[a-zA-Z0-9\s]+$/.test(v) ? true : t("ERR_DEFAULT_INPUT_FIELD_MSG")),
    //         } }}
    //         render={(props) => (
    //           <TextInput
    //             value={props.value}
    //             autoFocus={focusIndex.index === row.id && focusIndex.type === "RegNo"}
    //             errorStyle={(localFormState.touched.RegNo && errors?.RegNo?.message) ? true : false}
    //             onChange={(e) => {
    //               console.log(focusIndex,errors)
    //               props.onChange(e.target.value);
    //               setFocusIndex({ index: row.id, type: "RegNo" });
    //             }}
    //             onBlur={(e) => {
    //               setFocusIndex({ index: -1 });
    //               props.onBlur(e);
    //             }}
    //             // disable={isRenewal}
    //           />
    //         )}
    //       />
    //     </div>
    //   <div>
    //    <TextInput
    //   value={registrationNumber}
    //   onChange={(e) =>setRegistrationNumber(e.target.value)}
    // />
    //   <CardLabelError style={errorStyle}>{!registrationNumber ? "Field Required":""}</CardLabelError>
    // </div>
      <div className='field'>
        <TextInput name="requirementNumber" inputRef={register({
          pattern: /^[a-zA-Z0-9_.$@#\/]*$/
          })}
        />
        {errors && errors?.requirementNumber?.type === "pattern" && (
        <CardLabelError>{t(`WORKS_PATTERN_ERR`)}</CardLabelError>)}
      </div>
      ),
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("CATEGORY"),
      Cell: () => 
    //   <Dropdown 
    //   // option={userUlbs} 
    //   optionKey={"status"} 
    //   value={status} 
    //   selected={status} 
    //   select={setStatus} 
    //   t={t} 
    //   // disable={userUlbs} 
    // />
    <div>
      <Dropdown
        option={userUlbs} 
        optionKey={"Category"} 
        value={status} 
        selected={status} 
        select={(e) => {
        onChange(e);
        setStatus(e);
        }}
        {...register("Category")}/>
    </div>
    ,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("CONTRACTOR_CLASS"),
      Cell: () => 
    //   <Dropdown 
    //   // option={userUlbs} 
    //   optionKey={"contractorClass"} 
    //   value={contractorClass} 
    //   selected={contractorClass} 
    //   select={setContractorClass} 
    //   t={t} 
    //   // disable={userUlbs} 
    // />
    <div>
      <Dropdown
        option={userUlbs} 
        optionKey={"contractorClass"} 
        value={contractorClass} 
        selected={contractorClass} 
        select={(e) => {
        onChange(e);
        setContractorClass(e);
        }}
        {...register("contractorClass")}/>
    </div>,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("STATUS"),
      Cell: () => 
    //   <Dropdown 
    //   // option={userUlbs} 
    //   optionKey={"name"} 
    //   value={status} 
    //   selected={status} 
    //   select={setStatus} 
    //   t={t} 
    //   // disable={userUlbs} 
    // />
    <div>
      <Dropdown
        option={userUlbs} 
        optionKey={"status"} 
        value={status} 
        selected={status} 
        select={(e) => {
        onChange(e);
        setStatus(e);
        }}
        {...register("status", { required: true})}/>
      {errors && errors?.status &&(
      <CardLabelError>{t(`REQUIRED_FIELD`)}</CardLabelError>)}
    </div>,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("FROM_DATE"),
      Cell: () =>
      <div>
        <DatePicker
          date={createdFromDate}
          onChange={(d) => {
          setCreatedFromDate(d);
          }}
          {...register("fromDate", { required: true})}
        />
        {errors && errors?.fromDate &&(
          <CardLabelError>{t(`REQUIRED_FIELD`)}</CardLabelError>)}
      </div>,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("TO_DATE"),
      Cell: () => 
      <DatePicker
        date={createdToDate}
        onChange={(d) => {
          setCreatedToDate(d);
        }}
        {...register("toDate", { required: true})}
      />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
    {
      Header: t("ACTION"),
      Cell: ({row}) => 
      <LinkButton
        label={<DeleteIcon fill={"#494848"} />}
        style={{ margin: "10px" }}
        onClick={() => deleteRow(row.id)} 
      />,
      mobileCell: (original) => GetMobCell(t(`ES_PT_COMMON_STATUS_${original?.workflowData?.state?.["state"]}`)),
    },
  ];
  
  useEffect(() => {
    trigger();
  }, []);

  const addNewRow=(e)=>{
    e.preventDefault();
    let idx=data.length+1
    let tableData={SerialNumber:idx++,
      Department:"ENGINEERING",
      RegNo:"123",
      Category:"Supply",
      contractorClass:"Purification",
      Status:"Created",
      FromDate:"",
      ToDate:""};
    let newData=[...data,tableData];
    setData(newData)
  }

  const deleteRow=(recordId)=>{
    let row=[...data];
    row.splice(recordId,1);
    setData(row)
  }
  const result=()=>{
  return  (
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
  )}
  return (
    <div>
      {result()}
    <div style={{display:"flex",justifyContent:"center",alignItems:"center"}}>
    <span style={{display:"flex",cursor: "pointer"}}onClick={addNewRow}>
      <AddIcon fill="#fff" styles={{ width: "24px", height: "24px", background:"black",borderRadius:"50%" }} /> Add Line Item
    </span>
    </div>
    </div>
  )
}

export default WORKSContractorTable