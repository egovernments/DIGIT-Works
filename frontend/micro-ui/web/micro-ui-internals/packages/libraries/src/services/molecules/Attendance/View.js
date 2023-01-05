import AttendanceService from "../../elements/Attendance";

const transformViewDataToApplicationDetails = (data) => {
  console.log('##', data)
  const musterRoll = data.musterRolls[0]
  const RegistrationDetails = {
    title: "ATM_REGISTRATION_DETAILS",
    asSectionHeader: true,
    values: [
      { title: "WORKS_ORG_NAME", value: "Mission Shakti" },
      { title: "REGISTER_ID", value: musterRoll.registerId },
      { title: "REGISTER_NAME", value: "Wall Painting Ward 2" },
    ],
    additionalDetails: {
      table: {
        weekTable: {
          tableHeader: "ATM_ENROLLED_USERS",
          renderTable: true,
        },
      },
      dateRange: {
        title: "Attendance For Week",
        startDate: new Date(),
        endDate: new Date()
      },
    },
  };
  const workflowDetails = {
    isLoading: false,
    error: null,
    isError: false,
    breakLineRequired: false,
    data: {
      timeline: [
        {
          performedAction: "CREATE",
          status: "ES_COMMON_CREATED",
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
          status: "ES_COMMON_CREATED",
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
      nextActions: [
        {
          action: "CHECK",
          roles: "EST_CHECKER,EST_CHECKER",
        },
        {
          action: "REJECT",
          roles: "EST_CHECKER,EST_CHECKER",
        },
        {
          action: "APPROVE",
          roles: "EST_CHECKER,EST_CHECKER",
        },
        {
          action: "EDIT",
          roles: "EST_CHECKER,EST_CHECKER",
        },
      ],
      actionState: {
        auditDetails: {
          createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
          lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
          createdTime: 1663568004997,
          lastModifiedTime: 1663568004997,
        },
        uuid: "67d17040-0c49-40a1-b932-a7b5a5266557",
        tenantId: "pb.amritsar",
        businessServiceId: "52e2c4e0-f12c-4c75-aef3-1535bc8edac0",
        sla: null,
        state: "CREATED",
        applicationStatus: "CREATED",
        docUploadRequired: false,
        isStartState: true,
        isTerminateState: false,
        isStateUpdatable: true,
        actions: [
          {
            auditDetails: {
              createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              createdTime: 1663568004997,
              lastModifiedTime: 1663568004997,
            },
            uuid: "568b7e7d-d88f-4079-bb02-3dc9a37c56ea",
            tenantId: "pb.amritsar",
            currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
            action: "CHECK",
            nextState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
            roles: ["EST_CHECKER"],
            active: true,
          },
          {
            auditDetails: {
              createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              createdTime: 1663568004997,
              lastModifiedTime: 1663568004997,
            },
            uuid: "1a6d9f29-893d-49d9-870f-6e007a6820e8",
            tenantId: "pb.amritsar",
            currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
            action: "REJECT",
            nextState: "af66155b-f5ac-447f-947b-f56539c4d671",
            roles: ["EST_CHECKER"],
            active: true,
          },
          {
            auditDetails: {
              createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              createdTime: 1663568004997,
              lastModifiedTime: 1663568004997,
            },
            uuid: "1a6d9f29-893d-49d9-870f-6e007a6820e8",
            tenantId: "pb.amritsar",
            currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
            action: "APPROVE",
            nextState: "af66155b-f5ac-447f-947b-f56539c4d671",
            roles: ["EST_CHECKER"],
            active: true,
          },
        ],
        nextActions: [
          {
            auditDetails: {
              createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              createdTime: 1663568004997,
              lastModifiedTime: 1663568004997,
            },
            uuid: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
            tenantId: "pb.amritsar",
            businessServiceId: "52e2c4e0-f12c-4c75-aef3-1535bc8edac0",
            sla: null,
            state: "CHECKED",
            applicationStatus: "ATTENDANCE_CHECKED",
            docUploadRequired: false,
            isStartState: false,
            isTerminateState: false,
            isStateUpdatable: true,
            actions: [
              {
                auditDetails: {
                  createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  createdTime: 1663568004997,
                  lastModifiedTime: 1663568004997,
                },
                uuid: "a952bc13-07ef-4384-9214-9c7c3e974ec8",
                tenantId: "pb.amritsar",
                currentState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
                action: "TECHNICALSANCATION",
                nextState: "e41d89f8-0977-4b43-9193-3e17c1257ff6",
                roles: ["EST_TECH_SANC"],
                active: true,
              },
              {
                auditDetails: {
                  createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  createdTime: 1663568004997,
                  lastModifiedTime: 1663568004997,
                },
                uuid: "c788321f-dc5b-4dc8-a6e6-bd78c6a769fb",
                tenantId: "pb.amritsar",
                currentState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
                action: "REJECT",
                nextState: "af66155b-f5ac-447f-947b-f56539c4d671",
                roles: ["EST_TECH_SANC"],
                active: true,
              },
            ],
            assigneeRoles: ["EST_TECH_SANC", "EST_TECH_SANC"],
            action: "CHECK",
            roles: ["EST_CHECKER"],
          },
          {
            auditDetails: {
              createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              createdTime: 1663568004997,
              lastModifiedTime: 1663568004997,
            },
            uuid: "af66155b-f5ac-447f-947b-f56539c4d671",
            tenantId: "pb.amritsar",
            businessServiceId: "52e2c4e0-f12c-4c75-aef3-1535bc8edac0",
            sla: null,
            state: "REJECTED",
            applicationStatus: "ATTENDANCE_REJECTED",
            docUploadRequired: false,
            isStartState: false,
            isTerminateState: false,
            isStateUpdatable: true,
            actions: [
              {
                auditDetails: {
                  createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  createdTime: 1663568004997,
                  lastModifiedTime: 1663568004997,
                },
                uuid: "0250f409-7e07-464d-926e-97ac72457d72",
                tenantId: "pb.amritsar",
                currentState: "af66155b-f5ac-447f-947b-f56539c4d671",
                action: "EDIT",
                nextState: "67d17040-0c49-40a1-b932-a7b5a5266557",
                roles: ["EST_CREATOR"],
                active: true,
              },
            ],
            assigneeRoles: ["EST_CREATOR"],
            action: "REJECT",
            roles: ["EST_CHECKER"],
          },
          {
            auditDetails: {
              createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
              createdTime: 1663568004997,
              lastModifiedTime: 1663568004997,
            },
            uuid: "af66155b-f5ac-447f-947b-f56539c4d671",
            tenantId: "pb.amritsar",
            businessServiceId: "52e2c4e0-f12c-4c75-aef3-1535bc8edac0",
            sla: null,
            state: "APPROVE",
            applicationStatus: "ATTENDANCE_APPROVE",
            docUploadRequired: false,
            isStartState: false,
            isTerminateState: false,
            isStateUpdatable: true,
            actions: [
              {
                auditDetails: {
                  createdBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  lastModifiedBy: "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
                  createdTime: 1663568004997,
                  lastModifiedTime: 1663568004997,
                },
                uuid: "0250f409-7e07-464d-926e-97ac72457d72",
                tenantId: "pb.amritsar",
                currentState: "af66155b-f5ac-447f-947b-f56539c4d671",
                action: "EDIT",
                nextState: "67d17040-0c49-40a1-b932-a7b5a5266557",
                roles: ["EST_CREATOR"],
                active: true,
              },
            ],
            assigneeRoles: ["EST_CREATOR"],
            action: "APPROVE",
            roles: ["EST_CHECKER"],
          },
        ],
        roles: ["EST_CHECKER", "EST_CHECKER"],
      },
      applicationBusinessService: "estimate-approval-2",
      processInstances: [
        {
          id: "e3f890d0-bcb0-4526-afde-1d36d2be91f4",
          tenantId: "pb.amritsar",
          businessService: "estimate-approval-2",
          businessId: "EP/2022-23/11/000160",
          action: "CREATE",
          moduleName: "estimate-service",
          state: {
            auditDetails: null,
            uuid: "67d17040-0c49-40a1-b932-a7b5a5266557",
            tenantId: "pb",
            businessServiceId: "52e2c4e0-f12c-4c75-aef3-1535bc8edac0",
            sla: null,
            state: "CREATED",
            applicationStatus: "CREATED",
            docUploadRequired: false,
            isStartState: true,
            isTerminateState: false,
            isStateUpdatable: null,
            actions: [
              {
                auditDetails: null,
                uuid: "568b7e7d-d88f-4079-bb02-3dc9a37c56ea",
                tenantId: "pb",
                currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
                action: "CHECK",
                nextState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
                roles: ["EST_CHECKER"],
                active: null,
              },
              {
                auditDetails: null,
                uuid: "568b7e7d-d88f-4079-bb02-3dc9a37c56ea",
                tenantId: "pb",
                currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
                action: "APPROVE",
                nextState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
                roles: ["EST_CHECKER"],
                active: null,
              },
              {
                auditDetails: null,
                uuid: "1a6d9f29-893d-49d9-870f-6e007a6820e8",
                tenantId: "pb",
                currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
                action: "REJECT",
                nextState: "af66155b-f5ac-447f-947b-f56539c4d671",
                roles: ["EST_CHECKER"],
                active: null,
              },
            ],
          },
          comment: "",
          documents: null,
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
          nextActions: [
            {
              auditDetails: null,
              uuid: "568b7e7d-d88f-4079-bb02-3dc9a37c56ea",
              tenantId: "pb",
              currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
              action: "CHECK",
              nextState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
              roles: ["EST_CHECKER"],
              active: null,
            },
            {
              auditDetails: null,
              uuid: "568b7e7d-d88f-4079-bb02-3dc9a37c56ea",
              tenantId: "pb",
              currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
              action: "APPROVE",
              nextState: "e970bdf2-a968-4be5-b0fe-bc6584e62829",
              roles: ["EST_CHECKER"],
              active: null,
            },
            {
              auditDetails: null,
              uuid: "1a6d9f29-893d-49d9-870f-6e007a6820e8",
              tenantId: "pb",
              currentState: "67d17040-0c49-40a1-b932-a7b5a5266557",
              action: "REJECT",
              nextState: "af66155b-f5ac-447f-947b-f56539c4d671",
              roles: ["EST_CHECKER"],
              active: null,
            },
          ],
          stateSla: null,
          businesssServiceSla: 431977852,
          previousStatus: null,
          entity: null,
          auditDetails: {
            createdBy: "be99b2c2-5780-4b1c-8e41-e3f8a972ebda",
            lastModifiedBy: "be99b2c2-5780-4b1c-8e41-e3f8a972ebda",
            createdTime: 1669175470551,
            lastModifiedTime: 1669175470551,
          },
          rating: 0,
          escalated: false,
        },
      ],
    },
  };
  const applicationDetails = { applicationDetails: [RegistrationDetails] };

  return {
    applicationDetails,
    applicationData: { regNo: 111, HosName: "Name", DOR: "10-10-2020" }, //dummy data
    workflowDetails,
  }
};

export const fetchAttendanceDetails = async (tenantId, searchParams) => {
  try {
    const response = await AttendanceService.search(tenantId, searchParams);
    return transformViewDataToApplicationDetails(response)
  } catch (error) {
      throw new Error(error?.response?.data?.Errors[0].message);
  }
};

const dummyResponse = {
  "ResponseInfo": {
    "apiId": "asset-services",
    "ver": null,
    "ts": null,
    "resMsgId": "uief87324",
    "msgId": "search with from and to values",
    "status": "successful"
  },
  "musterRolls": [
    {
      "id": null,
      "tenantId": "pb.amritsar",
      "musterRollNumber": null,
      "registerId": "56shcwgdt227",
      "status": "ACTIVE",
      "musterRollStatus": null,
      "startDate": 1669919400000,
      "endDate": 1670697000000,
      "individualEntries": [
        {
          "id": null,
          "individualId": "123e4567-e89b-12d3-a456-426614174444",
          "totalAttendance": 2,
          "attendanceEntries": [
            {
              "id": null,
              "time": 1669919400000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670005800000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670092200000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670178600000,
              "attendance": 0.5,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670265000000,
              "attendance": 0.5,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670351400000,
              "attendance": 1,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670437800000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670524200000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670610600000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670697000000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            }
          ],
          "additionalDetails": {
            "code": "Skill 1",
            "value": "Unskilled"
          },
          "auditDetails": {
            "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
            "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
            "createdTime": 1672809357044,
            "lastModifiedTime": 1672809357044
          }
        },
        {
          "id": null,
          "individualId": "123e4567-e89b-12d3-a456-426614174445",
          "totalAttendance": 1,
          "attendanceEntries": [
            {
              "id": null,
              "time": 1669919400000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670005800000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670092200000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670178600000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670265000000,
              "attendance": 1,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670351400000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670437800000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670524200000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670610600000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            },
            {
              "id": null,
              "time": 1670697000000,
              "attendance": 0,
              "auditDetails": {
                "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
                "createdTime": 1672809357044,
                "lastModifiedTime": 1672809357044
              },
              "additionalDetails": null
            }
          ],
          "additionalDetails": {
            "code": "Skill 1",
            "value": "Unskilled"
          },
          "auditDetails": {
            "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
            "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
            "createdTime": 1672809357044,
            "lastModifiedTime": 1672809357044
          }
        }
      ],
      "additionalDetails": null,
      "auditDetails": {
        "createdBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
        "lastModifiedBy": "81a1988f-9072-4ff9-8aa4-eee5cf10ba57",
        "createdTime": 1672809357044,
        "lastModifiedTime": 1672809357044
      }
    }
  ],
  "count": null
}