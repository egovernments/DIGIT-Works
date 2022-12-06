import {
  ActionBar,
  Card,
  CheckPoint,
  ConnectingCheckPoints,
  FormComposer,
  Header,
  CardSectionHeader,
  Menu,
  SubmitBar,
  Table,
} from "@egovernments/digit-ui-react-components";
import React, { Fragment, useCallback, useRef, useState } from "react";
import { useTranslation } from "react-i18next";
import TLCaption from "../../../../../templates/ApplicationDetails/components/TLCaption";
import AttendanceDateRange from "../../../pageComponents/AttendanceDateRange";

const ViewAttendance = () => {
  const { t } = useTranslation();
  const [localSearchParams, setLocalSearchParams] = useState(() => ({}));
  const [displayMenu, setDisplayMenu] = useState(false);
  const menuRef = useRef();

  const handleChange = useCallback((data) => {
    setLocalSearchParams(() => ({ ...data }));
  }, []);

  const closeMenu = () => {
    setDisplayMenu(false);
  };

  Digit.Hooks.useClickOutside(menuRef, closeMenu, displayMenu);

  const actionULB = [
    {
      name: "FORWARD",
    },
    {
      name: "REJECT_ESTIMATE",
    },
    {
      name: "MODIFY_ESTIMATE",
    },
    {
      name: "APPROVE_ESTIMATE",
    },
  ];

  const convertEpochToDateDMY = (dateEpoch) => {
    if (dateEpoch == null || dateEpoch == undefined || dateEpoch == "") {
      return "NA";
    }
    const dateFromApi = new Date(dateEpoch);
    let month = dateFromApi.getMonth() + 1;
    let day = dateFromApi.getDate();
    let year = dateFromApi.getFullYear();
    month = (month > 9 ? "" : "0") + month;
    day = (day > 9 ? "" : "0") + day;
    return `${day}/${month}/${year}`;
  };

  const getTimelineCaptions = (checkpoint) => {
    if (checkpoint.state === "OPEN" || (checkpoint.status === "INITIATED" && !window.location.href.includes("/obps/"))) {
      const caption = {
        date: convertEpochToDateDMY(applicationData?.auditDetails?.createdTime),
        source: applicationData?.channel || "",
      };
      return <TLCaption data={caption} />;
    } else if (window.location.href.includes("/obps/") || window.location.href.includes("/noc/") || window.location.href.includes("/ws/")) {
      //From BE side assigneeMobileNumber is masked/unmasked with connectionHoldersMobileNumber and not assigneeMobileNumber
      const privacy = { uuid: checkpoint?.assignes?.[0]?.uuid, fieldName: ["connectionHoldersMobileNumber"], model: "WaterConnectionOwner" };
      const caption = {
        date: checkpoint?.auditDetails?.lastModified,
        name: checkpoint?.assignes?.[0]?.name,
        mobileNumber:
          applicationData?.processInstance?.assignes?.[0]?.uuid === checkpoint?.assignes?.[0]?.uuid &&
          applicationData?.processInstance?.assignes?.[0]?.mobileNumber
            ? applicationData?.processInstance?.assignes?.[0]?.mobileNumber
            : checkpoint?.assignes?.[0]?.mobileNumber,
        comment: t(checkpoint?.comment),
        wfComment: checkpoint.wfComment,
        thumbnailsToShow: checkpoint?.thumbnailsToShow,
      };
      return <TLCaption data={caption} OpenImage={OpenImage} privacy={privacy} />;
    } else {
      const caption = {
        date: `${Digit.DateUtils?.ConvertTimestampToDate(checkpoint.auditDetails.lastModifiedEpoch)} ${Digit.DateUtils?.ConvertEpochToTimeInHours(
          checkpoint.auditDetails.lastModifiedEpoch
        )} ${Digit.DateUtils?.getDayfromTimeStamp(checkpoint.auditDetails.lastModifiedEpoch)}`,
        // name: checkpoint?.assigner?.name,
        name: checkpoint?.assignes?.[0]?.name,
        // mobileNumber: checkpoint?.assigner?.mobileNumber,
        wfComment: checkpoint?.wfComment,
        mobileNumber: checkpoint?.assignes?.[0]?.mobileNumber,
      };

      return <TLCaption data={caption} />;
    }
  };

  const workflowDetails = {
    data: {
      timeline: [
        {
          performedAction: "CREATE",
          status: "CREATED",
          state: "CREATED",
          assigner: {
            id: 109,
            userName: "Nipsyyyy",
            name: "Nipun ",
            type: "EMPLOYEE",
            mobileNumber: "9667076655",
            emailId: "",
            roles: [
              {
                id: null,
                name: "Employee",
                code: "EMPLOYEE",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST CREATOR",
                code: "EST_CREATOR",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST_CHECKER",
                code: "EST_CHECKER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "LOI APPROVER",
                code: "LOI_APPROVER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST TECH SANC",
                code: "EST_TECH_SANC",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST FIN SANC",
                code: "EST_FIN_SANC",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "LOI CREATOR",
                code: "LOI_CREATOR",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "SUPER USER",
                code: "SUPERUSER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST TECH SANC",
                code: "EST_ADMIN_SANC",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "LOI CHECKER",
                code: "LOI_CHECKER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "HRMS Admin",
                code: "HRMS_ADMIN",
                tenantId: "pb.amritsar",
              },
            ],
            tenantId: "pb.amritsar",
            uuid: "be99b2c2-5780-4b1c-8e41-e3f8a972ebda",
          },
          rating: 0,
          wfComment: [],
          wfDocuments: null,
          thumbnailsToShow: {},
          assignes: [
            {
              id: 111,
              userName: "EMP-107-000011",
              name: "Estimate Checker",
              type: "EMPLOYEE",
              mobileNumber: "8877665544",
              emailId: null,
              roles: [
                {
                  id: null,
                  name: "EST_CHECKER",
                  code: "EST_CHECKER",
                  tenantId: "pb.amritsar",
                },
                {
                  id: null,
                  name: "Employee",
                  code: "EMPLOYEE",
                  tenantId: "pb.amritsar",
                },
              ],
              tenantId: "pb.amritsar",
              uuid: "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
            },
          ],
          caption: [
            {
              name: "Estimate Checker",
              mobileNumber: "8877665544",
            },
          ],
          auditDetails: {
            created: "23/11/2022",
            lastModified: "23/11/2022",
            lastModifiedEpoch: 1669175470551,
          },
          timeLineActions: [],
        },
        {
          performedAction: "CREATE",
          status: "CREATED",
          state: "CREATED",
          assigner: {
            id: 109,
            userName: "Nipsyyyy",
            name: "Nipun ",
            type: "EMPLOYEE",
            mobileNumber: "9667076655",
            emailId: "",
            roles: [
              {
                id: null,
                name: "Employee",
                code: "EMPLOYEE",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST CREATOR",
                code: "EST_CREATOR",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST_CHECKER",
                code: "EST_CHECKER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "LOI APPROVER",
                code: "LOI_APPROVER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST TECH SANC",
                code: "EST_TECH_SANC",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST FIN SANC",
                code: "EST_FIN_SANC",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "LOI CREATOR",
                code: "LOI_CREATOR",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "SUPER USER",
                code: "SUPERUSER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "EST TECH SANC",
                code: "EST_ADMIN_SANC",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "LOI CHECKER",
                code: "LOI_CHECKER",
                tenantId: "pb.amritsar",
              },
              {
                id: null,
                name: "HRMS Admin",
                code: "HRMS_ADMIN",
                tenantId: "pb.amritsar",
              },
            ],
            tenantId: "pb.amritsar",
            uuid: "be99b2c2-5780-4b1c-8e41-e3f8a972ebda",
          },
          rating: 0,
          wfComment: [],
          wfDocuments: null,
          thumbnailsToShow: {},
          assignes: [
            {
              id: 111,
              userName: "EMP-107-000011",
              name: "Estimate Checker",
              type: "EMPLOYEE",
              mobileNumber: "8877665544",
              emailId: null,
              roles: [
                {
                  id: null,
                  name: "EST_CHECKER",
                  code: "EST_CHECKER",
                  tenantId: "pb.amritsar",
                },
                {
                  id: null,
                  name: "Employee",
                  code: "EMPLOYEE",
                  tenantId: "pb.amritsar",
                },
              ],
              tenantId: "pb.amritsar",
              uuid: "88bd1b70-dd6d-45f7-bcf7-5aa7a6fae7d9",
            },
          ],
          caption: [
            {
              name: "Estimate Checker",
              mobileNumber: "8877665544",
            },
          ],
          auditDetails: {
            created: "23/11/2022",
            lastModified: "23/11/2022",
            lastModifiedEpoch: 1669175470551,
          },
          timeLineActions: [],
        },
      ],
    },
  };

  function onActionSelect(action) {
    console.log(action);
    // if(action?.name==="FORWARD"){
    //     setShowModal(true)
    // }
    // if(action?.name==="REJECT_ESTIMATE"){
    //     setShowRejectModal(true)
    // }
    // if(action?.name==="MODIFY_ESTIMATE"){
    //     history.push("/works-ui/employee/works/modify-estimate",{ tenantId,estimateNumber })
    // }
    // if(action?.name==="APPROVE_ESTIMATE"){
    // }
  }

  const configs = [
    {
      head: "Register Details",
      body: [
        {
          label: "Organisation Name",
          isMandatory: false,
          type: "custom",
          populators: {
            name: "organisation_name",
            customProps: { t, optionKey: "i18nKey" },
            component: (props, customProps) => (
              <div>
                <p>Mission Shakti</p>
              </div>
            ),
          },
        },
        {
          label: "Register ID",
          isMandatory: false,
          type: "custom",
          populators: {
            name: "register_id",
            customProps: { t, optionKey: "i18nKey" },
            component: (props, customProps) => (
              <div>
                <p>ABC-ADCC</p>
              </div>
            ),
          },
        },
        {
          label: "Register Name",
          isMandatory: false,
          type: "custom",
          populators: {
            name: "register_name",
            customProps: { t, optionKey: "i18nKey" },
            component: (props, customProps) => (
              <div>
                <p>Wall Painting Ward 2</p>
              </div>
            ),
          },
        },
        {
          label: "Attendance For Week",
          isMandatory: false,
          type: "custom",
          populators: {
            name: "register_name",
            customProps: { t, optionKey: "i18nKey" },
            component: (props, customProps) => (
              <AttendanceDateRange
                t={t}
                values={localSearchParams?.range}
                onFilterChange={handleChange}
                filterLabel="MARK_ATTENDENCE_FOR_WEEK"
                classname="attendance-date-range"
                labelRequired={false}
              />
            ),
          },
        },
      ],
    },
  ];

  const onSubmit = (data) => {
    console.log("Data", data);
  };

  const data = [
    {
      SNo: 1,
      Registration_ID: "123455",
      Name_of_the_Individual: "Rashmi Ranjan",
      Father_Guardian_Name: "Rashmi Ranjan",
    },
    {
      SNo: 2,
      Registration_ID: "123455",
      Name_of_the_Individual: "Rashmi Ranjan",
      Father_Guardian_Name: "Rashmi Ranjan",
    },
    {
      SNo: 3,
      Registration_ID: "123455",
      Name_of_the_Individual: "Rashmi Ranjan",
      Father_Guardian_Name: "Rashmi Ranjan",
    },
  ];

  const columns = [
    {
      Header: "S. No",
      accessor: "SNo",
    },
    {
      Header: "Registration ID",
      accessor: "Registration_ID",
    },
    {
      Header: "Name of the Individual",
      accessor: "Name_of_the_Individual",
    },
    {
      Header: "Father / Guardian' Name",
      accessor: "Father_Guardian_Name",
    },
  ];

  const cardStyles = {
    "margin-left": "0px !important",
  };

  return (
    <React.Fragment>
      <Header styles={{ marginLeft: "14px" }}>View Attendance</Header>
      <Card>
        <FormComposer
          heading={t("")}
          label={false}
          config={configs}
          onSubmit={onSubmit}
          submitInForm={false}
          fieldStyle={{ marginRight: 0 }}
          inline={false}
          className="card-no-margin"
        />
        <CardSectionHeader>Users Enrolled</CardSectionHeader>
        <Table
          t={t}
          data={data}
          columns={columns}
          className="table attendance-view-table"
          getCellProps={(cellInfo) => {
            return {
              style: {
                padding: "8px 8px",
                fontSize: "10px",
                border: "1px solid grey",
              },
            };
          }}
          totalRecords={data.length}
        />
        <CardSectionHeader>Workflow History</CardSectionHeader>
        <React.Fragment>
          <ActionBar>
            {displayMenu ? <Menu localeKeyPrefix={"WORKS"} options={actionULB} optionKey={"name"} t={t} onSelect={onActionSelect} /> : null}
            <SubmitBar ref={menuRef} label={t("WF_TAKE_ACTION")} onSubmit={() => setDisplayMenu(!displayMenu)} />
          </ActionBar>
          {workflowDetails?.data?.timeline && workflowDetails?.data?.timeline?.length === 1 ? (
            <CheckPoint
              isCompleted={true}
              label={t(`${workflowDetails?.data?.timeline[0]?.state}`)}
              customChild={getTimelineCaptions(workflowDetails?.data?.timeline[0])}
            />
          ) : (
            <ConnectingCheckPoints>
              {workflowDetails?.data?.timeline &&
                workflowDetails?.data?.timeline.map((checkpoint, index, arr) => {
                  return (
                    <React.Fragment key={index}>
                      <CheckPoint
                        keyValue={index}
                        isCompleted={index === 0}
                        info={checkpoint.comment}
                        label={t(`${checkpoint?.performedAction === "EDIT" ? `${checkpoint?.performedAction}_ACTION` : checkpoint?.["status"]}`)}
                        customChild={getTimelineCaptions(checkpoint)}
                      />
                    </React.Fragment>
                  );
                })}
            </ConnectingCheckPoints>
          )}
        </React.Fragment>
      </Card>
    </React.Fragment>
  );
};

export default ViewAttendance;
