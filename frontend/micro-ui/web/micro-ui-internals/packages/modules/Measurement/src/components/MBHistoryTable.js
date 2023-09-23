
import { Card, Header, Button } from "@egovernments/digit-ui-react-components";
import React from "react";
import { useTranslation } from "react-i18next";
import { useState } from "react";

const CustomCollapsibleTable = ({ children, isTableCollapsed }) => {
  return (
    <div className={`custom-collapsible-table ${isTableCollapsed ? 'collapsed' : ''}`}>
      {children}
    </div>
  );
};



const MeasurementHistory = ({ contractNumber }) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();


  const [isTableCollapsed, setIsTableCollapsed] = useState(false);

  const toggleTableCollapse = () => {
    setIsTableCollapsed(prevState => !prevState);
  };

  const requestCriteria = {
    url: "/measurement-service/v1/_search",

    body: {
      criteria: {
        tenantId : tenantId,
        referenceId: [contractNumber],
        ids: ["70380648-45c2-4407-bf91-27ede3c481e5"],
      },
      pagination: {
        "limit": 10,
        "offSet": 0,
        "sortBy": "string",
        "order": "DESC"
    }
    },
  };

  var dummyResponse = {
    responseInfo: null,
    measurements: [
      {
        id: "1a39840f-6b30-4d19-9d1b-e5c88bd88d55",
        tenantId: "tenant1",
        measurementNumber: "mbNumber1",
        physicalRefNumber: "phyRef1",
        referenceId: "6470d652-7684-44dd-a5d4-d1cc14e451b2",
        entryDate: 1631606400,
        measures: [
          {
            id: null,
            referenceId: "6470d652-7684-44dd-a5d4-d1cc14e451b2",
            targetId: null,
            length: 10.5,
            breadth: 5.2,
            height: 3.0,
            numItems: 100,
            currentValue: null,
            cumulativeValue: null,
            isActive: null,
            comments: null,
            documents: null,
            auditDetails: null,
            additionalDetails: null,
          },
        ],
        isActive: true,
        auditDetails: {
          createdBy: "user1",
          lastModifiedBy: "user1",
          createdTime: 1631606400,
          lastModifiedTime: 1631606400,
        },
        additionalDetails: '{"key": "value1"}',
      },
    ],
  };
  const { isLoading, data } = Digit.Hooks.useCustomAPIHook(requestCriteria);
  const columns = [
    { label: t("MB_SNO"), key: "sno" },
    { label: t("MB_REFERENCE_NUMBER"), key: "mbref" },
    { label: t("MB_MUSTER_ROLL_ID"), key: "musterid" },
    { label: t("MB_DATE"), key: "mbDate" },
    { label: t("MB_PERIOD"), key: "period" },
    { label: t("MB_STATUS"), key: "status" },
    { label: t("MB_ONLY_AMOUNT"), key: "amount" },
  ];

  const rows = [
    {
      sno: 1,
      mbref: dummyResponse?.measurements[0]?.measurementNumber,
      musterid: "M123",
      mbDate: Digit.Utils.pt.convertDateToEpoch(dummyResponse?.measurements[0]?.entryDate),
      period: "September",
      status: "Paid",
      amount: 1000,
    },
  ];

  return (

    <Card className = "override-card">
      <Header className="works-header-view">{t("MB_HISTORY")}</Header>
      <CustomCollapsibleTable
        isTableCollapsed={isTableCollapsed}
      >

      <table className="table reports-table sub-work-table">
        <thead>
          <tr>
            {columns.map((column, index) => (
              <th key={index}>{column.label}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((row, rowIndex) => (
            <tr key={rowIndex}>
              {columns.map((column, columnIndex) => (
                <td key={columnIndex}>{row[column.key]}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>

      </CustomCollapsibleTable>
      <Button 
      className={"collapse-button"}
      onButtonClick={toggleTableCollapse}
      label={isTableCollapsed ? 'Show MB History' : 'Hide MB History'}>
      </Button>
    </Card>
  );
};

export default MeasurementHistory;
