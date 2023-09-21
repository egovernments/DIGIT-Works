import React from "react";

const MeasurementHistory = ({ contractNumber }) => {
  const requestCriteria = {
    url: "/measurement/v1/_search",
    body: {
      criteria: {
        referenceId: [contractNumber],
        // measurementNumber : "mbNumber1",
        // ids: ["1a39840f-6b30-4d19-9d1b-e5c88bd88d55"]
      },
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
    { label: "S.No", key: "sno" },
    { label: "MB reference number", key: "mbref" },
    { label: "Muster Roll ID", key: "musterid" },
    { label: "MB Date", key: "mbDate" },
    { label: "Period", key: "period" },
    { label: "Status", key: "status" },
    { label: "Amount", key: "amount" },
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
    // Add more rows as needed
  ];

  return (
    <table className="table reports-table sub-work-table" style={{ marginTop: "-2rem" }}>
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
  );
};

export default MeasurementHistory;
