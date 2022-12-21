import React from "react";

const GetCell = (value) => <span className="cell-text">{value}</span>;

const renderTotalLabel = (value) => {
    return (
      <div className="total-attendence-label">
        <span>{value}</span>
      </div>
    );
};

const renderBankAccountDetails = (value) => {
    return (
      <div className="column-bank-details">
        <p className="detail">{value.accountNo}</p>
        <p className="detail">{value.ifsc}</p>
      </div>
    );
};

export const mustorRollDetailsTableColumns = (t) => [
    {
        Header: "S. No",
        accessor: "sno",
        Cell: ({value, row}) => {
            
            if (row.original.type === "total") {
              return renderTotalLabel(t(value))
            }
            return GetCell(value)
          },
    },
    {
      Header: "Registration ID",
      accessor: (row) => (GetCell(row?.registrationId)),
    },
    {
        Header: "Name of the Individual",
        accessor: (row) => (GetCell(row?.individualName)),
    },
    {
        Header: "Father / Guardian’ Name",
        accessor: (row) => (GetCell(row?.guardianName)),
    },
    {
        Header: "Modified Amount (Rs)",
        accessor: (row) => (GetCell(row?.modifiedAmt)),
    },
    {
        Header: "Bank Account Details",
        accessor: "bankAccountDetails",
        Cell: ({ value }) => {
            return renderBankAccountDetails(value);
        }
    },
    {
        Header: "Aadhar Number",
        accessor: (row) => (GetCell(row?.aadhar)),
    }    
]