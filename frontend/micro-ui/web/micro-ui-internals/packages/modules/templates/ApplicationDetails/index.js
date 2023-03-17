import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useQueryClient } from "react-query";
import { format } from "date-fns";

import { Loader } from "@egovernments/digit-ui-react-components";

import ActionModal from "./Modal";

import { useHistory, useParams } from "react-router-dom";
import ApplicationDetailsContent from "./components/ApplicationDetailsContent";
import ApplicationDetailsToast from "./components/ApplicationDetailsToast";
import ApplicationDetailsActionBar from "./components/ApplicationDetailsActionBar";
import ApplicationDetailsWarningPopup from "./components/ApplicationDetailsWarningPopup";

const ApplicationDetails = (props) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const state = Digit.ULBService.getStateId();
  const { t } = useTranslation();
  const history = useHistory();
  let { id: applicationNumber } = useParams();
  const [displayMenu, setDisplayMenu] = useState(false);
  const [selectedAction, setSelectedAction] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [isEnableLoader, setIsEnableLoader] = useState(false);
  const [isWarningPop, setWarningPopUp] = useState(false);
  const [modify, setModify] = useState(false);
  const [saveAttendanceState, setSaveAttendanceState] = useState({ displaySave : false, updatePayload: []})

  const {
    applicationDetails,
    showToast,
    setShowToast,
    isLoading,
    isDataLoading,
    applicationData,
    mutate,
    nocMutation,
    workflowDetails,
    businessService,
    closeToast,
    moduleCode,
    timelineStatusPrefix,
    forcedActionPrefix,
    statusAttribute,
    ActionBarStyle,
    MenuStyle,
    paymentsList,
    showTimeLine = true,
    oldValue,
    isInfoLabel = false,
    clearDataDetails,
    noBoxShadow,
    sectionHeadStyle,
    showActionBar = true,
    setshowEditTitle = () => {}
  } = props;
  
  const [billingCallData,setBillingCallData] = useState(null)
  
  const reqCriteria = {
    url: "/expensebilling/demand/v1/_create",
    params:{},
    body: { "musterRolls": billingCallData?.mustorObject,"count":1},
    // body: {
    //   musterRoll: {
    //     "id": "d844b2d0-2998-4e3a-86f6-539b75935c27",
    //     "tenantId": "pg.citya",
    //     "musterRollNumber": "MR/2022-23/03/16/000550",
    //     "registerId": "0f25ffea-325b-4371-8041-fa0d136aa4f4",
    //     "status": "ACTIVE",
    //     "musterRollStatus": "APPROVED",
    //     "startDate": 1672597800000,
    //     "endDate": 1673116200000,
    //     "individualEntries": [
    //       {
    //         "id": "167edc52-2aa4-41f1-b7a6-9f83beb8209f",
    //         "individualId": "40147cb7-a233-454d-bca9-bbf3444a0102",
    //         "actualTotalAttendance": 3.5,
    //         "modifiedTotalAttendance": null,
    //         "attendanceEntries": [
    //           {
    //             "id": "57991737-7488-433e-8b8b-a605cecbd5b9",
    //             "time": 1672597800000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "0c9ac774-752c-4bb0-9f34-d1afea9df772",
    //             "time": 1672684200000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "dbd12d9a-bbb1-4f53-9f63-876fbe7bf3ef",
    //             "time": 1672770600000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "ae62e765-8f0a-4ae2-985c-417065bcee6a",
    //             "time": 1672857000000,
    //             "attendance": 0.5,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "50fd4d93-fffd-4eb6-89ba-3118311b3e7e",
    //             "time": 1672943400000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "ef8c2b90-ab47-49bf-b3e2-2030da844131",
    //             "time": 1673029800000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "6c8fe193-b076-4ffe-a1e6-14d38579f577",
    //             "time": 1673116200000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           }
    //         ],
    //         "additionalDetails": {
    //           "userId": "IND-2023-03-16-001305",
    //           "userName": "CK",
    //           "fatherName": "Ganguly Sr",
    //           "skillValue": "",
    //           "bankDetails": "880182873839-SBIN0001237",
    //           "aadharNumber": "XXXXXXXX8203"
    //         },
    //         "auditDetails": {
    //           "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //           "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //           "createdTime": 1678933535209,
    //           "lastModifiedTime": 1678981893971
    //         }
    //       },
    //       {
    //         "id": "3ba1c5ce-ae70-4a08-aba5-4d8b84b95ab9",
    //         "individualId": "e5921fd4-0960-4bbb-aec8-d73e75d2a20b",
    //         "actualTotalAttendance": 1,
    //         "modifiedTotalAttendance": null,
    //         "attendanceEntries": [
    //           {
    //             "id": "3499e4bd-3a93-4d9c-ab4f-1d760039e5e8",
    //             "time": 1672597800000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "47dba8d1-3349-4adc-b98b-98a9aae47708",
    //             "time": 1672684200000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "ed20d82f-b432-4893-ae9e-57a2261f141f",
    //             "time": 1672770600000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "4c46e292-cf65-4acd-9142-2119ea07e47f",
    //             "time": 1672857000000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "1e63be7b-b900-464d-a7d3-212fe511e691",
    //             "time": 1672943400000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "ef888e65-125b-4d66-a66a-dfd6446d6dcb",
    //             "time": 1673029800000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "37827a3a-7391-4e2b-adf8-99e07078b71d",
    //             "time": 1673116200000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           }
    //         ],
    //         "additionalDetails": {
    //           "userId": "IND-2023-03-16-001307",
    //           "userName": "Nirbhay",
    //           "fatherName": "Ganguly Sr",
    //           "skillValue": "",
    //           "bankDetails": "880182873839-SBIN0001237",
    //           "aadharNumber": "XXXXXXXX8203"
    //         },
    //         "auditDetails": {
    //           "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //           "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //           "createdTime": 1678933535209,
    //           "lastModifiedTime": 1678981893971
    //         }
    //       },
    //       {
    //         "id": "27357a4e-57de-411a-977e-b32f22d20a36",
    //         "individualId": "dfc686ef-da52-4166-b49d-d90b52944ff4",
    //         "actualTotalAttendance": 5,
    //         "modifiedTotalAttendance": null,
    //         "attendanceEntries": [
    //           {
    //             "id": "60010d06-6a32-484f-8d27-bd62184fb0bb",
    //             "time": 1672597800000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "f78ea782-f6c9-4f05-930f-910b13c38495",
    //             "time": 1672684200000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "30e319cf-ae60-48b1-a972-a36c2c4f951b",
    //             "time": 1672770600000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "d97ce680-d2ce-43a1-9b46-1d6a6a77c6ed",
    //             "time": 1672857000000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "37f7018e-6864-4f68-bca7-02e029e5ab4e",
    //             "time": 1672943400000,
    //             "attendance": 0.5,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "aa7ff945-c6d3-4f12-b6f5-38bc51965ef1",
    //             "time": 1673029800000,
    //             "attendance": 0.5,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "165ec9bf-fcdf-4861-ae69-08f86e0639d2",
    //             "time": 1673116200000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           }
    //         ],
    //         "additionalDetails": {
    //           "userId": "IND-2023-03-16-001304",
    //           "userName": "Jagan",
    //           "fatherName": "Ganguly Sr",
    //           "skillValue": "",
    //           "bankDetails": "880182873839-SBIN0001237",
    //           "aadharNumber": "XXXXXXXX8203"
    //         },
    //         "auditDetails": {
    //           "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //           "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //           "createdTime": 1678933535209,
    //           "lastModifiedTime": 1678981893971
    //         }
    //       },
    //       {
    //         "id": "28390480-8648-4ca1-b502-f70fb59d5e24",
    //         "individualId": "b326dbbe-401b-4ad4-afaa-0f2538b0a752",
    //         "actualTotalAttendance": 3.5,
    //         "modifiedTotalAttendance": null,
    //         "attendanceEntries": [
    //           {
    //             "id": "5d9927ba-9e9b-4448-b5ee-ad27d3dd386c",
    //             "time": 1672597800000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "db86fc43-b313-4b98-88d9-68d3a2caeb40",
    //             "time": 1672684200000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "88077898-9eb8-4b23-a1ef-717de6937ee6",
    //             "time": 1672770600000,
    //             "attendance": 1,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "9075cdf4-e151-4e4f-b4d7-d5a9725698d2",
    //             "time": 1672857000000,
    //             "attendance": 0.5,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "916e34bb-ef5d-4f4f-97f2-81bd86382730",
    //             "time": 1672943400000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "2c94fed4-e8ba-46c3-8bb4-6d7f1afdace3",
    //             "time": 1673029800000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           },
    //           {
    //             "id": "fdf3f35b-6e88-4f59-b31c-cadd01d44991",
    //             "time": 1673116200000,
    //             "attendance": 0,
    //             "auditDetails": {
    //               "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //               "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //               "createdTime": 1678933535209,
    //               "lastModifiedTime": 1678981893971
    //             },
    //             "additionalDetails": null
    //           }
    //         ],
    //         "additionalDetails": {
    //           "userId": "IND-2023-03-16-001306",
    //           "userName": "Anitha",
    //           "fatherName": "Ganguly Sr",
    //           "skillValue": "",
    //           "bankDetails": "880182873839-SBIN0001237",
    //           "aadharNumber": "XXXXXXXX8203"
    //         },
    //         "auditDetails": {
    //           "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //           "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //           "createdTime": 1678933535209,
    //           "lastModifiedTime": 1678981893971
    //         }
    //       }
    //     ],
    //     "additionalDetails": {
    //       "projectName": "Building Walls",
    //       "assignee": "John Doe",
    //       "amount": 5000,
    //       "billType": "Work Order",
    //       "projectId": "PR/2022-23/03/001111",
    //       "orgName": "Jagan Constructions",
    //       "contractId": "WIN-111/2022-0001",
    //       "attendanceRegisterNo": "WR/2022-23/01/12/000176",
    //       "attendanceRegisterName": "Construct Roads"
    //     },
    //     "auditDetails": {
    //       "createdBy": "a16a335b-5508-42f2-8c3f-f717ff329e3a",
    //       "lastModifiedBy": "7e38d2ca-f5f3-4b58-9e46-0ae54ef82861",
    //       "createdTime": 1678933535209,
    //       "lastModifiedTime": 1678981893971
    //     },
    //     "processInstance": {
    //       "id": "f49ac562-a619-4a5b-a254-975a4060b57d",
    //       "tenantId": "pg.citya",
    //       "businessService": "muster-roll-approval",
    //       "businessId": "MR/2022-23/03/16/000550",
    //       "action": "APPROVE",
    //       "moduleName": "muster-roll-services",
    //       "state": {
    //         "auditDetails": {
    //           "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
    //           "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
    //           "createdTime": 1678878326276,
    //           "lastModifiedTime": 1678878326276
    //         },
    //         "uuid": "5b8566a9-cc5e-4f57-89b1-270051855b13",
    //         "tenantId": "pg",
    //         "businessServiceId": "5195a75e-681c-42c5-8d2b-8013f9f9b81a",
    //         "sla": null,
    //         "state": "APPROVED",
    //         "applicationStatus": "APPROVED",
    //         "docUploadRequired": false,
    //         "isStartState": false,
    //         "isTerminateState": false,
    //         "isStateUpdatable": false,
    //         "actions": [
    //           {
    //             "auditDetails": {
    //               "createdBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
    //               "lastModifiedBy": "7e46e32c-187c-4fb4-9d6b-1ac70fa8f011",
    //               "createdTime": 1678878326276,
    //               "lastModifiedTime": 1678878326276
    //             },
    //             "uuid": "4160d78e-a4dd-4297-a9e3-d4bb8c222fa3",
    //             "tenantId": "pg",
    //             "currentState": "5b8566a9-cc5e-4f57-89b1-270051855b13",
    //             "action": "BILLCREATE",
    //             "nextState": "1ddc8e78-a41f-48f7-9eba-8afbe4617123",
    //             "roles": [
    //               "BILL_CREATOR",
    //               "BILL_VIEWER"
    //             ]
    //           }
    //         ]
    //       },
    //       "comment": "APPROVE done",
    //       "documents": null,
    //       "assignes": null
    //     }
    //   }
    // },
    config: {
      enabled: billingCallData ? true : false,
      // enabled: true
    },
  }

  const { isLoading:isBillLoading, data:billData, revalidate, isFetching:isBillFetching } = Digit.Hooks.useCustomAPIHook(reqCriteria);

  useEffect(() => {
    if (billData?.BillDemands?.[0]?.billNumber ){
      history.push(`/${window.contextPath}/employee/attendencemgmt/response?musterRollNumber=${billingCallData?.mustorObject?.musterRolls?.[0]?.musterRollNumber}`, {...billingCallData?.responseScreenState,billNo:"BS/122-3123-13"})
    }
  }, [billData])
    

  useEffect(() => {
    if (showToast) {
      workflowDetails.revalidate();
    }
  }, [showToast]);

  function onActionSelect(action) {
    
    if (action) {
      if(action?.isToast){
        setShowToast({ key: "error", error: { message: action?.toastMessage } });
        setTimeout(closeToast, 5000);
      }
      else if (action?.isWarningPopUp) {
        setWarningPopUp(true);
      } else if (action?.redirectionUrll) {
      //here do the loi edit upon rejection
        if (action?.redirectionUrll?.action === "EDIT_LOI_APPLICATION") {
          history.push(`${action?.redirectionUrll?.pathname}`, { data: action?.redirectionUrll?.state });
        }
        if (action?.redirectionUrll?.action === "EDIT_ESTIMATE_APPLICATION") {
          history.push(`${action?.redirectionUrll?.pathname}`,{ data: action?.redirectionUrll?.state });
        }
        
      } else if (!action?.redirectionUrl) {
        if(action?.action === 'EDIT') {
          setModify(true)
          setshowEditTitle(true)
        }
        else setShowModal(true);
      } else {
        history.push({
          pathname: action.redirectionUrl?.pathname,
          state: { ...action.redirectionUrl?.state },
        });
      }
    }
    setSelectedAction(action);
    setDisplayMenu(false);
  }

  const queryClient = useQueryClient();

  const closeModal = () => {
    setSelectedAction(null);
    setShowModal(false);
  };

  const closeWarningPopup = () => {
    setWarningPopUp(false);
  };

  const getResponseHeader = (action) => {

    if(action?.includes("CHECK")){
      return t("WORKS_LOI_RESPONSE_FORWARD_HEADER")
    } else if (action?.includes("APPROVE")){
     return  t("WORKS_LOI_RESPONSE_APPROVE_HEADER")
    }else if(action?.includes("REJECT")){
      return t("WORKS_LOI_RESPONSE_REJECT_HEADER")
    }
  }

  const getResponseMessage = (action,updatedLOI) => {
  
    if (action?.includes("CHECK")) {
      return t("WORKS_LOI_RESPONSE_MESSAGE_CHECK", { loiNumber: updatedLOI?.letterOfIndentNumber,name:"Nipun",designation:"SE" })
    } else if (action?.includes("APPROVE")) {
      return t("WORKS_LOI_RESPONSE_MESSAGE_APPROVE", { loiNumber: updatedLOI?.letterOfIndentNumber })
    } else if (action?.includes("REJECT")) {
      return t("WORKS_LOI_RESPONSE_MESSAGE_REJECT", { loiNumber: updatedLOI?.letterOfIndentNumber })
    }
  }

  const getEstimateResponseHeader = (action) => {

    if(action?.includes("CHECK")){
      return t("WORKS_ESTIMATE_RESPONSE_FORWARD_HEADER")
    } else if (action?.includes("TECHNICALSANCATION")){
     return  t("WORKS_ESTIMATE_RESPONSE_FORWARD_HEADER")
    }else if (action?.includes("ADMINSANCTION")){
      return  t("WORKS_ESTIMATE_RESPONSE_APPROVE_HEADER")
    }else if(action?.includes("REJECT")){
      return t("WORKS_ESTIMATE_RESPONSE_REJECT_HEADER")
    }
  }

  const getEstimateResponseMessage = (action,updatedEstimate) => {
  
    if (action?.includes("CHECK")) {
      return t("WORKS_ESTIMATE_RESPONSE_MESSAGE_CHECK", { estimateNumber: updatedEstimate?.estimateNumber,Name:"Super",Designation:"SE",Department:"Health" })
    } else if (action?.includes("TECHNICALSANCATION")) {
      return t("WORKS_ESTIMATE_RESPONSE_MESSAGE_CHECK", { estimateNumber: updatedEstimate?.estimateNumber,Name:"Super",Designation:"SE",Department:"Health" })
    } else if (action?.includes("ADMINSANCTION")) {
      return t("WORKS_ESTIMATE_RESPONSE_MESSAGE_APPROVE", { estimateNumber: updatedEstimate?.estimateNumber })
    } else if (action?.includes("REJECT")) {
      return t("WORKS_ESTIMATE_RESPONSE_MESSAGE_REJECT", { estimateNumber: updatedEstimate?.estimateNumber })
    }
  }

  const getAttendanceResponseHeaderAndMessage = (action) => {
    let response = {}
    if (action?.includes("VERIFY")) {
      response.header = t("ATM_ATTENDANCE_VERIFIED")
      response.message = t("ATM_ATTENDANCE_VERIFIED_SUCCESS")
    } else if (action?.includes("REJECT")) {
      response.header = t("ATM_ATTENDANCE_REJECTED")
      response.message = t("ATM_ATTENDANCE_REJECTED_SUCCESS")
    } else if (action?.includes("APPROVE")) {
      response.header = t("ATM_ATTENDANCE_APPROVED")
      response.message = t("ATM_ATTENDANCE_APPROVED_SUCCESS")
    } 
    return response
  }

  const submitAction = async (data, nocData = false, isOBPS = {}) => {
    
    const performedAction = data?.workflow?.action
    setIsEnableLoader(true);
    if (mutate) {
      setIsEnableLoader(true);
      mutate(data, {
        onError: (error, variables) => {
          setIsEnableLoader(false);
          setShowToast({ key: "error", error });
          setTimeout(closeToast, 5000);
        },
        onSuccess: (data, variables) => {
          
          setIsEnableLoader(false);
          //just history.push to the response component from here and show relevant details
          if(data?.letterOfIndents?.[0]){
            const updatedLOI = data?.letterOfIndents?.[0]
            const state = {
              header:getResponseHeader(performedAction,updatedLOI),
              id: updatedLOI?.letterOfIndentNumber,
              info: t("WORKS_LOI_ID"),
              message: getResponseMessage(performedAction,updatedLOI),
              links: [
                {
                  name: t("WORKS_CREATE_NEW_LOI"),
                  redirectUrl: `/${window.contextPath}/employee/works/create-loi`,
                  code: "",
                  svg: "CreateEstimateIcon",
                  isVisible:false,
                  type:"add"
                },
                {
                  name: t("WORKS_GOTO_LOI_INBOX"),
                  redirectUrl: `/${window.contextPath}/employee/works/LOIInbox`,
                  code: "",
                  svg: "CreateEstimateIcon",
                  isVisible:true,
                  type:"inbox"
                },
              ],
              responseData:data,
              requestData:variables
            }
            history.push(`/${window.contextPath}/employee/works/response`, state)
          }
          if(data?.estimates?.[0]){
            const updatedEstimate = data?.estimates?.[0]
            const state = {
              header:getEstimateResponseHeader(performedAction,updatedEstimate),
              id: updatedEstimate?.estimateNumber,
              info: t("WORKS_ESTIMATE_ID"),
              message: getEstimateResponseMessage(performedAction,updatedEstimate),
              links: [
                {
                  name: t("WORKS_CREATE_ESTIMATE"),
                  redirectUrl: `/${window.contextPath}/employee/works/create-estimate`,
                  code: "",
                  svg: "CreateEstimateIcon",
                  isVisible:false,
                  type:"add"
                },
                {
                  name: t("WORKS_GOTO_ESTIMATE_INBOX"),
                  redirectUrl: `/${window.contextPath}/employee/works/inbox`,
                  code: "",
                  svg: "RefreshIcon",
                  isVisible:true,
                  type:"inbox"
                },
              ],
              responseData:data,
              requestData:variables
            }
            history.push(`/${window.contextPath}/employee/works/response`, state)
          }
          if(data?.musterRolls?.[0]) {
            const musterRoll = data?.musterRolls?.[0]
            const response = getAttendanceResponseHeaderAndMessage(performedAction)
            const state = {
              performedAction,
              header: response?.header,
              message: `${response?.message}`,
              info: t("ATM_MUSTER_ROLL_WEEK"),
              id: `${musterRoll.musterRollNumber} | ${format(new Date(musterRoll.startDate), "dd/MM/yyyy")} - ${format(new Date(musterRoll.endDate), "dd/MM/yyyy")}`,
            }
            if(performedAction==="APPROVE"){
              
              const individualEntriesResponse = data?.musterRolls?.[0]?.individualEntries
              
              individualEntriesResponse?.forEach(row=>{
                row.additionalDetails = { ...row.additionalDetails, accountType: "SAVINGS", accountHolderName: row?.additionalDetails?.userName, bankDetails: "880182873839-SBIN0001237", skillCode: row.additionalDetails?.skillValue ? row.additionalDetails?.skillValue:"UNSKILLED.MALE_MULIA" }
              })

            setBillingCallData({
              mustorObject: data?.musterRolls,
              responseScreenState: state
            })
          }
            else {
              history.push(`/${window.contextPath}/employee/attendencemgmt/response?musterRollNumber=${musterRoll.musterRollNumber}`, state)
            }
          }
          setShowToast({ key: "success", action: selectedAction });
          clearDataDetails && setTimeout(clearDataDetails, 3000);
          setTimeout(closeToast, 5000);
          queryClient.clear();
          queryClient.refetchQueries("APPLICATION_SEARCH");
          //push false status when reject
          //here make a dummy api Call and in response page show some static billNo
        },
      });
    }

    closeModal();
  };

  if (isLoading || isEnableLoader) {
    return <Loader />;
  }

  return (
    <React.Fragment>
      {!isLoading ? (
        <React.Fragment>
          <ApplicationDetailsContent
            applicationDetails={applicationDetails}
            workflowDetails={workflowDetails}
            isDataLoading={isDataLoading}
            applicationData={applicationData}
            timelineStatusPrefix={timelineStatusPrefix}
            statusAttribute={statusAttribute}
            paymentsList={paymentsList}
            showTimeLine={showTimeLine}
            oldValue={oldValue}
            isInfoLabel={isInfoLabel}
            noBoxShadow={noBoxShadow}
            sectionHeadStyle={sectionHeadStyle}
            modify={modify}
            setSaveAttendanceState={setSaveAttendanceState}
            applicationNo={props.applicationNo}
            tenantId={props.tenantId}
            businessService={businessService}
          />
          {showModal ? (
            <ActionModal
              t={t}
              action={selectedAction}
              tenantId={tenantId}
              state={state}
              id={applicationNumber}
              applicationDetails={applicationDetails}
              applicationData={applicationDetails?.applicationData}
              closeModal={closeModal}
              submitAction={submitAction}
              actionData={workflowDetails?.data?.timeline}
              businessService={businessService}
              workflowDetails={workflowDetails}
              moduleCode={moduleCode}
              saveAttendanceState={saveAttendanceState}
            />
          ) : null}
          {isWarningPop ? (
            <ApplicationDetailsWarningPopup
              action={selectedAction}
              workflowDetails={workflowDetails}
              businessService={businessService}
              isWarningPop={isWarningPop}
              closeWarningPopup={closeWarningPopup}
            />
          ) : null}
          <ApplicationDetailsToast t={t} showToast={showToast} closeToast={closeToast} businessService={businessService} />
          {showActionBar && <ApplicationDetailsActionBar
            workflowDetails={workflowDetails}
            displayMenu={displayMenu}
            onActionSelect={onActionSelect}
            setDisplayMenu={setDisplayMenu}
            businessService={businessService}
            forcedActionPrefix={forcedActionPrefix}
            ActionBarStyle={ActionBarStyle}
            MenuStyle={MenuStyle}
            saveAttendanceState={saveAttendanceState}
          />}
        </React.Fragment>
      ) : (
        <Loader />
      )}
    </React.Fragment>
  );
};

export default ApplicationDetails;
